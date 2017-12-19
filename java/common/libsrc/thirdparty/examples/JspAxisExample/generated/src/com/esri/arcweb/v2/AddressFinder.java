/**
 * AddressFinder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.esri.arcweb.v2;

public interface AddressFinder extends javax.xml.rpc.Service {

/**
 * The Address Finder Web Service enables users to input a street
 * address and receive a candidate list of matching addresses and associated
 * coordinates. It also enables users to input x,y-coordinates and receive
 * a street address. ArcWeb Service is intended to support application
 * developers who would like to provide "find an address" and "get an
 * address" functionality within their Internet applications.
 */
    public java.lang.String getIAddressFinderAddress();

    public com.esri.arcweb.v2.IAddressFinder getIAddressFinder() throws javax.xml.rpc.ServiceException;

    public com.esri.arcweb.v2.IAddressFinder getIAddressFinder(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
