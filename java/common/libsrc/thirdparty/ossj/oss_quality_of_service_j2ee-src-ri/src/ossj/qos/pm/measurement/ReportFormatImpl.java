package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.ReportFormat;

//import ossj.qos.pm.util.Log;
import ossj.qos.util.Log;


import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;
import ossj.qos.util.Util;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class ReportFormatImpl implements ReportFormat {

  private int type = ReportFormat.XML;
  private String specification = "DOCTYPE MeasDataCollection SYSTEM MeasDataCollection.dtd";
  private String owner = "3GPP";
  private String technology = "3G";
  private String version = "32.602-5 V4.0";

  public ReportFormatImpl() {
  }

  public Object clone() {

    Object clone = null;
    try {
      clone = super.clone();
    }
    catch(CloneNotSupportedException e) {
      Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
    }
    return clone;
  }


  /**
   * Gets the report format type.
   *
   * <p>
   * The type of the report format can have one of the following values:
   * <ul>
   * <li> XML
   * <li> ASN1
   * <li> ASCII
   * <li> BINARY
   * </ul>
   *
   *@return int The report format type.
   */
  public int getType() {
    return type;
  }

  /**
   * Get the report format version.
   *
   *@return String The version of the report format.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Get the vendor name or the organization that have defined the format.
   *
   * <p>
   * Returns the vendor name or organization name of the report format. For example: if the
   * report format is 3GPP XML or ASN1 format this operation should return "3GPP".
   *
   *@return String The name of the Owner of the report format.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Gets the technology area that this report format is used for.
   *
   * <p>
   * This operation indicates the technology area of the nodes in a measurement report of this
   * report format. For example the operation should return "3G" for 3rd generation networks and
   * "ATM" for a ATM networks.
   *
   * <p>
   * The operation can return an empty string if the report format is applicable for many
   * technology areas.
   *
   *@return String The name of the technology.
   */
  public String getTechnology() {
    return technology;
  }

  /**
   * Returns information about the specification of the report format.
   *
   * <p>
   * This operation shall allow the client to get more detailed information on how to parse
   * the report format. For example this operation should return
   * "DOCTYPE MeasDataCollection SYSTEM MeasDataCollection.dtd" if the report format is
   * according to 3GPP XML format.
   *
   * <p>
   * If no applicable information is available, then an empty string should be returned.
   *
   *@return String Information on how to parse the report format.
   */
  public String getSpecification() {
    return specification;
  }

  public void setType(int type) throws java.lang.IllegalArgumentException {
    if(type != ReportFormat.ASCII && type != ReportFormat.ASN1
       && type != ReportFormat.BINARY && type != ReportFormat.XML)
      throw new java.lang.IllegalArgumentException();

    this.type = type;
  }

  public void setVersion(String version) throws java.lang.IllegalArgumentException {
    if(version == null)
      throw new java.lang.IllegalArgumentException();

    this.version = version;
  }

  public void setOwner(String owner) throws java.lang.IllegalArgumentException {
    if(owner == null)
      throw new java.lang.IllegalArgumentException();

    this.owner = owner;
  }

  public void setSpecification(String specification) throws java.lang.IllegalArgumentException {
    if(specification == null)
      throw new java.lang.IllegalArgumentException();

    this.specification = specification;
  }

  public void setTechnology(String technology) throws java.lang.IllegalArgumentException {
    if(technology == null)
      throw new java.lang.IllegalArgumentException();

    this.technology = technology;
  }
  /*
  Added the method Below to comply with the new implementation.
  March 15, 2002.
  Hooman Tahamtani, Ericsson Microwave AB
  Gothenburg, Sweden
  */
      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( PmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

     /*
        Added the method below to comply with the new implementation.
        March 15, 2002
        Hooman Tahamtani, Ericsson Microwave AB
        Gothenburg, Sweden
     */

    public Serializer makeSerializer( String serializerType )
    throws java.lang.IllegalArgumentException {
        Serializer serializer = null;
     try{
        serializer = new PmXmlSerializerImpl(serializerType);
     }catch(java.lang.IllegalArgumentException e){
        throw new java.lang.IllegalArgumentException(e.getMessage());
     }
        return serializer;
    }

	public int hashCode() {
		return ((this.specification != null) ? this.specification.hashCode() : -1);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		ReportFormatImpl rformat = (ReportFormatImpl)o;
		return ((this.type == rformat.type) &&
			    Util.isEqual(this.owner, rformat.owner) &&
				Util.isEqual(this.specification, rformat.specification) &&
				Util.isEqual(this.technology, rformat.technology) &&
				Util.isEqual(this.version, rformat.version));
	}

}
