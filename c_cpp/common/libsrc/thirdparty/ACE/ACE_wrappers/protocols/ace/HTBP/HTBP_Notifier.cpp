/* -*- C++ -*- */

//=============================================================================
/**
 *  @file    HTBP_Notifier.cpp
 *
 *  HTBP_Notifier.cpp,v 1.3 2005/11/27 12:11:35 ossama Exp
 *
 *  @author Phil Mesnier, Priyanka Gontla
 */
//=============================================================================
#include "HTBP_Notifier.h"
#include "HTBP_Channel.h"
#include "HTBP_Session.h"
#include "ace/Reactor.h"

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

ACE::HTBP::Notifier::Notifier (ACE::HTBP::Channel *s)
  : channel_(s)
{
}

int
ACE::HTBP::Notifier::handle_input(ACE_HANDLE )
{
  switch (this->channel_->state())
    {
    case ACE::HTBP::Channel::Detached:
      this->channel_->pre_recv();
      break;
    case ACE::HTBP::Channel::Wait_For_Ack:
      this->channel_->recv_ack();
      break;
    default:
      this->channel_->load_buffer();
    }

  if (this->channel_->state() == ACE::HTBP::Channel::Closed)
    {
      this->unregister();
      return 0;
    }

  if (this->channel_->session_)
    {
      if (this->channel_ == this->channel_->session_->inbound())
        {
          ACE_Event_Handler *h = this->channel_->session_->handler();
          if (h && this->reactor())
            this->reactor()->notify(h,
                                    ACE_Event_Handler::READ_MASK);
          else
            ACE_DEBUG ((LM_DEBUG,"Notifier cannot notify, session has no handler (%x), or reactor (%x)\n",h,this->reactor()));
        }
      else
        this->channel_->flush_buffer();
    }
  else
    ACE_DEBUG ((LM_DEBUG,"Notifier has no session to notify!\n"));
  return 0;
}

int
ACE::HTBP::Notifier::handle_output (ACE_HANDLE )
{
  return -1;

}

void
ACE::HTBP::Notifier::unregister (void)
{
  if (this->reactor())
    this->reactor()->remove_handler(this,
                                    ACE_Event_Handler::READ_MASK |
                                    ACE_Event_Handler::DONT_CALL);
}

ACE_HANDLE
ACE::HTBP::Notifier::get_handle(void) const
{
  return this->channel_->ace_stream().get_handle();
}

ACE_END_VERSIONED_NAMESPACE_DECL
