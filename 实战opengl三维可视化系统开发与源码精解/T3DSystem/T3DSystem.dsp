# Microsoft Developer Studio Project File - Name="T3DSystem" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Application" 0x0101

CFG=T3DSystem - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "T3DSystem.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "T3DSystem.mak" CFG="T3DSystem - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "T3DSystem - Win32 Release" (based on "Win32 (x86) Application")
!MESSAGE "T3DSystem - Win32 Debug" (based on "Win32 (x86) Application")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "T3DSystem - Win32 Release"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /Yu"stdafx.h" /FD /c
# ADD CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /FD /c
# ADD BASE MTL /nologo /D "NDEBUG" /mktyplib203 /win32
# ADD MTL /nologo /D "NDEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0x804 /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0x804 /d "NDEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /machine:I386
# ADD LINK32 /nologo /subsystem:windows /machine:I386

!ELSEIF  "$(CFG)" == "T3DSystem - Win32 Debug"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /Yu"stdafx.h" /FD /GZ /c
# ADD CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /FR /Yu"stdafx.h" /FD /GZ /c
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0x804 /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0x804 /d "_DEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /debug /machine:I386 /pdbtype:sept
# ADD LINK32 glaux.lib glu32.lib glut32.lib OpenGL32.LIB glew32.lib oci.lib ImageLoad.lib ImageObjectD.lib vfw32.lib /nologo /subsystem:windows /debug /machine:I386 /pdbtype:sept

!ENDIF 

# Begin Target

# Name "T3DSystem - Win32 Release"
# Name "T3DSystem - Win32 Debug"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\3DSModel.cpp
# End Source File
# Begin Source File

SOURCE=.\alCulateF.cpp
# End Source File
# Begin Source File

SOURCE=.\AllocUnAlloc2D3D.cpp
# End Source File
# Begin Source File

SOURCE=.\AVICapture.cpp
# End Source File
# Begin Source File

SOURCE=.\AviParameter1.cpp
# End Source File
# Begin Source File

SOURCE=.\BridgeData.cpp
# End Source File
# Begin Source File

SOURCE=.\BridgeSet.cpp
# End Source File
# Begin Source File

SOURCE=.\ClientCapture.cpp
# End Source File
# Begin Source File

SOURCE=.\Delaunay.cpp
# End Source File
# Begin Source File

SOURCE=.\DemLoad.cpp
# End Source File
# Begin Source File

SOURCE=.\DesingScheme.cpp
# End Source File
# Begin Source File

SOURCE=.\DialogInputData.cpp
# End Source File
# Begin Source File

SOURCE=.\DXF.cpp
# End Source File
# Begin Source File

SOURCE=.\font.cpp
# End Source File
# Begin Source File

SOURCE=.\GetDemInsertValue.cpp
# End Source File
# Begin Source File

SOURCE=.\Load3DS.cpp
# End Source File
# Begin Source File

SOURCE=.\MainFrm.cpp
# End Source File
# Begin Source File

SOURCE=.\MainToolBar.cpp
# End Source File
# Begin Source File

SOURCE=.\ModelMangerQD.cpp
# End Source File
# Begin Source File

SOURCE=.\msflexgrid.cpp
# End Source File
# Begin Source File

SOURCE=.\NewProject.cpp
# End Source File
# Begin Source File

SOURCE=.\NewScheme.cpp
# End Source File
# Begin Source File

SOURCE=.\OCIclass.cpp
# End Source File
# Begin Source File

SOURCE=.\OpenProject.cpp
# End Source File
# Begin Source File

SOURCE=.\picture.cpp
# End Source File
# Begin Source File

SOURCE=.\PlaneGraphic.cpp
# End Source File
# Begin Source File

SOURCE=.\PLaneRL0.cpp
# End Source File
# Begin Source File

SOURCE=.\RecordPictureSpeed.cpp
# End Source File
# Begin Source File

