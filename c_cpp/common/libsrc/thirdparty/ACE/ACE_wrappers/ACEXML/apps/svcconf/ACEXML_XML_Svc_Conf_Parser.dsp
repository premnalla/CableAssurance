# Microsoft Developer Studio Project File - Name="ACEXML_XML_Svc_Conf_Parser" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=ACEXML_XML_Svc_Conf_Parser - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="ACEXML_XML_Svc_Conf_Parser - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "ACEXML_XML_Svc_Conf_Parser - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "ACEXML_XML_Svc_Conf_Parser - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "ACEXML_XML_Svc_Conf_Parser - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "..\..\..\lib"
# PROP Intermediate_Dir "Debug\ACEXML_XML_Svc_Conf_Parser"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\ACEXML_XML_Svc_Conf_Parser/" /I "..\..\.." /I "..\..\..\ACEXML\common" /D _DEBUG /D WIN32 /D _WINDOWS /FD /c
# SUBTRACT CPP /Fr /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.." /i "..\..\..\ACEXML\common"
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACEXML_XML_Svc_Conf_Parser.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib ACEXMLd.lib ACEXML_Parserd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:windows /pdb:"..\..\..\lib\ACEXML_XML_Svc_Conf_Parserd.pdb" /implib:"..\..\..\lib\ACEXML_XML_Svc_Conf_Parserd.lib" /dll /debug /machine:I386 /out:"..\..\..\lib\ACEXML_XML_Svc_Conf_Parserd.dll"

!ELSEIF  "$(CFG)" == "ACEXML_XML_Svc_Conf_Parser - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "..\..\..\lib"
# PROP Intermediate_Dir "Release\ACEXML_XML_Svc_Conf_Parser"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /I "..\..\..\ACEXML\common" /D NDEBUG /D WIN32 /D _WINDOWS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.." /i "..\..\..\ACEXML\common"
BSC32=bscmake.exe
# ADD BSC32 /nologo /o"..\..\..\lib\ACEXML_XML_Svc_Conf_Parser.bsc"
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib ACEXML.lib ACEXML_Parser.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:windows /pdb:none /implib:"..\..\..\lib\ACEXML_XML_Svc_Conf_Parser.lib" /dll  /machine:I386 /out:"..\..\..\lib\ACEXML_XML_Svc_Conf_Parser.dll"

!ENDIF

# Begin Target

# Name "ACEXML_XML_Svc_Conf_Parser - Win32 Debug"
# Name "ACEXML_XML_Svc_Conf_Parser - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Svcconf.cpp"
# End Source File
# Begin Source File

SOURCE="Svcconf_Handler.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Svcconf.h"
# End Source File
# Begin Source File

SOURCE="Svcconf_Handler.h"
# End Source File
# End Group
# Begin Group "Inline Files"

# PROP Default_Filter "i;inl"
# Begin Source File

SOURCE="Svcconf_Handler.i"
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
