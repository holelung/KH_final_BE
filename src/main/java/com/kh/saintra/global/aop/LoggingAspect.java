package com.kh.saintra.global.aop;

import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.service.LogService;
import com.kh.saintra.global.logging.model.vo.Log;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    
    // 목적 사용자가 무엇을 했는지 알고자함
    // 그럼 컨트롤러 단에서만 로그를찍으면 되지않나?
    
    private final LogService logService;
    

    @Around("execution(* com.kh.saintra..controller..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = null;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr != null ? attr.getRequest() : null;
        String requestUri = request != null ? request.getRequestURI() : null;
        String clientIp = request != null ? request.getRemoteAddr() : "UNKNOWN";
        String referer = request != null ? request.getHeader("Referer") : "NONE";
        String httpMethod = request != null ? request.getMethod() : "UNKNOWN";

        // CustomUserDetails user = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = getUserSafely();

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
                    .orElse(0001L);
        } catch (Exception e) {
            return null;
        }
    }
}
