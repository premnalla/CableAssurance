/*
 * Copyright 1999,2004-2005 The Apache Software Foundation.
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


package org.apache.catalina.cluster.session;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Custom subclass of <code>ObjectInputStream</code> that loads from the
 * class loader for this web application.  This allows classes defined only
 * with the web application to be found correctly.
 *
 * @author Craig R. McClanahan
 * @author Bip Thelin
 * @version $Revision: 1.1 $, $Date: 2007/03/01 20:19:04 $
 */

public final class ReplicationStream extends ObjectInputStream {


    /**
     * The class loader we will use to resolve classes.
     */
    private ClassLoader classLoader = null;

    /**
     * Construct a new instance of CustomObjectInputStream
     *
     * @param stream The input stream we will read from
     * @param classLoader The class loader used to instantiate objects
     *
     * @exception IOException if an input/output error occurs
     */
    public ReplicationStream(InputStream stream,
                             ClassLoader classLoader)
        throws IOException {

        super(stream);
        this.classLoader = classLoader;
    }

    /**
     * Load the local class equivalent of the specified stream class
     * description, by using the class loader assigned to this Context.
     *
     * @param classDesc Class description from the input stream
     *
     * @exception ClassNotFoundException if this class cannot be found
     * @exception IOException if an input/output error occurs
     */
    public Class resolveClass(ObjectStreamClass classDesc)
        throws ClassNotFoundException, IOException {
        String name = classDesc.getName();
        boolean tryRepFirst = name.startsWith("org.apache.catalina.cluster");
        try {
            try
            {
                if ( tryRepFirst ) return findReplicationClass(name);
                else return findWebappClass(name);
            }
            catch ( Exception x )
            {
                if ( tryRepFirst ) return findWebappClass(name);
                else return findReplicationClass(name);
            }
        } catch (ClassNotFoundException e) {
            return super.resolveClass(classDesc);
        }
    }
    
    public Class findReplicationClass(String name)
        throws ClassNotFoundException, IOException {
        return Class.forName(name, false, getClass().getClassLoader());
    }

    public Class findWebappClass(String name)
        throws ClassNotFoundException, IOException {
        return Class.forName(name, false, classLoader);
    }


}
