Ext.define('AM.model.sys.dict.DictItem', {
    extend: 'Ext.data.Model',
    fields: ['id','name', 'value', 'remark','type_id','parentid','dictionaryType'],
    idProperty : "id"
});