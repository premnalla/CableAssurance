// adaptive_recursive_lock_test.cpp,v 1.4 2004/05/07 21:33:02 shuston Exp

#define  ACE_BUILD_SVC_DLL
#include "ace/Log_Msg.h"
#include "Adaptive_Lock_Performance_Test_Base.h"
#include "ace/Lock_Adapter_T.h"
#include "ace/Recursive_Thread_Mutex.h"

ACE_RCSID(Synch_Benchmarks, adaptive_recursive_lock_test, "adaptive_recursive_lock_test.cpp,v 1.4 2004/05/07 21:33:02 shuston Exp")

#if defined (ACE_HAS_THREADS)

class ACE_Svc_Export Adaptive_Recursive_Lock_Test : public Adaptive_Lock_Performance_Test_Base
{
public:
  virtual int init (int, ACE_TCHAR *[]);
};

int
Adaptive_Recursive_Lock_Test::init (int, ACE_TCHAR *[])
{
  ACE_Lock *lock;
  ACE_NEW_RETURN (lock,
                  ACE_Lock_Adapter<ACE_Recursive_Thread_Mutex> (),
                  -1);

  return this->set_lock (lock);
}

ACE_SVC_FACTORY_DECLARE (Adaptive_Recursive_Lock_Test)
ACE_SVC_FACTORY_DEFINE  (Adaptive_Recursive_Lock_Test)

#if defined (ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION)
template class ACE_Lock_Adapter<ACE_Recursive_Thread_Mutex>;
#elif defined (ACE_HAS_TEMPLATE_INSTANTIATION_PRAGMA)
#pragma instantiate ACE_Lock_Adapter<ACE_Recursive_Thread_Mutex>
#endif /* ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION */
#endif /* ACE_HAS_THREADS */
