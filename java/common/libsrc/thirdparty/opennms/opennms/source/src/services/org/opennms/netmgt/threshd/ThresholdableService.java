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

package org.opennms.netmgt.threshd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.PollOutagesConfigFactory;
import org.opennms.netmgt.config.threshd.Parameter;
import org.opennms.netmgt.config.ThreshdConfigFactory;
import org.opennms.netmgt.config.threshd.Service;
import org.opennms.netmgt.poller.monitors.IPv4NetworkInterface;
import org.opennms.netmgt.scheduler.ReadyRunnable;
import org.opennms.netmgt.scheduler.Scheduler;
import org.opennms.netmgt.utils.EventProxy;
import org.opennms.netmgt.xml.event.Event;

/**
 * <P>
 * The ThresholdableService class ...
 * </P>
 * 
 * @author <A HREF="mailto:mike@opennms.org">Mike Davidson </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 */
final class ThresholdableService extends IPv4NetworkInterface implements ReadyRunnable {
    /**
     * Interface's parent node identifier
     */
    private int m_nodeId;

    /**
     * The package information for this interface/service pair
     */
    private org.opennms.netmgt.config.threshd.Package m_package;

    /**
     * The service informaion for this interface/service pair
     */
    private final Service m_service;

    /**
     * Last known/current status
     */
    private int m_status;

    /**
     * The last time a threshold check ocurred
     */
    private long m_lastThresholdCheckTime;

    /**
     * The last time this service was scheduled for threshold checking.
     */
    private long m_lastScheduledThresholdCheckTime;

    /**
     * The proxy used to send events.
     */
    private final EventProxy m_proxy;

    /**
     * The scheduler for threshd
     */
    private final Scheduler m_scheduler;

    /**
     * Service updates
     */
    private ThresholderUpdates m_updates;

    /**
     * 
     */
    private static final boolean ABORT_THRESHOLD_CHECK = true;

    /**
     * The map of thresholding parameters
     */
    private static Map m_properties = new TreeMap();

    private ServiceThresholder m_thresholder;

    /**
     * The key used to lookup the service properties that are passed to the
     * thresholder.
     */
    private final String m_svcPropKey;

    /**
     * The map of service parameters. These parameters are mapped by the
     * composite key <em>(package name, service name)</em>.
     */
    private static Map SVC_PROP_MAP = Collections.synchronizedMap(new TreeMap());

    /**
     * Constructs a new instance of a ThresholdableService object.
     * 
     * @param dbNodeId
     *            The database identifier key for the interfaces' node
     * @param address
     *            InetAddress of the interface to collect from
     * @param svcName
     *            Service name
     * @param pkg
     *            The package containing parms for this collectable service.
     * 
     */
    ThresholdableService(int dbNodeId, InetAddress address, String svcName, org.opennms.netmgt.config.threshd.Package pkg) {
        super(address);
        m_nodeId = dbNodeId;
        m_package = pkg;
        m_status = ServiceThresholder.THRESHOLDING_SUCCEEDED;

        m_proxy = Threshd.getInstance().getEventProxy();
        m_scheduler = Threshd.getInstance().getScheduler();
        m_thresholder = Threshd.getInstance().getServiceThresholder(svcName);
        m_updates = new ThresholderUpdates();

        // Initialize last scheduled threshold check and last threshold
        // check times to current time.
        m_lastScheduledThresholdCheckTime = System.currentTimeMillis();
        m_lastThresholdCheckTime = System.currentTimeMillis();

        // find the service matching the name
        //
        Service svc = null;
        Enumeration esvc = m_package.enumerateService();
        while (esvc.hasMoreElements()) {
            Service s = (Service) esvc.nextElement();
            if (s.getName().equalsIgnoreCase(svcName)) {
                svc = s;
                break;
            }
        }
        if (svc == null)
            throw new RuntimeException("Service name not part of package!");

        // save reference to the service
        m_service = svc;

        // add property list for this service/package combination if
        // it doesn't already exist in the service property map
        //
        m_svcPropKey = m_package.getName() + "." + m_service.getName();
        synchronized (SVC_PROP_MAP) {
            if (!SVC_PROP_MAP.containsKey(m_svcPropKey)) {
                Map m = Collections.synchronizedMap(new TreeMap());
                Enumeration ep = m_service.enumerateParameter();
                while (ep.hasMoreElements()) {
                    Parameter p = (Parameter) ep.nextElement();
                    m.put(p.getKey(), p.getValue());
                }

                // Add configured service 'interval' attribute as
                // a property as well. Needed by the ServiceThresholder
                // check() method in order to generate the
                // correct rrdtool fetch command.
                m.put("interval", Integer.toString((int) m_service.getInterval()));

                SVC_PROP_MAP.put(m_svcPropKey, m);
            }
        }
    }

    /**
     * Returns node identifier
     */
    public int getNodeId() {
        return m_nodeId;
    }

    /**
     * Set node identifier
     */
    public void setNodeId(int nodeId) {
        m_nodeId = nodeId;
    }

