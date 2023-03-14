package org.srn.web.Recruiter.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Employee_Service_Impl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping(value = "/Recruit/Employee")
////@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
//
//@Api(value = "Employee Management", description = "Employee Management Operation", tags = "Employee Controller")
public class Employee_Controller {

	@Autowired
	private Employee_Service_Impl employee_service;

	@PostMapping(value = "/add_employee")
	@ApiOperation(value = "search profile", notes = "This Rest Api fetch the profile detail by applicant mobile number or email or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> add_employee(HttpServletRequest request,
			@ApiParam(value = "enter signum_id", required = true) @RequestParam String signum_id,
			@ApiParam(value = "enter employee_name", required = true) @RequestParam String employee_name,
			@ApiParam(value = "enter official_id", required = true) @RequestParam String official_id,
			@ApiParam(value = "enter personal_id", required = true) @RequestParam String personal_id,
			@ApiParam(value = "enter mobile_number", required = true) @RequestParam String mobile_number,
			@ApiParam(value = "enter email", required = true) @RequestParam String email,
			@ApiParam(value = "yyyy-MM-dd date format") @RequestParam(defaultValue = "", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_of_joining,
			@ApiParam(value = "yyyy-MM-dd  date format") @RequestParam(defaultValue = "", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_of_exit,
			@ApiParam(value = "enter offer_salary", required = true) @RequestParam String offer_salary,
			@ApiParam(value = "enter current_salary", required = true) @RequestParam String current_salary,
			@ApiParam(value = "enter department", required = true) @RequestParam String department,
			@ApiParam(value = "enter initial_designation", required = true) @RequestParam String initial_designation,
			@ApiParam(value = "enter current_designation", required = true) @RequestParam String current_designation,
			@ApiParam(value = "enter job_stage") @RequestParam String job_stage,
			@ApiParam(value = "enter offer_id") @RequestParam String offer_id,
			@ApiParam(value = "enter employee_status") @RequestParam String employee_status) {
		return employee_service.saveEmployee(request, signum_id, employee_name, official_id, personal_id, mobile_number,
				email, date_of_joining, date_of_exit, offer_salary, current_salary, department, initial_designation,
				current_designation, job_stage, offer_id, employee_status);
	}

	@GetMapping(value = "/search_employee")
	@ApiOperation(value = "search profile", notes = "This Rest Api fetch the profile detail by applicant mobile number or email or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> get_employee(HttpServletRequest request,
			@ApiParam(value = "enter employee name") @RequestParam(defaultValue = "", required = false) String name,
			@ApiParam(value = "enter employee mobile number") @RequestParam(defaultValue = "", required = false) String contact,
			@ApiParam(value = "enter official_id") @RequestParam(defaultValue = "", required = false) String official_id,
			@ApiParam(value = "enter department") @RequestParam(defaultValue = "", required = false) String department) {
		return employee_service.fetchEmployee(request, name, contact, official_id, department);
	}

	@DeleteMapping(value = "/delete_employee")
	@ApiOperation(value = "delete_employee", notes = "This Rest Api delete the employee details by ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> delete_employee(HttpServletRequest request,
			@ApiParam(value = "enter employee_id", required = true) @RequestParam long employee_id) {
		return employee_service.deleteEmployee(request, employee_id);
	}

	@GetMapping(value = "/get_all_employee")
	@ApiOperation(value = "get all employee", notes = "This Rest Api get the all employee details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpServletRequest request) {
		return employee_service.fetchALLEmployee(request);
	}

	@GetMapping(value = "/get_by_year")
	@ApiOperation(value = "get all employee by year", notes = "This Rest Api get the all employee details by year")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getByJoiningYear(HttpServletRequest request,
			@ApiParam(value = "search by year_of_joining", required = false) @RequestParam(defaultValue = "0", required = false) int year_of_joining,
			@ApiParam(value = "search by year_of_exit", required = false) @RequestParam(defaultValue = "0", required = false) int year_of_exit) {
		if (year_of_joining != 0 && year_of_exit == 0) {
			return employee_service.fetchByJoiningDate(request, year_of_joining);
		} else if (year_of_joining == 0 && year_of_exit != 0) {
			return employee_service.fetchByExitDate(request, year_of_exit);
		} else {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage(VMessageConstant.INVALID);
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}
	
	@PutMapping(value = "/update_employee")
	public ResponseEntity<ResponseDto> update_employee(HttpServletRequest request,
			@ApiParam(value = "enter employee_id", required = true) @RequestParam long employee_id,
			@ApiParam(value = "update email", required = false) @RequestParam(defaultValue = "", required = false)  String email,
			@ApiParam(value = "update contact", required = false) @RequestParam(defaultValue = "", required = false)  String contact,
			@ApiParam(value = "yyyy-MM-dd  date format") @RequestParam(defaultValue = "", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_of_exit,
			@ApiParam(value = "update current_designation", required = false) @RequestParam(defaultValue = "", required = false)  String current_designation,
			@ApiParam(value = "update current_salary", required = false) @RequestParam(defaultValue = "", required = false)  String current_salary) {
		return employee_service.update_employee(request, employee_id, email, contact, date_of_exit, current_designation,
				current_salary);
	}

}
