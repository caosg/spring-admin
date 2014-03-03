Ext.define('AM.model.sys.dict.Dict', {
    extend: 'Ext.data.Model',
    fields: ['id', 'code', 'name','remark','dictionaries','isdel'],
    idProperty : "id"
});