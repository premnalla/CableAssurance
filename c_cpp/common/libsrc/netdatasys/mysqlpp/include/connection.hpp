#ifndef _connection_hpp_
#define _connection_hpp_

#include "connection.h"

namespace foo {

class connection : public mysqlpp::Connection
// class connection
{
public:
  connection();
  virtual ~connection();
protected:
private:
};

}

#endif /* _connection_hpp_ */
