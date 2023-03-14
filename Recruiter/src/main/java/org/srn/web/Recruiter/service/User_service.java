package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.User;

public interface User_service {

	public ResponseEntity<ResponseDto> varifyUser(HttpServletRequest request, String username, String password);

	public ResponseEntity<ResponseDto> addUser(HttpServletRequest request, User user);

	public ResponseEntity<ResponseDto> update_password(HttpServletRequest request, String email, String current_password,
			String new_password);
	
}
