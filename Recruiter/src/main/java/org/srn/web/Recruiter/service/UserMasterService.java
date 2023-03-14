package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.UserMaster;

public interface UserMasterService {

	public Object getUser(String email);

	public UserMaster varifyPartnerIdOfUser(String email);
	
	public ResponseEntity<ResponseDto> addUser(HttpSession  session,String token,String name,String password ,String company,String email,String designation,String contact);
}
