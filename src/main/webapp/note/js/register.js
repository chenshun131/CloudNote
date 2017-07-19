$(function () {
    var isNotChecked = true;
    $("#clauseCheckBox").on("ifChanged", function (event) {
        isNotChecked = !isNotChecked;
    });

    $("#registerBtn").click(function () {
        $("#username_msg").html("");
        $("#email_msg").html("");
        $("#password_msg").html("");
        var username = $("#username").val().trim();
        var email = $("#email").val().trim();
        var password = $("#password").val().trim();
        var isOk = true;
        if (username == "") {
            isOk = false;
            $("#username_msg").html("<div style='color:orangered'>用户名不能为空</div>");
        } else if (username.length < 1 || username.length > 20) {
            isOk = false;
            $("#username_msg").html("<div style='color:orangered'>用户名必须是1到20位字符</div>");
        }
        if (email == "") {
            isOk = false;
            $("#email_msg").html("<div style='color:orangered'>Email能为空</div>");
        } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            isOk = false;
            $("#email_msg").html("<div style='color:orangered'>email格式不正确</div>");
        }
        if (password == "") {
            isOk = false;
            $("#password_msg").html("<div style='color:orangered'>密码不能为空</div>");
        } else if (password.length < 6 || password.length > 30) {
            isOk = false;
            $("#password_msg").html("<div style='color:orangered'>密码必须是6到30位字符</div>");
        }
        if (isNotChecked) {
            isOk = false;
            swal({
                title: "提示",
                text: "请同意条款",
                type: "error",
                allowOutsideClick: true,
                confirmButtonText: "确定"
            });
        }
        if (isOk) {
            $.ajax({
                url: "../../user/regist",
                type: "post",
                data: {
                    "cnUserName": username,
                    "cnUserPassword": password,
                    "cnUserDesc": email
                },
                dataType: "json",
                success: function (result) {
                    if (result.statusCode == 200) {// 成功
                        window.location.href = "./login.html";
                    } else {
                        swal({
                            title: "提示",
                            text: result.message,
                            type: "error",
                            allowOutsideClick: true,
                            confirmButtonText: "确定"
                        });
                    }
                },
                error: function () {
                    alert("注册失败");
                }
            });
        }
    });
});
