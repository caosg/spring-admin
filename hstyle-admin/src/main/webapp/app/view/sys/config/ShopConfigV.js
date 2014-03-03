Ext.define('AM.view.sys.config.ShopConfigV',{
			extend : 'Ext.panel.Panel',
			alias : 'widget.ShopConfigV',
			title : "店铺配置管理",
			id : 'sys.config.ShopConfigView',
			loadMask: true,
			closable: true,
			layout: {
		        type: 'border'
		    },
			viewConfig : {
				loadMask : new Ext.LoadMask(this, {
							msg : '正在努力的为您加载......'
						})
			},
			initComponent : function() {
				var me = this;
				var tabs=[];
				var store=Ext.create('AM.store.sys.config.ModuleS');
				var leftPanel=Ext.create('AM.view.sys.config.ShopTreePanel');//
				for(var i=0;i<store.getCount();i++){
					var record=store.getAt(i);
					if(record.get('type')=="SHOP"){//如果是私有级,需配置店铺平台等信息
						var form=Ext.create('AM.view.sys.config.ShopForm',{moduleId:record.get('code')});
						var panel={
								id   : record.get('code'),
								frame:true,
								layout: {
							        type: 'border'
							    },
							    title:record.get('name')+"配置",
					            autoScroll:true,
					            items:[form]
					            }
						tabs.push(panel);
					}
				}
				var tabPanel= Ext.create('Ext.tab.Panel', {
					region:'center',
					id:'shopConfigTab',
					items:tabs
				});
				
				me.items=[leftPanel,tabPanel];
				this.callParent(arguments);
			}
});