Ext.define('AM.view.sys.role.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.roleEditWin',
    requires: ['Ext.form.Panel'],
    title : 'Edit Role',
    layout: 'fit',
    autoShow: true,
    width: 300,
    modal:true,
    initComponent: function() {
	   
        this.items = [
            {
                xtype: 'form',
                
                padding: '5 5 0 5',
                border: false,
                style: 'background-color: #fff;',
                defaultType:'textfield',
                layout : 'anchor',
                defaults : {
								anchor : '100%',
								labelWidth:80
							},
                model:'sys.role.Role',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'code',
                        fieldLabel: '角色代码',
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:3,minLengthText:'长度不能小于3个字符',
                        maxLength:32,maxLengthText:'长度不能大于32个字符'
                    },
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: '角色名称',
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:2,minLengthText:'长度不能小于2个字符',
                        maxLength:32,maxLengthText:'长度不能大于32个字符'
                    },
                    {

						xtype : 'radiogroup',
						fieldLabel : '超级管理员',
						defaults : {
							name : 'admin',
							readOnly:true
						},
						items : [{
								inputValue : '1',
								boxLabel : '是'
								}, {
								inputValue : '0',
								boxLabel : '否'
								}]
						
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
                     
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: '保存',
                action: 'save'
                
            },
            {
                text: '取消',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
    
});
