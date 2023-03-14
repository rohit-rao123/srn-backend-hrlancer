package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Followups;

public interface Followups_Service {

	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long followup_id);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request);

	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Followups follow);


	public ResponseEntity<ResponseDto> editFollowupsRecordById(HttpServletRequest request, long followup_id, String active, String comment,
			String status);

	public ResponseEntity<ResponseDto> fetchByAId(HttpServletRequest request, long application_id);

	public ResponseEntity<ResponseDto> fetchByClId(HttpServletRequest request, int client_id);

	public ResponseEntity<ResponseDto> fetchByMobile(HttpServletRequest request, String contact);
	
	public ResponseEntity<ResponseDto> fetchByComment(HttpServletRequest request, String comment);

//	public ResponseEntity<ResponseDto> fetchByMultipleOption(long job_id, String job_name, long application_id,
//			int client_id, String contact, String email, String name, long profile_id, String comment);

	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String comment);
	
	public ResponseEntity<Resource> downloadAllServices(HttpServletRequest request);

	public ResponseEntity<ResponseDto> editFollowupsById(HttpServletRequest request, long id, String active, String comment, String status);

	public ResponseEntity<ResponseDto> updateFollowupsById(HttpServletRequest request, long profile_id, String contact, String active, String comment,
			String status);

	public ResponseEntity<ResponseDto> eraseByFollowupId(HttpServletRequest request, long followup_id);

//	public ResponseEntity<ResponseDto> add_followup(String followup_status, String comment, String contact, String email);

	public ResponseEntity<ResponseDto> add_followup(HttpSession session,String token, String comment, String contact, String email);

	public ResponseEntity<ResponseDto> fetchByMultipleOption(HttpServletRequest request, long job_id, String job_name, long application_id, int client_id,
			String contact, String email, String name, long profile_id, String comment, String followup_status,
			String followup_creation_date);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpSession session, String token);

//	public ResponseEntity<ResponseDto> add_followup(String comment, String followup_status, long application_id,
//			String contact, String email);

}
