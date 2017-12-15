/**
 * LocalSystemServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.LocalSystem;

public class LocalSystemServiceTestCase extends junit.framework.TestCase {
    public LocalSystemServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testLocalSystemEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemServiceLocator().getLocalSystemEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1LocalSystemEPGetLocalSystem() throws Exception {
        com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemServiceLocator().getLocalSystemEP();
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
        com.nds.www.wsdl.CableAssurance.LocalSystemT value = null;
        value = binding.getLocalSystem();
        // TBD - validate results
    }

    public void test2LocalSystemEPGetLocalSystems() throws Exception {
        com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemSOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.LocalSystem.LocalSystemServiceLocator().getLocalSystemEP();
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
        com.nds.www.wsdl.CableAssurance.LocalSystemT[] value = null;
        value = binding.getLocalSystems();
        // TBD - validate results
    }

}
