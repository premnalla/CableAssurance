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
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Category;
import org.opennms.core.fiber.Fiber;
import org.opennms.core.utils.ThreadCategory;

/**
 * This class implement the server features necessary to receive events from
 * incomming connections.
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http;//www.opennms.org">OpenNMS </a>
 * 
 */
final class TcpServer implements Runnable {
    /**
     * The default TCP/IP port where the server listens for connections. Each
     * connection to the server will be processed by its own thread.
     */
    static final int TCP_PORT = 5817;

    /**
     * The TCP/IP Port for the server socket's binding. By default this should
     * be equal to {@link #TCP_PORT TCP_PORT}but it can be overridden in the
     * constructor.
     */
    private int m_tcpPort;

    /**
     * The TCP/IP Port for the server socket's binding. By default this should
     * be equal to {@link #TCP_PORT TCP_PORT}but it can be overridden in the
     * constructor.
     */
    private ServerSocket m_tcpSock;

    /**
     * When set true the server thread will exit.
     */
    private volatile boolean m_stop;

    /**
     * <p>
     * The list of receivers that are currently being processed. Each instance
     * in this list is of type {@link java.lang.Thread Thread}and will remain
     * in the list so long as it's alive.
     * </p>
     * 
     * <p>
     * This list is periodically cleaned by the main server thread.
     * </p>
     */
    private LinkedList m_receivers;

    /**
     * The thread which is executing the server context
     */
    private Thread m_context;

    /**
     * The parent fiber.
     */
    private Fiber m_parent;

    /**
     * The list of event handlers
     */
    private List m_handlers;

    /**
     * The logging context
     */
    private String m_logPrefix;

    /**
     * the events per connection
     */
    private int m_recsPerConn;

    /**
     * Constructs a new instance of an server to handle incomming tcp
     * connections.
     * 
     * @param parent
     *            The parent fiber
     */
    TcpServer(Fiber parent, List handlers) throws IOException {
        m_parent = parent;
        m_tcpPort = TCP_PORT;
        m_tcpSock = new ServerSocket(m_tcpPort);
        m_receivers = new LinkedList();
        m_stop = false;
        m_context = null;
        m_handlers = handlers;
        m_logPrefix = org.opennms.netmgt.eventd.Eventd.LOG4J_CATEGORY;
        m_recsPerConn = TcpEventReceiver.UNLIMITED_EVENTS;
    }

    /**
     * Constructs a new instance of an server to handle incomming tcp
     * connections.
     * 
     * @param parent
     *            The parent fiber
     * @param port
     *            The port to listen on.
     */
    TcpServer(Fiber parent, List handlers, int port) throws IOException {
        m_parent = parent;
        m_tcpPort = port;
        m_tcpSock = new ServerSocket(m_tcpPort);
        m_receivers = new LinkedList();
        m_stop = false;
        m_context = null;
        m_handlers = handlers;
        m_logPrefix = org.opennms.netmgt.eventd.Eventd.LOG4J_CATEGORY;
        m_recsPerConn = TcpEventReceiver.UNLIMITED_EVENTS;
    }

    /**
     * This is called inform the current execution of this object is stopped.
     * Once called the object cannot be reused in another thread.
     */
    void stop() throws InterruptedException {
        Category log = ThreadCategory.getInstance(getClass());
        boolean isTracing = log.isDebugEnabled();

        if (isTracing)
            log.debug("stop method invoked.");

        // Stop this context
        //
        m_stop = true;
        if (m_context != null) {
            if (isTracing)
                log.debug("Interrupting and joining context thread " + m_context.getName());

            m_context.interrupt();
            m_context.join();

            if (isTracing)
                log.debug("Thread context stopped and joined " + m_context.getName());

            m_context = null;
        }

        if (isTracing) {
            log.debug("Attempting to stop and join all stream handlers");
            log.debug("There are " + m_receivers.size() + " receivers");
        }

        // stop all the receivers
        //
        int ndx = 0; // for tracing!
        Iterator i = m_receivers.iterator();
        while (i.hasNext()) {
            TcpStreamHandler t = (TcpStreamHandler) i.next();
            if (t.isAlive()) {
                if (isTracing)
                    log.debug("Calling stop on handler index " + ndx);

                t.stop();

                if (isTracing)
                    log.debug("Stopped handler index " + ndx);
            }
            ndx++;
            i.remove();
        }

        if (isTracing)
            log.debug("All TCP Handlers are stopped and removed");
    }

