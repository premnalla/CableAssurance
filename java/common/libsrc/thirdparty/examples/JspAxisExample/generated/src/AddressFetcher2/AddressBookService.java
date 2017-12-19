/**
 * AddressBookService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package AddressFetcher2;

public interface AddressBookService extends javax.xml.rpc.Service {
    public java.lang.String getAddressBookAddress();

    public AddressFetcher2.AddressBook getAddressBook() throws javax.xml.rpc.ServiceException;

    public AddressFetcher2.AddressBook getAddressBook(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
