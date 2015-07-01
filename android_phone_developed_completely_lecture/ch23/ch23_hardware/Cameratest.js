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
 * JavaScript objects DataNetworkInfo. 
 */

function showCameratest() {
    var Cameratest = document.getElementById("Hardwaretest");
    Cameratest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_Camera()
{
    var Cameratest = document.getElementById("Hardwaretest");
    Cameratest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block"; 
}
function CamerasetWindow()
{

    document.getElementById("forCameraInfo").innerHTML = " Method Camera <I>setWindow(Div)</I>:<br><font color=rgb(0,0,255)>This method attaches the camera to a DOM object which specifies the preview window.</font><br>E.g.:<br> var Div = document.getElementById(\"CameraDiv\");<br>var mCamera = Widget.Multimedia.Camera;<br>mCamera.setWindow(Div);<font color=rgb(255,0,0)><br>Notice : The size of Preview Window should larger than 32(pixel) * 24(pixel)";

    var Div = document.getElementById("CameraDiv");
    var mCamera = Widget.Multimedia.Camera;
    mCamera.setWindow(Div);
}

function captureImage() {

document.getElementById("forCameraInfo").innerHTML = " Method Camera <I>captureImage (file)</I>:<br><font color=rgb(0,0,255)>Capture image.</font><br>E.g.:<br> var Div = document.getElementById(\"CameraDiv\");<br>Widget.Multimedia.Camera<br>.onCameraCaptured = function(fullpath){};<br>Widget.Multimedia.Camera<br>.captureImage(\"/sdcard/Sample.jpg\",true);<font color=rgb(255,0,0)><br>Notice : Makesure the sdcard is exist in the phone";

Widget.Multimedia.Camera.onCameraCaptured = function(fullpath) {
	var cInfo = document.getElementById("forCameraInfo");
        cInfo.innerHTML =cInfo.innerHTML + "<br>"+"captured image fullpath: "
                + fullpath;
    };
    Widget.Multimedia.Camera.captureImage("/sdcard/Sample.jpg", true);
   	var cInfo = document.getElementById("forCameraInfo");
        cInfo.innerHTML =cInfo.innerHTML +"<br>"+"Status: Start Imge capture!";

    
}



function startVideoCapture() {

document.getElementById("forCameraInfo").innerHTML = " Method Camera <I>startVideoCapture (fileName,lowRes,maxDurationSeconds,<br>showDefaultControls)</I>:<br><font color=rgb(0,0,255)>Start video capture</font><br>E.g.:<br> Widget.Multimedia.Camera<br>.startVideoCapture(\"/sdcard/Sample.3gp\", false, 30, true);<font color=rgb(255,0,0)><br>Notice : Makesure the sdcard is exist in the phone";

Widget.Multimedia.Camera.onCameraCaptured = function(fullpath) {
        var cInfo = document.getElementById("forCameraInfo");
        cInfo.innerHTML =cInfo.innerHTML + "<br>"+ "captured Video fullpath: "
                + fullpath;
    };
    Widget.Multimedia.Camera.startVideoCapture("/sdcard/Sample.3gp", false, 30, true);
   var cInfo = document.getElementById("forCameraInfo");
        cInfo.innerHTML =cInfo.innerHTML +"<br>"+ "Status: Start video capture!";


}


function stopVideoCapture() {

document.getElementById("forCameraInfo").innerHTML = " Method Camera <I>stopVideoCapture()</I>:<br><font color=rgb(0,0,255)>Explicitly stop a video capture in process.</font><br>E.g.:<br> Widget.Multimedia.Camera<br>.stopVideoCapture();<font color=rgb(255,0,0)><br>Notice : None";
Widget.Multimedia.Camera.stopVideoCapture();

    
}


function cameracapturedcallback(fullpath)
{
    var cInfo = document.getElementById("forCameraInfo");
        cInfo.innerHTML =cInfo.innerHTML +"<br>"+ "captured Video fullpath: "
                + fullpath;
}

function clickonCameraCaptured()
{
    document.getElementById("forCameraInfo").innerHTML = " Method Camera <I>onCameraCaptured()</I>:<br><font color=rgb(0,0,255)>Callback function when the camera stops capturing images or video.</font><br>E.g.:<br> Widget.Multimedia.Camera<br>.onCameraCaptured = cameraCapturedFun;<font color=rgb(255,0,0)><br>Notice : cameraCapturedFun is a callback function.";

Widget.Multimedia.Camera.onCameraCaptured = cameracapturedcallback;

}

