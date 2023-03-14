package org.srn.web.Recruiter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="jobs")
public class Jobs implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( nullable = false)
	private long job_id;
	
	@Column(unique = false, nullable = false)
	private int client_id;
	
	@Column(unique = false, nullable = false)
	private String domain;
	
	@Column( unique = false, nullable = false)
	private String job_role;
	
	@Column( unique = false, nullable = false, columnDefinition = "longText")
	private String skills;
	
	@Column( unique = false, nullable = false, columnDefinition = "longText")
	private String jd;
	
	@Column( unique = false, nullable = false)
	private double min_yr_exp;
	
	@Column( unique = false, nullable = false)
	private double max_yr_exp;
	
	@Column(unique = false, nullable = false)
	private double max_yr_budget;
	
	@Column( unique = false, nullable = false)
	private String qualification;
	
	@Column( unique = false, nullable = false)
	private String location ;
	
//	public enum ClientInvoiceStatus {
//		RAISED, AWAITED, IN_DISCUSSION, DISPUTED, PAID, IN_FOLLOWUP
//	}
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "client_invoice_status", columnDefinition = "enum('RAISED', 'AWAITED', 'IN_DISCUSSION', 'DISPUTED', 'PAID', 'IN_FOLLOWUP')", nullable = false)
//	private ClientInvoiceStatus client_invoice_status; 
	
	public enum JobType{
		OFFSHORE, RECRUITMENT, OFFSHORE_RECRUITMENT;
		
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name ="job_type",columnDefinition = "ENUM('OFFSHORE', 'RECRUITMENT', 'OFFSHORE_RECRUITMENT')", nullable = false)
	private JobType job_type;	
	public enum WorkingMode{
		WFH, WFO, REMOTE, HYBRID	
		}
	
	@Enumerated(EnumType.STRING)
	@Column( name ="working_mode",columnDefinition ="ENUM('WFH', 'WFO', 'REMOTE', 'HYBRID')", nullable = false)
	private WorkingMode working_mode;
	
	@Column(unique = false, nullable = false)
	private int head_count;

	@Column(unique = false, nullable = false)
	private int hired_count;

	@Column(unique = false, nullable = false)
	private int open;
	
	@Column(unique = false, nullable = false)
	private String created_by;
	
	@Column(unique = false, nullable = false)
	private int notice;
	
	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dt = new Date();
	
	@Column(unique = false, nullable = false)
	private int status;

	public Jobs() {
		super();
	}

	public Jobs(long job_id, int client_id, String domain, String job_role, String skills, String jd, double min_yr_exp,
			double max_yr_exp, double max_yr_budget, String qualification, String location, JobType job_type,
			WorkingMode working_mode, int head_count, int hired_count, int open, String created_by, int notice, Date dt,
			int status) {
		super();
		this.job_id = job_id;
		this.client_id = client_id;
		this.domain = domain;
		this.job_role = job_role;
		this.skills = skills;
		this.jd = jd;
		this.min_yr_exp = min_yr_exp;
		this.max_yr_exp = max_yr_exp;
		this.max_yr_budget = max_yr_budget;
		this.qualification = qualification;
		this.location = location;
		this.job_type = job_type;
		this.working_mode = working_mode;
		this.head_count = head_count;
		this.hired_count = hired_count;
		this.open = open;
		this.created_by = created_by;
		this.notice = notice;
		this.dt = dt;
		this.status = status;
	}

	public long getJob_id() {
		return job_id;
	}

	public void setJob_id(long job_id) {
		this.job_id = job_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getJob_role() {
		return job_role;
	}

	public void setJob_role(String job_role) {
		this.job_role = job_role;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public double getMin_yr_exp() {
		return min_yr_exp;
	}

	public void setMin_yr_exp(double min_yr_exp) {
		this.min_yr_exp = min_yr_exp;
	}

	public double getMax_yr_exp() {
		return max_yr_exp;
	}

	public void setMax_yr_exp(double max_yr_exp) {
		this.max_yr_exp = max_yr_exp;
	}

	public double getMax_yr_budget() {
		return max_yr_budget;
	}

	public void setMax_yr_budget(double max_yr_budget) {
		this.max_yr_budget = max_yr_budget;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public JobType getJob_type() {
		return job_type;
	}

	public void setJob_type(JobType job_type) {
		this.job_type = job_type;
	}

	public WorkingMode getWorking_mode() {
		return working_mode;
	}

	public void setWorking_mode(WorkingMode working_mode) {
		this.working_mode = working_mode;
	}

	public int getHead_count() {
		return head_count;
	}

	public void setHead_count(int head_count) {
		this.head_count = head_count;
	}

	public int getHired_count() {
		return hired_count;
	}

	public void setHired_count(int hired_count) {
		this.hired_count = hired_count;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
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
		return "Jobs [job_id=" + job_id + ", client_id=" + client_id + ", domain=" + domain + ", job_role=" + job_role
				+ ", skills=" + skills + ", jd=" + jd + ", min_yr_exp=" + min_yr_exp + ", max_yr_exp=" + max_yr_exp
				+ ", max_yr_budget=" + max_yr_budget + ", qualification=" + qualification + ", location=" + location
				+ ", job_type=" + job_type + ", working_mode=" + working_mode + ", head_count=" + head_count
				+ ", hired_count=" + hired_count + ", open=" + open + ", created_by=" + created_by + ", notice="
				+ notice + ", dt=" + dt + ", status=" + status + "]";
	}
	
	

	

	
	
	
	
}

