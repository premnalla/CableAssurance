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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.opennms.netmgt.eventd.EventIpcManager;
import org.opennms.netmgt.eventd.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Events;
import org.opennms.netmgt.xml.event.Log;

public class MockEventIpcManager implements EventIpcManager {

    static class ListenerKeeper {
        EventListener m_listener;

        Set m_ueiList;

        ListenerKeeper(EventListener listener, Set ueiList) {
            m_listener = listener;
            m_ueiList = ueiList;
        }

        public boolean equals(Object o) {
            if (o instanceof ListenerKeeper) {
                ListenerKeeper keeper = (ListenerKeeper) o;
                return m_listener.equals(keeper.m_listener) && (m_ueiList == null ? keeper.m_ueiList == null : m_ueiList.equals(keeper.m_ueiList));
            }
            return false;
        }

        private boolean eventMatches(Event e) {
            if (m_ueiList == null)
                return true;
            return m_ueiList.contains(e.getUei());
        }

        public void sendEventIfAppropriate(Event e) {
            if (eventMatches(e)) {
                m_listener.onEvent(e);
            }
        }
    }

    private EventAnticipator m_anticipator;
    
    private EventWriter m_eventWriter = new EventWriter() {
        public void writeEvent(Event e) {
            
        }
    };

    private List m_listeners = new ArrayList();

    private int m_pendingEvents;

    public MockEventIpcManager() {
        m_anticipator = new EventAnticipator();
    }
    
    public void addEventListener(EventListener listener) {
        m_listeners.add(new ListenerKeeper(listener, null));
    }

    public void addEventListener(EventListener listener, List ueilist) {
        m_listeners.add(new ListenerKeeper(listener, new HashSet(ueilist)));
    }

    public void addEventListener(EventListener listener, String uei) {
        m_listeners.add(new ListenerKeeper(listener, Collections.singleton(uei)));
    }

    public void broadcastNow(Event event) {
        MockUtil.printEvent("Sending", event);
        Iterator it = m_listeners.iterator();
        while (it.hasNext()) {
            ListenerKeeper k = (ListenerKeeper) it.next();
            k.sendEventIfAppropriate(event);
        }
    }
    
    public void setEventWriter(EventWriter eventWriter) {
        m_eventWriter = eventWriter;
    }

    public EventAnticipator getEventAnticipator() {
        return m_anticipator;
    }
    
    public void setEventAnticipator(EventAnticipator anticipator) {
        m_anticipator = anticipator;
    }

    public void removeEventListener(EventListener listener) {
        m_listeners.remove(new ListenerKeeper(listener, null));
    }

    public void removeEventListener(EventListener listener, List ueiList) {
        m_listeners.remove(new ListenerKeeper(listener, new HashSet(ueiList)));
    }

    public void removeEventListener(EventListener listener, String uei) {
        m_listeners.remove(new ListenerKeeper(listener, Collections.singleton(uei)));
    }

    /**
     * @param event
     */
    public void sendEventToListeners(final Event event) {
        m_eventWriter.writeEvent(event);
        broadcastNow(event);
    }

    public synchronized void sendNow(final Event event) {
        m_pendingEvents++;
        MockUtil.println("StartEvent processing: m_pendingEvents = "+m_pendingEvents);
        MockUtil.printEvent("Received", event);
        m_anticipator.eventReceived(event);

        Runnable r = new Runnable() {
            public void run() {
                try {
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                    m_eventWriter.writeEvent(event);
                    broadcastNow(event);
                    m_anticipator.eventProcessed(event);
                } finally {
                    synchronized(MockEventIpcManager.this) {
                        m_pendingEvents--;
                        MockUtil.println("Finished processing event m_pendingEvents = "+m_pendingEvents);
                        MockEventIpcManager.this.notifyAll();
                    }
                }
            }
        };
        
        Thread thread = new Thread(r);
        thread.start();
//        r.run();
    }

    public void sendNow(Log eventLog) {
        Events events = eventLog.getEvents();
        for (int i = 0; i < events.getEventCount(); i++) {
            Event event = events.getEvent(i);
            sendNow(event);
        }
    }

    /**
     * 
     */
    public synchronized void finishProcessingEvents() {
        
        while (m_pendingEvents > 0) {
            MockUtil.println("Waiting for event processing: m_pendingEvents = "+m_pendingEvents);
            try { wait(); } catch (InterruptedException e) {}
        }
    }

}
