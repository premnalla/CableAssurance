/**
 * AddressBook.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package AddressFetcher2;

public interface AddressBook extends java.rmi.Remote {
    public void addEntry(java.lang.String name, AddressFetcher2.Address address) throws java.rmi.RemoteException;
    public AddressFetcher2.Address getAddressFromName(java.lang.String name) throws java.rmi.RemoteException;
}
