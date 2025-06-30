package com.kh.saintra.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserSocketController {
    
    private final SimpMessagingTemplate template;

    
    @MessageMapping("/chat.test")
	public void greet() {
		String text = "[" + getTimestamp() + "] :";
		System.out.println("▶▶▶ greet() 호출됨");
		template.convertAndSend("/topic/message", text);
	}

    private String getTimestamp() {
		return new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
	}
}
