
package org.srn.web.Recruiter.dao_impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.srn.web.Recruiter.entity.Invoices;

@Repository
@SuppressWarnings("unchecked")

public class Invoices_dao_impl {

	@Autowired
	private SessionFactory sessionRef;

	public boolean saveInvoices(Invoices invoice) {
		try {
			long new_invoice_id = (long) sessionRef.getCurrentSession().save(invoice);
			System.out.println("New record created :" + new_invoice_id);
			if (new_invoice_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Invoices> fetchByMultipleParam(String partner_invoice_raised_id, Double partner_invoice_amt,
			int partner_invoice_items, Date partner_invoice_raised_dt, String partner_transaction_id,
			Date partner_invoice_paid_dt, Double partner_invoice_remaining_amt, String client_remaining_amt_comment,
			String partner_invoice_updator, Date partner_invoice_updator_dt, EnumType partner_invoice_status) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Invoices> fetchByraisedID(HttpSession session, String partnerInvoiceRaisedId) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_raised_id like :identity";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("identity", "%" + partnerInvoiceRaisedId + "%").getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_raised_id like :identity and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("identity", "%" + partnerInvoiceRaisedId + "%").setParameter("pid", partner_id)
						.getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypiamt(HttpSession session, double partner_invoice_amt) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_amt =:amt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("amt", partner_invoice_amt).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_amt =:amt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("amt", partner_invoice_amt).setParameter("pid", partner_id).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypidt(HttpSession session, String partnerInvoiceRaisedDt) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where cast(a.partner_invoice_raised_dt as date) =:dt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("dt", partnerInvoiceRaisedDt).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where cast(a.partner_invoice_raised_dt as date) =:dt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("dt", partnerInvoiceRaisedDt).setParameter("pid", partner_id).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchByptid(HttpSession session, String partnerTransactionId) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_transaction_id like :ptid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("ptid", "%" + partnerTransactionId + "%").getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_transaction_id like :ptid and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("ptid", "%" + partnerTransactionId + "%").setParameter("pid", partner_id)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypitems(HttpSession session, int partnerInvoiceItems) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_items like :pitems";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pitems", partnerInvoiceItems).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_items like :pitems and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pitems", partnerInvoiceItems).setParameter("pid", partner_id).getResultList();
				System.out.println(list.toString());
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypiremamt(HttpSession session, double partnerInvoiceRemainingAmt) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_remaining_amt =:premamt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("premamt", partnerInvoiceRemainingAmt).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_remaining_amt =:premamt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("premamt", partnerInvoiceRemainingAmt).setParameter("pid", partner_id)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypipaidt(HttpSession session, String partnerInvoicePaidDt) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where cast(a.partner_invoice_paid_dt as date) =:pipaidt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pipaidt", partnerInvoicePaidDt).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where cast(a.partner_invoice_paid_dt as date) =:pipaidt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pipaidt", partnerInvoicePaidDt).setParameter("pid", partner_id).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypramtcmt(HttpSession session, String partnerRemainingAmtComment) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_remaining_amt_comment like :pramtcmt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pramtcmt", "%" + partnerRemainingAmtComment + "%").getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_remaining_amt_comment like :pramtcmt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pramtcmt", "%" + partnerRemainingAmtComment + "%")
						.setParameter("pid", partner_id).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypiupdt(HttpSession session, String partnerInvoiceUpdator) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_updator like :piupdator";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("piupdator", "%" + partnerInvoiceUpdator + "%").getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_updator like :piupdator and a.partner_id";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("piupdator", "%" + partnerInvoiceUpdator + "%").setParameter("pid", partner_id)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypiupDt(HttpSession session, String partnerInvoiceUpdatorDt) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where cast(a.partner_invoice_updator_dt as date) =:piupdt";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("piupdt", partnerInvoiceUpdatorDt).getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where cast(a.partner_invoice_updator_dt as date) =:piupdt and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("piupdt", partnerInvoiceUpdatorDt).setParameter("pid", partner_id)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchBypistatus(HttpSession session, String partnerInvoiceStatus) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select * from invoices as a where a.partner_invoice_status like :pistatus";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pistatus", "%" + partnerInvoiceStatus + "%").getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		} else {
			String query = "select * from invoices as a where a.partner_invoice_status like :pistatus and a.partner_id=:pid";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pistatus", "%" + partnerInvoiceStatus + "%").setParameter("pid", partner_id)
						.getResultList();
				return list;
			} catch (Exception e) {
				return null;
			}
		}

	}

	public List<Invoices> fetchAll(HttpSession session) {
		long partner_id = (long) session.getAttribute("partner_id");
		if (partner_id == 1) {
			String query = "select  * from  invoices order by invoice_id desc";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.getResultList();
				System.out.println(list.toString());
				if (list.isEmpty()) {
					List<Invoices> list1 = new ArrayList<Invoices>();
					return list1;
				} else {
					return list;
				}
			} catch (Exception e) {
				List<Invoices> list = new ArrayList<Invoices>();
				return list;
			}
		} else {
			String query = "select  * from  invoices as a  where a.partner_id=:pid  order by invoice_id desc";
			try {
				List<Invoices> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Invoices.class)
						.setParameter("pid", partner_id).getResultList();
				System.out.println(list.toString());
				if (list.isEmpty()) {
					List<Invoices> list1 = new ArrayList<Invoices>();
					return list1;
				} else {
					return list;
				}
			} catch (Exception e) {
				return null;
			}
		}

	}

}