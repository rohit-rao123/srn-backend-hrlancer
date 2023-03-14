package org.srn.web.Recruiter.service_impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.ResponseConstants;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao.UserMasterDao;
import org.srn.web.Recruiter.dao_impl.Partners_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.dto.ResponseDtoLogin;
import org.srn.web.Recruiter.entity.Partners;
import org.srn.web.Recruiter.entity.UserMaster;
import org.srn.web.Recruiter.service.UserMasterService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterDao masterDao;
	

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private Partners_dao_impl partner_repos;

	public Object getUser(String email) {
		ResponseDtoLogin response = new ResponseDtoLogin();
		response.reset();

		Object obj = masterDao.getUser(email);

		if (obj != null) {
			return obj;
		} else {
			response.setStatus(false);
			response.setMessage(ResponseConstants.RECORD_NOT_FOUND);
			response.setData(null);
		}

		return null;
	}

	@Override
	public UserMaster varifyPartnerIdOfUser(String email) {
		try {
			UserMaster user = masterDao.varifyPartner(email);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
//		
		}

	}

//	File file =new File ( "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\upload_file\\resume04.pdf");
//	File file = new File(
//			"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf");
	public void mailService(HttpSession session, String email, String password) {

//			
		String info = "username: " + email + "\n" + "password: " + password;
		System.out.println(info);
//		String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\Authentication.pdf";
		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/Authentication.pdf";
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Paragraph paragraph = new Paragraph(info);
			document.add(paragraph);
			document.close();

			System.out.println("PDF file created and data written successfully.");
		} catch (DocumentException | IOException err) {
			System.out.println("An error occurred while creating the PDF file.");
			err.printStackTrace();
		}
		String message = "Hello user you have successfully registered !";
		String subject = "Access credential of HRLancer !";
		String to = email;
		String from = "pankaj3214sharma@gmail.com";
//		File file = new File(
//				"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\Authentication.pdf");
			 File file = new File(
					"/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/Authentication.pdf");
		sendEmail(session, message, subject, to, from, file);

	}

	@Override
	public ResponseEntity<ResponseDto> addUser(HttpSession session, String token, String name,
			String password, String company, String email, String designation, String contact) {
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
		// name,password ,company,email,designation,contact
		UserMaster user = null;
		try {
			if(name.isBlank()) {
				response.setMessage("Name is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} if(password.isBlank()) {
				response.setMessage("password is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} if(company.isBlank()) {
				response.setMessage("company is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} if(email.isBlank()) {
				response.setMessage("email is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} if(designation.isBlank()) {
				response.setMessage("designation is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} if(contact.isBlank()) {
				response.setMessage("contact is mandatory");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
				
//            checking partner validity
			long pId = (long) session.getAttribute("partner_id");
			Partners partner = partner_repos.getById(pId);
			System.out.println(partner.getPartnership_end());
			Date date = partner.getPartnership_end();
			Date currentDate = new Date();
			if (date == null) {

			} else {
				boolean check = currentDate.before(date);
				if (check == false) {
					response.setMessage("Cannot add new user as your partnership expired");
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
//				long user_id, long partner_id, String name, String email, String password, String contact,
//				String company, String designation, String role, String created_by, Date date_of_creation
					user = new UserMaster();
					Date date1 = new Date();
					user.setPartner_id(pId);
					user.setName(name);
					user.setEmail(email);
					user.setPassword(password);
					user.setContact(contact);
					user.setCompany(company);
					user.setDesignation(designation);
					user.setRole("USER");
					user.setCreated_by((String) session.getAttribute("email"));
					user.setDate_of_creation(date1);

					boolean checkDuplicasy = masterDao.getByContactOrEmail(contact, email);
					if (checkDuplicasy == true) {
						response.setMessage("Cannot create duplicate user !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
					boolean checkUser = masterDao.createUser(session, user);

					if (checkUser == true) {
						mailService(session,email ,password);
						response.setMessage("New user created succesfully");
						response.setBody(null);
						response.setStatus(true);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						response.setMessage("Internal server error !");
						response.setBody(null);
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error!");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
		masterDao.createUser(session, user);

		return null;
	}

}
