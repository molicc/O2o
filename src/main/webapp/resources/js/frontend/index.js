$(function () {
    //定义访问后台，获取头条列表以及一级类别列表的url
    var url = "/frontend/listmainpageinfo"
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList
            var swiperHtml = ""
            headLineList.map(function (value) {
                swiperHtml += ''
                    + '<div class="swiper-slide img-wrap">' +
                    '<a href="' + value.lineLink
                    + '" external>'
                    + '<img class="banner-img" src="' + value.lineImg + '" alt="' + value.lineName + '">'
                    + '</div>';
            });
            //将轮播图组赋值给前端HTML控件
            $(".swiper-wrapper").html(swiperHtml)
            //设定轮播图轮换时间为3秒
            $(".swiper-container").swiper({
                autoplay:3000,
                autoplayDisableOnInteraction:false
            })
            //获取后台传递过来的大类列表
            var shopCategoryList =data.shopCategoryList
            var categoryHtml =""
            //遍历大类列表，拼接类别
            shopCategoryList.map(function (value) {
                categoryHtml += ''
                    +  '<div class="col-50 shop-classify" data-category='+ value.shopCategoryId +'>'
                    +      '<div class="word">'
                    +          '<p class="shop-title">'+ value.shopCategoryName +'</p>'
                    +          '<p class="shop-desc">'+ value.shopCategoryDesc +'</p>'
                    +      '</div>'
                    +      '<div class="shop-classify-img-warp">'
                    +          '<img class="shop-img" src="'+ value.shopCategoryImg +'">'
                    +      '</div>'
                    +  '</div>';
            });
            //将拼接好的类别赋值给前端HTML控件
            $(".row").html(categoryHtml)
        }
    });
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    })
    $(".row").on("click",".shop-classify",function (e) {
        var shopCategoryId =e.currentTarget.dataset.category;
        //var newUrl ="/frontend/shoplist?parentId="+shopCategoryId;
        var newUrl ="/frontend/shoplist";
        window.location.href=newUrl
    })
})