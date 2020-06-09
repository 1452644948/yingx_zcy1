<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<%--<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.6.js"></script>--%>
<!-- 引入 ECharts 文件 -->
<script src="${path}/bootstrap/js/goeasy-1.0.5.js"></script>
<script src="${path}/bootstrap/js/echarts.js"></script>
<%--<script src="${path}/bootstrap/js/jquery.min.js"></script>--%>
<script type="text/javascript">
    /*初始化GoEasy对象*/
    var goEasy = new GoEasy({
        host: 'http://rest-hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-50256662ac7e4725a0e3816b126e7d8b", //替换为您的应用appkey
    });

    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        /*浏览器接收消息*/
        goEasy.subscribe({
            channel: "yingx_zcy", //替换为您自己的channel
            onMessage: function (message) {
                //alert("Channel:" + message.channel + " content:" + message.content);

                var datas = message.content;
                //将json字符串转为JavaScript对象
                var data = JSON.parse(datas);

                /*// 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量统计图', //标题
                        show:true,
                        link:"
                ${path}/main/main.jsp",
                        subtext:"纯属虚构",
                    },
                    tooltip: {
                        show:true,
                    },  //鼠标提示
                    legend: {
                        data:['小男孩','小女孩']  //选项卡
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},
                    series: [{
                        name: '小男孩',
                        type: 'bar', //bar 柱状图  line  折线图
                        data: data.boys
                    },{
                        name: '小女孩',
                        type: 'bar',
                        data: data.girls
                    }]
                };*/

                // 使用刚指定的配置项和数据显示图表。
                //  myChart.setOption(option);
            }
        });
    });
</script>

<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        $.get("${path}/echarts/getEchartsUserData", function (data) {

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
                    data: data.month
                },
                yAxis: {},
                series: [{
                    name: '小男孩',
                    type: 'bar', //bar 柱状图  line  折线图
                    data: data.boys
                }, {
                    name: '小女孩',
                    type: 'bar',
                    data: data.girls
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        }, "JSON");
    });
</script>

<div align="center">
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
</div>


