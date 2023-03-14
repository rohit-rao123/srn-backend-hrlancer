package org.srn.web.Recruiter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Workflow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long workflow_id;
	
	@Column(unique = false, nullable = false)
	private long job_id;
	
	@Column(unique = false, nullable = false)
	private long application_id;
	
	@Column(unique = false, nullable = false)
	private String recruiter_email;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date tech_interview_1_date;
	
	@Column(unique = false, nullable = false)
	public String tech_interviewer_1_name;
	
	@Column(unique = false, nullable = false)
	public String tech_interviewer_1_comment;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date tech_interview_2_date;
	
	@Column(unique = false, nullable = false)
	public String tech_interviewer_2_name;
	
	@Column(unique = false, nullable = false)
	public String tech_interviewer_2_comment;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date manager_interview_1_date;
	
	@Column(unique = false, nullable = false)
	public String manager_interviewer_1_name;
	
	@Column(unique = false, nullable = false)
	public String manager_interviewer_1_comment;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date hr_interview_1_date;
	
	@Column(unique = false, nullable = false)
	public String hr_interviewer_1_name;
	
	@Column(unique = false, nullable = false)
	public String hr_interviewer_1_comment;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date client_interview_1_date;
	
	@Column(unique = false, nullable = false)
	public String client_interviewer_1_name;
	
	@Column(unique = false, nullable = false)
	public String client_interviewer_1_comment;
	
	@Column(unique = false, nullable = false)
	public String offer_letter_id;
	
	@Column(unique = false, nullable = false)
	public double offer_amt;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date onbloading_dt;
	
	@Column(unique = false, nullable = false)
	public double joining_amt;
	
	@Column(unique = false, nullable = false)
	public String recuiter_stage;
	
	@Column(unique = false, nullable = false)
	public String recuiter_status;
	
	@Column(unique = false, nullable = false)
	private String created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date workflow_creation_date = new Date();	

	@Column(unique = false, nullable = false)
	private int status;

	public Workflow(long workflow_id, long job_id, long application_id, String recruiter_email,
			Date tech_interview_1_date, String tech_interviewer_1_name, String tech_interviewer_1_comment,
			Date tech_interview_2_date, String tech_interviewer_2_name, String tech_interviewer_2_comment,
			Date manager_interview_1_date, String manager_interviewer_1_name, String manager_interviewer_1_comment,
			Date hr_interview_1_date, String hr_interviewer_1_name, String hr_interviewer_1_comment,
			Date client_interview_1_date, String client_interviewer_1_name, String client_interviewer_1_comment,
			String offer_letter_id, double offer_amt, Date onbloading_dt, double joining_amt, String recuiter_stage,
			String recuiter_status, String created_by, Date workflow_creation_date, int status) {
		super();
		this.workflow_id = workflow_id;
		this.job_id = job_id;
		this.application_id = application_id;
		this.recruiter_email = recruiter_email;
		this.tech_interview_1_date = tech_interview_1_date;
		this.tech_interviewer_1_name = tech_interviewer_1_name;
		this.tech_interviewer_1_comment = tech_interviewer_1_comment;
		this.tech_interview_2_date = tech_interview_2_date;
		this.tech_interviewer_2_name = tech_interviewer_2_name;
		this.tech_interviewer_2_comment = tech_interviewer_2_comment;
		this.manager_interview_1_date = manager_interview_1_date;
		this.manager_interviewer_1_name = manager_interviewer_1_name;
		this.manager_interviewer_1_comment = manager_interviewer_1_comment;
		this.hr_interview_1_date = hr_interview_1_date;
		this.hr_interviewer_1_name = hr_interviewer_1_name;
		this.hr_interviewer_1_comment = hr_interviewer_1_comment;
		this.client_interview_1_date = client_interview_1_date;
		this.client_interviewer_1_name = client_interviewer_1_name;
		this.client_interviewer_1_comment = client_interviewer_1_comment;
		this.offer_letter_id = offer_letter_id;
		this.offer_amt = offer_amt;
		this.onbloading_dt = onbloading_dt;
		this.joining_amt = joining_amt;
		this.recuiter_stage = recuiter_stage;
		this.recuiter_status = recuiter_status;
		this.created_by = created_by;
		this.workflow_creation_date = workflow_creation_date;
		this.status = status;
	}

	public Workflow() {
		super();
	}

	public long getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(long workflow_id) {
		this.workflow_id = workflow_id;
	}

	public long getJob_id() {
		return job_id;
	}

	public void setJob_id(long job_id) {
		this.job_id = job_id;
	}

	public long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(long application_id) {
		this.application_id = application_id;
	}

	public String getRecruiter_email() {
		return recruiter_email;
	}

	public void setRecruiter_email(String recruiter_email) {
		this.recruiter_email = recruiter_email;
	}

	public Date getTech_interview_1_date() {
		return tech_interview_1_date;
	}

	public void setTech_interview_1_date(Date tech_interview_1_date) {
		this.tech_interview_1_date = tech_interview_1_date;
	}

	public String getTech_interviewer_1_name() {
		return tech_interviewer_1_name;
	}

	public void setTech_interviewer_1_name(String tech_interviewer_1_name) {
		this.tech_interviewer_1_name = tech_interviewer_1_name;
	}

	public String getTech_interviewer_1_comment() {
		return tech_interviewer_1_comment;
	}

	public void setTech_interviewer_1_comment(String tech_interviewer_1_comment) {
		this.tech_interviewer_1_comment = tech_interviewer_1_comment;
	}

	public Date getTech_interview_2_date() {
		return tech_interview_2_date;
	}

	public void setTech_interview_2_date(Date tech_interview_2_date) {
		this.tech_interview_2_date = tech_interview_2_date;
	}

	public String getTech_interviewer_2_name() {
		return tech_interviewer_2_name;
	}

	public void setTech_interviewer_2_name(String tech_interviewer_2_name) {
		this.tech_interviewer_2_name = tech_interviewer_2_name;
	}

	public String getTech_interviewer_2_comment() {
		return tech_interviewer_2_comment;
	}

	public void setTech_interviewer_2_comment(String tech_interviewer_2_comment) {
		this.tech_interviewer_2_comment = tech_interviewer_2_comment;
	}

	public Date getManager_interview_1_date() {
		return manager_interview_1_date;
	}

	public void setManager_interview_1_date(Date manager_interview_1_date) {
		this.manager_interview_1_date = manager_interview_1_date;
	}

	public String getManager_interviewer_1_name() {
		return manager_interviewer_1_name;
	}

	public void setManager_interviewer_1_name(String manager_interviewer_1_name) {
		this.manager_interviewer_1_name = manager_interviewer_1_name;
	}

	public String getManager_interviewer_1_comment() {
		return manager_interviewer_1_comment;
	}

	public void setManager_interviewer_1_comment(String manager_interviewer_1_comment) {
		this.manager_interviewer_1_comment = manager_interviewer_1_comment;
	}

	public Date getHr_interview_1_date() {
		return hr_interview_1_date;
	}

	public void setHr_interview_1_date(Date hr_interview_1_date) {
		this.hr_interview_1_date = hr_interview_1_date;
	}

	public String getHr_interviewer_1_name() {
		return hr_interviewer_1_name;
	}

	public void setHr_interviewer_1_name(String hr_interviewer_1_name) {
		this.hr_interviewer_1_name = hr_interviewer_1_name;
	}

	public String getHr_interviewer_1_comment() {
		return hr_interviewer_1_comment;
	}

	public void setHr_interviewer_1_comment(String hr_interviewer_1_comment) {
		this.hr_interviewer_1_comment = hr_interviewer_1_comment;
	}

	public Date getClient_interview_1_date() {
		return client_interview_1_date;
	}

	public void setClient_interview_1_date(Date client_interview_1_date) {
		this.client_interview_1_date = client_interview_1_date;
	}

	public String getClient_interviewer_1_name() {
		return client_interviewer_1_name;
	}

	public void setClient_interviewer_1_name(String client_interviewer_1_name) {
		this.client_interviewer_1_name = client_interviewer_1_name;
	}

	public String getClient_interviewer_1_comment() {
		return client_interviewer_1_comment;
	}

	public void setClient_interviewer_1_comment(String client_interviewer_1_comment) {
		this.client_interviewer_1_comment = client_interviewer_1_comment;
	}

	public String getOffer_letter_id() {
		return offer_letter_id;
	}

	public void setOffer_letter_id(String offer_letter_id) {
		this.offer_letter_id = offer_letter_id;
	}

	public double getOffer_amt() {
		return offer_amt;
	}

	public void setOffer_amt(double offer_amt) {
		this.offer_amt = offer_amt;
	}

	public Date getOnbloading_dt() {
		return onbloading_dt;
	}

	public void setOnbloading_dt(Date onbloading_dt) {
		this.onbloading_dt = onbloading_dt;
	}

	public double getJoining_amt() {
		return joining_amt;
	}

	public void setJoining_amt(double joining_amt) {
		this.joining_amt = joining_amt;
	}

	public String getRecuiter_stage() {
		return recuiter_stage;
	}

	public void setRecuiter_stage(String recuiter_stage) {
		this.recuiter_stage = recuiter_stage;
	}

	public String getRecuiter_status() {
		return recuiter_status;
	}

	public void setRecuiter_status(String recuiter_status) {
		this.recuiter_status = recuiter_status;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getWorkflow_creation_date() {
		return workflow_creation_date;
	}

	public void setWorkflow_creation_date(Date workflow_creation_date) {
		this.workflow_creation_date = workflow_creation_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "workflow [workflow_id=" + workflow_id + ", job_id=" + job_id + ", application_id=" + application_id
				+ ", recruiter_email=" + recruiter_email + ", tech_interview_1_date=" + tech_interview_1_date
				+ ", tech_interviewer_1_name=" + tech_interviewer_1_name + ", tech_interviewer_1_comment="
				+ tech_interviewer_1_comment + ", tech_interview_2_date=" + tech_interview_2_date
				+ ", tech_interviewer_2_name=" + tech_interviewer_2_name + ", tech_interviewer_2_comment="
				+ tech_interviewer_2_comment + ", manager_interview_1_date=" + manager_interview_1_date
				+ ", manager_interviewer_1_name=" + manager_interviewer_1_name + ", manager_interviewer_1_comment="
				+ manager_interviewer_1_comment + ", hr_interview_1_date=" + hr_interview_1_date
				+ ", hr_interviewer_1_name=" + hr_interviewer_1_name + ", hr_interviewer_1_comment="
				+ hr_interviewer_1_comment + ", client_interview_1_date=" + client_interview_1_date
				+ ", client_interviewer_1_name=" + client_interviewer_1_name + ", client_interviewer_1_comment="
				+ client_interviewer_1_comment + ", offer_letter_id=" + offer_letter_id + ", offer_amt=" + offer_amt
				+ ", onbloading_dt=" + onbloading_dt + ", joining_amt=" + joining_amt + ", recuiter_stage="
				+ recuiter_stage + ", recuiter_status=" + recuiter_status + ", created_by=" + created_by
				+ ", workflow_creation_date=" + workflow_creation_date + ", status=" + status + "]";
	}
	
	
}
