# Microsoft Developer Studio Project File - Name="Gateway_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=Gateway_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="Gateway_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "Gateway_Static - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE "Gateway_Static - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Gateway_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\Gateway_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"..\..\..\lib\Gatewaysd.pdb" /I "..\..\.." /D _DEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\Gateway.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\Gatewaysd.lib"

!ELSEIF  "$(CFG)" == "Gateway_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\Gateway_Static"
# PROP Target_Dir ""
LINK32=link.exe -lib
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _WINDOWS /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\Gateway.bsc"
LIB32=link.exe -lib
# ADD LIB32 /nologo /out:"..\..\..\lib\Gateways.lib"

!ENDIF

# Begin Target

# Name "Gateway_Static - Win32 Debug"
# Name "Gateway_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Concrete_Connection_Handlers.cpp"
# End Source File
# Begin Source File

SOURCE="Config_Files.cpp"
# End Source File
# Begin Source File

SOURCE="Connection_Handler.cpp"
# End Source File
# Begin Source File

SOURCE="Connection_Handler_Acceptor.cpp"
# End Source File
# Begin Source File

SOURCE="Connection_Handler_Connector.cpp"
# End Source File
# Begin Source File

SOURCE="Event_Channel.cpp"
# End Source File
# Begin Source File

SOURCE="Event_Forwarding_Discriminator.cpp"
# End Source File
# Begin Source File

SOURCE="File_Parser.cpp"
# End Source File
# Begin Source File

SOURCE="Gateway.cpp"
# End Source File
# Begin Source File

SOURCE="Options.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Concrete_Connection_Handlers.h"
# End Source File
# Begin Source File

SOURCE="Config_Files.h"
# End Source File
# Begin Source File

SOURCE="Connection_Handler.h"
# End Source File
# Begin Source File

SOURCE="Connection_Handler_Acceptor.h"
# End Source File
# Begin Source File

SOURCE="Connection_Handler_Connector.h"
# End Source File
# Begin Source File

SOURCE="Event_Channel.h"
# End Source File
# Begin Source File

SOURCE="Event_Forwarding_Discriminator.h"
# End Source File
# Begin Source File

SOURCE="File_Parser.h"
# End Source File
# Begin Source File

SOURCE="Gateway.h"
# End Source File
# Begin Source File

SOURCE="Options.h"
# End Source File
# End Group
# End Target
# End Project
