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

@Entity
@Table(name="followups")
public class Followups implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long followups_id;

	@Column(unique = false, nullable = false)
	private int client_id;

	//partner_id
	@Column(unique = false, nullable = false)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private long application_id;

	@Column(unique = false, nullable = false)
	private long profile_id;

	@Column(unique = false, nullable = false)
	private long job_id;

	@Column(unique = false, nullable = false)
	private String contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = true, length = 8192, columnDefinition = "longtext")
	private String comment;

	@Column(unique = false, nullable = false)
	private String followup_status;

	@Column(unique = false, nullable = false)
	private String created_by;

	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date followup_creation_date = new Date();

	@Column(unique = false, nullable = false)
	private int status;

	public Followups() {
		super();
	}

	public Followups(long followups_id, int client_id, long partner_id, long application_id, long profile_id,
			long job_id, String contact, String email, String comment, String followup_status, String created_by,
			Date followup_creation_date, int status) {
		super();
		this.followups_id = followups_id;
		this.client_id = client_id;
		this.partner_id = partner_id;
		this.application_id = application_id;
		this.profile_id = profile_id;
		this.job_id = job_id;
		this.contact = contact;
		this.email = email;
		this.comment = comment;
		this.followup_status = followup_status;
		this.created_by = created_by;
		this.followup_creation_date = followup_creation_date;
		this.status = status;
	}

	public long getFollowups_id() {
		return followups_id;
	}

	public void setFollowups_id(long followups_id) {
		this.followups_id = followups_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}

	public long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(long application_id) {
		this.application_id = application_id;
	}

	public long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(long profile_id) {
		this.profile_id = profile_id;
	}

	public long getJob_id() {
		return job_id;
	}

	public void setJob_id(long job_id) {
		this.job_id = job_id;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFollowup_status() {
		return followup_status;
	}

	public void setFollowup_status(String followup_status) {
		this.followup_status = followup_status;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getFollowup_creation_date() {
		return followup_creation_date;
	}

	public void setFollowup_creation_date(Date followup_creation_date) {
		this.followup_creation_date = followup_creation_date;
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
		return "Followups [followups_id=" + followups_id + ", client_id=" + client_id + ", partner_id=" + partner_id
				+ ", application_id=" + application_id + ", profile_id=" + profile_id + ", job_id=" + job_id
				+ ", contact=" + contact + ", email=" + email + ", comment=" + comment + ", followup_status="
				+ followup_status + ", created_by=" + created_by + ", followup_creation_date=" + followup_creation_date
				+ ", status=" + status + "]";
	}
}
