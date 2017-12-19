package ossj.qos.ri.pm.measurement.eis;


import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.SAXParser;
import java.util.*;
import java.io.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;
import ossj.qos.util.ObservableObjectClassIteratorImpl;
import ossj.qos.util.ObservableObjectIteratorImpl;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.ScaledTimeGMT;
import java.text.*;


/** <code>NetworkModel</code> class.
 *  <p>
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */

public class NetworkModel extends DefaultHandler {


  /** An arrary of all observable object classes.
   */
  private String[] observableObjectClasses = null;

  /** A list of class to Granularities
   *  relationsships (String to int[]).
   */
  private HashMap classToGranularities = new HashMap();

  /** A list of class to Attributes
   *  relationsships (String to PerformanceAttributeDescriptor[]).
   */
  private HashMap classToAttributes = new HashMap();

  /** An array of all observable objects
   *  in the network model.
   */
  private ObservableObject[] obsObjects = null;

  /**
   */
  private HashMap allObservableObjects = null;

  /**
   */
  private Vector obsObjectsVector = new Vector(0, 1);

  /**
   */
  private GregorianCalendar endDate;

  /** This constructor creates a <code>NetworkModel</code> object with
   *  current time tomorrow as the end date. This constructor should
   *  not be used.
   *  <p>
   *
   */

  public NetworkModel() {
    EisSimulatorImpl.trace.log("->NetworkModel.NetworkModel()", 1);
    this.endDate = new GregorianCalendar();
    endDate.add(GregorianCalendar.DATE, 1);
    EisSimulatorImpl.trace.log("<-NetworkModel.NetworkModel()", 1);
  }


  /** This constructor creates a <code>NetworkModel</code> object with
   *  the specified end date.
   *  <p>
   *
   *  @param endDate Specifies the endDate in the ScaledTimeGMT class
   *
   */

  public NetworkModel( String endDate ) {
    EisSimulatorImpl.trace.log("->NetworkModel.NetworkModel( String endDate )", 1);
    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss");

    this.endDate = new GregorianCalendar();
    try {
      Date tmpDate = parser.parse(endDate);
      this.endDate.setTime(tmpDate);

      EisSimulatorImpl.trace.log("Enddate is " + this.endDate.getTime().toString(), 1);
    }
    catch (Exception e) {
      EisSimulatorImpl.trace.log("<-Not possible to parse the date! " + e.toString(), 1);
      this.endDate.add(GregorianCalendar.DATE, 1);
    }
    EisSimulatorImpl.trace.log("<-NetworkModel.NetworkModel( String endDate )", 1);
  }


  /** <code>getReport</code> will create a performance report for a set of
   *  observable objects.
   *
   *  <p>
   * @param  dnList               The distinguished names of the objects that
   *                              should be in the report.
   * @param  attribList           The attributes that are going to be reported.
   * @param  granularityPeriod    The granularity period of the report in seconds.
   * @param  argStartOfGranPeriod The start of the measurement period.
   * @return                      A performance report encoded in XML according
   *                              to 3GPP TS32.401.
   */


