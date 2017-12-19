// file      : Protocol.h
// author    : Boris Kolpackov <boris@kolpackov.net>
// cvs-id    : Protocol.h,v 1.2 2005/06/17 15:36:33 boris Exp

#ifndef PROTOCOL_H
#define PROTOCOL_H

unsigned short const payload_size = 1234;
unsigned long const message_count = 1000;

struct Message
{
  unsigned long sn;

  unsigned short payload[payload_size];
};

#endif  // PROTOCOL_H
