package org.srn.web.Recruiter.dao_impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Followups_dao;
import org.srn.web.Recruiter.entity.Followups;
import org.srn.web.Recruiter.entity.Profiles;

@Repository
@SuppressWarnings("unchecked")

public class Followups_dao_impl implements Followups_dao {

	@Autowired
	private SessionFactory sessionRef;
	
	@Autowired
	private Profile_dao_impl prof_repos;


	@Override
	public List<Followups>  fetchById(long followup_id) {
		String query = "select * from followups as f where f.followups_id=:identity";
		try {
			List<Followups>  list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("identity", followup_id).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Followups> fetchByMultipleParam(long job_id, String job_name, long application_id, int client_id,
			String contact, String email, String name, long profile_id, String comment , String followups_status ,String  followup_creation_date) {

		if (job_id > 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank()
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null ) {
			

			try {
				String query = "select * from followups as f where f.job_id=:jId";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("jId", job_id).getResultList();

				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() == false && application_id == 0 && client_id == 0 && contact.isBlank()
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select *  from followups as f inner join jobs as j on f.job_id=j.job_id  where j.job_name=:jname";

				System.out.println("Dao");

				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("jname", job_name).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id > 0 && client_id == 0 && contact.isBlank()
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select * from followups as f where f.application_id=:aId";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("aId", application_id).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id > 0 && contact.isBlank()
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select *  from followups as f inner join application as a on a.application_id=f.application_id  where a.client_id=:clId";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("clId", client_id).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank()== false
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select *  from followups as f  where f.contact=:cntct";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("cntct", contact).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() == false  && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select *  from followups as f  where f.email=:mail";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("mail", email).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() && name.isBlank() == false && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			List<Profiles> locallist = prof_repos.findByName(name);
			
			try {
				if(locallist == null || locallist.isEmpty()) {
					return null;
				}else {
					List<Followups> list2 = new ArrayList<Followups>();
					for(Profiles pf : locallist) {
						long y = pf.getProfile_id();
						String query = "select *  from followups as f  where f.profile_id=:pid";
						List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
								.addEntity(Followups.class).setParameter("pid", y).getResultList();
						list2.addAll(list);
					}
					return list2;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() && name.isBlank() && profile_id > 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select *  from followups as f  where f.profile_id=:pId";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(Followups.class).setParameter("pId", profile_id).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}  else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank()== false && followups_status.isBlank() && followup_creation_date==null) {

			try {
				String query = "select * from followups as f where f.comment like :cmmt";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
						.setParameter("cmmt", "%"+comment+"%").getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank()==false && followup_creation_date==null) {

			try {
				String query = "select * from followups as f where f.followup_status like :sts";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
						.setParameter("sts", "%"+followups_status+"%").getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date!=null) {

			System.out.println("Dao");
			try {
				String query = "select * from followups as f where cast(f.followup_creation_date as date)=:dt";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
						.setParameter("dt", followup_creation_date).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}

	}

	public List<Followups> fetchByMobileEmail(String contact, String email){
		String query = "select * from followups where contact = :contact or email = :email";

		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Followups.class)
					.setParameter("contact", contact)
					.setParameter("email", email)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}

	}
	
	@Override
	public List<Followups> findAllFollowups(HttpSession session ) {
		long partner_id = (long) session.getAttribute("partner_id");
		try {
			String query = "select  * from  followups as f where f.partner_id =:pid order by followups_id desc";
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("pid",partner_id )
					.list();
//			System.out.println(list);
			return list;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Followups> findLatestFollowups() {
		String query = "select  * from  followups order by followups_id desc limit 10 ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Followups.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Followups getByProfAndJobId(long profile_id, long job_id, String followup_status) {
		String query = "select * from followups as f where f.profile_id =:pid and f.job_id =:jid and f.followups_status like :status";
		try {
			Followups follow = (Followups) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("pid", profile_id ).setParameter("jid", job_id ).setParameter("status", "%"+followup_status+"%" ).getSingleResult();
			return follow;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public long saveFollowups(Followups follow) {
		long new_followup_id = (long) sessionRef.getCurrentSession().save(follow);
		System.out.println("New record created :" + new_followup_id);
		return new_followup_id;
	}

	@Override
	public boolean deleteFollowupsById(long id) {
		String query = "delete from followups where followups.followups_id=:identity";
		long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", id).executeUpdate();
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

//	@Override
//	public boolean updateFollowupsById(long profile_id, String comment, String status, int active) {
//		String query = "update Followups as f set f.comment=:Cmnt,f.status=:st,f.active=:act where f.profile_id=:identity ";
//		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", profile_id)
//				.setParameter("Cmnt", comment).setParameter("st", status).setParameter("act", active).executeUpdate();
//		try {
//			if (x >= 1) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
//	select *  from Followups as f inner join Application as a on a.application_id=f.application_id  where a.profile_id=:pId
	@Override
	public boolean updateFollowupsById(String updated_by, long profile_id, String comment, String status, String followup_status) {
		int x = 0;
		Date dt =new Date();
		try {
			if (!followup_status.isEmpty() && comment.isEmpty() && status.isEmpty()) {
				String query = "update followups as f set f.followup_status=:act,f.updated_by=:uby,f.followup_updated_date=:date where f.profile_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
						.setParameter("act", followup_status).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} else if (followup_status.isEmpty()  && !comment.isEmpty() && status.isEmpty()) {
				String query = "update followups as f set f.comment=:Cmnt,f.updated_by=:uby,f.followup_updated_date=:date where f.profile_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
						.setParameter("Cmnt", comment).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} else if(followup_status.isEmpty()  && comment.isEmpty() && !status.isEmpty()) {
				String query = "update followups as f set f.status=:st,f.updated_by=:uby,f.followup_updated_date=:date where f.profile_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
						.setParameter("st", status).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} 
		} catch (Exception se) {
			return false;
		}

		try {
			if (x >= 1) {
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
	public boolean updateFollowupsByContact(String updated_by,String contact, String comment, String status, String followup_status) {
		int x = 0;
		Date dt =new Date();
		try {
			if (!followup_status.isEmpty() && comment.isEmpty() && status.isEmpty()) {
				String query = "update followups as f set f.followup_status=:act,f.updated_by=:uby,f.followup_updated_date=:date where f.contact=:cntct";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("cntct", contact)
						.setParameter("act", followup_status).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} else if (followup_status.isEmpty()  && !comment.isEmpty() && status.isEmpty()) {
				String query = "update followups as f set f.comment=:Cmnt,f.updated_by=:uby,f.followup_updated_date=:date where f.contact=:cntct";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("cntct", contact)
						.setParameter("Cmnt", comment).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} else if(followup_status.isEmpty()  && comment.isEmpty() && !status.isEmpty()) {
				String query = "update followups as f set f.status=:st,f.updated_by=:uby,f.followup_updated_date=:date where f.contact=:cntct";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("cntct", contact)
						.setParameter("st", status).setParameter("uby", updated_by).setParameter("date", dt).executeUpdate();
			} 
		} catch (Exception se) {
			return false;
		}

		try {
			if (x >= 1) {
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
	public List<Followups> fetchByAId(long id) {
		String query = "select * from followups as f where f.application_id=:identity";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("identity", id).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Followups> fetchByClId(long id) {
		String query = "select *"
				+ "from followups inner join  application on followups.profile_id = application.profile_id"
				+ " where application.client_id=:identity ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("identity", id).getResultList();
			System.out.println(list);
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	

//	@Override
//	public List<Followups> fetchByMultipleParam(long job_id, String job_name, long application_id, int client_id,
//			String contact, String email, String name, long profile_id, String comment , String followups_status ,String  followup_creation_date) {
//
//		if (job_id > 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank()
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null ) {
//			
//
//			try {
//				String query = "select * from followups as f where f.job_id=:jId";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("jId", job_id).getResultList();
//
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() == false && application_id == 0 && client_id == 0 && contact.isBlank()
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select *  from followups as f inner join jobs as j on f.job_id=j.job_id  where j.job_name=:jname";
//
//				System.out.println("Dao");
//
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("jname", job_name).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id > 0 && client_id == 0 && contact.isBlank()
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select * from followups as f where f.application_id=:aId";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("aId", application_id).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id > 0 && contact.isBlank()
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select *  from followups as f inner join application as a on a.application_id=f.application_id  where a.client_id=:clId";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("clId", client_id).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank()== false
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select *  from followups as f  where f.contact=:cntct";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("cntct", contact).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() == false  && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select *  from followups as f  where f.email=:mail";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("mail", email).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() && name.isBlank() == false && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			List<Profiles> locallist = prof_repos.findByName(name);
//			
//			try {
//				if(locallist == null || locallist.isEmpty()) {
//					return null;
//				}else {
//					List<Followups> list2 = new ArrayList<Followups>();
//					for(Profiles pf : locallist) {
//						long y = pf.getProfile_id();
//						String query = "select *  from followups as f  where f.profile_id=:pid";
//						List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//								.addEntity(Followups.class).setParameter("pid", y).getResultList();
//						list2.addAll(list);
//					}
//					return list2;
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		} else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() && name.isBlank() && profile_id > 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select *  from followups as f  where f.profile_id=:pId";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query)
//						.addEntity(Followups.class).setParameter("pId", profile_id).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}  else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank()== false && followups_status.isBlank() && followup_creation_date==null) {
//
//			try {
//				String query = "select * from followups as f where f.comment like :cmmt";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
//						.setParameter("cmmt", "%"+comment+"%").getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank()==false && followup_creation_date==null) {
//
//			try {
//				String query = "select * from followups as f where f.followup_status like :sts";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
//						.setParameter("sts", "%"+followups_status+"%").getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}else if (job_id == 0 && job_name.isBlank() && application_id == 0 && client_id == 0 && contact.isBlank() 
//				&& email.isBlank() && name.isBlank() && profile_id == 0 && comment.isBlank() && followups_status.isBlank() && followup_creation_date!=null) {
//
//			System.out.println("Dao");
//			try {
//				String query = "select * from followups as f where cast(f.followup_creation_date as date)=:dt";
//				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
//						.setParameter("dt", followup_creation_date).getResultList();
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}else {
//			return null;
//		}
//
//	}
	
	@Override
	public List<Followups> fetchByComment(String comment) {
		String query = "select * from followups as f where f.comment like :comment";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("comment", "%"+comment+"%").getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Followups> fetchByMobile(String contact) {
		String query = "select * from followups as f where f.contact like :mobileNumber ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("mobileNumber", contact).getResultList();
			if(list.isEmpty()){
				return null;
			}else {
				return list;
			}		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Followups> fetchByMobile(HttpSession session, String contact) {
		long partner_id = (long) session.getAttribute("partner_id");
		if(partner_id==1) {
			String query = "select * from followups as f where f.contact like :mobileNumber ";
			try {
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
						.setParameter("mobileNumber", contact).getResultList();
				if(list.isEmpty()){
					return null;
				}else {
					return list;
				}		} catch (Exception e) {
				return null;
			}
		}else {
			String query = "select * from followups as f where f.contact like :mobileNumber and f.partner_id=:pid";
			try {
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
						.setParameter("mobileNumber", contact).setParameter("pid", partner_id).getResultList();
				if(list.isEmpty()){
					return null;
				}else {
					return list;
				}		} catch (Exception e) {
				return null;
			}
		}
		
	}
	
	

	public List<Followups> fetchByEmails(String email) {
		
		String query = "select * from followups as f where f.email like :mail ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("mail","%"+email+"%").getResultList();
			if(list.isEmpty()){
				return null;
			}else {
				return list;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	
public List<Followups> fetchByEmails(HttpSession session , String email) {
	long partner_id = (long) session.getAttribute("partner_id");
	if(partner_id==1) {
		String query = "select * from followups as f where f.email like :mail ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("mail","%"+email+"%").getResultList();
			if(list.isEmpty()){
				return null;
			}else {
				return list;
			}
		} catch (Exception e) {
			return null;
		}
	}else {
		String query = "select * from followups as f where f.email like :mail and f.partner_id=:pid ";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("mail","%"+email+"%").setParameter("pid",partner_id).getResultList();
			if(list.isEmpty()){
				return null;
			}else {
				return list;
			}
		} catch (Exception e) {
			return null;
		}
	}
		
	}
	
//select * from followups as f where f.contact like :mobileNumber
	public List<Followups> fetchByEmailsAndContact(String contact, String email) {
		String query = "select * from followups as f where f.email like :mail and f.contact like :cntct";
		try {
			List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Followups.class)
					.setParameter("mail","%"+email+"%").setParameter("cntct", contact).getResultList();
			if(list.isEmpty()){
				return null;
			}else {
				return list;
			}		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Followups> fetchAll(HttpSession session) {
		long partner_id = (long) session.getAttribute("partner_id");
		if(partner_id==1) {
			try {
				Long pid = (Long) session.getAttribute("partner_id");
				System.out.println(pid);
				String query = "SELECT * FROM followups";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query, Followups.class).setParameter("pid", pid).list();
				System.out.println(list);
				if(list.isEmpty()){
					return null;
				}else {
					return list;
				}		 
			}catch(Exception e) {
				return null;

			}
		}else {
			try {
				Long pid = (Long) session.getAttribute("partner_id");
				System.out.println(pid);
				String query = "SELECT * FROM followups WHERE partner_id = :pid";
				List<Followups> list = sessionRef.getCurrentSession().createNativeQuery(query, Followups.class).setParameter("pid", pid).list();
				System.out.println(list);
				if(list.isEmpty()){
					return null;
				}else {
					return list;
				}		 
			}catch(Exception e) {
				return null;

			}
		}
	
	}

	@Override
	public List<Followups> findAllFollowups() {
		// TODO Auto-generated method stub
		return null;
	}

}
