/**
 * 用户选择界面
 */
Ext.define("AM.view.sys.user.UserSelector", {
	extend : 'Ext.panel.Panel',
	id : 'userSelector',
	alias : 'widget.userselector',
	
	layout : "border",
	split : true,
	border : 0,
	callback:[],
	initComponent : function() {
		var me = this;
		var deptStore=Ext.create('AM.store.sys.dept.Selectors');
		var userStore=Ext.create('AM.store.sys.user.SelectorUsers');
		//左侧部门树
		me.tree = Ext.widget("treepanel", {
			region : "west",
			title : '组织机构',
			collapsible : true,
			rootVisible : true,
			store : deptStore,
			width : 200,
			minWidth : 100,
			maxWidth : 300,
			split : true,
			
			listeners : {
				//部门节点添加单击事件，右侧显示基本信息及人员列表
				'itemclick' : function(view, record, item, index, evt, options) {
					
					me.grid.down("#selectorDeptCode").setValue(record.data.code);
					me.grid.down("#selectorDeptName").setValue(record.data.text);
					var gridStore = me.grid.store;					
					gridStore.load({
								params : {
									page : 1,
									start:0,									
									id : record.data.id
								}
							});
					Ext.getCmp('userSelectorPage').moveFirst();
				}
			}
		});
        //右侧部门详细信息面板
		me.grid = Ext.widget("grid",{
			title : "用户列表",
		    id : 'userSelectorList',
		    store : userStore,
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
		    tbar :[{
							iconCls : 'accept',
							text:'确定',
							scope : this,
							tooltip : '选择选中用户',
							id : "userChecked",
							handler:me.selectUsers
						},
						'->',{
							xtype:'textfield',emptyText:'输入用户姓名',
							width:120,id:'selectorUserName'
						}, {xtype: 'tbspacer',width:5},{
							xtype:'button',text:'查询',handler:me.queryUserName,
							iconCls:'search',
							scope:this
						},
						
						{
						    xtype: 'hiddenfield',
                            id:'selectorDeptCode'},
                         {
						    xtype: 'hiddenfield',
                            id:'selectorDeptName'}],
		    columns : [
	        {
				header : '登录账号',
				dataIndex : 'loginName',
				flex : 1
			}, {
				header : '用户姓名',
				dataIndex : 'userName',
				flex : 1
			},
			{
				header : '部门',
				dataIndex : 'deptName',
				flex : 1
			},
			{
				header : '状态',
				dataIndex : 'status',
				flex : 1,
				renderer : me.stateRender
			}],
	        dockedItems : [{
				xtype : 'pagingtoolbar',
				id:'userSelectorPage',
				dock : 'bottom',
				store : userStore,
				displayInfo : true
			}]
		});
		me.items = [me.tree, me.grid];

		me.callParent(arguments);
	},
	
	stateRender : function(val) {
		if (val == '0')
			return '<span style="color:green;">启用</span>';
		if (val == '1')
			return '<span style="color:red;">禁用</span>';
	    if (val == '2')
			return '<span style="color:black;">删除</span>';
	},
	queryUserName:function(){				
		
		this.down('grid').store.loadPage(1);
	},
	//选择确定用户
	selectUsers:function(){
		var me = this, grid = me.down('grid'),
		rs = grid.getSelectionModel().getSelection();
		var users=[];
		if (rs.length > 0) {
				Ext.Array.each(rs, function(rec){
                        users.push(rec.data);
                    });
		}
		
        me.callback(users);
        me.up('window').close();
	}
});