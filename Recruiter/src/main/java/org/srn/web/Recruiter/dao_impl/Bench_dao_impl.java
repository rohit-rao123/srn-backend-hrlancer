package org.srn.web.Recruiter.dao_impl;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Bench_dao;
import org.srn.web.Recruiter.entity.Bench;

@Repository
@Transactional
@SuppressWarnings("unchecked")

public class Bench_dao_impl implements Bench_dao {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveBench(Bench bench) {
		// TODO Auto-generated method stub
		try {
			long new_bench_id = (long) sessionFactory.getCurrentSession().save(bench);
			System.out.println("New Bench created :" + new_bench_id);
			if (new_bench_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Bench> getByBenchId(HttpSession session, long bench_id) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.bench_id=:bnid order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("bnid", bench_id).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByClientId(HttpSession session, long client_id) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.client_id=:clid order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("clid", client_id).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByPartnerId(HttpSession session, long partner_id) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.partner_id=:partner_id order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("partner_id", partner_id).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByBenchName(HttpSession session, String name) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.name like :nme order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("nme", "%" + name + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByContact(HttpSession session, String contact) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.contact =:cntct order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("cntct", contact).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByEmail(HttpSession session, String email) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.email like :mail order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("mail", "%" + email + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByMinExp(HttpSession session, double min_exp) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.exp >=:minxp  order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("minxp", min_exp)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByMaxExp(HttpSession session, double max_exp) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where  b.exp <=:maxxp order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("maxxp", max_exp)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByDomain(HttpSession session, String domain) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.domain like :dmn order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("dmn", "%" + domain + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByBenchType(HttpSession session, String bench_type) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.bench_type like :btype order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("btype", "%" + bench_type + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getBypSkill(HttpSession session, String primary_skill) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.primary_skill like :pskill order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("pskill", "%" + primary_skill + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getBysSkill(HttpSession session, String secondary_skill) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.secondary_skill like :sSkill order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("sSkill", "%" + secondary_skill + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	public List<Bench> getByMinBudget(HttpSession session, double min_budget) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.budget >=:minbudget order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("minbudget", min_budget)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	public List<Bench> getByMaxBudget(HttpSession session, double max_budget) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.budget <=:maxbudget order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("maxbudget", max_budget)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	public List<Bench> getBySalary(HttpSession session, double salary) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.salary =:salary order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("salary", salary)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	public List<Bench> getByOrgName(HttpSession session, String org_name) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.org_name like :org_name order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("org_name", "%" + org_name + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByOrgUrl(HttpSession session, String org_url) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.org_url like :org_url order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("org_url", "%" + org_url + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByCurrentRole(HttpSession session, String current_role) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.current_role like :crole order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("crole", "%" + current_role + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByQualification(HttpSession session, String qualification) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.qualification like :qlf order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("qlf", "%" + qualification + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByLocation(HttpSession session, String location) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.location like :location order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("location", "%" + location + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByIs_shift_flexibility(HttpSession session, long is_shift_flexibility) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.is_shift_flexibility like :is_shift_flexibility order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("is_shift_flexibility", "%" + is_shift_flexibility + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Bench> getByWorkingMode(HttpSession session, String working_mode) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.working_mode like :working_mode order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("working_mode", "%" + working_mode + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	

	public List<Bench> getByResume(HttpSession session, String resume) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.resume like :rsme order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("rsme", "%" + resume + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByStartDt(HttpSession session, String bench_startDt) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.bench_start_dt like :startDt order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("startDt", bench_startDt).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByEndDt(HttpSession session, String bench_endDt) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.bench_end_dt like :endDt order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("endDt", bench_endDt).addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByBenchStatus(HttpSession session, String bench_status) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.bench_status like :bstatus order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("bstatus", "%" + bench_status + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByCreateBy(HttpSession session, String created_by) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.created_by like :cby order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("cby", "%" + created_by + "%").addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByDt(HttpSession session, String dt) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where cast(b.dt as date) =:dts order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("dts", dt)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getByStatus(HttpSession session, int status) {
		// TODO Auto-generated method stub
		String query = "select * from bench as b where b.status =:sts order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("sts", status)
					.addEntity(Bench.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Bench> getAllBenchs(HttpSession session) {
		// TODO Auto-generated method stub
		String query = "select * from bench order by bench_id desc";
		try {
			List<Bench> list = sessionFactory.getCurrentSession().createNativeQuery(query).addEntity(Bench.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
