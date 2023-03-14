package org.srn.web.Recruiter.dao_impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Jobs_dao;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Jobs;
import org.srn.web.Recruiter.entity.Partners;

@Repository
@SuppressWarnings("unchecked")

public class Jobs_dao_impl implements Jobs_dao {

	@Autowired
	private SessionFactory sessionRef;

	@Override
	public List<Jobs> fetchById(HttpSession session, long id) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
			System.out.println(partner.toString());
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String query1 = "select * from jobs as j where j.job_id=:identity";
			String type = partner.getPartner_type().toString();
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("identity", id).list();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query1 = "select * from jobs as j where j.job_id=:identity and j.job_type =:pType";
			String type = partner.getPartner_type().toString();
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("identity", id).setParameter("pType", type).list();
				System.out.println(list);
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> fetchByPartnerType(String partnerType) {
		String query = "select * from jobs as j where j.job_type=:pType";
		try {
			List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
					.setParameter("pType", partnerType).list();
			System.out.println(list);
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Jobs getById(long id) {
		String query = "select * from jobs as j where j.job_id=:identity";
		try {
			Jobs job = (Jobs) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
					.setParameter("identity", id).getSingleResult();
			System.out.println(job);
			return job;
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public String getJobDescription(long jobId) {

		String query = "select * from jobs as j where j.job_id=:jId";
		try {
			Jobs job = (Jobs) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
					.setParameter("jId", jobId).getSingleResult();
			return job.getJd();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Jobs> findAllJobs(HttpSession session) {
		String partner_type = (String) session.getAttribute("partnerType");
		String query = "select  * from  jobs as j  where j.job_type like :pType";
		List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
				.setParameter("pType", partner_type).list();
		System.out.println(list);
		return list;
	}

	@Override
	public List<Jobs> findLatestJobs() {
		String query = "select  * from  jobs order by job_id desc limit 10 ";
		try {
			List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean saveJobs(Jobs job) {

		try {
			System.out.println(job.toString());
			long new_job_id = (long) sessionRef.getCurrentSession().save(job);
			System.out.println("New record created :" + new_job_id);
			if (new_job_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteJobsById(long id) {
		String query = "delete from jobs where jobs.job_id=:identity";
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
//	public boolean updateJobsById(long job_id,  String head_count,
//			String hired_count, String max_exp, String max_salary, String min_exp, String open,
//			String domain, int status) {
//
//		String query = "update Jobs as j set j.head_count=:hdcount,"
//				+ "j.hired_count=:hrcount,j.max_exp=:mexp,j.max_salary=:msal,j.min_exp=:minexp,j.open=:opn,j.domain=:skls,"
//				+ "j.status=:sts where j.job_id=:identity ";
//		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", job_id)
////				.setParameter("cl", client_id).setParameter("cby", created_by)
//				.setParameter("hdcount", head_count).setParameter("hrcount", hired_count).setParameter("mexp", max_exp)
//				.setParameter("msal", max_salary).setParameter("minexp", min_exp).setParameter("opn", open)
//				.setParameter("skls", domain).setParameter("sts", status).executeUpdate();
//
//		try {
//			if (x >= 1) {
//				System.out.println(x);
//				return true;
//			} else {
//				System.out.println(x);
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e);
//			return false;
//		}
//	}
//	
	@Override
	public boolean updateJobsId(long job_id, String head_count, String hired_count, String max_exp, String max_salary,
			String min_exp, String open, String domain, int status) {

		int x = 0;
		try {
			if (!head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.head_count=:hdcnt where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("hdcnt", head_count).executeUpdate();

			} else if (head_count.isEmpty() && !hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.hired_count=:hrcnt where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("hrcnt", hired_count).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && !max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.max_exp=:exp where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("exp", max_exp).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && !max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.max_salary=:salary where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("salary", max_salary).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& !min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.min_exp=:min where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("min", min_exp).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && !open.isEmpty() && domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.open=:op where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("op", open).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && !domain.isEmpty() && (status != 0 || status != 1)) {
				String query = "update jobs as j set j.domain=:main where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("main", domain).executeUpdate();

			} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
					&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty() && (status == 0 || status == 1)) {
				String query = "update jobs as j set j.status=:sts where j.job_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", job_id)
						.setParameter("sts", status).executeUpdate();

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
	public List<Jobs> fetchByName(String jobs_name) {
		String query = "select * from jobs as j where j.job_name like :name";
		try {
			List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
					.setParameter("name", "%" + jobs_name + "%").list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Jobs> fetchByCIdAndJobName(int client_id, String jobs_name) {
		String query = "select * from jobs as j where j.job_name like :name and j.client_id=:id";
		try {
			List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Jobs.class)
					.setParameter("name", "%" + jobs_name + "%").setParameter("id", client_id).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	// %"+jobs_name+"%
	// j.jobtype=:pType

	@Override
	public List<Jobs> findByClientId(HttpSession session, int clientId) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.client_id =:cid";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("cid", clientId).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.client_id =:cid and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("cid", clientId).setParameter("pType", partner_type).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByJobType(HttpSession session, String jobtype) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		String partner_type = partner.getPartner_type().toString();
		if (partner_type == "OFFSHORE_RECRUITMENT") {
			try {
				String query1 = "select * from jobs ";
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else if (partner_id == 1) {
			try {
				String query1 = "select * from jobs as j where j.job_type =:jtype";
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("jtype", jobtype).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				String query1 = "select * from jobs as j where j.job_type =:jtype and j.job_type=:pType";
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("jtype", jobtype).setParameter("pType", partner_type).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByDomain(HttpSession session, String domain) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.domain like :dom ";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("dom", domain).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.domain like :dom and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("dom", domain).setParameter("pType", partner_type).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findBySkill(HttpSession session, String skills) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
			System.out.println(partner.toString());
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.skills like :skl";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("skl", "%" + skills + "%").getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.skills like :skl and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("skl", "%" + skills + "%").setParameter("pType", partner_type).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByLocation(HttpSession session, String location) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.location like :locate";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("locate", "%" + location + "%").list();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.location like :locate and  j.job_type =pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("locate", "%" + location + "%").setParameter("pType", partner_type).list();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByMin_yr_exp(HttpSession session, double min_yr_exp) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.min_yr_exp>=:min_exp ";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("min_exp", min_yr_exp).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.min_yr_exp>=:min_exp and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("min_exp", min_yr_exp).setParameter("pType", partner_type).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByMax_yr_exp(HttpSession session, double max_yr_exp) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.max_yr_exp<=:max_exp ";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("max_exp", max_yr_exp).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.max_yr_exp<=:max_exp and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("max_exp", max_yr_exp).setParameter("pType", partner_type).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByMax_yr_budget(HttpSession session, double max_yr_budget) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.max_yr_budget <=:budget ";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("budget", max_yr_budget).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.max_yr_budget <=:budget and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("budget", max_yr_budget).setParameter("pType", partner_type).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByWorking_mode(HttpSession session, String working_mode) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.working_mode like :wMode ";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("wMode", "%" + working_mode + "%").getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.working_mode like :wMode  and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("wMode", "%" + working_mode + "%").setParameter("pType", partner_type)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Jobs> findByOpen(HttpSession session, int open) {
		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		String query = "select * from partners as p where p.partner_id=:pid ";
		try {
			partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.setParameter("pid", partner_id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
		if (partner_id == 1) {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.open=:op";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("op", open).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String partner_type = partner.getPartner_type().toString();
			String query1 = "select * from jobs as j where j.open=:op  and j.job_type =:pType";
			try {
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Jobs.class)
						.setParameter("op", open).setParameter("pType", partner_type).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	@Override
	public List<Jobs> fetchById(HttpServletRequest request, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Jobs> findAllJobs(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Jobs> findByClientId(HttpServletRequest request, int clientId) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public List<Jobs> fetchAll(HttpSession session) {

		long partner_id = (long) session.getAttribute("partner_id");
		Partners partner = null;
		if (partner_id == 1) {
			try {
				String query2 = "select * from jobs order by job_id desc";
				List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query2).addEntity(Jobs.class)
						.getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				List<Jobs> list = new ArrayList<Jobs>();
				return list;
			}
		} else {
			String query = "select * from partners as p where p.partner_id=:pid ";
			try {
				partner = (Partners) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
						.setParameter("pid", partner_id).getSingleResult();
				String partner_type = partner.getPartner_type().toString();
				if (partner_type == "OFFSHORE_RECRUITMENT") {
					String query2 = "select * from jobs as j order by j.job_id desc ";
					List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query2).addEntity(Jobs.class)
							.getResultList();
					System.out.println(list.toString());
					return list;
				} else {
					String query2 = "select * from jobs as j where j.job_type =:pType order by j.job_id desc";
					List<Jobs> list = sessionRef.getCurrentSession().createNativeQuery(query2).addEntity(Jobs.class)
							.setParameter("pType", partner_type).getResultList();
					System.out.println(list.toString());
					return list;
				}

			} catch (Exception e) {
				List<Jobs> list = new ArrayList<Jobs>();
				return list;
			}
		}

	}

	@Override
	public List<Jobs> findAll(HttpSession session) {
		long partner_id = (long) session.getAttribute("partner_id");
		System.out.println(partner_id);
		String query1 = "select * from applications as a where a.partner_id=:pid";
		List<Applications> appList = null;
		try {
			appList = sessionRef.getCurrentSession().createNativeQuery(query1).addEntity(Applications.class)
					.setParameter("pid", partner_id).getResultList();
			System.out.println(appList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			List<Jobs> jobList = new ArrayList<Jobs>();
			for (Applications app : appList) {
				long jid = app.getJob_id();
				String query2 = "select * from jobs as j where j.job_id=:identity";
				Jobs job = (Jobs) sessionRef.getCurrentSession().createNativeQuery(query2).addEntity(Jobs.class)
						.setParameter("identity", jid).getSingleResult();
				System.out.println(job);
				jobList.add(job);
			}
			return jobList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
