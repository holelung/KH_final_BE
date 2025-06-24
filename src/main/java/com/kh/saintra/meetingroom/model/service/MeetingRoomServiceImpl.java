package com.kh.saintra.meetingroom.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.meetingroom.model.dao.MeetingRoomMapper;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService{
	
	private final MeetingRoomMapper meetingRoomMapper;

	
	@Override
    public List<MeetingRoomDTO> getWeeklyReservations(String startDate, String endDate) {
        try {
            // 날짜 형식 검증만 (yyyy-MM-dd)
            LocalDate.parse(startDate);
            LocalDate.parse(endDate);

            return meetingRoomMapper.getWeeklyReservations(startDate, endDate);

        } catch (DateTimeParseException e) {
            throw new InvalidValueException("날짜 형식이 잘못되었습니다. (yyyy-MM-dd)");
        } catch (Exception e) {
            throw new DataAccessException("예약 목록을 조회하는 도중 오류가 발생했습니다.");
        }
    }
}
