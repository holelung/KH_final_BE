package com.kh.saintra.global.aop;

import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    
    private final LogService logService;
    
    @Pointcut("execution(* com.kh.saintra..controller..*(..))"
        + " && !within(com.kh.saintra..controller.UserStatusController)"
        + " && !within(com.kh.saintra..controller.MailController)"
        + " && !execution(* com.kh.saintra..controller.UserController.join(..))"
        + " && !execution(* com.kh.saintra..controller.AuthController.findPassword(..))"
        + " && !execution(* com.kh.saintra..controller.AuthController.login(..))"
        + " && !execution(* com.kh.saintra..controller.AuthController.changePassword(..))"
        + " && !execution(* com.kh.saintra..controller.AuthController.refresh(..))")
    public void httpControllerMethods() {}

    @Around("httpControllerMethods()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("AOP 진입");

        long start = System.currentTimeMillis();
        Object result = null;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr != null ? attr.getRequest() : null;
        String requestUri = request != null ? request.getRequestURI() : null;
        String clientIp = request != null ? request.getRemoteAddr() : "UNKNOWN";
        String referer = request != null ? request.getHeader("Referer") : "NONE";
        String httpMethod = request != null ? request.getMethod() : "UNKNOWN";

        Long userId = getUserSafely();
        if( userId == null) {
            return joinPoint.proceed();
        }

        LogDTO logDto = LogDTO.builder()
                .userId(userId)
                .actionArea(requestUri)
                .actionType(httpMethod)
                .clientIp(clientIp)
                .referer(referer)
                .build();

        try {
            result = joinPoint.proceed();
            log.info("[log 접속 IP] : {}", clientIp);
            log.info("[log 요청 url] : {}", requestUri);
            log.info("[log 접속 referer] : {}", referer);
            log.info("[log 접속 httpMethod] : {}", httpMethod);
            log.info("[log 접속 userId] : {}", userId);
            logDto.setActionResult("Success");
            logService.saveLog(logDto);

        } catch (Exception e) {
            result = joinPoint.getSignature();
            log.info("[트랜잭션 롤백]: {}", result);

            logDto.setActionResult("Failure");
            logService.saveLog(logDto);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            log.debug("Action: {}.{} - {}ms", requestUri, httpMethod, end-start);
        }

        return result;
    }


    private Long getUserSafely() {
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .map(auth -> (CustomUserDetails) auth.getPrincipal())
                    .map(CustomUserDetails::getId)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
