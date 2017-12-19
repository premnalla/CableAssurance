# Microsoft Developer Studio Project File - Name="txObject" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Static Library" 0x0104

CFG=txObject - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "txObject.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "txObject.mak" CFG="txObject - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "txObject - Win32 Release" (based on "Win32 (x86) Static Library")
!MESSAGE "txObject - Win32 Debug" (based on "Win32 (x86) Static Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "txObject - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /YX /FD /c
# ADD CPP /nologo /MD /W3 /GX /O2 /I "..\..\layer1\inc" /I "..\..\layer2\inc" /I "..\..\layer3\inc" /I "..\..\layer3\src" /I "..\..\layer4\inc" /I "..\..\layer5\inc" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "TX_WIN" /D "TX_OOT_SUPPORT" /D "FIBER_SUPPORT" /FR /YX /FD /GT /c
# ADD BASE RSC /l 0x409
# ADD RSC /l 0x409
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo

!ELSEIF  "$(CFG)" == "txObject - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_WINDOWS" /YX /FD /c
# ADD CPP /nologo /MD /W3 /Gm /GX /ZI /Od /I "..\..\layer1\inc" /I "..\..\layer2\inc" /I "..\..\layer3\inc" /I "..\..\layer3\src" /I "..\..\layer4\inc" /I "..\..\layer5\inc" /D "WIN32" /D "_WINDOWS" /D "TX_WIN" /D "TX_OOT_SUPPORT" /D "FIBER_SUPPORT" /FR /YX /FD /GT /c
# SUBTRACT CPP /X
# ADD BASE RSC /l 0x409
# ADD RSC /l 0x409
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LIB32=link.exe -lib
# ADD BASE LIB32 /nologo
# ADD LIB32 /nologo

!ENDIF 

# Begin Target

# Name "txObject - Win32 Release"
# Name "txObject - Win32 Debug"
# Begin Group "layer1"

# PROP Default_Filter ""
# Begin Group "inc1"

# PROP Default_Filter "h"
# Begin Group "sys1"

# PROP Default_Filter "h"
# Begin Source File

SOURCE=..\..\layer1\inc\sys\txobjref.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\sys\txrefobj.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\sys\txsyslist.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\sys\txtypecheckss.h
# End Source File
# End Group
# Begin Source File

SOURCE=..\..\layer1\inc\txboolean.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txbtree.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txchar16.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txchar8.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txdouble.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txfactory.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txfloat.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txglist.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txgobject.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txgvector.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txhashmap.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txhashtable.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txin.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txint16.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txint32.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txint64.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txint8.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txlist.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txobject.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txostring.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txout.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txqueue.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txsmrtbuf.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txstream.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txstring.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txtypecodes.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuchar16.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuchar8.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuint16.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuint32.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuint64.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txuint8.h
# End Source File
# Begin Source File

SOURCE=..\..\layer1\inc\txutil.h
# End Source File
# End Group
# Begin Group "src1"

# PROP Default_Filter "cxx"
# Begin Source File

SOURCE=..\..\layer1\src\txboolean.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txbtree.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txchar16.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txchar8.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txdouble.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txfactory.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txfloat.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txgobject.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txhashmap.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txhashtable.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txin.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txint16.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txint32.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txint64.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txint8.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txlist.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txobject.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txobjref.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txostring.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txout.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txrefobj.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txsmrtbuf.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txstream.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txstring.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txsyslist.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txtypecheckss.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuchar16.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuchar8.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuint16.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuint32.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuint64.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer1\src\txuint8.cxx
# End Source File
# End Group
# End Group
# Begin Group "layer2"

# PROP Default_Filter ""
# Begin Group "inc2"

# PROP Default_Filter "h"
# Begin Source File

SOURCE=..\..\layer2\inc\txsync.h
# End Source File
# Begin Source File

SOURCE=..\..\layer2\inc\txtimer.h
# End Source File
# End Group
# Begin Group "src2"

# PROP Default_Filter "cxx"
# Begin Source File

SOURCE=..\..\layer2\src\txsync.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer2\src\txtimer.cxx
# End Source File
# End Group
# End Group
# Begin Group "layer3"

# PROP Default_Filter ""
# Begin Group "inc3"

# PROP Default_Filter "h"
# Begin Group "sys3"

# PROP Default_Filter "h"
# Begin Source File

SOURCE=..\..\layer3\inc\sys\txthrdbase.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\inc\sys\txthrdss.h
# End Source File
# End Group
# Begin Source File

SOURCE=..\..\layer3\inc\txevent.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\inc\txeventq.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\inc\txlock.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\inc\txthrdmgr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\inc\txthread.h
# End Source File
# End Group
# Begin Group "src3"

# PROP Default_Filter "cxx"
# Begin Group "tx_oot"

# PROP Default_Filter "h,cxx"
# Begin Source File

SOURCE=..\..\layer3\src\Event.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\Event.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thrdmgr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thrdmgr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thrdplist.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thrdplist.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thrdstats.h
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thread.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\thread.h
# End Source File
# End Group
# Begin Source File

SOURCE=..\..\layer3\src\txevent.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txeventq.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txlock.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txthrdbase.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txthrdmgr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txthrdss.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer3\src\txthread.cxx
# End Source File
# End Group
# End Group
# Begin Group "layer4"

# PROP Default_Filter ""
# Begin Group "inc4"

# PROP Default_Filter "h"
# Begin Source File

SOURCE=..\..\layer4\inc\txabspacker.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txabspeer.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txabssrvr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txipcsrvr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txippeer.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txntwrkmsg.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txpeerlist.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txsocket.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txtcpsrvr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer4\inc\txudpsrvr.h
# End Source File
# End Group
# Begin Group "src4"

# PROP Default_Filter "cxx"
# Begin Source File

SOURCE=..\..\layer4\src\txabspacker.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txabspeer.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txabssrvr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txipcsrvr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txippeer.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txntwrkmsg.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txpeerlist.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txsocket.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txtcpsrvr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer4\src\txudpsrvr.cxx
# End Source File
# End Group
# End Group
# Begin Group "layer5"

# PROP Default_Filter ""
# Begin Group "inc5"

# PROP Default_Filter "h"
# Begin Group "sys5"

# PROP Default_Filter "h"
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjcbmgr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjintf.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjmsg.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjptr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjrpc.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjrpcbc.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjsrvr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txdobjutil.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txmemobj.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txmemspace.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstate.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstatectmtx.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstatemgr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstatemtx.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstateobj.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txstatevec.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\sys\txsyncmtx.h
# End Source File
# End Group
# Begin Source File

SOURCE=..\..\layer5\inc\txdobject.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\txdobjglbls.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\txdobjmgr.h
# End Source File
# Begin Source File

SOURCE=..\..\layer5\inc\txdobjspace.h
# End Source File
# End Group
# Begin Group "src5"

# PROP Default_Filter "cxx"
# Begin Source File

SOURCE=..\..\layer5\src\txdobjcbmgr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobject.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjglbls.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjintf.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjmgr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjmsg.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjptr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjrpc.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjrpcbc.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjspace.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjsrvr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txdobjutil.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txmemobj.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txmemspace.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstate.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstatectmtx.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstatemgr.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstatemtx.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstateobj.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txstatevec.cxx
# End Source File
# Begin Source File

SOURCE=..\..\layer5\src\txsyncmtx.cxx
# End Source File
# End Group
# End Group
# End Target
# End Project
