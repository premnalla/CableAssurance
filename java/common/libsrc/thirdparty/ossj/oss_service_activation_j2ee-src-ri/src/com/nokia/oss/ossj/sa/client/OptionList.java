package com.nokia.oss.ossj.sa.client;

import javax.swing.*;

/**
 * Insert the type's description here. Creation date: (10.11.00 21:29:58)
 * @author:
 */
public class OptionList extends JPanel {
    private JOptionPane ivjaJOptionPane = null;
    private JButton okButton = null;
    private JPanel ivjJPanel1 = null;
    private JScrollPane ivjJScrollPane1 = null;
    private java.util.List ivjreturnValues = null;
    private JList ivjoptionList = null;
    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == OptionList.this.getOkButton())
            connEtoM1(e);
        }
    }


    /** OptionList constructor comment. */
    public OptionList() {
        super();
        initialize();
    }

    /**
     * OptionList constructor comment.
     * @param layout java.awt.LayoutManager
     */
    public OptionList(java.awt.LayoutManager layout) {
        super(layout);
    }

    /**
     * OptionList constructor comment.
     * @param layout java.awt.LayoutManager
     * @param isDoubleBuffered boolean
     */
    public OptionList(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * OptionList constructor comment.
     * @param isDoubleBuffered boolean
     */
    public OptionList(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * connEtoM1:  (JButton2.action.actionPerformed(java.awt.event.ActionEvent) -->
     * aJOptionPane.setValue(Ljava.lang.Object;)V)
     * @param arg1 java.awt.event.ActionEvent
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private void connEtoM1(java.awt.event.ActionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            if ((getreturnValueMap() != null)) {
                getaJOptionPane().setValue(new Integer(getoptionList().getSelectedIndex()));
            }
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the aJOptionPane property value.
     * @return javax.swing.JOptionPane
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private javax.swing.JOptionPane getaJOptionPane() {
        // user code begin {1}
        // user code end
        return ivjaJOptionPane;
    }

    /**
     * Return the JButton2 property value.
     * @return javax.swing.JButton
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private javax.swing.JButton getOkButton() {
        if (okButton == null) {
            try {
                okButton = new javax.swing.JButton();
                okButton.setName("JButton2");
                okButton.setText("OK");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return okButton;
    }

    /**
     * Method generated to support the promotion of the jOptionPane attribute.
     * @return javax.swing.JOptionPane
     */
    public javax.swing.JOptionPane getJOptionPane() {
        return getaJOptionPane();
    }

    /**
     * Return the JPanel1 property value.
     * @return javax.swing.JPanel
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private javax.swing.JPanel getJPanel1() {
        if (ivjJPanel1 == null) {
            try {
                ivjJPanel1 = new javax.swing.JPanel();
                ivjJPanel1.setName("JPanel1");
                ivjJPanel1.setLayout(new java.awt.FlowLayout());
                getJPanel1().add(getOkButton(), getOkButton().getName());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel1;
    }

    /**
     * Return the JScrollPane1 property value.
     * @return javax.swing.JScrollPane
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private javax.swing.JScrollPane getJScrollPane1() {
        if (ivjJScrollPane1 == null) {
            try {
                ivjJScrollPane1 = new javax.swing.JScrollPane();
                ivjJScrollPane1.setName("JScrollPane1");
                getJScrollPane1().setViewportView(getoptionList());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane1;
    }

    /**
     * Return the JList1 property value.
     * @return javax.swing.JList
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private javax.swing.JList getoptionList() {
        if (ivjoptionList == null) {
            try {
                ivjoptionList = new javax.swing.JList();
                ivjoptionList.setName("optionList");
                ivjoptionList.setBounds(0, 0, 300, 60);
                ivjoptionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjoptionList;
    }

    /**
     * Return the returnValues property value.
     * @return java.util.TreeMap
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private java.util.List getreturnValueMap() {
        // user code begin {1}
        // user code end
        return ivjreturnValues;
    }

    /**
     * Method generated to support the promotion of the valueMap attribute.
     * @return java.util.TreeMap
     */
    public java.util.List getValueMap() {
        return getreturnValueMap();
    }

    /**
     * Called whenever the part throws an exception.
     * @param exception java.lang.Throwable
     */
    private void handleException(java.lang.Throwable exception) {
        /* Uncomment the following lines to print uncaught exceptions to stdout */

        // System.out.println("--------- UNCAUGHT EXCEPTION ---------");
        // exception.printStackTrace(System.out);
    }

    /**
     * Initializes connections
     * @exception java.lang.Exception The exception description.
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private void initConnections() throws java.lang.Exception {
        // user code begin {1}
        // user code end
        getOkButton().addActionListener(ivjEventHandler);
    }

    /** Initialize the class. */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("OptionList");
            setLayout(new java.awt.BorderLayout());
            setSize(270, 120);
            add(getJPanel1(), "South");
            add(getJScrollPane1(), "Center");
            initConnections();
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    public static void main(java.lang.String[] args) {
        try {
            if (args[0].equalsIgnoreCase("-jframe")) {
                JFrame frame = new javax.swing.JFrame();
                JOptionPane query = new JOptionPane("TEST", JOptionPane.QUESTION_MESSAGE);
                OptionList aOptionList;
                aOptionList = new OptionList();
                aOptionList.setJOptionPane(query);
                aOptionList.setValues(java.util.Arrays.asList(args));
                java.awt.Component[] options = new java.awt.Component[1];
                options[0] = aOptionList;
                query.setOptions(options);

                frame.setContentPane(aOptionList);
                frame.setSize(aOptionList.getSize());
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                    }
                });
                aOptionList.getOkButton().addActionListener( new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            } else if (args[0].equalsIgnoreCase("-jdialog")) {
                JOptionPane query = new JOptionPane("TEST", JOptionPane.QUESTION_MESSAGE);
                OptionList aOptionList;
                aOptionList = new OptionList();
                aOptionList.setJOptionPane(query);
                aOptionList.setValues(java.util.Arrays.asList(args));
                java.awt.Component[] options = new java.awt.Component[1];
                options[0] = aOptionList;
                query.setOptions(options);

                javax.swing.JDialog aDialog = query.createDialog(null, "TEST");
                aDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                    }
                });
                aOptionList.getOkButton().addActionListener( new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        //System.exit(0);
                    }
                });
                aDialog.setVisible(true);
                System.out.println(((Integer)query.getValue()).intValue());
                System.exit(0);
            } else if (args[0].equalsIgnoreCase("-simple")) {
            Object[] possibleValues = { "First", "Second", "Third" };
                Object selectedValue = javax.swing.JOptionPane.showInputDialog(null,
                "Choose one", "Input",
                javax.swing.JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
            }
        } catch (Throwable exception) {
            System.err.println("Exception occurred in main() of javax.swing.JPanel");
            exception.printStackTrace(System.out);
        }
    }

    /**
     * Set the aJOptionPane to a new value.
     * @param newValue javax.swing.JOptionPane
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private void setaJOptionPane(javax.swing.JOptionPane newValue) {
        if (ivjaJOptionPane != newValue) {
            try {
                javax.swing.JOptionPane oldValue = getaJOptionPane();
                ivjaJOptionPane = newValue;
                firePropertyChange("jOptionPane", oldValue, newValue);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        // user code begin {3}
        // user code end
    }

    /**
     * Method generated to support the promotion of the jOptionPane attribute.
     * @param arg1 javax.swing.JOptionPane
     */
    public void setJOptionPane(javax.swing.JOptionPane arg1) {
        setaJOptionPane(arg1);
    }

    /**
     * Set the returnValues to a new value.
     * @param newValue java.util.TreeMap
     */

    /* WARNING: THIS METHOD WILL BE REGENERATED. */

    private void setreturnValueMap(java.util.List newValue) {
        if (ivjreturnValues != newValue) {
            try {
                java.util.List oldValue = getreturnValueMap();
                ivjreturnValues = newValue;
                firePropertyChange("valueMap", oldValue, newValue);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        // user code begin {3}
        // user code end
    }

    /**
     * Method generated to support the promotion of the valueMap attribute.
     * @param arg1 java.util.TreeMap
     */
    public void setValues(java.util.List values) {
        setreturnValueMap(values);
        getoptionList().setListData(values.toArray());
    }
}
