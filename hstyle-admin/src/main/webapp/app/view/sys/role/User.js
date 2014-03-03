/**
 * 角色已分配给的人员列表
 */
Ext.define('AM.view.sys.role.User', {
    extend: 'Ext.window.Window',
    id:'roleUserWin',
    height: 400,
    width: 600,
    layout: {
        type: 'fit'
    },
    modal:true,
    title: '角色已分配人员',
    roleId:[],
    initComponent: function() {
        var me = this;
        var store=Ext.create('AM.store.sys.role.Users',{roleId:me.roleId});
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel', 
                    selType : "checkboxmodel",
			        selModel : {
				          checkOnly : false,
				          mode : "MULTI"
			        },
			        loadMask: true,
			        columnLines: true,
                    store: store,
                    
                    tbar: [{
							iconCls : "add",
							text:'增加',
							tooltip : '增加人员',
							scope:me,
							id : "roleUserAdd",
							handler:me.addUser
						},'-', {
							iconCls : "delete",
							text:'删除',
							tooltip : '删除人员',
							id : "roleUserRemove",
							scope:me,
							disabled : true,
							handler:me.deleteUser
						}],
                    columns: [
                        {
							header : '登录账号',
							dataIndex : 'loginName',
							flex : 1
						}, {
							header : '用户姓名',
							dataIndex : 'userName',
							flex : 1
						}, {
							header : '部门',
							dataIndex : 'deptName',
							flex : 1
						}, {
							header : '状态',
							dataIndex : 'status',
							flex : .5,
							renderer : me.stateRender
						}
                    ],
                     listeners:{
                     	selectionchange:this.onRowSelected
                     }
                    
                }
            ]
        });

        me.callParent(arguments);
    },
    onRowSelected:function(model, sels){
    	var me=this;
    	this.down('#roleUserRemove').setDisabled(sels.length == 0);
    	
    	
    },
    stateRender : function(val) {
				if (val == '0')
					return '<span style="color:green;">启用</span>';
				if (val == '1')
					return '<span style="color:red;">禁用</span>';
	},
	//增加用户
	addUser:function(button){
		
		var userSelector=Ext.create('AM.view.sys.user.UserSelectorWin',{callback:this.selectedUser});
		userSelector.show();
	},
	//删除用户:
	deleteUser:function(button){
		 var me=this;
		 var grid = button.up('grid'),
		 rs = grid.getSelectionModel().getSelection();
		 if (rs.length > 0) {
		 	 
			var ids = [];
            for(var i = 0; i < rs.length; i++){
                     ids.push(rs[i].data.id);
            }                                                    
            var param={id:me.roleId,userIds:ids};
            
			doAjax('role/remove-users.do',param,me.delUserSuccess,me);		
		}
	},
	//用户选择窗口回调
	selectedUser:function(users){
		var me=Ext.getCmp('roleUserWin');
		var userIds=[];
		Ext.Array.each(users,function(user){
		  userIds.push(user.id);
		});
		var param={id:me.roleId,userIds:userIds};
		doAjax('role/add-users.do',param,me.addUserSuccess,me);
	},
	addUserSuccess:function(result,scope){
		MsgBoxInfo('添加用户成功！');		
		scope.down('grid').store.load();
	},
	delUserSuccess:function(result,scope){
		MsgBoxInfo('删除用户成功！');	
		scope.down('grid').store.load();
	}

});