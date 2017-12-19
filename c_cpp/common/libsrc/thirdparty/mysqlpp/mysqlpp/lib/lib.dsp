# Microsoft Developer Studio Project File - Name="lib" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=mysqlpp - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "lib.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "lib.mak" CFG="mysqlpp - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "mysqlpp - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "mysqlpp - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "mysqlpp - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release\mysqlpp"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\mysqlpp"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MD /W1 /FdRelease\mysqlpp.pdb /I ".." /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /c
# ADD CPP /nologo /FD /MD /W1 /FdRelease\mysqlpp.pdb /I ".." /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /c
# ADD BASE MTL /nologo /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /mktyplib203 /win32
# ADD MTL /nologo /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /mktyplib203 /win32
# ADD BASE RSC /l 0x409 /i ".." /d "_UNICODE" /d "MYSQLPP_MAKING_DLL" /i c:\mysql\include
# ADD RSC /l 0x409 /i ".." /d "_UNICODE" /d "MYSQLPP_MAKING_DLL" /i c:\mysql\include
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 libmysql.lib /nologo /dll /machine:i386 /out:"Release\mysqlpp.dll" /implib:"Release\mysqlpp.lib" /libpath:"c:\mysql\lib\opt"
# ADD LINK32 libmysql.lib /nologo /dll /machine:i386 /out:"Release\mysqlpp.dll" /implib:"Release\mysqlpp.lib" /libpath:"c:\mysql\lib\opt"

!ELSEIF  "$(CFG)" == "mysqlpp - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug\mysqlpp"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug\mysqlpp"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /FD /MDd /W1 /Zi /Gm /GZ /FdDebug\mysqlpp.pdb /I ".." /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_DEBUG" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /c
# ADD CPP /nologo /FD /MDd /W1 /Zi /Gm /GZ /FdDebug\mysqlpp.pdb /I ".." /I "c:\mysql\include" /GR /EHsc /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_DEBUG" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /c
# ADD BASE MTL /nologo /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_DEBUG" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /mktyplib203 /win32
# ADD MTL /nologo /D "WIN32" /D "_USRDLL" /D "DLL_EXPORTS" /D "_DEBUG" /D "_UNICODE" /D "MYSQLPP_MAKING_DLL" /mktyplib203 /win32
# ADD BASE RSC /l 0x409 /d "_DEBUG" /i ".." /d "_UNICODE" /d "MYSQLPP_MAKING_DLL" /i c:\mysql\include
# ADD RSC /l 0x409 /d "_DEBUG" /i ".." /d "_UNICODE" /d "MYSQLPP_MAKING_DLL" /i c:\mysql\include
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 libmysql.lib /nologo /dll /machine:i386 /out:"Debug\mysqlpp.dll" /implib:"Debug\mysqlpp.lib" /debug /libpath:"c:\mysql\lib\opt"
# ADD LINK32 libmysql.lib /nologo /dll /machine:i386 /out:"Debug\mysqlpp.dll" /implib:"Debug\mysqlpp.lib" /debug /libpath:"c:\mysql\lib\opt"

!ENDIF

# Begin Target

# Name "mysqlpp - Win32 Release"
# Name "mysqlpp - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter ""
# Begin Source File

SOURCE=.\coldata.cpp
# End Source File
# Begin Source File

SOURCE=.\connection.cpp
# End Source File
# Begin Source File

SOURCE=.\datetime.cpp
# End Source File
# Begin Source File

SOURCE=.\field_names.cpp
# End Source File
# Begin Source File

SOURCE=.\field_types.cpp
# End Source File
# Begin Source File

SOURCE=.\fields.cpp
# End Source File
# Begin Source File

SOURCE=.\manip.cpp
# End Source File
# Begin Source File

SOURCE=.\myset.cpp
# End Source File
# Begin Source File

SOURCE=.\qparms.cpp
# End Source File
# Begin Source File

SOURCE=.\query.cpp
# End Source File
# Begin Source File

SOURCE=.\result.cpp
# End Source File
# Begin Source File

SOURCE=.\row.cpp
# End Source File
# Begin Source File

SOURCE=.\sql_string.cpp
# End Source File
# Begin Source File

SOURCE=.\string_util.cpp
# End Source File
# Begin Source File

SOURCE=.\transaction.cpp
# End Source File
# Begin Source File

SOURCE=.\type_info.cpp
# End Source File
# Begin Source File

SOURCE=.\vallist.cpp
# End Source File
# End Group
# End Target
# End Project

