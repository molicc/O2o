$(function () {
    //定义路由
    var listAreaUrl = "/superadmin/listarea" ;
    var modifyAreaUrl = "/superadmin/modifyareabyid";
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
                        '<div class="row no-gutter html" style="font-size: 14px">'+
                        '<div class="col-20">'+value.areaName+'</div>'+
                        '<div class="col-10">'+value.priority+'</div>'+
                        '<div class="col-40" >'+"&emsp;&emsp13;"+new Date(value.lastEditTime).Format("yyyy-MM-dd")+'</div>'+
                        '<div class="col-15"><a class="modify button button-success"'+"area-name="+value.areaName+
                                                                                      " area-id="+value.areaId+
                                                                                      " priority="+value.priority+
                                                                                      " last-edit-time="+new Date(value.lastEditTime).Format("yyyy-MM-dd")+'>'+"修改"+'</a></div>'+
                        '<div class="col-15"><a class="submit button button-submit"'+"area-id="+value.areaId+'>'+"提交"+'</a></div>'+
                        '</div>'+
                        '<hr class="html"/>';
                })
                //添加到area-wrap
                $('.area-wrap').html(html);
            }else {
                $.toast("拉取初始化信息失败");
            }
        })
    }
    //初始化界面
    getAreaList();
    //监听添加按钮，按下确认就添加一个html，输入的类容进行ajax提交
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
    $(document).on('click','.submit',function () {
        $.confirm("确认提交吗？",function () {
            var area = {} ;
            var tempArr =$(".tempHtml");
            tempArr.map(function (index,item) {
                area.areaName=$(item).find('.area-name-input').val();
                area.priority=$(item).find('.priority').val();
                if (area.areaName!=null&&area.priority>=0){
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
        })

    });
    //为新添加的做一个取消监听
    $(document).on("click",".delete",function () {
        $.confirm("确认取消么？",function() {
            $('.tempHtml').remove();
            getAreaList();
        })
    });
    //监听修改按钮，按下确认就修改这个html，让这个html变为添加框，修改类容，做ajax提交
    $(document).on('click','.modify',function (e) {
        //点击修改则新增一个修改框，并且把原来的类容放进去，而且需要把id放在里面
        $('.html').remove();
        var tempHtml = ''+
            '<div class="row no-gutter tempHtml" style="font-size: 14px">'
            + '<div class="col-20"><input class="area-name-input" type="text font-size: 14px" placeholder="'+$(this).attr('area-name')+'"></div>'
            + '<div class="col-10"><input class="priority" type="number font-size: 14px" placeholder="'+$(this).attr('priority')+'"></div>'
            +'<div class="col-40" >'+"&emsp;&emsp13;"+$(this).attr('last-edit-time')+'</div>'
            + '<div class="col-15"><a href="#" class="button button-success delete">取消</a></div>'
            +'<div class="col-15"><a class="submit-modify button button-submit"'+"area-id="+$(this).attr('area-id')+'>'+"提交"+'</a></div>'
            + '</div>'+
            '<hr class="tempHtml"/>';
        $('.area-wrap').append(tempHtml);
        //$.toast($(this).attr('area-name'));

    });
    //修改数据的ajax提交
    $(document).on('click','.submit-modify',function () {
        var area = {} ;
        var tempArr =$(".tempHtml");
        area.areaId=$(this).attr('area-id');
        $.confirm("确认提交么",function () {
            tempArr.map(function (index,item) {
                //没有填写，则传空
                if($(item).find('.area-name-input').val()){
                    area.areaName=$(item).find('.area-name-input').val();
                }else {
                    //area.areaName="nulll" ;
                }
                if($(item).find('.priority').val()){
                    area.priority=$(item).find('.priority').val();
                }else {
                    area.priority = 0 ;
                }
                if (area.areaId!=null){
                    $.ajax({
                        url:modifyAreaUrl,
                        type:'GET',
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
        })
    })

});