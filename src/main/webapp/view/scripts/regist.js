$(function () {
    // ��ע�ᰴť�󶨵�������
    $("#regist_button").click(function () {
        // �����ʾ��Ϣ
        $("#warning_1").hide();
        // ��ȡҪ�ύ������
        var username = $("#regist_username").val().trim();
        var password = $("#regist_password").val().trim();
        var nickname = $("#nickname").val().trim();
        var final_password = $("#final_password").val().trim();
        // TODO ���ݸ�ʽ���
        var ok = true;
        if (username == "") {
            ok = false;
            // �޸���ʾ��Ϣ
            $("#warning_1").find("span").html("�û�������Ϊ��");
            $("#warning_1").show();// ��ʾ��ʾ��Ϣdiv
        }
        // ����Ajax����
        if (ok) {// ������ͨ����ⷢ��ajax
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
                    if (result.status == 200) {// �ɹ�
                        $("#back").click();// �������ذ�ť����
                    } else if (result.status == 1) {// �û�������
                        $("#warning_1").find("span").html(result.message);
                        $("#warning_1").show();// ��ʾ��ʾ��Ϣdiv
                    } else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("ע��ʧ��");
                }
            });
        }
    });
});
