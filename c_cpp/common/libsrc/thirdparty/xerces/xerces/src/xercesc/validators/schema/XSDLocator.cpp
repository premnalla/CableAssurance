/*
 * Copyright 2002,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Id: XSDLocator.cpp,v 1.1.1.1 2006/04/05 02:29:43 prem Exp $
 */


// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/validators/schema/XSDLocator.hpp>

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  XSDLocator: Constructors and Destructor
// ---------------------------------------------------------------------------
XSDLocator::XSDLocator() :
    fLineNo(0)
    , fColumnNo(0)
    , fSystemId(0)
    , fPublicId(0)
{

}


// ---------------------------------------------------------------------------
//  XSDLocator: Setter methods
// ---------------------------------------------------------------------------
void XSDLocator::setValues(const XMLCh* const systemId,
                           const XMLCh* const publicId,
                           const XMLSSize_t lineNo, const XMLSSize_t columnNo)
{
    fLineNo = lineNo;
    fColumnNo = columnNo;
    fSystemId = systemId;
    fPublicId = publicId;
}

XERCES_CPP_NAMESPACE_END
