


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
@Table(name = "invoices")
public class Invoices implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long invoice_id;

	@Column(unique = false, nullable = false)
	private long client_id;

	@Column(unique = false, nullable = false)
	private long partner_id;

	@Column(unique = false, nullable = false)
	private long job_id;

	@Column(unique = false, nullable = false)
	private long application_id;

	@Column(unique = false, nullable = false)
	private String client_invoice_id;

	@Column(unique = false, nullable = false)
	private double client_invoice_amt;

	@Column(unique = false, nullable = false)
	private int client_invoice_items;

	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date client_invoice_raised_dt ;

	@Column(unique = false)
	private String client_transaction_id;

	@Column(unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date client_invoice_paid_dt ;

	@Column(unique = false)
	private double client_invoice_remaining_amt;

	@Column(unique = false)
	private String client_remaining_amt_comment;

	public enum ClientInvoiceStatus {
		RAISED, AWAITED, IN_DISCUSSION, DISPUTED, PAID, IN_FOLLOWUP
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "client_invoice_status", columnDefinition = "enum('RAISED', 'AWAITED', 'IN_DISCUSSION', 'DISPUTED', 'PAID', 'IN_FOLLOWUP')", nullable = false)
	private ClientInvoiceStatus client_invoice_status;

	@Column(unique = false)
	private String partner_invoice_raised_id;

	@Column(unique = false)
	private double partner_invoice_amt;

	@Column(unique = false, nullable = false)
	private int partner_invoice_items;

	@Column(unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date partner_invoice_raised_dt;

	@Column(unique = false)
	private String partner_transaction_id;

	@Column(unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date partner_invoice_paid_dt;

	@Column(unique = false)
	private double partner_invoice_remaining_amt;

	@Column(unique = false)
	private String partner_remaining_amt_comment;

	@Column(unique = false, nullable = false)
	private String partner_invoice_updator;

	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date partner_invoice_updator_dt;

	public enum PartnerInvoiceStatus {
		NOT_RAISED, RAISED, AWAITED, IN_DISCUSSION, DISPUTED, PAID, IN_FOLLOWUP, CLIENT_INVOICE_AWAITED
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "partner_invoice_status", columnDefinition = "ENUM('NOT_RAISED', 'RAISED', 'AWAITED', 'IN_DISCUSSION', 'DISPUTED', 'PAID', 'IN_FOLLOWUP', 'CLIENT_INVOICE_AWAITED')", nullable = false)
	private PartnerInvoiceStatus partner_invoice_status;

	@Column(unique = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date onboarded_date ;

	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date probation_end_dt ;

	public enum FinalStatus {
		IN_CLIENT_INVOICING, IN_PARTNER_INVOICING, IN_PROBATION, IN_DISPUTE, IN_REPLACEMENT, COMPLETED,
		NO_PARTNER_INVOICING
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "final_status", columnDefinition = "ENUM('IN_CLIENT_INVOICING', 'IN_PARTNER_INVOICING', 'IN_PROBATION', 'IN_DISPUTE', 'IN_REPLACEMENT', 'COMPLETED',\r\n"
			+ "		'NO_PARTNER_INVOICING')", nullable = false)
	private FinalStatus final_status;

	@Column(unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updated_dt;

	@Column(unique = false)
	private String updator;

	@Column(unique = false, nullable = false)
	private String creator;

	@Column(unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dt ;

	@Column(unique = false, nullable = false)
	private int status;

	public Invoices() {
		super();
	}

	public Invoices(long invoice_id, long client_id, long partner_id, long job_id, long application_id,
			String client_invoice_id, double client_invoice_amt, int client_invoice_items,
			Date client_invoice_raised_dt, String client_transaction_id, Date client_invoice_paid_dt,
			double client_invoice_remaining_amt, String client_remaining_amt_comment,
			ClientInvoiceStatus client_invoice_status, String partner_invoice_raised_id, double partner_invoice_amt,
			int partner_invoice_items, Date partner_invoice_raised_dt, String partner_transaction_id,
			Date partner_invoice_paid_dt, double partner_invoice_remaining_amt, String partner_remaining_amt_comment,
			String partner_invoice_updator, Date partner_invoice_updator_dt,
			PartnerInvoiceStatus partner_invoice_status, Date onboarded_date, Date probation_end_dt,
			FinalStatus final_status, Date updated_dt, String updator, String creator, Date dt, int status) {
		super();
		this.invoice_id = invoice_id;
		this.client_id = client_id;
		this.partner_id = partner_id;
		this.job_id = job_id;
		this.application_id = application_id;
		this.client_invoice_id = client_invoice_id;
		this.client_invoice_amt = client_invoice_amt;
		this.client_invoice_items = client_invoice_items;
		this.client_invoice_raised_dt = client_invoice_raised_dt;
		this.client_transaction_id = client_transaction_id;
		this.client_invoice_paid_dt = client_invoice_paid_dt;
		this.client_invoice_remaining_amt = client_invoice_remaining_amt;
		this.client_remaining_amt_comment = client_remaining_amt_comment;
		this.client_invoice_status = client_invoice_status;
		this.partner_invoice_raised_id = partner_invoice_raised_id;
		this.partner_invoice_amt = partner_invoice_amt;
		this.partner_invoice_items = partner_invoice_items;
		this.partner_invoice_raised_dt = partner_invoice_raised_dt;
		this.partner_transaction_id = partner_transaction_id;
		this.partner_invoice_paid_dt = partner_invoice_paid_dt;
		this.partner_invoice_remaining_amt = partner_invoice_remaining_amt;
		this.partner_remaining_amt_comment = partner_remaining_amt_comment;
		this.partner_invoice_updator = partner_invoice_updator;
		this.partner_invoice_updator_dt = partner_invoice_updator_dt;
		this.partner_invoice_status = partner_invoice_status;
		this.onboarded_date = onboarded_date;
		this.probation_end_dt = probation_end_dt;
		this.final_status = final_status;
		this.updated_dt = updated_dt;
		this.updator = updator;
		this.creator = creator;
		this.dt = dt;
		this.status = status;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public long getClient_id() {
		return client_id;
	}

	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}

	public long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
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

	public String getClient_invoice_id() {
		return client_invoice_id;
	}

	public void setClient_invoice_id(String client_invoice_id) {
		this.client_invoice_id = client_invoice_id;
	}

	public double getClient_invoice_amt() {
		return client_invoice_amt;
	}

	public void setClient_invoice_amt(double client_invoice_amt) {
		this.client_invoice_amt = client_invoice_amt;
	}

	public int getClient_invoice_items() {
		return client_invoice_items;
	}

	public void setClient_invoice_items(int client_invoice_items) {
		this.client_invoice_items = client_invoice_items;
	}

	public Date getClient_invoice_raised_dt() {
		return client_invoice_raised_dt;
	}

	public void setClient_invoice_raised_dt(Date client_invoice_raised_dt) {
		this.client_invoice_raised_dt = client_invoice_raised_dt;
	}

	public String getClient_transaction_id() {
		return client_transaction_id;
	}

	public void setClient_transaction_id(String client_transaction_id) {
		this.client_transaction_id = client_transaction_id;
	}

	public Date getClient_invoice_paid_dt() {
		return client_invoice_paid_dt;
	}

	public void setClient_invoice_paid_dt(Date client_invoice_paid_dt) {
		this.client_invoice_paid_dt = client_invoice_paid_dt;
	}

	public double getClient_invoice_remaining_amt() {
		return client_invoice_remaining_amt;
	}

	public void setClient_invoice_remaining_amt(double client_invoice_remaining_amt) {
		this.client_invoice_remaining_amt = client_invoice_remaining_amt;
	}

	public String getClient_remaining_amt_comment() {
		return client_remaining_amt_comment;
	}

	public void setClient_remaining_amt_comment(String client_remaining_amt_comment) {
		this.client_remaining_amt_comment = client_remaining_amt_comment;
	}

	public ClientInvoiceStatus getClient_invoice_status() {
		return client_invoice_status;
	}

	public void setClient_invoice_status(ClientInvoiceStatus client_invoice_status) {
		this.client_invoice_status = client_invoice_status;
	}

	public String getPartner_invoice_raised_id() {
		return partner_invoice_raised_id;
	}

	public void setPartner_invoice_raised_id(String partner_invoice_raised_id) {
		this.partner_invoice_raised_id = partner_invoice_raised_id;
	}

	public double getPartner_invoice_amt() {
		return partner_invoice_amt;
	}

	public void setPartner_invoice_amt(double partner_invoice_amt) {
		this.partner_invoice_amt = partner_invoice_amt;
	}

	public int getPartner_invoice_items() {
		return partner_invoice_items;
	}

	public void setPartner_invoice_items(int partner_invoice_items) {
		this.partner_invoice_items = partner_invoice_items;
	}

	public Date getPartner_invoice_raised_dt() {
		return partner_invoice_raised_dt;
	}

	public void setPartner_invoice_raised_dt(Date partner_invoice_raised_dt) {
		this.partner_invoice_raised_dt = partner_invoice_raised_dt;
	}

	public String getPartner_transaction_id() {
		return partner_transaction_id;
	}

	public void setPartner_transaction_id(String partner_transaction_id) {
		this.partner_transaction_id = partner_transaction_id;
	}

	public Date getPartner_invoice_paid_dt() {
		return partner_invoice_paid_dt;
	}

	public void setPartner_invoice_paid_dt(Date partner_invoice_paid_dt) {
		this.partner_invoice_paid_dt = partner_invoice_paid_dt;
	}

	public double getPartner_invoice_remaining_amt() {
		return partner_invoice_remaining_amt;
	}

	public void setPartner_invoice_remaining_amt(double partner_invoice_remaining_amt) {
		this.partner_invoice_remaining_amt = partner_invoice_remaining_amt;
	}

	public String getPartner_remaining_amt_comment() {
		return partner_remaining_amt_comment;
	}

	public void setPartner_remaining_amt_comment(String partner_remaining_amt_comment) {
		this.partner_remaining_amt_comment = partner_remaining_amt_comment;
	}

	public String getPartner_invoice_updator() {
		return partner_invoice_updator;
	}

	public void setPartner_invoice_updator(String partner_invoice_updator) {
		this.partner_invoice_updator = partner_invoice_updator;
	}

	public Date getPartner_invoice_updator_dt() {
		return partner_invoice_updator_dt;
	}

	public void setPartner_invoice_updator_dt(Date partner_invoice_updator_dt) {
		this.partner_invoice_updator_dt = partner_invoice_updator_dt;
	}

	public PartnerInvoiceStatus getPartner_invoice_status() {
		return partner_invoice_status;
	}

	public void setPartner_invoice_status(PartnerInvoiceStatus partner_invoice_status) {
		this.partner_invoice_status = partner_invoice_status;
	}

	public Date getOnboarded_date() {
		return onboarded_date;
	}

	public void setOnboarded_date(Date onboarded_date) {
		this.onboarded_date = onboarded_date;
	}

	public Date getProbation_end_dt() {
		return probation_end_dt;
	}

	public void setProbation_end_dt(Date probation_end_dt) {
		this.probation_end_dt = probation_end_dt;
	}

	public FinalStatus getFinal_status() {
		return final_status;
	}

	public void setFinal_status(FinalStatus final_status) {
		this.final_status = final_status;
	}

	public Date getUpdated_dt() {
		return updated_dt;
	}

	public void setUpdated_dt(Date updated_dt) {
		this.updated_dt = updated_dt;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
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
		return "Invoices [invoice_id=" + invoice_id + ", client_id=" + client_id + ", partner_id=" + partner_id
				+ ", job_id=" + job_id + ", application_id=" + application_id + ", client_invoice_id="
				+ client_invoice_id + ", client_invoice_amt=" + client_invoice_amt + ", client_invoice_items="
				+ client_invoice_items + ", client_invoice_raised_dt=" + client_invoice_raised_dt
				+ ", client_transaction_id=" + client_transaction_id + ", client_invoice_paid_dt="
				+ client_invoice_paid_dt + ", client_invoice_remaining_amt=" + client_invoice_remaining_amt
				+ ", client_remaining_amt_comment=" + client_remaining_amt_comment + ", client_invoice_status="
				+ client_invoice_status + ", partner_invoice_raised_id=" + partner_invoice_raised_id
				+ ", partner_invoice_amt=" + partner_invoice_amt + ", partner_invoice_items=" + partner_invoice_items
				+ ", partner_invoice_raised_dt=" + partner_invoice_raised_dt + ", partner_transaction_id="
				+ partner_transaction_id + ", partner_invoice_paid_dt=" + partner_invoice_paid_dt
				+ ", partner_invoice_remaining_amt=" + partner_invoice_remaining_amt
				+ ", partner_remaining_amt_comment=" + partner_remaining_amt_comment + ", partner_invoice_updator="
				+ partner_invoice_updator + ", partner_invoice_updator_dt=" + partner_invoice_updator_dt
				+ ", partner_invoice_status=" + partner_invoice_status + ", onboarded_date=" + onboarded_date
				+ ", probation_end_dt=" + probation_end_dt + ", final_status=" + final_status + ", updated_dt="
				+ updated_dt + ", updator=" + updator + ", creator=" + creator + ", dt=" + dt + ", status=" + status
				+ "]";
	}

}