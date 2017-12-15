/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.CTEDataT;
import com.palmyrasys.www.webservices.CableAssurance.CTEQueryInputT;
import com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub;
import com.palmyrasys.www.webservices.CableAssurance.Cte.CteServiceLocator;


/**
 * @author Prem
 *
 */
public class CteHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(CteHelper.class);

	/**
	 * 
	 */
	private CteSOAPBindingStub m_binding = null;
	
	/**
	 * 
	 *
	 */
	public CteHelper() {
		try {
			m_binding = (CteSOAPBindingStub) new CteServiceLocator()
					.getCteEP();
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
	public CteHelper(String host) {
		try {
			CteServiceLocator loc = new CteServiceLocator();
			loc.setHost(host);
			m_binding = (CteSOAPBindingStub) loc.getCteEP();
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
	 * @param queryInput
	 * @return
	 */
	public CTEDataT[] getCteData(CTEQueryInputT[] queryInput) {
		CTEDataT[] ret = null;
		
		try {
			ret = m_binding.getCteData(queryInput);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
}
