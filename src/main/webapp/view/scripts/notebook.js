/**
 * 加载用户笔记本信息
 * @param userId
 */
function loadbooks(userId) {
    $.ajax({
        url: path + "/notebook/loadbooks",
        type: "post",
        data: {"userId": userId},
        dataType: "json",
        success: function (result) {
            if (result.status == 200) {// 成功
                // 获取笔记本信息生成笔记本列表
                var books = result.data;
                // 循环
                for (i = 0; i < books.length; i++) {
                    var bookId = books[i].cnNotebookId;
                    var bookName = books[i].cnNotebookName;
                    addBook_li(bookId, bookName, true);
                }
            }
            // 默认设置第一个选中
            $("#book_ul li:first").click();
        },
        error: function () {
            alert("加载笔记本失败");
        }
    });
}

/**
 * 向笔记本列表追加一个li元素
 * @param bookId
 * @param bookName
 * @param append
 */
function addBook_li(bookId, bookName, append) {
    //拼成一个li字符串
    var s_li = '<li class="online">';
    s_li += '<a>';
    s_li += '<i class="fa fa-book" title="online" rel="tooltip-bottom">';
    s_li += '</i>' + bookName + '</a>';
    s_li += '</li>';
    $li = $(s_li);//将li字符串变成jQuery对象
    $li.data("bookId", bookId);//将id绑定到li上
    //将li添加到笔记本ul中
    if (append) {
        $("#book_ul").append($li);
    } else {
        $("#book_ul").prepend($li);
    }
}

/**
 * 弹出添加笔记本对话框
 */
function pop_addbook_window() {
    // 采用ajax发送请求，将返回结果填充到id=can的div中
    $("#can").load(path + "/view/alert/alert_notebook.html");
    $(".opacity_bg").show();
}

/**
 * 关闭对话框
 */
function close_window() {
    $("#can").empty();// 清除对话框内容,隐藏
    $(".opacity_bg").hide();// 取消灰色背景
}

/**
 * 确认添加笔记本
 */
function sure_addbook() {
    // 获取提交的数据
    var bookName = $("#input_notebook").val().trim();
    // 检查数据格式
    if (bookName == "") {
        $("#input_notebook_msg").html("<font color='red'>名称不能为空<font/>");
        return;
    }
    // 发送ajax请求
    $.ajax({
        url: path + "/notebook/add",
        type: "post",
        data: {
            "userId": userId,
            "bookName": bookName
        },
        dataType: "json",
        success: function (result) {
            if (result.statusCode == 10002) {
                window.location.href = "../log_in.html";
            }
            else if (result.statusCode == 200) {// 成功
                $(".close").click();// 关闭对话框
                // 向笔记本列表追加一个li
                var bookId = result.body;// 获取笔记本ID
                addBook_li(bookId, bookName, false);
                // 设置新建笔记本为选中状态
                $("#book_ul li:first").click();
            }
            else {
                $("#input_notebook_msg").html("<font color='red'>" + result.message + "</font>");
            }
        },
        error: function () {
            alert("添加笔记本失败");
        }
    });
};
