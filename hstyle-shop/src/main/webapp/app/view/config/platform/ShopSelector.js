Ext.define('AM.view.config.platform.ShopSelector',{
	extend : 'Ext.window.Window',
	id : 'shopSelectoreWin',
	height : 446,
	width : 600,
	layout : {
		type : 'hbox'
	},
	modal : true,
	callback:null,
	initComponent : function() {
		var me = this;
		me.platSelectorStore = Ext.create('AM.store.config.PlatformS',{
			sorters: [{
        		property: 'id',
		        direction: 'ASC'
		    }]
		});
		me.shopSelectorStore = Ext.create('AM.store.config.ShopS',{
			sorters: [{
        		property: 'id',
		        direction: 'ASC'
		    }]
		});
		
		me.plateSelectorGrid = Ext.create('Ext.grid.Panel',{
			id : 'plateSelectorGrid',
			title : '平台列表',
			width : 300,
			store : me.platSelectorStore,
			columnLines: true,
			viewConfig : {
				enableTextSelection: true
			},
			columns : [
				{
					header : '代码',
					dataIndex : 'code',
					hidden : true,
					flex : 1
				},
				{
					header : '名称',
					dataIndex : 'name',
					flex : 1
				}
			],
			listeners :{
				'render':function(grid, opts){
					grid.store.load({
			    		action:'readAll',
						params : {
							page : 1,
							start:0
						}                		
			    	});
				},
				'selectionchange': function(view, records) {
					if(records.length == 1){
						//刷新商品列表
						me.shopSelectorStore.load({
				    		action:'read',
							params : {
								parentid : records[0].get('id'),
								page : 1,
								start:0
							}                		
				    	});
					}
				}
			}
		});
		
		me.shopSelectorGrid = Ext.create('Ext.grid.Panel',{
			id : 'shopSelectorGrid',
			title : '店铺列表',
			width : 300,
			store : me.shopSelectorStore,
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
			columnLines: true,
			viewConfig : {
				enableTextSelection: true
			},
			columns : [
				{
					header : '代码',
					dataIndex : 'code',
					hidden : true,
					flex : 1
				},
				{
					header : '名称',
					dataIndex : 'name',
					flex : 1
				}
			],
			listeners :{
				'render':function(grid, opts){
					var rs = me.plateSelectorGrid.getSelectionModel().getSelection();
					if(rs.length != 0){
						var plate = me.plateSelectorGrid.getSelectionModel().getSelection()[0];
						grid.store.load({
				    		action:'read',
							params : {
								parentid : plate.get('id'),
								page : 1,
								start:0
							}                		
				    	});
					}
				}
			}
		});
		
		me.dockedItems = [
			{
				xtype: 'toolbar',
                items: [
                    {  
                        xtype: 'button',
                        iconCls:'add',
                        text : '确定',
                        tooltip : '添加店铺',
                        handler:function(){
                        	var  names = [];
                        	var plateRs = me.plateSelectorGrid.getSelectionModel().getSelection()[0];
                            var shopRs= me.shopSelectorGrid.getSelectionModel().getSelection();
                            if(shopRs.length == 0){
                            	MsgBoxWar('还没有选择店铺');return;  
                            }
                        	me.callback(plateRs.get('code'),shopRs);
                        	me.close();
                        },
                        scope:me
                    },'-',
                    {
                        xtype: 'button',
                        iconCls:'close',
                        text : '关闭',
                        tooltip : '关闭窗口',
                        handler:function(){
                        	me.close();
                        }
                    }
                ]
			}
		];
		
		me.items = [
			me.plateSelectorGrid,me.shopSelectorGrid
		];
		
		me.callParent(arguments);
	}
});