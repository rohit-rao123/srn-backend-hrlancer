package org.srn.web.Recruiter.dao;

import java.util.List;

import org.srn.web.Recruiter.entity.Company;

public interface Company_dao  {

	public boolean newCompany(Company comp);

	public List<Company> findByCompanyName(String company_name);

	public boolean deleteCompanyById(long id);

	public List<Company> findByCompanyId(long company_id);

//	public List<Company> getCompanyByCompanyName(String company_name);

	public boolean saveCompany(Company comp);

	public boolean editById(long company_id, String company_name, String website, long minimum_employees,
			long maximum_employees, String location, String industry, String ranking, int status);

	public Company getCompanyByWebsite(String website);

	public List<Company> fetchCompanyByWebsite(String website);

	public List<Company> findLatestCompany();

	public Company findCompanyByName(String company_name);
	
	

	
}
