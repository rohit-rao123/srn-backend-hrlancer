package org.srn.web.Recruiter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="company")
public class Company implements Serializable{

//	Company Name	Industry	Location	Minimum Employees	Maximum Employees	Ranking	website

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long company_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date company_creation_date = new Date();
	
	@Column(unique = false, nullable = false)
	private String company_name;
	
	@Column(unique = false, nullable = true, length = 8192 ,columnDefinition = "longtext")
	private String industry;
	
	@Column(unique = false, nullable = true, columnDefinition = "text")
	private String location;
	
	@Column(unique = false, nullable = false)
	private long minimum_employees;
	
	@Column(unique = false, nullable = false)
	private long maximum_employees;

	@Column(unique = true, nullable = false)
	private String website;
	
	@Column(unique = false, nullable = false)
	private String created_by;
	
	@Column(unique = false, nullable = false)
	private String ranking;
	
	@Column(unique = false, nullable = false)
	private int status;

	public Company(long company_id, Date company_creation_date, String company_name, String industry, String location,
			long minimum_employees, long maximum_employees, String website, String created_by, String ranking,
			int status) {
		super();
		this.company_id = company_id;
		this.company_creation_date = company_creation_date;
		this.company_name = company_name;
		this.industry = industry;
		this.location = location;
		this.minimum_employees = minimum_employees;
		this.maximum_employees = maximum_employees;
		this.website = website;
		this.created_by = created_by;
		this.ranking = ranking;
		this.status = status;
	}

	public Company() {
		super();
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public Date getCompany_creation_date() {
		return company_creation_date;
	}

	public void setCompany_creation_date(Date company_creation_date) {
		this.company_creation_date = company_creation_date;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getMinimum_employees() {
		return minimum_employees;
	}

	public void setMinimum_employees(long minimum_employees) {
		this.minimum_employees = minimum_employees;
	}

	public long getMaximum_employees() {
		return maximum_employees;
	}

	public void setMaximum_employees(long maximum_employees) {
		this.maximum_employees = maximum_employees;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", company_creation_date=" + company_creation_date
				+ ", company_name=" + company_name + ", industry=" + industry + ", location=" + location
				+ ", minimum_employees=" + minimum_employees + ", maximum_employees=" + maximum_employees + ", website="
				+ website + ", created_by=" + created_by + ", ranking=" + ranking + ", status=" + status + "]";
	}

	
}
