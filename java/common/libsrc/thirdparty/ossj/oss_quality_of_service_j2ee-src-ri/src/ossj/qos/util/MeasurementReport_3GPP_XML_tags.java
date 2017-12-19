/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */

//package ossj.qos.pm.util;

package ossj.qos.util;


/**
 * XML tags for Measurement Report.
 *
 * <p>Implementation taken from 3GPP TS 32.401 V4.0.0 (2001-09), technical
 * specification; 3rd Generation partnership Project; Technical Specification
 * Group Services and Aspects; Telecommunication Management; Performance
 * Management (PM); Concept and Requirements (Release 4).
 *
 * <p>DTD definition:
 * <pre>
 * &lt!-- MeasDataCollection.dtd version 2.0--&gt
 * &lt?xml version="1.0" encoding="UTF-8"?&gt
 * &lt!ELEMENT mdc (mfh , md*, mff )&gt
 * &lt!ELEMENT mfh (ffv, sn, st, vn, cbt) &gt
 * &lt!ELEMENT md (neid , mi*)&gt
 * &lt!ELEMENT neid (neun, nedn, nesw?)&gt
 * &lt!ELEMENT mi (mts,gp, mt*, mv*)&gt
 * &lt!ELEMENT mv (moid , r*, sf? )&gt
 * &lt!ELEMENT mff (ts)&gt
 * &lt!ELEMENT ts (#PCDATA)&gt
 * &lt!ELEMENT sf (#PCDATA)&gt
 * &lt!ELEMENT r (#PCDATA)&gt
 * &lt!ATTLIST r p CDATA ""&gt
 * &lt!ELEMENT mt (#PCDATA)&gt
 * &lt!ATTLIST mt p CDATA ""&gt
 * &lt!ELEMENT moid (#PCDATA)&gt
 * &lt!ELEMENT gp (#PCDATA)&gt
 * &lt!ELEMENT mts (#PCDATA)&gt
 * &lt!ELEMENT nedn (#PCDATA)&gt
 * &lt!ELEMENT neun (#PCDATA)&gt
 * &lt!ELEMENT nesw (#PCDATA)&gt
 * &lt!ELEMENT cbt (#PCDATA)&gt
 * &lt!ELEMENT vn (#PCDATA)&gt
 * &lt!ELEMENT st (#PCDATA)&gt
 * &lt!ELEMENT sn (#PCDATA)&gt
 * &lt!ELEMENT ffv (#PCDATA)&gt
 *
 * &lt!-- end of MeasDataCollection.dtd --&gt
 * </pre>
 * No guarantee that this implementation is correct!
 *
 * @author Henrik Lindstrom, Ericsson
 * @see <a href="http://www.3gpp.org">http://www.3gpp.org</a>
 */
public interface MeasurementReport_3GPP_XML_tags {
    /**
     * MeasDataCollection, mdc. This is the top-level tag, which identifies the file
     * as a collection of measurement data. The file content is made up of a
     * header ("measFileHeader"), the collection of measurement result items
     * ("measData"), and a measurement file footer ("measFileFooter").
     */
    String MDC_ELEMENT = "mdc";

    /**
     * measFileHeader, mfh. This is the measurement result file header to be
     * inserted in each file. It includes a version indicator, the name, type
     * and vendor name of the sending network node, and a time stamp
     * ("collectionBeginTime").
     */
    String MFH_ELEMENT = "mfh";

    /**
     * measData, md. The measData construct represents the sequence of zero or
     * more measurement result items contained in the file. It can be empty in
     * case no measurement data can be provided. The individual measData
     * elements can appear in any order. Each measData element contains the name
     * of the NE ("nEId") and the list of measurement results pertaining to that
     * NE ("measInfo").
     */
    String MD_ELEMENT = "md";

    /**
     * measFileFooter, mff.	The measurement result file footer to be inserted in
     * each file. It includes a time stamp, which refers to the end of the
     * overall measurement collection interval that is covered by the collected
     * measurement results being stored in this file.
     */
    String MFF_ELEMENT = "mff";

    /**
     * fileFormatVersion, ffv. This parameter identifies the file format version
     * applied by the sender. The format version defined in the present document
     * shall be "2" for both the XML and ASN.1 formats alike.
     */
    String FFV_ELEMENT = "ffv";

    /**
     * senderName, sn. The senderName uniquely identifies the NE or EM that
     * assembled this measurement file, according to the definitions in 3GPP TS
     * 32.300 [10]. It is identical to the sender's nEDistinguishedName. The
     * string may be empty (i.e. string size =0) in case it is not configured in
     * the sender.
     */
    String SN_ELEMENT = "sn";

    /**
     * senderType, st. This is a user configurable identifier of the type of
     * network node that generated the file, e.g. NodeB, EM, SGSN. The string
     * may be empty (i.e. string size =0) in case the "senderType" is not
     * configured in the sender.
     */
    String ST_ELEMENT = "st";

    /**
     * vendorName, vn. The vendorName identifies the vendor of the equipment
     * that provided the measurement file. The string may be empty (i.e. string
     * size =0) if the "vendorName" is not configured in the sender.
     */
    String VN_ELEMENT = "vn";

    /**
     * collectionBeginTime,	cbt. The collectionBeginTime is a time stamp that
     * refers to the start of the first measurement collection interval
     * (granularity period) that is covered by the collected measurement results
     * that are stored in this file.
     */
    String CBT_ELEMENT = "cbt";

