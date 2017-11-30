<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工管理系统</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/outOfBounds.js"
	type="text/javascript"></script>
<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/menu.json',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				//var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#treeMenu"), setting, data);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});

		
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
	});
    // 退出登录
    function logoutFun() {
        $.messager
            .confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
                if (isConfirm) {
                    location.href = '${pageContext.request.contextPath }/userAction_logout.action';
                }
            });
    }
	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined && treeNode.page!= "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}


</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height:50px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">

		<div id="sessionInfoDiv"
			style="position: absolute;right: 5px;top:10px;" onclick="logoutFun();">
			[<strong>退出系统</strong>]
		</div>


	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'"
		style="width:150px">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
				<ul id="treeMenu" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs" border="false">
			<div title="员工管理" id="subWarp"
				style="width:100%;height:100%;overflow:hidden">
				<iframe src="${pageContext.request.contextPath }/page_base_staff.action"
					style="width:100%;height:100%;border:0;"></iframe>
				<%--				这里显示公告栏、预警信息和代办事宜--%>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height:40px;padding:10px;background:url('./images/header_bg.png') no-repeat right;">
		<table style="width: 100%;">
			<tbody>
				<tr>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>