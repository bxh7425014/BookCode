/*
 * Copyright by JIL, 2009. 
 * 
 * @fileoverview: This file is to be used for testing all functions and properties of 
 * JavaScript objects AudioPlayer.
 * @version 0.9
 */

function showAudioPlayertest() {
    var AudioPlayertest = document.getElementById("AudioPlayertest");
    AudioPlayertest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_AudioPlayer()
{
    var AudioPlayertest = document.getElementById("AudioPlayertest");
    AudioPlayertest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function clickItAPopen() {

    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Audio open (file)";
    document.getElementById("textdivshowExplanation").innerHTML = "Open Audio file from URL resource";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.open\n(\"song.mp3\");";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Make sure the song.mp3 is exist in the widget package";
    flag = "APopenAction";
}

function audiostateChangecback(state) 
{
    document.getElementById("textdivshowResult").innerHTML = "Audio player state is " + state;
}

function APopenAction() 
{
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    var fileUrl = "/song.mp3";
    if (isNotEmpty(fileUrl))
    Widget.Multimedia.AudioPlayer.open(fileUrl);
}  

function clickItAPplay() 
{
    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Audio play";
    document.getElementById("textdivshowExplanation").innerHTML = "Play the audio which is opened by open function. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.play(3);";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Before playing the audio,please run Method open(file) ";
    flag = "APplayAction";
}

function APplayAction() {
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    var repeatTimes = 3;
    if (isNotEmpty(repeatTimes)) {
        if (isNumber(repeatTimes)) {
            Widget.Multimedia.AudioPlayer.play(repeatTimes);
        }
    }    
}

function clickItAPpause() {
    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Audio pause";
    document.getElementById("textdivshowExplanation").innerHTML = "Pause the audio which is played by play function. ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.pause();";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "APpauseAction";
}

function APpauseAction() 
{
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    Widget.Multimedia.AudioPlayer.pause();
}

function clickItAPresume() {
    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Audio resume";
    document.getElementById("textdivshowExplanation").innerHTML = "Resume the audio which is paused by pause function.  ";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.resume();    ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "APresumeAction";
}

function APresumeAction() 
{
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    Widget.Multimedia.AudioPlayer.resume();
}

function clickItAPstop() {
    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method Audio stop";
    document.getElementById("textdivshowExplanation").innerHTML = " Stop the audio which is played by playing function.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.stop();";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "APstopAction";
}

function APstopAction() 
{
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    Widget.Multimedia.AudioPlayer.stop();
}

function clickItAPOnStatusChange() {
    showDetail_AudioPlayer();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onStateChange";
    document.getElementById("textdivshowExplanation").innerHTML = " Call back method to invoke when the state of the audio player changes.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: Widget.Multimedia.AudioPlayer.onStateChange = stateChange;//Note that <I>stateChange</I> is a callback function.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";    
    flag = "clickAPOnStatusChange";
}

function clickAPOnStatusChange() 
{
    Widget.Multimedia.AudioPlayer.onStateChange = audiostateChangecback;
    document.getElementById("textdivshowResult").innerHTML = "onStateChange  changed";
}

function clickItbackAudioPlayer() {
    showMultimediatest();
}
