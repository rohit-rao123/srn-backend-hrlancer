package org.srn.web.Recruiter.service_impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.component.FileHelper;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Jobs_dao_impl;
import org.srn.web.Recruiter.dao_impl.Partners_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Jobs;
import org.srn.web.Recruiter.entity.Jobs.JobType;
import org.srn.web.Recruiter.entity.Jobs.WorkingMode;
import org.srn.web.Recruiter.entity.Partners;
import org.srn.web.Recruiter.entity.Profiles;
import org.srn.web.Recruiter.service.Jobs_Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Transactional
public class Jobs_Service_Impl implements Jobs_Service {

	@Autowired
	private Jobs_dao_impl Jobs_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private Partners_dao_impl partner_repos;

	@Override
	public ResponseEntity<ResponseDto> fetchById(HttpServletRequest request, long jobs_id) {
		List<Jobs> list = new ArrayList<Jobs>();
//		List<Jobs> list = Jobs_repos.fetchById(jobs_id);
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
			if (jobs_id <= 0) {
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
		List<Jobs> list = Jobs_repos.fetchAll(session);

		try {
			if (list.isEmpty()) {
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
	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Jobs job) {

		Profiles prof = new Profiles();
		try {
			if (job.getJob_role().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "job_role"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getClient_id() <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "client_id"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getDomain().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "domain"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getMin_yr_exp() < 0.0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "min_exp"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getMax_yr_exp() < 0.0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "max_exp"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getMax_yr_budget() > 0.0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "max_salary"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getHead_count() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "head_count"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getHired_count() < 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "hired_count"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job.getCreated_by().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "created_by"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			} else {
				Date date = new Date();
				job.setDt(date);
				job.setStatus(1);
//				job.setDomain(prof.getDomain());
//				job.setMax_salary(prof.getCtc());
//				job.setMax_exp(prof.getExp());
//				job.setQualification(prof.getQualification());
				boolean check = Jobs_repos.saveJobs(job);

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.CREATED);
				response.setBody("Generated  new job_id succesfully");
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
	public ResponseEntity<ResponseDto> eraseByJobsId(HttpServletRequest request, long job_id) {
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
		boolean check = Jobs_repos.deleteJobsById(job_id);
		try {
			if (job_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + job_id);
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
//	public ResponseEntity<ResponseDto> editRecordById(long job_id,
//			String head_count, String hired_count, String max_exp, String max_salary, String min_exp,
//			String open, String domain, int status) {
//
//		boolean check = Jobs_repos.updateJobsById(job_id, head_count, hired_count,
//				max_exp, max_salary, min_exp, open, domain, status);
//
//		try {
//			if (job_id <= 0) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.ID_MISSING);
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}else if (head_count.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "head_count"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (hired_count.isBlank()) {
//				ResponseDto response = new ResponseDto();
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "hired_count"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (max_exp.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "max_exp"));
//				response.setBody("unprocessable input");
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			} else if (max_salary.isBlank()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "max_salary"));
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
//				Date date = new Date();
//				
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.UPDATE);
//				response.setBody( job_id);
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
	public ResponseEntity<ResponseDto> fetchByName(HttpServletRequest request, String jobs_name) {
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
		List<Jobs> list = Jobs_repos.fetchByName(jobs_name);
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
	public ResponseEntity<Resource> downloadService(HttpServletRequest request, String job_name) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Content-Disposition", "attachment; filename=" );
//				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//				headers.add("Pragma", "no-cache");
//				headers.add("Expires", "0");
//
//				ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//						.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//				return response;
//
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
//
//		}

		List<Jobs> list = Jobs_repos.fetchByName(job_name);
		File file = FileHelper.getJobFile(list);
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

	public ResponseEntity<InputStreamResource> generatePdf(HttpSession session, String token, String job_id)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		msRequestFilter.validateToken(session, token);

		try {
			if (session.getAttribute("isSession") != "true") {	
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		// Step 1: Take the input string from the user
		long jobId = 0;
		try {
			jobId = Long.parseLong(job_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		String input = Jobs_repos.getJobDescription(jobId);
		if (input == null || input.isBlank()) {
			input = "Job Description is not found !";
		}

		// Step 2: Create a PDF and write the input string into it
		Document document = new Document();
		try {
//			String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf";

			String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/JOB_DESCRIPTION.pdf";
//			String filePath = "C:\\Users\\Akshay Tyagi\\Pictures\\Recruiter\\Recruiter\\src\\main\\resources\\download_files\\JOB_PROFILE.pdf";
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Paragraph paragraph = new Paragraph(input);
			document.add(paragraph);
			document.close();
			System.out.println("PDF file created and data written successfully.");
		} catch (DocumentException | IOException e) {
			System.out.println("An error occurred while creating the PDF file.");
			e.printStackTrace();
		}
//		String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf";
		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/JOB_DESCRIPTION.pdf";
//		String filePath = "C:\\Users\\Akshay Tyagi\\Pictures\\Recruiter\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf"; // file
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=JOB_PROFILE.pdf");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(fileInputStream));

	}

	@Override
	public ResponseEntity<Resource> downloadAllServices(HttpServletRequest request) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Content-Disposition", "attachment; filename=" );
//				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//				headers.add("Pragma", "no-cache");
//				headers.add("Expires", "0");
//
//				ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
//						.contentType(MediaType.parseMediaType("application/plain")).body(null);
//
//				return response;
//
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
//
//		}
		List<Jobs> list = new ArrayList<Jobs>();

//		List<Jobs> list = Jobs_repos.findAllJobs();
		File file = FileHelper.getJobFile(list);
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
	public ResponseEntity<ResponseDto> updateRecordById(HttpServletRequest request, long job_id, String head_count,
			String hired_count, String max_exp, String max_salary, String min_exp, String open, String domain,
			int status) {

//		boolean check = Jobs_repos.updateJobsById(job_id, head_count, hired_count, max_exp, max_salary, min_exp, open, domain, status);

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

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
//				List<Jobs> list = Jobs_repos.fetchById(job_id);
				List<Jobs> list = new ArrayList<Jobs>();
				if (list == null || list.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody("identity :" + job_id);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					if (!head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty() && max_salary.isEmpty()
							&& min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && !hired_count.isEmpty() && max_exp.isEmpty()
							&& max_salary.isEmpty() && min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && !max_exp.isEmpty()
							&& max_salary.isEmpty() && min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty()
							&& !max_salary.isEmpty() && min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty()
							&& max_salary.isEmpty() && !min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty()
							&& max_salary.isEmpty() && min_exp.isEmpty() && !open.isEmpty() && domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty()
							&& max_salary.isEmpty() && min_exp.isEmpty() && open.isEmpty() && !domain.isEmpty()
							&& (status != 0 || status != 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (head_count.isEmpty() && hired_count.isEmpty() && max_exp.isEmpty()
							&& max_salary.isEmpty() && min_exp.isEmpty() && open.isEmpty() && domain.isEmpty()
							&& (status == 0 || status == 1)) {
						boolean check = Jobs_repos.updateJobsId(job_id, head_count, hired_count, max_exp, max_salary,
								min_exp, open, domain, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + job_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + job_id);
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
	public ResponseEntity<ResponseDto> fetchByCid(HttpServletRequest request, int client_id, String jobs_name) {
		List<Jobs> list = Jobs_repos.fetchByCIdAndJobName(client_id, jobs_name);
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
			if (client_id <= 0) {
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

	public void sendEmail(HttpSession sessin, String message, String subject, String to, String from, File file) {

		// Variable for gmail
		String host = "smtp.gmail.com";

		// get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES " + properties);

		// setting important information to properties object

		// host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1: to get the session object..
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("akshaytyagi3102003@gmail.com", "vfbifvqpwhstpcae");
			}

		});

		session.setDebug(true);

		// Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);

		try {

			// from email
			m.setFrom(from);

			// adding recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// adding subject to message
			m.setSubject(subject);

			// adding text to message
			m.setText(message);

			// Create multi-part content
			MimeMultipart mimeMultipart = new MimeMultipart();

			// Add text part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(message);
			mimeMultipart.addBodyPart(textBodyPart);

			// Add file part
			if (file != null) {
				MimeBodyPart fileBodyPart = new MimeBodyPart();
				fileBodyPart.attachFile(file);
				mimeMultipart.addBodyPart(fileBodyPart);
			}

			// Set message content
			m.setContent(mimeMultipart);

			// Step 3 : send the message using Transport class
			Transport.send(m);
			System.out.println("Successfully send mail");
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage("Successfully send mail");
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
//			ResponseDto response = new ResponseDto();
//			response.setStatus(false);
//			response.setMessage("Failed to send mail, due to exception");
//			response.setBody(null);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

//	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
//		File convFile = new File(file.getOriginalFilename());
//		file.transferTo(convFile);
//	System.out.println(convFile.getName());	
//		return convFile;
//	}

//	public File jdFileWriter(String jd) {
//	
//		Document document = new Document();
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream("JOB_DESCRIPTION.pdf"));
//			document.open();
//			Paragraph paragraph = new Paragraph(jd);
//			document.add(paragraph);
//			document.close();
//			
//			System.out.println("PDF file created and data written successfully.");
//		} catch (Exception e) {
//			System.out.println("An error occurred while creating the PDF file.");
//			e.printStackTrace();
//		}
//	}

	// step 1 find all partners of same job_type
	// step 2 find their mail id and then send mail to them
	// step 3 mail contain jd info file with attachment
	// step 4 print delivery report in response body
	public void mailService(HttpSession session, String job_type, String jd) {

		List<Partners> list = partner_repos.findByPartnerType(session, job_type);
		System.out.println(list.toString());
		list.stream().forEach(e -> {
			System.out.println(e);
			String emailReciever = e.getEmail();
//			File file =new File ( "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\upload_file\\resume04.pdf");
//			File file = new File(
//					"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf");
//			String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf";

			String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/JOB_DESCRIPTION.pdf";
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(filePath));
				document.open();
				Paragraph paragraph = new Paragraph(jd);
				document.add(paragraph);
				document.close();

				System.out.println("PDF file created and data written successfully.");
			} catch (DocumentException | IOException err) {
				System.out.println("An error occurred while creating the PDF file.");
				err.printStackTrace();
			}
			String message = "Hello partner find new jobs description";
			String subject = "HRLancer new job update !";
			String to = emailReciever;
			String from = "pankaj3214sharma@gmail.com";
//			File file = new File(
//					"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf");
			File file = new File("/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/JOB_DESCRIPTION.pdf");
			sendEmail(session, message, subject, to, from, file);
		});

	}

	@Override
	public ResponseEntity<ResponseDto> addrecord(HttpSession session, String token, int client_id, String domain,
			String job_role, String skills, String jd, double min_yr_exp, double max_yr_exp, double max_yr_budget,
			String qualification, String location, String job_type, String working_mode, int head_count, int notice) {
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
			if (client_id == 0) {
				response.setMessage("client_id is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (domain.isBlank() || domain == null) {
				response.setMessage("domain is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (job_role.isBlank() || job_role == null) {
				response.setMessage("job_role is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (skills.isBlank() || skills == null) {
				response.setMessage("skills is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (jd.isBlank() || jd == null) {
				response.setMessage("jd is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (min_yr_exp == 0) {
				response.setMessage("min_yr_exp is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (max_yr_exp == 0) {
				response.setMessage("max_yr_exp is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (max_yr_budget == 0) {
				response.setMessage("max_yr_budget is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (qualification.isBlank() || qualification == null) {
				response.setMessage("qualification is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (location.isBlank() || location == null) {
				response.setMessage("location is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (working_mode.isBlank() || working_mode == null) {
				response.setMessage("working_mode is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (head_count == 0) {
				response.setMessage("head_count is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (notice == 0) {
				response.setMessage("notice is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			response.setMessage("unable to proceed");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		String username = (String) session.getAttribute("email");
		System.out.println(username);
		try {

			Jobs job = new Jobs();
			Date date = new Date();
			job.setDt(date);
			job.setStatus(1);
			job.setClient_id(client_id);
//			job.setCreated_by((String) request.getSession().getAttribute("userName"));
			job.setCreated_by(username);

			job.setDomain(domain);
			job.setSkills(skills);
			job.setHead_count(head_count);
			job.setHired_count(0);
			job.setJd(jd);
			job.setJob_role(job_role);
			job.setMax_yr_exp(max_yr_exp);
			job.setMax_yr_budget(max_yr_budget);
			job.setMin_yr_exp(min_yr_exp);
			job.setOpen(1);
			job.setNotice(notice);
			try {
				long partner_id = (long) session.getAttribute("partner_id");
				Partners partner = partner_repos.getById(partner_id);
				if (partner_id == 1) {
					if (job_type.isBlank()) {
						response.setMessage("Job_type is mandatory");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						if (JobType.OFFSHORE.toString().contentEquals(job_type)) {
							job.setJob_type(JobType.OFFSHORE);
						} else if (JobType.OFFSHORE_RECRUITMENT.toString().contentEquals(job_type)) {
							job.setJob_type(JobType.OFFSHORE_RECRUITMENT);
						} else if (JobType.RECRUITMENT.toString().contentEquals(job_type)) {
							job.setJob_type(JobType.RECRUITMENT);
						} else {
							job.setJob_type(JobType.OFFSHORE);
						}
					}

				} else {
					if (JobType.OFFSHORE.toString().contentEquals(partner.getPartner_type().toString())) {
						job.setJob_type(JobType.OFFSHORE);
					} else if (JobType.OFFSHORE_RECRUITMENT.toString()
							.contentEquals(partner.getPartner_type().toString())) {
						job.setJob_type(JobType.OFFSHORE_RECRUITMENT);
					} else if (JobType.RECRUITMENT.toString().contentEquals(partner.getPartner_type().toString())) {
						job.setJob_type(JobType.RECRUITMENT);
					} else {
						job.setJob_type(JobType.OFFSHORE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (JobType.OFFSHORE.toString().contentEquals(job_type)) {
				job.setJob_type(JobType.OFFSHORE);
			} else if (JobType.OFFSHORE_RECRUITMENT.toString().contentEquals(job_type)) {
				job.setJob_type(JobType.OFFSHORE_RECRUITMENT);
			} else if (JobType.RECRUITMENT.toString().contentEquals(job_type)) {
				job.setJob_type(JobType.RECRUITMENT);
			} else {
				job.setJob_type(JobType.OFFSHORE);
			}

			job.setLocation(location);

			if (WorkingMode.HYBRID.toString().contentEquals(working_mode)) {
				job.setWorking_mode(WorkingMode.HYBRID);
			} else if (WorkingMode.REMOTE.toString().contentEquals(working_mode)) {
				job.setWorking_mode(WorkingMode.REMOTE);
			} else if (WorkingMode.WFH.toString().contentEquals(working_mode)) {
				job.setWorking_mode(WorkingMode.WFH);
			} else if (WorkingMode.WFO.toString().contentEquals(working_mode)) {
				job.setWorking_mode(WorkingMode.WFO);
			} else {
				job.setWorking_mode(WorkingMode.WFH);
			}
			job.setQualification(qualification);
			boolean check = Jobs_repos.saveJobs(job);
			try {
				if (check == true) {
					mailService(session, job_type, jd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (check == false) {

				response.setMessage("Cannot created new job now!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				response.setMessage("New job is created !");
				response.setBody(job);
				response.setStatus(true);
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
	public ResponseEntity<ResponseDto> searchByOptions(HttpSession session, String token, long job_id, int client_id,
			String jobtype, String domain, String skills, String location, double min_yr_exp, double max_yr_exp,
			double max_yr_budget, String working_mode, int open) {

		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();
//	String partnerType =(String) request.getSession().getAttribute("partnerType");
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
			if (job_id != 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.fetchById(session, job_id);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (job_id == 0 && client_id != 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByClientId(session, client_id);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() == false && domain.isBlank()
					&& skills.isBlank() && location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0
					&& max_yr_budget == 0 && working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByJobType(session, jobtype);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() == false
					&& skills.isBlank() && location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0
					&& max_yr_budget == 0 && working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByDomain(session, domain);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank()
					&& skills.isBlank() == false && location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0
					&& max_yr_budget == 0 && working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findBySkill(session, skills);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() == false && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByLocation(session, location);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp != 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByMin_yr_exp(session, min_yr_exp);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp != 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByMax_yr_exp(session, max_yr_exp);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget != 0
					&& working_mode.isBlank() && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByMax_yr_budget(session, max_yr_budget);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() == false && open == (-1)) {
				List<Jobs> list = Jobs_repos.findByWorking_mode(session, working_mode);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
					response.setBody(list);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
					&& location.isBlank() && min_yr_exp == 0 && max_yr_exp == 0 && max_yr_budget == 0
					&& working_mode.isBlank() && open != (-1)) {
				List<Jobs> list = Jobs_repos.findByOpen(session, open);
				if (list.isEmpty() || list == null) {
					response.setMessage("Does not found");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("searched jobs");
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

//	public boolean testJobType(String type) {
//	JobType OFFSHORE = JobType.valueOf(type.toUpperCase());
//	if(JobType.OFFSHORE.toString().contentEquals(type)) {
//		return
//	}
//
//	}

	public boolean testWorkingMode(String type) {
		if (WorkingMode.HYBRID.name() == type) {
			return true;
		} else if (WorkingMode.REMOTE.name() == type) {
			return true;
		} else if (WorkingMode.WFH.name() == type) {
			return true;
		} else if (WorkingMode.WFO.name() == type) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public ResponseEntity<ResponseDto> addrecord(HttpSession session, int client_id, String domain, String job_role,
			String skills, String jd, double min_yr_exp, double max_yr_exp, double max_yr_budget, String qualification,
			String location, String job_type, String working_mode, int head_count, int notice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseDto> searchByOptions(HttpServletRequest request, long job_id, int client_id,
			String jobtype, String domain, String skills, String location, double min_yr_exp, double max_yr_exp,
			double max_yr_budget, String working_mode, int open) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseDto> fetchAllRecords(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pdfFileWriter(File file, String job_descrition) {

//		String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf";
//		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/JOB_DESCRIPTION.pdf";
		String filePath = file.getAbsolutePath();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Paragraph paragraph = new Paragraph(job_descrition);
			document.add(paragraph);
			document.close();

			System.out.println("PDF file created and data written successfully.");
		} catch (DocumentException | IOException err) {
			System.out.println("An error occurred while creating the PDF file.");
			err.printStackTrace();
		}
	}

	@Override
	public ResponseEntity<Resource> downloadAllServices(HttpSession session, String job_id) {
		// TODO Auto-generated method stub
		return null;
	}

//	if (job_id == 0 && client_id == 0 && jobtype.isBlank() && domain.isBlank() && skills.isBlank()
//			&& location.isBlank() && min_yr_exp == 0.0 && max_yr_exp == 0.0 && max_yr_budget == 0.0
//			&& working_mode.isBlank() && open == 0) {
//		List<Jobs> list = Jobs_repos.findAll(session);
//		System.out.println("service");
//		if (list.isEmpty() || list == null) {
//			response.setMessage("Does not found");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			response.setMessage("searched jobs");
//			response.setBody(list);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//	} else
}
