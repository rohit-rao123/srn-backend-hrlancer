package org.srn.web.Recruiter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="employee")

public class Employee implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long employee_id;
	private String signum_id;
	private String employee_name;
	private String official_id;
	private String personal_id;
	private String mobile_number;
	private String email;
	private String created_by;
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_of_joining;
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_of_exit;
	private String offer_salary;
	private String current_salary;
	private String department;
	private String initial_designation;
	private String current_designation;
	private String job_stage;
	private String offer_id;
	private String employee_status;
	
	public Employee(long employee_id, String signum_id, String employee_name, String official_id, String personal_id,
			String mobile_number, String email, String created_by, Date date_of_joining, Date date_of_exit,
			String offer_salary, String current_salary, String department, String initial_designation,
			String current_designation, String job_stage, String offer_id, String employee_status) {
		super();
		this.employee_id = employee_id;
		this.signum_id = signum_id;
		this.employee_name = employee_name;
		this.official_id = official_id;
		this.personal_id = personal_id;
		this.mobile_number = mobile_number;
		this.email = email;
		this.created_by = created_by;
		this.date_of_joining = date_of_joining;
		this.date_of_exit = date_of_exit;
		this.offer_salary = offer_salary;
		this.current_salary = current_salary;
		this.department = department;
		this.initial_designation = initial_designation;
		this.current_designation = current_designation;
		this.job_stage = job_stage;
		this.offer_id = offer_id;
		this.employee_status = employee_status;
	}
	
	public Employee() {
		super();
	}

	public long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}

	public String getSignum_id() {
		return signum_id;
	}

	public void setSignum_id(String signum_id) {
		this.signum_id = signum_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getOfficial_id() {
		return official_id;
	}

	public void setOfficial_id(String official_id) {
		this.official_id = official_id;
	}

	public String getPersonal_id() {
		return personal_id;
	}

	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getDate_of_joining() {
		return date_of_joining;
	}

	public void setDate_of_joining(Date date_of_joining) {
		this.date_of_joining = date_of_joining;
	}

	public Date getDate_of_exit() {
		return date_of_exit;
	}

	public void setDate_of_exit(Date date_of_exit) {
		this.date_of_exit = date_of_exit;
	}

	public String getOffer_salary() {
		return offer_salary;
	}

	public void setOffer_salary(String offer_salary) {
		this.offer_salary = offer_salary;
	}

	public String getCurrent_salary() {
		return current_salary;
	}

	public void setCurrent_salary(String current_salary) {
		this.current_salary = current_salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getInitial_designation() {
		return initial_designation;
	}

	public void setInitial_designation(String initial_designation) {
		this.initial_designation = initial_designation;
	}

	public String getCurrent_designation() {
		return current_designation;
	}

	public void setCurrent_designation(String current_designation) {
		this.current_designation = current_designation;
	}

	public String getJob_stage() {
		return job_stage;
	}

	public void setJob_stage(String job_stage) {
		this.job_stage = job_stage;
	}

	public String getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}

	public String getEmployee_status() {
		return employee_status;
	}

	public void setEmployee_status(String employee_status) {
		this.employee_status = employee_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id + ", signum_id=" + signum_id + ", employee_name=" + employee_name
				+ ", official_id=" + official_id + ", personal_id=" + personal_id + ", mobile_number=" + mobile_number
				+ ", email=" + email + ", created_by=" + created_by + ", date_of_joining=" + date_of_joining
				+ ", date_of_exit=" + date_of_exit + ", offer_salary=" + offer_salary + ", current_salary="
				+ current_salary + ", department=" + department + ", initial_designation=" + initial_designation
				+ ", current_designation=" + current_designation + ", job_stage=" + job_stage + ", offer_id=" + offer_id
				+ ", employee_status=" + employee_status + "]";
	}
	
	
	
}
