#ifndef ASNDATATYPE_H
#define ASNDATATYPE_H

#include "snmpObj.H"
#include "asnBadAssignment.H"
#include <exception>

// ber specific types

enum berTag {
// BER defined tags 
  BER_TAG_BOOLEAN       = (0x01),
  BER_TAG_INTEGER32     = (0x02),
  BER_TAG_BIT_STRING    = (0x03),
  BER_TAG_OCTET_STRING  = (0x04),
  BER_TAG_NULL	        = (0x05),
  BER_TAG_OBJECT_ID     = (0x06),

  BER_TAG_SEQUENCE      = (0x30),

// Application specific (SNMP specific) types (= 0x40 | TAG)
  BER_TAG_IPADDRESS     = (0x40),
  BER_TAG_COUNTER       = (0x41),
  BER_TAG_GAUGE         = (0x42),
  BER_TAG_UNSIGNED32    = (0x42),  // Same as Gauge
  BER_TAG_TIMETICKS     = (0x43),
  BER_TAG_OPAQUE        = (0x44),
  BER_TAG_COUNTER64     = (0x46),
  
// Application specific (SNMP specific) PDU types (= 0x20 | 0x80 | TAG)
  BER_TAG_PDU_GET          = (0xA0),
  BER_TAG_PDU_GETNEXT      = (0xA1),
  BER_TAG_PDU_RESPONSE     = (0xA2),
  BER_TAG_PDU_SET          = (0xA3),

  BER_TAG_PDU_TRAP         = (0xA4), // SNMPv1 only.
  BER_TAG_PDU_GETBULK      = (0xA5),
  BER_TAG_PDU_INFORM       = (0xA6),
  BER_TAG_PDU_TRAP2        = (0xA7),
  BER_TAG_PDU_REPORT       = (0xA8),

// Errorrs essentially  (SNMP specific) = (0x80 | TAG)
  BER_TAG_NOSUCHOBJECT     = (0x80),
  BER_TAG_NOSUCHINSTANCE   = (0x81),
  BER_TAG_ENDOFMIBVIEW     = (0x82)
};

class OID;

class asnDataType : public snmpObj {
 protected:

   berTag tag;
   int headerLen;
   int dataLen;

  // NOTE: this operator streams ASCII, not ASN
  friend std::ostream& operator << ( std::ostream&, const asnDataType & );

 public:
   asnDataType();
   virtual ~asnDataType();
   asnDataType(berTag t);

   asnDataType & operator =( const asnDataType & from ) { return change_value( from ); };

   // support functions
   virtual void    set_tag(berTag t);
   virtual berTag  get_tag() const;

   // Reverse Encodings

   static char *EncodeLength(int len, char *buf);
   static char *EncodeHeader(char *cp, int len, const berTag & theTag);

   virtual char *asnEncode(char *) const;

   virtual char *DecodeLength(char *, int *);
   virtual char *DecodeHeader(char *, int *);
   virtual char *asnDecode(char *, int length);
   static  char *asnDecodeUnknown(char *cp, asnDataType **ppadt, int length);
   static  int   asnLengthWithHeader(char *);
   static  int   asnHeaderLength(char *);

   // must be defined by children
   virtual char *EncodeData(char *cp) const = 0;
   virtual void  DecodeData(char *cp, int len) = 0;
   virtual void  EraseData();

  //  Virtual functions from snmpObj
   virtual std::string toString() const = 0;
   virtual asnDataType *clone() const = 0;


  // the change_value method should be overridden by derived
  // classes to allow a new value to be set from an asnDataType
  // object. A dynamic_cast should be used to ensure that the
  // 'from' value is of the correct type.  If the derived object
  // has any special rules to enforce,  this is the place to do it.
   virtual asnDataType &change_value(const asnDataType &) {
       throw asnBadAssignment();
   }

  // the change_value method should be overridden by derived
  // classes to allow a new value to be set from an asnDataType
  // object. A dynamic_cast should be used to ensure that the
  // 'from' value is of the correct type.  This method should
  // be used when it is necessary to force a new value which
  // may not normally be allowed by change_value.
  virtual asnDataType &override_value(const asnDataType &from) {
    return change_value( from );
  }

   // the following routines check the compatibility of new values
   // with the old.  They should return one of the enum values if an
   // error condition is found.

   enum valueErrors {
       // these should match snmpProtoErr's protocol_error_enums values!
       // they are an exact subset.
       VALUE_NO_ERROR	     = 0,
       VALUE_TOOBIG               = 1,    // XXX? needed?  legal?
       VALUE_BADVALUE             = 3,  // v1 only (or proxy)
       VALUE_GENERR		  = 5,
       VALUE_WRONGTYPE            = 7,
       VALUE_WRONGVALUE           = 10,
       VALUE_INCONSISTENTVALUE    = 12,
       VALUE_RESOURCEUNAVAILABLE  = 13,
       VALUE_INCONSISTENTNAME     = 18
   };
   
   // checks that the new value is compatible with the old.
   virtual valueErrors check_type(const asnDataType &newval) {
       if (get_tag() != newval.get_tag())
           return VALUE_WRONGTYPE;
       return VALUE_NO_ERROR;
   }

   // checks to see that the value meets range/size restrictions.
   virtual valueErrors check_range(const asnDataType &) {
       return VALUE_NO_ERROR;
   }
   
   // checks to see that the new value is a legal value and that the
   // change to it from the current one is legal.
   virtual valueErrors check_value(const asnDataType &) {
       return VALUE_NO_ERROR;
   }

   // calls all of the check routines sequentially
   virtual valueErrors check_new_value(const asnDataType &newval) {
       valueErrors ret;
       if ((ret = check_type(newval)) != VALUE_NO_ERROR)
           return ret;
       if ((ret = check_range(newval)) != VALUE_NO_ERROR)
           return ret;
       if ((ret = check_value(newval)) != VALUE_NO_ERROR)
           return ret;
       return VALUE_NO_ERROR;
   }

   // bind_semantics can be used to make a datatype inherit the
   // required semantics of a node in the mib tree.  It should use the
   // mib classes to look up things like range and/or size
   // restrictions and remember them for future calls of check_new_value();
   virtual void bind_semantics(OID &) {}
};

#endif /* ASNDATATYPE_H */
