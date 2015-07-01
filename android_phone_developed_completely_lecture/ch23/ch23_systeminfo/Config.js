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
 * @fileoverview: This file is to be used for testing all functions and properties of 
 * JavaScript objects Config. 
 */
function showConfigtest() {
    var Configtest = document.getElementById("DeviceStateInfotest");
    Configtest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";     
}

function showDetail_Config()
{
    var Configtest = document.getElementById("DeviceStateInfotest");
    Configtest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";     
}
/*
 * This function used to test the method setDefaultRingtone. This method set
 * Default Ringtone.
 */
function clickItsetDefaultRingtone() {
    showDetail_Config();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method setDefaultRingtone";
    document.getElementById("textdivshowExplanation").innerHTML = "Set Default Ringtone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the song.mp3 is exist in the correct path, and test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceStateInfo.Config.setDefaultRingtone(\"/sdcard/test/song.mp3\"); ";
    flag = "clicksetDefaultRingtone";
}
 
function clicksetDefaultRingtone() {
    var ringtoneFileurl = "/sdcard/test/song.mp3";
    Widget.Device.DeviceStateInfo.Config.setDefaultRingtone(ringtoneFileurl);
    document.getElementById("textdivshowResult").innerHTML = "Finished";
}

/*
 * This function used to test the method setAsWallpaper. This method set a
 * picture as Wallpaper.
 */
function clickItsetAsWallpaper() {
    showDetail_Config();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method setAsWallpaper";
    document.getElementById("textdivshowExplanation").innerHTML = "Set a picture as Wallpaper. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the picture.png is exist in the correct path, and test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceStateInfo.Config.setAsWallpaper(\"/sdcard/test/picture.png\");";
    flag = "clicksetAsWallpaper";
}
 
function clicksetAsWallpaper() {
    var filePath = "/sdcard/test/picture.png";
    
    Widget.Device.DeviceStateInfo.Config.setAsWallpaper(filePath);
    document.getElementById("textdivshowResult").innerHTML = "Finished";
}

/*
 * This function used to test the field msgRingtoneVolume. The volume of the
 * ringtone to be played when a new message arrives, which takes the value from
 * 0 to 10, where 0 is off.
 */

function clickItmsgRingtoneVolume() {
    showDetail_Config();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field msgRingtoneVolume";
    document.getElementById("textdivshowExplanation").innerHTML = " The volume of the ringtone to be played when a new message arrives, which takes the value from 0 to 7, where 0 is off.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceStateInfo.Config.msgRingtoneVolume;";
    flag = "clickmsgRingtoneVolume";
}

function clickmsgRingtoneVolume() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceStateInfo.Config.msgRingtoneVolume ;
}

/*
 * This function used to test the field ringtoneVolume. The volume of the
 * ringtone, which takes the value from 0 to 10, where 0 is off.
 */
function clickItringtoneVolume() {
    showDetail_Config();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field ringtoneVolume";
    document.getElementById("textdivshowExplanation").innerHTML = " The volume of the ringtone, which takes the value from 0 to 7, where 0 is off.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceStateInfo.Config.ringtoneVolume;";
    flag = "clickringtoneVolume";
}

function clickringtoneVolume() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceStateInfo.Config.ringtoneVolume;
}

/*
 * This function used to test the field vibration. The status indicating whether
 * the vibration is set or not. Possible values are "ON" and "OFF".
 */
function clickItvibration() {
    showDetail_Config();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field vibrationSetting";
    document.getElementById("textdivshowExplanation").innerHTML = "The status indicating whether the vibration is set or not.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceStateInfo.Config.vibrationSetting;";
    flag = "clickvibration";
}

function clickvibration() 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceStateInfo.Config.vibrationSetting;
}
