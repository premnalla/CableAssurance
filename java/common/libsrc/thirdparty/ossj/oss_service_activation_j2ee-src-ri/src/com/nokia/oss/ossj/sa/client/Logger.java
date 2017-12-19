/*
 * Logger.java
 *
 * Created on 19. Februar 2001, 17:17
 */

package com.nokia.oss.ossj.sa.client;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.JScrollPane;

/**
 *
 * @author  aebbert
 * @version
 */
public class Logger extends Object {

    public static final int DEFAULT = 0;
    public static final int DETAIL = 1;
    public static final int EXCEPTION = 2;
    private int threshold;
    private JFrame outputDialog;
    private java.io.PrintStream out;

    private class JTextAreaOutputStream extends java.io.OutputStream {
        private JTextArea textArea;
        public JTextAreaOutputStream (JTextArea textArea) {
            this.textArea = textArea;
        }

        public void write(int b)throws java.io.IOException {
            char[] chars = new char[1];
            chars[0] = (char)b;
            textArea.append(new String(chars));
        }
        public void write(byte b[], int off, int len) throws java.io.IOException {
            textArea.append(new String(b, off, len));
        }
    }

    /** Creates new Logger */
    public Logger(int threshold) {
        this.threshold = threshold;
        out = System.out;
    }

    public void setRootFrame(JFrame frame) {
        outputDialog = new JFrame("log");
        JTextArea textArea = new JTextArea("",25,80);
        out = new java.io.PrintStream(new JTextAreaOutputStream(textArea));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
        scrollPane.getViewport().addChangeListener( new javax.swing.event.ChangeListener() {
            private int lastheight;
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                JViewport aViewport = (JViewport)e.getSource();
                // height of complete textArea
                int totalheight = aViewport.getViewSize().height;
                if (totalheight!=lastheight) {
                    lastheight = totalheight;
                    // height of visible part
                    int viewheight = aViewport.getExtentSize().height;
                    // always scroll down to newest entries
                    aViewport.setViewPosition(
                        new java.awt.Point(0,Math.max(0,totalheight-viewheight))
                    );
                } 
            }
        });
        //scrollPane.add(textArea);
        outputDialog.getContentPane().add(scrollPane);
        outputDialog.pack();
        outputDialog.setVisible(true);
    }
    
    public void setOutputFile(java.io.File aFile) {
        try {
            out = new java.io.PrintStream(new java.io.FileOutputStream(aFile));
        } catch (java.io.FileNotFoundException fnfe) {
            log(EXCEPTION, "could not find logfile "+aFile);
        }
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;

    }

    public void log(String what) {
        log(0,what);
    }
    
    public void log(Exception e) {
        log(EXCEPTION, e.toString());
        e.printStackTrace();
    }

    public void log(int priority, String what) {
        if ( 0 <= priority && priority <= threshold) {
            logAlways (what);
        }
    }

    public void logAlways(String what) {
        out.println(what);
        out.flush();
    }
}
