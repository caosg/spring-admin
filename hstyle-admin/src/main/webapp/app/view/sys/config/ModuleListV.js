Ext.define('AM.view.sys.config.ModuleListV',{
			extend : 'Ext.grid.Panel',
			alias : 'widget.moduleListV',
			title : "模块",
			id : 'moduleListView',
			store : 'sys.config.ModuleS',
			region:'west',
//			flex : 1,
			width:350,
			autoScroll:true,
			selType : "checkboxmodel",
			selModel : {
				checkOnly : false,
				mode : "SINGLE"
			},
			loadMask: true,
			viewConfig : {
				loadMask : new Ext.LoadMask(this, {
							msg : '正在努力的为您加载......'
						})
			},
			columns: {
			    items: [
			        {
			            text: "名称",
			            dataIndex: "name"
			        },{
			            text: "编号",
			            dataIndex: "code"
			        },
			        {
			            text: "类型",
			            dataIndex: "type",
			            flex:1,
			            renderer:function(val){
			            	console.log(val);
							if(val=='SYS'){//
								return '系统级'
							}else if(val=='SHOP'){
								return '店铺级'
							}else{
								return '未知'
							}
						}
			        },{
			            text: "描述",
			            dataIndex: "description",
			            flex:1
			        }
			    ]
			},
			initComponent : function() {
				var me = this;
				
				this.callParent(arguments);
			}
});