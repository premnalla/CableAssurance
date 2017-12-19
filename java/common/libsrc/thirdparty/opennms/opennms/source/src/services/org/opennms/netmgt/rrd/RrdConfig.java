//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2005 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// Aug 23, 2004: Created this file.
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
//
package org.opennms.netmgt.rrd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.ConfigFileConstants;

/**
 * Provides access to the rrd configuration data.
 */
class RrdConfig {

    private static Properties m_properties = null;

    /**
     * This loads the configuration file.
     * 
     * @return a Properties object representing the configuration properties
     * @throws IOException
     */
    private static synchronized Properties getProperties() throws IOException {
        if (m_properties == null) {
            Properties properties = new Properties(System.getProperties());
            File configFile = ConfigFileConstants.getFile(ConfigFileConstants.RRD_CONFIG_FILE_NAME);
            InputStream in = new FileInputStream(configFile);
            properties.load(in);
            in.close();
            m_properties = properties;
        }
        return m_properties;

    }

    /**
     * Get a string valued property, returning default value if it is not set.
     * 
     * @param name
     *            the property name
     * @param defaultVal
     *            the default value to use if the property is not set
     * @return the value of the property
     */
    public static String getProperty(String name, String defaultVal) {
        Category log = ThreadCategory.getInstance(RrdConfig.class);

        try {
            return getProperties().getProperty(name, defaultVal);
        } catch (IOException e) {
            log.error("Unable to read property " + name + " returning defaultValue: " + defaultVal, e);
            return defaultVal;
        }

    }

    /**
     * Get a boolean valued property, returning default value if it is not set
     * or is set to an invalid value.
     * 
     * @param name
     *            the property name
     * @param defaultVal
     *            the default value to use if the property is not set
     * @return the value of the property
     */
    public static boolean getProperty(String name, boolean defaultVal) {
        return "true".equalsIgnoreCase(getProperty(name, (defaultVal ? "true" : "false")));
    }

    /**
     * Get a int valued property, returning default value if it is not set or is
     * set to an invalid value.
     * 
     * @param name
     *            the property name
     * @param defaultVal
     *            the default value to use if the property is not set
     * @return the value of the property
     */
    public static int getProperty(String name, int defaultVal) {
        String val = getProperty(name, (String) null);
        if (val != null) {
            try {
                return Integer.decode(val).intValue();
            } catch (NumberFormatException e) {
            }
        }
        return defaultVal;
    }

    /**
     * Get a long valued property, returning default value if it is not set or
     * is set to an invalid value
     * 
     * @param name
     *            the property name
     * @param defaultVal
     *            the default value to use if the property is not set
     * @return the value of the property
     */
    public static long getProperty(String name, long defaultVal) {
        String val = getProperty(name, (String) null);
        if (val != null) {
            try {
                return Long.decode(val).longValue();
            } catch (NumberFormatException e) {
            }
        }
        return defaultVal;
    }

}