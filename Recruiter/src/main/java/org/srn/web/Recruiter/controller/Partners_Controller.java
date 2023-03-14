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
import org.srn.web.Recruiter.service_impl.Partner_service_impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/Partner")
////@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@Api(value = "Recruiter_microservice", description = "Partner Management Operations", tags = "Partner Controller")
public class Partners_Controller {

	@Autowired
	private Partner_service_impl partner_service_impl;

	@PostMapping("/add/Partner")
	@ApiOperation(value = "add new partner record", notes = "This Rest API adds a new partner record.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> saveClient(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter the partner name", required = false) @RequestParam(required = false, defaultValue = "") String name,
			@ApiParam(value = "Enter the partner contact information", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "Enter the partner email", required = false) @RequestParam(required = false, defaultValue = "") String email,
			@ApiParam(value = "Enter the partner website", required = false) @RequestParam(required = false, defaultValue = "") String website,
			@ApiParam(value = "Enter the partner type", required = false) @RequestParam(required = false, defaultValue = "") String partner_type,
			@ApiParam(value = "Enter the partner_category", required = false)@RequestParam(required = false, defaultValue = "") String  partner_category,
			@ApiParam(value = "Enter the sharing percentage", required = false) @RequestParam(required = false, defaultValue = "") String sharing_percentage,
			@ApiParam(value = "Enter the start of partnership", required = false) @RequestParam(required = false, defaultValue = "") String partnership_start,
			@ApiParam(value = "Enter the end of partnership", required = false) @RequestParam(required = false, defaultValue = "") String partnership_end) {
		

		return partner_service_impl.addNewPartner(session, token, name, contact, email, website, partner_type,partner_category,
				sharing_percentage, partnership_start, partnership_end);
	}

	@GetMapping("/search/Partner")
	@ApiOperation(value = "search Partners", notes = "This API fetches Partners based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchClients(HttpSession session, @RequestHeader("Authorization") String token,
			@RequestParam(value = "partner_id", required = false, defaultValue = "0") int partner_id,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "contact", required = false, defaultValue = "") String contact,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "website", required = false, defaultValue = "") String website,
			@RequestParam(value = "partner_type", required = false, defaultValue = "") String partner_type,
			@RequestParam(value="partner_category", required=false) String partner_category,
			@RequestParam(value = "sharing_percentage", required = false, defaultValue = "-1") double sharing_percentage,
			@RequestParam(value = "partnership_start", required = false, defaultValue = "") String partnership_start,
			@RequestParam(value = "partnership_end", required = false, defaultValue = "") String partnership_end,
			@RequestParam(value = "creator", required = false, defaultValue = "") String creator,
			@RequestParam(value = "dt", required = false, defaultValue = "") String dt,
			@RequestParam(value = "status", required = false, defaultValue = "-1") int status) {
		return partner_service_impl.fetchByPartner(session, token, partner_id, name, contact, email, website,
				partner_type,partner_category, sharing_percentage, partnership_start, partnership_end, creator, dt, status);
	}
	
	
	@GetMapping("/getAll")
	@ApiOperation(value = "search Partners", notes = "This API fetches all Partners ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session, @RequestHeader("Authorization") String token){
		return partner_service_impl.fetchAllPartners(session,token);
	}

}
