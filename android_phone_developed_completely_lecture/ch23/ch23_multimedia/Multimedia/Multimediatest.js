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
 * JavaScript objects Multimedia. 
 */

function showMultimediatest() {
    var Multimediatest = document.getElementById("Multimediatest");
    Multimediatest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_Multimedia() {
    var Multimediatest = document.getElementById("Multimediatest");
    Multimediatest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}
 
function clickItMultimediagetVolume() {
    showDetail_Multimedia();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Multimedia getVolume()";
    document.getElementById("textdivshowExplanation").innerHTML = "Get audio volume from 0 to 15, where 0 is off. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v=Widget.Multimedia.getVolume();";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickMultimediagetVolume";
}

function clickMultimediagetVolume() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "Current volume: " + Widget.Multimedia.getVolume();
}


function clickIttextdivisVideoPlaying() {
    showDetail_Multimedia();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field Multimedia isVideoPlaying";
    document.getElementById("textdivshowExplanation").innerHTML = "The flag if there is any video playing. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: if(Widget.Multimedia.isVideoPlaying)<br>document.getElementById(\"videoStates\")<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.innerHTML =\"Video is playing\";";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";    
    flag = "clicktextdivisVideoPlaying";
}

function clicktextdivisVideoPlaying() {
        if (Widget.Multimedia.isVideoPlaying)
        document.getElementById("textdivshowResult").innerHTML = "Result : "
                + "Video is playing ";
    else
        document.getElementById("textdivshowResult").innerHTML = "Result : "
                + "Video is not playing";
}

function clickItMultimediastopall() {
    showDetail_Multimedia();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Multimedia stopAll()";
    document.getElementById("textdivshowExplanation").innerHTML = "Stop all audio and video play. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.stopAll();";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickMultimediastopall";
}

function clickMultimediastopall() {
    Widget.Multimedia.stopAll();
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + "Stop all audio and video play";
}

function clickIttextdivisAudioPlaying() {
    showDetail_Multimedia();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field Multimedia isAudioPlaying";
    document.getElementById("textdivshowExplanation").innerHTML = "The flag if there is any audio playing. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: if(Widget.Multimedia.isAudioPlaying)<br>Widget.Multimedia.AudioPlayer.pause();";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clicktextdivisAudioPlaying";
}

function clicktextdivisAudioPlaying() {
    if (Widget.Multimedia.isAudioPlaying)
        document.getElementById("textdivshowResult").innerHTML = "Result : " + "Audio is playing ";
    else
        document.getElementById("textdivshowResult").innerHTML = "Result : " + "Audio is not playing";
}
