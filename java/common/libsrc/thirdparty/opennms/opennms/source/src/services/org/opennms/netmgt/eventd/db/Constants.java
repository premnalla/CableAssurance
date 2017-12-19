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
// Tab Size = 8
//

package org.opennms.netmgt.eventd.db;

import java.util.Iterator;
import java.util.List;

/**
 * This class contains the constants and methods related to inserting events
 * into the database
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Kumaraswamy </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class Constants {
    /**
     * Enumerated values for severity being indeterminate
     */
    public final static int SEV_INDETERMINATE = 1;

    /**
     * Enumerated values for severity being unimplemented at this time
     */
    public final static int SEV_CLEARED = 2;

    /**
     * Enumerated values for severity indicates a warning
     */
    public final static int SEV_NORMAL = 3;

    /**
     * Enumerated values for severity indicates a warning
     */
    public final static int SEV_WARNING = 4;

    /**
     * Enumerated values for severity is minor
     */
    public final static int SEV_MINOR = 5;

    /**
     * Enumerated values for severity is major
     */
    public final static int SEV_MAJOR = 6;

    /**
     * Enumerated values for severity is critical
     */
    public final static int SEV_CRITICAL = 7;

    /**
     * Enumerated value for the state(tticket and forward) when entry is active
     */
    final static int STATE_ON = 1;

    /**
     * Enumerated value for the state(tticket and forward) when entry is not
     * active
     */
    final static int STATE_OFF = 0;

    /**
     * The 'parms' are added to a single column of the DB - the parm name and
     * value are added as delimiter separated list of ' <parmName>= <value>'
     * strings
     */
    final static char NAME_VAL_DELIM = '=';

    /**
     * The delimiter used to delimit multiple values of the same element that
     * are appended and made the value of a single database column
     */
    final static char MULTIPLE_VAL_DELIM = ';';

    /**
     * The parser adds the value and attributes of an element to a single
     * element of eventBlock and uses the ATTRIB_DELIM to separate these values
     */
    final static String ATTRIB_DELIM = "/\\";

    /**
     * The values and the corresponding attributes of an element are added to a
     * single column of the table and delimited by DB_ATTRIB_DELIM
     */
    final static char DB_ATTRIB_DELIM = ',';

    /**
     * Multiple values of any xml element are appended into one value when
     * inserted into the database - if the length of the appended string exceeds
     * the column length, the value is appended with this pattern
     */
    final static String VALUE_TRUNCATE_INDICATOR = "...";

    /**
     * This method is used to escape required values from strings that may
     * contain those values. If the passed string contains the passed value then
     * the character is reformatted into its <EM>%dd</EM> format.
     * 
     * 
     * @param inStr
     *            string that might contain the delimiter
     * @param delimchar
     *            delimiter to escape
     * 
     * @return The string with the delimiter escaped as in URLs
     * 
     * @see #DB_ATTRIB_DELIM
     * @see #MULTIPLE_VAL_DELIM
     */
    public static String escape(String inStr, char delimchar) {
        // integer equivalent of the delimiter
        int delim = delimchar;

        // convert this to a '%<int>' string
        String delimEscStr = "%" + String.valueOf(delim);

        // the buffer to return
        StringBuffer outBuffer = new StringBuffer(inStr);

        int index = 0;
        int delimIndex = inStr.indexOf(delimchar, index);
        while (delimIndex != -1) {
            // delete the delimiter and add the escape string
            outBuffer.deleteCharAt(delimIndex);
            outBuffer.insert(delimIndex, delimEscStr);

            index = delimIndex + delimEscStr.length() + 1;
            delimIndex = outBuffer.toString().indexOf(delimchar, index);
        }

        return outBuffer.toString();
    }

    /**
     * This method is passed a list of strings and a maximum string size that
     * must not be exceeded by the composite string.
     * 
     * @param strings
     *            The list of String objects.
     * @param maxlen
     *            The maximum length of the composite string
     * 
     * @return The composite string.
     * 
     * @exception java.lang.ClassCastException
     *                Thrown if any processed item in the list is not a string
     *                object.
     * 
     */
    public static String format(List strings, int maxlen) {
        if (strings == null)
            return null;

        StringBuffer buf = new StringBuffer();
        boolean first = true;
        Iterator i = strings.iterator();

        while (i.hasNext() && buf.length() < maxlen) {
            String s = (String) i.next();
            s = escape(s, MULTIPLE_VAL_DELIM);
            if (!first)
                buf.append(MULTIPLE_VAL_DELIM);
            buf.append(s);
            first = false;
        }

        if (buf.length() >= maxlen) {
            buf.setLength(maxlen - 4);
            buf.append(VALUE_TRUNCATE_INDICATOR);
        }
        return buf.toString();
    }

    /**
     * This method is passed an array of strings and a maximum string size that
     * must not be exceeded by the composite string.
     * 
     * @param strings
     *            The list of String objects.
     * @param maxlen
     *            The maximum length of the composite string
     * 
     * @return The composite string.
     * 
     * @exception java.lang.ClassCastException
     *                Thrown if any processed item in the list is not a string
     *                object.
     * 
     */
    public static String format(String[] strings, int maxlen) {
        if (strings == null || strings.length <= 0)
            return null;

        StringBuffer buf = new StringBuffer();
        boolean first = true;

        for (int index = 0; index < strings.length && buf.length() < maxlen; index++) {
            String s = (String) strings[index];
            s = escape(s, MULTIPLE_VAL_DELIM);
            if (!first)
                buf.append(MULTIPLE_VAL_DELIM);
            buf.append(s);
            first = false;
        }

        if (buf.length() >= maxlen) {
            buf.setLength(maxlen - 4);
            buf.append(VALUE_TRUNCATE_INDICATOR);
        }
        return buf.toString();
    }

    /**
     * This method is passed a string to be truncated to the maximum string size
     * passed.
     * 
     * @param string
     *            The string object.
     * @param maxlen
     *            The maximum length of the composite string
     * 
     * @return The string(truncated if necessary).
     * 
     */
    public static String format(String string, int maxlen) {
        if (string == null)
            return null;

        if (string.length() >= maxlen) {
            StringBuffer buf = new StringBuffer(string);

            buf.setLength(maxlen - 4);
            buf.append(VALUE_TRUNCATE_INDICATOR);

            return buf.toString();
        } else {
            return string;
        }
    }

    /**
     * Converts the severity to an integer
     * 
     * @return integer equivalent for the severity
     */
    public static int getSeverity(String sev) {
        int rc = SEV_INDETERMINATE;
        if (sev != null) {
            sev = sev.trim();
            if (sev.equalsIgnoreCase("normal"))
                rc = SEV_NORMAL;
            else if (sev.equalsIgnoreCase("warning"))
                rc = SEV_WARNING;
            else if (sev.equalsIgnoreCase("minor"))
                rc = SEV_MINOR;
            else if (sev.equalsIgnoreCase("major"))
                rc = SEV_MAJOR;
            else if (sev.equalsIgnoreCase("critical"))
                rc = SEV_CRITICAL;
            else if (sev.equalsIgnoreCase("cleared"))
                rc = SEV_CLEARED;
        }
        return rc;
    }

}
