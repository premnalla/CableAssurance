/**
 * IAddressFinder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.esri.arcweb.v2;

public interface IAddressFinder extends java.rmi.Remote {

    /**
     * Returns an address from an x,y-coordinate.
     */
    public com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address getAddress(com.themindelectric.www._package.com_esri_is_services_common_v2_geom.Point point, com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions addressFinderOptions, java.lang.String token) throws java.rmi.RemoteException;

    /**
     * Returns an x,y-coordinate from an address.
     */
    public com.themindelectric.www._package.com_esri_is_services_common_v2.LocationInfo findAddress(com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.Address address, com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder.AddressFinderOptions addressFinderOptions, java.lang.String token) throws java.rmi.RemoteException;

    /**
     * Returnes an array of country names serviced by a specified
     * datasource.
     */
    public java.lang.String[] getCountries(java.lang.String datasource, java.lang.String token) throws java.rmi.RemoteException;
    public java.lang.String getVersion() throws java.rmi.RemoteException;
}
