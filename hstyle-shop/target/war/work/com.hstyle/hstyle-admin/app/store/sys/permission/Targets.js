/**
 * 数据权限Target store
 */
Ext.define("AM.store.sys.permission.Targets",{
    extend: 'Ext.data.Store',
    id:'functionListStore',
    model: 'AM.model.sys.permission.Target',
    autoLoad: false,
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    proxy: {
        type: "ajax",
        url: "permission/rules.do",
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