package org.srn.web.Recruiter.service_impl;

import java.text.SimpleDateFormat;
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
import org.srn.web.Recruiter.dao_impl.Partners_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Partners;
import org.srn.web.Recruiter.entity.Partners.PartnerCategory;
import org.srn.web.Recruiter.entity.Partners.PartnerType;
import org.srn.web.Recruiter.service.Partner_service;

@Service
@Transactional
public class Partner_service_impl implements Partner_service {

	@Autowired
	private Partners_dao_impl partners_dao_impl;
	
	@Autowired
	private MSRequestFilter msRequestFilter;

	@Override
	public ResponseEntity<ResponseDto> searchByPartnerIdOrType(Long partnerId, String partnerType) {
		ResponseDto response = new ResponseDto();
		try {
			List<Partners> list = partners_dao_impl.searchByPartnerIdOrType(partnerId, partnerType);
			if (list.isEmpty() || list == null) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			} else {
				response.setMessage("searched partners");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseDto> addNewPartner(HttpSession session, String token, String name, String contact,
			String email, String website, String partner_type,String partner_category, String sharing_percentage, String partnership_start,
			String partnership_end) {
		// TODO Auto-generated method stub

		msRequestFilter.validateToken(session, token);
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

		ResponseDto response = new ResponseDto();
		double sharingPercentage = 0;

		try {
			if (name == null || name.isEmpty()) {
				response.setMessage("Name is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (contact == null || contact.isEmpty()) {
				response.setMessage("Contact is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (email == null || email.isEmpty()) {
				response.setMessage("Email is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (website == null || website.isEmpty()) {
				response.setMessage("Website is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (partner_type == null || partner_type.isEmpty()) {
				response.setMessage("Partner type is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if (partner_category.isBlank()) {
				response.setMessage("partner_category is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			try {
				if (sharing_percentage.isBlank()) {
					response.setMessage("sharing Percentage is mandatory !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					sharingPercentage = Double.parseDouble(sharing_percentage);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Enter valid sharing Percentage number !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (partnership_start == null || partnership_start.isEmpty()) {
				response.setMessage("Partnership start date is mandatory.");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Partners partner = new Partners();
			partner.setName(name);
			partner.setContact(contact);
			partner.setEmail(email);
			partner.setWebsite(website);
			if (PartnerType.OFFSHORE.toString().contentEquals(partner_type)) {
				partner.setPartner_type(PartnerType.OFFSHORE);
			} else if (PartnerType.RECRUITMENT.toString().contentEquals(partner_type)) {
				partner.setPartner_type(PartnerType.RECRUITMENT);
			} else if (PartnerType.OFFSHORE_RECRUITMENT.toString().contentEquals(partner_type)) {
				partner.setPartner_type(PartnerType.OFFSHORE_RECRUITMENT);
			}else {
				partner.setPartner_type(PartnerType.OFFSHORE);
			}
			
			if(PartnerCategory.INDIVIDUAL.toString().contains(partner_category)) {
				partner.setPartner_category(PartnerCategory.INDIVIDUAL);
			}else if(PartnerCategory.ORGANIZATION.toString().contains(partner_category)) {
				partner.setPartner_category(PartnerCategory.ORGANIZATION);
			}else {
				partner.setPartner_category(PartnerCategory.INDIVIDUAL);
			}
			

			partner.setSharing_percentage(sharingPercentage);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try {
				Date start_dt = dateFormat.parse(partnership_start);
				partner.setPartnership_start(start_dt);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.setMessage("Invalid date format !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			
			try {
			if(partnership_end.isBlank()) {
				
			}else {
				Date end_dt = dateFormat.parse(partnership_end);
				partner.setPartnership_end(end_dt);
			}
			}catch(Exception e) {
				e.printStackTrace();
			}

			String username = (String) session.getAttribute("email");

			partner.setCreator(username);
			partner.setDt(new Date());
			partner.setStatus(1);
//			partners_dao_impl.getPartnerByEmailAndContact()
			boolean check = partners_dao_impl.save(partner);
			System.out.println(check);
			if (check == true) {
				response.setMessage("Partner saved succesfully");
				response.setBody(partner);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Partner cannot be saved!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseDto> fetchByPartner(HttpSession session, String token, int partnerId, String name,
			String contact, String email, String website, String partnerType,String partner_category, double sharingPercentage,
			String partnershipStart, String partnershipEnd, String creator, String dt, int status) {
		// TODO Auto-generated method stub

		msRequestFilter.validateToken(session, token);
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
		ResponseDto response = new ResponseDto();

			List<Partners> partner = partners_dao_impl.findByPartner(session, partnerId, name, contact, email, website,
					partnerType,partner_category, sharingPercentage, partnershipStart, partnershipEnd, creator, dt, status);

			try {
				if (partner == null || partner.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(partner);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} catch (Exception e) {

				response.setMessage(VMessageConstant.DEFAULT);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}


	}
//	@Override
//    public ResponseEntity<ResponseDto> searchByPartnerIdOrType(Long partnerId, String partnerType) {
//        return partners_dao_impl.searchByPartnerIdOrType(partnerId, partnerType);
//    }
//}

	public ResponseEntity<ResponseDto> fetchAllPartners(HttpSession session,String token) {
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
		List<Partners> list = partners_dao_impl.findAll(session);
		if(list==null || list.isEmpty()) {
			response.setMessage("Does not found");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}else {
			response.setMessage("Searched result:");
			response.setBody(list);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		
	}
}