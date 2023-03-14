package org.srn.web.Recruiter.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srn.web.Recruiter.dao_impl.Partners_dao_impl;
import org.srn.web.Recruiter.dao_impl.User_dao_impl;
import org.srn.web.Recruiter.entity.Partners;
import org.srn.web.Recruiter.entity.User;

@Component
public class SessionManagement {

	@Autowired
	private User_dao_impl user_repos;

	@Autowired
	private Partners_dao_impl partners_dao;

	@SuppressWarnings("unused")
	public HttpSession getSession(HttpServletRequest request, String username) {
		User user = user_repos.getByEmail(username);
		if (user == null) {
			return null;
		} else {
			HttpSession session = request.getSession();
			long p_id =user.getPartner_id();
			System.out.println(p_id);
			Partners partners = partners_dao.getById(p_id);
			System.out.println(partners.toString());
			if (partners == null) {
				return null;
			} else {
				session.setAttribute("userName", username);
				session.setAttribute("partner_id", partners.getPartner_id());
				session.setAttribute("partnerName", partners.getName());
				session.setAttribute("PartnerEmail", partners.getEmail());
				session.setAttribute("contact", partners.getContact());
				session.setAttribute("partnerType", partners.getPartner_type());
				session.setMaxInactiveInterval(1800);

				return session;
			}

		}

	}
}
