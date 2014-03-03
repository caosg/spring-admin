Ext.define('AM.view.sys.dept.Right', {
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
							id : 'basicDeptForm',
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
										fieldLabel : '部门名称',
										size : 60
									}, {

										name : 'level',
										fieldLabel : '级别',
										size : 60
									}, {

										name : 'code',
										fieldLabel : '部门代码',
										size : 60
									}, {

										name : "parentName",
										fieldLabel : '上级部门'
									}, {

										name : 'orderNum',
										fieldLabel : '序号'
									}, {
                                        name:'remark',
										fieldLabel : '备注'
									},{
                                        xtype: 'hiddenfield',
                                        name : 'id'
                                        
                       
                                   }]
						});
				me.users=Ext.create('AM.view.sys.dept.Users');
				Ext.applyIf(me, {
							items : [me.form,me.users]
						});

				me.callParent(arguments);
			}

		});