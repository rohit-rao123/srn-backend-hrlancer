package org.srn.web.Recruiter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Followups_Service_Impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = "/Recruit/Follow")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )


@Api(value = "Follow_ups_microservice", description = "Followups Management Operation", tags = "Followup Controller")
public class Followups_Controller {


	@Autowired
	private Followups_Service_Impl followup_service;
	


	@GetMapping("/search")
	@ApiOperation(value = "Search followups by contact and email", notes = "This API fetches followup details based on contact number and email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchFollowups(HttpSession session,@RequestHeader("Authorization") String token,
			@ApiParam(value = "contact number", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "email", required = false) @RequestParam(required = false, defaultValue = "") String email) {

		return followup_service.fetchByMobileEmail(session,token, contact, email);
	}


	@PostMapping("/add/followup")
	@ApiOperation(value = "add new record", notes = "This Rest Api  add new  Followups records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> save_followup(HttpSession session,@RequestHeader("Authorization") String token, @RequestParam String comment,
			@ApiParam(value = "Enter the contact number", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "Enter the email", required = false) @RequestParam(required = false, defaultValue = "") String email) {
		return followup_service.add_followup(session,token, comment, contact, email);
	}
	
	
//
//	@GetMapping("/search/followups")
//	@ApiOperation(value = " search by ", notes = "This Rest Api fetch the  detail by various options")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> searchFollowups(HttpServletRequest request,
//			@ApiParam(value = "search by job_id", required = false) @RequestParam(defaultValue = "0") long job_id,
//			@ApiParam(value = "search by job_name", required = false) @RequestParam(required = false, defaultValue = "") String job_name,
//			@ApiParam(value = "search by application_id", required = false) @RequestParam(defaultValue = "0") long application_id,
//			@ApiParam(value = "search by client_id", required = false) @RequestParam(defaultValue = "0") int client_id,
//			@ApiParam(value = "search by contact number", required = false) @RequestParam(required = false, defaultValue = "") String contact,
//			@ApiParam(value = "search by email ", required = false) @RequestParam(required = false, defaultValue = "") String email,
//			@ApiParam(value = "search by name", required = false) @RequestParam(required = false, defaultValue = "") String name,
//			@ApiParam(value = "search by profile_id", required = false) @RequestParam(defaultValue = "0") long profile_id,
//			@ApiParam(value = "search by comment", required = false) @RequestParam(required = false, defaultValue = "") String comment,
//			@ApiParam(value = "search by application status", required = false) @RequestParam(required = false, defaultValue = "") String followup_status,
//			@ApiParam(value = "DateFormat ( yyyy-MM-dd )", required = false) @RequestParam(required = false, defaultValue = "") String followup_creation_date) {
//		String dt = FileHelper.dateConvertor(followup_creation_date);
//		return followup_service.fetchByMultipleOption(request, job_id, job_name, application_id, client_id, contact,
//				email, name, profile_id, comment, followup_status, dt);
//
//	}
	

//@GetMapping("/search/followups")
//	@ApiOperation(value = " search by ", notes = "This Rest Api fetch the  detail by various options")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> searchFollowups(HttpServletRequest request,
//			@ApiParam(value = "search by job_id", required = false) @RequestParam(defaultValue = "0") long job_id,
//			@ApiParam(value = "search by job_name", required = false) @RequestParam(required = false, defaultValue = "") String job_name,
//			@ApiParam(value = "search by application_id", required = false) @RequestParam(defaultValue = "0") long application_id,
//			@ApiParam(value = "search by client_id", required = false) @RequestParam(defaultValue = "0") int client_id,
//			@ApiParam(value = "search by contact number", required = false) @RequestParam(required = false, defaultValue = "") String contact,
//			@ApiParam(value = "search by email ", required = false) @RequestParam(required = false, defaultValue = "") String email,
//			@ApiParam(value = "search by name", required = false) @RequestParam(required = false, defaultValue = "") String name,
//			@ApiParam(value = "search by profile_id", required = false) @RequestParam(defaultValue = "0") long profile_id,
//			@ApiParam(value = "search by comment", required = false) @RequestParam(required = false, defaultValue = "") String comment,
//			@ApiParam(value = "search by application status", required = false) @RequestParam(required = false, defaultValue = "") String followup_status,
//			@ApiParam(value = "DateFormat ( yyyy-MM-dd )", required = false) @RequestParam(required = false, defaultValue = "") String followup_creation_date) {
//		String dt = FileHelper.dateConvertor(followup_creation_date);
//		return followup_service.fetchByMultipleOption(request, job_id, job_name, application_id, client_id, contact,
//				email, name, profile_id, comment, followup_status, dt);
//
//	}

