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
@Table(name = "clients")
public class Clients implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Client_id;

	@Column(unique = false, nullable = false)
	private String client_name;

	@Column(unique = true, nullable = true)
	private String contact;

	@Column(unique = false, nullable = false)
	private String email;

	@Column(unique = false, nullable = false)
	private String website;

	public enum ClientType {
		INDIVIDUAL, ORGANIZATION, PLATFORM
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "Client_type", columnDefinition = "ENUM('INDIVIDUAL', 'ORGANIZATION', 'PLATFORM')", nullable = false)
	private ClientType Client_type;

	@Column(unique = false, nullable = true)
	private String address;

	public enum SubscriptionType {
		DEMO, SUBSCRIPTION
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "subscription_type", columnDefinition = "ENUM('DEMO', 'SUBSCRIPTION')", nullable = false)
	private SubscriptionType subscription_type;

	@Column(unique = false, nullable = false)
	private int is_subscribed;

	@Column(unique = false, nullable = false)
	private String created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(unique = false, nullable = false)
	private Date dt;

	@Column(unique = false, nullable = false)
	private int status;

	public Clients() {
	}

	public Clients(long client_id, String client_name, String contact, String email, String website,
			ClientType client_type, String address, SubscriptionType subscription_type, int is_subscribed,
			String created_by, Date dt, int status) {
		Client_id = client_id;
		this.client_name = client_name;
		this.contact = contact;
		this.email = email;
		this.website = website;
		Client_type = client_type;
		this.address = address;
		this.subscription_type = subscription_type;
		this.is_subscribed = is_subscribed;
		this.created_by = created_by;
		this.dt = dt;
		this.status = status;
	}

	public long getClient_id() {
		return Client_id;
	}

	public void setClient_id(long client_id) {
		Client_id = client_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
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

	public ClientType getClient_type() {
		return Client_type;
	}

	public void setClient_type(ClientType client_type) {
		Client_type = client_type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SubscriptionType getSubscription_type() {
		return subscription_type;
	}

	public void setSubscription_type(SubscriptionType subscription_type) {
		this.subscription_type = subscription_type;
	}

	public int getIs_subscribed() {
		return is_subscribed;
	}

	public void setIs_subscribed(int is_subscribed) {
		this.is_subscribed = is_subscribed;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Clients [Client_id=" + Client_id + ", client_name=" + client_name + ", contact=" + contact + ", email="
				+ email + ", website=" + website + ", Client_type=" + Client_type + ", address=" + address
				+ ", subscription_type=" + subscription_type + ", is_subscribed=" + is_subscribed + ", created_by="
				+ created_by + ", dt=" + dt + ", status=" + status + "]";
	}

}
