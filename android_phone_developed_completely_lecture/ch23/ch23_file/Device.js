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
 * JavaScript objects Device. 
 */

function showDevicetest() {
    var Devicetest = document.getElementById("Filetest");
    Devicetest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

/*
 * This function used to show the UI of testing the DeviceInfo class
 */
function clickItshowDeviceInfotest() 
{
    showDeviceInfotest();
}

/*
 * This function used to show the UI of testing the DataNetworkInfo class
 */
function clickItshowDataNetworkInfotest() 
{
    showDataNetworkInfotest();
}

function showDetail_device()
{
    var Devicetest = document.getElementById("Filetest");
    Devicetest.style.display = "none";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}
 
function clickItshowwidgetEngineNametest() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field widgetEngineName";
    document.getElementById("textdivshowExplanation").innerHTML = "Name of the widget engine.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.widgetEngineName;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickshowwidgetEngineNametest";
}

function clickshowwidgetEngineNametest() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.widgetEngineName;
}

function clickItshowwidgetEngineProvidertest() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field widgetEngineProvider";
    document.getElementById("textdivshowExplanation").innerHTML = "The name of the party who provides the widget engine.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.widgetEngineProvider;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickshowwidgetEngineProvidertest";
}

function clickshowwidgetEngineProvidertest() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.widgetEngineProvider;
}

function clickItshowwidgetEngineVersiontest() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field widgetEngineVersion";
    document.getElementById("textdivshowExplanation").innerHTML = "The version of the widget engine.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var v = Widget.Device.widgetEngineVersion;";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    flag = "clickshowwidgetEngineVersiontest";
}

function clickshowwidgetEngineVersiontest() {
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + Widget.Device.widgetEngineVersion;
}

function clickItshowclipboardString() {
	showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field clipboardString ";
    document.getElementById("textdivshowExplanation").innerHTML = "This field can be used to get/set clipboardString ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g.   var str = Widget.Device.clipboardString;";
    flag = "clipboardStringAction";
}

function clipboardStringAction() {
    var time = new Date();
    Widget.Device.clipboardString ="new clipboard string add at "+ time;
    document.getElementById("textdivshowResult").innerHTML = "String on the clipboard: "+ Widget.Device.clipboardString;
}

/*
 * This function used to test the method copyFile. This method copies the
 * specified file to the path and file name specified in the
 * destinationFullName. The new file's fullName property will be that specified
 * by the destinationFullName value in this function.
 */
function clickItcopyFile() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method copyFile";
    document.getElementById("textdivshowExplanation").innerHTML = "This method copies the specified file to the path and file name specified in the destinationFullName. This method also applies to directories. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the source file exists in the correct path.";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g. var b = Widget.Device.copyFile(\"/sdcard/text1.txt\", \"/sdcard/text2.txt\");";
    flag = "clickcopyFile";
}
 
function clickcopyFile() {

    var sourceFilePath = "/sdcard/test1.txt";
    var destinationFilePath = "/sdcard/test2.txt";
    if (isNotEmpty(sourceFilePath) && isNotEmpty(destinationFilePath)){
        var b = Widget.Device.copyFile(sourceFilePath, destinationFilePath);
    } 
    if (b) {
        document.getElementById("textdivshowResult").innerHTML = "Result : Success! Copy file /sdcard/test1.txt to /sdcard/test2.txt.";
    } else {
        document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Copy file /sdcard/test1.txt to /sdcard/test2.txt.";
    }
}

/*
* This function used to test the method getFile. This method provides access to
* a File object located at the specified full path and name. This fullName
* should correspond to the 'fullName' property of the file, which includes the
* path and the file name. A right slash ('/') is used as the Widget separator.
*/
function clickItgetFile() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method getFile";
   document.getElementById("textdivshowExplanation").innerHTML = "This provides access to a File object located at the specified full path and name.This method also applies to directories. ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the file exists in the correct path.";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var files = Widget.Device.getFile(\"/sdcard/text1.txt\");";
   flag = "clickgetFile";
}

