# Microsoft Developer Studio Project File - Name="pinset" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=pinset - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="pinset - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "pinset - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "pinset - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "pinset - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "."
# PROP Intermediate_Dir "Debug\pinset"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\pinset/" /I "..\..\.." /D _DEBUG /D WIN32 /D _CONSOLE /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:".\pout.pdb" /debug /machine:I386 /out:".\pout.exe"

!ELSEIF  "$(CFG)" == "pinset - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\pinset"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _CONSOLE /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Release\pout.exe"

!ENDIF

# Begin Target

# Name "pinset - Win32 Debug"
# Name "pinset - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="pascal.cpp"
# End Source File
# Begin Source File

SOURCE="test.cpp"
# End Source File
# End Group
# Begin Group "Gperf Files"

# PROP Default_Filter "gperf"
# Begin Source File

SOURCE="pascal.gperf"

!IF  "$(CFG)" == "pinset - Win32 Debug"

# PROP Ignore_Default_Tool 1
# Begin Custom Build - Invoking ..\..\..\bin\gperf on $(InputPath)
InputPath="pascal.gperf"

BuildCmds= \
	PATH=%PATH%;..\..\..\lib \
	..\..\..\bin\gperf -a -o -S2 -p > "pascal.cpp" $(InputPath) \

"pascal.cpp" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
   $(BuildCmds)
# End Custom Build

!ELSEIF  "$(CFG)" == "pinset - Win32 Release"

# PROP Ignore_Default_Tool 1
# Begin Custom Build - Invoking ..\..\..\bin\gperf on $(InputPath)
InputPath="pascal.gperf"

BuildCmds= \
	PATH=%PATH%;..\..\..\lib \
	..\..\..\bin\gperf -a -o -S2 -p > "pascal.cpp" $(InputPath) \

"pascal.cpp" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
   $(BuildCmds)
# End Custom Build

!ENDIF

# End Source File
# End Group
# End Target
# End Project
