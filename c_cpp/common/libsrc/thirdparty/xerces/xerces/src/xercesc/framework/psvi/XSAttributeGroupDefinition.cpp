/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 * $Id: XSAttributeGroupDefinition.cpp,v 1.1.1.1 2006/04/05 02:29:42 prem Exp $
 */

#include <xercesc/framework/psvi/XSAttributeGroupDefinition.hpp>
#include <xercesc/framework/psvi/XSAttributeUse.hpp>
#include <xercesc/validators/schema/XercesAttGroupInfo.hpp>
#include <xercesc/framework/psvi/XSModel.hpp>

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  XSAttributeGroupDefinition: Constructors and Destructor
// ---------------------------------------------------------------------------
XSAttributeGroupDefinition::XSAttributeGroupDefinition
(
    XercesAttGroupInfo* const   xercesAttGroupInfo
    , XSAttributeUseList* const xsAttList
    , XSWildcard* const         xsWildcard
    , XSAnnotation* const       xsAnnot
    , XSModel* const            xsModel
    , MemoryManager * const     manager
)
    : XSObject(XSConstants::ATTRIBUTE_GROUP_DEFINITION, xsModel, manager)
    , fXercesAttGroupInfo(xercesAttGroupInfo)
    , fXSAttributeUseList(xsAttList)
    , fXSWildcard(xsWildcard)
    , fAnnotation(xsAnnot)
{
}

XSAttributeGroupDefinition::~XSAttributeGroupDefinition()
{
    if (fXSAttributeUseList)
        delete fXSAttributeUseList;

    // don't delete fXSWildcard - deleted by XSModel
}

// XSObject methods
const XMLCh *XSAttributeGroupDefinition::getName() 
{
    return fXSModel->getURIStringPool()->getValueForId(fXercesAttGroupInfo->getNameId());        
}

const XMLCh *XSAttributeGroupDefinition::getNamespace() 
{
    return fXSModel->getURIStringPool()->getValueForId(fXercesAttGroupInfo->getNamespaceId());
}

XSNamespaceItem *XSAttributeGroupDefinition::getNamespaceItem() 
{
    return fXSModel->getNamespaceItem(getNamespace());
}


XERCES_CPP_NAMESPACE_END

