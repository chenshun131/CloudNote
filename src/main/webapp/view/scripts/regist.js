$(function () {
    // 给注册按钮绑定单击处理
    $("#regist_button").click(function () {
        // 清除提示信息
        $("#warning_1").hide();
        // 获取要提交的数据
        var username = $("#regist_username").val().trim();
        var password = $("#regist_password").val().trim();
        var nickname = $("#nickname").val().trim();
        var final_password = $("#final_password").val().trim();
        // TODO 数据格式检查
        var ok = true;
        if (username == "") {
            ok = false;
            // 修改提示信息
            $("#warning_1").find("span").html("用户名不能为空");
            $("#warning_1").show();// 显示提示信息div
        }
        // 发送Ajax请求
        if (ok) {// 表单数据通过检测发送ajax
            $.ajax({
                url: path + "/user/regist",
                type: "post",
                data: {
                    "cnUserName": username,
                    "cnUserPassword": password,
                    "cnUserDesc": nickname
                },
                dataType: "json",
                success: function (result) {
                    if (result.status == 200) {// 成功
                        $("#back").click();// 触发返回按钮单击
                    } else if (result.status == 1) {// 用户名错误
                        $("#warning_1").find("span").html(result.message);
                        $("#warning_1").show();// 显示提示信息div
                    } else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("注册失败");
                }
            });
        }
    });
});
