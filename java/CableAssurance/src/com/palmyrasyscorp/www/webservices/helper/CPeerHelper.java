/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CmDataT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsCmDataT;
import com.palmyrasys.www.webservices.CableAssurance.EventMessageT;
import com.palmyrasys.www.webservices.CableAssurance.MtaDataT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.CPeerService.*;

/**
 * @author Prem
 * 
 */
public class CPeerHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(CPeerHelper.class);

	/**
	 * 
	 */
	private CPeerServiceSOAPBindingStub m_binding = null;

	/**
	 * 
	 * 
	 */
	public CPeerHelper() {

		try {
			m_binding = (CPeerServiceSOAPBindingStub) new CPeerServiceServiceLocator()
					.getCPeerServiceEP();
			// System.out.println("Binding is not null");
		} catch (javax.xml.rpc.ServiceException jre) {
			// jrlog.error(null, e);
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("m_binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);

	}

	/**
	 * 
	 * 
	 */
	public CPeerHelper(String host) {

		try {
			CPeerServiceServiceLocator loc = new CPeerServiceServiceLocator();
			loc.setHost(host);
			m_binding = (CPeerServiceSOAPBindingStub) loc.getCPeerServiceEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);

	}

	/**
	 * 
	 * @param topologyKey
	 * @param mtaResId
	 * @return
	 * @throws RemoteException
	 */
	public String pingMta(TopoHierarchyKeyT topologyKey, BigInteger mtaResId) {
		String ret = null;

		try {
			ret = m_binding.pingMta(topologyKey, mtaResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param mtaResId
	 * @return
	 * @throws RemoteException
	 */
	public MtaDataT getMtaData(TopoHierarchyKeyT topologyKey,
			BigInteger mtaResId) {
		MtaDataT ret = null;

		try {
			ret = m_binding.getMtaData(topologyKey, mtaResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param cmResId
	 * @return
	 * @throws RemoteException
	 */
	public CmDataT getCmData(TopoHierarchyKeyT topologyKey, BigInteger cmResId) {
		CmDataT ret = null;

		try {
			ret = m_binding.getCmData(topologyKey, cmResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param cmtsResId
	 * @param cmResId
	 * @return
	 * @throws RemoteException
	 */
	public CmtsCmDataT getCmtsCmData(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId, BigInteger cmResId) {
		CmtsCmDataT ret = null;

		try {
			ret = m_binding.getCmtsCmData(topologyKey, cmtsResId, cmResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param event
	 * @return
	 * @throws RemoteException
	 */
	public short sendEvent(EventMessageT event) {
		short ret = -1;

		try {
			ret = m_binding.sendEvent(event);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * Test main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CPeerHelper cph = new CPeerHelper("192.168.1.104:9091");
		byte[] b = { 0 };
		TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
				new BigInteger(b), new BigInteger(b));
		BigInteger mtaResId = new BigInteger("1");
		// String ret = cph.pingMta(tK, mtaResId);
		// System.out.println("ret=" + ret);
	}
}
