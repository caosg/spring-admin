/**
 * 数据字典store
 */
Ext.define("AM.store.sys.dict.Dicts",{
    extend: 'Ext.data.Store',
    id:'dictStore',
    model: 'AM.model.sys.dict.Dict',
    autoLoad: true,
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
        url: "dict/listDictType.do",
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
});