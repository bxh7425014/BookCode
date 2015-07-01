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

function showWidget() {
    var front = document.getElementById("Filetest");
    front.style.display = "block"; 
    var Detail = document.getElementById("showDetail");
    Detail.style.display = "none";
}

function clickItSurface() 
{
    showWidget();
}

function clickItResult() {
    switch (flag) {
    case "clickopenURL":
        clickopenURL();
        break;
    case "clicksetPreferenceForKey":
        clicksetPreferenceForKey();
        break;
    case "clickpreferenceForKey":
        clickpreferenceForKey();
        break;
    case "clickonRestoreFocus":
        clickonRestoreFocus();
        break;
    case "clickonFocus":
        clickonFocus(); 
        break;
    case "clickonMaximize":
        clickonMaximize();
        break;
    case "clickonRestore":
    	clickonRestore();
    	break;
    case "clickonWakeup":
        clickonWakeup();
    	break;
    default:
        break;
    }
}

function showDetail()
{
    var front = document.getElementById("Filetest");
    front.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}
 
function clickItopenURL() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method openURL";
    document.getElementById("textdivshowExplanation").innerHTML = "This method will launch the device browser implementation to the url spcified in the string argument .";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.openURL(\"http://www.jil.org\");";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :URL - to access, upon browser launch , URL must be exist.";
    flag = "clickopenURL";
}

/*
 * This function used to test the method openURL. This method will launch the
 * device browser implementation to the url spcified in the string argument
 */
function clickopenURL() {
    var testURL = "http://www.jil.org";
    Widget.openURL(testURL);
}

/*
 * This function used to test the method setPreferenceForKey. This method takes
 * two String arguments, preference and key. When called, this method will store
 * the string in the preference argument with the key named in the key argument
 * for later retrival using the preferenceForKey() method. If the
 * setPreferenceForKey() method is called with the value null in the prefence
 * argument, the key identified in the key argument will be deleted.
 */
function clicksetPreferenceForKey() {
    var key = "testkey";
    var preference = "testvalue";
    if (key != null && preference != null)
        Widget.setPreferenceForKey(key, preference);
        document.getElementById("textdivshowResult").innerHTML = "Result : " + Widget.preferenceForKey(key);
}

function clickItsetPreferenceForKey() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method setPreferenceForKey";
    document.getElementById("textdivshowExplanation").innerHTML = "This method takes two String arguments, preference and key. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.setPreferenceForKey(\"testkey\", \"testvalue\");";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :key - identifier of the preference<br>preference - string value of the preference ";
    flag = "clicksetPreferenceForKey";
}

/*
 * This function used to test the method preferenceForKey. This method takes a
 * String argument, Key. When called, this method will return a string that has
 * previously been stored with the setPrefernceForKey method, or undefined if
 * the key does not exist.
 */
function clickpreferenceForKey() {
    var ret = Widget.preferenceForKey("testkey");
    document.getElementById("textdivshowResult").innerHTML = "Result : " + ret;
}

function clickItpreferenceForKey() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method preferenceForKey";
    document.getElementById("textdivshowExplanation").innerHTML = "This method takes a String argument, Key. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var ret = Widget.preferenceForKey(\"testkey\"); ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :key - identifier of the preference ";
    flag = "clickpreferenceForKey";
}

/*
 * This function used to test the method onRestoreFocus. This method set Default
 * Ringtone.
 */
function clickonRestoreFocus() 
{
    Widget.onRestoreFocus = callback;
}

function clickItonRestoreFocus() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onRestoreFocus";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a call back method which will be triggered when the widget is restored to default display mode. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.onRestoreFocus = callback; function callback(){};";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :N/A  ";
    flag = "clickonRestoreFocus";
}

function callback() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "succeed";
}

function callbackonFocus() 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "onFocus";
}

function clickonFocus() 
{
    Widget.onFocus = callbackonFocus;
}

function clickItonFocus() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onFocus";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a call back method which will be triggered when the widget gains focus when multiple widgets are displayed on the same screen.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.:Widget.onFocus = callbackonFocus; function callbackonFocus(){document.write(\"the widget gains focus!\");}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :Need event to happen and test on phone. ";
    flag = "clickonFocus";
}

function callbackonMaximize() 
{
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "onMaximize";
}

function clickonMaximize() {
    Widget.onMaximize = callbackonMaximize;
}

function clickItonMaximize() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onMaximize";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a call back method which will be triggered when the widget is displayed in the maximized display mode. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.onMaximize = callbackonMaximize;  function callbackonMaximize(){document.write(\"the widget is displayed in the maximized mode.\");}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :Need event to happen and test on phone. ";
    flag = "clickonMaximize";
}

function callbackonRestore() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "onRestore";
}

function clickonRestore() {
    Widget.onRestore = callbackonRestore;
}

function clickItonRestore() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onRestore";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a call back method which will be triggered when the widget is restored to default display mode.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.onRestore = callbackonRestore;  function callbackonRestore(){document.write(\"The widget is restored to default display modle!\");}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :Need event to happen and test on phone. ";
    flag = "clickonRestore";
}

function callbackonWakeup() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "onWakeup";
}

function clickonWakeup() {
    Widget.onWakeup = callbackonWakeup;
}

function clickItonWakeup() {
    showDetail();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onWakeup";
    document.getElementById("textdivshowExplanation").innerHTML = "This is a call back method which will be called when the widget is triggered to wake up.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.onWakeup = callbackonWakeup; function callbackonWakeup(){the widget is triggered to wake up.}";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :Need event to happen and test on phone. ";
    flag = "clickonWakeup";
}