  public String[] getReport(String[] dnList, PerformanceAttributeDescriptor[] attributesToMeasure,
    int granularityPeriod, Date argStartOfGranPeriod ) {

    EisSimulatorImpl.trace.log("->NetworkModel.getReport( String[] dnList, String[] attribList," +
      " int granularityPeriod, Date argStartOfGranPeriod  )", 1);

    String[] attribList = new String[attributesToMeasure.length];

    for (int i = 0; i < attributesToMeasure.length; i++) {
      attribList[i] = attributesToMeasure[i].getName();
    }

    // Mycal is an endDate where the performance data ends.
    GregorianCalendar myCal = new GregorianCalendar();

    myCal.add(GregorianCalendar.DATE, 1);

    ScaledTimeGMT st = new ScaledTimeGMT( endDate );

    Date startOfGranPeriod = new Date( st.getTimeStamp( argStartOfGranPeriod.getTime() ));
    //long index = st.getRowNumber(argStartOfGranPeriod.getTime(), 1000);
    long index = 0;
    //System.out.println("index: " + index);

    // Here we are using the compressed time format.

    java.text.DateFormat reportTimeStampFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
    java.text.DateFormat fileTimeStampFormat = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss");

    // Some processing to round of startOfPeriod to the
    // nearest measurement period.

    long tmpTime = startOfGranPeriod.getTime() / 1000;

    tmpTime = tmpTime - (tmpTime % granularityPeriod);

    //    long index = ( tmpTime / granularityPeriod ) % ( (24 * 3600) / granularityPeriod );


    Date startOfPeriod = new Date( tmpTime * 1000 );
    Date endOfPeriod = new Date( (tmpTime + granularityPeriod) * 1000 );

    String fileName = new String("B" + fileTimeStampFormat.format(startOfPeriod) + "-"
        + fileTimeStampFormat.format(endOfPeriod) + "_"
        + dnList[0] + ".xml");


    // For the description of the elements see 3GPP TS32.401
    // Header for report
    StringBuffer report = new StringBuffer (
        "<?xml version=\"1.0\"?>\n" +
        "<?xml-stylesheet type=\"text/xsl\" href=\"MeasDataCollection.xsl\" ?>\n" +
        "<!DOCTYPE mdc SYSTEM \"MeasDataCollection.dtd\">\n\n" );

    // Meas Data Collection
    report.append("<mdc>\n\n");
    // Meas File Header
    report.append("<mfh>\n");
    report.append("<ffv>2</ffv> \n");
    // Sender name. Can be an empty string.
    report.append("<sn></sn> \n");
    // Sender type. Can be an empty string.
    report.append("<st></st> \n");
    // Vendor name. Can be an empty string.
    report.append("<vn></vn> \n");
    // Collection begin time. TBD
    report.append("<cbt>" + reportTimeStampFormat.format(startOfPeriod) + "</cbt> \n");
    report.append("</mfh>\n\n");

    for (int i = 0; i < dnList.length; i++) {

      report.append("<md>\n");
      report.append("<neid>\n");
      // User definable NE name.Can be an empty string.
      report.append("<neun></neun>\n");
      // The distinguished name for the network element.
      // Warning. This is not correct made here!!!
      report.append("<nedn>" + dnList[i] + "</nedn>\n");
      report.append("</neid>\n");
      report.append("<mi>\n");
      report.append("<mts>" + reportTimeStampFormat.format(endOfPeriod) + "</mts> \n");
      report.append("<gp> " + granularityPeriod + " </gp>\n");

      for (int r = 0; r < attribList.length; r++)
        report.append("<mt p=\"" + (r + 1) + "\"> " + attribList[r] + " </mt>\n");

      report.append("<mv>\n");
      ObservableObject oo = (ObservableObject) allObservableObjects.get(dnList[i]);

      report.append("<moid></moid>\n");

      if ( oo != null )
        report.append( oo.toXml((int) index, attribList ) );
      report.append("<sf>FALSE</sf>\n");
      report.append("</mv>\n");
      report.append("</mi>\n");
      report.append("</md>\n\n");
    }

    report.append("\n</mdc>\n");

    EisSimulatorImpl.trace.log("<-NetworkModel.getReport( String[] dnList, String[] attribList," +
      " int granularityPeriod, Date argStartOfGranPeriod  )", 1);
    return new String[] { report.toString(), fileName };

  }

  /** <code>getSupportedObservableObjects</code> will filter out which
   *  of the potentially observable objects are actually observable.
   *  <p>
   * @param dnList
   * @return strSupported
   */

  public String[] getSupportedObservableObjects( String[] dnList ) {
    EisSimulatorImpl.trace.log("->NetworkModel.getSupportedObservableObjects( String[] dnList )", 1);

    Vector supported = new Vector(0, 1);
    String[] strSupported = null;

    // We leave to the reader of this code to improve the lines below...
    for (int i = 0; i < dnList.length; i++) {
      boolean found = false;

      for (int j = 0; j < obsObjects.length && found == false; j++) {
        if (dnList[i].compareTo(obsObjects[j].getDistinguishedName()) == 0) {
          supported.add(dnList[i]);
          found = true;
        }
      }
    }

    if (supported.size() == 0)
      strSupported = new String[0];
    else
      strSupported = (String[]) supported.toArray();
    EisSimulatorImpl.trace.log("<-NetworkModel.getSupportedObservableObjects( String[] dnList )", 1);
    return strSupported;
  }

  /** <code>getSupportedGranularities</code> will return an array of granularities
   *  that is supported by the supplied observable object class.
   *  <p>
   * @param  observableObjectClass
   * @return                        An array of granularities that is supported
   *                                by the supplied observable object class.
   */

