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
 * JavaScript objects DeviceInfo. 
 */

function showDeviceInfotest() {
    var DeviceInfotest = document.getElementById("DeviceInfotest");
    DeviceInfotest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_DeviceInfo()
{
    var DeviceInfotest = document.getElementById("DeviceInfotest");
    DeviceInfotest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function clickItphoneColorDepthDefault() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneColorDepthDefault";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the color depth of the primary screen in its primary mode of operation. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneColorDepthDefault;";
    flag = "phoneColorDepthDefaultAction";
}

function phoneColorDepthDefaultAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneColorDepthDefault;
}

function clickItphoneFirmware() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneFirmware";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the firmware version for the operating system. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneFirmware;";
    flag = "phoneFirmwareAction";
}

function phoneFirmwareAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneFirmware;
}

function clickItphoneManufacturer() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneManufacturer";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the manufacturer of the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneManufacturer;";
    flag = "phoneManufacturerAction";
}

function phoneManufacturerAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneManufacturer;
}

function clickItphoneModel() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneModel";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the model of the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneModel;";
    flag = "phoneModelAction";
}

function phoneModelAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneModel;
}

function clickItphoneOS() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneOS";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the operating system of the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneOS;";
    flag = "phoneOSAction";
}

function phoneOSAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneOS;
}

function clickItphoneSoftware() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phoneSoftware";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the software version of the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneSoftware;";
    flag = "phoneSoftwareAction";
}

function phoneSoftwareAction() 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneSoftware;
}

function clickItphonescreenHeightDefault() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phonescreenHeightDefault";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the pixel height of the primary screen in its primary mode of operation.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneScreenHeightDefault;";
    flag = "phonescreenHeightDefaultAction";
}

function phonescreenHeightDefaultAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneScreenHeightDefault;
}

function clickItphonescreenWidthDefault() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field phonescreenWidthDefault";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the pixel width of the primary screen in its primary mode of operation. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.phoneScreenWidthDefault;";
    flag = "phonescreenWidthDefaultAction";
}

function phonescreenWidthDefaultAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.phoneScreenWidthDefault;
}

function clickIttotalMemory() {
    showDetail_DeviceInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field totalMemory";
    document.getElementById("textdivshowExplanation").innerHTML = "This is the total amount of RAM in kilobytes installed on the phone. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.DeviceInfo.totalMemory;";
    flag = "totalMemoryAction";
}

function totalMemoryAction() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.DeviceInfo.totalMemory;
}
