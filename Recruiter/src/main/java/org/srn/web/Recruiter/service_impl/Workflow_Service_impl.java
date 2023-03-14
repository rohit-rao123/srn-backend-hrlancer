package org.srn.web.Recruiter.service_impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Workflow_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Workflow;
import org.srn.web.Recruiter.service.Workflow_Service;

@Service
@Transactional
public class Workflow_Service_impl implements Workflow_Service {

	@Autowired
	Workflow_dao_impl work_repos;

	@Override
	public ResponseEntity<ResponseDto> addWorkflow(long job_id, long application_id, String recruiter_email,
			String tech_interview_1_date, String tech_interviewer_1_name, String tech_interviewer_1_comment,
			String tech_interview_2_date, String tech_interviewer_2_name, String tech_interviewer_2_comment,
			String manager_interview_1_date, String manager_interviewer_1_name, String manager_interviewer_1_comment,
			String hr_interview_1_date, String hr_interviewer_1_name, String hr_interviewer_1_comment,
			String client_interview_1_date, String client_interviewer_1_name, String client_interviewer_1_comment,
			String offer_letter_id, double offer_amt, String onbloading_dt, double joining_amt, String recuiter_stage,
			String recuiter_status) {
		
		Workflow flow = new Workflow();
		try {
			if (flow.getApplication_id() <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (flow.getJob_id() <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				flow.setStatus(1);
				Date date = new Date();
				flow.setWorkflow_creation_date(date);
				flow.setCreated_by("charlie.indulkar@sroniyan.com");
				boolean check = work_repos.newWorkflow(flow);
				if (check == true) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.CREATED);
					response.setBody("Generated Id:  " + flow.getWorkflow_id());
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Error);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.Error);
			response.setBody("Workflow is not saved, due to exception");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> eraseByWorkflowId(long workflow_id) {
		boolean check = work_repos.deleteWorkflowById(workflow_id);
		try {
			if (workflow_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + workflow_id);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
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

	}

	public ResponseEntity<ResponseDto> fetchById(long workflow_id) {
		List<Workflow> work = work_repos.fetchById(workflow_id);
		try {
			if (workflow_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (work == null || work.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(work);
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

	}

//	public ResponseEntity<ResponseDto> edit(long workflow_id, long job_id, long application_id, String recruiter_email,
//			 String tech_interviewer_1_name, String tech_interviewer_1_comment,
//			 String tech_interviewer_2_name, String tech_interviewer_2_comment,
//			 String manager_interviewer_1_name, String manager_interviewer_1_comment,
//			String hr_interviewer_1_name, String hr_interviewer_1_comment,
//			 String client_interviewer_1_name, String client_interviewer_1_comment,
//			String offer_letter_id, double offer_amt, String onbloading_dt, double joining_amt, String recuiter_stage,
//			String recuiter_status, int status) {
//		try {
//			if (workflow_id <= 0) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.ID_MISSING);
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				boolean check = work_repos.updateWorkflowById(workflow_id, job_id,application_id,  recruiter_email,
//						   tech_interviewer_1_name,  tech_interviewer_1_comment,
//						   tech_interviewer_2_name,  tech_interviewer_2_comment,
//						   manager_interviewer_1_name,  manager_interviewer_1_comment,
//						   hr_interviewer_1_name,  hr_interviewer_1_comment,
//						   client_interviewer_1_name,  client_interviewer_1_comment,
//						 offer_letter_id,  offer_amt,  onbloading_dt,  joining_amt,  recuiter_stage,
//						 recuiter_status,status);
//			}
//				if (job_id != 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id!=0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && !recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && !tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && !tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && !tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && !tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						!manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  !manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && !hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && !hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && !client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						!client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && !offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt != 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && !onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id != 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id != 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id != 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt!=0 &&		
//								recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								!recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//					
//				}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//						manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//						client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//								recuiter_stage.isBlank() && (status==0 || status ==1)) {
//					
//				}
	
		
	
	


}