  public int[] getSupportedGranularities( String observableObjectClass )
  throws javax.oss.IllegalArgumentException {

    EisSimulatorImpl.trace.log("->NetworkModel.getSupportedGranularities( String observableObjectClass )", 1);

    int[] supportedGranularities = (int[]) classToGranularities.get(observableObjectClass);

    if ( supportedGranularities == null )
      throw new javax.oss.IllegalArgumentException("Observable Object Class: " + observableObjectClass + " not found.");

    EisSimulatorImpl.trace.log("<-NetworkModel.getSupportedGranularities( String observableObjectClass )", 1);

    return supportedGranularities;
  }

  /** <code>getObservableObjectClasses</code> will return all observable
   *  object classes in the model.
   *  <p>
   * @return                        An array of granularities that is supported
   *                                by the supplied observable object class.
   */

  public ObservableObjectClassIterator getObservableObjectClasses() {

   //System.out.println("---wrx:before in networkmodel");

    EisSimulatorImpl.trace.log("->NetworkModel.getObservableObjectClasses()", 1);

    ObservableObjectClassIterator observableObjectClassesIterator = null;

    try {
      observableObjectClassesIterator = new ObservableObjectClassIteratorImpl(observableObjectClasses);
    }
    catch (Exception e) {
      EisSimulatorImpl.trace.log(e.toString(), 1);
    }

    //System.out.println("---wrx:after in networkmodel");
    if ( observableObjectClasses.length == 0 )
      EisSimulatorImpl.trace.log("Model does not contain any observable object classes.", 1);
    for ( int i = 0; i<observableObjectClasses.length ; i++ )
      EisSimulatorImpl.trace.log("Observable object class: " + observableObjectClasses[i], 1);

    EisSimulatorImpl.trace.log("<-NetworkModel.getObservableObjectClasses()", 1);

    return observableObjectClassesIterator;
  }

  /** <code>getObservableObjects</code> will return all observable
   *  objects of a certain class under a level determined by the
   *  base. If base is not specified then all instances of that class are returned.
   *  <p>

   * @param observableObjectClassName The class name of the observable object instances
   *                                  to be found.
   * @param base                      The distinguished name of the level where
   *                                  the search should start from.
   *
   * @return                          Iterator that contains all supported
   *                                  observable objects.
   *
   * @exception java.rmi.RemoteException           Is raised when a unexpected system
   *                                               exception occurs.
   * @exception javax.oss.IllegalArgumentException Is raised if the input paramters
   *                                               are not valid.
   */

  public ObservableObjectIterator getObservableObjects(String observableObjectClassName, String base )
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {

    EisSimulatorImpl.trace.log("->NetworkModel.getObservableObjects(String observableObjectClassName, String base )", 1);

    ObservableObjectClassIterator ooClassIterator = null;
    ObservableObjectIterator ooIterator = null;
    String[] observableObjectClasses = null;
    boolean validObjectClass = false;

    //Validating input parameters
    if (base == null)
      throw new javax.oss.IllegalArgumentException("base is null");
    if ( observableObjectClassName == null )
      throw new javax.oss.IllegalArgumentException("observableObjectClassName is null");

    // Check if valid manged object class
    ooClassIterator = getObservableObjectClasses();
    observableObjectClasses = ooClassIterator.getNext(100);
    while (observableObjectClasses.length != 0 && validObjectClass == false ) {
      for (int i = 0; i < observableObjectClasses.length; i++) {
        if (observableObjectClassName.compareTo(observableObjectClasses[i]) == 0) {
          validObjectClass = true;
          continue;
        }
      }
      observableObjectClasses = ooClassIterator.getNext(100);
    }

    if ( validObjectClass == false )
      throw new javax.oss.IllegalArgumentException();

    Vector distinguishedNames = new Vector(0, 1);

    for (int i = 0; i < obsObjects.length; i++) {
      if (obsObjects[i].getDistinguishedName().startsWith(base) == true) {
        if (obsObjects[i].getManagedObjectClass().compareTo(observableObjectClassName) == 0) {
          distinguishedNames.add(obsObjects[i].getDistinguishedName());
        }
      }
    }

      Object[] values =  distinguishedNames.toArray();
      String[] result = null;

    if (values != null) {
      result = new String[values.length];
      System.arraycopy(values, 0, result, 0, values.length);
    }

    if (result.length == 0 ) {
      EisSimulatorImpl.trace.log("No Observable Objects found. Class: \""  + observableObjectClassName + "\" Base: \"" + base + "\"");
    }
    else
      for ( int i=0; i<result.length ; i++ )
        EisSimulatorImpl.trace.log("Observable objects found: " + result[i], 1);


    ooIterator = new ObservableObjectIteratorImpl(result);

    EisSimulatorImpl.trace.log("<-NetworkModel.getObservableObjects(String observableObjectClassName, String base )", 1);

    return ooIterator;
  }

