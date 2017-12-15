/**
 * AdministrationServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Administration;

public class AdministrationServiceTestCase extends junit.framework.TestCase {
    public AdministrationServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAdministrationEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AdministrationEPUpdateLocalSystem() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateLocalSystem(new com.palmyrasys.www.webservices.CableAssurance.LocalSystemT());
        // TBD - validate results
    }

    public void test2AdministrationEPUpdateRegion() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateRegion(new com.palmyrasys.www.webservices.CableAssurance.RegionT());
        // TBD - validate results
    }

    public void test3AdministrationEPAddRegion() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addRegion(new com.palmyrasys.www.webservices.CableAssurance.RegionT());
        // TBD - validate results
    }

    public void test4AdministrationEPUpdateMarket() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateMarket(new com.palmyrasys.www.webservices.CableAssurance.MarketT());
        // TBD - validate results
    }

    public void test5AdministrationEPAddMarket() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addMarket(new com.palmyrasys.www.webservices.CableAssurance.MarketT());
        // TBD - validate results
    }

    public void test6AdministrationEPUpdateBlade() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateBlade(new com.palmyrasys.www.webservices.CableAssurance.BladeT());
        // TBD - validate results
    }

    public void test7AdministrationEPAddBlade() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addBlade(new com.palmyrasys.www.webservices.CableAssurance.BladeT());
        // TBD - validate results
    }

    public void test8AdministrationEPDeleteBlade() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.deleteBlade(new com.palmyrasys.www.webservices.CableAssurance.BladeT());
        // TBD - validate results
    }

    public void test9AdministrationEPUpdateCmts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmts(new com.palmyrasys.www.webservices.CableAssurance.CmtsT());
        // TBD - validate results
    }

    public void test10AdministrationEPAddCmts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addCmts(new com.palmyrasys.www.webservices.CableAssurance.CmtsT());
        // TBD - validate results
    }

    public void test11AdministrationEPDeleteCmts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.deleteCmts(new com.palmyrasys.www.webservices.CableAssurance.CmtsT());
        // TBD - validate results
    }

    public void test12AdministrationEPAddCmtsAllSnmpV2CAttributes() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addCmtsAllSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[0]);
        // TBD - validate results
    }

    public void test13AdministrationEPUpdateCmtsAllSnmpV2CAttributes() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmtsAllSnmpV2CAttributes(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT[0]);
        // TBD - validate results
    }

    public void test14AdministrationEPUpdateCms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCms(new com.palmyrasys.www.webservices.CableAssurance.CmsT());
        // TBD - validate results
    }

    public void test15AdministrationEPAddCms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addCms(new com.palmyrasys.www.webservices.CableAssurance.CmsT());
        // TBD - validate results
    }

    public void test16AdministrationEPDeleteCms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.deleteCms(new com.palmyrasys.www.webservices.CableAssurance.CmsT());
        // TBD - validate results
    }

    public void test17AdministrationEPGetPollingIntervals() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT value = null;
        value = binding.getPollingIntervals();
        // TBD - validate results
    }

    public void test18AdministrationEPUpdatePollingIntervals() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updatePollingIntervals(new com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT());
        // TBD - validate results
    }

    public void test19AdministrationEPGetMtaStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT value = null;
        value = binding.getMtaStatusThreshold();
        // TBD - validate results
    }

    public void test20AdministrationEPUpdateMtaStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateMtaStatusThreshold(new com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT());
        // TBD - validate results
    }

    public void test21AdministrationEPGetHfcStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT value = null;
        value = binding.getHfcStatusThreshold();
        // TBD - validate results
    }

    public void test22AdministrationEPUpdateHfcStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateHfcStatusThreshold(new com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT());
        // TBD - validate results
    }

    public void test23AdministrationEPGetChannelStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT value = null;
        value = binding.getChannelStatusThreshold();
        // TBD - validate results
    }

    public void test24AdministrationEPUpdateChannelStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateChannelStatusThreshold(new com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT());
        // TBD - validate results
    }

    public void test25AdministrationEPGetCmtsStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT value = null;
        value = binding.getCmtsStatusThreshold();
        // TBD - validate results
    }

    public void test26AdministrationEPUpdateCmtsStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmtsStatusThreshold(new com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT());
        // TBD - validate results
    }

    public void test27AdministrationEPGetCmsStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT value = null;
        value = binding.getCmsStatusThreshold();
        // TBD - validate results
    }

    public void test28AdministrationEPUpdateCmsStatusThreshold() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmsStatusThreshold(new com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT());
        // TBD - validate results
    }

    public void test29AdministrationEPGetMtaAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT value = null;
        value = binding.getMtaAlarmConfig();
        // TBD - validate results
    }

    public void test30AdministrationEPUpdateMtaAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateMtaAlarmConfig(new com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT());
        // TBD - validate results
    }

    public void test31AdministrationEPGetHfcAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT value = null;
        value = binding.getHfcAlarmConfig();
        // TBD - validate results
    }

    public void test32AdministrationEPUpdateHfcAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateHfcAlarmConfig(new com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT());
        // TBD - validate results
    }

    public void test33AdministrationEPGetCmtsAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT value = null;
        value = binding.getCmtsAlarmConfig();
        // TBD - validate results
    }

    public void test34AdministrationEPUpdateCmtsAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmtsAlarmConfig(new com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT());
        // TBD - validate results
    }

    public void test35AdministrationEPGetCmsAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT value = null;
        value = binding.getCmsAlarmConfig();
        // TBD - validate results
    }

    public void test36AdministrationEPUpdateCmsAlarmConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmsAlarmConfig(new com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT());
        // TBD - validate results
    }

    public void test37AdministrationEPGetCmPerfConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT value = null;
        value = binding.getCmPerfConfig();
        // TBD - validate results
    }

    public void test38AdministrationEPUpdateCmPerfConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateCmPerfConfig(new com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT());
        // TBD - validate results
    }

    public void test39AdministrationEPGetUsers() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.UserT[] value = null;
        value = binding.getUsers();
        // TBD - validate results
    }

    public void test40AdministrationEPAddUser() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addUser(new com.palmyrasys.www.webservices.CableAssurance.UserT());
        // TBD - validate results
    }

    public void test41AdministrationEPGetUser() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.UserT value = null;
        value = binding.getUser(new java.lang.String());
        // TBD - validate results
    }

    public void test42AdministrationEPUpdateUser() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateUser(new com.palmyrasys.www.webservices.CableAssurance.UserT());
        // TBD - validate results
    }

    public void test43AdministrationEPUpdateUserPassword() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateUserPassword(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test44AdministrationEPGetApplicationDomains() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT[] value = null;
        value = binding.getApplicationDomains();
        // TBD - validate results
    }

    public void test45AdministrationEPGetAccessRights() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.UserAccessT[] value = null;
        value = binding.getAccessRights();
        // TBD - validate results
    }

    public void test46AdministrationEPGetRoles() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.RoleT[] value = null;
        value = binding.getRoles();
        // TBD - validate results
    }

    public void test47AdministrationEPGetRole() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.RoleT value = null;
        value = binding.getRole(new java.lang.String());
        // TBD - validate results
    }

    public void test48AdministrationEPAddRole() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.addRole(new com.palmyrasys.www.webservices.CableAssurance.RoleT());
        // TBD - validate results
    }

    public void test49AdministrationEPUpdateRole() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.updateRole(new com.palmyrasys.www.webservices.CableAssurance.RoleT());
        // TBD - validate results
    }

    public void test50AdministrationEPDownloadConfigFromParent() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        value = binding.downloadConfigFromParent();
        // TBD - validate results
    }

    public void test51AdministrationEPGetConfig() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Administration.AdministrationServiceLocator().getAdministrationEP();
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
        com.palmyrasys.www.webservices.CableAssurance.ConfigDownloadT value = null;
        value = binding.getConfig();
        // TBD - validate results
    }

}
