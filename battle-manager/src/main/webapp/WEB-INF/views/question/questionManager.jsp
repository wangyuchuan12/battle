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
    
    <input id = "battleId" value="${battle.id}" hidden="true"/>
    <div class="row">
    	<div> 
        	<a href="javascript:void(0)" class="easyui-linkbutton" id="addQuestionButton" iconcls="icon-add">新增题目</a>
		</div> 
        <div class="col-lg-12">
            <div class="panel panel-default">
            
            	
            
                <div class="panel-body" style="border: 0px solid black;">
                	
                	<div class="questionPeriods" id="questionPeriods">
                		<ul>
                			<!--  
                			<li>题库1</li>
                			<li>题库2</li>
                			<li>题库3</li>
                			<li>题库4</li>
                			<li>题库5</li>
                			<li>题库6</li>
                			-->
                		</ul>
                	</div>
                        	
                	<div class="questionStages" id="questionStages">
                		<ul>
                			<!--  
                			<li><a href="">第1关</a></li>
                			<li><a href="">第2关</a></li>
                			<li><a href="">第3关</a></li>
                			<li><a href="">第4关</a></li>
                			<li><a href="">第5关</a></li>
                			<li><a href="">第6关</a></li>
                			<li><a href="">第7关</a></li>
                			<li><a href="">第8关</a></li>
                			<li><a href="">第9关</a></li>
                			-->
                		</ul>
                	</div>
                 	
                 	<div class="questionSubjects" id="questionSubjects">
                 		<ul>
                 			<!--
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			
                 			<li>
                 				<img src="http://ovqk5bop3.bkt.clouddn.com/284c81c5391685011e26e5a7b25af98b.png"></img>
                 				<span>题目数：10</span>
                 			</li>
                 			-->
                 		</ul>
                 	</div>
                 	
                 	
                 	
                 	<div class="table-responsive">
                   		
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th>图片</th>
                                    <th>问题</th>
                                    <th>类型</th>
                                    <th>答案</th>
                                    <th>选项</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="questionRecords">
                            	
                                <tr class="odd gradeX">
                                    <td>1</td>
                                    <td>2</td>
                                    <td>3</td>
                                    <td>4</td>
                                    <td>5</td>
                                </tr>
                                
                            </tbody>
                        </table>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>


<div id="editDialog" class="easyui-dialog" style="width: 500px; height: 600px;display:none;"
       closed="true" buttons="#dlg-buttons"> 
	
	<input id = "questionId"  type="hidden">
	<img src="http://p4t3idqqn.bkt.clouddn.com/%E6%80%80%E6%97%A7%E5%8A%A8%E6%BC%AB.png" style="width:150px;height:150px;" id="questionImg">	  
	<textarea rows="8" cols="40" placeholder="请输入问题" id="questionTextarea">这是一个什么问题哈</textarea>
	<div class="questionTypes">
		<ul>
			<li id="questionTypeSelect">选择题</li>
			<li id="questionTypeFill">填词题</li>
		</ul>
	</div>
	
	<div class="questionOperation">
		<div class="questionOperationSelect" id="questionOperationSelect">
			<ul>
				<li>
					<span>正确答案</span><input value="秦始皇">
				</li>
				
				<li>
					<span>错误答案</span><input value="汉武帝">
				</li>
				
				<li>
					<span>错误答案</span><input value="成吉思汗">
				</li>
				
				<li>
					<span>错误答案</span><input value="康熙">
				</li>
			</ul>
		</div>
		
		<div class="questionOperationFillwords" id="questionOperationFillwords">
			<div class="questionOperationWords" id="questionOperationWords">
				<div class="questionOperationWordsHeader" id="questionOperationWordsHeaderSub">-</div>
				<ul>
					<li index="1"></li>
					<li index="2"></li>
					<li index="3"></li>
					<li index="4"></li>
				</ul>
				<div class="questionOperationWordsHeader" id="questionOperationWordsHeaderAdd">+</div>
			</div>
			<ul>
				<li index="1" status="0">
					<input value="字">
				</li>
				<li index="2" status="0">
					<input value="字">
				</li>
				<li index="3" status="0">
					<input value="字">
				</li>
				<li index="4" status="0">
					<input value="字">
				</li>
				<li index="5" status="0">
					<input value="字">
				</li>
				<li index="6" status="0">
					<input value="字">
				</li>
				<li index="7" status="0">
					<input value="字">
				</li>
				<li index="8" status="0">
					<input value="字">
				</li>
				<li index="9" status="0">
					<input value="字">
				</li>
				<li index="10" status="0">
					<input value="字">
				</li>
				<li index="11" status="0">
					<input value="字">
				</li>
				<li index="12" status="0">
					<input value="字">
				</li>
				<li index="13" status="0">
					<input value="字">
				</li>
				<li index="14" status="0">
					<input value="字">
				</li>
				<li index="15" status="0">
					<input value="字">
				</li>
				<li index="16" status="0">
					<input value="字">
				</li>
			</ul>
		</div>
	</div>
	
	<div id="dlg-buttons"> 
        <a href="javascript:void(0)" class="easyui-linkbutton" id="saveQuestionButton" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#editDialog').dialog('close')" iconcls="icon-cancel">取消</a> 
	</div> 
