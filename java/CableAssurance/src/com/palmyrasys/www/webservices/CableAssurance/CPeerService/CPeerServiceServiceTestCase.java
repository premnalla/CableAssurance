/**
 * CPeerServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.CPeerService;

public class CPeerServiceServiceTestCase extends junit.framework.TestCase {
    public CPeerServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCPeerServiceEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CPeerServiceEPPingMta() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEP();
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
        java.lang.String value = null;
        value = binding.pingMta(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test2CPeerServiceEPGetMtaData() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaDataT value = null;
        value = binding.getMtaData(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test3CPeerServiceEPGetCmData() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmDataT value = null;
        value = binding.getCmData(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test4CPeerServiceEPGetCmtsCmData() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmtsCmDataT value = null;
        value = binding.getCmtsCmData(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test5CPeerServiceEPSendEvent() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.CPeerService.CPeerServiceServiceLocator().getCPeerServiceEP();
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
        short value = -3;
        value = binding.sendEvent(new com.palmyrasys.www.webservices.CableAssurance.EventMessageT());
        // TBD - validate results
    }

}