    /**
     * Returns true if this runnable is executing.
     */
    boolean isAlive() {
        boolean rc = false;
        if (m_context != null)
            rc = m_context.isAlive();

        return rc;
    }

    /**
     * The logic execution context to accept and process incomming connection
     * requests. When a new connection is received a new thread of control is
     * crated to process the connection. This method encapsulates that control
     * logic so that it can be executed in it's own java thread.
     */
    public void run() {
        // get the thread context for
        // the ability to stop the process
        //
        m_context = Thread.currentThread();
        synchronized (m_context) {
            m_context.notifyAll();
        }

        // get the log information
        //
        ThreadCategory.setPrefix(m_logPrefix);
        Category log = ThreadCategory.getInstance(this.getClass());
        boolean isTracing = log.isDebugEnabled();

        // check to see if the thread has already been stopped.
        //
        if (m_stop) {
            if (isTracing)
                log.debug("Stop flag set on thread startup");

            try {
                if (m_tcpSock != null)
                    m_tcpSock.close();

                if (isTracing)
                    log.debug("The socket has been closed");
            } catch (Exception e) {
                if (isTracing)
                    log.debug("An exception occured closing the socket", e);
            }

            if (isTracing)
                log.debug("Thread exiting");

            return;
        }

        // get the logger
        //
        if (isTracing)
            log.debug("Server connection processor started on port " + m_tcpPort);

        //
        // Set the initial timeout on the socket. This allows
        // the thread to wakeup every 1/2 second and check the
        // shutdown status.
        //
        try {
            m_tcpSock.setSoTimeout(500);
        } catch (SocketException e) {
            if (!m_stop)
                log.warn("An I/O exception occured setting the socket timeout", e);

            if (isTracing)
                log.debug("Thread exiting due to socket error");

            return;
        }
        // used to avoid seeing the trace message repeatedly
        //
        boolean ioInterrupted = false;

        //
        // Check the status of the fiber and respond
        // correctly. When the fiber enters a STOPPED or
        // STOP PENDING state then shutdown occurs by exiting
        // the while loop
        //
        while (m_parent.getStatus() != Fiber.STOPPED && m_parent.getStatus() != Fiber.STOP_PENDING && !m_stop) {
            try {
                if (isTracing && !ioInterrupted)
                    log.debug("Waiting for new connection");

                // Get the newbie socket connection from the client.
                // After accepting the connection start up a thread
                // to process the request
                //
                Socket newbie = m_tcpSock.accept();
                ioInterrupted = false; // reset the flag

                // build a connection string for
                // the thread identifier
                //
                StringBuffer connection = new StringBuffer(newbie.getInetAddress().getHostAddress());
                connection.append(":").append(newbie.getPort());

                if (isTracing)
                    log.debug("New connection accepted from " + connection);

                // start a new handler
                //
                TcpStreamHandler handler = new TcpStreamHandler(m_parent, newbie, m_handlers, m_recsPerConn);
                Thread processor = new Thread(handler, m_parent.getName() + "[" + connection + "]");
                synchronized (processor) {
                    processor.start();
                    try {
                        processor.wait();
                    } catch (InterruptedException e) {
                        log.warn("The thread was interrupted", e);
                    }
                }

                if (isTracing)
                    log.debug("A new stream handler thread has been started");

                // add the handler to the list
                //
                m_receivers.add(handler);
            } catch (InterruptedIOException e) {
                // do nothing on interrupted I/O
                // DON'T Continue, the end of the loop
                // checks and removes terminated threads
                //
                ioInterrupted = true;
            } catch (IOException e) {
                log.error("Server Socket I/O Error", e);
                break;
            }

            // go through the threads in the list of
            // receivers and find the dead ones. When
            // their no longer alive just remove them
            // from the list.
            //
            Iterator i = m_receivers.iterator();
            while (i.hasNext()) {
                TcpStreamHandler t = (TcpStreamHandler) i.next();
                if (!t.isAlive())
                    i.remove();
            }
        }

        // Either a fatal I/O error has occured or the
        // service has been stopped.
        //
        try {
            if (isTracing)
                log.debug("closing the server socket connection");

            m_tcpSock.close();
        } catch (Throwable t) {
            log.error("An I/O Error Occcured Closing the Server Socket", t);
        }

        // Log the termination of this runnable
        //
        if (isTracing)
            log.debug("TCP Server Shutdown");

    } // end run();

    public void setLogPrefix(String prefix) {
        m_logPrefix = prefix;
    }

    public void setEventsPerConnection(int number) {
        m_recsPerConn = number;
    }

} // end TcpServer Class

