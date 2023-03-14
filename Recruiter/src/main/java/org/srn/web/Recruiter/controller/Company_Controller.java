package org.srn.web.Recruiter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Company_Service_Impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/Recruit/Company")
////@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
//
@Api(value = "Company_microservice", description = "Company Management Operation", tags = "Company Controller")
public class Company_Controller {

	@Autowired
	private Company_Service_Impl company_service;

	@PostMapping("/add/company")
	@ApiOperation(value = " add company data", notes = "This Rest Api  add new company records in the database ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addJobsParameter(HttpServletRequest request, @RequestParam String company_name,
			@RequestParam String website, @RequestParam long maximum_employees, @RequestParam long minimum_employees,
			@RequestParam String location,
			@RequestParam String industry, @RequestParam String ranking) {
		return company_service.addrecord(request, company_name, website, maximum_employees, minimum_employees,
				location, industry, ranking);
	}
	
	
	@PostMapping("/inject/bulkCompany")
	@ApiOperation(value = " add company data", notes = "This Rest Api  add new company records in the database ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addMultipleCompany(HttpServletRequest request, @RequestParam MultipartFile file) {
		return company_service.addCompanyFile(request, file);
	}

	@GetMapping("/getAll")
	@ApiOperation(value = " get all company", notes = "This Rest Api fetch all company ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session,@RequestHeader("Authorization") String token){
	
		return company_service.fetchAllCompany(session, token);

	}
	
	@GetMapping("/search/comapny")
	@ApiOperation(value = " get by company or website", notes = "This Rest Api fetch the company detail by name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getById(HttpServletRequest request,
			@ApiParam(value = "search by company name", required = false) @RequestParam(defaultValue = "") String company_name,
			@ApiParam(value = "search by website ", required = false) @RequestParam(required = false, defaultValue = "") String website) {
		try {
			return company_service.getCompany(request, company_name,website);
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.Not_Found);
			response.setBody("Invalid input");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}
	
	@DeleteMapping("/delete/company")
	@ApiOperation(value = "delete company ", notes = "This Rest Api delete the company records by company_id ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> deleteById(HttpServletRequest request,
			@ApiParam(value = "Enter the company_id number", required = true) @RequestParam long company_id) {
		return company_service.eraseByCompanyId(request, company_id);
	}

	@PutMapping("/updateBy/id")
	@ApiOperation(value = "update the company", notes = "This Rest Api update company records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> updateById(HttpServletRequest request, 
			@ApiParam(value = "Enter company_id ", required = true) @RequestParam(required = false, defaultValue = "") long company_id,
			@ApiParam(value = "update company name  ", required = false) @RequestParam(required = false, defaultValue = "") String company_name,
			@ApiParam(value = "update website of company", required = false) @RequestParam(required = false, defaultValue = "") String website,
			@ApiParam(value = "update minimum_employees of company ", required = false) @RequestParam(required = false, defaultValue = "0") long minimum_employees,
			@ApiParam(value = "update maximum_employees of company ", required = false) @RequestParam(required = false, defaultValue = "0") long maximum_employees,
			@ApiParam(value = "update location of company ", required = false) @RequestParam(required = false, defaultValue = "") String location,
			@ApiParam(value = "update industry of company", required = false) @RequestParam(required = false, defaultValue = "") String industry,
			@ApiParam(value = "update ranking of company ", required = false) @RequestParam(required = false, defaultValue = "") String ranking,
			@ApiParam(value = "update status of company ", required = false) @RequestParam(required = false, defaultValue = "-1") int status) {
		return company_service.edit(request, company_id, company_name, website, minimum_employees, maximum_employees, location, industry, ranking, status);
	}

}
