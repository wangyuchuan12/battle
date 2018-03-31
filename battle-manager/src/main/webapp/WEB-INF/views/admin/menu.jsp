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
                            <a href="<c:url value="/manager/handlers"/>">系统处理类</a>
                        </li>
                        <li>
                            <a href="<c:url value="/manager/activities"/>">支付活动管理</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/manager/good_type_view"/>">商品类别</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/manager/ad_good_header_img"/>">广告栏</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/manager/quick_entrance"/>">流动栏</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/remote_info/key_list"/>">商家管理</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/manager/orders_program"/>">审核订单</a>
                        </li>
                        
                        
                        
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                </shiro:hasRole>
                
                <li>
                     <a href="<c:url value="/manager/business_info"/>"><i class="fa fa-user fa-fw"></i>个人信息</a>
                </li>
                
                <li>
                     <a href="<c:url value="/manager/goods"/>"><i class="fa fa-cubes fa-fw"></i>商品管理</a>
                </li>
                
                <li>
                     <a href="<c:url value="/manager/orders"/>"><i class="fa fa-reorder fa-fw"></i>订单管理</a>
                </li>
                
                <li>
                     <a href="<c:url value="/manager/groups"/>"><i class="fa fa-group fa-fw"></i>组团管理</a>
                </li>
                
                <li>
                     <a href="<c:url value="/manager/messages"/>"><i class="fa fa-comments fa-fw"></i>消息管理</a>
                </li>
                <li>
                	 <a href="<c:url value="/manager/activities"/>"><i class="fa fa-money fa-fw"></i>支付活动管理</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
