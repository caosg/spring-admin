/**
 * 部门人员store
 */
Ext.define("AM.store.sys.dept.Users",{
    extend: 'Ext.data.Store',
    id:'deptUsersStore',
    model: 'AM.model.sys.user.User',
    autoLoad: false,
    batchActions: false,
    remoteFilter: true,
    remoteSort: true,
    pageSize: 25,
    proxy: {
        type: "ajax",
        url: "dept/users.do",
        reader: {
            type: 'json',
            root: 'result',
            totalProperty:'totalItems'
        }

    },
    listeners:{
    	    //分页添加查询参数
            beforeload:function (store, options){  
                this.proxy.extraParams = {
                	deptCode : Ext.getCmp('basicDeptForm').getForm()
								.findField('code').getValue()
                    
                };
            }
        }

});