Ext.define('AM.view.sys.user.List', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.userList',
			title : "用户列表",
			id : 'userlist',
			store : 'sys.user.Users',
			region:'center',
			flex : 1,
			selType : "checkboxmodel",
			selModel : {
				checkOnly : false,
				mode : "MULTI"
			},
			loadMask: true,
			viewConfig : {
				loadMask : new Ext.LoadMask(this, {
							msg : '正在努力的为您加载......'
						})
			},
			initComponent : function() {
				var me = this;
				me.positionStore=Ext.create('AM.store.sys.dict.SelectorS', {storeId:'positionStore',dictType : 'POSITION'}).load();
				me.tbar = [{
							iconCls : "person-add",
							text:'增加',
							scope : me,
							tooltip : '增加用户',
							id : "perms-user-add"
						},'-', {
							iconCls : "person-edit",
							text:'编辑',
							scope : me,
							tooltip : '编辑用户',
							id : "perms-user-edit",
							disabled : true
						},'-', {
							iconCls : "person-delete",
							text:'删除',
							scope : me,
							tooltip : '删除用户',
							id : "perms-user-delete",
							disabled : true
						},'-', {
							iconCls : "password-reset",
							text:'密码重置',
							scope : me,
							tooltip : '密码重置',
							id : "perms-user-reset"
						},
						'-', {
							iconCls : "operate",
							text:'分配角色',
							scope : me,
							tooltip : '分配角色',
							id : "perms-user-assignrole"
						},
						'->',{
							xtype:'textfield',emptyText:'输入用户姓名',
							width:120,id:'queryUserName'
						}, {xtype: 'tbspacer',width:5},
						{   
							id:'perms-user-query',
							xtype:'button',text:'查询',handler:me.queryUserName,
							scope:this
						},
						
						{
						    xtype: 'hiddenfield',
                            id:'deptId'},
                         {
						    xtype: 'hiddenfield',
                            id:'deptName'},
                         {
						    xtype: 'hiddenfield',
                            id:'deptCode'}
                        ], 
				me.columns = [
				        {
							header : '登录账号',
							dataIndex : 'loginName',
							flex : 1
						}, {
							header : '用户姓名',
							dataIndex : 'userName',
							flex : 1
						}, {
							header : '职位',
							dataIndex : 'position',
							flex : 1,
							renderer :me.positionRender
						},{
							header : '部门',
							dataIndex : 'deptName',
							flex : 1
						},
						 {
							header : '电子邮件',
							dataIndex : 'email',
							flex : 1
						}, {
							header : '状态',
							dataIndex : 'status',
							flex : 1,
							renderer :stateRender
						}];
				me.dockedItems = [{
							xtype : 'pagingtoolbar',
							id:'userpage',
							dock : 'bottom',
							store : me.store,
							displayInfo : true
						}];

				this.callParent(arguments);
			},
			positionRender:function(val){
		      var record = Ext.getStore('positionStore').findRecord('value',val);          
	          if(record==null)  //当store中找不到对应id得时候,index为-1 
	              return '未知';  
	          else{  
	            var name = record.get('name');   
	            return name;   
	          }  	
	        },
			queryUserName:function(){				
				
				this.store.loadPage(1);
			}
		});
