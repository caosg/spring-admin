/**
 * 商品类目远程数据加载 Store
 */
Ext.define("AM.store.config.Categorys",{
    extend: 'Ext.data.Store',
    id:'dictStore',
    model: 'AM.model.config.Category',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    pageSize: 25,
    sorters: [{
        property: 'code',
        direction: 'ASC'
    }],    
    proxy: {
        type: "ajax",
        url: "productcategory/query.do",
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    },
    listeners:{
	    //分页添加查询参数
        beforeload:function (store, options){  
            this.proxy.extraParams = {
            	filter_RLIKE_S_code : Ext.getCmp('basicCategoryForm').getForm()
							.findField('code').getValue()
                
            };
        }
    }
});