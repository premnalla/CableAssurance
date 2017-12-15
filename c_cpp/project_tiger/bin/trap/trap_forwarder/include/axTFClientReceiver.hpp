
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFClientReceiver_hpp_
#define _axTFClientReceiver_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAceWorker.hpp"
#include "axSocketFailInterface.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define MAX_CLIENTS                                                 20

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axTFClientReceiver.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class axTFClientReceiver : public axAceWorker, 
                           public axSocketFailInterface
{
public:

  /// default constructor
  axTFClientReceiver();

  /// destructor
  virtual ~axTFClientReceiver();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */

   /**
    *
    */
  virtual AX_INT32 run(void);

   /**
    * from interface axSocketFailInterface
    */
  virtual void handleReadFailure(AX_INT32);

   /**
    * from interface axSocketFailInterface
    */
  virtual void handleWriteFailure(AX_INT32);

protected:


private:

  axTFClientReceiver(const axTFClientReceiver &);

  void addClient(AX_INT32, struct sockaddr *, size_t);
  void removeClient(AX_INT32);
  void setFdSet(fd_set *);

  bool m_exit;
};

#endif // _axTFClientReceiver_hpp_
