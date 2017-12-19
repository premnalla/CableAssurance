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

package org.opennms.protocols.snmp;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * This class provides a set of utilities that may be used by other package
 * members. This class is not accessable to non-package classes.
 * 
 * The util class maintains a dynamically created list of SnmpSyntax object that
 * is uses to lookup received messages. The typeId() method of each SnmpSyntax
 * object provides the comparision data for the received ASN.1 type.
 * 
 * @see SnmpInt32
 * @see SnmpCounter32
 * @see SnmpGauge32
 * @see SnmpTimeTicks
 * @see SnmpOctetString
 * @see SnmpIPAddress
 * @see SnmpObjectId
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 */
class SnmpUtil extends Object {
    /**
     * The array of dynamically registred SnmpSyntax objects
     * 
     */
    static ArrayList m_syntaxArray = null;

    //
    // when the class is "created" and initiazlied
    // be sure to create an array to store the
    // syntax object into.
    //
    static {
        m_syntaxArray = new ArrayList();
    }

    /**
     * Used to register a SnmpSyntax object with the SnmpUtil class. Once
     * registered it can be dynamically found based on it's typeId().
     * 
     * @param obj
     *            The SnmpSyntax object to add
     * 
     * @return True if the object is successfully added
     * 
     */
    static boolean registerSyntax(SnmpSyntax obj) {
        boolean rc = false;
        synchronized (m_syntaxArray) {
            //
            // verify that the object is not in
            // the list already
            //
            boolean addIt = true;
            for (int x = 0; x < m_syntaxArray.size(); x++) {
                SnmpSyntax tmp = (SnmpSyntax) m_syntaxArray.get(x);
                if (obj.typeId() == tmp.typeId()) {
                    addIt = false;
                    break;
                }
            }
            if (addIt == true)
                rc = m_syntaxArray.add(obj);
        }
        return rc;
    }

    /**
     * Used to dynamically lookup registered SnmpSyntax objects
     * 
     * @param asnType
     *            The ASN.1 type to search for
     * 
     * @return A new SnmpSyntax object of the appropiate type
     * 
     */
    static SnmpSyntax getSyntaxObject(byte asnType) {
        SnmpSyntax obj = null;
        switch (asnType) {
        case SnmpInt32.ASNTYPE:
            obj = new SnmpInt32();
            break;

        case SnmpCounter32.ASNTYPE:
            obj = new SnmpCounter32();
            break;

        case SnmpGauge32.ASNTYPE:
            obj = new SnmpGauge32();
            break;

        case SnmpCounter64.ASNTYPE:
            obj = new SnmpCounter64();
            break;

        case SnmpTimeTicks.ASNTYPE:
            obj = new SnmpTimeTicks();
            break;

        case SnmpOctetString.ASNTYPE:
            obj = new SnmpOctetString();
            break;

        case SnmpOpaque.ASNTYPE:
            obj = new SnmpOpaque();
            break;

        case SnmpIPAddress.ASNTYPE:
            obj = new SnmpIPAddress();
            break;

        case SnmpObjectId.ASNTYPE:
            obj = new SnmpObjectId();
            break;

        case SnmpV2PartyClock.ASNTYPE:
            obj = new SnmpV2PartyClock();
            break;

        case SnmpNoSuchInstance.ASNTYPE:
            obj = new SnmpNoSuchInstance();
            break;

        case SnmpNoSuchObject.ASNTYPE:
            obj = new SnmpNoSuchObject();
            break;

        case SnmpEndOfMibView.ASNTYPE:
            obj = new SnmpEndOfMibView();
            break;

        case SnmpNull.ASNTYPE:
            obj = new SnmpNull();
            break;
        } // end case

        //
        // If the object is null then search
        // through user registered objects
        // see the SnmpSession.registerSyntaxObject
        // method
        //
        if (obj == null) {
            synchronized (m_syntaxArray) {
                for (int x = m_syntaxArray.size() - 1; x >= 0; --x) {
                    SnmpSyntax o = (SnmpSyntax) m_syntaxArray.get(x);
                    if (asnType == o.typeId()) {
                        obj = o.duplicate();
                        break; // exit the loop
                    }
                }
            }
        }
        return obj;
    }

    /**
     * Rotates a give buffer area marked by begin, pivot, and end. The pivot
     * marks the point where the array between [pivot..end) are moved to the
     * position marked by begin. The bytes between [begin..pivot) are shifted
     * such that begin is at [begin+(end-pivot)].
     * 
     * @param arrayBuf
     *            The buffer containing the data to rotate
     * @param begin
     *            The start of the rotation
     * @param pivot
     *            The pivot point for the rotation
     * @param end
     *            The end of the rotational buffer
     * 
     */
    static void rotate(byte[] arrayBuf, int begin, int pivot, int end) {
        // The amount of data to move between the pivot point
        // and the end of the buffer
        //
        int pedist = end - pivot;
        int bpdist = pivot - begin;

        // Allocate an array to hold half of the moving buffer
        //
        byte[] hold = new byte[pedist];

        // Copy to the back half of the rotating buffer to the
        // hold area
        //
        System.arraycopy(arrayBuf, // source
                         pivot, // source offset
                         hold, // destination
                         0, // destination offset
                         pedist); // length

        // Move the front half to the back half
        //
        System.arraycopy(arrayBuf, // source
                         begin, // source offset
                         arrayBuf, // destination
                         begin + pedist,// destination offset
                         bpdist); // length

        System.arraycopy(hold, // source
                         0, // source offset
                         arrayBuf, // destination
                         begin, // destination offset
                         pedist); // length
    }

    /**
     * Dumps an array of byte to the output string as a sequence of hexadecimal
     * digits.
     * 
     * @param out
     *            The output stream
     * @param data
     *            The data to dump
     * @param offset
     *            The start location within the data
     * @param length
     *            The length of data to dump
     * 
     */
    static void dumpHex(PrintStream out, byte[] data, int offset, int length) {
        if ((offset + length) > data.length)
            return;

        int cnt = 0;
        while (length > 0) {
            byte b = data[offset];
            out.print("0x");
            out.print(Integer.toHexString((b >> 4) & 0xf));
            out.print(Integer.toHexString(b & 0xf));
            out.print(" ");
            --length;
            offset++;

            if ((cnt++ % 16) == 0 && cnt != 1)
                out.println("");
        }
    }
}
