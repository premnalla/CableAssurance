/**
 * LocalSystemServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance;

public class LocalSystemServiceTestCase extends junit.framework.TestCase {
    public LocalSystemServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testLocalSystemPortWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator().getLocalSystemPortAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1LocalSystemPortQueryLocalSystem() throws Exception {
        com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator().getLocalSystemPort();
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
        com.nds.www.wsdl.CableAssurance.LocalSystemType value = null;
        value = binding.queryLocalSystem();
        // TBD - validate results
    }

    public void test2LocalSystemPortQueryChildAggregates() throws Exception {
        com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator().getLocalSystemPort();
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
        com.nds.www.wsdl.CableAssurance.AggregateType[] value = null;
        value = binding.queryChildAggregates();
        // TBD - validate results
    }

    public void test3LocalSystemPortQueryChildCmts() throws Exception {
        com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.LocalSystemSOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.LocalSystemServiceLocator().getLocalSystemPort();
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
        com.nds.www.wsdl.CableAssurance.CmtsType[] value = null;
        value = binding.queryChildCmts();
        // TBD - validate results
    }

}
