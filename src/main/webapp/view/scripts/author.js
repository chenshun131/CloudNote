var token = getCookie("userToken");
if (token == null) {// Cookie没有身份信息跳转到登录页面
    window.location.href = "log_in.html";
}
