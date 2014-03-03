/**
 * 仓库远程数据加载 Store
 */
Ext.define("AM.store.config.WareHouseS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.WareHouseM',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    sorters: [{
        property: 'id',
        direction: 'DESC'
    }],    
    proxy: {
        type: "ajax",
        api : {
			read : 'warehouse/listWareHouse.do',
			readAll:'warehouse/getAllWareHouse.do'
		},        
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    } 
});