var OrderMainGrid = Ext.extend(Ext.grid.GridPanel,{
	getSelected:function(){
		var selectionModel = this.getSelectionModel();
		return selectionModel.getSelected();
	},
	constructor:function(){
		var outThis = this;
		outThis.addEvents({
			"sendClick":true,
			"signClick":true,
			"refundClick":true,
			"goodInfoClick":true,
			"customerInfoClick":true,
			"statusSelected":true,
			"groupInfoClick":true,
			"orderHandleClick":true
		});
		//1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理
		var store = new Ext.data.SimpleStore({
			   fields : ['value', 'text'],
			   data : [['0',"全部"],['1',"未付款"],['2','已付款未发货'],['3','已发货未签收'],['4','已签收'],['5','退款未处理'],['6','退款已处理']]
			});
		OrderMainGrid.superclass.constructor.call(this,{
			tbar:["-",{text:"发货",handler:function(){outThis.fireEvent("sendClick",outThis.getSelected());}},"-",
			      {text:"签收",handler:function(){outThis.fireEvent("signClick",outThis.getSelected());}},"-",
			      
			      {text:"退款处理",handler:function(){outThis.fireEvent("refundClick",outThis.getSelected());}},"-",
			      {xtype:"combo",store:store,
			    	  valueField:"value",value:0,
			    	  listeners:{"select":function(combo,record,index){
			    		  outThis.fireEvent("statusSelected",record.get("value"));
			    	  }},
			    	  editable :false,triggerAction:'all',
			    	  displayField:"text",mode:"local"},"-",
			      {text:"查看商品信息",handler:function(){outThis.fireEvent("goodInfoClick",outThis.getSelected());}},"-",
			      {text:"查看顾客信息",handler:function(){outThis.fireEvent("customerInfoClick",outThis.getSelected());}},"-",
			      {text:"查看组团情况",handler:function(){outThis.fireEvent("groupInfoClick",outThis.getSelected());}},"-",
			      {text:"订单处理",handler:function(){outThis.fireEvent("orderHandleClick",outThis.getSelected());}},"-"],
																		columns:[{header:"id"},
																				 {header:"货物id"},
																				 {header:"货物名称"},
																				 {header:"物流费用"},
																				 {header:"商品单价"},
																				 {header:"订单类型",renderer:function(a){
																					 if(a==0){
																						 return "团购";
																					 }else if (a==1) {
																						return "单卖";
																					}else if (a==2) {
																						return "开团劵购买";
																					}
																				 }},
																				 {header:"订单创建时间",width:200}],
																		width:1898,
																		autoScroll:true,
																		height:800,
																		store:new Ext.data.JsonStore({
																										url: "/manager/api/order_list",
																									 	root:"root",
																										fields:['id',"good_id","good_name","flow_price","good_price","type","created_at"]
																									 })
		});
	}	
});