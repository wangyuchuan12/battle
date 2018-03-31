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
                        <div class="panel-heading" style="color:green">
                            登录者密码修改
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                 <form action="<c:url value="/admin/change_password"/>" method=post >
                                         <input type="hidden" value="${admin.id}" name="id">
                                         <input type="hidden" value="${admin.username}" name="username">
                                        <div class="form-group">
                                          <label >原密码</label>
                                            <input class="form-control" value="" name="password_od" type="password">
                                           <p class="help-block">请正确输入原密码</p>
                                       </div>
                                       <div class="form-group">
                                          <label>新密码</label>
                                            <input class="form-control" name="password_new" type="password" value="">
                                           <p class="help-block">请输入长度不少于6位的新密码，不能与原密码一样</p>
                                       </div>
                                        <div class="form-group" >
                                            <label>确认新密码</label>
                                              <input class="form-control" name="confirm_password" value="" type="password">
                                                <p class="help-block">请准确再输入一次新密码</p>
                                         </div>
                              
                                        <button type="submit" class="btn btn-default" style="color:green">提交</button>
                                    </form>    
                                  </div>                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>

</tiles:insertDefinition>