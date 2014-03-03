/**
 * 数据字段主面板，主面板包含一个上下布局的FormPanel和GridPanel
 */
Ext.define("AM.view.sys.dict.Main", {
	extend : 'Ext.panel.Panel',
	id : 'sys.dict.DictsView',
	alias : 'widget.dictmain',
	title : "数据字典",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//右侧Grid列表
		me.grid = Ext.create('AM.view.sys.dict.List');
		me.items = [me.form,me.grid];

		me.callParent(arguments);
	}
});