package org.srn.web.Recruiter.dao_impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Company_dao;
import org.srn.web.Recruiter.entity.Company;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class Company_dao_impl implements Company_dao {

	@Autowired
	private SessionFactory sessionRef;

	@Override
	public boolean newCompany(Company comp) {
		try {
			long id = (long) sessionRef.getCurrentSession().save(comp);

			if (id > 0) {
				System.out.println("Generated  id = " + id);
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public boolean saveCompany(Company comp) {
		try {
			long id = (long) sessionRef.getCurrentSession().save(comp);

			if (id > 0) {
				System.out.println("Generated  id = " + id);
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public List<Company> findByCompanyName(String company_name) {
		String query = "select * from company as c where c.company_name like :name";
		try {
			List<Company> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.setParameter("name", "%" + company_name + "%").list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Company findCompanyByName(String company_name) {
		String query = "select * from company as c where c.company_name like :name";
		try {
			Company company = (Company) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.setParameter("name", "%" + company_name + "%").getSingleResult();
			return company;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Company> findByCompanyId(long company_id) {
		String query = "select * from company as c where c.company_id=:identity";

		try {
			List<Company> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.setParameter("identity", company_id).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteCompanyById(long id) {
		String query = "delete from company where company.company_id =:identity";
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
	public boolean editById(long company_id, String company_name, String website, long minimum_employees,
			long maximum_employees, String location, String industry, String ranking, int status) {
		int x = 0;
		try {
			if (!company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& location.isBlank() && industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.company_name=:name where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("name", company_name).executeUpdate();
				System.out.println(x);

			} else if (company_name.isBlank() && !website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& location.isBlank() && industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.website=:site where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("site", website).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees != 0 && maximum_employees == 0
					&& location.isBlank() && industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.minimum_employees=:employee where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("employee", minimum_employees).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees != 0
					&& location.isBlank() && industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.maximum_employees=:employee where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("employee", maximum_employees).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& !location.isBlank() && industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.location=:lct where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("lct", location).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& location.isBlank() && !industry.isBlank() && ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.industry=:ind where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("ind", industry).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& location.isBlank() && industry.isBlank() && !ranking.isBlank() && (status != 0 || status != 1)) {
				String query = "update company as c set c.ranking=:rk where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
						.setParameter("rk", ranking).executeUpdate();

			} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
					&& location.isBlank() && industry.isBlank() && ranking.isBlank() && (status == 0 || status == 1)) {
				String query = "update company as c set c.status=:sts where c.company_id=:id";
				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", company_id)
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
	public Company getCompanyByWebsite(String website) {
		try {
			String query = "select * from company as c where c.website like :nm";
		     Company comp = (Company) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.setParameter("nm", "%" + website + "%").getSingleResult();
			return comp;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Company> findLatestCompany() {
		String query = "select  * from  company order by company_id desc limit 10 ";
		try {
			List<Company> list = sessionRef.getCurrentSession().createNativeQuery(query)
					.addEntity(Company.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Company> fetchCompanyByWebsite(String website) {
		try {
			String query = "select * from company as c where c.website like :nm";
		     List<Company> comp = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.setParameter("nm", "%" + website + "%").list();
			return comp;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Company> getAllCompany() {
		try {
			String query = "select * from company order by company_id desc ";
		     List<Company> comp = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Company.class)
					.getResultList();
			return comp;
		} catch (Exception e) {
			return null;
		}
	}

}
