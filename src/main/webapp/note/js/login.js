$(function () {
    $("#loginBtn").click(function () {
        $("#username_msg").html("");
        $("#password_msg").html("");
        var username = $("#username").val().trim();
        var password = $("#password").val().trim();
        var isOk = true;
        if (username == "") {
            isOk = false;
            $("#username_msg").html("<div style='color:orangered'>用户名不能为空</div>");
        } else if (username.length < 1 || username.length > 20) {
            isOk = false;
            $("#username_msg").html("<div style='color:orangered'>用户名必须是1到20位字符</div>");
        }
        if (password == "") {
            isOk = false;
            $("#password_msg").html("<div style='color:orangered'>密码不能为空</div>");
        } else if (password.length < 6 || password.length > 30) {
            isOk = false;
            $("#password_msg").html("<div style='color:orangered'>密码必须是6到30位字符</div>");
        }
        if (isOk) {
            $.ajax({
                url: "../../user/login",
                type: "post",
                dataType: "json",
                beforeSend: function (xhr) {// xhr就是XmlHttpRequest
                    xhr.setRequestHeader("Authorization", "Basic " + Base64.encode(username + ":" + password));
                },
                success: function (result) {// 回调处理
                    if (result.statusCode == 200) {// 成功
                        // 将身份信息写入Cookie
                        $.cookie("userToken", result.body.userToken, {expires: 7});
                        $.cookie("userId", result.body.userId, {expires: 7});
                        window.location.href = "./main.html";
                    } else {
                        swal({
                            title: "提示",
                            text: result.message,
                            type: "error",
                            allowOutsideClick: true,
                            confirmButtonText:"确定"
                        });
                    }
                },
                error: function () {
                    alert("登录异常");
                }
            });
        }
    });
});
