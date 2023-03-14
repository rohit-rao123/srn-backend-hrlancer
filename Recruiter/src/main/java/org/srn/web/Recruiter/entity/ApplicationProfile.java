
package org.srn.web.Recruiter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ApplicationProfile {


	@Id
	@Column(unique = true, nullable = false)
	private long application_id;

	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = false)
	private int client_id;

	@Column(unique = false, nullable = false)
	private long job_id;
	
	@Column(unique = false, nullable = false)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private long profile_id;
	
	@Column(unique = false, nullable = false)
	private double rel_exp;

	@Column(unique = false, nullable = false)
	private long ectc;

	@Column(unique = false, nullable = false)
	private int is_ectc_nego;
	
	@Column(unique = false, nullable = false)
	private int notice;
	
	@Column(unique = false, nullable = false)
	private String cv_path;

	@Column(unique = false, nullable = false)
	private String progress;

	@Column(unique = false, nullable = true, columnDefinition = "longtext")
	private String comment;

	@Column(unique = false, nullable = false)
	private String created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dt = new Date();

	@Column(unique = false, nullable = false)
	private int status;

	public ApplicationProfile() {
		super();
	}

	public ApplicationProfile(long application_id, String name, String contact, String email, int client_id,
			long job_id, long partner_id, long profile_id, double rel_exp, long ectc, int is_ectc_nego, int notice,
			String cv_path, String progress, String comment, String created_by, Date dt, int status) {
		super();
		this.application_id = application_id;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.client_id = client_id;
		this.job_id = job_id;
		this.partner_id = partner_id;
		this.profile_id = profile_id;
		this.rel_exp = rel_exp;
		this.ectc = ectc;
		this.is_ectc_nego = is_ectc_nego;
		this.notice = notice;
		this.cv_path = cv_path;
		this.progress = progress;
		this.comment = comment;
		this.created_by = created_by;
		this.dt = dt;
		this.status = status;
	}

	public long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(long application_id) {
		this.application_id = application_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public long getJob_id() {
		return job_id;
	}

	public void setJob_id(long job_id) {
		this.job_id = job_id;
	}

	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}

	public long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(long profile_id) {
		this.profile_id = profile_id;
	}

	public double getRel_exp() {
		return rel_exp;
	}

	public void setRel_exp(double rel_exp) {
		this.rel_exp = rel_exp;
	}

	public long getEctc() {
		return ectc;
	}

	public void setEctc(long ectc) {
		this.ectc = ectc;
	}

	public int getIs_ectc_nego() {
		return is_ectc_nego;
	}

	public void setIs_ectc_nego(int is_ectc_nego) {
		this.is_ectc_nego = is_ectc_nego;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	public String getCv_path() {
		return cv_path;
	}

	public void setCv_path(String cv_path) {
		this.cv_path = cv_path;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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

	@Override
	public String toString() {
		return "ApplicationProfile [application_id=" + application_id + ", name=" + name + ", contact=" + contact
				+ ", email=" + email + ", client_id=" + client_id + ", job_id=" + job_id + ", partner_id=" + partner_id
				+ ", profile_id=" + profile_id + ", rel_exp=" + rel_exp + ", ectc=" + ectc + ", is_ectc_nego="
				+ is_ectc_nego + ", notice=" + notice + ", cv_path=" + cv_path + ", progress=" + progress + ", comment="
				+ comment + ", created_by=" + created_by + ", dt=" + dt + ", status=" + status + "]";
	}

	



	
	

	
}
