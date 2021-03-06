// HTBP_Filter_Factory.cpp,v 1.2 2005/11/27 12:11:35 ossama Exp

#include "HTBP_Filter_Factory.h"
#include "HTBP_Inside_Squid_Filter.h"
#include "HTBP_Outside_Squid_Filter.h"

#include "HTBP_Stream.h"
#include "ace/Service_Object.h"
#include "ace/Dynamic_Service.h"
#include "ace/Log_Msg.h"

ACE_RCSID (ACE_HTBP_Filter_Factory, HTBP, "HTBP_Filter_Factory.cpp,v 1.2 2005/11/27 12:11:35 ossama Exp")

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

ACE::HTBP::Filter_Factory::Filter_Factory ()
{
}

ACE::HTBP::Filter_Factory::~Filter_Factory ()
{
}

ACE::HTBP::Filter *
ACE::HTBP::Filter_Factory::get_filter (int inside)
{
  ACE::HTBP::Filter *filter = 0;
  if (inside)
    ACE_NEW_RETURN (filter,
                    ACE::HTBP::Inside_Squid_Filter (),0);
  else
    ACE_NEW_RETURN (filter,
                    ACE::HTBP::Outside_Squid_Filter (),0);
  return filter;
}

int
ACE::HTBP::Filter_Factory::init (int /* argc */,
                         ACE_TCHAR * /* argv */ [])
{
  return 0;
}

ACE_END_VERSIONED_NAMESPACE_DECL

ACE_STATIC_SVC_DEFINE (ACE_HTBP_Filter_Factory,
                       ACE_TEXT ("Filter_Factory"),
                       ACE_SVC_OBJ_T,
                       &ACE_SVC_NAME (ACE_HTBP_Filter_Factory),
                       ACE_Service_Type::DELETE_THIS |
                       ACE_Service_Type::DELETE_OBJ,
                       0)

ACE_FACTORY_NAMESPACE_DEFINE (HTBP, ACE_HTBP_Filter_Factory,
                              ACE::HTBP::Filter_Factory)

#if defined (ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION)
template class ACE_Dynamic_Service<ACE::HTBP::Filter_Factory>;
#elif defined (ACE_HAS_TEMPLATE_INSTANTIATION_PRAGMA)
#pragma instantiate ACE_Dynamic_Service<ACE::HTBP::Filter_Factory>
#endif

