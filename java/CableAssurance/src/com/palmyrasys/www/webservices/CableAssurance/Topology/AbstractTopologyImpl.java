/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Topology;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.BladeT;
import com.palmyrasys.www.webservices.CableAssurance.CACustomerT;
import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.ChannelT;
import com.palmyrasys.www.webservices.CableAssurance.CmsT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasys.www.webservices.CableAssurance.HfcT;
import com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceT;
import com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCommonServiceImpl;
// import
// com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.CACustomer;
import com.palmyrasyscorp.db.tables.CableModem;
import com.palmyrasyscorp.db.tables.Channel;
import com.palmyrasyscorp.db.tables.Cms;
import com.palmyrasyscorp.db.tables.Cmts;
import com.palmyrasyscorp.db.tables.Emta;
import com.palmyrasyscorp.db.tables.HfcPlant;
import com.palmyrasyscorp.db.tables.Market;
import com.palmyrasyscorp.db.tables.Resource;
import com.palmyrasyscorp.db.tables.SnmpV2CAttributes;

/**
 * @author Prem
 * 
 */
public abstract class AbstractTopologyImpl extends AbstractCommonServiceImpl
		implements TopologyEP {

	private static Log log = LogFactory.getLog(AbstractTopologyImpl.class);

	/**
	 * 
	 */
	public ResourceT getResource(TopoHierarchyKeyT topologyKey,
			BigInteger resourceId) throws java.rmi.RemoteException {
		ResourceT ret = null;

		try {
			// System.out.println("ResouceId="+ resourceId);
			
			Resource dbRes = new Resource(resourceId.longValue());
			ret = new ResourceT(dbRes);
			ret.setTopologyKey(topologyKey);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public BladeT[] getBlades() throws RemoteException {
		BladeT[] ret = null;

		try {
			Market mkt = new Market();
			List l = mkt.getBlades();

			if (l.size() > 0) {
				ret = new BladeT[l.size()];
				for (int i = 0; i < l.size(); i++) {
					ret[i] = (new BladeT((Blade) l.get(i)));
				}
			}

			mkt = null;
			l.clear();
			l = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public BladeT getBlade(BigInteger regionId, BigInteger marketId,
			BigInteger bladeId) throws java.rmi.RemoteException {
		BladeT ret = null;

		try {
			Blade dbB = new Blade(bladeId.intValue());
			ret = new BladeT(dbB);

			dbB = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public BladeT getBladeByName(BigInteger regionId, BigInteger marketId,
			String bladeName) throws java.rmi.RemoteException {
		BladeT ret = null;

		try {
			Blade dbB = Blade.GetBladeByName(bladeName);

			if (dbB != null) {
				ret = new BladeT(dbB);
			}

			dbB = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CableModemT getCableModem(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) throws RemoteException {
		CableModemT ret = null;

		try {
			/*
			 * Get CM from myself (which is Market)
			 */
			CableModem dbCm = new CableModem(cmResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new CableModemT(dbCm);
			ret.setTopologyKey(topologyKey);

			dbCm = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public ChannelT getChannel(TopoHierarchyKeyT topologyKey,
			BigInteger channelResId) throws RemoteException {
		ChannelT ret = null;

		try {
			/*
			 * Get Channel from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new ChannelT(dbChannel);
			ret.setTopologyKey(topologyKey);

			dbChannel = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public ChannelT[] getChannels(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId) throws RemoteException {
		ChannelT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			/*
			 * Get Channels from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbCmts.queryChannelChildren();
			List chnls = dbCmts.getListOfChannels();
			for (int i = 0; i < chnls.size(); i++) {
				Channel dbChnl = (Channel) chnls.get(i);
				ChannelT svcChnl = new ChannelT(dbChnl);
				svcChnl.setTopologyKey(topologyKey);
				al.add(svcChnl);
			}
			chnls.clear();

			if (al.size() > 0) {
				ChannelT[] tmp = new ChannelT[al.size()];
				ret = (ChannelT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmsT getCms(TopoHierarchyKeyT topologyKey, BigInteger cmsResId)
			throws RemoteException {
		CmsT ret = null;

		try {
			// System.out.println("CMS resid:" + cmsResId);

			/*
			 * Get CMS from myself (which is Market)
			 */
			Cms dbCms = new Cms(cmsResId.longValue());
			ret = new CmsT(dbCms);
			ret.setTopologyKey(topologyKey);
			// ret.setStatusColor(StatusColorT.Green);
			// System.out.println("CMS name: " + dbCms.getCmsName());

			dbCms = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmtsT getCmts(TopoHierarchyKeyT topologyKey, BigInteger cmtsResId)
			throws RemoteException {
		CmtsT ret = null;

		try {
			// System.out.println("CMTS resid:" + cmtsResId);

			/*
			 * Get CMTS from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new CmtsT(dbCmts);
			// ret.setStatusColor(StatusColorT.Green);
			ret.setTopologyKey(topologyKey);

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmtsT getCmtsByName(TopoHierarchyKeyT topologyKey, String cmtsName)
			throws java.rmi.RemoteException {
		CmtsT ret = null;

		try {
			// System.out.println("CMTS name:" + cmtsName);

			/*
			 * Get CMTS from myself (which is Market)
			 */
			Cmts dbCmts = Cmts.GetCmtsByName(cmtsName);
			if (dbCmts != null) {
				// System.out.println("CMTS resid: " + dbCmts.getCmtsResId());
				ret = new CmtsT(dbCmts);
				// ret.setStatusColor(StatusColorT.Green);
				ret.setTopologyKey(topologyKey);
			}

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmsT[] getCmses() throws RemoteException {
		CmsT[] ret = null;

		try {
			/*
			 * Array to store object that need to be returned
			 */
			ArrayList al = new ArrayList();

			/*
			 * Get CMS's from the market server
			 */
			Market mkt = new Market();
			List cmses = mkt.getCmses();
			byte[] b = { 0 };
			TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
					new BigInteger(b), new BigInteger(b));

			// System.out.println("Num CMS's =" + cmses.size());

			for (int i = 0; i < cmses.size(); i++) {
				Cms c = (Cms) cmses.get(i);

				/*
				 * 
				 */
				CmsT rC = new CmsT(c);
				rC.setTopologyKey(tK);
				al.add(rC);
			}
			cmses.clear();

			if (al.size() > 0) {
				CmsT[] tmp = new CmsT[al.size()];
				ret = (CmsT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			mkt = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CmtsT[] getCmtses() throws RemoteException {
		CmtsT[] ret = null;

		try {
			/*
			 * Array to store object that need to be returned
			 */
			ArrayList al = new ArrayList();

			/*
			 * Spawn a thread each for each of the blades in the market. Each
			 * thread will get the list of CMTS's from each blade and
			 * subsequently add it to 'a' AVL tree. Once getting is done or,
			 * timeout has occured, the AVL tree will be processed and result
			 * returned to the caller.
			 */

			Blade bld = new Blade();

			/*
			 * Get CMTS's from the local server
			 */
			List cmtses = bld.getCmtses();
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
				// rC.setStatusColor(StatusColorHelper.AlertLevelToStatusColor(c));
				rC.setTopologyKey(tK);
				al.add(rC);
			}
			cmtses.clear();
			// System.out.println("CMTS's end\n");

			if (al.size() > 0) {
				CmtsT[] tmp = new CmtsT[al.size()];
				ret = (CmtsT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			bld = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public SnmpV2CAttributesT getCmtsSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			// System.out.println("eMta resid:" + emtaResId);

			if (topologyKey.getBladeId().longValue() == 0) {
				/*
				 * TODO
				 */
			} else {
				/*
				 * Get Channel from Blade
				 */
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	public SnmpV2CAttributesT getCmSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			// System.out.println("eMta resid:" + emtaResId);

			if (topologyKey.getBladeId().longValue() == 0) {
				/*
				 * TODO
				 */

			} else {
				/*
				 * Get Channel from Blade
				 */
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	public SnmpV2CAttributesT getMtaSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			// System.out.println("eMta resid:" + emtaResId);

			if (topologyKey.getBladeId().longValue() == 0) {
				/*
				 * TODO
				 */

			} else {
				/*
				 * Get Channel from Blade
				 */
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	public SnmpV2CAttributesT[] getCmtsAllSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT[] ret = null;

		try {
			/*
			 * 
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			SnmpV2CAttributes[] dbV2CAttrsdb = dbCmts.getSnmpV2CAttributes();
			if (dbV2CAttrsdb != null) {
				ret = new SnmpV2CAttributesT[3];
				int i = 0;
				ret[i] = new SnmpV2CAttributesT(dbV2CAttrsdb[i++]);
				ret[i] = new SnmpV2CAttributesT(dbV2CAttrsdb[i++]);
				ret[i] = new SnmpV2CAttributesT(dbV2CAttrsdb[i++]);
			}

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public EmtaT getEmta(TopoHierarchyKeyT topologyKey, BigInteger emtaResId)
			throws RemoteException {
		EmtaT ret = null;

		try {
			// System.out.println("eMta resid:" + emtaResId);

			/*
			 * 
			 */
			Emta dbEmta = new Emta(emtaResId.longValue());
			// System.out.println("eMTA mac: " + dbEmta.getMacAddress());
			ret = new EmtaT(dbEmta);
			ret.setTopologyKey(topologyKey);

			dbEmta = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public EmtaSecondaryT getEmtaSecondary(TopoHierarchyKeyT topologyKey,
			BigInteger emtaResId) throws RemoteException {
		EmtaSecondaryT ret = null;

		return (ret);
	}

	/**
	 * 
	 */
	public HfcT getHfc(TopoHierarchyKeyT topologyKey, BigInteger hfcResId)
			throws RemoteException {
		HfcT ret = null;

		try {
			/*
			 * Get HFC from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			ret = new HfcT(dbHfc);
			ret.setTopologyKey(topologyKey);

			dbHfc = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CableModemT[] getHfcCmes(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		CableModemT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getHfcCmes(): channel resid=" + hfcResId);

			/*
			 * Get CM's from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbHfc.queryCableModemChildren();
			List cmes = dbHfc.getListOfCableModems();
			for (int i = 0; i < cmes.size(); i++) {
				CableModem dbCm = (CableModem) cmes.get(i);

				// System.out.println("CM mac:" + dbCm.getMacAddress());

				CableModemT svcCm = new CableModemT(dbCm);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			cmes.clear();

			if (al.size() > 0) {
				CableModemT[] tmp = new CableModemT[al.size()];
				ret = (CableModemT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbHfc = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public EmtaT[] getHfcEmtas(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		EmtaT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getHfcEmtas(): channel resid=" + hfcResId);

			/*
			 * Get eMTA's from myself (which is Market)
			 */
			HfcPlant dbHfc = new HfcPlant(hfcResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbHfc.queryMtaChildren();
			List emtas = dbHfc.getListOfEmtas();
			for (int i = 0; i < emtas.size(); i++) {
				Emta dbEmta = (Emta) emtas.get(i);

				// System.out.println("Emta mac:" + dbEmta.getMacAddress());

				EmtaT svcCm = new EmtaT(dbEmta);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			emtas.clear();

			if (al.size() > 0) {
				EmtaT[] tmp = new EmtaT[al.size()];
				ret = (EmtaT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbHfc = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public HfcT[] getHfcs(TopoHierarchyKeyT topologyKey, BigInteger cmtsResId)
			throws RemoteException {
		HfcT[] ret = null;

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getHfcs(): cmts resid=" + cmtsResId);

			/*
			 * Get HFC's from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmtsResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbCmts.queryHfcPlantChildren();
			List hfcs = dbCmts.getListOfHfcPlants();
			for (int i = 0; i < hfcs.size(); i++) {
				HfcPlant dbHfc = (HfcPlant) hfcs.get(i);
				HfcT svcHfc = new HfcT(dbHfc);
				svcHfc.setTopologyKey(topologyKey);
				al.add(svcHfc);
			}
			hfcs.clear();

			if (al.size() > 0) {
				HfcT[] tmp = new HfcT[al.size()];
				ret = (HfcT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
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

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannelCmes(): channel resid=" +
			// channelResId);

			/*
			 * Get CM's from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbChannel.queryUpstreamCableModemChildren();
			List cmes = dbChannel.getListOfUpstreamCableModems();
			for (int i = 0; i < cmes.size(); i++) {
				CableModem dbCm = (CableModem) cmes.get(i);

				// System.out.println("CM mac:" + dbCm.getMacAddress());

				CableModemT svcCm = new CableModemT(dbCm);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			cmes.clear();

			if (al.size() > 0) {
				CableModemT[] tmp = new CableModemT[al.size()];
				ret = (CableModemT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbChannel = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
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

		try {
			ArrayList al = new ArrayList();

			// System.out.println("getChannelEmtas(): channel resid=" +
			// channelResId);

			/*
			 * Get eMTA's from myself (which is Market)
			 */
			Channel dbChannel = new Channel(channelResId.longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			dbChannel.queryUpstreamMtaChildren();
			List emtas = dbChannel.getListOfUpstreamEmtas();
			for (int i = 0; i < emtas.size(); i++) {
				Emta dbEmta = (Emta) emtas.get(i);

				// System.out.println("Emta mac:" + dbEmta.getMacAddress());

				EmtaT svcCm = new EmtaT(dbEmta);
				svcCm.setTopologyKey(topologyKey);
				al.add(svcCm);
			}
			emtas.clear();

			if (al.size() > 0) {
				EmtaT[] tmp = new EmtaT[al.size()];
				ret = (EmtaT[]) al.toArray(tmp);
			}

			al.clear();
			al = null;
			dbChannel = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MappedEuDevicesT getDevicesForCsrPortal(String cmMac, String mtaMac)
			throws java.rmi.RemoteException {
		MappedEuDevicesT ret = null;

		try {
			CableModemT cm = null;
			EmtaT mta = null;

			byte[] b = { 0 };
			TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
					new BigInteger(b), new BigInteger(b));

			if (cmMac != null) {
				CableModem dbCm = new CableModem(cmMac);
				cm = new CableModemT(dbCm);
				cm.setTopologyKey(tK);
			} else if (mtaMac != null) {
				Emta dbEmta = new Emta(mtaMac);
				mta = new EmtaT(dbEmta);
				mta.setTopologyKey(tK);
				CableModem dbCm = new CableModem(dbEmta.getCmResId()
						.longValue());
				cm = new CableModemT(dbCm);
				cm.setTopologyKey(tK);
			} else {
				return (ret);
			}

			ret = new MappedEuDevicesT(cm, mta);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CACustomerT getCustomerForResource(TopoHierarchyKeyT topologyKey,
			BigInteger resourceId) throws java.rmi.RemoteException {
		CACustomerT ret = null;

		try {
			CACustomer cust = CACustomer.getCustomerForResource(resourceId);
			ret = new CACustomerT(cust);
			if (!ret.isValid()) {
				ret = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
		return (ret);
	}

}
