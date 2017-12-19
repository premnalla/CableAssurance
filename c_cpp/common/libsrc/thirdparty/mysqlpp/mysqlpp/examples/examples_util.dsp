# Microsoft Developer Studio Project File - Name="examples_util" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=util - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "examples_util.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "examples_util.mak" CFG="util - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "util - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE "util - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "util - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release\util"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\util"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MD /W1 /I "..\lib" /I "c:\mysql\include" /GR /EHsc /FdRelease\mysqlpp_util.pdb /D "WIN32" /D "_LIB" /D "_UNICODE" /c
# ADD CPP /nologo /FD /MD /W1 /I "..\lib" /I "c:\mysql\include" /GR /EHsc /FdRelease\mysqlpp_util.pdb /D "WIN32" /D "_LIB" /D "_UNICODE" /c
# ADD BASE RSC /l 0x409
# ADD RSC /l 0x409
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo /out:"Release\mysqlpp_util.lib"
# ADD LIB32 /nologo /out:"Release\mysqlpp_util.lib"

!ELSEIF  "$(CFG)" == "util - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug\util"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug\util"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MDd /W1 /I "..\lib" /I "c:\mysql\include" /GR /EHsc /Zi /Gm /GZ /FdDebug\mysqlpp_util.pdb /D "WIN32" /D "_LIB" /D "_UNICODE" /D "_DEBUG" /c
# ADD CPP /nologo /FD /MDd /W1 /I "..\lib" /I "c:\mysql\include" /GR /EHsc /Zi /Gm /GZ /FdDebug\mysqlpp_util.pdb /D "WIN32" /D "_LIB" /D "_UNICODE" /D "_DEBUG" /c
# ADD BASE RSC /l 0x409
# ADD RSC /l 0x409
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo /out:"Debug\mysqlpp_util.lib"
# ADD LIB32 /nologo /out:"Debug\mysqlpp_util.lib"

!ENDIF

# Begin Target

# Name "util - Win32 Release"
# Name "util - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter ""
# Begin Source File

SOURCE=.\util.cpp
# End Source File
# End Group
# End Target
# End Project

