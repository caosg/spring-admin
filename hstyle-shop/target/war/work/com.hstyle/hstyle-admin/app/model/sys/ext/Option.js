Ext.define('AM.model.sys.ext.Option', {
    extend: 'Ext.data.Model',
    alias: 'model.option',

    fields: [
            {
                name: 'key',
                type: 'string'
            },
            {
                name: 'value',
                type: 'string'
            }
        ]
    
});