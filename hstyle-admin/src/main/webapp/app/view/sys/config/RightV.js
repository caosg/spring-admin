Ext.define("AM.view.sys.config.RightV", {
	extend : 'Ext.panel.Panel',
	id : 'sys.user.RightVView',
	alias : 'widget.RightV',
	region : "center",
	layout : {
		align : 'stretch',
		type : 'vbox'
	},
	initComponent : function() {
		var me = this;
		me.moduleProperiy = Ext.create('AM.view.sys.config.ModulePropertiesV');
		me.moduleOption = Ext.create('AM.view.sys.config.ModuleOptionsV');
		Ext.applyIf(me, {
			items : [me.moduleProperiy,me.moduleOption]
		});
		me.callParent(arguments);
	}
});