/**
 * AddressBookServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.addr;

public class AddressBookServiceTestCase extends junit.framework.TestCase {
    public AddressBookServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAddressBookWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new samples.addr.AddressBookServiceLocator().getAddressBookAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new samples.addr.AddressBookServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AddressBookAddEntry() throws Exception {
        samples.addr.AddressBookSOAPBindingStub binding;
        try {
            binding = (samples.addr.AddressBookSOAPBindingStub)
                          new samples.addr.AddressBookServiceLocator().getAddressBook();
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
        binding.addEntry(new java.lang.String(), new samples.addr.Address());
        // TBD - validate results
    }

    public void test2AddressBookGetAddressFromName() throws Exception {
        samples.addr.AddressBookSOAPBindingStub binding;
        try {
            binding = (samples.addr.AddressBookSOAPBindingStub)
                          new samples.addr.AddressBookServiceLocator().getAddressBook();
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
        samples.addr.Address value = null;
        value = binding.getAddressFromName(new java.lang.String());
        // TBD - validate results
    }

}
