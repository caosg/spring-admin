Ext.define('AM.view.sys.user.Password', {
    extend: 'Ext.window.Window',
    alias : 'widget.userPassword',
    requires: ['Ext.form.Panel'],
    title : '修改密码',
    layout: 'fit',
    resizable:false,
    autoShow: true,
    width: 300,
    modal:true,
    initComponent: function() {
	    var me=this;
	    
        this.items = [
            {
                xtype: 'form',
                url:'user/changePassword.do',
                padding: '5 5 0 5',
                border: false,
               // frame: true,
                style: 'background-color: #fff;',
                layout : 'anchor',
                defaults : {
								anchor : '100%',
								labelAlign:'right',
                                labelWidth:80
							},
                defaultType:'textfield',
                model:'user.User',
                items: [
                    {
                        name : 'oldPassword',
                        fieldLabel: '旧密码',
                        inputType : "password",
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:6,minLengthText:'长度不能小于6个字符',
                        maxLength:16,maxLengthText:'长度不能大于16个字符'
                    },
                    {
                        name : 'newPassword',
                        fieldLabel: '新密码',
                        vtype:'alphanum',
                        inputType : "password",
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:6,minLengthText:'长度不能小于6个字符',
                        maxLength:16,maxLengthText:'长度不能大于16个字符'
                    },
                    {
                        id:'secondPassword',
                        fieldLabel: '确认新密码',
                        vtype:'alphanum',
                        inputType : "password",
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:6,minLengthText:'长度不能小于6个字符',
                        maxLength:16,maxLengthText:'长度不能大于16个字符'
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: '确认',
                action: 'save',
                handler:me.changePassword
            },
            {
                text: '取消',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    },
    changePassword:function(button){
    	var win=button.up("window"), form=win.down('form').getForm();
    	
		if (form.isValid()) {
			var password=form.findField('newPassword').getValue();
			var secondPassword=form.findField('secondPassword').getValue();
			if(password!=secondPassword){
				MsgBoxWar('两次输入的密码不一致！');return;
			}
			form.submit({
				success : function(form, action) {	
					MsgBoxInfo('修改成功');
					win.close();
				},
				failure: function(form, action) {
                       MsgBoxErr('保存失败');
                 }
				
			});
		}
    }
});
