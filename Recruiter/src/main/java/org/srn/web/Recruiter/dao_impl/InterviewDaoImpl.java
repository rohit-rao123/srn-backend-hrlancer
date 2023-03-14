package org.srn.web.Recruiter.dao_impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Interviews;

@Repository
@SuppressWarnings("unchecked")
public class InterviewDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Interviews> searchBookSlots(Long jobId, Long partnerId, String dt) {

		if (jobId != 0 && partnerId == 0 && dt.isBlank()) {
			String query = "select * from interviews as i where i.job_id=:jid";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).setParameter("jid", jobId).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				;
				return null;
			}
		} else if (jobId == 0 && partnerId != 0 && dt.isBlank()) {
			String query = "select * from interviews as i where i.partner_id=:pid";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).setParameter("pid", partnerId).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				;
				return null;
			}
		} else if (jobId == 0 && partnerId == 0 && !dt.isBlank()) {
			String query = "select * from interviews as i where cast(i.dt as date)=:date";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).setParameter("date", dt).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (jobId != 0 && partnerId != 0 && dt.isBlank()) {
			String query = "select * from interviews as i where i.job_id=:jid and i.partner_id=:pid";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).setParameter("jid", jobId).setParameter("pid", partnerId)
						.getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				;
				return null;
			}
		} else if (jobId != 0 && partnerId != 0 && !dt.isBlank()) {
			String query = "select * from interviews as i where i.job_id=:jid and i.partner_id=:pid and cast(i.dt as date)=:date";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).setParameter("jid", jobId).setParameter("pid", partnerId)
						.setParameter("date", dt).getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

