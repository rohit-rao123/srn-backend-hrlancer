package org.srn.web.Recruiter.service_impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.component.FileHelper;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.Jobs_dao_impl;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.ApplicationProfile;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Jobs;
import org.srn.web.Recruiter.entity.Profiles;
import org.srn.web.Recruiter.service.Application_Service;

@Service
@Transactional

public class Application_Service_Impl implements Application_Service {

	@Autowired
	private Application_dao_impl application_repos;

	@Autowired
	private Profile_dao_impl prof_repos;

	@Autowired
	private Jobs_dao_impl Jobs_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private Profile_Service_Impl profile_service;

	@Override
	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long application_id) {
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
		List<Applications> list = application_repos.fetchById(application_id);
		try {
			if (application_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(true);
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

		List<ApplicationProfile> list = application_repos.findAllApplication(session);
		try {
			if (list == null || list.isEmpty()) {
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
	public ResponseEntity<ResponseDto> eraseByApplicationId(HttpServletRequest request, long application_id) {
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
		boolean check = application_repos.deleteApplicationById(application_id);
		try {
			if (application_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + application_id);
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

	@Override
	public ResponseEntity<ResponseDto> editApplicationRecordById(HttpServletRequest request, long application_id,
			String comment, int status) {
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
		boolean check = application_repos.updateApplicationById(application_id, comment, status);

		try {
			if (application_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
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
				response.setBody("application_id :" + application_id);
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
	public ResponseEntity<ResponseDto> fetchByPId(HttpServletRequest request, long profile_id) {
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
		List<Applications> list = application_repos.fetchByPId(profile_id);
		try {
			if (profile_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "profile_id"));
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (list.isEmpty()) {
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
	public ResponseEntity<Resource> downloadServices(HttpServletRequest request) {
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

		List<Applications> list = application_repos.findAllApplication();
		File file = FileHelper.getAppFile(list);
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

		List<Applications> list = application_repos.fetchByDomain(domain);
		File file = FileHelper.getAppFile(list);
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
	public ResponseEntity<ResponseDto> addNewrecord(HttpSession session, String token, long profile_id, long job_id,
			double is_ectc_nego, double rel_exp, String comment, MultipartFile file) {
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();

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
			if (profile_id == 0) {

				response.setMessage("profile_id is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job_id == 0) {

				response.setMessage("job_id is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (is_ectc_nego != 0 && is_ectc_nego != 1) {
				response.setMessage("is_ectc_nego is mandatory");
				response.setBody("enter either 0 or 1");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (rel_exp == 0) {
				response.setMessage("rel_exp is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (file == null) {
				response.setMessage("file is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Profiles prof = prof_repos.getById(profile_id);
				Jobs job = (Jobs) Jobs_repos.getById(job_id);
				if (prof == null) {

					response.setMessage("profile_id does not exist");
					response.setBody("unprocessable input");
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else if (job == null) {

					response.setMessage("job_id does not exist");

					response.setBody("unprocessable input");
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					List<Applications> locallist = new ArrayList<Applications>();

					{
						Applications app = new Applications();
						Date date = new Date();
						String cvDir = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/"
								+ file.getOriginalFilename();
						app.setStatus(1);
						app.setCreated_by((String) session.getAttribute("email"));
						app.setPartner_id((long) session.getAttribute("partner_id"));
						app.setDt(date);
						app.setClient_id(job.getClient_id());
						app.setJob_id(job_id);
						app.setProfile_id(profile_id);
						app.setEctc(prof.getEctc());
						app.setNotice(prof.getNotice_period());
						app.setIs_ectc_nego(is_ectc_nego);
						app.setCv_path(cvDir);
						app.setProgress("APPLIED");
						app.setRel_exp(rel_exp);
						app.setComment(comment);
						boolean check = application_repos.saveApplication(app);
						if (check == true) {
							System.out.println("Application_id is saved = " + app.getApplication_id());
							locallist.add(app);
							response.setMessage("new application added succesfully");
							response.setBody(app);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							System.out.println("Application is not saved, due to internal server error");
							response.setMessage("unable to save now");
							response.setBody(null);
							response.setStatus(false);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Application is not saved");
			response.setMessage("internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<ResponseDto> addAppByDomain(HttpServletRequest request, long job_id) {
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
			if (job_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "job_id"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				List<Applications> locallist = new ArrayList<Applications>();
				Jobs job = (Jobs) Jobs_repos.getById(job_id);
				String dom = job.getDomain();
				List<Profiles> proflist = prof_repos.fetchBySkills(dom);

				if (job == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage("Can't apply for job, as job_id is not found");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else if (proflist == null || proflist.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage("Can't apply for job, as no profile matches with given domain");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					for (Profiles pf : proflist) {
						Applications app = new Applications();
						Date date = new Date();
						app.setStatus(1);
						app.setDt(date);
						app.setClient_id(job.getClient_id());
						app.setJob_id(job_id);
						app.setProfile_id(pf.getProfile_id());

						List<Applications> list = application_repos.getByJobAndProfile(app.getJob_id(),
								app.getProfile_id());
						if (list == null || list.isEmpty()) {
							boolean check = application_repos.saveApplication(app);
							System.out.println(check);
							if (check == true) {
								System.out.println("Application_id is saved = " + app.getApplication_id());
								locallist.add(app);
							} else {
								System.out.println("Application is not saved, due to internal server error");
							}

						} else {
							System.out.println("This Application_id already exist = " + app.getApplication_id());
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
						response.setMessage(VMessageConstant.CREATED);
						response.setBody(locallist);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				}

			}

		} catch (Exception e) {
			ResponseDto response = new ResponseDto();
			System.out.println(e);

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> updateApplicationRecordById(HttpSession session, String token,
			long application_id, double ectc, int notice, String progress) {
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
		ResponseDto response = new ResponseDto();
		try {
			if (application_id != 0 && ectc != 0.0 && notice == 0 && progress.isBlank()) {
				boolean check = application_repos.updateEctc(application_id, ectc);
				if (check == true) {
					response.setMessage("application updated");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("application does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (application_id != 0 && ectc == 0.0 && notice != 0 && progress.isBlank()) {
				boolean check = application_repos.updateNotice(application_id, notice);
				if (check == true) {
					response.setMessage("application updated");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("application does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (application_id != 0 && ectc == 0.0 && notice == 0 && progress.isBlank() == false) {
				boolean check = application_repos.updateProgress(application_id, progress);
				if (check == true) {
					response.setMessage("application updated");
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("application does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else {
				response.setMessage("please enter only one field");
				response.setBody("Invalid input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<ResponseDto> fetchByContactOrEmail(HttpServletRequest request, String contact, String email) {
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
		if (!contact.isBlank() && email.isBlank()) {
			List<Applications> app = application_repos.findByMobile(contact);
			try {
				if (app == null || app.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "contact"));
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else if (contact.isBlank() && !email.isBlank()) {
			List<Applications> app = application_repos.findByEmail(email);
			try {
				if (app == null || app.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "email"));
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else {
			ResponseDto response = new ResponseDto();
			response.setMessage("Please enter contact or email");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

//	@Override
//	public ResponseEntity<ResponseDto> fetchByApp(HttpSession session,String token, String contact, String email,
//			String progress, String app_creation_date) {
//		msRequestFilter.validateToken(session, token);
//		try {
//			if (session.getAttribute("isSession") != "true") {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(false);
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
//		if (!contact.isBlank() && email.isBlank() && progress.isBlank() && app_creation_date == null) {
//			List<Applications> app = application_repos.findByMobile(contact);
//			try {
//				if (app == null || app.isEmpty()) {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "contact"));
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(app);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			} catch (Exception e) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.DEFAULT);
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else if (contact.isBlank() && !email.isBlank() && progress.isBlank() && app_creation_date == null) {
//			List<Applications> app = application_repos.findByEmail(email);
//			try {
//				if (app == null || app.isEmpty()) {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "email"));
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(app);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			} catch (Exception e) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.DEFAULT);
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else if (contact.isBlank() && email.isBlank() && !progress.isBlank() && app_creation_date == null) {
//			List<Applications> app = application_repos.findByApplicationStatus(progress);
//			try {
//				if (app == null || app.isEmpty()) {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "application_status"));
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(app);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			} catch (Exception e) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.DEFAULT);
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else if (contact.isBlank() && email.isBlank() && progress.isBlank() && app_creation_date != null) {
//			List<Applications> app = application_repos.findByDate(app_creation_date);
//			try {
//				if (app == null || app.isEmpty()) {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "app_creation_date"));
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					ResponseDto response = new ResponseDto();
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(app);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//			} catch (Exception e) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.DEFAULT);
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} else {
//			ResponseDto response = new ResponseDto();
//			response.setMessage("Please enter anyone field");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//	}

	@Override
	public ResponseEntity<ResponseDto> fetchByApp(HttpSession session, String token, String contact, String email,
			String progress, String app_creation_date) {
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
		if (!contact.isBlank() && email.isBlank() && progress.isBlank() && app_creation_date.isBlank()) {
			List<ApplicationProfile> app = application_repos.findByMobile(session,contact);
			try {
				if (app.isEmpty() || app == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "contact"));
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else if (contact.isBlank() && !email.isBlank() && progress.isBlank() && app_creation_date.isBlank()) {
			List<ApplicationProfile> app = application_repos.findByEmail(session,email);
			try {
				if (app.isEmpty() || app == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "email"));
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else if (contact.isBlank() && email.isBlank() && !progress.isBlank() && app_creation_date.isBlank()) {
			List<ApplicationProfile> app = application_repos.findByApplicationStatus(session,progress);
			try {
				if (app.isEmpty() || app == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "application_status"));
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else if (contact.isBlank() && email.isBlank() && progress.isBlank() && !app_creation_date.isBlank()) {
			List<ApplicationProfile> app = application_repos.findByDate(session,app_creation_date);
			try {
				if (app.isEmpty() || app == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "app_creation_date"));
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else if (contact.isBlank() && email.isBlank() && progress.isBlank() && app_creation_date.isBlank()) {
			List<ApplicationProfile> app = application_repos.findAllApplication(session);
			try {
				if (app.isEmpty() || app == null) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found.replace("<FIELD>", "app_creation_date"));
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(app);
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
		} else {
			ResponseDto response = new ResponseDto();
			response.setMessage("Please enter anyone field");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}
	
	
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
		try {
			String fileContentType = file.getContentType();
			if (fileContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				return true;
			} else if (fileContentType.equals("application/pdf")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ResponseEntity<ResponseDto> jobApplications(HttpSession session, String token, long profile_id, long job_id,
			double is_ectc_nego, double rel_exp, String comment, int notice_period) {

		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();
//		boolean fileFormatChecker = checkFileFormat(resume);
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
			if (profile_id == 0) {
				response.setMessage("Candidate_name is mandatory !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job_id == 0) {
				response.setMessage("Job_id is mandatory !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}else if (is_ectc_nego == -1) {
				response.setMessage("Is_ectc_nego is mandatory !");
				response.setBody("enter either 1 or 0");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (is_ectc_nego != 0 && is_ectc_nego != 1 ) {
				response.setMessage("Is_ectc_nego is invalid !");
				response.setBody("enter either 1 or 0");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}  else if (rel_exp == 0) {
				response.setMessage("Rel_exp is mandatory !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (notice_period == 0) {
				response.setMessage("Notice_period is mandatory !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
//			} else if (resume == null) {
//				System.out.println(resume);
//				response.setMessage("File is mandatory !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (fileFormatChecker == false) {
//				response.setMessage("Only pdf and docx file is allowed !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
				else {
				Profiles prof = prof_repos.getById(profile_id);
				if (prof != null) {
					Jobs job = (Jobs) Jobs_repos.getById(job_id);
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
						app.setProfile_id(profile_id);
						app.setEctc(prof.getEctc());
						app.setNotice(notice_period);;
						app.setIs_ectc_nego(is_ectc_nego);
						app.setCv_path(cvDir);
						app.setProgress("APPLIED");
						app.setRel_exp(rel_exp);
						app.setComment(comment);
						List<Applications> appList = application_repos.fetchByJob_idAndProfile_id(job_id, profile_id);
						
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

					}

				} else {
					response.setMessage("Candidate profile does not exist !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("unable to save due to internal server error");
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

//				else {
//					Jobs job = (Jobs) Jobs_repos.getById(job_id);
//					if (name.isBlank() || name == null) {
//						response.setMessage("Name is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (contact.isBlank() || contact == null) {
//						response.setMessage("Contact is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (email.isBlank() || email == null) {
//						response.setMessage("Email is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (ctc == 0) {
//						response.setMessage("Ctc is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (ectc == 0) {
//						response.setMessage("Ectc is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (company_id == 0) {
//						response.setMessage("Company_id is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (company.isBlank() || company == null) {
//						response.setMessage("Company is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (notice_period == 0) {
//						response.setMessage("Notice_period is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (domain.isBlank() || domain == null) {
//						response.setMessage("Domain is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (primary_skill.isBlank() || primary_skill == null) {
//						response.setMessage("Primary_skill is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (secondary_skill.isBlank() || secondary_skill == null) {
//						response.setMessage("Secondary_skill is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (qualification.isBlank() || qualification == null) {
//						response.setMessage("Qualification is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (location.isBlank() || location == null) {
//						response.setMessage("Location is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (current_role.isBlank() || current_role == null) {
//						response.setMessage("Current_role is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (rel_exp == 0) {
//						response.setMessage("Rel_exp is mandatory !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else if (job == null) {
//						response.setMessage("Job_id does not found !");
//						response.setBody(null);
//						response.setStatus(false);
//						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//					} else {
//						Profiles profile = profile_service.createProfile(session, token, company_id, name, contact,
//								email, ctc, ectc, current_role, company, location, domain, primary_skill,
//								secondary_skill, rel_exp, alternate_contact, alternate_email, qualification,
//								year_of_passing, notice_period, resume);
//						if (profile == null) {
//							System.out.println("unable to create profile");
//							response.setMessage("Cannot create duplicate profile !");
//							response.setBody(null);
//							response.setStatus(false);
//							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//						} else {
//							Applications app = new Applications();
//							Date date = new Date();
//							String cvDir = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/"
//									+ resume.getOriginalFilename();
//							app.setStatus(1);
//							app.setCreated_by((String) session.getAttribute("email"));
//							app.setPartner_id((long) session.getAttribute("partner_id"));
//							app.setDt(date);
//							app.setClient_id(job.getClient_id());
//							app.setJob_id(job_id);
//							app.setProfile_id(profile.getProfile_id());
//							app.setEctc(profile.getEctc());
//							app.setNotice(profile.getNotice_period());
//							app.setIs_ectc_nego(is_ectc_nego);
//							app.setCv_path(cvDir);
//							app.setProgress("APPLIED");
//							app.setRel_exp(rel_exp);
//							app.setComment(comment);
//							boolean check = application_repos.saveApplication(app);
//							if (check == true) {
//								System.out.println("Application_id is saved = " + app.getApplication_id());
//								response.setMessage("Application added succesfully !");
//								response.setBody(app);
//								response.setStatus(true);
//								return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//							} else {
//								System.out.println("unable to save due to internal server error");
//								response.setMessage("Application is not created !");
//								response.setBody(null);
//								response.setStatus(false);
//								return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//							}
//						}
//					}
//				}
//			}

	}

	@Override
	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

//@Override
//public ResponseEntity<ResponseDto> fetchById(long application_id) {
//	Application apps = (Application) application_repos.fetchById(application_id);
//	try {
//		if (apps.getEmail().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//		if (application_id <= 0) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.ID_MISSING);
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getContact().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
////		else if (apps.getActive() != "0" && apps.getActive() != "1") {
////			ResponseDto response = new ResponseDto();
////
////			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "active"));
////			response.setBody("unprocessable input");
////			response.setStatus(false);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		} 
//		else if (apps.getProfile_id() <= 0) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Profile_id"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getApplied_for().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "applied_for"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getName().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "name"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getAlternate_email().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Alternate_email"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getExp().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Exp"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getCtc().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Ctc"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getEctc().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Ectc"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getNotice_peroid().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Notice_period"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getCompany().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "company"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getAlternate_contact().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "alternate_contact()"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getCurrent_role().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "current_role"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getQualification().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "qualification"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getYear_of_passing().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "year_of_passing"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getLocation().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "location"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getDomain().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "domain"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getClient_id() <= 0) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "client_id"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (apps.getJob_id() <= 0) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "job_id"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.MESSAGE);
//			response.setBody(apps);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	} catch (Exception e) {
//		ResponseDto response = new ResponseDto();
//
//		response.setMessage(VMessageConstant.DEFAULT);
//		response.setBody(null);
//		response.setStatus(false);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	}
//
//}

//@Override
//public ResponseEntity<ResponseDto> addNewrecord(Application app) {
//	try {
//		if (app.getName().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "name"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (app.getContact().isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (app.getEmail().isEmpty()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
//			response.setBody("unprocessable input");
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			Date date = new Date();
//			app.setApp_creation_date(date);
//			app.setStatus(1);
//			app.setActive("1");
//			boolean check = application_repos.saveApplication(app);
//			ResponseDto response = new ResponseDto();
//			response.setMessage(VMessageConstant.CREATED);
//			response.setBody("Generated  new application_id is created" + app.getApplication_id());
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//	} catch (Exception e) {
//		ResponseDto response = new ResponseDto();
//
//		response.setMessage(VMessageConstant.DEFAULT);
//		response.setBody(null);
//		response.setStatus(false);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	}
//}

//@Override
//public ResponseEntity<ResponseDto> addNewrecord(Application app) {
////	try {
////		if (app.getName().isBlank()) {
////			ResponseDto response = new ResponseDto();
////
////			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "name"));
////			response.setBody("unprocessable input");
////			response.setStatus(false);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		} else if (app.getContact().isBlank()) {
////			ResponseDto response = new ResponseDto();
////
////			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "contact"));
////			response.setBody("unprocessable input");
////			response.setStatus(false);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		} else if (app.getEmail().isEmpty()) {
////			ResponseDto response = new ResponseDto();
////
////			response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "email"));
////			response.setBody("unprocessable input");
////			response.setStatus(false);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		} else {
////			Date date = new Date();
////			app.setApp_creation_date(date);
////			app.setStatus(1);
////			app.setActive("1");
////			boolean check = application_repos.saveApplication(app);
////			ResponseDto response = new ResponseDto();
////			response.setMessage(VMessageConstant.CREATED);
////			response.setBody("Generated  new application_id is created" + app.getApplication_id());
////			response.setStatus(true);
////			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////		}
////
////	} catch (Exception e) {
////		ResponseDto response = new ResponseDto();
////
////		response.setMessage(VMessageConstant.DEFAULT);
////		response.setBody(null);
////		response.setStatus(false);
////		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
////	}
//	return null;
//}

//@Override
//public ResponseEntity<ResponseDto> addAppFile(MultipartFile file) {
//
//	List<Application> list = new ArrayList<Application>();
//	boolean check = true;
//	int i;
//	try {
//		if (FileHelper.checkFileFormat(file) == false) {
//
//			ResponseDto response = new ResponseDto();
//			response.setStatus(false);
//			response.setMessage("file format is not correct");
//			response.setBody(null);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			list = FileHelper.getAppExcelFile(file.getInputStream());
//
//		}
//	} catch (IOException e1) {
//		e1.printStackTrace();
//
//		ResponseDto response = new ResponseDto();
//		response.setStatus(false);
//		response.setMessage("file data is invalid");
//		response.setBody(null);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	}
//
//	try {
//		if (list == null) {
//
//			ResponseDto response = new ResponseDto();
//			response.setStatus(false);
//			response.setMessage("file is empty");
//			response.setBody(null);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			for (i = 0; i < list.size(); i++) {
//				check = application_repos.saveApplication(list.get(i));
//				if (check == false) {
//					break;
//				} else {
//					continue;
//				}
//			}
//			if (check == false) {
//				ResponseDto response = new ResponseDto();
//				response.setStatus(false);
//				response.setMessage("data is not saved ");
//				response.setBody("Record already exist in database");
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else {
//
//				ResponseDto response = new ResponseDto();
//				response.setStatus(true);
//				response.setMessage("file is uploaded");
//				response.setBody(list);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		}
//	} catch (NumberFormatException ne) {
//		ne.printStackTrace();
//
//		ResponseDto response = new ResponseDto();
//		response.setStatus(false);
//		response.setMessage("file is not uploaded, due to invalid number input");
//		response.setBody(null);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	} catch (InternalServerError ie) {
//		ie.printStackTrace();
//
//		ResponseDto response = new ResponseDto();
//		response.setStatus(false);
//		response.setMessage("file is not uploaded, due to internal server error");
//		response.setBody(null);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	} catch (Exception e) {
//		e.printStackTrace();
//
//		ResponseDto response = new ResponseDto();
//		response.setStatus(false);
//		response.setMessage("file is not uploaded, due to invalid input");
//		response.setBody(null);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	}
//
//}

//@Override
//public ResponseEntity<ResponseDto> addAppFile(MultipartFile file) {
//	boolean check = false;
//	try {
//		if (file.getSize() == 0) {
//			ResponseDto response = new ResponseDto();
//			response.setMessage("Given file is empty !");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			check = FileHelper.checkFileFormat(file);
//		}
//	} catch (Exception e1) {
//		e1.printStackTrace();
//		ResponseDto response = new ResponseDto();
//		response.setMessage("Exception occur in format of file");
//		response.setBody(null);
//		response.setStatus(false);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	}
//
//	if (check == false) {
//		ResponseDto response = new ResponseDto();
//		response.setMessage("This file is not of excel format");
//		response.setBody(null);
//		response.setStatus(false);
//		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//	} else {
//
//		List<Application> list = new ArrayList<Application>();
//		try {
//			list = FileHelper.getAppExcelFile(file.getInputStream());
//		} catch (IOException e1) {
//			list = null;
//			e1.printStackTrace();
//
//		}
//
//		if (list.size() == 0) {
//
//			ResponseDto response = new ResponseDto();
//			response.setStatus(false);
//			response.setMessage("file is empty");
//			response.setBody(null);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//
//			try {
//				List<Application> locallist = new ArrayList<Application>();
//
//				for (int i = 0; i < list.size(); i++) {
//					check = application_repos.saveApplication(list.get(i));
//					System.out.println("New Application Saved with name  " + list.get(i).getName());
//					locallist.add(list.get(i));
//				}
//				if (locallist.size() == 0) {
//					ResponseDto response = new ResponseDto();
//					response.setStatus(true);
//					response.setMessage("This file is already injected, so cannot be inject again");
//					response.setBody(null);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					ResponseDto response = new ResponseDto();
//					response.setStatus(true);
//					response.setMessage("All Application is saved");
//					response.setBody(locallist);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//
//			} catch (InternalServerError ie) {
//				ie.printStackTrace();
//				ResponseDto response = new ResponseDto();
//				response.setStatus(false);
//				response.setMessage("file is not uploaded, due to internal server error");
//				response.setBody(null);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//
//				ResponseDto response = new ResponseDto();
//				response.setStatus(false);
//				response.setMessage("file is not uploaded, due to exception");
//				response.setBody(null);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		}
//	}
//
//}
