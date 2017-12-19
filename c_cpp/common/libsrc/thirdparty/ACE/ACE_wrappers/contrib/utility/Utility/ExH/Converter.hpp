// file      : Utility/ExH/Converter.hpp
// author    : Boris Kolpackov <boris@kolpackov.net>
// copyright : Copyright (c) 2002-2003 Boris Kolpackov
// license   : http://kolpackov.net/license.html

#ifndef UTILITY_EX_H_CONVERTER_HPP
#define UTILITY_EX_H_CONVERTER_HPP

#include <string>

namespace Utility
{
  namespace ExH
  {
    template <typename T>
    std::string
    converter (T const& t);
  }
}

#include "Utility/ExH/Converter.tpp"

#endif  // UTILITY_EX_H_CONVERTER_HPP
//Converter.hpp,v 1.1 2005/05/24 04:33:12 turkaye Exp
