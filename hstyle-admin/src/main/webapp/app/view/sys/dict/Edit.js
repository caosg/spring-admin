Ext.define('AM.view.sys.dict.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.dicteditwin',
    requires: ['Ext.form.Panel'],
    title : 'Edit Dict',
    layout: {
        align: 'stretch',
        type: 'border'
    },
    autoShow: true,
    height: 570,
    width: 850,
    modal:true,
    listeners : {//每次关闭window都清空列表里的数据
    	'beforeclose':function(win , e) {
        	this.grid.store.removeAll();
        	this.mappinggrid.store.removeAll();
        }
    },
    initComponent: function() {
    	var me = this;
    	/**
    	 * 创建一个行编辑器插件
    	 * 1:字典子表行编辑器
    	 */
    	me.dictRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        	id:'dictRowEditing',
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners : {
            	'edit': function(editor, e){
            		//获取列表里的值	
            		var p_id = e.record.get('id');
            		var p_value = e.record.get('value');
            		var p_name = e.record.get('name');
            		var p_typeid = Ext.getCmp('i_id').getValue();
            		var p_remark = e.record.get('remark');
            		//创建与后台交互的ajax对象
            		Ext.Ajax.request({
            		    url: 'dict/updateDict.do',
            		    params: {
            		        id: p_id, value: p_value, name: p_name, parentid: p_typeid, remark: p_remark
            		    },
            		    success: function(response){
            		    	me.grid.store.load();
            		    }
            		});
            	},'canceledit': function( editor, e, eOpts ){
            		var rs = Ext.getCmp('DictEditGrid').getSelectionModel().getSelection();
            		var idTmp = e.record.get('id');
            		if(idTmp!='' && idTmp!=null && idTmp !=undefined)return;//如果数据已经存在于数据库
            		var valueTmp = e.record.get('value');
            		var nameTmp = e.record.get('name');
            		var remarkTmp = e.record.get('remark');
            		if( (valueTmp==''||valueTmp==null||valueTmp==undefined) 
            				&& (nameTmp==''||nameTmp==null||nameTmp==undefined) 
            				&& (remarkTmp==''||remarkTmp==null||remarkTmp==undefined)  )
            		me.grid.store.remove(rs[0]);
            	}
            }
        });
    	
    	//me.selector = Ext.create('AM.view.dict.Selector',{dictType:'RESOURCE_TYPE'});
        
    	/**
    	 * 创建一个行编辑器插件
    	 * 1:字典映射行编辑器
    	 */
        
    	me.mappingRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        	id:'mappingRowEditing',
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners : {
            	'edit': function(editor, e){
            		//获取列表里的值	
            		var rs = Ext.getCmp('DictEditGrid').getSelectionModel().getSelection();
            		var p_id = e.record.get('id');
            		var p_value = e.record.get('value');
            		var p_name = e.record.get('name');
            		var p_platform = e.record.get('platform');
            		var p_dictid = rs[0].data.id;
            		//创建与后台交互的ajax对象
            		Ext.Ajax.request({
            		    url: 'dict/updateMapping.do',
            		    params: {
            		        id: p_id, value: p_value, name: p_name, dictId: p_dictid, platform: p_platform
            		    },
            		    success: function(response){
            		    	me.mappinggrid.store.load({
								params : {
									page: 1,
									start:0,
									parentid: p_dictid
								}
            		    	});
            		    }
            		});
            	},'canceledit': function( editor, e, eOpts ){
            		var rs = Ext.getCmp('DictMappingEditGrid').getSelectionModel().getSelection();
            		var idTmp = e.record.get('id');
            		if(idTmp!='' && idTmp!=null && idTmp !=undefined)return;//如果数据已经存在于数据库
            		var valueTmp = e.record.get('value');
            		var nameTmp = e.record.get('name');
            		var platformTmp = e.record.get('platform');
            		if( (valueTmp==''||valueTmp==null||valueTmp==undefined) 
            				&& (nameTmp==''||nameTmp==null||nameTmp==undefined) 
            				&& (platformTmp==''||platformTmp==null||platformTmp==undefined)  )
            		me.mappinggrid.store.remove(rs[0]);
            	}
            }
        });
        
        /**
         * 子项列表临时Store
         * 1:初始化已有的Store用于子项列表动态添加
         * 2:Store中包含子项列表的字段模型
         */

		me.form = Ext.widget("form", {
			id : 'DictEditForm',
			region:'north',
			frame: true,
			layout : {
				columns : 2,
				type : 'table'
			},
			bodyPadding : 10,
			defaults : {
				anchor : '100%',
				labelAlign : 'right'
			},
			buttonAlign : 'center',
			defaultType : 'textfield',
			store: 'sys.dict.Dict',
			url: 'dict/updateDictType.do',
			items : [{
		                xtype: 'textfield',
		                id: 'code_id',
		                name: 'code',
		                fieldLabel: '代码',
		                allowBlank: false,
		                emptyText: '必填',
		                maxLength: 32,
  		                regex: /[^\u4e00-\u9fa5]/,
		                regexText: '不可输入中文',
		                listeners: {
		                	'change': function(textField,  newValue,  oldValue,  eOpts ){
		                		if(newValue.length==0){
		                			Ext.getCmp('i_id').setValue('');
		                		}
		                	}
		                }
					}, {
                        xtype: 'textareafield',
                        id: 'remark_id',
                        name: 'remark',
                        width: 290,
                        height: 63,
                        fieldLabel: '备注说明',
                        rowspan: 2,
                        maxLength: 127
					}, {
                        xtype: 'textfield',
                        id: 'name_id',
                        name:'name',
                        fieldLabel: '名称',
                        allowBlank: false,
		                emptyText: '必填',
		                maxLength: 32
					}, {
						xtype: 'textfield',//隐藏域,用于回写数据库主键
						id: 'i_id',
						name: 'id',
						hidden: true
					}
			],
			buttons : [{
					   	  text: '保存',
				          formBind: true, //only enabled once the form is valid
				          disabled: true,
				          id: 'dict-update'
					   },{
						  text: '清空',
					      handler: function() {
					    	  this.up('form').getForm().reset();
					    	  Ext.getCmp('DictEditGrid').store.removeAll();
					      }
					   }
			]
		});
		
		
		me.grid = Ext.widget("gridpanel", {
            id: 'DictEditGrid',
            region:'west',
            height: 428,
            title: '字典子项列表',
            store : 'sys.dict.DictItems',
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
            columnLines: true,
            flex: 1,
            padding: '5 2 0 5',
            autoScroll:true,
            columns: [{xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden: true},
                      {xtype: 'gridcolumn',dataIndex: 'value',text: '键值',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 32,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }                            
                      },
                      {xtype: 'gridcolumn',dataIndex: 'name',text: '名称',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 32
                          }
                      },
                      {xtype: 'gridcolumn',dataIndex: 'remark',text: '说明',flex: 1,editor: {
                              maxLength: 127
                          }                           
                      },
                      {xtype: 'gridcolumn',dataIndex: 'dictionaryType.id',text: 'type_id',
		                  hidden: true                    	  
                      }
          ],
          dockedItems: [{xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'dict_addLine',
                                    iconCls : "add",
                                    text: '新增',
                                    handler : function() {
                                    	me.dictRowEditing.cancelEdit();//禁止编辑
                                    	var type_id = Ext.getCmp('i_id').getValue();
                                    	if(type_id=='' || type_id == null || type_id == undefined){
                                    		Ext.Msg.alert('信息提示','请先保存数据字典主信息');
                                    		return ;
                                    	}
                                        var r = Ext.create('AM.model.sys.dict.DictItem', {
                                        	name: '',
                                            value: '',
                                            remark: '',
                                            type_id: type_id
                                        });
                                        me.grid.store.insert(0, r);
                                        me.dictRowEditing.startEdit(0, 0);
                                    }                                       
                                },{
                                    xtype: 'button',
                                    id: 'dict_removeLine',
                                    iconCls : "delete",
                                    text: '删除',
                                    handler: function() {
                                        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
	                                            if( e == "yes"){
				                                    	me.dictRowEditing.cancelEdit();//锁定编辑
				                                    	var ids = '';
				                                        var rs = Ext.getCmp('DictEditGrid').getSelectionModel().getSelection();
				                                        for(var i=0;i<rs.length;i++){
				                                        	if(rs[i].data.id=='' || rs[i].data.id==null || rs[i].data.id==undefined){
				                                        		me.grid.store.remove(rs[i]);
				                                        	}else{
				                                        		ids+=','+rs[i].data.id;
				                                        	}
				                                    	}
				                                        if(ids!='' && ids!=null && ids!=undefined){
				                                    		ids = ids.substr(1);
				                                    		Ext.Ajax.request({
				                                    		    url: 'dict/delDict.do',
				                                    		    params: {
				                                    		        ids: ids
				                                    		    },
				                                    		    success: function(response){
				                                    		        Ext.Msg.alert('信息提示','操作成功');
				                                    		        Ext.getCmp('DictEditGrid').store.load();
				                                    		    }
				                                    		});
				                                        }
	                                            }
                                        });
                                    },
                                    disabled: true
                                }
                            ]
                        }
            ],
            plugins: [me.dictRowEditing],
            listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('dict_removeLine').setDisabled(!records.length);
                	if(records.length==1){
                		Ext.getCmp('mapping_addLine').setDisabled(false);
                    	Ext.getCmp('DictMappingEditGrid').store.load({
    						params : {
    							page : 1,
    							start:0,
    							parentid: records[0].data.id
    						}
    					});                		
                	}else{Ext.getCmp('mapping_addLine').setDisabled(true);}
                }
            }   
		});
        
		
		me.mappinggrid = Ext.widget("gridpanel", {
            id: 'DictMappingEditGrid',
            region:'center',
            height: 428,
            title: '字典映射列表',
            store : 'sys.dict.DictMappings',
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
            columnLines: true,
            flex: 1,
            padding: '5 5 0 2',
            autoScroll:true,
            columns: [{xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden: true},
                      {xtype: 'gridcolumn',dataIndex: 'value',text: '键值',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 32,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }                            
                      },
                      {xtype: 'gridcolumn',dataIndex: 'name',text: '名称',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 32
                          }
                      },
                      {xtype: 'gridcolumn',dataIndex: 'platform',text: '平台',flex: 1,editor: {
	                    	  emptyText: '必填项',
	                          allowBlank: false,                    	  
                              maxLength: 20,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'                          
                          }                           
                      },
                      {xtype: 'gridcolumn',dataIndex: 'dictionary.id',text: 'dict_id',hidden: true}
          ],
          dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'mapping_addLine',
                                    iconCls : "add",
                                    text: '新增',
                                    disabled: true,
                                    handler : function() {
                                    	me.mappingRowEditing.cancelEdit();//禁止编辑
                                    	var rs = Ext.getCmp('DictEditGrid').getSelectionModel().getSelection();
                                    	if(rs.length!=1){//只能选取一条数据进行添加映射
                                    		Ext.Msg.alert('信息提示','请选择一条子项数据');
                                    		return ;
                                    	}
                                        var r = Ext.create('AM.model.sys.dict.DictMapping', {
                                        	name: '',
                                            value: '',
                                            platform: '',
                                            type_id: rs[0].data.id
                                        });
                                        me.mappinggrid.store.insert(0, r);
                                        me.mappingRowEditing.startEdit(0, 0);                                    	
                                    }                                       
                                },{
                                    xtype: 'button',
                                    id: 'mapping_removeLine',
                                    iconCls : "delete",
                                    text: '删除',
                                    disabled: true,
                                    handler: function() {
                                        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
	                                            if( e == "yes"){                                    	
				                                    	me.mappingRowEditing.cancelEdit();//锁定编辑
				                                    	var ids = '';
				                                        var rs = Ext.getCmp('DictMappingEditGrid').getSelectionModel().getSelection();
				                                        for(var i=0;i<rs.length;i++){
				                                        	if(rs[i].data.id=='' || rs[i].data.id==null || rs[i].data.id==undefined){
				                                        		me.grid.store.remove(rs[i]);
				                                        	}else{
				                                        		ids+=','+rs[i].data.id;
				                                        	}
				                                    	}
				                                        if(ids!='' && ids!=null && ids!=undefined){
				                                    		ids = ids.substr(1);
				                                    		Ext.Ajax.request({
				                                    		    url: 'dict/delMapping.do',
				                                    		    params: {
				                                    		        ids: ids
				                                    		    },
				                                    		    success: function(response){
				                                    		    	var rs = Ext.getCmp('DictEditGrid').getSelectionModel().getSelection();
				                                    		        Ext.Msg.alert('信息提示','操作成功');
				                                    		        Ext.getCmp('DictMappingEditGrid').store.load({
				                                    		        	params : {
				                        									page: 1,
				                        									start:0,
				                        									parentid: rs[0].data.id
				                        								}
				                                    		        });
				                                    		    }
				                                    		});
				                                        }
	                                            }
                                        });  
                                    }
                                }
                            ]
                        }
                    ],
            plugins: [me.mappingRowEditing],
            listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('mapping_removeLine').setDisabled(!records.length);
                }
            }   
		});		
		
		me.items = [me.form,me.grid,me.mappinggrid];
		me.callParent(arguments);
    }

});
