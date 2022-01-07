<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>myTitle</title>
</head>
<body>
$.ajax({
    url:"",
    data:{},
    type:"",
    dataType:"json",
    success:function (resp) {
    }
})
</body>
</html>
