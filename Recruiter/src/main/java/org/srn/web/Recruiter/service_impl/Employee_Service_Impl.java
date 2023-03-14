package org.srn.web.Recruiter.service_impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Employee_Dao_Impl;
import org.srn.web.Recruiter.dao_impl.User_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Employee;
import org.srn.web.Recruiter.entity.User;
import org.srn.web.Recruiter.service.Employee_Service;

@Service
@Transactional
public class Employee_Service_Impl implements Employee_Service {

	@Autowired
	private Employee_Dao_Impl employee_repos;

	@Autowired
	private User_dao_impl user_repos;

	@Override
	public ResponseEntity<ResponseDto> fetchEmployee(HttpServletRequest request, String name, String contact,
			String official_id, String department) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		if (name.isBlank() && contact.isBlank() && official_id.isBlank() && department.isBlank()) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage("please insert one value");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			List<Employee> list = employee_repos.getEmployee(name, contact, official_id, department);
			if (list == null || list.isEmpty()) {
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("does not exist");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));

				if (user.getRole().equalsIgnoreCase("admin")) {
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(list);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					List<Employee> localList = new ArrayList<Employee>();
					for (Employee emp : list) {
						Employee employee = new Employee();
						employee.setEmployee_id(emp.getEmployee_id());
						employee.setSignum_id(emp.getSignum_id());
						employee.setEmployee_name(emp.getEmployee_name());
						employee.setOfficial_id(emp.getOfficial_id());
						employee.setPersonal_id(emp.getPersonal_id());
						employee.setMobile_number(emp.getMobile_number());
						employee.setEmail(emp.getEmail());
						employee.setCreated_by(emp.getCreated_by());
						employee.setDate_of_joining(emp.getDate_of_joining());
						employee.setDate_of_exit(emp.getDate_of_exit());
						employee.setOffer_salary(null);
						employee.setCurrent_salary(null);
						employee.setDepartment(emp.getDepartment());
						employee.setInitial_designation(emp.getInitial_designation());
						employee.setCurrent_designation(emp.getCurrent_designation());
						employee.setJob_stage(emp.getJob_stage());
						employee.setOffer_id(emp.getOffer_id());
						employee.setEmployee_status(emp.getEmployee_status());
						localList.add(employee);
					}
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(localList);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			}
		}
	}

	@Override
	public ResponseEntity<ResponseDto> saveEmployee(HttpServletRequest request, String signum_id, String employee_name,
			String official_id, String personal_id, String mobile_number, String email, Date date_of_joining,
			Date date_of_exit, String offer_salary, String current_salary, String department,
			String initial_designation, String current_designation, String job_stage, String offer_id,
			String employee_status) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		try {
			List<Employee> list = employee_repos.getByEmail(email);
			System.out.println("hi" + list);
			if (list != null) {
				ResponseDto response = new ResponseDto();

				response.setMessage("Employee already exist");
				response.setBody("Duplicate entry not allowed");
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Employee employee = new Employee();
				employee.setSignum_id(signum_id);
				employee.setEmployee_name(employee_name);
				employee.setOfficial_id(official_id);
				employee.setPersonal_id(personal_id);
				employee.setMobile_number(mobile_number);
				employee.setEmail(email);
				employee.setCreated_by((String) request.getSession().getAttribute("userName"));
				employee.setDate_of_joining(date_of_joining);
				employee.setDate_of_exit(date_of_exit);
				employee.setOffer_salary(offer_salary);
				employee.setCurrent_salary(current_salary);
				employee.setDepartment(department);
				employee.setInitial_designation(initial_designation);
				employee.setCurrent_designation(current_designation);
				employee.setJob_stage(job_stage);
				employee.setOffer_id(offer_id);
				employee.setEmployee_status(employee_status);
				boolean check = employee_repos.saveEmployee(employee);
				if (check == true) {
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("Succesfully added new employee details");
					response.setBody("Record generated for " + employee_name);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("Record cannot be added");
					response.setBody("due to internal server error");
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage("Record cannot be added");
			response.setBody("due to internal server error");
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<ResponseDto> deleteEmployee(HttpServletRequest request, long employee_id) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}

		try {
			User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));
			if (user.getRole().equalsIgnoreCase("admin")) {
				if (employee_repos.getById(employee_id) == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					boolean check = employee_repos.deleteEmployeeById(employee_id);
					if (check == true) {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.DELETE);
						response.setBody("deleted employee_id:" + employee_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.DEFAULT);
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				}

			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage("Access denied for USER");
				response.setBody("Only ADMIN can access");
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseDto> fetchALLEmployee(HttpServletRequest request) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}

		List<Employee> list = employee_repos.getAllEmployee();
		if (list == null || list.isEmpty()) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage("does not exist");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));

			if (user.getRole().equalsIgnoreCase("admin")) {
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("searched employee");
				response.setBody(list);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				List<Employee> localList = new ArrayList<Employee>();
				for (Employee emp : list) {
					Employee employee = new Employee();
					employee.setEmployee_id(emp.getEmployee_id());
					employee.setSignum_id(emp.getSignum_id());
					employee.setEmployee_name(emp.getEmployee_name());
					employee.setOfficial_id(emp.getOfficial_id());
					employee.setPersonal_id(emp.getPersonal_id());
					employee.setMobile_number(emp.getMobile_number());
					employee.setEmail(emp.getEmail());
					employee.setCreated_by(emp.getCreated_by());
					employee.setDate_of_joining(emp.getDate_of_joining());
					employee.setDate_of_exit(emp.getDate_of_exit());
					employee.setOffer_salary(null);
					employee.setCurrent_salary(null);
					employee.setDepartment(emp.getDepartment());
					employee.setInitial_designation(emp.getInitial_designation());
					employee.setCurrent_designation(emp.getCurrent_designation());
					employee.setJob_stage(emp.getJob_stage());
					employee.setOffer_id(emp.getOffer_id());
					employee.setEmployee_status(emp.getEmployee_status());
					localList.add(employee);
				}
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("searched employee");
				response.setBody(localList);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		}

	}

	@Override
	public ResponseEntity<ResponseDto> fetchAll(HttpServletRequest request) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		List<Employee> list = employee_repos.getAllEmployee();
		if (list == null) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage(VMessageConstant.Not_Found);
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));
			if (user.getRole().equalsIgnoreCase("admin")) {
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("searched employee");
				response.setBody(list);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				List<Employee> localList = new ArrayList<Employee>();
				for (Employee emp : list) {
					Employee employee = new Employee();
					employee.setEmployee_id(emp.getEmployee_id());
					employee.setSignum_id(emp.getSignum_id());
					employee.setEmployee_name(emp.getEmployee_name());
					employee.setOfficial_id(emp.getOfficial_id());
					employee.setPersonal_id(emp.getPersonal_id());
					employee.setMobile_number(emp.getMobile_number());
					employee.setEmail(emp.getEmail());
					employee.setCreated_by(emp.getCreated_by());
					employee.setDate_of_joining(emp.getDate_of_joining());
					employee.setDate_of_exit(emp.getDate_of_exit());
					employee.setOffer_salary(null);
					employee.setCurrent_salary(null);
					employee.setDepartment(emp.getDepartment());
					employee.setInitial_designation(emp.getInitial_designation());
					employee.setCurrent_designation(emp.getCurrent_designation());
					employee.setJob_stage(emp.getJob_stage());
					employee.setOffer_id(emp.getOffer_id());
					employee.setEmployee_status(emp.getEmployee_status());
					localList.add(employee);
				}
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("searched employee");
				response.setBody(localList);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}

	}

	@Override
	public ResponseEntity<ResponseDto> fetchByJoiningDate(HttpServletRequest request, int year) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}

		if (year < 1000) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage(VMessageConstant.INVALID);
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			List<Employee> list = employee_repos.getByYearOfJoining(year);
			if (list != null) {
				User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));
				if (user.getRole().equalsIgnoreCase("admin")) {
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(list);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					List<Employee> localList = new ArrayList<Employee>();
					for (Employee emp : list) {
						Employee employee = new Employee();
						employee.setEmployee_id(emp.getEmployee_id());
						employee.setSignum_id(emp.getSignum_id());
						employee.setEmployee_name(emp.getEmployee_name());
						employee.setOfficial_id(emp.getOfficial_id());
						employee.setPersonal_id(emp.getPersonal_id());
						employee.setMobile_number(emp.getMobile_number());
						employee.setEmail(emp.getEmail());
						employee.setCreated_by(emp.getCreated_by());
						employee.setDate_of_joining(emp.getDate_of_joining());
						employee.setDate_of_exit(emp.getDate_of_exit());
						employee.setOffer_salary(null);
						employee.setCurrent_salary(null);
						employee.setDepartment(emp.getDepartment());
						employee.setInitial_designation(emp.getInitial_designation());
						employee.setCurrent_designation(emp.getCurrent_designation());
						employee.setJob_stage(emp.getJob_stage());
						employee.setOffer_id(emp.getOffer_id());
						employee.setEmployee_status(emp.getEmployee_status());
						localList.add(employee);
					}
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(localList);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else {
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchByExitDate(HttpServletRequest request, int year) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}

		if (year < 1000) {
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage(VMessageConstant.INVALID);
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			List<Employee> list = employee_repos.getByYearOfExit(year);
			if (list != null) {
				User user = user_repos.getByEmail((String) request.getSession().getAttribute("userName"));
				if (user.getRole().equalsIgnoreCase("admin")) {
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(list);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					List<Employee> localList = new ArrayList<Employee>();
					for (Employee emp : list) {
						Employee employee = new Employee();
						employee.setEmployee_id(emp.getEmployee_id());
						employee.setSignum_id(emp.getSignum_id());
						employee.setEmployee_name(emp.getEmployee_name());
						employee.setOfficial_id(emp.getOfficial_id());
						employee.setPersonal_id(emp.getPersonal_id());
						employee.setMobile_number(emp.getMobile_number());
						employee.setEmail(emp.getEmail());
						employee.setCreated_by(emp.getCreated_by());
						employee.setDate_of_joining(emp.getDate_of_joining());
						employee.setDate_of_exit(emp.getDate_of_exit());
						employee.setOffer_salary(null);
						employee.setCurrent_salary(null);
						employee.setDepartment(emp.getDepartment());
						employee.setInitial_designation(emp.getInitial_designation());
						employee.setCurrent_designation(emp.getCurrent_designation());
						employee.setJob_stage(emp.getJob_stage());
						employee.setOffer_id(emp.getOffer_id());
						employee.setEmployee_status(emp.getEmployee_status());
						localList.add(employee);
					}
					ResponseDto response = new ResponseDto();
					response.setStatus(true);
					response.setMessage("searched employee");
					response.setBody(localList);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else {
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}
	}

	@Override
	public ResponseEntity<ResponseDto> update_employee(HttpServletRequest request, long employee_id, String email,
			String contact, Date date_of_exit, String current_designation, String current_salary) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		if (employee_id <= 0) {
			ResponseDto response = new ResponseDto();

			response.setMessage("Invalid employee_id");
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (employee_repos.getById(employee_id)==null) {
			
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			
		} else {
			try {
				boolean check = employee_repos.updateEmployee(employee_id, email, contact, date_of_exit,
						current_designation, current_salary);
				if (check == true) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.UPDATE);
					response.setBody("updated employee_id =" + employee_id);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} catch (Exception e) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DEFAULT);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		}
	}

}
