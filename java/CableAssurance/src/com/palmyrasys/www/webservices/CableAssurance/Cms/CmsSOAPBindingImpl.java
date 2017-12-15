/**
 * CmsSOAPBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Cms;

import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;

public class CmsSOAPBindingImpl implements
		com.palmyrasys.www.webservices.CableAssurance.Cms.CmsEP {
	public com.palmyrasys.www.webservices.CableAssurance.CMSLineT[] getLineStatus(
			com.palmyrasys.www.webservices.CableAssurance.CMSLineT[] input)
			throws java.rmi.RemoteException {
		CmsEP cms = LocalSystemSingleton.getInstance().getCmsHandler();
		return (cms.getLineStatus(input));
	}

}
