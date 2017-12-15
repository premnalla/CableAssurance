/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Cms;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CMSLineT;

/**
 * @author Prem
 * 
 */
public class MarketCmsImpl extends AbstractCmsImpl {

	private static Log log = LogFactory.getLog(MarketCmsImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketCmsImpl() {

	}

	/**
	 * 
	 */
	public CMSLineT[] getLineStatus(CMSLineT[] input) throws RemoteException {
		CMSLineT[] ret = null;

		try {
			/*
			 * TODO
			 */
			ret = new CMSLineT[1];
			ret[0] = new CMSLineT(input[0].getNumber(),
					new String("In-Service"));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		return ret;
	}

}
