/**
 * 角色所拥有的菜单功能树远程数据加载
 */
Ext.define('AM.store.sys.role.FunctionTrees', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.role.Function',
			model : 'AM.model.sys.role.Function',
            autoLoad:true,
            roleId:[],
			proxy : {
				type : 'ajax',
				api : {
					read : 'role/functions.do'

				},
				reader : {
					type : 'json',					
					idProperty:'id'
				}
			},
			
            root : {
				text : 'Root',
				id : '-1',
				expanded : true
			},
			folderSort : true,
			sorters : [{
						property : 'sort',
						direction : 'ASC'
					}],
            listeners : {
				// 添加查询参数
				beforeload : function(store, options) {
					var me=this;
					var roleId=me.roleId;
					if(roleId==null||roleId.length==0)
					return false;
					this.proxy.extraParams = {
						roleId:roleId
					};
				}
			}

});