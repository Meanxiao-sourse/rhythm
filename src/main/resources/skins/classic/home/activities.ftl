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
<#include "../macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${activityLabel} - ${symphonyLabel}">
        </@head>
        <link rel="stylesheet" href="${staticServePath}/css/index.css?${staticResourceVersion}" />
    </head>
    <body>
        <#include "../header.ftl">
        <div class="main">
            <div class="wrapper">
                <div class="content activity">
                    <div class="module">
                        <h2 class="sub-head">${activityLabel}</h2>
                        <div class="list">
                            <ul>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/activities/1A0001.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/activity/1A0001">${activity1A0001Label}</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                                下注后，请在当天 16-24 点在本页面进行兑奖，逾期作废！
                                            </span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/activities/char.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/activity/character">${characterLabel}</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">${activityCharacterTitleLabel}</span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/activities/snak.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/activity/eating-snake">${eatingSnakeLabel}</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                                ${activityEatingSnakeTitleLabel}
                                            </span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/activities/gobang.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/activity/gobang">${gobangLabel}</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                            ${activityGobangTitleLabel}
                                            </span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/games/adarkroom/img/adr.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/games/adarkroom/?lang=zh_cn">《A Dark Room》</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                                极简主义的游戏杰作，有上世纪 ascii 游戏的遗风。<br>
                                                支持存档云同步，参与 <a href="${servePath}/top/adr">${symphonyLabel} ADR 游戏总分排行榜</a>
                                            </span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/mofish.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="https://p.hancel.org/" target="_blank">摸鱼大闯关</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                                一个非常烧脑的 CTF 类闯关游（酷）戏（刑）。这个游戏的目标就只有一个：进入下一关。在大部分关卡中，你需要利用网页上的提示来找到密码并进入下一关。<br>
                                                参与 <a href="${servePath}/top/mofish">摸鱼大闯关排行榜</a>
                                            </span>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class='fn-flex'>
                                        <div class="avatar"
                                             style="background-image:url('${staticServePath}/images/restart.png')"></div>
                                        <div class="fn-flex-1">
                                            <h2>
                                                <a href="${servePath}/games/lifeRestart/view/">人生重开模拟器</a>
                                            </h2>
                                            <span class="ft-fade vditor-reset">
                                                如果你不满意现在的人生，那就来重开吧～<br>
                                                支持存档云同步，参与 <a href="${servePath}/top/lifeRestart">人生重开模拟器成就排行榜</a>
                                            </span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="side">
                    <#include "../side.ftl">
                </div>
            </div>
        </div>
        <#include "../footer.ftl">
    </body>
</html>
