<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insertDefinition name="adminLayout">
    <tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
    <tiles:putAttribute name="title">控制台</tiles:putAttribute>
    <tiles:putAttribute name="body">
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Blank</h1>
                </div>
            </div>
        </div>
  
    </tiles:putAttribute>
</tiles:insertDefinition>
