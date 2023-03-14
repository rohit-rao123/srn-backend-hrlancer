package org.srn.web.Recruiter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;


//application_id	profile_id	applied_for	app_creation_date	name	contact	alternate_contact	
//email	alternate_email	exp	ctc	ectc	np	company	current_role	qualification	
//year_of_passing	skills	client_id	job_id	location	active	status


@Entity
@Table(name="applications")
public class Applications implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long application_id;

	@Column(unique = false, nullable = false)
	private int client_id;

	@Column(unique = false, nullable = false)
	private long job_id;

	@Column(unique = false, nullable = false)
	private long profile_id;

	@Column(unique = false, nullable = false)
	private double ectc;

	@Column(unique = false, nullable = false)
	private double is_ectc_nego;

	@Column(unique = false, nullable = false)
	private double rel_exp;

	@Column(unique = false, nullable = true, columnDefinition = "longtext")
	private String comment;

	@Column(unique = false, nullable = false)
	private String created_by;

	@Column(unique = false, nullable = false)
	private int status;

	@Column(unique = false, nullable = false)
	private int notice;

	@Column(unique = false, nullable = false)
	private String progress;

	@Column(unique = false, nullable = false)
	private String cv_path;

	@Column(unique = false, nullable = false)
	private long partner_id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dt = new Date();

	public Applications() {
	}

	public Applications(long application_id, int client_id, long job_id, long profile_id, Date dt, double ectc,
			double is_ectc_nego, double rel_exp, String comment, String progress, String cv_path, long partner_id,
			String created_by, int status, int notice) {
		this.application_id = application_id;
		this.client_id = client_id;
		this.job_id = job_id;
		this.profile_id = profile_id;
		this.dt = dt;
		this.ectc = ectc;
		this.is_ectc_nego = is_ectc_nego;
		this.rel_exp = rel_exp;
		this.comment = comment;
		this.progress = progress;
		this.cv_path = cv_path;
		this.partner_id = partner_id;
		this.created_by = created_by;
		this.status = status;
		this.notice = notice;
	}

	public long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(long application_id) {
		this.application_id = application_id;
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

	public long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(long profile_id) {
		this.profile_id = profile_id;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public double getEctc() {
		return ectc;
	}

	public void setEctc(double ectc) {
		this.ectc = ectc;
	}

	public double getIs_ectc_nego() {
		return is_ectc_nego;
	}

	public void setIs_ectc_nego(double is_ectc_nego) {
		this.is_ectc_nego = is_ectc_nego;
	}

	public double getRel_exp() {
		return rel_exp;
	}

	public void setRel_exp(double rel_exp) {
		this.rel_exp = rel_exp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getCv_path() {
		return cv_path;
	}

	public void setCv_path(String cv_path) {
		this.cv_path = cv_path;
	}

	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Applications [application_id=" + application_id + ", client_id=" + client_id + ", job_id=" + job_id
				+ ", profile_id=" + profile_id + ", dt=" + dt + ", ectc=" + ectc + ", is_ectc_nego=" + is_ectc_nego
				+ ", rel_exp=" + rel_exp + ", comment=" + comment + ", progress=" + progress + ", cv_path=" + cv_path
				+ ", partner_id=" + partner_id + ", created_by=" + created_by + ", status=" + status + ", notice="
				+ notice + "]";
	}
	
}
