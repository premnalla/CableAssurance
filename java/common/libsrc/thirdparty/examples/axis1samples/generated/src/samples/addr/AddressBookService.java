/**
 * AddressBookService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.addr;

public interface AddressBookService extends javax.xml.rpc.Service {
    public java.lang.String getAddressBookAddress();

    public samples.addr.AddressBook getAddressBook() throws javax.xml.rpc.ServiceException;

    public samples.addr.AddressBook getAddressBook(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
