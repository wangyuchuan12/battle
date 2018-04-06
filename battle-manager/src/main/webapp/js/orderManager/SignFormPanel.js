var SignFormPanel = Ext.extend(Ext.form.FormPanel,{
	devieryTimeTextField:null,
	remarkTextField:null,
	constructor:function(){
		var outThis = this;
		outThis.devieryTimeTextField = new Ext.form.DateField({
			fieldLabel:"签收时间",
			format:"Y-m-d H:i:s",
			name:"sign_time",
			maxValue:new Date()
		});
		outThis.remarkTextField = new Ext.form.TextField({
			fieldLabel:"备注",
			name:"remark"
		});
		SignFormPanel.superclass.constructor.call(this,{
			items:[outThis.devieryTimeTextField,outThis.remarkTextField],
			frame:true,
			buttons:[{text:"确定"}]
		});
	}
});