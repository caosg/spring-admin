/**
 * 平台远程数据加载 Store
 */
Ext.define("AM.store.config.ShopS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.ShopM',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    sorters: [{
        property: 'id',
        direction: 'ASC'
    }],    
    proxy: {
        type: "ajax",
        api : {
			read : 'platform/listShop.do',
			readAll:'platform/getCommonAllShop.do'
		},
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
});