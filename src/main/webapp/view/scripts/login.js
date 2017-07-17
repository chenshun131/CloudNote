$(function () {// 页面载入后执行
    // 登录按钮单击处理
    $("#login").click(function () {
        // 先清除原有提示信息
        $("#count_msg").html("");
        $("#password_msg").html("");
        // 获取请求要提交的数据
        var name = $("#count").val().trim();
        var pwd = $("#password").val().trim();
        // 检测数据格式
        var ok = true;// 默认通过检测
        if (name == "") {
            $("#count_msg").html("用户名不能为空");
            ok = false;
        }
        if (pwd == "") {
            $("#password_msg").html("密码不能为空");
            ok = false;
        }
        // 通过格式检测发送ajax请求
        if (ok) {
            // 将身份信息拼成串,进行base64编码
            var msg = name + ":" + pwd;
            var base64_msg = Base64.encode(msg);
            // 将信息设置到消息头，发送请求
            $.ajax({
                url: path + "/user/login",
                type: "post",
                dataType: "json",
                beforeSend: function (xhr) {// xhr就是XmlHttpRequest
                    xhr.setRequestHeader("Authorization", "Basic " + base64_msg);
                },
                success: function (result) {// 回调处理
                    if (result.statusCode == 200) {// 成功
                        // 将身份信息写入Cookie
                        var token = result.body.userToken;
                        var id = result.body.userId;
                        // (加密)写入Cookie
                        addCookie("userToken", token, 2);
                        addCookie("userId", id, 2);
                        window.location.href = "./edit.html";
                    } else if (result.statusCode == 1) {// 用户名错误
                        $("#count_msg").html(result.message);
                    } else if (result.statusCode == 2) {// 密码错误
                        $("#password_msg").html(result.message);
                    } else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("登录异常");
                }
            });
        }
    });
});
