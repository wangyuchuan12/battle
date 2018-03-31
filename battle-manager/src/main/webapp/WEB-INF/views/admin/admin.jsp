<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">管理员</tiles:putAttribute>
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
            
                <div class="panel-body">
                
                 
                    <div class="table-responsive">
                   
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                            <thead>
                                <tr>
                                    <th>用户名</th>
                                    <th>管理员姓名</th>
                                    <th>联系电话</th>
                                    <th>上次登录时间</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${admins}" var="admin" varStatus="">
                                <tr class="odd gradeX">
                                    <td>${admin.username}</td>
                                    <td>${admin.realname}</td>
                                    <td>${admin.mobile}</td>
                                    <td class="center"><joda:format value="${admin.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td> 
                                    <td class="center"><joda:format value="${admin.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td class="center">                                   
                                       <a href="<c:url value="/admin/update?username=${admin.username}"/>">修改</a>                       
                                       <a href="<c:url value="/admin/delete?username=${admin.username}"/>">删除</a>                                                             
                                       <a href="<c:url value="/admin/reset?username=${admin.username}"/>">重置密码</a>
                               
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
	 var table = $('#dataTables-admin').dataTable({
	    	"oLanguage": {
	    	
	            "sSearch": "<button name='addButton' style='color:green ; width:100px;height:28px' >新增</button>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp搜索:",

	            "sLengthMenu": "每页显示 _MENU_ 条记录",
	                "sZeroRecords": "Nothing found - 没有记录",
	            "sInfo": "显示第  _START_ 条到第  _END_ 条记录,一共  _TOTAL_ 条记录",
	            "sInfoEmpty": "显示0条记录",
	            "oPaginate": {
	                "sPrevious": " 上一页 ",
	                "sNext":     " 下一页 "
	                }
	            }
	        });
	 var addButton = $("button[name=addButton]");
	    addButton.bind("click",function(){
	    	window.location="<c:url value='/admin/add'/>";
	     });
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
