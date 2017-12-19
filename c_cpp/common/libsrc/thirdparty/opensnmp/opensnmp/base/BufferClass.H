#ifndef BUFFERCLASS_H
#define BUFFERCLASS_H

#include <sys/types.h>

#include <string>
#include "snmpStringException.H"

// BufferClass Class:
//   this class holds a messag in a buffer, and provides for deleting it 
//   and accessing it.
//   Instantiation requires the pointer to the beginning of the entire
//   buffer (used for deletion), size or the entire buffer, and
//   begginning of the useful part (i.e. the message) of the buffer, 
//   and the length of the useful part (i.e. the message's length)
//
//   used particularly in cases were data is placed in allocatied memory 
//   in reverse order and doesn't fill the entire block of memory.
//
//   Exceptions: BufferClass will raise a 'snmpStringException' if the 
//               constructor values are incorrect.


class BufferClass {

private:
  char  *buff, *msg;
  int   buffLength, msgLength;
  
public:
  
  BufferClass(char *buffer, int bufferSize,
	      char *message, int messageLength);
  BufferClass(const BufferClass &fromBuff);

  virtual ~BufferClass();
  
  virtual BufferClass *clone() const;
  
  virtual string toString();  

  operator char*() const;
  operator u_char*() const;

  int message_length();
  int buffer_length(); // notice, this is not the same as message length!

};  /* class BufferClass */


#endif /* BUFFERCLASS_H */
