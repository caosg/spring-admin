/**
 * 数据字段主面板
 */
Ext.define("AM.view.config.platform.Main", {
	extend : 'Ext.panel.Panel',
	id : 'config.PlatformsView',
	alias : 'widget.platformmain',
	title : "平台店铺",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//右侧Grid列表
		me.grid = Ext.create('AM.view.config.platform.List');
		me.items = [me.form,me.grid];
		me.callParent(arguments);
	}
});