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
							修改密码
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form action="<c:url value="/admin/reset_admin"/>" method="post" onsubmit="return updatePasswordSubmit();">
										<input type="hidden" value="${admin.id}" name="id">
                             			<input type="hidden" value="${admin.username}" name="username">
										<div class="form-group" name="passwordDiv">
                                          <label>密码</label>
                                            <input class="form-control" name="password" value="" type="password">
                                           <p class="help-block">请输入密码</p>
                                       </div>
                                       <div class="form-group" name="affirmPasswordDiv">
                                          <label>确认密码</label>
                                            <input class="form-control" name="affirm_password" value="" type="password" onkeyup="checkPassword();">
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

		
      <script>
      function updatePasswordSubmit(){
         if(!checkPassword())
          {
        	 $("input[name=password]").val("");
        	 $("input[name=affirm_password]").val("");
             return false;
          }
         else{
        	 $("input[name=password]").val("");
        	 $("input[name=affirm_password]").val("");
             return true;
          }
      }
      function checkPassword()
      {
    	  var password = $("input[name=password]");
          var affPassword = $("input[name=affirm_password]");
          if(password.val()!=affPassword.val())
          {
        	  $("div[name=affirmPasswordDiv]").addClass("has-error");
        	  $("div[name=affirmPasswordDiv]").find("p").text("确认密码和原密码不相等");
        	  return false;
          }
          else{
        	  $("div[name=affirmPasswordDiv]").removeClass("has-error");
        	  $("div[name=affirmPasswordDiv]").find("p").text("请确认密码");
        	  return true;
          }
      }
      </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
