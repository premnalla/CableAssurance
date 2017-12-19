// file      : Utility/ExH/System/DescriptiveException.ipp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

#include "Utility/ExH/Converter.hpp"

namespace Utility
{
  namespace ExH
  {
    namespace System
    {
      template <typename T>
      inline DescriptiveException::
      DescriptiveException (T const& description) throw ()
      {
        init (converter<T> (description).c_str ());
      }
    }
  }
}
//DescriptiveException.tpp,v 1.1 2005/05/24 04:33:12 turkaye Exp
