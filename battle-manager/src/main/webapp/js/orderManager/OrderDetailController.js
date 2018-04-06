var OrderDetailController = Ext.extend(Ext.util.Observable,{
	orderDetailGridPanel:null,
	constructor:function(orderDetailGridPanel){
		this.orderDetailGridPanel = orderDetailGridPanel;
		var outThis = this;
		orderDetailGridPanel.on("applyRefund",function(record){
			
			var callback = new Object();
			callback.success=function(){
				outThis.refush();
			}
			Ext.Msg.prompt("提示","请输入备注信息",function(btn,text){
				if(btn=="ok"){
					outThis.requestHandle(record.id,"apply_refund",text,"",callback);
				}
			});
			
		});

		orderDetailGridPanel.on("refundComplete",function(record){
			var callback = new Object();
			callback.success=function(){
				outThis.refush();
			}
			Ext.Msg.prompt("提示","请输入备注信息",function(btn,text){
				if(btn=="ok"){
					outThis.requestHandle(record.id,"refund_complete",text,"",callback);
				}
			});
					
		});
		
		
		orderDetailGridPanel.on("deliver",function(record){
			var callback = new Object();
			callback.success=function(){
				outThis.refush();
			}
			Ext.Msg.prompt("提示","请输入物流单号",function(btn,logisticsNo){
				if(btn=="ok"){
					Ext.Msg.prompt("提示","请输入备注信息",function(btn,remarks){
						if(btn=="ok"){
							outThis.requestHandle(record.id,"deliver",remarks,logisticsNo,callback);
						}
					});
				}
			});
		});
		
		
		orderDetailGridPanel.on("deliverSign",function(record){
			var callback = new Object();
			callback.success=function(){
				outThis.refush();
			}
			Ext.Msg.prompt("提示","请输入备注信息",function(btn,text){
				if(btn=="ok"){
					outThis.requestHandle(record.id,"deliver_sign",text,"",callback);
				}
			});
					
		});
	},
	refush:function(){
		var outThis = this;
		var selectionModel = this.orderDetailGridPanel.getSelectionModel();
		var record = selectionModel.getSelected();
		Ext.Ajax.request({
			url:"/manager/api/get_orderdetail_by_order?order_id="+record.get("orderId"),
			success:function(resp){
				var obj = eval("("+resp.responseText+")");
				outThis.orderDetailGridPanel.getStore().loadData(obj);
			}
		});
	},
	requestHandle:function(id,way,remarks,logisticsNo,callback){
		var obj = new Object();
		obj.group_partake_id = id;
		obj.way = way;
		obj.logistics_no = logisticsNo;
		obj.remarks = remarks;
		Ext.Ajax.request({
			url:"/manager/api/order_handle",
			method:"POST",
			params:obj,
			success:function(response){
				callback.success(response);
			},
			failure:function(response){
				callback.failure(response);
			}
		});
	}
});