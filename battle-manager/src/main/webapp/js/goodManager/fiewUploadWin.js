var FileUploadWin = Ext.extend(Ext.Window,{
	constructor:function(url){
		var outThis = this;
		this.addEvents({
			"submitSuccess":true
		});
		var form = new Ext.form.FormPanel({
							 baseCls : 'x-plain',
							 labelWidth : 70,
							 fileUpload : true,
							 defaultType : 'textfield',
							
							 items : [{
								xtype : 'textfield',
								fieldLabel : '上传文件名',
								name : 'img',
								id : 'userfile',
								inputType : 'file',
								blankText : 'File can\'t not empty.',
								anchor : '100%' // anchor width by percentage
							   }]
							});
		FileUploadWin.superclass.constructor.call(this,{
			width:300,
			height:100,
			resizable:false,
			items:[form],
			title:"文件上传",
			buttons:[{text:"上传",handler:function(){
				if(form.items.itemAt(0).getValue()){
					form.getForm().submit({
						url:url,
						method:"post",
						success:function(fom,action){
							if(action.result.success){
								outThis.fireEvent("submitSuccess");
							}else{
								Ext.Msg.alert("错误","系统发生错误，找出问题或者联系大王");
							}
						},
						failure:function(){
							Ext.Msg.alert("错误","上传失败");
						}
					});
				}else{
					Ext.Msg.alert("系统提醒","请选择图片");
				}
			}}]
		});
	}
});