<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<jsp:include page="common.jsp" flush="true"></jsp:include>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title><tiles:insertAttribute name="title" /> - 疯狂题库</title>
<!-- Bootstrap Core CSS -->
<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
<!-- MetisMenu CSS -->
<%--<link href="<c:url value="/css/plugins/metisMenu/metisMenu.min.css"/>" rel="stylesheet">--%>
<link href="//cdnjscn.b0.upaiyun.com/libs/metisMenu/1.1.0/metisMenu.min.css" rel="stylesheet">
<!-- DataTables CSS -->
<link href="<c:url value="/css/plugins/dataTables.bootstrap.css"/>" rel="stylesheet">
<%--<link href="//cdnjscn.b0.upaiyun.com/libs/datatables-tabletools/2.1.5/css/TableTools.css" rel="stylesheet">--%>
<%--<link href="<c:url value="/css/plugins/dataTables.editor.min.css"/>" rel="stylesheet">--%>
<!-- Custom CSS -->
<link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">
<link href="<c:url value="/css/lanrenzhijia.css"/>" rel="stylesheet">
<link href="<c:url value="/css/jquery.datetimepicker.css"/>" rel="stylesheet">

<link href="<c:url value="/css/style.css"/>" rel="stylesheet">
<!-- Custom Fonts -->
<link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/chatView.css"/>" rel="stylesheet" type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js does not work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
<script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->


</head>

<body>
<div id="wrapper">
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="menu" />

    <tiles:insertAttribute name="body" />

    <tiles:insertAttribute name="footer" />
</div>
<!-- /#wrapper -->

<!-- jQuery -->

<script src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>

<input type="hidden" value="<c:url value="/"/>" id="contextUrl"/>

<form action="<c:url value="/api/common/resource"/>" id="fileForm">
	<input type="file" name="file" id="file" style="width:0px;height:0px;"/>
</form>

<script>

	var contextUrl = $("#contextUrl").val();

	
	
	function request(params){
		var url = params.url;
		url = contextUrl+url;
		params.url = url;
		$.ajax(params);
	}
	
	function uploadFile(callback){
		$("#file").unbind("change");
		$("#file").on("change",function(){
			var contextUrl = $("#contextUrl").textbox().textbox('getValue');
			var fileupUrl = contextUrl+"/api/common/resource/upload";
			$.ajaxFileUpload({
				fileElementId:"file",
				url:fileupUrl,
				async:true,
				type:"post",
				dataType:"json",
				secureuri:false,
				name:"file",
				success:function(resp){
					if(resp.success){
						if(callback&&callback.success){
							callback.success(resp.data);
						}
					}else{
						if(callback&&callback.fail){
							callback.fail();
						}
					}
				},
				error:function(){
					if(callback&&callback.fail){
						callback.fail();
					}
				}
			});
		});
		$("#file").click();
	}
</script>


<!-- Bootstrap Core JavaScript -->
<script src="<c:url value="/js/bootstrap.js"/>"></script>

<!-- Metis Menu Plugin JavaScript -->
<%--<script src="<c:url value="/js/plugins/metisMenu/metisMenu.min.js"/>"></script>--%>
<script src="//cdnjscn.b0.upaiyun.com/libs/metisMenu/1.1.0/metisMenu.min.js"></script>

<!-- DataTables JavaScript -->
<script src="//cdnjscn.b0.upaiyun.com/libs/datatables/1.10.1/js/jquery.dataTables.min.js"></script>
<%--<script src="//cdnjscn.b0.upaiyun.com/libs/datatables-tabletools/2.1.5/js/TableTools.min.js"></script>--%>
<script src="<c:url value="/js/plugins/dataTables/dataTables.bootstrap.js"/>"></script>
<%--<script src="<c:url value="/js/plugins/dataTables/dataTables.editor.min.js"/>"></script>--%>

<!-- Custom Theme JavaScript -->
<!--  
<script src="<c:url value="/js/sb-admin-2.js"/>"></script>
-->
<script src="<c:url value="/js/init.js"/>"></script>
<script src="<c:url value="/js/jquery.datetimepicker.js"/>"></script>
<script src="<c:url value="/js/flowJS.js"/>"></script>


<!-- 导入easyui类库 -->
<script src="<c:url value="/jquery-easyui-1.4.5/jquery.easyui.min.js"/>"></script>
<script src="<c:url value="/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"/>"></script>
<link href="<c:url value="/jquery-easyui-1.4.5/themes/default/easyui.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/jquery-easyui-1.4.5/themes/icon.css"/>" rel="stylesheet" type="text/css">
<script src="<c:url value="/js/ajaxfileupload.js"/>"></script>
<tiles:insertAttribute name="footerJavascript" defaultValue="" />
</body>
</html>
