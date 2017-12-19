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

package org.opennms.netmgt.eventd.adaptors.tcp;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.eventd.adaptors.EventHandler;
import org.opennms.netmgt.eventd.adaptors.EventHandlerMBeanProxy;
import org.opennms.netmgt.eventd.adaptors.EventReceiver;

/**
 * This class is the access point for the agents to hook into the event queue.
 * This fiber sets up an server socket that accepts incomming connections on the
 * configured port (port 5814 by default).
 * 
 * When a connection is established a new thread is started to process the
 * socket connection. The event document is decoded and each of the events are
 * passed to the handlers. Based upon the action of the handlers an event recipt
 * is generated and sent to the remote client.
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http;//www.opennms.org">OpenNMS </a>
 * 
 */
public final class TcpEventReceiver implements EventReceiver, TcpEventReceiverMBean {
    /**
     * The value that defines unlimited events per connection.
     */
    static final int UNLIMITED_EVENTS = -1;

    /**
     * The main server thread. This thread of execution is used to handle all
     * incomming connection requests.
     */
    private Thread m_worker;

    /**
     * The server socket
     */
    private TcpServer m_server;

    /**
     * The registered list of event handlers. Each incomming event will be
     * passed to all event handlers. The event handlers <em>MUST NOT</em>
     * modify the passed event.
     */
    private List m_handlers;

    /**
     * The fiber's status.
     */
    private volatile int m_status;

    /**
     * The TCP server to listen on
     */
    private int m_tcpPort;

    /**
     * The logging prefix
     */
    private String m_logPrefix;

    /**
     * The number of event records per connection.
     */
    private int m_recsPerConn;

    /**
     * Constructs a new TCP/IP event receiver on the default TCP/IP port. The
     * server socket allocation is delayed until the fiber is actually started.
     */
    public TcpEventReceiver() throws IOException {
        m_handlers = new ArrayList(3);
        m_status = START_PENDING;
        m_tcpPort = TcpServer.TCP_PORT;
        m_server = null;
        m_worker = null;
        m_logPrefix = null;
        m_recsPerConn = UNLIMITED_EVENTS;
    }

    /**
     * Constructs a new TCP/IP event receiver on the passed port. The server
     * socket allocation is delayed until the fiber is actually started.
     * 
     * @param port
     *            The binding port for the TCP/IP server socket.
     */
    public TcpEventReceiver(int port) throws IOException {
        m_handlers = new ArrayList(3);
        m_status = START_PENDING;
        m_tcpPort = port;
        m_server = null;
        m_worker = null;
        m_logPrefix = null;
        m_recsPerConn = UNLIMITED_EVENTS;
    }

    /**
     * Allocates the server socket and starts up the server socket processor
     * thread. If an error occurs allocating the server socket or the Fiber is
     * in an erronous state then a
     * {@link java.lang.RuntimeException runtime exception}is thrown.
     * 
     * @throws java.lang.reflect.UndeclaredThrowableException
     *             Thrown if an error occurs allocating the server socket.
     * @throws java.lang.RuntimeException
     *             Thrown if the fiber is in an erronous state or the underlying
     *             thread cannot be started.
     */
    public synchronized void start() {
        if (m_status != START_PENDING && m_status != STOPPED)
            throw new IllegalStateException("The Fiber is in an incorrect state");

        m_status = STARTING;
        try {
            m_server = new TcpServer(this, m_handlers, m_tcpPort);
            if (m_logPrefix != null) {
                m_server.setLogPrefix(m_logPrefix);
            }
            if (m_recsPerConn != UNLIMITED_EVENTS) {
                m_server.setEventsPerConnection(m_recsPerConn);
            }
        } catch (IOException e) {
            throw new UndeclaredThrowableException(e, "Error opening server socket");
        }
        m_worker = new Thread(m_server, "Event TCP Server[" + m_tcpPort + "]");

        try {
            m_worker.start();
        } catch (RuntimeException e) {
            m_worker.interrupt();
            m_status = STOPPED;
            throw e;
        }

        m_status = RUNNING;
    }

    /**
     * Stops the TCP/IP event receiver. This method will block until all the
     * children threads of this object are terminated and
     * {@link java.lang.Thread#join joined}.
     */
    public synchronized void stop() {
        if (m_status == STOPPED)
            return;
        if (m_status == START_PENDING) {
            m_status = STOPPED;
            return;
        }

        m_status = STOP_PENDING;

        // Stop the main server thread
        // then iterate over the connected
        // threads
        //
        try {
            m_server.stop();
        } catch (InterruptedException e) {
            Category log = ThreadCategory.getInstance(getClass());
            log.warn("Thread Interrupted while attempting to join server socket thread", e);
        }
        m_server = null;
        m_worker = null;

        m_status = STOPPED;
    }

    /**
     * Returns the name of this Fiber.
     */
    public String getName() {
        return "Event TCP Receiver[" + m_tcpPort + "]";
    }

    /**
     * Returns the status of this Fiber.
     */
    public int getStatus() {
        return m_status;
    }

    /**
     * Called when the fiber is initialized
     */
    public void init() {
        // do nothing
    }

    /**
     * Called when the fiber is destroyed
     */
    public void destroy() {
        // do nothing
    }

    /**
     * Adds a new event handler to receiver. When new events are received the
     * decoded event is passed to the handler.
     * 
     * @param handler
     *            A reference to an event handler
     * 
     */
    public void addEventHandler(EventHandler handler) {
        synchronized (m_handlers) {
            if (!m_handlers.contains(handler))
                m_handlers.add(handler);
        }
    }

    /**
     * Removes an event handler from the list of handler called when an event is
     * received. The handler is removed based upon the method
     * <code>equals()</code> inherieted from the <code>Object</code> class.
     * 
     * @param handler
     *            A reference to the event handler.
     * 
     */
    public void removeEventHandler(EventHandler handler) {
        synchronized (m_handlers) {
            m_handlers.remove(handler);
        }
    }

    public Integer getPort() {
        return new Integer(m_tcpPort);
    }

    public void setPort(Integer port) {
        if (m_status != START_PENDING && m_status != STOPPED)
            throw new IllegalStateException("The service is already running and cannot be modified");
        m_tcpPort = port.intValue();
    }

    public void addEventHandler(String name) throws MalformedObjectNameException, InstanceNotFoundException {
        addEventHandler(new EventHandlerMBeanProxy(new ObjectName(name)));
    }

    public void removeEventHandler(String name) throws MalformedObjectNameException, InstanceNotFoundException {
        removeEventHandler(new EventHandlerMBeanProxy(new ObjectName(name)));
    }

    public void setLogPrefix(String prefix) {
        m_logPrefix = prefix;
    }

    /**
     * The number of event records a new connection is allowed to send before
     * the connection is terminated by the server. The connection is always
     * terminated after an event receipt is generated, if one is required.
     * 
     * @param number
     *            The number of event records.
     */
    public void setEventsPerConnection(Integer number) {
        if (m_status != START_PENDING && m_status != STOPPED)
            throw new IllegalStateException("The managed bean is already running and its state cannot be changed");
        m_recsPerConn = number.intValue();
    }
}
