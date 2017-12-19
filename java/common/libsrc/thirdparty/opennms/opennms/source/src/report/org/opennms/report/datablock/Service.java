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
// Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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

package org.opennms.report.datablock;

//import org.apache.log4j.Category;
//import org.opennms.core.utils.ThreadCategory;

/**
 * This class holds the service information and list of outages for the service.
 * 
 * @author <A HREF="mailto:jacinta@oculan.com">Jacinta Remedios </A>
 * @author <A HREF="http://www.oculan.com">Oculan Corp </A>
 */
public class Service extends StandardNamedObject {
    /**
     * The log4j category used to log debug messsages and statements.
     */
    private static final String LOG4J_CATEGORY = "OpenNMS.Report";

    /**
     * List of outages.
     */
    private OutageSvcTimesList m_outageList;

    /**
     * Percentage Availability during regular hours.
     */
    private double m_percentAvail;

    /**
     * Percentage Availability during business hours.
     */
    private double m_percentBusAvail;

    /**
     * DownTime during regular hours.
     */
    private long m_downTime;

    /**
     * DownTime during business hours.
     */
    private long m_busDownTime;

    /**
     * Total Regular Monitored Time
     */
    private long m_monitoredTime;

    /**
     * Total Monitored Time during business hours.
     */
    private long m_monitoredBusTime;

    /**
     * Default Constructor.
     */
    public Service() {
        m_outageList = new OutageSvcTimesList();
    }

    public Service(String name) {
        setName(name);
        m_outageList = new OutageSvcTimesList();
    }

    /**
     * Constructor that sets the name and the outages.
     * 
     * @param name
     *            Name of the service.
     * @param outages
     *            Outages to be set for this service.
     */
    public Service(String name, OutageSvcTimesList outages) {
        setName(name);
        if (outages != null)
            m_outageList = outages;
        else
            m_outageList = new OutageSvcTimesList();
    }

    /**
     * Constructor that sets the outages.
     * 
     * @param outages
     *            Outages for this service to be set.
     */
    public Service(OutageSvcTimesList outages) {
        if (outages != null)
            m_outageList = outages;
    }

    /**
     * Returns the outage time for this service.
     */
    public double getDownTime() {
        return m_downTime;
    }

    /**
     * Returns the outage time for this service during business hours.
     */
    public long getBusDownTime() {
        return m_busDownTime;
    }

    /**
     * Returns the percentage Availability.
     */
    public double getPercentAvail() {
        return m_percentAvail;
    }

    /**
     * Returns the percentage Availability for this service during business
     * hours.
     */
    public double getBusPercentAvail() {
        return m_percentBusAvail;
    }

    /**
     * Returns the percentage Availability for this service during business
     * hours.
     */
    public long getMonitoredTime() {
        return m_monitoredTime;
    }

    /**
     * Returns the monitored time for this service during business hours.
     */
    public long getMonitoredBusTime() {
        return m_monitoredBusTime;
    }

    /**
     * Return the outages
     * 
     * @return outages Outages to be set.
     */
    public OutageSvcTimesList getOutages() {
        return m_outageList;
    }

    /**
     * Added outage.
     */
    public void addOutage(long lost, long regained) {
        if (m_outageList == null)
            m_outageList = new OutageSvcTimesList();
        m_outageList.addSvcTime(lost, regained);
    }

    /**
     * Added outage.
     */
    public void addOutage(long lost) {
        if (m_outageList == null)
            m_outageList = new OutageSvcTimesList();
        m_outageList.addSvcTime(lost);
    }

    /**
     * Adds a lost time / regained time combination for the node.
     */
    public void addOutage(Outage outage) {
        if (m_outageList == null)
            m_outageList = new OutageSvcTimesList();
        m_outageList.addSvcTime(outage.getLostTime(), outage.getRegainedTime());
    }

    /**
     * Return the outage for this service.
     */
    public long getDownTime(long currentTime, long rollingWindow) {
        if (m_outageList != null)
            return m_outageList.getDownTime(currentTime, rollingWindow);
        return 0;
    }

    /**
     * Returns the Percentage Availability for the service
     * 
     * @param currentTime
     *            Time at the end of the Rolling Window.
     * @param rollingWindow
     *            Actual Monitored Time.
     * @return Percentage Availability
     */
    public double getPercentAvail(long currentTime, long rollingWindow) {
        m_downTime = getDownTime(currentTime, rollingWindow);
        double outage = 1.0 * m_downTime;
        double denom = 1.0 * rollingWindow;
        double percent = 100.0 * (1.0 - outage / denom);
        /*
         * ThreadCategory.setPrefix(LOG4J_CATEGORY); Category log =
         * ThreadCategory.getInstance(this.getClass()); if(log.isInfoEnabled())
         * log.info("Outage -->> " + outage + " DENOM -->> " + denom);
         */
        m_percentAvail = percent;
        m_monitoredTime = rollingWindow;
        return percent;
    }

    /**
     * Equals method.
     */
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof String)
                return ((String) obj).equals(getName());
            else if (obj instanceof Service)
                return obj == this;
        }
        return false;
    }
}
