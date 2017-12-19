package com.nokia.oss.ossj.sa.client;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.beans.PropertyEditor;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.util.Arrays;
import java.awt.Component;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0.1
 */

public class UserInteractUtil {
    static final String DIVIDER = "\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\###############===============---------------";
    
    private JFrame rootFrame = null;
    private Logger myLog = new Logger(0);

    public UserInteractUtil() {
        // do nothing
    }

    public void setRootFrame(JFrame frame) {
        rootFrame = frame;
    }
    public void setLogger(Logger aLogger) {
        myLog = aLogger;
    }

    public static void main(String[] args) throws Exception {
        UserInteractUtil tester = new UserInteractUtil();
        Integer i = new Integer(5);
        i = (Integer)tester.editProperty("Enter new value", i);
        System.out.println(i);
        // test select
        /*    int choice;

        String[] c1 = {"First"};
        choice = select("Test Case 1: Enter first '2' and then '1':", c1);
        my_assert(choice == 0, "tc 1");

        String[] c2 = {"First", "Second"};
        choice = select("Test Case 2: Enter first 'blah' and the 'Second'", c2);
        my_assert(choice == 1, "tc 2");

        MyProcessData data = new MyProcessData();
        data.putValue("intValue", new Integer(10));
        data.putValue("stringValue", "");
        data.putValue("name", "Stefan");
        readProcessData("Test case 3: Change stringValue->'hello' and intValue->'0'", data);
        my_assert(data.size() == 3, "tc 3");
        my_assert(((Integer)data.getValue("intValue")).intValue() == 0, "tc 3");
        my_assert(((String) data.getValue("stringValue")).equals("hello"), "tc 3");
        my_assert(((String) data.getValue("name")).equals("Stefan"), "tc 3");

        System.out.println("Module test of 'util' passed successfully");
         */
    }

    static void my_assert(boolean b, String id) {
        if (!b) {
            throw new IllegalArgumentException(id);
        }
    }

    private BufferedReader bufferedSystemInReader;

    public BufferedReader getSystemInReader() {
        if (bufferedSystemInReader == null) {
            bufferedSystemInReader = new BufferedReader(new InputStreamReader(System.in));
        }
        return bufferedSystemInReader;
    }

    private Class getPrimitiveType(Object obj) {
        try {
            java.lang.reflect.Field primClassField = obj.getClass().getDeclaredField("TYPE");
            return (Class)primClassField.get(null); //maybe null, because TYPE is static
        } catch (NoSuchFieldException nsfe) {
        } catch (SecurityException se) {
        } catch (IllegalAccessException iae) {
        } catch (IllegalArgumentException iae) {
        }
        return null;
    }
    
    private Object showPropertyEditor(PropertyEditor pe, String title, Object obj) {
        if (pe.supportsCustomEditor()) {
            // show cursom editor in dialog
            return null;
        } else {
            String[] tags = pe.getTags();
            if (tags != null) {
                // show drop down field
                return null;
            } else {
                if (obj instanceof String) {
                    pe.setAsText((String)obj);
                } else {
                    pe.setValue(obj);
                }
                boolean ok = false;
                while (!ok) {
                    JOptionPane    pane = new JOptionPane(title, JOptionPane.QUESTION_MESSAGE,
                                                  JOptionPane.OK_CANCEL_OPTION, null,
                                                  null, null);
                    // shows JTextField
                    pane.setWantsInput(true);
                    // initialise JTextField
                    pane.setInitialSelectionValue(pe.getAsText());
                    JDialog dialog = pane.createDialog(rootFrame, "Please enter");
                    dialog.show();
                    // read value
                    Object value = pane.getInputValue();
                    if(value == JOptionPane.UNINITIALIZED_VALUE)
                    {
                        return null;
                    }

                    try {
                        pe.setAsText(value.toString());
                        ok = true;
                    } catch (IllegalArgumentException iae) {
                        info(iae.toString());
                        ok = false;
                    }
                }
                return pe.getValue();
            }
        }
    }
    public Object createProperty(String question, Class type) {
        PropertyEditor pe = java.beans.PropertyEditorManager.findEditor(type);
        if (pe == null) {
            throw new IllegalArgumentException("Could not find editor for\n"+type);
        } else {
            return showPropertyEditor(pe, question, null);
        }
    }
    
    public Object editProperty(String question, Object object) {
        PropertyEditor pe;
        Class objClass = getPrimitiveType(object);
        if (objClass == null) {
            objClass = object.getClass();
        }
        pe = java.beans.PropertyEditorManager.findEditor(objClass);
        if (pe == null) {
            throw new IllegalArgumentException("Could not find editor for\n"+object.getClass());
        } else {
            return showPropertyEditor(pe, question, object);
        }
    }
    
    public String queryUserString(String question, String defaultAnswer) {
        return (String)editProperty(question, defaultAnswer);
        /*
        String returnValue = null;
        while (returnValue == null) {
            try {
                System.out.println(DIVIDER);
                System.out.println(question);
                if (defaultAnswer!= null) {
                    System.out.println("Press Enter to acceppt default \""+defaultAnswer+"\"");
                }
                System.out.print("? ");
                String answer = getSystemInReader().readLine();
                System.out.println("...accepted, thanks");
                if ( (answer == null || answer.equals("")) && defaultAnswer!=null ) {
                    myLog.log("returning defaultAnswer "+defaultAnswer+" for userQuery \""+question+"\"");
                    returnValue = defaultAnswer;
                } else {
                    myLog.log("returning user answer "+answer+" for userQuery \""+question+"\"");
                    returnValue = answer;
                }
            } catch (IOException ioe) {
                System.out.println("Error: "+ioe);
            }
        }
        return returnValue;*/
   }

