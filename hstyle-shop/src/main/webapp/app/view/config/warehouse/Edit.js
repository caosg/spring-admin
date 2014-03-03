Ext.define('AM.view.config.warehouse.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.areaeditwin',
    requires: ['Ext.form.Panel'],
    title : '',
    layout: {
        align: 'stretch',
        type: 'vbox'
    },
    autoShow: true,
    height: 570,
    width: 850,
    modal:true,
    listeners : {//每次关闭window都清空列表里的数据
    	'beforeclose':function(win , e) {
        	this.grid.store.removeAll();
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
            		var p_whcode = e.record.get('code');
            		var p_whname = e.record.get('name');
            		var p_whareaid = Ext.getCmp('i_id').getValue();
            		var p_outercode = e.record.get('outercode');
            		//创建与后台交互的ajax对象
            		Ext.Ajax.request({
            		    url: 'warehouse/updateWareHouse.do',
            		    params: {
            		        id: p_id, whcode: p_whcode, whname: p_whname, parentid: p_whareaid, outercode: p_outercode
            		    },
            		    success: function(response){
            		    	me.grid.store.load({
            		    		action:'read',
								params : {
									page : 1,
									start:0,
									parentid:p_whareaid
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
			id : 'AreaEditForm',
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
			store: 'config.WareHouseAreaS',
			url: 'warehouse/updateWareHouseArea.do',
			items : [{
		                xtype: 'textfield',
		                id: 'whareacode_id',
		                name: 'whareacode',
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
                        id: 'whareaname_id',
                        name: 'whareaname',
                        fieldLabel: '名称',
		                emptyText: '必填',
		                maxLength: 25,
		                allowBlank: false
					}, {
                        xtype: 'textfield',
                        id:'outercode_id',
                        name:'outercode',
                        fieldLabel: '外部代码',
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
				          id: 'wh-areaedit'
					   },{
						  text: '清空',
					      handler: function() {
					    	  this.up('form').getForm().reset();
					    	  Ext.getCmp('WareHouseEditGrid').store.removeAll();
					      }
					   }
			]
		});
		
		me.grid = Ext.widget("gridpanel", {
            id: 'WareHouseEditGrid',
            height: 428,
            title: '仓库',
            store : 'config.WareHouseS',
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
            columnLines: true,
            flex: 1,
            padding: '5 5 5 5',
            autoScroll:true,
			viewConfig : {
				enableTextSelection: true		
			},            
            columns: [
                      {xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden: true},
                      {xtype: 'gridcolumn',dataIndex: 'code',text: '仓库代码',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 20,
		  		              regex: /[^\u4e00-\u9fa5]/,
				              regexText: '不可输入中文'
                          }                            
                      },
                      {xtype: 'gridcolumn',dataIndex: 'name',text: '仓库名称',flex: 1,editor: {
                        	  emptyText: '必填项',
                              allowBlank: false,
                              maxLength: 25
                          }
                      },
                      {xtype: 'gridcolumn',dataIndex: 'outercode',text: '外部代码',flex: 1,editor: {
                              maxLength: 20
                          }                           
                      },
                      {xtype: 'gridcolumn',dataIndex: 'wharea.id',text: 'area_id',
		                  hidden: true                    	  
                      }
          ],
          dockedItems: [{xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'wh_addLine',
                                    iconCls : "add",
                                    text: '新增',
                                    handler : function() {
                                    	me.rowEditing.cancelEdit();//禁止编辑
                                    	var whareaid = Ext.getCmp('i_id').getValue();
                                    	if(whareaid=='' || whareaid == null || whareaid == undefined){
                                    		Ext.Msg.alert('信息提示','请先保存区域信息');
                                    		return ;
                                    	}
                                        var r = Ext.create('AM.model.config.WareHouseM', {
                                        	code: '',
                                        	name: '',
                                            outercode: '',
                                            parentid: whareaid
                                        });
                                        me.grid.store.insert(0, r);
                                        me.rowEditing.startEdit(0, 0);
                                    }                                       
                                },{
                                    xtype: 'button',
                                    id: 'wh_delete',
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
				                                            //var param={ids:ids};
				                                    		Ext.Ajax.request({
				                                    		    url: 'warehouse/deleteWareHouse.do',
				                                    		    params: {
				                                    		        ids: ids
				                                    		    },
				                                    		    success: function(response){
				                                    		        Ext.Msg.alert('信息提示','操作成功');
				                                    		        me.grid.store.load({
				                                    		        	action: 'read',
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
                	Ext.getCmp('wh_delete').setDisabled(!records.length);
                }
            }   
		});
		
		me.items = [me.form,me.grid];
		me.callParent(arguments);
    }

});
