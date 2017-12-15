/**
 * CmsServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Cms;

public class CmsServiceTestCase extends junit.framework.TestCase {
    public CmsServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCmsEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Cms.CmsServiceLocator().getCmsEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Cms.CmsServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CmsEPGetLineStatus() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Cms.CmsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Cms.CmsServiceLocator().getCmsEP();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.palmyrasys.www.webservices.CableAssurance.CMSLineT[] value = null;
        value = binding.getLineStatus(new com.palmyrasys.www.webservices.CableAssurance.CMSLineT[0]);
        // TBD - validate results
    }

}
