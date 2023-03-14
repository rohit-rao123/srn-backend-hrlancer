package org.srn.web.Recruiter.service_impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.component.FileHelper;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.Followups_dao_impl;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Followups;
import org.srn.web.Recruiter.entity.Profiles;
import org.srn.web.Recruiter.service.Followups_Service;

@Service
@Transactional
public class Followups_Service_Impl implements Followups_Service {

	@Autowired
	private Followups_dao_impl followups_repos;

	@Autowired
	private Application_dao_impl app_repos;

	@Autowired
	private Profile_dao_impl prof_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Override
	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long followup_id) {
		List<Followups> list = followups_repos.fetchById(followup_id);
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
			if (followup_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list == null || list.isEmpty()) {
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

//	public ResponseEntity<ResponseDto> fetchByMobileEmail(HttpSession session,String token, String contact, String email) {
////		List<Followups> list =new ArrayList<Followups>();
//		msRequestFilter.validateToken(session, token);
//		ResponseDto response = new ResponseDto();
//		try {
//			if (session.getAttribute("isSession") != "true") {
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//		try {
//			if (contact.isBlank()==false && email.isBlank()) {
//				List<Followups> list =new ArrayList<Followups>();
//
//				list =	followups_repos.fetchByMobile(contact);
//				if(list== null) {
//					response.setMessage("Does not found");
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}else {
//					response.setMessage("searched followups");
//					response.setBody(list);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//
//				
//			} else if (contact.isBlank() && email.isBlank()==false) {
//				List<Followups> list =new ArrayList<Followups>();
//
//				list =	followups_repos.fetchByEmails(email);
//				if(list== null) {
//					response.setMessage("Does not found");
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}else {
//					response.setMessage("searched followups");
//					response.setBody(list);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			}else if (contact.isBlank()==false && email.isBlank()==false) {
//				List<Followups> list =new ArrayList<Followups>();
//
//				list =	followups_repos.fetchByEmailsAndContact(contact,email);
//				if(list== null) {
//					response.setMessage("Does not found");
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}else {
//					response.setMessage("searched followups");
//					response.setBody(list);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			} else {
//
//				response.setMessage("Invalid input !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//	}

	public ResponseEntity<ResponseDto> fetchByMobileEmail(HttpSession session, String token, String contact,
			String email) {
		ResponseDto response = new ResponseDto();
		msRequestFilter.validateToken(session, token);
		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
		List<Followups> followups = new ArrayList<>();
		
		if (!contact.isBlank() && email.isBlank()) {
			followups = followups_repos.fetchByMobile(session,contact);
		} else if (contact.isBlank() && !email.isBlank()) {
			followups = followups_repos.fetchByEmails(session,email);
		} else if (contact.isBlank() && email.isBlank()) {
			System.out.println("service layer access");
			followups = followups_repos.fetchAll(session);
		}
		if ( followups==null ||  followups.isEmpty()) {
			response.setMessage("Follow-ups not found");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			response.setMessage("Follow-ups found");
			response.setBody(followups);
			response.setStatus(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	

	@Override
	public ResponseEntity<ResponseDto> fetchByMultipleOption(HttpServletRequest request, long job_id, String job_name,
			long application_id, int client_id, String contact, String email, String name, long profile_id,
			String comment, String followup_status, String followup_creation_date) {
		System.out.println("Service");
		List<Followups> list = followups_repos.fetchByMultipleParam(job_id, job_name, application_id, client_id,
				contact, email, name, profile_id, comment, followup_status, followup_creation_date);
		ResponseDto response = new ResponseDto();
		if (list == null) {
			System.out.println(list);
			response.setMessage(VMessageConstant.Not_Found);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else if ((!list.isEmpty())) {
			response.setMessage(VMessageConstant.MESSAGE);
			response.setBody(list);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {
			response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", " given field"));
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
		List<Followups> list = followups_repos.findAllFollowups(session);
		try {
			if (list == null || list.isEmpty()) {
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
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Followups follow) {
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
			if (follow.getApplication_id() <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "application_id"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (follow.getProfile_id() <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "profile_id"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (follow.getComment().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "comment"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Date date = new Date();
				follow.setFollowup_creation_date(date);
				long x = followups_repos.saveFollowups(follow);

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.CREATED);
				response.setBody(x);
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
	public ResponseEntity<ResponseDto> eraseByFollowupId(HttpServletRequest request, long followup_id) {
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
		boolean check = followups_repos.deleteFollowupsById(followup_id);
		try {
			if (followup_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + followup_id);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
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

	}

	@Override
	public ResponseEntity<ResponseDto> editFollowupsRecordById(HttpServletRequest request, long id, String active,
			String comment, String status) {
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
		String updated_by = (String) request.getSession().getAttribute("userName");
		boolean check = followups_repos.updateFollowupsById(updated_by, id, comment, status, active);
		try {
			if (id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (comment.isBlank()) {
				ResponseDto response = new ResponseDto();
				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "comment"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (status.isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "status"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (active.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "active"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == false) {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.UPDATE);
				response.setBody(id);
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
	public ResponseEntity<ResponseDto> fetchByAId(HttpServletRequest request, long application_id) {
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
		List<Followups> list = followups_repos.fetchByAId(application_id);
		try {
			if (application_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "application_id"));
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list == null) {
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
	public ResponseEntity<ResponseDto> editFollowupsById(HttpServletRequest request, long profile_id, String active,
			String comment, String status) {
		/*
		 * try { if (request.getSession().getAttribute("userName") == null) {
		 * ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage("Please login!"); response.setBody(null);
		 * response.setStatus(true); return new ResponseEntity<ResponseDto>(response,
		 * HttpStatus.OK); } } catch (Exception e) { ResponseDto response = new
		 * ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.DEFAULT); response.setBody(null);
		 * response.setStatus(false); return new ResponseEntity<ResponseDto>(response,
		 * HttpStatus.OK); } try { if (profile_id <= 0) { ResponseDto response = new
		 * ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.ID_MISSING);
		 * response.setBody("unprocessable input"); response.setStatus(false); return
		 * new ResponseEntity<ResponseDto>(response, HttpStatus.OK); } else {
		 * List<Profile> list = prof_repos.fetchById(profile_id); if (list == null ||
		 * list.isEmpty()) { ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.Not_Found);
		 * response.setBody("identity :" + profile_id); response.setStatus(true); return
		 * new ResponseEntity<ResponseDto>(response, HttpStatus.OK); } else { if
		 * (!active.isEmpty() && comment.isEmpty() && status.isEmpty()) { boolean check
		 * = followups_repos.updateFollowupsById(profile_id, comment, status, active);
		 * if (check == true) { ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.UPDATE); response.setBody("updated id :"
		 * + profile_id); response.setStatus(true); return new
		 * ResponseEntity<ResponseDto>(response, HttpStatus.OK); } else { ResponseDto
		 * response = new ResponseDto(); System.out.print(VMessageConstant.Not_Found);
		 * response.setMessage(VMessageConstant.Not_Found);
		 * response.setBody("identity :" + profile_id); response.setStatus(true); return
		 * new ResponseEntity<ResponseDto>(response, HttpStatus.OK); } } else if
		 * (active.isEmpty() && !comment.isBlank() && status.isBlank()) { boolean check
		 * = followups_repos.updateFollowupsById(profile_id, comment, status, active);
		 * if (check == true) { ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.UPDATE); response.setBody("updated id :"
		 * + profile_id); response.setStatus(true); return new
		 * ResponseEntity<ResponseDto>(response, HttpStatus.OK); } else { ResponseDto
		 * response = new ResponseDto(); System.out.print(VMessageConstant.Not_Found);
		 * response.setMessage(VMessageConstant.Not_Found);
		 * response.setBody("identity :" + profile_id); response.setStatus(true); return
		 * new ResponseEntity<ResponseDto>(response, HttpStatus.OK); } } else if
		 * (active.isEmpty() && comment.isBlank() && !status.isBlank()) { boolean check
		 * = followups_repos.updateFollowupsById(profile_id, comment, status, active);
		 * if (check == true) { System.out.println("sanskar"); ResponseDto response =
		 * new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.UPDATE); response.setBody("updated id :"
		 * + profile_id); response.setStatus(true); return new
		 * ResponseEntity<ResponseDto>(response, HttpStatus.OK); } else { ResponseDto
		 * response = new ResponseDto(); System.out.print(VMessageConstant.Not_Found);
		 * response.setMessage(VMessageConstant.Not_Found);
		 * response.setBody("identity :" + profile_id); response.setStatus(true); return
		 * new ResponseEntity<ResponseDto>(response, HttpStatus.OK); } } else {
		 * ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.Not_Found);
		 * response.setBody("Invalid input"); response.setStatus(false); return new
		 * ResponseEntity<ResponseDto>(response, HttpStatus.OK); } } } } catch
		 * (Exception e) { ResponseDto response = new ResponseDto();
		 * 
		 * response.setMessage(VMessageConstant.DEFAULT); response.setBody(null);
		 * response.setStatus(false); return new ResponseEntity<ResponseDto>(response,
		 * HttpStatus.OK); }
		 */
		return null;
	}

	@Override
	public ResponseEntity<ResponseDto> fetchByClId(HttpServletRequest request, int client_id) {
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
		List<Followups> list = followups_repos.fetchByClId(client_id);
		try {
			if (client_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "client_id"));
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
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
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<ResponseDto> fetchByMobile(HttpServletRequest request, String contact) {
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
		List<Followups> list = followups_repos.fetchByMobile(contact);
		try {
			if (contact.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
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
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}
//
//	@Override
//	public ResponseEntity<ResponseDto> fetchByMultipleOption(long job_id, String job_name, long application_id,
//			int client_id, String contact, String email, String name, long profile_id, String comment, String followup_status ,Date  followup_creation_date) {
//		System.out.println("Service");
//
//		List<Followups> list = followups_repos.fetchByMultipleParam(job_id, job_name, application_id, client_id,
//				contact, email, name, profile_id, comment ,followup_status , followup_creation_date);
//		ResponseDto response = new ResponseDto();
//		if (list == null) {
//			response.setMessage(VMessageConstant.Not_Found);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if ((!list.isEmpty())) {
//			response.setMessage(VMessageConstant.MESSAGE);
//			response.setBody(list);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", " given field"));
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

	public ResponseEntity<ResponseDto> fetchByComment(HttpServletRequest request, String comment) {
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
		List<Followups> list = followups_repos.fetchByComment(comment);
		try {
			if (comment.isEmpty()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "comment"));
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
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
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String comment) {

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
		List<Followups> list = followups_repos.fetchByComment(comment);
		File file = FileHelper.getFollowupFile(list);
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

	public ResponseEntity<Resource> downloadAllServices(HttpServletRequest request) {
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
		List<Followups> list = followups_repos.findAllFollowups();
		File file = FileHelper.getFollowupFile(list);
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
//
//	@Override
//	public ResponseEntity<ResponseDto> add_followup(String comment, String followup_status, long application_id,
//			String contact, String email) {
//
//		if (application_id != 0 && contact.isBlank() && email.isBlank()) {
//			Application app = app_repos.getAppById(application_id);
//			if (app == null || application_id <= 0) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "application_id"));
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				Followups followups = new Followups();
//				followups.setApplication_id(application_id);
//				followups.setProfile_id(app.getProfile_id());
//				followups.setJob_id(app.getJob_id());
//				followups.setActive(app.getActive());
//				followups.setContact(app.getContact());
//				followups.setEmail(app.getEmail());
//				followups.setComment(comment);
//				followups.setStatus("1");
//				Date date = new Date();
//				followups.setFollowup_creation_date(date);
//				long x = followups_repos.saveFollowups(followups);
//
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.CREATED);
//				response.setBody("Generated followup id = " + x);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else if (application_id == 0 && !contact.isBlank() && email.isBlank()) {
//			List<Followups> locallist = new ArrayList<Followups>();
//			
//			if(!app_repos.getByContact(contact).isEmpty() || app_repos.getByContact(contact) != null) {
//	
//				for(Application application : app_repos.getByContact(contact)) {
//					Followups followups = new Followups();
//					followups.setApplication_id(application.getApplication_id());
//					followups.setProfile_id(application.getProfile_id());
//					followups.setJob_id(application.getJob_id());
//					followups.setActive(application.getActive());
//					followups.setContact(contact);
//					followups.setEmail(application.getEmail());
//					followups.setComment(comment);
//					followups.setStatus("1");
//					Date date = new Date();
//					followups.setFollowup_creation_date(date);
//					long x = followups_repos.saveFollowups(followups);
//					locallist.add(followups);
//				}
//				
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.CREATED);
//				response.setBody(locallist);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				
//			}else {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//			
//		} else if (application_id == 0 && contact.isBlank() && !email.isBlank()) {
//	         List<Followups> locallist = new ArrayList<Followups>();
//			
//			if(!app_repos.getByEmail(email).isEmpty() || app_repos.getByEmail(email) != null) {
//	
//				for(Application application : app_repos.getByEmail(email)) {
//					Followups followups = new Followups();
//					followups.setApplication_id(application.getApplication_id());
//					followups.setProfile_id(application.getProfile_id());
//					followups.setJob_id(application.getJob_id());
//					followups.setActive(application.getActive());
//					followups.setContact(application.getContact());
//					followups.setEmail(email);
//					followups.setComment(comment);
//					followups.setStatus("1");
//					Date date = new Date();
//					followups.setFollowup_creation_date(date);
//					long x = followups_repos.saveFollowups(followups);
//					locallist.add(followups);
//				}
//				
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.CREATED);
//				response.setBody(locallist);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				
//			}else {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//			
//		}else {
//			ResponseDto response = new ResponseDto();
//			response.setMessage(VMessageConstant.INVALID);
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

	@Override
	public ResponseEntity<ResponseDto> updateFollowupsById(HttpServletRequest request, long profile_id, String contact,
			String followup_status, String comment, String status) {
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
			if (profile_id < 0 && contact.isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			else if (profile_id > 0 && contact.isBlank()) {
				List<Profiles> list = prof_repos.fetchById(profile_id);
				if (list == null || list.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody("identity :" + profile_id);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

				else {
					if (!followup_status.isEmpty() && comment.isEmpty() && status.isEmpty()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsById(updated_by, profile_id, comment, status,
								followup_status);
						if (check == true) {
							ResponseDto response = new ResponseDto();
							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + profile_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

						else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + profile_id);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					}

					else if (followup_status.isEmpty() && !comment.isBlank() && status.isBlank()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsById(updated_by, profile_id, comment, status,
								followup_status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + profile_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

						else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + profile_id);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					}

					else if (followup_status.isEmpty() && comment.isBlank() && !status.isBlank()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsById(updated_by, profile_id, comment, status,
								followup_status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + profile_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

						else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + profile_id);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					}

					else {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("Invalid input");
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				}
			}

			else if (profile_id == 0 && !contact.isBlank()) {
				List<Profiles> list = prof_repos.findByMobile(contact);
				if (list == null || list.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody("Contact :" + contact);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

				else {
					if (!followup_status.isEmpty() && comment.isEmpty() && status.isEmpty()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsByContact(updated_by, contact, comment, status,
								followup_status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("Contact :" + contact);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("Contact :" + contact);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else if (followup_status.isEmpty() && !comment.isBlank() && status.isBlank()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsByContact(updated_by, contact, comment, status,
								followup_status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("Contact :" + contact);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("Contact :" + contact);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else if (followup_status.isEmpty() && comment.isBlank() && !status.isBlank()) {
						String updated_by = (String) request.getSession().getAttribute("userName");
						boolean check = followups_repos.updateFollowupsByContact(updated_by, contact, comment, status,
								followup_status);
						if (check == true) {
//							System.out.println("sanskar");
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("Contact :" + contact);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("Contact :" + contact);
							response.setStatus(false);
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
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody("Invalid input");
				response.setStatus(false);
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

//	@Override
//	public ResponseEntity<ResponseDto> add_followup(String comment, String contact, String email) {
//
//		if (!contact.isBlank() && email.isBlank()) {
//			List<Followups> locallist = new ArrayList<Followups>();
//
////			if(!app_repos.getByContact(contact).isEmpty() || app_repos.getByContact(contact) != null) {
//
//			List<Application> list = app_repos.findByMobile(contact);
//			
//			
//			
//			if (list == null || list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//			for (Application application : list) {
//				Followups followups = new Followups();
//				followups.setApplication_id(application.getApplication_id());
//				followups.setProfile_id(application.getProfile_id());
//				followups.setJob_id(application.getJob_id());
//				followups.setClient_id(application.getClient_id());
//				followups.setContact(contact);
//				Profile prof = prof_repos.getByMobile(contact);
//				followups.setEmail(prof.getEmail());
//				followups.setComment(comment);
//				followups.setFollowup_status("NOT REACHABLE");
//				followups.setCreated_by("charlie.indulkar@sroniyan.com");
//				followups.setStatus(1);
//				Date date = new Date();
//				followups.setFollowup_creation_date(date);
//				long x = followups_repos.saveFollowups(followups);
//				locallist.add(followups);
//			}
//
//			ResponseDto response = new ResponseDto();
//			response.setMessage(VMessageConstant.CREATED);
//			response.setBody(locallist);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		} else if (contact.isBlank() && !email.isBlank()) {
//			List<Followups> locallist = new ArrayList<Followups>();
//
////			if(!app_repos.getByEmail(email).isEmpty() || app_repos.getByEmail(email) != null) {
//
//			List<Application> list = app_repos.findByEmail(email);
//			if (list == null || list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//			for (Application application : list) {
//				Followups followups = new Followups();
//				followups.setApplication_id(application.getApplication_id());
//				followups.setProfile_id(application.getProfile_id());
//				followups.setJob_id(application.getJob_id());
//				followups.setClient_id(application.getClient_id());
//				Profile prof = prof_repos.getByEmail(email);
//				followups.setContact(prof.getContact());
//				followups.setEmail(email);
//				followups.setComment(comment);
//				followups.setFollowup_status("NOT REACHABLE");
//				followups.setCreated_by("charlie.indulkar@sroniyan.com");
//				followups.setStatus(1);
//				Date date = new Date();
//				followups.setFollowup_creation_date(date);
//				long x = followups_repos.saveFollowups(followups);
//				locallist.add(followups);
//			}
//
//			ResponseDto response = new ResponseDto();
//			response.setMessage(VMessageConstant.CREATED);
//			response.setBody(locallist);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		} else {
//			ResponseDto response = new ResponseDto();
//			response.setMessage("Please enter either of contact or email");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

//
//	@Override
//	public ResponseEntity<ResponseDto> add_followup(String comment, String contact, String email) {
//
//		if (!contact.isBlank() && email.isBlank()) {
//			List<Followups> locallist = new ArrayList<Followups>();
//			List<Application> list = app_repos.findByMobile(contact);
//			if (list == null || list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//			
//			for (Application application : list) {
//				Application followlist = app_repos.getByProfAndJobId(application.getProfile_id(),
//						application.getJob_id(), "closed");
//				if (followlist != null) {
//					break;
//				}
//				Followups followups = new Followups();
//				followups.setApplication_id(application.getApplication_id());
//				followups.setProfile_id(application.getProfile_id());
//				followups.setJob_id(application.getJob_id());
//				followups.setClient_id(application.getClient_id());
//				followups.setContact(contact);
//				Profile prof = prof_repos.getByMobile(contact);
//				followups.setEmail(prof.getEmail());
//				followups.setComment(comment);
//
//				followups.setFollowup_status("NOT REACHABLE");
//				followups.setCreated_by("charlie.indulkar@sroniyan.com");
//				followups.setStatus(1);
//				Date date = new Date();
//				followups.setFollowup_creation_date(date);
//				long x = followups_repos.saveFollowups(followups);
//				locallist.add(followups);
//			}
//			if (locallist == null || locallist.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.CREATED);
//				response.setBody(locallist);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} else if (contact.isBlank() && !email.isBlank()) {
//			List<Followups> locallist = new ArrayList<Followups>();
//
////			if(!app_repos.getByEmail(email).isEmpty() || app_repos.getByEmail(email) != null) {
//
//			List<Application> list = app_repos.findByEmail(email);
//			if (list == null || list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//			for (Application application : list) {
//				Followups followlist = followups_repos.getByProfAndJobId(application.getProfile_id(),
//						application.getJob_id(), "closed");
//				if (followlist != null) {
//					break;
//				}
//				Followups followups = new Followups();
//				followups.setApplication_id(application.getApplication_id());
//				followups.setProfile_id(application.getProfile_id());
//				followups.setJob_id(application.getJob_id());
//				followups.setClient_id(application.getClient_id());
//				Profile prof = prof_repos.getByEmail(email);
//				followups.setContact(prof.getContact());
//				followups.setEmail(email);
//				followups.setComment(comment);
//				followups.setFollowup_status("NOT REACHABLE");
//				followups.setCreated_by("charlie.indulkar@sroniyan.com");
//				followups.setStatus(1);
//				Date date = new Date();
//				followups.setFollowup_creation_date(date);
//				long x = followups_repos.saveFollowups(followups);
//				locallist.add(followups);
//			}
//			if (locallist == null || locallist.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.CREATED);
//				response.setBody(locallist);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else {
//			ResponseDto response = new ResponseDto();
//			response.setMessage("Please enter either of contact or email");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}

	@Override
	public ResponseEntity<ResponseDto> add_followup(HttpSession session, String token, String comment, String contact,
			String email) {
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
		if (!contact.isBlank() && email.isBlank()) {
			List<Followups> locallist = new ArrayList<Followups>();
			List<Applications> list = app_repos.findByMbl(session,contact);
			if (list == null || list.isEmpty()) {
				ResponseDto response = new ResponseDto();
				response.setMessage("application not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			for (Applications application : list) {
				Profiles prof = prof_repos.getByMobile(contact);
				if (prof == null) {
					ResponseDto response = new ResponseDto();
					response.setMessage("profile does not exist");
					response.setBody("enter valid contact");
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					Followups followups = new Followups();
					followups.setApplication_id(application.getApplication_id());
					followups.setProfile_id(application.getProfile_id());
					followups.setPartner_id((long) session.getAttribute("partner_id"));
					followups.setJob_id(application.getJob_id());
					followups.setClient_id(application.getClient_id());
					followups.setContact(contact);
					followups.setEmail(prof.getEmail());
					followups.setComment(comment);
					followups.setFollowup_status("NOT REACHABLE");
					followups.setCreated_by((String) session.getAttribute("email"));
					followups.setStatus(1);
					Date date = new Date();
					followups.setFollowup_creation_date(date);
					long x = followups_repos.saveFollowups(followups);
					locallist.add(followups);
				}
			}
			if (locallist == null || locallist.isEmpty()) {
				ResponseDto response = new ResponseDto();
				response.setMessage("application not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();
				response.setMessage(VMessageConstant.CREATED);
				response.setBody(locallist);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} else if (contact.isBlank() && !email.isBlank()) {
			List<Followups> locallist = new ArrayList<Followups>();
			List<Applications> list = app_repos.findEmail(session,email);
			if (list == null || list.isEmpty()) {
				ResponseDto response = new ResponseDto();
				response.setMessage("application not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			for (Applications application : list) {
//				Applications app = app_repos.getAppById(application.getApplication_id());
				Profiles prof = prof_repos.getByEmail(email);
				if (prof == null) {
					ResponseDto response = new ResponseDto();
					response.setMessage("profile does not exist");
					response.setBody("enter valid email");
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					Followups followups = new Followups();
					followups.setApplication_id(application.getApplication_id());
					followups.setProfile_id(application.getProfile_id());
					followups.setPartner_id((long) session.getAttribute("partner_id"));
					followups.setJob_id(application.getJob_id());
					followups.setClient_id(application.getClient_id());
					followups.setContact(contact);

					followups.setEmail(prof.getEmail());
					followups.setComment(comment);
					followups.setFollowup_status("NOT REACHABLE");
					followups.setCreated_by((String) session.getAttribute("email"));
					followups.setStatus(1);
					Date date = new Date();
					followups.setFollowup_creation_date(date);
					long x = followups_repos.saveFollowups(followups);
					locallist.add(followups);
				}

			}
			if (locallist == null || locallist.isEmpty()) {
				ResponseDto response = new ResponseDto();
				response.setMessage("application does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();
				response.setMessage(VMessageConstant.CREATED);
				response.setBody(locallist);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else {
			ResponseDto response = new ResponseDto();
			response.setMessage("Please enter either of contact or email");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
//	@Override
//	public ResponseEntity<ResponseDto> fetchByMultipleOption(HttpServletRequest request, long job_id, String job_name,
//			long application_id, int client_id, String contact, String email, String name, long profile_id,
//			String comment, String followup_status, String followup_creation_date) {
//		System.out.println("Service");
////		try {
////			if (request.getSession().getAttribute("userName") == null) {
////				ResponseDto response = new ResponseDto();
////				response.setMessage("Please login!");
////				response.setBody(null);
////				response.setStatus(true);
////				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////			}
////		} catch (Exception e) {
////			ResponseDto response = new ResponseDto();
////
////			response.setMessage(VMessageConstant.DEFAULT);
////			response.setBody(null);
////			response.setStatus(false);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		}
//		List<Followups> list = followups_repos.fetchByMultipleParam(job_id, job_name, application_id, client_id,
//				contact, email, name, profile_id, comment, followup_status, followup_creation_date);
//		ResponseDto response = new ResponseDto();
//		if (list == null) {
//			System.out.println(list);
//			response.setMessage(VMessageConstant.Not_Found);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if ((!list.isEmpty())) {
//			response.setMessage(VMessageConstant.MESSAGE);
//			response.setBody(list);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", " given field"));
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	}
//
//}
