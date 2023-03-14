package org.srn.web.Recruiter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Application_Service_Impl;
import org.srn.web.Recruiter.service_impl.Profile_Service_Impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/App")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@Api(value = "Application_service", description = " Application Management Operation", tags = "Application Controller")

public class Application_Controller {

	@Autowired
	private Application_Service_Impl appService;

	@Autowired
	private Profile_Service_Impl profile_service;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@GetMapping("/search/application")
	@ApiOperation(value = " search by application_id", notes = "This Rest Api fetch the Application detail by profile id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchApplication(HttpSession session,
			@RequestHeader("Authorization") String token,
			@ApiParam(value = "To access the Application service record by contact", required = false) @RequestParam(defaultValue = "", required = false) String contact,
			@ApiParam(value = "To access the Application service record by email", required = false) @RequestParam(defaultValue = "", required = false) String email,
			@ApiParam(value = "To access the Application service record by application_status", required = false) @RequestParam(defaultValue = "", required = false) String progress,
			@ApiParam(value = "DateFormat ( yyyy-MM-dd )", required = false) @RequestParam(required = false, defaultValue = "") String app_creation_date) {

//		 System.out.println(app_creation_date);
//		 
//		 String date = FileHelper.dateConvertor(app_creation_date);
		return appService.fetchByApp(session, token, contact, email, progress, app_creation_date);
	}

	@GetMapping("/getAll")
	@ApiOperation(value = "get all application", notes = "This Rest Api fetch all the application records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session, @RequestHeader("Authorization") String token) {
		return appService.fetchAllRecords(session, token);
	}

