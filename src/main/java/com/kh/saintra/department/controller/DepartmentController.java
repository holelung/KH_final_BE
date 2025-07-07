package com.kh.saintra.department.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.department.model.dto.DepartmentDeleteDTO;
import com.kh.saintra.department.model.dto.DepartmentInsertDTO;
import com.kh.saintra.department.model.dto.DepartmentListDTO;
import com.kh.saintra.department.model.dto.DepartmentUpdateDTO;
import com.kh.saintra.department.model.service.DepartmentService;
import com.kh.saintra.department.model.vo.DepartmentVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;
	
	@GetMapping
	@Transactional
	public ResponseEntity<?> getDepartmentList() {
		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList();
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, departmentList, "부서 목록 응답 성공"));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> insertDepartment(@RequestBody @Valid DepartmentInsertDTO departmentInsertInfo) {
		
		departmentService.insertDepartment(departmentInsertInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, "부서 신청 등록 성공"));
	}
	
	@GetMapping("/{deptId}")
	@Transactional
	public ResponseEntity<?> getDepartmentPage(@PathVariable(name = "deptId") Long deptId) {
		
		// 팀 관련 API 완성되면 다시 작업
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, "부서 페이지 응답 성공"));
	}
	
	@PutMapping("/{deptId}")
	@Transactional
	public ResponseEntity<?> updateDepartment(@PathVariable(name = "deptId") Long deptId, DepartmentUpdateDTO departmentUpdateInfo) {
		
		departmentService.updateDepartment(deptId, departmentUpdateInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "부서 정보 수정 성공"));
	}
	
	@PatchMapping("/{deptId}")
	@Transactional
	public ResponseEntity<?> enableDepartment(@PathVariable(name = "deptId") Long deptId) {
		
		log.info("deptId: {}", deptId);
		
		departmentService.enableDepartment(deptId);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "부서 활성화 성공"));
	}
	
	@DeleteMapping("/{deptId}")
	@Transactional
	public ResponseEntity<?> deleteDepartment(@PathVariable(name = "deptId") Long deptId, @RequestBody @Valid DepartmentDeleteDTO departmentDeleteInfo) {
		
		departmentService.deleteDepartment(deptId, departmentDeleteInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETE_SUCCESS, "부서 정보 삭제 성공"));
	}
}
