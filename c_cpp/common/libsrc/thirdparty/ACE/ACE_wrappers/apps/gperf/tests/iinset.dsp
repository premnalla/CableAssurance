# Microsoft Developer Studio Project File - Name="iinset" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=iinset - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="iinset - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "iinset - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "iinset - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "iinset - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "."
# PROP Intermediate_Dir "Debug\iinset"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\iinset/" /I "..\..\.." /D _DEBUG /D WIN32 /D _CONSOLE /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:".\iout.pdb" /debug /machine:I386 /out:".\iout.exe"

!ELSEIF  "$(CFG)" == "iinset - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\iinset"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _CONSOLE /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Release\iout.exe"

!ENDIF

# Begin Target

# Name "iinset - Win32 Debug"
# Name "iinset - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="idl.cpp"
# End Source File
# Begin Source File

SOURCE="test.cpp"
# End Source File
# End Group
# Begin Group "Gperf Files"

# PROP Default_Filter "gperf"
# Begin Source File

SOURCE="idl.gperf"

!IF  "$(CFG)" == "iinset - Win32 Debug"

# PROP Ignore_Default_Tool 1
# Begin Custom Build - Invoking ..\..\..\bin\gperf on $(InputPath)
InputPath="idl.gperf"

BuildCmds= \
	PATH=%PATH%;..\..\..\lib \
	..\..\..\bin\gperf -m -M -c -C -D -S1 -E -T -a -o -p > "idl.cpp" $(InputPath) \

"idl.cpp" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
   $(BuildCmds)
# End Custom Build

!ELSEIF  "$(CFG)" == "iinset - Win32 Release"

# PROP Ignore_Default_Tool 1
# Begin Custom Build - Invoking ..\..\..\bin\gperf on $(InputPath)
InputPath="idl.gperf"

BuildCmds= \
	PATH=%PATH%;..\..\..\lib \
	..\..\..\bin\gperf -m -M -c -C -D -S1 -E -T -a -o -p > "idl.cpp" $(InputPath) \

"idl.cpp" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
   $(BuildCmds)
# End Custom Build

!ENDIF

# End Source File
# End Group
# End Target
# End Project
