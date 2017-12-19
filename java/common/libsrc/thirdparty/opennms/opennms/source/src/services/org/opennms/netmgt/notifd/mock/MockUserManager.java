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
// OpenNMS Licensing       <license@opennms.org>
//     http://www.opennms.org/
//     http://www.opennms.com/
//
package org.opennms.netmgt.notifd.mock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.config.GroupManager;
import org.opennms.netmgt.config.UserManager;

/**
 * @author brozow
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MockUserManager extends UserManager {

    /**
     * @param groupManager
     * @throws ValidationException
     * @throws MarshalException
     */
    public MockUserManager(GroupManager groupManager, String xmlString) throws MarshalException, ValidationException {
        super(groupManager);
        Reader reader = new StringReader(xmlString);
        parseXML(reader);
    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.config.UserManager#saveXML(java.lang.String)
     */
    protected void saveXML(String writerString) throws IOException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.config.UserManager#update()
     */
    protected void update() throws IOException, FileNotFoundException, MarshalException, ValidationException {
        // TODO Auto-generated method stub

    }

}
