# Microsoft Developer Studio Project File - Name="examples_load_file" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=load_file - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "examples_load_file.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "examples_load_file.mak" CFG="load_file - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "load_file - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "load_file - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "load_file - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release\load_file"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\load_file"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MD /W1 /I "..\lib" /FdRelease\load_file.pdb /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_CONSOLE" /D "_UNICODE" /c
# ADD CPP /nologo /FD /MD /W1 /I "..\lib" /FdRelease\load_file.pdb /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_CONSOLE" /D "_UNICODE" /c
# ADD BASE RSC /l 0x409 /d "_CONSOLE" /i "..\lib" /d "_UNICODE" /i c:\mysql\include
# ADD RSC /l 0x409 /d "_CONSOLE" /i "..\lib" /d "_UNICODE" /i c:\mysql\include
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 mysqlpp.lib libmysql.lib /nologo /machine:i386 /out:"Release\load_file.exe" /subsystem:console /libpath:"c:\mysql\lib\opt" /libpath:"..\lib\release"
# ADD LINK32 mysqlpp.lib libmysql.lib /nologo /machine:i386 /out:"Release\load_file.exe" /subsystem:console /libpath:"c:\mysql\lib\opt" /libpath:"..\lib\release"

!ELSEIF  "$(CFG)" == "load_file - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug\load_file"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug\load_file"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MDd /W1 /I "..\lib" /Zi /Gm /GZ /FdDebug\load_file.pdb /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_CONSOLE" /D "_DEBUG" /D "_UNICODE" /c
# ADD CPP /nologo /FD /MDd /W1 /I "..\lib" /Zi /Gm /GZ /FdDebug\load_file.pdb /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_CONSOLE" /D "_DEBUG" /D "_UNICODE" /c
# ADD BASE RSC /l 0x409 /d "_CONSOLE" /i "..\lib" /d "_DEBUG" /d "_UNICODE" /i c:\mysql\include
# ADD RSC /l 0x409 /d "_CONSOLE" /i "..\lib" /d "_DEBUG" /d "_UNICODE" /i c:\mysql\include
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 mysqlpp.lib libmysql.lib /nologo /machine:i386 /out:"Debug\load_file.exe" /subsystem:console /debug /libpath:"c:\mysql\lib\opt" /libpath:"..\lib\debug"
# ADD LINK32 mysqlpp.lib libmysql.lib /nologo /machine:i386 /out:"Debug\load_file.exe" /subsystem:console /debug /libpath:"c:\mysql\lib\opt" /libpath:"..\lib\debug"

!ENDIF

# Begin Target

# Name "load_file - Win32 Release"
# Name "load_file - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter ""
# Begin Source File

SOURCE=.\load_file.cpp
# End Source File
# End Group
# End Target
# End Project

