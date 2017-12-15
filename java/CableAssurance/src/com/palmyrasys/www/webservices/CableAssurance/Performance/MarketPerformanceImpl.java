/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Performance;

import java.math.BigInteger;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;
import com.palmyrasyscorp.db.tables.*;
import com.palmyrasyscorp.www.webservices.helper.PerformanceHelper;

/**
 * @author Prem
 * 
 */
public class MarketPerformanceImpl extends AbstractPerformanceImpl {

	private static Log log = LogFactory.getLog(MarketPerformanceImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketPerformanceImpl() {

	}

	/**
	 * 
	 */
	public GenericCountsHistoryT[] getChannelCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getChannelCountsHistory(topologyKey, channelResId,
						fromTime, toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getChannelCountsHistory(topologyKey, channelResId,
						fromTime, toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public CmPerformanceHistoryT[] getCmPerformanceHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		CmPerformanceHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCmPerformanceHistory(topologyKey, cmResId, fromTime,
						toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCmPerformanceHistory(topologyKey, cmResId, fromTime,
						toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public CmStatusHistoryT[] getCmStatusHistory(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT queryState) throws RemoteException {
		CmStatusHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCmStatusHistory(topologyKey, cmResId, fromTime,
						toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCmStatusHistory(topologyKey, cmResId, fromTime, toTime,
						batch, queryState);
				bld = null;
				ph = null;
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
	public GenericCountsHistoryT[] getCmtsCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCmtsCountsHistory(topologyKey, cmtsResId, fromTime,
						toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCmtsCountsHistory(topologyKey, cmtsResId, fromTime,
						toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public GenericCountsT getCurrentChannelCounts(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId)
			throws RemoteException {
		GenericCountsT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentChannelCounts(topologyKey, channelResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentChannelCounts(topologyKey, channelResId);
				bld = null;
				ph = null;
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
	public CmCurrentPerformanceT getCurrentCmPerformance(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId)
			throws RemoteException {
		CmCurrentPerformanceT ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentCmPerformance(topologyKey, cmResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentCmPerformance(topologyKey, cmResId);
				bld = null;
				ph = null;
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
	public CmStatusT getCurrentCmStatus(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) throws RemoteException {
		CmStatusT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentCmStatus(topologyKey, cmResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentCmStatus(topologyKey, cmResId);
				bld = null;
				ph = null;
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
	public GenericCountsT getCurrentCmtsCounts(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId) throws RemoteException {
		GenericCountsT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentCmtsCounts(topologyKey, cmtsResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentCmtsCounts(topologyKey, cmtsResId);
				bld = null;
				ph = null;
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
	public GenericCountsT getCurrentHfcCounts(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) throws RemoteException {
		GenericCountsT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentHfcCounts(topologyKey, hfcResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentHfcCounts(topologyKey, hfcResId);
				bld = null;
				ph = null;
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
	public MtaAvailabilityT getCurrentMtaAvailability(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		MtaAvailabilityT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentMtaAvailability(topologyKey, mtaResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentMtaAvailability(topologyKey, mtaResId);
				bld = null;
				ph = null;
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
	public MtaPingStatusT getCurrentMtaPingStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		MtaPingStatusT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentMtaPingStatus(topologyKey, mtaResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentMtaPingStatus(topologyKey, mtaResId);
				bld = null;
				ph = null;
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
	public MtaProvStatusT getCurrentMtaProvisionedStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId)
			throws RemoteException {
		MtaProvStatusT ret = null;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getCurrentMtaProvisionedStatus(topologyKey, mtaResId);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getCurrentMtaProvisionedStatus(topologyKey, mtaResId);
				bld = null;
				ph = null;
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
	public GenericCountsHistoryT[] getHfcCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger hfcResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		GenericCountsHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getHfcCountsHistory(topologyKey, hfcResId, fromTime,
						toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getHfcCountsHistory(topologyKey, hfcResId, fromTime,
						toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		MtaAvailabilityHistoryT[] ret = null;

		try {
			// System.out.println("getMtaAvailabilityHistory(): cmts resid=" +
			// mtaResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getMtaAvailabilityHistory(topologyKey, mtaResId,
						fromTime, toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getMtaAvailabilityHistory(topologyKey, mtaResId, fromTime,
						toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public MtaPingStatusHistoryT[] getMtaPingStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		MtaPingStatusHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getMtaPingStatusHistory(topologyKey, mtaResId,
						fromTime, toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getMtaPingStatusHistory(topologyKey, mtaResId, fromTime,
						toTime, batch, queryState);
				bld = null;
				ph = null;
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
	public MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) throws RemoteException {
		MtaProvStatusHistoryT[] ret = null;

		try {
			// System.out.println("getChannels(): cmts resid=" + cmtsResId);

			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Get Alarms from myself (which is Market)
				 */
				ret = super.getMtaProvisionedStatusHistory(topologyKey, mtaResId,
						fromTime, toTime, batch, queryState);
			} else {
				/*
				 * Get Alarms from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				PerformanceHelper ph = new PerformanceHelper(bld
						.getHostForWS(gSysCfg.getWebServicesPort()));
				ret = ph.getMtaProvisionedStatusHistory(topologyKey, mtaResId,
						fromTime, toTime, batch, queryState);
				bld = null;
				ph = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

}
