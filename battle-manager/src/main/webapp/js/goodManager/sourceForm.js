var SourceForm = new Ext.extend(Ext.form.FormPanel,{
	idText:null,
	priceText:null,
	typeText:null,
	urlText:null,
	addressText:null,
	constructor:function(){
		this.idText = new Ext.form.TextField({
	  		fieldLabel:"id",
			readOnly:true,
			name:"id"
		});
		this.priceText = new Ext.form.NumberField({
	  		fieldLabel:"价格",
			name:"price",
			allowBlank:false
		});
		this.typeText = new Ext.form.NumberField({
	  		fieldLabel:"类型(0表示线下，1表示线上)",
			name:"type",
			value:1,
			regex: /^[0,1]$/,
			allowBlank:false
		});
		this.urlText = new Ext.form.TextField({
	  		fieldLabel:"url",
			name:"url",
			regex:/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&=]*)?$/,
			allowBlank:false
		});
		this.addressText = new Ext.form.TextField({
	  		fieldLabel:"商家地址",
			name:"address",
			allowBlank:false
		});
		
		this.nameText = new Ext.form.TextField({
			fieldLabel:"名称",
			name:"name",
			allowBlank:false
		});
		SourceForm.superclass.constructor.call(this,{
			frame:true,
			width:350,
			fileUpload:true,
			items:[this.idText,this.nameText,this.priceText,this.typeText,this.urlText,this.addressText],
			buttons:[{text:"确定"}]
		});
		
	}
});