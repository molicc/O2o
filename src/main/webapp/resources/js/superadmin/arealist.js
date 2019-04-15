$(function () {
    //定义路由
    var listAreaUrl = "/superadmin/listarea" ;
    var modifAreaUrl = "/superadmin/modifyareabyid";
    var addAreaUrl = "/superadmin/addarea";
    //1、获取区域列表，并拼接到html $.getJSON(url,function(){})
    function getAreaList(){
        $.getJSON(listAreaUrl,function (data) {
            //请求成功，则html平拼接
            if(data.success){
                var count = data.count ;
                var html = '' ;
                data.arealist.map(function (value) {
                    html += ''+
                        '<div class="row no-gutter" style="font-size: 14px">'+
                        '<div class="col-20"'+"area-id="+value.areaId+'>'+value.areaName+'</div>'+
                        '<div class="col-10">'+value.priority+'</div>'+
                        '<div class="col-40" >'+"&emsp;&emsp13;"+new Date(value.lastEditTime).Format("yyyy-MM-dd")+'</div>'+
                        '<div class="col-15"><a class="modify button button-success"'+"area-id="+value.areaId+'>'+"修改"+'</a></div>'+
                        '<div class="col-15"><a class="submit button button-submit"'+"area-id="+value.areaId+'>'+"提交"+'</a></div>'+
                        '</div>'+
                        '<hr/>';
                })
                //添加到area-wrap
                $('.area-wrap').html(html);
            }else {
                $.toast("拉取初始化信息失败");
            }
        })
    }
    getAreaList();
    //2、监听修改按钮，按下确认就修改这个html，让这个html变为添加框，修改类容，做ajax提交
    $(document).on("click",".modify",function (e) {
        $.confirm($(this).attr("area-id"));
    });
    //3、监听添加按钮，按下确认就添加一个html，输入的类容进行ajax提交
    $("#add-area").click(function (e) {
        var tempHtml = ''+
            '<div class="row no-gutter tempHtml" style="font-size: 14px">'
            + '<div class="col-20"><input class="area-name-input" type="text font-size: 14px" placeholder="区域名称"></div>'
            + '<div class="col-10"><input class="priority" type="number font-size: 14px" placeholder="priority"></div>'
            +'<div class="col-40" >'+"&emsp;&emsp;&emsp;"+'</div>'
            + '<div class="col-15"><a href="#" class="button button-success delete">取消</a></div>'
            +'<div class="col-15"><a class="submit button button-submit">'+"提交"+'</a></div>'
            + '</div>'+
            '<hr class="tempHtml"/>';
        $('.area-wrap').append(tempHtml);
    });
    //点击提交，判断类容是否做了修改，修改规范则做提交
    $(document).on('click','.submit',function (e) {
        var area = {} ;
        var tempArr =$(".tempHtml");
        tempArr.map(function (index,item) {
            area.areaName=$(item).find('.area-name-input').val();
            area.priority=$(item).find('.priority').val();
            if (area.areaName!=null&area.priority>=0){
                    $.ajax({
                        url:addAreaUrl,
                        type:'POST',
                        data:area,
                        success:function(data){
                            if(data.success){
                                $.toast("提交成功");
                                //刷新区域列表
                                getAreaList();
                            }else{
                                $.toast(data.errMsg);
                            }

                        }
                    });
            }
        });
    });
    //为新添加的做一个取消监听
    $(document).on("click",".delete",function (e) {
        $('.tempHtml').remove();
    });
    //todo：对修改按钮和提交刷新做改变
    //初始化界面
    //$.init();
});