$(function () {
    var listUrl = '/shopadmin/getproductcategorylist';
    var addUrl='/shopadmin/addproductcategorys';
    var deleteUrl='/shopadmin/removeproductcategory';
    getList();

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                var tempHtml = '';
                $(".category-wrap").html(tempHtml);
                dataList.map(function (value) {
                        tempHtml +=
                            '<div class="row row-product-category now">'
                            + '<div class="col-33 product-category-name">'
                            + value.productCategoryName
                            + '</div>'
                            + '<div class="col-33">'
                            + value.priority
                            + '</div>'
                            + '<div class="col-33"><a href="#" class="button delete" data-id="'
                            + value.productCategoryId
                            + '">删除</a></div>' + '</div>';
                });
                $(".category-wrap").append(tempHtml);
            }
        });

    }

    $("#new").click(function () {
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
            + '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $(".category-wrap").append(tempHtml);
    });
    $("#submit").click(function () {
        var tempArr =$(".temp")
        var productCategoryList=[];
        tempArr.map(function (index,item) {
            var tempObj={};
            tempObj.productCategoryName=$(item).find('.category').val();
            tempObj.priority=$(item).find('.priority').val();
            if (tempObj.productCategoryName&&tempObj.priority){
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url:addUrl,
            type:'POST',
            data:JSON.stringify(productCategoryList),
            contentType:'application/json',
            success:function (data) {
                if (data.success==true){
                    $.toast("提交成功");
                    //提交成功，才进行html赋值
                    getList();
                }else {
                    $.toast(data.errMsg);
                }
            }
        })
    });

    //后期生成的html不可以直接绑定事件，需要这样绑定
    $(".category-wrap").on("click",".delete",function (e) {
        var target =e.currentTarget;
        $.confirm("确定删除?",function () {
            var productCategoryId=target.dataset.id;
            if (productCategoryId){
                $.ajax({
                    url:deleteUrl,
                    type:"POST",
                    data:{productCategoryId:productCategoryId},
                    success:function (data) {
                        if (data.success){
                            $.toast("删除成功")
                        } else {
                            $.toast("删除失败,"+data.errMsg)
                        }
                        getList();

                    }
                })
            }else{
                //todo
                $(e).parent.parent.removeChild();
            }
        })
    });


});

