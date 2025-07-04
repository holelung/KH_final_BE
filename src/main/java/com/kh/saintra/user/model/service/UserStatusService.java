package com.kh.saintra.user.model.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.kh.saintra.user.model.dto.UserStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatusService {
    
    private final SimpMessagingTemplate template;
    private final Map<String, String> statusMap = new ConcurrentHashMap<>();

    public void updateStatus(UserStatusDTO dto){
        statusMap.put(dto.username(), dto.status());
        broadcast();
    }

    public void markOnline(String username) {
        statusMap.put(username, "ONLINE");
        log.info("로그인: {}", statusMap);
        broadcast();
    }

    public void markOffline(String username) {
        statusMap.put(username, "OFFLINE");
        log.info("로그아웃: {}", statusMap);
        broadcast();
    }

    public void getStatus() {
        broadcast();
    }

    private void broadcast(){
        var list = statusMap.entrySet()
            .stream()
            .map(e ->  new UserStatusDTO(e.getKey(), e.getValue()))
            .toList();
        
        template.convertAndSend("/topic/users",list);
    }
}
