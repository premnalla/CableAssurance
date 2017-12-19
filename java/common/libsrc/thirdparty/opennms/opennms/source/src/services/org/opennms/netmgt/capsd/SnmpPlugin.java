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
//
// Tab Size = 8
//

package org.opennms.netmgt.capsd;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.InetAddress;
import java.util.Map;

import org.opennms.netmgt.config.SnmpPeerFactory;
import org.opennms.netmgt.utils.ParameterMap;
import org.opennms.netmgt.utils.SnmpResponseHandler;
import org.opennms.protocols.snmp.SnmpPduBulk;
import org.opennms.protocols.snmp.SnmpPduPacket;
import org.opennms.protocols.snmp.SnmpPduRequest;
import org.opennms.protocols.snmp.SnmpPeer;
import org.opennms.protocols.snmp.SnmpSMI;
import org.opennms.protocols.snmp.SnmpSession;
import org.opennms.protocols.snmp.SnmpVarBind;

/**
 * This class is used to test passed address for SNMP support. The configuration
 * used to determine the SNMP information is managed by the
 * {@link SnmpPeerFactorySnmpPeerFactory} class.
 * 
 * @author <A HREF="mailto:weave@oculan.com">Brian Weaver </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS </A>
 * 
 */
public final class SnmpPlugin extends AbstractPlugin {
    /**
     * The protocol supported by this plugin
     */
    private static final String PROTOCOL_NAME = "SNMP";

    /**
     * The system object identifier to retreive from the remote agent.
     */
    private static final String DEFAULT_OID = ".1.3.6.1.2.1.1.2.0";

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
        boolean isSupported = false;
        SnmpSession session = null;
        SnmpPeer peer = SnmpPeerFactory.getInstance().getPeer(address);
        try {
            if (peer == null)
                peer = new SnmpPeer(address);
            session = new SnmpSession(peer);

            SnmpResponseHandler handler = new SnmpResponseHandler();
            SnmpPduPacket out = new SnmpPduRequest(SnmpPduPacket.GET, new SnmpVarBind[] { new SnmpVarBind(DEFAULT_OID) });

            synchronized (handler) {
                session.send(out, handler);
                try {
                    wait((long) ((peer.getRetries() + 1) * peer.getTimeout()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (handler.getResult() != null) {
                isSupported = true;
            }
        } catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        } finally {
            if (session != null)
                session.close();
        }

        return isSupported;
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
        boolean isSupported = false;
        SnmpSession session = null;
        SnmpPeer peer = SnmpPeerFactory.getInstance().getPeer(address);
        try {
            if (peer == null)
                peer = new SnmpPeer(address);

            String expectedValue = null;
            if (qualifiers != null) {
                // "port" parm
                //
                if (qualifiers.get("port") != null) {
                    int port = ParameterMap.getKeyedInteger(qualifiers, "port", peer.getPort());
                    peer.setPort(port);
                }

                // "timeout" parm
                //
                if (qualifiers.get("timeout") != null) {
                    int timeout = ParameterMap.getKeyedInteger(qualifiers, "timeout", peer.getTimeout());
                    peer.setTimeout(timeout);
                }

                // "retry" parm
                //
                if (qualifiers.get("retry") != null) {
                    int retry = ParameterMap.getKeyedInteger(qualifiers, "retry", peer.getRetries());
                    peer.setRetries(retry);
                }

                // "force version" parm
                //
                if (qualifiers.get("force version") != null) {
                    String version = (String) qualifiers.get("force version");
                    if (version.equalsIgnoreCase("snmpv1"))
                        peer.getParameters().setVersion(SnmpSMI.SNMPV1);
                    else if (version.equalsIgnoreCase("snmpv2"))
                        peer.getParameters().setVersion(SnmpSMI.SNMPV2);
                }

                // "vbvalue" parm
                //
                if (qualifiers.get("vbvalue") != null) {
                    expectedValue = (String) qualifiers.get("vbvalue");
                }
            }

            session = new SnmpSession(peer);

            String oid = ParameterMap.getKeyedString(qualifiers, "vbname", DEFAULT_OID);

            SnmpResponseHandler handler = new SnmpResponseHandler();

            SnmpPduPacket out = null;
            if (peer.getParameters().getVersion() == SnmpSMI.SNMPV1)
                out = new SnmpPduRequest(SnmpPduPacket.GET, new SnmpVarBind[] { new SnmpVarBind(oid) });
            else if (peer.getParameters().getVersion() == SnmpSMI.SNMPV2)
                out = new SnmpPduBulk(1, // nonRepeaters
                        0, // maxRepetitions
                        new SnmpVarBind[] { new SnmpVarBind(oid) });

            synchronized (handler) {
                session.send(out, handler);
                try {
                    handler.wait((long) ((peer.getRetries() + 1) * peer.getTimeout()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (handler.getResult() != null) {
                String retrievedValue = handler.getResult().getValue().toString();

                // If no expected value to check against isSupported is true
                if (expectedValue == null) {
                    isSupported = true;
                }
                // If there is an expected value, verify it matches the
                // retrieved value
                else {
                    if (retrievedValue.equals(expectedValue))
                        isSupported = true;
                }

                if (qualifiers != null) {
                    qualifiers.put("vbname", handler.getResult().getName().toString());
                    qualifiers.put("vbvalue", retrievedValue);
                    if (peer.getParameters().getVersion() == SnmpSMI.SNMPV1)
                        qualifiers.put("version", "SNMPv1");
                    else if (peer.getParameters().getVersion() == SnmpSMI.SNMPV2)
                        qualifiers.put("version", "SNMPv2");
                }
            }
        } catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        } finally {
            if (session != null)
                session.close();
        }

        return isSupported;
    }
}
