package org.srn.web.Recruiter.entity;

import java.io.Serializable;
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
public class InterviewProfile  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long interview_id;
	
	@Column(unique = false, nullable = false)
	private String candidate_name;
	
	@Column(unique = false, nullable = false)
	private String candidate_contact;
	
	@Column(unique = false, nullable = false)
	private String candidate_email;

	@Column(unique = false, nullable = false)
	private long client_id;

	@Column(unique = false, nullable = false)
	private long job_id;

	@Column(unique = false, nullable = false)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private long application_id;

	@Column(unique = false, nullable = false)
	private String type;

	@Column(unique = false, nullable = true)
	private int no_of_round;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date slot_start_dt;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date slot_end_dt;

	@Column(unique = true, nullable = false)
	private String interview_tool;

	@Column(unique = false, nullable = false)
	private String interviewer_name;

	@Column(unique = false, nullable = true)
	private int interviewer_mail;

	@Column(unique = false, nullable = true)
	private String interview_status;

	@Column(unique = false, nullable = true)
	private String interview_result;

	@Column(unique = false, nullable = true)
	private String creator;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date dt;

	@Column(unique = false, nullable = true)
	private int status;

	public InterviewProfile() {
		super();
	}

	public InterviewProfile(long interview_id, String candidate_name, String candidate_contact, String candidate_email,
			long client_id, long job_id, long partner_id, long application_id, String type, int no_of_round,
			Date slot_start_dt, Date slot_end_dt, String interview_tool, String interviewer_name, int interviewer_mail,
			String interview_status, String interview_result, String creator, Date dt, int status) {
		super();
		this.interview_id = interview_id;
		this.candidate_name = candidate_name;
		this.candidate_contact = candidate_contact;
		this.candidate_email = candidate_email;
		this.client_id = client_id;
		this.job_id = job_id;
		this.partner_id = partner_id;
		this.application_id = application_id;
		this.type = type;
		this.no_of_round = no_of_round;
		this.slot_start_dt = slot_start_dt;
		this.slot_end_dt = slot_end_dt;
		this.interview_tool = interview_tool;
		this.interviewer_name = interviewer_name;
		this.interviewer_mail = interviewer_mail;
		this.interview_status = interview_status;
		this.interview_result = interview_result;
		this.creator = creator;
		this.dt = dt;
		this.status = status;
	}

	public long getInterview_id() {
		return interview_id;
	}

	public void setInterview_id(long interview_id) {
		this.interview_id = interview_id;
	}

	public String getCandidate_name() {
		return candidate_name;
	}

	public void setCandidate_name(String candidate_name) {
		this.candidate_name = candidate_name;
	}

	public String getCandidate_contact() {
		return candidate_contact;
	}

	public void setCandidate_contact(String candidate_contact) {
		this.candidate_contact = candidate_contact;
	}

	public String getCandidate_email() {
		return candidate_email;
	}

	public void setCandidate_email(String candidate_email) {
		this.candidate_email = candidate_email;
	}

	public long getClient_id() {
		return client_id;
	}

	public void setClient_id(long client_id) {
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

	public long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(long application_id) {
		this.application_id = application_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNo_of_round() {
		return no_of_round;
	}

	public void setNo_of_round(int no_of_round) {
		this.no_of_round = no_of_round;
	}

	public Date getSlot_start_dt() {
		return slot_start_dt;
	}

	public void setSlot_start_dt(Date slot_start_dt) {
		this.slot_start_dt = slot_start_dt;
	}

	public Date getSlot_end_dt() {
		return slot_end_dt;
	}

	public void setSlot_end_dt(Date slot_end_dt) {
		this.slot_end_dt = slot_end_dt;
	}

	public String getInterview_tool() {
		return interview_tool;
	}

	public void setInterview_tool(String interview_tool) {
		this.interview_tool = interview_tool;
	}

	public String getInterviewer_name() {
		return interviewer_name;
	}

	public void setInterviewer_name(String interviewer_name) {
		this.interviewer_name = interviewer_name;
	}

	public int getInterviewer_mail() {
		return interviewer_mail;
	}

	public void setInterviewer_mail(int interviewer_mail) {
		this.interviewer_mail = interviewer_mail;
	}

	public String getInterview_status() {
		return interview_status;
	}

	public void setInterview_status(String interview_status) {
		this.interview_status = interview_status;
	}

	public String getInterview_result() {
		return interview_result;
	}

	public void setInterview_result(String interview_result) {
		this.interview_result = interview_result;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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
		return "InterviewProfile [interview_id=" + interview_id + ", candidate_name=" + candidate_name
				+ ", candidate_contact=" + candidate_contact + ", candidate_email=" + candidate_email + ", client_id="
				+ client_id + ", job_id=" + job_id + ", partner_id=" + partner_id + ", application_id=" + application_id
				+ ", type=" + type + ", no_of_round=" + no_of_round + ", slot_start_dt=" + slot_start_dt
				+ ", slot_end_dt=" + slot_end_dt + ", interview_tool=" + interview_tool + ", interviewer_name="
				+ interviewer_name + ", interviewer_mail=" + interviewer_mail + ", interview_status=" + interview_status
				+ ", interview_result=" + interview_result + ", creator=" + creator + ", dt=" + dt + ", status="
				+ status + "]";
	}

	
	
}
