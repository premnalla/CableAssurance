package ossj.qos.ri.pm.measurement.eis;


import java.io.*;
import java.util.*;
import java.lang.*;


/** <code>ObservableObject</class> class.
 *  <p>
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */


public class ObservableObject {

  private String managedObjectClass;
  private String distinguishedName;

  // These two should be removed!!!
  private String supportedGranularities;
  private String observableObjectAttributes;

  // Performance data
  // Contains a table of counters and their values.
  // Time | CounterA | CounterB | CounterC
  // 40     50         60         70
  // 50     101        17         300000
  // etc

  private Vector[] performanceData;
  private HashMap fileRows = new HashMap();
  private HashSet attributesToMeasure = new HashSet();


  public ObservableObject(String distinguishedName, String managedObjectClass, String supportedGranularities,
    String observableObjectAttributes ) {

    EisSimulatorImpl.trace.log("->ObservableObject.ObservableObject(String distinguishedName, String managedObjectClass, String supportedGranularities," +
      "String observableObjectAttributes )", 1);

    this.managedObjectClass = managedObjectClass;
    this.distinguishedName = distinguishedName;
    this.supportedGranularities = supportedGranularities;
    this.observableObjectAttributes = observableObjectAttributes;

    StringTokenizer tok = new StringTokenizer(observableObjectAttributes, ",");

    // Build a set containing all the attributes that exists in the network model xml-file
    String attributeName;

    while (tok.hasMoreElements()) {

      if ( !attributesToMeasure.add( tok.nextElement() ) )
        EisSimulatorImpl.trace.log("Could not add element!", 1);
      tok.nextElement();
      tok.nextElement();
    }

    EisSimulatorImpl.trace.log("<-ObservableObject.ObservableObject(String distinguishedName, String managedObjectClass, String supportedGranularities," +
      "String observableObjectAttributes )", 1);
  }

  /**
   * @param dataDirectory
   */

  public void loadPerformanceData(String dataDirectory) {

    EisSimulatorImpl.trace.log("->ObservableObject.loadPerformanceData(String dataDirectory)", 1);

    BufferedReader input = null;

    String pathAndFileName = new String(dataDirectory + "/" + distinguishedName);

   //System.out.println(pathAndFileName);

    try {
      // The assumption is that the name of the file in the directory
      // is the same as the distinguished name of the observable object.
      input = new BufferedReader(new FileReader(pathAndFileName));
      EisSimulatorImpl.trace.log("Loading performance data from file " + pathAndFileName, 1);

      // The first line should contain the attribute names
      String line = input.readLine();

      int rowNumber = 0;

      StringTokenizer tokens = new StringTokenizer(line);

      while (tokens.hasMoreElements()) {
        Object foo = fileRows.put( tokens.nextElement(), new Integer(rowNumber) );

        rowNumber++;
      }

      // Allocate a set of vectors that will hold the performance
      // data
      performanceData = new Vector[fileRows.size()];
      for (int i = 0; i < fileRows.size(); i++)
        performanceData[i] = new Vector(900, 100);

      line = input.readLine();

      while ( line != null && line.length() > 0 ) {
        // Parse the line
        StringTokenizer tok = new StringTokenizer(line);

        rowNumber = 0;
        while ( tok.hasMoreElements() ) {
          String rowValue = tok.nextToken();

          performanceData[rowNumber].addElement(rowValue);
          //System.out.println(rowValue);

          rowNumber++;
        }
        line = input.readLine();
      }

    }
    catch (FileNotFoundException fnf ) {
      //VP
	  //System.out.println(fnf);
      EisSimulatorImpl.trace.log("Cannot open file: " + pathAndFileName, 1);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if ( input != null ) {
        try {
          input.close();
        }
        catch (Exception ex) {

        }
      }
    }

    EisSimulatorImpl.trace.log("<-ObservableObject.loadPerformanceData(String dataDirectory)", 1);
  }

  /**
   * @param index Where to look in the data file.
   * @param attrList A list of which attributes should be included.
   */

  public String toXml(int index, String[] attrList) {

    EisSimulatorImpl.trace.log("->ObservableObject.toXml(int index, String[] attrList)", 1);

    StringBuffer observation = new StringBuffer();
    Integer row;

    // Go through all wanted attributes!

    for (int i = 0; i < attrList.length; i++) {
      row = (Integer) fileRows.get(attrList[i]);

      try {
        if ( row != null )
          observation.append("<r p=\"" + (i + 1) + "\"> " + performanceData[row.intValue()].get(index) + " </r>\n");
        else
          EisSimulatorImpl.trace.log("Attribute not found.", 1);
      }
      catch ( java.lang.ArrayIndexOutOfBoundsException e) {
        EisSimulatorImpl.trace.log("Warning! No valid time interval found for " + distinguishedName + " No performance data will be returned.", 1);
        observation.append("<r p=\"" + ( i + 1 ) + "\"></r>\n");
      }

    }

    EisSimulatorImpl.trace.log("<-ObservableObject.toXml(int index, String[] attrList)", 1);

    return observation.toString();
  }

  public String getManagedObjectClass() {
    EisSimulatorImpl.trace.log("->ObservableObject.getManagedObjectClass()", 1);
    EisSimulatorImpl.trace.log("<-ObservableObject.getManagedObjectClass()", 1);
    return managedObjectClass;
  }

  // @todo This one should be removed.
  public String getBase() {
    EisSimulatorImpl.trace.log("->ObservableObject.getBase()", 1);
    EisSimulatorImpl.trace.log("<-ObservableObject.getBase()", 1);
    return distinguishedName;
  }

  public String getDistinguishedName() {
    EisSimulatorImpl.trace.log("->ObservableObject.getDistinguishedName()", 1);
    EisSimulatorImpl.trace.log("<-ObservableObject.getDistinguishedName()", 1);
    return distinguishedName;
  }

  public String getSupportedGranularities() {
    EisSimulatorImpl.trace.log("->ObservableObject.getSupportedGranularities()", 1);
    EisSimulatorImpl.trace.log("<-ObservableObject.getSupportedGranularities()", 1);
    return supportedGranularities;
  }

  public String getObservableObjectAttributes() {
    EisSimulatorImpl.trace.log("->ObservableObject.getObservableObjectAttributes()", 1);
    EisSimulatorImpl.trace.log("<-ObservableObject.getObservableObjectAttributes()", 1);
    return observableObjectAttributes;
  }

}
