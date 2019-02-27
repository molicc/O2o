$(function () {
    var listUrl ="/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999"
    var statusUrl ="/shopadmin/modifyproduct"
    getList();
    function getList() {
        $.getJSON(listUrl,function (data) {
            if (data.success){
                var productList= data.productList
                var tempHtml =''
                productList.map(function (value) {
                    var textOp ="下架"
                    var contraryStatus =0
                    if (value.enableStatus == 0) {
                        textOp ="上架"
                        contraryStatus=1;
                    }else {
                        contraryStatus=0
                    }

                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + value.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + value.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + value.productId
                        + '" data-status="'
                        + value.enableStatus
                        + '">   编辑   </a>'
                        + '<a href="#" class="status" data-id="'
                        + value.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        +    textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + value.productId
                        + '" data-status="'
                        + value.enableStatus
                        + '">   预览   </a>'
                        + '</div>'
                        + '</div>';
                })
                $(".product-warp").html(tempHtml);
            }
        })
    }

    $(".product-warp").on("click","a",function (e) {
        var target =$(e.currentTarget);
        if (target.hasClass("edit")) {
            window.location.href="/shopadmin/productoperation?productId="+e.currentTarget.dataset.id;
        }else if (target.hasClass("status")) {
            changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status)
        }else if (target.hasClass("preview")) {
            window.location.href="/frontend/productdetail?productId="+e.currentTarget.dataset.id;
        }
    })

    function changeItemStatus(id,enableStatus) {
        var product={}
        product.productId=id
        product.enableStatus =enableStatus
        $.confirm("确定么?",function () {
            $.ajax({
                url:statusUrl,
                type:"POST",
                data:{
                    productStr:JSON.stringify(product),
                    statusChange:true
                },
                dataType:"json",
                success:function (data) {
                    if (data.success){
                        $.toast("操作成功")
                        getList()
                    }else {
                        $.toast("操作失败")
                    }
                }
            })
        })

    }
})