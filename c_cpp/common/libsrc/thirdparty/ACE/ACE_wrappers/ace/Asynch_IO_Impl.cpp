// Asynch_IO_Impl.cpp,v 4.10 2005/10/28 16:14:51 ossama Exp

#include "ace/Asynch_IO_Impl.h"

#if (defined (ACE_WIN32) && !defined (ACE_HAS_WINCE)) || (defined (ACE_HAS_AIO_CALLS))
// This only works on Win32 platforms and on Unix platforms supporting
// aio calls.

#if !defined (__ACE_INLINE__)
#include "ace/Asynch_IO_Impl.inl"
#endif /* __ACE_INLINE__ */

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

ACE_Asynch_Result_Impl::~ACE_Asynch_Result_Impl (void)
{
}

ACE_Asynch_Operation_Impl::~ACE_Asynch_Operation_Impl (void)
{
}

ACE_Asynch_Read_Stream_Impl::~ACE_Asynch_Read_Stream_Impl (void)
{
}

ACE_Asynch_Read_Stream_Result_Impl::~ACE_Asynch_Read_Stream_Result_Impl (void)
{
}

ACE_Asynch_Write_Stream_Impl::~ACE_Asynch_Write_Stream_Impl (void)
{
}

ACE_Asynch_Write_Stream_Result_Impl::~ACE_Asynch_Write_Stream_Result_Impl (void)
{
}

ACE_Asynch_Read_File_Impl::~ACE_Asynch_Read_File_Impl (void)
{
}

ACE_Asynch_Write_File_Impl::~ACE_Asynch_Write_File_Impl (void)
{
}

ACE_Asynch_Read_File_Result_Impl::~ACE_Asynch_Read_File_Result_Impl (void)
{
}

ACE_Asynch_Write_File_Result_Impl::~ACE_Asynch_Write_File_Result_Impl (void)
{
}

ACE_Asynch_Accept_Result_Impl::~ACE_Asynch_Accept_Result_Impl (void)
{
}

ACE_Asynch_Connect_Result_Impl::~ACE_Asynch_Connect_Result_Impl (void)
{
}

ACE_Asynch_Accept_Impl::~ACE_Asynch_Accept_Impl (void)
{
}

ACE_Asynch_Connect_Impl::~ACE_Asynch_Connect_Impl (void)
{
}

ACE_Asynch_Transmit_File_Impl::~ACE_Asynch_Transmit_File_Impl (void)
{
}

ACE_Asynch_Transmit_File_Result_Impl::~ACE_Asynch_Transmit_File_Result_Impl (void)
{
}

ACE_Asynch_Read_Dgram_Impl::~ACE_Asynch_Read_Dgram_Impl (void)
{
}

ACE_Asynch_Read_Dgram_Impl::ACE_Asynch_Read_Dgram_Impl (void)
{
}

ACE_Asynch_Write_Dgram_Impl::~ACE_Asynch_Write_Dgram_Impl (void)
{
}

ACE_Asynch_Write_Dgram_Impl::ACE_Asynch_Write_Dgram_Impl (void)
{
}

//***********************************************

ACE_Asynch_Read_Dgram_Result_Impl::~ACE_Asynch_Read_Dgram_Result_Impl (void)
{
}

ACE_Asynch_Read_Dgram_Result_Impl::ACE_Asynch_Read_Dgram_Result_Impl (void)
{
}

//***********************************************

ACE_Asynch_Write_Dgram_Result_Impl::~ACE_Asynch_Write_Dgram_Result_Impl (void)
{
}

ACE_Asynch_Write_Dgram_Result_Impl::ACE_Asynch_Write_Dgram_Result_Impl (void)
{
}

ACE_END_VERSIONED_NAMESPACE_DECL

#endif /* ACE_WIN32 || ACE_HAS_WINCE */
