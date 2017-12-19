/*
 * Copyright 1999-2000,2004 The Apache Software Foundation.
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

/**
 * $Id: StdInInputSource.cpp,v 1.1.1.1 2006/04/05 02:29:42 prem Exp $
 */


// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/util/BinFileInputStream.hpp>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/framework/StdInInputSource.hpp>

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  StdInInputSource: Implementation of the input source interface
// ---------------------------------------------------------------------------
BinInputStream* StdInInputSource::makeStream() const
{
    // Open a binary file stream for the standard input file handle
    BinFileInputStream* retStream = new (getMemoryManager()) BinFileInputStream
    (
        XMLPlatformUtils::openStdInHandle(getMemoryManager())
    );

    if (!retStream->getIsOpen())
    {
        delete retStream;
        return 0;
    }
    return retStream;
}

XERCES_CPP_NAMESPACE_END
