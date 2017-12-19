#ifndef DOMDocumentRange_HEADER_GUARD_
#define DOMDocumentRange_HEADER_GUARD_

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
 * $Id: DOMDocumentRange.hpp,v 1.1.1.1 2006/04/05 02:29:42 prem Exp $
*/

#include <xercesc/util/XercesDefs.hpp>

XERCES_CPP_NAMESPACE_BEGIN


class DOMRange;


/**
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>Document Object Model (DOM) Level 2 Traversal and Range Specification</a>.
 * @since DOM Level 2
 */
class CDOM_EXPORT DOMDocumentRange {

protected:
    // -----------------------------------------------------------------------
    //  Hidden constructors
    // -----------------------------------------------------------------------
    /** @name Hidden constructors */
    //@{    
    DOMDocumentRange() {};
    //@}

private:
    // -----------------------------------------------------------------------
    // Unimplemented constructors and operators
    // -----------------------------------------------------------------------
    /** @name Unimplemented constructors and operators */
    //@{
    DOMDocumentRange(const DOMDocumentRange &);
    DOMDocumentRange & operator = (const DOMDocumentRange &);
    //@}

public:
    // -----------------------------------------------------------------------
    //  All constructors are hidden, just the destructor is available
    // -----------------------------------------------------------------------
    /** @name Destructor */
    //@{
    /**
     * Destructor
     *
     */
    virtual ~DOMDocumentRange() {};
    //@}

    // -----------------------------------------------------------------------
    //  Virtual DOMDocumentRange interface
    // -----------------------------------------------------------------------
    /** @name Functions introduced in DOM Level 2 */
    //@{
    /**
	  * To create the range  consisting of boundary-points and offset of the
     * selected contents
     *
     * @return The initial state of the Range such that both the boundary-points
     * are positioned at the beginning of the corresponding DOMDOcument, before
     * any content. The range returned can only be used to select content
     * associated with this document, or with documentFragments and Attrs for
     * which this document is the ownerdocument
     * @since DOM Level 2
	  */
    virtual DOMRange    *createRange() = 0;

    //@}
};


XERCES_CPP_NAMESPACE_END

#endif
