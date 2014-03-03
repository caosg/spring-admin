/**
 * 部门管理主视图，左侧为部门树，右侧上部为部门基本信息，右侧下部为部门下的人员列表
 */
Ext.define("AM.view.sys.dept.Main", {
	extend : 'Ext.panel.Panel',
	id : 'sys.dept.DeptsView',
	alias : 'widget.deptmain',
	title : "部门管理",
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
			tbar : [{
						iconCls : "add",
						scope : me,
						//text : '增加',
						tooltip:'增加部门',
						menu : {
							xtype : 'menu',

							width : 120,
							items : [{

										text : '增加子级部门',
										id : 'perms-dept-add-child'
									}, {

										id : 'perms-dept-add',
										text : '增加平级部门'
									}]
						}
					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "edit",
						scope : me,
						//text : '编辑',
						tooltip : '编辑部门',
						id : "perms-dept-edit"

					},{xtype: 'tbspacer',width:5},
					{
						iconCls : "delete",
						scope : me,
						//text : '删除',
						tooltip : '删除部门',
						id : "perms-dept-del"

					}, {xtype: 'tbspacer',width:5},
					{
						iconCls : "refresh",
						scope : me,
						//text : '刷新',
						tooltip : '刷新',
						id : "perms-dept-refresh"
					}],
			listeners : {
				//部门节点添加单击事件，右侧显示基本信息及人员列表
				'itemclick' : function(view, record, item, index, evt, options) {
					if(!record.parentNode)
					  return ;
					var selectedNode=this.getSelectionModel().getSelection()[0];
					selectedNode.expand();
					record.data.parentName = record.parentNode.data.text;
					Ext.getCmp('basicDeptForm').loadRecord(record);
					var gridStore = Ext.getCmp('deptUsersView').store;					
					var userGrid = gridStore.load({
								params : {
									page : 1,
									start:0,
									id : record.data.id
								}
							});
				}
			}
		});
        //右侧部门详细信息面板
		me.right = Ext.create('AM.view.sys.dept.Right');
		me.items = [me.tree, me.right];

		me.callParent(arguments);
	}
});