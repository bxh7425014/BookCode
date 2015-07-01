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

function isNumber(elem, except) {
    var str = trim(elem + "");
    var re = /^(\+)?\d+$/;
    str = str.toString();
    if (!str.match(re)) {
        if (arguments.length > 1)
            except = "Enter only numbers into the field.";
        return false;
    }
    if (arguments.length > 1)
        except = null;
    return true;
}

function isNotEmpty(elem, except) {
    if (elem == null || elem == undefined)
        return false;
    var str = trim(elem + "");
    var re = /.+/;
    if (!str.match(re)) {
        if (arguments.length > 1)
            except = "Please fill in the required field.";
        return false;
    } else {
        if (arguments.length > 1)
            except = null;
        return true;
    }
}

function isLen11(elem, except) {
    var str = trim(elem + "");
    var re = /\b.{11}\b/;
    if (!str.match(re)) {
        if (arguments.length > 1)
            except = "Entry does not contain the required 11 characters.";
        return false;
    } else {
        if (arguments.length > 1)
            except = null;
        return true;
    }
}

function validChar(obj, except) {
    var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (re.test(trim(obj))) {
        if (arguments.length > 1)
            except = null;
        return true;
    }
    if (arguments.length > 1)
        except = "Input contain  invalid  character";
    return false;
}

function verifyPhoneNumber(obj, except) {
    var regu = /(^[1][0-9]+$)|(^0[1][3][0-9]{9}$)/;
    var re = new RegExp(regu);
    if (re.test(trim(obj + ""))) {
        if (arguments.length > 1)
            except = null;
        return true;
    }
    if (arguments.length > 1)
        except = "Input contain invalid phone number";
    return false;
}

function trim(str) {
    return str.replace(/^\s+|\s+$/g, "");
}

function verifyURL(obj, except) {
    var myReg = /^http:\/\/{1}((\w)+[.]){1,3}/;
    if (myReg.test(trim(obj + "")))
        return true;
    if (arguments.length > 1)
        except = "Invalid URL";
    return false;
}

function verifyFileRule(obj, except) {
    var regex = /^file:\/\/\/(\w)/;
    if (regex.test(trim(obj + "")))
        return true;
    if (arguments.length > 1)
        except = "Invalid file url";
    return false;
}

function verifyEmailAddress(obj, except) {
    var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
    if (myReg.test(obj))
        return true;
    if (arguments.length > 1)
        except = "Invalid email address";
    return false;
}