    /**
     * Returns the service name
     */
    public String getServiceName() {
        return m_service.getName();
    }

    /**
     * Returns the service name
     */
    public String getPackageName() {
        return m_package.getName();
    }

    /**
    * Uses the existing package name to try and re-obtain the package from the threshd config factory.
    * Should be called when the threshd config has been reloaded.
    */
    public void refreshPackage() {
	org.opennms.netmgt.config.threshd.Package refreshedPackage=ThreshdConfigFactory.getInstance().getPackage(this.getPackageName());
	if(refreshedPackage!=null) {
		this.m_package=refreshedPackage;
	}
    }

    /**
     * Returns updates object
     */
    public ThresholderUpdates getThresholderUpdates() {
        return m_updates;
    }

    /**
     * This method is used to evaluate the status of this interface and service
     * pair. If it is time to run the threshold check again then a value of true
     * is returned. If the interface is not ready then a value of false is
     * returned.
     */
    public boolean isReady() {
        boolean ready = false;

        if (!Threshd.getInstance().isSchedulingCompleted())
            return false;

        if (m_service.getInterval() < 1) {
            ready = true;
        } else {
            ready = ((m_service.getInterval() - (System.currentTimeMillis() - m_lastScheduledThresholdCheckTime)) < 1);
        }

        return ready;
    }

    /**
     * Returns the service's configured thresholding interval.
     */
    public long getInterval() {
        return m_service.getInterval();
    }

