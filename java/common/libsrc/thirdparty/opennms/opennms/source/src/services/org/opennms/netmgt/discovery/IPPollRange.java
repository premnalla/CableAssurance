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
// 2003 Jan 31: Cleaned up some unused imports.
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

package org.opennms.netmgt.discovery;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * <p>
 * This class is designed to encapsualte the information about an address range
 * plus the retry &amp; timeout information. The class is designed so that it
 * can return either an {@link java.util.Enumeration enumeration}or an
 * {@link java.util.Iterator iterator}to traverse the range of addresses.
 * </p>
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya </A>
 * @author <A HREF="mailto:weave@oculan.com">Brian Weaver </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * 
 */
final class IPPollRange {
    /**
     * The range to cycle over.
     */
    private IPAddressRange m_range;

    /**
     * The timeout in milliseconds (1/1000th)
     */
    private long m_timeout;

    /**
     * The number of retries for each generate object.
     */
    private int m_retries;

    /**
     * <P>
     * The purpose of the IPPollRangeGenerator class is to provide an
     * Enumeration or Iterator object that can be returned by the encapsulating
     * class. The class implements the new style Iterator interface, as well as
     * the old style Enumeration to allow the developer freedom of choice when
     * cycling over ranges.
     * </P>
     * 
     * @see java.util.Iterator
     * @see java.util.Enumeration
     */
    final class IPPollRangeGenerator implements Enumeration, Iterator {
        /**
         * <P>
         * The range of address to generate.
         * </P>
         */
        private Enumeration m_range;

        /**
         * <P>
         * Creates a poll range generator object.
         * </P>
         * 
         * @param en
         *            The Enumeration to use for address generation.
         */
        public IPPollRangeGenerator(Enumeration en) {
            m_range = en;
        }

        /**
         * <P>
         * Returns true if the Enumeration described by this object still has
         * more elements.
         * </P>
         */
        public boolean hasMoreElements() {
            return m_range.hasMoreElements();
        }

        /**
         * <P>
         * Returns the next IPPollAddress in the enumeration.
         * </P>
         * 
         * @exception java.util.NoSuchElementException
         *                Thrown if there are no more elements in the iteration.
         */
        public Object nextElement() {
            return new IPPollAddress((InetAddress) m_range.nextElement(), m_timeout, m_retries);
        }

        /**
         * <P>
         * If there are more elements left in the iteration then a value of true
         * is returned. Else a false value is returned.
         * </P>
         */
        public boolean hasNext() {
            return hasMoreElements();
        }

        /**
         * <P>
         * Returns the next object in the iteration and increments the internal
         * pointer.
         * </P>
         * 
         * @exception java.util.NoSuchElementException
         *                Thrown if there are no more elements in the iteration.
         */
        public Object next() {
            return nextElement();
        }

        /**
         * The remove method is part of the Iterator interface and is optional.
         * Since it is not implemnted it will always throw an
         * UnsupportedOperationException.
         * 
         * @exception java.lang.UnsupportedOperationException
         *                Always thrown by this method.
         */
        public void remove() {
            throw new UnsupportedOperationException("remove operation not supported");
        }

    } // end IPPollRangeGenerator

    /**
     * <P>
     * Creates an IPPollRange object that can be used to generate IPPollAddress
     * objects. The addresses are encapsulated by the range object and the
     * values of timeout and retry are set in each generated IPPollAddress
     * object.
     * </P>
     * 
     * @param fromIP
     *            The start of the address range to cycle over.
     * @param toIP
     *            The end of the address range to cycle over.
     * @param timeout
     *            The timeout for each generated IPPollAddress.
     * @param retries
     *            The number of retries for generated addresses.
     * 
     * @see IPPollAddress
     * @see IPAddressRange
     * 
     */
    IPPollRange(String fromIP, String toIP, long timeout, int retries) throws java.net.UnknownHostException {
        m_range = new IPAddressRange(fromIP, toIP);
        m_timeout = timeout;
        m_retries = retries;
    }

    /**
     * <P>
     * Creates an IPPollRange object that can be used to generate IPPollAddress
     * objects. The addresses are encapsulated by the range [start..end] and the
     * values of timeout and retry are set in each generated IPPollAddress
     * object.
     * </P>
     * 
     * @param start
     *            The start of the address range to cycle over.
     * @param end
     *            The end of the address range to cycle over.
     * @param timeout
     *            The timeout for each generated IPPollAddress.
     * @param retries
     *            The number of retries for generated addresses.
     * 
     * @see IPPollAddress
     * @see IPAddressRange
     * 
     */
    IPPollRange(InetAddress start, InetAddress end, long timeout, int retries) {
        m_range = new IPAddressRange(start, end);
        m_timeout = timeout;
        m_retries = retries;
    }

    /**
     * <P>
     * Creates an IPPollRange object that can be used to generate IPPollAddress
     * objects. The addresses are encapsulated by the range object and the
     * values of timeout and retry are set in each generated IPPollAddress
     * object.
     * </P>
     * 
     * @param range
     *            The address range to cycle over.
     * @param timeout
     *            The timeout for each generated IPPollAddress.
     * @param retries
     *            The number of retries for generated addresses.
     * 
     * @see IPPollAddress
     * 
     */
    IPPollRange(IPAddressRange range, long timeout, int retries) {
        m_range = range;
        m_timeout = timeout;
        m_retries = retries;
    }

    /**
     * <P>
     * Returns the timeout set for the object. The timeout should be in 1/1000th
     * of a second increments.
     * </P>
     */
    long getTimeout() {
        return m_timeout;
    }

    /**
     * <P>
     * Returns the retry count for the object.
     * </P>
     */
    int getRetries() {
        return m_retries;
    }

    /**
     * <P>
     * Returns the configured address ranges that are encapsulated by this
     * object.
     * </P>
     */
    IPAddressRange getAddressRange() {
        return m_range;
    }

    /**
     * <P>
     * Returns an Enumeration that can be used to cycle over the range of
     * pollable addresses.
     * </P>
     */
    Enumeration elements() {
        return new IPPollRangeGenerator(m_range.elements());
    }

    /**
     * <P>
     * Returns an Iterator object that can be used to cycle over the range of
     * pollable address information.
     * </P>
     */
    Iterator iterator() {
        return new IPPollRangeGenerator(m_range.elements());
    }
}