/**
 * 数据字典子项store
 */
Ext.define("AM.store.sys.dict.DictMappings",{
    extend: 'Ext.data.Store',
    id:'dictMappingStore',
    model: 'AM.model.sys.dict.DictMapping',
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    sorters: [{
        property: 'id',
        direction: 'ASC'
    }],    
    proxy: {
        type: "ajax",
        url: "dict/listMapping.do",
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }
    }
});