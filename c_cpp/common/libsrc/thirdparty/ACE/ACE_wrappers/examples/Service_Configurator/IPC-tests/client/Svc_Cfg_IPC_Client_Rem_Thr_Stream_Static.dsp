# Microsoft Developer Studio Project File - Name="Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Static_Debug"
# PROP Intermediate_Dir "Static_Debug\Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Static_Debug\Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static/" /I "..\..\..\.." /D _DEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /i "..\..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEsd.lib /libpath:"." /libpath:"..\..\..\..\lib" /nologo /subsystem:console /pdb:"Static_Debug\remote_thr_stream_test.pdb" /debug /machine:I386 /out:"Static_Debug\remote_thr_stream_test.exe"

!ELSEIF  "$(CFG)" == "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Static_Release"
# PROP Intermediate_Dir "Static_Release\Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\..\.." /D NDEBUG /D WIN32 /D _CONSOLE /D ACE_AS_STATIC_LIBS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /i "..\..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEs.lib /libpath:"." /libpath:"..\..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Static_Release\remote_thr_stream_test.exe"

!ENDIF

# Begin Target

# Name "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Debug"
# Name "Svc_Cfg_IPC_Client_Rem_Thr_Stream_Static - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="remote_thr_stream_client_test.cpp"
# End Source File
# End Group
# End Target
# End Project
