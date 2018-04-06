var SendFormPanel = Ext.extend(Ext.form.FormPanel,{
	logisticsOrderTextField:null,
	deliveryTimeTextField:null,
	remarkTextField:null,
	constructor:function(){
		outThis = this;
		outThis.logisticsOrderTextField= new Ext.form.TextField({
			fieldLabel:"物流单子",
			name:"logistics_order"
		});
		outThis.deliveryTimeTextField = new Ext.form.DateField({
			fieldLabel:"发货时间",
			format:"Y-m-d H:i:s",
			name:"delivery_time",
			maxValue:new Date()
		});
		
		outThis.remarkTextField = new Ext.form.TextField({
			fieldLabel:"备注",
			name:"remark"
		});
		SendFormPanel.superclass.constructor.call(this,{
			items:[outThis.logisticsOrderTextField,outThis.deliveryTimeTextField,outThis.remarkTextField],
			frame:true,
			buttons:[{text:"确定"}]
		});
	}
});