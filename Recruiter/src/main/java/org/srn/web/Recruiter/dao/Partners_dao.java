package org.srn.web.Recruiter.dao;

import java.util.List;

import org.srn.web.Recruiter.entity.Partners;

public interface Partners_dao {

	public List<Partners> searchByPartnerIdOrType(Long partner_id, String partner_type);
}
