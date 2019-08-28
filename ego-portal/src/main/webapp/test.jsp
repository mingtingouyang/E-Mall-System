<%--
  Created by IntelliJ IDEA.
  User: carlosouyang
  Date: 19-8-26
  Time: 上午12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta content="text/html" http-equiv="Content-Type"/>
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
    <textarea id="text" style="width: 1200px; height: 200px"></textarea>
    <input type="button" value="测试异步跨域请求" onclick="testAjax()"/>
    <script type="text/javascript">
        function testAjax() {
            $.ajax({
                url: "http://localhost:8081/category.json",
                type: "GET",
                dataType: "jsonp",  //jsonp请求
                jsonp: "callbackFunction",  //请求参数名
                jsonpCallback: "showData",  //回调函数名称，也可以直接用 success 接受参数
                /*success: function (data) {
                    $('#text').val(JSON.stringify(data));
                }*/
            })
        }
        function showData(data) {
            $('#text').val(JSON.stringify(data));
        }
    </script>
</body>
</html>
