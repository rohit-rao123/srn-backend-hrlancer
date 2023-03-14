package org.srn.web.Recruiter.service;

import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;

public interface Partner_service {
	
	 public ResponseEntity<ResponseDto> searchByPartnerIdOrType(Long partnerId, String partnerType) ;
}
