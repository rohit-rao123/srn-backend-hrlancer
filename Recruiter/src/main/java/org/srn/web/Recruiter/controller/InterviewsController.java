package org.srn.web.Recruiter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.InterviewServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/Interviews")
@Api(value = "Recruiter_microservice", description = "Interview Management Operations", tags = "Interview Controller")
public class InterviewsController {

	@Autowired
	private InterviewServiceImpl interviewServiceImpl;

	@PostMapping("/add/interview")
	@ApiOperation(value = "add new interview record", notes = "This Rest API adds a new interview record.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> saveInterview(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter the application ID", required = false) @RequestParam(required = false, defaultValue = "") String application_id,
			@ApiParam(value = "Enter the interview type", required = false) @RequestParam(required = false, defaultValue = "") String type,
			@ApiParam(value = "Enter the number of rounds", required = false) @RequestParam(required = false, defaultValue = "0") int no_of_round,
			@ApiParam(value = "yyyy-MM-dd HH:mm:ss", required = false) @RequestParam(required = false, defaultValue = "") String slot_start_dt,
			@ApiParam(value = "yyyy-MM-dd HH:mm:ss", required = false) @RequestParam(required = false, defaultValue = "") String slot_end_dt,
			@ApiParam(value = "Enter the interview tool used", required = false) @RequestParam(required = false, defaultValue = "") String interview_tool,
			@ApiParam(value = "Enter the name of the interviewer", required = false) @RequestParam(required = false, defaultValue = "") String interviewer_name,
			@ApiParam(value = "Enter the email of the interviewer", required = false) @RequestParam(required = false, defaultValue = "0") int interviewer_mail)

	{
		ResponseDto response = new ResponseDto();
//		Date start_dt = null;
//		Date end_dt = null;
//		try {
//			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			
//			LocalDateTime localStartDateTime = LocalDateTime.parse(slot_start_dt, inputFormatter);
//			String startDt = localStartDateTime.format(outputFormatter);
//
//			start_dt = dateFormat.parse(startDt);
//			System.out.println(start_dt);
//
//			LocalDateTime localEndDateTime = LocalDateTime.parse(slot_end_dt, inputFormatter);
//			String endDt = localEndDateTime.format(outputFormatter);
//			end_dt = dateFormat.parse(endDt);
//			System.out.println(end_dt);
//
//		} catch (Exception e) {
////			e.printStackTrace();
//		}

		long app_id = 0;

		try {
			if(application_id.isBlank()) {
				response.setMessage("Application_id is mandatory !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		else if (application_id.matches("\\d+")) {
				app_id = Long.parseLong(application_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Enter valid application_id number !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}

		return interviewServiceImpl.bookSlot(session, token, app_id, type, no_of_round, slot_start_dt, slot_end_dt,
				interview_tool, interviewer_name, interviewer_mail);
	}

	@GetMapping("/search/allBookedSlot")
	@ApiOperation(value = "search Book Slot", notes = "This API fetches book slot based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchBookSlots(HttpSession session,
			@RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter the job_id", required = false) @RequestParam(value = "job_id", required = false, defaultValue = "0") long jobId,
			@ApiParam(value = "Enter the partner_id", required = false) @RequestParam(value = "partner_id", required = false, defaultValue = "0") long partnerId,
			@ApiParam(value = "yyyy-MM-dd", required = false) @RequestParam(value = "dt", required = false, defaultValue = "") String dt) {
		return interviewServiceImpl.getAllBookSlot(session, token, jobId, partnerId, dt);
	}

//	@GetMapping("/search/interview")
//	@ApiOperation(value = "search Interview", notes = "This API fetches data based on specified parameters")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> searchInterview(HttpSession session,
//			@RequestHeader("Authorization") String token,
//			@ApiParam(value = "Enter the client_id ", required = false) @RequestParam(value = "client_id", required = false, defaultValue = "") String client_id,
//			@ApiParam(value = "Enter the application_id ", required = false) @RequestParam(value = "application_id", required = false, defaultValue = "") String application_id,
//			@ApiParam(value = "Enter the interviewer_name ", required = false) @RequestParam(value = "interviewer_name", required = false, defaultValue = "") String interviewer_name) {
//		return interviewServiceImpl.search(session, token, interviewer_name, application_id, client_id);
//	}
	
	@GetMapping("/search/interview")
	@ApiOperation(value = "search Interview", notes = "This API fetches data based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchInterview(HttpSession session,
			@RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter the client_id ", required = false) @RequestParam(value = "client_id", required = false, defaultValue = "") String client_id,
			@ApiParam(value = "Enter the application_id ", required = false) @RequestParam(value = "application_id", required = false, defaultValue = "") String application_id,
			@ApiParam(value = "Enter the interviewer_name ", required = false) @RequestParam(value = "interviewer_name", required = false, defaultValue = "") String interviewer_name,
			@ApiParam(value = "Enter the candidate_email ", required = false) @RequestParam(value = "candidate_email", required = false, defaultValue = "") String candidate_email,
			@ApiParam(value = "Enter the candidate_contact ", required = false) @RequestParam(value = "candidate_contact", required = false, defaultValue = "") String candidate_contact) {
		return interviewServiceImpl.search(session, token, interviewer_name, application_id, client_id,candidate_email,candidate_contact);
	}

	@GetMapping("/search/availableSlots")
	@ApiOperation(value = "search Available Slot", notes = "This API fetches Available slot based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAvailableSlots(HttpSession session,
			@RequestHeader("Authorization") String token,
			@ApiParam(value = "yyyy-MM-dd", required = false) @RequestParam(required = false, defaultValue = "") String start_dt,
			@ApiParam(value = "HH:mm:ss", required = false) @RequestParam(required = false, defaultValue = "") String Start_Time,
			@ApiParam(value = "HH:mm:ss", required = false) @RequestParam(required = false, defaultValue = "") String End_Time) {
		return interviewServiceImpl.getAvailableSlots(session, token, start_dt, Start_Time, End_Time);
	}
	
	@GetMapping("/getAll")
	@ApiOperation(value = "search interview", notes = "This API fetches Interview")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session, @RequestHeader("Authorization") String token) {
		return interviewServiceImpl.fetchAllInterview(session, token);
	}	
}