function clickgetFile() {
   var filePath = "/sdcard/test1/f1.txt";
   var file = null;
   if (isNotEmpty(filePath)){
       file = Widget.Device.getFile("/sdcard/test1/f1.txt");
   }
   if (file) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Get file, "
               + "fileName: " + file.fileName
               +  ", filePath: " + 
               + file.filePath
               +  ", isDirectory: " +
               + file.isDirectory
               +  ", fileSize: " +
               + file.fileSize
               +   ", createDate: " +
               + file.createDate
               +  ", lastModifyDate: " +
               + file.lastModifyDate;
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : can not get file";
   }
}

/*
 * This function used to test the method deleteFile. This method deletes the
 * file located at the specified fullName. The fullname includes the path and
 * file name.
 */
function clickItdeleteFile() {
     showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method deleteFile";
    document.getElementById("textdivshowExplanation").innerHTML = "This method deletes the file located at the specified fullName.This method also applies to directories. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the file exists in the correct path.";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g. var b = Widget.Device.deleteFile(\"/sdcard/text2.txt\");";
    flag = "clickdeleteFile";
}

function clickdeleteFile() {
    var filePath = "/sdcard/test1/f1.txt";
    if (isNotEmpty(filePath)){
        var b = Widget.Device.deleteFile(filePath);
    }
    if (b) {
        document.getElementById("textdivshowResult").innerHTML = "Result : Success! Delete file /sdcard/test2.txt.";
    } else {
        document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Delete file /sdcard/test2.txt.";
    }
}

/*
 * This function used to test the method findFiles. This method returns an array
 * of Files on all file systems that match the specified File. Widget can
 * specify only search a range of files between startInx and endInx. This is an
 * asynchronous function, and will invoke Device.onFilesFound() when search
 * completes.
 */
function clickItfindFiles() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method findFiles";
    document.getElementById("textdivshowExplanation").innerHTML = "This method returns an array of files on all file systems that match the specified file.This method also applies to directories. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : start index and end index must be number type";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g. Widget.Device.onFilesFound = someCallBackFunction; var compareFile = new Widget.Device.File(); compareFile.fileName = \"test\"; Widget.Device.findFiles(compareFile, 0, 9);";
    flag = "clickfindFiles";
}
 
function clickfindFiles() {
    var file = new Widget.Device.File();
    file.fileName = "f1.txt";
    var beginIndex = 0;
    var endIndex = 99;
    Widget.Device.onFilesFound = function(searchFiles) {
        if (searchFiles && searchFiles.length > 0) {
            document.getElementById("textdivshowResult").innerHTML = "Result count: "
                    + searchFiles.length
                    + ". The first file: "
                    + "fileName: "
                    + searchFiles[0].fileName
                    + ", filePath: "
                    + searchFiles[0].filePath
                    + ", fileSize: "
                    + searchFiles[0].fileSize
                    + ", createDate: "
                    + searchFiles[0].createDate;
        } else {
            document.getElementById("textdivshowResult").innerHTML = "Result : no search results";
        }
    };

    Widget.Device.findFiles(file, beginIndex, endIndex);
}

/*
* This function used to test the method moveFile. This method moves the
* specified file to the path and file name specified in the
* destinationFullName. If successful, the source file is removed from its
* original location and placed in the new location with the new file name. The
* new file's fullName property will be that specified by the
* destinationFullName value in this function.
*/
function clickItmoveFile() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method moveFile";
   document.getElementById("textdivshowExplanation").innerHTML = "This method moves the specified file to the path and file name specified in the destinationFullName.This method also applies to directories. ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the source file exists in the correct path.";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var b = Widget.Device.moveFile(\"/sdcard/test1.txt\", \"/sdcard/text2.txt\");";
   flag = "clickmoveFile";
}

function clickmoveFile() {
   var source = "/sdcard/f1.txt";
   var dest = "/sdcard/f2.txt";
   var b = null;
   if (isNotEmpty(source) && isNotEmpty(dest)){
       b = Widget.Device.moveFile(source, dest);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Move file /sdcard/test1.txt to /sdcard/test2.txt.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Move file /sdcard/test1.txt to /sdcard/test2.txt.";
   }
}

/*
* This function used to test the method createFile. This method create the
* specified file with the given path and name.
*/
function clickItcreateFile() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method createFile";
   document.getElementById("textdivshowExplanation").innerHTML = "This method ceate the file located at the specified fullName.  ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the path folder exists";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var b = Widget.Device.createFile(\"/sdcard/text3.txt\"); ";
   flag = "clickcreateFile";
}

