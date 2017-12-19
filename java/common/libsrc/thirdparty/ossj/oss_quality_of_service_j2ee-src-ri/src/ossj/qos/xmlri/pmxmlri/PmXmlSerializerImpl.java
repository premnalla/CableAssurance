package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * I fixed this class, it was incomplete and did not work.
 * 2002-06-06, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
 */


import javax.oss.*;
import javax.oss.pm.measurement.*;
import ossj.qos.xmlri.pmxmlri.*;


public class PmXmlSerializerImpl implements javax.oss.XmlSerializer {

  private String type = null;
    private String encodingStyle = null;

     /**
     * Get all the encoding styles supported by this Serializer.
     * This may return an empty array, in case no serializer is implemented
     *
     * @return An Array of EncodingStyle strings.
     */

    public PmXmlSerializerImpl( String type ) {
        //Hooman
        //VP
		//System.out.println("PmXmlSerializerImpl, is called......");
        this.type = type;
    }

    /**
     * Get all the encoding styles supported by this Serializer.
     * This may return an empty array, in case no serializer is implemented
     *
     * @return An Array of EncodingStyle strings.
     */

   public  String[] getSupportedEncodingStyles() {

        String[] supportedEncodingStyles = new String[1];
        supportedEncodingStyles[0] =
        new String( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE );
        return supportedEncodingStyles;
    }

    /**
     * Returns the default encoding that is the encoding style
     * used by the Serializer when it is created. To set the
     * encoding style to another value use <CODE>setEncodingStyle()</CODE>
     *
     * @return The default encoding style.
     */

    public String getDefaultEncodingStyle() {

       return XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE;

    }


    /**
     * Set the encoding style associated with this Serializer.
     * <p>
     * For example "http://www.ossj.org/encoding/ossxml" for the OSSJ
     * XML Encoding Style defined in the OSSJ Design Guidelines.
     * <p>
     * One of the items returned by <CODE>getSupportedEncodingStyles()</CODE>
     * should be provided as parameter.
     *
     * @param encodingStyle The encodingStyle string.
     * @exception javax.oss.IllegalArgumentException if the provided
     * encoding style is not valid or is not recognized by the Serializer.
     */

    public void setEncodingStyle(String encodingStyle)
    throws java.lang.IllegalArgumentException
    {
        if(  ! encodingStyle.equals( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE))
        throw new java.lang.IllegalArgumentException("Invalid Encoding Style");
        this.encodingStyle = encodingStyle;

    }



    /**
     * The encoding style associated with this Serializer.
     * <p>
     * For example "http://www.ossj.org/encoding/ossxml" for the OSSJ
     * XML Encoding Style defined in the OSSJ Design Guidelines.
     * <p>
     * A Serializer is always created with a default encoding style.
     * <p>
     * The default encoding style is provided by the value of the
     * <CODE>OSS_DEFAULT_ENCODING_STYLE</CODE> found in the declaration of the
     * specific serializer type <CODE>EncodingStyles</CODE> interface.
     *
     * @return The encoding style associated with this Serializer.
     */

    public String getEncodingStyle() {
        return XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE;
    }


    /**
     * Returns the type of object that this Serializer can
     * marshall and unmarshall from and to XML, for example
     * <CODE>javax.oss.TroubleTicketValue</CODE>
     *
     * @return The type of object that this object can serialize
     */
    public String getType() { //return TroubleTicketValue.getClass().getName();
       return type;
    }