//		List<Interviews> interviews;
//		Date date_start = null;
//		String queryString = "FROM Interviews i WHERE 1=1";
//		if (jobId > 0) {
//			queryString += " AND i.job_id =:jobId";
//		}
//		if (partnerId > 0) {
//			queryString += " AND i.partner_id =:partnerId";
//		}
//		if (dt != null) {
//
//			try {
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				date_start = dateFormat.parse(dt);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			queryString += " AND i.dt =:date_start";
//
//		}
//
//		Query<Interviews> query = sessionFactory.getCurrentSession().createQuery(queryString, Interviews.class);
//
//		if (jobId > 0) {
//			query.setParameter("jobId", jobId);
//		}
//		if (partnerId > 0) {
//			query.setParameter("partnerId", partnerId);
//		}
//		if (dt != null) {
//			query.setParameter("date_start", date_start);
//		}
//
//		interviews = query.getResultList();
//		return interviews;

	}

	public boolean save(Interviews interview) {
		// TODO Auto-generated method stub
		try {
			long new_interview_id = (long) sessionFactory.getCurrentSession().save(interview);
			System.out.println("New record created :" + new_interview_id);
			if (new_interview_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	public List<String> getBookedTime() {
//		String sql = "SELECT slot_start_dt FROM Interviews";
//		Session session = sessionFactory.getCurrentSession();
//
//		Query<Timestamp> query = session.createNativeQuery(sql);
//		List<Timestamp> timestamps = query.list();
//		List<String> result = timestamps.stream().map(t -> t.toString()).collect(Collectors.toList());
//		return result;
//	}

	public List<String> getBookedTime() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<String> criteria = builder.createQuery(String.class);
		Root<Interviews> root = criteria.from(Interviews.class);
		criteria.select(root.get("slot_start_dt").as(String.class));
		List<String> timestamps = session.createQuery(criteria).getResultList();
		return timestamps;
	}

	public List<Interviews> search(HttpSession session, String interviewName, long applicationId, long clientId,
			String email, String contact) {
		long partner_id = (long) session.getAttribute("partner_id");

		if (partner_id == 1) {
			if (!interviewName.isBlank() && applicationId == 0 && clientId == 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.interviewer_name like :name";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("name", "%" + interviewName + "%")
							.getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else if (interviewName.isBlank() && applicationId != 0 && clientId == 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.application_id=:id";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("id", applicationId).getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId != 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.client_id=:id";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("id", clientId).getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId == 0 && !email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from applications as a inner join profile as p on a.profile_id=p.profile_id where p.email like :mail";
				List<Applications> appList = new ArrayList<Applications>();
				List<Interviews> list = new ArrayList<Interviews>();
				try {
					appList = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("mail", "%" + email + "%").addEntity(Applications.class).getResultList();
					appList.stream().forEach(e -> {
						List<Interviews> interviewsList = getByAppId(e.getApplication_id());
						if (interviewsList == null || interviewsList.isEmpty()) {

						} else {
							for (Interviews intv : interviewsList) {
								list.add(intv);
							}
						}
					});
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId == 0 && email.isBlank()
					&& !contact.isBlank()) {
				String query = "select * from applications as a inner join profile as p on a.profile_id=p.profile_id where p.contact like :cntct";
				List<Applications> appList = new ArrayList<Applications>();
				List<Interviews> list = new ArrayList<Interviews>();
				try {
					appList = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("cntct", "%" + contact + "%").addEntity(Applications.class).getResultList();
					appList.stream().forEach(e -> {
						List<Interviews> interviewsList = getByAppId(e.getApplication_id());
						if (interviewsList == null || interviewsList.isEmpty()) {

						} else {
							for (Interviews intv : interviewsList) {
								list.add(intv);
							}
						}
					});
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		} else {
			if (!interviewName.isBlank() && applicationId == 0 && clientId == 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.interviewer_name like :name and i.partner_id=:pid";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("name", "%" + interviewName + "%")
							.setParameter("pid", partner_id).getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else if (interviewName.isBlank() && applicationId != 0 && clientId == 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.application_id=:id and i.partner_id=:pid";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("id", applicationId)
							.setParameter("pid", partner_id).getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId != 0 && email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from interviews as i where i.client_id=:id and i.partner_id=:pid";
				try {
					List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
							.addEntity(Interviews.class).setParameter("id", clientId).setParameter("pid", partner_id)
							.getResultList();
					return list;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId == 0 && !email.isBlank()
					&& contact.isBlank()) {
				String query = "select * from applications as a inner join profile as p on a.profile_id=p.profile_id where p.email like :mail";
				List<Applications> appList = new ArrayList<Applications>();
				List<Interviews> list = new ArrayList<Interviews>();
				try {
					appList = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("mail", "%" + email + "%").addEntity(Applications.class).getResultList();
					System.out.println(appList.toString());
					appList.stream().forEach(e -> {
						List<Interviews> interviewsList = getByAppIdWithPartnerId(session, e.getApplication_id());
						if (interviewsList == null || interviewsList.isEmpty()) {

						} else {
							for (Interviews intv : interviewsList) {
								list.add(intv);
							}
						}
					});
					return list;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else if (interviewName.isBlank() && applicationId == 0 && clientId == 0 && email.isBlank()
					&& !contact.isBlank()) {
				String query = "select * from applications as a inner join profile as p on a.profile_id=p.profile_id where p.contact like :cntct ";
				List<Applications> appList = new ArrayList<Applications>();
				List<Interviews> list = new ArrayList<Interviews>();
				try {
					appList = sessionFactory.getCurrentSession().createNativeQuery(query)
							.setParameter("cntct", "%" + contact + "%").addEntity(Applications.class).getResultList();
					System.out.println(appList.toString());
					appList.stream().forEach(e -> {
						List<Interviews> interviewsList = getByAppIdWithPartnerId(session, e.getApplication_id());
						if (interviewsList == null || interviewsList.isEmpty()) {

						} else {
							for (Interviews intv : interviewsList) {
								list.add(intv);
							}
						}
					});
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

	public List<Interviews> getByAppId(long application_id) {
		String query = "select * from interviews as i where i.application_id=:appid order by interview_id desc";
		try {
			List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("appid", application_id).addEntity(Interviews.class).getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Interviews> getByAppIdWithPartnerId(HttpSession session, long application_id) {
		long partner_id = (long) session.getAttribute("partner_id");
		String query = "select * from interviews as i where i.application_id=:appid and i.partner_id=:pid order by interview_id desc";
		try {
			List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.setParameter("pid", partner_id).setParameter("appid", application_id).addEntity(Interviews.class)
					.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Interviews> getAllInterview(HttpSession session) {
		long partner_id = (long) session.getAttribute("partner_id");
		System.out.println(partner_id);
		// TODO Auto-generated method stub

		if (partner_id == 1) {
			String query = "select * from interviews order by interview_id desc";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.addEntity(Interviews.class).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from interviews as i where i.partner_id=:pid order by i.interview_id desc";
			try {
				List<Interviews> list = sessionFactory.getCurrentSession().createNativeQuery(query)
						.setParameter("pid", partner_id).addEntity(Interviews.class).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

//		// TODO Auto-generated method stub
//		List<Interviews> interviews;
//		String queryString = "FROM Interviews i WHERE 1=1";
//		if (interviewId > 0) {
//			queryString += " AND i.interview_id =:interviewId";
//		}
//		if (applicationId > 0) {
//			queryString += " AND i.application_id =:applicationId";
//		}
//		if (interviewName != null) {
//			queryString += " AND i.interviewer_name =:interviewName";
//
//		}
//
//		Query<Interviews> query = sessionFactory.getCurrentSession().createQuery(queryString, Interviews.class);
//
//		if (interviewId > 0) {
//			query.setParameter("interviewId", interviewId);
//		}
//		if (applicationId > 0) {
//			query.setParameter("applicationId", applicationId);
//		}
//		if (interviewName != null) {
//			query.setParameter("interviewName", interviewName);
//		}
//
//		interviews = query.getResultList();
//		return interviews;

}
