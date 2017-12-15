/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Cte;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CTEDataT;
import com.palmyrasys.www.webservices.CableAssurance.CTEQueryInputT;
import com.palmyrasyscorp.db.tables.LocalSystem;
import com.palmyrasyscorp.www.webservices.helper.CteHelper;

/**
 * @author Prem
 *
 */
public class BladeCteImpl extends AbstractCteImpl {

	private static Log log = LogFactory.getLog(BladeCteImpl.class);

	/**
	 * 
	 *
	 */
	public BladeCteImpl() {
		
	}
	
	/**
	 * 
	 */
	public CTEDataT[] getCteData(CTEQueryInputT[] queryInput)
			throws RemoteException {
		CTEDataT[] ret = null;
		
		try {
			/*
			 * get data from Market server
			 */
			LocalSystem ls = new LocalSystem();
			CteHelper ch = new CteHelper(ls.getParentHost());
			ret = ch.getCteData(queryInput);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return ret;
	}

}
