/**
 * 用户管理主视图，左侧为部门树，右侧为人员列表
 */
Ext.define("AM.view.sys.user.Main", {
	extend : 'Ext.panel.Panel',
	id : 'sys.user.UsersView',
	alias : 'widget.usermain',
	title : "用户管理",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//左侧部门树
		me.tree = Ext.widget("treepanel", {
			region : "west",
			title : '组织机构',
			collapsible : true,
			rootVisible : true,
			store : 'sys.dept.Depts',
			width : 200,
			minWidth : 100,
			maxWidth : 300,
			split : true,
			
			listeners : {
				//部门节点添加单击事件，右侧显示基本信息及人员列表
				'itemclick' : function(view, record, item, index, evt, options) {
					
					me.down("#deptId").setValue(record.data.id);
					me.down('#deptCode').setValue(record.data.code);
					me.down("#deptName").setValue(record.data.text);
					var gridStore = me.grid.store;	
					me.grid.setTitle(record.data.text+'用户列表');
					gridStore.loadPage(1,{
								params : {
									page : 1,
									start:0,									
									id : record.data.id
								}
							});
					//Ext.getCmp('userpage').moveFirst();
				}
			}
		});
        //右侧部门详细信息面板
		me.grid = Ext.create('AM.view.sys.user.List');
		me.items = [me.tree, me.grid];

		me.callParent(arguments);
	}
});