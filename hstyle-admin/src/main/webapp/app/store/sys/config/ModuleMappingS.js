Ext.define('AM.store.sys.config.ModuleMappingS', {
    extend: 'Ext.data.Store',
    alias: 'widget.ModuleMappingS',
    requires: [
        'AM.model.sys.config.moduleMappingM'
    ],
    model: 'AM.model.sys.config.moduleMappingM',
    storeId: 'ModuleMappingStore',
    sorters: [{
        property: 'orderNo',
        direction: 'ASC'
    }],
    proxy: {
        type: 'ajax',
        api : {
        	read : 'moduleProperty/listSysConfig.do'
		},
        reader: {
            type: 'json'
        }
    }
    
});