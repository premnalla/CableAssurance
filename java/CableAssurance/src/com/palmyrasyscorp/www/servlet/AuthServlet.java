/**
 * 
 */
package com.palmyrasyscorp.www.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.palmyrasyscorp.www.auth.*;
import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.tables.User;
import com.palmyrasyscorp.www.resourcebundle.ResourceBundleSingleton;
import com.palmyrasyscorp.www.resourcebundle.AbstractResourceBundle;

/**
 * @author Prem
 * 
 */
public class AuthServlet extends HttpServlet {

	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/*
		 * Init Singletons
		 */
		// CADbConnectionPool.getInstance();
		
		RequestDispatcher rd;
		String nextPage = "/app/initial_data_page.jsp";

		Object o = req.getAttribute(ServletConstants.USER);
		if (o == null) {
			String userName = req.getParameter(CAUser.USER_NAME);
			String password = req.getParameter(CAUser.PASSWORD);
			if (userName == null || password == null || userName.length() == 0
					|| password.length() == 0) {
				nextPage = "/caservlet/HomeServlet";
			} else {
				// CAUser user = new CAUser(userName, password);
				User u = User.GetUserByUserPw(userName, password);
				if (u != null) {
					HttpSession sessn = req.getSession();
					AbstractResourceBundle bundle = 
						ResourceBundleSingleton.getInstance().getBundle(Locale.US);
					/*
					 * Don't need to synchronize resouce bundle in Session because
					 * it rarely changes and therefore sync'ing will slow page loading
					 * by pages that load a lot of data, or get data from multiple sources
					 */
					sessn.setAttribute(ServletConstants.RESOURCE_BUNDLE, bundle);
					
					if (bundle != null) {
						System.out.println("bundle not null");
					} else {
						System.out.println("bundle is null");						
					}
					
					synchronized (sessn) {
						sessn.setAttribute(ServletConstants.USER, u);
						sessn.setMaxInactiveInterval(60*60);
					}
				}
			}
			// System.out.println("Added user");
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
		 * Object o = req.getAttribute(ServletConstants.USER); if (o == null) {
		 * String userName = req.getParameter(CAUser.USER_NAME); String password =
		 * req.getParameter(CAUser.PASSWORD); CAUser user = new CAUser(userName,
		 * password); req.getSession().setAttribute(ServletConstants.USER,
		 * user); System.out.println("Added user"); }
		 * 
		 * nextPage = "/app/initial_data_page.jsp"; rd =
		 * getServletContext().getRequestDispatcher(nextPage); rd.forward(req,
		 * resp);
		 */
	}

}
