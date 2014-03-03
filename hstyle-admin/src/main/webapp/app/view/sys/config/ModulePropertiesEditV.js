Ext.define('AM.view.sys.config.ModulePropertiesEditV',{
	extend: 'Ext.window.Window',
	requires: ['Ext.form.Panel','Ext.grid.Panel'],
	alias : 'widget.modulePropertiesEditV',
	id : 'modulePropertiesEditV',
	buttonAlign:'center',
	autoShow : true,
	autoHeight:true,
	modal : true,
	resizable: true,
	x:400,
	y:60,
	layout: {
        align: 'stretch',
        type: 'vbox'
    },
    width:400,
    url:'',
    autoScroll:true,
	initComponent : function(){
		var me = this;
		me.typeStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'SCM_PROPERTY_TYPE'}).load();//属性项，类型
		//策略包含类目
		me.optionStore = Ext.create('AM.store.sys.config.ModuleOptionsS',{
			model : 'AM.model.sys.config.ModuleOptionsM'
		});
//		
		me.mappingRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        	id:'metaRowEditing',
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners : {
            	'canceledit': function( editor, e, eOpts ){
            		var rs = Ext.getCmp('moduleOptionGrid').getSelectionModel().getSelection();
            		me.optionGrid.store.remove(rs[0]);
            	}
            }
        });
		me.optionGrid = Ext.widget('gridpanel',{
			id : 'moduleOptionGrid',
			name : 'moduleOptionGrid',
			title : '属性选项',
			height:200,
			 flex: 1,
			//autoHeight:true,
			 autoScroll:true,
			clicksToEdit: 1,
			alias : 'widget.moduleOptionGrid',
			selType : "checkboxmodel",
			selModel : {
				checkOnly : false,
				mode : "MULTI"
			},
			columnLines: true,
			store : me.optionStore,
			viewConfig : {
                enableTextSelection: true
			},
			listeners:{
				'render':function(){
	        		if(Ext.getCmp('options_').value!=''){
	        			var valuess=Ext.getCmp('options_').value;
	        			var optArr=valuess.split(",");
	        			for(var i=0;i<optArr.length;i++){
	        				var itemArr=optArr[i].split(":");
	        				var r;
	        				for(var n=0;n<itemArr.length;n++){
	        					r = Ext.create('AM.model.sys.config.ModuleOptionsM',{
	        						code : itemArr[0],
	        						name : itemArr[1]
	        					});
	        				}
	        				me.optionStore.insert(0, r);
	        				me.optionGrid.doLayout();
	        			}
	        		}
	        	}	
	        },
			plugins: [me.mappingRowEditing],
			columns : [
			           {xtype: 'gridcolumn',dataIndex: 'code',text: '键值',flex: 1,editor: {
			        	   emptyText: '必填项',
			        	   allowBlank: false,
			        	   maxLength: 32,
			        	   regex:/^[A-Za-z0-9]+$/,
                           regexText: '只能输入数字和字母'
			           }                            
			           },
			           {xtype: 'gridcolumn',dataIndex: 'name',text: '名称',flex: 1,editor: {
			        	   emptyText: '必填项',
			        	   allowBlank: false,
			        	   maxLength: 32
			           }
			           }
			],
			dockedItems : [
				{
					xtype: 'toolbar',
                    items: [
						{
							xtype: 'button',
							iconCls : 'add',
							tooltip : '新增',
							 handler : function() {
                                 var r = Ext.create('AM.model.sys.config.ModuleOptionsM', {
                                 	code: '',
                                    name: ''
                                 });
                                 me.optionGrid.store.insert(0, r);
                                 me.mappingRowEditing.startEdit(0, 0);                                    	
                             } 
						},
						{
							xtype: 'button',
							iconCls : 'delete',
							tooltip : '移除',
							handler : function() {
								var rs = Ext.getCmp('moduleOptionGrid').getSelectionModel().getSelection(); 
								if(rs.length==0){
									Ext.Msg.alert('信息提示','请至少选择一条信息');
                            		return ;
								}
								for(var i=0;i<rs.length;i++){
									me.optionGrid.store.remove(rs[i]);
								}
                            } ,
							scope:me
						}
					]
				}
			]
			
		});
		me.items = [
			{
				xtype: 'form',
				id : 'moduleOptionform',
				border : false,
				style: 'background-color: #fff;',
				bodyPadding : 10,
				padding: '5 5 5 5',
			    layout: {
                    type: 'auto'
                },
            	defaultType : 'combo',
				model : 'scm.property.PropertyMetaM',
				items : [
					{
	                    xtype: 'fieldset',
	                    layout: {
	                        align: 'stretch',
	                        type: 'vbox'
	                    },
	                    defaults: {
					    	anchor: '100%'
					    },
	                    title: '基本信息',
	                    items: [
		                    {
								xtype: 'hiddenfield',
								name : 'id',
								id : 'id_'
							},
							{
								xtype: 'hiddenfield',
								name : 'moduleType',
								id : 'moduleType_'
							},
							{
								xtype: 'hiddenfield',
								name : 'value',
								id : 'value_',
								value:''
							},
							{
								xtype: 'hiddenfield',
								name : 'options',
								id : 'options_',
								value:''
							},
							{
								xtype: 'hiddenfield',
								id : 'module_',
								name : 'module',
								value:''
							},
	                        {
	                            xtype: 'textfield',
	                            name : 'code',
	                            id : 'code_',
	                            flex: 1,
	                            anchor: '100%',
	                            fieldLabel: '属性代码',
	                            afterLabelTextTpl : required,
	                            regex:/^[A-Za-z0-9]+$/,
	                            regexText: '只能输入数字和字母',
								allowBlank:false,blankText:'本字段不能为空',
								maxLength:20,maxLengthText:'长度不能大于20个字符'
	                        },
	                        {
	                            xtype: 'textfield',
	                            name : 'name',
	                            id : 'name_',
	                            flex: 1,
	                            anchor: '100%',
	                            fieldLabel: '属性名称',
	                            afterLabelTextTpl : required,
								allowBlank:false,blankText:'本字段不能为空',
								maxLength:30,maxLengthText:'长度不能大于30个字符'
	                        },
	    					{xtype:"combo",name : 'type',fieldLabel : '类型',id:'type_',
	    						width:200,
	    						anchor:'50%',
	    						allowBlank:false,blankText:'本字段不能为空',
	    						store : me.typeStore,
	    						afterLabelTextTpl : required,
	    						displayField: 'name',
	    						valueField: 'value',
	    						queryMode: 'local',
	    						editable:false,
	    						listeners:{'change':function(){
	    							var me=this;
	    							var form = me.up('form');
	    							var window=form.up('window');
							    	var s= Ext.getCmp('type_').getValue(); //选择的类型
									if(s=='selectfield'||s=='checkboxfield'||s=='radiofield'){//当选中的类型为  下拉框，复选框 或者单选按钮时，动态添加
											window.optionGrid.show();
											window.optionGrid.store.removeAll();
											window.add(window.optionGrid);
											window.doLayout();
										
									}else{
										window.optionGrid.hide();
										window.optionGrid.store.removeAll();
										window.doLayout();
									}
							    }}
	    					},
	    					{
	                            xtype: 'textfield',
	                            name : 'defaultValue',
	                            id : 'defaultValue_',
	                            flex: 1,
	                            anchor: '100%',
	                            fieldLabel: '默认值',
								maxLength:10,maxLengthText:'长度不能大于10个字符'
	                        },
	                        {
	                            xtype: 'numberfield',
	                            anchor: '100%',
						        name: 'orderNo',
						        id : 'orderNo_',
						        fieldLabel: '序号',
						        allowBlank:false,
						        value: 0,
						        maxValue: 9999,
						        minValue: 0
	                        },
	    					{xtype:"textarea",fieldLabel:"描述",name:"remark",id : 'remark_',
	    						width:403,height:70,maxLength:99,maxLengthText:"最大字数为99个字"}			
	                    ]
	                }
				]
			}
			
		];
		me.buttons = [
			{
	            text: '保存',
	            action: 'save',
	            id : 'moduleProp-save'
	        },
	        {
	            text: '取消',
	            scope: this,
	            handler: this.close
	        }
		];
		me.callParent(arguments);
	}
    
});