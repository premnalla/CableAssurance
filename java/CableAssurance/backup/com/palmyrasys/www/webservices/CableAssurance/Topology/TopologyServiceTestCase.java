/**
 * TopologyServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Topology;

public class TopologyServiceTestCase extends junit.framework.TestCase {
    public TopologyServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testTopologyEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1TopologyEPGetRegions() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.RegionT[] value = null;
        value = binding.getRegions();
        // TBD - validate results
    }

    public void test2TopologyEPGetMarkets() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MarketT[] value = null;
        value = binding.getMarkets();
        // TBD - validate results
    }

    public void test3TopologyEPGetBlades() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.BladeT[] value = null;
        value = binding.getBlades();
        // TBD - validate results
    }

    public void test4TopologyEPGetCmtses() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmtsT[] value = null;
        value = binding.getCmtses();
        // TBD - validate results
    }

    public void test5TopologyEPGetCmts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmtsT value = null;
        value = binding.getCmts(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test6TopologyEPGetChannels() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.ChannelT[] value = null;
        value = binding.getChannels(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test7TopologyEPGetChannel() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.ChannelT value = null;
        value = binding.getChannel(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test8TopologyEPGetHfcs() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcT[] value = null;
        value = binding.getHfcs(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test9TopologyEPGetHfc() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcT value = null;
        value = binding.getHfc(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test10TopologyEPGetCableModem() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        value = binding.getCableModem(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test11TopologyEPGetChannelCmes() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CableModemT[] value = null;
        value = binding.getChannelCmes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test12TopologyEPGetHfcCmes() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CableModemT[] value = null;
        value = binding.getHfcCmes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test13TopologyEPGetEmta() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        value = binding.getEmta(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test14TopologyEPGetChannelEmtas() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.EmtaT[] value = null;
        value = binding.getChannelEmtas(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test15TopologyEPGetHfcEmtas() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Topology.TopologySOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Topology.TopologyServiceLocator().getTopologyEP();
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
        com.palmyrasys.www.webservices.CableAssurance.EmtaT[] value = null;
        value = binding.getHfcEmtas(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

}
