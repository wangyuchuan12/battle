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
            <h1 class="page-header">题库管理</h1>
        </div>
     
    </div>
    
    <div class="tableHeader">
          <ul>
          	<li><a href="javascript:void(0)" onClick="addOpen()">新增</a></li>
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
                                <c:forEach items="${battles}" var="battle" varStatus="">
                                <tr class="odd gradeX" id="${battle.id}">
                                    <td name="id">${battle.id}</td>
                                    <td name="headImg">
                                    	<img src="${battle.headImg}" class="tableLiImg"/>
                                    </td>
                                    <td name="name">${battle.name}</td>
                                    <td class="center">                                   
                                       <a href="javascript:void(0)" onclick="updateOpen('${battle.id}')">修改信息</a>                       
                                       <a href="<c:url value="/battle/subject/list?battleId=${battle.id}"/>">题目类型</a>                                                             
                                       <a href="<c:url value="/battle/question/questionManager?battleId=${battle.id}"/>">题目管理</a>
                               
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


<div id="addDialog" class="easyui-dialog" style="width: 400px; height: 500px; padding: 10px 20px;"
       closed="true" buttons="#dlg-buttons"> 
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
		    			<td>简介</td>
		    			<td>
		    				<textarea class="textarea easyui-validatebox" id="addDialogInstruction" name="instruction"  rows="10" cols="15" style="height:50px;text-align:left;"></textarea>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>是否激活</td>
		    			<td>
		    				<input id="addIsActivationYes" type="radio" name=isActivation
					            class="easyui-validatebox" checked="checked" value="0">
					            <label>是</label>
					        </input>
					        <input id="addIsActivationNo" type="radio" name="isActivation"
					            class="easyui-validatebox" value="1">
					            <label>否</label>
					        </input>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>状态</td>
		    			<td>
		    				<input id="addStatusFree" type="radio" name="status"
					            class="easyui-validatebox"  value="0">
					            <label>游离</label>
					        </input>
					        <input id="addStatusIn" type="radio" name="status" checked="checked"
					            class="easyui-validatebox" value="1">
					            <label>进行中</label>
					        </input>
		    			</td>
		    		</tr>
		    		
		    	</table>
		   	 </form>
		   	 
		<div> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addBattle()" iconcls="icon-save">保存2</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#addDialog').dialog('close')" iconcls="icon-cancel">取消</a> 
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
		    			<td>简介</td>
		    			<td>
		    				<textarea class="textarea easyui-validatebox" id="updateDialogInstruction" name="instruction"  rows="10" cols="15" style="height:50px;text-align:left;"></textarea>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>是否激活</td>
		    			<td>
		    				<input id="updateIsActivationYes" type="radio" name=isActivation
					            class="easyui-validatebox" checked="checked" value="0">
					            <label>是</label>
					        </input>
					        <input id="updateIsActivationNo" type="radio" name="isActivation"
					            class="easyui-validatebox" value="1">
					            <label>否</label>
					        </input>
		    			</td>
		    		</tr>
		    		
		    		
		    		<tr>
		    			<td>状态</td>
		    			<td>
		    				<input id="updateStatusFree" type="radio" name="status"
					            class="easyui-validatebox" checked="checked" value="0">
					            <label>游离</label>
					        </input>
					        <input id="updateStatusIn" type="radio" name="status"
					            class="easyui-validatebox" value="1">
					            <label>进行中</label>
					        </input>
		    			</td>
		    		</tr>
		    		
		    	</table>
		   	 </form>
		   	 
		<div> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateBattle()" iconcls="icon-save">保存1</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#updateDialog').dialog('close')" iconcls="icon-cancel">取消</a> 
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
	$("#addDialog").dialog("open").dialog('setTitle', '新增题库');
}

function addBattle(){
	var name = $("#addDialogName").textbox().textbox('getValue');
	var instruction = $("#addDialogInstruction").textbox().textbox('getValue');
	var imgUrl = $("#addDialogImg").attr("src");
	var isActivation = 0;
	
	var status = 0;
	
	if($("#addStatusFree").attr("checked")){
		status = 0;
	}else{
		status = 1;
	}
	
	
	if($("#addIsActivationYes").attr("checked")){
		isActivation =1;
	}else{
		isActivation = 0;
	}
	$("#addDialog").dialog("close");
	request({
		url:"/api/battle/add",
		dataType:'json',
		type:"POST",
		data:{
			name:name,
			instruction:instruction,
			isActivation:isActivation,
			headImg:imgUrl,
			status:status
		},
		success:function(resp){
			location.reload();
		},
		error:function(){
			alert("保存发生错误");
		}
	});
}

function updateBattle(){
	var id = $("#updateDialogId").textbox().textbox('getValue');
	var name = $("#updateDialogName").textbox().textbox('getValue');
	var instruction = $("#updateDialogInstruction").textbox().textbox('getValue');
	var imgUrl = $("#updateDialogImg").attr("src");
	var isActivation = 0;
	
	var status = 0;
	
	if($("#updateIsActivationYes").attr("checked")){
		isActivation =1;
	}else{
		isActivation = 0;
	}
	
	if($("#updateStatusFree").attr("checked")){
		status = 0;
	}else{
		status = 1;
	}
	
	request({
		url:"/api/battle/update",
		dataType:'json',
		type:"POST",
		data:{
			id:id,
			name:name,
			instruction:instruction,
			isActivation:isActivation,
			headImg:imgUrl,
			status:status
		},
		success:function(resp){
			if(resp.success){
				var data = resp.data;
				var id = data.id;
				var name = data.name;
				var headImg = data.headImg;
				$("#"+id+">td[name=headImg]>img").attr("src",headImg);
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
		url:"/api/battle/info",
		dataType:'json',
		type:"POST",
		data:{
			id:id
		},
		success:function(resp){
			if(resp.success){
				var battle = resp.data;
				$("#updateDialogId").textbox().textbox('setValue',battle.id);
				$("#updateDialogName").textbox().textbox('setValue',battle.name);
				$("#updateDialogInstruction").textbox().textbox('setValue',battle.instruction);
				$("#updateDialogImg").attr("src",battle.headImg);
				var isActivation = battle.isActivation;
				var status = battle.status;
				if(isActivation==1){
					$("#updateIsActivationYes").attr("checked",true);
				}else{
					$("#updateIsActivationNo").attr("checked",true);
				}
				
				if(status==0){
					$("#updateStatusFree").attr("checked",true)
				}else{
					$("#updateStatusIn").attr("checked",true)
				}
				
				$("#updateDialog").dialog("open").dialog('setTitle', '更新题库');
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
