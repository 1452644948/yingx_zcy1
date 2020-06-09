<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学视频APP后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>


</head>
<body>
<!--顶部导航-->
<div class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" title="logoTitle" href="#">应学视频APP后台管理系统</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li role="presentation">
            <a href="#">欢迎：<span class="badge">${sessionScope.admin.admin_name}</span></a>
        </li>
        <li>
            <a href="${path}/admin/exit">
                退出<span class="glyphicon glyphicon-share"></span></a>
        </li>
    </ul>
</div>

<!--栅格系统-->
<!--左边手风琴部分-->
<div class="pageContainer">
    <%--手风琴--%>
    <div class="row">
        <div class="col-md-2">
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <a href="#tg1" class="panel-title" data-toggle="collapse" data-parent="#acc">
                        <center>用户管理</center>
                    </a>
                </div>
                <div class="panel-collapse collapse" id="tg1" align="center">
                    <div class="panel-body">
                        <button type="button" class="btn btn-warning" id="btn1">
                            <a href="javascript:$('#showId').load('${path}/user/showUser.jsp')">用户展示</a>
                        </button>
                        <br><br>
                        <button type="button" class="btn btn-warning" id="btn2">
                            <a href="javascript:$('#showId').load('${path}/echarts/GoEasyEchartsContr.jsp')">用户统计</a>
                        </button>
                        <br><br>
                        <button type="button" class="btn btn-warning" id="btn3">
                            <a href="javascript:$('#showId').load('${path}/echarts/EchartsMapJsonController.jsp')">用户分布</a>
                        </button>
                        <br>
                    </div>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <a href="#tg2" class="panel-title" data-toggle="collapse" data-parent="#acc">
                        <center>分类管理</center>
                    </a>
                </div>
                <div class="panel-collapse collapse" id="tg2">
                    <div class="panel-body" align="center">
                        <button type="button" class="btn btn-success" id="btn4">
                            <a href="javascript:$('#showId').load('${path}/category/showCategory.jsp')">分类展示</a>
                        </button>
                        <br>
                    </div>
                </div>
            </div>

            <div class="panel panel-warning">
                <%--<div class="panel-heading">
                    <a href="#tg3" class="panel-title" data-toggle="collapse" data-parent="#acc"><center>视频管理</center></a>
                </div>--%>
                <div class="panel-heading" align="center">
                    <a href="#tg3" class="panel-title" data-toggle="collapse" data-parent="#acc">视频管理</a>
                </div>
                <div class="panel-collapse collapse" id="tg3">
                    <div class="panel-body" align="center">
                        <button type="button" class="btn btn-warning">
                            <a href="javascript:$('#showId').load('${path}/video/showVideo.jsp')">视频展示</a>
                        </button>
                        <br>
                        <button type="button" class="btn btn-warning">
                            <a href="javascript:$('#showId').load('${path}/video/videoSearch.jsp')">视频检索</a>
                        </button>
                        <br>
                    </div>
                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <a href="#tg5" class="panel-title" data-toggle="collapse" data-parent="#acc">
                        <center>日志管理</center>
                    </a>
                </div>
                <div class="panel-collapse collapse" id="tg5">
                    <div class="panel-body" align="center">
                        <button type="button" class="btn btn-info">
                            <a href="javascript:$('#showId').load('${path}/log/showLog.jsp')">日志展示</a>
                        </button>
                        <br>
                    </div>
                </div>
            </div>

        </div>

        <div class="col-md-9" id="showId">
            <div class="jumbotron">
                <h2>
                    <center>欢迎来到应学视频APP后台管理系统</center>
                </h2>
            </div>

            <div class="carousel slide" data-ride="carousel" data-interval="2000" id="cl">
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${path}/image/8.jpg" alt="" style="width: 100%;height: 700px">
                    </div>
                    <div class="item">
                        <img src="${path}/image/5.jpg" alt="" style="width: 100%;height: 700px">
                    </div>
                    <div class="item">
                        <img src="${path}/image/11.jpg" alt="" style="width: 100%;height: 700px">
                    </div>
                </div>
                <a href="#cl" class="carousel-control left" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left">
                        </span>
                </a>
                <a href="#cl" class="carousel-control right" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right">
                        </span>
                </a>
            </div>
        </div>
    </div>
</div>
<!--页脚-->
<div class="panel panel-footer" align="center">
    <div>@百知教育 zhaocy@zparkhr.com</div>
</div>

</div>
</body>
</html>
