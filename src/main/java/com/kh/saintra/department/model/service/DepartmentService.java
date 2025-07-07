package com.kh.saintra.department.model.service;

import java.util.List;
import java.util.Map;

import com.kh.saintra.department.model.dto.DepartmentDeleteDTO;
import com.kh.saintra.department.model.dto.DepartmentInsertDTO;
import com.kh.saintra.department.model.dto.DepartmentListDTO;
import com.kh.saintra.department.model.dto.DepartmentUpdateDTO;
import com.kh.saintra.department.model.vo.DepartmentPageVO;
import com.kh.saintra.department.model.vo.DepartmentVO;


public interface DepartmentService {

	List<DepartmentVO> getDepartmentList();
	
	void insertDepartment(DepartmentInsertDTO deptInsertInfo);
	
	DepartmentPageVO getDepartmentPage(Long deptId);
	
	void updateDepartment(Long deptId, DepartmentUpdateDTO deptUpdateInfo);
	
	void enableDepartment(Long deptId);
	
	void deleteDepartment(Long deptId, DepartmentDeleteDTO deptDeleteInfo);
}
