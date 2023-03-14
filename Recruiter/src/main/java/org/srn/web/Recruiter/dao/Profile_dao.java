package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.Profiles;

public interface Profile_dao {
	public List<Profiles> fetchById(long id);

	public List<Profiles> findAllProfile();
	
	public List<Profiles> findLatestProfile(); 

	public boolean saveProfile(Profiles prof);

	public boolean deleteProfileById(long id);

//	public boolean updateProfileById(long identity, String name, String contact, String email, String ctc, String ectc,
//			String current_role, String company, String location, String resume, String domain, String primary_skill,
//			String secondary_skill, String company_id, Date dt, String status, String alternate_contact,
//			String alternate_email, String qualification, String year_of_passing);

	public List<Profiles> findByMobile(String contact);

	public List<Profiles> findByEmail(String email);

	public List<Profiles> findByName(String name);

	public boolean checkContactDuplicacy(String contact);

	public boolean checkEmailDuplicacy(String email);

	public boolean updateName(long identity, String name);

	public boolean updateContact(long identity, String contact);

	public boolean updateEmail(long identity, String email);

	public boolean newProfile(Profiles prof);

	public List<Profiles> fetchBySkills(String domain);

	public boolean updateProfileById(long profile_id, String name, String contact, String email, String location);

//	public List<Profile> getProfileByContactAndEmail( String email);

	public Profiles getById(long id);

	public List<Profiles> getProfileByContactAndEmail(String contact, String email);

	public Profiles getByMobile(String contact);

	public Profiles getByEmail(String email);

	public List<Profiles> findAllProfile(long partner_id);

	public List<Profiles> findByMobile(HttpSession session, String contact);

	public List<Profiles> findByEmail(HttpSession session, String email);

	public List<Profiles> findByName(HttpSession session, String name);

//	public boolean newProfileCreated(int company_id, Date prof_creation_date, String name, String contact, String email,
//			String ctc, String ectc, String current_role, String company, String location, String resume, String domain,
//			String primary_skill, String secondary_skill, String exp, String alternate_contact, String alternate_email,
//			String qualification, String year_of_passing, String notice_period);

}
