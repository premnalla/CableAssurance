/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Topology;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CACustomerT;
import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.ChannelT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.CmsT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasys.www.webservices.CableAssurance.HfcT;
import com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceT;
import com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;

import com.palmyrasyscorp.db.tables.*;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;

/**
 * @author Prem
 * 
 */
public class MarketTopologyImpl extends AbstractTopologyImpl {

	private static Log log = LogFactory.getLog(MarketTopologyImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketTopologyImpl() {

	}

	/**
	 * 
	 */
	public ResourceT getResource(TopoHierarchyKeyT topologyKey,
			BigInteger resourceId) throws java.rmi.RemoteException {
		ResourceT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CM from myself (which is Market)
				 */
				ret = super.getResource(topologyKey, resourceId);
			} else {
				/*
				 * Get CM from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getResource(topologyKey, resourceId);
				bld = null;
				th = null;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return ret;
	}

	/**
	 * 
	 */
	public CableModemT getCableModem(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) throws RemoteException {
		CableModemT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CM from myself (which is Market)
				 */
				ret = super.getCableModem(topologyKey, cmResId);
			} else {
				/*
				 * Get CM from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCableModem(topologyKey, cmResId);
				bld = null;
				th = null;
			}
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
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Channel from myself (which is Market)
				 */
				ret = super.getChannel(topologyKey, channelResId);
			} else {
				/*
				 * Get Channel from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getChannel(topologyKey, channelResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Channels from myself (which is Market)
				 */
				ret = super.getChannels(topologyKey, cmtsResId);
			} else {
				/*
				 * Get Channels from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getChannels(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
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

			if (topologyKey.getBladeId().longValue() == 0) {
				/*
				 * Get CMS from myself (which is Market)
				 */
				ret = super.getCms(topologyKey, cmsResId);
			} else {
				/*
				 * Blades don't have CMS's
				 */
			}
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

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.getCmts(topologyKey, cmtsResId);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCmts(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
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

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.getCmtsByName(topologyKey, cmtsName);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCmtsByName(topologyKey, cmtsName);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmtsT[] getCmtses() throws RemoteException {
		CmtsT[] ret = null;

		try {
			/*
			 * Tree of sorted CMTS object
			 */
			TreeMap cmtsTree = new TreeMap();

			/*
			 * Spawn a thread each for each of the blades in the market. Each
			 * thread will get the list of CMTS's from each blade and
			 * subsequently add it to 'a' AVL tree. Once getting is done or,
			 * timeout has occured, the AVL tree will be processed and result
			 * returned to the caller.
			 */

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			Market mkt = new Market();
			List blades = mkt.getBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade bld = (Blade) blades.get(i);
				/*
				 * 
				 */
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				CmtsT[] l = th.getCmtses();
				if (l != null) {
					for (int j = 0; j < l.length; j++) {
						// al.add(l[j]);
						l[j].getTopologyKey().setBladeId(
								new BigInteger(bld.getId().toString()));
						cmtsTree.put(l[j], l[j]);
					}
				}
				l = null;
			}
			blades.clear();
			mkt = null;

			/*
			 * Get CMTS's from the market server
			 */
			CmtsT[] mktCmtsArr = super.getCmtses();
			if (mktCmtsArr != null) {
				for (int i = 0; i < mktCmtsArr.length; i++) {
					CmtsT c = mktCmtsArr[i];
					cmtsTree.put(c, c);
				}
			}

			/*
			 * Tree To Array
			 */
			Collection coln = cmtsTree.values();
			// Iterator iter = c.iterator();
			CmtsT[] tmp = new CmtsT[coln.size()];
			ret = (CmtsT[]) coln.toArray(tmp);

			coln = null;
			tmp = null;

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
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.getCmtsSnmpV2CAttributes(topologyKey, cmtsResId);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCmtsSnmpV2CAttributes(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public SnmpV2CAttributesT getCmSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.getCmSnmpV2CAttributes(topologyKey, cmtsResId);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCmSnmpV2CAttributes(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public SnmpV2CAttributesT getMtaSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.getMtaSnmpV2CAttributes(topologyKey, cmtsResId);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getMtaSnmpV2CAttributes(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public SnmpV2CAttributesT[] getCmtsAllSnmpV2CAttributes(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId) {
		SnmpV2CAttributesT[] ret = null;

		try {
			// System.out.println("eMta resid:" + emtaResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * 
				 */
				ret = super.getCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId);
			} else {
				/*
				 * 
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
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

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * 
				 */
				ret = super.getEmta(topologyKey, emtaResId);
			} else {
				/*
				 * 
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getEmta(topologyKey, emtaResId);
				bld = null;
				th = null;
			}
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

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * 
				 */
				ret = super.getEmtaSecondary(topologyKey, emtaResId);
			} else {
				/*
				 * 
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getEmtaSecondary(topologyKey, emtaResId);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}


	/**
	 * 
	 */
	public HfcT getHfc(TopoHierarchyKeyT topologyKey, BigInteger hfcResId)
			throws RemoteException {
		HfcT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get HFC from myself (which is Market)
				 */
				ret = super.getHfc(topologyKey, hfcResId);
			} else {
				/*
				 * Get HFC from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getHfc(topologyKey, hfcResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getHfcCmes(): channel resid=" + hfcResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CM's from myself (which is Market)
				 */
				ret = super.getHfcCmes(topologyKey, hfcResId);
			} else {
				/*
				 * Get CM's from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getHfcCmes(topologyKey, hfcResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getHfcEmtas(): channel resid=" + hfcResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get eMTA's from myself (which is Market)
				 */
				ret = super.getHfcEmtas(topologyKey, hfcResId);
			} else {
				/*
				 * Get eMTA's from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getHfcEmtas(topologyKey, hfcResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getHfcs(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get HFC's from myself (which is Market)
				 */
				ret = super.getHfcs(topologyKey, cmtsResId);
			} else {
				/*
				 * Get HFC's from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getHfcs(topologyKey, cmtsResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getChannelCmes(): channel resid=" +
			// channelResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CM's from myself (which is Market)
				 */
				ret = super.getChannelCmes(topologyKey, channelResId);
			} else {
				/*
				 * Get CM's from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getChannelCmes(topologyKey, channelResId);
				bld = null;
				th = null;
			}
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
			// System.out.println("getChannelEmtas(): channel resid=" +
			// channelResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get eMTA's from myself (which is Market)
				 */
				ret = super.getChannelEmtas(topologyKey, channelResId);
			} else {
				/*
				 * Get eMTA's from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getChannelEmtas(topologyKey, channelResId);
				bld = null;
				th = null;
			}
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
			AbstractTopoLookup al = null;

			if (cmMac != null) {
				al = new TopoCmLookup(cmMac);
			} else if (mtaMac != null) {
				al = new TopoMtaLookup(mtaMac);
			} else {
				return (ret);
			}

			if ((al.getResId()) == null) {
				return (ret);
			}

			Blade b = new Blade(al.getContainerId().intValue());
			TopologyHelper th = new TopologyHelper(b.getHost());
			ret = th.getDevicesForCsrPortal(cmMac, mtaMac);
			
//			CableModemT cm = null;
//			EmtaT mta = null;
//
//			byte[] b = { 0 };
//			TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
//					new BigInteger(b), new BigInteger(al.getContainerId()
//							.toString()));
//			BigInteger bResId = new BigInteger(resId.toString());
//
//			if (cmMac != null) {
//				cm = getCableModem(tK, bResId);
//				cm.setTopologyKey(tK);
//			} else {
//				mta = getEmta(tK, bResId);
//				mta.setTopologyKey(tK);
//				cm = getCableModem(tK, mta.getCmResId());
//				cm.setTopologyKey(tK);
//			}
//
//			ret = new MappedEuDevicesT(cm, mta);
			
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
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get CM from myself (which is Market)
				 */
				ret = super.getCustomerForResource(topologyKey, resourceId);
			} else {
				/*
				 * Get CM from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				TopologyHelper th = new TopologyHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = th.getCustomerForResource(topologyKey, resourceId);
				bld = null;
				th = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

}
