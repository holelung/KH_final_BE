package com.kh.saintra.department.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.department.model.dao.DepartmentMapper;
import com.kh.saintra.department.model.dto.DepartmentDeleteDTO;
import com.kh.saintra.department.model.dto.DepartmentInsertDTO;
import com.kh.saintra.department.model.dto.DepartmentListDTO;
import com.kh.saintra.department.model.dto.DepartmentUpdateDTO;
import com.kh.saintra.department.model.vo.DepartmentPageVO;
import com.kh.saintra.department.model.vo.DepartmentVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.global.error.exceptions.UnauthorizedAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final AuthService authService;
	private final DepartmentMapper departmentMapper;
	
	@Override
	public List<DepartmentVO> getDepartmentList() {

		List<DepartmentVO> departmentList = departmentMapper.selectDepartmentList();
		
		return departmentList;
	}

	@Override
	public void insertDepartment(DepartmentInsertDTO deptInsertInfo) {
		
		// 토큰에서 가져온 사용자 정보와 요청 데이터의 사용자 정보 비교

		log.info("departmentInsertInfo2: {}", deptInsertInfo);
		
		// 이미 존재하는 부서와 이름이 중복되는지 확인
		String deptName = deptInsertInfo.getDepartmentName();
		
		List<String> deptNameList = departmentMapper.selectDepartmentNameList();
		
		if(deptNameList.contains(deptName)) {
			
			throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "이미 존재하는 부서 이름 입니다.");
		}
		
		// 부서 권한 관련 신청이 왔을 때, 해당 권한을 이미 가지고 있는 부서가 존재하는지 확인
		String personnel = deptInsertInfo.getPersonnel();
		String inspect = deptInsertInfo.getInspect();
		
		if(personnel.equals("Y") && inspect.equals("Y")) {
			
			throw new InvalidValueException(ResponseCode.INVALID_VALUE, "하나의 부서가 2개 이상의 권한을 가질 수는 없습니다.");
		}
		
		if(personnel.equals("Y")) {
			
			if(departmentMapper.checkPersonnel() != 0) {
				
				throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "인사 권한을 가진 부서가 존재합니다.");
			}
		}
		
		if(inspect.equals("Y")) {
			
			if(departmentMapper.checkInspect() != 0) {
				
				throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "감사 권한을 가진 부서가 존재합니다.");
			}
		}
		
		log.info("departmentInsertInfo3: {}", deptInsertInfo);
		
		// 부서 정보 삽입
		if(departmentMapper.insertDepartment(deptInsertInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "부서 등록이 정상적으로 이뤄지지 않았습니다.");
		}
	}

	@Override
	public DepartmentPageVO getDepartmentPage(Long deptId) {
		
		// 토큰의 정보를 통해 해당 부서에 소속된 사용자인지 확인
		if(!(deptId.equals(authService.getUserDetails().getDeptId()))) {
			
			throw new UnauthorizedAccessException(ResponseCode.INVALID_ACCESS, "접근 권한이 없습니다.");
		}
		
		// 부서 페이지 정보 요청하고 반환(나중에 작업)
		
		
		return null;
	}

	@Override
	public void updateDepartment(Long deptId, DepartmentUpdateDTO deptUpdateInfo) {
		
		// 토큰에서 가져온 사용자 정보와 요청 데이터의 사용자 정보 비교
		CustomUserDetails userInfo = authService.getUserDetails();
		
		DepartmentVO deptToUpdate = departmentMapper.selectDepartmentById(deptId);
		
		if(deptToUpdate == null) {
			
			throw new EntityNotFoundException(ResponseCode.SQL_ERROR, "존재하지 않는 부서 입니다.");
		}
		
		// 사용자가 해당 부서 소속이며 일정 직급 이상인지 확인
		if(!(userInfo.getDeptId()).equals(deptId)) {
			
			throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "해당 부서 소속이 아닙니다.");
		}
		
		if(Integer.parseInt(userInfo.getJobId()) < 5) {
			
			throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "관리 직급이 아닙니다.");
		}
		
		// 수정하려는 부서 이름이 다른 부서의 이름과 중복되지 않는지 확인
		if(departmentMapper.selectDepartmentNameList().contains(deptUpdateInfo.getDepartmentName())) {
			
			throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "이미 존재하는 부서 이름 입니다.");
		}
		
		// 부서 이름 수정
		if(departmentMapper.updateDepartment(deptId, deptUpdateInfo.getDepartmentName()) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "부서 이름 수정에 실패 했습니다.");
		}
		
		// 부서 권한 수정
		String personnel = deptUpdateInfo.getPersonnel();
		String inspect = deptUpdateInfo.getInspect();
		
		if(personnel.equals("Y") && inspect.equals("Y")) {
			
			throw new InvalidValueException(ResponseCode.INVALID_VALUE, "하나의 부서가 2개 이상의 권한을 가질 수는 없습니다.");
		}
		
		if(!deptToUpdate.getPersonnel().equals(personnel)) {
			
			if(personnel.equals("Y")) {
				
				if(departmentMapper.checkPersonnel() != 0) {
					
					throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "인사 권한을 가진 부서가 존재합니다.");
				}
			}
			
			if(departmentMapper.updatePersonnel(deptId, personnel) != 1) {
				
				throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "인사 권한 수정에 실패 했습니다.");
			}
		}
		
		if(!deptToUpdate.getInspect().equals(inspect)) {
			
			if(inspect.equals("Y")) {
				
				if(departmentMapper.checkInspect() != 0) {
					
					throw new DuplicateDataException(ResponseCode.INVALID_VALUE, "감사 권한을 가진 부서가 존재합니다.");
				}
			}
			
			if(departmentMapper.updateInspect(deptId, inspect) != 1) {
				
				throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "감사 권한 수정에 실패 했습니다.");
			}
		}
	}
	
	@Override
	public void enableDepartment(Long deptId) {
		
		if(departmentMapper.enableDepartment(deptId) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "부서 활성화에 실패 했습니다.");
		}
	}

	@Override
	public void deleteDepartment(Long deptId, DepartmentDeleteDTO deptDeleteInfo) {
		
		CustomUserDetails userInfo = authService.getUserDetails();
		
		DepartmentVO deptToUpdate = departmentMapper.selectDepartmentById(deptId);
		
		if(deptToUpdate == null) {
			
			throw new EntityNotFoundException(ResponseCode.SQL_ERROR, "존재하지 않는 부서 입니다.");
		}
		
		// 사용자가 해당 부서 소속이며 일정 직급 이상인지 확인
		if(!(userInfo.getDeptId()).equals(Long.toString(deptId))) {
			
			throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "해당 부서 소속이 아닙니다.");
		}
		
		if(Integer.parseInt(userInfo.getJobId()) < 5) {
			
			throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "관리 직급이 아닙니다.");
		}
		
		// 부서 삭제
		if(departmentMapper.deleteDepartment(deptId) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "부서 삭제에 실패 했습니다.");
		}
	}
}
