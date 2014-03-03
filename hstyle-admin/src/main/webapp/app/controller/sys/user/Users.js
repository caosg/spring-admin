Ext.define('AM.controller.sys.user.Users', {
    extend: 'Ext.app.Controller',
    stores: ['sys.user.Users','sys.dept.Depts'],
    models: ['sys.user.User','sys.dept.Dept'],
    views: ['sys.user.Main', 'sys.user.List'],
    refs: [
        {ref : 'userList', selector : 'userList' },
        {ref : 'userEdit', selector : 'userEditWin'}
    ],

    init: function() {

        this.control({
        	'userList':{
        	    render : this.onPanelRendered,
        	    selectionchange:this.onUserSelected
        	 },
            'panel > userList dataview': {
                itemdblclick: this.onEditUser
            },
            'userList #perms-user-add': {
                click: this.onAddUser
            },
             '#perms-user-edit': {
                click: this.onEditUser
            },
            'userList #perms-user-assignrole': {
                click: this.onAssignRole
            },
             '#perms-user-delete': {
                click: this.onDeleteUser
            },
            '#perms-user-reset': {
                click: this.onResetPassword
            },
            'userEditWin button[action=save]': {
                click: this.updateUser
            }
        });
    },
    //返回主视图
    getMainView : function() {
		return this.getSysUserMainView();
	},
    onPanelRendered : function(panel) {
		console.log('The userList mainpanel was rendered');
	},
	//grid选中事件
    onUserSelected:function(model, sels){
    	var me=this;
    	
    },
    //添加用户
    onAddUser:function(){
    	var me = this, win = Ext.create('AM.view.sys.user.Edit');   	
    	var deptId=Ext.getCmp('deptId').getValue();
    	if(deptId==null||deptId.length==0){
    	   MsgBoxWar("请先选择部门,再添加用户！",win.close());
    	   return;
    	}
    	var deptName=Ext.getCmp('deptName').getValue();
    	var model = Ext.create('AM.model.sys.user.User',{deptId:deptId,deptName:deptName,status:'0'});
        win.down('form').getForm().url="user/add.do";
		win.setTitle("新增用户");
		win.down('form').loadRecord(model);
		win.show();
    },
    //编辑用户
    onEditUser:function() {
		var me = this, grid = me.getUserList(),
		 rs = grid.getSelectionModel().getSelection();
		 var deptId=grid.down('#deptId').getValue();    	
    	var deptName=grid.down('#deptName').getValue();
		 if (rs.length > 0) {
		 	    if (rs.length != 1) {
		 	   	 MsgBoxWar('请选择一条记录！');return;
		 	    }
              	rs = rs[0];	
				if(rs.data.status=='2') {
		 	   	 MsgBoxWar('删除状态不允许编辑！');return;
		 	    }
				rs.data.deptId=deptId;
				rs.data.deptName=deptName;
				var depts=rs.data.depts;
				win =Ext.create('AM.view.sys.user.Edit');
				win.setTitle("编辑用户");
				win.down('form').getForm().url="user/edit.do";
				win.down('form').loadRecord(rs);
				win.down('form').getForm().findField('loginName').setReadOnly(true);				
				if(depts!=null)
				  win.down('grid').store.loadData(depts);
				win.show();
		}else{
			MsgBoxWar('请选择一条记录！');return;
		}
	},
    //查看用户详细信息
    onViewUser: function(grid, record) {
        var win = Ext.create('AM.view.sys.user.Edit').show();
        win.setTitle('用户明细');
        win.down('form').loadRecord(record);
    },
    //删除用户
    onDeleteUser:function() {
		var me = this, grid = me.getUserList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length > 0) {
				var content = "确定删除用户？<br/><p style='color:red'>注意：删除的用户将不能恢复!</p>";
				Ext.Msg.confirm("删除用户", content, function(btn) {
					if (btn == "yes") {
						
						if (rs.length > 0) {
							var ids = [];
                            for(var i = 0; i < rs.length; i++){
                                  ids.push(rs[i].data.id);
                             }                                                    
                          var param={ids:ids};
						  doAjax('user/delete.do',param,me.delSuccess,me);	
						}
					}
				}, grid);
		}else{
		  MsgBoxWar('请选择一条记录！');return;
		}
	},
	//密码重置
	onResetPassword:function(){
	   var me = this, grid = me.getUserList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length > 0) {
				var content = "确定重置密码？<br/><p style='color:red'>重置后密码为123456!</p>";
				Ext.Msg.confirm("重置密码", content, function(btn) {
					if (btn == "yes") {
						
						if (rs.length > 0) {
							var ids = [];
                            for(var i = 0; i < rs.length; i++){
                                  ids.push(rs[i].data.id);
                             }                                                    
                          var param={ids:ids};
						  doAjax('user/resetPassword.do',param,me.resetSuccess,me);	
						}
					}
				}, grid);
		}else{
		  MsgBoxWar('请选择一条记录！');return;
		}
	},
	//打开分配角色窗口
    onAssignRole:function(){
    	var me = this, grid = me.getUserList(),
		rs = grid.getSelectionModel().getSelection();
		if (rs.length != 1) {
			 MsgBoxWar('请选择一条记录！');return;
		}
		if(rs.length>0){
		  if(rs[0].data.status!='0') {
		 	   	 MsgBoxWar('只有启用状态可以分配角色！');return;
		  }
		  var userName=rs[0].data.userName;
		  var userId=rs[0].data.id;
		  var win = Ext.create('AM.view.sys.user.Role',{userId:userId}).show();	
    	  win.setTitle('已分配角色->'+userName);
    	  win.show();
		}
    },
    updateUser: function(button) {
    	var me = this;
        var win  = button.up('window'), form   = win.down('form').getForm(); 
        var deptStore=win.down('grid').store;
        var depts=[];
        for(i=0;i<deptStore.getCount();i++){
        	depts.push(deptStore.getAt(i).data.id);
        }
		if (form.isValid()) {
			  form.submit({
			  	  params: {
                       deptIds: depts
                  },
				  success : function(form, action) {	
					 Ext.Msg.alert('提示', action.result.data);
					 me.getSysUserUsersStore().reload({params: {start:0,page:1}});
					 win.close();
				  },
				  failure: function(form, action) {
                        Ext.Msg.alert('提示', action.result.data);
                  }
				
			  });
		  }
	
    },
    delSuccess:function(result){
    	Ext.Msg.alert('提示', '删除用户成功');
    	Ext.getCmp('userpage').store.reload({params: {start:0,page:1}});
    	Ext.getCmp('userpage').moveFirst();
    },
    resetSuccess:function(result){
    	Ext.Msg.alert('提示', '密码重置成功!<br/><p style="color:red">重置后密码为123456!</p>');
    	
    }
});
