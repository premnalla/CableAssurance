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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class holds the interface information and list of services that belong
 * to the interface.
 * 
 * @author <A HREF="mailto:jacinta@oculan.com">Jacinta Remedios </A>
 * @author <A HREF="http://www.oculan.com">oculan.com </A>
 * 
 */
public class Interface extends StandardNamedObject {
    /**
     * List of services.
     */
    private ArrayList m_services;

    private static class ServiceComparator {
        private String m_svcname;

        private ServiceComparator(String svc) {
            m_svcname = svc;
        }

        static ServiceComparator make(String name) {
            return new ServiceComparator(name);
        }

        static ServiceComparator make(Service svc) {
            return new ServiceComparator(svc.getName());
        }

        public boolean equals(Object o) {
            boolean rc = false;
            if (o != null) {
                if (o == this)
                    rc = true;
                else if (o instanceof Service)
                    rc = m_svcname.equals(((Service) o).getName());
                else if (o instanceof String)
                    rc = m_svcname.equals(o);
            }
            return rc;
        }
    }

    /**
     * Default Constructor.
     */
    public Interface() {
        m_services = new ArrayList();
    }

    /**
     * Constructor that sets the name of interface and sets the services.
     * 
     * @param name
     *            Name of the interface.
     * @param services
     *            Services to be set for this interface.
     */
    public Interface(String name, ArrayList services) {
        setName(name);
        if (services == null)
            m_services = new ArrayList();
        else
            m_services = services;
    }

    /**
     * Constructor that sets the name and adds service. If there is already an
     * interface with name, this method adds a service to this found service.
     * Otherwise adds a new interface with service.
     * 
     * @param name
     *            Name of the interface.
     * @param service
     *            Name of the service to be added
     */
    public Interface(String name, String service) {
        setName(name);
        m_services = new ArrayList();
        Service svc;
        if (service != null) {
            svc = new Service(service);
            m_services.add(svc);
        }
    }

    /**
     * Constructor that sets the name to interface and adds service and outage
     * with lost time. If there is already an interface with name, adds a
     * service depending upon whether it exists or not. Otherwise adds a new
     * interface with service.
     * 
     * @param name
     *            Name of the interface.
     * @param service
     *            Name of the service to be added
     * @param losttime
     *            Lost time
     */
    public Interface(String name, String service, long losttime) {
        setName(name);
        Service svc;
        m_services = new ArrayList();
        if (service != null) {
            svc = new Service(service);
            if (losttime > 0)
                svc.addOutage(losttime);
            m_services.add(svc);
        }
    }

    /**
     * Constructor that sets the name to interface and adds service and outage
     * with lost time and regained time. If there is already an interface with
     * name, adds a service depending upon whether it exists or not. Otherwise
     * adds a new interface with service.
     * 
     * @param name
     *            Name of the interface.
     * @param service
     *            Name of the service to be added
     * @param losttime
     *            Lost time
     * @param regainedtime
     *            Regained Time
     */
    public Interface(String name, String service, long losttime, long regainedtime) {
        setName(name);
        m_services = new ArrayList();
        Service svc;
        if (service != null) {
            svc = new Service(service);
            if (losttime > 0) {
                if (regainedtime > 0)
                    svc.addOutage(losttime, regainedtime);
                else
                    svc.addOutage(losttime);
            }
            m_services.add(svc);
        }
    }

    /**
     * Constructor that sets the name.
     * 
     * @param name
     *            Name of the interface.
     */
    public Interface(String name) {
        m_services = new ArrayList();
        setName(name);
    }

    /**
     * Constructor that sets the services.
     * 
     * @param services
     *            Services for this interface to be set.
     */
    public Interface(ArrayList services) {
        if (services == null)
            m_services = new ArrayList();
        else
            m_services = services;
    }

    /**
     * Return the services
     * 
     * @return Services to be set.
     */
    public List getServices() {
        return m_services;
    }

    /**
     * Return the Service object given the service name
     * 
     * @param svcname
     *            The service name to lookup.
     * @return Service with matching service name.
     */
    public Service getService(String svcname) {
        int ndx = m_services.indexOf(ServiceComparator.make(svcname));
        if (ndx != -1) {
            return (Service) m_services.get(ndx);
        }

        return null;
    }

    /**
     * Adds a new service object to this interface
     * 
     * @param service
     *            The service to be add.
     */
    public void addService(Service service) {
        if (service != null)
            m_services.add(service);
    }

    /**
     * Adds a new service to this interface
     * 
     * @param service
     *            The name of the service to add.
     */
    public void addService(String service) {
        int ndx = m_services.indexOf(ServiceComparator.make(service));
        if (ndx != -1) {
            return;
        }

        if (service != null) {
            Service svc = new Service(service);
            m_services.add(svc);
        }
    }

    /**
     * Adds a new service to this interface.
     * 
     * @param service
     *            Service name to be added
     * @param losttime
     *            Outage with lost time to be added to service
     */
    public void addService(String service, long losttime) {
        int ndx = m_services.indexOf(ServiceComparator.make(service));
        if (ndx != -1) {
            Service svc = (Service) m_services.get(ndx);
            if (svc != null) {
                if (losttime > 0)
                    svc.addOutage(losttime);
            }
            return;
        }

        if (service != null) {
            Service svc = new Service(service);
            if (losttime > 0)
                svc.addOutage(losttime);
            m_services.add(svc);
        }
    }

    /**
     * Adds a service to this interface with outage having lost time and
     * regained time.
     * 
     * @param service
     *            Service name
     * @param losttime
     *            Lost time
     * @param regainedtime
     *            Regained Time
     */
    public void addService(String service, long losttime, long regainedtime) {
        if (service == null)
            return;

        int ndx = m_services.indexOf(ServiceComparator.make(service));
        if (ndx != -1) {
            Service svc = (Service) m_services.get(ndx);
            if (svc != null) {
                if (losttime > 0) {
                    if (regainedtime > 0)
                        svc.addOutage(losttime, regainedtime);
                    else
                        svc.addOutage(losttime);
                }
            }
            return;
        }

        Service svc = new Service(service);
        if (losttime > 0) {
            if (regainedtime > 0)
                svc.addOutage(losttime, regainedtime);
            else
                svc.addOutage(losttime);
        }
        m_services.add(svc);
    }

    /**
     * Returns the down time for this interface.
     * 
     * @param currentTime
     *            End of rolling window
     * @param rollingWindow
     *            Rolling Window
     * @return the down time for this interface.
     */
    public long getDownTime(long currentTime, long rollingWindow) {
        long outageTime = 0;
        if (m_services != null && m_services.size() > 0) {
            Iterator svcIter = m_services.listIterator();
            while (svcIter.hasNext()) {
                Service service = (Service) svcIter.next();
                long down = service.getDownTime(currentTime, rollingWindow);
                if (down > 0)
                    outageTime += down;
            }
        }
        return outageTime;
    }

    /**
     * Returns the number of services that this node/interface has.
     */
    public int getServiceCount() {
        if (m_services != null && m_services.size() > 0)
            return m_services.size();
        return -1;
    }

    /**
     * Returns the number of services affected.
     */
    public int getServiceAffectCount() {
        int count = 0;
        if (m_services != null && m_services.size() > 0) {
            ListIterator lstIter = m_services.listIterator();
            while (lstIter.hasNext()) {
                Service service = (Service) lstIter.next();
                if (service.getOutages().size() > 0)
                    count++;
            }
        }
        return count;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof String)
                return ((String) obj).equals(getName());
            else if (obj instanceof Interface)
                return obj == this;
        }
        return false;
    }
}
