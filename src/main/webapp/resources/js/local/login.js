$(function () {
    var loginUrl ="/local/logincheck"

    // var usertype = getQueryString("usertype");

    var loginCount = 0;

    $("#submit").click(function () {
        var username = $("#username").val();
        var password =$("#psw").val();
        var verifyCodeActual = $('#j_captcha').val();

        //是否需要验证码
        var needVerify =false;
        //如果登录三次都失败
        if (loginCount >= 3) {
            //需要验证码
            if (!verifyCodeActual){
                $.toast("请输入验证码!");
                return;
            }else {
                needVerify=true;
            }
        }


        //访问后台进行登录验证
        $.ajax({
            url:loginUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:"json",
            data:{
                userName:username,
                password:password,
                verifyCodeActual:verifyCodeActual,
                //是否需要做验证码校验
                needVerify:needVerify
            },
            success:function (data) {
                if (data.success){
                    $.toast("登录成功!")
                    usertype=data.userType;
                    if (usertype==1){
                        //若用户在前端展示系统页面则自动连接到前端展示系统首页
                        window.location.href="/frontend/index";
                    }else if(usertype == 2){
                        window.location.href="/shopadmin/shoplist";
                    }else if(usertype == 3){
                        window.location.href="/superadmin/head";
                    }
                }else {
                    $.toast("登录失败！"+data.errMsg);
                    loginCount++
                    if (loginCount>=3){
                        $("#verifyPart").show();
                    }
                }
            }
        })

    })
    $("#register").click(function () {
        window.location.href="/local/register";
    })
})