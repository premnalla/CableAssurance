/**
 * 
 */
package com.palmyrasyscorp.www.servlet;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// import javax.servlet.http.HttpSession;

// import org.apache.jasper.tagplugins.jstl.core.Url;

import com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT;
import com.palmyrasys.www.webservices.CableAssurance.CTEDataT;
import com.palmyrasys.www.webservices.CableAssurance.CTEQueryInputT;
import com.palmyrasyscorp.www.webservices.helper.CteHelper;
import com.palmyrasyscorp.www.jsp.helper.UrlHelper;

/**
 * @author Prem
 * 
 */
public class CsrInputFormServlet extends HttpServlet {

	/**
	 * 
	 * 
	 */
	public CsrInputFormServlet() {
		super();
	}

	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// RequestDispatcher rd;
		// String nextPage;
		//
		// HttpSession sessn = req.getSession();
		// synchronized (sessn) {
		// Object o = sessn.getAttribute(ServletConstants.USER);
		// if (o == null) {
		// // System.out.println("Didn't find user");
		// nextPage = "/login_frm.jsp";
		// // String userName = req.getParameter(CAUser.USER_NAME);
		// // String password = req.getParameter(CAUser.PASSWORD);
		// // CAUser user = new CAUser(userName, password);
		// // req.setAttribute(ServletConstants.USER, user);
		// } else {
		// String op = req.getParameter(ServletConstants.OP);
		// if (op != null && op.equalsIgnoreCase(ServletConstants.LOGOUT)) {
		// sessn.removeAttribute(ServletConstants.USER);
		// o = null;
		// nextPage = "/login_frm.jsp";
		// } else {
		// nextPage = "/app/initial_data_page.jsp";
		// }
		// }
		// }
		// rd = getServletContext().getRequestDispatcher(nextPage);
		// rd.forward(req, resp);

	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// this.doGet(req, resp);

		RequestDispatcher rd;
		String nextPage = "/app/empty_frm.jsp";

		// HttpSession sessn = req.getSession();
		String accountNumber = req
				.getParameter(ServletConstants.CSR_ACCOUNT_NUM);
		String customerName = req
				.getParameter(ServletConstants.CSR_CUSTOMER_NAME);
		String mtaMac = req.getParameter(ServletConstants.CSR_MTA_MAC);
		String cmMac = req.getParameter(ServletConstants.CSR_CM_MAC);

		boolean gotData = false;

		HttpSession session = req.getSession();

		synchronized (session) {
			session.setAttribute(ServletConstants.CUSTOMER_NAMES_LIST, null);
			session.setAttribute(ServletConstants.CUSTOMER, null);
			session.setAttribute(ServletConstants.HTTP_CM, null);
			session.setAttribute(ServletConstants.HTTP_MTA, null);
		}

		if (accountNumber != null && accountNumber.length() != 0) {
			CTEDataT cust = getCustomerByAccountNumber(accountNumber);
			synchronized (session) {
				session.setAttribute(ServletConstants.CUSTOMER, cust);
			}
			if (cust != null) {
				StringBuffer sb = new StringBuffer(
						"/app/csr_portal_result_frm.jsp?");
				CTEAbstractMacT m = cust.getMta();
				CTEAbstractMacT c = cust.getCm();
				if (m != null && m.getMac() != null) {
					sb.append(UrlHelper.TYPE).append("=").append(UrlHelper.MTA)
							.append("&").append(UrlHelper.MAC).append("=")
							.append(m.getMac());
				} else if (c != null && c.getMac() != null) {
					sb.append(UrlHelper.TYPE).append("=").append(UrlHelper.CM)
							.append("&").append(UrlHelper.MAC).append("=")
							.append(c.getMac());
				} else {
					// ...
				}
				nextPage = sb.toString();
				gotData = true;
			}
		}

		if (!gotData) {
			if (customerName != null && customerName.length() != 0) {
				CTEDataT[] names = getCustomersByName(customerName);
				if (names != null) {
					if (names.length == 1) {
						nextPage = "/app/csr_portal_result_frm.jsp";
						synchronized (session) {
							session.setAttribute(ServletConstants.CUSTOMER,
									names[0]);
						}
						gotData = true;
					} else if (names.length > 1) {
						nextPage = "/app/csr_portal_multi_results_frm.jsp";
						LinkedList lNames = new LinkedList();
						for (int i = 0; i < names.length; i++) {
							lNames.add(names[i]);
						}
						synchronized (session) {
							session.setAttribute(
									ServletConstants.CUSTOMER_NAMES_LIST,
									lNames);
						}
						gotData = true;
					} else {
						// not an error. but not good either. indeterminate
					}
				}
			}
		}

		if (!gotData) {
			if (mtaMac != null && mtaMac.length() != 0) {
				StringBuffer sb = new StringBuffer(
						"/app/csr_portal_result_frm.jsp?");
				sb.append(UrlHelper.TYPE).append("=").append(UrlHelper.MTA)
						.append("&").append(UrlHelper.MAC).append("=").append(
								mtaMac);
				nextPage = sb.toString();
				gotData = true;
			}
		}

		if (!gotData) {
			if (cmMac != null && cmMac.length() != 0) {
				StringBuffer sb = new StringBuffer(
						"/app/csr_portal_result_frm.jsp?");
				sb.append(UrlHelper.TYPE).append("=").append(UrlHelper.CM)
						.append("&").append(UrlHelper.MAC).append("=").append(
								cmMac);
				nextPage = sb.toString();
				gotData = true;
			}
		}

		// System.out.println("Next page: " + nextPage);

		rd = getServletContext().getRequestDispatcher(nextPage);
		rd.forward(req, resp);
	}

	/**
	 * 
	 * @param customerName
	 * @return
	 */
	private CTEDataT[] getCustomersByName(String customerName) {
		CTEDataT[] ret = null;

		try {
			String firstName = "";
			String lastName = "";
			StringTokenizer tok = new StringTokenizer(customerName, ",");
			if (tok.hasMoreTokens()) {
				firstName = tok.nextToken();
			}
			if (tok.hasMoreTokens()) {
				lastName = tok.nextToken();
			}

			/*
			 * Call Service to get list of matching customer names
			 */
			CteHelper ch = new CteHelper();
			CTEQueryInputT[] queryInput = new CTEQueryInputT[1];
			queryInput[0] = new CTEQueryInputT();
			queryInput[0].setFirstName(firstName);
			queryInput[0].setLastName(lastName);
			ret = ch.getCteData(queryInput);
			// CsrPortalHelper h = new CsrPortalHelper();
			// CTEDataT[] names = h.getCustomerByName(firstName, lastName);
		} catch (Exception e) {

		}

		return (ret);
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	private CTEDataT getCustomerByAccountNumber(String accountNumber) {
		CTEDataT ret = null;

		try {
			/*
			 * Call Service to get list of matching customer names
			 */
			CteHelper ch = new CteHelper();
			CTEQueryInputT[] queryInput = new CTEQueryInputT[1];
			queryInput[0] = new CTEQueryInputT();
			queryInput[0].setAccountNumber(accountNumber);
			CTEDataT[] c = ch.getCteData(queryInput);
			ret = c[0];
		} catch (Exception e) {

		}

		return (ret);
	}
}
