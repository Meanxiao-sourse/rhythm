<#--

    Rhythm - A modern community (forum/BBS/SNS/blog) platform written in Java.
    Modified version from Symphony, Thanks Symphony :)
    Copyright (C) 2012-present, b3log.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<#include "macro-settings.ftl">
<@home "account">
<div class="module">
    <div class="module-header">
        <h2>你的背包</h2>
    </div>
    <div class="module-panel form fn-clear" id="bag">
    </div>
    <script>
        let html = '';
        let bag = ${sysBag};
        if (bag.checkin2days !== undefined && bag.checkin2days > 0) {
            html += '<button style="margin:0 5px 5px 0" onclick="Settings.use2dayCheckinCard(\'${csrfToken}\')">两天免签卡 x' + bag.checkin2days + '</button>';
        }
        if (bag.sysCheckinRemain !== undefined && bag.sysCheckinRemain > 0) {
            html += '<button style="margin:0 5px 5px 0">免签卡生效中，剩余' + bag.sysCheckinRemain + '天</button>';
        }
        if (html === '') {
            html = '你的背包和钱包一样，是空的。';
        }
        document.getElementById("bag").innerHTML = html;
    </script>
</div>

<div class="module">
    <div class="module-header">
        <h2>${userNameLabel} ${updateNameTipLabel}</h2>
    </div>
    <div class="module-panel form fn-clear">
        <label>${currentUsernameLabel}</label>
        <input value="${currentUser.userName}" type="text" readonly />

        <label>${newUsernameLabel}</label>
        <input id="newUsername" type="text" /><br/><br/>

        <div id="usernameTip" class="tip"></div><br/>
        <button class="fn-right" onclick="Settings.update('username', '${csrfToken}')">${saveLabel}</button>
    </div>
</div>

<div class="module">
    <div class="module-header">
        <h2>${passwordLabel}</h2>
    </div>
    <div class="module-panel form fn-clear">
        <label>${oldPasswordLabel}</label>
        <input id="pwdOld" type="password" />

        <label>${newPasswordLabel}</label>
        <input id="pwdNew" type="password" />

        <label>${confirmPasswordLabel}</label>
        <input id="pwdRepeat" type="password" /> <br/><br/>
        <div id="passwordTip" class="tip"></div><br/>
        <button class="fn-right" onclick="Settings.update('password', '${csrfToken}')">${saveLabel}</button>
    </div>
</div>

<div class="module">
    <div class="module-header">
        <h2>${emailLabel}</h2>
    </div>
    <div class="module-panel form fn-clear">
        <label>${emailLabel}</label>
        <input id="emailInput" type="text" value="<#if !currentUser.userEmail?ends_with("sym.b3log.org")>${currentUser.userEmail}</#if>" />

        <div class="home-account__captch">
            <label>${imageCaptchaLabel}</label>
            <img class="fn-pointer" height="20px" src="${servePath}/captcha" onclick="this.src = '${servePath}/captcha?' + (new Date()).getTime()" />
            <input id="emailVerify" type="text" />
        </div>
        <div class="fn-none" id="emailCodePanel">
            <label>${emailCaptchaLabel}</label>
            <input id="emailCode" type="text" />
        </div>
        <div id="emailTip" class="tip"></div><br/>
        <button id="emailSubmitBtn" class="fn-right fn-none"
                onclick="Settings.updateEmail('${csrfToken}')">${submitLabel}</button>
        <button id="emailGetBtn"
                class="fn-right"
                onclick="Settings.getEmailCaptcha('${csrfToken}')">${getEmailCaptchaLabel}</button>
    </div>
</div>

<div class="module">
    <div class="module-header">
        <h2>${deactivateAccountLabel} ${deactivateAccountTipLabel}</h2>
    </div>
    <div class="module-panel form fn-clear">
        <label>${currentUsernameLabel}</label>
        <input value="${currentUser.userName}" type="text" readonly /><br/><br/>

        <div id="deactivateTip" class="tip"></div><br/>
        <button class="fn-right ft-red" onclick="Settings.update('deactivate', '${csrfToken}')">${saveLabel}</button>
    </div>
</div>
</@home>
