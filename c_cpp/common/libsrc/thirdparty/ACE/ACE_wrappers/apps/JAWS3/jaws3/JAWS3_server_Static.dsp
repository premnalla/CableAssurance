# Microsoft Developer Studio Project File - Name="JAWS3_server_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=JAWS3_server_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="JAWS3_server_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "JAWS3_server_Static - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "JAWS3_server_Static - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "JAWS3_server_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\JAWS3_server_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Static_Debug\JAWS3_server_Static/" /I "..\..\.." /I ".." /D _DEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.." /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEsd.lib JAWS3sd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:"Static_Debug\main.pdb" /debug /machine:I386 /out:"Static_Debug\main.exe"

!ELSEIF  "$(CFG)" == "JAWS3_server_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\JAWS3_server_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /I ".." /D NDEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.." /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEs.lib JAWS3s.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Static_Release\main.exe"

!ENDIF

# Begin Target

# Name "JAWS3_server_Static - Win32 Debug"
# Name "JAWS3_server_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="main.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Asynch_IO.h"
# End Source File
# Begin Source File

SOURCE="Asynch_IO_Helpers.h"
# End Source File
# Begin Source File

SOURCE="Cached_Allocator_T.h"
# End Source File
# Begin Source File

SOURCE="Concurrency.h"
# End Source File
# Begin Source File

SOURCE="Concurrency_T.h"
# End Source File
# Begin Source File

SOURCE="Config_File.h"
# End Source File
# Begin Source File

SOURCE="Datagram.h"
# End Source File
# Begin Source File

SOURCE="Event_Completer.h"
# End Source File
# Begin Source File

SOURCE="Event_Dispatcher.h"
# End Source File
# Begin Source File

SOURCE="Event_Result.h"
# End Source File
# Begin Source File

SOURCE="Export.h"
# End Source File
# Begin Source File

SOURCE="FILE.h"
# End Source File
# Begin Source File

SOURCE="IO.h"
# End Source File
# Begin Source File

SOURCE="Options.h"
# End Source File
# Begin Source File

SOURCE="Protocol_Handler.h"
# End Source File
# Begin Source File

SOURCE="Reactive_IO.h"
# End Source File
# Begin Source File

SOURCE="Reactive_IO_Helpers.h"
# End Source File
# Begin Source File

SOURCE="Signal_Task.h"
# End Source File
# Begin Source File

SOURCE="Symbol_Table.h"
# End Source File
# Begin Source File

SOURCE="Synch_IO.h"
# End Source File
# Begin Source File

SOURCE="Task_Timer.h"
# End Source File
# Begin Source File

SOURCE="THYBRID_Concurrency.h"
# End Source File
# Begin Source File

SOURCE="Timer.h"
# End Source File
# Begin Source File

SOURCE="Timer_Helpers.h"
# End Source File
# Begin Source File

SOURCE="TPOOL_Concurrency.h"
# End Source File
# Begin Source File

SOURCE="TPR_Concurrency.h"
# End Source File
# End Group
# Begin Group "Template Files"

# PROP Default_Filter ""
# Begin Source File

SOURCE="Cached_Allocator_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Concurrency_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# End Group
# End Target
# End Project
