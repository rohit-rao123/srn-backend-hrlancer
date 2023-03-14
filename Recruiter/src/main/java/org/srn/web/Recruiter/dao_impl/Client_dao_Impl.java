package org.srn.web.Recruiter.dao_impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.dao.Client_dao;
import org.srn.web.Recruiter.entity.Clients;
import org.srn.web.Recruiter.entity.Clients.ClientType;
import org.srn.web.Recruiter.entity.Clients.SubscriptionType;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class Client_dao_Impl implements Client_dao {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(Clients client) {

		try {
			long new_client_id = (long) sessionFactory.getCurrentSession().save(client);
			System.out.println("New client created :" + new_client_id);
			if (new_client_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Clients> findByClients(long clientId, String clientName, String contact, String email, String website,
			String clientType, String address, String subscriptionType, int isSubscribed, String createdBy, String dt,
			int status) {
		// TODO Auto-generated method stub
		List<Clients> client = null;
		String queryString = "FROM Clients as c";

		try {
			if (clientId > 0) {
				queryString += " WHERE client_id = :clientId";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("clientId", clientId).getResultList();
				return client;
			} else if (clientName != null && !clientName.isEmpty()) {
				queryString += " WHERE client_name LIKE :name";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("name", "%" + clientName + "%").getResultList();
				return client;
			} else if (contact != null && !contact.isEmpty()) {
				queryString += " WHERE contact LIKE :contact";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("contact", "%" + contact + "%").getResultList();
				return client;
			} else if (email != null && !email.isEmpty()) {
				queryString += " WHERE email LIKE :email";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("email", "%" + email + "%").getResultList();
				return client;
			} else if (website != null && !website.isEmpty()) {
				queryString += " WHERE website LIKE :website";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("website", "%" + website + "%").getResultList();
				return client;
			} else if (address != null && !address.isEmpty()) {
				queryString += " WHERE address LIKE :address";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("address", "%" + address + "%").getResultList();
				return client;
			} else if (clientType != null && !clientType.isEmpty()) {

				try {
					ClientType clientTypeEnum = ClientType.valueOf(clientType);
					String query = "SELECT * FROM clients WHERE client_type LIKE :clientTypeEnum";
					client = sessionFactory.getCurrentSession().createNativeQuery(query, Clients.class)
							.setParameter("clientTypeEnum", "%" + clientTypeEnum + "%").getResultList();
					return client;
				} catch (IllegalArgumentException e) {
					System.out.println(e);
					return null;
				}

			} else if (isSubscribed >= 0) {
				queryString += " WHERE is_subscribed =:isSubscribed";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("isSubscribed", isSubscribed).getResultList();
				return client;
			} else if (subscriptionType != null && !subscriptionType.isEmpty()) {
				try {
					SubscriptionType subscriptionTypeEnum = SubscriptionType.valueOf(subscriptionType);
					queryString += " WHERE subscription_type = :type";
					client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
							.setParameter("type", subscriptionTypeEnum).getResultList();
					return client;

				} catch (IllegalArgumentException e) {
					System.out.println(e);
					return null;

				}
			} else if (createdBy != null && !createdBy.isEmpty()) {
				queryString += " WHERE created_by LIKE :createdBy";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("createdBy", "%" + createdBy + "%").getResultList();
				return client;
			} else if (dt != null && !dt.isEmpty()) {
				try {
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dt);
					queryString += " WHERE cast(dt as date) =:dt";
					client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
							.setParameter("dt", date).getResultList();
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				return client;
			} else if (status >= 0) {
				queryString += " WHERE status = :status";
				client = sessionFactory.getCurrentSession().createQuery(queryString, Clients.class)
						.setParameter("status", status).getResultList();
				return client;
			} else {
				String query = "SELECT * FROM clients";
				client = sessionFactory.getCurrentSession().createNativeQuery(query, Clients.class).getResultList();
				return client;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Clients> findByClients(long clientId, String name, String contact, String email, String website,
			String clientType, double sharingPercentage, String clientshipStart, String clientshipEnd, String creator,
			String dt, int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clients> getAllClients(HttpSession session) {
		// TODO Auto-generated method stub
		String query = "select * from clients order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByClientId(HttpSession session, long client_id) {
		String query = "select * from clients as c where c.client_id=:clid order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("clid", client_id).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByClientName(HttpSession session, String clientName) {
		String query = "select * from clients as c where c.client_name like :name order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("name","%"+ clientName+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByContact(HttpSession session, String contact) {
		String query = "select * from clients as c where c.contact like :cntct order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("cntct","%"+ contact+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByEmail(HttpSession session, String email) {
		String query = "select * from clients as c where c.email like :mail order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("mail","%"+ email+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByWebsite(HttpSession session, String website) {
		String query = "select * from clients as c where c.website like :web order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("web","%"+ website+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByClientType(HttpSession session, String clientType) {
		String query = "select * from clients as c where c.client_type =:cType order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("cType",clientType).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByAddress(HttpSession session, String address) {
		String query = "select * from clients as c where c.address like :add order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("add","%"+ address+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getBySubscriptionType(HttpSession session, String subscriptionType) {
		String query = "select * from clients as c where c.subscription_type =:sType order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("sType",subscriptionType).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByIsSubscribed(HttpSession session, int isSubscribed) {
		String query = "select * from clients as c where c.is_subscribed =:iss order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("iss",isSubscribed).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByCreatedBy(HttpSession session, String createdBy) {
		String query = "select * from clients as c where c.created_by like :cBy order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("cBy","%"+ createdBy+"%").addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByDt(HttpSession session, String dt) {
		String query = "select * from clients as c where cast(c.dt as date) =:dts order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("dts",dt).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Clients> getByStatus(HttpSession session, int status) {
		String query = "select * from clients as c where c.status =:sts order by client_id desc";
		try {
			List<Clients> list = sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("sts",status).addEntity(Clients.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
