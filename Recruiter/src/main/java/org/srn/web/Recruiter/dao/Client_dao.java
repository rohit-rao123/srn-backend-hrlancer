package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.Clients;

public interface Client_dao {

	public boolean save(Clients client);
	public List<Clients> findByClients(long clientId, String name, String contact, String email, String website,
			String clientType, double sharingPercentage, String clientshipStart, String clientshipEnd, String creator,
			String dt, int status);
	public List<Clients> getAllClients(HttpSession session);
}
