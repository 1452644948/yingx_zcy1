<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>

    $(function () {
        pageInit();
    });

    function pageInit() {
        //初始化表单
        $("#userTable").jqGrid({
            url: "${path}/log",  //接收 page:当前页     rows：每页展示条数
            datatype: "json",                //返回   page:当前页   rows:数据（List） total:总页数  recoreds:总条数
            rowNum: 2,
            rowList: [2, 3, 10, 5],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "auto",
            pager: '#userPager',  //工具栏
            viewrecords: true,  //是否展示总条数
            colNames: ['Id', '管理员名称', '时间', '操作', '是否成功'],
            colModel: [
                {name: 'id', width: 200},
                {name: 'adminName', width: 160},
                {name: 'times', width: 180},
                {name: 'description', width: 180, align: "right"},
                {name: 'status', width: 180, align: "right"},
            ]
        });

    }

</script>


<%--初始化面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志管理</h2>
    </div>

    <%--创建选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">日志展示</a></li>
    </div>


    <%--初始化表单--%>
    <table id="userTable"/>

    <%--工具栏--%>
    <div id="userPager"/>

</div>