  /** <code>getObservableAttributes</code> will return all observable
   *  attributes for a certain class.
   *  <p>

   * @param observableObjectClassName The class name of the observable object instances
   *                                  to be found.
   *
   * @return                          An array of attribute descriptors.
   *
   * @exception javax.oss.IllegalArgumentException Is raised if the input paramters
   *                                               are not valid.
   */

  public PerformanceAttributeDescriptor[] getObservableAttributes( String observableObjectClassName )
  throws javax.oss.IllegalArgumentException {

    EisSimulatorImpl.trace.log("->NetworkModel.getObservableAttributes( String observableObjectClassName )", 1);

    PerformanceAttributeDescriptor[] attributes = (PerformanceAttributeDescriptor[]) classToAttributes.get(observableObjectClassName);

    if ( attributes == null )
      throw new javax.oss.IllegalArgumentException("No observableObjectClass " + observableObjectClassName );

    EisSimulatorImpl.trace.log("<-NetworkModel.getObservableAttributes( String observableObjectClassName )", 1);

    return attributes;

  }

  /** <code>loadData</code> will load all performance data for the objects
   *  in the network model. Everything is cached in memory.
   *  <p>

   * @param dataDir  The directory where to get the performance data files.
   *
   * @exception javax.oss.IllegalArgumentException Is raised if the input paramters
   *                                               are not valid.
   */


  public void loadData(String dataDir) {

    EisSimulatorImpl.trace.log("->NetworkModel.loadData(String dataDir)", 1);

    Iterator obsObjectIterator = allObservableObjects.keySet().iterator();

    try {
      while ( obsObjectIterator.hasNext() ) {
        String distinguishedName = (String) obsObjectIterator.next();
        ObservableObject oo = (ObservableObject) allObservableObjects.get(distinguishedName);

        oo.loadPerformanceData(dataDir);
      }
    }
    catch ( NoSuchElementException nse ) {
    }
    EisSimulatorImpl.trace.log("<-NetworkModel.loadData(String dataDir)", 1);
  }

  // Here is a few lines parsing the XML network model file.

  public void loadModel(String argv) {

    EisSimulatorImpl.trace.log("->NetworkModel.loadModel(String argv)", 1);

    try {
      XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

      parser.setContentHandler(this);
      parser.parse(argv);
    }

    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    catch (SAXException saxe) {
      saxe.printStackTrace();
    }
    EisSimulatorImpl.trace.log("<-NetworkModel.loadModel(String argv)", 1);
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
    if (ch[0] == '\n')
      return;
  }

  public void endDocument() throws SAXException {
    EisSimulatorImpl.trace.log("->NetworkModel.endDocument()", 1);
    EisSimulatorImpl.trace.log("Finished parsing XML-file keeping managed objects. Found " + obsObjectsVector.size() +
      " managed object(s) and " + classToGranularities.size() + " different managed object class(es).");

    observableObjectClasses = new String[classToGranularities.keySet().size()];
    Object aObj[] = classToGranularities.keySet().toArray(observableObjectClasses);

    obsObjects = new ObservableObject[obsObjectsVector.size()];
    Object[] anObj =  (ObservableObject[]) obsObjectsVector.toArray(obsObjects);

    allObservableObjects = new HashMap(obsObjects.length);
    for (int i = 0; i < obsObjects.length; i++)
      allObservableObjects.put(obsObjects[i].getDistinguishedName(), obsObjects[i]);

    EisSimulatorImpl.trace.log("<-NetworkModel.endDocument()", 1);

  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
  }

