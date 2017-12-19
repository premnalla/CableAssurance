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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.destinationPaths.DestinationPaths;
import org.opennms.netmgt.config.destinationPaths.Header;
import org.opennms.netmgt.config.destinationPaths.Path;
import org.opennms.netmgt.config.destinationPaths.Target;

/**
 * @author David Hustace <david@opennms.org>
 */
public abstract class DestinationPathManager {

    /**
     * 
     */
    private DestinationPaths allPaths;
    /**
     * 
     */
    private Map m_destinationPaths;
    /**
     * 
     */
    protected InputStream configIn;
    /**
     * 
     */
    private Header oldHeader;

    /**
     * @param reader
     * @throws MarshalException
     * @throws ValidationException
     */
    protected void parseXML(Reader reader) throws MarshalException, ValidationException {
        allPaths = (DestinationPaths) Unmarshaller.unmarshal(DestinationPaths.class, reader);
        oldHeader = allPaths.getHeader();
    
        m_destinationPaths = new TreeMap();
    
        Iterator i = allPaths.getPathCollection().iterator();
        while (i.hasNext()) {
            Path curPath = (Path) i.next();
            m_destinationPaths.put(curPath.getName(), curPath);
        }
    }

    /**
     * 
     */
    public Path getPath(String pathName) throws IOException, MarshalException, ValidationException {
        update();
    
        return (Path) m_destinationPaths.get(pathName);
    }

    /**
     * 
     */
    public Map getPaths() throws IOException, MarshalException, ValidationException {
        update();
    
        return m_destinationPaths;
    }

    /**
     * 
     */
    public Collection getTargetCommands(Path path, int index, String target) throws IOException, MarshalException, ValidationException {
        update();
    
        Target[] targets = getTargetList(index, path);
    
        for (int i = 0; i < targets.length; i++) {
            if (targets[i].getName().equals(target))
                return targets[i].getCommandCollection();
        }
    
        // default null value if target isn't found in Path
        return null;
    }

    /**
     * 
     */
    public Target[] getTargetList(int index, Path path) throws IOException, MarshalException, ValidationException {
        update();
    
        Target[] targets = null;
        // index of -1 indicates the initial targets, any other index means to
        // get
        // the targets from the Escalate object at that index
        if (index == -1) {
            targets = path.getTarget();
        } else {
            targets = path.getEscalate(index).getTarget();
        }
    
        return targets;
    }

    /**
     * 
     */
    public boolean pathHasTarget(Path path, String target) throws IOException, MarshalException, ValidationException {
        update();
    
        Collection targets = path.getTargetCollection();
    
        Iterator i = targets.iterator();
        while (i.hasNext()) {
            Target curTarget = (Target) i.next();
            if (curTarget.getName().equals(target))
                return true;
        }
    
        // default false value if target isn't found
        return false;
    }

    /**
     * 
     */
    public synchronized void addPath(Path newPath) throws MarshalException, ValidationException, IOException {
        m_destinationPaths.put(newPath.getName(), newPath);
    
        saveCurrent();
    }

    /**
     * 
     */
    public synchronized void replacePath(String oldName, Path newPath) throws MarshalException, ValidationException, IOException {
        if (m_destinationPaths.containsKey(oldName)) {
            m_destinationPaths.remove(oldName);
        }
    
        addPath(newPath);
    }

    /**
     * Removes a Path from the xml file.
     * 
     * @param path
     *            the path to remove
     * @exception MarshalException
     * @exception ValidationException
     * @exception IOException
     * 
     */
    public synchronized void removePath(Path path) throws MarshalException, ValidationException, IOException {
        m_destinationPaths.remove(path.getName());
        saveCurrent();
    }

    /**
     * Removes a Path form the xml file based on its name
     * 
     * @param name
     *            the name of the path to remove
     * @exception MarshalException
     * @exception ValidationException
     * @exception IOException
     */
    public synchronized void removePath(String name) throws MarshalException, ValidationException, IOException {
        m_destinationPaths.remove(name);
        saveCurrent();
    }

    /**
     * 
     */
    public synchronized void saveCurrent() throws MarshalException, ValidationException, IOException {
        allPaths.clearPath();
        Iterator i = m_destinationPaths.keySet().iterator();
        while (i.hasNext()) {
            allPaths.addPath((Path) m_destinationPaths.get(i.next()));
        }
    
        allPaths.setHeader(rebuildHeader());
    
        // marshall to a string first, then write the string to the file. This
        // way the original config
        // isn't lost if the xml from the marshall is hosed.
        StringWriter stringWriter = new StringWriter();
        Marshaller.marshal(allPaths, stringWriter);
        String writerString = stringWriter.toString();
        saveXML(writerString);
    
        /*
         * TODO: what do do about this?  Should this be here?
         * Appears that everything is handled through the update
         * method when a member of field is requested.
         * 
         * Delete after all Notifd tests are passing.
         */
        //reload();
    }

    /**
     * @param writerString
     * @throws IOException
     */
    protected abstract void saveXML(String writerString) throws IOException;
    
    /**
     * 
     */
    private Header rebuildHeader() {
        Header header = oldHeader;
    
        header.setCreated(EventConstants.formatToString(new Date()));
    
        return header;
    }

    /**
     * @throws IOException
     * @throws MarshalException
     * @throws ValidationException
     * @throws FileNotFoundException
     */
    public abstract void update() throws IOException, MarshalException, ValidationException, FileNotFoundException;

}
