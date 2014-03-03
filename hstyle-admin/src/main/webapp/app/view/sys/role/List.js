Ext.define('AM.view.sys.role.List', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.roleList',
			title : "角色列表",
			id : 'sys.role.RolesView',
			store : 'sys.role.Roles',
			selType : "checkboxmodel",
			selModel : {
				checkOnly : false,
				mode : "MULTI"
			},
			loadMask: true,
			closable : true,
			columnLines: true,
			viewConfig : {
                enableTextSelection: true
				
			},
			initComponent : function() {
				var me = this;
				me.tbar = [{
							iconCls : "add",
							text:'增加',
							id:'perms-role-add',
							scope : me,
							tooltip : '增加角色'
							
							
						},'-', {
							iconCls : "edit",
							text:'编辑',
							id:'perms-role-edit',
							scope : me,
							tooltip : '编辑角色',							
							disabled : true
						},'-', {
							iconCls : "delete",
							text:'删除',
							id:'perms-role-delete',
							scope : me,
							tooltip : '删除角色',							
							disabled : true
						},'-',{
							text:'分配权限',
							id:'perms-role-assignfunc',
							scope : me,
							tooltip : '分配权限',
							disabled : true
						},'-',{
							text:'分配人员',
							id:'perms-role-assignuser',
							scope : me,
							tooltip : '分配人员',
							disabled : true
						},'-',{
							text:'导出excel',
							id:'perms-role-excel',
							scope : me,
							tooltip : '导出excel',
							iconCls:'download',
							handler:function(){
							  window.location=contextPath+'/role/export.do';
							}
						},
						'->',{
							xtype:'textfield',emptyText:'输入角色名称',
							width:120,id:'queryRoleName'
						}, {xtype: 'tbspacer',width:5},
						{
							id:'perms-role-queryfunc',
							xtype:'button',text:'查询',handler:me.queryRoleName,
							scope:this
						}
                        ], 
				me.columns = [
				        {
							header : '代码',
							dataIndex : 'code',
							flex : 1
						}, {
							header : '名称',
							dataIndex : 'name',
							flex : 1
						},
						{
							header : '超级管理员',
							dataIndex : 'admin',
							flex : 1,
							renderer:me.stateRender
						},
						{
							header : '备注',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
							dataIndex : 'remark',
							flex : 2
						}];
				me.dockedItems = [{
							xtype : 'pagingtoolbar',
							id:'rolePage',
							dock : 'bottom',
							store : me.store,
							displayInfo : true
						}];

				this.callParent(arguments);
			},
			queryRoleName:function(){				
				
				this.store.loadPage(1);
			},
			stateRender:function(val){
		      if(val=='1')
		         return '是';
		     
		     return '否';
	        }
		});
