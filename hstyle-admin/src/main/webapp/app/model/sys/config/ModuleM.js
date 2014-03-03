Ext.define('AM.model.sys.config.ModuleM', {
    extend: 'Ext.data.Model',
    fields: ['name', 'code', 'description','type'],
    idProperty : "code"
    
});