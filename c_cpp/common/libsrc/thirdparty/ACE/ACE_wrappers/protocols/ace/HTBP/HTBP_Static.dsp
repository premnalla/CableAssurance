# Microsoft Developer Studio Project File - Name="HTBP_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=HTBP_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="HTBP_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "HTBP_Static - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE "HTBP_Static - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "HTBP_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\HTBP_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"..\..\..\lib\ACE_HTBPsd.pdb" /I "..\..\.." /D _DEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACE_HTBP.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\ACE_HTBPsd.lib"

!ELSEIF  "$(CFG)" == "HTBP_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\HTBP_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACE_HTBP.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\ACE_HTBPs.lib"

!ENDIF

# Begin Target

# Name "HTBP_Static - Win32 Debug"
# Name "HTBP_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="HTBP_Addr.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Channel.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Environment.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Filter.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Filter_Factory.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_ID_Requestor.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Inside_Squid_Filter.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Notifier.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Outside_Squid_Filter.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Session.cpp"
# End Source File
# Begin Source File

SOURCE="HTBP_Stream.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="HTBP_Addr.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Channel.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Environment.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Export.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Filter.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Filter_Factory.h"
# End Source File
# Begin Source File

SOURCE="HTBP_ID_Requestor.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Inside_Squid_Filter.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Macros.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Notifier.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Outside_Squid_Filter.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Session.h"
# End Source File
# Begin Source File

SOURCE="HTBP_Stream.h"
# End Source File
# End Group
# Begin Group "Inline Files"

# PROP Default_Filter "i;inl"
# Begin Source File

SOURCE="HTBP_Channel.inl"
# End Source File
# Begin Source File

SOURCE="HTBP_Filter.inl"
# End Source File
# Begin Source File

SOURCE="HTBP_Inside_Squid_Filter.inl"
# End Source File
# Begin Source File

SOURCE="HTBP_Outside_Squid_Filter.inl"
# End Source File
# Begin Source File

SOURCE="HTBP_Session.inl"
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

SOURCE="ACE_HTBP.pc.in"
# PROP Exclude_From_Build 1
# End Source File
# End Group
# End Target
# End Project
