package com.kh.saintra.chat.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageDTO {

	private String teamId;
	
    private String messageId;
    
    private String senderId;   

    private String senderName;    
    
    private String profileImgUrl;  

    private String content;      
    
    private String sentDate;  
    
    private String type;
}
