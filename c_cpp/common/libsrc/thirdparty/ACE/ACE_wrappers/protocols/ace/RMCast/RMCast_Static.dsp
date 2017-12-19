# Microsoft Developer Studio Project File - Name="RMCast_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=RMCast_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="RMCast_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "RMCast_Static - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE "RMCast_Static - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "RMCast_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\RMCast_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"..\..\..\lib\ACE_RMCastsd.pdb" /I "..\..\.." /D _DEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACE_RMCast.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\ACE_RMCastsd.lib"

!ELSEIF  "$(CFG)" == "RMCast_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\RMCast_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACE_RMCast.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\ACE_RMCasts.lib"

!ENDIF

# Begin Target

# Name "RMCast_Static - Win32 Debug"
# Name "RMCast_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Acknowledge.cpp"
# End Source File
# Begin Source File

SOURCE="Flow.cpp"
# End Source File
# Begin Source File

SOURCE="Fragment.cpp"
# End Source File
# Begin Source File

SOURCE="Link.cpp"
# End Source File
# Begin Source File

SOURCE="Protocol.cpp"
# End Source File
# Begin Source File

SOURCE="Reassemble.cpp"
# End Source File
# Begin Source File

SOURCE="Retransmit.cpp"
# End Source File
# Begin Source File

SOURCE="Simulator.cpp"
# End Source File
# Begin Source File

SOURCE="Socket.cpp"
# End Source File
# Begin Source File

SOURCE="Stack.cpp"
# End Source File
# Begin Source File

SOURCE="Template_Instantiations.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Acknowledge.h"
# End Source File
# Begin Source File

SOURCE="Bits.h"
# End Source File
# Begin Source File

SOURCE="Flow.h"
# End Source File
# Begin Source File

SOURCE="Fragment.h"
# End Source File
# Begin Source File

SOURCE="Link.h"
# End Source File
# Begin Source File

SOURCE="Parameters.h"
# End Source File
# Begin Source File

SOURCE="Protocol.h"
# End Source File
# Begin Source File

SOURCE="Reassemble.h"
# End Source File
# Begin Source File

SOURCE="Retransmit.h"
# End Source File
# Begin Source File

SOURCE="RMCast_Export.h"
# End Source File
# Begin Source File

SOURCE="Simulator.h"
# End Source File
# Begin Source File

SOURCE="Socket.h"
# End Source File
# Begin Source File

SOURCE="Stack.h"
# End Source File
# End Group
# Begin Group "Documentation"

# PROP Default_Filter ""
# Begin Source File

SOURCE="README"
# End Source File
# End Group
# Begin Group "Pkgconfig Files"

# PROP Default_Filter "in"
# Begin Source File

SOURCE="ACE_RMCast.pc.in"
# PROP Exclude_From_Build 1
# End Source File
# End Group
# End Target
# End Project
