//
// // // This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc. All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// 2003 Jul 21: Explicitly close sockets.
// 2003 Jul 18: Fixed exception to enable retries.
// 2003 Jan 31: Cleaned up some unused imports.
// 2003 Jan 29: Added response time
// 2002 Nov 14: Used non-blocking I/O for speed improvements.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp. All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//       
// For more information contact:
//      OpenNMS Licensing <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
// Tab Size = 8
//

package org.opennms.netmgt.capsd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.utils.ParameterMap;

/**
 * <P>
 * This class is designed to be used by the capabilities daemon to test for the
 * existance of MS Exchange server on remote interfaces. The class implements
 * the Plugin interface that allows it to be used along with other plugins by
 * the daemon.
 * </P>
 * 
 * @author <A HREF="mailto:mike@opennms.org">Mike </A>
 * @author <A HREF="mailto:weave@oculan.com">Weave </A>
 * @author <A HREF="http://www.opennsm.org">OpenNMS </A>
 * 
 */
public final class MSExchangePlugin extends AbstractPlugin {

    /**
     * The name of this protocol plugin
     */
    private static final String PROTOCOL_NAME = "MSExchange";

    /**
     * The banner string to look for.
     */
    private static final String BANNER_STRING = "Microsoft Exchange";

    /**
     * <P>
     * The default port on which to check for POP3 service.
     * </P>
     */
    private static final int DEFAULT_POP3_PORT = 110;

    /**
     * <P>
     * The default port on which to check for IMAP service.
     * </P>
     */
    private static final int DEFAULT_IMAP_PORT = 143;

    /**
     * <P>
     * The default port on which to check for MAPI service. To check for MAPI we
     * check the http-rpc-epmap/ncacn_http service on port 593. This
     * port/service is used by exchange for doing RPC over HTTP.
     * </P>
     */
    private static final int DEFAULT_MAPI_PORT = 593;

    /**
     * Default number of retries.
     */
    private final static int DEFAULT_RETRY = 0;

    /**
     * Default timeout (in milliseconds).
     */
    private final static int DEFAULT_TIMEOUT = 5000;

    /**
     * The array location for POP3 information.
     */
    private final static int POP3_INDEX = 0;

    /**
     * The array location for IMAP information.
     */
    private final static int IMAP_INDEX = 1;

    private boolean isServer(InetAddress host, int port, int retries, int timeout) {
        Category log = ThreadCategory.getInstance(getClass());

        boolean isAServer = false;
        for (int attempts = 0; attempts <= retries && !isAServer; attempts++) {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), timeout);
                socket.setSoTimeout(timeout);
                log.debug("MSExchangePlugin: connected to host: " + host + " on port: " + port);

