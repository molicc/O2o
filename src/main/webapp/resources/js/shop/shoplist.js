$(function () {
    getlist();
    function getlist(e) {
        $.ajax({
            url: "/shopadmin/getshoplist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        })
    }

    function handleUser(user) {
        $("#user-name").text(user.name);
    }

    function handleList(shopList) {
        var html = '';
        shopList.map(function (value) {
            html +=
                '<div class="row row-shop"> <div class="col-40">' +
                value.shopName + '</div> <div class="col-40">' +
                shopStatus(value.enableStatus) + '</div> <div class="col-20">' +
                goShop(value.enableStatus, value.shopId) + '</div> </div>'
        });

        $(".shop-warp").html(html);
    }

    function shopStatus(enableStatus) {
        if (enableStatus==0){
            return '审核中'
        } else if (enableStatus==-1){
            return '店铺非法'
        }else if (enableStatus == 1) {
            return '审核通过'
        }
    }

    function goShop(enableStatus,shopId) {
        if (enableStatus==1){
            return '<a href="javascript:location.href=`/shopadmin/shopmanagement?shopId=' +shopId+'`">进入</a>';
        }else {
            return "";
        }
    }
});