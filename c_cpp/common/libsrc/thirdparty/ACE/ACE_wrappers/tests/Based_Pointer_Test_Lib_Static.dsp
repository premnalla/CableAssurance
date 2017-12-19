# Microsoft Developer Studio Project File - Name="Based_Pointer_Test_Lib_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=Based_Pointer_Test_Lib_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="Based_Pointer_Test_Lib_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "Based_Pointer_Test_Lib_Static - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE "Based_Pointer_Test_Lib_Static - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Based_Pointer_Test_Lib_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\Based_Pointer_Test_Lib_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"..\lib\Based_Pointer_Test_Libsd.pdb" /I ".." /D _DEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\lib\Based_Pointer_Test_Lib.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\lib\Based_Pointer_Test_Libsd.lib"

!ELSEIF  "$(CFG)" == "Based_Pointer_Test_Lib_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\Based_Pointer_Test_Lib_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I ".." /D NDEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\lib\Based_Pointer_Test_Lib.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\lib\Based_Pointer_Test_Libs.lib"

!ENDIF

# Begin Target

# Name "Based_Pointer_Test_Lib_Static - Win32 Debug"
# Name "Based_Pointer_Test_Lib_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Based_Pointer_Test_Lib.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="ACE_Init_Test.h"
# End Source File
# Begin Source File

SOURCE="ACE_Init_Test_Resource.h"
# End Source File
# Begin Source File

SOURCE="ACE_Init_Test_StdAfx.h"
# End Source File
# Begin Source File

SOURCE="ACE_Init_TestDlg.h"
# End Source File
# Begin Source File

SOURCE="Bound_Ptr_Test.h"
# End Source File
# Begin Source File

SOURCE="Cache_Map_Manager_Test.h"
# End Source File
# Begin Source File

SOURCE="Cached_Accept_Conn_Test.h"
# End Source File
# Begin Source File

SOURCE="Cached_Conn_Test.h"
# End Source File
# Begin Source File

SOURCE="CE_fostream.h"
# End Source File
# Begin Source File

SOURCE="Collection_Test.h"
# End Source File
# Begin Source File

SOURCE="Config_Test.h"
# End Source File
# Begin Source File

SOURCE="Conn_Test.h"
# End Source File
# Begin Source File

SOURCE="DLL_Test.h"
# End Source File
# Begin Source File

SOURCE="DLL_Test_Impl.h"
# End Source File
# Begin Source File

SOURCE="DLL_Test_Parent.h"
# End Source File
# Begin Source File

SOURCE="DLL_Test_Parent_Export.h"
# End Source File
# Begin Source File

SOURCE="Framework_Component_DLL.h"
# End Source File
# Begin Source File

SOURCE="Framework_Component_DLL_Export.h"
# End Source File
# Begin Source File

SOURCE="Framework_Component_Test.h"
# End Source File
# Begin Source File

SOURCE="Malloc_Test.h"
# End Source File
# Begin Source File

SOURCE="Map_Test.h"
# End Source File
# Begin Source File

SOURCE="Max_Default_Port_Test.h"
# End Source File
# Begin Source File

SOURCE="MEM_Stream_Test.h"
# End Source File
# Begin Source File

SOURCE="Message_Queue_Test_Ex.h"
# End Source File
# Begin Source File

SOURCE="MT_Reactor_Timer_Test.h"
# End Source File
# Begin Source File

SOURCE="Network_Adapters_Test.h"
# End Source File
# Begin Source File

SOURCE="NonBlocking_Conn_Test.h"
# End Source File
# Begin Source File

SOURCE="Priority_Reactor_Test.h"
# End Source File
# Begin Source File

SOURCE="Proactor_Test.h"
# End Source File
# Begin Source File

SOURCE="Process_Strategy_Test.h"
# End Source File
# Begin Source File

SOURCE="QtReactor_Test.h"
# End Source File
# Begin Source File

SOURCE="RB_Tree_Test.h"
# End Source File
# Begin Source File

SOURCE="Reactor_Performance_Test.h"
# End Source File
# Begin Source File

SOURCE="Refcounted_Auto_Ptr_Test.h"
# End Source File
# Begin Source File

SOURCE="Service_Config_DLL.h"
# End Source File
# Begin Source File

SOURCE="Service_Config_DLL_Export.h"
# End Source File
# Begin Source File

SOURCE="Task_Ex_Test.h"
# End Source File
# Begin Source File

SOURCE="test_config.h"
# End Source File
# Begin Source File

SOURCE="Test_Output_Export.h"
# End Source File
# Begin Source File

SOURCE="Thread_Pool_Reactor_Resume_Test.h"
# End Source File
# Begin Source File

SOURCE="Thread_Pool_Reactor_Test.h"
# End Source File
# Begin Source File

SOURCE="TP_Reactor_Test.h"
# End Source File
# Begin Source File

SOURCE="TSS_Test_Errno.h"
# End Source File
# Begin Source File

SOURCE="Upgradable_RW_Test.h"
# End Source File
# End Group
# Begin Group "Documentation"

# PROP Default_Filter ""
# Begin Source File

SOURCE="README"
# End Source File
# End Group
# End Target
# End Project
