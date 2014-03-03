Ext.define('AM.view.sys.permission.Right', {
			extend : 'Ext.panel.Panel',
			layout : {
				align : 'stretch',
				type : 'vbox'
			},
			border : 0,
			region : "center",
			initComponent : function() {
				var me = this;
				var domainStore = Ext.create('AM.store.sys.dict.SelectorS', {
							dictType : 'RESOURCE_TYPE'
						}).load();
				var operatorStore = Ext.create('AM.store.sys.dict.SelectorS', {
							dictType : 'OPERATOR_TYPE'
						}).load();
				me.form = Ext.widget("form", {
							title : '基本信息',
							id : 'basicPermissionForm',
							layout : {
								type : 'table',
								columns : 4
							},
							bodyPadding : 10,
							defaults : {
								anchor : '100%',
								labelAlign : 'right',
								readOnly : true
							},
							// The fields
							defaultType : 'textfield',
							items : [{
										name : 'name',
										fieldLabel : '权限名称'

									}, {

										name : 'code',
										fieldLabel : '权限代码'

									}, {
										xtype : 'combobox',
										name : 'resource',
										fieldLabel : '资源类型',
										store : domainStore,
										displayField : 'name',
										valueField : 'value'
									}, {

										xtype : 'radiogroup',
										id : 'typeGroup',
										fieldLabel : '类型',
										defaults : {
											name : 'type'
											
										},
										items : [{
													inputValue : 'domain',
													boxLabel : '领域'
												}, {
													inputValue : 'action',
													boxLabel : '操作'
												}]
									}, {

										name : 'action',
										fieldLabel : '操作'

									}, {
										xtype : 'combobox',
										name : 'operator',
										fieldLabel : '运算符',
										store : operatorStore,
										displayField : 'name',
										valueField : 'value'
									},{
										name : 'expression',
										fieldLabel : '权限表达式'
									},
									{

										name : "parentName",
										fieldLabel : '上级分类'
									}, {

										name : 'sort',
										fieldLabel : '序号'
									},

									 {
										name : 'remark',
										fieldLabel : '备注'
									}, {
										xtype : 'hiddenfield',
										name : 'id'

									}, {
										xtype : 'hiddenfield',
										name : 'leaf'
									}]
						});
				me.functions = Ext.create('AM.view.sys.permission.RuleList');
				Ext.applyIf(me, {
							items : [me.form, me.functions]
						});

				me.callParent(arguments);
			}

		});