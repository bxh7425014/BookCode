
var flag = "";
function clickItResult(){
    switch (flag) {
        case "clickMultimudiagetVolume":
            clickMultimudiagetVolume();
            break;  
	case "CamerasetWindowAction":
	      CamerasetWindowAction();
	break;  
         case "clicktextdivisVideoPlaying":
            clicktextdivisVideoPlaying();
            break;
        case "clickMultimudiastopall":
            clickMultimudiastopall();
            break;
        case "clicktextdivisAudioPlaying":
            clicktextdivisAudioPlaying();
            break;
        case "APopenAction":
            APopenAction();
            break;
        case "APplayAction":
            APplayAction();
            break;
        case "APpauseAction":
            APpauseAction();
            break;
        case "APresumeAction":
            APresumeAction();
            break;
        case "APstopAction":
            APstopAction();
            break;
        case "clickAPOnStatusChange":
            clickAPOnStatusChange();
            break;
        case "captureImgAction":
            captureImgAction();
            break;
        case "startVideoCaptureAction":
            startVideoCaptureAction();
            break;
        case "stopVideoCaptureAction":
            stopVideoCaptureAction();
            break;
        case "ActiveonCameraCaptured":
            ActiveonCameraCaptured();
            break;
        default:
            break;
    }
}
