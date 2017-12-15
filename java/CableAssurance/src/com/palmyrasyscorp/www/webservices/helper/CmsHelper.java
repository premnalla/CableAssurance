/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CMSLineT;
import com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub;
import com.palmyrasys.www.webservices.CableAssurance.Cms.CmsServiceLocator;

/**
 * @author Prem
 *
 */
public class CmsHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(CmsHelper.class);

	/**
	 * 
	 */
	private CmsSOAPBindingStub m_binding = null;
	
	/**
	 * 
	 *
	 */
	public CmsHelper() {
		try {
			m_binding = (CmsSOAPBindingStub) new CmsServiceLocator()
					.getCmsEP();
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
	 * @param host
	 */
	public CmsHelper(String host) {
		try {
			CmsServiceLocator loc = new CmsServiceLocator();
			loc.setHost(host);
			m_binding = (CmsSOAPBindingStub) loc.getCmsEP();
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
	 * @param input
	 * @return
	 */
	public CMSLineT[] getLineStatus(CMSLineT[] input) {
		CMSLineT[] ret = null;
		
		try {
			ret = m_binding.getLineStatus(input);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
}
