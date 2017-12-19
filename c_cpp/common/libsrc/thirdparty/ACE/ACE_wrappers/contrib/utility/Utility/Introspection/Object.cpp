// file      : Utility/Introspection/Object.cpp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

#include "Utility/Introspection/Object.hpp"

namespace Utility
{
  namespace Introspection
  {
    namespace
    {
      TypeInfo object_ (typeid (Object));
    }

    TypeInfo const& Object::
    static_type_info () throw ()
    {
      return object_;
    }
  }
}
//Object.cpp,v 1.1 2005/05/24 04:33:12 turkaye Exp