  public void startDocument() throws SAXException {
    EisSimulatorImpl.trace.log("->NetworkModel.startDocument()", 1);
    EisSimulatorImpl.trace.log("Starting parsing XML-file keeping managed objects.", 1);
    EisSimulatorImpl.trace.log("<-NetworkModel.startDocument()", 1);
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes)
  throws SAXException {

    EisSimulatorImpl.trace.log("->NetworkModel.startElement(String uri, String localName, String qName, Attributes attributes)", 1);

    if ( localName.compareTo( "managedObject" ) == 0 ) {

      String supportedGranularities = null;
      String observableObjectAttributes = null;
      String distinguishedName = null;
      String managedObjectClass = null;

      // Find our attributes.
      for ( int loop = 0; loop < attributes.getLength(); loop++ ) {
        if ( attributes.getLocalName(loop).compareTo("supportedGranularities") == 0 )
          supportedGranularities = attributes.getValue(loop);
        else if ( attributes.getLocalName(loop).compareTo("observableObjectAttributes") == 0 )
          observableObjectAttributes = attributes.getValue(loop);
        else if ( attributes.getLocalName(loop).compareTo("distinguishedName") == 0 )
          distinguishedName = attributes.getValue(loop);
        else if ( attributes.getLocalName(loop).compareTo("managedObjectClass") == 0 )
          managedObjectClass = attributes.getValue(loop);

      }

      if ( distinguishedName != null && managedObjectClass != null &&
        supportedGranularities != null && observableObjectAttributes != null ) {

        obsObjectsVector.add(new ObservableObject( distinguishedName, managedObjectClass,
            supportedGranularities, observableObjectAttributes ));
        try {
          // Check first if we alread have this class!
          if ( classToAttributes.containsKey(managedObjectClass) != true ) {

            // Parse the granularities string into an int[]
            StringTokenizer tokens = new StringTokenizer( supportedGranularities, ",");
            Vector allGranularities = new Vector(0, 1);

            while (tokens.hasMoreTokens()) {
              Integer tmp = new Integer(Integer.parseInt( tokens.nextToken().trim() ));

              allGranularities.add(tmp);
            }
            int[] supportedParsedGranularities = new int[allGranularities.size()];

            for ( int i = 0; i < allGranularities.size(); i++)
              supportedParsedGranularities[i] = ((Integer) allGranularities.get(i)).intValue();

            classToGranularities.put(managedObjectClass, supportedParsedGranularities );


            // Parsing of the attributes!!!

            PerformanceAttributeDescriptor pad = new PerformanceAttributeDescriptorImpl(PerformanceAttributeDescriptor.STATUS_INSPECTION);
            HashSet padSet = new HashSet();

            StringTokenizer attributeTokens = new StringTokenizer( observableObjectAttributes, ",");

            while (attributeTokens.hasMoreTokens()) {

              String name = attributeTokens.nextToken().trim();
              String type = attributeTokens.nextToken().trim();
              String collectionMethod = attributeTokens.nextToken().trim();

              if ( collectionMethod.compareTo("STATUS_INSPECTION") == 0 )
                pad = new PerformanceAttributeDescriptorImpl(PerformanceAttributeDescriptor.STATUS_INSPECTION);
              else if ( collectionMethod.compareTo("GAUGE") == 0 )
                pad = new PerformanceAttributeDescriptorImpl(PerformanceAttributeDescriptor.GAUGE);
              else if ( collectionMethod.compareTo("DISCRETE_EVENT_REGISTRATION") == 0 )
                pad = new PerformanceAttributeDescriptorImpl(PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION);
              else if ( collectionMethod.compareTo("CUMULATIVE_COUNTER") == 0 )
                pad = new PerformanceAttributeDescriptorImpl(PerformanceAttributeDescriptor.CUMULATIVE_COUNTER);
              else {
                EisSimulatorImpl.trace.log("Unknown collection method: " + collectionMethod, 1);
                continue;
              }

              pad.setName(name);
              if ( type.compareTo("INTEGER") == 0 )
                pad.setType(PerformanceAttributeDescriptor.INTEGER);
              else if ( type.compareTo("REAL") == 0 )
                pad.setType(PerformanceAttributeDescriptor.REAL);
              else if ( type.compareTo("STRING") == 0 )
                pad.setType(PerformanceAttributeDescriptor.STRING);
              else {
                EisSimulatorImpl.trace.log("Unknown counter type: " + type, 1);
                continue;
              }

              padSet.add(pad);
            }

            PerformanceAttributeDescriptor[] attributeDescriptor = new PerformanceAttributeDescriptor[ padSet.size() ];
            Object[] aObject =  (PerformanceAttributeDescriptor[]) padSet.toArray( attributeDescriptor );

            classToAttributes.put(managedObjectClass, attributeDescriptor );
          }
        }
        catch (Exception e) {
          EisSimulatorImpl.trace.log(e.toString(), 1);
        }
      }
      else
        EisSimulatorImpl.trace.log("Could not find all required attributes for managedObject element:\n" +
          "distinguishedName: " + distinguishedName + "\n" +
          "managedObjectClass: " + managedObjectClass + "\n" +
          "supportedGranularities: " + supportedGranularities + "\n" +
          "observableObjectAttributes: " + observableObjectAttributes, 1);
    }
    EisSimulatorImpl.trace.log("<-NetworkModel.startElement(String uri, String localName, String qName, Attributes attributes)", 1);

  }
}
