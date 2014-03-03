/* 属性项 model*/
Ext.define('AM.model.sys.config.moduleMappingM',{
	extend: 'Ext.data.Model',
	 fields: [
	 	{name:'fieldId',mapping:'id'},
	 	{name:'fieldType',mapping:'type'},
	 	{name:'fieldLabel',mapping:'name'},
	 	{name:'fieldEmptyText',mapping:'defaultValue'},
	 	{name:'fieldName',mapping:'code'},
		{name:'fieldOptions',mapping:'options'},
		{name:'value',mapping:'value'},
		{name:'orderNo',mapping:'orderNo'}
	 ]
	
});
