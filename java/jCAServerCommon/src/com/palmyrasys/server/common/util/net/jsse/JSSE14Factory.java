/*
 *  Copyright 1999-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.palmyrasys.server.common.util.net.jsse;

import java.net.Socket;
import javax.net.ssl.SSLSocket;

import com.palmyrasys.server.common.util.net.SSLSupport;
import com.palmyrasys.server.common.util.net.ServerSocketFactory;

/**
 * Implementation class for JSSEFactory for JSSE 1.1.x (that ships with the
 * 1.4 JVM).
 *
 * @author Bill Barker
 */

class JSSE14Factory implements JSSEFactory {

    JSSE14Factory() {
    }

    public ServerSocketFactory getSocketFactory() {
	return new JSSE14SocketFactory();
    }

    public SSLSupport getSSLSupport(Socket socket) {
	return new JSSE14Support((SSLSocket)socket);
    }
}
