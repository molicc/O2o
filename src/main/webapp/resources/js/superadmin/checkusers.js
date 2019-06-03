$(function () {
    //定义路由
    var getListUrl = "/superadmin/querypersoninfolist";
    var modifyInfoUrl = "/superadmin/modifystate";

    function getList() {
        $.getJSON(getListUrl,function (data) {
            if(data.success){
                //var count = data.count ;
                var html = '' ;
                data.personList.map(function (value) {
                    html += ''+
                        '<div class="card">'+
                            '<div class="card-content">'+
                                '<div class="list-block media-list">'+
                                    '<ul>'+
                                        '<li class="item-content">'+
                                            '<div class="item-media">'+
                                                '<img src="'+value.profileImg+'" width="44">'+
                                            '</div>'+
                                            '<div class="item-inner">'+
                                                '<div class="item-title-row">'+
                                                    '<div class="item-title"><strong>'+"用户名:"+'</strong>'+value.name+'</div>'+
                                                '</div>'+
                                                '<div className="item-title-row">'+
                                                    '<div class="item-subtitle"><strong>'+"性别:"+'</strong>'+value.gender+'</div>'+
                                                    '<div class="item-subtitle"><strong>'+"账户类别:"+'</strong>'+getType(value.userType)+'</div>'+
                                                    '<div class="item-subtitle"><strong>'+"用户权限:"+'</strong>'+'<a id="change-status'+value.userId+'">'+getStatus(value.enableStatus)+'</a></div>'+
                                                '</div>'+
                                                '<div class="item-subtitle"><strong>'+"邮箱:"+'</strong>'+value.email+ '</div>'+
                                            '</div>'+
                                        '</li>'+
                                    '</ul>'+
                                '</div>'+
                            '</div>'+
                            '<div class="card-footer">'+
                                '<span>'+"注册时间"+new Date(value.createTime).Format("yyyy-MM-dd")+'</span>'+
                                '<div>'+
                                    '<a href="#" id="change-status" class="button button-fill button-submit" user-id="'+value.userId+'">'+"审核用户"+'</a>'+
                                '</div>'+
                            '</div>'+
                        '</div>'
                });
                //$.toast(html);
                $('.user-warp').append(html);
                //去除加载提示符
                // 加载完毕，则注销无限加载事件，以防不必要的加载
                $.detachInfi;
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
            }else {
                $.toast("拉取信息失败，请重试。。。");
            }
        })
    }
    getList();

    function getType(userType) {
        if(userType == 1){
            return "用户";
        }else if(userType == 2){
            return "商家";
        }else if(userType == 3){
            return "超级管理员";
        }
    }
    function getStatus(enableStatus) {
        if(enableStatus == 1){
            return "允许使用本商场";
        }else if(enableStatus == 0){
            return "禁止使用本商场";
        }
    }
    //审核用户
    //操作店铺
    $(document).on("click","#change-status", function (e) {
        // var advice = oninput;
        var userId = $(this).attr("user-id");
        var modifyUrl = "/superadmin/modifystate";
        var enableStatus = 0 ;
        var buttons1 = [
            {
                text: '请选择',
                label: true
            },
            {
                text: '审核通过',
                onClick: function() {
                    enableStatus = 1 ;
                    $.ajax({
                        url:modifyUrl,
                        data:{userId,enableStatus},
                        type:'GET',
                        success:function (data) {
                            if(data.success){
                                $.toast("审核结果提交成功");
                                var str = '<a id="change-status'+userId+'">'+getStatus(enableStatus)+'</a>';
                                this.$('#change-status'+userId).html(str);
                            }else {
                                $.toast(data.errMsg);
                            }
                        }
                    });
                }
            },
            {
                text: '审核不通过',
                bold: true,
                color: 'danger',
                onClick: function() {
                    enableStatus = 0 ;
                    $.ajax({
                        url:modifyUrl,
                        data:{userId,enableStatus},
                        type:'GET',
                        success:function (data) {
                            if(data.success){
                                var str = '<a id="change-status'+userId+'">'+getStatus(enableStatus)+'</a>';
                                this.$('#change-status'+userId).html(str);//要刷新的div
                                //$(this).attr(getStatus(-1));
                                $.toast("审核结果提交成功");
                            }else {
                                $.toast(data.errMsg);
                            }
                        }
                    });
                }
            }
        ];
        var buttons2 = [
            {
                text: '取消'
            }
        ];
        var groups = [buttons1, buttons2];
        $.actions(groups);
    });
});