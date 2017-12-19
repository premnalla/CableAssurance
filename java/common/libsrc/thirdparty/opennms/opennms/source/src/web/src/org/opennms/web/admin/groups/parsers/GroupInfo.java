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

package org.opennms.web.admin.groups.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.opennms.netmgt.config.users.DutySchedule;

/**
 * This is a data class to store the group information from the groups.xml file
 * 
 * @author <A HREF="mailto:jason@opennms.org">Jason Johns </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 * @version 1.1.1.1
 * @deprecated Use the Group class instead
 */
public class GroupInfo {
    /**
     * The name of the group
     */
    private String m_groupName;

    /**
     * The comments for the group
     */
    private String m_groupComments;

    /**
     * The list of users in the group
     */
    private List m_users;

    /**
     * The list of duty schedules in the group
     */
    private List m_dutySchedules;

    /**
     * Default constructor, intializes the users list
     */
    public GroupInfo() {
        m_groupName = "";
        m_groupComments = "";
        m_users = new ArrayList();
        m_dutySchedules = new Vector();
    }

    /**
     * Sets the group name
     * 
     * @param aName
     *            the name of the group
     */
    public void setGroupName(String aName) {
        m_groupName = aName;
    }

    /**
     * Returns the group name
     * 
     * @return the name of the group
     */
    public String getGroupName() {
        return m_groupName;
    }

    /**
     * Sets the comments for the group
     * 
     * @param someComments
     *            the comments for the group
     */
    public void setGroupComments(String someComments) {
        m_groupComments = someComments;
    }

    /**
     * Returns the comments for the group
     * 
     * @return the comments for the group
     */
    public String getGroupComments() {
        return m_groupComments;
    }

    /**
     * Adds a username to the list of users
     * 
     * @param aUser
     *            a new username
     */
    public void addUser(String aUser) {
        m_users.add(aUser);
    }

    /**
     * Removes a username from the list of users
     * 
     * @param aUser
     *            the user to remove
     */
    public void removeUser(String aUser) {
        m_users.remove(aUser);
    }

    /**
     * Returns the list of users
     * 
     * @return the list of users
     */
    public List getUsers() {
        return m_users;
    }

    /**
     * Returns a count of the users in the list
     * 
     * @return how many users in this group
     */
    public int getUserCount() {
        return m_users.size();
    }

    /**
     * This method adds a duty schedule
     * 
     * @param aSchedule
     *            a new duty schedule to associate with a group
     */
    public void addGroupDutySchedule(DutySchedule aSchedule) {
        m_dutySchedules.add(aSchedule);
    }

    /**
     * This method sets a full list of duty schedules for a group
     * 
     * @param someSchedules
     *            a list of DutySchedule objects for a group
     */
    public void setDutySchedule(List someSchedules) {
        m_dutySchedules = someSchedules;
    }

    /**
     * Returns the number of DutySchedule object for a group
     * 
     * @return the number of DutySchedules
     */
    public int getDutyScheduleCount() {
        return m_dutySchedules.size();
    }

    /**
     * Returns the full list of DutySchedules
     * 
     * @return the full list of DutySchedules
     */
    public List getDutySchedules() {
        return m_dutySchedules;
    }

    /**
     * Returns a boolean indicating if the user is on duty at the specified
     * time.
     * 
     * @param aTime
     *            a time to see if the user is on duty
     * @return true if the user is on duty, false otherwise
     */
    public boolean isOnDuty(Calendar aTime) {
        boolean result = false;

        // if there is no schedule assume that the user is on duty
        if (m_dutySchedules.size() == 0) {
            return true;
        }

        DutySchedule curSchedule = null;

        for (int i = 0; i < m_dutySchedules.size(); i++) {
            curSchedule = (DutySchedule) m_dutySchedules.get(i);

            result = curSchedule.isInSchedule(aTime);

            // don't continue if the time is in this schedule
            if (result)
                break;
        }

        return result;
    }

    /**
     * Returns a String representation of the group, used primarily for
     * debugging.
     * 
     * @return a string representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("name     = " + m_groupName + "\n");
        buffer.append("comments = " + m_groupComments + "\n");
        buffer.append("users:\n");

        for (int i = 0; i < m_users.size(); i++) {
            buffer.append("\t" + (String) m_users.get(i) + "\n");
        }
 
        for (int i = 0; i < m_dutySchedules.size(); i++) {
            buffer.append(m_dutySchedules.get(i).toString() + "\n");
        }

        return buffer.toString();
    }
}