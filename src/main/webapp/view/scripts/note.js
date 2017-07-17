/**
 * 根据笔记本ID加载笔记信息
 */
function loadnotes() {
    // 切换显示列表
    $("#pc_part_6").hide();// 隐藏搜索笔记列表
    $("#pc_part_2").show();// 显示笔记列表
    // 清除笔记编辑区数据
    clearNote();
    // 获取点击的笔记本ID
    var bookId = $(this).data("bookId");
    // 将点击的笔记本设置成选中状态,给li中的<a>加class="checked"
    $("#book_ul li a").removeClass("checked");
    $(this).find("a").addClass("checked");
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/loadnotes",
        type: "post",
        data: {"bookId": bookId},
        dataType: "json",
        success: function (result) {
            if (result.status == 200) {
                var notes = result.body;// json对象数据
                // 清除原有的笔记li
                $("#note_ul").empty();
                // 循环生成笔记li
                for (i = 0; i < notes.length; i++) {
                    var noteId = notes[i].cn_note_id;
                    var noteTitle = notes[i].cn_note_title;
                    // 生成li添加到笔记列表区
                    addnote_li(noteId, noteTitle);
                }
            }
        },
        error: function () {
            alert("加载笔记失败");
        }
    });
}

/**
 * 追加一个笔记li
 * @param noteId
 * @param noteTitle
 */
function addnote_li(noteId, noteTitle) {
    var s_li = '<li class="online">';
    s_li += '	<a>';
    s_li += '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>' + noteTitle + '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
    s_li += '	</a>';
    s_li += '	<div class="note_menu" tabindex="-1">';
    s_li += '	 <dl>';
    s_li += '		<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
    s_li += '		<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
    s_li += '		<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
    s_li += '		</dl>';
    s_li += '	</div>';
    s_li += '</li>';
    $li = $(s_li);// 将li字符串转成jquery对象
    $li.data("noteId", noteId);
    $("#note_ul").append($li);// 添加到笔记ul中
}

/**
 * 创建笔记
 */
function sure_addnote() {
    // 获取笔记本ID
    var bookId = $("#book_ul a.checked").parent().data("bookId");
    // 获取笔记名
    var noteTitle = $("#input_note").val().trim();
    // 检测笔记名
    if (noteTitle == "") {
        $("#input_note_msg").html("笔记名不能为空");
        return;
    }
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/add",
        type: "post",
        data: {
            "cnNotebookId": bookId,
            "cnUserId": userId,
            "cnNoteTitle": noteTitle
        },
        dataType: "json",
        success: function (result) {
            if (result.statusCode == 200) {
                // 关闭对话框
                $(".close").click();
                // 将笔记添加到笔记本列表
                var noteId = result.message;//获取笔记ID
                addnote_li(noteId, noteTitle);
                // 将刚创建的笔记设置默认选中
                $("#note_ul li:last").click();
            }
        },
        error: function () {
            alert("创建笔记失败");
        }
    });
}

/**
 * 弹出添加笔记对话框
 */
function pop_addnote_window() {
    $("#can").load(path + "/view/alert/alert_note.html");
    $(".opacity_bg").show();
}

function loadNoteById() {
    // 将当前笔记设置成选中状态
    $("#note_ul li a").removeClass("checked");
    $(this).find("a").addClass("checked");
    // 获取笔记ID
    var noteId = $(this).data("noteId");
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/loadnote",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.status == 200) {
                var noteTitle = result.body.cnNoteTitle;
                var noteBody = result.body.cnNoteBody;
                // 设置编辑区
                $("#input_note_title").val(noteTitle);
                um.setContent(noteBody);
            } else {
                alert(result.message);
            }
        },
        error: function () {
            alert("加载笔记信息失败");
        }
    });
}

/**
 * 清空笔记编辑区数据
 */
function clearNote() {
    $("#input_note_title").val("");
    um.setContent("");
}

/**
 * 保存笔记操作
 */
function saveNote() {
    // 获取笔记ID,笔记标题,笔记内容
    var noteId = $("#note_ul li a.checked").parent().data("noteId");
    var noteTitle = $("#input_note_title").val().trim();
    var noteBody = um.getContent().trim();
    // 检查标题和内容格式
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/update",
        type: "post",
        data: {
            "cnNoteId": noteId,
            "cnNoteTitle": noteTitle,
            "cnNoteBody": noteBody
        },
        dataType: "json",
        success: function (result) {
            if (result.statusCode == 200) {
                // 如果名字发生改变,修改笔记列表li
                var li_NoteTitle = $("#note_ul li a.checked").text();
                if (noteTitle != li_NoteTitle) {// 替换li中的笔记名字内容
                    var a_content = '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>' + noteTitle + '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
                    $("#note_ul li a.checked").html(a_content);
                }
                // 提示成功
                alert(result.message);
            }
        },
        error: function () {
            alert("保存笔记失败");
        }
    });
}

/**
 * 笔记删除操作
 */
function deleteNote() {
    // 获取要删除的笔记ID $(this)表示删除按钮
    var $li = $(this).parents("li");
    var noteId = $li.data("noteId");
    if (!confirm("是否确认删除该笔记")) {
        return;
    }
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/first_delete",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.statusCode == 200) {
                // 将笔记li从列表删除
                $li.remove();
                // 提示成功
                alert(result.message);
            } else {
                alert(result.message);
            }
        },
        error: function () {
            alert("笔记删除失败");
        }
    });
}

function shareNote() {
    // 获取笔记ID
    var $li = $(this).parents("li");
    var noteId = $li.data("noteId");
    // 发送Ajax请求
    $.ajax({
        url: path + "/note/share",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.status == 200) {// 分享成功
                alert(result.message);
            } else {// 分享失败,重复分享
                alert(result.message);
            }
        },
        error: function () {
            alert("笔记分享失败");
        }
    });
    return false;
};
