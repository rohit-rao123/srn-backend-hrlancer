package org.srn.web.Recruiter.dao;

import java.util.Date;
import java.util.List;

import org.srn.web.Recruiter.entity.Employee;

public interface Employee_dao {

	public boolean saveEmployee(Employee employee);

	public boolean deleteEmployeeById(long id);

	public List<Employee> getEmployee(String name, String contact, String official_id, String department);

	public List<Employee> getByEmail(String email);
	
	public Employee getById(long employee_id);

	public List<Employee> getAllEmployee();

	public List<Employee> getByYearOfJoining(int year);

	public List<Employee> getByYearOfExit(int year);
	
	public boolean updateEmployee(long employee_id, String email,
			String contact, Date date_of_exit, String current_designation, String current_salary);

	public List<Employee> findLatestEmployee();
	
}
