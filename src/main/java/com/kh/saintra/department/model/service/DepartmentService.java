package com.kh.saintra.department.model.service;

import java.util.Map;

import com.kh.saintra.department.model.dto.DepartmentDeleteDTO;
import com.kh.saintra.department.model.dto.DepartmentInsertDTO;
import com.kh.saintra.department.model.dto.DepartmentListDTO;
import com.kh.saintra.department.model.dto.DepartmentUpdateDTO;
import com.kh.saintra.department.model.vo.DepartmentPageVO;


public interface DepartmentService {

	/**
	 * 조건에 맞는 부서 목록 가져오고 페이지네이션해서 반환
	 * @param deptListInfo
	 * @return 페이지네이션한 부서 목록
	 */
	Map<String, Object> getDepartmentList(DepartmentListDTO deptListInfo);
	
	void insertDepartment(DepartmentInsertDTO deptInsertInfo);
	
	DepartmentPageVO getDepartmentPage(String deptId);
	
	void updateDepartment(String deptId, DepartmentUpdateDTO deptUpdateInfo);
	
	void deleteDepartment(String deptId, DepartmentDeleteDTO deptDeleteInfo);
}
