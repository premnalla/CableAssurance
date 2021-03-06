// file      : Utility/ReferenceCounting/Interface.tpp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

namespace Utility
{
  namespace ReferenceCounting
  {
    template <typename Type>
    inline Type*
    add_ref (Type* ptr)
      throw (Interface::Exception, Interface::SystemException)
    {
      if (ptr != 0) ptr->add_ref ();
      return ptr;
    }
  }
}
//Interface.tpp,v 1.1 2005/05/24 04:33:13 turkaye Exp
