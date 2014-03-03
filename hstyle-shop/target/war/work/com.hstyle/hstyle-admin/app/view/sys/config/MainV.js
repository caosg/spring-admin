/**
 * 系统配置主视图，左侧为模块列表，右侧为配置属性
 */
Ext.define("AM.view.sys.config.MainV", {
	extend : 'Ext.panel.Panel',
	id : 'sys.config.ModuleView',
	alias : 'widget.mainV',
	title : "配置项定义",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		me.moduleList = Ext.create('AM.view.sys.config.ModuleListV');
		me.right = Ext.create('AM.view.sys.config.RightV');
		me.items = [me.moduleList,me.right];

		me.callParent(arguments);
	}
});