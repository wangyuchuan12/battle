var GroupDetailGridPanel = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(){
		GroupDetailGridPanel.superclass.constructor.call(this,{
			columns:[{header:"id"},
					 {header:"角色",renderer:function(role){
						 if(role==1){
							 return "团长";
						 }else if (role==2) {
							return "沙发";
						}else if (role==3) {
							return "参团者";
						}
					 }},
					 {header:"订单id"},
					 {header:"默认地址"},
					 {header:"手机号码"},
					 {header:"城市"},
					 {header:"国家"},
					 {header:"团id"},
					 {header:"头像"},
					 {header:"语言"},
					 {header:"用户信息id"},
					 {header:"用户别昵"},
					 {header:"用户所在的省"},
					 {header:"性别"},
					 {header:"付款方式",renderer:function(type){
						 if(type==0){
							 return "团购";
						 }else if (type==1) {
							return "单买";
						}else if (type==2){
							return "开团劵购买";
						}else {
							return "另外的方式："+type;
						}
					 }},
					 {header:"token"}],
			width:1100,
			autoScroll:true,
			height:800,
			store:new Ext.data.JsonStore({
				url: "/manager/api/get_orderdetail_by_order",
			 	root:"root",
				fields:['id',"role",
				        "orderId","defaultAdress",
				        "phonenumber","city",
				        "country","groupid",
				        "headimgurl","language",
				        "userinfoId","nickname","province",
				        "sex","type","token"]
			 })
		});
	}
});