                // Allocate a line reader
                //
                BufferedReader lineRdr = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Read the banner line and see if it contains the
                // substring "Microsoft Exchange"
                //
                String banner = lineRdr.readLine();
                if (banner != null) {
                    int rc = banner.indexOf(BANNER_STRING);
                    if (rc > -1)
                        isAServer = true;
                }
            } catch (ConnectException e) {
                // Connection refused!! Continue to retry.
                //
                log.debug("isServer: Connection refused to " + host.getHostAddress() + ":" + port);
            } catch (NoRouteToHostException e) {
                // No Route to host!!!
                //
                e.fillInStackTrace();
                log.info("isServer: Failed to connect to host " + host.getHostAddress() + ", no route to host", e);
                throw new UndeclaredThrowableException(e);
            } catch (InterruptedIOException e) {
                // ignore this
                log.debug("MSExchangePlugin: did not connect to host within timeout: " + timeout + " attempt: " + attempts);
            } catch (IOException e) {
                log.info("isServer: Unexpected I/O exception occured with host " + host.getHostAddress() + " on port " + port, e);
            } catch (Throwable t) {
                log.error("isServer: Undeclared throwable caught communicating with host " + host.getHostAddress() + " on port " + port, t);
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }
                } catch (IOException e) {
                }
            }
        }

        return isAServer;
    }

    /**
     * <P>
     * Test to see if the passed host is running Microsoft Exchange server. If
     * the remote host is running POP3, IMAP or MAPI and we are able to retreive
     * a banner from any of the ports these services listen on wich include the
     * text "Microsoft Exchange" then this method will return true. Otherwise a
     * false value is returned to the caller.
     * </P>
     * 
     * @param host
     *            The remote host to test.
     * @param ports
     *            The remote ports to test. Port value of -1 indicates that all
     *            ports should be tested, otherwise only the specified
     *            port/protocol will be tested..
     * 
     * @return The array of supported protocols by the exchange server The
     *         values are in the order POP3, IMAP, MAPI in the returned array.
     */
    private boolean[] isServer(InetAddress host, int[] ports, int retries, int timeout) {
        boolean isExPop3 = false;
        boolean isExImap = false;
        boolean isExMapi = false; // NOTE: MAPI protocol check currently
                                    // disabled...see NOTE
        // below...

        if (ports[POP3_INDEX] > 0)
            isExPop3 = isServer(host, ports[POP3_INDEX], retries, timeout);

        if (ports[IMAP_INDEX] > 0)
            isExImap = isServer(host, ports[IMAP_INDEX], retries, timeout);

        //
        // NOTE: We aren't able to confirm that MS Exchange uses port 593 for
        // MAPI communication with MS Outlook clients. Further, even if
        // we were able to confirm it we can't be certain what other
        // applications
        // also use port 593 for doing RPC over HTTP. Therefore
        // we can't be sure that just because a box is listening on port 593
        // it is running MS Exchange. Commenting this check out for now.
        //
        // if(ports[MAPI_INDEX] > 0)
        // isExMapi = isServer(host, ports[MAPI_INDEX], retries, timeout);

        return new boolean[] { isExPop3, isExImap, isExMapi };
    }

    /**
     * Returns the name of the protocol that this plugin checks on the target
     * system for support.
     * 
     * @return The protocol name for this plugin.
     */
    public String getProtocolName() {
        return PROTOCOL_NAME;
    }

    /**
     * Returns true if the protocol defined by this plugin is supported. If the
     * protocol is not supported then a false value is returned to the caller.
     * 
     * @param address
     *            The address to check for support.
     * 
     * @return True if the protocol is supported by the address.
     */
    public boolean isProtocolSupported(InetAddress address) {
        boolean[] result = isServer(address, new int[] { DEFAULT_POP3_PORT, DEFAULT_IMAP_PORT, DEFAULT_MAPI_PORT }, DEFAULT_RETRY, DEFAULT_TIMEOUT);

        return (result[0] || result[1] || result[2]);
    }

    /**
     * Returns true if the protocol defined by this plugin is supported. If the
     * protocol is not supported then a false value is returned to the caller.
     * The qualifier map passed to the method is used by the plugin to return
     * additional information by key-name. These key-value pairs can be added to
     * service events if needed.
     * 
     * @param address
     *            The address to check for support.
     * @param qualifiers
     *            The map where qualification are set by the plugin.
     * 
     * @return True if the protocol is supported by the address.
     */
    public boolean isProtocolSupported(InetAddress address, Map qualifiers) {
        int retries = DEFAULT_RETRY;
        int timeout = DEFAULT_TIMEOUT;
        int pop3port = DEFAULT_POP3_PORT;
        int imapport = DEFAULT_IMAP_PORT;
        int mapiport = DEFAULT_MAPI_PORT;

        if (qualifiers != null) {
            retries = ParameterMap.getKeyedInteger(qualifiers, "retry", DEFAULT_RETRY);
            timeout = ParameterMap.getKeyedInteger(qualifiers, "timeout", DEFAULT_TIMEOUT);
            pop3port = ParameterMap.getKeyedInteger(qualifiers, "pop3 port", DEFAULT_POP3_PORT);
            imapport = ParameterMap.getKeyedInteger(qualifiers, "imap port", DEFAULT_IMAP_PORT);
            mapiport = ParameterMap.getKeyedInteger(qualifiers, "mapi port", DEFAULT_MAPI_PORT);
        }

        boolean[] result = isServer(address, new int[] { pop3port, imapport, mapiport }, retries, timeout);
        if (qualifiers != null) {
            if (result[0] && !qualifiers.containsKey("pop3 port"))
                qualifiers.put("pop3 port", new Integer(pop3port));

            if (result[1] && !qualifiers.containsKey("imap port"))
                qualifiers.put("imap port", new Integer(imapport));

            if (result[2] && !qualifiers.containsKey("mapi port"))
                qualifiers.put("mapi port", new Integer(mapiport));
        }

        return (result[0] || result[1] || result[2]);
    }
}
