//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2005 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
// OpenNMS Licensing       <license@opennms.org>
//     http://www.opennms.org/
//     http://www.opennms.com/
//

package org.opennms.netmgt.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opennms.netmgt.xml.event.Event;

/**
 * A test network configuration
 * 
 * @author brozow
 * 
 */
public class MockNetwork extends MockContainer {

    public Event createDownEvent() {
        throw new UnsupportedOperationException("Cannot generate down event for the network");
    }
    public Event createUpEvent() {
        throw new UnsupportedOperationException("Cannot generate up event for the network");
    }
    public Event createDeleteEvent() {
        throw new UnsupportedOperationException("Cannot generate delete event for the network");
    }
    private MockInterface m_currentInterface;

    private MockNode m_currentNode;

    private long m_defaultInterval = 1234L;

    private Map m_idToNameMap = new HashMap();

    private int m_invalidPollCount;

    private Map m_nameToIdMap = new HashMap();
    
    private String m_criticalService;
	
	private String m_ifAlias;

    private int m_nextServiceId = 1;

    public MockNetwork() {
        super(null);
        m_criticalService = "ICMP";
    }
    
    public String getCriticalService() {
        return m_criticalService;
    }
    
    public void setCriticalService(String svcName) {
        m_criticalService = svcName;
    }
    
    public String getIfAlias() {
        return m_ifAlias;
    }
    
    public void setIfAlias(String ifAlias) {
        m_currentInterface.setIfAlias(ifAlias);
    }

    // model
    public MockInterface addInterface(int nodeId, String ipAddr) {
        return getNode(nodeId).addInterface(ipAddr);
    }

    // model
    public MockInterface addInterface(String ipAddr) {
        m_currentInterface = m_currentNode.addInterface(ipAddr);
        return m_currentInterface;
    }

    // model
    public MockNode addNode(int nodeid, String label) {
        m_currentNode = (MockNode) addMember(new MockNode(this, nodeid, label));
        return m_currentNode;
    }

    // model 
    public MockService addService(int nodeId, String ipAddr, String svcName) {
        int serviceId = getServiceId(svcName);
        return getInterface(nodeId, ipAddr).addService(svcName, serviceId);
    }

    // model
    public MockService addService(String svcName) {
        int serviceId = getServiceId(svcName);
        return m_currentInterface.addService(svcName, serviceId);

    }

    // model
    public Map getIdToNameMap() {
        return Collections.unmodifiableMap(m_idToNameMap);
    }

    // model
    public MockInterface getInterface(int nodeid, String ipAddr) {
        MockNode node = getNode(nodeid);
        return (node == null ? null : node.getInterface(ipAddr));
    }

    // stats
    public int getInvalidPollCount() {
        return m_invalidPollCount;
    }

    // impl
    Object getKey() {
        return this;
    }

    // model
    public Map getNameToIdMap() {
        return Collections.unmodifiableMap(m_nameToIdMap);
    }

    // model
    public MockNode getNode(int i) {
        return (MockNode) getMember(new Integer(i));
    }

    // model
    public int getNodeIdForInterface(final String ipAddr) {
        class NodeFinder extends MockVisitorAdapter {
            MockNode node;

            public MockNode getNode() {
                return node;
            }

            public void visitInterface(MockInterface iface) {
                if (iface.getIpAddr().equals(ipAddr)) {
                    node = iface.getNode();
                }
            }

        }
        ;
        NodeFinder finder = new NodeFinder();
        visit(finder);
        return finder.getNode() == null ? -1 : finder.getNode().getNodeId();
    }

    // model
    public MockService getService(int nodeid, String ipAddr, String svcName) {
        MockInterface iface = getInterface(nodeid, ipAddr);
        return (iface == null ? null : iface.getService(svcName));
    }

    // model
    private int getServiceId(String svcName) {
        int serviceId;
        if (m_nameToIdMap.containsKey(svcName)) {
            serviceId = ((Integer) m_nameToIdMap.get(svcName)).intValue();
        } else {
            serviceId = m_nextServiceId++;
            Integer serviceIdObj = new Integer(serviceId);
            m_nameToIdMap.put(svcName, serviceIdObj);
            m_idToNameMap.put(serviceIdObj, svcName);
        }
        return serviceId;
    }

    // stats
    public void receivedInvalidPoll(String ipAddr, String svcName) {
        m_invalidPollCount++;
    }

    // model
    public void removeElement(MockElement element) {
        MockContainer parent = element.getParent();
        parent.removeMember(element);
    }

    // model
    public void removeInterface(MockInterface iface) {
        removeElement(iface);
    }

    // model
    public void removeNode(MockNode node) {
        removeElement(node);
    }

    // model
    public void removeService(MockService svc) {
        removeElement(svc);
    }

    // stats
    public void resetInvalidPollCount() {
        m_invalidPollCount = 0;
    }

    // impl
    public void visit(MockVisitor v) {
        super.visit(v);
        v.visitNetwork(this);
        visitMembers(v);
    }
    
    public int getNodeCount() {
        class NodeCounter extends MockVisitorAdapter {
            int count = 0;
            public void visitNode(MockNode node) {
                count++;
            }
            public int getCount() {
                return count;
            }
        }
        NodeCounter counter = new NodeCounter();
        visit(counter);
        return counter.getCount();
    }

    public int getInterfaceCount() {
        class InterfaceCounter extends MockVisitorAdapter {
            int count = 0;
            public void visitInterface(MockInterface iface) {
                count++;
            }
            public int getCount() {
                return count;
            }
        }
        InterfaceCounter counter = new InterfaceCounter();
        visit(counter);
        return counter.getCount();
    }

    public int getServiceCount() {
        class ServiceCounter extends MockVisitorAdapter {
            int count = 0;
            public void visitService(MockService svc) {
                count++;
            }
            public int getCount() {
                return count;
            }
        }
        ServiceCounter counter = new ServiceCounter();
        visit(counter);
        return counter.getCount();
    }


}
