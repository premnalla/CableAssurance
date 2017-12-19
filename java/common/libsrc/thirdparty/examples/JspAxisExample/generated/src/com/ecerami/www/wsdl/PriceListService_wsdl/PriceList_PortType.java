/**
 * PriceList_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ecerami.www.wsdl.PriceListService_wsdl;

public interface PriceList_PortType extends java.rmi.Remote {
    public double[] getPriceList(java.lang.String[] sku_list) throws java.rmi.RemoteException;
}
