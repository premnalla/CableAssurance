/*
 * Copyright 2002,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.mbeans;


import java.util.ArrayList;
import java.util.Iterator;

import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.commons.modeler.BaseModelMBean;
import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;
import org.apache.tomcat.util.compat.JdkCompat;

/**
 * <p>A <strong>ModelMBean</strong> implementation for the
 * <code>org.apache.catalina.User</code> component.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2007/02/23 16:51:00 $
 */

public class UserMBean extends BaseModelMBean {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a <code>ModelMBean</code> with default
     * <code>ModelMBeanInfo</code> information.
     *
     * @exception MBeanException if the initializer of an object
     *  throws an exception
     * @exception RuntimeOperationsException if an IllegalArgumentException
     *  occurs
     */
    public UserMBean()
        throws MBeanException, RuntimeOperationsException {

        super();

    }


    // ----------------------------------------------------- Class Variables


    /**
     * JDK compatibility support
     */
    private static final JdkCompat jdkCompat = JdkCompat.getJdkCompat();


    // ----------------------------------------------------- Instance Variables


    /**
     * The configuration information registry for our managed beans.
     */
    protected Registry registry = MBeanUtils.createRegistry();


    /**
     * The <code>MBeanServer</code> in which we are registered.
     */
    protected MBeanServer mserver = MBeanUtils.createServer();


    /**
     * The <code>ManagedBean</code> information describing this MBean.
     */
    protected ManagedBean managed =
        registry.findManagedBean("User");


    // ------------------------------------------------------------- Attributes


    /**
     * Return the MBean Names of all groups this user is a member of.
     */
    public String[] getGroups() {

        User user = (User) this.resource;
        ArrayList results = new ArrayList();
        Iterator groups = user.getGroups();
        while (groups.hasNext()) {
            Group group = null;
            try {
                group = (Group) groups.next();
                ObjectName oname =
                    MBeanUtils.createObjectName(managed.getDomain(), group);
                results.add(oname.toString());
            } catch (MalformedObjectNameException e) {
                IllegalArgumentException iae = new IllegalArgumentException
                    ("Cannot create object name for group " + group);
                jdkCompat.chainException(iae, e);
                throw iae;
            }
        }
        return ((String[]) results.toArray(new String[results.size()]));

    }


    /**
     * Return the MBean Names of all roles assigned to this user.
     */
    public String[] getRoles() {

        User user = (User) this.resource;
        ArrayList results = new ArrayList();
        Iterator roles = user.getRoles();
        while (roles.hasNext()) {
            Role role = null;
            try {
                role = (Role) roles.next();
                ObjectName oname =
                    MBeanUtils.createObjectName(managed.getDomain(), role);
                results.add(oname.toString());
            } catch (MalformedObjectNameException e) {
                IllegalArgumentException iae = new IllegalArgumentException
                    ("Cannot create object name for role " + role);
                jdkCompat.chainException(iae, e);
                throw iae;
            }
        }
        return ((String[]) results.toArray(new String[results.size()]));

    }


    // ------------------------------------------------------------- Operations


    /**
     * Add a new {@link Group} to those this user belongs to.
     *
     * @param groupname Group name of the new group
     */
    public void addGroup(String groupname) {

        User user = (User) this.resource;
        if (user == null) {
            return;
        }
        Group group = user.getUserDatabase().findGroup(groupname);
        if (group == null) {
            throw new IllegalArgumentException
                ("Invalid group name '" + groupname + "'");
        }
        user.addGroup(group);

    }


    /**
     * Add a new {@link Role} to those this user belongs to.
     *
     * @param rolename Role name of the new role
     */
    public void addRole(String rolename) {

        User user = (User) this.resource;
        if (user == null) {
            return;
        }
        Role role = user.getUserDatabase().findRole(rolename);
        if (role == null) {
            throw new IllegalArgumentException
                ("Invalid role name '" + rolename + "'");
        }
        user.addRole(role);

    }


    /**
     * Remove a {@link Group} from those this user belongs to.
     *
     * @param groupname Group name of the old group
     */
    public void removeGroup(String groupname) {

        User user = (User) this.resource;
        if (user == null) {
            return;
        }
        Group group = user.getUserDatabase().findGroup(groupname);
        if (group == null) {
            throw new IllegalArgumentException
                ("Invalid group name '" + groupname + "'");
        }
        user.removeGroup(group);

    }


    /**
     * Remove a {@link Role} from those this user belongs to.
     *
     * @param rolename Role name of the old role
     */
    public void removeRole(String rolename) {

        User user = (User) this.resource;
        if (user == null) {
            return;
        }
        Role role = user.getUserDatabase().findRole(rolename);
        if (role == null) {
            throw new IllegalArgumentException
                ("Invalid role name '" + rolename + "'");
        }
        user.removeRole(role);

    }


}
