/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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

package org.apache.xerces.impl.dtd.models;

/**
 * A content model node.
 * 
 * @xerces.internal
 *
 * @version $Id: CMNode.java,v 1.1.1.1 2006/04/05 02:30:19 prem Exp $
 */
public abstract class CMNode
{
    // -------------------------------------------------------------------
    //  Constructors
    // -------------------------------------------------------------------
    public CMNode(int type) 
    {
        fType = type;
    }


    // -------------------------------------------------------------------
    //  Package, abstract methods
    // -------------------------------------------------------------------
    // made this public so it could be implemented and used outside this package -neilg.
    public abstract boolean isNullable() ;


    // -------------------------------------------------------------------
    //  Package final methods
    // -------------------------------------------------------------------
    public final int type()
    {
        return fType;
    }

    // made this public so it could be implemented and used outside this package -neilg.
    public final CMStateSet firstPos() 
    {
        if (fFirstPos == null)
        {
            fFirstPos = new CMStateSet(fMaxStates);
            calcFirstPos(fFirstPos);
        }
        return fFirstPos;
    }

    // made this public so it could be implemented and used outside this package -neilg.
    public final CMStateSet lastPos() 
    {
        if (fLastPos == null)
        {
            fLastPos = new CMStateSet(fMaxStates);
            calcLastPos(fLastPos);
        }
        return fLastPos;
    }

    final void setFollowPos(CMStateSet setToAdopt)
    {
        fFollowPos = setToAdopt;
    }

    public final void setMaxStates(int maxStates)
    {
        fMaxStates = maxStates;
    }


    // -------------------------------------------------------------------
    //  Protected, abstract methods
    // -------------------------------------------------------------------
    protected abstract void calcFirstPos(CMStateSet toSet) ;

    protected abstract void calcLastPos(CMStateSet toSet) ;


    // -------------------------------------------------------------------
    //  Private data members
    //
    //  fType
    //      The type of node. This indicates whether its a leaf or an
    //      operation. Though we also do derived classes for these types,
    //      it is too expensive to use runtime typing to find this out.
    //      This is one of the ContentSpecNode.NODE_XXX types.
    //
    //  fFirstPos
    //      The set of NFA states that represent the entry states of this
    //      node in the DFA.
    //
    //  fFollowPos
    //      The set of NFA states that can be gotten to from from this
    //      node in the DFA.
    //
    //  fLastPos
    //      The set of NFA states that represent the final states of this
    //      node in the DFA.
    //
    //  fMaxStates
    //      The maximum number of states that the NFA has, which means the
    //      max number of NFA states that have to be traced in the state
    //      sets during the building of the DFA. Its unfortunate that it
    //      has to be stored redundantly, but we need to fault in the
    //      state set members and they have to be sized to this size. We
    //      init to to -1 so it will cause an error if its used without
    //      being initialized.
    // -------------------------------------------------------------------
    private int         fType;
    private CMStateSet  fFirstPos   = null;
    private CMStateSet  fFollowPos  = null;
    private CMStateSet  fLastPos    = null;
    private int         fMaxStates  = -1;
};

