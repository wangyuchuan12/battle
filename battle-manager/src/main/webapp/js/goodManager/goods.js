var GoodMainGrid = Ext.extend(Ext.grid.GridPanel,{
		constructor:function(){
			GoodMainGrid.superclass.constructor.call(this,{
																	 	tbar:["-",{text:"新增"},"-",{text:"修改"},"-",{text:"删除"},"-",{text:"图片管理"},"-",{text:"货源管理"},"-",{text:"配送地管理"},"-"],
																		
																		columns:[{header:"id"},
																				 {header:"名称"},
																				 {header:"单卖原价"},
																				 {header:"单卖折扣"},
																				 {header:"团购原价"},
																				 {header:"团购折扣"},
																				 {header:"市场价"},
																				 {header:"几人团"},
																				 {header:"创建时间"},
																				 {header:"货源地址"},
																				 {header:"启用"},
																				 {header:"优先级"}],
																		width:1300,
																		autoScroll:true,
																		height:800,
																		store:new Ext.data.JsonStore({
																										url: "/manager/api/good_list",
																									 	root:"root",
																										fields:['id',"name","alone_original_cost","alone_discount",
																											"group_original_cost","group_discount","market_price","group_num","","source_address","status","rank"]
																									 })
													
																	 });
		}
	});


