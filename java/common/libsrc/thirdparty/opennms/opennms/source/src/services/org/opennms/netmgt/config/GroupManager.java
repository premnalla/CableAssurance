//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2005 The OpenNMS Group, Inc.  All rights reserved.
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
//   OpenNMS Licensing       <license@opennms.org>
//   http://www.opennms.org/
//   http://www.opennms.com/
//
// Tab Size = 8

package org.opennms.netmgt.config;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.groups.Group;
import org.opennms.netmgt.config.groups.Groupinfo;
import org.opennms.netmgt.config.groups.Groups;
import org.opennms.netmgt.config.groups.Header;
import org.opennms.netmgt.config.users.DutySchedule;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;


/**
 * @author david
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class GroupManager {

    /**
     * The duty schedules for each group
     */
    protected static HashMap m_dutySchedules;

    /**
     * A mapping of Group object by name
     */
    private Map m_groups;
    private Header m_oldHeader;

    /**
     * @param reader
     * @throws MarshalException
     * @throws ValidationException
     */
    protected synchronized void parseXml(Reader reader) throws MarshalException, ValidationException {
        Groupinfo groupinfo = (Groupinfo) Unmarshaller.unmarshal(Groupinfo.class, reader);
        Groups groups = groupinfo.getGroups();
        m_groups = new HashMap();
        Collection groupList = groups.getGroupCollection();
        m_oldHeader = groupinfo.getHeader();
        Iterator i = groupList.iterator();
        while (i.hasNext()) {
            Group curGroup = (Group) i.next();
            m_groups.put(curGroup.getName(), curGroup);
        }
        buildDutySchedules(m_groups);
    }

    /**
     * Set the groups data
     */
    public void setGroups(Map grp) {
        m_groups = grp;
    }

    /**
     * Get the groups
     */
    public Map getGroups() throws IOException, MarshalException, ValidationException {
    
        updateFromFile();
    
        Map newMap = new HashMap();
    
        Iterator i = m_groups.keySet().iterator();
    
        while (i.hasNext()) {
            String key = (String) i.next();
            newMap.put(key, (Group) m_groups.get(key));
        }
    
        return newMap;
    
    }

    /**
     * @throws ValidationException
     * @throws MarshalException
     * @throws IOException
     * 
     */
    protected abstract void updateFromFile() throws IOException, MarshalException, ValidationException;

    /**
     * Returns a boolean indicating if the group name appears in the xml file
     * 
     * @return true if the group exists in the xml file, false otherwise
     */
    public boolean hasGroup(String groupName) throws IOException, MarshalException, ValidationException {
        updateFromFile();
    
        return m_groups.containsKey(groupName);
    }

    /**
     */
    public List getGroupNames() throws IOException, MarshalException, ValidationException {
        updateFromFile();
    
        List groupNames = new ArrayList();
    
        Iterator i = m_groups.keySet().iterator();
    
        while (i.hasNext()) {
            groupNames.add((String) i.next());
        }
    
        return groupNames;
    }

    /**
     * Get a group using its name
     * 
     * @param name
     *            the name of the group to return
     * @return Group, the group specified by name
     */
    public Group getGroup(String name) throws IOException, MarshalException, ValidationException {
        updateFromFile();
    
        return (Group) m_groups.get(name);
    }

    /**
     */
    public synchronized void saveGroups() throws Exception {
        Header header = m_oldHeader;
    
        header.setCreated(EventConstants.formatToString(new Date()));
    
        Groups groups = new Groups();
        Collection collgroups = (Collection) m_groups.values();
        Iterator iter = collgroups.iterator();
        while (iter != null && iter.hasNext()) {
            Group grp = (Group) iter.next();
            groups.addGroup(grp);
        }
    
        Groupinfo groupinfo = new Groupinfo();
        groupinfo.setGroups(groups);
        groupinfo.setHeader(header);
    
        m_oldHeader = header;
    
        // marshall to a string first, then write the string to the file. This
        // way the original config
        // isn't lost if the xml from the marshall is hosed.
        StringWriter stringWriter = new StringWriter();
        Marshaller.marshal(groupinfo, stringWriter);
        String data = stringWriter.toString();
        saveXml(data);
    }

    /**
     * Builds a mapping between groups and duty schedules. These are used when
     * determining to send a notice to a given group. This helps speed up the decision process.
     * @param groups the map of groups parsed from the xml config file
     */
    private static void buildDutySchedules(Map groups) {
        m_dutySchedules = new HashMap();
        Iterator i = groups.keySet().iterator();
        while(i.hasNext()) {
            String key = (String)i.next();
            Group curGroup = (Group)groups.get(key);
            if (curGroup.getDutyScheduleCount() > 0) {
                List dutyList = new ArrayList();
                Enumeration duties = curGroup.enumerateDutySchedule();
                while(duties.hasMoreElements()) {
                    dutyList.add(new DutySchedule( (String)duties.nextElement() ));
                }
                m_dutySchedules.put(key, dutyList);
            }
        }
    }

    /**
     * Determines if a group is on duty at a given time. If a group has no duty schedules
     * listed in the config file, that group is assumed to always be on duty.
     * @param group the group whose duty schedule we want
     * @param time the time to check for a duty schedule
     * @return boolean, true if the group is on duty, false otherwise.
     */
    public boolean isGroupOnDuty(String group, Calendar time) throws IOException, MarshalException, ValidationException {
        updateFromFile();
        //if the group has no duty schedules then it is on duty
        if (!m_dutySchedules.containsKey(group)) {
            return true;
        }
        boolean result = false;
        List dutySchedules = (List)m_dutySchedules.get(group);
        for (int i = 0; i < dutySchedules.size(); i++) {
            DutySchedule curSchedule = (DutySchedule)dutySchedules.get(i);
            result = curSchedule.isInSchedule(time);
            //don't continue if the time is in this schedule
            if (result) {
                break;
            }
        }
        return result;
    }
  
    /**
     * Determines when a group is next on duty. If a group has no duty schedules
     * listed in the config file, that group is assumed to always be on duty.
     * @param group the group whose duty schedule we want
     * @param time the time to check for a duty schedule
     * @return long, the time in millisec until the group is next on duty
     */
    public long groupNextOnDuty(String group, Calendar time) throws IOException, MarshalException, ValidationException {
        Category log = ThreadCategory.getInstance(this.getClass());
        long next = -1;
        updateFromFile();
        //if the group has no duty schedules then it is on duty
        if (!m_dutySchedules.containsKey(group)) {
            return 0;
        }
        List dutySchedules = (List)m_dutySchedules.get(group);
        for (int i = 0; i < dutySchedules.size(); i++) {
            DutySchedule curSchedule = (DutySchedule)dutySchedules.get(i);
            long tempnext =  curSchedule.nextInSchedule(time);
            if( tempnext < next || next == -1 ) {
                if (log.isDebugEnabled()) {
                    log.debug("isGroupOnDuty: On duty in " + tempnext + " millisec from schedule " + i);
                }
                next = tempnext;
            }
        }
        return next;
    } 

    /**
     * @param data
     * @throws IOException
     */
    protected abstract void saveXml(String data) throws IOException;

    /**
     * Adds a new user and overwrites the "groups.xml"
     */
    public synchronized void saveGroup(String name, Group details) throws Exception {
        if (name == null || details == null) {
            throw new Exception("GroupFactory:saveGroup  null");
        } else {
            m_groups.put(name, details);
        }
    
        saveGroups();
    }

    /**
     * Removes the user from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void deleteUser(String name) throws Exception {
        // Check if the user exists
        if (name != null || !name.equals("")) {
            // Remove the user in the group.
            Set grps = (Set) m_groups.keySet();
            Iterator iterator = (Iterator) grps.iterator();
            while (iterator.hasNext()) {
                Group group;
                group = (Group) m_groups.get((String) iterator.next());
                group.removeUser(name);
            }
        } else {
            throw new Exception("GroupFactory:delete Invalid user name:" + name);
        }
        // Saves into "groups.xml" file
        saveGroups();
    }

    /**
     * Removes the group from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void deleteGroup(String name) throws Exception {
        // Check if the group exists
        if (name != null || !name.equals("")) {
            if (m_groups.containsKey(name)) {
                // Remove the group.
                m_groups.remove(name);
    
            } else
                throw new Exception("GroupFactory:delete Group doesnt exist:" + name);
        } else {
            throw new Exception("GroupFactory:delete Invalid user group:" + name);
        }
        // Saves into "groups.xml" file
        saveGroups();
    }

    /**
     * Renames the group from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void renameGroup(String oldName, String newName) throws Exception {
        /*
         * NEED TO REIMPLEMENT THIS // Check if the group exists if(oldName !=
         * null || !oldName.equals("") || newName != "" || oldName != "") {
         * if(groups.containsKey(oldName)) { grp = (Group)groups.get(oldName);
         *  // Remove the group. groups.remove(oldName);
         * grp.setGroupName(newName); groups.put(newName, grp);
         *  // Remove the user in the view. viewFactory.renameGroup(oldName,
         * newName); } else throw new Exception("GroupFactory:rename Group
         * doesnt exist:" + oldName); } else { throw new
         * Exception("GroupFactory:rename Invalid group name:" + oldName ); } //
         * Saves into "groups.xml" file groupWriter.save(groups.values());
         */
    }

    /**
     * When this method is called group name is changed, so also is the
     * groupname belonging to the view. Also overwrites the "groups.xml" file
     */
    public synchronized void renameUser(String oldName, String newName) throws Exception {
        // Get the old data
        if (oldName == null || newName == null || oldName == "" || newName == "") {
            throw new Exception("Group Factory: Rename user.. no value ");
        } else {
            Collection coll = (Collection) m_groups.values();
            Iterator iter = (Iterator) coll.iterator();
            Map map = new HashMap();
    
            while (iter.hasNext()) {
                Group group = (Group) iter.next();
                Enumeration en = group.enumerateUser();
                String name = "";
                while (en.hasMoreElements()) {
                    name = (String) en.nextElement();
                    if (name.equals(oldName)) {
                        group.removeUser(oldName);
                        group.addUser(newName);
                    }
                }
                map.put(group.getName(), group);
            }
            m_groups.clear();
            m_groups = map;
            saveGroups();
        }
    }

}
