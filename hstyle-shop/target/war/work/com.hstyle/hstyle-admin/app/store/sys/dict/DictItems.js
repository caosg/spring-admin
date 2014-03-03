/**
 * 数据字典子项store
 */
Ext.define("AM.store.sys.dict.DictItems",{
    extend: 'Ext.data.Store',
    id:'dictItemStore',
    model: 'AM.model.sys.dict.DictItem',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    sorters: [{
        property: 'id',
        direction: 'ASC'
    }],    
    proxy: {
        type: "ajax",
        url: "dict/listDict.do",
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    },
	listeners : {
			// 分页添加查询参数
			beforeload : function(store, options) {
				this.proxy.extraParams = {
						parentid: Ext.getCmp('i_id').getValue()
				};
			}
	}	    
});