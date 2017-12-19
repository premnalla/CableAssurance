/*
 * Copyright 2003-2006 The Apache Software Foundation.

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file was auto-generated by the Axis C++ Web Service Generator (WSDL2Ws)
 * This file contains Web Service Wrapper declarations
 */

#if !defined(__INTEROPTESTPORTTYPEWRAPPER_SERVERWRAPPER_H__INCLUDED_)
#define __INTEROPTESTPORTTYPEWRAPPER_SERVERWRAPPER_H__INCLUDED_

#include "InteropTestPortType.hpp"
#include <axis/server/WrapperClassHandler.hpp>
#include <axis/IMessageData.hpp>
#include <axis/GDefine.hpp>
#include <axis/Axis.hpp>
#include <axis/AxisWrapperAPI.hpp>
#include "AxisServiceException.hpp" 
AXIS_CPP_NAMESPACE_USE 

class InteropTestPortTypeWrapper : public WrapperClassHandler
{
private:/* Actual web service object*/
	InteropTestPortType *pWs;
public:
	InteropTestPortTypeWrapper();
public:
	virtual ~InteropTestPortTypeWrapper();
public:/*implementation of WrapperClassHandler interface*/
	int AXISCALL invoke(void* pMsg);
	void AXISCALL onFault(void* pMsg);
	AXIS_BINDING_STYLE AXISCALL getBindingStyle(){return RPC_ENCODED;};
private:/*Methods corresponding to the web service methods*/
	int echoString(void* pMsg);
	int echoStringArray(void* pMsg);
	int echoInteger(void* pMsg);
	int echoIntegerArray(void* pMsg);
	int echoFloat(void* pMsg);
	int echoFloatArray(void* pMsg);
	int echoStruct(void* pMsg);
	int echoStructArray(void* pMsg);
	int echoVoid(void* pMsg);
	int echoBase64(void* pMsg);
	int echoDate(void* pMsg);
	int echoHexBinary(void* pMsg);
	int echoDecimal(void* pMsg);
	int echoBoolean(void* pMsg);
};

#endif /* !defined(__INTEROPTESTPORTTYPEWRAPPER_SERVERWRAPPER_H__INCLUDED_)*/
