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
// Modifications:
//
// 2003 Jan 31: Cleaned up some unused imports.
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
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
// Tab Size = 8
//

package org.opennms.netmgt.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Category;
import org.apache.regexp.RE;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.ConfigFileConstants;
import org.opennms.netmgt.config.vulnscand.Excludes;
import org.opennms.netmgt.config.vulnscand.Range;
import org.opennms.netmgt.config.vulnscand.ScanLevel;
import org.opennms.netmgt.config.vulnscand.VulnscandConfiguration;

/**
 * This is the singleton class used to load the configuration for the OpenNMS
 * Vulnscand service from the vulnscand-configuration xml file.
 * 
 * <strong>Note: </strong>Users of this class should make sure the
 * <em>init()</em> is called before calling any other method to ensure the
 * config is loaded before accessing other convenience methods.
 * 
 * @author <a href="mailto:seth@opennms.org">Seth Leger </a>
 * @author <a href="mailto:mike@opennms.org">Mike Davidson </a>
 * @author <a href="mailto:weave@oculan.com">Weave </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
public final class VulnscandConfigFactory {
    /**
     * The string indicating the start of the comments in a line containing the
     * IP address in a file URL
     */
    private final static String COMMENT_STR = " #";

    /**
     * This character at the start of a line indicates a comment line in a URL
     * file
     */
    private final static char COMMENT_CHAR = '#';

    /**
     * This member is set to true if the configuration file has been loaded.
     */
    private static boolean m_loaded = false;

    /**
     * The singleton instance of this factory
     */
    private static VulnscandConfigFactory m_singleton = null;

    /**
     * The config class loaded from the config file
     */
    private static VulnscandConfiguration m_config;

    /**
     * Cached value of the plugin lists for each scan level
     */
    private static String[] m_pluginLists = null;

    /**
     * Cached value of the "safe checks" values for each scan level
     */
    private static boolean[] m_safeChecks = null;

    /**
     * Cached set of the excluded IP addresses
     */
    private static Set m_excludes = null;

    /**
     * Whitespace regex
     */
    private static RE m_space = null;

    /**
     * The SQL statement used to determine if an IP address is already in the
     * ipInterface table and there is known.
     */
    private static final String RETRIEVE_IPADDR_SQL = "SELECT ipaddr FROM ipinterface WHERE ipaddr=? AND ismanaged!='D'";

    /**
     * The SQL statement used to determine if an IP address is already in the
     * ipInterface table and if so what its parent nodeid is.
     */
    private static final String RETRIEVE_IPADDR_NODEID_SQL = "SELECT nodeid FROM ipinterface WHERE ipaddr=? AND ismanaged!='D'";

    /**
     * Constructs a new VulnscandConfigFactory object for access to the
     * Vulnscand configuration information.
     * 
     * @param configFile
     *            The configuration file to load.
     * @exception java.io.IOException
     *                Thrown if the specified config file cannot be read
     * @exception org.exolab.castor.xml.MarshalException
     *                Thrown if the file does not conform to the schema.
     * @exception org.exolab.castor.xml.ValidationException
     *                Thrown if the contents do not match the required schema.
     */
    private VulnscandConfigFactory(String configFile) throws IOException, MarshalException, ValidationException {
        InputStream cfgIn = new FileInputStream(configFile);

        m_config = (VulnscandConfiguration) Unmarshaller.unmarshal(VulnscandConfiguration.class, new InputStreamReader(cfgIn));
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

        File cfgFile = ConfigFileConstants.getFile(ConfigFileConstants.VULNSCAND_CONFIG_FILE_NAME);

        ThreadCategory.getInstance(VulnscandConfigFactory.class).debug("init: config file path: " + cfgFile.getPath());

        m_singleton = new VulnscandConfigFactory(cfgFile.getPath());

        try {
            m_space = new RE("[:space:]+");
        } catch (org.apache.regexp.RESyntaxException ex) {
            ThreadCategory.getInstance(VulnscandConfigFactory.class).error("UNEXPECTED CONDITION: Regex in config factory is incorrect. Check the code.", ex);
        }

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

        // Destroy all cached values
        m_pluginLists = null;
        m_safeChecks = null;
        m_excludes = null;

        init();
    }

    /**
     * Saves the current settings to disk
     */
    public static synchronized void saveCurrent() throws Exception {
        // Marshall to a string first, then write the string to the file. This
        // way the original config
        // isn't lost if the XML from the marshall is hosed.
        StringWriter stringWriter = new StringWriter();
        Marshaller.marshal(m_config, stringWriter);
        if (stringWriter.toString() != null) {
            FileWriter fileWriter = new FileWriter(ConfigFileConstants.getFile(ConfigFileConstants.VULNSCAND_CONFIG_FILE_NAME));
            fileWriter.write(stringWriter.toString());
            fileWriter.flush();
            fileWriter.close();
        }

        reload();
    }

    /**
     * Return the singleton instance of this factory.
     * 
     * @return The current factory instance.
     * 
     * @throws java.lang.IllegalStateException
     *             Thrown if the factory has not yet been initialized.
     */
    public static synchronized VulnscandConfigFactory getInstance() {
        if (!m_loaded)
            throw new IllegalStateException("The factory has not been initialized");

        return m_singleton;
    }

    /**
     * Return the Vulnscand configuration object.
     */
    public static VulnscandConfiguration getConfiguration() {
        return m_config;
    }

    /**
     * This method is used to convert the passed IP address to a
     * <code>long</code> value. The address is converted in network byte order
     * (big endin). This is compatable with the number format of the JVM, and
     * thus the return longs can be compared with other converted IP Addresses
     * to determine inclusion.
     * 
     * @param addr
     *            The IP address to convert.
     * 
     * @return The converted IP address.
     * 
     * @deprecated See
     *             org.opennms.core.utils.InetAddressCollection.toLong(InetAddress
     *             addr)
     * 
     */
    public static long toLong(InetAddress addr) {
        byte[] baddr = addr.getAddress();

        return ((((long) baddr[0] & 0xffL) << 24) | (((long) baddr[1] & 0xffL) << 16) | (((long) baddr[2] & 0xffL) << 8) | ((long) baddr[3] & 0xffL));
    }

    /**
     * <P>
     * Converts a 64-bit unsigned quantity to a IPv4 dotted decimal string
     * address.
     * </P>
     * 
     * @param address
     *            The 64-bit quantity to convert.
     * 
     * @return The dotted decimal IPv4 address string.
     * 
     */
    public static InetAddress toInetAddress(long address) throws UnknownHostException {
        StringBuffer buf = new StringBuffer();
        buf.append((int) ((address >>> 24) & 0xff)).append('.');
        buf.append((int) ((address >>> 16) & 0xff)).append('.');
        buf.append((int) ((address >>> 8) & 0xff)).append('.');
        buf.append((int) (address & 0xff));

        return InetAddress.getByName(buf.toString());
    }

    /**
     * 
     */
    public static boolean isInterfaceInDB(Connection dbConn, InetAddress ifAddress) throws SQLException {
        Category log = ThreadCategory.getInstance(VulnscandConfigFactory.class);

        boolean result = false;

        log.debug("isInterfaceInDB: attempting to lookup interface " + ifAddress.getHostAddress() + " in the database.");

        PreparedStatement s = dbConn.prepareStatement(RETRIEVE_IPADDR_SQL);
        s.setString(1, ifAddress.getHostAddress());

        ResultSet rs = s.executeQuery();
        if (rs.next())
            result = true;

        // Close result set and statement
        //
        rs.close();
        s.close();

        return result;
    }

    /**
     * 
     */
    public static int getInterfaceDbNodeId(Connection dbConn, InetAddress ifAddress) throws SQLException {
        Category log = ThreadCategory.getInstance(VulnscandConfigFactory.class);

        log.debug("getInterfaceDbNodeId: attempting to lookup interface " + ifAddress.getHostAddress() + " in the database.");

        // Set connection as read-only
        //
        // dbConn.setReadOnly(true);

        PreparedStatement s = dbConn.prepareStatement(RETRIEVE_IPADDR_NODEID_SQL);
        s.setString(1, ifAddress.getHostAddress());

        ResultSet rs = s.executeQuery();
        int nodeid = -1;
        if (rs.next()) {
            nodeid = rs.getInt(1);
        }

        // Close result set and statement
        //
        rs.close();
        s.close();

        return nodeid;
    }

    private static ScanLevel getScanLevel(int level) {
        Enumeration scanLevels = m_config.enumerateScanLevel();

        while (scanLevels.hasMoreElements()) {
            ScanLevel scanLevel = (ScanLevel) scanLevels.nextElement();
            if (level == scanLevel.getLevel()) {
                return scanLevel;
            }
        }
        throw new ArrayIndexOutOfBoundsException("No scan level with that index could be located in the configuration file, index = " + level);
    }

    public void addSpecific(int level, InetAddress specific) {
        addSpecific(getScanLevel(level), specific);
    }

    public void addSpecific(ScanLevel level, InetAddress specific) {
        level.addSpecific(specific.getHostAddress());
    }

    public void addRange(int level, InetAddress begin, InetAddress end) {
        addRange(getScanLevel(level), begin, end);
    }

    public void addRange(ScanLevel level, InetAddress begin, InetAddress end) {
        Range addMe = new Range();
        addMe.setBegin(begin.getHostAddress());
        addMe.setEnd(end.getHostAddress());

        level.addRange(addMe);
    }

    public void removeSpecific(int level, InetAddress specific) {
        removeSpecific(getScanLevel(level), specific);
    }

    public void removeSpecific(ScanLevel level, InetAddress specific) {
        level.removeSpecific(specific.getHostAddress());
    }

    public void removeRange(int level, InetAddress begin, InetAddress end) {
        removeRange(getScanLevel(level), begin, end);
    }

    public void removeRange(ScanLevel level, InetAddress begin, InetAddress end) {
        Range removeMe = new Range();
        removeMe.setBegin(begin.getHostAddress());
        removeMe.setEnd(end.getHostAddress());

        level.removeRange(removeMe);
    }

    /**
     * 
     */
    public Set getAllIpAddresses(int level) {
        return getAllIpAddresses(getScanLevel(level));
    }

    public Set getAllIpAddresses(ScanLevel level) {
        Set retval = new TreeSet();

        Enumeration e = level.enumerateRange();
        while (e.hasMoreElements()) {
            Range ir = (Range) e.nextElement();

            try {
                for (long i = Long.parseLong(ir.getBegin()); i <= Long.parseLong(ir.getEnd()); i++) {
                    retval.add(toInetAddress(i));
                }
            } catch (UnknownHostException uhE) {
                ThreadCategory.getInstance(getClass()).warn("Failed to convert address range (" + ir.getBegin() + ", " + ir.getEnd() + ")", uhE);
            }

        }

        e = level.enumerateSpecific();
        while (e.hasMoreElements()) {
            String current = (String) e.nextElement();
            try {
                retval.add(InetAddress.getByName(current));
            } catch (UnknownHostException uhE) {
                ThreadCategory.getInstance().warn("Failed to convert address: " + current, uhE);
            }
        }

        return retval;
    }

    public Set getAllExcludes() {
        if (m_excludes == null) {
            m_excludes = new TreeSet();

            Excludes excludes = m_config.getExcludes();

            if (excludes != null) {
                if (excludes.getRangeCount() > 0) {
                    Enumeration e = excludes.enumerateRange();
                    while (e.hasMoreElements()) {
                        Range ir = (Range) e.nextElement();

                        try {
                            for (long i = Long.parseLong(ir.getBegin()); i <= Long.parseLong(ir.getEnd()); i++) {
                                m_excludes.add(toInetAddress(i));
                            }
                        } catch (UnknownHostException uhE) {
                            ThreadCategory.getInstance(getClass()).warn("Failed to convert address range (" + ir.getBegin() + ", " + ir.getEnd() + ")", uhE);
                        }
                    }
                }

                if (excludes.getSpecificCount() > 0) {
                    Enumeration e = excludes.enumerateSpecific();
                    while (e.hasMoreElements()) {
                        String current = (String) e.nextElement();
                        try {
                            m_excludes.add(InetAddress.getByName(current));
                        } catch (UnknownHostException uhE) {
                            ThreadCategory.getInstance().warn("Failed to convert address: " + current, uhE);
                        }
                    }
                }
            }
        }
        return m_excludes;
    }

    public void removeExcludeRange(InetAddress begin, InetAddress end) {
        Range removeMe = new Range();
        removeMe.setBegin(begin.getHostAddress());
        removeMe.setEnd(end.getHostAddress());

        m_config.getExcludes().removeRange(removeMe);
    }

    public void removeExcludeSpecific(InetAddress specific) {
        m_config.getExcludes().removeSpecific(specific.getHostAddress());
    }

    /**
     * 
     */
    public long getRescanFrequency() {
        long frequency = -1;

        if (m_config.hasRescanFrequency())
            frequency = m_config.getRescanFrequency();
        else {
            ThreadCategory.getInstance(VulnscandConfigFactory.class).warn("Vulnscand configuration file is missing rescan interval, defaulting to 24 hour interval.");
            frequency = 86400000; // default is 24 hours
        }

        return frequency;
    }

    /**
     * 
     */
    public long getInitialSleepTime() {
        long sleep = -1;

        if (m_config.hasInitialSleepTime())
            sleep = m_config.getInitialSleepTime();
        else {
            ThreadCategory.getInstance(VulnscandConfigFactory.class).warn("Vulnscand configuration file is missing initial pause time, defaulting to 5 minutes.");
            sleep = 300000; // default is 5 minutes
        }

        return sleep;
    }

    /**
     * 
     */
    public InetAddress getServerAddress() {
        try {
            return (InetAddress.getByName(m_config.getServerAddress()));
        } catch (UnknownHostException ex) {
            ThreadCategory.getInstance(VulnscandConfigFactory.class).error("Invalid server in config file", ex);
            return null;
        }
    }

    /**
     * Gets the cached value of the plugin lists in the config file
     */
    public String[] getPluginLists() {
        if (m_pluginLists == null) {
            m_pluginLists = new String[5];

            // Dummy value
            m_pluginLists[0] = "";

            try {
                Enumeration scanLevels = m_config.enumerateScanLevel();
                while (scanLevels.hasMoreElements()) {
                    ScanLevel scanLevel = (ScanLevel) scanLevels.nextElement();

                    String levelPluginList = scanLevel.getPluginList();

                    // Get rid of all of the carriage returns, tabs, and spaces
                    levelPluginList = levelPluginList.replace('\n', ' ');
                    levelPluginList = levelPluginList.replace('\t', ' ');
                    levelPluginList = m_space.subst(levelPluginList, "");

                    m_pluginLists[scanLevel.getLevel()] = levelPluginList;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                ThreadCategory.getInstance(getClass()).error("Error while loading plugin lists from config file", ex);
            }
        }
        return m_pluginLists;
    }

    /**
     * Gets the cached value of the safe checks settings in the config file
     */
    public boolean[] getSafeChecks() {
        if (m_safeChecks == null) {
            m_safeChecks = new boolean[5];

            // Dummy value
            m_safeChecks[0] = true;

            try {
                Enumeration scanLevels = m_config.enumerateScanLevel();
                while (scanLevels.hasMoreElements()) {
                    ScanLevel scanLevel = (ScanLevel) scanLevels.nextElement();

                    m_safeChecks[scanLevel.getLevel()] = scanLevel.getSafeChecks();
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                ThreadCategory.getInstance(getClass()).error("Error while loading safe checks settings from config file", ex);
            }
        }
        return m_safeChecks;
    }

    /**
     * 
     */
    public int getServerPort() {
        return m_config.getServerPort();
    }

    /**
     * 
     */
    public String getServerUsername() {
        return m_config.getServerUsername();
    }

    /**
     * 
     */
    public String getServerPassword() {
        return m_config.getServerPassword();
    }

    /**
     * 
     */
    public boolean getStatus() {
        return m_config.getStatus();
    }

    /**
     * 
     */
    public boolean getManagedInterfacesStatus() {
        return m_config.getManagedInterfaces().getStatus();
    }

    /**
     * 
     */
    public int getManagedInterfacesScanLevel() {
        return m_config.getManagedInterfaces().getScanLevel();
    }

    /**
     * 
     */
    public int getMaxSuspectThreadPoolSize() {
        return m_config.getMaxSuspectThreadPoolSize();
    }

    /**
     * 
     */
    public int getMaxRescanThreadPoolSize() {
        return m_config.getMaxRescanThreadPoolSize();
    }
}
