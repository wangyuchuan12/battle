<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
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
				<div class="panel-heading">添加管理员</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/admin/add_admin"/>" method="post"
								onsubmit="return addSubmit();">
								<input name="roles" type="hidden"/>
								<input name="usrenameCheck" type="hidden" />
								<div class="form-group" name="usernameDiv">
									<label>用户名</label> <input class="form-control"
										onblur="check('usernameDiv')" name="username" value=""
										onkeyup="check('usernameDiv')">
									<p class="help-block">请输入用户名，用户名唯一，最少4字符</p>
								</div>
								<div class="form-group" name="realnameDiv">
									<label>管理员姓名</label> <input class="form-control"
										name="realname" value="">
									<p class="help-block">请输入管理员真实姓名</p>
								</div>
								<div class="form-group" name="rolesDiv">
									<label>用户角色</label>
									<div class="form-group">
										<div class="checkbox">
											<label>
												<input type="checkbox" value="admin" onchange="check('rolesDiv')">
												admin
											</label>
											<label>
												<input type="checkbox" value="god">
												god
											</label>
										</div>
										<p class="help-block">请选择用户角色</p>
									</div>

								</div>
								<div class="form-group" name="mobileDiv">
									<label>联系电话</label> <input class="form-control" name="mobile"
										value="">
									<p class="help-block">请输入联系电话，电话号码必须是数字</p>
								</div>
								<div class="form-group" name="emailDiv">
									<label>邮箱</label> <input class="form-control"
										onblur="check('emailDiv')" name="email" value=""
										onkeyup="check('emailDiv')">
									<p class="help-block">请输入邮箱</p>
								</div>
								<div class="form-group" name="passwordDiv">
									<label>用户密码</label> <input class="form-control" name="password"
															   type="password"
										value="" onblur="check('passwordDiv')"
										onkeyup="check('passwordDiv')">
									<p class="help-block">请输入用户初始密码</p>
								</div>

								<div class="form-group" name="affirm_passwordDiv">
									<label>确认密码</label> <input class="form-control"
															   type="password"
										name="affirm_password" value=""
										onblur="check('affirm_passwordDiv')"
										onkeyup="check('affirm_passwordDiv')">
									<p class="help-block">请确认密码</p>
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
//		check('usernameDiv');
	});
	function addSubmit() {
		if (check("usernameDiv") && check('passwordDiv')
				&& check('affirm_passwordDiv')) {
			var rolesDiv = getJqueryDivObject("rolesDiv");
			var input = rolesDiv.find("div div label input");
			if(input[0].checked&&!input[1].checked){
				$("input[name=roles]").val("admin");
			}
			else if(input[0].checked&&input[1].checked){
				$("input[name=roles]").val("admin,god");
			}
			else if(!input[0].checked&&input[1].checked){
				$("input[name=roles]").val("god");
			}
			return true;
		} else {
			return false;
		}

	}
	function check(name) {
		var divObject = getJqueryDivObject(name);
		var inputObject = divObject.find("input");
		if (name == "passwordDiv") {
			if (!inputObject.val()) {
				divObject.find("p").text("密码不能为空");
				addErrorCss(name);
				return false;
			} else {
				divObject.find("p").text("请输入用户初始密码");
				remoteErrorCss(name);
				return true;
			}
		} else if (name == "affirm_passwordDiv") {
			var passwordInputObject = getJqueryDivObject("passwordDiv")
					.find("input");
			var oldContent = divObject.find("p").text();
			if (passwordInputObject.val() != inputObject.val()) {
				divObject.find("p").text("原始密码和确认密码不一致");
				addErrorCss(name);
				return false;
			} else {
				divObject.find("p").text("请确认密码");
				remoteErrorCss(name);
				return true;
			}
		} else if (name == "usernameDiv") {
			var oldContent = divObject.find("p").text();
			if (!inputObject.val()) {
				addErrorCss(name);
				oldContent = divObject.find("p").text("用户名不为空");
				return false;
			} else {
				var htmlobj=$.ajax({url:"<c:url value="/admin/get_admin_json"/>",async:false,data:{username:inputObject.val()}});
				if(!htmlobj.responseText||!eval("("+htmlobj.responseText+")")||!eval("("+htmlobj.responseText+")").username)
				{
					divObject.find("p").text("请输入用户名，用户名唯一，最少4字符");
					remoteErrorCss(name);
					return true;
				}
				else{
					addErrorCss(name);
					oldContent = divObject.find("p").text("用户名不能重复");
					return false;
				}
				
			}
		}
	}
	function remoteErrorCss(name) {
		var obj = getJqueryDivObject(name);
		obj.removeClass("has-error");
	}
	function addErrorCss(name) {
		var obj = getJqueryDivObject(name);
		obj.addClass("has-error");
	}
	function getJqueryDivObject(name) {
		var obj = $("div[name=" + name + "]");
		return obj;
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>