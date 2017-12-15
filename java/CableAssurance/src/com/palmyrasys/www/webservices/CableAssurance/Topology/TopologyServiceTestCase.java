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

    public void test1TopologyEPGetResource() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.ResourceT value = null;
        value = binding.getResource(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test2TopologyEPGetRegions() throws Exception {
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

    public void test3TopologyEPGetMarkets() throws Exception {
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

    public void test4TopologyEPGetBlades() throws Exception {
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

    public void test5TopologyEPGetBlade() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.BladeT value = null;
        value = binding.getBlade(new java.math.BigInteger("0"), new java.math.BigInteger("0"), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test6TopologyEPGetBladeByName() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.BladeT value = null;
        value = binding.getBladeByName(new java.math.BigInteger("0"), new java.math.BigInteger("0"), new java.lang.String());
        // TBD - validate results
    }

    public void test7TopologyEPGetCmses() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.CmsT[] value = null;
        value = binding.getCmses();
        // TBD - validate results
    }

    public void test8TopologyEPGetCms() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.CmsT value = null;
        value = binding.getCms(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test9TopologyEPGetCmtses() throws Exception {
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

    public void test10TopologyEPGetCmts() throws Exception {
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

    public void test11TopologyEPGetCmtsByName() throws Exception {
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
        value = binding.getCmtsByName(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.lang.String());
        // TBD - validate results
    }

    public void test12TopologyEPGetCmtsSnmpV2CAttributes() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT value = null;
        value = binding.getCmtsSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test13TopologyEPGetCmSnmpV2CAttributes() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT value = null;
        value = binding.getCmSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test14TopologyEPGetMtaSnmpV2CAttributes() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT value = null;
        value = binding.getMtaSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test15TopologyEPGetCmtsAllSnmpV2CAttributes() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[] value = null;
        value = binding.getCmtsAllSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test16TopologyEPGetChannels() throws Exception {
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

    public void test17TopologyEPGetChannel() throws Exception {
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

    public void test18TopologyEPGetHfcs() throws Exception {
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

    public void test19TopologyEPGetHfc() throws Exception {
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

    public void test20TopologyEPGetCableModem() throws Exception {
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

    public void test21TopologyEPGetChannelCmes() throws Exception {
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

    public void test22TopologyEPGetHfcCmes() throws Exception {
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

    public void test23TopologyEPGetEmta() throws Exception {
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

    public void test24TopologyEPGetEmtaSecondary() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.EmtaSecondaryT value = null;
        value = binding.getEmtaSecondary(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test25TopologyEPGetChannelEmtas() throws Exception {
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

    public void test26TopologyEPGetHfcEmtas() throws Exception {
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

    public void test27TopologyEPGetDevicesForCsrPortal() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT value = null;
        value = binding.getDevicesForCsrPortal(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test28TopologyEPGetCustomerForResource() throws Exception {
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
        com.palmyrasys.www.webservices.CableAssurance.CACustomerT value = null;
        value = binding.getCustomerForResource(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

}