    /**
     * nEId, neid. The unique identification of the NE in the system. It
     * includes the user name ("nEUserName"), the distinguished name
     * ("nEDistinguishedName") and the software version (nESoftwareVersion) of
     * the NE.
     */
    String NEID_ELEMENT = "neid";

    /**
     * nEUserName, neun. This is the user definable NE name, cf. 3GPP TS 32.300
     * [10]. The string may be empty (i.e. string size =0) if the "nEUserName"
     * is not configured in the CM applications.
     */
    String NEUN_ELEMENT = "neun";

    /**
     * nEDistinguishedName,	nedn. This is the distinguishedName (DN) defined for
     * the NE in 3GPP TS 32.300 [10]. It is unique across an operator's 3G
     * network. The string may be empty (i.e. string size =0) if the
     * " nEDistinguishedName " is not configured in the CM applications.
     */
    String NEDN_ELEMENT = "nedn";

    /**
     * nESoftwareVersion, nesw. This is the software version (swVersion) defined
     * for the NE in 3GPP TS 32.622.
     * This is an optional parameter which allows post-processing systems to take
     * care of vendor specific measurements modified between software versions.
     */
    String NESW_ELEMENT = "nesw";

    /**
     * measInfo, mi. The sequence of measurements, values and related
     * information. It includes a list of measurement types ("measTypes") and
     * the corresponding results ("measValues"), together with the time stamp
     * ("measTimeStamp") and granularity period ("granularityPeriod") pertaining
     * to these measurements.
     */
    String MI_ELEMENT = "mi";

    /**
     * measTimeStamp, mts. Time stamp referring to the end of the granularity
     * period.
     */
    String MTS_ELEMENT = "mts";

    /**
     * granularityPeriod, gp. Granularity period of the measurement(s) in seconds.
     */
    String GP_ELEMENT = "gp";

    /**
     * measTypes, mt. This is the list of measurement types for which the
     * following, analogous list of measurement values ("measValues") pertains.
     * The GSM only measurement types are defined in TS 52.402 [22]. The
     * measurement types for UMTS and combined UMTS/GSM implementations are
     * specified in TS 32.403 [23].
     */
    String MT_ELEMENT = "mt";

    /**
     * measValues, mv. This parameter contains the list of measurement results
     * for the resource being measured, e.g. trunk, cell. It includes an
     * identifier of the resource ("measObjInstId"), the list of measurement
     * result values ("measResults") and a flag that indicates whether the data
     * is reliable ("suspectFlag").
     */
    String MV_ELEMENT = "mv";

    /**
     * measObjInstId, moid. The "measObjInstId" field identifies the relative
     * distinguished name (RDN) of the measured object within the scope defined
     * by the nEDistinguishedName. The concatenation of the nEDistinguishedName
     * and the measObjInstId yields the DN of the measured object. The
     * measObjInstId is therefore empty if the nEDistinguishedName already
     * specifies completely the DN of the measured object, which is the case for
     * all measurements specified on NE level. For example, if the measured
     * object is an "RncFunction" representing RNC "RF-1", then the
     * nEDistinguishedName will be
     * "G3SubNetwork=Sweden,MeContext=MEC-Gbg-1,G3ManagedElement=RNC-Gbg-1,RncFunction=RF-1",
     * and the measObjInstId is empty. On the other hand, if the measured object
     * is a "UtranCell", representing cell "Gbg-997" managed by that RNC, then
     * the nEDistinguishedName would be as before, i.e.
     * "G3SubNetwork=Sweden,MeContext=MEC-Gbg-1,G3ManagedElement=RNC-Gbg-1,RncFunction=RF-1"
     * and the measObjInstId is "UtranCell=Gbg-997" The class of the
     * measObjInstId is defined in item F of each measurement definition template.
     */
    String MOID_ELEMENT = "moid";

    /**
     * measResults, r. This parameter contains the sequence of result values for
     * the observed measurement types. The "measResults" sequence shall have the
     * same number of elements, which follow the same order as the measTypes
     * sequence. Normal values are INTEGERs and REALs. The NULL value is
     * reserved to indicate that the measurement item is not applicable or could
     * not be retrieved for the object instance.
     */
    String R_ELEMENT = "r";

    /**
     * suspectFlag, sf. Used as an indication of quality of the scanned data.
     * FALSE in the case of reliable data, TRUE if not reliable. The default
     * value is "FALSE", in case the suspect flag has its default value it may
     * be omitted.
     */
    String SF_ELEMENT = "sf";

    /**
     * TimeStamp, ts. ASN.1 GeneralizedTime format. The minimum required
     * information within timestamp is year, month, day, hour, minute, and
     * second.
     */
    String TS_ELEMENT = "ts";

    /**
     * optional positioning attribute, mt p. Not Required. An optional positioning
     * attribute of the &ltmt/&gt tag, used to identify a measurement type for
     * the purpose of correlation to a result. The value of this field is
     * expected to be a non-zero, non-negative integer value, that is unique for
     * each instance of the &ltmt/&gt tag that is contained within the
     * measurement data collection file.
     */
    String MT_P_ATTRIBUTE = "p";

    /**
     * optional positioning attribute, r p. Not Required. An optional
     * positioning attribute of the &ltr/&gt tag, used to correlate a result to a
     * measurement type. The value used for the r p attribute should match the
     * value of the corresponding mt p attribute.
     */
    String R_P_ATTRIBUTE = "p";
}