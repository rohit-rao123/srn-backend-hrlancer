package org.srn.web.Recruiter.service_impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.Invoices_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Invoices;
import org.srn.web.Recruiter.entity.Invoices.ClientInvoiceStatus;
import org.srn.web.Recruiter.entity.Invoices.FinalStatus;
import org.srn.web.Recruiter.entity.Invoices.PartnerInvoiceStatus;

@Service
@Transactional
public class Invoices_Service_impl {

	@Autowired
	private Invoices_dao_impl Invoices_repos;

	@Autowired
	private Application_dao_impl app_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	public ResponseEntity<ResponseDto> addNewrecord(HttpSession session, String token, String partner_invoice_updator,
			String partner_remaining_amt_comment, String partner_invoice_remaining_amt, String partner_transaction_id,
			String partner_invoice_items, String partner_invoice_amt, String partner_invoice_raised_id,
			String client_remaining_amt_comment, String client_invoice_remaining_amt, String client_transaction_id,
			String client_invoice_items, String client_invoice_amt, String client_invoice_id, String application_id,
			String updator, String creator) {
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();

		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		try {
			

			long app_id = 0;
			double clientInvoiceAmt = 0;
			double clientInvoiceRemainingAmt = 0;
			double partnerInvoiceAmt = 0;
			double partnerInvoiceRemainingAmt = 0;
			int clientInvoiceItems = 0;
			int partnerInvoiceItems = 0;

			try {
				if (application_id.isBlank()) {
					response.setMessage("Application_Id is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					app_id = Long.parseLong(application_id);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid application_Id number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		   if (client_invoice_id.isBlank() || client_invoice_id == null) {
				response.setMessage("client_invoice_id is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			try {
				if (client_invoice_amt.isBlank()) {
					response.setMessage("Client Invoice Amt is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					clientInvoiceAmt = Double.parseDouble(client_invoice_amt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid Client Invoice Amt number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			try {
				if (client_invoice_items.isBlank()) {
					response.setMessage("Client Invoice Items is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					clientInvoiceItems = Integer.parseInt(client_invoice_items);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid Client Invoice Items number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
			try {
				if (client_invoice_remaining_amt.isBlank()) {
					response.setMessage("Client Invoice Remaining Amt is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					clientInvoiceRemainingAmt = Double.parseDouble(client_invoice_remaining_amt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid Client Invoice Remaining Amt number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
			 if (partner_invoice_raised_id.isBlank() || partner_invoice_raised_id == null) {
					response.setMessage("partner_invoice_raised_id is mandatory");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} 
			try {
				if (partner_invoice_amt.isBlank()) {
					response.setMessage("Partner Invoice Amt is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					partnerInvoiceAmt = Double.parseDouble(partner_invoice_amt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid Partner Invoice Amt number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			try {
				if (partner_invoice_items.isBlank()) {
					response.setMessage("Partner Invoice Items is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					partnerInvoiceItems = Integer.parseInt(partner_invoice_items);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid Partner Invoice Items number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
			try {
				if (partner_invoice_remaining_amt.isBlank()) {
					response.setMessage("Partner Invoice Remaining Amt is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					partnerInvoiceRemainingAmt = Double.parseDouble(partner_invoice_remaining_amt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid partner_Invoice_Remaining_Amt amount !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}					
			if (partner_invoice_updator.isBlank() || partner_invoice_updator == null) {
				response.setMessage("partner_invoice_updator is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}  else if (creator.isBlank() || creator == null) {
				response.setMessage("creator is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Applications app = app_repos.fetchByApplication(app_id);
				if (app == null) {
					response.setMessage("application_id not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					Invoices invoice = new Invoices();
					
					
//					long app_id = 0;
//					double clientInvoiceAmt = 0;
//					double clientInvoiceRemainingAmt = 0;
//					double partnerInvoiceAmt = 0;
//					double partnerInvoiceRemainingAmt = 0;
//					int clientInvoiceItems = 0;
//					int partnerInvoiceItems = 0;

					invoice.setClient_id(app.getClient_id());
					invoice.setPartner_id((long) session.getAttribute("partner_id"));
					invoice.setJob_id(app.getJob_id());
					invoice.setApplication_id(app_id);
					invoice.setClient_invoice_id(client_invoice_id);
					invoice.setClient_invoice_amt(clientInvoiceAmt);
					invoice.setClient_invoice_items(clientInvoiceItems);
					invoice.setClient_invoice_raised_dt(new Date());
					invoice.setClient_transaction_id(client_transaction_id);
					invoice.setClient_invoice_paid_dt(null);
					invoice.setClient_invoice_remaining_amt(clientInvoiceRemainingAmt);
					invoice.setClient_remaining_amt_comment(client_remaining_amt_comment);
					invoice.setClient_invoice_status(ClientInvoiceStatus.RAISED);
					invoice.setPartner_invoice_raised_id(partner_invoice_raised_id);
					invoice.setPartner_invoice_amt(partnerInvoiceAmt);
					invoice.setPartner_invoice_items(partnerInvoiceItems);
					invoice.setPartner_invoice_raised_dt(null);
					invoice.setPartner_transaction_id(partner_transaction_id);
					invoice.setPartner_invoice_paid_dt(null);
					invoice.setPartner_invoice_remaining_amt(partnerInvoiceRemainingAmt);
					invoice.setPartner_remaining_amt_comment(partner_remaining_amt_comment);
					invoice.setPartner_invoice_updator(partner_invoice_updator);
					invoice.setPartner_invoice_updator_dt(new Date());
					invoice.setPartner_invoice_status(PartnerInvoiceStatus.CLIENT_INVOICE_AWAITED);
					System.out.println(invoice.getPartner_invoice_status());
					invoice.setOnboarded_date(null);
					invoice.setProbation_end_dt(new Date());
					invoice.setFinal_status(FinalStatus.IN_CLIENT_INVOICING);
					invoice.setUpdated_dt(null);
					invoice.setUpdator(updator);
					invoice.setCreator(creator);
					invoice.setDt(new Date());
					invoice.setStatus(1);
					invoice.setPartner_invoice_updator(partner_invoice_updator);
					invoice.setPartner_invoice_updator_dt(new Date());
					boolean check = Invoices_repos.saveInvoices(invoice);
					System.out.println(check);
					if (check == true) {
						response.setMessage("Invoice saved succesfully");
						response.setBody(invoice);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						response.setMessage("unable to save new invoice ");
						response.setBody("Internal server error occured");
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("unable to save new invoice ");
			response.setBody("Internal server error occured");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

//	public ResponseEntity<ResponseDto> fetchByInvoice(HttpSession session,String token, String partnerInvoiceStatus,
//			String partnerInvoiceUpdatorDt, String partnerInvoiceUpdator, String partnerRemainingAmtComment,
//			double partnerInvoiceRemainingAmt, String partnerInvoicePaidDt, String partnerTransactionId,
//			String partnerInvoiceRaisedDt, int partnerInvoiceItems, double partnerInvoiceAmt,
//			String partnerInvoiceRaisedId) {
//		msRequestFilter.validateToken(session, token);
//		ResponseDto response = new ResponseDto();
//
//		try {
//			if (session.getAttribute("isSession") != "true") {
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//		if (!partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchByraisedID(partnerInvoiceRaisedId);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//			}
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt != 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypiamt(partnerInvoiceAmt);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt != null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypidt(partnerInvoiceRaisedDt);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems != 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypitems(partnerInvoiceItems);
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && !partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchByptid(partnerTransactionId);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt != null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypipaidt(partnerInvoicePaidDt);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt != 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypiremamt(partnerInvoiceRemainingAmt);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && !partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypramtcmt(partnerRemainingAmtComment);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& !partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null
//				&& partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypiupdt(partnerInvoiceUpdator);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt != null && partnerInvoiceStatus == null) {
//			List<Invoices> app = Invoices_repos.fetchBypiupDt(partnerInvoiceUpdatorDt);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt == 0.0 && partnerInvoiceItems == 0
//				&& partnerInvoiceRaisedDt == null && partnerTransactionId.isEmpty() && partnerInvoicePaidDt == null
//				&& partnerInvoiceRemainingAmt == 0.0 && partnerRemainingAmtComment.isEmpty()
//				&& partnerInvoiceUpdator.isEmpty() && partnerInvoiceUpdatorDt == null && partnerInvoiceStatus != null) {
//			List<Invoices> app = Invoices_repos.fetchBypistatus(partnerInvoiceStatus);
//
//			if (app == null || app.isEmpty()) {
//
//				response.setMessage("Invoice does not found !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(app);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else {
//
//			response.setMessage("Invalid input");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

	public ResponseEntity<ResponseDto> fetchByInvoice(HttpSession session, String token, String partnerInvoiceStatus,
			String partnerInvoiceUpdatorDt, String partnerInvoiceUpdator, String partnerRemainingAmtComment,
			String partnerInvoiceRemainingAmt, String partnerInvoicePaidDt, String partnerTransactionId,
			String partnerInvoiceRaisedDt, String partnerInvoiceItems, String partnerInvoiceAmt,
			String partnerInvoiceRaisedId) {
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();

		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		
		double partnerInvoiceAmount = 0.0;
		double partnerInvoiceRemainingAmount = 0.0;
		int pInvoiceItems =0;
//		try {
//			if (partnerInvoiceRemainingAmt.isBlank()) {
//				response.setMessage("partner Invoice Remaining Amt is mandatory !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				partnerInvoiceRemainingAmount = Double.parseDouble(partnerInvoiceRemainingAmt);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.setMessage("Enter valid partner Invoice Remaining Amt number !");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//		try {
//			if (partnerInvoiceAmt.isBlank()) {
//				response.setMessage("partner Invoice Amt is mandatory !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				partnerInvoiceAmount = Double.parseDouble(partnerInvoiceAmt);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.setMessage("Enter valid partner_Invoice_Amt amount number!");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		}
//		try {
//			if (partnerInvoiceItems.isBlank()) {
//				response.setMessage("partner Invoice Item is mandatory !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				pInvoiceItems = Integer.parseInt(partnerInvoiceItems);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.setMessage("Enter valid partner Invoice Item number!");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		}

	try {	
		
		if (!partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchByraisedID(session,partnerInvoiceRaisedId);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		} else if (partnerInvoiceRaisedId.isBlank() && !partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			try {
			
					partnerInvoiceAmount = Double.parseDouble(partnerInvoiceAmt);
			
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid partner_Invoice_Amt amount number!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
			List<Invoices> app = Invoices_repos.fetchBypiamt(session,partnerInvoiceAmount);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& !partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypidt(session,partnerInvoiceRaisedDt);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && !partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			try {
				
					pInvoiceItems = Integer.parseInt(partnerInvoiceItems);
				

			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid partner Invoice Item number!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
			List<Invoices> app = Invoices_repos.fetchBypitems(session,pInvoiceItems);
			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && !partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchByptid(session,partnerTransactionId);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && !partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypipaidt(session,partnerInvoicePaidDt);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& !partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			try {
				
					partnerInvoiceRemainingAmount = Double.parseDouble(partnerInvoiceRemainingAmt);
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid partner Invoice Remaining Amt number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			List<Invoices> app = Invoices_repos.fetchBypiremamt(session,partnerInvoiceRemainingAmount);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && !partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypramtcmt(session,partnerRemainingAmtComment);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& !partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank()
				&& partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypiupdt(session,partnerInvoiceUpdator);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && !partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypiupDt(session,partnerInvoiceUpdatorDt);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && !partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchBypistatus(session,partnerInvoiceStatus);

			if (app == null || app.isEmpty()) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (partnerInvoiceRaisedId.isBlank() && partnerInvoiceAmt.isBlank() && partnerInvoiceItems.isBlank()
				&& partnerInvoiceRaisedDt.isBlank() && partnerTransactionId.isBlank() && partnerInvoicePaidDt.isBlank()
				&& partnerInvoiceRemainingAmt.isBlank() && partnerRemainingAmtComment.isBlank()
				&& partnerInvoiceUpdator.isBlank() && partnerInvoiceUpdatorDt.isBlank() && partnerInvoiceStatus.isBlank()) {
			List<Invoices> app = Invoices_repos.fetchAll(session);

			if ( app.isEmpty()  || app == null) {

				response.setMessage("Invoice does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(app);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else {

			response.setMessage("Please search by only one field ! ");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}catch(Exception e) {
		e.printStackTrace();
		response.setMessage("Internal server error !");
		response.setBody(null);
		response.setStatus(false);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
	}
	}

	public ResponseEntity<ResponseDto> fetchAll(HttpSession session, String token) {
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();

		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}		List<Invoices> list = Invoices_repos.fetchAll(session);
		if ( list.isEmpty()) {

			response.setMessage("Invoice does not found !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {

			response.setMessage(VMessageConstant.MESSAGE);
			response.setBody(list);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

}
