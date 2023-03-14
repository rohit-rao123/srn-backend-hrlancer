package org.srn.web.Recruiter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bench")
public class Bench implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bench_id;
	
	@Column(unique = false, nullable = false)
	private long partner_id;
	
	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = false, nullable = false)
	private String contact;

	@Column(unique = false, nullable = false)
	private String alternate_contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = false)
	private String alternate_email;

	@Column(unique = false, nullable = false)
	private Double exp;

	@Column(unique = false, nullable = false)
	private String domain;

	@Column(unique = false, nullable = false)
	private String bench_type;

	@Column(unique = false, nullable = false)
	private String primary_skill;

	@Column(unique = false, nullable = false)
	private String secondary_skill;

	@Column(unique = false, nullable = false)
	private double budget;
	
	@Column(unique = false, nullable = false)
	private double salary;
	
	@Column(unique = false, nullable = false)
	private String org_name;
	
	@Column(unique = false, nullable = false)
	private String org_url;
	
	@Column(unique = false, nullable = false)
	private String current_role;

	@Column(unique = false, nullable = false)
	private String qualification;
	
	@Column(unique = false, nullable = false)
	private String created_by;
	
	@Column(unique = false, nullable = false)
	private String resume;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date dt;

	@Column(unique = false, nullable = false)
	private int status;


	public Bench() {
		super();
	}

	
	
	public Bench(Long bench_id, long partner_id, String name, String contact, String alternate_contact, String email,
			String alternate_email, Double exp, String domain, String bench_type, String primary_skill,
			String secondary_skill, double budget, double salary, String org_name, String org_url, String current_role,
			String qualification, String created_by,String resume, Date dt, int status) {
		super();
		this.bench_id = bench_id;
		this.partner_id = partner_id;
		this.name = name;
		this.contact = contact;
		this.alternate_contact = alternate_contact;
		this.email = email;
		this.alternate_email = alternate_email;
		this.exp = exp;
		this.domain = domain;
		this.bench_type = bench_type;
		this.primary_skill = primary_skill;
		this.secondary_skill = secondary_skill;
		this.budget = budget;
		this.salary = salary;
		this.org_name = org_name;
		this.org_url = org_url;
		this.current_role = current_role;
		this.qualification = qualification;
		this.created_by = created_by;
		this.resume=resume;
		this.status = status;
	}



	public Long getBench_id() {
		return bench_id;
	}

	public void setBench_id(Long bench_id) {
		this.bench_id = bench_id;
	}

	
	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAlternate_contact() {
		return alternate_contact;
	}

	public void setAlternate_contact(String alternate_contact) {
		this.alternate_contact = alternate_contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternate_email() {
		return alternate_email;
	}

	public void setAlternate_email(String alternate_email) {
		this.alternate_email = alternate_email;
	}

	public Double getExp() {
		return exp;
	}

	public void setExp(Double exp) {
		this.exp = exp;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBench_type() {
		return bench_type;
	}

	public void setBench_type(String bench_type) {
		this.bench_type = bench_type;
	}

	public String getPrimary_skill() {
		return primary_skill;
	}

	public void setPrimary_skill(String primary_skill) {
		this.primary_skill = primary_skill;
	}

	public String getSecondary_skill() {
		return secondary_skill;
	}

	public void setSecondary_skill(String secondary_skill) {
		this.secondary_skill = secondary_skill;
	}

	public String getCurrent_role() {
		return current_role;
	}

	public void setCurrent_role(String current_role) {
		this.current_role = current_role;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_url() {
		return org_url;
	}

	public void setOrg_url(String org_url) {
		this.org_url = org_url;
	}

	

	
	public String getCreated_by() {
		return created_by;
	}



	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	


	public String getResume() {
		return resume;
	}



	public void setResume(String resume) {
		this.resume = resume;
	}



	public Date getDt() {
		return dt;
	}



	public void setDt(Date dt) {
		this.dt = dt;
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
		return "Bench [bench_id=" + bench_id + ", partner_id=" + partner_id + ", name=" + name + ", contact=" + contact
				+ ", alternate_contact=" + alternate_contact + ", email=" + email + ", alternate_email="
				+ alternate_email + ", exp=" + exp + ", domain=" + domain + ", bench_type=" + bench_type
				+ ", primary_skill=" + primary_skill + ", secondary_skill=" + secondary_skill + ", budget=" + budget
				+ ", salary=" + salary + ", org_name=" + org_name + ", org_url=" + org_url + ", current_role="
				+ current_role + ", qualification=" + qualification + ", created_by=" + created_by + ", resume="
				+ resume + ", dt=" + dt + ", status=" + status + "]";
	}



	

	

	
}
