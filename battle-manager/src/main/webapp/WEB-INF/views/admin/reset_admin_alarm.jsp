<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
    <tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
    <tiles:putAttribute name="title">机顶盒云端管理系统 </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <!-- Page Content -->
        
    
     <form action="<c:url value="/admin/reset_admin_alarm"/>">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">修改无效</h1>
                     </div>  
                             <span style.color="red">
				                       你无权限修改： 
				        	 </span>
				              <br>
				              </br>

				              <input type="submit" name="Submit" value="返回主菜单" >
				              	                   
               </div>
        </div>
      </form>
      <script>
      function check_password(val){
              var pwd=document.getElementById("pwd").value;
              var msg=document.getElementById("msg");
                 if(val.length>=pwd.length){
                    if(val==pwd){msg.innerHTML="输入正确";msg.style.color="#3399ff";}
                    else{msg.innerHTML="两次输入密码不一致";msg.style.color="red";}
                 }else{
                       msg.innerHTML="";
                       msg.style.color="red";
                  }
        }
      </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
