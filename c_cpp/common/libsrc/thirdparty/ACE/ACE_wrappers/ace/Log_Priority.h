// -*- C++ -*-

//=============================================================================
/**
 *  @file    Log_Priority.h
 *
 *  Log_Priority.h,v 4.19 2005/10/28 16:14:53 ossama Exp
 *
 *  @author Douglas C. Schmidt <schmidt@cs.wustl.edu>
 */
//=============================================================================

#ifndef ACE_LOG_PRIORITY_H
#define ACE_LOG_PRIORITY_H

#include /**/ "ace/pre.h"

#include "ace/config-lite.h"

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

/**
 * @enum ACE_Log_Priority
 *
 * @brief This data type indicates the relative priorities of the
 *    logging messages, from lowest to highest priority.
 *
 * These values are defined using powers of two so that it's
 * possible to form a mask to turn them on or off dynamically.
 * We only use 12 bits, however, so users are free to use the
 * remaining 19 bits to define their own priority masks.
 */
enum ACE_Log_Priority
{
  // = Note, this first argument *must* start at 1!

  /// Shutdown the logger (decimal 1).
  LM_SHUTDOWN = 0x01,

  /// Messages indicating function-calling sequence (decimal 2).
  LM_TRACE = 0x02,

  /// Messages that contain information normally of use only when
  /// debugging a program (decimal 4).
  LM_DEBUG = 0x04,

  /// Informational messages (decimal 8).
  LM_INFO = 0x08,

  /// Conditions that are not error conditions, but that may require
  /// special handling (decimal 16).
  LM_NOTICE = 0x010,

  /// Warning messages (decimal 32).
  LM_WARNING = 0x020,

  /// Initialize the logger (decimal 64).
  LM_STARTUP = 0x040,

  /// Error messages (decimal 128).
  LM_ERROR = 0x080,

  /// Critical conditions, such as hard device errors (decimal 256).
  LM_CRITICAL = 0x0100,

  /// A condition that should be corrected immediately, such as a
  /// corrupted system database (decimal 512).
  LM_ALERT = 0x0200,

  /// A panic condition.  This is normally broadcast to all users
  /// (decimal 1024).
  LM_EMERGENCY = 0x0400,

  /// By: Prem
  LM_SCHED_DEBUG   =    0x0800,
  LM_TIMER_DEBUG   =   0x01000,
  LM_INTDS_DEBUG   =   0x02000,
  LM_DB_DEBUG      =   0x04000,
  LM_ALARM_DEBUG   =   0x08000,
  LM_SNMP_DEBUG    =  0x010000,
  LM_PING_DEBUG    =  0x020000,
  LM_MISC_DEBUG    =  0x040000,
  LM_TIMING_DEBUG  =  0x080000,
  LM_POLL_DEBUG    = 0x0100000,

  /// The maximum logging priority.
  LM_MAX = LM_POLL_DEBUG,

  /// Do not use!!  This enum value ensures that the underlying
  /// integral type for this enum is at least 32 bits.
  LM_ENSURE_32_BITS = 0x7FFFFFFF
};

ACE_END_VERSIONED_NAMESPACE_DECL

#include /**/ "ace/post.h"
#endif /* ACE_LOG_PRIORITY_H */