    /**
     * Generate event and send it to eventd via the event proxy.
     * 
     * uei Universal event identifier of event to generate.
     */
    private void sendEvent(String uei) {
        Category log = ThreadCategory.getInstance(getClass());
        Event event = new Event();
        event.setUei(uei);
        event.setNodeid((long) m_nodeId);
        event.setInterface(m_address.getHostAddress());
        event.setService("SNMP");
        event.setSource("OpenNMS.Threshd");
        try {
            event.setHost(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            event.setHost("unresolved.host");
        }

        event.setTime(EventConstants.formatToString(new java.util.Date()));

        // Send the event
        //
        try {
            m_proxy.send(event);
        } catch (Exception ex) {
            log.error("Failed to send the event " + uei + " for interface " + m_address.getHostAddress(), ex);
        }

        if (log.isDebugEnabled())
            log.debug("sendEvent: Sent event " + uei + " for " + m_nodeId + "/" + m_address.getHostAddress() + "/" + m_service.getName());
    }

    /**
     * This is the main method of the class. An instance is normally enqueued on
     * the scheduler which checks its <code>isReady</code> method to determine
     * execution. If the instance is ready for execution then it is started with
     * it's own thread context to execute the query. The last step in the method
     * before it exits is to reschedule the interface.
     * 
     */
    public void run() {
        Category log = ThreadCategory.getInstance(getClass());

        // Process any oustanding updates.
        //
        if (processUpdates() == ABORT_THRESHOLD_CHECK)
            return;

        // Update last scheduled poll time
        m_lastScheduledThresholdCheckTime = System.currentTimeMillis();

        // Check scheduled outages to see if any apply indicating
        // that threshold checking should be skipped
        //
        if (scheduledOutage()) {
            // Outage applied...reschedule the service and return
            m_scheduler.schedule(this, m_service.getInterval());
            return;
        }

        // Perform threshold checking
        //
        if (log.isDebugEnabled())
            log.debug("run: starting new threshold check for " + m_address.getHostAddress());

        int status = ServiceThresholder.THRESHOLDING_FAILED;
        Map propertiesMap = (Map) SVC_PROP_MAP.get(m_svcPropKey);
        try {
            status = m_thresholder.check(this, m_proxy, propertiesMap);
        } catch (Throwable t) {
            log.error("run: An undeclared throwable was caught during SNMP thresholding for interface " + m_address.getHostAddress(), t);
        }

        // Update last threshold check time
        m_lastThresholdCheckTime = System.currentTimeMillis();

        // Any change in status?
        //
        if (status != m_status) {
            // Generate transition events
            if (log.isDebugEnabled())
                log.debug("run: change in thresholding status, generating event.");

            // Send the appropriate event
            //
            switch (status) {
            case ServiceThresholder.THRESHOLDING_SUCCEEDED:
                sendEvent(EventConstants.THRESHOLDING_SUCCEEDED_EVENT_UEI);
                break;

            case ServiceThresholder.THRESHOLDING_FAILED:
                sendEvent(EventConstants.THRESHOLDING_FAILED_EVENT_UEI);
                break;

            default:
                break;
            }
        }

        // Set the new status
        m_status = status;

        // Reschedule ourselves
        //
        m_scheduler.schedule(this, this.getInterval());

        return;
    }

    Map getPropertyMap() {
        return (Map) SVC_PROP_MAP.get(m_svcPropKey);
    }

    /**
     * Checks the package information for the thresholdable service and
     * determines if any of the calendar outages associated with the package
     * apply to the current time and the service's interface. If an outage
     * applies true is returned...otherwise false is returned.
     * 
     * @return false if no outage found (indicating thresholding may be
     *         performed) or true if applicable outage is found (indicating
     *         thresholding should be skipped).
     */
    private boolean scheduledOutage() {
        boolean outageFound = false;

        PollOutagesConfigFactory outageFactory = PollOutagesConfigFactory.getInstance();

        // Iterate over the outage names defined in the interface's package.
        // For each outage...if the outage contains a calendar entry which
        // applies to the current time and the outage applies to this
        // interface then break and return true. Otherwise process the
        // next outage.
        // 
        Iterator iter = m_package.getOutageCalendarCollection().iterator();
        while (iter.hasNext()) {
            String outageName = (String) iter.next();

            // Does the outage apply to the current time?
            if (outageFactory.isCurTimeInOutage(outageName)) {
                // Does the outage apply to this interface?
		if ((outageFactory.isNodeIdInOutage((long)m_nodeId, outageName)) ||
			(outageFactory.isInterfaceInOutage(m_address.getHostAddress(), outageName))) {
                    if (ThreadCategory.getInstance(getClass()).isDebugEnabled())
                        ThreadCategory.getInstance(getClass()).debug("scheduledOutage: configured outage '" + outageName + "' applies, interface " + m_address.getHostAddress() + " will not be collected for " + m_service);
                    outageFound = true;
                    break;
                }
            }
        }

        return outageFound;
    }

    /**
     * Process any outstanding updates.
     * 
     * @return true if update indicates that threshold check should be aborted
     *         (for example due to deletion flag being set), false otherwise.
     */
    private boolean processUpdates() {
        Category log = ThreadCategory.getInstance(getClass());

        // All update processing takes place within synchronized block
        // to ensure that no updates are missed.
        //
        synchronized (this) {
            if (!m_updates.hasUpdates())
                return !ABORT_THRESHOLD_CHECK;

            // Update: deletion flag
            //
            if (m_updates.isDeletionFlagSet()) {
                // Deletion flag is set, simply return without polling
                // or rescheduling this collector.
                //
                if (log.isDebugEnabled())
                    log.debug("Collector for  " + m_address.getHostAddress() + " is marked for deletion...skipping thresholding, will not reschedule.");

                return ABORT_THRESHOLD_CHECK;
            }

            // Update: reinitialization flag
            //
            if (m_updates.isReinitializationFlagSet()) {
                // Reinitialization flag is set, call initialize() to
                // reinit the collector for this interface
                //
                if (log.isDebugEnabled())
                    log.debug("ReinitializationFlag set for " + m_address.getHostAddress());

                try {
                    m_thresholder.release(this);
                    m_thresholder.initialize(this, this.getPropertyMap());
                    if (log.isDebugEnabled())
                        log.debug("Completed reinitializing SNMP collector for " + m_address.getHostAddress());
                } catch (RuntimeException rE) {
                    log.warn("Unable to reschedule " + m_address.getHostAddress() + " for " + m_service.getName() + " thresholding, reason: " + rE.getMessage());
                } catch (Throwable t) {
                    log.error("Uncaught exception, failed to reschedule interface " + m_address.getHostAddress() + " for " + m_service.getName() + " thresholding", t);
                }
            }

            // Update: reparenting flag
            //
            if (m_updates.isReparentingFlagSet()) {
                if (log.isDebugEnabled())
                    log.debug("ReparentingFlag set for " + m_address.getHostAddress());

                // Convert new nodeId to integer value
                int newNodeId = -1;
                try {
                    newNodeId = Integer.parseInt(m_updates.getReparentNewNodeId());
                } catch (NumberFormatException nfE) {
                    log.warn("Unable to convert new nodeId value to an int while processing reparenting update: " + m_updates.getReparentNewNodeId());
                }

                // Set this collector's nodeId to the value of the interface's
                // new parent nodeid.
                m_nodeId = newNodeId;

                // We must now reinitialize the thresholder for this interface,
                // in order to update the NodeInfo object to reflect changes
                // to the interface's parent node among other things.
                //
                try {
                    if (log.isDebugEnabled())
                        log.debug("Reinitializing SNMP thresholder for " + m_address.getHostAddress());
                    m_thresholder.release(this);
                    m_thresholder.initialize(this, this.getPropertyMap());
                    if (log.isDebugEnabled())
                        log.debug("Completed reinitializing SNMP thresholder for " + m_address.getHostAddress());
                } catch (RuntimeException rE) {
                    log.warn("Unable to initialize " + m_address.getHostAddress() + " for " + m_service.getName() + " thresholding, reason: " + rE.getMessage());
                } catch (Throwable t) {
                    log.error("Uncaught exception, failed to initialize interface " + m_address.getHostAddress() + " for " + m_service.getName() + " thresholding", t);
                }
            }

            // Updates have been applied. Reset ThresholderUpdates object.
            // .
            m_updates.reset();
        } // end synchronized

        return !ABORT_THRESHOLD_CHECK;
    }
}
