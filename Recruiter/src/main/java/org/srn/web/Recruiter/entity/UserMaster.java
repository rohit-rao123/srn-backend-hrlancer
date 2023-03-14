package org.srn.web.Recruiter.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="users")
public class UserMaster implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id = 0;
	@Column(unique=false, nullable = false)
	private long partner_id  = 0;
	@Column(unique=false, nullable = false)
	private String name = null;
	@Column(unique=false, nullable = false)
	private String email = null;
	@Column(unique=false, nullable = false)
	private String password = null;
	@Column(unique=false, nullable = false)
	private String contact = null;
	@Column(unique=false, nullable = false)
	private String company = null;
	@Column(unique=false, nullable = false)
	private String designation = null;
	@Column(unique=false, nullable = false)
	private String role = null;
	@Column(unique=false, nullable = false)
	private String created_by = null;
	@Column(unique=false, nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date_of_creation = null;
	
	
	
	public UserMaster() {
		super();
	}
	
	
	
	public UserMaster(long user_id, long partner_id, String name, String email, String password, String contact,
			String company, String designation, String role, String created_by, Date date_of_creation) {
		super();
		this.user_id = user_id;
		this.partner_id = partner_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.company = company;
		this.designation = designation;
		this.role = role;
		this.created_by = created_by;
		this.date_of_creation = date_of_creation;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public Date getDate_of_creation() {
		return date_of_creation;
	}
	public void setDate_of_creation(Date date_of_creation) {
		this.date_of_creation = date_of_creation;
	}
	@Override
	public String toString() {
		return "UserMaster [user_id=" + user_id + ", partner_id=" + partner_id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", contact=" + contact + ", company=" + company + ", designation="
				+ designation + ", role=" + role + ", created_by=" + created_by + ", date_of_creation="
				+ date_of_creation + "]";
	}
		
	
}
