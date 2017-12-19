/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.xml;

import javax.ejb.*;
import javax.naming.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.rmi.*;
import java.io.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface XmlCoDec extends EJBLocalObject {

    public Object decodeParameter(Object newParameter, Element rootElement, String tag) throws javax.oss.IllegalArgumentException ;
    public Object decodeParameter(Object newParameter, Element rootElement, String tag, int position) throws javax.oss.IllegalArgumentException ;
    public Object decodeParameter(Object newParameter, Element rootElement, String tag, Class type) throws javax.oss.IllegalArgumentException ;
/**
 * @param newParameter  if specified this object is used and filled with values according to document, 
 *                      null if a new object shall be created.
 * @param rootElement   the xml element which contains the element which shall be decoded
 * @param tag           the tag of the element which shall be decoded
 * @param nsURI         the namespace URI of the tag
 * @param position      use the n-th element which name is "tag", if not specified, first element is used
 * @param type          type of java object which shall be decoded, if null xsi:type is used to determine type dynamically
 */    
    public Object decodeParameter(Object newParameter, Element rootElement, String nsURI, String tag, int position, Class type) throws javax.oss.IllegalArgumentException ;
    public void encodeParameter(Document doc, Element parent, Object value);
    public void encodeParameter(Document doc, Element parent, Class type, Object value);
    public void encodeParameter(Document doc, Element parent, String name, Object value);
    public void encodeParameter(Document doc, Element parent, String name, Class type, Object value);
    public Document getXmlTemplate(String name) ;
    public String toString(Document aDoc);

}