SOURCE=.\rowcursor.cpp
# End Source File
# Begin Source File

SOURCE=.\Snow.cpp
# End Source File
# Begin Source File

SOURCE=.\SpaceSearchSet.cpp
# End Source File
# Begin Source File

SOURCE=.\StdAfx.cpp
# ADD CPP /Yc"stdafx.h"
# End Source File
# Begin Source File

SOURCE=.\T3DSystem.cpp
# End Source File
# Begin Source File

SOURCE=.\T3DSystem.rc
# End Source File
# Begin Source File

SOURCE=.\T3DSystemDoc.cpp
# End Source File
# Begin Source File

SOURCE=.\T3DSystemView.cpp
# End Source File
# Begin Source File

SOURCE=.\TextProgressCtrl.cpp
# End Source File
# Begin Source File

SOURCE=.\Texture.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureBP.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureLJ.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureLoad.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureQLHpm.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureTunnel.cpp
# End Source File
# Begin Source File

SOURCE=.\TextureTunnelDm.cpp
# End Source File
# Begin Source File

SOURCE=.\TunncelPropertySet.cpp
# End Source File
# Begin Source File

SOURCE=.\TunnelData.cpp
# End Source File
# Begin Source File

SOURCE=.\vector.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmDesign.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmGeoSegmentFeatures.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmJtLpPcPD.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmR.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmSlopeData.cpp
# End Source File
# Begin Source File

SOURCE=.\ZdmStartLC_H.cpp
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\3DSModel.h
# End Source File
# Begin Source File

SOURCE=.\alCulateF.h
# End Source File
# Begin Source File

SOURCE=.\AllocUnAlloc2D3D.h
# End Source File
# Begin Source File

SOURCE=.\AVICapture.h
# End Source File
# Begin Source File

SOURCE=.\AviParameter1.h
# End Source File
# Begin Source File

SOURCE=.\BridgeData.h
# End Source File
# Begin Source File

SOURCE=.\BridgeSet.h
# End Source File
# Begin Source File

SOURCE=.\ClientCapture.h
# End Source File
# Begin Source File

SOURCE=.\Delaunay.h
# End Source File
# Begin Source File

SOURCE=.\DemLoad.h
# End Source File
# Begin Source File

SOURCE=.\DesingScheme.h
# End Source File
# Begin Source File

SOURCE=.\DialogInputData.h
# End Source File
# Begin Source File

SOURCE=.\DXF.h
# End Source File
# Begin Source File

SOURCE=.\font.h
# End Source File
# Begin Source File

SOURCE=.\GetDemInsertValue.h
# End Source File
# Begin Source File

SOURCE=.\Load3DS.h
# End Source File
# Begin Source File

SOURCE=.\MainFrm.h
# End Source File
# Begin Source File

SOURCE=.\MainToolBar.h
# End Source File
# Begin Source File

SOURCE=.\ModelMangerQD.h
# End Source File
# Begin Source File

SOURCE=.\msflexgrid.h
# End Source File
# Begin Source File

SOURCE=.\NewProject.h
# End Source File
# Begin Source File

SOURCE=.\NewScheme.h
# End Source File
# Begin Source File

SOURCE=.\OCIclass.h
# End Source File
# Begin Source File

SOURCE=.\OpenProject.h
# End Source File
# Begin Source File

SOURCE=.\picture.h
# End Source File
# Begin Source File

SOURCE=.\PlaneGraphic.h
# End Source File
# Begin Source File

SOURCE=.\PLaneRL0.h
# End Source File
# Begin Source File

SOURCE=.\RecordPictureSpeed.h
# End Source File
# Begin Source File

SOURCE=.\Resource.h
# End Source File
# Begin Source File

SOURCE=.\rowcursor.h
# End Source File
# Begin Source File

SOURCE=.\Snow.h
# End Source File
# Begin Source File

SOURCE=.\SpaceSearchSet.h
# End Source File
# Begin Source File

