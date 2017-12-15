/**
 * AlarmServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Alarm;

public class AlarmServiceTestCase extends junit.framework.TestCase {
    public AlarmServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAlarmEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AlarmEPGetAlarmHistory() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.AlarmHistoryT[] value = null;
        value = binding.getAlarmHistory(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test2AlarmEPGetCurrentAlarms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT value = null;
        value = binding.getCurrentAlarms(new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test3AlarmEPGetCurrentAlarmsForResource() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT value = null;
        value = binding.getCurrentAlarmsForResource(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test4AlarmEPGetCurrentAlarm() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT value = null;
        value = binding.getCurrentAlarm(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test5AlarmEPGetCurrentAlarmsByType() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmsRespT value = null;
        value = binding.getCurrentAlarmsByType(new java.lang.String(), new java.lang.String(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test6AlarmEPGetHistoricalAlarms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT value = null;
        value = binding.getHistoricalAlarms(new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test7AlarmEPGetHistoricalAlarmsForResource() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT value = null;
        value = binding.getHistoricalAlarmsForResource(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test8AlarmEPGetHistoricalAlarm() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT value = null;
        value = binding.getHistoricalAlarm(new com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT(), new java.math.BigInteger("0"));
        // TBD - validate results
    }

    public void test9AlarmEPGetHistoricalAlarmsByType() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmsRespT value = null;
        value = binding.getHistoricalAlarmsByType(new java.lang.String(), new java.lang.String(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.InputTimeT(), new com.palmyrasys.www.webservices.CableAssurance.ResultBatchT(), new com.palmyrasys.www.webservices.CableAssurance.QueryStateT[0]);
        // TBD - validate results
    }

    public void test10AlarmEPClearAlarms() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Alarm.AlarmServiceLocator().getAlarmEP();
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
        value = binding.clearAlarms(new com.palmyrasys.www.webservices.CableAssurance.AlarmIdT[0]);
        // TBD - validate results
    }

}
