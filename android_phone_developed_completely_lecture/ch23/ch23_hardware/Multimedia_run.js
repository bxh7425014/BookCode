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
