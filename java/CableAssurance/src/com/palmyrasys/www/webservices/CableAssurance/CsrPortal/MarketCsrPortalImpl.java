/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.CsrPortal;

import java.rmi.*;
import com.palmyrasys.www.webservices.CableAssurance.*;

/**
 * @author Prem
 * 
 */
public class MarketCsrPortalImpl implements CsrPortalEP {

	/**
	 * 
	 * 
	 */
	public MarketCsrPortalImpl() {

	}

	/**
	 * 
	 */
	public CTEDataT[] getCustomerByName(String firstName, String lastName)
			throws RemoteException {
		/*
		 * Connect to Customer DB and query a list of matching names (first,
		 * last)
		 */
		return null;
	}

	public CableModemT getCmByMac(String macAddress) throws RemoteException {
		/*
		 * Look in the local db for cm mac; then figure out the region, market,
		 * or blade to connect to and ask for the cm
		 */
		return null;
	}

	public EmtaT getMtaByMac(String macAddress) throws RemoteException {
		/*
		 * Look in the local db for mta mac; then figure out the region, market,
		 * or blade to connect to and ask for the mta
		 */
		return null;
	}

}
