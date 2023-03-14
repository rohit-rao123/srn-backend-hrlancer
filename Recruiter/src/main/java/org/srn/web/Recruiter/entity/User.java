package org.srn.web.Recruiter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="users")
public class User {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	@Column(unique = false, nullable = false)
	private long partner_id;
	@Column(unique = false, nullable = false)
	private String name;
	@Column(unique = false, nullable = false)
	private String password;
	@Column(unique = false, nullable = false)
	private String email;
	@Column(unique = false, nullable = false)
	private String contact;
	@Column(unique = false, nullable = false)
	private String designation;
	@Column(unique = false, nullable = true)
	private String role;
	@Column(unique = false, nullable = false)
	private String created_by;
	private String company;
	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_creation = new Date();
	
	public User(long user_id, long partner_id, String name, String password, String email, String contact,
			String designation, String role, String created_by, String company, Date date_of_creation) {
		super();
		this.user_id = user_id;
		this.partner_id = partner_id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.contact = contact;
		this.designation = designation;
		this.role = role;
		this.created_by = created_by;
		this.company = company;
		this.date_of_creation = date_of_creation;
	}

	public User() {
		super();
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getDate_of_creation() {
		return date_of_creation;
	}

	public void setDate_of_creation(Date date_of_creation) {
		this.date_of_creation = date_of_creation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", partner_id=" + partner_id + ", name=" + name + ", password=" + password
				+ ", email=" + email + ", contact=" + contact + ", designation=" + designation + ", role=" + role
				+ ", created_by=" + created_by + ", company=" + company + ", date_of_creation=" + date_of_creation
				+ "]";
	}
	
	
		
}
