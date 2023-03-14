package org.srn.web.Recruiter.service_impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.component.FileHelper;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.Company_dao_impl;
import org.srn.web.Recruiter.dao_impl.Jobs_dao_impl;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Company;
import org.srn.web.Recruiter.entity.Jobs;
import org.srn.web.Recruiter.entity.Profiles;
import org.srn.web.Recruiter.service.Profile_Service;

@Transactional
@Service

public class Profile_Service_Impl implements Profile_Service {

	@Autowired
	private Profile_dao_impl profile_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private Company_dao_impl company_repos;
	
	@Autowired
	private Jobs_dao_impl job_repos;
	
	@Autowired
	private Application_dao_impl application_repos;

	@Override
	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long profile_id) {
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
		List<Profiles> prof = profile_repos.fetchById(profile_id);
		try {
			if (profile_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof == null || prof.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(prof);
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

	@Override
	public ResponseEntity<ResponseDto> fetchAllRecords(HttpSession session, String token) {
		msRequestFilter.validateToken(session, token);
		try {
			if (session.getAttribute("isSession") != "true") {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please login!");
				response.setBody(null);
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
		long partner_id = (long) session.getAttribute("partner_id");
		List<Profiles> list = profile_repos.findAllProfile(partner_id);

		try {
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
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

	@Override
	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Profiles prof) {
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
			if (prof.getEmail().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getContact().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getProf_creation_date() == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Prof_creation_date"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getName().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "name"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getAlternate_contact().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Alternate_contact"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getAlternate_email().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Alternate_email"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getExp() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Exp"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getCtc() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Ctc"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getEctc() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Ectc"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getNotice_period() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Notice_period"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getCompany().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "company"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getCompany_id() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "company_id"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getCurrent_role().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "current_role"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getQualification().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "qualification"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getYear_of_passing() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "year_of_passing"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getStatus() != 0 && prof.getStatus() != 1) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "status"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getLocation().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "location"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getDomain().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "domain"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getSecondary_skill().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "secondary_skill"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (prof.getPrimary_skill().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "primary_skill"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				List<Profiles> list1 = profile_repos.getProfileByContactAndEmail(prof.getContact(), prof.getEmail());
				if (list1 == null || list1.isEmpty()) {
					Date date = new Date();
					prof.setProf_creation_date(date);
					boolean check = profile_repos.newProfile(prof);

					if (check == true) {
						System.out.println("New Profile Saved with id " + prof.getProfile_id());
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.CREATED);
						response.setBody(profile_repos.getProfileByContactAndEmail(prof.getContact(), prof.getEmail()));
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						System.out.println("Profile is not saved due to internal server error");
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.Error);
						response.setBody(null);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				} else {
					System.out.println("This Profile is already exist with name  " + prof.getName());
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Exist);
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			ResponseDto response = new ResponseDto();
			response.setStatus(false);
			response.setMessage("Profile is not saved, due to exception");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
	}

	@Override
	public ResponseEntity<ResponseDto> eraseByProfileId(HttpServletRequest request, long profile_id) {
		boolean check = profile_repos.deleteProfileById(profile_id);
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
			if (profile_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + profile_id);
				response.setBody(null);
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
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

//	@Override
//	public ResponseEntity<ResponseDto> editProfileRecordById(long identity, String name, String contact, String email,
//			String ctc, String ectc, String current_role, String company, String location, String resume, String domain,
//			String primary_skill, String secondary_skill, long comapny_id, Date dt, String status,
//			String alternate_contact, String alternate_email, String qualification, String year_of_passing) {
//		dt = getCurrentDate();
//		System.out.println(dt);
//		boolean check = profile_repos.updateProfileById(identity, name, contact, email, ctc, ectc, current_role,
//				company, location, resume, domain, primary_skill, secondary_skill, company_id, dt, status,
//				alternate_contact, alternate_email, qualification, year_of_passing);
//		System.out.println(check);
//		try {
//			if (identity <= 0) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.ID_MISSING);
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (name.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "name"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (contact.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (email.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (current_role.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "current_role"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (company.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "company"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (location.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "location"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (qualification.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "qualification"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (resume.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "resume"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (domain.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "skills"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (check == false) {
//
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.UPDATE);
//				response.setBody("  updated record with profile_id :" + identity);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//	}

	@Override
	public ResponseEntity<ResponseDto> fetchByContact(HttpServletRequest request, String contact) {
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
		List<Profiles> list = profile_repos.findByMobile(contact);
		try {
			if (contact.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.CREATED);
				response.setBody(list);
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

//	private double parseDouble(String exp) {
//		double res = Double.parseDouble(exp);
//		return res;
//	}
//
//	private long parseLong(String contact) {
//		String s = contact;
//		long l = Long.parseLong(s);
//		return l;
//	}
//
//	private Date parseDate(String dt) throws ParseException {
//		String sDate1 = dt;
//		java.util.Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
//
//		System.out.println("Util date in Java : " + date1);
//		java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
//		return sqlDate;
//
//	}

	public Date getCurrentDate() {
		LocalDate dateToConvert = LocalDate.now();
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public ResponseEntity<ResponseDto> editName(HttpServletRequest request, long identity, String name) {
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
		if (identity <= 0) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.ID_MISSING);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (name.isBlank()) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", name));
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			boolean check = profile_repos.updateName(identity, name);
			if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.UPDATE);
				response.setBody("updated id :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();
				System.out.print(VMessageConstant.Not_Found);
				response.setMessage(VMessageConstant.Not_Found);
				response.setBody("identity :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}

	}

	@Override
	public ResponseEntity<ResponseDto> editContact(HttpServletRequest request, long identity, String contact) {
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
		if (identity <= 0) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.ID_MISSING);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (contact.isEmpty()) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.FIELD_MISSING);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			boolean check = profile_repos.updateContact(identity, contact);
			if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.UPDATE);
				response.setBody("updated id :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody("identity :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}

	}

	@Override
	public ResponseEntity<ResponseDto> editEmail(HttpServletRequest request, long identity, String email) {
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
		if (identity <= 0) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.ID_MISSING);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (email.isBlank()) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", email));
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			boolean check = profile_repos.updateEmail(identity, email);
			if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.UPDATE);
				response.setBody("updated id :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody("identity :" + identity);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchByEmail(HttpServletRequest request, String email) {
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
		List<Profiles> list = profile_repos.findByEmail(email);
		try {
			if (email == null || email.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.CREATED);
				response.setBody(list);
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

	@Override()
	public boolean inactivateSession(long lastAccessedTime) {
		if ((System.currentTimeMillis() - lastAccessedTime) < 90000) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchProfile(HttpSession session, String token, String mobile_number,
			String email, String name) {
		msRequestFilter.validateToken(session, token);

		try {
			if (session.getAttribute("isSession") != "true") {
				ResponseDto response = new ResponseDto();

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		if (!mobile_number.isEmpty() && email.isEmpty() && name.isEmpty()) {
			List<Profiles> list = profile_repos.findByMobile(session,mobile_number);
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (mobile_number.isEmpty() && !email.isEmpty() && name.isEmpty()) {
			List<Profiles> list = profile_repos.findByEmail(session,email);
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (mobile_number.isEmpty() && email.isEmpty() && !name.isEmpty()) {
			List<Profiles> list = profile_repos.findByName(session,name);
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.Not_Found);
			response.setBody("Invalid input");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<Resource> downloadService(HttpServletRequest request) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Content-Disposition", "attachment; filename=");
//				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//				headers.add("Pragma", "no-cache");
//				headers.add("Expires", "0");
//
//				ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//						.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//				return response;
//			}
//		} catch (Exception e) {
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Disposition", "attachment; filename=");
//			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//			headers.add("Pragma", "no-cache");
//			headers.add("Expires", "0");
//
//			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//					.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//			return response;
//		}

		List<Profiles> list = profile_repos.findAllProfile();
		File file = FileHelper.getFile(list);
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			resource = null;
		}
		System.out.println(file.getName() + "  " + file.length());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + file.getName());
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/plain")).body(resource);

		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> addlist(HttpServletRequest request, MultipartFile file) {
		boolean check = false;
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
			if (file.getSize() == 0) {
				ResponseDto response = new ResponseDto();
				response.setMessage("Given file is empty !");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				check = FileHelper.checkFileFormat(file);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Exception occur in format of file");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		if (check == false) {
			ResponseDto response = new ResponseDto();
			response.setMessage("This file is not of excel format");
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {

			List<Profiles> list = new ArrayList<Profiles>();
			try {
				list = FileHelper.getProfExcelFile(request, file.getInputStream());
			} catch (IOException e1) {
				list = null;
				e1.printStackTrace();

			}

			if (list == null || list.size() == 0) {

				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("file is empty");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				try {
					List<Profiles> locallist = new ArrayList<Profiles>();

					for (int i = 0; i < list.size(); i++) {
						List<Profiles> list1 = profile_repos.getProfileByContactAndEmail(list.get(i).getContact(),
								list.get(i).getEmail());
						if (list1 == null || list1.isEmpty()) {
							check = profile_repos.newProfile(list.get(i));
							System.out.println("New Profile Saved with name  " + list.get(i).getName());
							locallist.add(list.get(i));
						} else {
							System.out.println("This Profile is already exist with name  " + list.get(i).getName());
							continue;
						}
					}
					if (locallist.size() == 0) {
						ResponseDto response = new ResponseDto();
						response.setStatus(true);
						response.setMessage("This file is already injected, so cannot be inject again");
						response.setBody(null);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						response.setStatus(true);
						response.setMessage("All Profile is saved");
						response.setBody(locallist);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				} catch (InternalServerError ie) {
					ie.printStackTrace();
					ResponseDto response = new ResponseDto();
					response.setStatus(false);
					response.setMessage("file is not uploaded, due to internal server error");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();

					ResponseDto response = new ResponseDto();
					response.setStatus(false);
					response.setMessage("file is not uploaded, due to exception");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		}

	}
	/// home/ubuntu/app/internal/recruiter/disk/upload_files

//	public final String UPLOAD_DIR = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\upload_file\\";

	public final String UPLOAD_DIR = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/";

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
	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String domain) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Content-Disposition", "attachment; filename=");
//				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//				headers.add("Pragma", "no-cache");
//				headers.add("Expires", "0");
//
//				ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//						.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//				return response;
//			}
//		} catch (Exception e) {
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Disposition", "attachment; filename=");
//			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//			headers.add("Pragma", "no-cache");
//			headers.add("Expires", "0");
//
//			ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//					.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//			return response;
//		}

		List<Profiles> list = profile_repos.fetchBySkills(domain);
//		System.out.println(list);
		File file = FileHelper.getFile(list);
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			resource = null;
		}
		System.out.println(file.getName() + "  " + file.length());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + file.getName());
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/plain")).body(resource);

		return response;
	}

	@Override
	public ResponseEntity<ResponseDto> edit(HttpServletRequest request, long profile_id, String email, String contact,
			String name, String location) {
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
			if (profile_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				if (!contact.isEmpty() && email.isEmpty() && name.isEmpty() && location.isEmpty()) {
					boolean check = profile_repos.updateProfileById(profile_id, name, contact, email, location);
					if (check == true) {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.UPDATE);
						response.setBody("updated id :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						System.out.print(VMessageConstant.Not_Found);
						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("identity :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				} else if (contact.isEmpty() && !email.isEmpty() && name.isEmpty() && location.isEmpty()) {
					boolean check = profile_repos.updateProfileById(profile_id, name, contact, email, location);
					if (check == true) {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.UPDATE);
						response.setBody("updated id :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						System.out.print(VMessageConstant.Not_Found);
						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("identity :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				} else if (contact.isEmpty() && email.isEmpty() && !name.isEmpty() && location.isEmpty()) {
					boolean check = profile_repos.updateProfileById(profile_id, name, contact, email, location);
					if (check == true) {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.UPDATE);
						response.setBody("updated id :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						System.out.print(VMessageConstant.Not_Found);
						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("identity :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				} else if (contact.isEmpty() && email.isEmpty() && name.isEmpty() && !location.isEmpty()) {
					boolean check = profile_repos.updateProfileById(profile_id, name, contact, email, location);
					if (check == true) {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.UPDATE);
						response.setBody("updated id :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						System.out.print(VMessageConstant.Not_Found);
						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("identity :" + profile_id);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody("Invalid input");
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> add_profile(HttpServletRequest request, long company_id, String name,
			String contact, String email, long ctc, long ectc, String current_role, String company, String location,
			String domain, String primary_skill, String secondary_skill, long exp, String alternate_contact,
			String alternate_email, String qualification, int year_of_passing, int notice_period, String resume) {
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
			Profiles prof = new Profiles();
			List<Profiles> list1 = profile_repos.getProfileByContactAndEmail(contact, email);
			if (list1 == null || list1.isEmpty()) {
				Date date = new Date();
				prof.setProf_creation_date(date);
				prof.setStatus(1);
				if (alternate_contact.isBlank() || alternate_contact == "NA") {
					prof.setAlternate_contact("NA");
				} else {
					prof.setAlternate_contact(alternate_contact);
				}
				if (alternate_email.isBlank() || alternate_email == "NA") {
					prof.setAlternate_email("NA");
				} else {
					prof.setAlternate_email(alternate_email);
				}
				prof.setCompany(company);
				prof.setCompany_id(company_id);
				prof.setContact(contact);
				prof.setCtc(ctc);
				prof.setCurrent_role(current_role);
				prof.setDomain(domain);
				prof.setEctc(ectc);
				prof.setEmail(email);
				prof.setExp(exp);
				prof.setCreated_by((String) request.getSession().getAttribute("userName"));
				if (location.isBlank() || location == "NA") {
					prof.setLocation("NA");
				} else {
					prof.setLocation(location);
				}
				prof.setName(name);
				prof.setNotice_period(notice_period);
				prof.setPrimary_skill(primary_skill);

				if (qualification.isBlank() || qualification == "NA") {
					prof.setQualification("NA");
				} else {
					prof.setQualification(qualification);
				}

				prof.setSecondary_skill(secondary_skill);

				if (year_of_passing == 0) {
					prof.setYear_of_passing(0);
				} else {
					prof.setYear_of_passing(year_of_passing);
				}

				if (resume.isBlank() || resume == "NA") {
					prof.setResume("NA");
				} else {
					prof.setResume(resume);
				}
				boolean check = profile_repos.newProfile(prof);

				if (check == true) {
					System.out.println("New Profile Saved with id " + prof.getProfile_id());
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.CREATED);
					response.setBody(profile_repos.getProfileByContactAndEmail(prof.getContact(), prof.getEmail()));
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
//					System.out.println("Profile is not saved due to internal server error");
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Error);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else {
				System.out.println("This Profile is already exist with name  " + prof.getName());
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Exist);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			ResponseDto response = new ResponseDto();
			response.setStatus(false);
			response.setMessage("Profile is not saved, due to exception");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
	}

	@Override
	public ResponseEntity<ResponseDto> saveProfile(HttpSession session, String token, String name,
			String contact, String email, double ctc, double ectc, String current_role, String company, String location,
			String domain, String primary_skill, String secondary_skill, double exp, String alternate_contact,
			String alternate_email, String qualification, int year_of_passing, int notice_period,
			MultipartFile resume) {

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

		 if (name.isBlank() || name == null) {
			response.setMessage("name is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (contact.isBlank() || contact == null) {
			response.setMessage("contact is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (email.isBlank() || email == null) {
			response.setMessage("email is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (ctc == 0) {
			response.setMessage("ctc is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (ectc == 0) {
			response.setMessage("ectc is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (current_role.isBlank() || current_role == null) {
			response.setMessage("current_role is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (company.isBlank() || company == null) {
			response.setMessage("company is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (location.isBlank() || location == null) {
			response.setMessage("location is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (domain.isBlank() || domain == null) {
			response.setMessage("domain is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (primary_skill.isBlank() || primary_skill == null) {
			response.setMessage("primary_skill is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (secondary_skill.isBlank() || secondary_skill == null) {
			response.setMessage("secondary_skill is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (exp == 0) {
			response.setMessage("exp is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (qualification.isBlank() || qualification == null) {
			response.setMessage("qualification is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (notice_period <= 0) {
			response.setMessage("notice_period is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (resume == null) {
			response.setStatus(false);
			response.setMessage("resume file is mandatory");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			if (uploadFile(resume) == true) {
				Profiles profile = new Profiles();
				Company cmpny = company_repos.findCompanyByName(company);
				if(cmpny==null) {
					profile.setCompany_id(0);
				}else {
					profile.setCompany_id(cmpny.getCompany_id());
				}
				String resumeName = resume.getOriginalFilename();
				System.out.println(resumeName);
				profile.setCompany(company);
				profile.setName(name);
				profile.setContact(contact);
				profile.setAlternate_contact(alternate_contact);
				profile.setEmail(email);
				profile.setAlternate_email(alternate_email);
				profile.setDomain(domain);
				profile.setCtc(ctc);
				profile.setEctc(ectc);
				profile.setExp(exp);
				profile.setLocation(location);
				profile.setCurrent_role(current_role);
				profile.setNotice_period(notice_period);
				profile.setQualification(qualification);
				profile.setPrimary_skill(primary_skill);
				profile.setSecondary_skill(secondary_skill);
				profile.setYear_of_passing(year_of_passing);
				profile.setStatus(1);
				profile.setResume(resumeName);
				profile.setPartner_id((long) session.getAttribute("partner_id"));
				profile.setCreated_by((String) session.getAttribute("email"));
				profile.setProf_creation_date(new Date());
				List<Profiles> localList = profile_repos.getByEmailAndContact(email, contact);
				if (localList.isEmpty() || localList == null) {
					boolean check = profile_repos.saveProfile(profile);
					if (check == true) {
						response.setStatus(true);
						response.setMessage("new profile is created for " + name);
						response.setBody(profile);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						response.setStatus(false);
						response.setMessage("new profile cannot be created");
						response.setBody("Internal server error occured");
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				} else {
					response.setStatus(false);
					response.setMessage("Candidate profile already exist");
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
	public Profiles createProfile(HttpSession session, String token, long company_id, String name, String contact,
			String email, double ctc, double ectc, String current_role, String company, String location, String domain,
			String primary_skill, String secondary_skill, double exp, String alternate_contact, String alternate_email,
			String qualification, int year_of_passing, int notice_period, MultipartFile resume) {
		msRequestFilter.validateToken(session, token);
		try {
			boolean check = false;
			String resumeName = resume.getOriginalFilename();
			System.out.println(resumeName);
			Profiles profile = new Profiles();
			profile.setCompany_id(company_id);
			profile.setCompany(company);
			profile.setName(name);
			profile.setContact(contact);
			profile.setAlternate_contact(alternate_contact);
			profile.setEmail(email);
			profile.setAlternate_email(alternate_email);
			profile.setDomain(domain);
			profile.setCtc(ctc);
			profile.setEctc(ectc);
			profile.setExp(exp);
			profile.setLocation(location);
			profile.setCurrent_role(current_role);
			profile.setNotice_period(notice_period);
			profile.setQualification(qualification);
			profile.setPrimary_skill(primary_skill);
			profile.setSecondary_skill(secondary_skill);
			profile.setYear_of_passing(year_of_passing);
			profile.setStatus(1);
			profile.setResume(resumeName);
			profile.setPartner_id(0);
			profile.setCreated_by((String) session.getAttribute("email"));
			profile.setProf_creation_date(new Date());
			List<Profiles> profileList = profile_repos.getProfileByContactAndEmail(contact, email);
			boolean fileChecker = uploadFile(resume);
			if (profileList.isEmpty() || profileList == null) {
				check = profile_repos.saveProfile(profile);
				if (check == true && fileChecker == true) {
					return profile;
				} else {
					return null;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fileDeleter(HttpServletRequest request, String filename) {
		String filepath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/";
//		String filepath="D:\\ECLIPSE WORKSPACE\\Recruiter_ms_space\\Recruiter\\src\\main\\resources\\Upload_Files\\";
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
			File f = new File(filepath + filename); // file to be delete
			if (f.delete()) // returns Boolean value
			{
				System.out.println(f.getName() + " deleted"); // getting and printing the file name
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("Resume is deleted succesfully");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			} else {
				System.out.println("failed");
				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("Resume is not exist");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setStatus(true);
			response.setMessage("Internal server error");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseDto> addAppWithProfileCreating(HttpSession session, String token, String name,
			String contact, String email, double ctc, double ectc, String current_role, String company, String location,
			String domain, String primary_skill, String secondary_skill, double exp, String alternate_contact,
			String alternate_email, String qualification, int year_of_passing, int notice_period, long job_id,
			int is_ectc_nego, double rel_exp, String comment, MultipartFile resume) {
		// TODO Auto-generated method stub
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();
		boolean checkProfile = false;
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

		if (name.isBlank() || name == null) {
			response.setMessage("name is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else	if (contact.isBlank() || contact == null) {
			response.setMessage("contact is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else	if (email.isBlank() || email == null) {
			response.setMessage("email is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (ctc == 0) {
			response.setMessage("ctc is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (ectc == 0) {
			response.setMessage("ectc is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (current_role.isBlank() || current_role == null) {
			response.setMessage("current_role is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (company.isBlank() || company == null) {
			response.setMessage("company is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (location.isBlank() || location == null) {
			response.setMessage("location is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (domain.isBlank() || domain == null) {
			response.setMessage("domain is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (primary_skill.isBlank() || primary_skill == null) {
			response.setMessage("primary_skill is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else	if (secondary_skill.isBlank() || secondary_skill == null) {
			response.setMessage("secondary_skill is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (exp == 0) {
			response.setMessage("exp is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (qualification.isBlank() || qualification == null) {
			response.setMessage("qualification is mandatory");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (notice_period <= 0) {
			response.setMessage("notice_period is mandatory !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (job_id == 0) {
			response.setStatus(false);
			response.setMessage("job_id is mandatory !");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (is_ectc_nego == -1) {
			response.setMessage("Is_ectc_nego is mandatory !");
			response.setBody("enter either 1 or 0");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if (is_ectc_nego != 0 && is_ectc_nego != 1 ) {
			response.setMessage("Is_ectc_nego is invalid !");
			response.setBody("enter either 1 or 0");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else if (rel_exp == 0) {
			response.setStatus(false);
			response.setMessage("rel_exp  is mandatory !");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		else 	if (resume == null) {
			response.setStatus(false);
			response.setMessage("resume file is mandatory !");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}else {
			try {
				Profiles profile = new Profiles();
				Company cmpny = company_repos.findCompanyByName(company);
				if (cmpny == null) {
					profile.setCompany_id(0);
				} else {
					profile.setCompany_id(cmpny.getCompany_id());
				}
				profile.setCompany(company);
				profile.setName(name);
				profile.setContact(contact);
				profile.setAlternate_contact(alternate_contact);
				profile.setEmail(email);
				profile.setAlternate_email(alternate_email);
				profile.setDomain(domain);
				profile.setCtc(ctc);
				profile.setEctc(ectc);
				profile.setExp(exp);
				profile.setLocation(location);
				profile.setCurrent_role(current_role);
				profile.setNotice_period(notice_period);
				profile.setQualification(qualification);
				profile.setPrimary_skill(primary_skill);
				profile.setSecondary_skill(secondary_skill);
				profile.setYear_of_passing(year_of_passing);
				profile.setStatus(1);
				profile.setResume(resume.getOriginalFilename());
				profile.setPartner_id((long) session.getAttribute("partner_id"));
				profile.setCreated_by((String) session.getAttribute("email"));
				profile.setProf_creation_date(new Date());
				List<Profiles> list = profile_repos.getProfileByContactAndEmail(contact, email);
				if (list.isEmpty() || list == null) {
					checkProfile = profile_repos.saveProfile(profile);
					if(checkProfile==true && uploadFile(resume)==true) {
						Profiles prof = new Profiles();
					try {
						prof =	profile_repos.getByMobile(contact);

					}catch(Exception e) {
						e.printStackTrace();
						response.setMessage("Profile_id  does not found !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
					System.out.println(prof.toString());
					Jobs job = (Jobs) job_repos.getById(job_id);
					if (job == null) {
						response.setMessage("Job_id does not found !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						Applications app = new Applications();
						Date date = new Date();
						String cvDir = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/"
								+ prof.getResume();
						app.setStatus(1);
						app.setCreated_by((String) session.getAttribute("email"));
						app.setPartner_id((long) session.getAttribute("partner_id"));
						app.setDt(date);
						app.setClient_id(job.getClient_id());
						app.setJob_id(job_id);
						app.setProfile_id(prof.getProfile_id());
						app.setEctc(prof.getEctc());
						app.setNotice(notice_period);;
						app.setIs_ectc_nego(is_ectc_nego);
						app.setCv_path(cvDir);
						app.setProgress("APPLIED");
						app.setRel_exp(rel_exp);
						app.setComment(comment);
						List<Applications> appList = application_repos.fetchByJob_idAndProfile_id(job_id, prof.getProfile_id());
						
						if (!appList.isEmpty()) {
							response.setMessage("Duplicate application cannot be added !");
							response.setBody(null);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							boolean check = application_repos.saveApplication(app);
							if (check == true ) {
								System.out.println("Application_id is saved = " + app.getApplication_id());
								response.setMessage("Application added succesfully !");
								response.setBody(app);
								response.setStatus(true);
								return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
							} else {
								System.out.println("unable to save due to internal server error");
								response.setMessage("Application is not created !");
								response.setBody(null);
								response.setStatus(false);
								return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
							}
						}
					}}
				}else {
					response.setMessage("profile already exist ,cannot create duplicate!");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Internal server error !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		}
		return null;

		

	}

}
