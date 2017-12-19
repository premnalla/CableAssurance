

        /**
        * StockQuoteServiceMessageReceiverInOut.java
        *
        * This file was auto-generated from WSDL
        * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
        */
        package com.nds.www.stockquote.service.adb;

        /**
        *  StockQuoteServiceMessageReceiverInOut message receiver
        */

        public class StockQuoteServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        StockQuoteServiceSkeletonInterface skel = (StockQuoteServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if(op.getName() != null & (methodName = op.getName().getLocalPart()) != null){

        

            if("getPrice".equals(methodName)){

            
            stockquoteservice.xsd.GetPriceResponse param7 = null;
                    
                            //doc style
                            stockquoteservice.xsd.GetPrice wrappedParam =
                                                 (stockquoteservice.xsd.GetPrice)fromOM(
                        msgContext.getEnvelope().getBody().getFirstElement(),
                        stockquoteservice.xsd.GetPrice.class,
                        getEnvelopeNamespaces(msgContext.getEnvelope()));
                                    
                                   param7 =
                                             skel.getPrice(wrappedParam) ;
                                        
                                    envelope = toEnvelope(getSOAPFactory(msgContext), param7, false);
                                

            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(stockquoteservice.xsd.Update param, boolean optimizeContent){
            
                     return param.getOMElement(stockquoteservice.xsd.Update.MY_QNAME,
                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(stockquoteservice.xsd.GetPrice param, boolean optimizeContent){
            
                     return param.getOMElement(stockquoteservice.xsd.GetPrice.MY_QNAME,
                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(stockquoteservice.xsd.GetPriceResponse param, boolean optimizeContent){
            
                     return param.getOMElement(stockquoteservice.xsd.GetPriceResponse.MY_QNAME,
                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, stockquoteservice.xsd.GetPriceResponse param, boolean optimizeContent){
                      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                       
                                emptyEnvelope.getBody().addChild(param.getOMElement(stockquoteservice.xsd.GetPriceResponse.MY_QNAME,factory));
                            

                     return emptyEnvelope;
                    }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces){

        try {
        
                if (stockquoteservice.xsd.Update.class.equals(type)){
                
                           return stockquoteservice.xsd.Update.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (stockquoteservice.xsd.GetPrice.class.equals(type)){
                
                           return stockquoteservice.xsd.GetPrice.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (stockquoteservice.xsd.GetPriceResponse.class.equals(type)){
                
                           return stockquoteservice.xsd.GetPriceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    