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

package org.opennms.netmgt.outage;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.opennms.core.queue.FifoQueue;
import org.opennms.core.queue.FifoQueueException;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.eventd.EventIpcManager;
import org.opennms.netmgt.eventd.EventListener;
import org.opennms.netmgt.xml.event.Event;

/**
 * OutageMgrEventProcessor is responsible for receiving events from eventd and
 * queing them to the outage writer pool.
 * 
 * @author <a href="mailto:sowmya@opennms.org">Sowmya Nataraj </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
final class OutageMgrEventProcessor implements EventListener {
    /**
     * The location where incoming events of interest are enqueued
     */
    private FifoQueue m_writerQ;
    
    /**
     * The outage manager that created this event processor
     */
    private OutageManager m_outageMgr;

    private boolean m_eventListenerRegistered = false;

    /**
     * Constructor
     * 
     * @param writerQ
     *            The queue where events of interest are added.
     */
    OutageMgrEventProcessor(OutageManager outageMgr, FifoQueue writerQ) {
        m_writerQ = writerQ;
        m_outageMgr = outageMgr;
    }

    /**
     * Create a list of UEIs of interest to the OutageManager and subscribe to
     * eventd
     */
    public void start() {
        
        if (m_eventListenerRegistered) return;
        
        // Create the selector for the ueis this service is interested in
        //
        List ueiList = new ArrayList();

        // nodeLostService
        ueiList.add(EventConstants.NODE_LOST_SERVICE_EVENT_UEI);

        // interfaceDown
        ueiList.add(EventConstants.INTERFACE_DOWN_EVENT_UEI);

        // nodeDown
        ueiList.add(EventConstants.NODE_DOWN_EVENT_UEI);

        // nodeUp
        ueiList.add(EventConstants.NODE_UP_EVENT_UEI);

        // interfaceUp
        ueiList.add(EventConstants.INTERFACE_UP_EVENT_UEI);

        // nodeRegainedService
        ueiList.add(EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI);

        // interfaceReparented
        ueiList.add(EventConstants.INTERFACE_REPARENTED_EVENT_UEI);

        getEventMgr().addEventListener(this, ueiList);
        m_eventListenerRegistered  = true;
    }

    /**
     * @return
     */
    private EventIpcManager getEventMgr() {
        return m_outageMgr.getEventMgr();
    }

    /**
     * Unsubscribe from eventd
     */
    public void close() {
        getEventMgr().removeEventListener(this);
        m_eventListenerRegistered = false;
    }

    /**
     * This method is invoked by the EventIpcManager when a new event is
     * available for processing. Each message is examined for its Universal
     * Event Identifier and the appropriate action is taking based on each UEI.
     * 
     * @param event
     *            The event
     */
    public void onEvent(Event event) {
        if (event == null)
            return;

        Category log = ThreadCategory.getInstance(getClass());
        if (log.isDebugEnabled())
            log.debug("About to start processing recd. event");

        try {
            String uei = event.getUei();
            if (uei == null)
                return;

            m_writerQ.add(new OutageWriter(m_outageMgr, event));

            if (log.isDebugEnabled())
                log.debug("Event " + uei + " added to writer queue");
        } catch (InterruptedException ex) {
            log.error("Failed to process event", ex);
            return;
        } catch (FifoQueueException ex) {
            log.error("Failed to process event", ex);
            return;
        } catch (Throwable t) {
            log.error("Failed to process event", t);
            return;
        }

    }

    /**
     * Return an id for this event listener
     */
    public String getName() {
        return "OutageManager:OutageMgrEventProcessor";
    }
} // end class
