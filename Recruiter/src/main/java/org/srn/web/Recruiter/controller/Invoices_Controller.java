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
import org.srn.web.Recruiter.service_impl.Invoices_Service_impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/Recruit/Invoices")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@Api(value = "Recruiter_microservice", description = "Invoices Management Operations", tags = "Invoices Controller")

public class Invoices_Controller {

	@Autowired
	private Invoices_Service_impl Invoices_service;

	@PostMapping("/add/Invoices")
	@ApiOperation(value = "add new record", notes = "This Rest Api  add new  Invoice records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> save_Invoice(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter the application ID", required = false) @RequestParam(required = false, defaultValue = "") String application_id,
			@ApiParam(value = "Enter the client invoice ID", required = false) @RequestParam(required = false, defaultValue = "") String client_invoice_id,
			@ApiParam(value = "Enter the client invoice amount", required = false) @RequestParam(required = false, defaultValue = "") String client_invoice_amt,
			@ApiParam(value = "Enter the client invoice items", required = false) @RequestParam(required = false, defaultValue = "") String client_invoice_items,
			@ApiParam(value = "Enter the client transaction ID", required = false) @RequestParam(required = false, defaultValue = "") String client_transaction_id,
			@ApiParam(value = "Enter the client invoice remaining amount", required = false) @RequestParam(required = false, defaultValue = "") String client_invoice_remaining_amt,
			@ApiParam(value = "Enter the client remaining amount comment", required = false) @RequestParam(required = false, defaultValue = "") String client_remaining_amt_comment,
			@ApiParam(value = "Enter the partner invoice raised ID", required = false) @RequestParam(required = false, defaultValue = "") String partner_invoice_raised_id,
			@ApiParam(value = "Enter the partner invoice amount", required = false) @RequestParam(required = false, defaultValue = "") String partner_invoice_amt,
			@ApiParam(value = "Enter the partner invoice items", required = false) @RequestParam(required = false, defaultValue = "") String partner_invoice_items,
			@ApiParam(value = "Enter the partner transaction ID", required = false) @RequestParam(required = false, defaultValue = "") String partner_transaction_id,
			@ApiParam(value = "Enter the partner invoice remaining amount", required = false) @RequestParam(required = false, defaultValue = "") String partner_invoice_remaining_amt,
			@ApiParam(value = "Enter the partner remaining amount comment", required = false) @RequestParam(required = false, defaultValue = "") String partner_remaining_amt_comment,
			@ApiParam(value = "Enter the partner invoice updator", required = false) @RequestParam(required = false, defaultValue = "") String partner_invoice_updator,
			@ApiParam(value = "Enter the updator", required = false) @RequestParam(required = false, defaultValue = "") String updator,
			@ApiParam(value = "Enter the creator", required = false) @RequestParam(required = false, defaultValue = "") String creator) {

		return Invoices_service.addNewrecord(session, token, partner_invoice_updator, partner_remaining_amt_comment,
				partner_invoice_remaining_amt, partner_transaction_id, partner_invoice_items, partner_invoice_amt, 
				partner_invoice_raised_id,
				client_remaining_amt_comment, client_invoice_remaining_amt, 
				client_transaction_id, client_invoice_items, client_invoice_amt, client_invoice_id, application_id, updator, creator);
	}

	@GetMapping("/search")
	@ApiOperation(value = "search Invoices", notes = "This API fetches Invoices based on specified parameters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> searchInvoices(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "search by partner_invoice_raised_id ", required = false) @RequestParam(value = "partner_invoice_raised_id", required = false, defaultValue = "") String partner_invoice_raised_id,
			@ApiParam(value = "search by partner_invoice_amt ", required = false) @RequestParam(value = "partner_invoice_amt", required = false, defaultValue = "") String partner_invoice_amt,
			@ApiParam(value = "search by partner_invoice_items ", required = false) @RequestParam(value = "partner_invoice_items", required = false, defaultValue = "") String partner_invoice_items,
			@ApiParam(value = "yyyy-MM-dd format ", required = false) @RequestParam(value = "partner_invoice_raised_dt", required = false, defaultValue = "") String partner_invoice_raised_dt,
			@ApiParam(value = "search by partner_transaction_id ", required = false) @RequestParam(value = "partner_transaction_id", required = false, defaultValue = "") String partnerTransactionId,
			@ApiParam(value = "yyyy-MM-dd format ", required = false) @RequestParam(value = "partner_invoice_paid_dt", required = false, defaultValue = "") String partner_invoice_paid_dt,
			@ApiParam(value = "search by partner_invoice_remaining_amt ", required = false) @RequestParam(value = "partner_invoice_remaining_amt", required = false, defaultValue = "") String partner_invoice_remaining_amt,
			@ApiParam(value = "search by partner_remaining_amt_comment ", required = false) @RequestParam(value = "partner_remaining_amt_comment", required = false, defaultValue = "") String partner_remaining_amt_comment,
			@ApiParam(value = "search by partner_invoice_updator ", required = false) @RequestParam(value = "partner_invoice_updator", required = false, defaultValue = "") String partner_invoice_updator,
			@ApiParam(value = "yyyy-MM-dd format ", required = false) @RequestParam(value = "partner_invoice_updator_dt", required = false, defaultValue = "") String partner_invoice_updator_dt,
			@ApiParam(value = "search by partner_invoice_status ", required = false) @RequestParam(value = "partner_invoice_status", required = false, defaultValue = "") String partner_invoice_status) {

		
		return Invoices_service.fetchByInvoice(session, token, partner_invoice_status, partner_invoice_updator_dt,
				partner_invoice_updator, partner_remaining_amt_comment, partner_invoice_remaining_amt, partner_invoice_paid_dt,
				partnerTransactionId, partner_invoice_raised_dt, partner_invoice_items, partner_invoice_amt,
				partner_invoice_raised_id);
	}

	@GetMapping("/getAll")
	@ApiOperation(value = "search all Invoices", notes = "This API fetches all Invoices ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAllInvoices(HttpSession session,
			@RequestHeader("Authorization") String token) {
		return Invoices_service.fetchAll(session, token);
	}

}
