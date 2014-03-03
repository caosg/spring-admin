/**
 * 功能列表store
 */
Ext.define("AM.store.sys.menu.Functions",{
    extend: 'Ext.data.Store',
    id:'functionListStore',
    model: 'AM.model.sys.menu.Function',
    autoLoad: false,
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    proxy: {
        type: "ajax",
        url: "menu/functions.do",
        reader: {
            type: 'json'        
        },
        listeners : {
		       exception : AM.proxyException
	       }

    },
    listeners:{
    	    //分页添加查询参数
            beforeload:function (store, options){  
                this.proxy.extraParams = {
                	
                };
            }
        }

});