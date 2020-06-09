<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Echarts</title>
    <!-- 引入 ECharts 文件 -->
    <script src="${path}/bootstrap/js/echarts.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户注册量统计图', //标题
                    show: true,
                    link: "${path}/main/main.jsp",
                    subtext: "纯属虚构",
                },
                tooltip: {
                    show: true,
                },  //鼠标提示
                legend: {
                    data: ['小男孩', '小女孩']  //选项卡
                },
                xAxis: {
                    data: ["1月", "2月", "3月", "4月", "5月", "6月"]
                },
                yAxis: {},
                series: [{
                    name: '小男孩',
                    type: 'line', //bar 柱状图  line  折线图
                    data: [5, 20, 36, 10, 10, 20]
                }, {
                    name: '小女孩',
                    type: 'line',
                    data: [5, 29, 36, 60, 10, 70]
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>

