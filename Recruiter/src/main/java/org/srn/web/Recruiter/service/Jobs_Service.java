package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Jobs;

public interface Jobs_Service {

	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request,  long jobs_id);

	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request);

	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Jobs job);

	public ResponseEntity<ResponseDto> eraseByJobsId(HttpServletRequest request, long job_id);

//	public ResponseEntity<ResponseDto> editRecordById(long job_id,
//			String head_count, String hired_count,  String max_exp, String max_salary, String min_exp,
//			String open, String domain, int status);

	public ResponseEntity<ResponseDto> fetchByName(HttpServletRequest request, String jobs_name);

	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String job_name);

	public ResponseEntity<Resource> downloadAllServices(HttpServletRequest request);

	public ResponseEntity<ResponseDto> updateRecordById(HttpServletRequest request, long job_id, String head_count, String hired_count, String max_exp,
			String max_salary, String min_exp, String open, String domain, int status);

	public ResponseEntity<ResponseDto> fetchByCid(HttpServletRequest request, int client_id, String jobs_name);

	public ResponseEntity<ResponseDto> addrecord(HttpSession session, int client_id, String domain,
			String job_role, String skills, String jd, double min_yr_exp, double max_yr_exp, double max_yr_budget,
			String qualification, String location, String job_type, String working_mode, int head_count,int notice);

	public ResponseEntity<ResponseDto> searchByOptions(HttpServletRequest request,long job_id, int client_id, String jobtype, String domain, String skills, String location,
			double min_yr_exp, double max_yr_exp, double max_yr_budget, String working_mode, int open);

	public ResponseEntity<ResponseDto> addrecord(HttpSession session, String token, int client_id, String domain,
			String job_role, String skills, String jd, double min_yr_exp, double max_yr_exp, double max_yr_budget,
			String qualification, String location, String job_type, String working_mode, int head_count, int notice);

	public ResponseEntity<ResponseDto> searchByOptions(HttpSession session, String token, long job_id, int client_id,
			String jobtype, String domain, String skills, String location, double min_yr_exp, double max_yr_exp,
			double max_yr_budget, String working_mode, int open);

	public ResponseEntity<Resource> downloadAllServices(HttpSession session, String job_id);



}
