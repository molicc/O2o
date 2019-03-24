$(function () {
    $("#log-out").click(function () {
        $.ajax({
            url:"/local/logout",
            type:"post",
            async:false,
            cache:false,
            dataType:"json",
            success:function (data) {
                if (data.success){
                    //var usertype=$("#log-out").attr("usertype");
                    window.location.href="/local/login";
                    return false;
                }
            },
            error:function (data, error) {
                alert(error);
            }
        })
    })
});