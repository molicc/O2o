$(function () {
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;
    var initUrl = "/shopadmin/getshopinitinfo";
    var registerShopUrl = "/shopadmin/registershop";
    var shopInfoUrl = "/shopadmin/getshopbyid?shopId=";
    var editShopUrl = "/shopadmin/modifyshop";
    if (!isEdit) {
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }

    function getShopInitInfo() {
        //获取地区，类别选项
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = "";
                var tempAreaHtml = "";
                data.shopCategoryList.map(function (item) {
                    tempHtml += '<option data-id ="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>>';
                });
                data.areaList.map(function (item) {
                    tempAreaHtml += '<option data-id ="' + item.areaId + '">' + item.areaName + '</option>>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl + shopId, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="' +
                    shop.shopCategory.shopCategoryId + '"selected>' +
                    shop.shopCategory.shopCategoryName + '</option>'

                var tempAreaHtml = '';
                data.areaList.map(function (item) {
                    tempAreaHtml += "<option data-id='" + item.areaId + "'>" + item.areaName + "</option>"
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled')
                $('#area').html(tempAreaHtml);
                $('#area option[data-id="'+shop.area.areaId+'"]').attr("selected","selected")
            }
        })
    }

    //提交点击事件
    $('#submit').click(function () {
        var shop = {};
        if (isEdit) {
            shop.shopId=shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };

        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $('#j_kaptcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url: isEdit ? editShopUrl: registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功');
                } else {
                    $.toast('提交失败' + data.errMsg);
                }
                $('#kaptcha_img').click();
            }

        });
    });
});