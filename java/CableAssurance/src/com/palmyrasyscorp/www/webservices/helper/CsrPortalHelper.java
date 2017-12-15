/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CTEDataT;
import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub;
import com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator;

/**
 * @author Prem
 *
 */
public class CsrPortalHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(CsrPortalHelper.class);

	/**
	 * 
	 */
	private CsrPortalSOAPBindingStub m_binding = null;
	
	/**
	 * 
	 *
	 */
	public CsrPortalHelper() {
		try {
			m_binding = (CsrPortalSOAPBindingStub) new CsrPortalServiceLocator()
					.getCsrPortalEP();
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
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public CTEDataT[] getCustomerByName(String firstName, String lastName) {
		CTEDataT[] value = null;
		
		try {
			value = m_binding.getCustomerByName(firstName, lastName);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}
	
	/**
	 * 
	 * @param macAddress
	 * @return
	 */
    public CableModemT getCmByMac(String macAddress) {
    	CableModemT value = null;
    	
		try {
			value = m_binding.getCmByMac(macAddress);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
    }

    /**
     * 
     * @param macAddress
     * @return
     */
    public EmtaT getMtaByMac(String macAddress) {
    	EmtaT value = null;
    	
		try {
			value = m_binding.getMtaByMac(macAddress);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);    	
    }

}
