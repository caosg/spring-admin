/**
 * 部门树选择框封装
 */
Ext.define('AM.view.sys.dept.Selector', {
	extend : 'Ext.window.Window',
    modal:true,
	height : 400,
	width : 300,
	layout : {
		type : 'fit'
	},
	title : '部门选择',
	border:false,
    callback:null,
	initComponent : function() {
		var me = this;
		var store = Ext.create('AM.store.sys.dept.Selectors');
		var tree = Ext.create('Ext.tree.Panel', {
			store : store,
			rootVisible : false,

			frame : true,
			dockedItems : [{
						xtype : 'toolbar',
						items : [{
							text : '确定',
							iconCls : 'accept',
							handler : function() {
								var  names = [];
                                var selectNode= tree.getSelectionModel().getSelection()[0];
                                
                                if(selectNode==null){
                                	MsgBoxWar('还没有选中一个部门');return;  
                                }
                                
								if(me.callback!=null)
								   me.callback(selectNode.data);
								 me.close();
                                
								
							}
						},{
							text : '关闭',
							iconCls : 'close',
							handler : function() {me.close();}
						}]
					}]
		});
		Ext.applyIf(me, {
					items : [tree]
				});

		me.callParent(arguments);
	}

});