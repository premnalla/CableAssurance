/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Administration;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT;
import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT;
import com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.ConfigDownloadT;
import com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT;
import com.palmyrasys.www.webservices.CableAssurance.UserAccessT;
// import com.palmyrasys.www.webservices.CableAssurance.RoleOAPairT;
import com.palmyrasys.www.webservices.CableAssurance.RoleT;
import com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT;
// import com.palmyrasys.www.webservices.CableAssurance.UserOAPairT;
// import com.palmyrasys.www.webservices.CableAssurance.UserRoleT;
import com.palmyrasys.www.webservices.CableAssurance.UserT;
import com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT;
import com.palmyrasys.www.webservices.CableAssurance.Common.GlobalSystemConfig;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.OAAccess;
import com.palmyrasyscorp.db.tables.OAObject;
import com.palmyrasyscorp.db.tables.Role;
import com.palmyrasyscorp.db.tables.RoleObjectAccess;
// import com.palmyrasyscorp.db.tables.RoleOAPair;
import com.palmyrasyscorp.db.tables.User;
// import com.palmyrasyscorp.db.tables.UserOAPair;
// import com.palmyrasyscorp.db.tables.UserRole;
import com.palmyrasyscorp.www.webservices.helper.AdminHelper;

/**
 * @author Prem
 * 
 */
public class MarketAdminImpl extends AbstractAdminImpl {

	private static Log log = LogFactory.getLog(MarketAdminImpl.class);

	/**
	 * 
	 * 
	 */
	public MarketAdminImpl() {

	}

