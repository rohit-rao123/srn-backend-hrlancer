package org.srn.web.Recruiter.Configuration.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.srn.web.Recruiter.Configuration.jwt.JWTConfig;
import org.srn.web.Recruiter.constant.MutableHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MSRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTConfig jWTConfig;

	Logger log = LoggerFactory.getLogger(MSRequestFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authenticationHeader = request.getHeader("Authentication");

		String username = null;
		String jwtToken = null;

		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
			jwtToken = authenticationHeader.substring(7);
			username = jWTConfig.extractUsername(jwtToken);
		}

		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
		mutableRequest.putHeader("email", username);

		log.info("Microserivices Request is executed...");

		filterChain.doFilter(mutableRequest, response);
	}

	public String getAllTokenData(HttpSession session, String jwtToken) {
try {

	java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
	String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)

	String payloadJson = new String(decoder.decode(parts[1]));
	String payload = "[" + payloadJson + "]";
	System.out.println(payloadJson);

	boolean checkSession = true;

	JSONArray array1 = new JSONArray(payload);
	JSONObject object = null;
	JSONObject object2 = null;
	for (int i = 0; i < array1.length(); i++) {
		object = array1.getJSONObject(i);
		object2 = object.getJSONObject("data");
		System.out.println(object.getJSONObject("data"));

	}
	String name = object2.getString("name");
	String email = object2.getString("email");
	long partner_id = object2.getLong("partner_id");
	String contact = object2.getString("contact");
	String company = object2.getString("company");
	String role = object2.getString("role");
	String designation = object2.getString("designation");

	System.out.println("print name here" + name);
	System.out.println(partner_id);

//	session.setMaxInactiveInterval(3600);
	
	session.setAttribute("isSession", "true");
	session.setAttribute("name", name);
	session.setAttribute("email", email);
	session.setAttribute("partner_id", partner_id);
	session.setAttribute("contact", contact);
	session.setAttribute("company", company);
	session.setAttribute("role", role);
	session.setAttribute("designation", designation);

	return payloadJson;
}catch(Exception e) {
	e.printStackTrace();
	return null;
}
	}
	
	public void validateToken(HttpSession session, String jwtToken) {
		try {

			java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
			String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)

			String payloadJson = new String(decoder.decode(parts[1]));
			String payload = "[" + payloadJson + "]";
			System.out.println(payloadJson);

			boolean checkSession = true;

			JSONArray array1 = new JSONArray(payload);
			JSONObject object = null;
			JSONObject object2 = null;
			for (int i = 0; i < array1.length(); i++) {
				object = array1.getJSONObject(i);
				object2 = object.getJSONObject("data");
				System.out.println(object.getJSONObject("data"));

			}
			String name = object2.getString("name");
			String email = object2.getString("email");
			long partner_id = object2.getLong("partner_id");
			String contact = object2.getString("contact");
			String company = object2.getString("company");
			String role = object2.getString("role");
			String designation = object2.getString("designation");

			System.out.println("print name here" + name);
			System.out.println(partner_id);

			session.setAttribute("isSession", "true");
			session.setAttribute("name", name);
			session.setAttribute("email", email);
			session.setAttribute("partner_id", partner_id);
			session.setAttribute("contact", contact);
			session.setAttribute("company", company);
			session.setAttribute("role", role);
			session.setAttribute("designation", designation);

			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
			}


}