	// http://localhost:8085/swagger-ui.html <==use this to access Swagger

	
//	@GetMapping("/getBy/id")
//	@ApiOperation(value = " get by id", notes = "This Rest Api fetch the Followups table by followup_id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getById(HttpServletRequest request,
//			@ApiParam(value = "To access the followup service record by followup_id number", required = true) @RequestParam int followup_id) {
//
//		return followup_service.fetchById(request, followup_id);
//	}

	@GetMapping("/getAll/followup")
	@ApiOperation(value = "get all data", notes = "This Rest Api fetch all the followup records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session,@RequestHeader("Authorization") String token) {
		return followup_service.fetchAllRecords(session, token);
	}

//	@DeleteMapping("/delete/followup")
//	@ApiOperation(value = "delete record by id", notes = "This Rest Api delete the followup records by followup_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> deleteById(HttpServletRequest request,
//			@ApiParam(value = "Enter the followup_id number", required = true) @RequestParam long followup_id) {
//		return followup_service.eraseByFollowupId(request, followup_id);
//	}

//	@PostMapping("/backup")
//	@ApiOperation(value = "backup", notes = "this Rest Api  add new  Followups records ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> saveRecord(@RequestBody Followups follow) {
//		return followup_service.addNewrecord(follow);
//	}



//	@PostMapping("/downloadBy/comment")
//	@ApiOperation(value = "search and download Followup data by comment", notes = "This Rest Api  search  followup records by comment and download file  ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response,
//			@ApiParam(value = "To access the followup service record by comment", required = true) @RequestParam String comment) {
//
//		return followup_service.downloadService(request, comment);
//	}

//	@PostMapping("/downloadAll/followup")
//	@ApiOperation(value = "download Followup data", notes = "This Rest Api search all followup records and download file  ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response) {
//
//		return followup_service.downloadAllServices(request);
//	}

//	@PutMapping("/update")
//	@ApiOperation(value = "update", notes = "This Rest Api update followup records")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> updateById(HttpServletRequest request,
//			@ApiParam(value = "Must enter either  profile_id or contact", required = false) @RequestParam(required = false, defaultValue = "0") long profile_id,
//			@ApiParam(value = "Must enter either  profile_id or contact", required = false) @RequestParam(required = false, defaultValue = "") String contact,
//			@ApiParam(value = "update followup_status of followup ", required = false) @RequestParam(required = false, defaultValue = "") String followup_status,
//			@ApiParam(value = "update comment of followup  ", required = false) @RequestParam(required = false, defaultValue = "") String comment,
//			@ApiParam(value = "update status of followup  ", required = false) @RequestParam(required = false, defaultValue = "") String status) {
//		return followup_service.updateFollowupsById(request, profile_id, contact, followup_status, comment, status);
//	}
	/*
	 * @PutMapping("/updateByFId")
	 * 
	 * @ApiOperation(value = "update the existingrecord", notes =
	 * "this Rest Api update  the Followup records by given followup_id ")
	 * 
	 * @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
	 * 
	 * @ApiResponse(code = 400, message = "Bad request error"),
	 * 
	 * @ApiResponse(code = 401, message = "Unauthorized client error"),
	 * 
	 * @ApiResponse(code = 403, message = "Forbidden client error"),
	 * 
	 * @ApiResponse(code = 404, message = "Invalid data"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal server error") }) public
	 * ResponseEntity<ResponseDto> updateById(@RequestParam int
	 * followup_id,@RequestParam int active,@RequestParam String
	 * applied_for,@RequestParam String comment,
	 * 
	 * @RequestParam String status) { return
	 * followup_service.editFollowupsRecordById(followup_id, active, applied_for,
	 * comment, status); }
	 */
}

//@ApiParam(value = "To access the followup by optional parameter", required = true, defaultValue = "default")