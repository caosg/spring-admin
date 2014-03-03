Ext.define('AM.view.sys.permission.PermissionEdit', {
			extend : 'Ext.window.Window',
			alias : 'widget.permissionedit',

			requires : ['Ext.form.Panel','AM.view.sys.dict.Selector'],
			layout : 'fit',
			autoShow : true,
			width : 360,
			constrain : true,
			closable : false,
			modal : true,
			initComponent : function() {
				var domainStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'RESOURCE_TYPE'}).load();	
				var operatorStore= Ext.create('AM.store.sys.dict.SelectorS',{dictType:'OPERATOR_TYPE'}).load();
				var me=this;
				this.items = [{
							xtype : 'form',
							url : 'permission/save.do',
							padding : '5 5 0 5',
							border : false,
							style : 'background-color: #fff;',
							model : 'sys.permission.Permission',
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
                                      //  minLength:4,minLengthText:'长度不能小于4个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'
									},

									{
										name : 'code',
										fieldLabel : '代码',
										afterLabelTextTpl: required,
										allowBlank:false,blankText:'本字段不能为空',
                                        minLength:4,minLengthText:'长度不能小于4个字符',
                                        maxLength:32,maxLengthText:'长度不能大于32个字符'

									},{
									    xtype:'combobox',
								    name:'resource',								    
								    store:domainStore,
								    fieldLabel:'资源类型'	,
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

										xtype : 'radiogroup',
										fieldLabel : '类型',
                                        afterLabelTextTpl: required,
										defaults : {
											name : 'type'										
										},
										items : [{
													inputValue : 'domain',
													boxLabel : '领域'
												}, {
													inputValue : 'action',
													boxLabel : '操作'
												}],
										listeners: {  
					                     change: function(ra, newValue) {
					                    	 var fieldset=me.down('form fieldset');				                    	 
					                         if (newValue.type == "domain") {  					                        	
					                            Ext.Array.forEach(fieldset.query(), function(field) {
                                                 field.setDisabled(true);                          
                                                 if (!Ext.isIE6) {
                                                       field.el.animate({opacity:0.6 });
                                                  }
                                                });
					                        } else {  
					                            Ext.Array.forEach(fieldset.query(), function(field) {
                                                  field.setDisabled(false);                          
                                                  if (!Ext.isIE6) {
                                                       field.el.animate({opacity: 1});
                                                  }
                                                });  
					                        }  
					                    }  
					                 }  
									},
									{
						                xtype: 'fieldset',
						                title: '操作定义',
						                layout: 'anchor',
						                defaults: {
						                    anchor: '100%'
						                },
						                defaultType : 'textfield',
						                items: [
									        {
									          name : 'action',
									          fieldLabel : '操作',
                                              emptyText:'多个以,分隔;如:query,update,check.....',
                                              listeners: {  
					                              change: me.setExpressionValue
					                              
					                           }
								            },
									       {
									          xtype:'combobox',
								              name:'operator',
								              store:operatorStore,
								              fieldLabel:'运算符'	,
								              triggerAction:'all',  
                                              queryMode: 'local', //本地模式 因为此控件建议手动执行store load方法,故控件初始化完成后store已经获取到了远程数据
                                              editable: false,//不可编辑
                                              autoScroll: true,//滚动条                                   
                                             
                                              emptyText: '请选择',
                                              displayField: 'name',
                                              valueField: 'value',
                                              listeners: {  
					                              change: me.setExpressionValue
					                           }
								          },{
									        name : 'expression',
									        fieldLabel : '权限表达式',
									        readOnly:true
								         }
								    ]},
									{
										name : "parentName",
										fieldLabel : '上级分类',
										readOnly : true
									}, {
                                        xtype : 'numberfield',
										name : 'sort',                             
										fieldLabel : '序号'
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
										name : 'leaf',
                                        value:'0'
									}, 
									{
										xtype : 'hiddenfield',
										name : 'parentId'

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
			},
			setExpressionValue:function(){
				var me=this;
				var fieldset=me.up('fieldset');	
				var domain=fieldset.up('form').down('combobox[name=resource]').getValue();
				var action=fieldset.down('textfield[name=action]').getValue();
				var operator=fieldset.down('combobox[name=operator]').getValue();
				var exp=fieldset.down('textfield[name=expression]');
				domain=domain==null?'':domain;
				action=action==null?'':':'+action;
				operator=operator==null?'':':'+operator;
				exp.setValue(domain+action+operator);
			}

		});