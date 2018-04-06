var GoodInfo = Ext.extend(Ext.form.FormPanel,{
										idText:null,
										aloneDiscountText:null,
										aloneOriginalCostText:null,
										couponCostText:null,
										flowPriceText:null,
										groupDiscountText:null,
										groupNumText:null,
										groupOriginalCostText:null,
										instructionText:null,
										marketPriceText:null,
										nameText:null,
										statusPanel:null,
										constructor:function(){
												
												this.idText = new Ext.form.TextField({
																				  		fieldLabel:"id",
																						readOnly:true,
																						name:"id"
																				  });
												this.aloneDiscountText = new Ext.form.NumberField({
																						fieldLabel:"单卖折扣",
																						name:"alone_discount",
																						value:1,
																						maxValue:1
																					});
												this.aloneOriginalCostText = new Ext.form.NumberField({
																						fieldLabel:"单卖原价",
																						name:"alone_original_cost",
																						allowBlank:false

																					});
												this.couponCostText = new Ext.form.NumberField({
																						fieldLabel:"需要开团劵",
																						name:"coupon_cost",
																						value:1,
																						allowBlank:false,
																						minValue:0,
																						decimalPrecision:0
																					});
												this.flowPriceText = new Ext.form.NumberField({
																						fieldLabel:"物流费",
																						name:"flow_price",
																						allowBlank:false,
																						value:0
																					});
												this.groupDiscountText = new Ext.form.NumberField({
																						fieldLabel:"团购折扣",
																						name:"group_discount",
																						value:1,
																						allowBlank:false,
																						maxValue:1
																					});
												this.groupNumText = new Ext.form.NumberField({
																						fieldLabel:"需要组团数量",
																						name:"group_num",
																						value:5,
																						minValue:1,
																						decimalPrecision:0,
																						allowBlank:false
																					});
												this.groupOriginalCostText = new Ext.form.NumberField({
																						fieldLabel:"团购原价",
																						name:"group_original_cost",
																						allowBlank:false
																					});
												this.instructionText = new Ext.form.TextField({
																						fieldLabel:"介绍",
																						name:"instruction",
																						allowBlank:false
																					});
												this.fileText = new Ext.form.TextField({
																						fieldLabel:"商品图片",
																						inputType:"file",
																						name:"head_img"
																					});
												this.marketPriceText = new Ext.form.NumberField({
																						fieldLabel:"市场价",
																						name:"market_price",
																						allowBlank:false
																					});
												this.nameText = new Ext.form.TextField({
																						fieldLabel:"商品名称",
																						name:"name",
																						allowBlank:false
																					});
												this.titleText = new Ext.form.TextField({
																						fieldLabel:"商品标题",
																						name:"title",
																						allowBlank:false
																					}); 
												this.rankText = new Ext.form.NumberField({
																						fieldLabel:"优先级",
																						name:"rank",
																						allowBlank:false
																					});
												this.statusPanel = new Ext.form.RadioGroup({
													layout: 'table',
													fieldLabel: '启用',
													defaultType: 'radio',
													isFormField: true,
													items:[{
														name:"status",
														boxLabel:"启用",
														inputValue:1
													},{
														name:"status",
														boxLabel:"禁用",
														inputValue:0,
														checked:true
												
													}]
												});
												GoodInfo.superclass.constructor.call(this,{
																							   		frame:true,
																									width:350,
																									height:400,
																									fileUpload:true,
																							   		items:[this.idText,this.nameText,this.titleText,this.aloneDiscountText,this.aloneOriginalCostText,
																										this.couponCostText,this.flowPriceText,this.groupDiscountText,
																										this.groupNumText,this.groupOriginalCostText,this.instructionText,this.marketPriceText,this.fileText,this.rankText,this.statusPanel],
																									buttons:[{text:"确定",scope:this,handler:function(){
																										
																									}}]
																							   
																							   });
											}
									});