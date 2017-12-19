/*
 * This file was auto-generated by the Axis C++ Web Service Generator (WSDL2Ws)
 * This file contains an Exception class of the web service.
 */

#if !defined(__AXISSERVICEEXCEPTION_EXCEPTION_H__INCLUDED_)
#define __AXISSERVICEEXCEPTION_EXCEPTION_H__INCLUDED_

#include <string>
#include <exception>
#include <axis/AxisException.hpp>
#include <axis/ISoapFault.hpp>
using namespace std;
AXIS_CPP_NAMESPACE_USE 

class AxisServiceException: public AxisException
{
public:
	STORAGE_CLASS_INFO AxisServiceException(ISoapFault* pFault);
	STORAGE_CLASS_INFO AxisServiceException(const int iExceptionCode, const char* pcMessage = NULL );
	STORAGE_CLASS_INFO AxisServiceException(const AxisServiceException& e);
	STORAGE_CLASS_INFO virtual ~AxisServiceException() throw();
	STORAGE_CLASS_INFO const ISoapFault* getFault();

private:
	STORAGE_CLASS_INFO string getMessageForExceptionCode(int iExceptionCode);
	 ISoapFault* m_pISoapFault;
	 string m_sMessageForExceptionCode;

};

#endif /* !defined(__AXISSERVICEEXCEPTION_EXCEPTION_H__INCLUDED_)*/
