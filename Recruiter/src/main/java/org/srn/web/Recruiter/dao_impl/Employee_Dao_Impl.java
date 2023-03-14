package org.srn.web.Recruiter.dao_impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Employee_dao;
import org.srn.web.Recruiter.entity.Employee;

@SuppressWarnings("unchecked")
@Repository
public class Employee_Dao_Impl implements Employee_dao {

	@Autowired
	private SessionFactory sf;

	@Override
	public boolean saveEmployee(Employee employee) {
		try {
			long new_employee_id = (long) sf.getCurrentSession().save(employee);
			if (new_employee_id > 0) {
				System.out.println("Generated new employee_id :" + new_employee_id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEmployeeById(long id) {
		String query = "delete from employee as a where a.employee_id=:identity";
		try {
			int x = sf.getCurrentSession().createNativeQuery(query).setParameter("identity", id).executeUpdate();
			if (x > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Employee> getEmployee(String name, String contact, String official_id, String department) {
		if (!name.isBlank() && contact.isBlank() && official_id.isBlank() && department.isBlank()) {
			String query = "select * from employee as e where e.employee_name like :nm";
			try {
				List<Employee> list = sf.getCurrentSession().createNativeQuery(query)
						.setParameter("nm", "%" + name + "%").addEntity(Employee.class).getResultList();
				if (list == null || list.isEmpty()) {
					return null;
				} else {
					return list;
				}
			} catch (Exception e) {
				return null;
			}
		} else if (name.isBlank() && !contact.isBlank() && official_id.isBlank() && department.isBlank()) {
			String query = "select * from employee as e where e.mobile_number like :cntct";
			try {
				List<Employee> list = sf.getCurrentSession().createNativeQuery(query)
						.setParameter("cntct", "%" + contact + "%").addEntity(Employee.class).getResultList();
				if (list == null || list.isEmpty()) {
					return null;
				} else {
					return list;
				}
			} catch (Exception e) {
				return null;
			}
		} else if (name.isBlank() && contact.isBlank() && !official_id.isBlank() && department.isBlank()) {
			String query = "select * from employee as e where e.official_id like :office_id";
			try {
				List<Employee> list = sf.getCurrentSession().createNativeQuery(query)
						.setParameter("office_id", "%" + official_id + "%").addEntity(Employee.class).getResultList();
				if (list.isEmpty() || list == null) {
					return null;
				} else {
					return list;
				}
			} catch (Exception e) {
				return null;
			}
		} else if (name.isBlank() && contact.isBlank() && official_id.isBlank() && !department.isBlank()) {
			String query = "select * from employee as e where e.department like :dp";
			try {
				List<Employee> list = sf.getCurrentSession().createNativeQuery(query)
						.setParameter("dp", "%" + department + "%").addEntity(Employee.class).getResultList();
				if (list.isEmpty() || list == null) {
					return null;
				} else {
					return list;
				}
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<Employee> getByEmail(String email) {
		String query = "select * from employee as a where a.email =:mail";
		try {
			List<Employee> list = sf.getCurrentSession().createNativeQuery(query).addEntity(Employee.class)
					.setParameter("mail", email).getResultList();
			if (list.isEmpty() || list == null) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Employee getById(long employee_id) {
		String query = "select * from employee as a where a.employee_id=:identity";
		try {
			Employee employee = (Employee) sf.getCurrentSession().createNativeQuery(query).addEntity(Employee.class)
					.setParameter("identity", employee_id).getSingleResult();
			if (employee != null) {
				return employee;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Employee> getAllEmployee() {
		String query = "select * from employee ";
		try {
			List<Employee> list = sf.getCurrentSession().createNativeQuery(query).addEntity(Employee.class)
					.getResultList();
			if (list != null) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Employee> findLatestEmployee() {
		String query = "select  * from  employee order by employee_id desc limit 10 ";
		try {
			List<Employee> list = sf.getCurrentSession().createNativeQuery(query)
					.addEntity(Employee.class).list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Employee> getByYearOfJoining(int year) {
		String query = "select * from employee as a where year(a.date_of_joining)=:yrs ";
		try {
			List<Employee> list = sf.getCurrentSession().createNativeQuery(query).setParameter("yrs", year)
					.addEntity(Employee.class).getResultList();
			if (list == null || list.isEmpty()) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Employee> getByYearOfExit(int year) {
		String query = "select * from employee as a where year(a.date_of_exit) =:yrs ";
		try {
			List<Employee> list = sf.getCurrentSession().createNativeQuery(query).setParameter("yrs", year)
					.addEntity(Employee.class).getResultList();
			if (list == null || list.isEmpty()) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean updateEmployee(long employee_id, String email, String contact, Date date_of_exit,
			String current_designation, String current_salary) {

		if (!email.isBlank() && contact.isBlank() && date_of_exit == null && current_designation.isBlank()
				&& current_salary.isBlank()) {
			String query = "update employee as a set a.email=:mail where a.employee_id=:identity";
			try {
				int x = sf.getCurrentSession().createNativeQuery(query).setParameter("mail", email).setParameter("identity", employee_id).executeUpdate();
				if (x > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(email.isBlank() && !contact.isBlank() && date_of_exit == null && current_designation.isBlank()
				&& current_salary.isBlank()) {
			String query = "update employee as a set a.mobile_number=:cntct where a.employee_id=:identity";
			try {
				int x = sf.getCurrentSession().createNativeQuery(query).setParameter("cntct", contact).setParameter("identity", employee_id).executeUpdate();
				if (x > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		else if(email.isBlank() && contact.isBlank() && date_of_exit != null && current_designation.isBlank()
				&& current_salary.isBlank()) {
			String query = "update employee as a set a.date_of_exit=:date where a.employee_id=:identity";
			try {
				int x = sf.getCurrentSession().createNativeQuery(query).setParameter("date", date_of_exit).setParameter("identity", employee_id).executeUpdate();
				if (x > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(email.isBlank() && contact.isBlank() && date_of_exit == null && !current_designation.isBlank()
				&& current_salary.isBlank()) {
			String query = "update employee as a set a.current_designation=:cd where a.employee_id=:identity";
			try {
				int x = sf.getCurrentSession().createNativeQuery(query).setParameter("cd", current_designation).setParameter("identity", employee_id).executeUpdate();
				if (x > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(email.isBlank() && contact.isBlank() && date_of_exit == null && current_designation.isBlank()
				&& !current_salary.isBlank()) {
			String query = "update employee as a set a.current_salary=:cs where a.employee_id=:identity";
			try {
				int x = sf.getCurrentSession().createNativeQuery(query).setParameter("cs", current_salary).setParameter("identity", employee_id).executeUpdate();
				if (x > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
		}
}
