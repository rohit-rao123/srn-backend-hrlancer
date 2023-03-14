package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.dto.ResponseDto;

public interface Application_Service {

	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request,  long application_id);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request);

//	public ResponseEntity<ResponseDto> addNewrecord(Application app);

	public ResponseEntity<ResponseDto> eraseByApplicationId(HttpServletRequest request, long application_id);

	public ResponseEntity<ResponseDto> editApplicationRecordById(HttpServletRequest request, long application_id, String comment ,
			int status);

	public ResponseEntity<ResponseDto> fetchByPId(HttpServletRequest request, long profile_id);

//	public ResponseEntity<ResponseDto> addAppFile(MultipartFile file);

	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String skills);

	public ResponseEntity<Resource> downloadServices(HttpServletRequest request);

	public ResponseEntity<ResponseDto> addNewrecord(HttpSession session,String token, long profile_id, long job_id, double is_ectc_nego, double rel_exp ,String comment
			,MultipartFile file);

	public ResponseEntity<ResponseDto> addAppByDomain(HttpServletRequest request, long job_id);
	
	public ResponseEntity<ResponseDto> fetchByContactOrEmail(HttpServletRequest request, String contact, String email);

	public ResponseEntity<ResponseDto> updateApplicationRecordById(HttpSession session,String token, long application_id,
			double ectc,int notice,String progress);

	public ResponseEntity<ResponseDto> fetchByApp(HttpSession session,String token, String contact, String email, String application_status,
			String app_creation_date);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpSession session, String token);

	public ResponseEntity<ResponseDto> jobApplications(HttpSession session, String token, long profile_id, long job_id,
			double is_ectc_nego, double rel_exp, String comment, int notice_period);

}