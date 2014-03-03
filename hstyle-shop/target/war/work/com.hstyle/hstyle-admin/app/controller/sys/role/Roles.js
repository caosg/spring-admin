Ext.define('AM.controller.sys.role.Roles', {
    extend: 'Ext.app.Controller',
    stores: ['sys.role.Roles'],
    models: ['sys.role.Role','sys.menu.Function'],
    views: [ 'sys.role.List','sys.role.Edit'],
    refs: [
        {ref : 'roleList', selector : 'roleList' }
    ],

    init: function() {

        this.control({
        	'roleList':{
        	    render : this.onPanelRendered,
        	    selectionchange:this.onRoleSelected
        	 },
            'panel > roleList dataview': {
                itemdblclick: this.onEdit
            },
            '#perms-role-add': {
                click: this.onAdd
            },
             '#perms-role-edit': {
                click: this.onEdit
            },
             '#perms-role-delete': {
                click: this.onDelete
            },
            '#perms-role-assignfunc':{
                click: this.onAssign
            },
            '#perms-role-assignuser':{
                click: this.onAssignUser
            },
            'roleEditWin button[action=save]': {
                click: this.update
            },
            '#roleAssignWin button[action=saveFunc]': {
                click: this.saveAssignFuncs
            },
            '#roleAssignWin button[action=saveData]': {
                click: this.saveAssignDatas
            }
        });
    },
    //返回主视图
    getMainView : function() {
				return this.getSysRoleListView();
	},
    onPanelRendered : function(panel) {
		console.log('The roleList mainpanel was rendered');			
				
	},
	//grid选中事件
    onRoleSelected:function(model, sels){
    	var me=this;
    	//this.getRoleList().down('#role.edit').setDisabled(sels.length == 0);
    	//this.getRoleList().down('#role.delete').setDisabled(sels.length == 0);
    	//this.getRoleList().down('#role.assignfun').setDisabled(sels.length == 0);
    	//this.getRoleList().down('#role.assignuser').setDisabled(sels.length == 0);
    },
    //添加角色
    onAdd:function(){
    	var me = this, win = Ext.create('AM.view.sys.role.Edit');   	
    	var model = Ext.create('AM.model.sys.role.Role',{admin:0});
        win.down('form').getForm().url="role/add.do";
		win.setTitle("新增角色");
		win.down('form').loadRecord(model);
		win.show();
    },
    //编辑角色
    onEdit:function() {
		var me = this,  grid = me.getRoleList(),
		 rs = grid.getSelectionModel().getSelection();
		 if (rs.length > 0) {
		 	   if (rs.length != 1) {
		 	   	 MsgBoxWar('请选择一条记录！');return;
		 	   }
		 	   win =Ext.create('AM.view.sys.role.Edit'),
				rs = rs[0];	
				if(rs.data.admin)
				   rs.data.admin=1;
				else
				   rs.data.admin=0;
				win.setTitle("编辑角色");
				win.down('form').getForm().url="role/edit.do";
				win.down('form').loadRecord(rs);
				win.show();
		}else{
			MsgBoxWar('请选择一条记录！');return;
		}
	},
    //查看用户详细信息
    onView: function(grid, record) {
        var win = Ext.create('AM.view.sys.role.Edit').show();
        win.setTitle('用户明细');
        win.down('form').loadRecord(record);
    },
    //删除用户
    onDelete:function() {
		var me = this, grid = me.getRoleList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length > 0) {
				var content = "确定删除角色？<br/><p style='color:red'>注意：删除的角色将不能恢复!</p>";
				Ext.Msg.confirm("删除角色", content, function(btn) {
					if (btn == "yes") {
						
						if (rs.length > 0) {
							var ids = [];
                            for(var i = 0; i < rs.length; i++){
                            	  if(rs[i].data.admin){
                            	  	MsgBoxWar('超级管理员不允许删除！');return;
                            	  }
                                  ids.push(rs[i].data.id);
                             }                                                    
                          var param={ids:ids};
						  doAjax('role/delete.do',param,me.delSuccess,me);	
						}
					}
				}, grid);
		}else{
			MsgBoxWar('请选择一条记录！');return;
		}
	},
    update: function(button) {
    	  var me = this;
        var win  = button.up('window'),
        form   = win.down('form').getForm(); 
        
		   if (form.isValid()) {
			   form.submit({
				   success : function(form, action) {	
					 Ext.Msg.alert('提示', action.result.data);
					 me.getSysRoleRolesStore().reload({params: {start:0,page:1}});
					 win.close();
				 },
				 failure: function(form, action) {
				 	   if(action.result)
				 	      MsgBoxInfo(action.result.data);
				 	   else
                         Ext.Msg.alert('提示', '保存失败');
                     }
				
			   });
		   }
	
    },
    delSuccess:function(result){
    	Ext.Msg.alert('提示', '删除角色成功');
    	Ext.getCmp('rolePage').store.reload({params: {start:0,page:1}});
    	Ext.getCmp('rolePage').moveFirst();
    },
    //打开分配权限窗口
    onAssign:function(){
    	
    	var me = this, grid = me.getRoleList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length != 1) {
			 MsgBoxWar('请选择一条记录！');return;
		}
		if(rs.length>0){
		  if(rs[0].data.admin){
                   MsgBoxWar('超级管理员不需要分配权限！');return;
          }
		  var roleName=rs[0].data.name;
		  var roleId=rs[0].data.id;
		  var win = Ext.create('AM.view.sys.role.Permission',{roleId:roleId}).show();	
    	  win.setTitle('分配权限->'+roleName);
    	 
		}
    },
    //保存菜单功能权限分配
    saveAssignFuncs:function(button){
    	var me=this;
    	 var tree=Ext.getCmp('funcTreegrid');
    	 var roleId=button.up('window').roleId;

    	 var records = tree.getView().getChecked(),
                        permissionIds = [];
                    
                    Ext.Array.each(records, function(rec){
                    	
                          permissionIds.push(rec.get('id'));
                    });
                   
                    var param={id:roleId,permissionIds:permissionIds};
					doAjax('role/save-rolefunc.do',param,me.assignSuccess,me);
    },
    assignSuccess:function(result){
    	Ext.Msg.alert('提示', '分配权限成功');
    	
    },
    //保存数据权限权限分配
    saveAssignDatas:function(button){
    	var me=this;
    	 var tree=button.up('window').down('#dataTreegrid');
    	 var roleId=button.up('window').roleId;

    	 var records = tree.getView().getChecked(),
                        permissionIds = [];
                    
                    Ext.Array.each(records, function(rec){
                    	
                          permissionIds.push(rec.get('id'));
                    });
                   
                    var param={id:roleId,permissionIds:permissionIds};
                    
					doAjax('role/save-roleData.do',param,me.assignDataSuccess,me);
    },
    assignDataSuccess:function(result){
    	Ext.Msg.alert('提示', '分配权限成功');
    	
    },
    //打开分配人员窗口
    onAssignUser:function(){
    	var me = this, grid = me.getRoleList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length != 1) {
			 MsgBoxWar('请选择一条记录！');return;
		}
		if(rs.length>0){
		 var roleName=rs[0].data.name;
		 var roleId=rs[0].data.id;
		 var win = Ext.create('AM.view.sys.role.User',{roleId:roleId}).show();	
    	  win.setTitle('已分配人员->'+roleName);
    	 
		}
    }
    
});
