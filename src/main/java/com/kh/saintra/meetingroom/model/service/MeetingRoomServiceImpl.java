package com.kh.saintra.meetingroom.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.global.error.exceptions.UnauthorizedAccessException;
import com.kh.saintra.global.error.exceptions.UnknownException;
import com.kh.saintra.meetingroom.model.dao.MeetingRoomMapper;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;
import com.kh.saintra.meetingroom.model.vo.MeetingRoom;
import com.kh.saintra.schedule.model.dto.ScheduleRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomMapper meetingRoomMapper;
    
    // 1. 회의실 주간 예약 조회
    @Override
    public List<MeetingRoomResponseDTO> getWeeklyReservations(LocalDate startDate, 
            LocalDate endDate) {
        return executeWithExceptionHandling("예약 목록 조회", () -> {
            List<MeetingRoomResponseDTO> list = meetingRoomMapper.getWeeklyReservations(startDate, endDate);
            log.debug("예약 결과 리스트: {}", list);
            return list;
        });
    }


    // 2. 회의실 예약 등록
    @Override
    @Transactional
    public Long createReservation(MeetingRoomRequestDTO dto, Long createdBy) {
        return executeWithExceptionHandling("예약 등록", () -> {
            validateTime(dto.getStartTime(), dto.getEndTime());
            checkMeetingRoomExists(dto.getRoomId());
            checkDuplicateReservation(dto);

            Long reserverId = registerReserver(dto.getReserverType());
            insertReserverMappingByType(dto, reserverId);
            LocalDateTime startDateTime = dto.getReserveDate().atTime(dto.getStartTime());
            LocalDateTime endDateTime = dto.getReserveDate().atTime(dto.getEndTime());
            insertReservation(dto, reserverId, createdBy, startDateTime, endDateTime);

            return dto.getReservationId();
        });
    }

    // 3. 회의실 예약 수정 
    @Override
    @Transactional
    public Long updateReservation(MeetingRoomRequestDTO dto, Long userId) {
        return executeWithExceptionHandling("예약 수정", () -> {
            validateTime(dto.getStartTime(), dto.getEndTime());
            checkMeetingRoomExists(dto.getRoomId());        

            validateReservation(dto.getReservationId(), userId);
            duplicateForUpdate(dto);

            int result = meetingRoomMapper.updateReservation(dto);
            if (result != 1) {
                throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "회의실 예약 수정에 실패하였습니다.");
            }

            return dto.getReservationId();
        });
    }

    // 4.회의실 예약 삭제 
    @Override
    @Transactional
    public Long deleteReservation(Long reservationId, Long userId) {
        return executeWithExceptionHandling("예약 삭제", () -> {
            MeetingRoom existing = meetingRoomMapper.findReservationById(reservationId);
            if (existing == null) {
                throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "해당 예약이 존재하지 않습니다.");
            }

            if (!existing.getCreatedBy().equals(userId)) {
                throw new UnauthorizedAccessException(ResponseCode.AUTH_FAIL, "해당 예약에 대한 삭제 권한이 없습니다.");
            }

            int result = meetingRoomMapper.deleteReservation(reservationId);
            if (result != 1) {
                throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "회의실 예약 삭제에 실패하였습니다.");
            }

            return reservationId;
        });
    }

 // 공통 예외 처리 메서드
    private <T> T executeWithExceptionHandling(String action, Supplier<T> logic) {
        try {
            return logic.get();
        } catch (DuplicateDataException | InvalidValueException | EntityNotFoundException |
                 DataAccessException | UnauthorizedAccessException e) {
            log.warn("{} 실패: {}", action, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("{} 중 알 수 없는 오류 발생", action, e);
            throw new UnknownException(ResponseCode.UNKNOWN_ERROR, action + " 중 오류가 발생했습니다.");
        }
    }

    // 시간 유효성 검사
    private void validateTime(LocalTime startTime, LocalTime endTime) {
        
        if (!startTime.isBefore(endTime)) {
            throw new InvalidValueException(ResponseCode.INVALID_VALUE, "시작 시간은 종료 시간보다 이전이어야 합니다.");
        }
    }

    // 회의실 존재 여부 확인
    private void checkMeetingRoomExists(Long roomId) {
        if (meetingRoomMapper.existsMeetingRoom(roomId) == 0) {
            throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "회의실이 존재하지 않습니다.");
        }
    }


    // 중복 예약 검사
    private void checkDuplicateReservation(MeetingRoomRequestDTO dto) {
        if (meetingRoomMapper.existsDuplicateReservation(dto) > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATE_RESERVATION, "해당 시간대에 이미 예약이 존재합니다.");
        }
    }

    // 예약자 등록 및 ID 반환
    private Long registerReserver(String reserverType) {
        MeetingRoom reserverVo = MeetingRoom.builder()
                .reserverType(reserverType)
                .build();

        int result = meetingRoomMapper.insertReserver(reserverVo);
        Long reserverId = reserverVo.getId();

        if (result != 1 || reserverId == null) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 정보 저장에 실패했습니다.");
        }

        return reserverId;
    }

    // 예약자 유형별 INSERT
    private void insertReserverMappingByType(MeetingRoomRequestDTO dto, Long reserverId) {
        switch (dto.getReserverType()) {
            case "USER" -> {
                if (dto.getUserId() == null) {
                    throw new InvalidValueException(ResponseCode.INVALID_VALUE, "USER 예약자는 userId가 필요합니다.");
                }
                insertUserReserver(reserverId, dto.getUserId());
            }
            case "TEAM" -> {
                if (dto.getTeamId() == null) {
                    throw new InvalidValueException(ResponseCode.INVALID_VALUE, "TEAM 예약자는 teamId가 필요합니다.");
                }
                insertTeamReserver(reserverId, dto.getTeamId());
            }
            default -> throw new InvalidValueException(ResponseCode.INVALID_VALUE, "예약자 유형은 USER 또는 TEAM이어야 합니다.");
        }
    }
    
    // 예약자 유형이 USER일 경우 INSERT
    private void insertUserReserver(Long reserverId, Long userId) {
        int result = meetingRoomMapper.insertUserReserver(reserverId, userId);
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "USER 예약자 저장에 실패했습니다.");
        }
    }

    // 예약자 유형이 TEAM일 경우 INSERT
    private void insertTeamReserver(Long reserverId, Long teamId) {
        int result = meetingRoomMapper.insertTeamReserver(reserverId, teamId);
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "TEAM 예약자 저장에 실패했습니다.");
        }
    }
    
    // 예약 등록
    private void insertReservation(MeetingRoomRequestDTO dto, Long reserverId, Long createdBy, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int result = meetingRoomMapper.insertReservation(dto, reserverId, createdBy, startDateTime, endDateTime );
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "회의실 예약 등록에 실패했습니다.");
        }
    }

    // 수정용 중복 예약 검사 (자기자신 제외)
    private void duplicateForUpdate(MeetingRoomRequestDTO dto) {
        int count = meetingRoomMapper.duplicateForUpdate(dto);
        if (count > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATE_RESERVATION, "해당 시간대에 다른 예약이 이미 존재합니다.");
        }
    }

    // 예약 존재 + 권한 확인
    private MeetingRoom validateReservation(Long reservationId, Long userId) {
        MeetingRoom reservation = meetingRoomMapper.findReservationById(reservationId);

        if (reservation == null) {
            throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "해당 예약이 존재하지 않습니다.");
        }

        if (!Objects.equals(reservation.getCreatedBy(), userId)) {
            throw new UnauthorizedAccessException(ResponseCode.AUTH_FAIL, "해당 예약에 대한 수정 권한이 없습니다.");
        }

        return reservation;
    }
    
    // 회의실 목록 조회 
    @Override
    public List<MeetingRoomResponseDTO> getAllRooms() {
        try {
            return meetingRoomMapper.selectAllRooms();
        } catch (Exception e) {
        	log.error("❌ 회의실 목록 조회 중 오류 발생", e);
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "회의실 목록 조회에 실패했습니다.");
        }
    }
}
