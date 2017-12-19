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

package org.opennms.netmgt.rtc.datablock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.opennms.netmgt.config.categories.Category;

/**
 * This class is used to encapsulate a category in the categories xml file.
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS.org </A>
 */
public class RTCCategory extends Category {
    /**
     * The 'effective' rule
     */
    private String m_effectiveRule;

    /**
     * The nodes list - list of node ids
     */
    private List m_nodes;

    /**
     * The default constructor - initializes the values
     */
    public RTCCategory(Category cat, String commonRule) {
        setLabel(cat.getLabel());
        setComment(cat.getComment());
        setRule(cat.getRule());
        setNormal(cat.getNormal());
        setWarning(cat.getWarning());
        setService(cat.getService());

        m_effectiveRule = "(" + commonRule + ") & (" + cat.getRule() + ")";

        m_nodes = Collections.synchronizedList(new ArrayList());
    }

    /**
     * Add to the nodes in this category
     * 
     * @param node
     *            the node to add
     */
    public void addNode(RTCNode node) {
        Long longnodeid = new Long(node.getNodeID());

        if (!m_nodes.contains(longnodeid))
            m_nodes.add(longnodeid);
    }

    /**
     * Add to the nodes in this category
     * 
     * @param nodeid
     *            the nodeid to add
     */
    public void addNode(long nodeid) {
        Long longnodeid = new Long(nodeid);

        if (!m_nodes.contains(longnodeid))
            m_nodes.add(longnodeid);
    }

    /**
     * Delete from the nodes in this category
     * 
     * @param nodeid
     *            the nodeid to delete
     */
    public void deleteNode(long nodeid) {
        Long longnodeid = new Long(nodeid);

        m_nodes.remove(longnodeid);
    }

    /**
     * Returns true if the service is in the services list in this category or
     * if service list is null
     * 
     * @return true if the service is in the services list in this category or
     *         if service list is null
     */
    public boolean containsService(String svcname) {
        if (getServiceCount() <= 0) {
            // service list is null - so include all services
            return true;
        }

        boolean found = false;

        Enumeration en = enumerateService();
        while (en.hasMoreElements()) {
            String svc = (String) en.nextElement();
            if (svc.equals(svcname)) {
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Return the 'effective' category rule
     * 
     * @return the 'effective' category rule
     */
    public String getEffectiveRule() {
        return m_effectiveRule;
    }

    /**
     * Get the node ids in this category
     * 
     * @return the list of node ids in this category
     */
    public List getNodes() {
        return m_nodes;
    }
}
