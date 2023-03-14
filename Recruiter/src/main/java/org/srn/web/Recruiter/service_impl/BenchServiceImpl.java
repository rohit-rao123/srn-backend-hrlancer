package org.srn.web.Recruiter.service_impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import org.srn.web.Recruiter.entity.Company;
import org.srn.web.Recruiter.entity.Profiles;
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


	@Override
	public ResponseEntity<ResponseDto> addNewBench(HttpSession session, String token,String name,
			String contact, String alternateContact, String email, String alternateEmail, String xp, String domain, String bench_type,
			String primarySkill, String secondarySkill,String budget,String salary,String org_name,String org_url, String currentRole, String qualification,MultipartFile resume) {
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
			//	long client_id = 0;
		double exp = 0;
		double budgetes = 0;
		double salaries = 0;
		//int status = 0;
		//Date benchStartDtDate = null;
		//Date benchEndDtDate = null;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		
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
			else if (resume == null) {
			response.setStatus(false);
			response.setMessage("resume file is mandatory");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			if (uploadFile(resume) == true) {
				String resumeName = resume.getOriginalFilename();
				//System.out.println(resumeName);
				
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
				

			Bench bench = new Bench();
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
			bench.setCreated_by((String) session.getAttribute("email"));
			bench.setResume(resumeName);
			bench.setDt(new Date());
			bench.setStatus(1);
			List<Bench> localList = bench_repos.getByContact(session, contact);
				if (localList.isEmpty() || localList == null) {
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

			} else {
				response.setStatus(false);
				response.setMessage("please check resume file format ");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		}
	}

	@Override
	public ResponseEntity<ResponseDto> searchBench(HttpSession session, String token, String benchId,
			String name, String contact, String email, String xp, String domain, String bench_type, String primary_skill,
			String secondary_skill,String budget,String salary,String org_name,String org_url, String current_role, String qualification) {
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
		if (!benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
				
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
			} else if (benchId.isBlank() && !name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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

			} else if (benchId.isBlank() && name.isBlank() && !contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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
			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && !email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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

			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& !xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
				double exp = 0;
				try {
					exp = Double.parseDouble(xp);
				} catch (Exception e) {
					response.setMessage("Enter valid Exp !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByExp(session, exp);
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

			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && !domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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

			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && !bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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

			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && !primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
				list = bench_repos.getBypSkill(session, primary_skill);
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

			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && !secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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
			}else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& !budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
				double budgets = 0;
				try {
					budgets = Double.parseDouble(budget);
				} catch (Exception e) {
					response.setMessage("Enter valid Budget !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
				list = bench_repos.getByBudget(session, budgets);
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
			}else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && !salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {
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
			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && !org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {

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
			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && !org_url.isBlank() && current_role.isBlank() && qualification.isBlank()) {

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
			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && !current_role.isBlank() && qualification.isBlank()) {

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
			} else if (benchId.isBlank() && name.isBlank() && contact.isBlank() && email.isBlank()
					&& xp.isBlank() && domain.isBlank() && bench_type.isBlank() && primary_skill.isBlank() && secondary_skill.isBlank()
					&& budget.isBlank() && salary.isBlank() && org_name.isBlank() && org_url.isBlank() && current_role.isBlank() && !qualification.isBlank()) {

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
		ResponseDto response = new ResponseDto();
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
			response.setMessage("Internal server error !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}
}
