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

package org.opennms.netmgt.poller.monitors;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.netmgt.config.PollerConfig;
import org.opennms.netmgt.utils.ParameterMap;

/**
 * This class implements a basic JDBC monitoring framework; The idea is than
 * these tests doesn't take too long (or too much resources to run) and provide
 * the basic healt information about the polled server. See
 * <code>src/services/org/opennms/netmgt/poller</code> OpenNMS plugin
 * information at <a
 * href="http://www.opennms.org/users/docs/docs/html/devref.html">OpenNMS
 * developer site </a>
 * 
 * @author Jose Vicente Nunez Zuleta (josevnz@users.sourceforge.net) - RHCE,
 *         SJCD, SJCP version 0.1 - 07/23/2002 * version 0.2 - 08/05/2002 --
 *         Added retry logic, input validations to poller.
 * @since 0.1
 */
final public class JDBCStoredProcedureMonitor extends JDBCMonitor
{
   /**
   * Class constructor.
   */
   public JDBCStoredProcedureMonitor() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
   }

   public int checkStatus( Category log, Map parameters, Connection con )
   {
      int status = SERVICE_UNAVAILABLE;
      CallableStatement cs = null;
      try
      {
         boolean bPass = false;
         String storedProcedure = ParameterMap.getKeyedString(parameters, "stored-procedure", null);
         if ( storedProcedure == null )
            return status;

         String procedureCall = "{ ? = call test." + storedProcedure + "()}";
         cs = con.prepareCall( procedureCall );
         if (log.isDebugEnabled())
            log.debug(getClass().getName() + ": Calling stored procedure: " + procedureCall );
         cs.registerOutParameter(1, java.sql.Types.BIT );
         cs.executeUpdate();
         bPass = cs.getBoolean( 1 );
         if (log.isDebugEnabled())
            log.debug(getClass().getName() + ": Stored procedure returned: " + bPass );

         // If the query worked, assume than the server is ok
         if (bPass)
         {
            status = SERVICE_AVAILABLE;
         }
      }
      catch (SQLException sqlEx)
      {
         if ( log.isDebugEnabled() )
         {
            log.debug(getClass().getName() + ": JDBC stored procedure call not functional: " + sqlEx.getSQLState() + ", " + sqlEx.toString());
            sqlEx.printStackTrace();
         }
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


