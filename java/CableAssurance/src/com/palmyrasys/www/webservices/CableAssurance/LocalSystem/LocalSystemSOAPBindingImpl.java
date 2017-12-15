/**
 * LocalSystemSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.LocalSystem;

//Prem:
import com.palmyrasyscorp.db.tables.LocalSystem;
import com.palmyrasyscorp.www.webservices.helper.LocalSystemHelper;
import com.palmyrasys.www.webservices.CableAssurance.LocalSystemT;
// import com.palmyrasys.www.webservices.CableAssurance.SystemTypeT;

public class LocalSystemSOAPBindingImpl implements com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemEP{

	public com.palmyrasys.www.webservices.CableAssurance.LocalSystemT getLocalSystem() throws java.rmi.RemoteException {
    	LocalSystemT ret = null;

    	LocalSystem dbLs = new LocalSystem();
    	ret = LocalSystemHelper.getLocalSystem(dbLs);

//    	switch (lc.getSystemType().intValue()) {
//    	case LocalSystem.LSTYPE_ENTERPRISE:
//        	ret = new LocalSystemT(SystemTypeT.EnterpriseServer, lc.getSystemName());
//    		break;
//    	case LocalSystem.LSTYPE_REGION:
//        	ret = new LocalSystemT(SystemTypeT.RegionServer, lc.getSystemName());
//    		break;
//    	case LocalSystem.LSTYPE_MARKET:
//        	ret = new LocalSystemT(SystemTypeT.MarketServer, lc.getSystemName());
//    		break;
//    	case LocalSystem.LSTYPE_BLADE:
//        	ret = new LocalSystemT(SystemTypeT.BladeServer, lc.getSystemName());
//    		break;
//    	default:
//    		break;
//    	}
    	    	
    	return (ret);
    }

}
