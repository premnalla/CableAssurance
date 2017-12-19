/**
 * GetQuoteServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.stock;

public class GetQuoteServiceTestCase extends junit.framework.TestCase {
    public GetQuoteServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testGetQuoteWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new samples.stock.GetQuoteServiceLocator().getGetQuoteAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new samples.stock.GetQuoteServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1GetQuoteGetQuote() throws Exception {
        samples.stock.GetQuoteBindingStub binding;
        try {
            binding = (samples.stock.GetQuoteBindingStub)
                          new samples.stock.GetQuoteServiceLocator().getGetQuote();
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
        float value = -3;
        value = binding.getQuote(new java.lang.String());
        // TBD - validate results
    }

    public void test2GetQuoteTest() throws Exception {
        samples.stock.GetQuoteBindingStub binding;
        try {
            binding = (samples.stock.GetQuoteBindingStub)
                          new samples.stock.GetQuoteServiceLocator().getGetQuote();
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
        java.lang.String value = null;
        value = binding.test();
        // TBD - validate results
    }

}
