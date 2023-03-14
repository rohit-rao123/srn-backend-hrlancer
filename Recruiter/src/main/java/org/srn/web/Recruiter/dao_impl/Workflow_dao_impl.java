package org.srn.web.Recruiter.dao_impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Workflow_dao;
import org.srn.web.Recruiter.entity.Workflow;

@Repository
@SuppressWarnings("unchecked")
public class Workflow_dao_impl implements Workflow_dao{

	@Autowired
	private SessionFactory sessionRef;
	
	@Override
	public boolean newWorkflow(Workflow flow) {
		try {
			long id = (long) sessionRef.getCurrentSession().save(flow);

			if (id > 0) {
				System.out.println("Generated  id = " + id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
            System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean deleteWorkflowById(long workflow_id) {
		String query = "delete from workflow where workflow.workflow_id=:identity";
		long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", workflow_id).executeUpdate();
		try {
			if (x >= 1) {
				System.out.println("Number of record deleted :" + x);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Workflow> fetchById(long id) {
		String query = "select * from workflow as w where w.workflow_id =:identity";
		try {
			List<Workflow> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Workflow.class)
					.setParameter("identity", id).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

//	public boolean updateWorkflowById(long workflow_id, long job_id, long application_id, String recruiter_email,
//			String tech_interviewer_1_name, String tech_interviewer_1_comment, String tech_interviewer_2_name,
//			String tech_interviewer_2_comment, String manager_interviewer_1_name, String manager_interviewer_1_comment,
//			String hr_interviewer_1_name, String hr_interviewer_1_comment, String client_interviewer_1_name,
//			String client_interviewer_1_comment, String offer_letter_id, double offer_amt, String onbloading_dt,
//			double joining_amt, String recuiter_stage, String recuiter_status, int status) {
//		int x = 0;
//		
//		if (job_id != 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.job_id=:job where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("job", job_id).executeUpdate();
//			
//		}else if(job_id == 0 && application_id!=0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.application_id=:app where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("app", application_id).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && !recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.recruiter_email=:mail where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("mail", recruiter_email).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && !tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.tech_interviewer_1_name=:name where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("name", tech_interviewer_1_name).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && !tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.tech_interviewer_1_comment=:comment where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("comment", tech_interviewer_1_comment).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && !tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.tech_interviewer_2_name=:nm where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("nm", tech_interviewer_2_name).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && !tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.tech_interviewer_2_comment=:cmmt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("cmmt", tech_interviewer_2_comment).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				!manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.manager_interviewer_1_name=:nm where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("nm", manager_interviewer_1_name).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  !manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.manager_interviewer_1_comment=:cmmt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("cmmt", manager_interviewer_1_comment).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && !hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.hr_interviewer_1_name=:nm where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("nm", hr_interviewer_1_name).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && !hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.hr_interviewer_1_comment=:cmt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("cmt", hr_interviewer_1_comment).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && !client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.client_interviewer_1_name=:nm where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("nm", client_interviewer_1_name).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				!client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.client_interviewer_1_comment=:cmmt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("cmmt", client_interviewer_1_comment).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && !offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.offer_letter_id=:id where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("id", offer_letter_id).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt != 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.offer_amt=:amt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("amt", offer_amt).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && !onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.onbloading_dt=:dt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("dt", onbloading_dt).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt!=0 &&		
//						recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.joining_amt=:amt where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("amt", joining_amt).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						!recuiter_stage.isBlank() && (status!=0 || status !=1)) {
//			String query = "update workflow as w set w.recuiter_stage=:stg where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("stg", recuiter_stage).executeUpdate();
//			
//		}else if(job_id == 0 && application_id==0 && recruiter_email.isBlank() && tech_interviewer_1_name.isBlank() && tech_interviewer_1_comment.isEmpty() && tech_interviewer_2_name.isEmpty() && tech_interviewer_2_comment.isEmpty() &&
//				manager_interviewer_1_name.isEmpty() &&  manager_interviewer_1_comment.isBlank() && hr_interviewer_1_name.isBlank() && hr_interviewer_1_comment.isBlank() && client_interviewer_1_name.isBlank() &&
//				client_interviewer_1_comment.isBlank() && offer_letter_id.isBlank() && offer_amt == 0 && onbloading_dt.isBlank() && joining_amt==0 &&		
//						recuiter_stage.isBlank() && (status==0 || status ==1)) {
//			String query = "update workflow as w set w.job_id=:job where p.workflow_id=:id";
//			x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", workflow_id)
//					.setParameter("job", job_id).executeUpdate();
//		}
//	}
}
