Ext.define("AM.view.config.logisticcompany.Main", {
	extend : 'Ext.panel.Panel',
	id : 'config.LogisticCompanysView',
	alias : 'widget.lgcompanymain',
	title : "物流公司",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//右侧Grid列表
		me.grid = Ext.create('AM.view.config.logisticcompany.List');
		me.items = [me.form,me.grid];
		me.callParent(arguments);
	}
});