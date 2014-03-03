Ext.define('AM.view.sys.config.ModuleConfigV',{
			extend : 'Ext.tab.Panel',
			alias : 'widget.moduleConfigV',
			title : "系统配置管理",
			id : 'sys.config.ModuleConfigView',
			loadMask: true,
			closable: true,
			viewConfig : {
				loadMask : new Ext.LoadMask(this, {
							msg : '正在努力的为您加载......'
						})
			},
			initComponent : function() {
				var me = this;
				var store=Ext.create('AM.store.sys.config.ModuleS');
				var tabs=[];
				for(var i=0;i<store.getCount();i++){
					var record=store.getAt(i);
					if(record.get('type')=="SYS"){//如果是私有级,需配置店铺平台等信息
						var form=Ext.create('AM.view.sys.config.ModuleForm',{moduleId:record.get('code')});
						var panel={
								id   : record.get('code'),
								frame:true,
								layout: {
							        type: 'border'
							    },
					            title: record.get('name')+"配置",
					            autoScroll:true,
					            items:[form]
					            }
						tabs.push(panel);
					}
					/*if(record.get('type')=="1"){//如果是私有级,需配置店铺平台等信息
						var leftPanel=Ext.create('AM.view.sys.config.ShopTreePanel',{id:record.get('code')+'tree'});//
						var form=Ext.create('AM.view.sys.config.ModuleForm',{moduleId:record.get('code')});
						var panel={
								id   : record.get('code'),
								frame:true,
								layout: {
							        type: 'border'
							    },
					            title: record.get('name')+"配置",
					            autoScroll:true,
					            items:[leftPanel,form]
					            }
						tabs.push(panel);
					}else{//公共级别不涉及店铺
						var form=Ext.create('AM.view.sys.config.ModuleForm',{moduleId:record.get('code')});
						var panel={
								id   : record.get('code'),
								frame:true,
								layout: {
							        type: 'border'
							    },
					            title: record.get('name')+"配置",
					            autoScroll:true,
					            items:[form]
					            }
						tabs.push(panel);
					}*/
						
					}
				me.items=tabs;
				this.callParent(arguments);
			}
});