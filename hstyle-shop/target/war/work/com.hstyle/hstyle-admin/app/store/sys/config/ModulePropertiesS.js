Ext.define('AM.store.sys.config.ModulePropertiesS', {
	extend : 'Ext.data.Store',
	model : 'AM.model.sys.config.ModulePropertiesM',
	alias : 'widget.modulePropertiesS',
	loadMask : true,
	sorters: [{
        property: 'orderNo',
        direction: 'ASC'
    }],
	proxy : {
		type : "ajax",
		url : 'moduleProperty/list.do',
		reader : {
			type : 'json'
		},
		listeners : {
			exception : AM.proxyException
		}

	}
});