/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Cte;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CTEAbstractMacT;
import com.palmyrasys.www.webservices.CableAssurance.CTEAbstractNameT;
import com.palmyrasys.www.webservices.CableAssurance.CTECustomerT;
import com.palmyrasys.www.webservices.CableAssurance.CTEDataT;
import com.palmyrasys.www.webservices.CableAssurance.CTEQueryInputT;

/**
 * @author Prem
 * 
 */
public class MarketCteImpl extends AbstractCteImpl {

	private static Log log = LogFactory.getLog(MarketCteImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketCteImpl() {

	}

	/**
	 * 
	 */
	public CTEDataT[] getCteData(CTEQueryInputT[] queryInput)
			throws RemoteException {
		CTEDataT[] ret = null;

		try {
			/*
			 * TODO
			 */
			ret = new CTEDataT[1];
			CTECustomerT cust = new CTECustomerT();
			cust.setAccountNumber("88381604");
			cust.setAccountActive(new Short((short) 1));
			cust.setFirstName("John");
			cust.setLastName("Doe");
			cust.setStreet1("100 Technology Drive");
			cust.setCity("Marlborough");
			cust.setState("MA");
			cust.setZip("01752");
			cust.setPhone1("7743484000");

			CTEAbstractMacT cm = new CTEAbstractMacT("000102030405",
					"000102030405.myisp.com", "192.168.1.100");
			CTEAbstractMacT mta = new CTEAbstractMacT("000102030406",
					"000102030406.myisp.com", "192.168.1.100");

			CTEAbstractNameT cmts = new CTEAbstractNameT("Unknown", null, "192.168.1.100");
			CTEAbstractNameT cms = new CTEAbstractNameT("Northern", null, "192.168.1.100");
			String hfcName = "Unknown";
			
			ret[0] = new CTEDataT(cust, cm, mta, cmts, cms, hfcName);
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

}