    public URL queryUserUrl(String question, URL defaultAnswer) {
        while (true) {
            try {
                System.out.println(DIVIDER);
                System.out.println(question);
                while ( true ) {
                    if (defaultAnswer!= null) {
                        System.out.println("Press Enter to acceppt default \""+defaultAnswer.toExternalForm()+"\"");
                    }
                    System.out.print("? ");
                    String answer = getSystemInReader().readLine();
                    if ( (answer == null || answer.equals("")) && defaultAnswer!=null ) {
                        System.out.println("...accepted, thanks");
                        return defaultAnswer;
                    } else {
                        try {
                            URL answerUrl = new URL(answer);
                            System.out.println("...accepted, thanks");
                            return answerUrl;
                        } catch (MalformedURLException mue) {
                            System.out.println("\""+answer+"\" is not a valid URL");
                        }
                    }
                }
            } catch (IOException ioe) {
                System.out.println("Error: "+ioe);
            }
        }
    }

    public int select(String headline, String[] choices) {
        JOptionPane query = new JOptionPane(headline, JOptionPane.QUESTION_MESSAGE);
        OptionList ol = new OptionList();
        ol.setJOptionPane(query);
        ol.setValues(Arrays.asList(choices));
        Component[] options = new Component[1];
        options[0] = ol;
        query.setOptions(options);
        JDialog dialog = query.createDialog(null, "Please choose one");
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                //System.exit(0);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
        return ((Integer)query.getValue()).intValue();
    }

    public int selectOld(String headline, String[] choices) {
        // print menu
        System.out.println(DIVIDER);
        System.out.println(headline);
        for (int i = 1; i <= choices.length; i++) {
            System.out.println("\t" + i + ") " + choices[i-1]);
        }

        // allow the user to select
        int choice = 0;
        while (choice == 0) {
            String line="";
            // repeat until line contains value
            while (line==null || line.equals("")) {
                System.out.print("\tSelect number: ");
                try {
                    line = getSystemInReader().readLine();
                } catch (IOException ioe) {
                    myLog.log(ioe.toString());
                    line = "";
                }
            }
            // try text
            for (int i = 1; i <= choices.length; i++) {
                if (choices[i-1].equals(line)) {
                    choice = i;
                }
            }
            // try number
            try {
                choice = Integer.parseInt(line);
                if (! (1 <= choice && choice <= choices.length)) {
                    choice = 0;
                }
            }
            catch (NumberFormatException e) {
            }
        }
        return choice-1;
    }
    
    public void info(String text) {
        JOptionPane.showMessageDialog(rootFrame,text);
    }
        
    
    /*
    static public void readProcessData(String heading, ProcessData data) throws Exception {

    String[] keys = data.nameArray();

    // show process data and let the user change one value,
    // until zero is entered.
    int choice = -1;
    while (choice != 0) {
    String[] display = new String[data.size()+1];
    display[0] = "[for exit]";
    String[] dataDisplay = convertProcessDataToArray(data);
    for (int i = 0; i < dataDisplay.length; i++) {
    display[i+1] = dataDisplay[i];
    }

    // ask user to select one value
    choice = select(heading, display);
    if (choice != 0) { // zero to exit loop
    // ask for the value
    Object value = data.getValue(keys[choice-1]);
    System.out.print("Enter new " + value.getClass().toString().substring(16) +
    " for key '" + keys[choice-1] + "': ");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String stringValue = in.readLine();
    // set the new value
    try {
    if (value.getClass() == String.class) {
    value = stringValue;
    } else if (value.getClass() == Boolean.class) {
    value = Boolean.valueOf(stringValue);
    } else if (value.getClass() == Byte.class) {
    value = Byte.valueOf(stringValue);
    } else if (value.getClass() == Short.class) {
    value = Short.valueOf(stringValue);
    } else if (value.getClass() == Integer.class) {
    value = Integer.valueOf(stringValue);
    } else if (value.getClass() == Long.class) {
    value = Long.valueOf(stringValue);
    } else if (value.getClass() == Float.class) {
    value = Float.valueOf(stringValue);
    } else if (value.getClass() == Double.class) {
    value = Double.valueOf(stringValue);
    }
    data.putValue(keys[choice-1], value);
    }
    catch (NumberFormatException e) {
    System.out.println("The value you entered could not be converted.");
    }
    } // end if 'some value should be changed'
    } // end entry loop
    }

    static void printProcessData(ProcessData data) {
    String[] display = convertProcessDataToArray(data);
    for (int i = 0; i < display.length; i++) {
    System.out.println(display[i]);
    }
    }

    static String[] convertProcessDataToArray(ProcessData data) {
    String[] keys = data.nameArray();

    // calculate max length of key for correct column layout
    int maxKeyLength = 1;
    for (int i = 0; i < keys.length; i++) {
    if (keys[i].length() > maxKeyLength) {
    maxKeyLength = keys[i].length();
    }
    }

    String[] result = new String[data.size()];

    // show all key/value pairs
    StringBuffer b = new StringBuffer();
    for (int i = 0; i < keys.length; i++) {
    b.append(keys[i]);
    for (int ii=b.length(); ii<=maxKeyLength; ii++) {
    b.append(" ");
    }
    b.append(data.getValue(keys[i]).toString());
    result[i] = b.toString();
    b.setLength(0);
    }
    return result;
    }

     */
}
