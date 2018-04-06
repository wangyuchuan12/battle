var GoodController = Ext.extend(Ext.util.Observable,{
	goodMainGrid:null,
	goodAddForm:null,
	goodUpdateForm:null,
	init:function(){
		this.goodMainGrid.getStore().load();
	},
	constructor:function(goodMainGrid,goodAddForm,goodUpdateForm,imageManager,sourceForm,distributionTree){
		var outThis = this;
		
		var distributionWin = new Ext.Window({
			title : '配送地管理',
			width:400,
			height:400,
			layout:"fit",
			closeAction:"hide",
			buttonAlign : 'center',
			items:[distributionTree]
		});
		
		distributionTree.on("beforeload",function(node){
			var selectionModel = goodMainGrid.getSelectionModel();
			var record = selectionModel.getSelected();
			distributionTree.loader.dataUrl = "/manager/api/get_distribution?good_id="+record.get("id");
		});
		
		var goodAddFormWin = new Ext.Window({
			title : '商品新增',
			 width : 400,
			 height : 500,
			 minWidth : 300,
			 minHeight : 100,
			 layout : 'fit',
			 plain : true,
			 closeAction:"hide",
			 bodyStyle : 'padding:5px;',
			 buttonAlign : 'center',
			 items:[goodAddForm]
		});
		
		var goodUpdateFormWin = new Ext.Window({
			title : '商品修改',
			 width : 400,
			 height : 500,
			 minWidth : 300,
			 minHeight : 100,
			 layout : 'fit',
			 plain : true,
			 closeAction:"hide",
			 bodyStyle : 'padding:5px;',
			 buttonAlign : 'center',
			 items:[goodUpdateForm]
		});
		
		var imageManagerWin = new Ext.Window({
			width:350,
			height:500,
			title : '图片管理',
			layout : 'fit',
			closeAction:"hide",
			items:[imageManager]
		});
		
		var sourceFormWin = new Ext.Window({
			width:350,
			height:300,
			title : '货源管理',
			layout : 'fit',
			closeAction:"hide",
			items:[sourceForm]
		});
		this.goodMainGrid = goodMainGrid;
		this.goodAddForm = goodAddForm;
		this.goodUpdateForm = goodUpdateForm;
		this.init();
		this.goodMainGrid.getTopToolbar().items.itemAt(1).on("click",function(){
			goodAddFormWin.show();
		});
		this.goodMainGrid.getTopToolbar().items.itemAt(3).on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			if(selectionModel.hasSelection()){
				goodUpdateFormWin.show();
				var record=selectionModel.getSelected();
				Ext.Ajax.request({
					url:"/manager/api/good_info?id="+record.get('id'),
					success:function(resp){
						var content = resp.responseText;
						var obj = eval("("+content+")");
						goodUpdateForm.idText.setValue(obj.id);
						goodUpdateForm.aloneDiscountText.setValue(obj.alone_discount);
						goodUpdateForm.aloneOriginalCostText.setValue(obj.alone_original_cost);
						goodUpdateForm.couponCostText.setValue(obj.coupon_cost);
						goodUpdateForm.flowPriceText.setValue(obj.flow_price);
						goodUpdateForm.groupDiscountText.setValue(obj.group_discount);
						goodUpdateForm.groupNumText.setValue(obj.group_num);
						goodUpdateForm.groupOriginalCostText.setValue(obj.group_original_cost);
						goodUpdateForm.instructionText.setValue(obj.instruction);
						goodUpdateForm.marketPriceText.setValue(obj.market_price);
						goodUpdateForm.nameText.setValue(obj.name);
						goodUpdateForm.statusPanel.setValue(obj.status);
						goodUpdateForm.titleText.setValue(obj.title);
						goodUpdateForm.rankText.setValue(obj.rank);
						
					}
					
				});
			}else{
				Ext.Msg.alert("系统提醒","请选中行");
			}
		});
		this.goodMainGrid.getTopToolbar().items.itemAt(5).on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			if(selectionModel.hasSelection()){
				Ext.Msg.confirm("系统提醒","确定要删除该记录吗？",function(btn){
					if(btn=='yes'){
							var record=selectionModel.getSelected();
							Ext.Ajax.request({
								url:"/manager/api/good_delete?id="+record.get('id'),
								success:function(){
									goodMainGrid.getStore().load();
								},
								failure:function(){
									alert("失败了，赶快检查一下数据，或者通知下大王");
								}
							});
						
						
					}
					
				});
			}else{
				Ext.Msg.alert("系统提醒","请选中行");
			}
			
		});
		
		this.goodMainGrid.getTopToolbar().items.itemAt(7).on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			if(selectionModel.hasSelection()){
				imageManagerWin.show();
				var record = selectionModel.getSelected();
				Ext.Ajax.request({
					url:"/manager/api/good_imgs?good_id="+record.id,
					success:function(response){
						imageManager.clearItem();
						var array = eval("("+response.responseText+")");
						if(array){
							for(var i = 0 ;i<array.length;i++){
								var obj = array[i];
								imageManager.addItem(obj.src,obj.id);
							}
							
						}
						imageManager.doLayout();
					}
				});
			}else{
				Ext.Msg.alert("提示提醒","请选中一行");
			}
		});
		
		this.goodMainGrid.getTopToolbar().items.itemAt(9).on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			
			sourceForm.idText.setValue(null);
			sourceForm.priceText.setValue(null);
			sourceForm.typeText.setValue(null);
			sourceForm.urlText.setValue(null);
			sourceForm.nameText.setValue(null);
			sourceForm.addressText.setValue(null);
			if(selectionModel.hasSelection()){
				var record = selectionModel.getSelected();
				sourceFormWin.show();
				Ext.Ajax.request({
					url:"/manager/api/get_source?good_id="+record.id,
					success:function(response){
						if(response&&response.responseText){
							var obj = eval("("+response.responseText+")");
							sourceForm.idText.setValue(obj.id);
							sourceForm.priceText.setValue(obj.price);
							sourceForm.typeText.setValue(obj.type);
							sourceForm.urlText.setValue(obj.url);
							sourceForm.nameText.setValue(obj.name);
							sourceForm.addressText.setValue(obj.address);
						}
						
					},
					failure:function(){
						alert("failure");
					}
				});
			}else{
				Ext.Msg.alert("提示提醒","请选中一行");
			}
		});
		
		this.goodMainGrid.getTopToolbar().items.itemAt(11).on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			if(selectionModel.hasSelection()){
				distributionWin.show();
			}else{
				Ext.Msg.alert("提示提醒","请选中一行");
			}
		});
		this.goodAddForm.buttons[0].on("click",function(){
			goodAddForm.getForm().submit({
				url:"/manager/api/add_good",
				method:"POST",
				scope:this,
				 success: function(form, action) {
					if(action.result.success){
						Ext.Msg.alert("Success", "成功");
					}else{
						Ext.Msg.alert("Success", "发生了错误");
					}
					goodAddFormWin.hide();
					goodAddForm.getForm().reset();
					goodMainGrid.getStore().load();
				},
				failure: function(form, action) {
					switch (action.failureType) {
						case Ext.form.Action.CLIENT_INVALID:
							Ext.Msg.alert("Failure", "Form fields may not be submitted with invalid values");
							break;
						case Ext.form.Action.CONNECT_FAILURE:
							Ext.Msg.alert("Failure", "Ajax communication failed");
							break;
						case Ext.form.Action.SERVER_INVALID:
						   Ext.Msg.alert("Failure", "server invalid");
				   }
				}

			},this);
		},this);
		
		this.goodUpdateForm.buttons[0].on("click",function(){
			goodUpdateForm.getForm().submit({
				url:"/manager/api/update_good",
				method:"POST",
				scope:this,
				 success: function(form, action) {
					if(action.result.success){
						Ext.Msg.alert("Success", "成功");
					}else{
						Ext.Msg.alert("Success", "发生了错误");
					}
					goodUpdateFormWin.hide();
					goodUpdateForm.getForm().reset();
					goodMainGrid.getStore().load();
				},
				failure: function(form, action) {
					switch (action.failureType) {
						case Ext.form.Action.CLIENT_INVALID:
							Ext.Msg.alert("Failure", "Form fields may not be submitted with invalid values");
							break;
						case Ext.form.Action.CONNECT_FAILURE:
							Ext.Msg.alert("Failure", "Ajax communication failed");
							break;
						case Ext.form.Action.SERVER_INVALID:
						   Ext.Msg.alert("Failure", "server invalid");
				   }
				}

			},this);
			
		},this);
		
		sourceForm.buttons[0].on("click",function(){
			var selectionModel = goodMainGrid.getSelectionModel();
			var record = selectionModel.getSelected();

			sourceForm.getForm().submit({
				url:"/manager/api/save_source?good_id="+record.get("id"),
				method:"post",
				success:function(form, action){
					if(action.result.success){
						sourceFormWin.hide();
					}
				},
				failure:function(){
					Ext.Msg.alert("系统提醒","保存失败，找出问题或者联系大王");
				}
			});
		});
		imageManager.on("uploadClick",function(){
			
			var selectionModel = goodMainGrid.getSelectionModel();
			var selectRecord = selectionModel.getSelected();
			var fileUploadWin = new FileUploadWin("/manager/api/add_img?good_id="+selectRecord.id);
			fileUploadWin.show();
			fileUploadWin.on("submitSuccess",function(){
				fileUploadWin.close();
				var record = selectionModel.getSelected();
				Ext.Ajax.request({
					url:"/manager/api/good_imgs?good_id="+record.id,
					success:function(response){
						imageManager.clearItem();
						var array = eval("("+response.responseText+")");
						if(array){
							for(var i = 0 ;i<array.length;i++){
								var obj = array[i];
								imageManager.addItem(obj.src,obj.id);
							}
							
						}
						imageManager.doLayout();
					}
				});
			});
		});
		
		imageManager.on("removeClick",function(id , panel){
			if(id){
				Ext.Ajax.request({
					url:"/manager/api/delete_img?img_id="+id,
					success:function(){
						var selectionModel = goodMainGrid.getSelectionModel();
						var record = selectionModel.getSelected();
						Ext.Ajax.request({
							url:"/manager/api/good_imgs?good_id="+record.id,
							success:function(response){
								imageManager.clearItem();
								var array = eval("("+response.responseText+")");
								if(array){
									for(var i = 0 ;i<array.length;i++){
										var obj = array[i];
										imageManager.addItem(obj.src,obj.id);
									}
									
								}
								imageManager.doLayout();
							}
						});
					},
					failure:function(){
						alert("failure");
					}
				});
			}else{
				Ext.Msg.alert("系统提示","请选中一张图片");
			}
		});
		GoodController.superclass.constructor.call(this,{
			
		});
	}
});