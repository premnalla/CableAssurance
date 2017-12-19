#ifndef SNMPVACMPROCESSINCOMINGASI_H
#define SNMPVACMPROCESSINCOMINGASI_H

#include "snmpMessageObj.H"
#include "snmpStatusInfo.H"
#include "HeaderData.H"

#include "snmpErrorObj.H"
#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"
#include "snmpV3Message.H"

enum viewType 
{
    READ_VIEW = 5,         // matches the appropriate column number in the
    WRITE_VIEW = 6,        // vacmAccessTable.
    NOTIFY_VIEW = 7,       //
};

class VACMError : public snmpErrorObj {
 public:
   enum VACMResultCodes 
   {
       ACCESSALLOWED,
       NOTINVIEW,
       NOSUCHVIEW,
       NOSUCHCONTEXT,
       NOGROUPNAME,
       NOACCESSENTRY,
       OTHERERROR
   };

  VACMResultCodes resultCode;

  VACMError( VACMError & ref ) {
    this->resultCode = ref.resultCode;
  }

  VACMError(VACMResultCodes result = ACCESSALLOWED) {
    this->resultCode = result;
  }

  virtual VACMError *clone() { return new VACMError(*this); }
};

class snmpVACMIsAccessAllowedASI : public snmpMessageObj {

private:

   Integer32        *securityModel;
   OctetString      *securityName;
   Integer32        *securityLevel;
   viewType          theViewType;
   OctetString      *contextName;
   OID              *variableName;

  // -----------------------------------------

   snmpStatusInfo   *statusInformation;
   snmpFIFOObj      *theFIFO;
   snmpObj          *stateReference;
  
public:
   
   snmpVACMIsAccessAllowedASI(snmpVACMIsAccessAllowedASI &ref);
   snmpVACMIsAccessAllowedASI(Integer32        *securityModel = NULL,
                              OctetString      *securityName = NULL,
                              Integer32        *securityLevel = NULL,
                              viewType          viewType = READ_VIEW,
                              OctetString      *contextName = NULL,
                              OID              *variableName = NULL,
                              snmpStatusInfo   *newStatusInformation = NULL,
                              snmpFIFOObj      *theFIFO     = NULL,
                              snmpObj          *stateReference = NULL);


   virtual ~snmpVACMIsAccessAllowedASI();

   virtual snmpVACMIsAccessAllowedASI *clone() {
       return new snmpVACMIsAccessAllowedASI(*this);
   }

   // accessors
   void             set_securityModel(Integer32  *securityModel);
   Integer32       *get_securityModel(bool extract=false);
   void             set_securityName(OctetString  *securityName);
   OctetString     *get_securityName(bool extract=false);
   void             set_securityLevel(Integer32  *securityLevel);
   Integer32       *get_securityLevel(bool extract=false);
   void             set_contextName(OctetString  *contextName);
   OctetString     *get_contextName(bool extract=false);
   void             set_variableName(OID  *variableName);
   OID             *get_variableName(bool extract=false);
   void             set_statusInformation(snmpStatusInfo  *statusInformation);
   snmpStatusInfo  *get_statusInformation(bool extract=false);
   void             set_stateReference(snmpObj  *stateReference);
   snmpObj         *get_stateReference(bool extract=false);
   void             set_viewType(viewType viewtype);
   viewType         get_viewType();
};

#endif /* SNMPVACMISACCESSALLOWEDASI_H */
