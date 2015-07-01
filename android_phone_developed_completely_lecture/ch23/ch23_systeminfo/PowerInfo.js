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
 * JavaScript objects PowerInfo. 
 */

function showPowerInfotest() {
    var PowerInfotest = document.getElementById("PowerInfotest");
    PowerInfotest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_PowerInfo()
{
    var PowerInfotest = document.getElementById("PowerInfotest");
    PowerInfotest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function isChargingAction() {
    var charging = Widget.Device.PowerInfo.isCharging;
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + charging;
}

function clickItisCharging(){
    showDetail_PowerInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field isCharging";
    document.getElementById("textdivshowExplanation").innerHTML = "This field indicates whether the phone is now charging.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var charging = Widget.Device.PowerInfo.isCharging;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "isChargingAction";

}

function percentRemainingAction() {
    var remaining = Widget.Device.PowerInfo.percentRemaining;
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + remaining;
}

function clickpercentRemaining() {
    showDetail_PowerInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field percentRemaining";
    document.getElementById("textdivshowExplanation").innerHTML = "This field indicates the percentage of battery power remaining.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var remaining = Widget.Device.PowerInfo.percentRemaining;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "percentRemainingAction";
}

function clickItonChargeStateChange() {
    showDetail_PowerInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onChargeState\n-Change";
    document.getElementById("textdivshowExplanation").innerHTML = "Call back method to invoke when the charge state changes.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.PowerInfo.onChargeStateChange = callback ; function callback(state){}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need event to happen and test on phone.";
    flag = "onChargeStateChangeAction";
}

function onChargeStateChangeAction() 
{
    Widget.Device.PowerInfo.onChargeStateChange = callbackDeviceInfoPowerInfo4;
}

function callbackDeviceInfoPowerInfo4(state) {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + state;
}

function clickItonChargeLevelChange() {
    showDetail_PowerInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onChargeLevelChange";
    document.getElementById("textdivshowExplanation").innerHTML = "Callback method to invoke when the percentage of the remaining battery changes during the charging process.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.PowerInfo.onChargeLevelChange = callback ; function callback(newPercentageRemaining){}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need event to happen and test on phone.";
    flag = "onChargeLevelChangeAction";
}

function onChargeLevelChangeAction() 
{
    Widget.Device.PowerInfo.onChargeLevelChange = callbackDeviceInfoPowerInfo15;
}

function callbackDeviceInfoPowerInfo15(newPercentageRemaining) {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + newPercentageRemaining;
}

function clickItonLowBattery() {
    showDetail_PowerInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method  onLowBattery";
    document.getElementById("textdivshowExplanation").innerHTML = "Call back method to invoke when the battery is low .";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.PowerInfo.onLowBattery = callback ; function callback(percentRemaining){}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need event to happen and test on phone.";
    flag = "onLowBatteryAction";
}

function onLowBatteryAction() 
{
    Widget.Device.PowerInfo.onLowBattery = callbackDeviceInfoPowerInfo16;
}

function callbackDeviceInfoPowerInfo16(percentRemaining) {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + percentRemaining;
}

function init() 
{
    Widget.Device.RadioInfo.onSignalStrengthChange = callbackDeviceInfoPowerInfo4;
}