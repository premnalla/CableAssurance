/**
 * CsrPortalServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CsrPortal;

public class CsrPortalServiceTestCase extends junit.framework.TestCase {
    public CsrPortalServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCsrPortalEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator().getCsrPortalEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CsrPortalEPGetCustomerByName() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator().getCsrPortalEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CTEDataT[] value = null;
        value = binding.getCustomerByName(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test2CsrPortalEPGetCmByMac() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator().getCsrPortalEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CableModemT value = null;
        value = binding.getCmByMac(new java.lang.String());
        // TBD - validate results
    }

    public void test3CsrPortalEPGetMtaByMac() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CsrPortal.CsrPortalServiceLocator().getCsrPortalEP();
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
        com.palmyrasys.www.webservices.CableAssurance.EmtaT value = null;
        value = binding.getMtaByMac(new java.lang.String());
        // TBD - validate results
    }

}
