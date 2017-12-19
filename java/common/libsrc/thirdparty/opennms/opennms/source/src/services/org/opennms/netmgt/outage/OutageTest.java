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
package org.opennms.netmgt.outage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.mock.MockDatabase;
import org.opennms.netmgt.mock.MockElement;
import org.opennms.netmgt.mock.MockEventIpcManager;
import org.opennms.netmgt.mock.MockInterface;
import org.opennms.netmgt.mock.MockNetwork;
import org.opennms.netmgt.mock.MockOutageConfig;
import org.opennms.netmgt.mock.MockService;
import org.opennms.netmgt.mock.MockUtil;
import org.opennms.netmgt.mock.MockVisitor;
import org.opennms.netmgt.mock.MockVisitorAdapter;
import org.opennms.netmgt.mock.OutageAnticipator;
import org.opennms.netmgt.utils.Querier;
import org.opennms.netmgt.xml.event.Event;

public class OutageTest extends TestCase {

    private OutageManager m_outageMgr;
    private MockNetwork m_network;
    private MockDatabase m_db;
    private MockEventIpcManager m_eventMgr;
    private OutageAnticipator m_outageAnticipator;
    private boolean m_started = false;
    
    protected void setUp() throws Exception {
        MockUtil.setupLogging();
        MockUtil.resetLogLevel();
        
        m_network = new MockNetwork();
        m_network.setCriticalService("ICMP");
        m_network.addNode(1, "Router");
        m_network.addInterface("192.168.1.1");
        m_network.addService("ICMP");
        m_network.addService("SMTP");
        m_network.addInterface("192.168.1.2");
        m_network.addService("ICMP");
        m_network.addService("SMTP");
        m_network.addNode(2, "Server");
        m_network.addInterface("192.168.1.3");
        m_network.addService("ICMP");
        m_network.addService("HTTP");
        m_network.addInterface("192.168.1.2");
        
        m_db = new MockDatabase();
        m_db.populate(m_network);
        
        m_outageAnticipator = new OutageAnticipator(m_db);
        
        m_eventMgr = new MockEventIpcManager();
        m_eventMgr.setEventWriter(m_db);
        m_eventMgr.addEventListener(m_outageAnticipator);
        
        MockOutageConfig config = new MockOutageConfig();
        config.setGetNextOutageID(m_db.getNextOutageIdStatement());
        
        m_outageMgr = new OutageManager();
        m_outageMgr.setEventMgr(m_eventMgr);
        m_outageMgr.setOutageMgrConfig(config);
        m_outageMgr.setDbConnectionFactory(m_db);
        
    }

    private void startOutageMgr() {
        m_outageMgr.init();
        m_outageMgr.start();
        m_started = true;
    }

    protected void tearDown() throws Exception {
        m_eventMgr.finishProcessingEvents();
        stopOutageMgr();
        sleep(100);
        assertTrue(MockUtil.noWarningsOrHigherLogged());
        m_db.drop();
    }
    
    private void stopOutageMgr() {
        if (m_started) {
            m_outageMgr.stop();
        }
    }

    class OutageChecker extends Querier { 
        private Event m_lostSvcEvent;
        private Timestamp m_lostSvcTime;
        private MockService m_svc;
        private Event m_regainedSvcEvent;
        private Timestamp m_regainedSvcTime;
        OutageChecker(MockService svc, Event lostSvcEvent) throws Exception {
            this(svc, lostSvcEvent, null);
        }
        OutageChecker(MockService svc, Event lostSvcEvent, Event regainedSvcEvent) {
            super(m_db, "select * from outages where nodeid = ? and ipAddr = ? and serviceId = ?");
            
            m_svc = svc;
            m_lostSvcEvent = lostSvcEvent;
            m_lostSvcTime = m_db.convertEventTimeToTimeStamp(m_lostSvcEvent.getTime());
            m_regainedSvcEvent = regainedSvcEvent;
            if (m_regainedSvcEvent != null)
                m_regainedSvcTime = m_db.convertEventTimeToTimeStamp(m_regainedSvcEvent.getTime());
        }
       public void processRow(ResultSet rs) throws SQLException {
            assertEquals(m_svc.getNodeId(), rs.getInt("nodeId"));
            assertEquals(m_svc.getIpAddr(), rs.getString("ipAddr"));
            assertEquals(m_svc.getId(), rs.getInt("serviceId"));
            assertEquals(m_lostSvcEvent.getDbid(), rs.getInt("svcLostEventId"));
            assertEquals(m_lostSvcTime, rs.getTimestamp("ifLostService"));
            assertEquals(getRegainedEventId(), rs.getObject("svcRegainedEventId"));
            assertEquals(m_regainedSvcTime, rs.getTimestamp("ifRegainedService"));
        }
       private Integer getRegainedEventId() {
           if (m_regainedSvcEvent == null)
               return null;
           return new Integer(m_regainedSvcEvent.getDbid());
       }
    };
    

    public void testNodeLostRegainedService() throws Exception {

        testElementDownUp(m_network.getService(1, "192.168.1.1", "SMTP"));

    }
    
    
    public void testInterfaceDownUp() {

        testElementDownUp(m_network.getInterface(1, "192.168.1.1"));
    }

    public void testNodeDownUp() {
        testElementDownUp(m_network.getNode(1));
    }

