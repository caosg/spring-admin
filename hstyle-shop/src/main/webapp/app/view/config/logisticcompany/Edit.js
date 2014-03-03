Ext.define('AM.view.config.logisticcompany.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.lgcompanyeditwin',
    requires: ['Ext.form.Panel'],
    title : '',
    layout: 'fit',
    autoShow: true,
    height: 170,
    width: 350,
    modal:true,
    buttonAlign:'center',
    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                frame : true,
                items: [
                    {
                        xtype: 'textfield',
                        id:'companycode_id',
                        name : 'companycode',
                        fieldLabel: '公司代码',
                        allowBlank: false,
		                emptyText: '必填',
		                maxLength: 20,
  		                regex: /[^\u4e00-\u9fa5]/,
		                regexText: '不可输入中文'
                    },
                    {
                        xtype: 'textfield',
                        id:'companyname_id',
                        name : 'companyname',
                        fieldLabel: '公司名称',
                        emptyText: '必填',
		                maxLength: 25,
		                allowBlank: false
                    },
                    {
                    	xtype: 'textfield',
                        id:'outercode_id',
                        name : 'outercode',
                        fieldLabel: '外部代码',
                        regex: /[^\u4e00-\u9fa5]/,
		                regexText: '不可输入中文',                        
		                maxLength: 20
                    },
                    {
                    	xtype: 'textfield',//隐藏域,用于回写数据库主键
						id: 'i_id',
						name: 'id',
						hidden: true
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
                text: '清空',
                handler: function() {
			    	  this.up('window').down('form').getForm().reset();
			      }
            }
        ];

        this.callParent(arguments);
    }

});
