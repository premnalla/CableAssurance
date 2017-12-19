# Microsoft Developer Studio Project File - Name="JAWS2" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=JAWS2 - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="JAWS2 - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "JAWS2 - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "JAWS2 - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "JAWS2 - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "..\..\..\lib"
# PROP Intermediate_Dir "Debug\JAWS2"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\JAWS2/" /I "..\..\.." /I ".." /D _DEBUG /D WIN32 /D _WINDOWS /D JAWS_BUILD_DLL /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.." /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\JAWS2.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:windows /pdb:"..\..\..\lib\JAWS2d.pdb" /implib:"..\..\..\lib\JAWS2d.lib" /dll /debug /machine:I386 /out:"..\..\..\lib\JAWS2d.dll"

!ELSEIF  "$(CFG)" == "JAWS2 - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "..\..\..\lib"
# PROP Intermediate_Dir "Release\JAWS2"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /I ".." /D NDEBUG /D WIN32 /D _WINDOWS /D JAWS_BUILD_DLL /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.." /i ".."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\JAWS2.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:windows /pdb:none /implib:"..\..\..\lib\JAWS2.lib" /dll  /machine:I386 /out:"..\..\..\lib\JAWS2.dll"

!ENDIF

# Begin Target

# Name "JAWS2 - Win32 Debug"
# Name "JAWS2 - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Assoc_Array.cpp"
# End Source File
# Begin Source File

SOURCE="Cache_Manager.cpp"
# End Source File
# Begin Source File

SOURCE="Cache_Object.cpp"
# End Source File
# Begin Source File

SOURCE="Concurrency.cpp"
# End Source File
# Begin Source File

SOURCE="Data_Block.cpp"
# End Source File
# Begin Source File

SOURCE="FILE.cpp"
# End Source File
# Begin Source File

SOURCE="Filecache.cpp"
# End Source File
# Begin Source File

SOURCE="Headers.cpp"
# End Source File
# Begin Source File

SOURCE="IO.cpp"
# End Source File
# Begin Source File

SOURCE="IO_Acceptor.cpp"
# End Source File
# Begin Source File

SOURCE="IO_Handler.cpp"
# End Source File
# Begin Source File

SOURCE="Parse_Headers.cpp"
# End Source File
# Begin Source File

SOURCE="Pipeline.cpp"
# End Source File
# Begin Source File

SOURCE="Pipeline_Tasks.cpp"
# End Source File
# Begin Source File

SOURCE="Policy.cpp"
# End Source File
# Begin Source File

SOURCE="Reaper.cpp"
# End Source File
# Begin Source File

SOURCE="Server.cpp"
# End Source File
# Begin Source File

SOURCE="Waiter.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Assoc_Array.h"
# End Source File
# Begin Source File

SOURCE="Cache_Hash_T.h"
# End Source File
# Begin Source File

SOURCE="Cache_Heap_T.h"
# End Source File
# Begin Source File

SOURCE="Cache_List_T.h"
# End Source File
# Begin Source File

SOURCE="Cache_Manager.h"
# End Source File
# Begin Source File

SOURCE="Cache_Manager_T.h"
# End Source File
# Begin Source File

SOURCE="Cache_Object.h"
# End Source File
# Begin Source File

SOURCE="Concurrency.h"
# End Source File
# Begin Source File

SOURCE="Data_Block.h"
# End Source File
# Begin Source File

SOURCE="Export.h"
# End Source File
# Begin Source File

SOURCE="FILE.h"
# End Source File
# Begin Source File

SOURCE="Filecache.h"
# End Source File
# Begin Source File

SOURCE="Hash_Bucket_T.h"
# End Source File
# Begin Source File

SOURCE="Headers.h"
# End Source File
# Begin Source File

SOURCE="IO.h"
# End Source File
# Begin Source File

SOURCE="IO_Acceptor.h"
# End Source File
# Begin Source File

SOURCE="IO_Handler.h"
# End Source File
# Begin Source File

SOURCE="JAWS.h"
# End Source File
# Begin Source File

SOURCE="Parse_Headers.h"
# End Source File
# Begin Source File

SOURCE="Pipeline.h"
# End Source File
# Begin Source File

SOURCE="Pipeline_Handler_T.h"
# End Source File
# Begin Source File

SOURCE="Pipeline_Tasks.h"
# End Source File
# Begin Source File

SOURCE="Policy.h"
# End Source File
# Begin Source File

SOURCE="Reaper.h"
# End Source File
# Begin Source File

SOURCE="Server.h"
# End Source File
# Begin Source File

SOURCE="Waiter.h"
# End Source File
# End Group
# Begin Group "Template Files"

# PROP Default_Filter ""
# Begin Source File

SOURCE="Cache_Hash_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Cache_Heap_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Cache_List_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Cache_Manager_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Hash_Bucket_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# Begin Source File

SOURCE="Pipeline_Handler_T.cpp"
# PROP Exclude_From_Build 1
# End Source File
# End Group
# End Target
# End Project