SOURCE=.\StdAfx.h
# End Source File
# Begin Source File

SOURCE=.\T3DSystem.h
# End Source File
# Begin Source File

SOURCE=.\T3DSystemDoc.h
# End Source File
# Begin Source File

SOURCE=.\T3DSystemView.h
# End Source File
# Begin Source File

SOURCE=.\TextProgressCtrl.h
# End Source File
# Begin Source File

SOURCE=.\Texture.h
# End Source File
# Begin Source File

SOURCE=.\TextureBP.h
# End Source File
# Begin Source File

SOURCE=.\TextureLJ.h
# End Source File
# Begin Source File

SOURCE=.\TextureLoad.h
# End Source File
# Begin Source File

SOURCE=.\TextureQLHpm.h
# End Source File
# Begin Source File

SOURCE=.\TextureTunnel.h
# End Source File
# Begin Source File

SOURCE=.\TextureTunnelDm.h
# End Source File
# Begin Source File

SOURCE=.\TunncelPropertySet.h
# End Source File
# Begin Source File

SOURCE=.\TunnelData.h
# End Source File
# Begin Source File

SOURCE=.\Vector.h
# End Source File
# Begin Source File

SOURCE=.\ZdmDesign.h
# End Source File
# Begin Source File

SOURCE=.\ZdmGeoSegmentFeatures.h
# End Source File
# Begin Source File

SOURCE=.\ZdmJtLpPcPD.h
# End Source File
# Begin Source File

SOURCE=.\ZdmR.h
# End Source File
# Begin Source File

SOURCE=.\ZdmSlopeData.h
# End Source File
# Begin Source File

SOURCE=.\ZdmStartLC_H.h
# End Source File
# End Group
# Begin Group "Resource Files"

# PROP Default_Filter "ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe"
# Begin Source File

SOURCE=.\res\bmp00001.bmp
# End Source File
# Begin Source File

SOURCE=.\res\bmp00016.bmp
# End Source File
# Begin Source File

SOURCE=.\res\SnowFloor.bmp
# End Source File
# Begin Source File

SOURCE=.\res\SnowLarge.bmp
# End Source File
# Begin Source File

SOURCE=.\res\SnowMiddle.bmp
# End Source File
# Begin Source File

SOURCE=.\res\SnowSmall.bmp
# End Source File
# Begin Source File

SOURCE=.\res\T3DSystem.ico
# End Source File
# Begin Source File

SOURCE=.\res\T3DSystem.rc2
# End Source File
# Begin Source File

SOURCE=.\res\T3DSystemDoc.ico
# End Source File
# Begin Source File

SOURCE=.\res\Toolbar.bmp
# End Source File
# Begin Source File

SOURCE=.\res\toolbar1.bmp
# End Source File
# End Group
# Begin Source File

SOURCE=.\ReadMe.txt
# End Source File
# Begin Source File

