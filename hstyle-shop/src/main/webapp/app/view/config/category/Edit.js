Ext.define('AM.view.config.category.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.categoryedit',
    title : '编辑类目',
    layout: 'fit',
    autoShow: true,
    height: 220,
    width: 280,
    constrain:true,
    closable:false,
    modal:true,
    initComponent: function() {
    	var me = this;
		me.form = Ext.widget("form", {
			id: 'categoryEditForm',
            url: 'productcategory/edit.do',
            padding: '5 5 0 5',
            border: false,
            style: 'background-color: #fff;',
            model:'config.Categorys',
            layout: 'anchor',
            defaults: {
                         anchor: '100%'
            },
            defaultType: 'textfield',
            buttonAlign: 'center',
			items : [{
                         xtype: 'textfield',
                         name : 'name',
                         fieldLabel: '名称',
 		                 emptyText: '必填',
		                 maxLength: 32,
		                 allowBlank: false
                     },{
                         xtype: 'numberfield',
                         name : 'orderNum',
                         fieldLabel: ' 序号',
                         maxValue: 99,
                         minValue: 0
                     },{
                         xtype: 'textfield',
                         name : 'level',
                         fieldLabel: '级别',
                         readOnly:true
                     },{
                         xtype: 'textfield',
                         name : 'parentName',
                         fieldLabel: '上级类目',
                         readOnly:true
                     },{
                         xtype: 'hiddenfield',
                         name : 'id'
                     },{
                         xtype: 'hiddenfield',
                         name : 'parentId'
                     },{
                         xtype: 'hiddenfield',
                         name : 'status'
                     },{
                         xtype: 'hiddenfield',
                         name : 'code'
                    }],
             buttons:[{
                    	id: 'category-update',
                        text: '保存',
                        formBind: true,
                        action: 'save'
                    },{
                    	id: 'categoryCancelButton',
                        text: '取消',
                        scope: this,
                        action: 'cancel'
                    }]                    
		});    	
		me.items = [me.form];
        this.callParent(arguments);
    }
    
});