function clickcreateFile() {
   var destFile = "/sdcard/f3.txt";
   var b = null;
   if (isNotEmpty(destFile)){
       b = Widget.Device.createFile(destFile);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Create file /sdcard/test3.txt.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Create file /sdcard/test3.txt.";
   }
}

/*
* This function used to test the method createFolder. This method create the
* specified folder with the given path and name.
*/
function clickItcreateFolder() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method createFolder";
   document.getElementById("textdivshowExplanation").innerHTML = "This method ceate the folder located at the specified fullName. ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the path folder exists";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var b = Widget.Device.createFolder(\"/sdcard/hoho/\");";
   flag = "clickcreateFolder";
}

function clickcreateFolder() {
   var destPath = "/sdcard/hoho/";
   var b = null;
   if (isNotEmpty(destPath)){
       b = Widget.Device.createFolder(destPath);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Create folder /sdcard/hoho/.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Create folder /sdcard/hoho/.";
   }
}
/*
* This function used to test the method copyFolder. This method copies the
* specified folder to the path and folder name specified in the
* destinationFullName. The new folder's fullName property will be that
* specified by the destinationFullName value in this function.
*/
function clickItcopyFolder() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method copyFolder";
   document.getElementById("textdivshowExplanation").innerHTML = "This method copies the specified folder to the path and folder name specified in the destinationFullName. ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the source folder exists in the correct path.";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var b = Widget.Device.copyFolder(\"/sdcard/test1/\", \"/sdcard/test2/\");";
   flag = "clickcopyFolder";
}

function clickcopyFolder() {
   var sourcePath = "/sdcard/test1/";
   var destPath = "/sdcard/test2/";
   var b = null;
   if (isNotEmpty(sourcePath) && isNotEmpty(destPath)){
       b = Widget.Device.copyFolder(sourcePath, destPath);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Copy folder /sdcard/test1/ to /sdcard/test2/.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Copy folder /sdcard/test1/ to /sdcard/test2/.";
   }
}

/*
* This function used to test the method deleteFolder. This method deletes the
* folder located at the specified fullName. The fullname includes the path and
* folder name.
*/
function clickItdeleteFolder() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method deleteFolder";
   document.getElementById("textdivshowExplanation").innerHTML = "This method delete the folder located at the specified fullName.  ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the folder exists in the correct path.";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var b = Widget.Device.deleteFolder(\"/sdcard/test2/\");";
   flag = "clickdeleteFolder";
}

function clickdeleteFolder() {
   var path = "/sdcard/test2/";
   var b = null;
   if (isNotEmpty(path)){
       b = Widget.Device.deleteFolder(path);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Delete folder /sdcard/test2/.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Delete folder /sdcard/test2/.";
   }
}

/*
* This function used to test the method moveFolder. This method moves the
* specified folder to the path and folder name specified in the
* destinationFullName. If successful, the source folder is removed from its
* original location and placed in the new location with the new folder name.
* The new folder's fullName property will be that specified by the
* destinationFullName value in this function.
*/
function clickItmoveFolder() {
   showDetail_device();
   document.getElementById("textdivshowResult").innerHTML = "";
   document.getElementById("textdivshowName").innerHTML = "Method moveFolder";
   document.getElementById("textdivshowExplanation").innerHTML = "This method moves the specified folder to the path and folder name specified in the destinationFullName. ";
   document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the source folder exists in the correct path.";
   document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var b = Widget.Device.moveFolder(\"/sdcard/test1/\", \"/sdcard/test2/\");";
   flag = "clickmoveFolder";
}

function clickmoveFolder() {
   var sourcePath = "/sdcard/test1/";
   var destPath = "/sdcard/test2/";
   var b = null;
   if (isNotEmpty(sourcePath) && isNotEmpty(destPath)){
       b = Widget.Device.moveFolder(sourcePath, destPath);
   }
   if (b) {
       document.getElementById("textdivshowResult").innerHTML = "Result : Success! Move folder /sdcard/test1/ to /sdcard/test2/.";
   } else {
       document.getElementById("textdivshowResult").innerHTML = "Result : Fail! Move folder /sdcard/test1/ to /sdcard/test2/.";
   }
}

/*
 * This function used to test the method getWidgetFileNames. This method Get
 * names of all files in the specified Widget.
 */
function clickItgetDirectoryFileNames() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method getDirectoryFileNames";
    document.getElementById("textdivshowExplanation").innerHTML = "Get all files name in specified directory. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the file exists in the correct path.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var files = Widget.Device.getDirectoryFileNames(\"/sdcard/test1/\");";
    flag = "clickgetDirectoryFileNames";
}
 
