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
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Profile_Service_Impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/Prof")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )

@Api(value = "Recruiter_microservice", description = "Profile Management Operation", tags = "Profile Controller")
public class Profile_Controller {

	@Autowired
	private Profile_Service_Impl profile_service;
	
	


	@PostMapping("/add/profile")
	@ApiOperation(value = "add profile", notes = "This Rest Api add the profile records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> saveRecord(HttpSession session,@RequestHeader("Authorization") String token,

			@ApiParam(value = "name", required = false) @RequestParam(required = false, defaultValue = "") String name,
			@ApiParam(value = "contact", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "email", required = false) @RequestParam(required = false, defaultValue = "") String email,
			@ApiParam(value = "alternate contact", required = false) @RequestParam(required = false, defaultValue = "") String alternate_contact,
			@ApiParam(value = "alternate email", required = false) @RequestParam(required = false, defaultValue = "") String alternate_email,
			@ApiParam(value = "experience", required = false) @RequestParam(required = false, defaultValue = "0") double exp,
			@ApiParam(value = "cost to company", required = false) @RequestParam(required = false, defaultValue = "0") double ctc,
			@ApiParam(value = "expected cost to company ", required = false) @RequestParam(required = false, defaultValue = "0") double ectc,
			@ApiParam(value = "notice period", required = false) @RequestParam(required = false, defaultValue = "0") int notice_period,
			@ApiParam(value = "company", required = false) @RequestParam(required = false, defaultValue = "") String company,
			@ApiParam(value = "current role", required = false) @RequestParam(required = false, defaultValue = "") String current_role,
			@ApiParam(value = "qualification", required = false) @RequestParam(required = false, defaultValue = "") String qualification,
			@ApiParam(value = "year of passing", required = false) @RequestParam(required = false, defaultValue = "0") int year_of_passing,
			@ApiParam(value = "domain", required = false) @RequestParam(required = false, defaultValue = "") String domain,
			@ApiParam(value = "primary skill", required = false) @RequestParam(required = false, defaultValue = "") String primary_skill,
			@ApiParam(value = "secondary skill", required = false) @RequestParam(required = false, defaultValue = "") String secondary_skill,
			@ApiParam(value = "location", required = false) @RequestParam(required = false, defaultValue = "") String location,
			@ApiParam(value = "Browse resume", required = false) @RequestParam(required = false,value ="file") MultipartFile resume) {
		return profile_service.saveProfile(session,token, name, contact, email, ctc, ectc, current_role, company,
				location, domain, primary_skill, secondary_skill, exp, alternate_contact, alternate_email,
				qualification, year_of_passing, notice_period, resume);
	}
	


	@GetMapping("/search/profile")
	@ApiOperation(value = "search profile", notes = "This Rest Api fetch the profile detail by applicant mobile number or email or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getById(HttpSession session,@RequestHeader("Authorization") String token,
			@ApiParam(name = "mobile_number") @RequestParam(defaultValue = "", required = false) String mobile_number,
			@ApiParam(name = "email") @RequestParam(defaultValue = "", required = false) String email,
			@ApiParam(name = "name") @RequestParam(defaultValue = "", required = false) String name) {

		try {
			return profile_service.fetchProfile(session,token, mobile_number, email, name);
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.Not_Found);
			response.setBody("Invalid input");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}
	
	@GetMapping("/getAll/profile")
	@ApiOperation(value = "get all Profile", notes = "This Rest Api fetch all the profile records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session ,@RequestHeader("Authorization") String token) {
		return profile_service.fetchAllRecords(session,token);
	}
	
