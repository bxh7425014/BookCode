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
 * JavaScript objects RadioInfo. 
 */

function showRadioInfotest() {
    var RadioInfotest = document.getElementById("RadioInfotest");
    RadioInfotest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_RadioInfo()
{
    var RadioInfotest = document.getElementById("RadioInfotest");
    RadioInfotest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function clickItisRoaming() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field isRoaming";
    document.getElementById("textdivshowExplanation").innerHTML = "This field indicates whether the phone is roaming.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var ret = Widget.Device.RadioInfo.isRoaming;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "isRoamingAction";
}

function isRoamingAction() {
    var ret = Widget.Device.RadioInfo.isRoaming;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + ret;
}

function clickItradioSignalSource() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field radioSignalSource";
    document.getElementById("textdivshowExplanation").innerHTML = "This field indicates the radio signal source.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var ret = Widget.Device.RadioInfo.radioSignalSource;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "radioSignalSourceAction";
}

function radioSignalSourceAction() {
    var ret = Widget.Device.RadioInfo.radioSignalSource;    
    document.getElementById("textdivshowResult").innerHTML = "Result : " + ret;
}

function clickItradioSignalStrengthPercent() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field radioSignalStrengthPercent";
    document.getElementById("textdivshowExplanation").innerHTML = "This field indicates the percentage of signal strength.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var ret = Widget.Device.RadioInfo.radioSignalStrengthPercent;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "radioSignalStrengthPercentAction";
}

function radioSignalStrengthPercentAction() {
    var ret = Widget.Device.RadioInfo.radioSignalStrengthPercent;
    document.getElementById("textdivshowResult").innerHTML = "Result : " + ret;
}

function clickRadioInfo4() 
{
    Widget.Device.RadioInfo.onSignalStrengthChange = callback4;
}

function clickItRadioInfo4() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onSignalStrengthChange";
    document.getElementById("textdivshowExplanation").innerHTML = "Call back method to invoke when the signal strength changes .";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Device.RadioInfo.onSignalStrengthChange = callback; function callback(percent){};";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickRadioInfo4";
}

function callback4(percent) 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + percent;
}

function clickItisRadioEnabled() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method isRadioEnabled";
    document.getElementById("textdivshowExplanation").innerHTML = "Explanation : This field indicates whether the phone is allowed to connect to radio network.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.:Widget.Device.RadioInfo.isRadioEnabled;a";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "isRadioEnabledAction";
}

function isRadioEnabledAction() {
    var ret = Widget.Device.RadioInfo.isRadioEnabled;    
    document.getElementById("textdivshowResult").innerHTML = "Result: "+ret;
}

function clickItonSignalSourceChange() {
    showDetail_RadioInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onSignalSourceChange";
    document.getElementById("textdivshowExplanation").innerHTML = "Explanation : Call back method to invoke when the signal source changes";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.:Widget.Device.RadioInfo.onSignalSourceChange = callback; function callback(signalSource, isroaming){};";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need event to happen and test on phone.";
    flag = "onSignalSourceChangeAction";
}

function onSignalSourceChangeAction() 
{
    Widget.Device.RadioInfo.onSignalSourceChange = callback5;
}

function callback5(signalSource, isroaming) 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "+ signalSource+":"+isroaming;
}