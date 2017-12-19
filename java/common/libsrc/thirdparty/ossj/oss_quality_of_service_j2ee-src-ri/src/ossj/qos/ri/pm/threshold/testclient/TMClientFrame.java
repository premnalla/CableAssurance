/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.testclient;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

//
//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Simple threshold monitor client.
 *
 * @author Henrik Lindstrom, Ericsson
 */
public class TMClientFrame extends JFrame {
    private BorderLayout borderLayout1 = new BorderLayout();
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jMenuFile = new JMenu();
    private JMenuItem jMenuItemExit = new JMenuItem();
    private JMenu jMenuHelp = new JMenu();
    private JMenuItem jMenuItemAbout = new JMenuItem();
    private JMenuItem jMenuItemHelp = new JMenuItem();
    private JPanel jPanelCenter = new JPanel();
    private JPanel jPanelSouth = new JPanel();
    private JLabel jLabelEricsson = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();

    /**
     * Ericsson logo.
     */
    private ImageIcon imageIcon = new ImageIcon( "ossj.qos.ri.pm/threshold/testclient/logo_ericsson.gif" );
//    private JTableHeader measurementHeader = new JTableHeader();
//    private JTableHeader alarmHeader = new JTableHeader();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanelOO = new JPanel();
    private JLabel jLabelObservableObjectClasses = new JLabel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JComboBox jComboBoxObservableObjectClasses = new JComboBox();
    private JLabel jLabelObservableObject = new JLabel();
    private JComboBox jComboBoxObservableObject = new JComboBox();
    private TitledBorder titledBorderMonitoredObject;
    private TitledBorder titledBorderObservableObjectAttributes;
    private JPanel jPanelThreshold = new JPanel();
    private TitledBorder titledBorderThreshold;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private ButtonGroup buttonGroupDirection = new ButtonGroup();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JPanel jPanelThresholdDefinition = new JPanel();
    private JComboBox jComboBoxGranularity = new JComboBox();
    private JLabel jLabelOffset = new JLabel();
    private JTextField jTextFieldOffset = new JTextField();
    private JLabel jLabelGranularity = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabelSchedule = new JLabel();
    private JPanel jPanelButtons = new JPanel();
    private JButton jButtonStop = new JButton();
    private FlowLayout flowLayout2 = new FlowLayout();
    private JButton jButtonStart = new JButton();
    private JLabel jLabelValue = new JLabel();
    private JTextField jTextFieldValue = new JTextField();
    private JLabel jLabelAlarmConfig = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabelObservableObjectAttribute = new JLabel();
    private JComboBox jComboBoxObservableObjectAttribute = new JComboBox();
    private JLabel jLabelSeconds = new JLabel();
    private JPanel jPanelDirection = new JPanel();
    private TitledBorder titledBorderDirection;
    private JRadioButton jRadioButtonDirectionFalling = new JRadioButton();
    private JRadioButton jRadioButtonDirectionRising = new JRadioButton();
    private FlowLayout flowLayout3 = new FlowLayout();
    private JPanel jPanelMeasurement = new JPanel();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private TitledBorder titledBorderMeasurment;
    private JPanel jPanelAlarm = new JPanel();
    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private TitledBorder titledBorderAlarm;
    private JScrollPane jScrollPaneMeasurement = new JScrollPane();
    private JTable jTableMeasurement = new JTable();
    private JScrollPane jScrollPaneAlarm = new JScrollPane();
    private JTable jTableAlarm = new JTable();
    private JLabel jLabel3 = new JLabel();
    private JTextField jTextFieldName = new JTextField();

    /**
     * Constructs a new client UI.
     */
    public TMClientFrame() {
        // my stuff
        URL ericssonLogoURL =
            ClassLoader.getSystemClassLoader().getResource(
                "ossj.qos.ri.pm/threshold/testclient/logo_ericsson.gif" );
        imageIcon = new ImageIcon( ericssonLogoURL, "Ericsson" );

        // init UI
        try {
            jbInit();
        }
        catch(Exception e) {
            Log.write(this,"TMClientFrame()","exception="+e);
            e.printStackTrace();
        }
    }

