$(function () {
    var productId = getQueryString("productId")
    var infoUrl = "/shopadmin/getproductbyid?productId=";
    var categoryUrl = "/shopadmin/getproductcategorylist"
    var productPostUrl = "/shopadmin/modifyproduct"

    //商品添加和编辑使用的统一页面，通过productId来进行区别添加和编辑操作
    var isEdit = false;
    if (productId) {
        getInfo(productId);
        isEdit = true;
    } else {
        getCategory();
        productPostUrl = "/shopadmin/addproduct"
    }

    function getInfo(id) {
        $.getJSON(infoUrl + id, function (data) {
            if (data.success) {
                var product = data.product;
                $("#product-name").val(product.productName);
                $("#product-desc").val(product.productDesc);
                $("#priority").val(product.priority);
                $("#point").val(product.point);
                $("#normal-price").val(product.normalPrice);
                $("#promotion-price").val(product.promotionPrice);

                var optionHtml = "";
                var optionArr = data.productCategoryList;
                if (product.productCategory == null) {
                    optionHtml += '<option data-value =null selected>' +
                        "请选择" +
                        '</option>'
                    optionArr.map(function (value) {

                        optionHtml +=
                            '<option data-value ="' +
                            value.productCategoryId +
                            '" isSeleted>' +
                            value.productCategoryName +
                            '</option>'
                    })
                }
                else {
                    var optionSelected = product.productCategory.productCategoryId;
                    optionArr.map(function (value) {

                        var isSeleted = optionSelected === value.productCategoryId ? "selected" : "";

                        optionHtml +=
                            '<option data-value ="' +
                            value.productCategoryId +
                            '" isSeleted>' +
                            value.productCategoryName +
                            '</option>'
                    })
                }
                $("#category").html(optionHtml);
            }
        })
    }

    //为商品添加操作提供该店铺下的所有商品类别列表
    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = "";
                productCategoryList.map(function (value) {
                    optionHtml +=
                        '<option data-value="' +
                        value.productCategoryId +
                        '">' +
                        value.productCategoryName +
                        '</option>'
                });
                $("#category").html(optionHtml);
            }
        })
    };

    //针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），且控件总数未达到6个，则生成新的一个文件上传控件
    $(".detail-img-div").on("change", ".detail-img:last-child", function () {
        if ($(".detail-img").length < 6) {
            $("#detail-img").append("<input type='file' class='detail-img'>")
        }
    });

    $("#submit").click(function () {
        var product = {};
        product.productName = $("#product-name").val();
        product.productDesc = $("#product-desc").val();
        product.priority = $("#priority").val();
        product.point = $("#point").val();
        product.normalPrice = $("#normal-price").val();
        product.promotionPrice = $("#promotion-price").val();

        product.productCategory = {
            productCategoryId: $("#category").find("option").not(
                function () {
                    return !this.selected;
                }
            ).data("value")
        };
        product.productId = productId;

        var formData = new FormData();
        var thumbnail = $("#small-img")[0].files[0];
        formData.append("thumbnail", thumbnail);
        $(".detail-img").map(
            function (index, value) {
                if ($(".detail-img")[index].files.length > 0) {
                    formData.append("productImg" + index, $(".detail-img")[index].files[0]);
                }
            }
        );
        formData.append("productStr", JSON.stringify(product));
        var verifyCodeActual = $("#j_kaptcha").val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码")
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: productPostUrl,
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功")
                    window.history.go(-1);
                } else {
                    $.toast("提交失败," + data.errMsg)
                }
                $("#kaptcha_img").click();
            }
        })
    })


});
