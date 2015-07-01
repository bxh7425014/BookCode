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
 * JavaScript objects DeviceStateInfo. 
 */

function showDeviceStateInfotest() {
    var DeviceStateInfotest = document.getElementById("DeviceStateInfotest");
    DeviceStateInfotest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

/*
 * This function used to show the UI of testing the Config class
 */
function clickshowAccelerometerInfo() 
{
    showAccelerometerInfo();
}

function clickItshowConfigtest() {
    showConfigtest();
}

function showDetail_DeviceStateInfo()
{
    var DeviceStateInfotest = document.getElementById("DeviceStateInfotest");
    DeviceStateInfotest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function clicItklanguage() 
{
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field language";
    document.getElementById("textdivshowExplanation").innerHTML = "The language that is currently in use on the handset. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var language = Widget.Device.DeviceStateInfo.language;";
    flag = "languageAction";
}

function languageAction() 
{
    var la = Widget.Device.DeviceStateInfo.language;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + la;
}

function clickDeviceStateInfoAP(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field audioPath";
    document.getElementById("textdivshowExplanation").innerHTML = "This property indicates where audio is currently configured for output.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var audiopath = Widget.Device.DeviceStateInfo.audioPath;";
    flag = "getAudioPathAction";
}

function getAudioPathAction(){
    var la =Widget.Device.DeviceStateInfo.audioPath;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + la;
}

function clickDeviceStateInfoPUP(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field processorUtilizationPercent";
    document.getElementById("textdivshowExplanation").innerHTML = "Notice : This is the % utilization of the phone's processor.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var la =Widget.Device.DeviceStateInfo.processorUtilizationPercent;";
    flag = "getPUPAction";
}

function getPUPAction(){
    var la =Widget.Device.DeviceStateInfo.processorUtilizationPercent;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + la;
}


function clickDeviceStateInfobackLightOn(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field backLightOn";
    document.getElementById("textdivshowExplanation").innerHTML = "Notice :A Read-Only property indicating whether the Back light is on or off. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var backLightOn = Widget.Device.DeviceStateInfo.backLightOn;";
    flag = "getbackLightOnAction";
}

function getbackLightOnAction(){
    var backLightOn = Widget.Device.DeviceStateInfo.backLightOn;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + backLightOn;
}

function clickDeviceStateInfoAccelerometerInfo(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field AccelerometerInfo";
    document.getElementById("textdivshowExplanation").innerHTML = "Notice : The accelerometer information of the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var accelerometerInfo = Widget.Device.DeviceStateInfo.AccelerometerInfo;";
    flag = "getAccelerometerInfoAction";
}

function getAccelerometerInfoAction(){
    var accelerometerInfo = Widget.Device.DeviceStateInfo.AccelerometerInfo;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + accelerometerInfo.xAxis  +":"+ accelerometerInfo.yAxis  +":"+ accelerometerInfo.zAxis;
}

function clickDeviceStateInfoavailableMemory(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field availableMemory";
    document.getElementById("textdivshowExplanation").innerHTML = "Notice : The amount of RAM, in Kb, that is available on the phone. This field should be a double value. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var available = Widget.Device.DeviceStateInfo.availableMemory;";
    flag = "getavailableMemoryAction";
}

function getavailableMemoryAction(){
    var available = Widget.Device.DeviceStateInfo.availableMemory;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + available;
}

function clickDeviceStateInfokeypadLightOn(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field keypadLightOn";
    document.getElementById("textdivshowExplanation").innerHTML = "Notice :A Read-Only property indicating whether the keypad light is on or off. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var key = Widget.Device.DeviceStateInfo.keypadLightOn;";
    flag = "getkeypadLightOnAction";
}

function getkeypadLightOnAction(){
    var key = Widget.Device.DeviceStateInfo.keypadLightOn;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + key;
}

function clickDeviceStateInfoOnScreenChange(){
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onScreenChangeDimensions";
    document.getElementById("textdivshowExplanation").innerHTML = "This callback function is invoked when the phone's display changes characteristics. E.g., this would be called if the phone were to switch from landscape to portrait mode.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need event to happen and test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.DeviceStateInfo.\nonScreenChangeDimensions=callback;\n"+
                            "function callback(width, height)\n"+
                            "{document.getElementById(textdivshowResult)\n.innerHTML = width +: + height;};";
    flag = "screenChangeListener";
}

function screenChangeListener(){
    Widget.Device.DeviceStateInfo.onScreenChangeDimensions=callback;
    function callback(width, height)
    {
        document.getElementById("textdivshowResult").innerHTML = width +":" + height;
    }
}

function clickItavailableMemory() {
    showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field availableMemory";
    document.getElementById("textdivshowExplanation").innerHTML = "The amount of RAM, in Kb, that is available on the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var available = Widget.Device.DeviceStateInfo.availableMemory;";
    flag = "availableMemoryAction";
}

function availableMemoryAction() {
    var available = Widget.Device.DeviceStateInfo.availableMemory;
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + available;
}

function clickOnPositionRetrieved()
{
	showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onPositionRetrieved";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a callback function that is invoked as a response to the requestLocationInfo() method.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : No object will be returned if the location could not be identified.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.DeviceStateInfo.onPositionRetrieved=callback; Widget.Device.DeviceStateInfo.requestPositionInfo(\"agps\"); function callback(position, method){var result = position.latitude+ position.longitude+ position.altitude+ position.cellID+ position.accuracy+ position.method+ position.timeStamp;} ";
    flag = "onPositionRetrievedAction";
}

function onPositionRetrievedAction()
{
	Widget.Device.DeviceStateInfo.onPositionRetrieved=callback;
	Widget.Device.DeviceStateInfo.requestPositionInfo("gps");
}

function clickRequestPositionInfo()
{
	showDetail_DeviceStateInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method requestPositionInfo";
    document.getElementById("textdivshowExplanation").innerHTML = "This issues an asynchronous request for the phone's location.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : This is an asynchronous call. The widget must register with the onPositionRetrieved callback method in order to receive the position information once it is available.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.DeviceStateInfo.onPositionRetrieved=callback; Widget.Device.DeviceStateInfo.requestPositionInfo(\"agps\"); function callback(position, method){var result = position.latitude+ position.longitude+ position.altitude+ position.cellID+ position.accuracy+ position.method+ position.timeStamp;} ";
    flag = "requestPositionInfoAction";
}

function requestPositionInfoAction()
{
	Widget.Device.DeviceStateInfo.onPositionRetrieved=callback;
	Widget.Device.DeviceStateInfo.requestPositionInfo("gps");
}

function callback(position, method) {
    m_positioninfo = position;
    m_method = method;
    document.getElementById("textdivshowResult").innerHTML = "Result : latitude:"
            + position.latitude
            + ", longitude:"
            + position.longitude
            + ", altitude:"
            + position.altitude
            + ", cellID:"
            + position.cellID
            + ", accuracy:"
            + position.accuracy
            + ", method:"
            + method
            + ", timeStamp:" + position.timeStamp;
}