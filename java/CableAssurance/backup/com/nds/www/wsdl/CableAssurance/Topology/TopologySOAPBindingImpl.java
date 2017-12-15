/**
 * TopologySOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.Topology;

import java.util.*;
import com.nds.www.wsdl.CableAssurance.*;
// import com.networkdatapeople.db.web.*;
import com.palmyrasyscorp.common.dsandalgo.*;

public class TopologySOAPBindingImpl implements
		com.nds.www.wsdl.CableAssurance.Topology.TopologyEP {
	public com.nds.www.wsdl.CableAssurance.RegionT[] getRegions()
			throws java.rmi.RemoteException {
		return null;
	}

	public com.nds.www.wsdl.CableAssurance.MarketT[] getMarkets()
			throws java.rmi.RemoteException {
		MarketT[] ret = null;
		
		ArrayList ar = new ArrayList();
		MarketT mkt = new MarketT();
		mkt.setName("TestMarket");
		ar.add(mkt);
		ret = (MarketT[]) ar.toArray();
		return (ret);
	}

	public com.nds.www.wsdl.CableAssurance.BladeT[] getBlades()
			throws java.rmi.RemoteException {
		return null;
	}

	public com.nds.www.wsdl.CableAssurance.CmtsT[] getCmtses()
			throws java.rmi.RemoteException {
		CmtsT[] ret = null;

		// LocalSystem ls = new LocalSystem();
		// switch (ls.getSystemType().shortValue()) {
		// case LocalSystem.LSTYPE_ENTERPRISE:
		// 	break;
		// case LocalSystem.LSTYPE_REGION:
		// 	break;
		// case LocalSystem.LSTYPE_MARKET:
		// 	ret = getMarketCmtses();
		// 	break;
		// case LocalSystem.LSTYPE_DOMAIN:
		// 	break;
		// default:
		// 	break;
		// }

		ret = getMarketCmtses();
		
		return ret;
	}

	private CmtsT[] getMarketCmtses() {
		CmtsT[] ret = null;
		
		/*
		 * Array to store object that need to be returned
		 */
		ArrayList al = new ArrayList();
		
		/*
		 * Tree of sorted CMTS object
		 */
		AvlTree cmtsTree = new AvlTree();

		/*
		 * Spawn a thread each for each of the blades in the market.
		 * Each thread will get the list of CMTS's from each blade
		 * and subsequently add it to 'a' AVL tree. Once getting is done
		 * or, timeout has occured, the AVL tree will be processed and
		 * result returned to the caller.
		 */
		
		// Market mkt = new Market();
		// List blades = mkt.getBlades();
		// for (int i=0; i<blades.size(); i++) {
		// 	Blade bld = (Blade) blades.get(i);
		
			/*
			 * 
			 */
		// }
		// blades.clear();
		
		/*
		 * Get CMTS's from the market server
		 */
		// List cmtses = mkt.getCmtses();
		// for (int i=0; i<cmtses.size(); i++) {
		// 	Cmts c = (Cmts) cmtses.get(i);
		
			/*
			 * 
			 */
		// 	CmtsT rC = new CmtsT(c);
		// 	rC.setStatusColor(StatusColorT.Green);
		// 	al.add(rC);
		// }
		// cmtses.clear();
		
		CmtsT c = new CmtsT();
		c.setCmtsName("Jaffna-Town");
		al.add(c);
		ret = (CmtsT[]) al.toArray();
		
		// al.clear();
		
		return (ret);
	}
}
