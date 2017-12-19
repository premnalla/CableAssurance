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
// 2004 Jan 06: added support for STATUS_SUSPEND and STATUS_RESUME
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

package org.opennms.web.element;

import java.util.HashMap;
import java.util.Map;

public class ElementUtil extends Object {
    /**
     * Do not use directly. Call {@link #getInterfaceStatusMap 
     * getInterfaceStatusMap} instead.
     */
    protected static HashMap interfaceStatusMap;

    /**
     * Do not use directly. Call {@link #getServiceStatusMap 
     * getServiceStatusMap} instead.
     */
    protected static HashMap serviceStatusMap;

    /** Returns the interface status map, initializing a new one if necessary. */
    protected static Map getInterfaceStatusMap() {
        if (interfaceStatusMap == null) {
            synchronized (ElementUtil.class) {
                interfaceStatusMap = new HashMap();

                interfaceStatusMap = new HashMap();
                interfaceStatusMap.put(new Character('M'), "Managed");
                interfaceStatusMap.put(new Character('U'), "Unmanaged");
                interfaceStatusMap.put(new Character('D'), "Deleted");
                interfaceStatusMap.put(new Character('F'), "Forced Unmanaged");
                interfaceStatusMap.put(new Character('N'), "Not Monitored");
            }
        }

        return (interfaceStatusMap);
    }

    /** Return the human-readable name for a interface's status, may be null. */
    public static String getInterfaceStatusString(Interface intf) {
        if (intf == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return getInterfaceStatusString(intf.isManagedChar());
    }

    /**
     * Return the human-readable name for a interface status character, may be
     * null.
     */
    public static String getInterfaceStatusString(char c) {
        Map statusMap = getInterfaceStatusMap();
        return (String) statusMap.get(new Character(c));
    }

    /** Returns the service status map, initializing a new one if necessary. */
    protected static Map getServiceStatusMap() {
        if (serviceStatusMap == null) {
            synchronized (ElementUtil.class) {
                serviceStatusMap = new HashMap();

                serviceStatusMap.put(new Character('A'), "Managed");
                serviceStatusMap.put(new Character('U'), "Unmanaged");
                serviceStatusMap.put(new Character('D'), "Deleted");
                serviceStatusMap.put(new Character('F'), "Forced Unmanaged");
                serviceStatusMap.put(new Character('N'), "Not Monitored");
                serviceStatusMap.put(new Character('R'), "Rescan to Resume");
                serviceStatusMap.put(new Character('S'), "Rescan to Suspend");
            }
        }

        return (serviceStatusMap);
    }

    /** Return the human-readable name for a service's status, may be null. */
    public static String getServiceStatusString(Service svc) {
        if (svc == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return getServiceStatusString(svc.getStatus());
    }

    /**
     * Return the human-readable name for a service status character, may be
     * null.
     */
    public static String getServiceStatusString(char c) {
        Map statusMap = getServiceStatusMap();
        return (String) statusMap.get(new Character(c));
    }

    public static final int DEFAULT_TRUNCATE_THRESHOLD = 28;

    public static String truncateLabel(String label) {
        return truncateLabel(label, DEFAULT_TRUNCATE_THRESHOLD);
    }

    public static String truncateLabel(String label, int truncateThreshold) {
        if (label == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        if (truncateThreshold < 3) {
            throw new IllegalArgumentException("Cannot take a truncate position less than 3.");
        }

        String shortLabel = label;

        if (label.length() > truncateThreshold) {
            shortLabel = label.substring(0, truncateThreshold - 3) + "...";
        }

        return shortLabel;
    }

    /** Private constructor so this class cannot be instantiated. */
    private ElementUtil() {
    }

}
