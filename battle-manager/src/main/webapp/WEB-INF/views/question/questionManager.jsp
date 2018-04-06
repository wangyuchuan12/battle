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
        <div class="col-lg-12">
            <div class="panel panel-default">
            
            	
            
                <div class="panel-body">
                	
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



</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
var periodId;
var stageId;
var subjectId;
var battleId = 1;
$(document).ready(function() {
	battleId = $("#battleId").val();
	initPeriods(battleId,{
		success:function(){
			initSubjects(battleId,{
				success:function(subjects){
					initStages(periodId,{
						success:function(){
							initQuestions(battleId,stageId,subjectId);
							initQuestionCount(battleId,stageId);
							onEvent();
						}
					});
				}
			});
		}
	});
	
});


function onEvent(){
	
}

function checkPeriod(id){
	$("#questionPeriods>ul>li").css("background-color","white");
	$("#"+id).css("background-color","red");
	periodId = id;
}

function checkStage(id){
	
	$("#questionStages>ul>li").css("background-color","white");
	$("#"+id).css("background-color","red");
	stageId = id;
}

function checkSubject(id){
	$("#questionSubjects>ul>li").css("border","1px solid black");
	$("#"+id).css("border","1px solid red");
	subjectId = id;
}

function showQuestions(questions){
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
		var trEl = "<tr class='odd gradeX'>"+
		"<td>"+question.id+"</td>"+
		"<td><img src='"+question.imgUrl+"' style='width:50px;height:50px;'></img></td>"+
		"<td>"+question.question+"</td>"+
		"<td>"+typeStr+"</td>"+
		"<td>"+question.answer+"</td>"+
		"<td>"+question.options+"</td>"+
		"<td></td></tr>";
		
		$("#questionRecords").append(trEl);
	}
}

function showPeriods(periods){
	$("#questionPeriods>ul").empty();
	for(var i=0;i<periods.length;i++){
		var period = periods[i];
		var liStr = "<li id='"+period.id+"'>题库"+(i+1)+"</li>";
		var liEl = $(liStr);
		liEl.click(function(){
			var id = $(this).attr("id");
			checkPeriod(id);
			initStages(id,{
				success:function(){
					initSubjects(battleId,{
						success:function(){
							initQuestions(battleId,stageId,subjectId);
							initQuestionCount(battleId,stageId);
						}
					});
				}
				
			})
		});
		$("#questionPeriods>ul").append(liEl);
	}
	
}

function showStages(stages){
	$("#questionStages>ul").empty();
	for(var i=0;i<stages.length;i++){
		var stage = stages[i];
		var liStr = "<li id='"+stage.id+"'>第"+stage.index+"关</li>";
		var liEl = $(liStr);
		liEl.click(function(){
			var id = $(this).attr('id');
			checkStage(id);
			initSubjects(battleId,{
				success:function(){
					initQuestions(battleId,id,subjectId);
					initQuestionCount(battleId,stageId);
				}
			})
			
		});
		$("#questionStages>ul").append(liEl);
	}
}


function showSubjects(subjects){
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
			checkSubject(id);
			
			initQuestions(battleId,stageId,id);
		});
		$("#questionSubjects>ul").append(liEl);
	}
}

function showQuestionCount(questionCounts){
	for(var i=0;i<questionCounts.length;i++){
		var questionCount = questionCounts[i];
		var numSpan = $("#num_"+questionCount.subjectId);
		numSpan.html("题目数"+questionCount.num);
	}
}

function initQuestions(battleId,stageId,subjectId){
	$.ajax({
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
					showQuestions(resp.data);
				}
			}
		}
	});
}

function initStages(periodId,callback){
	$.ajax({
		url:"/api/battle/question/stages",
		dataType:'json',
		type:"POST",
		data:{
			periodId:periodId
		},
		success:function(resp){
			if(resp.success){
				showStages(resp.data);
				
				var stages = resp.data;
				if(stages&&stages.length>0){
					var stage = stages[0];
					checkStage(stage.id);
				}
				if(callback&&callback.success){
					callback.success();
				}
			}else{
				if(callback&&callback.fail){
					callback.fail();
				}
			}
		},
		error:function(){
			if(callback&&callback.fail){
				callback.fail();
			}
		}
	});
}

function initQuestionCount(battleId,stageId){
	$.ajax({
		url:"/api/battle/question/queryQuestionCount",
		dataType:'json',
		type:"POST",
		data:{
			battleId:battleId,
			stageId:stageId
		},
		success:function(resp){
			if(resp.success){
				showQuestionCount(resp.data);
			}
		}
	});
}

function initSubjects(battleId,callback){
	$.ajax({
		url:"/api/battle/question/subjects",
		dataType:'json',
		type:"POST",
		data:{
			battleId:battleId
		},
		success:function(resp){
			if(resp.success){
				showSubjects(resp.data);
				var subjects = resp.data;
				if(subjects&&subjects.length>0){
					var subject = subjects[0];
					checkSubject(subject.id);
				}
				if(callback&&callback.success){
					callback.success(subjects);
				}
			}else{
				if(callback&&callback.fail){
					callback.fail();
				}
			}
		},
		error:function(){
			if(callback&&callback.fail){
				callback.fail();
			}
		}
	});
}

function initPeriods(battleId,callback){
	$.ajax({
		url:"/api/battle/question/periods",
		dataType:'json',
		type:"POST",
		data:{
			battleId:battleId
		},
		success:function(resp){
			if(resp.success){
				showPeriods(resp.data);
				var periods = resp.data;
				if(periods&&periods.length>0){
					var period = periods[0];
					checkPeriod(period.id);
				}
				
				if(callback&&callback.success){
					callback.success();
				}
			}else{
				if(callback&&callback.fail){
					callback.fail();
				}
			}
		},
		error:function(){
			if(callback&&callback.fail){
				callback.fail();
			}
		}
	});
}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
