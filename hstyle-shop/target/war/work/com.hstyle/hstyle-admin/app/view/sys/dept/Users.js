Ext.define('AM.view.sys.dept.Users', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.deptUserGrid',
	title : "用户列表",
	id : 'deptUsersView',
	store : 'sys.dept.Users',
	flex: 1,
	loadMask : true,
    viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'正在努力的为您加载......'})
    },
	initComponent : function() {
		var me = this;	
		me.columns=[
	      { header:'序号',xtype: 'rownumberer',width:40,align:'center'},
          { header: '登录账号',  dataIndex: 'loginName',flex:1 },
          { header: '用户姓名',  dataIndex: 'userName',flex:1 },
          { header: '电子邮件', dataIndex: 'email', flex: 1 },
          { header: '状态', dataIndex: 'status',flex:1 ,renderer:stateRender}
        ];
		me.dockedItems= [
                        {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            store:me.store,                      
                            displayInfo: true
                        }
                    ];
      
		this.callParent(arguments);
	}
	
});