    /**
     * Serialize this Java object as an XML element.  No doctype declaration is generated,
     * in order to allow this String to be embedded as an element of a larger XML
     * document instance.
     * <p>
     *
     * For example assuming that this a Serializer for a &lt;ManagedEntity&gt;Key
     * then calling <CODE>toXML( "myElementName")</CODE> would result in the following:
     * <pre>
     * < myElementName >
     *          < co:applicationContext >
     *                  < co:factoryClass >String&lt;/co:factoryClass >
     *                  < co:url >http://www.xmlspy.com< /co:url >
     *                  < co:systemProperties >
     *                          < co:property >
     *                                  < co:name >String< /co:name >
     *                                  < co:value >String< /co:value >
     *                          < /co:property >
     *                  < /co:systemProperties >
     *          < /co:applicationContext >
     *          < co:applicationDN >String< /co:applicationDN >
     *          < co:type >String< /co:type >
     *          < primaryKey >String< /primaryKey >
     *  < /myElementName >
     * </pre>
     *
     *
     * @param elementName String the name of the element for this value
     * @param value
     * @return String XML element
     * @exception javax.oss.IllegalArgumentException if the value is not of the proper type
     */
    public String toXml( Object object, String elementName)
    throws java.lang.IllegalArgumentException {
      String doc = null;
//hooman
//VP
//System.out.println("**********************************\n");
//System.out.println("In PM serializer toXml, element name is      = " + elementName );
//System.out.println("In PM serializer toXml, Object class name is = " + object.getClass().getName() );
//System.out.println("**********************************\n");

      if(elementName.equalsIgnoreCase(PerformanceDataEvent.class.getName())){
          try {
          doc = PerformanceDataEventXmlTranslator.toXml((PerformanceDataEvent)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceDataAvailableEvent.class.getName())){
          try {
          doc = PerformanceDataAvailableEventXmlTranslator.toXml((PerformanceDataAvailableEvent)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorByClassesValue.class.getName())){
          try {
          doc = PerformanceMonitorByClassesValueXmlTranslator.toXml((PerformanceMonitorByClassesValue)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorByObjectsValue.class.getName())){
          try {
          //hooman
          //VP
		  //System.out.println("In PMXmlSerializer, pm by objects. \n");
          doc = PerformanceMonitorByObjectsValueXmlTranslator.toXml((PerformanceMonitorByObjectsValue)object, elementName );
          }
          catch( Exception e ) {
             //hooman
             //VP
			 //System.out.println("exception is: \n");
             e.printStackTrace();
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorKey.class.getName())){
          try {
          doc = PerformanceMonitorKeyXmlTranslator.toXml((PerformanceMonitorKey)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorValue.class.getName())){
          try {
          doc = PerformanceMonitorValueXmlTranslator.toXml((PerformanceMonitorValue)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(QueryByDNValue.class.getName())){
          try {
          doc = QueryByDNValueXmlTranslator.toXml((QueryByDNValue)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(QueryPerformanceMonitorValue.class.getName())){
          try {
          doc = QueryPerformanceMonitorValueXmlTranslator.toXml((QueryPerformanceMonitorValue)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          else if(elementName.equalsIgnoreCase(ReportFormat.class.getName())){
          try {
          doc = ReportFormatXmlTranslator.toXml((ReportFormat)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
           else if(elementName.equalsIgnoreCase(ReportInfo.class.getName())){
          try {
          doc = ReportInfoXmlTranslator.toXml((ReportInfo)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
           else if(elementName.equalsIgnoreCase(CurrentResultReport.class.getName())){
          try {
          //doc = CurrentResultReportXmlTranslator.toXml((CurrentResultReport)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          else if(elementName.equalsIgnoreCase(PerformanceMonitorKeyResult.class.getName())){
          try {
          doc = PerformanceMonitorKeyResultXmlTranslator.toXml((PerformanceMonitorKeyResult)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          else if(elementName.equalsIgnoreCase(ReportData.class.getName())){
          try {
          doc = ReportDataXmlTranslator.toXml((ReportData)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          else if(elementName.equalsIgnoreCase(PerformanceAttributeDescriptor.class.getName())){
          try {
          doc = PerformanceAttributeDescriptorXmlTranslator.toXml((PerformanceAttributeDescriptor)object, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }


      return doc;

    }



    /**
     * Deserialize the XML DOM element subtree into the Java object.
     * For example assuming that this a Serializer for a <CODE>< ManagedEntity >Key</CODE>
     * and that we have a DOM element representing the following
     * <pre>
     * < key >
     *          < co:applicationContext >
     *                  < co:factoryClass >String< /co:factoryClass >
     *                  < co:url >String< /co:url >
     *                  < co:systemProperties >
     *                          < co:property >
     *                                  < co:name >String< /co:name >
     *                                  < co:value >String< /co:value >
     *                          < /co:property >
     *                  < /co:systemProperties >
     *          < /co:applicationContext >
     *          < co:applicationDN >String< /co:applicationDN >
     *          < co:type >String< /co:type >
     *          < primaryKey >String< /primaryKey >
     *  < /key >
     * </pre>
     * then the returned object would be a < ManagedEntity >Key key populated
     * with the proper <CODE>ApplicationContext</CODE>, <CODE>applicationDN</CODE>, <CODE>primaryKey</CODE>
     * and <CODE>type</CODE> info
     * @param element <CODE>org.w3c.dom.Element</CODE> the subtree representing the serialized object
     * @exception javax.oss.IllegalArgumentException if an invalid dom element is passed
     */
    public Object fromXml(org.w3c.dom.Element element)
    throws java.lang.IllegalArgumentException {
    String elementName = element.getTagName();
    Object obj = null;

    //VP
	//System.out.println("***************************");
    //System.out.println("In PMXmlSerialzer, fromXML:: element name is: " + elementName);
    //System.out.println("In PMXmlSerialzer, fromXML:: element to string is: " + element.toString());
    //System.out.println("****************************");

      if(elementName.equalsIgnoreCase(PerformanceDataEvent.class.getName())){
          try {
          obj = PerformanceDataEventXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceDataAvailableEvent.class.getName())){
          try {
          obj = PerformanceDataAvailableEventXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorByClassesValue.class.getName())){
          try {
          obj = PerformanceMonitorByClassesValueXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorByObjectsValue.class.getName())){
          try {
          obj = PerformanceMonitorByObjectsValueXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorKey.class.getName())){
          try {
          obj = PerformanceMonitorKeyXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(PerformanceMonitorValue.class.getName())){
          try {
          obj = PerformanceMonitorValueXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
        else if(elementName.equalsIgnoreCase(QueryByDNValue.class.getName())){
          try {
          obj = QueryByDNValueXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }

        else if(elementName.equalsIgnoreCase(QueryPerformanceMonitorValue.class.getName())){
          try {
          obj = QueryPerformanceMonitorValueXmlTranslator.fromXml(element,elementName);
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          //added this elseif statement. 2002-06-05, Hooman Tahamtani, Ericsson AB.
          else if(elementName.equalsIgnoreCase("ReportFormat") || elementName.equalsIgnoreCase("measurement:currentReportFormat")){
          try {
          obj = ReportFormatXmlTranslator.fromXml(element, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }
          //added this elseif statement. 2002-06-11, Hooman Tahamtani, Ericsson AB.
          else if(elementName.equalsIgnoreCase("PerformanceAttributeDescriptor")){
          try {
          obj = PerformanceAttributeDescriptorXmlTranslator.fromXml(element, elementName );
          }
          catch( Exception e ) {
             throw new java.lang.IllegalArgumentException( e.getMessage());
          }
          }

        return obj;
    }



}
