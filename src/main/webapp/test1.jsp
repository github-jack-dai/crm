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
    success:function (data) {
    }
})
if (data){
alert("成功");
}else {
alert("失败");
}
boolean flag=true;
if (count!=1){
flag=false;
}
</body>
</html>
