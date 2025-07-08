package com.kh.saintra.department.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.department.model.dto.DepartmentInsertDTO;
import com.kh.saintra.department.model.dto.DepartmentListDTO;
import com.kh.saintra.department.model.vo.DepartmentPageVO;
import com.kh.saintra.department.model.vo.DepartmentVO;

@Mapper
public interface DepartmentMapper {

	int selectTotalDeptCount(DepartmentListDTO deptListInfo);
	
	List<DepartmentVO> selectDepartmentList();
	
	List<String> selectDepartmentNameList();
	
	int checkPersonnel();
	
	int checkInspect();
	
	int insertDepartment(DepartmentInsertDTO deptInsertInfo);
	
	DepartmentPageVO selectDepartmentPage(Long id);
	
	DepartmentVO selectDepartmentById(Long id);
	
	int updatePersonnel(Long deptId, String personnel);
	
	int updateInspect(Long deptId, String inspect);
	
	int updateDepartment(Long deptId, String deptName);
	
	int enableDepartment(Long deptId);
	
	int deleteDepartment(Long deptId);
}
