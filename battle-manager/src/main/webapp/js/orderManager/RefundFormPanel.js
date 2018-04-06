var RefundFormPanel = Ext.extend(Ext.form.FormPanel,{
	refundDeviveryTimeTextField:null,
	refundSignTimeTextField:null,
	refundAmountTextField:null,
	remarkTextField:null,
	constructor:function(){
		outThis = this;
		outThis.refundDeviveryTimeTextField = new Ext.form.DateField({
			fieldLabel:"退款发货时间",
			format:"Y-m-d H:i:s",
			name:"refund_devivery_time",
			maxValue:new Date()
		});
		
		outThis.refundSignTimeTextField = new Ext.form.DateField({
			fieldLabel:"退款签收时间",
			format:"Y-m-d H:i:s",
			name:"refund_sign_time",
			maxValue:new Date()
		});
		
		outThis.refundAmountTextField = new Ext.form.TextField({
			fieldLabel:"退款金额",
			name:"refund_amount"
		});
		
		outThis.remarkTextField = new Ext.form.TextField({
			fieldLabel:"备注",
			name:"remark"
		});
		RefundFormPanel.superclass.constructor.call(this,{
			items:[outThis.refundDeviveryTimeTextField,outThis.refundSignTimeTextField,outThis.refundAmountTextField,outThis.remarkTextField],
			frame:true,
			buttons:[{text:"确定"}]
		});
	}
});