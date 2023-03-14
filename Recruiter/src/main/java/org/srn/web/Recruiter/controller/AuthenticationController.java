package org.srn.web.Recruiter.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
/*import org.springframework.web.bind.annotation.CrossOrigin;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.Configuration.jwt.JWTConfig;
import org.srn.web.Recruiter.dto.AuthenticationRequest;
import org.srn.web.Recruiter.dto.AuthenticationResponse;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.UserMaster;
import org.srn.web.Recruiter.service.UserMasterService;
import org.srn.web.Recruiter.service_impl.MyUserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/Recruit/login")
@Api(value = "Login system", description = "Login Management Operation", tags = "Login Controller")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private MSRequestFilter msRequestFilter;

	@Autowired
	private JWTConfig jWTConfig;

	@Autowired
	private UserMasterService masterService;

	Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@GetMapping("/welcome")
	public String welcomeWorld() {
		return "Welcome to HrLancer application !";
	}

	@PostMapping("/doAnyLogin")
	public ResponseEntity<AuthenticationResponse> doLogin(HttpServletRequest request,
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		HttpSession session = request.getSession();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			log.info("Incorrect username or password");
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "Incorrect username or password.", new ArrayList<Object>()),
					HttpStatus.OK);
		}

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwtToken = jWTConfig.generateToken(userDetails);
		final String validateToken = msRequestFilter.getAllTokenData(session, jwtToken);
		UserMaster user = masterService.varifyPartnerIdOfUser(authenticationRequest.getEmail());
		if (user == null) {
			return new ResponseEntity<AuthenticationResponse>(
					new AuthenticationResponse(null, false, "User does not have partner_id", null), HttpStatus.OK);
		}
		System.out.println(validateToken);
		return new ResponseEntity<AuthenticationResponse>(
				new AuthenticationResponse(jwtToken, true, "Congrats ! you have login successfully.", user),
				HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/add")
	@ApiOperation(value = "add new user ", notes = "This Rest Api add new user to use Recruiter application")
	public ResponseEntity<ResponseDto> addUser(HttpSession session,@RequestHeader("Authorization") String token,
			@ApiParam(value = "Enter your name", required = false) @RequestParam(defaultValue = "", required = false) String name,
			@ApiParam(value = "Enter your strong password", required =false) @RequestParam(defaultValue = "", required = false) String password,
			@ApiParam(value = "Enter your company", required = false) @RequestParam(defaultValue = "", required = false) String company,
			@ApiParam(value = "Enter your email id", required = false) @RequestParam(defaultValue = "", required = false) String email,
			@ApiParam(value = "Enter your designation", required = false) @RequestParam(defaultValue = "", required = false) String designation,
			@ApiParam(value = "Enter your contact", required = false) @RequestParam(defaultValue = "", required = false) String contact) {
		

		return masterService.addUser(session,token,name,password ,company,email,designation,contact);
	}

//	@PostMapping("/doAdminLogin")
//	public ResponseEntity<AuthenticationResponse> doAdminLogin(@RequestBody AuthenticationRequest authenticationRequest)
//			throws Exception {
//
//		try {
//
//			if (myUserDetailsService.validateRole(authenticationRequest.getEmail())) {
//				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//						authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//			} else {
//				return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(null, false,
//						"User doesn't have right to access admin service.", new ArrayList<Object>()),
//						HttpStatus.FORBIDDEN);
//			}
//		} catch (BadCredentialsException e) {
//			log.info("Incorrect username or password");
//			return new ResponseEntity<AuthenticationResponse>(
//					new AuthenticationResponse(null, false, "Incorrect username or password.", new ArrayList<Object>()),
//					HttpStatus.OK);
//		}
//
//		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
//		final String jwtToken = jWTConfig.generateToken(userDetails);
//
//		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwtToken, true,
//				"Congrats ! you have logon successfully.", new ArrayList<Object>()), HttpStatus.OK);
//	}

//	@GetMapping("/getUser")
//	public ResponseEntity<ResponseDtoLogin> getUser(@RequestHeader("Authorization") String token,
//			@RequestParam(required = false) String email) {
//
//		ResponseDtoLogin response = new ResponseDtoLogin();
//		log.info(LoggingMessageConstants.INCOMING_WITH_MESSAGE + email);
//
//		response.reset();
//
//		if (email == null || email.isEmpty()) {
//			response.setStatus(false);
//			response.setMessage("email" + RequestConstants.PARAM_MISSING_OR_BLANK);
//
//			return new ResponseEntity<ResponseDtoLogin>(response, HttpStatus.OK);
//		} else {
//
//			Object dto = masterService.getUser(email);
//
//			if (dto != null) {
//				response.setData(dto);
//				response.setStatus(true);
//				response.setMessage(ResponseConstants.REQUEST_SUCCESS);
//			}
//
//			return new ResponseEntity<ResponseDtoLogin>(response, HttpStatus.OK);
//		}
//	}

//	@PostMapping(value= "/tokenDecoder")       
//	public String tokenDecode(@RequestHeader("Authorization") String token) throws UnsupportedEncodingException {
//		// header.payload.signature
//		java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
//		String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)
//
////		String headerJson = new String(decoder.decode(parts[0]));
//		String payloadJson = new String(decoder.decode(parts[1]));
//		// String signatureJson = new String(decoder.decode(parts[2])); String payload =
//		// token.split("\\.")[1];
////		System.out.println(Base64.decodeBase64(payload));
////		return new String(Base64.decodeBase64(payload),"UTF-8");
//		return payloadJson;
//	}
}
