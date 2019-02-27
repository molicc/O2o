$(function() {
	var url = '/local/changelocalpwd';
	var usertype = getQueryString("usertype");
	$('#submit').click(function() {
		var userName = $('#userName').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var confirmPassword = $("#confirmPassword").val();
		if (newPassword!=confirmPassword){
		    $.toast("两次输入密码不一致");
            $('#captcha_img').click();
            return;
        }
        //添加表单数据
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if (usertype==1){
					    window.location.href="/frontend.index"
                    } else {
					    window.location.href="/shopadmin/shoplist"
                    }
				} else {
					$.toast('提交失败！'+data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/shopadmin/shoplist';
	});
});
