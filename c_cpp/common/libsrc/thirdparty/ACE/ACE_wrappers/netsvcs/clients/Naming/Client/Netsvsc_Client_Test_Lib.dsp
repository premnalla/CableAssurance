# Microsoft Developer Studio Project File - Name="Netsvsc_Client_Test_Lib" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=Netsvsc_Client_Test_Lib - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="Netsvsc_Client_Test_Lib - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "Netsvsc_Client_Test_Lib - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "Netsvsc_Client_Test_Lib - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Netsvsc_Client_Test_Lib - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "..\..\..\..\lib"
# PROP Intermediate_Dir "Debug\Netsvsc_Client_Test_Lib"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\Netsvsc_Client_Test_Lib/" /I "..\..\..\.." /D _DEBUG /D WIN32 /D _WINDOWS /D ACE_CLIENT_TEST_BUILD_DLL /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\..\lib\Client_Test.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib /libpath:"." /libpath:"..\..\..\..\lib" /nologo /subsystem:windows /pdb:"..\..\..\..\lib\Client_Testd.pdb" /implib:"..\..\..\..\lib\Client_Testd.lib" /dll /debug /machine:I386 /out:"..\..\..\..\lib\Client_Testd.dll"

!ELSEIF  "$(CFG)" == "Netsvsc_Client_Test_Lib - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "..\..\..\..\lib"
# PROP Intermediate_Dir "Release\Netsvsc_Client_Test_Lib"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\..\.." /D NDEBUG /D WIN32 /D _WINDOWS /D ACE_CLIENT_TEST_BUILD_DLL /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\..\lib\Client_Test.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib /libpath:"." /libpath:"..\..\..\..\lib" /nologo /subsystem:windows /pdb:none /implib:"..\..\..\..\lib\Client_Test.lib" /dll  /machine:I386 /out:"..\..\..\..\lib\Client_Test.dll"

!ENDIF

# Begin Target

# Name "Netsvsc_Client_Test_Lib - Win32 Debug"
# Name "Netsvsc_Client_Test_Lib - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Client_Test.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Client_Test.h"
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
