/**
 * 仓库远程数据加载 Store
 */
Ext.define("AM.store.config.LogisticCompanyS",{
    extend: 'Ext.data.Store',
    model: 'AM.model.config.LogisticCompanyM',
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
			read : 'lgcompany/listCompany.do',
			readAll:'lgcompany/getAllCompany.do'
		},        
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    } 
});