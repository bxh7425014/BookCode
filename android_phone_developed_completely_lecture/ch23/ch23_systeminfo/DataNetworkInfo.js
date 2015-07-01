/*
 * Copyright by JIL, 2009. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

function showDataNetworkInfotest() {
    var DataNetworkInfotest = document.getElementById("SystemInfotest");
    DataNetworkInfotest.style.display = "block";
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "none";
}

function showDetail_DataNetworkInfo()
{
    var DataNetworkInfotest = document.getElementById("SystemInfotest");

    DataNetworkInfotest.style.display = "none"; 
    var showDetail = document.getElementById("showDetail");
    showDetail.style.display = "block";
}

function clickDisplayCurrentConnectionName() {
    showDetail_DataNetworkInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method onNetworkConnection-\nChanged";
    document.getElementById("textdivshowExplanation").innerHTML = "Call back method to invoke when the phone connects to another data network.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need test on phone";
    document.getElementById("textdivshowSampleCode").innerHTML = "e.g.\n"
            + "Widget.Device.DataNetworkInfo\n.onNetworkConnectionChanged = myCallBack;\n"
            + "function myCallBack(newConnectionName) {};";
    flag = "displayConnectNameAction";
}

function displayConnectNameAction() {
    Widget.Device.DataNetworkInfo.onNetworkConnectionChanged = myCallBack;
}

function clickgetNetworkConnectionType() {
    showDetail_DataNetworkInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field networkConnectionType";
    document.getElementById("textdivshowExplanation").innerHTML = "The array propery contains the types of the network connections to which the handset is currently connecting.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice :   Need test on phone";
    document.getElementById("textdivshowSampleCode").innerHTML = "var types = Widget.Device.DataNetworkInfo.networkConnectionType;";
    flag = "getConnectionTypesAction";
}

function clickgetNetWorkConnectName() {
    showDetail_DataNetworkInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Method getNetworkConnectionName";
    document.getElementById("textdivshowExplanation").innerHTML = "Retrieves the network connection name, specified by the type of the connection.";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : Need test on phone";
    document.getElementById("textdivshowSampleCode").innerHTML = "var names = Widget.Device.DataNetworkInfo.\ngetNetworkConnectionName(connectionType);";
    flag = "getNetWorkConnectNameAction";
}

function getNetWorkConnectNameAction() {
    var types = Widget.Device.DataNetworkInfo.networkConnectionType;
    var typeContent = "NetWork Connection name sum is " + types.length
            + "\n Contain network name have";
    for ( var i = 0; i < types.length; i++) {
        typeContent = typeContent
                + Widget.Device.DataNetworkInfo
                        .getNetworkConnectionName(types[i]);
        if (i != (types.length - 1))
            typeContent = typeContent + ",";
    }
    document.getElementById("textdivshowResult").innerHTML = typeContent;
}

function getConnectionTypesAction() {
    var types = Widget.Device.DataNetworkInfo.networkConnectionType;
    var typeContent = "NetWork Connection Type sum is " + types.length
            + "\n Contain network type have ";
    for ( var i = 0; i < types.length; i++) {
        typeContent = typeContent + types[i];
        if (i != (types.length - 1))
            typeContent = typeContent + ",";
    }
    document.getElementById("textdivshowResult").innerHTML = typeContent;
}

function myCallBack(newConnectionName) 
{
    document.getElementById("textdivshowResult").innerHTML = "Current network connection name is "
            + newConnectionName;
}

function clickisDataNetworkConnected() {

    showDetail_DataNetworkInfo();
    document.getElementById("textdivshowResult").innerHTML = "";
    document.getElementById("textdivshowName").innerHTML = "Field isDataNetworkConnected";
    document.getElementById("textdivshowExplanation").innerHTML = " Indicates whether the phone is connected to data network. ";
    document.getElementById("textdivshowInitialCondition").innerHTML = "Notice : n/a";
    document.getElementById("textdivshowSampleCode").innerHTML = "E.g.: var connected = Widget.Device.DataNetworkInfo.isDataNetworkConnected;";
   
    flag = "isDataNetworkConnectedAction";
    alert(flag);
}

function isDataNetworkConnectedAction() {

    var connected = Widget.Device.DataNetworkInfo.isDataNetworkConnected;
    document.getElementById("textdivshowResult").innerHTML = "Result : "
            + connected;
}
