Ext.define('AM.model.sys.config.ModulePropertiesM', {
    extend: 'Ext.data.Model',
    fields: [{name:'id',type:'long'},{name:'orderNo',type:'long'},
             'code', 'name', 'module','type','options',
             'defaultValue','remark','value'],
    idProperty : "id"
    
});