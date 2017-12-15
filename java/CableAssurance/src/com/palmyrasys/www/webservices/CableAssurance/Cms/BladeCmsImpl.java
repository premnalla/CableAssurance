/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Cms;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CMSLineT;
import com.palmyrasyscorp.db.tables.LocalSystem;
import com.palmyrasyscorp.www.webservices.helper.CmsHelper;

/**
 * @author Prem
 * 
 */
public class BladeCmsImpl extends AbstractCmsImpl {

	private static Log log = LogFactory.getLog(BladeCmsImpl.class);

	/**
	 * 
	 * 
	 */
	public BladeCmsImpl() {

	}

	/**
	 * 
	 */
	public CMSLineT[] getLineStatus(CMSLineT[] input) throws RemoteException {
		CMSLineT[] ret = null;

		try {
			/*
			 * get data from Market server
			 */
			LocalSystem ls = new LocalSystem();
			CmsHelper ch = new CmsHelper(ls.getParentHost());
			ret = ch.getLineStatus(input);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

}
