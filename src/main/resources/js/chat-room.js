/*
 * Rhythm - A modern community (forum/BBS/SNS/blog) platform written in Java.
 * Modified version from Symphony, Thanks Symphony :)
 * Copyright (C) 2012-present, b3log.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/**
 * @fileoverview 聊天室
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 1.3.0.2, Apr 30, 2020
 */

/**
 * @description Add comment function.
 * @static
 */
var ChatRoom = {
  init: function () {
    // 聊天窗口高度设置
    /* if ($.ua.device.type !== 'mobile') {
      $('.list').
        height($('.side').height() -
          $('.chat-room .module:first').outerHeight() - 20)
    } else {
      $('.list').height($(window).height() - 173)
    } */

    // 没用登录就不需要编辑器初始化了
    if ($('#chatContent').length === 0) {
      return false
    }

    ChatRoom.editor = Util.newVditor({
      id: 'chatContent',
      cache: true,
      preview: {
        mode: 'editor',
      },
      resize: {
        enable: true,
        position: 'bottom',
      },
      toolbar: [
        'emoji',
        'headings',
        'bold',
        'italic',
        'link',
        '|',
        'list',
        'ordered-list',
        'check',
        'outdent',
        'indent',
        '|',
        'quote',
        'code',
        'insert-before',
        'insert-after',
        '|',
        'upload',
        'table',
        '|',
        'undo',
        'redo',
        '|',
        {
          name: 'more',
          toolbar: [
            'fullscreen',
            'edit-mode',
            'both',
            'preview',
            'outline',
            'content-theme',
            'code-theme',
            'devtools',
            'info',
            'help',
          ],
        }],
      height: 200,
      counter: 40960,
      placeholder: '聊天室历史记录将永久保存，每天一次撤回机会；在发送消息前请三思而后行，友善是第一原则。',
      ctrlEnter: function () {
        ChatRoom.send()
      },
    })

    // img preview
    var fixDblclick = null
    $('.chat-room').on('dblclick', '.vditor-reset img', function () {
      clearTimeout(fixDblclick)
      if ($(this).hasClass('emoji')) {
        return
      }
      window.open($(this).attr('src'))
    }).on('click', '.vditor-reset img', function (event) {
      clearTimeout(fixDblclick)
      if ($(this).hasClass('emoji')) {
        return
      }
      var $it = $(this),
          it = this
      fixDblclick = setTimeout(function () {
        var top = it.offsetTop,
            left = it.offsetLeft

        $('body').
        append('<div class="img-preview" onclick="$(this).remove()"><img style="transform: translate3d(' +
            Math.max(0, left) + 'px, ' +
            Math.max(0, (top - $(window).scrollTop())) + 'px, 0)" src="' +
            ($it.attr('src').split('?imageView2')[0]) +
            '"></div>')

        $('.img-preview').css({
          'background-color': '#fff',
          'position': 'fixed',
        })
      }, 100)
    })
  },
  /**
   * 发送聊天内容
   * @returns {undefined}
   */
  send: function () {
    var content = ChatRoom.editor.getValue()
    var requestJSONObject = {
      content: content,
    }

    $.ajax({
      url: Label.servePath + '/chat-room/send',
      type: 'POST',
      cache: false,
      data: JSON.stringify(requestJSONObject),
      beforeSend: function () {
        $('.form button.red').
          attr('disabled', 'disabled').
          css('opacity', '0.3')
      },
      success: function (result) {
        if (0 === result.code) {
          $('#chatContentTip').removeClass('error succ').html('')

          ChatRoom.editor.setValue('')

        } else {
          $('#chatContentTip').
            addClass('error').
            html('<ul><li>' + result.msg + '</li></ul>')
        }
      },
      error: function (result) {
        $('#chatContentTip').
          addClass('error').
          html('<ul><li>' + result.statusText + '</li></ul>')
      },
      complete: function (jqXHR, textStatus) {
        $('.form button.red').removeAttr('disabled').css('opacity', '1')
      },
    })
  },
  /**
   * 获取更多内容
   * @returns {undefined}
   */
  more: function () {
    page++;
    $.ajax({
      url: Label.servePath + '/chat-room/more?page=' + page,
      type: 'GET',
      cache: false,
      async: false,
      success: function(result) {
        if (result.data.length !== 0) {
          for (let i in result.data) {
            let data = result.data[i];
            let liHtml = ChatRoom.renderMessage(data.userNickname, data.userName, data.userAvatarURL, data.time, data.content, data.oId, Label.currentUser, Label.level3Permitted);
            $('#chats').append(liHtml);
            $('#chats>div.fn-none').show(200);
            $('#chats>div.fn-none').removeClass("fn-none");
            ChatRoom.resetMoreBtnListen();
          }
          Util.listenUserCard();
        } else {
          $("#more").removeAttr("onclick");
          $("#more").html("已经到底啦！");
        }
      }
    });
  },
  /**
   * 监听点击更多按钮关闭事件
   */
  resetMoreBtnListen: function () {
    $("body").not("details-menu").click(function() {
      $("details[open]").removeAttr("open");
    });
  },
  /**
   * 撤回聊天室消息
   * @param oId
   */
  revoke: function (oId) {
    $.ajax({
      url: Label.servePath + '/chat-room/revoke/' + oId,
      type: 'DELETE',
      cache: false,
      success: function(result) {
        if (0 === result.code) {
          Util.alert(result.msg);
        } else {
          Util.alert(result.msg);
        }
      }
    });
  },
  /**
   * 艾特某个人
   */
  at: function (userName, id, justAt) {
    if (justAt === true) {
      let value = ChatRoom.editor.getValue();
      if (value !== "\n") {
        ChatRoom.editor.setValue("@" + userName + "  : " + value);
      } else {
        ChatRoom.editor.setValue("@" + userName + "  : ");
      }
    } else {
      let md = '';
      $.ajax({
        url: Label.servePath + '/cr/raw/' + id,
        method: 'get',
        async: false,
        success: function (result) {
          md = result.replace(/(<!--).*/g, "");
          md = md.replace(/\n/g, "\n> ");
        }
      });
      let value = ChatRoom.editor.getValue();
      if (value !== "\n") {
        ChatRoom.editor.setValue("@" + userName + "  引用：\n> " + md + "\n并说：" + value);
      } else {
        ChatRoom.editor.setValue("@" + userName + "  引用：\n> " + md + "\n并说：");
      }
    }
    ChatRoom.editor.focus();
  },
  /**
   * 渲染聊天室消息
   */
  renderMessage: function (userNickname, userName, userAvatarURL, time, content, oId, currentUser, isAdmin) {
    let meTag1 = "";
    let meTag2 = "";
    if (userNickname !== undefined && userNickname !== "") {
      userNickname = userNickname + " (" + userName + ")"
    } else {
      userNickname = userName;
    }
    if (currentUser === userName) {
      meTag1 = " chats__item--me";
      meTag2 = "<a onclick=\"ChatRoom.revoke(" + oId + ")\" class=\"item\">撤回</a>\n";
    }
    if (isAdmin) {
      meTag2 = "<a onclick=\"ChatRoom.revoke(" + oId + ")\" class=\"item\">撤回 (使用管理员权限)</a>\n";
    }
    let newHTML = '<div class="fn-none">';
    newHTML += '<div id="chatroom' + oId + '" class="fn__flex chats__item' + meTag1 + '">\n' +
        '    <a href="/member/' + userName + '">\n' +
        '        <div class="avatar tooltipped__user" aria-label="' + userName + '" style="background-image: url(\'' + userAvatarURL + '\');"></div>\n' +
        '    </a>\n' +
        '    <div class="chats__content">\n' +
        '        <div class="chats__arrow"></div>\n';
    if (currentUser !== userName) {
      newHTML += '<div class="ft__fade ft__smaller" style="padding-bottom: 3px;border-bottom: 1px solid #eee">\n' +
          '    <span class="ft-gray">' + userNickname + '</span>\n' +
          '</div>';
    }
    newHTML += '        <div style="margin-top: 4px" class="vditor-reset ft__smaller ' + Label.chatRoomPictureStatus + '">\n' +
        '            ' + content + '\n' +
        '        </div>\n' +
        '        <div class="ft__smaller ft__fade fn__right">\n' +
        '            ' + time + '\n' +
        '                <span class="fn__space5"></span>\n' +
        '                <details class="details action__item fn__flex-center">\n' +
        '                    <summary>\n' +
        '                        ···\n' +
        '                    </summary>\n' +
        '                    <details-menu class="fn__layer">\n' +
        '                        <a onclick=\"ChatRoom.at(\'' + userName + '\', \'' + oId + '\', true)\" class="item">@' + userName + '</a>\n' +
        '                        <a onclick=\"ChatRoom.at(\'' + userName + '\', \'' + oId + '\', false)\" class="item">引用</a>\n' +
        meTag2 +
        '                    </details-menu>\n' +
        '                </details>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div></div>';

    return newHTML;
  }
}