    // interfaceReparented: EventConstants.INTERFACE_REPARENTED_EVENT_UEI
    public void testInterfaceReparented() {

        // create some outages in the database
        testElementDownUp(m_network.getInterface(1, "192.168.1.2"));

        final String ifOutageOnNode1 = "select * from outages where nodeId = 1 and ipAddr = '192.168.1.2'";
        final String ifOutageOnNode2 = "select * from outages where nodeId = 2 and ipAddr = '192.168.1.2'";

        assertEquals(2, m_db.countRows(ifOutageOnNode1));
        assertEquals(0, m_db.countRows(ifOutageOnNode2));
        
        Event e = MockUtil.createReparentEvent("Test", "192.168.1.2", 1, 2);
        m_eventMgr.sendEventToListeners(e);
        
        sleep(500);

        assertEquals(0, m_db.countRows(ifOutageOnNode1));
        assertEquals(2, m_db.countRows(ifOutageOnNode2));
        
        
    }
    
    public void testOutageAlreadyExists() {
        // create an outage for the service
        MockService svc = m_network.getService(1, "192.168.1.1", "SMTP");
        MockInterface iface = m_network.getInterface(1, "192.168.1.2");
        
        Event svcLostEvent = MockUtil.createNodeLostServiceEvent("Test", svc);
        m_db.writeEvent(svcLostEvent);
        createOutages(svc, svcLostEvent);

        assertEquals(1, m_db.countOpenOutagesForService(svc));

        startOutageMgr();
        
        m_eventMgr.sendEventToListeners(svcLostEvent);
        
        sleep(200);
        
        // expect a warning to be logged
        assertFalse(MockUtil.noWarningsOrHigherLogged());
        
        // reset it so we don't break in tearDown
        MockUtil.resetLogLevel();

    }
    
    // test open outages for unmanaged services
    public void testUnmangedWithOpenOutageAtStartup() {
        // before we start we need to initialize the database
        
        // create an outage for the service
        MockService svc = m_network.getService(1, "192.168.1.1", "SMTP");
        MockInterface iface = m_network.getInterface(1, "192.168.1.2");
        
        Event svcLostEvent = MockUtil.createNodeLostServiceEvent("Test", svc);
        m_db.writeEvent(svcLostEvent);
        createOutages(svc, svcLostEvent);
        
        Event ifaceDownEvent = MockUtil.createInterfaceDownEvent("Test", iface);
        m_db.writeEvent(ifaceDownEvent);
        createOutages(iface, ifaceDownEvent);
        
        // mark the service as unmanaged
        m_db.setServiceStatus(svc, 'U');
        m_db.setInterfaceStatus(iface, 'U');
        
        // assert that we have an open outage
        assertEquals(1, m_db.countOpenOutagesForService(svc));
        assertEquals(1, m_db.countOutagesForService(svc));
        
        assertEquals(iface.getServices().size(), m_db.countOutagesForInterface(iface));
        assertEquals(iface.getServices().size(), m_db.countOpenOutagesForInterface(iface));
        
        startOutageMgr();
        
        // assert that we have no open outages
        assertEquals(0, m_db.countOpenOutagesForService(svc));
        assertEquals(1, m_db.countOutagesForService(svc));

        assertEquals(0, m_db.countOpenOutagesForInterface(iface));
        assertEquals(iface.getServices().size(), m_db.countOutagesForInterface(iface));

    }
    
    private void testElementDownUp(MockElement element) {
        startOutageMgr();

        String svcLostTime = EventConstants.formatToString(november(20, 2004, 12, 34, 56));
        String svcRegainedTime = EventConstants.formatToString(november(21, 2004, 12, 34, 56));

        Event lostService = element.createDownEvent();
        lostService.setTime(svcLostTime);
        
        Event regainService = element.createUpEvent();
        regainService.setTime(svcRegainedTime);
        
        resetAnticipated();
        anticipateDown(element, lostService);
        
        m_eventMgr.sendEventToListeners(lostService);
        
        sleep(1000);
        
        assertTrue(lostService.hasDbid());
        
        verifyAnticipated();
        
        resetAnticipated();
        anticipateUp(element, regainService);
        
        m_eventMgr.sendEventToListeners(regainService);
        
        sleep(1000);
        
        assertTrue(regainService.hasDbid());
        verifyAnticipated();
    }


    
    private void createOutages(MockElement element, final Event event) {
        MockVisitor outageCreater = new MockVisitorAdapter() {
            public void visitService(MockService svc) {
                m_db.createOutage(svc, event);
            }
        };
        element.visit(outageCreater);
    }
    

    private void resetAnticipated() {
        m_outageAnticipator.reset();
    }
    
    private void anticipateDown(MockElement element, Event lostService) {
        m_outageAnticipator.anticipateOutageOpened(element, lostService);
    }

    private void anticipateUp(MockElement element, Event regainService) {
        m_outageAnticipator.anticipateOutageClosed(element, regainService);
    }

    private void verifyAnticipated() {
        m_eventMgr.finishProcessingEvents();
        assertTrue(m_outageAnticipator.checkAnticipated());
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) {}
    }

    private void checkAllServices(MockElement element, final Event lostService, final Event regainService) {
        class SvcOutageChecker extends MockVisitorAdapter {
            private int svcCount = 0;
            private int outageCount = 0;
            public void visitService(MockService svc) {
                Querier checker = new OutageChecker(svc, lostService, regainService);
                checker.execute(new Integer(svc.getNodeId()), svc.getIpAddr(), new Integer(svc.getId()));
                assertEquals("Expected outage for svc "+svc, 1, checker.getCount());
                outageCount += checker.getCount();
                svcCount++;
            }
            public int getServiceCount() { return svcCount; }
            public int getOutageCount() { return outageCount; }
        };
        SvcOutageChecker svcChecker = new SvcOutageChecker();
        element.visit(svcChecker);
        assertTrue(svcChecker.getServiceCount() > 0);
        assertEquals(svcChecker.getServiceCount(), svcChecker.getOutageCount());
    }

    private Date november(int day, int year, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.NOVEMBER, day, hour, minute, second);
        return cal.getTime();
    }

}
