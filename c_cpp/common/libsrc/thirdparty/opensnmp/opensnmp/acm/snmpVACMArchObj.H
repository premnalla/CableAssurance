#ifndef SNMPVACMARCHOBJ_H
#define SNMPVACMARCHOBJ_H

#include <set>

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "snmpVACMArchRegObj.H"
#include "snmpVACMIsAccessAllowedASI.H"
#include "snmpDataTable.H"

class snmpVACMArchObj : public snmpArchObj {

 private:
   friend class snmpVACMArchRegObj;  // to share the fifoPtr

   DEBUGDECLARE(debugObj);

   snmpDataTable *securityToGroupTable;
   snmpDataTable *accessTable;
   snmpDataTable *MIBViewsTable;
   snmpDataTable *contextTable;

   void create_securityToGroupTable();
   void create_vacmAccessTable();
   void create_vacmViewTreeFamilyTable();
   void create_vacmContextTable();
   void createTables();

 protected:
   snmpFIFOObj  *fifoPtr;
   snmpFIFOObj  *crFIFO;
   
 public:
   snmpVACMArchObj(const snmpVACMArchObj &);
   snmpVACMArchObj(snmpFIFOObj *);
   virtual ~snmpVACMArchObj();

   virtual snmpVACMArchObj *clone() const {
     return new snmpVACMArchObj(*this);
   }

   virtual void main_loop();

   snmpVACMIsAccessAllowedASI *returnError(snmpVACMIsAccessAllowedASI *msg, 
                                           VACMError::VACMResultCodes errorcode);
   virtual snmpVACMIsAccessAllowedASI *isAccessAllowed(snmpVACMIsAccessAllowedASI *msg);

   // conversion functions.
   virtual int USMSecLevel_to_VACMSecLevel(int lev);

   // enum lists for row numbers and column enums.

   // vacmSecurityToGroupTable
   enum vacmSecurityTableCols
   {
       VS_vacmSecurityModel_COL              = 1,
       VS_vacmSecurityName_COL               = 2,
       VS_vacmGroupName_COL                  = 3,
       VS_vacmSecurityToGroupStorageType_COL = 4,
       VS_vacmSecurityToGroupStatus_COL      = 5,
   };
   

   
   // vacmAccessTable:
   enum vacmAccessTableCols
   {
       VA_ContextPrefix_COL  = 1,
       VA_SecurityModel_COL  = 2,
       VA_SecurityLevel_COL  = 3,
       VA_ContextMatch_COL   = 4,
       VA_ReadViewName_COL   = 5,
       VA_WriteViewName_COL  = 6,
       VA_NotifyViewName_COL = 7,
       VA_StorageType_COL    = 8,
       VA_Status_COL         = 9
   };

   enum vacmAccessTableIndexes
   {
       VA_GroupName_IDX      = 1,
       VA_ContextPrefix_IDX  = 2,
       VA_SecurityModel_IDX  = 3,
       VA_SecurityLevel_IDX  = 4,
   };
   
   enum vacmAccessContextMatch_enum
   {
       VACM_exact = 1,
       VACM_prefix = 2,
   };

   enum vacmSecurityLevel
   {
       VACM_noAuthNoPriv = 1,
       VACM_authNoPriv = 2,
       VACM_authPriv = 3,
   };

   enum vacmSecurityModel
   {
       VACM_USM = 3, 
       VACM_any = 0,
   };

   // vacmViewTreeFamilyTable
   enum vacmViewTreeFamilyCols
   {
       VVTF_vacmViewTreeFamilyViewName_COL    = 1,
       VVTF_vacmViewTreeFamilySubtree_COL     = 2,
       VVTF_vacmViewTreeFamilyMask_COL        = 3,
       VVTF_vacmViewTreeFamilyType_COL        = 4,
       VVTF_vacmViewTreeFamilyStorageType_COL = 5,
       VVTF_vacmViewTreeFamilyStatus_COL      = 6,
   };

   enum vacmViewTreeFamilyTypes 
   {
       VACM_included = 1, 
       VACM_excluded = 2
   };

   // vacmContextTable
   enum vacmContextTableCols 
   {
       VC_vacmContextName_COL = 1,
   };
   
};

#endif /* SNMPVACMARCHOBJ_H ifndef */
