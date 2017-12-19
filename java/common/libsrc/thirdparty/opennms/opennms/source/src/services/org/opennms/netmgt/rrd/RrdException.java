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
// Jun 24, 2004: Created this file.
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

package org.opennms.netmgt.rrd;

/**
 * This exception indicates an error has occurred creating, updating, or
 * fetching data from an Rrd file
 * 
 * @author brozow
 */
public class RrdException extends Exception {

    /**
     * 
     */
    public RrdException() {
        super();
        // FIXME Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public RrdException(String message) {
        super(message);
        // FIXME Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public RrdException(String message, Throwable cause) {
        super(message, cause);
        // FIXME Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public RrdException(Throwable cause) {
        super(cause);
        // FIXME Auto-generated constructor stub
    }

}
