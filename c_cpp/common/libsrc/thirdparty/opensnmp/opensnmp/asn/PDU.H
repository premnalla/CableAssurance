#ifndef PDU_H
#define PDU_H

#include "asnDataType.H"
#include "Integer32.H"
#include "VarBindList.H"
#include "snmpMutexObj.H"

enum PDU_Version
{
  PDU_VERSION_1,
  PDU_VERSION_2
};

typedef berTag PDU_Type;

class PDU      : public asnDataType {
 private:
   static  int           requestID_generator;
   static  snmpMutexObj  requestID_mutex;

   PDU_Version version;
   Integer32   request_id;
   Integer32   error_index;
   Integer32   error_status;
   VarBindList *varbinds;
   
 public:
   // NOTE: the proper way to create a unique request ID is to create
   // a pdu (with new_requestd_id defaulted or '0') and then use
   // get_requestID() to retrieve the generated request ID.  This
   // should ensure uniqueness engine wide.  If you set it by hand,
   // your on your own.
   PDU(berTag pdu_type       = BER_TAG_PDU_GET,
       int new_request_id    = 0,
       int error_index       = 0,
       int error_status      = 0,
       VarBindList *varbinds = new VarBindList(),
       PDU_Version version   = PDU_VERSION_2);

   PDU(const PDU &thePDU);

   virtual ~PDU();

   void EraseData();

   void Add(VarBind *);

   int length();

   int get_requestID();
   int get_errorIndex();
   int get_errorStatus();
   VarBindList *get_varBindList(bool extract = false);
   // get_varbind returns the varbind at position 'index' in the list
   // of varbinds. 
   // It returns NULL if the index value is too large or small.
   VarBind *    get_varBind(int index, bool extract = false);
   PDU_Version  get_version();
   PDU_Type     get_pduType();

   void set_pduType(PDU_Type type);
   void set_varBindList(VarBindList *newvbl);
   void set_requestID(int request_id);
   // creates a new unique requestID and returns it.
   virtual int create_new_requestID();
   void set_errorIndex(int error_index);
   void set_errorStatus(int error_status);
   void set_version(PDU_Version version);
   
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int length);
   virtual char *DecodeHeader(char *cp, int *thelength);

   //   print it!
   virtual operator string() const;
   virtual std::string toString() const;
   virtual std::string get_type() const;

   virtual PDU *clone() const { return new PDU(*this); };
   virtual PDU &change_value(const asnDataType &right);
};

#endif /* PDU_H */
