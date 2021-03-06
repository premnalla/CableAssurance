/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
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
 * $Id: XMLDOMDocumentFragment.h,v 1.1.1.1 2006/04/05 02:29:45 prem Exp $
 */

#ifndef ___xmldomdocumentfragment_h___
#define ___xmldomdocumentfragment_h___

#include <xercesc/dom/DOMDocumentFragment.hpp>
#include "IXMLDOMNodeImpl.h"

XERCES_CPP_NAMESPACE_USE

class ATL_NO_VTABLE CXMLDOMDocumentFragment :
	public CComObjectRootEx<CComSingleThreadModel>,
	public IXMLDOMNodeImpl<IXMLDOMDocumentFragment, &IID_IXMLDOMDocumentFragment>
{
public:
	CXMLDOMDocumentFragment()
	{}

	void	FinalRelease()
	{
		ReleaseOwnerDoc();
	}
	
	virtual DOMNode* get_DOMNode()			 { return documentFragment;}
	virtual DOMNodeType get_DOMNodeType() const  { return NODE_DOCUMENT_FRAGMENT; }

DECLARE_NOT_AGGREGATABLE(CXMLDOMDocumentFragment)

DECLARE_PROTECT_FINAL_CONSTRUCT()

BEGIN_COM_MAP(CXMLDOMDocumentFragment)
	COM_INTERFACE_ENTRY(IXMLDOMDocumentFragment)
	COM_INTERFACE_ENTRY(IXMLDOMNode)
	COM_INTERFACE_ENTRY(IIBMXMLDOMNodeIdentity)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
END_COM_MAP()

	DOMDocumentFragment* documentFragment;
};

typedef CComObject<CXMLDOMDocumentFragment> CXMLDOMDocumentFragmentObj;

#endif // ___xmldomdocumentfragment_h___