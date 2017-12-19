#ifndef SNMPCG_H
#define SNMPCG_H

/* CG allows to different methods of sending PDU's through the Opensnmp
   engine: 
   
   Synchronous : Uses send_and_wait to send a PDU and wait for a
                 response. This is the simplest method, but it is
                 inefficient for sending a large number of PDU's.

		 Send the info and await the pdu responding.
		 Currentnly the error handling is limited to
		 returning NULL.
	       
   
   Asynchronous : Send the PDU but don't wait for a response. Your
                  application will now have to handle differentiating
                  between the incoming PDUs (using the PDU's
                  request ID).

		  To use this method, your application will create
		  and set the values in each sendPduArgs object
		  associated with each PDU. A sendPduArgs object is
		  passed in with a PDU to 'send_dont_wait'. 

		  Later, the application uses get_message to check
		  for any queued incoming messages.
*/


#include "snmpCGArch.H"
#include "snmpEngine.H"
#include "snmpRegObj.H"

class CG : public snmpObj {

 private:
   static   snmpEngine   *theEngine;
   static   snmpRegObj   *archReg;

   DEBUGDECLARE(CGDEBUG);
   
   snmpFIFOObj      *ourFIFO;
   snmpFIFOObj      *cgArchFIFO;

 protected:
   virtual void init_internals();

 public:
   // TIMEOUT below is USED FOR SYNCHRONOUS OPERATION. The default
   // timeout is forever.  In general, a user who wants to set this
   // value should just use the asynchrounous calling procedure and
   // pass the timeout value in the maxwait parameter.

   // Note: For message timeouts, there is a sendPduArg timeout
   // value that should be used.  Using the pduArgs value will
   // result in an error message being returned to the application
   // when that message times out.
   unsigned int     timeout;

   CG();
   virtual ~CG();

   // shuts down all threads registered with the engine.
   virtual void shutdown();

   virtual CG *clone() const {
       throw;
   }

   /* main synchronous functions */
   virtual PDU   *send_and_wait(PDU *pdu, sendPduArgs & pArgs);

   /* main asynchronous functions */
   virtual bool  send_dont_wait(PDU *pdu, sendPduArgs & pArgs);
   
   // get_message has four basic types of results.  The CALLER is
   // responsible for freeeing any data assigned to *pdu or
   // *status. They will be set to NULL if no data of that type is
   // returned.

   // maxwait: the maximum time the call should wait for a message
   // before returning 'noMessage' (a message arriving will cause an
   // immediate return).  
   // '0' indicates return immediately (the defaullt). 
   // '-1' indicates wait forever until a message arrives.

   // The four types are: 

   // noMessage: no messages are waiting.  *pdu = NULL, *status =
   //            NULL.

   // response: an SNMP response from the agent, this usually
   //           indicates success. The *pdu will be the returned
   //           PDU, *status = NULL.

   // report: an SNMP report, this usually indicates an SNMP
   //         error returned by the agent (command receiver). The
   //         *pdu will be the returned PDU, *status = NULL.

   // internal_error: error within this engine, usually indicates a
   //                 problem sending the PDU.  *pdu will be the pdu
   //                 information initially sent, *status will be
   //                 snmpStatusInfo with any error information.

   enum messageTypeEnum { noMessage, response, report, internal_error };

   virtual messageTypeEnum get_message(PDU            * &pdu, 
				       snmpStatusInfo * &status,
				       OctetString    * &contextEngineID,
				       int              maxwait = 0);

   // takes the incoming message and prints out values if successful
   // or error if unsuccesful.
   virtual void print_incoming_pdu(PDU *pdu);

   /* convenience functions */
   // XXX these need to be updated to work with new cgArch thread
   // changes.
//    virtual VarBindList    *walk(const OID &oid);
//    virtual snmpDataTable  *get_table(const OID &oid);
   virtual void            set_engineID(string engID);

   // retuns the a pointer to snmpEngine.
   virtual snmpEngine * & get_engine(void);

   /* setup */
   DEFINE_ACCESSOR(unsigned int, timeout, );

};

#endif /* SNMPCG_H */
