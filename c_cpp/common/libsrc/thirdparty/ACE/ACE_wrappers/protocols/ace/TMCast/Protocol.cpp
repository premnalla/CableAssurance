// file      : ACE_TMCast/Protocol.cpp
// author    : Boris Kolpackov <boris@dre.vanderbilt.edu>
// cvs-id    : Protocol.cpp,v 1.3 2005/01/03 14:05:41 schmidt Exp

#include "Protocol.hpp"

namespace ACE_TMCast
{
  namespace Protocol
  {
    /*
      namespace
      {
      char const* labels[] = {
      "NONE", "BEGIN", "COMMIT", "ABORT", "COMMITED", "ABORTED"};
      }

      std::string
      tslabel (Protocol::TransactionStatus s)
      {
      return labels[s];
      }

      std::ostream&
      operator << (std::ostream& o, Transaction const& t)
      {
      return o << "{" << t.id << "; " << tslabel (t.status) << "}";
      }
    */
  }
}
