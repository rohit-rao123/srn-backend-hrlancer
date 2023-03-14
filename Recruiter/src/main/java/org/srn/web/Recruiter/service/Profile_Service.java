package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Profiles;

public interface Profile_Service {

	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long profile_id);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request);

	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request,  Profiles prof);

	public ResponseEntity<ResponseDto> eraseByProfileId(HttpServletRequest request, long profile_id);

	public ResponseEntity<ResponseDto> fetchByContact(HttpServletRequest request,  String contact);

	public ResponseEntity<ResponseDto> fetchByEmail(HttpServletRequest request,  String email);

//	public ResponseEntity<ResponseDto> addProfileFile(MultipartFile file);

	public ResponseEntity<ResponseDto> editName(HttpServletRequest request,  long identity, String name);

	public ResponseEntity<ResponseDto> editContact(HttpServletRequest request,  long identity, String contact);

	public ResponseEntity<ResponseDto> editEmail(HttpServletRequest request,  long identity, String email);

	public ResponseEntity<ResponseDto> fetchProfile(HttpSession session,String token, String mobile_number, String email,
			String name);

	public ResponseEntity<Resource> downloadService(HttpServletRequest request);

//	public ResponseEntity<ResponseDto> CreateMultipleProfile(MultipartFile file);

	public ResponseEntity<Resource> downloadService(HttpServletRequest request,  String domain);

	public ResponseEntity<ResponseDto> edit(HttpServletRequest request,  long profile_id, String email, String contact, String name,
			String location);

	public ResponseEntity<ResponseDto> addlist(HttpServletRequest request, MultipartFile file);

	public ResponseEntity<ResponseDto> add_profile(HttpServletRequest request,  long company_id, String name, String contact, String email, long ctc,
			long ectc, String current_role, String company, String location, String domain, String primary_skill,
			String secondary_skill, long exp, String alternate_contact, String alternate_email, String qualification,
			int year_of_passing, int notice_period, String resume);

	public ResponseEntity<ResponseDto> saveProfile(HttpSession session,String token ,String name,
			String contact, String email, double ctc, double ectc, String current_role, String company, String location,
			String domain, String primary_skill, String secondary_skill, double exp, String alternate_contact,
			String alternate_email, String qualification, int year_of_passing, int notice_period,
			MultipartFile resume);

	public ResponseEntity<ResponseDto> fileDeleter(HttpServletRequest request, String filename);

	public boolean inactivateSession(long lastAccessedTime);

	public Profiles createProfile(HttpSession session, String token, long company_id, String name, String contact,
			String email, double ctc, double ectc, String current_role, String company, String location, String domain,
			String primary_skill, String secondary_skill, double exp, String alternate_contact, String alternate_email,
			String qualification, int year_of_passing, int notice_period, MultipartFile resume);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpSession session, String token);

	public ResponseEntity<ResponseDto> addAppWithProfileCreating(HttpSession session, String token, String name,
			String contact, String email, double ctc, double ectc, String current_role, String company, String location,
			String domain, String primary_skill, String secondary_skill, double exp, String alternate_contact,
			String alternate_email, String qualification, int year_of_passing, int notice_period, long job_id,
			int is_ectc_nego, double rel_exp, String comment, MultipartFile resume);

}
