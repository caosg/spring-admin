Ext.define('AM.model.portlet.OperationRank', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'operation_name',type:'string'},
        {name: 'parent_name',type:'string'},
        {name: 'hits',type: 'int'},
        {name: 'users',  type: 'int'}
    ]
});