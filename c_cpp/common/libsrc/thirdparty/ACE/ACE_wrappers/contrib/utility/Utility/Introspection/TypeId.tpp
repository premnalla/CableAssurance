// file      : Utility/Introspection/TypeId.tpp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

namespace Utility
{
  namespace Introspection
  {
    template<typename T>
    inline TypeId::
    TypeId (T const& t)
        : tid_ (&typeid (t))
    {
    }
  }
}
//TypeId.tpp,v 1.1 2005/05/24 04:33:13 turkaye Exp
