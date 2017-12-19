# Microsoft Developer Studio Project File - Name="QOS_Change_Sender_TSpec_Sender" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

CFG=QOS_Change_Sender_TSpec_Sender - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE run the tool that generated this project file and specify the
!MESSAGE nmake output type.  You can then use the following command:
!MESSAGE
!MESSAGE NMAKE.
!MESSAGE
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE
!MESSAGE NMAKE CFG="QOS_Change_Sender_TSpec_Sender - Win32 Debug"
!MESSAGE
!MESSAGE Possible choices for configuration are:
!MESSAGE
!MESSAGE "QOS_Change_Sender_TSpec_Sender - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE "QOS_Change_Sender_TSpec_Sender - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "QOS_Change_Sender_TSpec_Sender - Win32 Debug"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "."
# PROP Intermediate_Dir "Debug\QOS_Change_Sender_TSpec_Sender"
# PROP Target_Dir ""
# ADD CPP /nologo /Ob0 /W3 /Gm /GX /Zi /MDd /GR /Gy /Fd"Debug\QOS_Change_Sender_TSpec_Sender/" /I "..\..\.." /D _DEBUG /D WIN32 /D _CONSOLE /D ACE_HAS_QOS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "_DEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d _DEBUG /d ACE_HAS_QOS /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACEd.lib ACE_QoSd.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:".\sender.pdb" /debug /machine:I386 /out:".\sender.exe"

!ELSEIF  "$(CFG)" == "QOS_Change_Sender_TSpec_Sender - Win32 Release"

# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release\QOS_Change_Sender_TSpec_Sender"
# PROP Target_Dir ""
# ADD CPP /nologo /O2 /W3 /GX /MD /GR /I "..\..\.." /D NDEBUG /D WIN32 /D _CONSOLE /D ACE_HAS_QOS /FD /c
# SUBTRACT CPP /YX

# ADD MTL /D "NDEBUG" /nologo /mktyplib203 /win32
# ADD RSC /l 0x409 /d NDEBUG /d ACE_HAS_QOS /i "..\..\.."
BSC32=bscmake.exe
# ADD BSC32 /nologo 
LINK32=link.exe
# ADD LINK32 advapi32.lib user32.lib /INCREMENTAL:NO ACE.lib ACE_QoS.lib /libpath:"." /libpath:"..\..\..\lib" /nologo /subsystem:console /pdb:none  /machine:I386 /out:"Release\sender.exe"

!ENDIF

# Begin Target

# Name "QOS_Change_Sender_TSpec_Sender - Win32 Debug"
# Name "QOS_Change_Sender_TSpec_Sender - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;cxx;c"
# Begin Source File

SOURCE="Fill_ACE_QoS.cpp"
# End Source File
# Begin Source File

SOURCE="QoS_Signal_Handler.cpp"
# End Source File
# Begin Source File

SOURCE="QoS_Util.cpp"
# End Source File
# Begin Source File

SOURCE="sender.cpp"
# End Source File
# Begin Source File

SOURCE="Sender_QoS_Event_Handler.cpp"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hh"
# Begin Source File

SOURCE="Fill_ACE_QoS.h"
# End Source File
# Begin Source File

SOURCE="QoS_Signal_Handler.h"
# End Source File
# Begin Source File

SOURCE="QoS_Util.h"
# End Source File
# Begin Source File

SOURCE="Sender_QoS_Event_Handler.h"
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
