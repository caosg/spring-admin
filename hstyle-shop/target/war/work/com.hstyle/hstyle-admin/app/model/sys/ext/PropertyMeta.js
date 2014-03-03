Ext.define('AM.model.sys.ext.PropertyMeta', {
    extend: 'Ext.data.Model',
    alias: 'model.propertymeta',

    fields: [
            {
                name: 'fieldId',
                type:'int'
            },
            {
                name: 'fieldType',
                type: 'string'
            },
            {
                name: 'fieldLabel',
                type: 'string'
            },
            
            {
                defaultValue: '',
                name: 'fieldEmptyText',
                type: 'string'
            },
            //是否必须
            {
                defaultValue: false,
                name: 'required',
                type: 'boolean'
            },
            {
                defaultValue: false,
                name: 'isChecked',
                type: 'boolean'
            },
            {
                defaultValue: '',
                name: 'fieldName',
                type: 'string'
            },
            {
                defaultValue: '',
                name: 'fieldValue',
                type: 'string'
            },
            {
                defaultValue: '',
                name: 'fieldOptions',
                type: 'string'
            }
        ]
    
});