# Microsoft Developer Studio Project File - Name="Send_Large_Msg_Client_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=Send_Large_Msg_Client_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="Send_Large_Msg_Client_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "Send_Large_Msg_Client_Static - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "Send_Large_Msg_Client_Static - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Send_Large_Msg_Client_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\Send_Large_Msg_Client_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Static_Debug\Send_Large_Msg_Client_Static/" /I "..\..\.." /I "..\..\..\protocols" /D _DEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\.." /i "..\..\..\protocols"
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEsd.lib ACE_HTBPsd.lib Test_Outputsd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:"Static_Debug\client.pdb" /debug /machine:I386 /out:"Static_Debug\client.exe"

!ELSEIF  "$(CFG)" == "Send_Large_Msg_Client_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\Send_Large_Msg_Client_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /I "..\..\..\protocols" /D NDEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\.." /i "..\..\..\protocols"
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEs.lib ACE_HTBPs.lib Test_Outputs.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Static_Release\client.exe"

!ENDIF

# Begin Target

# Name "Send_Large_Msg_Client_Static - Win32 Debug"
# Name "Send_Large_Msg_Client_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="client.cpp"
# End Source File
# End Group
# End Target
# End Project
