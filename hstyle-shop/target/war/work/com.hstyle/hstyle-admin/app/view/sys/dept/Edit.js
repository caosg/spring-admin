Ext.define('AM.view.sys.dept.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.deptedit',

    requires: ['Ext.form.Panel'],
    
    title : '编辑部门',
    layout: 'fit',
    autoShow: true,
    height: 280,
    width: 320,
    constrain:true,
    closable:false,
    modal:true,
    initComponent: function() {
    	var statustore=Ext.create('AM.store.sys.Status');
        this.items = [
            {
                xtype: 'form',
                padding: '5 5 0 5',
                border: false,
                style: 'background-color: #fff;',
                model:'dept.Dept',
                layout: 'anchor',
                defaults: {
                     anchor: '100%'
                 },
                 // The fields
                 defaultType: 'textfield',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: '名称',
                        afterLabelTextTpl: required,
                        allowBlank:false,
                        maxLength:32,maxLengthText:'长度不能大于32个字符'
                    },
                    
                    {
                        xtype: 'numberfield',
                        name : 'orderNum',
                        fieldLabel: ' 序号',
                        afterLabelTextTpl: required,
                        allowBlank:false,
                        maxValue: 99,
                        minValue: 0
                    },
                    {
                        xtype: 'textfield',
                        name : 'level',
                        fieldLabel: '级别',
                        readOnly:true
                       
                    },
                    {
                        xtype: 'textfield',
                        name : 'parentName',
                        fieldLabel: '上级部门',
                        readOnly:true
                       
                    },
                    {
                        xtype: 'combobox',
                        store:statustore,
                        queryMode: 'local',
                        name : 'status',
                        fieldLabel: '状态',
                        displayField: 'value',
                        valueField: 'key'
                    },
                    {
                        xtype:'textareafield',
						grow : true,
                        name : 'remark',
                        fieldLabel: '备注'
                     },
                    {
                        xtype: 'hiddenfield',
                        name : 'id'
                       
                    },
                    {
                        xtype: 'hiddenfield',
                        name : 'parentId'
                       
                    },
                    {
                        xtype: 'hiddenfield',
                        name : 'code'
                       
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: 'Save',
                formBind: true,
                action: 'save'
            },
            {
            	id : 'deptCancelButton',
                text: 'Cancel',
                scope: this,
                action: 'cancel'
            }
        ];
        
        this.callParent(arguments);
    }
    
    
});