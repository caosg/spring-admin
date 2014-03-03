Ext.define('AM.view.sys.config.ModuleOptionsV',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.ModuleOptionsV',
	id : 'sys.config.ModuleOptionsV',
	title : '配置项',
	region:'south',
	height: document.body.offsetHeight-550,
	autoScroll:true,
	defaults : {
		flex : 1
	},
//	selType : "checkboxmodel",
//	selModel : {
//		checkOnly : false,
//		mode : "SINGLE"
//	},
	initComponent : function() {
		var me = this;
		me.columns = [
		  			{
		  				header : '代码',
		  				dataIndex : 'code',
		  				width:200
		  			},
		  			{
		  				header : '名称',
		  				dataIndex : 'name',
		  				width:200
		  			}
		  		];
		me.store = Ext.create('AM.store.sys.config.ModuleOptionsS');
		me.callParent(arguments);
	}
});