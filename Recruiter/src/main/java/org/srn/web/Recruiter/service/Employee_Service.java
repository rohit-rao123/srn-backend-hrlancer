package org.srn.web.Recruiter.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;

public interface Employee_Service {

	public ResponseEntity<ResponseDto> fetchEmployee(HttpServletRequest request ,String name, String contact, String official_id, String department);

	public ResponseEntity<ResponseDto> saveEmployee( HttpServletRequest request ,String signum_id, String employee_name, String official_id, String personal_id, String mobile_number,
			String email, Date date_of_joining, Date date_of_exit, String offer_salary, String current_salary, String department, String initial_designation, String current_designation,
			String job_stage, String offer_id, String employee_status );
	
	public ResponseEntity<ResponseDto> deleteEmployee(HttpServletRequest request , long employee_id);
	
	public ResponseEntity<ResponseDto> fetchAll(HttpServletRequest request);
	
	public ResponseEntity<ResponseDto> fetchByJoiningDate(HttpServletRequest request, int year);

	public ResponseEntity<ResponseDto> fetchByExitDate(HttpServletRequest request, int year);

	public ResponseEntity<ResponseDto> update_employee(HttpServletRequest request, long employee_id, String email,
			String contact, Date date_of_exit, String current_designation, String current_salary);
}
