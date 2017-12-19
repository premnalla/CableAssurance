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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opennms.netmgt.poller.monitors.ServiceMonitor;

/**
 * @author brozow
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
abstract public class MockContainer extends MockElement {

    private Map m_members = new HashMap();

    protected MockContainer(MockContainer parent) {
        super(parent);
    }

    // FIXME: generic poll listener?
    public void addAnticipator(final PollAnticipator trigger) {
        MockVisitor triggerAdder = new MockVisitorAdapter() {
            public void visitService(MockService service) {
                service.addAnticipator(trigger);
            }
        };
        visit(triggerAdder);
    }

    // model
    protected MockElement addMember(MockElement element) {
        m_members.put(element.getKey(), element);
        element.setParent(this);
        return element;
    }

    // model
    protected MockElement getMember(Object key) {
        return (MockElement) m_members.get(key);
    }

    // model
    protected List getMembers() {
        return new ArrayList(m_members.values());
    }

    // stats
    public int getPollCount() {
        class PollCounter extends MockVisitorAdapter {
            int pollCount = 0;

            int getPollCount() {
                return pollCount;
            }

            public void visitService(MockService service) {
                pollCount += service.getPollCount();
            }
        }
        ;
        PollCounter pollCounter = new PollCounter();
        visit(pollCounter);
        return pollCounter.getPollCount();
    }

    // FIXME: where should this live?
    public int getPollStatus() {
        Iterator it = m_members.values().iterator();
        while (it.hasNext()) {
            MockElement element = (MockElement) it.next();
            if (element.getPollStatus() == ServiceMonitor.SERVICE_AVAILABLE)
                return ServiceMonitor.SERVICE_AVAILABLE;
        }
        return ServiceMonitor.SERVICE_UNAVAILABLE;
    }

    // FIXME: make a generic poll listener
    public void removeAnticipator(final PollAnticipator trigger) {
        MockVisitor triggerRemover = new MockVisitorAdapter() {
            public void visitService(MockService service) {
                service.removeAnticipator(trigger);
            }
        };
        visit(triggerRemover);
    }

    // model
    protected void removeMember(MockElement element) {
        m_members.remove(element.getKey());
        element.setParent(null);
    }

    // stats
    public void resetPollCount() {
        class PollCountReset extends MockVisitorAdapter {
            public void visitService(MockService service) {
                service.resetPollCount();
            }
        }
        ;
        PollCountReset pollCounter = new PollCountReset();
        visit(pollCounter);
    }

    // impl
    public void visit(MockVisitor v) {
        super.visit(v);
        v.visitContainer(this);
    }

    // impl
    protected void visitMembers(MockVisitor v) {
        Iterator it = m_members.values().iterator();
        while (it.hasNext()) {
            MockElement element = (MockElement) it.next();
            element.visit(v);
        }
    }
}
