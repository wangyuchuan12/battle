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
            <h1 class="page-header">${battle.name}</h1>
        </div>
     
    </div>
    <input id="battleId" value="${battle.id}" hidden="true"/>
    <div class="tableHeader">
          <ul>
          	<li><a href="javascript:void(0)" onclick="addOpen()">新增</a></li>
          </ul>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            
            	
            
                <div class="panel-body">
                
                 	
                    <div class="table-responsive">
                   		
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th>图片</th>
                                    <th>名称</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${subjects}" var="subject" varStatus="">
                                <tr class="odd gradeX" id="${subject.id}">
                                    <td name="id">${subject.id}</td>
                                    <td name="imgUrl">
                                    	<img src="${subject.imgUrl}" class="tableLiImg"/>
                                    </td>
                                    <td name="name">${subject.name}</td>
                                    <td class="center">                                   
                                       <a href="javascript:void(0)" onclick="updateOpen('${subject.id}')" style="padding-left:20px;padding-right:20px;">修改</a> 
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

<div id="updateDialog" class="easyui-dialog" style="width: 400px; height: 500px; padding: 10px 20px;"
       closed="true" buttons="#dlg-buttons"> 
		    <form>
		    	<table cellpadding="5">
		    		<tr>
		    			<img src="" id = "updateDialogImg" name="img" style="width:200px;height:200px;"></img>
		    		</tr>
		    		<tr>
		    			<td>id</td>
		    			<td><input class="easyui-textbox" type="text" id="updateDialogId" name="id" data-options="required:true" disabled="disabled"/></td>
		    		</tr>
		    		<tr>
		    			<td>名称</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" id="updateDialogName" name="name" data-options="required:true"/>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>序号</td>
		    			<td>
		    				<input class="easyui-numberbox" type="text" id="updateDialogSeq" name="seq" data-options="required:true"/>
		    			</td>
		    		</tr>
		    		
		   
		    		
		    	</table>
		   	 </form>
		   	 
		<div id="dlg-buttons"> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateSubject()" iconcls="icon-save">保存</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#updateDialog').dialog('close')" iconcls="icon-cancel">取消</a> 
	    </div> 
</div>




<div id="addDialog" class="easyui-dialog" style="width: 400px; height: 500px; padding: 10px 20px;"
       closed="true" buttons="#dlg-buttons2"> 
		    <form>
		    	<table cellpadding="5">
		    		<tr>
		    			<img src="" id = "addDialogImg" name="img" style="width:200px;height:200px;"></img>
		    		</tr>
		    		<tr>
		    			<td>名称</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" id="addDialogName" name="name" data-options="required:true"/>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>序号</td>
		    			<td>
		    				<input class="easyui-numberbox" type="text" id="addDialogSeq" name="seq" data-options="required:true"/>
		    			</td>
		    		</tr>
		    		
		   
		    		
		    	</table>
		   	 </form>
		   	 
		<div id="dlg-buttons2"> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addSubject()" iconcls="icon-save">保存</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#addDialog').dialog('close')" iconcls="icon-cancel">取消</a> 
	    </div> 
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
$(document).ready(function() {
	$("#updateDialogImg").click(function(){
		uploadFile({
			success:function(data){
				var url = data.url;
				$("#updateDialogImg").attr("src",url);
			},
			fail:function(){
				alert("fail")
			}
		});
	});
	
	$("#addDialogImg").click(function(){
		uploadFile({
			success:function(data){
				var url = data.url;
				$("#addDialogImg").attr("src",url);
			},
			fail:function(){
				alert("fail")
			}
		});
	});
	
});

function addOpen(){
	$("#addDialog").dialog("open").dialog("setTitle","新增类型");
}

function addSubject(){
	var name = $("#addDialogName").textbox().textbox('getValue');
	var seq = $("#addDialogSeq").textbox().textbox('getValue');
	var imgUrl = $("#addDialogImg").attr("src");
	var battleId = $("#battleId").val();
	request({
		url:"/api/battle/subject/add",
		dataType:'json',
		type:"POST",
		data:{
			name:name,
			seq:seq,
			imgUrl:imgUrl,
			battleId:battleId
		},
		success:function(resp){
			if(resp.success){
				location.reload();
			}else{
				alert("保存发生错误");
			}
		},
		error:function(){
			alert("保存发生错误");
		}
	});
}

function updateSubject(){
	var id = $("#updateDialogId").textbox().textbox('getValue');
	var name = $("#updateDialogName").textbox().textbox('getValue');
	var seq = $("#updateDialogSeq").textbox().textbox('getValue');
	var imgUrl = $("#updateDialogImg").attr("src");
	
	request({
		url:"/api/battle/subject/update",
		dataType:'json',
		type:"POST",
		data:{
			id:id,
			name:name,
			seq:seq,
			imgUrl:imgUrl
		},
		success:function(resp){
			if(resp.success){
				var data = resp.data;
				var id = data.id;
				var name = data.name;
				var imgUrl = data.imgUrl;
				var seq = data.seq;
				$("#"+id+">td[name=imgUrl]>img").attr("src",imgUrl);
				$("#"+id+">td[name=name]").html(name);
				$('#updateDialog').dialog('close')
			}else{
				alert("保存发生错误");
			}
		},
		error:function(){
			alert("保存发生错误");
		}
	});
}


function updateOpen(id){
	request({
		url:"/api/battle/subject/info",
		dataType:'json',
		type:"POST",
		data:{
			id:id
		},
		success:function(resp){
			if(resp.success){
				var subject = resp.data;
				$("#updateDialogId").textbox().textbox('setValue',subject.id);
				$("#updateDialogName").textbox().textbox('setValue',subject.name);
				$("#updateDialogSeq").textbox().textbox('setValue',subject.seq);
				$("#updateDialogImg").attr("src",subject.imgUrl);
				$("#updateDialog").dialog("open").dialog('setTitle', '类型修改');
			}else{
				alert("提交发生错误了");
			}
		},
		error:function(){
			alert("提交发生错误了");
		}

	});
}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
