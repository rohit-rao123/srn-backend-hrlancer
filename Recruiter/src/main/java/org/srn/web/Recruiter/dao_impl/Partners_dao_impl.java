package org.srn.web.Recruiter.dao_impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Partners_dao;
import org.srn.web.Recruiter.entity.Partners;

@Repository
@SuppressWarnings("unchecked")
public class Partners_dao_impl implements Partners_dao {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(Partners partner) {
		// TODO Auto-generated method stub
		try {
			long new_partner_id = (long) sessionFactory.getCurrentSession().save(partner);
			System.out.println("New client created :" + new_partner_id);
			if (new_partner_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Partners> searchByPartnerIdOrType(Long partner_id, String partner_type) {
		List<Partners> partners;
		String queryString = "FROM Partners";
		if (partner_id != null) {
			queryString += " WHERE partner_id = :partner_id";
			partners = sessionFactory.getCurrentSession().createQuery(queryString, Partners.class)
					.setParameter("partner_id", partner_id).getResultList();
		} else if (partner_type != null) {
			queryString += " WHERE partner_type = :partner_type";
			partners = sessionFactory.getCurrentSession().createQuery(queryString, Partners.class)
					.setParameter("partner_type", partner_type).getResultList();
		} else {
			partners = sessionFactory.getCurrentSession().createQuery(queryString, Partners.class).getResultList();
		}
		return partners;
	}

	public Partners getById(long partner_id) {
		String query = "select * from partners as p where p.partner_id=:pId";
		try {
			Partners partners = (Partners) sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("pId", partner_id).addEntity(Partners.class).getSingleResult();
			return partners;
		} catch (NoResultException e) {
			return null;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Partners> findByPartner(HttpSession session, int partnerId, String name, String contact, String email,
			String website, String partnerType, String partner_category, double sharingPercentage,
			String partnershipStart, String partnershipEnd, String creator, String dt, int status) {
		// TODO Auto-generated method stub

		long partner_identity = (long) session.getAttribute("partner_id");
		List<Partners> list = new ArrayList<Partners>();
		if (partner_identity == 1) {

			if (partnerId > 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("pid", partnerId)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && !name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.name like :nm";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("nm", "%" + name + "%").addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && !contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.contact  like :cntct";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("cntct", "%" + contact + "%").addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && !email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.email like :mail";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("mail", "%" + email + "%").addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && !website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.website like :www";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("www", "%" + website + "%").addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& !partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_type=:ptype";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("ptype", partnerType).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && !partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_category=:pc";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("pc", partner_category).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage != -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.sharing_percentage=:percent";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("percent", sharingPercentage).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& !partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.partnership_start=:dt";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("dt", partnershipStart).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && !partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.partnership_end=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("dt", partnershipEnd).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && !creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.creator=:crtr";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("crtr", creator)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && !dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where cast(p.dt as date )=:date";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("date", dt)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status != -1) {
				try {
					String query = "select * from partners as p where p.status=:sts";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("sts", status)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}

		} else {
			if (partnerId > 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_id=:pid and p.partner_id=:pid1";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("pid", partnerId)
							.setParameter("pid1", partner_identity).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && !name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.name like :nm  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("nm", "%" + name + "%").setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && !contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.contact  like :cntct  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("cntct", "%" + contact + "%").setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && !email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.email like :mail  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("mail", "%" + email + "%").setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && !website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.website like :www  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("www", "%" + website + "%").setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& !partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_type=:ptype  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("ptype", partnerType).setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && !partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.partner_category=:pc and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("pc", partner_category).setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage != -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.sharing_percentage=:percent  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("percent", sharingPercentage).setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& !partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.partnership_start=:dt  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("dt", partnershipStart).setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					System.out.println(list.toString());
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && !partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where p.partnership_end=:pid  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("dt", partnershipEnd).setParameter("pid", partner_identity)
							.addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && !creator.isBlank() && dt.isBlank()
					&& status == -1) {
				try {
					String query = "select * from partners as p where p.creator=:crtr  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("crtr", creator)
							.setParameter("pid", partner_identity).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && !dt.isBlank()
					&& status == -1) {
				try {

					String query = "select * from partners as p where cast(p.dt as date)=:date  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("date", dt)
							.setParameter("pid", partner_identity).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else if (partnerId == 0 && name.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
					&& partnerType.isBlank() && partner_category.isBlank() && sharingPercentage == -1
					&& partnershipStart.isBlank() && partnershipEnd.isBlank() && creator.isBlank() && dt.isBlank()
					&& status != -1) {
				try {
					String query = "select * from partners as p where p.status=:sts  and p.partner_id=:pid";
					list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("sts", status)
							.setParameter("pid", partner_identity).addEntity(Partners.class).getResultList();
					return list;
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}
		}

	}

	public List<Partners> findAll(HttpSession session) {
		List<Partners> list = null;
		String query = "select * from partners";
		try {
			list = sessionFactory.getCurrentSession().createNativeQuery(query).addEntity(Partners.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Partners> findByPartnerType(HttpSession session, String job_type) {
		List<Partners> list = null;

//		long partner_id = (long) session.getAttribute("partner_id"); 
		if (job_type.contentEquals("RECRUITMENT")) {
			String query = "select * from partners as p  where p.partner_type=:jtype or p.partner_type=:of_rec";
			try {
				list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("jtype", job_type)
						.setParameter("of_rec", "OFFSHORE_RECRUITMENT").addEntity(Partners.class).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (job_type.contentEquals("OFFSHORE")) {
			String query = "select * from partners as p  where p.partner_type=:jtype or p.partner_type=:of_rec";
			try {
				list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("jtype", job_type)
						.setParameter("of_rec", "OFFSHORE_RECRUITMENT").addEntity(Partners.class).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

	
}