SOURCE=.\SkinPPWTL.lib
# End Source File
# End Target
# End Project
# Section T3DSystem : {7CCA0F7D-8ACC-42D1-BF1A-A72099318B20}
# 	1:20:IDD_DIALOG_ZDMDESIGN:111
# 	2:21:TYPEDEF: ZdmDesignPts:ZdmDesignPts
# 	2:16:Resource Include:resource.h
# 	2:18:TYPEDEF: PZdmSlope:PZdmSlope
# 	2:23:TYPEDEF: PZdmCordiPoint:PZdmCordiPoint
# 	2:22:TYPEDEF: PZdmDesignPts:PZdmDesignPts
# 	2:19:CLASS: ZdmDesignPts:ZdmDesignPts
# 	2:20:CLASS: ZdmCordiPoint:ZdmCordiPoint
# 	2:15:CLASS: Stations:Stations
# 	2:13:ZdmDesign.cpp:ZdmDesign.cpp
# 	2:15:CLASS: ZdmSlope:ZdmSlope
# 	2:10:ENUM: enum:enum
# 	2:17:CLASS: CZdmDesign:CZdmDesign
# 	2:11:ZdmDesign.h:ZdmDesign.h
# 	2:21:TYPEDEF: TunnelBridge:TunnelBridge
# 	2:22:TYPEDEF: PTunnelBridge:PTunnelBridge
# 	2:19:CLASS: TunnelBridge:TunnelBridge
# 	2:20:IDD_DIALOG_ZDMDESIGN:IDD_DIALOG_ZDMDESIGN
# 	2:22:TYPEDEF: ZdmCordiPoint:ZdmCordiPoint
# 	2:17:TYPEDEF: Stations:Stations
# 	2:19:Application Include:T3DSystem.h
# 	2:17:TYPEDEF: ZdmSlope:ZdmSlope
# 	2:18:TYPEDEF: PStations:PStations
# End Section
# Section T3DSystem : {B82FBE23-40F3-4DCB-B649-68D4227CF5E6}
# 	1:24:IDD_DIALOG_ZDM_STARTLC_H:115
# 	2:20:CLASS: CZdmStartLC_H:CZdmStartLC_H
# 	2:16:Resource Include:resource.h
# 	2:24:IDD_DIALOG_ZDM_STARTLC_H:IDD_DIALOG_ZDM_STARTLC_H
# 	2:10:ENUM: enum:enum
# 	2:16:ZdmStartLC_H.cpp:ZdmStartLC_H.cpp
# 	2:14:ZdmStartLC_H.h:ZdmStartLC_H.h
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {44839339-4E05-46D2-A0AB-87B2ED4F4EA4}
# 	1:25:IDD_DIALOG_ZDMGEOFEATURES:112
# 	2:16:Resource Include:resource.h
# 	2:23:ZdmGeoSegmentFeatures.h:ZdmGeoSegmentFeatures.h
# 	2:29:CLASS: CZdmGeoSegmentFeatures:CZdmGeoSegmentFeatures
# 	2:10:ENUM: enum:enum
# 	2:25:ZdmGeoSegmentFeatures.cpp:ZdmGeoSegmentFeatures.cpp
# 	2:25:IDD_DIALOG_ZDMGEOFEATURES:IDD_DIALOG_ZDMGEOFEATURES
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {C30C2214-3C62-4D71-B6C3-3F3D4A54F770}
# 	1:23:IDD_DIALOG_DEMSEPERATE1:102
# 	2:16:Resource Include:resource.h
# 	2:11:DemLoad.cpp:DemLoad.cpp
# 	2:9:DemLoad.h:DemLoad.h
# 	2:10:ENUM: enum:enum
# 	2:22:IDD_DIALOG_DEMSEPERATE:IDD_DIALOG_DEMSEPERATE1
# 	2:15:CLASS: CDemLoad:CDemLoad
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {9F6AA700-D188-11CD-AD48-00AA003C9CB6}
# 	2:5:Class:CRowCursor
# 	2:10:HeaderFile:rowcursor.h
# 	2:8:ImplFile:rowcursor.cpp
# End Section
# Section T3DSystem : {165D8276-68E7-449F-8A8D-D3F9A7CBF94E}
# 	1:23:IDD_DIALOG_AVIPARAMETER:130
# 	2:16:Resource Include:resource.h
# 	2:23:IDD_DIALOG_AVIPARAMETER:IDD_DIALOG_AVIPARAMETER
# 	2:16:AviParameter.cpp:AviParameter1.cpp
# 	2:10:ENUM: enum:enum
# 	2:20:CLASS: CAviParameter:CAviParameter
# 	2:14:AviParameter.h:AviParameter1.h
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {62E89DFB-A630-4A71-8FA3-CAAC45F81B63}
# 	1:21:IDD_DIALOG_ZDM_JTPCPD:116
# 	2:16:Resource Include:resource.h
# 	2:15:ZdmJtLpPcPD.cpp:ZdmJtLpPcPD.cpp
# 	2:10:ENUM: enum:enum
# 	2:21:IDD_DIALOG_ZDM_JTPCPD:IDD_DIALOG_ZDM_JTPCPD
# 	2:13:ZdmJtLpPcPD.h:ZdmJtLpPcPD.h
# 	2:19:CLASS: CZdmJtLpPcPD:CZdmJtLpPcPD
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {423DF9FF-2412-4B58-AF4B-56E16A56C998}
# 	1:21:IDD_DIALOG_TEXTURE_LJ:123
# 	2:17:CLASS: CTextureLJ:CTextureLJ
# 	2:16:Resource Include:resource.h
# 	2:21:IDD_DIALOG_TEXTURE_LJ:IDD_DIALOG_TEXTURE_LJ
# 	2:10:ENUM: enum:enum
# 	2:11:TextureLJ.h:TextureLJ.h
# 	2:19:Application Include:T3DSystem.h
# 	2:13:TextureLJ.cpp:TextureLJ.cpp
# End Section
# Section T3DSystem : {83729AAF-B1B7-487F-B106-71EA9952B4E1}
# 	1:22:IDD_DIALOG_PLANE_GRAPH:110
# 	2:21:TYPEDEF: ScreenStruct:ScreenStruct
# 	2:18:CLASS: CPlaneGraph:CPlaneGraph
# 	2:16:Resource Include:resource.h
# 	2:14:PlaneGraph.cpp:PlaneGraph.cpp
# 	2:19:CLASS: ScreenStruct:ScreenStruct
# 	2:16:TYPEDEF: PCircle:PCircle
# 	2:15:CLASS: PolyLine:PolyLine
# 	2:13:CLASS: Circle:Circle
# 	2:10:ENUM: enum:enum
# 	2:15:TYPEDEF: Circle:Circle
# 	2:14:CLASS: CADText:CADText
# 	2:12:PlaneGraph.h:PlaneGraph.h
# 	2:16:TYPEDEF: CADText:CADText
# 	2:17:TYPEDEF: PolyLine:PolyLine
# 	2:19:Application Include:T3DSystem.h
# 	2:18:TYPEDEF: PPolyLine:PPolyLine
# 	2:17:TYPEDEF: PCADText:PCADText
# 	2:22:IDD_DIALOG_PLANE_GRAPH:IDD_DIALOG_PLANE_GRAPH
# End Section
# Section T3DSystem : {C0F65743-8274-44B1-ADBC-2368F0E5296D}
# 	1:17:IDD_DIALOG_SCHEME:107
# 	2:16:Resource Include:resource.h
# 	2:13:NewScheme.cpp:NewScheme.cpp
# 	2:17:CLASS: CNewScheme:CNewScheme
# 	2:10:ENUM: enum:enum
# 	2:11:NewScheme.h:NewScheme.h
# 	2:19:Application Include:T3DSystem.h
# 	2:17:IDD_DIALOG_SCHEME:IDD_DIALOG_SCHEME
# End Section
# Section T3DSystem : {109499B9-08AB-45B2-BFF9-A151C6378BC4}
# 	1:22:IDD_DIALOG_PLANE_R_l01:109
# 	2:16:Resource Include:resource.h
# 	2:10:ENUM: enum:enum
# 	2:21:IDD_DIALOG_PLANE_R_l0:IDD_DIALOG_PLANE_R_l01
# 	2:16:CLASS: CPLaneRL0:CPLaneRL0
# 	2:19:Application Include:T3DSystem.h
# 	2:10:PLaneRL0.h:PLaneRL1.h
# 	2:12:PLaneRL0.cpp:PLaneRL1.cpp
# End Section
# Section T3DSystem : {420A51E4-A840-47AB-A0D1-196CE84DAEFD}
# 	1:27:IDD_DIALOG_TEXTURE_TUNNELDM:125
# 	2:16:Resource Include:resource.h
# 	2:23:CLASS: CTextureTunnelDm:CTextureTunnelDm
# 	2:27:IDD_DIALOG_TEXTURE_TUNNELDM:IDD_DIALOG_TEXTURE_TUNNELDM
# 	2:10:ENUM: enum:enum
# 	2:19:TextureTunnelDm.cpp:TextureTunnelDm.cpp
# 	2:17:TextureTunnelDm.h:TextureTunnelDm.h
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {18ADE6BD-F36E-459C-A9E8-E93FD8F6F157}
# 	1:19:IDD_DIALOG_MODEL_QD:127
# 	2:16:Resource Include:resource.h
# 	2:19:IDD_DIALOG_MODEL_QD:IDD_DIALOG_MODEL_QD
# 	2:17:ModelMangerQD.cpp:ModelMangerQD.cpp
# 	2:10:ENUM: enum:enum
# 	2:15:ModelMangerQD.h:ModelMangerQD.h
# 	2:19:Application Include:T3DSystem.h
# 	2:21:CLASS: CModelMangerQD:CModelMangerQD
# End Section
# Section T3DSystem : {93F5AC89-BE5E-4360-AE31-EB61525D90B9}
# 	1:25:IDD_DIALOG_SPACESELECTSET:106
# 	2:16:Resource Include:resource.h
# 	2:22:CLASS: CSpaceSearchSet:CSpaceSearchSet
# 	2:16:SpaceSearchSet.h:SpaceSearchSet.h
# 	2:25:IDD_DIALOG_SPACESELECTSET:IDD_DIALOG_SPACESELECTSET
# 	2:10:ENUM: enum:enum
# 	2:19:Application Include:T3DSystem.h
# 	2:18:SpaceSearchSet.cpp:SpaceSearchSet.cpp
# End Section
# Section T3DSystem : {3C408321-294E-4AE1-8D3D-2CE715528DC5}
# 	1:24:IDD_DIALOG_ZDM_SLOPEDATA:113
# 	2:16:Resource Include:resource.h
# 	2:20:CLASS: CZdmSlopeData:CZdmSlopeData
# 	2:10:ENUM: enum:enum
# 	2:24:IDD_DIALOG_ZDM_SLOPEDATA:IDD_DIALOG_ZDM_SLOPEDATA
# 	2:14:ZdmSlopeData.h:ZdmSlopeData.h
# 	2:19:Application Include:T3DSystem.h
# 	2:16:ZdmSlopeData.cpp:ZdmSlopeData.cpp
# End Section
# Section T3DSystem : {17D41114-2638-4438-ABCB-6CBBCE539DA4}
# 	1:17:IDD_DIALOG_TUNNEL:117
# 	2:16:Resource Include:resource.h
# 	2:12:TunnelData.h:TunnelData.h
# 	2:18:CLASS: CTunnelData:CTunnelData
# 	2:14:TunnelData.cpp:TunnelData.cpp
# 	2:10:ENUM: enum:enum
# 	2:19:Application Include:T3DSystem.h
# 	2:17:IDD_DIALOG_TUNNEL:IDD_DIALOG_TUNNEL
# End Section
# Section T3DSystem : {38266E53-47B2-4932-8CE5-6886485E88B2}
# 	1:22:IDD_DIALOG_LOADTEXTURE:103
# 	2:16:Resource Include:resource.h
# 	2:10:ENUM: enum:enum
# 	2:22:IDD_DIALOG_LOADTEXTURE:IDD_DIALOG_LOADTEXTURE
# 	2:13:TextureLoad.h:TextureLoad.h
# 	2:19:CLASS: CTextureLoad:CTextureLoad
# 	2:15:TextureLoad.cpp:TextureLoad.cpp
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {5F4DF280-531B-11CF-91F6-C2863C385E30}
# 	2:5:Class:CMSFlexGrid
# 	2:10:HeaderFile:msflexgrid.h
# 	2:8:ImplFile:msflexgrid.cpp
# End Section
# Section T3DSystem : {BEF6E003-A874-101A-8BBA-00AA00300CAB}
# 	2:5:Class:COleFont
# 	2:10:HeaderFile:font.h
# 	2:8:ImplFile:font.cpp
# End Section
# Section T3DSystem : {6262D3A0-531B-11CF-91F6-C2863C385E30}
# 	2:21:DefaultSinkHeaderFile:msflexgrid.h
# 	2:16:DefaultSinkClass:CMSFlexGrid
# End Section
# Section T3DSystem : {0F118403-BA4F-4CD4-AB2B-33D091C4A660}
# 	1:22:IDD_DIALOG_OPENPROJECT:105
# 	2:16:Resource Include:resource.h
# 	2:10:ENUM: enum:enum
# 	2:22:IDD_DIALOG_OPENPROJECT:IDD_DIALOG_OPENPROJECT
# 	2:13:OpenProject.h:OpenProject.h
# 	2:19:Application Include:T3DSystem.h
# 	2:19:CLASS: COpenProject:COpenProject
# 	2:15:OpenProject.cpp:OpenProject.cpp
# End Section
# Section T3DSystem : {815175E5-E838-49E3-A377-843370BB735B}
# 	1:21:IDD_DIALOG_TUNNNELSET:120
# 	2:16:Resource Include:resource.h
# 	2:26:CLASS: CTunncelPropertySet:CTunncelPropertySet
# 	2:22:TunncelPropertySet.cpp:TunncelPropertySet.cpp
# 	2:10:ENUM: enum:enum
# 	2:21:IDD_DIALOG_TUNNNELSET:IDD_DIALOG_TUNNNELSET
# 	2:19:Application Include:T3DSystem.h
# 	2:20:TunncelPropertySet.h:TunncelPropertySet.h
# End Section
# Section T3DSystem : {C42E7CF1-2B0D-44BB-AFFF-877DA2DD3744}
# 	1:25:IDD_DIALOG_TEXTURE_TUNNEL:126
# 	2:16:Resource Include:resource.h
# 	2:17:TextureTunnel.cpp:TextureTunnel.cpp
# 	2:25:IDD_DIALOG_TEXTURE_TUNNEL:IDD_DIALOG_TEXTURE_TUNNEL
# 	2:15:TextureTunnel.h:TextureTunnel.h
# 	2:10:ENUM: enum:enum
# 	2:21:CLASS: CTextureTunnel:CTextureTunnel
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {4E3BD46D-6FF3-4500-B17A-34EDB0C3F3C8}
# 	1:21:IDD_DIALOG_TEXTURE_BP:122
# 	2:16:Resource Include:resource.h
# 	2:21:IDD_DIALOG_TEXTURE_BP:IDD_DIALOG_TEXTURE_BP
# 	2:10:ENUM: enum:enum
# 	2:11:TextureBP.h:TextureBP.h
# 	2:13:TextureBP.cpp:TextureBP.cpp
# 	2:19:Application Include:T3DSystem.h
# 	2:17:CLASS: CTextureBP:CTextureBP
# End Section
# Section T3DSystem : {ACBB41AC-21BF-418E-B4E2-7C5EEB4D12ED}
# 	1:16:IDD_DIALOG_ZDM_R:114
# 	2:16:Resource Include:resource.h
# 	2:16:IDD_DIALOG_ZDM_R:IDD_DIALOG_ZDM_R
# 	2:6:ZdmR.h:ZdmR.h
# 	2:10:ENUM: enum:enum
# 	2:12:CLASS: CZdmR:CZdmR
# 	2:8:ZdmR.cpp:ZdmR.cpp
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {9632558F-DB6A-430C-8825-5B314D790267}
# 	1:18:IDD_DIALOG_GZWNAME:119
# 	2:16:Resource Include:resource.h
# 	2:17:DialogInputData.h:DialogInputData.h
# 	2:10:ENUM: enum:enum
# 	2:19:Application Include:T3DSystem.h
# 	2:18:IDD_DIALOG_GZWNAME:IDD_DIALOG_GZWNAME
# 	2:23:CLASS: CDialogInputData:CDialogInputData
# 	2:19:DialogInputData.cpp:DialogInputData.cpp
# End Section
# Section T3DSystem : {7BF80981-BF32-101A-8BBB-00AA00300CAB}
# 	2:5:Class:CPicture
# 	2:10:HeaderFile:picture.h
# 	2:8:ImplFile:picture.cpp
# End Section
# Section T3DSystem : {FA0CEA61-7BDD-48AC-B4FB-85A063BFC1B8}
# 	1:20:IDD_DIALOG_BRIDGESET:121
# 	2:16:Resource Include:resource.h
# 	2:13:BridgeSet.cpp:BridgeSet.cpp
# 	2:10:ENUM: enum:enum
# 	2:17:CLASS: CBridgeSet:CBridgeSet
# 	2:20:IDD_DIALOG_BRIDGESET:IDD_DIALOG_BRIDGESET
# 	2:19:Application Include:T3DSystem.h
# 	2:11:BridgeSet.h:BridgeSet.h
# End Section
# Section T3DSystem : {F7036E8D-AE09-43BE-A5B6-D809EBB2000F}
# 	1:29:IDD_DIALOG_RECORDPICTURESPEED:132
# 	2:16:Resource Include:resource.h
# 	2:29:IDD_DIALOG_RECORDPICTURESPEED:IDD_DIALOG_RECORDPICTURESPEED
# 	2:22:RecordPictureSpeed.cpp:RecordPictureSpeed.cpp
# 	2:10:ENUM: enum:enum
# 	2:20:RecordPictureSpeed.h:RecordPictureSpeed.h
# 	2:26:CLASS: CRecordPictureSpeed:CRecordPictureSpeed
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {77277D7A-153C-4B73-A936-DB0CD62561C5}
# 	1:21:IDD_DIALOG_NEWPROJECT:104
# 	2:16:Resource Include:resource.h
# 	2:14:NewProject.cpp:NewProject.cpp
# 	2:21:IDD_DIALOG_NEWPROJECT:IDD_DIALOG_NEWPROJECT
# 	2:18:CLASS: CNewProject:CNewProject
# 	2:12:NewProject.h:NewProject.h
# 	2:10:ENUM: enum:enum
# 	2:19:Application Include:T3DSystem.h
# End Section
# Section T3DSystem : {583A8103-2A0E-474E-9799-8D878B80BB27}
# 	1:17:IDD_DIALOG_BRIDGE:118
# 	2:16:Resource Include:resource.h
# 	2:12:BridgeData.h:BridgeData.h
# 	2:14:BridgeData.cpp:BridgeData.cpp
# 	2:10:ENUM: enum:enum
# 	2:18:CLASS: CBridgeData:CBridgeData
# 	2:19:Application Include:T3DSystem.h
# 	2:17:IDD_DIALOG_BRIDGE:IDD_DIALOG_BRIDGE
# End Section
# Section T3DSystem : {4916E66B-8CF6-4E8F-AC4E-79617EC0E84A}
# 	1:28:IDD_DIALOG_TEXTURE_BRIDGEHPM:124
# 	2:16:Resource Include:resource.h
# 	2:28:IDD_DIALOG_TEXTURE_BRIDGEHPM:IDD_DIALOG_TEXTURE_BRIDGEHPM
# 	2:14:TextureQLHpm.h:TextureQLHpm.h
# 	2:10:ENUM: enum:enum
# 	2:16:TextureQLHpm.cpp:TextureQLHpm.cpp
# 	2:20:CLASS: CTextureQLHpm:CTextureQLHpm
# 	2:19:Application Include:T3DSystem.h
# End Section
