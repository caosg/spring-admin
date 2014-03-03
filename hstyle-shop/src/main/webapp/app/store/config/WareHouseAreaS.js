/**
 * 仓库区域远程数据加载 Store
 */
Ext.define("AM.store.config.WareHouseAreaS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.WareHouseAreaM',
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
			read : 'warehouse/listWareHouseArea.do',
			readAll:'warehouse/getAllArea.do'
		},        
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
});