function clickgetDirectoryFileNames() {
    var directory = "/sdcard/test1/";
    var files = null;
    if (isNotEmpty(directory)){
        files = Widget.Device.getDirectoryFileNames("/sdcard/test1/");
    }
    if (files != null && files.length > 0) {
        var v = "Result : Get all files name in folder /sdcard/test1/. They are ";
        for ( var i = 0; i < files.length - 1; i++) {
            v += files[i] + ", ";
        }
        v += files[files.length - 1];
        document.getElementById("textdivshowResult").innerHTML = v;
    } else {
        document.getElementById("textdivshowResult").innerHTML = "Result : no files found";
    }
}

/*
 * This function used to test the method getFileSystemRoots. This method get
 * file system root directories.
 */
function clickItgetFileSystemRoots() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method getFileSystemRoots";
    document.getElementById("textdivshowExplanation").innerHTML = "Get file system root directories. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var roots = Widget.Device.getFileSystemRoots();";
    flag = "clickgetFileSystemRoots";
}
 
function clickgetFileSystemRoots() {
    var roots = Widget.Device.getFileSystemRoots();
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + roots[0] + "; " + roots[1];
}

/*
 * This function used to test the method getFileSystemSize. This method get the
 * file system size.
 */
function clickItgetFileSystemSize() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method getFileSystemSize";
    document.getElementById("textdivshowExplanation").innerHTML = "Get the file system size. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Makesure the file exists in the correct path.";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g. var size = Widget.Device.getFileSystemSize(\"/sdcard/test1/\");";
    flag = "clickgetFileSystemSize";
}
 
function clickgetFileSystemSize(){
    var path = "/sdcard/test1/";
    if (isNotEmpty(path)) {
        var folderSize = Widget.Device.getFileSystemSize(path);
        document.getElementById("textdivshowResult").innerHTML = "Result : Get filesystem /sdcard/test1/ size: "
                + folderSize + "Bytes";
    }
}

function clickItgetAvailableApplications() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method getAvailableApplications";
    document.getElementById("textdivshowExplanation").innerHTML = "This method get the names of applications that can be launched. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g. var applications = Widget.Device.getAvailableApplications();";
    flag = "clickgetAvailableApplications";
}

function clickgetAvailableApplications() {
    var v = Widget.Device.getAvailableApplications();
    var tmp = "";
    for ( var i = 0; i < v.length; i++) {
        tmp += v[i] + ", ";
    }
    document.getElementById("textdivshowResult").innerHTML = tmp;
}

function clickItlaunchApplication() {
    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method launchApplication";
    document.getElementById("textdivshowExplanation").innerHTML = "This method launch a specified native application with start parameters. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g.     Widget.Device.launchApplication(\"Calendar\", \" \");";
    flag = "clicklaunchApplication";
}

function clicklaunchApplication() {
    Widget.Device.launchApplication("Calendar", " ");
}

function clickItsetRingtone() {

    showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method setRingtone";
    document.getElementById("textdivshowExplanation").innerHTML = "This method set Ringtone for an addressbook item. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : This ringtone file must be exist";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g.     var v = new Widget.PIM.AddressBookItem(); v.fullName = \"username\"; Widget.Device.setRingtone(\"/sdcard/myringtone.mp3\", v);";
    flag = "clicksetRingtone";
}

function clicksetRingtone() {
    var v = new Widget.PIM.AddressBookItem();
    v.fullName = "username";
    Widget.Device.setRingtone("/sdcard/myringtone.mp3", v);
    document.getElementById("textdivshowResult").innerHTML = "Result: Done!";
}

function clickItvibrate() {
	showDetail_device();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method vibrate";
    document.getElementById("textdivshowExplanation").innerHTML = "This method is called to vibrate the device for a specific time period. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Needed to test on phone.";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g.     Widget.Device.vibrate(5);";
    flag = "vibrateAction";
}

function vibrateAction() {
	Widget.Device.vibrate(5);
}
