/**
 * 数据权限管理主视图
 */
Ext.define("AM.view.sys.permission.Main", {
	extend : 'Ext.panel.Panel',
	id : 'sys.permission.PermissionsView',
	alias : 'widget.permissionmain',
	title : "数据权限管理",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//左侧部门树
		me.tree = Ext.widget("treepanel", {
			region : "west",			
			collapsible : true,
			rootVisible : true,
			store : 'sys.permission.TreePermissions',
			width : 200,
			minWidth : 100,
			maxWidth : 300,
			split : true,
			tbar : [{
						iconCls : "add",
						scope : me,
						//text : '增加',
						menu : {
							xtype : 'menu',

							width : 120,
							items : [{

										text : '增加子级分类',
										id : 'permission-add-child'
									}, {

										id : 'permission-add',
										text : '增加平级分类'
									}]
						}
					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "edit",
						scope : me,
						//text : '编辑',
						tooltip : '编辑权限分类',
						id : "permission-edit"

					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "delete",
						scope : me,
						//text : '删除',
						tooltip : '删除分类',
						id : "permission-del"

					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "refresh",
						scope : me,
						//text : '刷新',
						qtip : '刷新',
						id : "permission-refresh"
					}],
			listeners : {
				//部门节点添加单击事件，右侧显示基本信息及人员列表
				'itemclick' : function(view, record, item, index, evt, options) {
					if(!record.parentNode)
					  return ;
					record.data.parentName = record.parentNode.data.text;	
					if(record.data.leaf==true)
					   record.data.leaf="1";
					else
					  record.data.leaf="0";
					Ext.getCmp('basicPermissionForm').loadRecord(record);
					var gridStore = Ext.getCmp('rulesView').store;					
					gridStore.load({
								params : {
									typeId : record.data.id
								}
							});
				}
			}
		});
        //右侧菜单详细信息面板
		me.right = Ext.create('AM.view.sys.permission.Right');
		me.items = [me.tree, me.right];

		me.callParent(arguments);
	}
});