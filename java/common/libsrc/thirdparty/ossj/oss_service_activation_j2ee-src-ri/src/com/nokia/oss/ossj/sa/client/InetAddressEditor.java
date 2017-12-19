/*
 * InetAddressPropertyEditor.java
 *
 * Created on 8. März 2001, 16:28
 */

package com.nokia.oss.ossj.sa.client;

/**
 *
 * @author  aebbert
 * @version 
 */
public class InetAddressEditor extends java.beans.PropertyEditorSupport {

    /** Creates new InetAddressPropertyEditor */
    public InetAddressEditor() {
    }

    public String getAsText() {
        if (getValue() == null || getValue().equals("")) return null;
        return ((java.net.InetAddress)getValue()).getHostAddress();
    }

    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "0.0.0.0";
        } 
        try {
            setValue(java.net.InetAddress.getByName(text));
        } catch (java.net.UnknownHostException uhe) {
            throw new IllegalArgumentException("no valid IP");
        }
    }
}
