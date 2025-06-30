package com.kh.saintra.user.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attendance {
    
    private Long id;
    private Long userId;
    private String type;
    private LocalDateTime time;
    
}
