// Handle_L_Dgram.cpp,v 4.3 2004/10/27 21:06:58 shuston Exp

#include "Handle_L_Dgram.h"

ACE_RCSID(server, Handle_L_Dgram, "Handle_L_Dgram.cpp,v 4.3 2004/10/27 21:06:58 shuston Exp")

#if !defined (ACE_LACKS_UNIX_DOMAIN_SOCKETS)

const ACE_TCHAR *Handle_L_Dgram::DEFAULT_RENDEZVOUS =
  ACE_TEXT ("/tmp/foo_dgram");

#if !defined (__ACE_INLINE__)
#include "Handle_L_Dgram.i"
#endif /* __ACE_INLINE__ */

Handle_L_Dgram local_dgram;
ACE_Service_Object_Type ld (&local_dgram, ACE_TEXT ("Local_Dgram"));
#endif /* ACE_LACKS_UNIX_DOMAIN_SOCKETS */
