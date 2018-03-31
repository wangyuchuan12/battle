<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form>
	<input type="hidden" id="isOverTime" value="${isOverTime}">
	<input type="hidden" id="isWareTime" value="${isWareTime}"> 
	<input type="hidden" id="deadline" value="${deadline}"> 
</form>

<script type="text/javascript">
//初始化menu
function initMenu(){
	var menuWare = $("#menuWare");
	var isOverTime = $("#isOverTime").val();
	var isWareTime = $("#isWareTime").val();
	var deadline = $("#deadline").val();
	if(isOverTime=="true")
	{
		menuWare.text("密码已到期");
	}
	else if(isWareTime=="true")
	{
		menuWare.text("密码即将到期，到期时间为："+deadline);
	}
	else{
		menuWare.text("");
	}
}
</script>