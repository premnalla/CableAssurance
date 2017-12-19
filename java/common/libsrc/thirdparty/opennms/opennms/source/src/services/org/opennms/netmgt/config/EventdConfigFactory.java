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

package org.opennms.netmgt.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.ConfigFileConstants;
import org.opennms.netmgt.config.eventd.EventdConfiguration;

/**
 * This is the singleton class used to load the configuration for the OpenNMS
 * Eventd from the eventd-configuration xml file.
 * 
 * <strong>Note: </strong>Users of this class should make sure the
 * <em>init()</em> is called before calling any other method to ensure the
 * config is loaded before accessing other convenience methods.
 * 
 * @author <a href="mailto:sowmya@opennms.org">Sowmya Nataraj </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
public final class EventdConfigFactory {
    /**
     * The singleton instance of this factory
     */
    private static EventdConfigFactory m_singleton = null;

    /**
     * The config class loaded from the config file
     */
    private EventdConfiguration m_config;

    /**
     * This member is set to true if the configuration file has been loaded.
     */
    private static boolean m_loaded = false;

    /**
     * Private constructor
     * 
     * @exception java.io.IOException
     *                Thrown if the specified config file cannot be read
     * @exception org.exolab.castor.xml.MarshalException
     *                Thrown if the file does not conform to the schema.
     * @exception org.exolab.castor.xml.ValidationException
     *                Thrown if the contents do not match the required schema.
     */
    private EventdConfigFactory(String configFile) throws IOException, MarshalException, ValidationException {
        InputStream cfgIn = new FileInputStream(configFile);

        m_config = (EventdConfiguration) Unmarshaller.unmarshal(EventdConfiguration.class, new InputStreamReader(cfgIn));
        cfgIn.close();

    }

    /**
     * Load the config from the default config file and create the singleton
     * instance of this factory.
     * 
     * @exception java.io.IOException
     *                Thrown if the specified config file cannot be read
     * @exception org.exolab.castor.xml.MarshalException
     *                Thrown if the file does not conform to the schema.
     * @exception org.exolab.castor.xml.ValidationException
     *                Thrown if the contents do not match the required schema.
     */
    public static synchronized void init() throws IOException, MarshalException, ValidationException {
        if (m_loaded) {
            // init already called - return
            // to reload, reload() will need to be called
            return;
        }

        File cfgFile = ConfigFileConstants.getFile(ConfigFileConstants.EVENTD_CONFIG_FILE_NAME);

        m_singleton = new EventdConfigFactory(cfgFile.getPath());

        m_loaded = true;
    }

    /**
     * Reload the config from the default config file
     * 
     * @exception java.io.IOException
     *                Thrown if the specified config file cannot be read/loaded
     * @exception org.exolab.castor.xml.MarshalException
     *                Thrown if the file does not conform to the schema.
     * @exception org.exolab.castor.xml.ValidationException
     *                Thrown if the contents do not match the required schema.
     */
    public static synchronized void reload() throws IOException, MarshalException, ValidationException {
        m_singleton = null;
        m_loaded = false;

        init();
    }

    /**
     * Return the singleton instance of this factory.
     * 
     * @return The current factory instance.
     * 
     * @throws java.lang.IllegalStateException
     *             Thrown if the factory has not yet been initialized.
     */
    public static synchronized EventdConfigFactory getInstance() {
        if (!m_loaded)
            throw new IllegalStateException("The factory has not been initialized");

        return m_singleton;
    }

    /**
     * Return the port on which eventd listens for TCP connections.
     * 
     * @return the port on which eventd listens for TCP connections
     */
    public synchronized int getTCPPort() {
        return m_config.getTCPPort();
    }

    /**
     * Return the port on which eventd listens for UDP data.
     * 
     * @return the port on which eventd listens for UDP data
     */
    public synchronized int getUDPPort() {
        return m_config.getUDPPort();
    }

    /**
     * Return the number of event receivers to be started.
     * 
     * @return the number of event receivers to be started
     */
    public synchronized int getReceivers() {
        return m_config.getReceivers();
    }

    /**
     * Return string indicating if timeout is to be set on the socket.
     * 
     * @return string indicating if timeout is to be set on the socket
     */
    public synchronized String getSocketSoTimeoutRequired() {
        return m_config.getSocketSoTimeoutRequired();
    }

    /**
     * Return timeout to be set on the socket.
     * 
     * @return timeout is to be set on the socket
     */
    public synchronized int getSocketSoTimeoutPeriod() {
        return m_config.getSocketSoTimeoutPeriod();
    }

    /**
     * Return flag indicating if timeout to be set on the socket is specified.
     * 
     * @return flag indicating if timeout to be set on the socket is specified <
     */
    public synchronized boolean hasSocketSoTimeoutPeriod() {
        return m_config.hasSocketSoTimeoutPeriod();
    }

    /**
     * Return the SQL statemet to get the next event ID.
     * 
     * @return the SQL statemet to get the next event ID
     */
    public synchronized String getGetNextEventID() {
        return m_config.getGetNextEventID();
    }
}
