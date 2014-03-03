Ext.define('AM.model.sys.dict.DictMapping', {
    extend: 'Ext.data.Model',
    fields: ['id','name', 'value','dictId','dictionary','platform'],
    idProperty : "id"
});