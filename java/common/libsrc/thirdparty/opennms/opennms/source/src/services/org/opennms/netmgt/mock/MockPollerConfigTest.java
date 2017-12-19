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

import java.util.Enumeration;

import junit.framework.TestCase;

import org.opennms.netmgt.config.poller.Package;
import org.opennms.netmgt.config.poller.Service;

/**
 * @author brozow
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MockPollerConfigTest extends TestCase {
    
    MockPollerConfig m_pollerConfig;
    
    public void setUp() {
        MockNetwork network = new MockNetwork();
        network.setCriticalService("ICMP");
        network.addNode(1, "Router");
        network.addInterface("192.168.1.1");
        network.addService("ICMP");
        network.addService("SMTP");
        network.addInterface("192.168.1.2");
        network.addService("ICMP");
        network.addService("SMTP");
        network.addNode(2, "Server");
        network.addInterface("192.168.1.3");
        network.addService("ICMP");
        network.addService("HTTP");
        network.addInterface("192.168.1.2");

        m_pollerConfig = new MockPollerConfig();
        m_pollerConfig.addPackage("TestPackage");
        m_pollerConfig.addDowntime(1000L, 0L, -1L, false);
        m_pollerConfig.setDefaultPollInterval(1000L);
        m_pollerConfig.populatePackage(network);
        m_pollerConfig.setPollInterval("ICMP", 500L);

        
    }
    
    public void testPollerConfig() {
        m_pollerConfig.setNodeOutageProcessingEnabled(true);
        m_pollerConfig.setPollInterval("HTTP", 750L);
        m_pollerConfig.setPollerThreads(5);
        m_pollerConfig.setCriticalService("YAHOO");
        
        // test the nodeOutageProcessing setting works
        assertTrue(m_pollerConfig.nodeOutageProcessingEnabled());

        // test to ensure that the poller has packages
        Enumeration pkgs = m_pollerConfig.enumeratePackage();
        assertNotNull(pkgs);
        int pkgCount = 0;
        Package pkg = null;

        while (pkgs.hasMoreElements()) {
            pkg = (Package) pkgs.nextElement();
            pkgCount++;
        }
        assertTrue(pkgCount > 0);

        // ensure a sample interface is in the package
        assertTrue(m_pollerConfig.interfaceInPackage("192.168.1.1", pkg));
        // assertFalse(m_pollerConfig.interfaceInPackage(192.168.1.7", pkg)); FIXME

        Enumeration svcs = pkg.enumerateService();
        assertNotNull(svcs);
        
        int svcCount = 0;
        boolean icmpFound = false;
        boolean httpFound = false;
        while (svcs.hasMoreElements()) {
            Service svc = (Service) svcs.nextElement();
            svcCount++;
            if ("ICMP".equals(svc.getName())) {
                icmpFound = true;
                assertEquals(500L, svc.getInterval());
            }
            else if ("HTTP".equals(svc.getName())) {
                httpFound = true;
                assertEquals(750L, svc.getInterval());
            }
            else {
                assertEquals(1000L, svc.getInterval());
            }
        }
        
        assertTrue(icmpFound);
        assertTrue(httpFound);
        assertEquals(3, svcCount);

        // ensure that setting the thread worked
        assertEquals(5, m_pollerConfig.getThreads());

        // ensure that setting the critical service worked
        assertEquals("YAHOO", m_pollerConfig.getCriticalService());

        // ensure that we have service monitors to the sevices
        assertNotNull(m_pollerConfig.getServiceMonitor("SMTP"));

    }



}
