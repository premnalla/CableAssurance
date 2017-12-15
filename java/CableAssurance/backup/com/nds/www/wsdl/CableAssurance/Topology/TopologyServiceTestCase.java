/**
 * TopologyServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.Topology;

public class TopologyServiceTestCase extends junit.framework.TestCase {
    public TopologyServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testTopologyEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getTopologyEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1TopologyEPGetRegions() throws Exception {
        com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.nds.www.wsdl.CableAssurance.RegionT[] value = null;
        value = binding.getRegions();
        // TBD - validate results
    }

    public void test2TopologyEPGetMarkets() throws Exception {
        com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.nds.www.wsdl.CableAssurance.MarketT[] value = null;
        value = binding.getMarkets();
        // TBD - validate results
    }

    public void test3TopologyEPGetBlades() throws Exception {
        com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.nds.www.wsdl.CableAssurance.BladeT[] value = null;
        value = binding.getBlades();
        // TBD - validate results
    }

    public void test4TopologyEPGetCmtses() throws Exception {
        com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.nds.www.wsdl.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.nds.www.wsdl.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.nds.www.wsdl.CableAssurance.CmtsT[] value = null;
        value = binding.getCmtses();
        // TBD - validate results
    }

}
