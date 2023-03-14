package org.srn.web.Recruiter.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Clients_Service_impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/Client")
@CrossOrigin(origins = { "*" }, maxAge = 4800, allowCredentials = "false")
@Api(value = "Recruiter_microservice", description = "Client Management Operations", tags = "Client Controller")

public class Clients_Controller {

	@Autowired
	private Clients_Service_impl Client_service;
	@PostMapping("/add")
	@ApiOperation(value = "Add a new client", notes = "This API adds a new client.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addClient(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "Client name", required = false) @RequestParam(required = false, defaultValue = "") String client_name,
			@ApiParam(value = "Client type ", required = false) @RequestParam(required = false, defaultValue = "") String client_type,
			@ApiParam(value = "Client website", required = false) @RequestParam(required = false, defaultValue = "") String website,
			@ApiParam(value = "Client email", required = false) @RequestParam(required = false, defaultValue = "") String email,
			@ApiParam(value = "Client contact", required = false) @RequestParam(required = false, defaultValue = "") String contact,
			@ApiParam(value = "Client address", required = false) @RequestParam(required = false, defaultValue = "") String address,
			@ApiParam(value = "Subscription type", required = false) @RequestParam(required = false, defaultValue = "") String subscription_type,
			@ApiParam(value = "Is subscribed", required = false) @RequestParam(required = false, defaultValue = "-1") int is_subscribed) {

		return Client_service.addNewClient(session, token, client_name, client_type, website, email, contact, address,
				subscription_type, is_subscribed);

	}

	@GetMapping("/search/Clients")
	@ApiOperation(value = "search Clients", notes = "This API fetches Clients based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchClients(HttpSession session, @RequestHeader("Authorization") String token,
			@RequestParam(required = false, defaultValue = "") String client_id,
			@RequestParam(required = false, defaultValue = "") String client_name,
			@RequestParam(required = false, defaultValue = "") String contact,
			@RequestParam(required = false, defaultValue = "") String email,
			@RequestParam(required = false, defaultValue = "") String website,
			@RequestParam(required = false, defaultValue = "") String client_type,
			@RequestParam(required = false, defaultValue = "") String address,
			@RequestParam(required = false, defaultValue = "") String subscription_type,
			@RequestParam(required = false, defaultValue = "") String is_subscribed,
			@RequestParam(required = false, defaultValue = "") String created_by,
			@RequestParam(required = false, defaultValue = "") String dt,
			@RequestParam(required = false, defaultValue = "") String status) {
		// Call a service method to search for clients with the given parameters
		return Client_service.searchClients(session, token, client_id, client_name, contact, email, website, client_type,
				address, subscription_type, is_subscribed, created_by, dt, status);
	}

	@GetMapping("/getAll")
	@ApiOperation(value = "search Clients", notes = "This API fetches Clients based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session, @RequestHeader("Authorization") String token) {
		return Client_service.fetchAllClients(session, token);
	}
	
	
}