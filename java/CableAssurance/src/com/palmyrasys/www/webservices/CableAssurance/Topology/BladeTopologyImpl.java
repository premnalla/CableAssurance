/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Topology;

import java.math.BigInteger;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.BladeT;
import com.palmyrasys.www.webservices.CableAssurance.CmsT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;


/**
 * @author Prem
 *
 */
public class BladeTopologyImpl extends AbstractTopologyImpl {

	private static Log log = LogFactory.getLog(BladeTopologyImpl.class);

	/**
	 * 
	 *
	 */
	public BladeTopologyImpl() {
		
	}
	
	/**
	 * 
	 */
	public BladeT[] getBlades() throws RemoteException {
		BladeT[] ret = null;	
		return ret;
	}

	/**
	 * 
	 */
    public BladeT getBlade(BigInteger regionId, BigInteger marketId, 
    		BigInteger bladeId) throws java.rmi.RemoteException {
    	BladeT ret = null;
    	return (ret);
    }

    /**
     * 
     */
    public BladeT getBladeByName(BigInteger regionId, BigInteger marketId, 
    		String bladeName) throws java.rmi.RemoteException {
    	BladeT ret = null;
    	return (ret);
    }

	/**
	 * 
	 */
	public CmsT getCms(TopoHierarchyKeyT topologyKey, BigInteger cmsResId)
			throws RemoteException {
		CmsT ret = null;
		return ret;
	}

	/**
	 * 
	 */
	public CmsT[] getCmses() throws RemoteException {
		CmsT[] ret = null;
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

}
