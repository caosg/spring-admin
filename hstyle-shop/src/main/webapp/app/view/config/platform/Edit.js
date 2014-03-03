Ext.define('AM.view.config.platform.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.platformeditwin',
    requires: ['Ext.form.Panel'],
    title : 'Edit Platform',
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
        	this.appgrid.store.removeAll();
        }
    },
    initComponent: function() {
    	var me = this;
    	/**
    	 * 创建一个行编辑器插件
    	 * 1:字典子表行编辑器
    	 */
    	me.rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners : {
            	'edit': function(editor, e){
            		//获取列表里的值	
            		var p_id = e.record.get('id');
            		var p_shopcode = e.record.get('code');
            		var p_shopname = e.record.get('name');
            		var p_platformid = Ext.getCmp('i_id').getValue();
            		var p_outercode = e.record.get('outercode');
            		//创建与后台交互的ajax对象
            		Ext.Ajax.request({
            		    url: 'platform/updateShop.do',
            		    params: {
            		        id: p_id, shopcode: p_shopcode, shopname: p_shopname, parentid: p_platformid, outercode: p_outercode
            		    },
            		    success: function(response){
            		    	me.grid.store.load({
            		    		action:'read',
								params : {
									page: 1,
									start:0
								}
            		    	});
            		    }
            		});
            	} 
            }
        });
    	
    	me.appRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners : {
            	'edit': function(editor, e){
            		//获取列表里的值	
            		var rs = Ext.getCmp('ShopEditGrid').getSelectionModel().getSelection();
            		var p_id = e.record.get('id');
            		var p_appkey = e.record.get('appkey');
            		var p_appsecrect = e.record.get('appsecrect');
            		var p_appsession = e.record.get('appsession');
            		var p_tokensession = e.record.get('tokensession');
            		var p_shopid = rs[0].data.id;
            		//创建与后台交互的ajax对象
            		Ext.Ajax.request({
            		    url: 'platform/updateShopApp.do',
            		    params: {
            		        id: p_id, appkey: p_appkey, appsecrect: p_appsecrect, 
            		        appsession: p_appsession, tokensession: p_tokensession,
            		        shopid: p_shopid,status:'1'
            		    },
            		    success: function(response){
            		    	me.appgrid.store.load({
            		    		action:'read',
								params : {
									page: 1,
									start:0,
									shopid: p_shopid
								}
            		    	});
            		    }
            		});
            	} 
            }
        });    	
        
        
        /**
         * 子项列表临时Store
         * 1:初始化已有的Store用于子项列表动态添加
         * 2:Store中包含子项列表的字段模型
         */

		me.form = Ext.widget("form", {
			id : 'PlatformEditForm',
			region:'north',
			frame: true,
			layout : {
				columns : 3,
				type : 'table'
			},
			bodyPadding : 10,
			defaults : {
				anchor : '100%',
				labelAlign : 'right',
			},
			buttonAlign : 'center',
			defaultType : 'textfield',
			store: 'config.PlatformS',
			items : [{
		                xtype: 'textfield',
		                id: 'pltfomcode_id',
		                name: 'pltfomcode',
		                fieldLabel: '代码',
		                allowBlank: false,
		                emptyText: '必填',
		                maxLength: 20,
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
                        xtype: 'textfield',
                        id: 'pltfomname_id',
                        name: 'pltfomname',
                        fieldLabel: '名称',
		                emptyText: '必填',
		                maxLength: 25,
		                allowBlank: false
					}, {
                        xtype: 'textfield',
                        id:'outercode_id',
                        name:'outercode',
                        fieldLabel: '外部编码',
  		                regex: /[^\u4e00-\u9fa5]/,
		                regexText: '不可输入中文',                        
		                maxLength: 20
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
				          id: 'plat-edit'
					   },{
						  text: '清空',
					      handler: function() {
					    	  this.up('form').getForm().reset();
					    	  Ext.getCmp('PlatformEditGrid').store.removeAll();
					      }
					   }
			]
		});
		
		me.grid = Ext.widget("gridpanel", {
            id: 'ShopEditGrid',
            region:'west',
            height: 428,
            title: '店铺列表',
            store : 'config.ShopS',
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
            columnLines: true,
            flex: 1,
            padding: '5 2 0 5',
            autoScroll:true,
			viewConfig : {
				enableTextSelection: true		
			},             
            columns: [
                      {xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden: true},
                      {xtype: 'gridcolumn',dataIndex: 'code',text: '店铺代码',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 20,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }                            
                      },
                      {xtype: 'gridcolumn',dataIndex: 'name',text: '店铺名称',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 25
                          }
                      },
                      {xtype: 'gridcolumn',dataIndex: 'outercode',text: '外部代码',flex: 1,editor: {
                              maxLength: 20
                          }                           
                      },
                      {xtype: 'gridcolumn',dataIndex: 'platform.id',text: 'platform_id',
		                  hidden: true                    	  
                      }
          ],
          dockedItems: [{xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'shop_addLine',
                                    iconCls : "add",
                                    text: '新增',
                                    handler : function() {
                                    	me.rowEditing.cancelEdit();//禁止编辑
                                    	var platformid = Ext.getCmp('i_id').getValue();
                                    	if(platformid=='' || platformid == null || platformid == undefined){
                                    		Ext.Msg.alert('信息提示','请先保存平台信息');
                                    		return ;
                                    	}
                                        var r = Ext.create('AM.model.config.ShopM', {
                                        	code: '',
                                            name: '',
                                            outercode: '',
                                            parentid: platformid
                                        });
                                        me.grid.store.insert(0, r);
                                        me.rowEditing.startEdit(0, 0);
                                    }                                       
                                },{
                                    xtype: 'button',
                                    id: 'plat-deleteshop',
                                    iconCls : "delete",
                                    text: '删除',
                                    handler: function() {
                                        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
	                                            if( e == "yes"){
				                                    	me.rowEditing.cancelEdit();//锁定编辑
				                                    	var rs = me.grid.getSelectionModel().getSelection();
				                						if (rs.length > 0) {
				                							var ids = [];
				                                            for(var i = 0; i < rs.length; i++){
				                                                  ids.push(rs[i].data.id);
				                                             }                                                    
				                                    		Ext.Ajax.request({
				                                    		    url: 'platform/deleteShop.do',
				                                    		    params: {
				                                    		        ids: ids
				                                    		    },
				                                    		    success: function(response){
				                                    		        Ext.Msg.alert('信息提示','操作成功');
				                                    		        me.grid.store.load({
				                                    		        	action:'read',
				                        								params : {
				                        									page : 1,
				                        									start:0,
				                        									parentid:Ext.getCmp('i_id').getValue()
				                        								}
				                                    		        });
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
            plugins: [me.rowEditing],
            listeners: {
                'selectionchange': function(view, records) {
                		Ext.getCmp('plat-deleteshop').setDisabled(!records.length);
                	if(records.length==1){
                		Ext.getCmp('app_addLine').setDisabled(false);
	                	Ext.getCmp('ShopAppEditGrid').store.load({
	                		action:'read',
							params : {
								page : 1,
								start:0,
								shopid: records[0].data.id
							}
						}); 
                	}	
                	else{Ext.getCmp('app_addLine').setDisabled(true);}
                }
            }   
		});
		
		me.appgrid = Ext.widget("gridpanel", {
            id: 'ShopAppEditGrid',
            region:'center',
            height: 428,
            title: '密钥列表',
            store : 'config.ShopAppS',
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
            columnLines: true,
            flex: 1,
            padding: '5 5 0 2',
            autoScroll:true,
			viewConfig : {
				enableTextSelection: true		
			},            
            columns: [{xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden: true},
                      {xtype: 'gridcolumn',dataIndex: 'appkey',text: 'appkey',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 200,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }                            
                      },
                      {xtype: 'gridcolumn',dataIndex: 'appsecrect',text: 'appsecrect',flex: 1,editor: {
	                          maxLength: 100,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }
                      },
                      {xtype: 'gridcolumn',dataIndex: 'appsession',text: 'appsession',flex: 1,editor: {
                              maxLength: 100,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'                          
                          }                           
                      },
                      {xtype: 'gridcolumn',dataIndex: 'tokensession',text: 'tokensession',flex: 1,editor: {
	                          maxLength: 100,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'                          
                         }                           
                      },                      
                      {xtype: 'gridcolumn',dataIndex: 'platformShop.id',text: 'shop_id',hidden: true}
          ],
          dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'app_addLine',
                                    iconCls : "add",
                                    text: '新增',
                                    disabled: true,
                                    handler : function() {
                                    	me.appRowEditing.cancelEdit();//禁止编辑
                                    	var rs = Ext.getCmp('ShopEditGrid').getSelectionModel().getSelection();
                                    	if(rs.length!=1){//只能选取一条数据进行添加映射
                                    		Ext.Msg.alert('信息提示','请选择一条子项数据');
                                    		return ;
                                    	}
                                        var r = Ext.create('AM.model.config.ShopAppM', {
                                        	appkey: '',
                                        	appsecrect: '',
                                        	appsession: '',
                                        	tokensession: '',
                                            shopid: rs[0].data.id
                                        });
                                        me.appgrid.store.insert(0, r);
                                        me.appRowEditing.startEdit(0, 0);                                   	
                                    }                                       
                                },{
                                    xtype: 'button',
                                    id: 'plat-deleteappkey',
                                    iconCls : "delete",
                                    text: '删除',
                                    disabled: true,
                                    handler: function() {
                                        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
	                                            if( e == "yes"){                                    	
				                                    	me.appRowEditing.cancelEdit();//锁定编辑
				                                    	var ids = '';
				                                        var rs = Ext.getCmp('ShopAppEditGrid').getSelectionModel().getSelection();
				                                        for(var i=0;i<rs.length;i++){
				                                        	if(rs[i].data.id=='' || rs[i].data.id==null || rs[i].data.id==undefined){
				                                        		me.appgrid.store.remove(rs[i]);
				                                        	}else{
				                                        		ids+=','+rs[i].data.id;
				                                        	}
				                                    	}
				                                        if(ids!='' && ids!=null && ids!=undefined){
				                                    		ids = ids.substr(1);
				                                    		Ext.Ajax.request({
				                                    		    url: 'platform/deleteShopApp.do',
				                                    		    params: {
				                                    		        ids: ids
				                                    		    },
				                                    		    success: function(response){
				                                    		    	var rs = Ext.getCmp('ShopEditGrid').getSelectionModel().getSelection();
				                                    		        Ext.Msg.alert('信息提示','操作成功');
				                                    		        Ext.getCmp('ShopAppEditGrid').store.load({
				                                    		        	params : {
				                        									page: 1,
				                        									start:0,
				                        									shopid: rs[0].data.id
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
            plugins: [me.appRowEditing],
            listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('plat-deleteappkey').setDisabled(!records.length);
                }
            }   
		});				
		
		me.items = [me.form,me.grid,me.appgrid];
		me.callParent(arguments);
    }

});
