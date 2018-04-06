var OrderDetailGridPanel = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(){
		var outThis = this;
		outThis.addEvents({
			"applyRefund":true,
			"refundDeliver":true,
			"refundSign":true,
			"deliver":true,
			"deliverSign":true
		});
		
		OrderDetailGridPanel.superclass.constructor.call(this,{
			getSelected:function(){
				return outThis.getSelectionModel().getSelected();
			},
			tbar:["-",
			      {text:"申请退款",handler:function(){outThis.fireEvent("applyRefund",outThis.getSelected());}},
			      "-",
			      {text:"退款处理",handler:function(){outThis.fireEvent("refundComplete",outThis.getSelected());}},
			      "-",
			      {text:"发货",handler:function(){outThis.fireEvent("deliver",outThis.getSelected());}},
			      "-",
			      {text:"发货签收",handler:function(){outThis.fireEvent("deliverSign",outThis.getSelected());}},
			      "-"],
			columns:[{header:"id"},
					 {header:"订单id"},
					 {header:"物流单号"},
					 {header:"用户名称"},
					 {header:"收获地址"},
					 {header:"手机号码"},
					 {header:"付款状态",renderer:function(payStatus){
						if(payStatus==0){
							return "未付款";
						}else if (payStatus==1) {
							return "已付款";
						}else if (payStatus==2) {
							return "已退款";
						}else if (payStatus==3) {
							return "退款已处理";
						}
					 }},
					 {header:"物流状态",renderer:function(status){
						 if(status==0){
							 return "未发货";
						 }else if (status==1){
							 return "已发货";
						 }else if (status==2) {
							return "已签收";
						}
					 }},
					 {header:"备注"}],
			width:1100,
			autoScroll:true,
			height:800,
			store:new Ext.data.JsonStore({
				url: "/manager/api/get_orderdetail_by_order",
			 	root:"root",
				fields:['id',"orderId","logisticsOrder","address","customerName","phoneNumber","payStatus","deliverStatus","remarks"]
			 })
		});
	}
});