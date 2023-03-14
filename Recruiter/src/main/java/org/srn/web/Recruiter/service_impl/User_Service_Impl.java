package org.srn.web.Recruiter.service_impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.User_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.User;
import org.srn.web.Recruiter.service.User_service;
import org.srn.web.Recruiter.util.SessionManagement;

@Service
@Transactional
public class User_Service_Impl implements User_service {

	@Autowired
	private User_dao_impl user_repos;

	@Autowired
	private SessionManagement sessionManagement;

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<ResponseDto> varifyUser(HttpServletRequest request, String username, String password) {
		User user = user_repos.getUser(username, password);

		HttpSession session = sessionManagement.getSession(request, username);
		
//		System.out.println(session.getAttribute("userName"));
//		System.out.println(session.getAttribute("partnerName"));
//		System.out.println(session.getAttribute("PartnerEmail"));

		
		if (session == null) {
			ResponseDto response = new ResponseDto();

			response.setMessage("Invalid ! username or password");
			response.setBody("Unauthorized to access  Application");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		if (user != null) {
			User user1 = new User();
			user1.setUser_id(user.getUser_id());
			user1.setPartner_id(user.getPartner_id());
			user1.setName(user.getName());
			user1.setEmail(user.getEmail());
			user1.setDate_of_creation(user.getDate_of_creation());
			user1.setCompany(null);
			user1.setContact(null);
			user1.setDesignation(user.getDesignation());
			user1.setRole(null);
			user1.setPassword(null);
			user1.setCreated_by(null);

			ResponseDto response = new ResponseDto();

			response.setMessage("Login ! succesfully");
			response.setBody(user1);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		} else {
			ResponseDto response = new ResponseDto();

			response.setMessage("Invalid ! username or password");
			response.setBody("Unauthorized to access Recruiter Application");
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> addUser(HttpServletRequest request, User user) {
		try {
			if (request.getSession().getAttribute("userName") == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(true);
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
			User user1 = user_repos.getByEmail(user.getEmail());
			if (user1 != null) {
				ResponseDto response = new ResponseDto();

				response.setMessage("User already exist !");
				response.setBody("Cannot add duplicate records");
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				boolean check = user_repos.saveUser(user);
				if (check == true) {
					ResponseDto response = new ResponseDto();

					response.setMessage("User added succesfully");
					response.setBody("New user is :  " + user.getEmail());
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage("Cannot add new user now");
					response.setBody("Due to internal server error");
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> update_password(HttpServletRequest request, String email,
			String current_password, String new_password) {
		try {
			if (request.getSession().getAttribute("userName") == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(true);
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
			User user = user_repos.getUser(email, current_password);
			if (user != null) {
				boolean check = user_repos.update_password(email, new_password);
				if (check == true) {
					ResponseDto response = new ResponseDto();

					response.setMessage("Password changed succesfully!");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage("unable to change password");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please enter correct username and password");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage("Please login!");
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
	}

}
