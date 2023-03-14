package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.Followups;

public interface Followups_dao {

	public List<Followups> fetchById(long id);

	public List<Followups> findAllFollowups();

	public long saveFollowups(Followups follow);

	public boolean deleteFollowupsById(long id);

	public boolean updateFollowupsById(String updated_by,long followup_Id, String comment, String status, String active);

	public List<Followups> fetchByAId(long id);

	public List<Followups> fetchByClId(long id);

	public List<Followups> fetchByMobile(String contact);
	
	public List<Followups> fetchByComment(String comment);

	public boolean updateFollowupsByContact(String updated_by, String contact, String comment, String status, String active);

	public Followups getByProfAndJobId(long profile_id, long job_id, String followup_status);

	public List<Followups> fetchByMultipleParam(long job_id, String job_name, long application_id, int client_id,
			String contact, String email, String name, long profile_id, String comment, String followups_status,
			String followup_creation_date);

	public List<Followups> findLatestFollowups();

	public List<Followups> fetchAll(HttpSession session);

	public List<Followups> findAllFollowups(HttpSession session);

	public List<Followups> fetchByMobile(HttpSession session, String contact);

}
