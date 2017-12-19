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
// The code in this file is Copyright (C) 2004 DJ Gregor.
//
// Based on install.pl which was Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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

package org.opennms.install;

public class ColumnChange {
    private Column m_column = null;

    private Object m_nullReplace = null;

    private boolean m_upgradeTimestamp = false;

    private int m_selectIndex = 0;

    private int m_prepareIndex = 0;

    private int m_columnType = 0;

    public Column getColumn() {
        return m_column;
    }

    public void setColumn(Column column) {
        m_column = column;
    }

    public Object getNullReplace() {
        return m_nullReplace;
    }

    public boolean isNullReplace() {
        return (m_nullReplace != null);
    }

    public void setNullReplace(Object nullReplace) {
        m_nullReplace = nullReplace;
    }

    public boolean isUpgradeTimestamp() {
        return m_upgradeTimestamp;
    }

    public void setUpgradeTimestamp(boolean upgradeTimestamp) {
        m_upgradeTimestamp = upgradeTimestamp;
    }

    public int getSelectIndex() {
        return m_selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        m_selectIndex = selectIndex;
    }

    public int getPrepareIndex() {
        return m_prepareIndex;
    }

    public void setPrepareIndex(int prepareIndex) {
        m_prepareIndex = prepareIndex;
    }

    public int getColumnType() {
        return m_columnType;
    }

    public void setColumnType(int columnType) {
        m_columnType = columnType;
    }
}
