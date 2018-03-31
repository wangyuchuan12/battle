<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
    <tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
    <tiles:putAttribute name="title">机顶盒云端管理系统 </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <!-- Page Content -->
        
    <div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">管理员</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							修改用户信息
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form action="<c:url value="/admin/update_admin"/>" method="post" onsubmit="return updatePasswordSubmit();">
										<input type="hidden" value="${admin.id}" name="id">
                             			<input type="hidden" value="${admin.username}" name="username">
                             			<input type="hidden" name="roles" value="${admin.roles}"/>
										<div class="form-group">
                                          <label>姓名</label>
                                            <input class="form-control" name="realname" value="${admin.realname}">
                                           <p class="help-block">请输入用户姓名</p>
                                       </div>
                                       <div class="form-group">
                                          <label>电话</label>
                                            <input class="form-control" name="mobile" value="${admin.mobile}">
                                           <p class="help-block">请输入用户电话</p>
                                       </div>
										<div class="form-group">
											<label>邮箱</label>
											<input class="form-control" name="email" value="${admin.email}">
											<p class="help-block">请输入邮箱</p>
										</div>
										<div class="form-group" name="rolesDiv">
											<label>用户角色</label>
											<div class="form-group">
												<div class="checkbox">
													<label>
														<input type="checkbox" value="admin" onchange="javascript:synchonizedRoles()">
														admin
													</label>
													<label>
														<input type="checkbox" value="god" onchange="javascript:synchonizedRoles()">
														god
													</label>
												</div>
												<p class="help-block">请选择用户角色</p>
											</div>
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
			    var rolesValue = $("input[name=roles]")[0].value;
			   	var rolesSelect = $("input[type=checkbox]");
			   	if(rolesValue=="admin")
				{
					rolesSelect[0].checked =true;
				}
			   	else if(rolesValue=="god")
				{
					rolesSelect[1].checked = true;
				}
			   	else if(rolesValue=="admin,god"||rolesValue=="god,admin") {
					rolesSelect[0].checked = true;
					rolesSelect[1].checked = true;
				}
			});
			function synchonizedRoles()
			{
				var rolesValue = $("input[name=roles]")[0].value;
				var rolesSelect = $("input[type=checkbox]");
				if(rolesSelect[0].checked&&rolesSelect[1].checked){
					$("input[name=roles]")[0].value="admin,god"
				}
				else if(!rolesSelect[0].checked&&rolesSelect[1].checked){
					$("input[name=roles]")[0].value="god"
				}
				else if(rolesSelect[0].checked&&!rolesSelect[1].checked){
					$("input[name=roles]")[0].value="admin"
				}
				else{
					$("input[name=roles]")[0].value=""
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
