/**
 * CteServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance.Cte;

public class CteServiceTestCase extends junit.framework.TestCase {
    public CteServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCteEPWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.palmyrasys.www.webservices.CableAssurance.Cte.CteServiceLocator().getCteEPAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.palmyrasys.www.webservices.CableAssurance.Cte.CteServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CteEPGetCteData() throws Exception {
        com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub binding;
        try {
            binding = (com.palmyrasys.www.webservices.CableAssurance.Cte.CteSOAPBindingStub)
                          new com.palmyrasys.www.webservices.CableAssurance.Cte.CteServiceLocator().getCteEP();
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
        com.palmyrasys.www.webservices.CableAssurance.CTEDataT[] value = null;
        value = binding.getCteData(new com.palmyrasys.www.webservices.CableAssurance.CTEQueryInputT[0]);
        // TBD - validate results
    }

}
