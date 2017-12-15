/**
 * JDeviceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.services.CableAssurance.JDevice;

public class JDeviceServiceTestCase extends junit.framework.TestCase {
    public JDeviceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testJDeviceEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.services.CableAssurance.JDevice.JDeviceServiceLocator().getJDeviceEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.services.CableAssurance.JDevice.JDeviceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1JDeviceEPGetDeviceDetails() throws Exception {
        com.palmyrasys.www.services.CableAssurance.JDevice.JDeviceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.services.CableAssurance.JDevice.JDeviceSOAPBindingStub)
                          new com.palmyrasys.www.services.CableAssurance.JDevice.JDeviceServiceLocator().getJDeviceEP();
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
        com.palmyrasys.www.services.CableAssurance.EndUserDeviceTypeT value = null;
        value = binding.getDeviceDetails(new java.lang.String());
        // TBD - validate results
    }

}
