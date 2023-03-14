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
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service.BenchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@RestController
@RequestMapping(value = "/Recruit/Bench")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@Api(value = "Bench_service", description = " Bemch Management Operation", tags = "Bench Controller")
public class BenchController {

	@Autowired
	private BenchService benchService;
	
	@Autowired
	private MSRequestFilter msRequestFilter;

	@PostMapping("/addBench")
	@ApiOperation(value = "Add a new bench", notes = "This API adds a new bench.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addBench(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "Name", required = false) @RequestParam(required = false, defaultValue = "") String name,
			@ApiParam(value = "Contact", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "Alternate contact", required = false) @RequestParam(required = false, defaultValue = "") String alternate_contact,
			@ApiParam(value = "Email", required = false) @RequestParam(required = false, defaultValue = "") String email,
			@ApiParam(value = "Alternate email", required = false) @RequestParam(required = false, defaultValue = "") String alternate_email,
			@ApiParam(value = "Experience", required = false) @RequestParam(required = false, defaultValue = "") String exp,
			@ApiParam(value = "Domain", required = false) @RequestParam(required = false, defaultValue = "") String domain,
			@ApiParam(value = "Bench Type", required = false) @RequestParam(required = false, defaultValue = "") String bench_type,
			@ApiParam(value = "Primary Skill", required = false) @RequestParam(required = false, defaultValue = "") String primary_skill,
			@ApiParam(value = "Secondary Skill", required = false) @RequestParam(required = false, defaultValue = "") String secondary_skill,
			@ApiParam(value = "Budget", required = false) @RequestParam(required = false, defaultValue = "") String budget,
			@ApiParam(value = "Salary", required = false) @RequestParam(required = false, defaultValue = "") String salary,
			@ApiParam(value = "ORG_NAME", required = false) @RequestParam(required = false, defaultValue = "") String org_name,
			@ApiParam(value = "ORG_URL", required = false) @RequestParam(required = false, defaultValue = "") String org_url,
			@ApiParam(value = "Current Role", required = false) @RequestParam(required = false, defaultValue = "") String current_role,
			@ApiParam(value = "Qualification", required = false) @RequestParam(required = false, defaultValue = "") String qualification,
			@ApiParam(value = "Browse resume", required = false) @RequestParam(required = false,value ="file") MultipartFile resume) {

		// Call service method to add bench
		return benchService.addNewBench(session, token, name, contact, alternate_contact, email,
				alternate_email, exp, domain,bench_type, primary_skill, secondary_skill, budget,salary,org_name,org_url,current_role, qualification,resume);
	}

	@GetMapping("/search/Bench")
	@ApiOperation(value = "search Bench", notes = "This API fetches Bench resources based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchBench(HttpSession session, @RequestHeader("Authorization") String token,
			@RequestParam(required = false, defaultValue = "") String bench_id,
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String contact,
			//@RequestParam(required = false, defaultValue = "") String alternate_contact,
			@RequestParam(required = false, defaultValue = "") String email,
			//@RequestParam(required = false, defaultValue = "") String alternate_email,
			@RequestParam(required = false, defaultValue = "") String exp,
			@RequestParam(required = false, defaultValue = "") String domain,
			@RequestParam(required = false, defaultValue = "") String bench_type,
			@RequestParam(required = false, defaultValue = "") String primary_skill,
			@RequestParam(required = false, defaultValue = "") String secondary_skill,
			@RequestParam(required = false, defaultValue = "") String budget,
			@RequestParam(required = false, defaultValue = "") String salary,
			@RequestParam(required = false, defaultValue = "") String org_name,
			@RequestParam(required = false, defaultValue = "") String org_url,
			@RequestParam(required = false, defaultValue = "") String current_role,
			@RequestParam(required = false, defaultValue = "") String qualification) {

		// Call a service method to search for bench resources with the given parameters
		return benchService.searchBench(session, token, bench_id,name, contact,
				 email, exp, domain, bench_type, primary_skill, secondary_skill, budget,salary,org_name,org_url,
				current_role, qualification);
	}
	
	
	@GetMapping("/getAll")
	@ApiOperation(value = "search Benches", notes = "This API fetches Benches based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAllBenches(HttpSession session, @RequestHeader("Authorization") String token) {
		return benchService.fetchAllBenches(session, token);
	}
	
	
//	@GetMapping(value = "/download/resume", produces = { "application/octet-stream" })
//	@ApiOperation(value = " download resume", notes = "this Rest Api to download the resumes by its name")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<byte[]> download(HttpSession session, @RequestHeader("Authorization") String token, String filename) {
//		msRequestFilter.validateToken(session, token);
//		try {
//			if (session.getAttribute("isSession") != "true") {
//				System.out.println(session.getAttribute("isSession"));
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			}
//		} catch (Exception e) {
//
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	String filePath = "E:\\SRN-PROJECT-MAIN-NEW\\Latest-Recruiter-code\\bench\\" + filename;
////		String filePath = "/home/ubuntu/app/internal/recruiter/disk/upload_files/" + filename;
////		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/bench/" + filename;
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
//				String path = "E:\\SRN-PROJECT-MAIN-NEW\\Latest-Recruiter-code\\bench\\MESSAGE.docx";
//
////				String path = "/home/ubuntu/app/internal/recruiter/disk/upload_files/MESSAGE.docx";
//
//				//String path = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/bench/MESSAGE.docx";
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
