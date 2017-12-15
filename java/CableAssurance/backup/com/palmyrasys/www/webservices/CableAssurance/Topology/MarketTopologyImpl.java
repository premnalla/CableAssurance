/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Topology;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.palmyrasys.www.webservices.CableAssurance.BladeT;
import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.ChannelT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasys.www.webservices.CableAssurance.HfcT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.StatusColorT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

import com.palmyrasyscorp.common.dsandalgo.*;
import com.palmyrasyscorp.db.tables.*;
import com.palmyrasyscorp.www.jsp.helper.StatusColorHelper;

/**
 * @author Prem
 * 
 */
public class MarketTopologyImpl extends AbstractTopologyImpl implements
		TopologyEP {

	/**
	 * 
	 */
	public BladeT[] getBlades() throws RemoteException {
		return null;
	}

	/**
	 * 
	 */
	public CableModemT getCableModem(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) throws RemoteException {
		CableModemT ret = null;
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Channel from myself (which is Market)
			 */
			CableModem dbCm = new CableModem(cmResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new CableModemT(dbCm);
			ret.setTopologyKey(topologyKey);
			
		} else {
			/*
			 * Get Channel from Blade
			 */
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public ChannelT getChannel(TopoHierarchyKeyT topologyKey,
			BigInteger channelResId) throws RemoteException {
		ChannelT ret = null;
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Channel from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new ChannelT(dbChannel);
			ret.setTopologyKey(topologyKey);
			
		} else {
			/*
			 * Get Channel from Blade
			 */
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public ChannelT[] getChannels(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId) throws RemoteException {
		ChannelT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getChannels(): cmts resid=" + cmtsResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Channels from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbCmts.queryChannelChildren();
			List chnls = dbCmts.getListOfChannels();
			for (int i=0; i<chnls.size(); i++) {
				Channel dbChnl = (Channel) chnls.get(i);
				ChannelT svcChnl = new ChannelT(dbChnl);
				svcChnl.setTopologyKey(topologyKey);
				al.add(svcChnl);
			}
			chnls.clear();
			
		} else {
			/*
			 * Get Channels from Blade
			 */
		}

		if (al.size() > 0) {
			ChannelT[] tmp = new ChannelT[al.size()];
			ret = (ChannelT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmtsT getCmts(TopoHierarchyKeyT topologyKey, BigInteger cmtsResId)
			throws RemoteException {
		CmtsT ret = null;
		
		System.out.println("CMTS resid:" + cmtsResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			System.out.println("CMTS name: " + dbCmts.getName());
			ret = new CmtsT(dbCmts);
			ret.setStatusColor(StatusColorT.Green);
			ret.setTopologyKey(topologyKey);
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}
		
		return ret;
	}


	/**
	 * NOTE: Need to fixe this method !!!
	 */
	public CmtsT[] getCmtses() throws RemoteException {
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
		// System.out.println("\nCMTS's start: Server-side");
		byte[] b = { 0 };
		TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
				new BigInteger(b), new BigInteger(b));
		for (int i = 0; i < cmtses.size(); i++) {
			Cmts c = (Cmts) cmtses.get(i);
			// System.out.println(c.getName() + "\n");

			/*
			 * 
			 */
			CmtsT rC = new CmtsT(c);
			rC.setStatusColor(StatusColorHelper.AlertLevelToStatusColor(c));
			rC.setTopologyKey(tK);
			al.add(rC);
		}
		cmtses.clear();
		// System.out.println("CMTS's end\n");

		if (al.size() > 0) {
			CmtsT[] tmp = new CmtsT[al.size()];
			ret = (CmtsT[]) al.toArray(tmp);
		}

		// al.clear();

		return (ret);
	}

	/**
	 * 
	 */
	public EmtaT getEmta(TopoHierarchyKeyT topologyKey, BigInteger emtaResId)
			throws RemoteException {
		EmtaT ret = null;
		
		// System.out.println("eMta resid:" + emtaResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Channel from myself (which is Market)
			 */
			Emta dbEmta = new Emta(emtaResId.longValue());
			System.out.println("eMTA mac: " + dbEmta.getMacAddress());
			ret = new EmtaT(dbEmta);
			ret.setTopologyKey(topologyKey);
			
		} else {
			/*
			 * Get Channel from Blade
			 */
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public HfcT getHfc(TopoHierarchyKeyT topologyKey, BigInteger hfcResId)
			throws RemoteException {
		HfcT ret = null;
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Channel from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new HfcT(dbHfc);
			ret.setTopologyKey(topologyKey);
			
		} else {
			/*
			 * Get Channel from Blade
			 */
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public CableModemT[] getHfcCmes(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		CableModemT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getHfcCmes(): channel resid=" + hfcResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbHfc.queryCableModemChildren();
			List cmes = dbHfc.getListOfCableModems();
			for (int i=0; i<cmes.size(); i++) {
				CableModem dbCm = (CableModem) cmes.get(i);
				
				// System.out.println("CM mac:" + dbCm.getMacAddress());
				
				CableModemT svcCm = new CableModemT(dbCm);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			cmes.clear();
			
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}

		if (al.size() > 0) {
			CableModemT[] tmp = new CableModemT[al.size()];
			ret = (CableModemT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public EmtaT[] getHfcEmtas(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		EmtaT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getHfcEmtas(): channel resid=" + hfcResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbHfc.queryMtaChildren();
			List emtas = dbHfc.getListOfEmtas();
			for (int i=0; i<emtas.size(); i++) {
				Emta dbEmta = (Emta) emtas.get(i);
				
				System.out.println("Emta mac:" + dbEmta.getMacAddress());
				
				EmtaT svcCm = new EmtaT(dbEmta);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			emtas.clear();
			
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}

		if (al.size() > 0) {
			EmtaT[] tmp = new EmtaT[al.size()];
			ret = (EmtaT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public HfcT[] getHfcs(TopoHierarchyKeyT topologyKey, BigInteger cmtsResId)
			throws RemoteException {
		HfcT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getHfcs(): cmts resid=" + cmtsResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbCmts.queryHfcPlantChildren();
			List hfcs = dbCmts.getListOfHfcPlants();
			for (int i=0; i<hfcs.size(); i++) {
				HfcPlant dbHfc = (HfcPlant) hfcs.get(i);
				HfcT svcHfc = new HfcT(dbHfc);
				svcHfc.setTopologyKey(topologyKey);
				al.add(svcHfc);
			}
			hfcs.clear();
			
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}

		if (al.size() > 0) {
			HfcT[] tmp = new HfcT[al.size()];
			ret = (HfcT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MarketT[] getMarkets() throws RemoteException {
		return null;
	}

	/**
	 * 
	 */
	public RegionT[] getRegions() throws RemoteException {
		return null;
	}

	/**
	 * 
	 * @param topologyKey
	 * @param upChannelResId
	 * @return
	 * @throws RemoteException
	 */
	public CableModemT[] getChannelCmes(TopoHierarchyKeyT topologyKey,
			BigInteger channelResId) throws RemoteException {
		CableModemT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getChannelCmes(): channel resid=" + channelResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbChannel.queryUpstreamCableModemChildren();
			List cmes = dbChannel.getListOfUpstreamCableModems();
			for (int i=0; i<cmes.size(); i++) {
				CableModem dbCm = (CableModem) cmes.get(i);
				
				// System.out.println("CM mac:" + dbCm.getMacAddress());
				
				CableModemT svcCm = new CableModemT(dbCm);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			cmes.clear();
			
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}

		if (al.size() > 0) {
			CableModemT[] tmp = new CableModemT[al.size()];
			ret = (CableModemT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param upChannelResId
	 * @return
	 * @throws RemoteException
	 */
	public EmtaT[] getChannelEmtas(TopoHierarchyKeyT topologyKey,
			BigInteger channelResId) throws RemoteException {
		EmtaT[] ret = null;
		
		ArrayList al = new ArrayList();
		
		System.out.println("getChannelEmtas(): channel resid=" + channelResId);
		
		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get CMTS from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbChannel.queryUpstreamMtaChildren();
			List emtas = dbChannel.getListOfUpstreamEmtas();
			for (int i=0; i<emtas.size(); i++) {
				Emta dbEmta = (Emta) emtas.get(i);
				
				System.out.println("Emta mac:" + dbEmta.getMacAddress());
				
				EmtaT svcCm = new EmtaT(dbEmta);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			emtas.clear();
			
		} else {
			/*
			 * Get CMTS from Blade
			 */
		}

		if (al.size() > 0) {
			EmtaT[] tmp = new EmtaT[al.size()];
			ret = (EmtaT[]) al.toArray(tmp);
		}

		return (ret);
	}

}