    /**
     * Auto generated init method by JBuilder. Do not edit!
     */
    private void jbInit() throws Exception {
        titledBorderMonitoredObject = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Performance Monitor");
        titledBorderObservableObjectAttributes = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178)),"Observable Object Attribute:");
        titledBorderThreshold = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Threshold");
        titledBorderDirection = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Direction");
        titledBorderMeasurment = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178)),"Measurement");
        titledBorderAlarm = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178)),"Alarm");
        jLabelAlarmConfig.setText("Alarm Config:");
        jTextFieldValue.setColumns(10);
        jTextFieldValue.setHorizontalAlignment(SwingConstants.TRAILING);
        jTextFieldValue.setNextFocusableComponent(jTextFieldOffset);
        jTextFieldValue.setText("0");
        jLabelValue.setText("Value:");
        jLabelValue.setAlignmentX((float) 0.5);
        jLabelValue.setToolTipText("");
        jLabelValue.setDisplayedMnemonic('0');
        jLabelValue.setLabelFor(jTextFieldValue);
        jButtonStart.setText("Create");
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        jButtonStop.setText("Remove");
        jPanelButtons.setLayout(flowLayout2);
        this.setDefaultCloseOperation(3);
        this.setJMenuBar(jMenuBar);
        this.setTitle("Threshold Monitor Client");
        this.getContentPane().setLayout(borderLayout1);
        jMenuFile.setText("File");
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItemExit_actionPerformed(e);
            }
        });
        jMenuHelp.setText("Help");
        jMenuItemAbout.setText("About Threshold Monitor Client");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItemAbout_actionPerformed(e);
            }
        });
        jMenuItemHelp.setText("Help");
        jMenuItemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItemHelp_actionPerformed(e);
            }
        });
        jLabelEricsson.setToolTipText("Ericsson Radio Systems AB");
        jLabelEricsson.setIcon(imageIcon);
        jLabelEricsson.setText("     ");
        jPanelSouth.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jPanelCenter.setLayout(gridBagLayout1);
        jLabelObservableObjectClasses.setAlignmentX((float) 0.5);
        jLabelObservableObjectClasses.setLabelFor(jComboBoxObservableObjectClasses);
        jLabelObservableObjectClasses.setText("Observabale Object Class:");
        jPanelOO.setLayout(gridBagLayout2);
        jLabelObservableObject.setAlignmentX((float) 0.5);
        jLabelObservableObject.setLabelFor(jComboBoxObservableObject);
        jLabelObservableObject.setText("Observable Object:");
        jPanelOO.setBorder(titledBorderMonitoredObject);
        jPanelOO.setPreferredSize(new Dimension(292, 90));
        titledBorderMonitoredObject.setTitle("Monitored object");
        jPanelThreshold.setBorder(titledBorderThreshold);
        jPanelThreshold.setLayout(gridBagLayout3);
        jPanelThresholdDefinition.setLayout(gridBagLayout4);
        jLabelOffset.setAlignmentX((float) 0.5);
        jLabelOffset.setLabelFor(jTextFieldOffset);
        jLabelOffset.setText("Offset:");
        jTextFieldOffset.setNextFocusableComponent(jPanelDirection);
        jTextFieldOffset.setText("0");
        jTextFieldOffset.setColumns(10);
        jTextFieldOffset.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabelGranularity.setAlignmentX((float) 0.5);
        jLabelGranularity.setText("Granularity:");
        jLabel1.setEnabled(false);
        jLabel1.setAlignmentX((float) 0.5);
        jLabel1.setText("ALWAYS (Can be configured)");
        jLabelSchedule.setAlignmentX((float) 0.5);
        jLabelSchedule.setText("Schedule:");
        jLabel2.setEnabled(false);
        jLabel2.setText("QUALITY OF SERVICE ALARM, minor severity. (Can be configured)");
        jLabelObservableObjectAttribute.setAlignmentX((float) 0.5);
        jLabelObservableObjectAttribute.setLabelFor(jComboBoxObservableObjectAttribute);
        jLabelObservableObjectAttribute.setText("Observable Object Attribute:");
        jLabelSeconds.setText("(seconds)");
        jPanelDirection.setBorder(titledBorderDirection);
        jPanelDirection.setNextFocusableComponent(jComboBoxGranularity);
        jPanelDirection.setLayout(flowLayout3);
        jRadioButtonDirectionFalling.setAlignmentX((float) 0.5);
        jRadioButtonDirectionFalling.setMargin(new Insets(0, 0, 0, 0));
        jRadioButtonDirectionFalling.setText("Falling");
        jRadioButtonDirectionRising.setAlignmentX((float) 0.5);
        jRadioButtonDirectionRising.setMargin(new Insets(0, 0, 0, 0));
        jRadioButtonDirectionRising.setSelected(true);
        jRadioButtonDirectionRising.setText("Rising");
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setVgap(0);
        jPanelMeasurement.setLayout(gridBagLayout5);
        jPanelMeasurement.setBorder(titledBorderMeasurment);
        jPanelMeasurement.setMinimumSize(new Dimension(0, 100));
        jPanelMeasurement.setPreferredSize(new Dimension(0, 100));
        jPanelAlarm.setLayout(gridBagLayout6);
        jPanelAlarm.setBorder(titledBorderAlarm);
        jPanelAlarm.setMinimumSize(new Dimension(0, 100));
        jPanelAlarm.setPreferredSize(new Dimension(0, 100));
        jLabel3.setText("Name:");
        jTextFieldName.setColumns(10);
        titledBorderMeasurment.setTitle("Performance Monitor Events");
        titledBorderAlarm.setTitle("Alarm Monitor Events");
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuHelp);
        jMenuFile.add(jMenuItemExit);
        jMenuHelp.add(jMenuItemHelp);
        jMenuHelp.add(jMenuItemAbout);
        this.getContentPane().add(jPanelCenter, BorderLayout.CENTER);
        jPanelCenter.add(jPanelOO,              new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 20));
        jPanelOO.add(jLabelObservableObjectClasses,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.getContentPane().add(jPanelSouth,  BorderLayout.SOUTH);
        jPanelSouth.add(jLabelEricsson, null);
        jPanelOO.add(jComboBoxObservableObjectClasses,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelOO.add(jLabelObservableObject,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelOO.add(jComboBoxObservableObject,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelOO.add(jLabelObservableObjectAttribute,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelCenter.add(jPanelThreshold,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanelThreshold.add(jPanelThresholdDefinition,        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabelValue,                      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jTextFieldValue,                     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabelOffset,                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jTextFieldOffset,               new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabelSchedule,                   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 0, 0), 1, 1));
        jPanelThresholdDefinition.add(jLabelGranularity,              new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jComboBoxGranularity,          new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabel1,              new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabelAlarmConfig,              new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 0, 0), 0, 0));
        jPanelThreshold.add(jPanelButtons,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        jPanelButtons.add(jButtonStart, null);
        jPanelButtons.add(jButtonStop, null);
        jPanelCenter.add(jPanelMeasurement,     new GridBagConstraints(0, 2, 1, 1, 0.0, 2.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabel2,              new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jLabelSeconds,        new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelOO.add(jComboBoxObservableObjectAttribute,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jPanelDirection,          new GridBagConstraints(2, 1, 2, 2, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelDirection.add(jRadioButtonDirectionRising, null);
        jPanelDirection.add(jRadioButtonDirectionFalling, null);
        jPanelThresholdDefinition.add(jLabel3,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        jPanelThresholdDefinition.add(jTextFieldName,   new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        jPanelCenter.add(jPanelAlarm,   new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jPanelMeasurement.add(jScrollPaneMeasurement,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPaneMeasurement.getViewport().add(jTableMeasurement, null);
        jPanelAlarm.add(jScrollPaneAlarm,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPaneAlarm.getViewport().add(jTableAlarm, null);
    }

    /**
     * Show about dialog.
     */
    void jMenuItemAbout_actionPerformed(ActionEvent e) {
        Log.write(this,Log.LOG_ALL,"jMenuItemAbout_actionPerformed()","called");

        JOptionPane.showMessageDialog(this, "Threshold monitor client.\n"
            + "OSS/J Quality of Service API (1.0).\n"
            + "Threshold Monitor Reference Implementation.\n\n"
            + "Copyright © 2001 Ericsson Radio Systems AB.\n"
            + "All rights reserved. Use is subject to license terms.\n\n"
            + "Author: Henrik Lindström, \n"
            + "Ericsson Radio Systems AB, Linköping, Sweden.",
            "About Threshold Monitor Client",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exit application.
     */
    void jMenuItemExit_actionPerformed(ActionEvent e) {
        Log.write(this,Log.LOG_ALL,"jMenuItemExit_actionPerformed()","called");

        // simulate a window closing event (this could be solved in another way)
        WindowListener[] wls = (WindowListener[])(this.getListeners(WindowListener.class));
        WindowEvent windowEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        for (int i=0;i<wls.length;i++) {
            wls[i].windowClosing( windowEvent );
        }

        setVisible(false);
        dispose();
        System.exit(0);
    }

    /**
     * Show help dialog.
     */
    void jMenuItemHelp_actionPerformed(ActionEvent e) {
	System.out.println("There is no help function");
        Log.write(this,Log.LOG_ALL,"jMenuItemExit_actionPerformed()","called");

        JOptionPane.showMessageDialog(this,
            "No help available.",
            "Help",
            JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Returns the combo box used for Performance Attribute Descriptors.
     * @return the JComboBox for the Observable Object attributes
     */
    JComboBox getObservableObjectAttributeComboBox() {
        return this.jComboBoxObservableObjectAttribute;
    }

    /**
     * Returns the combo box used for observable object classes.
     * @return the JComboBox for Observable Object classes
     */
    JComboBox getObservableObjectClassesComboBox() {
        return this.jComboBoxObservableObjectClasses;
    }

    /**
     * Returns the combo box used for the observable object.
     * @return the JComboBox for the observable object
     */
    JComboBox getObservableObjectComboBox() {
       return this.jComboBoxObservableObject;
    }

    /**
     * Returns the combo box for the granularity.
     * @return the JComboBox for the granularity
     */
    JComboBox getGranularityComboBox() {
        return this.jComboBoxGranularity;
    }

    /**
     * Returns the value text field.
     * @return JTextField for threshold value
     */
    JTextField getThresholdValueTextField() {
        return this.jTextFieldValue;
    }

    /**
     * Returns the offset text field.
     * @return JTextField for threshold offset
     */
    JTextField getThresholdOffsetTextField() {
        return this.jTextFieldOffset;
    }

    /**
     * Returns the falling radio button.
     * @return JRadioButton
     */
    JRadioButton getFallingRadioButton() {
        return this.jRadioButtonDirectionFalling;
    }

    /**
     * Returns the rising radio button.
     * @return JRadioButton
     */
    JRadioButton getRisingRadioButton() {
        return this.jRadioButtonDirectionRising;
    }

    /**
     * Return the threshold start button.
     * @return JButton
     */
    JButton getStartButton() {
        return this.jButtonStart;
    }

    /**
     * Return the threshold stop button.
     */
    JButton getStopButton() {
        return this.jButtonStop;
    }

    /**
     * Returns the measurement table.
     */
    JTable getMeasurementTable() {
        return this.jTableMeasurement;
    }

    /**
     * Returns the alarm table.
     */
    JTable getAlarmTable() {
        return this.jTableAlarm;
    }

    /**
     * Returns the name text field.
     */
    JTextField getNameTextField() {
        return this.jTextFieldName;
    }
}
