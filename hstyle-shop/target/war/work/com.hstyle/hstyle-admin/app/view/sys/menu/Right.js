Ext.define('AM.view.sys.menu.Right', {
			extend : 'Ext.panel.Panel',
			layout : {
				align : 'stretch',
				type : 'vbox'
			},
			border : 0,
			region : "center",
			initComponent : function() {
				var me = this;
				me.form = Ext.widget("form", {
							title : '基本信息',
							id : 'basicMenuForm',
							layout : {
								columns : 3,
								type : 'table'
							},
							bodyPadding : 10,
							defaults : {
								anchor : '100%',
								labelAlign : 'right',
								readOnly : true,
								size : 60
							},
							// The fields
							defaultType : 'textfield',
							items : [{

										name : 'name',
										fieldLabel : '菜单名称'
										
									},{

										name : 'code',
										fieldLabel : '菜单代码'
										
									}, {
										
										xtype: 'radiogroup',
										fieldLabel : '功能菜单',
										defaults: {
                                               name: 'leaf' ,
                                               readOnly:true
                                        },
										items: [{
                                                   inputValue: '1',
                                                   boxLabel: '是'
                                                 }, {
                                                   inputValue: '0',
                                                   boxLabel: '否'
                                                }]
									},{

										name : "parentName",
										fieldLabel : '上级菜单'
									}, {

										name : 'sort',
										
										fieldLabel : '序号'
									},  {

										name : 'url',
										fieldLabel : 'URL'
										
									}, 
									 {

										name : 'iconCss',
										fieldLabel : '图标CSS'
										
									},
									{
                                        name : 'expression',
										fieldLabel : '权限表达式'
									}
									, {
                                        name : 'remark',
										fieldLabel : '备注'
									},{
                                        xtype: 'hiddenfield',
                                        name : 'id'
                                                           
                                   },{
                                        xtype: 'hiddenfield',
                                        name : 'resource' 
                                   }]
						});
				me.functions=Ext.create('AM.view.sys.menu.FunctionList');
				Ext.applyIf(me, {
							items : [me.form,me.functions]
						});

				me.callParent(arguments);
			}

		});