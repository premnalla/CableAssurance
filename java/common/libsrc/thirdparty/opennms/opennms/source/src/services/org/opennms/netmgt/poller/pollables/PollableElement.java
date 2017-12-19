//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2004-2005 The OpenNMS Group, Inc.  All rights reserved.
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
package org.opennms.netmgt.poller.pollables;

import java.util.Date;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.xml.event.Event;

/**
 * Represents a PollableElement 
 *
 * @author brozow
 */
abstract public class PollableElement {
    
    private PollableContainer m_parent;
    private PollStatus m_status = PollStatus.STATUS_UNKNOWN;
    private boolean m_statusChanged = false;
    private PollEvent m_cause;
    private boolean m_deleted;


    protected PollableElement(PollableContainer parent) {
        m_parent = parent;
    }

    protected PollableContainer getParent() {
        return m_parent;
    }
    
    protected void setParent(PollableContainer newParent) {
        m_parent = newParent;
    }

    public void visit(PollableVisitor v) {
        visitThis(v);
    }
    
    protected void visitThis(PollableVisitor v) {
        v.visitElement(this);
    }
    
    public PollStatus getStatus() {
        return m_status;
    }
    private void setStatus(PollStatus status) {
        m_status = status;
    }
    public boolean isStatusChanged() {
        return m_statusChanged;
    }
    private void setStatusChanged(boolean statusChanged) {
        m_statusChanged = statusChanged;
    }
    public void updateStatus(PollStatus newStatus) {
        if (getStatus() != newStatus) {
            
            ThreadCategory.getInstance(getClass()).info("Changing status of PollableElement "+this+" from "+getStatus()+" to "+newStatus);
            setStatus(newStatus);
            setStatusChanged(true);
        }
    }
    public void resetStatusChanged() {
        setStatusChanged(false);
    }
    public void recalculateStatus() {
        // do nothing for just an element
    }
    
    public abstract PollContext getContext();

    /**
     * @param service
     * @return
     */
    public PollStatus doPoll(PollableElement elem) {
        if (getParent() == null) {
            resetStatusChanged();
            return poll(elem);
        }
        else
            return getParent().doPoll(elem);
    }
    
    public PollableElement getLockRoot() {
        PollableContainer parent = getParent();
        return (parent == null ? this : parent.getLockRoot());
    }
    
    public boolean isTreeLockAvailable() {
        return getLockRoot().isTreeLockAvailable();
    }
    
    public void obtainTreeLock(long timeout) {
        getLockRoot().obtainTreeLock(timeout);
    }
    
    public void releaseTreeLock() {
        getLockRoot().releaseTreeLock();
    }

    public void withTreeLock(Runnable r) {
        withTreeLock(r, 0);
    }
    
    public void withTreeLock(Runnable r, long timeout) {
        try {
            Category log = ThreadCategory.getInstance(getClass());
            PollableElement lock = getLockRoot();
            obtainTreeLock(timeout);
            r.run();
        } finally {
            releaseTreeLock();
        }
    }

    /**
     * 
     */
    abstract public PollStatus poll();

    protected PollStatus poll(PollableElement elem) {
        if (elem != this)
            throw new IllegalArgumentException("Invalid parameter to poll on "+this+": "+elem);
        
        return poll();
    }

    /**
     * @return
     */
    public PollableElement selectPollElement() {
        return this;
    }

    /**
     * @param date
     * @return
     */
    public abstract Event createDownEvent(Date date);

    /**
     * @param date
     * @return
     */
    public abstract Event createUpEvent(Date date);

    /**
     * @param cause TODO
     */
    protected void createOutage(PollEvent cause) {
        setCause(cause);
    }

    /**
     * @param resolution TODO
     * @param e
     */
    protected void resolveOutage(PollEvent resolution) {
        setCause(null);
    }
    
    public boolean hasOpenOutage() {
        return m_cause != null;
    }
    
    public void setCause(PollEvent cause) {
        m_cause = cause;
    }
    
    public PollEvent getCause() {
        return m_cause;
    }

    /**
     * @param date
     * 
     */
    public void processStatusChange(Date date) {
        if (getStatus().isDown() && isStatusChanged()) {
            processGoingDown(date);
        } else if (getStatus().isUp() && isStatusChanged()) {
            processComingUp(date);
        }
    }

    protected void processComingUp(Date date) {
        PollEvent resolution = getContext().sendEvent(createUpEvent(date));
        processResolution(getCause(), resolution);
    }

    protected void processResolution(PollEvent cause, PollEvent resolution) {
        resolveOutage(resolution);
    }

    protected void processGoingDown(Date date) {
        PollEvent cause = getContext().sendEvent(createDownEvent(date));
        processCause(cause);
    }

    protected void processCause(PollEvent cause) {
        if (!hasOpenOutage())
            createOutage(cause);
    }

    protected void resolveAllOutages(PollEvent resolvedCause, PollEvent resolution) {
        if (resolvedCause.equals(getCause()))
            resolveOutage(resolution);
    }
    
    public boolean isDeleted() {
        return m_deleted;
    }
    public void delete() {
        Runnable r = new Runnable() {
            public void run() {
                m_deleted = true;
                if (m_parent != null) {
                    getParent().deleteMember(PollableElement.this);
                    getParent().recalculateStatus();
                }
            }
        };
        withTreeLock(r);
    }

    protected void processLingeringCauses(PollEvent resolvedCause, PollEvent resolution) {
        if (getStatus().isDown() && resolvedCause.equals(getCause())) {
            resolveAllOutages(resolvedCause, resolution);
            processGoingDown(resolution.getDate());
        } else if (getStatus().isUp() && resolvedCause.equals(getCause())) {
            processResolution(resolvedCause, resolution);
        } else if (getStatus().isUp() && !resolvedCause.equals(getCause())) {
            processComingUp(resolution.getDate());
        }
    }
    

}
