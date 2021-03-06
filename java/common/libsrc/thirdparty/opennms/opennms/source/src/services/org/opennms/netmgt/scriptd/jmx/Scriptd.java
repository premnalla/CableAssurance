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
// 2003 Aug 22: Added Scriptd code.
//
// Copyright (C) 2003 Tavve Software Company.  All rights reserved.
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

package org.opennms.netmgt.scriptd.jmx;

public class Scriptd implements ScriptdMBean {
    public void init() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.init();
    }

    public void start() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.start();
    }

    public void stop() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.stop();
    }

    public int getStatus() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        return scriptd.getStatus();
    }

    public String status() {
        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }

    public String getStatusText() {
        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }
}
