<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>

    $(function () {
        pageInit();
        //点击发送验证码
        $("#sendMsg").click(function () {

            //1.获取手机号
            var phone = $("#phoneCode").val();

            //2.发送验证码
            $.post("${path}/user/sendCode", {"phone": phone}, function (data) {
                //设置警告信息
                $("#messages").html(data.message);

                //展示警告框，提示信息
                $("#showMsg").show();

                //5秒自动关闭
                setTimeout(function () {
                    //关闭警告框
                    $("#showMsg").hide();
                }, 3000);
            }, "JSON")
        });
    });

    function pageInit() {
        //初始化表单
        $("#userTable").jqGrid({
            url: "${path}/user/showUser",  //接收 page:当前页     rows：每页展示条数
            editurl: "${path}/user/edit",
            datatype: "json",                //返回   page:当前页   rows:数据（List） total:总页数  recoreds:总条数
            rowNum: 2,
            rowList: [2, 3, 10, 5],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "auto",
            pager: '#userPager',  //工具栏
            viewrecords: true,  //是否展示总条数
            colNames: ['Id', '名称', '手机号', '头像', '描述', '微信', '状态', "注册时间", "性别"],
            colModel: [
                {name: 'id', width: 100},
                {name: 'username', width: 60, editable: true},
                {name: 'phone', width: 80, editable: true},
                {
                    name: 'headImg', width: 80, align: "right", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' style='height:70px;width:100px' />";
                    }
                },
                {name: 'brief', width: 80, align: "right", editable: true},
                {name: 'wechat', width: 80, align: "right"},

                /*    {name : 'status',width : 80,align:"center",
                        formatter:function(cellvalue, options, rowObject){
                       // console.log(rowObject);
                            //状态为1    正常     点击冻结
                            //状态为0    冻结     点击解除冻结
                            if(rowObject.status==1){
                                return "<button onclick='updateSta(rowObject.id,rowObject.status)' class='btn btn-success'>冻结</button>";
                            }else{
                                return "<button onclick='updateSta(rowObject.id,rowObject.status)' class='btn btn-danger'>解除冻结</button>";
                            }
                        }
                    },*/
                {
                    name: 'status', width: 80, align: "center",
                    //cellvalue：具体的值，options：操作的属性，rowObject：行对象一行的数据
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == "1") { //正常状态
                            return "<button onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")' class='btn btn-success'>冻结</button>";
                        } else {  //冻结状态
                            return "<button onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")' class='btn btn-danger'>解除冻结</button>";
                        }
                    }
                },
                {
                    name: 'createDate', width: 120, sortable: false,
                    formatter: "date", formatoptions: {newformat: 'Y-m-d'}
                },
                {name: 'sex', width: 30, align: "right", editable: true}
            ]
        });

        //表单增删改查操作
        $("#userTable").jqGrid('navGrid', '#userPager',
            {edit: true, add: true, del: true, addtext: "添加", edittext: "编辑", deltext: "删除"},
            {
                closeAfterEdit: true,  //添加之后关闭对话框
                //文件上传
                afterSubmit: function (reponse) {
                    //异步上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadUserCover",
                        type: "post",
                        data: {id: reponse.responseText},
                        fileElementId: "headImg",  //fileElementId　　　需要上传的文件域的ID，即<input type="file" id=  >的ID。
                        success: function () {
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });

                    //随便返回一个返回值
                    return "hello";
                }
            }, //修改之后额外的操作
            {
                closeAfterAdd: true,  //添加之后关闭对话框
                //文件上传
                afterSubmit: function (reponse) {
                    //异步上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadUserCover",
                        type: "post",
                        data: {id: reponse.responseText},
                        fileElementId: "headImg",  //fileElementId　　　需要上传的文件域的ID，即<input type="file" id=  >的ID。
                        success: function () {
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });

                    //随便返回一个返回值
                    return "hello";
                }
            },  //添加之后额外的操作
            {},  //删除之后额外的操作
        );
    }

    //修改用户状态
    function updateStatus(id, status) {
        // console.log("xiugai");
        //id 修改状态   异步的修改
        if (status == 1) {
            //正常状态  改为冻结 0
            $.ajax({
                url: "${path}/user/editStatus",
                type: "post",
                dataType: "text",
                data: {"id": id, "status": "1", "oper": "edit"},
                success: function () {
                    //刷新页面
                    $("#userTable").trigger("reloadGrid");
                }
            });
        } else {
            //冻结状态  改为正常 1
            $.ajax({
                url: "${path}/user/editStatus",
                type: "post",
                dataType: "text",
                data: {"id": id, "status": "0", "oper": "edit"},
                success: function () {
                    //刷新页面
                    $("#userTable").trigger("reloadGrid");
                }
            });
        }
    }
</script>


<%--初始化面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户管理</h2>
    </div>

    <%--创建选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">用户信息展示</a></li>
    </div>

    <%--面板按钮--%>
    <div class="panel panel-body">
        <button class="btn btn-danger" onclick="location.href='${path}/user/fileExport'">导出用户信息</button>
        <%--手机验证码--%>
        <div class="input-group" style="width: 300px;height: auto;float: right">
            <input type="text" id="phoneCode" class="form-control" placeholder="请输入手机号" aria-describedby="basic-addon2">
            <span class="input-group-addon" id="sendMsg">发送验证码</span>
        </div>
    </div>

    <%--初始化表单--%>
    <table id="userTable"/>

    <%--工具栏--%>
    <div id="userPager"/>

</div>
