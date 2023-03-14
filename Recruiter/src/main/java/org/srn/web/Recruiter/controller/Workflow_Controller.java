package org.srn.web.Recruiter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Workflow_Service_impl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@RestController
//@RequestMapping(value = "/Recruit/Workflow")
//@Api(value = "Workflow_microservice", description = "To perform crud operation on Workflow", tags ="Workflow_Controller")
public class Workflow_Controller {

	@Autowired
	Workflow_Service_impl work_service;
	
//	long workflow_id, long job_id, long application_id, String recruiter_email,
//	Date tech_interview_1_date, String tech_interviewer_1_name, String tech_interviewer_1_comment,
//	Date tech_interview_2_date, String tech_interviewer_2_name, String tech_interviewer_2_comment,
//	Date manager_interview_1_date, String manager_interviewer_1_name, String manager_interviewer_1_comment,
//	Date hr_interview_1_date, String hr_interviewer_1_name, String hr_interviewer_1_comment,
//	Date client_interview_1_date, String client_interviewer_1_name, String client_interviewer_1_comment,
//	String offer_letter_id, double offer_amt, Date onbloading_dt, double joining_amt, String recuiter_stage,
//	String recuiter_status, String created_by, Date workflow_creation_date, int status
	@PostMapping("/add/workflow")
	@ApiOperation(value = " add workflow", notes = "this Rest Api add the workflow")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> createBulkProfile( @RequestParam long job_id,  @RequestParam long application_id, @RequestParam String recruiter_email,
			@RequestParam String tech_interview_1_date, @RequestParam String tech_interviewer_1_name,@RequestParam String tech_interviewer_1_comment,
			@RequestParam String tech_interview_2_date, @RequestParam String tech_interviewer_2_name,@RequestParam String tech_interviewer_2_comment,
			@RequestParam String manager_interview_1_date, @RequestParam String manager_interviewer_1_name,@RequestParam String manager_interviewer_1_comment,
			@RequestParam String hr_interview_1_date, @RequestParam String hr_interviewer_1_name,@RequestParam String hr_interviewer_1_comment,
			@RequestParam String client_interview_1_date, @RequestParam String client_interviewer_1_name,@RequestParam String client_interviewer_1_comment,
			@RequestParam String offer_letter_id, @RequestParam double offer_amt, @RequestParam String onbloading_dt, @RequestParam double joining_amt,
			@RequestParam String recuiter_stage,
			@RequestParam String recuiter_status) {

		return work_service.addWorkflow(job_id,application_id,  recruiter_email,
				 tech_interview_1_date,  tech_interviewer_1_name,  tech_interviewer_1_comment,
				 tech_interview_2_date,  tech_interviewer_2_name,  tech_interviewer_2_comment,
				 manager_interview_1_date,  manager_interviewer_1_name,  manager_interviewer_1_comment,
				 hr_interview_1_date,  hr_interviewer_1_name,  hr_interviewer_1_comment,
				 client_interview_1_date,  client_interviewer_1_name,  client_interviewer_1_comment,
				 offer_letter_id,  offer_amt,  onbloading_dt,  joining_amt,  recuiter_stage,
				 recuiter_status);
	}

	@DeleteMapping("/delete/workflow")
	@ApiOperation(value = "delete workflow ", notes = "this Rest Api to delete the workflow by workflow_id ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> deleteById(
			@ApiParam(value = "Enter the workflow_id number", required = true) @RequestParam long workflow_id) {
		return work_service.eraseByWorkflowId(workflow_id);
	}
	
	@GetMapping("/getBy/id")
	@ApiOperation(value = " get by id", notes = "this Rest Api fetch the workflow detail by workflow id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getById(
			@ApiParam(value = "To access the workflow service record by workflow_id number", required = true) @RequestParam long workflow_id) {
		return work_service.fetchById(workflow_id);
	}
	
//	@PutMapping("/updateBy/id")
//	@ApiOperation(value = "update", notes = "this Rest Api update workflow by workflow_id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
////	@RequestParam long job_id,  @RequestParam long application_id, @RequestParam String recruiter_email,
////	@RequestParam String tech_interview_1_date, @RequestParam String tech_interviewer_1_name,@RequestParam String tech_interviewer_1_comment,
////	@RequestParam String tech_interview_2_date, @RequestParam String tech_interviewer_2_name,@RequestParam String tech_interviewer_2_comment,
////	@RequestParam String manager_interview_1_date, @RequestParam String manager_interviewer_1_name,@RequestParam String manager_interviewer_1_comment,
////	@RequestParam String hr_interview_1_date, @RequestParam String hr_interviewer_1_name,@RequestParam String hr_interviewer_1_comment,
////	@RequestParam String client_interview_1_date, @RequestParam String client_interviewer_1_name,@RequestParam String client_interviewer_1_comment,
////	@RequestParam String offer_letter_id, @RequestParam double offer_amt, @RequestParam String onbloading_dt, @RequestParam double joining_amt,
////	@RequestParam String recuiter_stage,
////	@RequestParam String recuiter_status
//	public ResponseEntity<ResponseDto> updateById(
//			@ApiParam(value = "To update the workflow by workflow_id ", required = true) @RequestParam(required = true, defaultValue = "") long workflow_id,
//			@ApiParam(value = "update contact ", required = false) @RequestParam(required = false, defaultValue = "") long job_id,
//			@ApiParam(value = "update name ", required = false) @RequestParam(required = false, defaultValue = "") long application_id,
//			@ApiParam(value = "update email ", required = false) @RequestParam(required = false, defaultValue = "") String recruiter_email,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String tech_interviewer_1_name,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String tech_interviewer_1_comment,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String tech_interviewer_2_name,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String tech_interviewer_2_comment,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String manager_interviewer_1_name,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String manager_interviewer_1_comment,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String hr_interviewer_1_name,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String hr_interviewer_1_comment,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String client_interviewer_1_name,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String client_interviewer_1_comment,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String offer_letter_id,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") double offer_amt,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String onbloading_dt,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") double joining_amt,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String recuiter_stage,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String recuiter_status,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") int status
//			) {
//		return work_service.edit(workflow_id, job_id,application_id,  recruiter_email,
//				   tech_interviewer_1_name,  tech_interviewer_1_comment,
//				   tech_interviewer_2_name,  tech_interviewer_2_comment,
//				   manager_interviewer_1_name,  manager_interviewer_1_comment,
//				   hr_interviewer_1_name,  hr_interviewer_1_comment,
//				   client_interviewer_1_name,  client_interviewer_1_comment,
//				 offer_letter_id,  offer_amt,  onbloading_dt,  joining_amt,  recuiter_stage,
//				 recuiter_status,status);
//	}
}
