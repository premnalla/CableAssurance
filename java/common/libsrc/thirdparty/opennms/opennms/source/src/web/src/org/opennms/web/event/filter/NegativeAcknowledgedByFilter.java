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

package org.opennms.web.event.filter;

/** Encapsulates filtering on exact unique event identifiers. */
public class NegativeAcknowledgedByFilter extends Object implements Filter {
    public static final String TYPE = "acknowledgedByNot";

    protected String user;

    public NegativeAcknowledgedByFilter(String user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        this.user = user;
    }

    public String getSql() {
        return (" (EVENTACKUSER<>'" + this.user + "' OR EVENTACKUSER IS NULL)");
    }

    public String getDescription() {
        return (TYPE + "=" + this.user);
    }

    public String getTextDescription() {
        return ("not acknowledged by " + this.user);
    }

    public String toString() {
        return ("<EventFactory.NegativeAcknowledgedByFilter: " + this.getDescription() + ">");
    }

    public String getAcknowledgedByFilter() {
        return (this.user);
    }

    public boolean equals(Object obj) {
        return (this.toString().equals(obj.toString()));
    }
}
