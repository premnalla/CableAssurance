/**
 * PerformanceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Performance;

public class PerformanceServiceTestCase extends junit.framework.TestCase {
    public PerformanceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testPerformanceEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1PerformanceEPGetCurrentCmtsCounts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsT value = null;
        value = binding.getCurrentCmtsCounts(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test2PerformanceEPGetCmtsCountsHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] value = null;
        value = binding.getCmtsCountsHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test3PerformanceEPGetCurrentChannelCounts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsT value = null;
        value = binding.getCurrentChannelCounts(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test4PerformanceEPGetChannelCountsHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] value = null;
        value = binding.getChannelCountsHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test5PerformanceEPGetCurrentHfcCounts() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsT value = null;
        value = binding.getCurrentHfcCounts(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test6PerformanceEPGetHfcCountsHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.GenericCountsHistoryT[] value = null;
        value = binding.getHfcCountsHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test7PerformanceEPGetCurrentCmStatus() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusT value = null;
        value = binding.getCurrentCmStatus(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test8PerformanceEPGetCmStatusHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusHistoryT[] value = null;
        value = binding.getCmStatusHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test9PerformanceEPGetCurrentCmPerformance() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmCurrentPerformanceT value = null;
        value = binding.getCurrentCmPerformance(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test10PerformanceEPGetCmPerformanceHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmPerformanceHistoryT[] value = null;
        value = binding.getCmPerformanceHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test11PerformanceEPGetCurrentMtaAvailability() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityT value = null;
        value = binding.getCurrentMtaAvailability(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test12PerformanceEPGetMtaAvailabilityHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaAvailabilityHistoryT[] value = null;
        value = binding.getMtaAvailabilityHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test13PerformanceEPGetCurrentMtaProvisionedStatus() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusT value = null;
        value = binding.getCurrentMtaProvisionedStatus(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test14PerformanceEPGetMtaProvisionedStatusHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaProvStatusHistoryT[] value = null;
        value = binding.getMtaProvisionedStatusHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

    public void test15PerformanceEPGetCurrentMtaPingStatus() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusT value = null;
        value = binding.getCurrentMtaPingStatus(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test16PerformanceEPGetMtaPingStatusHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Performance.PerformanceServiceLocator().getPerformanceEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaPingStatusHistoryT[] value = null;
        value = binding.getMtaPingStatusHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT());
        // TBD - validate results
    }

}
