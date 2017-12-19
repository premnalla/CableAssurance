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

package org.opennms.web.notification.parsers;

import java.util.Collection;
import java.util.List;

import org.opennms.web.notification.bobject.GroupTarget;
import org.opennms.web.notification.bobject.NotifTarget;
import org.opennms.web.notification.bobject.Notification;
import org.opennms.web.notification.bobject.NotificationTarget;
import org.opennms.web.notification.bobject.UserTarget;
import org.opennms.web.parsers.XMLHeader;
import org.opennms.web.parsers.XMLWriteException;
import org.opennms.web.parsers.XMLWriter;
import org.w3c.dom.Element;

/**
 * This class loads and saves information from the notifications.xml file
 * 
 * @author <A HREF="mailto:jason@opennms.org">Jason Johns </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 * @version 1.1.1.1
 * 
 */
public class NotificationsWriter extends XMLWriter {
    /**
     */
    private String status;

    /**
     * Default constructor, intializes the member variables
     */
    public NotificationsWriter(String fileName) throws XMLWriteException {
        super(fileName);

        // set the default notifications status
        status = "on";
    }

    /**
     */
    public void setStatus(String aStatus) {
        status = aStatus;
    }

    /**
     * This method creates a new DOM tree document that represents the data in
     * the collection. This document will be serialized to a file to save the
     * configuration.
     * 
     * @param notifications
     *            the information to save.
     * @exception XMLWriteException
     */
    protected void saveDocument(Collection notifications) throws XMLWriteException {
        Element root = m_document.createElement("notifications");
        root.setAttribute("status", status);
        m_document.appendChild(root);

        // write the header
        XMLHeader header = new XMLHeader(getVersion(), m_document);
        root.appendChild(header.getHeaderElement());

        if (notifications.size() > 0) {
            Object notificationsArray[] = notifications.toArray();
            for (int i = 0; i < notificationsArray.length; i++) {
                Notification curNotif = (Notification) notificationsArray[i];

                Element curNotifElement = addEmptyElement(root, "notification");
                curNotifElement.setAttribute("interval", curNotif.getInterval());

                addDataElement(curNotifElement, "name", curNotif.getName());
                addDataElement(curNotifElement, "comments", curNotif.getComments());

                List targets = curNotif.getTargets();

                for (int j = 0; j < targets.size(); j++) {
                    NotificationTarget curTarget = (NotificationTarget) targets.get(j);

                    Element curTargetElement = addEmptyElement(curNotifElement, "target");

                    if (curTarget.getType() == NotificationTarget.TARGET_TYPE_USER) {
                        addDataElement(curTargetElement, "user", ((UserTarget) curTarget).getUserName());
                        addDataElement(curTargetElement, "command", ((UserTarget) curTarget).getCommandName());
                    } else if (curTarget.getType() == NotificationTarget.TARGET_TYPE_NOTIF) {
                        curTargetElement.setAttribute("type", "notif");
                        addDataElement(curTargetElement, "notif", ((NotifTarget) curTarget).getNotifName());
                        String notifInterval = ((NotifTarget) curTarget).getInterval();
                        if (notifInterval != null && !notifInterval.trim().equals("")) {
                            addDataElement(curTargetElement, "interval", notifInterval);
                        }
                    } else if (curTarget.getType() == NotificationTarget.TARGET_TYPE_GROUP) {
                        curTargetElement.setAttribute("type", "group");
                        addDataElement(curTargetElement, "group", ((GroupTarget) curTarget).getGroupName());
                        addDataElement(curTargetElement, "command", ((GroupTarget) curTarget).getCommandName());
                    }
                }
            }
        }

        serializeToFile();
    }
}
