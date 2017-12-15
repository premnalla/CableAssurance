/**
 * 
 */
package com.palmyrasyscorp.www.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.palmyrasyscorp.www.auth.*;
import com.palmyrasyscorp.www.resourcebundle.ResourceBundleSingleton;

/**
 * @author Prem
 * 
 */
public class HomeServlet extends HttpServlet {

	public void init() throws ServletException {
		// System.out.println("\n\nHomeServlet::init(): Created Servlet\n\n");
		super.init();
		
		ResourceBundleSingleton.getInstance();		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		RequestDispatcher rd;
		String nextPage;

		HttpSession sessn = req.getSession();
		synchronized (sessn) {
			Object o = sessn.getAttribute(ServletConstants.USER);
			if (o == null) {
				// System.out.println("Didn't find user");
				nextPage = "/login_frm.jsp";
				// String userName = req.getParameter(CAUser.USER_NAME);
				// String password = req.getParameter(CAUser.PASSWORD);
				// CAUser user = new CAUser(userName, password);
				// req.setAttribute(ServletConstants.USER, user);
			} else {
				String op = req.getParameter(ServletConstants.OP);
				if (op != null && op.equalsIgnoreCase(ServletConstants.LOGOUT)) {
					sessn.removeAttribute(ServletConstants.USER);
					o = null;
					nextPage = "/login_frm.jsp";
				} else {
					nextPage = "/app/initial_data_page.jsp";
				}
			}
		}
		rd = getServletContext().getRequestDispatcher(nextPage);
		rd.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doGet(req, resp);

		/**
		 * RequestDispatcher rd; String nextPage;
		 * 
		 * Object o = req.getSession().getAttribute(ServletConstants.USER); if
		 * (o == null) { System.out.println("Didn't find user"); nextPage =
		 * "/login.jsp"; // String userName =
		 * req.getParameter(CAUser.USER_NAME); // String password =
		 * req.getParameter(CAUser.PASSWORD); // CAUser user = new
		 * CAUser(userName, password); //
		 * req.setAttribute(ServletConstants.USER, user); } else { nextPage =
		 * "/app/initial_data_page.jsp"; } rd =
		 * getServletContext().getRequestDispatcher(nextPage); rd.forward(req,
		 * resp);
		 */
	}

}
