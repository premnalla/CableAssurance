/**
 * AddressBook.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package samples.addr;

public interface AddressBook extends java.rmi.Remote {
    public void addEntry(java.lang.String name, samples.addr.Address address) throws java.rmi.RemoteException;
    public samples.addr.Address getAddressFromName(java.lang.String name) throws java.rmi.RemoteException;
}
