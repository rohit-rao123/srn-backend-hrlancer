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
@Table(name = "partners")
public class Partners implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = true)
	private String website;

	public enum PartnerCategory {
		INDIVIDUAL, ORGANIZATION;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('INDIVIDUAL', 'ORGANIZATION')", nullable = false)
	private PartnerCategory partner_category;

	// todo on its nullability
	@Column(unique = false, nullable = true)
	private double sharing_percentage;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = false)
	private Date partnership_start;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(unique = false, nullable = true)
	private Date partnership_end;

	@Column(unique = false, nullable = false)
	private String creator;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date dt;

	@Column(unique = false, nullable = false)
	private int status;

	public enum PartnerType {
		OFFSHORE, RECRUITMENT, OFFSHORE_RECRUITMENT;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "partner_type", columnDefinition = "ENUM('OFFSHORE', 'RECRUITMENT', 'OFFSHORE_RECRUITMENT')", nullable = true)
	private PartnerType partner_type;

	public Partners() {
		super();
	}

	public Partners(long partner_id, String name, String contact, String email, String website,
			PartnerCategory partner_category, double sharing_percentage, Date partnership_start, Date partnership_end,
			String creator, Date dt, int status, PartnerType partner_type) {
		super();
		this.partner_id = partner_id;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.website = website;
		this.partner_category = partner_category;
		this.sharing_percentage = sharing_percentage;
		this.partnership_start = partnership_start;
		this.partnership_end = partnership_end;
		this.creator = creator;
		this.dt = dt;
		this.status = status;
		this.partner_type = partner_type;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public PartnerCategory getPartner_category() {
		return partner_category;
	}

	public void setPartner_category(PartnerCategory partner_category) {
		this.partner_category = partner_category;
	}

	public double getSharing_percentage() {
		return sharing_percentage;
	}

	public void setSharing_percentage(double sharing_percentage) {
		this.sharing_percentage = sharing_percentage;
	}

	public Date getPartnership_start() {
		return partnership_start;
	}

	public void setPartnership_start(Date partnership_start) {
		this.partnership_start = partnership_start;
	}

	public Date getPartnership_end() {
		return partnership_end;
	}

	public void setPartnership_end(Date partnership_end) {
		this.partnership_end = partnership_end;
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

	public PartnerType getPartner_type() {
		return partner_type;
	}

	public void setPartner_type(PartnerType partner_type) {
		this.partner_type = partner_type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Partners [partner_id=" + partner_id + ", name=" + name + ", contact=" + contact + ", email=" + email
				+ ", website=" + website + ", partner_category=" + partner_category + ", sharing_percentage="
				+ sharing_percentage + ", partnership_start=" + partnership_start + ", partnership_end="
				+ partnership_end + ", creator=" + creator + ", dt=" + dt + ", status=" + status + ", partner_type="
				+ partner_type + "]";
	}

	
	
	
	
}