	/**
	 * 
	 */
	public short addCmts(CmtsT cmts) {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			int bldId;

			if ((bldId = cmts.getTopologyKey().getBladeId().intValue()) == 0) {
				/*
				 * Add CMTS to myself (which is Market)
				 */
				ret = super.addCmts(cmts);
			} else {
				/*
				 * Add CMTS to Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				AdminHelper ah = new AdminHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.addCmts(cmts);
				bld = null;
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
	public short addCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId, SnmpV2CAttributesT[] attributes) {
		short ret = 0;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Add SNMP v2c attributes to myself (which is Market)
				 */
				ret = super.addCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
						attributes);
			} else {
				/*
				 * Add SNMP v2c attributes to Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				AdminHelper ah = new AdminHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.addCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
						attributes);
				bld = null;
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
	public short deleteCmts(CmtsT cmts) {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			int bldId;

			if ((bldId = cmts.getTopologyKey().getBladeId().intValue()) == 0) {
				/*
				 * Add CMTS to myself (which is Market)
				 */
				super.deleteCmts(cmts);
			} else {
				/*
				 * Add CMTS to Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade bld = new Blade(bldId);
				AdminHelper ah = new AdminHelper(bld.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.deleteCmts(cmts);
				bld = null;
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
	public short addUser(UserT user) throws RemoteException {
		short ret = 0;

		try {
			ret = super.addUser(user);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.addUser(user);
			}
			blades.clear();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId, SnmpV2CAttributesT[] attributes) {
		short ret = 0;

		try {
			int bldId;

			if ((bldId = topologyKey.getBladeId().intValue()) == 0) {
				/*
				 * Add SNMP v2c attributes to myself (which is Market)
				 */
				ret = super.updateCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
						attributes);
			} else {
				/*
				 * Add SNMP v2c attributes to Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade b = new Blade(bldId);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ret = ah.updateCmtsAllSnmpV2CAttributes(topologyKey, cmtsResId,
						attributes);
				b = null;
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
	public short updateCmtsAlarmConfig(CmtsAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			ret = super.updateCmtsAlarmConfig(alm);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateCmtsAlarmConfig(alm);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmsAlarmConfig(CmsAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			ret = super.updateCmsAlarmConfig(alm);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateCmsAlarmConfig(alm);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateHfcAlarmConfig(HfcAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			ret = super.updateHfcAlarmConfig(alm);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateHfcAlarmConfig(alm);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateMtaAlarmConfig(MtaAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			ret = super.updateMtaAlarmConfig(alm);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateMtaAlarmConfig(alm);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updatePollingIntervals(PollingIntervalsT pollintInterval)
			throws RemoteException {

		short ret = super.updatePollingIntervals(pollintInterval);

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updatePollingIntervals(pollintInterval);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}


		return (ret);
	}

	/**
	 * 
	 */
	public short updateUser(UserT user) throws RemoteException {
		short ret = 0;

		ret = super.updateUser(user);

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateUser(user);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateUserPassword(String loginName, String newPassword)
			throws RemoteException {
		short ret = 0;

		ret = super.updateUserPassword(loginName, newPassword);

		try {
			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateUserPassword(loginName, newPassword);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmts(CmtsT cmts) throws RemoteException {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			int bldId;
			if ((bldId = cmts.getTopologyKey().getBladeId().intValue()) == 0) {
				/*
				 * Get CMTS from myself (which is Market)
				 */
				ret = super.updateCmts(cmts);
			} else {
				/*
				 * Get CMTS from Blade
				 */
				GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

				Blade b = new Blade(bldId);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateCmts(cmts);
				b = null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	public short updateRegion(RegionT rgn) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short addRegion(RegionT rgn) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short updateMarket(MarketT mkt) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short addMarket(MarketT mkt) throws RemoteException {
		short ret = -1;

		return ret;
	}

	/**
	 * 
	 */
	public short updateCmPerfConfig(CmPerformanceConfigT cmPerf)
			throws java.rmi.RemoteException {
		short ret = 0;

		try {
			ret = super.updateCmPerfConfig(cmPerf);

			GlobalSystemConfig gSysCfg = GlobalSystemConfig.getInstance();

			List blades = Blade.QueryBlades();
			for (int i = 0; i < blades.size(); i++) {
				Blade b = (Blade) blades.get(i);
				AdminHelper ah = new AdminHelper(b.getHostForWS(gSysCfg
						.getWebServicesPort()));
				ah.updateCmPerfConfig(cmPerf);
			}
			blades.clear();
			blades = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * Needs to be overloaded here ...
	 */
	public short downloadConfigFromParent() throws java.rmi.RemoteException {
		short ret = -1;

		return (ret);
	}

	/**
	 * 
	 */
	public ConfigDownloadT getConfig() throws java.rmi.RemoteException {
		ConfigDownloadT ret = null;

		try {
			ret = new ConfigDownloadT();
			ret.setCmPerfCfg(getCmPerfConfig());
			ret.setCmsAlarmCfg(getCmsAlarmConfig());
			ret.setCmtsAlarmCfg(getCmtsAlarmConfig());
			ret.setHfcAlarmCfg(getHfcAlarmConfig());
			ret.setMtaAlarmCfg(getMtaAlarmConfig());
			ret.setPollingIntervals(getPollingIntervals());

			List o = OAObject.GetList();
			ApplicationDomainT[] appDomains = new ApplicationDomainT[o.size()];
			for (int i = 0; i < o.size(); i++) {
				appDomains[i] = new ApplicationDomainT(ApplicationDomainTypeT
						.fromString(((OAObject) o.get(i)).getAppDomain()), null);
			}
			ret.setApplicationDomains(appDomains);

			o = OAAccess.GetList();
			UserAccessT[] userAccessRights = new UserAccessT[o.size()];
			for (int i = 0; i < o.size(); i++) {
				userAccessRights[i] = new UserAccessT(UserAccessTypeT
						.fromString(((OAAccess) o.get(i)).getAccess()));
			}
			ret.setUserAccessRights(userAccessRights);

			HashMap map = RoleObjectAccess.Get(null);
			RoleT[] roles = new RoleT[map.size()];
			Collection collection = map.values();
			Iterator iter = collection.iterator();
			for (int i = 0; i<map.size() && iter.hasNext(); i++) {
				// roles[i] = new RoleT(((Role) o.get(i)).getRoleName());
				RoleT r = new RoleT((Role)iter.next());
				roles[i] = r;
			}
			ret.setRoles(roles);

			o = User.GetUsers();
			UserT[] users = new UserT[o.size()];
			for (int i = 0; i < o.size(); i++) {
				users[i] = new UserT((User) o.get(i));
			}
			ret.setUsers(users);

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}
}
