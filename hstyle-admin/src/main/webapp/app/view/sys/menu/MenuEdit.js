Ext.define('AM.view.sys.menu.MenuEdit', {
			extend : 'Ext.window.Window',
			alias : 'widget.menuedit',

			requires : ['Ext.form.Panel'],
			layout : 'fit',
			autoShow : true,
			width : 300,
			constrain : true,
			closable : false,
			modal : true,
			initComponent : function() {
				this.items = [{
							xtype : 'form',
							url : 'menu/save.do',
							padding : '5 5 0 5',
							border : false,
							style : 'background-color: #fff;',
							model : 'sys.menu.Function',
							layout : 'anchor',
							defaults : {
								anchor : '100%'
							},
							// The fields
							defaultType : 'textfield',
							items : [{
										name : 'name',
										fieldLabel : '名称',
                                       afterLabelTextTpl: required,
										allowBlank:false,blankText:'本字段不能为空',
                                        minLength:4,minLengthText:'长度不能小于4个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'
									},

									{
										name : 'code',
										fieldLabel : '代码',
										afterLabelTextTpl: required,
										allowBlank:false,blankText:'本字段不能为空',
                                        minLength:4,minLengthText:'长度不能小于4个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'

									},
									{

										xtype : 'radiogroup',
										fieldLabel : '功能菜单',
										defaults : {
											name : 'leaf'										
										},
										items : [{
													inputValue : '1',
													boxLabel : '是'
												}, {
													inputValue : '0',
													boxLabel : '否'
												}]
									}, {

										name : "parentName",
										fieldLabel : '上级菜单',
										readOnly : true
									}, {
                                        xtype : 'numberfield',
										name : 'sort',                             
										fieldLabel : '序号'
									},  {
										name : 'url',
										fieldLabel : 'URL'
									},
									{
										name : 'expression',
										fieldLabel : '权限表达式'
									},{
										name : 'iconCls',
										fieldLabel : '图标CSS'
									},
									{
										xtype:'textareafield',
						                grow : true,
										name : 'remark',
										fieldLabel : '备注'
									}, {
										xtype : 'hiddenfield',
										name : 'id'

									}, {
										xtype : 'hiddenfield',
										name : 'parentId'

									}, {
										xtype : 'hiddenfield',
										name : 'resource'

									}]
						}];

				this.buttons = [{
							text : '保存',
							formBind : true,
							action : 'save'
						}, {
							text : '取消',
							scope : this,
							action : 'cancel'
						}];

				this.callParent(arguments);
			}

		});