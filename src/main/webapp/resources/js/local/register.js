var registerUrl = "/local/registerNew"
var checkflag = false;
$("#register").click(function () {
    var localAuth = {};
    var personInfo = {};
    localAuth.userName = $("#username").val();
    localAuth.password = $("#psw").val();
    personInfo.userType = $("#userType").find("option").not(
        function () {
            return !this.selected;
        }
    ).data("id");
    personInfo.name = $("#name").val();
    personInfo.gender = $("#gender").find("option").not(
        function () {
            return !this.selected;
        }
    ).data("text");
    personInfo.email = $("#email").val();
    personInfo.userImg = $("#user-img")[0].files;
    localAuth.personInfo = personInfo;
    var verifyCodeActual = $("#j_captcha").val();
    var formData = new FormData();
    formData.append("localAuth", JSON.stringify(localAuth));

    formData.append("verifyCodeActual", verifyCodeActual);

    if (!checkflag) {
        $.toast("两次密码输入不一致");
    } else {
        $.ajax({
                url: registerUrl,
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.success) {
                        $.toast("注册成功")
                        window.location.href = "/local/login"
                    } else {
                        $.toast(data.errMsg)
                    }
                    $("#captcha_img").click();
                }
            }
        )
    }
})

$("#psw").bind("change", confirmcheck);
$("#pswconfirm").bind("change", confirmcheck);

function confirmcheck() {
    var password = $("#psw").val();
    var passwordconfim = $("#pswconfirm").val();
    if (password != passwordconfim) {
        $.toast("两次密码输入不一致")
        checkflag = false;
    } else {
        checkflag = true;
    }
}