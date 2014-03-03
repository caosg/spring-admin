

Ext.require([
    'Ext.ux.RowExpander'
]);
var obj;

Ext.define('AM.view.config.logisticcompany.List', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.lgcompanylist',
			title : "物流公司列表",
			id : 'logisticcompanylist',
			store : 'config.LogisticCompanyS',
			region:'center',
			loadMask: true,
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
			columnLines: true,        
			viewConfig : {
				enableTextSelection: true						
			},
			initComponent : function() {
				var me = this;
				me.tbar = [{
							iconCls : "add",
							text:'增加',
							scope : me,
							tooltip : '增加',
							id : "companyAdd"
						},'-', {
							iconCls : "edit",
							text:'编辑',
							scope : me,
							tooltip : '编辑',
							id : "companyEdit",
							disabled : true
						},'-', {
							iconCls : "delete",
							text:'删除',
							scope : me,
							tooltip : '删除',
							id : "company-delete",
							disabled : true
						}];
				me.columns = [
				        {
							header : '代码',
							dataIndex : 'code',
							flex : 1
						}, {
							header : '名称',
							dataIndex : 'name',
							flex : 1
						}, {
							header : '外部代码',
							dataIndex : 'outercode',
							flex : 1
						}];
				me.dockedItems = [{
							xtype : 'pagingtoolbar',
							id:'logisticcompanypage',
							dock : 'bottom',
							store : me.store,
							displayInfo : true
						}];

				this.callParent(arguments);
			},
			queryUserName:function(){				
				this.store.reload({params: {start:0,page:1}});
				Ext.getCmp('logisticcompanypage').moveFirst();
			},
			listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('companyEdit').setDisabled(!records.length);
                	Ext.getCmp('company-delete').setDisabled(!records.length);
                },
                'render':function( grid, eOpts ){
                	grid.store.load({
    		    		action:'read',
    					params : {
    						page : 1,
    						start:0,
    						limit:25
    					}                		
                	})
                }	                
			}
		});