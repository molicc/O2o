$(function () {
    var url = "/superadmin/getshoplistignorestatus";
    //加载页数
    var pageNum = 1;
    //一页加载五条
    var pageSize = 3;
    //最大店铺加载数，初始化为0
    //var maxItems = 0 ;
    var loading = false ;
    //根据分页参数获取店铺
    function getItems(pageIndex,pageSize) {
        //根据分页参数拼接url
        loading = true ;
        url = url+"?"+"pageIndex="+pageIndex+"&pageSize="+pageSize ;
        $.getJSON(url,function (data) {
            if (data.success){
                //获取店铺数量，作为无极加载的最大的加载数
                maxItems = data.shopExecution.count;
                var html = '';
                //遍历该shoplist，拼接出商店列表
                data.shopExecution.shopList.map(function (value) {
                    html += ''+
                        '<div class="card">'+
                            '<div class="card-header">'+"店铺ID:"+
                            '<strong>'+value.shopId+'</strong>'+"店铺名称:"+'<strong>'+value.shopName+'</strong></div>'+
                            '<div class="card-content">'+
                                '<div class="list-block media-list">'+
                                    '<ul>'+
                                        '<li class="item-content">'+
                                            '<div class="item-media">'+
                                                '<img src="'+value.shopImg+'" width="44">'+
                                            '</div>'+
                                            '<div class="item-inner">'+
                                                '<div class="item-title-row">'+
                                                    '<div class="item-title">'+"店主:"+'<strong>'+value.owner.name+'</strong></div>'+
                                                '</div>'+
                                                '<div class="item-subtitle1" style="font-size:10px">'+"所属区域:"+'<strong>'+value.area.areaName+'</strong></div>'+
                                                '<div class="item-subtitle2" style="font-size:10px">'+"店铺类别:"+'<strong>'+value.shopCategory.shopCategoryName+'</strong></div>'+
                                                '<div class="item-subtitle3" style="font-size:10px">'+"联系电话:"+'<strong>'+value.phone+'</strong></div>'+
                                                '<div class="item-subtitle4" style="font-size:10px">'+"店铺简介:"+'<strong>'+value.shopDesc+'</strong></div>'+
                                                 '<div class="item-subtitle5" style="font-size:10px">'+"当前状态:"+'<strong id="change-status'+value.shopId+'">'+getStatus(value.enableStatus)+'</strong></div>'+
                                            '</div>'+
                                        '</li>'+
                                    '</ul>'+
                                '</div>'+
                            '</div>'+
                            '<div class="card-footer">'+
                                '<span>'+"注册时间:"+new Date(value.createTime).Format("yyyy-MM-dd")+'</span>'+
                                '<div >'+
                                    '<a href="#" id="change-status" class="button button-fill button-submit " shop-id="'+
                                    value.shopId+'">'+"审核店铺"+'</a>'+
                                '</div>'+
                            '</div>'+
                        '</div>';
                });
                $(".shop-warp").append(html);
            }
            //如果取出的数据没有pageSize，则去除加载符
            if(pageSize*pageNum >= maxItems){
                // 加载完毕，则注销无限加载事件，以防不必要的加载
                $.detachInfiniteScroll($('.infinite-scroll'));
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
                return ;
            }
        });
        pageNum += 1 ;
        loading = false ;
        url = "/superadmin/getshoplistignorestatus";
    }
    //先加载1条
    getItems(pageNum,pageSize);

    $(document).on('infinite', '.infinite-scroll', function () {
        alert("无限加载");
        if (loading)
            return;
        //否则继续加载
        getItems(pageNum, pageSize);
    });
    //根据状态信息返回店铺状态字符串-1：审核未通过，0：审核中，1：审核通过

    function getStatus(enableStatus) {
        if(enableStatus == -1){
            return "审核未通过";
        }else if(enableStatus == 0){
            return "审核中";
        }else if(enableStatus == 1){
            return "审核通过";
        }
    }
    //操作店铺
    $(document).on("click","#change-status", function (e) {
        // var advice = oninput;
        var shopId = $(this).attr("shop-id");
        var modifyUrl = "/superadmin/modifyshopstate";
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
                        data:{shopId,enableStatus},
                        type:'GET',
                        success:function (data) {
                            if(data.success){
                                $.toast("审核结果提交成功");
                                var str = '<strong id="change-status'+shopId+'">'+getStatus(1)+'</strong>';
                                $('#change-status'+shopId).html(str);
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
                    enableStatus = -1 ;
                    $.ajax({
                        url:modifyUrl,
                        data:{shopId,enableStatus},
                        type:'GET',
                        success:function (data) {
                            if(data.success){
                                var str = '<strong id="change-status'+shopId+'">'+getStatus(-1)+'</strong>';
                                this.$('#change-status'+shopId).html(str);//要刷新的div
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
    //初始化界面
    $.init();
});