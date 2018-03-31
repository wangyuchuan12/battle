<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form>
    <input type="hidden" id="day" value="${day}">
    <input type="hidden" id="warm" value="${warm}"> 
</form>

<script type="text/javascript">
//初始化menu
function initMenu(){
    var menu =$("#adjust");
    var day = $("#day").val();
    var warm = $("#warm").val();
   if(day<8){ 
        menu.text(warm);
    }else{
    menuWare.text("");
    }
}
</script>
