Ext.define('AM.store.sys.ext.PropertyMetaS', {
    extend: 'Ext.data.Store',
    alias: 'store.prometastore',

    requires: [
        'AM.model.sys.ext.PropertyMeta'
    ],

    model: 'AM.model.sys.ext.PropertyMeta',
    storeId: 'propertyMetaStore',
    data:[
        {fieldId:1, fieldType: 'textfield',fieldName:'userName',fieldLabel: 'Username', required:true,fieldEmptyText: 'Enter Username' },
        {fieldId:2, fieldType: 'passwordfield',fieldName:'password',fieldLabel: 'Password', required:true,fieldEmptyText: 'Enter Password' },
        {fieldId:3, fieldType: 'numberfield',fieldName:'age',fieldLabel: 'Age', required:true,fieldEmptyText: '请输入年龄' },
        {fieldId:4, fieldType: 'emailfield',fieldName:'email',fieldLabel: 'Email', required:true,fieldEmptyText: 'Enter Email' },
        {fieldId:5, fieldType: 'selectfield',fieldName:'country',fieldLabel: 'Country', fieldEmptyText:'请选择国家',fieldOptions: "usa:美国,zh:中国,en:英国,others:其他" },
        {fieldId:6, fieldType: 'radiofield',fieldName:'sex',fieldLabel: 'Sex', required:true,fieldOptions: "male:男性,female:女性" },
        {fieldId:7, fieldType: 'checkboxfield',fieldName:'color',fieldLabel: 'Color', required:true,fieldOptions: "red:红色,blue:蓝色,green:绿色,yellow:黄色,gray:灰色,white:白色" },
        {fieldId:8, fieldType: 'datefield',fieldName:'dat',fieldLabel: 'Date' },
        {fieldId:9, fieldType: 'textareafield',fieldName:'remark',fieldLabel: 'Remark', required:false,fieldEmptyText: '输入备注' }
    ]
    
});