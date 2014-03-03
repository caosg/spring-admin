Ext.define('AM.view.sys.dict.Selector', {
     extend: 'Ext.form.field.ComboBox',
     alias: ['widget.dictselector'],    
     forceSelection:true,
     triggerAction:'all',  
     queryMode: 'local', //本地模式 因为此控件建议手动执行store load方法,故控件初始化完成后store已经获取到了远程数据
     editable: false,//不可编辑
     autoScroll: true,//滚动条
     allowBlank: false,//不允许为空
     blankText: '不能为空',
     emptyText: '请选择',
     displayField: 'name',
     valueField: 'value',
     initComponent : function() {
     	
    	this.callParent(arguments);
     }
});