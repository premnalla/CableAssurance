//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// 2003 Jan 31: Cleaned up some unused imports.
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
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
// Tab Size = 8
//

package org.opennms.netmgt.eventd.adaptors;

import java.util.Iterator;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.EventReceipt;

/**
 * This interface provides the contract that implementor must implement in order
 * to receive events from adaptors.
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http;//www.opennms.org">OpenNMS </a>
 */
public class EventHandlerMBeanProxy implements EventHandler {
    private MBeanServer m_mbserver;

    private ObjectName m_listener;

    private void findServer() throws InstanceNotFoundException {
        List servers = MBeanServerFactory.findMBeanServer(null);
        Iterator i = servers.iterator();
        while (i.hasNext()) {
            try {
                MBeanServer sx = (MBeanServer) i.next();
                if (sx.getObjectInstance(m_listener) != null) {
                    m_mbserver = sx;
                    break;
                }
            } catch (InstanceNotFoundException e) {
                // do nothnig
            }
        }
        if (m_mbserver == null)
            throw new InstanceNotFoundException("could not locate mbean server instance");
    }

    public EventHandlerMBeanProxy(String name) throws MalformedObjectNameException, InstanceNotFoundException {
        m_listener = new ObjectName(name);
        findServer();
    }

    public EventHandlerMBeanProxy(ObjectName name) throws InstanceNotFoundException {
        m_listener = name;
        findServer();
    }

    public EventHandlerMBeanProxy(ObjectName name, MBeanServer server) {
        m_listener = name;
        m_mbserver = server;
    }

    public boolean processEvent(Event event) {
        boolean result = false;
        try {
            Boolean r = (Boolean) m_mbserver.invoke(m_listener, "processEvent", new Object[] { event }, new String[] { "org.opennms.netmgt.xml.event.Event" });
            result = r.booleanValue();
        } catch (Throwable t) {
            ThreadCategory.getInstance(getClass()).warn("Invocation on object " + m_listener + " failed", t);
        }

        return result;
    }

    public void receiptSent(EventReceipt receipt) {
        try {
            m_mbserver.invoke(m_listener, "receiptSent", new Object[] { receipt }, new String[] { "org.opennms.netmgt.xml.event.EventReceipt" });
        } catch (Throwable t) {
            ThreadCategory.getInstance(getClass()).warn("Invocation on object " + m_listener + " failed", t);
        }
    }

    public boolean equals(Object obj) {
        boolean rc = false;
        if (this == obj) {
            rc = true;
        } else if (obj != null && obj instanceof EventHandlerMBeanProxy) {
            EventHandlerMBeanProxy p = (EventHandlerMBeanProxy) obj;
            if (p.m_mbserver.equals(m_mbserver) && p.m_listener.equals(m_listener))
                rc = true;
        }

        return rc;
    }
}
