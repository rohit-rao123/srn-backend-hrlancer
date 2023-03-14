package org.srn.web.Recruiter.dao;

import org.srn.web.Recruiter.entity.User;

public interface User_dao {

	public User getUser(String username, String password);

	public boolean saveUser(User user);

	public boolean update_password(String email, String new_password);

	public User getByEmail(String email);
	
}
