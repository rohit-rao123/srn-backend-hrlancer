package org.srn.web.Recruiter.service_impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.InterviewDaoImpl;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.InterviewProfile;
import org.srn.web.Recruiter.entity.Interviews;
import org.srn.web.Recruiter.entity.Profiles;

@Service
@Transactional
public class InterviewServiceImpl {

	@Autowired
	private InterviewDaoImpl interviewDaoImpl;

	@Autowired
	private Application_dao_impl app_repos;

	@Autowired
	private Profile_dao_impl profile_repos;

	@Autowired
	private MSRequestFilter msRequestFilter;

	public ResponseEntity<ResponseDto> getAllBookSlot(HttpSession session, String token, long jobId, long partnerId,
			String dt) {
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

			List<Interviews> bookSlots = interviewDaoImpl.searchBookSlots(jobId, partnerId, dt);
			if (bookSlots.isEmpty() || bookSlots == null) {
				response.setMessage("no slot found for interview");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			} else {
				response.setMessage("searched interview slots");
				response.setBody(bookSlots);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseDto> bookSlot(HttpSession session, String token, long application_id, String type,
			int no_of_round, String slot_start_dt, String slot_end_dt, String interview_tool, String interviewer_name,
			int interviewer_mail) {
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
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date start_dt = null;
		Date end_dt = null;
		try {
			if (type == null || type.isBlank()) {
				response.setMessage("type is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if (no_of_round == 0) {
				response.setMessage("no_of_round is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			try {
				if (slot_start_dt.isBlank()) {
					response.setMessage("slot_start_dt is mandatory");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					LocalDateTime localStartDateTime = LocalDateTime.parse(slot_start_dt, inputFormatter);
					String startDt = localStartDateTime.format(outputFormatter);
					start_dt = dateFormat.parse(startDt);
				}
				if (slot_end_dt.isBlank()) {
					response.setMessage("slot_end_dt is mandatory");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					LocalDateTime localEndDateTime = LocalDateTime.parse(slot_end_dt, inputFormatter);
					String endDt = localEndDateTime.format(outputFormatter);
					end_dt = dateFormat.parse(endDt);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Cannot parse date !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if (interview_tool == null || interview_tool.isBlank()) {
				response.setMessage("interview_tool is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if (interviewer_name == null || interviewer_name.isBlank()) {
				response.setMessage("interviewer_name is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			Applications app = app_repos.fetchByApplication(application_id);
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (app == null) {
				response.setMessage("Application_id does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Interviews interview = new Interviews();
				interview.setClient_id(app.getClient_id());
				interview.setJob_id(app.getJob_id());
				interview.setPartner_id((long) session.getAttribute("partner_id"));
				interview.setApplication_id(application_id);
				interview.setType(type);
				interview.setNo_of_round(no_of_round);
				System.out.println(start_dt);
				interview.setSlot_start_dt(start_dt);
				interview.setSlot_end_dt(end_dt);
				interview.setInterview_tool(interview_tool);
				interview.setInterviewer_name(interviewer_name);
				interview.setInterviewer_mail(interviewer_mail);
				interview.setInterview_status("COMPLETED");
				interview.setInterview_result("'CLEARED'");
				interview.setCreator((String) session.getAttribute("email"));
				interview.setDt(new Date());
				interview.setStatus(0);

				// Saving the new Interview object to the database
				boolean check = interviewDaoImpl.save(interview);
				System.out.println(check);
				if (check == true) {
					response.setMessage("Interview saved succesfully !");
					response.setBody(interview);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					response.setMessage("Interview cannot be saved !");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseDto> getAvailableSlots(HttpSession session, String token, String start_dt,
			String Start_Time, String End_Time) {
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
		String start = start_dt + " " + Start_Time;
		String end_dt = start_dt + " " + End_Time;

		try {
			if (start == null || start.trim().length() == 0) {
				response.setMessage("Start date is required");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			if (End_Time == null || End_Time.trim().length() == 0) {
				response.setMessage("Invalid end Time format. Expected format is 'HH:mm:ss'");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			if (Start_Time == null || Start_Time.trim().length() == 0) {
				response.setMessage("Invalid start Time format. Expected format is 'HH:mm:ss'");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			if (end_dt == null || end_dt.trim().length() == 0) {
				response.setMessage("End date is required");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			// Define the time slot range
			Timestamp startTime;
			try {
				startTime = Timestamp.valueOf(start);
			} catch (IllegalArgumentException e) {
				response.setMessage("Invalid start Time format. Expected format is 'HH:mm:ss'");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			Timestamp endTime;
			try {
				endTime = Timestamp.valueOf(end_dt);
			} catch (IllegalArgumentException e) {
				response.setMessage("Invalid end Time format. Expected format is 'HH:mm:ss'");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			if (startTime.after(endTime)) {
				response.setMessage("Start date should be before end date");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.BAD_REQUEST);
			}

			// Define the time slot interval
			int intervalInMinutes = 60;

			List<String> dbTimeList = interviewDaoImpl.getBookedTime();

			List<Timestamp> dbTimeSlots = new ArrayList<Timestamp>();
			for (String xTimestamp : dbTimeList) {
				dbTimeSlots.add(Timestamp.valueOf(xTimestamp));
			}

			// Get the list of all time slots
			List<Timestamp> allTimeSlots = getTimeSlots(startTime, endTime, intervalInMinutes);

			// Find the time slots that are not present in the database
			List<Timestamp> missingTimeSlots = new ArrayList<Timestamp>();
			for (Timestamp timeSlot : allTimeSlots) {
				if (!dbTimeSlots.contains(timeSlot)) {
					missingTimeSlots.add(timeSlot);
				}
			}

			// Log the missing time slots
//			System.out.println("Missing Time Slots:");
//			for (Timestamp timeSlot : missingTimeSlots) {
//				System.out.println(timeSlot);
//			}

			List<Timestamp> body = missingTimeSlots;
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			List<String> formattedTimeSlots = new ArrayList<String>();
			for (Timestamp timestamp : body) {
				String formattedDate = format.format(timestamp);
				formattedTimeSlots.add(formattedDate);
			}

			if (!missingTimeSlots.isEmpty()) {
				response.setMessage("Free Slots Available");
				response.setBody(formattedTimeSlots);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("No Free Slots Available");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static List<Timestamp> getTimeSlots(Timestamp startTime, Timestamp endTime, int intervalInMinutes) {
		List<Timestamp> timeSlots = new ArrayList<Timestamp>();
		Timestamp currentTime = startTime;
		while (currentTime.before(endTime)) {
			timeSlots.add(currentTime);
			currentTime = new Timestamp(currentTime.getTime() + intervalInMinutes * 60 * 1000);
		}
		return timeSlots;
	}
// original
//	public ResponseEntity<ResponseDto> search(HttpSession session, String token, String interviewName,
//			String applicationId, String clientId) {
//		ResponseDto response = new ResponseDto();
//		msRequestFilter.validateToken(session, token);
//
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
//
//		long client_id = 0;
//
//		try {
//
//			if (clientId == null || clientId.isBlank()) {
//			} else if (clientId.matches("\\d+")) {
//				client_id = Long.parseLong(clientId);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			response.setMessage("Enter valid Client Id !");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//		long application_id = 0;
//
//		try {
//
//			if (applicationId == null || applicationId.isBlank()) {
//			} else if (applicationId.matches("\\d+")) {
//				application_id = Long.parseLong(applicationId);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			response.setMessage("Enter valid Application Id !");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
//
//		try {
//			List<InterviewProfile> localList = new ArrayList<InterviewProfile>();
//			if (!interviewName.isBlank() && applicationId.isBlank() && clientId.isBlank()) {
//				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id);
//				if (list == null || list.isEmpty()) {
//
//					response.setMessage(VMessageConstant.Not_Found);
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					
//					list.stream().forEach(e -> {
//					Applications app =	app_repos.fetchByApplication(e.getApplication_id());
//					Profiles prof = profile_repos.getById(app.getProfile_id());
//					InterviewProfile ip = new InterviewProfile();
//					ip.setInterview_id(e.getInterview_id());
//					ip.setCandidate_name(prof.getName());
//					ip.setCandidate_contact(prof.getContact());
//					ip.setCandidate_email(prof.getEmail());
//					ip.setClient_id(e.getClient_id());
//					ip.setJob_id(e.getJob_id());
//					ip.setPartner_id(e.getPartner_id());
//					ip.setApplication_id(e.getApplication_id());
//					ip.setType(e.getType());
//					ip.setNo_of_round(e.getNo_of_round());
//					ip.setSlot_start_dt(e.getSlot_start_dt());
//					ip.setSlot_end_dt(e.getSlot_end_dt());
//					ip.setInterview_tool(e.getInterview_tool());
//					ip.setInterviewer_name(e.getInterviewer_name());
//					ip.setInterviewer_mail(e.getInterviewer_mail());
//					ip.setInterview_status(e.getInterview_status());
//					ip.setInterview_result(e.getInterview_result());
//					ip.setCreator(e.getCreator());
//					ip.setDt(e.getDt());
//					ip.setStatus(e.getStatus());
//					localList.add(ip);
//					});
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(localList);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//
//			} else if (interviewName.isBlank() && !applicationId.isBlank() && clientId.isBlank()) {
//				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id);
//				if (list == null || list.isEmpty()) {
//
//					response.setMessage(VMessageConstant.Not_Found);
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					list.stream().forEach(e -> {
//						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
//						Profiles prof = profile_repos.getById(app.getProfile_id());
//						InterviewProfile ip = new InterviewProfile();
//						ip.setInterview_id(e.getInterview_id());
//						ip.setCandidate_name(prof.getName());
//						ip.setCandidate_contact(prof.getContact());
//						ip.setCandidate_email(prof.getEmail());
//						ip.setClient_id(e.getClient_id());
//						ip.setJob_id(e.getJob_id());
//						ip.setPartner_id(e.getPartner_id());
//						ip.setApplication_id(e.getApplication_id());
//						ip.setType(e.getType());
//						ip.setNo_of_round(e.getNo_of_round());
//						ip.setSlot_start_dt(e.getSlot_start_dt());
//						ip.setSlot_end_dt(e.getSlot_end_dt());
//						ip.setInterview_tool(e.getInterview_tool());
//						ip.setInterviewer_name(e.getInterviewer_name());
//						ip.setInterviewer_mail(e.getInterviewer_mail());
//						ip.setInterview_status(e.getInterview_status());
//						ip.setInterview_result(e.getInterview_result());
//						ip.setCreator(e.getCreator());
//						ip.setDt(e.getDt());
//						ip.setStatus(e.getStatus());
//						localList.add(ip);
//						});
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(localList);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//
//			} else if (interviewName.isBlank() && applicationId.isBlank() && !clientId.isBlank()) {
//				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id);
//				if (list == null || list.isEmpty()) {
//
//					response.setMessage(VMessageConstant.Not_Found);
//					response.setBody(null);
//					response.setStatus(false);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				} else {
//					list.stream().forEach(e -> {
//						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
//						Profiles prof = profile_repos.getById(app.getProfile_id());
//						InterviewProfile ip = new InterviewProfile();
//						ip.setInterview_id(e.getInterview_id());
//						ip.setCandidate_name(prof.getName());
//						ip.setCandidate_contact(prof.getContact());
//						ip.setCandidate_email(prof.getEmail());
//						ip.setClient_id(e.getClient_id());
//						ip.setJob_id(e.getJob_id());
//						ip.setPartner_id(e.getPartner_id());
//						ip.setApplication_id(e.getApplication_id());
//						ip.setType(e.getType());
//						ip.setNo_of_round(e.getNo_of_round());
//						ip.setSlot_start_dt(e.getSlot_start_dt());
//						ip.setSlot_end_dt(e.getSlot_end_dt());
//						ip.setInterview_tool(e.getInterview_tool());
//						ip.setInterviewer_name(e.getInterviewer_name());
//						ip.setInterviewer_mail(e.getInterviewer_mail());
//						ip.setInterview_status(e.getInterview_status());
//						ip.setInterview_result(e.getInterview_result());
//						ip.setCreator(e.getCreator());
//						ip.setDt(e.getDt());
//						ip.setStatus(e.getStatus());
//						localList.add(ip);
//						});
//
//					response.setMessage(VMessageConstant.MESSAGE);
//					response.setBody(localList);
//					response.setStatus(true);
//					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//				}
//
//			} else {
//
//				response.setMessage("Please provide only one search field !");
//				response.setBody(null);
//				response.setStatus(false);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//
//		} catch (Exception e) {
//
//			response.setMessage("Internal server error");
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		}
//	}
	
//change 2	
	public ResponseEntity<ResponseDto> search(HttpSession session, String token, String interviewName,
			String applicationId, String clientId,String email, String contact) {
		ResponseDto response = new ResponseDto();
		msRequestFilter.validateToken(session, token);

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

		long client_id = 0;

		try {

			if (clientId == null || clientId.isBlank()) {
			} else if (clientId.matches("\\d+")) {
				client_id = Long.parseLong(clientId);
			}

		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage("Enter valid Client Id !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		long application_id = 0;

		try {

			if (applicationId == null || applicationId.isBlank()) {
			} else if (applicationId.matches("\\d+")) {
				application_id = Long.parseLong(applicationId);
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage("Enter valid Application Id !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		try {
			List<InterviewProfile> localList = new ArrayList<InterviewProfile>();
			if (!interviewName.isBlank() && applicationId.isBlank() && clientId.isBlank() && email.isBlank() && contact.isBlank()) {
				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id,email,contact);
				if (list == null || list.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					
					list.stream().forEach(e -> {
					Applications app =	app_repos.fetchByApplication(e.getApplication_id());
					Profiles prof = profile_repos.getById(app.getProfile_id());
					InterviewProfile ip = new InterviewProfile();
					ip.setInterview_id(e.getInterview_id());
					ip.setCandidate_name(prof.getName());
					ip.setCandidate_contact(prof.getContact());
					ip.setCandidate_email(prof.getEmail());
					ip.setClient_id(e.getClient_id());
					ip.setJob_id(e.getJob_id());
					ip.setPartner_id(e.getPartner_id());
					ip.setApplication_id(e.getApplication_id());
					ip.setType(e.getType());
					ip.setNo_of_round(e.getNo_of_round());
					ip.setSlot_start_dt(e.getSlot_start_dt());
					ip.setSlot_end_dt(e.getSlot_end_dt());
					ip.setInterview_tool(e.getInterview_tool());
					ip.setInterviewer_name(e.getInterviewer_name());
					ip.setInterviewer_mail(e.getInterviewer_mail());
					ip.setInterview_status(e.getInterview_status());
					ip.setInterview_result(e.getInterview_result());
					ip.setCreator(e.getCreator());
					ip.setDt(e.getDt());
					ip.setStatus(e.getStatus());
					localList.add(ip);
					});

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(localList);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (interviewName.isBlank() && !applicationId.isBlank() && clientId.isBlank() && email.isBlank() && contact.isBlank()) {
				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id,email,contact);
				if (list == null || list.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					list.stream().forEach(e -> {
						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
						Profiles prof = profile_repos.getById(app.getProfile_id());
						InterviewProfile ip = new InterviewProfile();
						ip.setInterview_id(e.getInterview_id());
						ip.setCandidate_name(prof.getName());
						ip.setCandidate_contact(prof.getContact());
						ip.setCandidate_email(prof.getEmail());
						ip.setClient_id(e.getClient_id());
						ip.setJob_id(e.getJob_id());
						ip.setPartner_id(e.getPartner_id());
						ip.setApplication_id(e.getApplication_id());
						ip.setType(e.getType());
						ip.setNo_of_round(e.getNo_of_round());
						ip.setSlot_start_dt(e.getSlot_start_dt());
						ip.setSlot_end_dt(e.getSlot_end_dt());
						ip.setInterview_tool(e.getInterview_tool());
						ip.setInterviewer_name(e.getInterviewer_name());
						ip.setInterviewer_mail(e.getInterviewer_mail());
						ip.setInterview_status(e.getInterview_status());
						ip.setInterview_result(e.getInterview_result());
						ip.setCreator(e.getCreator());
						ip.setDt(e.getDt());
						ip.setStatus(e.getStatus());
						localList.add(ip);
						});

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(localList);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (interviewName.isBlank() && applicationId.isBlank() && !clientId.isBlank()  && email.isBlank() && contact.isBlank()) {
				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id,email,contact);
				if (list == null || list.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					list.stream().forEach(e -> {
						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
						Profiles prof = profile_repos.getById(app.getProfile_id());
						InterviewProfile ip = new InterviewProfile();
						ip.setInterview_id(e.getInterview_id());
						ip.setCandidate_name(prof.getName());
						ip.setCandidate_contact(prof.getContact());
						ip.setCandidate_email(prof.getEmail());
						ip.setClient_id(e.getClient_id());
						ip.setJob_id(e.getJob_id());
						ip.setPartner_id(e.getPartner_id());
						ip.setApplication_id(e.getApplication_id());
						ip.setType(e.getType());
						ip.setNo_of_round(e.getNo_of_round());
						ip.setSlot_start_dt(e.getSlot_start_dt());
						ip.setSlot_end_dt(e.getSlot_end_dt());
						ip.setInterview_tool(e.getInterview_tool());
						ip.setInterviewer_name(e.getInterviewer_name());
						ip.setInterviewer_mail(e.getInterviewer_mail());
						ip.setInterview_status(e.getInterview_status());
						ip.setInterview_result(e.getInterview_result());
						ip.setCreator(e.getCreator());
						ip.setDt(e.getDt());
						ip.setStatus(e.getStatus());
						localList.add(ip);
						});

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(localList);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (interviewName.isBlank() && applicationId.isBlank() && clientId.isBlank()  && !email.isBlank() && contact.isBlank()) {
				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id,email,contact);
				if (list == null || list.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					list.stream().forEach(e -> {
						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
						Profiles prof = profile_repos.getById(app.getProfile_id());
						InterviewProfile ip = new InterviewProfile();
						ip.setInterview_id(e.getInterview_id());
						ip.setCandidate_name(prof.getName());
						ip.setCandidate_contact(prof.getContact());
						ip.setCandidate_email(prof.getEmail());
						ip.setClient_id(e.getClient_id());
						ip.setJob_id(e.getJob_id());
						ip.setPartner_id(e.getPartner_id());
						ip.setApplication_id(e.getApplication_id());
						ip.setType(e.getType());
						ip.setNo_of_round(e.getNo_of_round());
						ip.setSlot_start_dt(e.getSlot_start_dt());
						ip.setSlot_end_dt(e.getSlot_end_dt());
						ip.setInterview_tool(e.getInterview_tool());
						ip.setInterviewer_name(e.getInterviewer_name());
						ip.setInterviewer_mail(e.getInterviewer_mail());
						ip.setInterview_status(e.getInterview_status());
						ip.setInterview_result(e.getInterview_result());
						ip.setCreator(e.getCreator());
						ip.setDt(e.getDt());
						ip.setStatus(e.getStatus());
						localList.add(ip);
						});

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(localList);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else if (interviewName.isBlank() && applicationId.isBlank() && clientId.isBlank()  && email.isBlank() && !contact.isBlank()) {
				List<Interviews> list = interviewDaoImpl.search(session, interviewName, application_id, client_id,email,contact);
				if (list == null || list.isEmpty()) {

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					list.stream().forEach(e -> {
						Applications app =	app_repos.fetchByApplication(e.getApplication_id());
						Profiles prof = profile_repos.getById(app.getProfile_id());
						InterviewProfile ip = new InterviewProfile();
						ip.setInterview_id(e.getInterview_id());
						ip.setCandidate_name(prof.getName());
						ip.setCandidate_contact(prof.getContact());
						ip.setCandidate_email(prof.getEmail());
						ip.setClient_id(e.getClient_id());
						ip.setJob_id(e.getJob_id());
						ip.setPartner_id(e.getPartner_id());
						ip.setApplication_id(e.getApplication_id());
						ip.setType(e.getType());
						ip.setNo_of_round(e.getNo_of_round());
						ip.setSlot_start_dt(e.getSlot_start_dt());
						ip.setSlot_end_dt(e.getSlot_end_dt());
						ip.setInterview_tool(e.getInterview_tool());
						ip.setInterviewer_name(e.getInterviewer_name());
						ip.setInterviewer_mail(e.getInterviewer_mail());
						ip.setInterview_status(e.getInterview_status());
						ip.setInterview_result(e.getInterview_result());
						ip.setCreator(e.getCreator());
						ip.setDt(e.getDt());
						ip.setStatus(e.getStatus());
						localList.add(ip);
						});

					response.setMessage(VMessageConstant.MESSAGE);
					response.setBody(localList);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			} else {

				response.setMessage("Please provide only one search field !");
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


	public ResponseEntity<ResponseDto> fetchAllInterview(HttpSession session, String token) {
		ResponseDto response = new ResponseDto();
		msRequestFilter.validateToken(session, token);

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
			List<InterviewProfile> localList = new ArrayList<InterviewProfile>();
			List<Interviews> list = interviewDaoImpl.getAllInterview(session);
			if (list == null || list.isEmpty()) {
				response.setMessage(" Does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				list.stream().forEach(e -> {
					Applications app =	app_repos.fetchByApplication(e.getApplication_id());
					Profiles prof = profile_repos.getById(app.getProfile_id());
					InterviewProfile ip = new InterviewProfile();
					ip.setInterview_id(e.getInterview_id());
					ip.setCandidate_name(prof.getName());
					ip.setCandidate_contact(prof.getContact());
					ip.setCandidate_email(prof.getEmail());
					ip.setClient_id(e.getClient_id());
					ip.setJob_id(e.getJob_id());
					ip.setPartner_id(e.getPartner_id());
					ip.setApplication_id(e.getApplication_id());
					ip.setType(e.getType());
					ip.setNo_of_round(e.getNo_of_round());
					ip.setSlot_start_dt(e.getSlot_start_dt());
					ip.setSlot_end_dt(e.getSlot_end_dt());
					ip.setInterview_tool(e.getInterview_tool());
					ip.setInterviewer_name(e.getInterviewer_name());
					ip.setInterviewer_mail(e.getInterviewer_mail());
					ip.setInterview_status(e.getInterview_status());
					ip.setInterview_result(e.getInterview_result());
					ip.setCreator(e.getCreator());
					ip.setDt(e.getDt());
					ip.setStatus(e.getStatus());
					localList.add(ip);
					});
				
				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(localList);
				response.setStatus(true);
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
}
