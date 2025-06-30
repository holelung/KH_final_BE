package com.kh.saintra.meetingroom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomResponseDTO {
    private Long reservationId;     // 예약 고유 ID
    private Long roomId;            // 회의실 ID
    private String roomName;        // 회의실 이름
    private String roomLocation;     // 회의실 위치
    private int capacity;        // 회의실 수용 인원
    private String reserveDate;     // 예약 날짜 (yyyy-MM-dd)
    private String startTime;       // 시작 시간 (HH:mm)
    private String endTime;         // 종료 시간 (HH:mm)
    private String purpose;         // 예약 목적
    private String reserverType;    // USER 또는 TEAM
    private Long reserverId;        // 예약자 ID
    private String reserverName;    // 예약자 이름 (사용자 이름 or 팀명)
    private String isActive;          // 예약 상태 (Y/N)
}
