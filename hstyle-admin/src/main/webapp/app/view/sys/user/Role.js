/**
 * 用户的角色列表窗口
 */
Ext.define('AM.view.sys.user.Role', {
    extend: 'Ext.window.Window',
    id:'user-role-win',
    height: 400,
    width: 600,
    layout: {
        type: 'fit'
    },
    modal:true,
    title: '已分配角色',
    userId:[],
    initComponent: function() {
        var me = this;
        var store=Ext.create('AM.store.sys.user.Roles',{userId:me.userId});
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
							tooltip : '增加角色',
							scope:me,
							id : "user-add-role",
							handler:me.addRole
						},'-', {
							iconCls : "delete",
							text:'删除',
							tooltip : '删除角色',
							id : "user-delete-role",
							scope:me,
							disabled : true,
							handler:me.deleteRole
						}],
                    columns: [
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
						}],
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
    	this.down('#user-delete-role').setDisabled(sels.length == 0);
    	
    	
    },
    stateRender:function(val){
	      if(val=='1')
	         return '是';
	     
	     return '否';
    },
	//增加角色
	addRole:function(button){
		
		var roleSelector=Ext.create('AM.view.sys.role.RoleSelector',{callback:this.selectedRole});
		roleSelector.show();
	},
	//删除角色:
	deleteRole:function(button){
		 var me=this;
		 var grid = button.up('grid'),
		 rs = grid.getSelectionModel().getSelection();
		 if (rs.length > 0) {
		 	 
			var ids = [];
            for(var i = 0; i < rs.length; i++){
                     ids.push(rs[i].data.id);
            }                                                    
            var param={id:me.userId,roleIds:ids};
            
			doAjax('user/remove-role.do',param,me.delSuccess,me);		
		}
	},
	//角色选择窗口回调
	selectedRole:function(roleIds){
		var me=Ext.getCmp('user-role-win');
		var param={id:me.userId,roleIds:roleIds};
		doAjax('user/add-role.do',param,me.addSuccess,me);
	},
	addSuccess:function(result,scope){
		MsgBoxInfo('添加角色成功！');		
		scope.down('grid').store.load();
	},
	delSuccess:function(result,scope){
		MsgBoxInfo('删除角色成功！');	
		scope.down('grid').store.load();
	}

});