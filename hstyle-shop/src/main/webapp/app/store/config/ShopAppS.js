/**
 * 平台远程数据加载 Store
 */
Ext.define("AM.store.config.ShopAppS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.ShopAppM',
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
			read : 'platform/listShopApp.do',
		},
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
});