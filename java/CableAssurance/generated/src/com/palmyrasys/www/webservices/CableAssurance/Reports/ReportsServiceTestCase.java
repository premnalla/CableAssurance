/**
 * ReportsServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Reports;

public class ReportsServiceTestCase extends junit.framework.TestCase {
    public ReportsServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testReportsEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1ReportsEPGetHfcStatusSummary1() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT value = null;
        value = binding.getHfcStatusSummary1(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test2ReportsEPGetHfcStatusSummary2() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryRespT value = null;
        value = binding.getHfcStatusSummary2(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test3ReportsEPGetMtaStatusSummary1() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT value = null;
        value = binding.getMtaStatusSummary1(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test4ReportsEPGetMtaStatusSummary2() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryRespT value = null;
        value = binding.getMtaStatusSummary2(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test5ReportsEPGetCmStatusSummary1() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT value = null;
        value = binding.getCmStatusSummary1(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test6ReportsEPGetCmStatusSummary2() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT value = null;
        value = binding.getCmStatusSummary2(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test7ReportsEPGetCmTcaStatusSummary1() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT value = null;
        value = binding.getCmTcaStatusSummary1(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test8ReportsEPGetCmTcaStatusSummary2() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Reports.ReportsServiceLocator().getReportsEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryRespT value = null;
        value = binding.getCmTcaStatusSummary2(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"), com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT.CMTS, new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

}
