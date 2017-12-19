/*
 * Copyright 2001,2004 The Apache Software Foundation.
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
 * $Id: IC_KeyRef.cpp,v 1.1.1.1 2006/04/05 02:29:43 prem Exp $
 */

// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/validators/schema/identity/IC_KeyRef.hpp>

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  IC_KeyRef: Constructors and Destructor
// ---------------------------------------------------------------------------
IC_KeyRef::IC_KeyRef(const XMLCh* const identityConstraintName,
                     const XMLCh* const elemName,
                     IdentityConstraint* const icKey,
                     MemoryManager* const manager)
    : IdentityConstraint(identityConstraintName, elemName, manager)
    , fKey(icKey)
{
}


IC_KeyRef::~IC_KeyRef()
{
}

/***
 * Support for Serialization/De-serialization
 ***/

IMPL_XSERIALIZABLE_TOCREATE(IC_KeyRef)

void IC_KeyRef::serialize(XSerializeEngine& serEng)
{
    IdentityConstraint::serialize(serEng);

    if (serEng.isStoring())
    {
        IdentityConstraint::storeIC(serEng, fKey);
    }
    else
    {
        fKey = IdentityConstraint::loadIC(serEng);
    }

}

IC_KeyRef::IC_KeyRef(MemoryManager* const manager)
:IdentityConstraint(0, 0, manager)
,fKey(0)
{
}

XERCES_CPP_NAMESPACE_END

/**
  * End of file IC_KeyRef.cpp
  */


