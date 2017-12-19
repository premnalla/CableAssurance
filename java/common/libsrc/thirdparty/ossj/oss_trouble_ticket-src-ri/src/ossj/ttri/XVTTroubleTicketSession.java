package ossj.ttri;

import javax.oss.SetException;

/**
 * XVTTroubleTicketSession Interface
 * Not part of the specification used a synchronous XML Facade
 * by the XVT Message Driven Bean.
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface XVTTroubleTicketSession extends javax.ejb.EJBObject {
    /*MetaData Methods*/

    //Get the Options supported by a Session Bean
    //Input Parameter is equal to  getSupportedOperationsRequest as defined in XML Schema
    //Output Parameter is equal to  getSupportedOperationsResponse as defined in XML Schema
    String getSupportedOperations(String GetSupportedOperationsRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    //Get the Trouble Ticket types supported by the Session Component
    //Input Parameter is equal to  getTroubleTicketTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getTroubleTicketTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String getTroubleTicketTypes(String GetTroubleTicketTypesRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    //Get the Query Names supported by the Session Component
    //Input Parameter is equal to  getQueryTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getQueryTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String getQueryTypes(String GetQueryTypesRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    //Get the Event Type Names supported by the Session Component
    //Input Parameter is equal to  getEventTypesRequest as defined in XML Schema
    //Output Parameter is equal to  getEventTypesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String getEventTypes(String GetEventTypesRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    //Create a Query Value Instance matching a Query type
    //Input Parameter is equal to  newQueryValueRequest as defined in XML Schema
    //Output Parameter is equal to  newQueryValueResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String makeQueryValue(String NewQueryValueRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;


    //Get the Event Descriptor associated with an event type
    //Input Parameter is equal to  getEventPropertyDescriptorRequest as defined in XML Schema
    //Output Parameter is equal to getEventPropertyDescriptorResponse as defined in XML Schema
    //which contains the XML representation of the EventPropertyDescriptor
    String getEventPropertyDescriptor(String GetEventPropertyDescriptorRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    /*Value Factory Methods */
    //Create a Value Type object for a specific Trouble Ticket type
    //Not to be confused with the creation of a Trouble Ticket
    //The Session Bean is used as a factory for the creation of Value types

    //Input Parameter is equal to  newTroubleTicketValueRequest as defined in XML Schema
    //Output Parameter is equal to newTroubleTicketValueResponse as defined in XML Schema
    String makeTroubleTicketValue(String NewTroubleTicketValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.CreateException, java.rmi.RemoteException;


    /*Get Methods*/

    //Get a Single Trouble Ticket given its ID and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to GetTroubleTicketByKeyResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    //---Implemented for the Demo---
    //IllegalArgument
    String getTroubleTicketByKey(String GetTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Get Multiple Trouble Tickets given their Ids and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to GetTroubleTicketByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String getTroubleTicketsByKeys(String GetTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException, java.rmi.RemoteException;

    //Get Multiple Trouble Tickets matching  the value template and return only the requested attributes
    //Input Parameter is equal to GetTroubleTicketByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to XmlValueIterator where the XmlResponseIterator return a single
    //GetTroubleTicketByTemplateResponse popultaed with how_many Trouble Tickets as defined
    //in XML Schema
    //Exceptions Contains XML as defined in Schema
    XmlResponseIterator
            getTroubleTicketsByTemplate(String GetTroubleTicketByTemplateRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Get Multiple Trouble Tickets matching at least one of the value template and return only the
    //requested attributes
    //Input Parameter is equal to GetTroubleTicketByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to XmlValueIterator where the XmlIterator return a single
    //GetTroubleTicketByTemplatesResponse populatedd with how_many Trouble Tickets as defined
    //in XML Schema
    //Exceptions Contains XML as defined in Schema
    XmlResponseIterator
            getTroubleTicketsByTemplates(String GetTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Creation of a Single Trouble Ticket
    //Input Parameter is equal to  createTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    String createTroubleTicketByValue(String CreateTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException, java.rmi.RemoteException;


    //Setting a Single Trouble Ticket given a Value Object
    //Input Parameter is equal to  SetTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketByValueResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    //---Implemented for the Demo---
    String setTroubleTicketByValue(String SetTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException, javax.oss.ResyncRequiredException;

    //Close a Trouble Ticket matching a given key
    //Input Parameter is equal to  CloseTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to CloseTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    String closeTroubleTicketByKey(String CloseTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            java.rmi.RemoteException;

    //Cancel a Trouble Ticket matching a given key
    //Input Parameter is equal to  CancelTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    String cancelTroubleTicketByKey(String CancelTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            java.rmi.RemoteException;

    //Escalate a Trouble Ticket matching a given key
    //Input Parameter is equal to  EscalateTroubleTicketByKeyRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByKeyResponse which is itself defined
    //in the XML Schema
    //---Implemented for the Demo---
    String escalateTroubleTicketByKey(String EscalateTroubleTicketByKeyRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException,
            java.rmi.RemoteException;


    //Query Multiple Trouble Tickets using a Query XML Document
    //Input Parameter is equal to  queryTroubleTicketsRequest as defined in XML Schema
    //Output Parameter is an XmlResponseIterator wich contains queryTroubleTicketsResponse
    //which is itself defined in the XML Schema
    //queryTroubleTickets TBD

    XmlResponseIterator queryTroubleTickets(String QueryTroubleTicketsRequest)
            throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    /*Bulk Set Methods - Best Effort */

    //Setting Multiple Trouble Tickets each with different values
    //Input Parameter is equal to  TrySetTroubleTicketsByValuesRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByValuesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String trySetTroubleTicketsByValues(String TrySetTroubleTicketsByValuesRequest)
            throws javax.oss.IllegalArgumentException,
            SetException, javax.ejb.DuplicateKeyException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets given their ID with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String trySetTroubleTicketsByKeys(String TrySetTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets matching  the template with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByTemplateResponse as defined in XML Schema
    XmlResponseIterator trySetTroubleTicketsByTemplate(String TrySetTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets matching at least one of the template with the same value
    //Input Parameter is equal to  TrySetTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TrySetTroubleTicketsByTemplatesResponse as defined in XML Schema
    XmlResponseIterator trySetTroubleTicketsByTemplates(String TrySetTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, SetException,
            javax.ejb.FinderException, javax.ejb.DuplicateKeyException,
            java.rmi.RemoteException;


    /*Bulk Create Methods - Best Effort */

    //Creation of Multiple Trouble Tickets each with a different value
    //Input Parameter is equal to  TryCreateTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to TryCreateTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    String tryCreateTroubleTicketsByValues(String TryCreateTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException, java.rmi.RemoteException;
    /**
     //Creation of Multiple Trouble Tickets each with the same value
     //Input Parameter is equal to  TryCreateTroubleTicketsByKeysRequest as defined in XML Schema
     //Output Parameter is equal to TryCreateTroubleTicketsByKeysResponse which is itself defined
     //in the XML Schema
     String tryCreateTroubleTicketsByKeys(String TryCreateTroubleTicketsByKeysRequest )
     throws javax.oss.IllegalArgumentException,  javax.ejb.DuplicateKeyException,
     javax.ejb.CreateException, java.rmi.RemoteException;

     **/
    /*Bulk Close Trouble Ticket Methods Best Effort*/


    //Close Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TryCloseTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    String tryCloseTroubleTicketsByKeys(String TryCloseTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException,
            javax.ejb.RemoveException, java.rmi.RemoteException;

    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCloseTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryCloseTroubleTicketsByTemplate(String TryCloseTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.RemoveException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCloseTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to TryCloseTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryCloseTroubleTicketsByTemplates(String TryCloseTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.RemoveException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    /*Bulk Cancel Trouble Ticket Methods Best Effort*/



    //Cancel Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TryCancelTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    String tryCancelTroubleTicketsByKeys(String TryCancelTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.ejb.FinderException,
            java.rmi.RemoteException;

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCancelTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketsByTemplateResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryCancelTroubleTicketsByTemplate(String TryCancelTroubleTicketsByValueRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryCancelTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TryCancelTroubleTicketsByTemplatesResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryCancelTroubleTicketsByTemplates(String TryCancelTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    /*Escalate Trouble Ticket Methods Best Effort*/



    //Escalate Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  TryEscalateTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    String tryEscalateTroubleTicketsByKeys(String TryEscalateTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryEscalateTroubleTicketByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByTemplateResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryEscalateTroubleTicketsByTemplate(String TryEscalateTroubleTicketByTemplateRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  TryEscalateTroubleTicketByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to TryEscalateTroubleTicketByTemplatesResponse which is itself defined
    //in the XML Schema
    XmlResponseIterator tryEscalateTroubleTicketsByTemplates(String TryEscalateTroubleTicketByTemplatesRequest)
            throws javax.oss.IllegalArgumentException,
            javax.ejb.FinderException, java.rmi.RemoteException;


    /*Atomic Operations */

    /*Bulk Set Methods - Atomic */

    //Setting Multiple Trouble Tickets each with different values
    //Input Parameter is equal to  SetTroubleTicketsByValuesRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByValuesResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String setTroubleTicketsByValues(String SetTroubleTicketsByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            SetException, javax.ejb.DuplicateKeyException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets given their ID with the same value
    //Input Parameter is equal to  SetTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByKeysResponse as defined in XML Schema
    //Exceptions Contains XML as defined in Schema
    String setTroubleTicketsByKeys(String SetTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets matching  the template with the same value
    //Input Parameter is equal to  SetTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByTemplateResponse as defined in XML Schema
    String setTroubleTicketsByTemplate(String SetTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Setting Multiple Trouble Tickets matching at least one of the template with the same value
    //Input Parameter is equal to  SetTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to SetTroubleTicketsByTemplatesResponse as defined in XML Schema
    String setTroubleTicketsByTemplates(String SetTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, SetException,
            javax.ejb.FinderException, javax.ejb.DuplicateKeyException,
            java.rmi.RemoteException;


    /*Bulk Create Methods - Atomic */

    //Creation of Multiple Trouble Tickets each with a different value
    //Input Parameter is equal to  createTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to createTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    String createTroubleTicketsByValues(String CreateTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException,
            javax.ejb.CreateException, java.rmi.RemoteException;

    /**
     //Creation of Multiple Trouble Tickets each with the same value
     //Input Parameter is equal to  createTroubleTicketsByKeysRequest as defined in XML Schema
     //Output Parameter is equal to createTroubleTicketsByKeysResponse which is itself defined
     //in the XML Schema
     String createTroubleTicketsByKeys(String CreateTroubleTicketsByKeysRequest )
     throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException,
     javax.ejb.CreateException, java.rmi.RemoteException;

     **/
    /*Closing Trouble Ticket Methods Atomic*/


    //Close Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  closeTroubleTicketsByKeysRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketsByKeysResponse which is itself defined
    //in the XML Schema
    String closeTroubleTicketsByKeys(String CloseTroubleTicketsByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.FinderException,
            javax.ejb.RemoveException, java.rmi.RemoteException;

    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  closeTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketsByTemplateResponse which is itself defined
    //in the XML Schema
    String closeTroubleTicketsByTemplate(String CloseTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            javax.ejb.RemoveException,
            javax.ejb.ObjectNotFoundException,
            java.rmi.RemoteException;

    //Close multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  closeTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to closeTroubleTicketsByTemplatesResponse which is itself defined
    //in the XML Schema
    String closeTroubleTicketsByTemplates(String CloseTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.RemoveException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    /*Cancel Trouble Ticket Methods Atomic*/


    //Cancel Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  CancelTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    String cancelTroubleTicketsByKeys(String CancelTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.FinderException,
            java.rmi.RemoteException;

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  CancelTroubleTicketsByTemplateRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketsByTemplateResponse which is itself defined
    //in the XML Schema
    String cancelTroubleTicketsByTemplate(String CancelTroubleTicketsByTemplateRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Cancel multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  CancelTroubleTicketsByTemplatesRequest as defined in XML Schema
    //Output Parameter is equal to CancelTroubleTicketsByTemplatesResponse which is itself defined
    //in the XML Schema
    String cancelTroubleTicketsByTemplates(String CancelTroubleTicketsByTemplatesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    /*Escalate Trouble Ticket Methods Atomic */



    //Escalate Multiple Trouble Tickets given their keys
    //Input Parameter is equal to  EscalateTroubleTicketByKeysRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByKeysResponse which is itself defined
    //in the XML Schema
    String escalateTroubleTicketsByKeys(String CancelTroubleTicketByKeysRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, java.rmi.RemoteException;

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  EscalateTroubleTicketByValueRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByValueResponse which is itself defined
    //in the XML Schema
    String escalateTroubleTicketByTemplate(String CancelTroubleTicketByValueRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

    //Escalate multiple Trouble Ticket matching at least one of the template
    //Input Parameter is equal to  EscalateTroubleTicketByValuesRequest as defined in XML Schema
    //Output Parameter is equal to EscalateTroubleTicketByValuesResponse which is itself defined
    //in the XML Schema
    String escalateTroubleTicketsByTemplate(String CancelTroubleTicketByValuesRequest)
            throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
            javax.ejb.FinderException, java.rmi.RemoteException;


}
