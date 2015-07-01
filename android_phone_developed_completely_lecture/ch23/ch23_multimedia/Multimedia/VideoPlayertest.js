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
 * JavaScript objects Telephony. 
 */

var videoPlayerVariable = null;

function showVideoPlayertest() {
    var VideoPlayertest = document.getElementById("VideoPlayertest");
    VideoPlayertest.style.display = "block";
    var Detail = document.getElementById("showDetail");
    Detail.style.display = "none";
}

function videoplayerOpen() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>open (URL)</I>:<br><font color=rgb(0,0,255)>Open Video file from URL resource</font><br>E.g.:<br> var pv = document.getElementById(\"PDiv\");<br>vp= Widget.Multimedia.VideoPlayer(pv);<br>vp.open(\"test.mp4\");<br><font color=rgb(255,0,0)>Notice : Make sure the URL resource is exist </font>";
    var playdiv = document.getElementById("forPlay");
    videoPlayerVariable = Widget.Multimedia.VideoPlayer;

    videoPlayerVariable.onStateChange = stateChange;
    videoPlayerVariable.open("/test.mp4");
    document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the file is opened succesfully.";
}

function videoplayerPause() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>pause()</I>:<br><font color=rgb(0,0,255)>Pause the video which is played by play function.</font><br>E.g.:<br> videoPlayerVariable.pause();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.pause();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is paused succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function clickPauseVideo() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>pause()</I>:<br><font color=rgb(0,0,255)>Pause the video which is played by play function.</font><br>E.g.:<br> videoPlayerVariable.pause();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.pause();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is paused succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function videoplayerResume() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>resume()</I>:<br><font color=rgb(0,0,255)>Resume the video which is paused by pause function.</font><br>E.g.:<br> videoPlayerVariable.resume();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.resume();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is resumed succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function videoplayerPlay() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>play(repeatTimes)</I>:<br><font color=rgb(0,0,255)>Play the video which is opened by open function.</font><br>E.g.:<br> videoPlayerVariable.play(3);<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.play(3);
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the video file is played succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function clickResumeVideo() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>resume()</I>:<br><font color=rgb(0,0,255)>Resume the video which is paused by pause function.</font><br>E.g.:<br> videoPlayerVariable.resume();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.resume();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is resumed succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function videoplayerOnStatus() {
    document.getElementById("forInfo").innerHTML = " Field VideoPlayer <I>onStateChange</I>:<br><font color=rgb(0,0,255)>Call back method to invoke when the state of the video player changes.</font><br>E.g.:<br> vp.onStateChange = stateChangeFun;<br><font color=rgb(255,0,0)>Notice : The <I>vp</I> is a videoplayer object which comes from <I>open </I>function and <I>stateChangeFun</I> is a callback function.</font>";
    document.getElementById("videoresult").innerHTML = "In this Sample widget, callback function will be set when <I>open</I> button is clicked.";
}

function clickStopVideo() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>stop()</I>:<br><font color=rgb(0,0,255)>Stop the video which is played by playing function.</font><br>E.g.:<br> videoPlayerVariable.stop();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";

    if (videoPlayerVariable != null) {
        videoPlayerVariable.stop();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is stoped succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}

function videoplayerStop() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>stop()</I>:<br><font color=rgb(0,0,255)>Stop the video which is played by playing function.</font><br>E.g.:<br> videoPlayerVariable.stop();<br><font color=rgb(255,0,0)>Notice : The <I>videoPlayerVariable</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        videoPlayerVariable.stop();
        document.getElementById("videoresult").innerHTML = "Please observer below label to verify if the videoplayer is stoped succesfully.";
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}
/*
function videoplayerSetSize() {
    document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>setScreenSize(width,height)</I>:<br><font color=rgb(0,0,255)>Set the Video screen size, this function should be called after open function.</font><br>E.g.:<br> vp.setScreenSize(100,120);<br><font color=rgb(255,0,0)>Notice : The <I>vp</I> is a videoplayer object which comes from <I>open </I>function.</font>";
    if (videoPlayerVariable != null) {
        var _width = document.getElementById("videowidthlable").value;
        var _height = document.getElementById("videoheightlable").value;
        if (isNotEmpty(_height) && isNotEmpty(_width)) {
            if (isNumber(_height) && isNumber(_width)) {
                videoPlayerVariable.setScreenSize(parseInt(_width),
                        parseInt(_height));
                document.getElementById("videoresult").innerHTML = "Set screen size, width: "
                        + _width
                        + "height: "
                        + _height
                        + ", but videoplayer will adjust these numbers to more reasonable";
            } else {
                document.getElementById("videoresult").innerHTML = "Please input correct type of value(number)";
            }
        } else {
            document.getElementById("videoresult").innerHTML = "Please input correct type of value(number)";
        }
    } else {
        document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
    }
}
*/
function videoplayerSetwindow() 
{
	document.getElementById("forInfo").innerHTML = " Method VideoPlayer <I>setWindow(DivObject)</I>:<br><font color=rgb(0,0,255)>This method attaches the player to a DOM object which specifites the video playing window.</font><br>E.g.:<br> vp.setWindow(div);<br><font color=rgb(255,0,0)>Notice : The <I>vp</I> is a videoplayer object which comes from <I>open </I>function.</font><br>The <I>div</I> is a Div object.";
    if (videoPlayerVariable != null) {
 		var playdiv = document.getElementById("forPlay");
      	   	videoPlayerVariable.setWindow(playdiv);
                document.getElementById("videoresult").innerHTML = "Set Window to a Div";

}
else
{
	document.getElementById("videoresult").innerHTML = "Please click <I>open</I> button frist.";
}
}
function stateChange(state) {
    document.getElementById("videocurrentstatus").innerHTML = "Current player state is "
            + state;
}

function videogosurface()
{
    if(videoPlayerVariable !=null)
    {
        videoPlayerVariable.stop();
        //videoPlayerVariable.setScreenSize(0,0);      
    }
    location.replace("../main.html");
}

function clickItbackVideoPlayer(){
    if(videoPlayerVariable !=null)
    {
        videoPlayerVariable.stop();
       // videoPlayerVariable.setScreenSize(0,0);        
    }
   location.replace("Multimedia.html"); 
}
