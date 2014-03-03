/**
 * 数据字段主面板
 */
Ext.define("AM.view.config.warehouse.Main", {
	extend : 'Ext.panel.Panel',
	id : 'config.WareHouseAreasView',
	alias : 'widget.warehouseareamain',
	title : "仓库管理",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//右侧Grid列表
		me.grid = Ext.create('AM.view.config.warehouse.List');
		me.items = [me.form,me.grid];
		me.callParent(arguments);
	}
});