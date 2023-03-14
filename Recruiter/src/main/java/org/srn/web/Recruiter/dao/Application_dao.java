package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.ApplicationProfile;
import org.srn.web.Recruiter.entity.Applications;

public interface Application_dao {

	public List<Applications> fetchById(long id);

	public List<Applications> findAllApplication();
	
	public List<Applications> findLatestApplication();

	public boolean saveApplication(Applications app);

	public boolean deleteApplicationById(long id);

	public boolean updateApplicationById(long application_id, String comment ,
			int status);

	public Object fetchByPId(long id);

	public List<Applications> fetchByDomain(String domain);

	public Applications fetchByContact(String contact);

	public Applications fetchByEmail(String email);

	public List<Applications> getByJidAndPId(long job_id, long profile_id);
	
	public List<Applications> getByJidAndEmail(long job_id, String email);

	public List<Applications> getById(long id);
	
	public List<Applications> getByContact(String contact);

	public List<Applications> getByEmail(String email);
	
	public List<Applications> getByJobAndProfile(long job_id, long profile_id);

	public Applications getAppById(long id);

	public List<ApplicationProfile> findByMobile(HttpSession session,String contact);

	public List<ApplicationProfile> findByEmail(HttpSession session,String email);

	public List<Applications> getByPId(long id);

	public Applications findByPId(long id);

	public boolean updateApp(long application_id, int client_id, long job_id, double rel_exp, String comment,
			String application_status, int is_ectc_nego);

	public Applications getByProfAndJobId(long profile_id, long job_id, String followup_status);

	public List<ApplicationProfile> findByApplicationStatus(HttpSession session,String application_status);

	public List<ApplicationProfile> findByDate(HttpSession session,String app_creation_date);

	public Applications getByAppIdAndStatus(long application_id, String string);

	public List<ApplicationProfile> findAll(HttpSession session);

	public List<ApplicationProfile> findAllApplication(HttpSession session);

	public List<Applications> fetchByJob_idAndProfile_id(long job_id, long profile_id);

}
