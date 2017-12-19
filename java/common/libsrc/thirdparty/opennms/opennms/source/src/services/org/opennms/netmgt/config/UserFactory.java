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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.ConfigFileConstants;

public class UserFactory extends UserManager {
    /**
     * The static singleton instance of the UserFactory
     */
    private static UserFactory instance;

    // private static ViewFactory viewFactory;

    /**
     * File path of users.xml
     */
    protected File usersFile;

    /**
     * Boolean indicating if the init() method has been called
     */
    private static boolean initialized = false;

    /**
     * 
     */
    private File m_usersConfFile;

    /**
     * 
     */
    private long m_lastModified;

    /**
     * Initializes the factory
     */
    public UserFactory() {
        super(GroupFactory.getInstance());
    }

    public static synchronized void init() throws IOException, FileNotFoundException, MarshalException, ValidationException {

        if (!initialized) {
            
            GroupFactory.init();
            getInstance().reload();
            initialized = true;
        }

    }

    /**
     * Singleton static call to get the only instance that should exist for the
     * UserFactory
     * 
     * @return the single user factory instance
     */
    static synchronized public UserFactory getInstance() {

        if (instance == null || !initialized) {
            instance = new UserFactory();
        }

        return instance;
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     * @throws MarshalException
     * @throws ValidationException
     */
    public void reload() throws IOException, FileNotFoundException, MarshalException, ValidationException {
        // Form the complete filename for the config file
        //
        m_usersConfFile = ConfigFileConstants.getFile(ConfigFileConstants.USERS_CONF_FILE_NAME);

        InputStream configIn = new FileInputStream(m_usersConfFile);
        m_lastModified = m_usersConfFile.lastModified();

        Reader reader = new InputStreamReader(configIn);
        parseXML(reader);
        
        initialized = true;

    }

    /**
     * @param writerString
     * @throws IOException
     */
    protected void saveXML(String writerString) throws IOException {
        if (writerString != null) {
            FileWriter fileWriter = new FileWriter(m_usersConfFile);
            fileWriter.write(writerString);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    /**
     * @return
     */
    public boolean isUpdateNeeded() {
        if (m_usersConfFile == null) {
            return true;
        }
        if (m_lastModified != m_usersConfFile.lastModified()) {
            return true;
        }
        return false;
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     * @throws MarshalException
     * @throws ValidationException
     */
    protected void update() throws IOException, FileNotFoundException, MarshalException, ValidationException {
        if (isUpdateNeeded()) {
            reload();
        }
    }
}
