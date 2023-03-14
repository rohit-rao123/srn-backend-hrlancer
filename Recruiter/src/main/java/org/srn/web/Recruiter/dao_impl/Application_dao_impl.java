package org.srn.web.Recruiter.dao_impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Application_dao;
import org.srn.web.Recruiter.entity.ApplicationProfile;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Followups;

@Repository
@SuppressWarnings("unchecked")

public class Application_dao_impl implements Application_dao {

	@Autowired
	private SessionFactory sessionRef;

	@Override
	public List<Applications> fetchById(long id) {
		String query = "select * from application as a where a.application_id=:identity";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Applications getByProfAndJobId(long profile_id, long job_id, String application_status) {
		String query = "select * from application as a where a.profile_id =:pid and a.job_id =:jid and a.application_status like :status";
		try {
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Followups.class).setParameter("pid", profile_id).setParameter("jid", job_id)
					.setParameter("status", "%" + application_status + "%").getSingleResult();
			return app;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Applications> getById(long id) {
		String query = "select * from application as a where a.application_id=:identity";
		try {
			List<Applications> app = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).list();
			return app;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Applications getAppById(long id) {
		String query = "select * from application as a where a.application_id=:identity";
		try {
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).getSingleResult();
			return app;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Applications fetchByContact(String contact) {
		System.out.println(contact);
		String query = "select * from application as a where a.contact=:cntct";
		try {
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("cntct", contact).getSingleResult();
			System.out.println(app);
			return app;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Applications> getByContact(String contact) {
		System.out.println(contact);
		String query = "select * from application as a where a.contact=:cntct";
		try {
			List<Applications> app = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("cntct", contact).list();
			System.out.println(app);
			return app;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Applications fetchByEmail(String email) {
		String query = "select * from Application as a where a.email=:mail";
		try {
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("mail", email).getSingleResult();
			return app;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Applications> getByEmail(String email) {
		String query = "select * from application as a where a.email=:mail";
		try {
			List<Applications> app = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("mail", email).list();
			return app;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ApplicationProfile> findAllApplication(HttpSession session) {
		long partners_id = (long) session.getAttribute("partner_id");
		if (partners_id == 1) {
			String query = "select  application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, "
					+ "cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id  order by application_id desc";
			try {
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(ApplicationProfile.class).list();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}else {
			String query = "select  application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, "
					+ "cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id where a.partner_id=:pid order by application_id desc";
//			String query = "select  * from  applications as a  where a.partner_id=:pid order by application_id desc";
			try {
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(ApplicationProfile.class).setParameter("pid", partners_id).list();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}
		
	}

	@Override
	public boolean saveApplication(Applications app) {
		try {
			long new_application_id = (long) sessionRef.getCurrentSession().save(app);
			System.out.println("New record created :" + new_application_id);
			if (new_application_id > 0) {
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
	public List<Applications> getByJidAndPId(long job_id, long profile_id) {
		try {
			String query = "select  * from  application as a where a.job_id=:id and a.profile_id=:identity";

			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("id", job_id).setParameter("identity", profile_id)
					.list();
			System.out.println(list);
			if (list.isEmpty() || list == null) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean deleteApplicationById(long id) {
		String query = "delete from application  where application.application_id=:identity";
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

	@Override
	public boolean updateApplicationById(long application_id, String comment, int status) {
		String query = "update application as a "
				+ "set a.status=:st , a.cmnt=:comment where a.application_id=:identity";
		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", application_id)
				.setParameter("cmnt", comment).setParameter("st", status).executeUpdate();

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
	public List<Applications> fetchByPId(long id) {
		String query = "select * from application as a where a.profile_id=:identity";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Applications> getByPId(long id) {
		String y = "closed";
		System.out.println(id);
		String query = "select * from application as a where a.profile_id=:identity and a.application_status!=:sts";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).setParameter("sts", y).list();
			System.out.println("application" + list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Applications findByPId(long id) {
		String y = "closed";
		System.out.println(id);
		String query = "select * from application as a where a.profile_id=:identity and a.application_status=:sts";
		try {
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("identity", id).setParameter("sts", y)
					.getSingleResult();
			System.out.println(app);
			return app;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Applications> fetchByDomain(String domain) {
		String query = "select * from application as a where a.domain like :skill ";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("skill", "%" + domain + "%").getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean updateApp(long application_id, int client_id, long job_id, double rel_exp, String comment,
			String application_status, int is_ectc_nego) {

		int x = 0;
		try {
			if (client_id != 0 && job_id == 0 && rel_exp == 0 && comment.isBlank() && application_status.isBlank()
					&& is_ectc_nego == 0) {
				String query = "update application as a set a.client_id=:cid where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("cid", client_id).executeUpdate();

			} else if (client_id == 0 && job_id != 0 && rel_exp == 0 && comment.isBlank()
					&& application_status.isBlank() && is_ectc_nego == 0) {
				String query = "update application as a set a.job_id=:jid where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("jid", job_id).executeUpdate();

			} else if (client_id == 0 && job_id == 0 && rel_exp != 0 && comment.isBlank()
					&& application_status.isBlank() && is_ectc_nego == 0) {
				String query = "update application as a set a.rel_exp=:rexp where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("rexp", rel_exp).executeUpdate();

			} else if (client_id == 0 && job_id == 0 && rel_exp == 0 && !comment.isBlank()
					&& application_status.isBlank() && is_ectc_nego == 0) {
				String query = "update application as a set a.comment=:cmnt where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("cmnt", comment).executeUpdate();

			} else if (client_id == 0 && job_id == 0 && rel_exp == 0 && comment.isBlank()
					&& !application_status.isBlank() && is_ectc_nego == 0) {
				String query = "update application as a set a.application_status=:sts where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("sts", application_status).executeUpdate();

			} else if (client_id == 0 && job_id == 0 && rel_exp == 0 && comment.isBlank()
					&& application_status.isBlank() && is_ectc_nego != 0) {
				String query = "update application as a set a.is_ectc_nego=:ectc where a.application_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", application_id)
						.setParameter("ectc", is_ectc_nego).executeUpdate();

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
	public List<Applications> getByJidAndEmail(long job_id, String email) {
		try {
			String query = "select  * from  application as a where a.job_id=:id and a.email=:mail";

			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("id", job_id).setParameter("mail", email).list();
			System.out.println(list);
			if (list.isEmpty() || list == null) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Applications> getByJobAndProfile(long job_id, long profile_id) {

		try {
			String query = "select  * from  application as a where a.job_id=:id and a.profile_id=:pid";

			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("id", job_id).setParameter("pid", profile_id).list();
			System.out.println(list);
			if (list.isEmpty() || list == null) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ApplicationProfile> findByMobile(HttpSession session, String contact) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			try {
				String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
						+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id  where p.contact like :cntct";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.setParameter("cntct", "%" + contact + "%").addEntity(ApplicationProfile.class).getResultList();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
						+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id  where p.contact like :cntct and p.partner_id=:pid";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.setParameter("cntct", "%" + contact + "%").setParameter("pid", partner_id)
						.addEntity(ApplicationProfile.class).getResultList();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	@Override
	public List<ApplicationProfile> findByEmail(HttpSession session, String email) {
		long partner_id = (long) session.getAttribute("partner_id");

		if (partner_id == 1) {
			try {
				String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
						+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id  where p.email like :mail";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.setParameter("mail", "%" + email + "%").addEntity(ApplicationProfile.class).getResultList();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				String query = "select *  from applications as a inner join profile as p on p.profile_id=a.profile_id  where p.email like :mail and p.partner_id=:pid";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.setParameter("mail", "%" + email + "%").setParameter("pid", partner_id)
						.addEntity(ApplicationProfile.class).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;

			}
		}

	}

	@Override
	public List<ApplicationProfile> findByApplicationStatus(HttpSession session, String application_status) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			try {
				String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
						+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id   where a.progress like :sts";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.setParameter("sts", "%"+application_status+"%").addEntity(ApplicationProfile.class).getResultList();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				return null;
			}
		} 
		else {
		try {
			String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
					+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id   where a.progress like :sts and a.partner_id=:pid";
			List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(ApplicationProfile.class).setParameter("sts", "%"+application_status+"%")
					.setParameter("pid", partner_id).getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		}
	}

	@Override
	public List<ApplicationProfile> findByDate(HttpSession session, String app_creation_date) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			try {
				String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
						+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id where cast(a.dt as date)=:dt";
				List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
						.addEntity(ApplicationProfile.class).setParameter("dt", app_creation_date).getResultList();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
		System.out.println(app_creation_date);
		try {
			String query = "select application_id,name,contact,email, client_id, job_id, a.partner_id, a.profile_id, rel_exp, a.ectc, is_ectc_nego, notice, \"\r\n"
					+ "					+ \"cv_path, progress, comment, a.created_by, dt, a.status from  applications as a inner join profile as p on a.profile_id=p.profile_id where cast(a.dt as date)=:dt  and a.partner_id=:pid";
			List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(ApplicationProfile.class).setParameter("dt", app_creation_date).setParameter("pid", partner_id)
					.getResultList();
			System.out.println(list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}}
	}

	@Override
	public Applications getByAppIdAndStatus(long application_id, String status) {
		try {
			String query = "select *  from applications as a where a.application_id=:id and a.application_status like :sts";
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("id", application_id)
					.setParameter("sts", "%" + status + "%").getSingleResult();
			return app;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public List<Applications> findLatestApplication() {
		String query = "select  * from  applications order by application_id desc limit 10 ";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean updateEctc(long application_id, double ectc) {
		String query = "update applications as a set a.ectc=:ec where application_id =:app_id";

		try {
			long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("ec", ectc)
					.setParameter("app_id", application_id).executeUpdate();
			if (x >= 1) {
				System.out.println(x);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateNotice(long application_id, int notice) {
		String query = "update applications as a set a.notice=:ntc where application_id =:app_id";

		try {
			long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("app_id", application_id)
					.setParameter("ntc", notice).executeUpdate();
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

	public boolean updateProgress(long application_id, String progress) {
		String query = "update applications as a set a.progress=:prog where application_id =:app_id";

		try {
			long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("app_id", application_id)
					.setParameter("prog", progress).executeUpdate();
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

	public Applications fetchByApplication(long application_id) {
		try {
			String query = "select *  from applications as a   where a.application_id=:app_id";
			Applications app = (Applications) sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Applications.class).setParameter("app_id", application_id).getSingleResult();
			System.out.println(app.toString());
			return app;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ApplicationProfile> findAll(HttpSession session) {
		// TODO Auto-generated method stub

		Long partnerId = (Long) session.getAttribute("partner_id");
		String query = "SELECT p.name, p.contact, p.email, a.* " + "FROM applications AS a "
				+ "inner JOIN profile AS p ON a.partner_id = p.partner_id " + "WHERE a.partner_id = :pid";

		List<ApplicationProfile> list = sessionRef.getCurrentSession().createNativeQuery(query)
				.addEntity(ApplicationProfile.class).setParameter("pid", partnerId).list();
		return list;

	}

	@Override
	public List<Applications> findAllApplication() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Applications> fetchByJob_idAndProfile_id(long job_id, long profile_id) {
		String query = "select * from applications as a where a.profile_id=:pid and a.job_id=:jid";

		List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Applications.class)
				.setParameter("pid", profile_id).setParameter("jid", job_id).getResultList();
		return list;
	}

	public List<Applications> findByMobile(String contact) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Applications> findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Applications> findByMbl(HttpSession session, String contact) {
		String query = "select  * from  applications as a inner join profile as p on a.profile_id=p.profile_id where p.contact like :cntct order by a.application_id desc ";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.setParameter("cntct", "%"+contact+"%").addEntity(Applications.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Applications> findEmail(HttpSession session, String email) {
		// TODO Auto-generated method stub
		String query = "select  * from  applications as a inner join profile as p on a.profile_id=p.profile_id where p.email like :mail order by a.application_id desc ";
		try {
			List<Applications> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.setParameter("mail", "%"+email+"%").addEntity(Applications.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}	}

}