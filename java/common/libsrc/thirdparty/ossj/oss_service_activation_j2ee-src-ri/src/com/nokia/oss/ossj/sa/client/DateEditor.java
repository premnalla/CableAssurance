/*
 * DateEditor.java
 *
 * Created on 8. März 2001, 16:37
 */

package com.nokia.oss.ossj.sa.client;

import java.util.Date;
import java.text.DateFormat;
/**
 *
 * @author  aebbert
 * @version 
 */
public class DateEditor extends java.beans.PropertyEditorSupport {

    //private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    
    /** Creates new DateEditor */
    public DateEditor() {
    }

    public String getAsText() {
        if (getValue()==null || getValue().equals("")) return null;
        return df.format((Date)getValue());
    }
    
    public void setAsText(String text) throws IllegalArgumentException {
        if (text==null || text.equals("")) {
            setValue(new Date());
        }
        try {
            setValue(df.parse(text));
        } catch (java.text.ParseException pe) {
            throw new IllegalArgumentException(pe.toString());
        }
    }
}
