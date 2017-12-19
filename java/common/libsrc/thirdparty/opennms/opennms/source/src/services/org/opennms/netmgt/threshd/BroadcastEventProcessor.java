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

package org.opennms.netmgt.threshd;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.config.ThreshdConfigFactory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.eventd.EventIpcManagerFactory;
import org.opennms.netmgt.eventd.EventListener;
import org.opennms.netmgt.scheduler.Scheduler;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.opennms.netmgt.xml.event.Parms;
import org.opennms.netmgt.xml.event.Value;

/**
 * 
 * @author <a href="mailto:mike@opennms.org">Mike Davidson </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
final class BroadcastEventProcessor implements EventListener {
    /**
     * The map of service names to service thresholders.
     */
    private Map m_monitors;

    /**
     * The scheduler assocated with this receiver
     */
    private Scheduler m_scheduler;

    /**
     * List of ThresholdableService objects.
     */
    private List m_thresholdableServices;

    /**
     * This constructor is called to initilize the JMS event receiver. A
     * connection to the message server is opened and this instance is setup as
     * the endpoint for broadcast events. When a new event arrives it is
     * processed and the appropriate action is taken.
     * 
     * @param thresholdableServices
     *            List of all the ThresholdableService objects scheduled for
     *            thresholding.
     * 
     */
    BroadcastEventProcessor(List thresholdableServices) {
        Category log = ThreadCategory.getInstance(getClass());

        // Set the configuration for this event
        // receiver.
        //
        m_scheduler = Threshd.getInstance().getScheduler();
        m_thresholdableServices = thresholdableServices;

        // Create the jms message selector
        installMessageSelector();
    }

    /**
     * Create message selector to set to the subscription
     */
    private void installMessageSelector() {
        // Create the JMS selector for the ueis this service is interested in
        //
        List ueiList = new ArrayList();

        // nodeGainedService
        ueiList.add(EventConstants.NODE_GAINED_SERVICE_EVENT_UEI);

        // interfaceIndexChanged
        // NOTE: No longer interested in this event...if Capsd detects
        // that in interface's index has changed a
        // 'reinitializePrimarySnmpInterface' event is generated.
        // ueiList.add(EventConstants.INTERFACE_INDEX_CHANGED_EVENT_UEI);

        // primarySnmpInterfaceChanged
        ueiList.add(EventConstants.PRIMARY_SNMP_INTERFACE_CHANGED_EVENT_UEI);

        // reinitializePrimarySnmpInterface
        ueiList.add(EventConstants.REINITIALIZE_PRIMARY_SNMP_INTERFACE_EVENT_UEI);

        // interfaceReparented
        ueiList.add(EventConstants.INTERFACE_REPARENTED_EVENT_UEI);

        // nodeDeleted
        ueiList.add(EventConstants.NODE_DELETED_EVENT_UEI);

        // duplicateNodeDeleted
        ueiList.add(EventConstants.DUP_NODE_DELETED_EVENT_UEI);

        // interfaceDeleted
        ueiList.add(EventConstants.INTERFACE_DELETED_EVENT_UEI);

        // serviceDeleted
        ueiList.add(EventConstants.SERVICE_DELETED_EVENT_UEI);

	// scheduled outage configuration change
	ueiList.add(EventConstants.SCHEDOUTAGES_CHANGED_EVENT_UEI);


        EventIpcManagerFactory.getInstance().getManager().addEventListener(this, ueiList);
    }

    /**
     * </p>
     * Closes the current connections to the Java Message Queue if they are
     * still active. This call may be invoked more than once safely and may be
     * invoked during object finalization.
     * </p>
     * 
     */
    synchronized void close() {
        EventIpcManagerFactory.getInstance().getManager().removeEventListener(this);
    }

    /**
     * This method may be invoked by the garbage thresholding. Once invoked it
     * ensures that the <code>close</code> method is called <em>at least</em>
     * once during the cycle of this object.
     * 
     */
    protected void finalize() throws Throwable {
        close(); // ensure it's closed
    }

    public String getName() {
        return "Threshd:BroadcastEventProcessor";
    }

    /**
     * This method is invoked by the JMS topic session when a new event is
     * available for processing. Currently only text based messages are
     * processed by this callback. Each message is examined for its Universal
     * Event Identifier and the appropriate action is taking based on each UEI.
     * 
     * @param event
     *            The event message.
     * 
     */
    public void onEvent(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        // print out the uei
        //
        if (log.isDebugEnabled()) {
            log.debug("received event, uei = " + event.getUei());
        }
	if(event.getUei().equals(EventConstants.SCHEDOUTAGES_CHANGED_EVENT_UEI)) {
		log.warn("Reloading Threshd config factory");
		try {
			ThreshdConfigFactory.reload();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed to reload ThreshdConfigFactory because "+e.getMessage());
		}
		Threshd.getInstance().refreshServicePackages();
	} else if(!event.hasNodeid()) {
	    // For all other events, if the event doesn't have a nodeId it can't be processed.
            log.info("no database node id found, discarding event");
        } else if (event.getUei().equals(EventConstants.NODE_GAINED_SERVICE_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else {
                nodeGainedServiceHandler(event);
            }
        } else if (event.getUei().equals(EventConstants.PRIMARY_SNMP_INTERFACE_CHANGED_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else {
                primarySnmpInterfaceChangedHandler(event);
            }
        } else if (event.getUei().equals(EventConstants.REINITIALIZE_PRIMARY_SNMP_INTERFACE_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else {
                reinitializePrimarySnmpInterfaceHandler(event);
            }
        } else if (event.getUei().equals(EventConstants.INTERFACE_REPARENTED_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else {
                interfaceReparentedHandler(event);
            }
        }
        // NEW NODE OUTAGE EVENTS
        else if (event.getUei().equals(EventConstants.NODE_DELETED_EVENT_UEI) || event.getUei().equals(EventConstants.DUP_NODE_DELETED_EVENT_UEI)) {
            nodeDeletedHandler(event);
        } else if (event.getUei().equals(EventConstants.INTERFACE_DELETED_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else {
                interfaceDeletedHandler(event);
            }
        } else if (event.getUei().equals(EventConstants.SERVICE_DELETED_EVENT_UEI)) {
            // If there is no interface then it cannot be processed
            //
            if (event.getInterface() == null || event.getInterface().length() == 0) {
                log.info("no interface found, discarding event");
            } else if (event.getService() == null || event.getService().length() == 0) {
                // If there is no service then it cannot be processed
                //
                log.info("no service found, discarding event");
            } else {
                serviceDeletedHandler(event);
            }
        }

    } // end onEvent()

    /**
     * Process the event.
     * 
     * This event is generated when a managed node which supports SNMP gains a
     * new interface. In this situation the ThresholdableService object
     * representing the primary SNMP interface of the node must be
     * reinitialized.
     * 
     * The ThresholdableService object associated with the primary SNMP
     * interface for the node will be marked for reinitialization.
     * Reinitializing the ThresholdableService object consists of calling the
     * ServiceThresholder.release() method followed by the
     * ServiceThresholder.initialize() method which will refresh attributes such
     * as the interface key list and number of interfaces (both of which most
     * likely have changed).
     * 
     * Reinitialization will take place the next time the ThresholdableService
     * is popped from an interval queue for thresholding.
     * 
     * If any errors occur scheduling the service no error is returned.
     * 
     * @param event
     *            The event to process.
     */
    private void reinitializePrimarySnmpInterfaceHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        if (event.getInterface() == null) {
            log.error("reinitializePrimarySnmpInterface event is missing an interface.");
            return;
        }

        // Mark the primary SNMP interface for reinitialization in
        // order to update any modified attributes associated with
        // the collectable service..
        //
        // Iterate over the ThresholdableService objects in the
        // updates map and mark any which have the same interface
        // address for reinitialization
        //
        synchronized (m_thresholdableServices) {
            Iterator iter = m_thresholdableServices.iterator();
            while (iter.hasNext()) {
                ThresholdableService tSvc = (ThresholdableService) iter.next();

                InetAddress addr = (InetAddress) tSvc.getAddress();
                if (addr.getHostAddress().equals(event.getInterface())) {
                    synchronized (tSvc) {
                        // Got a match! Retrieve the ThresholderUpdates object
                        // associated
                        // with this ThresholdableService.
                        ThresholderUpdates updates = tSvc.getThresholderUpdates();

                        // Now set the reinitialization flag
                        updates.markForReinitialization();
                        if (log.isDebugEnabled())
                            log.debug("reinitializePrimarySnmpInterfaceHandler: marking " + event.getInterface() + " for reinitialization for service SNMP.");
                    }
                }
            }
        }
    }

    /**
     * Process the event, construct a new ThresholdableService object
     * representing the node/interface combination, and schedule the interface
     * for thresholding.
     * 
     * If any errors occur scheduling the interface no error is returned.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void nodeGainedServiceHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        // Currently only support SNMP data thresholding.
        //
        if (!event.getService().equals("SNMP") && !event.getService().equals("SNMPv1") && !event.getService().equals("SNMPv2"))
            return;

        // Schedule the new service...
        //
        Threshd.getInstance().scheduleInterface((int) event.getNodeid(), event.getInterface(), event.getService(), false);
    }

    /**
     * Process the 'primarySnmpInterfaceChanged' event.
     * 
     * Extract the old and new primary SNMP interface addresses from the event
     * parms. Any ThresholdableService objects located in the collectable
     * services list which match the IP address of the old primary interface and
     * have a service name of "SNMP" are flagged for deletion. This will ensure
     * that the old primary interface is no longer collected against.
     * 
     * Finally the new primary SNMP interface is scheduled. The packages are
     * examined and new ThresholdableService objects are created, initialized
     * and scheduled for thresholding.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void primarySnmpInterfaceChangedHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());
        if (log.isDebugEnabled())
            log.debug("primarySnmpInterfaceChangedHandler:  processing primary SNMP interface changed event...");

        // Extract the old and new primary SNMP interface adddresses from the
        // event parms.
        //
        String oldPrimaryIfAddr = null;
        String newPrimaryIfAddr = null;
        Parms parms = event.getParms();
        if (parms != null) {
            String parmName = null;
            Value parmValue = null;
            String parmContent = null;

            Enumeration parmEnum = parms.enumerateParm();
            while (parmEnum.hasMoreElements()) {
                Parm parm = (Parm) parmEnum.nextElement();
                parmName = parm.getParmName();
                parmValue = parm.getValue();
                if (parmValue == null)
                    continue;
                else
                    parmContent = parmValue.getContent();

                // old primary SNMP interface (optional parameter)
                if (parmName.equals(EventConstants.PARM_OLD_PRIMARY_SNMP_ADDRESS)) {
                    oldPrimaryIfAddr = parmContent;
                }

                // old primary SNMP interface (optional parameter)
                else if (parmName.equals(EventConstants.PARM_NEW_PRIMARY_SNMP_ADDRESS)) {
                    newPrimaryIfAddr = parmContent;
                }
            }
        }

        if (oldPrimaryIfAddr != null) {
            // Mark the service for deletion so that it will not be rescheduled
            // for
            // thresholding.
            //
            // Iterate over the ThresholdableService objects in the service
            // updates map
            // and mark any which have the same interface address as the old
            // primary SNMP interface and a service name of "SNMP" for deletion.
            //
            synchronized (m_thresholdableServices) {
                ThresholdableService tSvc = null;
                ListIterator liter = m_thresholdableServices.listIterator();
                while (liter.hasNext()) {
                    tSvc = (ThresholdableService) liter.next();

                    InetAddress addr = (InetAddress) tSvc.getAddress();
                    if (addr.getHostAddress().equals(oldPrimaryIfAddr)) {
                        synchronized (tSvc) {
                            // Got a match! Retrieve the ThresholderUpdates
                            // object associated
                            // with this ThresholdableService.
                            ThresholderUpdates updates = tSvc.getThresholderUpdates();

                            // Now set the deleted flag
                            updates.markForDeletion();
                            if (log.isDebugEnabled())
                                log.debug("primarySnmpInterfaceChangedHandler: marking " + oldPrimaryIfAddr + " as deleted for service SNMP.");
                        }

                        // Now safe to remove the collectable service from
                        // the collectable services list
                        liter.remove();
                    }
                }
            }
        }

        // Now we can schedule the new service...
        //
        Threshd.getInstance().scheduleInterface((int) event.getNodeid(), event.getInterface(), event.getService(), false);

        if (log.isDebugEnabled())
            log.debug("primarySnmpInterfaceChangedHandler: processing of primarySnmpInterfaceChanged event for nodeid " + event.getNodeid() + " completed.");
    }

    /**
     * This method is responsible for processing 'interfacReparented' events. An
     * 'interfaceReparented' event will have old and new nodeId parms associated
     * with it. All ThresholdableService objects in the service updates map
     * which match the event's interface address and the SNMP service have a
     * reparenting update associated with them. When the scheduler next pops one
     * of these services from an interval queue for thresholding all of the RRDs
     * associated with the old nodeId are moved under the new nodeId and the
     * nodeId of the collectable service is updated to reflect the interface's
     * new parent nodeId.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void interfaceReparentedHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());
        if (log.isDebugEnabled())
            log.debug("interfaceReparentedHandler:  processing interfaceReparented event for " + event.getInterface());

        // Verify that the event has an interface associated with it
        if (event.getInterface() == null)
            return;

        // Extract the old and new nodeId's from the event parms
        String oldNodeIdStr = null;
        String newNodeIdStr = null;
        Parms parms = event.getParms();
        if (parms != null) {
            String parmName = null;
            Value parmValue = null;
            String parmContent = null;

            Enumeration parmEnum = parms.enumerateParm();
            while (parmEnum.hasMoreElements()) {
                Parm parm = (Parm) parmEnum.nextElement();
                parmName = parm.getParmName();
                parmValue = parm.getValue();
                if (parmValue == null)
                    continue;
                else
                    parmContent = parmValue.getContent();

                // old nodeid
                if (parmName.equals(EventConstants.PARM_OLD_NODEID)) {
                    oldNodeIdStr = parmContent;
                }

                // new nodeid
                else if (parmName.equals(EventConstants.PARM_NEW_NODEID)) {
                    newNodeIdStr = parmContent;
                }
            }
        }

        // Only proceed provided we have both an old and a new nodeId
        //
        if (oldNodeIdStr == null || newNodeIdStr == null) {
            log.warn("interfaceReparentedHandler: old and new nodeId parms are required, unable to process.");
            return;
        }

        // Iterate over the ThresholdableService objects in the services
        // list looking for entries which share the same interface
        // address as the reparented interface. Mark any matching objects
        // for reparenting.
        //
        // The next time the service is scheduled for execution it
        // will move all of the RRDs associated
        // with the old nodeId under the new nodeId and update the service's
        // SnmpMonitor.NodeInfo attribute to reflect the new nodeId. All
        // subsequent thresholdings will then be updating the appropriate RRDs.
        //
        boolean isPrimarySnmpInterface = false;
        synchronized (m_thresholdableServices) {
            ThresholdableService tSvc = null;
            Iterator iter = m_thresholdableServices.iterator();
            while (iter.hasNext()) {
                tSvc = (ThresholdableService) iter.next();

                InetAddress addr = (InetAddress) tSvc.getAddress();
                if (addr.getHostAddress().equals(event.getInterface())) {
                    synchronized (tSvc) {
                        // Got a match!
                        if (log.isDebugEnabled())
                            log.debug("interfaceReparentedHandler: got a ThresholdableService match for " + event.getInterface());

                        // Retrieve the ThresholderUpdates object associated
                        // with this ThresholdableService.
                        ThresholderUpdates updates = tSvc.getThresholderUpdates();

                        // Now set the reparenting flag
                        updates.markForReparenting(oldNodeIdStr, newNodeIdStr);
                        if (log.isDebugEnabled())
                            log.debug("interfaceReparentedHandler: marking " + event.getInterface() + " for reparenting for service SNMP.");
                    }
                }
            }
        }

        if (log.isDebugEnabled())
            log.debug("interfaceReparentedHandler: processing of interfaceReparented event for interface " + event.getInterface() + " completed.");
    }

    /**
     * This method is responsible for handling nodeDeleted events.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void nodeDeletedHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        int nodeId = (int) event.getNodeid();

        // Iterate over the collectable service list and mark any entries
        // which match the deleted nodeId for deletion.
        synchronized (m_thresholdableServices) {
            ThresholdableService tSvc = null;
            ListIterator liter = m_thresholdableServices.listIterator();
            while (liter.hasNext()) {
                tSvc = (ThresholdableService) liter.next();

                // Only interested in entries with matching nodeId
                if (!(tSvc.getNodeId() == nodeId))
                    continue;

                synchronized (tSvc) {
                    // Retrieve the ThresholderUpdates object associated
                    // with this ThresholdableService.
                    ThresholderUpdates updates = tSvc.getThresholderUpdates();

                    // Now set the update's deletion flag so the next
                    // time it is selected for execution by the scheduler
                    // the thresholding will be skipped and the service will not
                    // be rescheduled.
                    updates.markForDeletion();
                }

                // Now safe to remove the collectable service from
                // the collectable services list
                liter.remove();
            }
        }

        if (log.isDebugEnabled())
            log.debug("nodeDeletedHandler: processing of nodeDeleted event for nodeid " + nodeId + " completed.");
    }

    /**
     * This method is responsible for handling interfaceDeleted events.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void interfaceDeletedHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        int nodeId = (int) event.getNodeid();
        String ipAddr = event.getInterface();

        // Iterate over the collectable services list and mark any entries
        // which match the deleted nodeId/IP address pair for deletion
        synchronized (m_thresholdableServices) {
            ThresholdableService tSvc = null;
            ListIterator liter = m_thresholdableServices.listIterator();
            while (liter.hasNext()) {
                tSvc = (ThresholdableService) liter.next();

                // Only interested in entries with matching nodeId and IP
                // address
                InetAddress addr = (InetAddress) tSvc.getAddress();
                if (!(tSvc.getNodeId() == nodeId && addr.getHostName().equals(ipAddr)))
                    continue;

                synchronized (tSvc) {
                    // Retrieve the ThresholderUpdates object associated with
                    // this ThresholdableService if one exists.
                    ThresholderUpdates updates = tSvc.getThresholderUpdates();

                    // Now set the update's deletion flag so the next
                    // time it is selected for execution by the scheduler
                    // the thresholding will be skipped and the service will not
                    // be rescheduled.
                    updates.markForDeletion();
                }

                // Now safe to remove the collectable service from
                // the collectable services list
                liter.remove();
            }
        }

        if (log.isDebugEnabled())
            log.debug("interfaceDeletedHandler: processing of interfaceDeleted event for " + nodeId + "/" + ipAddr + " completed.");
    }

    /**
     * This method is responsible for handling serviceDeleted events.
     * 
     * @param event
     *            The event to process.
     * 
     */
    private void serviceDeletedHandler(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        // Currently only support SNMP data thresholding.
        //
        if (!event.getService().equals("SNMP") && !event.getService().equals("SNMPv1") && !event.getService().equals("SNMPv2"))
            return;

        int nodeId = (int) event.getNodeid();
        String ipAddr = event.getInterface();
        String svcName = event.getService();

        // Iterate over the collectable services list and mark any entries
        // which match the nodeId/ipAddr of the deleted service
        // for deletion.
        synchronized (m_thresholdableServices) {
            ThresholdableService tSvc = null;
            ListIterator liter = m_thresholdableServices.listIterator();
            while (liter.hasNext()) {
                tSvc = (ThresholdableService) liter.next();

                // Only interested in entries with matching nodeId, IP address
                // and service
                InetAddress addr = (InetAddress) tSvc.getAddress();
                if (!(tSvc.getNodeId() == nodeId && addr.getHostName().equals(ipAddr)) && tSvc.getServiceName().equals(svcName))
                    continue;

                synchronized (tSvc) {
                    // Retrieve the ThresholderUpdates object associated with
                    // this ThresholdableService if one exists.
                    ThresholderUpdates updates = tSvc.getThresholderUpdates();

                    // Now set the update's deletion flag so the next
                    // time it is selected for execution by the scheduler
                    // the thresholding will be skipped and the service will not
                    // be rescheduled.
                    updates.markForDeletion();
                }

                // Now safe to remove the collectable service from
                // the collectable services list
                liter.remove();
            }
        }

        if (log.isDebugEnabled())
            log.debug("serviceDeletedHandler: processing of serviceDeleted event for " + nodeId + "/" + ipAddr + "/" + svcName + " completed.");
    }
} // end class
