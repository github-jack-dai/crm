<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
	Map<String,String> pMap=(Map<String,String>)application.getAttribute("pMap");
	Set<String> keys=pMap.keySet();
	Iterator<String> it=keys.iterator();

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
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

<script type="text/javascript">

		var json={
		    <%
		    while(it.hasNext()){
				String key=it.next();
				String value=pMap.get(key);
				%>
			"<%=key%>":<%=value%>,
			<%
			}
		    %>
		}
        $(function () {
            $(".timeTop").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "top-left"
            });
            $(".timeButtom").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "buttom-left"
            });
            $("#create-customerName").typeahead({
                source: function (query, process) {
                    $.get(
                        "workbench/transaction/getCustomerName.do",
                        { "name" : query },
                        function (data) {
                            //alert(data);
                            process(data);
                        },
                        "json"
                    );
                },
                delay: 1000
            });
			$("#saveBtn").click(function () {
				alert(11);
			})
			$("#create-stage").change(function () {
				var stage=$("#create-stage").val();
                var possibility=json[stage];
                $("#create-possibility").val(possibility+'%');
            })
			$("#openMarketActivity").click(function () {
                $("#aname").val("");
				var name=$("#aname").val();
				$.post("workbench/clue/getActivityListByName.do",
					{"name":name},
					function (data) {

				    if (data.total>0){
                        //alert("标记1");
                        var html="";
                        $.each(data.dataList,function (i,n) {
                        		html+='<tr>';
                            	html+='<td><input type="radio" name="activity" value="'+n.id+'"/></td>';
                                html+='<td id="a'+n.id+'">'+n.name+'</td>';
                                html+='<td>'+n.startDate+'</td>';
                                html+='<td>'+n.endDate+'</td>';
                                html+='<td>'+n.owner+'</td>';
                                html+='</tr>';
                        })
						$("#activityBody").html(html);
                        $("#findMarketActivity").modal("show");
					} else {
				        alert("查询内容不存在")
					}

                },"json");
            })
			$("#closeActivityModal").click(function () {
                $("#findMarketActivity").modal("hide");
            })
			$("#aname").keydown(function (event) {
				if(event.keyCode==13){
                    var name=$("#aname").val();
                    $.post("workbench/clue/getActivityListByName.do",
                        {"name":name},
                        function (data) {
                            if (data.total>0){
                                //alert("标记1");
                                var html="";
                                $.each(data.dataList,function (i,n) {
                                    html+='<tr>';
                                    html+='<td><input type="radio" name="activity" value="'+n.id+'"/></td>';
                                    html+='<td id="a'+n.id+'">'+n.name+'</td>';
                                    html+='<td>'+n.startDate+'</td>';
                                    html+='<td>'+n.endDate+'</td>';
                                    html+='<td>'+n.owner+'</td>';
                                    html+='</tr>';
                                })
                                $("#activityBody").html(html);
                            } else {
                                alert("查询内容不存在")
                            }

                        },"json");
				    return false;
				}
            })
			$("#addActivityId").click(function () {
				if ($("input[name=activity]:checked").prop("checked")){
                    var id=$("input[name=activity]:checked").val();
					$("#create-activityId").val(id);
                    var name=$("#a"+id).html();
                    $("#create-activityName").val(name);
                    $("#findMarketActivity").modal("hide");
				}
            })
			$("#openFindContacts").click(function () {
                $("#bname").val("");
                var name=$("#bname").val();
                $.post("workbench/clue/getContactsListByName.do",
                    {"name":name},
                    function (data) {
                        if (data.total>0){
                            //alert("标记1");
                            var html="";
                            $.each(data.dataList,function (i,n) {
                                html+=' <tr>';
                                html+='<td><input type="radio" name="contacts" value="'+n.id+'"/></td>';
                                html+='<td id="b'+n.id+'">'+n.fullname+'</td>';
                                html+='<td>'+n.email+'</td>';
                                html+='<td>'+n.mphone+'</td>';
                                html+='</tr>';
                            })
                            $("#contactsBody").html(html);
                            $("#findContacts").modal("show");
                        } else {
                            alert("查询内容不存在")
                        }

                    },"json");
            })
            $("#closeContactsModal").click(function () {
                $("#findContacts").modal("hide");
            })
            $("#bname").keydown(function (event) {
                if(event.keyCode==13){
                    var name=$("#bname").val();
                    $.post("workbench/clue/getContactsListByName.do",
                        {"name":name},
                        function (data) {
                            if (data.total>0){
                                //alert("标记1");
                                var html="";
                                $.each(data.dataList,function (i,n) {
                               		html+=' <tr>';
                                    html+='<td><input type="radio" name="contacts" value="'+n.id+'"/></td>';
									html+='<td id="b'+n.id+'">'+n.fullname+'</td>';
									html+='<td>'+n.email+'</td>';
                                    html+='<td>'+n.mphone+'</td>';
                                    html+='</tr>';
                                })
                                $("#contactsBody").html(html);
                            } else {
                                alert("查询内容不存在")
                            }

                        },"json");
                    return false;
                }
            })
            $("#addContactsId").click(function () {
                if ($("input[name=contacts]:checked").prop("checked")){
                    var id=$("input[name=contacts]:checked").val();
                    $("#create-contactsId").val(id);
                    var name=$("#b"+id).html();
                    alert(name)
                    $("#create-contactsName").val(name);
                    $("#findContacts").modal("hide");
                }
            })
    })
</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" id="aname" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
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
					<button type="button" class="btn btn-default" id="closeActivityModal">取消</button>
					<button type="button" class="btn btn-primary" id="addActivityId">关联</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="bname" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactsBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="closeContactsModal">取消</button>
					<button type="button" class="btn btn-primary" id="addContactsId">关联</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" id="saveBtn" class="btn btn-default">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back()">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner">
				  <option></option>
				  <c:forEach items="${uList}" var="u">
					  <option value="${u.id}" ${user.id eq u.id?"selected":""}>${u.name}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-money">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-name">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control timeButtom" id="create-expectedClosingDate">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-stage">
			  	<option></option>
			  	<c:forEach items="${stageList}" var="s">
					<option value="${s.value}">${s.text}</option>
				</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType">
				  <option></option>
				  <c:forEach items="${transactionTypeList}" var="t">
					  <option value="${t.value}">${t.text}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource">
				  <option></option>
				  <c:forEach items="${sourceList}" var="s">
					  <option value="${s.value}">${s.text}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="openMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activityName" placeholder="点击左侧搜索按钮进行添加" readonly>
				<input type="hidden" name="activityId" id="create-activityId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" id="openFindContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsName" placeholder="点击左侧搜索按钮进行添加" readonly>
				<input type="hidden" name="contactsId" id="create-contactsId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control timeTop" id="create-nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>