<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<tiles:insertDefinition name="adminLayout">
	<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
	<tiles:putAttribute name="title">页面出错</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">${errorcode}</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" style="font-size: 25px">${errortitle}</div>
						<div class="panel-body">
							<div class="row" style="color: red">${errorMessage}</div>
							<div class="row">
								<a href="<c:url value='${returnUrl}'/>">返回</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="footerJavascript">
		<script>
        </script>
	</tiles:putAttribute>
</tiles:insertDefinition>
