<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
String success=request.getParameter("success");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	$(function(){
	    //一个细节的优化
        var success=<%=success%>;
		<%--console.log(success);--%>
			<%--var flag=false;--%>
		<%--console.log(1+'${success}');--%>
		<%--alert(${"success"});--%>
       /* 这个取参的方式才是正确的，我之前不加双引号
       的方法是错误的，那种是用来从四大域中取对象的,
       而加了双引号的方法相当于取一个普通的字符串，
       老师之前讲过这些内容，我却给忘了，用request
       的方法取参也可以，不过麻烦了点，多种选择也好
        */
		if (success){//这个弹窗原理是只有success不为空就可以弹窗提示
		    alert("添加成功");
        }
        $("#qx").click(function () {
            $("input[name=xz]").prop("checked",this.checked);
        })
        $("#tranBody").on("click",$("input[name=xz]"),function () {
            $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
        })
        //关于分页的一系列代码
        $("#searchBtn").click(function () {
            $("#hidden-contactsName").val($.trim($("#search-contactsName").val()));
            $("#hidden-owner").val($.trim($("#search-owner").val()));
            $("#hidden-customerName").val($.trim($("#search-customerName").val()));
            $("#hidden-transactionType").val($.trim($("#search-transactionType").val()));
            $("#hidden-source").val($.trim($("#search-source").val()));
            $("#hidden-stage").val($.trim($("#search-stage").val()));
            $("#hidden-name").val($.trim($("#search-name").val()));
            pageList(1,$("#tranPage").bs_pagination('getOption', 'rowsPerPage'));
        })
		pageList(1,2);
        function pageList(pageNo,pageSize) {
            //把全选的复选框的√给他干掉
            $("#qx").prop("checked",false);
            $("#search-contactsName").val($.trim($("#hidden-contactsName").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-customerName").val($.trim($("#hidden-customerName").val()));
            $("#search-transactionType").val($.trim($("#hidden-transactionType").val()));
            $("#search-source").val($.trim($("#hidden-source").val()));
            $("#search-stage").val($.trim($("#hidden-stage").val()));
            $("#search-name").val($.trim($("#hidden-name").val()));
            $.ajax({
                url:"workbench/transaction/pageList.do",//请求地址不能以/开头，会变绝对路径的
                data:{
                    "pageNo":pageNo,
                    "pageSize":pageSize,
                    "name":$.trim($("#search-name").val()),
                    "owner":$.trim($("#search-owner").val()),
                    "contactsId":$.trim($("#search-contactsName").val()),//这两个属性单独接收
                    "customerId":$.trim($("#search-customerName").val()),
                    "type":$.trim($("#search-transactionType").val()),
                    "source":$.trim($("#search-source").val()),
                    "stage":$.trim($("#search-stage").val())
                },
                type:"get",
                dataType:"json",
                success:function (data) {
                    var html="";
                    $.each(data.dataList,function (i,n) {
                        html+='<tr>';
                        html+='<td><input type="checkbox" name="xz"/></td>';
                        html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/transaction/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
                        html+='<td>'+n.customerId+'</td>';
                        html+='<td>'+n.stage+'</td>';
                        html+='<td>'+n.type+'</td>';
                        html+='<td>'+n.owner+'</td>';
                        html+='<td>'+n.source+'</td>';
                        html+='<td>'+n.contactsId+'</td>';
                        html+='</tr>';
                    })
                    $("#tranBody").html(html);
                    // var totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
                    var totalPages = data.total%pageSize==0 ? data.total/pageSize :parseInt(data.total/pageSize)+1;

                    $("#tranPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage : function(event, data){
                            pageList(data.currentPage , data.rowsPerPage);
                        }
                    });
                }
            })
        }

	});

</script>
</head>
<body>

<!--隐藏域，存储中间变量-->
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-customerName"/>
<input type="hidden" id="hidden-stage"/>
<input type="hidden" id="hidden-contactsName"/>
<input type="hidden" id="hidden-source"/>
<input type="hidden" id="hidden-transactionType"/>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="search-customerName">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="search-stage">
					  	<option></option>
                          <c:forEach items="${stageList}" var="s">
                              <option value="${s.value}">${s.text}</option>
                          </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="search-transactionType">
					  	<option></option>
                          <c:forEach items="${transactionTypeList}" var="t">
                              <option value="${t.value}">${t.text}</option>
                          </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="search-source">
						  <option></option>
						  <c:forEach items="${sourceList}" var="so">
                              <option value="${so.value}">${so.text}</option>
                          </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="search-contactsName">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/jumpSave.do';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='workbench/transaction/edit.jsp';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tranBody">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/transaction/detail.do';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 20px;">
				<div id="tranPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>