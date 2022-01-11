<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
        $(".time").datetimepicker({
            minView: "month",
            language:  'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            pickerPosition: "buttom-left"
        });
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});

		$("#openSearchActivity").click(function () {
            $("#SearchActivity").val("");
            var name=$("#SearchActivity").val();
            $.ajax({
                url:"workbench/clue/getActivityListByName.do",
                data:{"name":name},
                type:"post",
                dataType:"json",
                success:function (data) {
                    if (data.total>0){
						var html="";
						$.each(data.dataList,function (i,n) {
							html+='<tr>';
							html+='<td><input type="radio" name="xz" value="'+n.id+'"/></td>';
							html+='<td id="n'+n.id+'">'+n.name+'</td>';
							html+='<td>'+n.startDate+'</td>';
							html+='<td>'+n.endDate+'</td>';
							html+='<td>'+n.owner+'</td>';
							html+='</tr>';
                        })
						$("#activityBody").html(html);
						$("#searchActivityModal").modal("show");
					} else {
                        alert("无市场活动源")
					}
                }
            })
        })
		$("#SearchActivity").keydown(function (event) {
			if (event.keyCode==13){
			    // alert(1)
                var name=$("#SearchActivity").val();//一个选择器的问题，找个20分钟
                $.ajax({
                    url:"workbench/clue/getActivityListByName.do",
                    data:{"name":name},
                    type:"post",
                    dataType:"json",
                    success:function (data) {
                        if (data.total>0){
                            var html="";
                            $.each(data.dataList,function (i,n) {
                                html+='<tr>';
                                html+='<td><input type="radio" name="xz"/></td>';
                                html+='<td>'+n.name+'</td>';
                                html+='<td>'+n.startDate+'</td>';
                                html+='<td>'+n.endDate+'</td>';
                                html+='<td>'+n.owner+'</td>';
                                html+='</tr>';
                            })
                            $("#activityBody").html(html);
                        } else {
                            alert("无市场活动源")
                        }
                    }
                })
				return false;
			}
        })
        $("#closeSearchModal").click(function () {
            $("#SearchActivity").val("");
            $("#searchActivityModal").modal("hide");
        })
		$("#submitActivityBtn").click(function () {
			var id=$("input[name=xz]:checked").val();
			var name=$("#n"+id).html();
			$("#activityName").val(name);
            $("#searchActivityModal").modal("hide");
        })
		$("#convertBtn").click(function () {
		    if ($("#isCreateTransaction").prop("checked")) {
               // window.location.href="workbench/clue/convertDel.do?clueId=${c.id}&name=xxx&stage=xxx&money=xxx&activityId=xxx";
				//以上传递参数的方式很麻烦，而且表单一旦扩充，挂载的参数有可能超出浏览器地址栏的上限
				$("#tranForm").submit();
		    }else{
		        window.location.href="workbench/clue/convertDel.do?clueId=${c.id}";
			}
        })
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="SearchActivity" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activityBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="closeSearchModal">取消</button>
					<button type="button" class="btn btn-primary" id="submitActivityBtn">关联</button>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${c.fullname}${c.appellation}-${c.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${c.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${c.fullname}${c.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form id="tranForm" action="workbench/clue/convertDel.do" method="post">
			<input type="hidden" name="flag" value="a">
			<input type="hidden" id="clueId" value="${c.id}">
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="money" name="money">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="name" name="name" value="${c.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedDate" name="expectedDate" readonly>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage" name="stage" class="form-control">
		    	<option></option>
				<a:forEach items="${stageList}" var="s">
					<option value="${s.value}">${s.text}</option>
				</a:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="openSearchActivity" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
		  	<input type="hidden" id="activityId" name="activityId">
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${c.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" id="convertBtn" type="button" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>