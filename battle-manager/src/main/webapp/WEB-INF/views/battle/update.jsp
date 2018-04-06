<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">题库管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">修改题库</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">题库管理</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/api/battle/update?id=${battle.id}"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return updateSubmit();">
								<div class="form-group">
                    					<label>题库图片</label>
                    					<img style="display: block;" class="img-thumbnail" src="${battle.headImg}">
                    					<input type="file" name="headImg">
                    					<p class="help-block">*请选择文件</p>
                    					<p class="help-block">请选择图片</p>
                					</div>
                					
                					
								<div class="form-group" name="nameDiv">
									<label>名称</label> <input class="form-control" name="name" value="${battle.name}">
									<p class="help-block">*请输入题库名称</p>
									<p class="help-block" hidden="true">输入的题库名称格式不合法，不能包含特殊字符或者为空</p>
								</div>
			
								
								
								<button type="submit" class="btn btn-default">提交</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
	$(document).ready(function() {
		function updateSubmit() {
			return true;
		}
	});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>