Ext.define('AM.model.sys.role.Role', {
    extend: 'Ext.data.Model',
    fields: ['id', 'code', 'name','admin','remark'],
    idProperty : "id"
});