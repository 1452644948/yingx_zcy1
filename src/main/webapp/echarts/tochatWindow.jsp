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
    <title>190聊天室</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.6.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript">


        /*初始化GoEasy对象*/
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-49c4bb3ed91945448c35358477615835", //替换为您的应用appkey
        });

        $(function () {
            var contentMsgs;

            //接收消息
            goEasy.subscribe({
                channel: "190toChat", //替换为您自己的channel
                onMessage: function (message) {
                    console.log("Channel:" + message.channel + " content:" + message.content);
                    //获取接收到的内容
                    var receiveMsg = message.content;

                    //判断消息内容是否是你自己发的
                    if (contentMsgs == receiveMsg) {
                        //是  不在左侧展示
                    } else {
                        //不是 展示消息内容
                        //给发送的内容加一个样式
                        var msgStyleDiv = ("<div style=';width:auto;height: 25px;'><div style='float:left;background-color: #ccaadd ;border-radius:10px'>" + receiveMsg + "</div></div>");

                        //向展示框中追加内容
                        $("#showMsg").append(msgStyleDiv);
                    }
                }
            });


            //点击发送按钮触发
            $("#sendMsg").click(function () {

                //获取输入内容
                var content = $("#msgContent").val();

                //给变量赋值
                contentMsgs = content;

                /*浏览器发送消息*/
                goEasy.publish({
                    channel: "190toChat", //替换为您自己的channel
                    message: content, //替换为您想要发送的消息内容
                    onSuccess: function () {

                        //清空输入框
                        $("#msgContent").val("");

                        //给发送的内容加一个样式
                        var msgStyleDiv = ("<div style=';width:auto;height: 25px;'><div style='float:right;background-color: #2aabd2;border-radius:10px'>" + content + "</div></div>");

                        //向展示框中追加内容
                        $("#showMsg").append(msgStyleDiv);

                    },
                    onFailed: function (error) {
                        alert("消息发送失败，错误编码：" + error.code + " 错误信息：" + error.content);
                    }
                });


            })


        });


    </script>
<body>
<div align="center">
    <h1>190聊天室</h1>
    <%--聊天框--%>
    <div style="width: 500px;height: 600px;border:3px #ccaadd solid ">
        <%--聊天内容展示区域--%>
        <div id="showMsg" style="width: 494px;height: 500px;border:3px #2aabd2 solid "></div>
        <%--输入内容发送区域--%>
        <div style="width: 494px;height: 88px;border:3px #4cae4c solid ">
            <%--输入框--%>
            <textarea id="msgContent" style="width: 350px;height: 80px;border:3px #e4b9b9 solid "></textarea>
            <%--发送按钮--%>
            <button id="sendMsg"
                    style="width: 60px;height: 60px;border:3px #b4d100 solid;float: top;border-radius:10px ">发送
            </button>
        </div>

    </div>

</div>
</body>
</html>
