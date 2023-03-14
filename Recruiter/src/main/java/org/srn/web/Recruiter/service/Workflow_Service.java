package org.srn.web.Recruiter.service;

import org.springframework.http.ResponseEntity;
import org.srn.web.Recruiter.dto.ResponseDto;

public interface Workflow_Service {

	public ResponseEntity<ResponseDto> eraseByWorkflowId(long workflow_id);

	public ResponseEntity<ResponseDto> addWorkflow(long job_id, long application_id, String recruiter_email,
			String tech_interview_1_date, String tech_interviewer_1_name, String tech_interviewer_1_comment,
			String tech_interview_2_date, String tech_interviewer_2_name, String tech_interviewer_2_comment,
			String manager_interview_1_date, String manager_interviewer_1_name, String manager_interviewer_1_comment,
			String hr_interview_1_date, String hr_interviewer_1_name, String hr_interviewer_1_comment,
			String client_interview_1_date, String client_interviewer_1_name, String client_interviewer_1_comment,
			String offer_letter_id, double offer_amt, String onbloading_dt, double joining_amt, String recuiter_stage,
			String recuiter_status);

}
