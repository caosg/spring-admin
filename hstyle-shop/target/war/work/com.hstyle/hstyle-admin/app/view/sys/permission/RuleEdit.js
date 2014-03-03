Ext.define('AM.view.sys.permission.RuleEdit', {
			extend : 'Ext.window.Window',
			alias : 'widget.ruleedit',

			requires : ['Ext.form.Panel'],
			layout : 'fit',
			autoShow : true,
			width : 300,
			constrain : true,
			closable : false,
			modal : true,
			initComponent : function() {
				var me=this;
				var targetStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'TARGET_TYPE'}).load();	
				this.items = [{
							xtype : 'form',
							url : 'permission/saveTarget.do',
							padding : '5 5 0 5',
							border : false,
							style : 'background-color: #fff;',
							model : 'sys.permission.Target',
							layout : 'anchor',
							defaults : {
								anchor : '100%',
								labelAlign:'right',
								labelWidth:'60'
							},
							// The fields
							defaultType : 'textfield',
							items : [{
										name : 'name',
										fieldLabel : '名称',
										afterLabelTextTpl: required,
                                        allowBlank:false,blankText:'本字段不能为空',
                                        minLength:3,minLengthText:'长度不能小于3个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'
									},

									{
										name : 'value',
										fieldLabel : '值',
										afterLabelTextTpl: required,
										allowBlank:false,blankText:'本字段不能为空',
                                        minLength:1,minLengthText:'长度不能小于1个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'

									},
									{
									    xtype:'combobox',
								    name:'type',								    
								    store:targetStore,
								    fieldLabel:'目标类型'	,
                                    afterLabelTextTpl: required,
								    triggerAction:'all',  
                                    queryMode: 'local', //本地模式 因为此控件建议手动执行store load方法,故控件初始化完成后store已经获取到了远程数据
                                    editable: false,//不可编辑
                                    autoScroll: true,//滚动条
                                    allowBlank: false,//不允许为空
                                    blankText: '不能为空',
                                    emptyText: '请选择',
                                    displayField: 'name',
                                    valueField: 'value'
								   },
									{
										xtype:'textareafield',
										grow : true,
										name : 'remark',
										fieldLabel : '说明'
									}, {
										xtype : 'hiddenfield',
										name : 'id'

									}, {
										xtype : 'hiddenfield',
										name : 'dataId'

									}]
						}];

				this.buttons = [{
							text : '保存',
							formBind : true,
							action : 'save'
						}, {
							text : '取消',
							scope : this,
							action : 'cancel',
							handler:function(){
								me.close();
							}
						}];

				this.callParent(arguments);
			}

		});