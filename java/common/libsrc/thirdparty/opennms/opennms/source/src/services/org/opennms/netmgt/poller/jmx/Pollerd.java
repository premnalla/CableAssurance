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

package org.opennms.netmgt.poller.jmx;

import java.io.IOException;

import org.apache.log4j.Category;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.config.DatabaseConnectionFactory;
import org.opennms.netmgt.config.PollOutagesConfigFactory;
import org.opennms.netmgt.config.PollerConfigFactory;
import org.opennms.netmgt.eventd.EventIpcManager;
import org.opennms.netmgt.eventd.EventIpcManagerFactory;
import org.opennms.netmgt.poller.Poller;

public class Pollerd implements PollerdMBean {
    public final static String LOG4J_CATEGORY = "OpenNMS.Pollers";

    public void init() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);


        Category log = ThreadCategory.getInstance();
        try {
            PollerConfigFactory.init();
            PollOutagesConfigFactory.init();
            DatabaseConnectionFactory.init();
        } catch (MarshalException e) {
            log.error("Could not unmarshall configuration", e);
        } catch (ValidationException e) {
            log.error("validation error ", e);
        } catch (IOException e) {
            log.error("IOException: ", e);
        } catch (ClassNotFoundException e) {
            log.error("Unable to locate class ", e);
        }

        org.opennms.netmgt.poller.Poller poller = getPoller();
        poller.setPollerConfig(PollerConfigFactory.getInstance());
        poller.setPollOutagesConfig(PollOutagesConfigFactory.getInstance());
        poller.setDbConnectionFactory(DatabaseConnectionFactory.getInstance());

        EventIpcManagerFactory.init();
        EventIpcManager mgr = EventIpcManagerFactory.getInstance().getManager();
        poller.setEventManager(mgr);
        poller.init();
    }

    public void start() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        getPoller().start();
    }

    public void stop() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        getPoller().stop();
    }

    public int getStatus() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);
        Category log = ThreadCategory.getInstance();

        return getPoller().getStatus();
    }

    public String status() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }

    public String getStatusText() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }

    private Poller getPoller() {
        // Set the category prefix
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        return org.opennms.netmgt.poller.Poller.getInstance();
    }

}
