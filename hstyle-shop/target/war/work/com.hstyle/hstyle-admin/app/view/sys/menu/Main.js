/**
 * 部门管理主视图，左侧为部门树，右侧上部为部门基本信息，右侧下部为部门下的人员列表
 */
Ext.define("AM.view.sys.menu.Main", {
	extend : 'Ext.panel.Panel',
	id : 'sys.menu.FunctionsView',
	alias : 'widget.menumain',
	title : "菜单管理",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//左侧部门树
		me.tree = Ext.widget("treepanel", {
			region : "west",
			title : '权限菜单',
			collapsible : true,
			rootVisible : true,
			store : 'sys.menu.TreeFunctions',
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

										text : '增加子级菜单',
										id : 'menu-add-child'
									}, {

										id : 'menu-add',
										text : '增加平级菜单'
									}]
						}
					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "edit",
						scope : me,
						//text : '编辑',
						tooltip : '编辑菜单',
						id : "menu-edit"

					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "delete",
						scope : me,
						//text : '删除',
						tooltip : '删除菜单',
						id : "menu-del"

					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "refresh",
						scope : me,
						//text : '刷新',
						qtip : '刷新',
						id : "menu-refresh"
					}],
			listeners : {
				//部门节点添加单击事件，右侧显示基本信息及人员列表
				'itemclick' : function(view, record, item, index, evt, options) {
					record.data.parentName = record.parentNode.data.text;	
					if(record.data.leaf==true)
					   record.data.leaf="1";
					else
					  record.data.leaf="0";
					Ext.getCmp('basicMenuForm').loadRecord(record);
					var gridStore = Ext.getCmp('menuFunctionsView').store;					
					gridStore.load({
								params : {
									menuId : record.data.id
								}
							});
				}
			}
		});
        //右侧菜单详细信息面板
		me.right = Ext.create('AM.view.sys.menu.Right');
		me.items = [me.tree, me.right];

		me.callParent(arguments);
	}
});