	@PostMapping("/addAppByCreatingProfile")
	@ApiOperation(value = "add profile", notes = "This Rest Api add the profile records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addAppByCreatingProfile(HttpSession session,@RequestHeader("Authorization") String token,			
			@ApiParam(value = "name", required = false) @RequestParam(required = false, defaultValue = "") String name,
			@ApiParam(value = "contact", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "email", required = false) @RequestParam(required = false, defaultValue = "") String email,
			@ApiParam(value = "alternate contact", required = false) @RequestParam(required = false, defaultValue = "") String alternate_contact,
			@ApiParam(value = "alternate email", required = false) @RequestParam(required = false, defaultValue = "") String alternate_email,
			@ApiParam(value = "experience", required = false) @RequestParam(required = false, defaultValue = "0") double exp,
			@ApiParam(value = "cost to company", required = false) @RequestParam(required = false, defaultValue = "0") double ctc,
			@ApiParam(value = "expected cost to company ", required = false) @RequestParam(required = false, defaultValue = "0") double ectc,
			@ApiParam(value = "notice period", required = false) @RequestParam(required = false, defaultValue = "0") int notice_period,
			@ApiParam(value = "company", required = false) @RequestParam(required = false, defaultValue = "") String company,
			@ApiParam(value = "current role", required = false) @RequestParam(required = false, defaultValue = "") String current_role,
			@ApiParam(value = "qualification", required = false) @RequestParam(required = false, defaultValue = "") String qualification,
			@ApiParam(value = "year of passing", required = false) @RequestParam(required = false, defaultValue = "0") int year_of_passing,
			@ApiParam(value = "domain", required = false) @RequestParam(required = false, defaultValue = "") String domain,
			@ApiParam(value = "primary skill", required = false) @RequestParam(required = false, defaultValue = "") String primary_skill,
			@ApiParam(value = "secondary skill", required = false) @RequestParam(required = false, defaultValue = "") String secondary_skill,
			@ApiParam(value = "location", required = false) @RequestParam(required = false, defaultValue = "") String location,
			@ApiParam(value = "job_id", required = false)@RequestParam(defaultValue = "0", required = false) long job_id,
			@ApiParam(value = "is_ectc_nego", required = false)@RequestParam(defaultValue = "-1",required = false) int is_ectc_nego,
			@ApiParam(value = "rel_exp", required = false)@RequestParam(defaultValue = "0", required = false) double rel_exp,
			@ApiParam(value = "comment", required = false)@RequestParam(defaultValue = "", required = false) String comment,
			@ApiParam(value = "Browse resume", required = false) @RequestParam(required = false,value ="file") MultipartFile resume) {
		return profile_service.addAppWithProfileCreating(session,token, name, contact, email, ctc, ectc, current_role, company,
				location, domain, primary_skill, secondary_skill, exp, alternate_contact, alternate_email,
				qualification, year_of_passing, notice_period,job_id,is_ectc_nego,rel_exp,comment, resume);
	}

//	@PostMapping("/create/bulk/profile")
//	@ApiOperation(value = " add multiple profile", notes = "This Rest Api add the profile in bulk")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> createBulkProfile(HttpServletRequest request,
//			@ApiParam(value = "To add the profile in bulk", required = true) @RequestParam MultipartFile file) {
//		return profile_service.addlist(request, file);
//	}
	
//
//	@GetMapping("/getBy/id")
//	@ApiOperation(value = " get by id", notes = "This Rest Api fetch the profile detail by profile id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getById(HttpServletRequest request,
//			@ApiParam(value = "To access the profile service record by profile_id number", required = true) @RequestParam long Profile_id) {
//
//		return profile_service.fetchById(request, Profile_id);
//	}

//	@PostMapping("/upload/resume")
//	@ApiOperation(value = " upload resume", notes = "Rest Api to upload resume file in docx and pdf format")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> upload_File(HttpServletRequest request,
//			@ApiParam(value = "To upload the resume", required = true) @RequestParam("file") MultipartFile file) {
//		System.out.println(file.getOriginalFilename());
//		System.out.println(file.getSize());
//		System.out.println(file.getContentType());
////		try {
////			if (request.getSession().getAttribute("userName") == null) {
////				ResponseDto response = new ResponseDto();
////
////				response.setMessage(VMessageConstant.Not_Found);
////				response.setBody("Session expired !, Please login");
////				response.setStatus(true);
////				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////			} 
////			}catch(Exception e) {
////				ResponseDto response = new ResponseDto();
////
////				response.setMessage(VMessageConstant.DEFAULT);
////				response.setBody(null);
////				response.setStatus(false);
////				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////			}
//		try {
//			if (file.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("file is empty, please try again");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (profile_service.uploadFile(file) == true) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("file is succesfully uploaded to server");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("file cannot be uploaded");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

	
//	@DeleteMapping("/delete/profile")
//	@ApiOperation(value = "delete profile ", notes = "This Rest Api to delete the profile by profile_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> deleteById(HttpServletRequest request,
//			@ApiParam(value = "Enter the profile_id number", required = true) @RequestParam long profile_id) {
//		return profile_service.eraseByProfileId(request, profile_id);
//	}

//	@CrossOrigin(origins="http://35.173.186.169:8000")
	

//	@PutMapping("/updateBy/id")
//	@ApiOperation(value = "update", notes = "This Rest Api update profile by profile_id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> updateById(HttpServletRequest request,
//			@ApiParam(value = "To update the profile by profile_id ", required = true) @RequestParam(required = false, defaultValue = "") long profile_id,
//			@ApiParam(value = "update contact ", required = false) @RequestParam(required = false, defaultValue = "") String contact,
//			@ApiParam(value = "update name ", required = false) @RequestParam(required = false, defaultValue = "") String name,
//			@ApiParam(value = "update email ", required = false) @RequestParam(required = false, defaultValue = "") String email,
//			@ApiParam(value = "update location ", required = false) @RequestParam(required = false, defaultValue = "") String location) {
//		return profile_service.edit(request, profile_id, email, contact, name, location);
//	}

//	@GetMapping("/downloadAll/profile")
//	@ApiOperation(value = "download all profiles ", notes = "This Rest Api download all profiles ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		return profile_service.downloadService(request);
//	}

//	@GetMapping("/downloadBy/domain")
//	@ApiOperation(value = " search and download profile data", notes = "This Rest Api search and download the profile  details by domain")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response,
//			@ApiParam(value = "To access the profile service record by domain", required = true) @RequestParam String domain)
//			throws IOException {
//		return profile_service.downloadService(request, domain);
//	}

//	@PostMapping("/delete/resume")
//	@ApiOperation(value = " delete resume", notes = "Rest Api to delte resume file in docx and pdf format")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> delete_File(HttpServletRequest request,
//			@ApiParam(value = "To delete the resume", required = true) @RequestParam String fileName) {
//		System.out.println(fileName);
//		return profile_service.fileDeleter(request, fileName);
//	}

//	@GetMapping(value = "/download/resume", produces = { "application/octet-stream" })
//	@ApiOperation(value = " download resume", notes = "this Rest Api to download the resumes by its name")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<byte[]> download(HttpServletRequest request, String filename) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
////		String filePath = "C:\\Users\\Hp\\Desktop\\Recruiter\\src\\main\\resources\\Upload_Files\\" + filename;
////		String filePath = "/home/ubuntu/app/internal/recruiter/disk/upload_files/" + filename;
//		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/" + filename;
//		File file = new File(filePath);
//		if (file.exists()) {
//			try {
//				byte[] contents = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//
//				String Filename = file.getName();
//				System.out.println(file.getName());
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//				headers.setContentDisposition(ContentDisposition.attachment().filename(Filename).build());
//
//				return new ResponseEntity<>(contents, headers, HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} else {
//
//			try {
//
////				String path = "C:\\Users\\Hp\\Desktop\\Recruiter\\src\\main\\resources\\Upload_Files\\MESSAGE.docx";
//
////				String path = "/home/ubuntu/app/internal/recruiter/disk/upload_files/MESSAGE.docx";
//
//				String path = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/MESSAGE.docx";
//				File fileMessage = new File(path);
//
//				byte[] contents = Files.readAllBytes(Paths.get(fileMessage.getAbsolutePath()));
//
//				String Filename = fileMessage.getName();
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//				headers.setContentDisposition(ContentDisposition.attachment().filename(Filename).build());
//
//				return new ResponseEntity<>(contents, headers, HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
//
//	}
}
