// file      : Utility/ExH/Logic/Exception.hpp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

#ifndef UTILITY_EX_H_LOGIC_EXCEPTION_HPP
#define UTILITY_EX_H_LOGIC_EXCEPTION_HPP

#include "Utility/ExH/System/Exception.hpp"

namespace Utility
{
  namespace ExH
  {
    namespace Logic
    {

      // Logic::Exception inherits from System::Exception for the
      // following reason. Semantically for some part of the
      // system particular instance of Logic::Exception may seem as
      // opaque System::Exception and the only way to handle it would
      // be to propagate it further. In other words Logic::Exception
      // can be seemlesly "converted" to System::Exception if there is
      // no part of the system interested in handling it.
      //

      class Exception : public virtual System::Exception
      {
      public:
        virtual
        ~Exception () throw ();
      };
    }
  }
}

#include "Utility/ExH/Logic/Exception.ipp"

#endif  // UTILITY_EX_H_LOGIC_EXCEPTION_HPP
//Exception.hpp,v 1.1 2005/05/24 04:33:12 turkaye Exp