	@PostMapping(value="/add/application")
	@ApiOperation(value = "add application", notes = "This Rest Api fetch add new  application records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> apply(HttpSession session, @RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0", required = false) String profile_id,
			@RequestParam(defaultValue = "0", required = false) String job_id,
			@RequestParam(defaultValue = "-1",required = false) String is_ectc_nego,
			@RequestParam(defaultValue = "0.0", required = false) String rel_exp,
			@RequestParam(defaultValue = "", required = false) String comment,			
			@RequestParam(defaultValue = "0", required = false) int notice_period
//			@ApiParam(value = "Browse resume", required = false) @RequestParam(required = false,value ="file") MultipartFile file
			) {
		long profileId = 0;
		long jobId = 0;
		double relExp = 0;
		double isEctcNego=0;
		try {
			profileId = Long.parseLong(profile_id);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Enter valid profile_id number !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
		try {
			isEctcNego = Double.parseDouble(is_ectc_nego);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Enter valid  is_ectc_nego  number !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
		try {
			jobId = Long.parseLong(job_id);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Enter valid job_id number !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
		
		try {
			relExp = Double.parseDouble(rel_exp);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Enter valid rel_exp number !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		
		
		System.out.println("controller");

		return appService.jobApplications(session, token, profileId, jobId, isEctcNego, relExp, comment,
				 notice_period);
	}
	
	

	@PutMapping("/update")
	@ApiOperation(value = "update  Application", notes = "This Rest Api update  the application records by given application_id ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> updateById(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "To update the application by application_id ", required = true) @RequestParam(required = true, defaultValue = "0") long application_id,
			@ApiParam(value = "To update the ectc ", required = false) @RequestParam(required = false, defaultValue = "0.0") double ectc,
			@ApiParam(value = "To update the notice", required = false) @RequestParam(required = false, defaultValue = "0") int notice,
			@ApiParam(value = "To update the progress", required = false) @RequestParam(required = false, defaultValue = "") String progress) {
		return appService.updateApplicationRecordById(session, token, application_id, ectc, notice, progress);
	}

	@PostMapping("/addApplication")
	@ApiOperation(value = "add application", notes = "This Rest Api fetch add new  application records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> saveRecord(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "enter profile_id ", required = false) @RequestParam(defaultValue = "0", required = false) long profile_id,
			@ApiParam(value = "enter job_id", required = false) @RequestParam(defaultValue = "0", required = false) long job_id,
			@ApiParam(value = "enter is_ectc_nego", required = false) @RequestParam(defaultValue = "0", required = false) double is_ectc_nego,
			@ApiParam(value = "enter rel_exp", required = false) @RequestParam(defaultValue = "0", required = false) double rel_exp,
			@ApiParam(value = "enter comment", required = false) @RequestParam(defaultValue = "", required = false) String comment,
			@ApiParam(value = "To upload the resume", required = false) @RequestParam( required = false,value="file") MultipartFile file) {
		return appService.addNewrecord(session, token, profile_id, job_id, is_ectc_nego, rel_exp, comment, file);
	}
	
	
	

	@PostMapping("/upload/resume")
	@ApiOperation(value = " upload resume", notes = "Rest Api to upload resume file in docx and pdf format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> upload_File(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "To upload the resume", required = true) @RequestParam("file") MultipartFile file) {
		msRequestFilter.validateToken(session, token);
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		System.out.println(file.getContentType());
		if(file!=null) {
			
		}
		try {
			if (session.getAttribute("isSession") != "true") {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		try {
			if (file==null || file.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage("file is empty, please try again");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (profile_service.uploadFile(file) == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage("file is succesfully uploaded to server");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage("file cannot be uploaded !");
				response.setBody("Invalid file format !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

//	@GetMapping("/getBy/id")
//	@ApiOperation(value = " get by application_id", notes = "this Rest Api fetch the Application detail by application id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getById(HttpServletRequest request,
//			@ApiParam(value = "Enter the number of application_id", required = true) @RequestParam long application_id) {
//
//		return appService.fetchById(request, application_id);
//	}

//	@DeleteMapping("/delete/app")
//	@ApiOperation(value = "delete Application ", notes = "This Rest Api delete the application records by application_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> deleteById(HttpServletRequest request, 
//			@ApiParam(value = "Enter the application_id number", required = true)  @RequestParam long application_id) {
//		return appService.eraseByApplicationId(request, application_id);
//	} 

//	@GetMapping("/downloadAll/app")
//	@ApiOperation(value = " download application data", notes = "This Rest Api fetch the Application  details in csv file")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		return appService.downloadServices(request);
//	}

	/*
	 * @GetMapping("/downloadBy/domain")
	 * 
	 * @ApiOperation(value = " search and download application data", notes =
	 * "this Rest Api fetch the Application  details by domain")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
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
	 * ResponseEntity<Resource> downloadCsv(HttpServletResponse response,
	 * 
	 * @ApiParam(value = "To access the Application service record by domain",
	 * required = true) @RequestParam String domain) throws IOException { return
	 * appService.downloadService(domain); }
	 * 
	 * // @PostMapping("/applyFor/job") // @ApiOperation(value =
	 * "adding applications for given job_id", notes =
	 * "this Rest Api add the application in bulk ") // @ApiResponses(value =
	 * { @ApiResponse(code = 200, message = "Success"), // @ApiResponse(code = 400,
	 * message = "Bad request error"), // @ApiResponse(code = 401, message =
	 * "Unauthorized client error"), // @ApiResponse(code = 403, message =
	 * "Forbidden client error"), // @ApiResponse(code = 404, message =
	 * "Invalid data"), // @ApiResponse(code = 500, message =
	 * "Internal server error") }) // public ResponseEntity<ResponseDto>
	 * createApplicationByDomain( // @ApiParam(value = "Enter job_id number",
	 * required = true) @RequestParam long job_id) { // return
	 * appService.addAppByDomain(job_id); // }
	 * 
	 * @PutMapping("/update") // @ApiOperation(value = "update  Application", notes
	 * = "this Rest Api update  the application records by given application_id ")
	 * // @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
	 * // @ApiResponse(code = 400, message = "Bad request error"),
	 * // @ApiResponse(code = 401, message = "Unauthorized client error"),
	 * // @ApiResponse(code = 403, message = "Forbidden client error"),
	 * // @ApiResponse(code = 404, message = "Invalid data"), // @ApiResponse(code =
	 * 500, message = "Internal server error") }) // public
	 * ResponseEntity<ResponseDto> updateById(@RequestParam long
	 * application_id, @RequestParam String applied_for, // @RequestParam int
	 * client_id, @RequestParam String name, @RequestParam String contact,
	 * // @RequestParam String email, @RequestParam String exp, @RequestParam int
	 * ctc, @RequestParam int ectc, // @RequestParam String
	 * notice_period, @RequestParam String current_role, @RequestParam String
	 * company, // @RequestParam String location, @RequestParam String
	 * domain, @RequestParam int status, @RequestParam String
	 * alternate_contact, @RequestParam String alternate_email, // @RequestParam
	 * String qualification, @RequestParam String year_of_passing, @RequestParam
	 * long profile_id) { // return
	 * appService.editApplicationRecordById(application_id, applied_for, name,
	 * alternate_contact, //alternate_email, company, current_role, qualification,
	 * domain, location, status, ctc); // }
	 * 
	 * / double rel_exp, String comment,int is_ectc_nego
	 * 
	 * // @PostMapping("/add_application") // @ApiOperation(value =
	 * "add application", notes =
	 * "this Rest Api fetch add new  application records ") // @ApiResponses(value =
	 * { @ApiResponse(code = 200, message = "Success"), // @ApiResponse(code = 400,
	 * message = "Bad request error"), // @ApiResponse(code = 401, message =
	 * "Unauthorized client error"), // @ApiResponse(code = 403, message =
	 * "Forbidden client error"), // @ApiResponse(code = 404, message =
	 * "Invalid data"), // @ApiResponse(code = 500, message =
	 * "Internal server error") }) // public ResponseEntity<ResponseDto>
	 * saveRecord(@RequestBody Application app) { // return
	 * appService.addNewrecord(app); // }
	 */

}