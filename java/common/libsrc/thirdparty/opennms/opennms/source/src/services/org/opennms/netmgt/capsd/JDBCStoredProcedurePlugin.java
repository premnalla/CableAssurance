//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
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

package org.opennms.netmgt.capsd;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.DBTools;
import org.opennms.netmgt.utils.ParameterMap;

/**
 * This OpenNMS capsd plugin checks if a given server is running a server that
 * can talk JDBC on a given interface. If true then the interface is "saved" for
 * future service state checking. This plugin is slow; Stablishing a connection
 * between the client and the server is an slow operation. A connection pool
 * doesn't make any sense when discovering a database, Also opening and closing
 * a connection every time helps to discover problems like a RDBMS running out
 * of connections.
 * <p>
 * More plugin information available at: <a
 * href="http://www.opennms.org/users/docs/docs/html/devref.html">OpenNMS
 * developer site </a>
 * </p>
 * 
 * @author Jose Vicente Nunez Zuleta (josevnz@users.sourceforge.net) - RHCE,
 *         SJCD, SJCP
 * @version 0.1 - 07/22/2002
 * @since 0.1
 */
public final class JDBCStoredProcedurePlugin extends JDBCPlugin
{
   /**
    * The stored procedure checked by the plugin
    */
   private final static String DEFAULT_STORED_PROCEDURE = "isRunning";

   /**
     * Class constructor. Load the JDBC drivers.
     */
    public JDBCStoredProcedurePlugin()
    {
        Category log = ThreadCategory.getInstance(getClass());
        log.info(getClass().getName() + ": JDBCStoredProcedurePlugin class loaded");
    }

   public boolean checkStatus( Category log, Connection con, Map qualifiers )
   {
      boolean status = false;
      CallableStatement cs = null;
      try
      {
         String storedProcedure = ParameterMap.getKeyedString(qualifiers, "stored-procedure", DEFAULT_STORED_PROCEDURE);
         String procedureCall = "{ ? = call test." + storedProcedure + "()}";
         cs = con.prepareCall( procedureCall );
         log.debug(getClass().getName() + ": Calling stored procedure: " + procedureCall );
         cs.registerOutParameter(1, java.sql.Types.BIT );
         cs.executeUpdate();
         status = cs.getBoolean( 1 );
         boolean b = status;
         log.debug(getClass().getName() + ": Stored procedure returned: " + b );
      }
      catch (SQLException sqlEx)
      {
         log.debug(getClass().getName() + ": JDBC stored procedure call not functional: " + sqlEx.getSQLState() + ", " + sqlEx.toString());
         sqlEx.printStackTrace();
      }
      finally 
      {
         if (cs != null)
         {
            try
            {
               cs.close();
            }
            catch( SQLException ignore)
            {
            }
         }
      }
      return status;
   }

} // End of class

