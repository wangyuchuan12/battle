var ImageManager = Ext.extend(Ext.Panel,{
	selectId:null,
	selectItem:null,
	clearItem:function(){
		this.items.each(function(item){
			this.remove(item);
		},this);
	},
	addItem:function(src,id){
		var outThis = this;
		
		
		
		var panel = new Ext.Panel({
			id:id,
			itemId:id,
			html:"<img id='"+id+"' src='"+src+"' style='width:300;height:300;border:1px solid black'></img>",
			scope:this,
			listeners:{scope:this,render:function(){
				panel.getEl().on("click",function(){
					outThis.items.each(function(item){
						if(item){
							item.body.update("<img id='"+id+"' src='"+src+"' style='width:300;height:300;border:1px solid black'></img>");
							outThis.selectId = id;
							outThis.selectItem = panel;
							outThis.fireEvent("imageItemOnClick",id,panel);
							
						}
					});
					panel.body.update("<img id='"+id+"' src='"+src+"' style='width:300;height:300;border:1px solid red;'></img>");
					outThis.selectid = id;
				},this);
			}}
		});
		this.add(panel);
		
		
	},
	
	scope:this,
	constructor:function(){
		var outThis = this;
		outThis.addEvents({
		      "imageItemOnClick":true,
		      "uploadClick":true,
		      "updateClick":true,
		      "removeClick":true
		   });
		
		ImageManager.superclass.constructor.call(this,{
			frame:true,
			width:350,
			height:500,
			autoScroll:true,
			scope:this,
			listeners:{scope:this,"render":function(){
				this.getTopToolbar().items.itemAt(0).on("click",function(){outThis.fireEvent("uploadClick",outThis.selectId,outThis.selectItem)});
				this.getTopToolbar().items.itemAt(2).on("click",function(){outThis.fireEvent("updateClick",outThis.selectId,outThis.selectItem)});
				this.getTopToolbar().items.itemAt(4).on("click",function(){outThis.fireEvent("removeClick",outThis.selectId,outThis.selectItem)});
			}},
			tbar:[{text:"上传图片"},"-",{text:"更改图片"},"-",{text:"删除图片"}]
		});
	}
});