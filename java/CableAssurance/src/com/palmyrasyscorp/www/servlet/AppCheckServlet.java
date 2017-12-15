/**
 * !!! OBSOLETED !!!
 */
package com.palmyrasyscorp.www.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Prem
 * 
 */
public class AppCheckServlet extends HttpServlet {

	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nextPage = "/servlet/HomeServlet";

		Object o = req.getSession().getAttribute(ServletConstants.USER);
		if (o != null) {
			System.out.println("AppCheckServlet: user found");
			//super.doGet(req, resp);
			return;
		} else {
			System.out.println("AppCheckServlet: no found user");
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(nextPage);
		rd.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nextPage = "/servlet/HomeServlet";

		Object o = req.getSession().getAttribute(ServletConstants.USER);
		if (o != null) {
			System.out.println("AppCheckServlet: user found");
			//super.doPost(req, resp);
			return;
		} else {
			System.out.println("AppCheckServlet: no found user");
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(nextPage);
		rd.forward(req, resp);
	}

}
