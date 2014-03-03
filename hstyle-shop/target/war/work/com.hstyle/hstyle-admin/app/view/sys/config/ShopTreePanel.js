/**
 * 部门管理主视图，左侧为部门树，右侧上部为部门基本信息，右侧下部为部门下的人员列表
 */
Ext.define("AM.view.sys.config.ShopTreePanel", {
	extend : 'Ext.tree.Panel',
	id : 'sys.config.ShopTreePanel',
	alias : 'widget.ShopTreePanel',
	title : "店铺平台",
	store : 'sys.config.ShopTreeS',
	rootVisible : false,
	split : true,
	closable : true,
	region:'west',
	width : 200,
	border : 0,
	initComponent : function() {
		var me = this;
		me.callParent(arguments);
	}
});