</div>

</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
var periodId;
var stageId;
var subjectId;
var battleId = 1;
$(document).ready(function() {
	battleId = $("#battleId").val();
	var flowPlug = new FlowPlug({
		begin:function(){
			var outThis = this;
			this.flowData({
				battleId:battleId
			});
			this.setNext("initPeriods",function(){
				outThis.setNext("initSubjects",function(){
					outThis.setNext("initStages",function(){
						
						outThis.setNext("initQuestions");
						outThis.next();
						
						outThis.setNext("initQuestionCount");
						outThis.next();
					});
					outThis.next();
				});
				outThis.next();
			});
			this.nextData({
				battleId:battleId
			});
			
			this.next();
			
			this.setNext("onEvent");
			this.next();
		},
		
		onEvent:function(){
			var outThis = this;
			$("#addQuestionButton").click(function(){
				$("#editDialog").dialog("open").dialog({
					title: 'My Dialog',
				    closed: false,
				    cache: false,
				    modal: true
				});
				outThis.setNext("initAddEdit");
				outThis.next();
			});
			
			$("#saveQuestionButton").click(function(){
				outThis.setNext("submitQuestion");
				outThis.next();
			});
		},
		
		submitQuestion:function(){
			var submitType = this.flowData("submitType");
			if(submitType==0){
				this.setNext("submitQuestionToAdd");
				this.next();
			}else if(submitType==1){
				this.setNext("submitQuestionToUpdate");
				this.next();
			}
		},
		
		
		
		submitQuestionToAdd:function(){
			var outThis = this;
			var stageId = this.flowData("stageId");
			var subjectId = this.flowData("subjectId");
			var question = $("#questionTextarea").val();
			var imgUrl = $("#questionImg").attr("src");
			if($("#questionImg").attr("isImg")==0){
				imgUrl = "";
			}
			
			var questionType = this.flowData("questionType");
			var fillWords = "";
			var optionsStr="";
			var answer = "";
			if(questionType==2){
				$("#questionOperationWords>ul>li").each(function(){
					var text = $(this).text();
					if(!text){
						alert("请保持答案完整");
						return;
					}
					answer = answer+text;
				});
				
				$("#questionOperationFillwords>ul>li>input").each(function(){
					var text = $(this).val();
					if(!text){
						alert("请保持填词完整");
						return;
					}
					fillWords = fillWords+text;
				});
			}else if(questionType==0){
				var options = new Array();
				var seq = 0;
				function randomsort(a, b) {
					   return Math.random()>.5 ? -1 : 1;
					    //用Math.random()函数生成0~1之间的随机数与0.5比较，返回-1或1
				}
				var arr = [1, 2, 3, 4];
				arr.sort(randomsort);
				$("#questionOperationSelect>ul>li>input").each(function(){
					var value = $(this).val();
					var obj = new Object();
					var num = arr[seq];
					if(seq==0){
						obj.seq = num;
						obj.isRight = 1;
						obj.content = value;
						answer = value;
					}else{
						obj.seq = num;
						obj.isRight = 0;
						obj.content = value;
					}
					
					options.push(obj);
					seq++;
				});
				
				optionsStr = JSON.stringify(options);
			}
			
			$("#editDialog").dialog("close");
			request({
				url:"/api/battle/question/addQuestion",
				dataType:'json',
				type:"POST",
				data:{
					stageId:stageId,
					subjectId:subjectId,
					questionType:questionType,
					question:question,
					imgUrl:imgUrl,
					answer:answer,
					fillWords:fillWords,
					options:optionsStr
				},
				success:function(resp){
					if(resp.success){
						outThis.setNext("initQuestions");
						outThis.next();
						
						outThis.setNext("initQuestionCount");
						outThis.next();
					}else{
						alert("fail1");
					}
				},
				error:function(){
					alert("fail");
				}
			});
		},
		
		submitQuestionToUpdate:function(){
			var outThis = this;
			var stageId = this.flowData("stageId");
			var subjectId = this.flowData("subjectId");
			var question = $("#questionTextarea").val();
			var imgUrl = $("#questionImg").attr("src");
			if($("#questionImg").attr("isImg")==0){
				imgUrl = "";
			}
			var questionType = this.flowData("questionType");
			var fillWords = "";
			var optionsStr="";
			var answer = "";
			var questionId = $("#questionId").val();
			if(questionType==2){
				$("#questionOperationWords>ul>li").each(function(){
					var text = $(this).text();
					if(!text){
						alert("请保持答案完整");
						return;
					}
					answer = answer+text;
				});
				
				$("#questionOperationFillwords>ul>li>input").each(function(){
					var text = $(this).val();
					if(!text){
						alert("请保持填词完整");
						return;
					}
					fillWords = fillWords+text;
				});
			}else if(questionType==0){
				var options = new Array();
				var seq = 0;
				function randomsort(a, b) {
					   return Math.random()>.5 ? -1 : 1;
					    //用Math.random()函数生成0~1之间的随机数与0.5比较，返回-1或1
				}
				var arr = [1, 2, 3, 4];
				arr.sort(randomsort);
				$("#questionOperationSelect>ul>li>input").each(function(){
					var value = $(this).val();
					var obj = new Object();
					var num = arr[seq];
					if(seq==0){
						obj.seq = num;
						obj.isRight = 1;
						obj.content = value;
						obj.id = $(this).parent().attr("optionId");
						answer = value;
					}else{
						obj.seq = num;
						obj.isRight = 0;
						obj.content = value;
						obj.id = $(this).parent().attr("optionId");
					}
					
					options.push(obj);
					seq++;
				});
				optionsStr = JSON.stringify(options);
			}
			
			$("#editDialog").dialog("close");		
			request({
				url:"/api/battle/question/updateQuestion",
				dataType:'json',
				type:"POST",
				data:{
					stageId:stageId,
					subjectId:subjectId,
					questionType:questionType,
					question:question,
					imgUrl:imgUrl,
					answer:answer,
					fillWords:fillWords,
					options:optionsStr,
					battleQuestionId:questionId
				},
				success:function(resp){
					if(resp.success){
						outThis.setNext("initQuestions");
						outThis.next();
						
						outThis.setNext("initQuestionCount");
						outThis.next();
					}else{
						alert("fail1");
					}
				},
				error:function(){
					alert("fail");
				}
			});
		},
		
		initAddEdit:function(){
			this.flowData({
				submitType:0
			});
			
			$("#questionImg").attr("src","#");
			
			$("#questionImg").attr("isImg","0");
			
			$("#questionOperationWords>ul>li").empty();
			$("#questionOperationFillwords>ul>li>input").val("");
			
			$("#questionOperationSelect>ul>li>input").val("");
			
			$("#questionTextarea").val("");
			this.setNext("initEdit");
			this.next();
		},
		
		initUpdateEdit:function(){
			var outThis = this;
			var questionId = this.stepData("questionId");
			this.flowData({
				submitType:1
			});
			
			this.setNext("initEdit");
			this.next();
			$("#editDialog").dialog("open").dialog("setTitle","修改题目");
			request({
				url:"/api/battle/question/info",
				dataType:'json',
				type:"POST",
				data:{
					id:questionId
				},
				success:function(resp){
					if(resp.success){
						var data = resp.data;
						var battleQuestionId = data.id;
						var questionType = data.type;
						var question = data.question;
						var imgUrl = data.imgUrl;
						var answer = data.answer;
						var fillWords = data.fillWords;
						var options = data.options;
						$("#questionId").val(battleQuestionId);
						if(imgUrl){
							$("#questionImg").attr("src",imgUrl);
							$("#questionImg").attr("isImg","1");
						}else{
							$("#questionImg").attr("src","#");
							$("#questionImg").attr("isImg","0");
						}
						
						
						$("#questionTextarea").val(question);
						outThis.setNext("showQuestionTypes");
						outThis.flowData({
							questionType:questionType
						});
						outThis.next();
						
						if(questionType==0){
							
							var rightOption;
							for(var i=0;i<options.length;i++){
								if(options[i].content==answer){
									rightOption = options[i];
									options.splice(i,1);
								}
							}
							
							var index = 0;
							$("#questionOperationSelect>ul>li>input").each(function(){
								if(index==0){
									$(this).val(rightOption.content);
									$(this).parent().attr("optionId",rightOption.id);
								}else{
									$(this).val(options[index-1].content);
									$(this).parent().attr("optionId",options[index-1].id);
								}
								index++;
							});
						}else if(questionType==2){
							var index = 0;
							$("#questionOperationFillwords>ul>li>input").each(function(){
								$(this).val(fillWords[index]);
								$(this).parent().attr("status",1);
								index++;
							});
							
							index = 0;
							
							$("#questionOperationWords>ul").empty();
							for(var i=0;i<answer.length;i++){
								$("#questionOperationWords>ul").append("<li></li>");
							}
							$("#questionOperationWords>ul>li").each(function(){
								var answerEl = $(this);
								answerEl.text(answer[index]);
								index++;
								var flag = false;
								$("#questionOperationFillwords>ul>li>input").each(function(){
									var fillWordInputEl = $(this);
									var fillWordEl = fillWordInputEl.parent();
									if(fillWordInputEl.val()==answerEl.text()&&!flag){
										answerEl.attr("from",fillWordEl.attr("index"));
										flag = true;
									}
								});
							});
							
							outThis.setNext("fillWordsEvent");
							outThis.next();
							
							outThis.setNext("showQuestionOperationFillwordsStatus");
							outThis.next();
							
						}
					}else{
						alert("fail");
					}
				},
				error:function(){
					alert("fail");
				}
			});
		},
		
		initEdit:function(){
			this.setNext("initImgEdit");
			this.next();
			
			this.setNext("initQuestionTypes");
			this.next();
		},
		
		initImgEdit:function(){
			$("#questionImg").unbind("click");
			$("#questionImg").click(function(){
				uploadFile({
					success:function(data){
						var url = data.url;
						$("#questionImg").attr("src",url);
						$("#questionImg").attr("isImg","1");
					},
					fail:function(){
						alert("fail")
					}
				});
			});
		},
		
		//初始化题目类别选择功能
		initQuestionTypes:function(){
			var outThis = this;
			this.flowData({
				questionType:0
			});
			this.setNext("showQuestionTypes");
			this.next();
			
			$("#questionTypeSelect").click(function(){
				outThis.flowData({
					questionType:0
				});
				outThis.setNext("showQuestionTypes");
				outThis.next();
			});
			$("#questionTypeFill").click(function(){
				outThis.flowData({
					questionType:2
				});
				outThis.setNext("showQuestionTypes");
				outThis.next();
			});
		},
		
		showQuestionTypes:function(){
			var questionType = this.flowData("questionType");
			//选择题
			if(questionType==0){
				$("#questionTypeSelect").css("background","black");
				$("#questionTypeSelect").css("color","white");
				
				$("#questionTypeFill").css("background","white");
				$("#questionTypeFill").css("color","black");
				
				this.setNext("initQuestionOperationSelect");
				this.next();
				
				$("#questionOperationSelect").css("display","block");
				$("#questionOperationFillwords").css("display","none");
			//填词提
			}else if(questionType==2){
				$("#questionTypeSelect").css("background","white");
				$("#questionTypeSelect").css("color","black");
				
				$("#questionTypeFill").css("background","black");
				$("#questionTypeFill").css("color","white");
				this.setNext("initQuestionOperationFillwords");
				this.next();
				
				$("#questionOperationSelect").css("display","none");
				$("#questionOperationFillwords").css("display","block");
			}
		},
		
		initQuestionOperationSelect:function(){
			//$("#questionOperationSelect>ul>li>input").val("");
		},
		
		fillWordsEvent:function(){
			var outThis = this;
			$("#questionOperationFillwords>ul>li>input").unbind("click");
			$("#questionOperationFillwords>ul>li>input").click(function(){
				var liEl = $(this).parent();
				var status = liEl.attr("status");
				var selValue = $(this).val();
				//alert("staus:"+status);
				if(status==0){
					liEl.attr("status","0");
					$(this).removeAttr("readonly");
				}else if(status==1){
					var flag = true;
					$("#questionOperationWords>ul>li").each(function(){
						var value = $(this).text();
						if(flag&&!value){
							$(this).text(selValue);
							flag = false;
							$(this).attr("from",liEl.attr("index"));
						}
					});
					if(!flag){
						liEl.attr("status","2");
						$(this).attr("readonly","readonly");
					}
				}else if(status==2){
					var index = liEl.attr("index");
					$("#questionOperationWords>ul>li[from="+index+"]").empty();
					$("#questionOperationWords>ul>li[from="+index+"]").removeAttr("from");
					liEl.attr("status","0");
					$(this).removeAttr("readonly");
				}
				outThis.setNext("showQuestionOperationFillwordsStatus");
				outThis.next();
			});
			
			$("#questionOperationFillwords>ul>li>input").unbind("blur");
			$("#questionOperationFillwords>ul>li>input").blur(function(){
				var liEl = $(this).parent();
				var status = liEl.attr("status");
				var value = $(this).val();
				if(value&&status==0){
					$(this).val(value[0]);
					liEl.attr("status","1");
				}
				outThis.setNext("showQuestionOperationFillwordsStatus");
				outThis.next();
			});
			
			$("#questionOperationWords>ul>li").unbind("click");
			$("#questionOperationWords>ul>li").click(function(){
				var index = $(this).attr("from");
				$("#questionOperationFillwords>ul>li[index="+index+"]").attr("status","1");
				$(this).removeAttr("from");
				$(this).empty();
				outThis.setNext("showQuestionOperationFillwordsStatus");
				outThis.next();
			});
			
			$("#questionOperationWordsHeaderSub").unbind("click");
			$("#questionOperationWordsHeaderSub").click(function(){
				if($("#questionOperationWords>ul>li").length>1){
					var lastEl = $("#questionOperationWords>ul>li:last");
					var from = lastEl.attr("from");
					if(from){
						$("#questionOperationFillwords>ul>li[index="+from+"]").attr("status","1");
					}
					lastEl.remove();
					
					outThis.setNext("showQuestionOperationFillwordsStatus");
					outThis.next();
				}else{
					alert("最少需要一个字");
				}
				
			});
			
			$("#questionOperationWordsHeaderAdd").unbind("click");
			$("#questionOperationWordsHeaderAdd").click(function(){
				if($("#questionOperationWords>ul>li").length<7){
					$("#questionOperationWords>ul").append("<li></li>");
				}else{
					alert("最多只能七个字");
				}
				
				$("#questionOperationWords>ul>li").unbind("click");
				$("#questionOperationWords>ul>li").click(function(){
					var index = $(this).attr("from");
					$("#questionOperationFillwords>ul>li[index="+index+"]").attr("status","1");
					$(this).removeAttr("from");
					$(this).empty();
					outThis.setNext("showQuestionOperationFillwordsStatus");
					outThis.next();
				});
			});
		},
		
		//初始化填词功能
		initQuestionOperationFillwords:function(){
			/*
			$("#questionOperationWords>ul>li").empty();
			$("#questionOperationFillwords>ul>li>input").val("");
			*/
			this.setNext("showQuestionOperationFillwordsStatus");
			this.next();
			this.setNext("fillWordsEvent");
			this.next();
			
		},
		
		showQuestionOperationFillwordsStatus:function(){
			$("#questionOperationFillwords>ul>li").each(function(){
				var status = $(this).attr("status");
				if(status==0){
					$(this).children("input").css("background","red");
				}else if(status==1){
					$(this).children("input").css("background","black");
				}else if(status==2){
					$(this).children("input").css("background","green");
				}
			});
		},
		
		checkPeriod:function(){
			var periodId = this.stepData("periodId");
			$("#questionPeriods>ul>li").css("background-color","white");
			$("#"+periodId).css("background-color","red");
			this.flowData({
				periodId:periodId
			});
		},
		checkStage:function(){
			var stageId = this.stepData("stageId");
			$("#questionStages>ul>li").css("background-color","white");
			$("#"+stageId).css("background-color","red");
			this.flowData({
				stageId:stageId
			});
		},
		checkSubject:function(){
			var subjectId = this.stepData("subjectId");
			$("#questionSubjects>ul>li").css("border","1px solid black");
			$("#"+subjectId).css("border","1px solid red");
			this.flowData({
				subjectId:subjectId
			});
		},
		showQuestions:function(){
			var outThis = this;
			var questions = this.stepData("questions");
			$("#questionRecords").empty();
			for(var i=0;i<questions.length;i++){
			
				var question = questions[i];
			
				var type = question.type;
				
				var typeStr;
				if(type==0){
					typeStr = "选择题";
				}else if(type==1){
					typeStr = "填空";
				}else if(type==2){
					typeStr = "填词";
				}
				var trElStr = "<tr class='odd gradeX' questionId='"+question.id+"'>"+
				"<td>"+question.id+"</td>"+
				"<td><img src='"+question.imgUrl+"' style='width:50px;height:50px;'></img></td>"+
				"<td>"+question.question+"</td>"+
				"<td>"+typeStr+"</td>"+
				"<td>"+question.answer+"</td>"+
				"<td>"+question.options+"</td>"+
				"<td style='color:red;cursor: pointer;'>修改</td>"+
				"</tr>";
				
				var trEl = $(trElStr);
				$("#questionRecords").append(trEl);
				
				trEl.click(function(){
					var questionId = $(this).attr("questionId");
					outThis.setNext("initUpdateEdit");
					outThis.nextData({
						questionId:questionId
					});
					outThis.next();
				});
			}
			
		},
		showPeriods:function(){
			var outThis = this;
			var periods = this.stepData("periods");
			$("#questionPeriods>ul").empty();
			for(var i=0;i<periods.length;i++){
				var period = periods[i];
				var liStr = "<li id='"+period.id+"'>题库"+(i+1)+"</li>";
				var liEl = $(liStr);
				liEl.click(function(){
					var id = $(this).attr("id");
					outThis.setNext("checkPeriod");
					outThis.nextData({
						periodId:id
					});
					outThis.next();
					
					outThis.setNext("initStages",function(){
						outThis.setNext("initSubjects",function(){

							outThis.setNext("initQuestions");
							outThis.next();
							
							outThis.setNext("initQuestionCount");
							outThis.next();
						});
						outThis.next();
					});
					outThis.next();
					
				});
				$("#questionPeriods>ul").append(liEl);
			}
		},
		showStages:function(){
			var outThis = this;
			$("#questionStages>ul").empty();
			var stages = this.stepData("stages");
			for(var i=0;i<stages.length;i++){
				var stage = stages[i];
				var liStr = "<li id='"+stage.id+"'>第"+stage.index+"关</li>";
				var liEl = $(liStr);
				liEl.click(function(){
					var stageId = $(this).attr('id');
					
					outThis.setNext("checkStage")
					outThis.nextData({
						stageId:stageId
					});
					outThis.next();
					
					outThis.setNext("initSubjects",function(){
						outThis.setNext("initQuestions",function(){
							
						});
						outThis.next();
						
						outThis.setNext("initQuestionCount",function(){
							
						});
						outThis.next();
					});
					
					outThis.next();
					
				});
				$("#questionStages>ul").append(liEl);
			}
		},
		showSubjects:function(){
			var outThis = this;
			var subjects = this.stepData("subjects");
			$("#questionSubjects>ul").empty();
			for(var i=0;i<subjects.length;i++){
				var subject = subjects[i];
				var divLi = "<li id='"+subject.id+"'>"+
					"<img src='"+subject.imgUrl+"'></img>"+
						"<span>"+subject.name+"--</span>"+
						"<span id='num_"+subject.id+"'>题目数:0</span>"+
					"</li>"
				var liEl = $(divLi);
				liEl.click(function(){
					var id = $(this).attr("id");
					
					outThis.setNext("checkSubject");
					outThis.nextData({
						subjectId:id
					});
					outThis.next();
					
					outThis.setNext("initQuestions");
					outThis.next();
				});
				$("#questionSubjects>ul").append(liEl);
			}
			
		},
		showQuestionCount:function(){
			var questionCounts = this.stepData("questionCounts");
			for(var i=0;i<questionCounts.length;i++){
				var questionCount = questionCounts[i];
				var numSpan = $("#num_"+questionCount.subjectId);
				numSpan.html("题目数"+questionCount.num);
			}
			
		},
		initStages:function(){
			var outThis = this;
			var periodId = this.flowData("periodId");
			request({
				url:"/api/battle/question/stages",
				dataType:'json',
				type:"POST",
				data:{
					periodId:periodId
				},
				success:function(resp){
					if(resp.success){
						outThis.setNext("showStages");
						outThis.nextData({
							stages:resp.data
						});
						outThis.next();
						
						var stages = resp.data;
						if(stages&&stages.length>0){
							var stage = stages[0];
							outThis.setNext("checkStage");
							outThis.nextData({
								stageId:stage.id
							});
							outThis.next();
						}
						outThis.success();
					}else{
						outThis.fail();
					}
				},
				error:function(){
					outThis.fail();
				}
			});
		},
		initQuestionCount:function(){
			var outThis = this;
			var battleId = this.flowData("battleId");
			var stageId = this.flowData("stageId");
			request({
				url:"/api/battle/question/queryQuestionCount",
				dataType:'json',
				type:"POST",
				data:{
					battleId:battleId,
					stageId:stageId
				},
				success:function(resp){
					if(resp.success){
						outThis.setNext("showQuestionCount");
						outThis.nextData({
							questionCounts:resp.data
						});
						outThis.next();
					}
				}
			});
		},
		initQuestions:function(){
			var outThis = this;
			var battleId = this.flowData("battleId");
			var stageId = this.flowData("stageId");
			var subjectId = this.flowData("subjectId");
			request({
				url:"/api/battle/question/questions",
				dataType:'json',
				type:"POST",
				data:{
					battleId:battleId,
					stageId:stageId,
					subjectId:subjectId
				},
				success:function(resp){
					if(resp.success){
						if(resp.success){
							outThis.setNext("showQuestions");
							outThis.nextData({
								questions:resp.data
							});
							outThis.next();
						}
					}
				}
			});
		},
		initSubjects:function(){
			var outThis = this;
			var battleId = this.flowData("battleId");
			request({
				url:"/api/battle/question/subjects",
				dataType:'json',
				type:"POST",
				data:{
					battleId:battleId
				},
				success:function(resp){
					if(resp.success){
						outThis.setNext("showSubjects");
						outThis.nextData({
							subjects:resp.data
						});
						outThis.next();
						
						var subjects = resp.data;
						if(subjects&&subjects.length>0){
							var subject = subjects[0];
							outThis.setNext("checkSubject");
							outThis.nextData({
								subjectId:subject.id
							});;
							outThis.next();
						}
						outThis.success();
					}else{
						outThis.fail();
					}
				},
				error:function(){
					outThis.fail();
				}
			});
		},
		initPeriods:function(){
			var outThis = this;
			var battleId = this.stepData("battleId");
			request({
				url:"/api/battle/question/periods",
				dataType:'json',
				type:"POST",
				data:{
					battleId:battleId
				},
				success:function(resp){
					if(resp.success){
						var periods = resp.data;
						outThis.setNext("showPeriods");
						outThis.nextData({
							periods:periods
						});
						outThis.next();
						if(periods&&periods.length>0){
							var period = periods[0];
							outThis.setNext("checkPeriod");
							outThis.nextData({
								periodId:period.id
							});
							outThis.next();
						}
						
						outThis.success(resp.data);
					}else{
						outThis.fail();
					}
				},
				error:function(){
					outThis.fail();
				}
			});
		}
	});
	
});

</script>
</tiles:putAttribute>
</tiles:insertDefinition>
