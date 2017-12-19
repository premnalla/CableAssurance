//
// BufferClass implementation.
//

#include "config.h"
#include "BufferClass.H"

BufferClass::BufferClass(char *buffer, int bufferSize,
			 char *message, int messageLength) {

  if ( (message < buffer) || 
       ((message + messageLength) > (buffer + bufferSize)) ) {
    throw snmpStringException("BufferClass: incorrect constructor values");
  }

  this->buff       = buffer;
  this->buffLength = bufferSize;
  this->msg        = message;
  this->msgLength  = messageLength;
}

BufferClass::BufferClass(const BufferClass &fromBuff) {
  this->buff       = (char *)calloc(sizeof(char), fromBuff.msgLength);
  this->buffLength = fromBuff.msgLength;
  this->msg        = this->buff;
  this->msgLength  = this->buffLength;

  memcpy(this->msg, fromBuff.msg, this->msgLength);
}

BufferClass::~BufferClass() {
  free (this->buff);
}

BufferClass
*BufferClass::clone() const {
  return( new BufferClass(*this));
}

std::string
BufferClass::toString() {
  return (std::string(this->msg, this->msgLength));
}

BufferClass::operator char*() const {
  return(this->msg);
}

BufferClass::operator u_char*() const {
  return((u_char *)this->msg);
}

int
BufferClass::message_length() {
  return(this->msgLength);
}

int
BufferClass::buffer_length() {
  return(this->buffLength);
}
