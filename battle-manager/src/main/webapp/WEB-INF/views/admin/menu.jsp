<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/admin/"/>">晨曦家园后台管理系统</a>

        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" name="password_adjust" href="<c:url value="/admin/password"/>"><span id="adjust" style="color:red"></span></a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="<c:url value="/admin/password"/>"><i class="fa fa-gear fa-fw"></i> 修改密码</a>
                </li>
                <li class="divider"></li>
                <li><a href="<c:url value="/admin/logout"/>"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                </li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <shiro:hasRole name="god">
                <li>
                    <a href="#"><i class="fa fa-plus fa-fw"></i>系统管理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                    
                        <li>
                            <a href="<c:url value="/admin/list"/>">账户管理</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/battle/list"/>">题库管理</a>
                        </li>

                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                </shiro:hasRole>
           
            </ul>
        </div>
    </div>
</nav>
