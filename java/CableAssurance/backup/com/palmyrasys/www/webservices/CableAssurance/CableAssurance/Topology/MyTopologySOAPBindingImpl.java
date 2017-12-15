/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Topology;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasyscorp.common.dsandalgo.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * OBSOLETED...OBSOLETED...OBSOLETED...OBSOLETED...OBSOLETED...
 * @author Prem
 * 
 */
public class MyTopologySOAPBindingImpl {

	public com.palmyrasys.www.webservices.CableAssurance.RegionT[] getRegions()
			throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.MarketT[] getMarkets()
			throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.BladeT[] getBlades()
			throws java.rmi.RemoteException {
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public com.palmyrasys.www.webservices.CableAssurance.CmtsT[] getCmtses()
			throws java.rmi.RemoteException {
		CmtsT[] ret = null;

		System.out.println("Servier-side: getCmtses()\n");

		LocalSystem ls = new LocalSystem();
		switch (ls.getSystemType().shortValue()) {
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = getMarketCmtses();
			break;
		case LocalSystem.LSTYPE_DOMAIN:
			break;
		default:
			System.out.println("Unknown System Type: " + ls.getSystemType());
			break;
		}

		return ret;
	}

	public com.palmyrasys.www.webservices.CableAssurance.CmtsT getCmts(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.ChannelT[] getChannels(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.ChannelT getChannel(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger chanelResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.HfcT[] getHfcs(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmtsResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.HfcT getHfc(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.CableModemT getCableModem(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger cmResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getUpChannelCmes(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger upChannelResId)
			throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.CableModemT[] getHfcCmes(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.EmtaT getEmta(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger emtaResId) throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getUpChannelEmtas(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger upChannelResId)
			throws java.rmi.RemoteException {
		return null;
	}

	public com.palmyrasys.www.webservices.CableAssurance.EmtaT[] getHfcEmtas(
			com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT topologyKey,
			java.math.BigInteger hfcResId) throws java.rmi.RemoteException {
		return null;
	}

	/**
	 * 
	 * @return
	 */
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
		 * Spawn a thread each for each of the blades in the market. Each thread
		 * will get the list of CMTS's from each blade and subsequently add it
		 * to 'a' AVL tree. Once getting is done or, timeout has occured, the
		 * AVL tree will be processed and result returned to the caller.
		 */

		Market mkt = new Market();
		List blades = mkt.getBlades();
		for (int i = 0; i < blades.size(); i++) {
			Blade bld = (Blade) blades.get(i);

			/*
			 * 
			 */
		}
		blades.clear();

		/*
		 * Get CMTS's from the market server
		 */
		List cmtses = mkt.getCmtses();
		System.out.println("\nCMTS's start: Server-side");
		byte[] b = {0};
		TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
				new BigInteger(b), new BigInteger(b));
		for (int i = 0; i < cmtses.size(); i++) {
			Cmts c = (Cmts) cmtses.get(i);
			System.out.println(c.getName() + "\n");

			/*
			 * 
			 */
			CmtsT rC = new CmtsT(c);
			rC.setStatusColor(StatusColorT.Green);
			rC.setTopologyKey(tK);
			al.add(rC);
		}
		cmtses.clear();
		System.out.println("CMTS's end\n");

		// test code...
		// CmtsT c = new CmtsT();
		// c.setCmtsName("Jaffna-Town");
		// al.add(c);

		if (al.size() > 0) {
			CmtsT[] tmp = new CmtsT[al.size()];
			ret = (CmtsT[]) al.toArray(tmp);
		}

		// al.clear();

		return (ret);
	}

}
