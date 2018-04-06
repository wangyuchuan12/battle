var DistributionTree = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(){
		DistributionTree.superclass.constructor.call(this,{
			animate:true,
			title:"所有配送地",
			collapsible:true,
			autoScroll:true,
		    autoHeight:true,
		    lines:true,
		    loader:new Ext.tree.TreeLoader({dataUrl:""}),
		    root:new Ext.tree.AsyncTreeNode({
		           id:"root",
		           rootVisible:false,
		           text:"所有地区",//节点名称
		           expanded:false,//展开
		           leaf:false
		       })
		});
	}
});