/**
 * 平台远程数据加载 Store
 */
Ext.define("AM.store.config.PlatformS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.PlatformM',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    pageSize: 25,
    sorters: [{
        property: 'id',
        direction: 'DESC'
    }],    
    proxy: {
        type: "ajax",
        api : {
			read : 'platform/listPlatform.do',
			readAll:'platform/getCommonAllPlatform.do'
		},
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
	/**
	,
    listeners: {
    	'load':function(store , records, successful, eOpts){
    		for(var i =0;i<records.length;i++){
    			var record = records[i];
    			for(var j=0;j<record.data.shops.length;j++){
    				alert(record.data.shops[j].status);
    			}
    		}
    	}
    }
    */
});