/**
 * TopologyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nds.www.wsdl.CableAssurance.Topology;

public interface TopologyService extends javax.xml.rpc.Service {
    public java.lang.String getTopologyEPAddress();

    public com.nds.www.wsdl.CableAssurance.Topology.TopologyEP getTopologyEP() throws javax.xml.rpc.ServiceException;

    public com.nds.www.wsdl.CableAssurance.Topology.TopologyEP getTopologyEP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
