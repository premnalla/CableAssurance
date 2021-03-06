/*
 * This is the Service Class genarated by the tool WSDL2Ws
 * InteropTestPortType.hpp: interface for the InteropTestPortTypeclass.
 *
 */
#if !defined(__INTEROPTESTPORTTYPE_SERVERSKELETON_H__INCLUDED_)
#define __INTEROPTESTPORTTYPE_SERVERSKELETON_H__INCLUDED_

#include <axis/AxisUserAPI.hpp>
#include <axis/AxisUserAPIArrays.hpp>
#include <axis/ISoapAttachment.hpp>
#include "AxisServiceException.hpp" 

#include "SOAPStruct_Array.hpp"
#include "SOAPStruct.hpp"

class InteropTestPortType 
{
	public:
		InteropTestPortType();
	public:
		virtual ~InteropTestPortType();
	public: 
		void onFault();
		xsd__string echoString(xsd__string Value0);
		xsd__string_Array * echoStringArray(xsd__string_Array * Value0);
		xsd__int echoInteger(xsd__int Value0);
		xsd__int_Array * echoIntegerArray(xsd__int_Array * Value0);
		xsd__float echoFloat(xsd__float Value0);
		xsd__float_Array * echoFloatArray(xsd__float_Array * Value0);
		SOAPStruct* echoStruct(SOAPStruct* Value0);
		SOAPStruct_Array * echoStructArray(SOAPStruct_Array * Value0);
		void echoVoid();
		xsd__base64Binary echoBase64(xsd__base64Binary Value0);
		xsd__dateTime echoDate(xsd__dateTime Value0);
		xsd__hexBinary echoHexBinary(xsd__hexBinary Value0);
		xsd__decimal echoDecimal(xsd__decimal Value0);
		xsd__boolean echoBoolean(xsd__boolean Value0);
};

#endif /* !defined(__INTEROPTESTPORTTYPE_SERVERSKELETON_H__INCLUDED_)*/
