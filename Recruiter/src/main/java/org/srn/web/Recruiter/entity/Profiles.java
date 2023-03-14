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
@Table(name="profile")
public class Profiles implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profile_id;

	@Column(unique = false, nullable = false)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String contact;

	@Column(unique = false, nullable = true)
	private String alternate_contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = true)
	private String alternate_email;

	@Column(unique = false, nullable = false)
	private String current_role;

	@Column(unique = false, nullable = false)
	private double exp;

	@Column(unique = false, nullable = false)
	private double ctc;

	@Column(unique = false, nullable = false)
	private double ectc;

	@Column(unique = false, nullable = false)
	private int notice_period;

	@Column(unique = false, nullable = false)
	private String domain;

	@Column(unique = false, nullable = false, length = 8192, columnDefinition = "longtext")
	private String primary_skill;

	@Column(unique = false, nullable = false, length = 8192, columnDefinition = "longtext")
	private String secondary_skill;

	@Column(unique = false, nullable = false, length = 8192, columnDefinition = "text")
	private String qualification;

	@Column(unique = false, nullable = false, length = 8192, columnDefinition = "text")
	private String location;

	@Column(unique = false, nullable = false)
	private String resume;

	@Column(unique = false, nullable = true)
	private int year_of_passing;

	@Column(unique = false, nullable = false)
	private long company_id;

	@Column(unique = false, nullable = false)
	private String company;

	@Column(unique = false, nullable = false)
	private String created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date prof_creation_date;

	@Column(unique = false, nullable = false)
	private int status;

	public Profiles(long profile_id, long partner_id, String name, String contact, String alternate_contact,
			String email, String alternate_email, String current_role, double exp, double ctc, double ectc, int notice_period,
			String domain, String primary_skill, String secondary_skill, String qualification, String location,
			String resume, int year_of_passing, long company_id, String company, String created_by,
			Date prof_creation_date, int status) {
		this.profile_id = profile_id;
		this.partner_id = partner_id;
		this.name = name;
		this.contact = contact;
		this.alternate_contact = alternate_contact;
		this.email = email;
		this.alternate_email = alternate_email;
		this.current_role = current_role;
		this.exp = exp;
		this.ctc = ctc;
		this.ectc = ectc;
		this.notice_period = notice_period;
		this.domain = domain;
		this.primary_skill = primary_skill;
		this.secondary_skill = secondary_skill;
		this.qualification = qualification;
		this.location = location;
		this.resume = resume;
		this.year_of_passing = year_of_passing;
		this.company_id = company_id;
		this.company = company;
		this.created_by = created_by;
		this.prof_creation_date = prof_creation_date;
		this.status = status;
	}

	public Profiles() {
		super();
	}

	public long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(long profile_id) {
		this.profile_id = profile_id;
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

	public String getCurrent_role() {
		return current_role;
	}

	public void setCurrent_role(String current_role) {
		this.current_role = current_role;
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public double getCtc() {
		return ctc;
	}

	public void setCtc(double ctc) {
		this.ctc = ctc;
	}

	public double getEctc() {
		return ectc;
	}

	public void setEctc(double ectc) {
		this.ectc = ectc;
	}

	public int getNotice_period() {
		return notice_period;
	}

	public void setNotice_period(int notice_period) {
		this.notice_period = notice_period;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public int getYear_of_passing() {
		return year_of_passing;
	}

	public void setYear_of_passing(int year_of_passing) {
		this.year_of_passing = year_of_passing;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getProf_creation_date() {
		return prof_creation_date;
	}

	public void setProf_creation_date(Date prof_creation_date) {
		this.prof_creation_date = prof_creation_date;
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
	
	

	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}

	@Override
	public String toString() {
		return "Profiles [profile_id=" + profile_id + ", partner_id=" + partner_id + ", name=" + name + ", contact="
				+ contact + ", alternate_contact=" + alternate_contact + ", email=" + email + ", alternate_email="
				+ alternate_email + ", current_role=" + current_role + ", exp=" + exp + ", ctc=" + ctc + ", ectc="
				+ ectc + ", notice_period=" + notice_period + ", domain=" + domain + ", primary_skill=" + primary_skill
				+ ", secondary_skill=" + secondary_skill + ", qualification=" + qualification + ", location=" + location
				+ ", resume=" + resume + ", year_of_passing=" + year_of_passing + ", company_id=" + company_id
				+ ", company=" + company + ", created_by=" + created_by + ", prof_creation_date=" + prof_creation_date
				+ ", status=" + status + "]";
	}

	
	
}
