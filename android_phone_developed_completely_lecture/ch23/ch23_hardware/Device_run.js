/*
 * Copyright by JIL, 2009.  
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * @fileoverview: This file is set as the Widget of testing all classes of
 * JavaScript API.
 */
var flag = "";
function clickItResult(){
    switch (flag) {
	    case "clickshowwidgetEngineNametest":
	        clickshowwidgetEngineNametest();
	        break;
	    case "clickshowwidgetEngineProvidertest":
	        clickshowwidgetEngineProvidertest();
	        break;
	    case "clipboardStringAction":
	    	clipboardStringAction();
	        break;
	    case "clickcopyFile":
	        clickcopyFile();
	        break;
	    case "clickgetFile":
	    	clickgetFile();
	        break;
	    case "clickdeleteFile":
	        clickdeleteFile();
	        break;
	    case "clickfindFiles":
	        clickfindFiles();
	        break;
	    case "clickgetDirectoryFileNames":
	        clickgetDirectoryFileNames();
	        break;
	    case "clickgetFileSystemRoots":
	        clickgetFileSystemRoots();
	        break;
	    case "clickgetFileSystemSize":
	        clickgetFileSystemSize();
	        break;
	    case "vibrateAction":
	    	vibrateAction();
	        break;
	    case "clickmoveFile":
	        clickmoveFile();
	        break;
	    case "clickcreateFile":
	        clickcreateFile();
	        break;
	    case "clickcreateFolder":
	        clickcreateFolder();
	        break;
	    case "clickcopyFolder":
	        clickcopyFolder();
	        break;
	    case "clickdeleteFolder":
	        clickdeleteFolder();
	        break;
	    case "clickmoveFolder":
	        clickmoveFolder();
	        break;
	    case "clickgetAvailableApplications":
	        clickgetAvailableApplications();
	        break;
	    case "clicklaunchApplication":
	        clicklaunchApplication();
	        break;
	    case "clicksetRingtone":
	        clicksetRingtone();
	        break;
	    case "createDateAction":
	        createDateAction();
	        break;
	    case "fileNameAction":
	        fileNameAction();
	        break;
	    case "filePathAction":
	        filePathAction();
	        break;
	    case "fileSizeAction":
	        fileSizeAction();
	        break;
	    case "isDirectoryAction":
	        isDirectoryAction();
	        break;
	    case "lastModifyDateAction":
	        lastModifyDateAction();
	        break;
	    case "phoneOperatorNameAction":
	        phoneOperatorNameAction();
	        break;
	    case "phoneUserUniqueIdAction":
	        phoneUserUniqueIdAction();
	        break;
	    case "displayConnectNameAction":
	        displayConnectNameAction();
	        break;
	    case "getConnectionTypesAction":
	        getConnectionTypesAction();
	        break;
	    case "getNetWorkConnectNameAction":
	        getNetWorkConnectNameAction();
	        break;
	    case "isDataNetworkConnectedAction":
	        isDataNetworkConnectedAction();
	        break;
	    case "languageAction":
	        languageAction();
	        break;
	    case "getAudioPathAction":
	        getAudioPathAction();
	        break;
	    case "getPUPAction":
	        getPUPAction();
	        break;
	    case "getbackLightOnAction":
	        getbackLightOnAction();
	        break;
	    case "getavailableMemoryAction":
	        getavailableMemoryAction();
	        break;
	    case "getkeypadLightOnAction":
	        getkeypadLightOnAction();
	        break;
	    case "screenChangeListener":
	        screenChangeListener();
	        break;
	    case "availableMemoryAction":
	        availableMemoryAction();
	        break;
	    case "getx":
	        getx();
	        break;
	    case "gety":
	        gety();
	        break;
	    case "getz":
	        getz();
	        break;
	    case "clicksetDefaultRingtone":
	        clicksetDefaultRingtone();
	        break;
	    case "clicksetAsWallpaper":
	         clicksetAsWallpaper();
	        break;
	    case "clickmsgRingtoneVolume":
	        clickmsgRingtoneVolume();
	        break;
	    case "clickringtoneVolume":
	        clickringtoneVolume();
	        break;
	    case "clickvibration":
	        clickvibration();
	        break;
	    case "clicklatitude":
	        clicklatitude();
	        break;
	    case "clicklongitude":
	        clicklongitude();
	        break;
	    case "clickaltitude":
	        clickaltitude();
	        break;
	    case "clickcellID":
	        clickcellID();
	        break;
	    case "clickaccuracy":
	        clickaccuracy();
	        break;
	    case "altitudeAccuracyAction":
	    	altitudeAccuracyAction();
	        break;
	    case "clicktimeStamp":
	        clicktimeStamp();
	        break;
	    case "isRoamingAction":
	        isRoamingAction();
	        break;
	    case "radioSignalSourceAction":
	        radioSignalSourceAction();
	        break;
	    case "radioSignalStrengthPercentAction":
	        radioSignalStrengthPercentAction();
	        break;
	    case "clickRadioInfo4":
	    	clickRadioInfo4();
	        break;
	    case "onSignalSourceChangeAction":
	        onSignalSourceChangeAction();
	        break;
	    case "isRadioEnabledAction":
	        isRadioEnabledAction();
	        break;
	    case "isChargingAction":
	        isChargingAction();
	        break;
	    case "percentRemainingAction":
	        percentRemainingAction();
	        break;
	    case "onChargeStateChangeAction":
	        onChargeStateChangeAction();
	        break;
	    case "onChargeLevelChangeAction":
	        onChargeLevelChangeAction();
	        break;
	    case "onLowBatteryAction":
	        onLowBatteryAction();
		 break;
	    case "phoneColorDepthDefaultAction":
	    	phoneColorDepthDefaultAction();
 		break;	
	    case "phoneFirmwareAction":
	    	phoneFirmwareAction();
		 break;
	    case "phoneManufacturerAction":
	    	phoneManufacturerAction();
		 break;
	    case "phoneModelAction":
	    	phoneModelAction();
		 break;
	    case "phoneOSAction":
	    	phoneOSAction();
		 break;
	    case "phoneSoftwareAction":
	    	phoneSoftwareAction();
		 break;
	    case "phonescreenHeightDefaultAction":
	    	phonescreenHeightDefaultAction();
		 break;
	    case "phonescreenWidthDefaultAction":
	    	phonescreenWidthDefaultAction();
 		break; 
	    case "mobilePhoneAction":
	    	mobilePhoneAction();
 		break;
	    case "totalMemoryAction":
	    	totalMemoryAction();
	        break;
	    case "onPositionRetrievedAction":
	    	onPositionRetrievedAction();
	        break;
	    case "requestPositionInfoAction":
	    	requestPositionInfoAction();
	        break;
        default:
            break;
    }
}
