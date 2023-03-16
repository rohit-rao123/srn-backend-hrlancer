package org.srn.web.Recruiter.service_impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Bench_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Bench;
import org.srn.web.Recruiter.entity.Bench.WorkingMode;
import org.srn.web.Recruiter.service.BenchService;

@Service
@Transactional
public class BenchServiceImpl implements BenchService {

	@Autowired
	private Bench_dao_impl bench_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;
	
	public final String UPLOAD_DIR = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/bench/";
	
	//public final String UPLOAD_DIR = "E:\\SRN-PROJECT-MAIN-NEW\\Latest-Recruiter-code\\bench\\";

	public boolean uploadFile(MultipartFile fileInput) {
		boolean f = false;
		try {
			if (Profile_Service_Impl.checkFileFormat(fileInput) == false) {
				return false;
			} else {
				InputStream fIS = fileInput.getInputStream();
				byte[] datafile = new byte[fIS.available()];
				fIS.read(datafile);

				FileOutputStream fOS = new FileOutputStream(UPLOAD_DIR + fileInput.getOriginalFilename());
				fOS.write(datafile);
				fOS.close();
				f = true;
				return f;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return f;
		}

	}

		
	public static boolean checkFileFormat(MultipartFile file) {

		String fileContentType = file.getContentType();
		if (fileContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return true;
		} else if (fileContentType.equals("application/pdf")) {
			return true;
		} else {
			return false;
		}
	}



	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<ResponseDto> addNewBench(HttpSession session, String token,long partner_id,String name,
			String contact, String alternateContact, String email, String alternateEmail, String xp, String domain, String bench_type,
			String primarySkill, String secondarySkill,String budget,String salary,String org_name,String org_url, String currentRole, String qualification,String is_shift_flexibility,String location,String working_mode,String notice_period,MultipartFile resume) {
		// TODO Auto-generated method stub
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();
     
		try {
			if (session.getAttribute("isSession") != "true") {
				System.out.println(session.getAttribute("isSession"));
				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		long isshiftflexibility = 0;
		long noticeperiod = 0;
		double exp = 0;
		double budgetes = 0;
		double salaries = 0;
		//int status = 0;
		//Date benchStartDtDate = null;
		//Date benchEndDtDate = null;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String resumeName = null;
		Bench bench = new Bench();
		
		 if (name == null || name.isEmpty()) {
				response.setMessage("Name is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		else if (contact == null || contact.isEmpty()) {
				response.setMessage("Contact is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		else if (email == null || email.isEmpty()) {
				response.setMessage("Email is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		else if (xp == null || xp.isEmpty()) {
				response.setMessage("Experience is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			else if (domain == null || domain.isEmpty()) {
				response.setMessage("Domain is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			
			else if (bench_type == null || bench_type.isEmpty()) {
				response.setMessage("Bench Type is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			else if (primarySkill == null || primarySkill.isEmpty()) {
				response.setMessage("Primary skill is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (secondarySkill == null || secondarySkill.isEmpty()) {
				response.setMessage("Secondary skill is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (budget == null || budget.isEmpty()) {
				response.setMessage("Budget is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (salary == null || salary.isEmpty()) {
				response.setMessage("Salary is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (org_name == null || org_name.isEmpty()) {
				response.setMessage("Org Name is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (org_url == null || org_url.isEmpty()) {
				response.setMessage("Org Url is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (currentRole == null || currentRole.isEmpty()) {
				response.setMessage("Current role is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			else if (qualification == null || qualification.isEmpty()) {
				response.setMessage("Qualification is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (location == null || location.isEmpty()) {
				response.setMessage("Location is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (is_shift_flexibility ==null ||is_shift_flexibility.isEmpty()  ) {
				response.setMessage("Is_shift_flexibility is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			else if (working_mode == null || working_mode.isEmpty()) {
				response.setMessage("Working Mode is mandatory!");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if(partner_id == 0) {
				
				bench.setPartner_id((long) session.getAttribute("partner_id"));
				
			}else {
				bench.setPartner_id(partner_id);
			}
			if(resume == null) {
				
				try {
					isshiftflexibility = Long.parseLong(is_shift_flexibility);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid is_shift_flexibility number !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}

				try {
					noticeperiod = Long.parseLong(notice_period);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid notice period number !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}

				try {
					exp = Double.parseDouble(xp);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  exprience !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
				try {
					budgetes = Double.parseDouble(budget);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  budget !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
				try {
					salaries = Double.parseDouble(salary);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  salary !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
//				try {
//					benchStartDtDate = dateFormat.parse(benchStartDt);
//				} catch (Exception e) {
//					e.printStackTrace();
//					response.setMessage("Enter valid Start Date!");
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//				}
//
//				try {
//					benchEndDtDate = dateFormat.parse(benchEndDt);
//				} catch (Exception e) {
//					e.printStackTrace();
//					response.setMessage("Enter valid End Date!");
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//				}

				
			//Bench bench = new Bench();
			bench.setPartner_id((long) session.getAttribute("partner_id"));
			bench.setName(name);
			bench.setContact(contact);
			bench.setAlternate_contact(alternateContact);
			bench.setEmail(email);
			bench.setAlternate_email(alternateEmail);
			bench.setExp(exp);
			bench.setDomain(domain);
			bench.setBench_type(bench_type);
			bench.setPrimary_skill(primarySkill);
			bench.setSecondary_skill(secondarySkill);
			bench.setBudget(budgetes);
			bench.setSalary(salaries);
			bench.setOrg_name(org_name);
			bench.setOrg_url(org_url);
			bench.setCurrent_role(currentRole);
			bench.setQualification(qualification);
			bench.setLocation(location);
			bench.setIs_shift_flexibility(isshiftflexibility);
			if (WorkingMode.Hybrid.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Hybrid);
			} else if (WorkingMode.Remote.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Remote);
			} else if (WorkingMode.WFH.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.WFH);
			} else if (WorkingMode.Onside.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Onside);
			}else if (WorkingMode.Office.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Office);
			} else {
				bench.setWorking_mode(WorkingMode.WFH);
			}
			bench.setCreated_by((String) session.getAttribute("email"));
			bench.setResume("NA");
			bench.setBench_start_dt(new Date());
			bench.setBench_end_dt(null);
			bench.setBench_status("OPEN");
			bench.setNotice_period(noticeperiod);
			bench.setDt(new Date());
			bench.setStatus(1);
			List<Bench> localList = bench_repos.getByContact(session, contact);
				if (localList == null || localList.isEmpty() ) {
					boolean result = bench_repos.saveBench(bench);
					if (result == true) {
						response.setStatus(true);
						response.setMessage("new bench is created for " + name);
						response.setBody(bench);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						response.setStatus(false);
						response.setMessage("new bench cannot be created");
						response.setBody("Internal server error occured");
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				} else {
					response.setStatus(false);
					response.setMessage("bench already exist");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			
				
			}else {
				
			if (uploadFile(resume) == true) {
		
				 resumeName = resume.getOriginalFilename();
				//System.out.println(resumeName);
					try {
						isshiftflexibility = Long.parseLong(is_shift_flexibility);
					} catch (Exception e) {
						e.printStackTrace();
						response.setMessage("Enter valid is_shift_flexibility number !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

					}
					try {
						noticeperiod = Long.parseLong(notice_period);
					} catch (Exception e) {
						e.printStackTrace();
						response.setMessage("Enter valid notice period number !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

					}


				try {
					exp = Double.parseDouble(xp);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  exprience !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
				try {
					budgetes = Double.parseDouble(budget);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  budget !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
				try {
					salaries = Double.parseDouble(salary);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Enter valid  salary !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				}
			bench.setPartner_id((long) session.getAttribute("partner_id"));
			bench.setName(name);
			bench.setContact(contact);
			bench.setAlternate_contact(alternateContact);
			bench.setEmail(email);
			bench.setAlternate_email(alternateEmail);
			bench.setExp(exp);
			bench.setDomain(domain);
			bench.setBench_type(bench_type);
			bench.setPrimary_skill(primarySkill);
			bench.setSecondary_skill(secondarySkill);
			bench.setBudget(budgetes);
			bench.setSalary(salaries);
			bench.setOrg_name(org_name);
			bench.setOrg_url(org_url);
			bench.setCurrent_role(currentRole);
			bench.setQualification(qualification);
			bench.setLocation(location);
			bench.setIs_shift_flexibility(isshiftflexibility);
			if (WorkingMode.Hybrid.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Hybrid);
			} else if (WorkingMode.Remote.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Remote);
			} else if (WorkingMode.WFH.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.WFH);
			} else if (WorkingMode.Onside.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Onside);
			}else if (WorkingMode.Office.toString().contentEquals(working_mode)) {
				bench.setWorking_mode(WorkingMode.Office);
			} else {
				bench.setWorking_mode(WorkingMode.WFH);
			}
			bench.setCreated_by((String) session.getAttribute("email"));
			bench.setResume(resumeName);
			bench.setBench_start_dt(new Date());
			bench.setBench_end_dt(null);
			bench.setBench_status("OPEN");
			bench.setNotice_period(noticeperiod);
			bench.setDt(new Date());
			bench.setStatus(1);
			List<Bench> localList = bench_repos.getByContact(session, contact);
				if (localList == null || localList.isEmpty() ) {
					boolean result = bench_repos.saveBench(bench);
					if (result == true) {
						response.setStatus(true);
						response.setMessage("new bench is created for " + name);
						response.setBody(bench);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						response.setStatus(false);
						response.setMessage("new bench cannot be created");
						response.setBody("Internal server error occured");
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				} else {
					response.setStatus(false);
					response.setMessage("bench already exist");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}else {
				response.setStatus(false);
				response.setMessage("please check resume file format ");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		}
	
	
}


	@Override	
	public ResponseEntity<ResponseDto> searchBench(HttpSession session, String token, String benchId, String partner_id,
			String name, String contact, String email, String min_exp,String max_exp, String domain, String bench_type, List<String> primary_skill,
			String secondary_skill,String min_budget,String max_budget,String salary,String org_name,String org_url, String current_role, String qualification,String is_shift_flexibility,String location, String working_mode,String bench_start_dt) {
		// TODO Auto-generated method stub
		ResponseDto response = new ResponseDto();

		msRequestFilter.validateToken(session, token);
		List<Bench> list = new ArrayList<Bench>();
		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			
		}
		
		try {
		if (!benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()  
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				
			long bench_id = 0;
					try {
					bench_id = Long.parseLong(benchId);
				} catch (Exception e) {
					response.setMessage("Enter valid Bench id !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByBenchId(session,bench_id);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && !partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				long partnerid = 0;
				try {
					partnerid = Long.parseLong(partner_id);
			} catch (Exception e) {
				response.setMessage("Enter valid Bench id !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

				
				list = bench_repos.getByPartnerId(session, partnerid);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && !name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getByBenchName(session, name);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && !contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getByContact(session, contact);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && !email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getByEmail(session, email);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& !min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				double minexp = 0;
				
				try {
					minexp = Double.parseDouble(min_exp);
					
				} catch (Exception e) {
					response.setMessage("Enter valid Min Exp !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				
				list = bench_repos.getByMinExp(session, minexp);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && !max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				double maxexp = 0;
				try {
					maxexp = Double.parseDouble(max_exp);
				} catch (Exception e) {
					response.setMessage("Enter valid Max Exp !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByMaxExp(session, maxexp);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && !domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getByDomain(session, domain);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && !bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getByBenchType(session, bench_type);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && !primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				for(String primarh :primary_skill) {
				list = bench_repos.getBypSkill(session, primarh);
				}
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && !secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				list = bench_repos.getBysSkill(session, secondary_skill);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& !min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				double minbudgets = 0;
				try {
					minbudgets = Double.parseDouble(min_budget);
				} catch (Exception e) {
					response.setMessage("Enter valid Min Budget !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByMinBudget(session, minbudgets);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && !max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				double maxbudgets = 0;
				try {
					maxbudgets = Double.parseDouble(max_budget);
				} catch (Exception e) {
					response.setMessage("Enter valid Max Budget !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByMaxBudget(session, maxbudgets);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
			else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank()  && !salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				double salaries = 0;
				try {
					salaries = Double.parseDouble(salary);
				} catch (Exception e) {
					response.setMessage("Enter valid Salary !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getBySalary(session, salaries);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank()  && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank()  && salary.isBlank() && !org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByOrgName(session, org_name);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank()  && salary.isBlank() && org_name.isBlank() && !org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByOrgUrl(session, org_url);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && !current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByCurrentRole(session, current_role);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && !qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByQualification(session, qualification);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}	

			}else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && !is_shift_flexibility.isBlank() && location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {
				long isshiftflexibility = 0;
				try {
					isshiftflexibility = Long.parseLong(is_shift_flexibility);
			} catch (Exception e) {
				response.setMessage("Enter valid Bench id !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
				list = bench_repos.getByIs_shift_flexibility(session, isshiftflexibility);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}	

			}
			else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && !location.isBlank() && working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByLocation(session, location);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}	

			}else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && !working_mode.isBlank() && bench_start_dt.isBlank()) {

				list = bench_repos.getByWorkingMode(session, working_mode);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}	

			} else if (benchId.isBlank() && partner_id.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank() 
					&& min_exp.isBlank() && max_exp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isEmpty() && secondary_skill.isBlank()
					&& min_budget.isBlank() && max_budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank() && is_shift_flexibility.isBlank()  && location.isBlank() && working_mode.isBlank() && !bench_start_dt.isBlank()) {

				list = bench_repos.getByStartDt(session, bench_start_dt);
				if (list == null || list.isEmpty()) {
					response.setMessage("Does not found !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Searched  Bench !");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}	

			} else {
				response.setMessage("please enter atleast one field ");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		
	}
	@Override
	public ResponseEntity<ResponseDto> fetchAllBenches(HttpSession session, String token) {
		// TODO Auto-generated method stub
		msRequestFilter.validateToken(session,token);
		ResponseDto response = new ResponseDto();
     
		try {
			if (session.getAttribute("isSession") != "true") {
				System.out.println(session.getAttribute("isSession"));
				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		try {
			List<Bench> list = bench_repos.getAllBenchs(session);
			if (list == null || list.isEmpty()) {
				response.setMessage("Bench does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e);
			response.setMessage("Internal server error !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}


	
}
