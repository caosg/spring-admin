/**
 * 菜单远程数据加载
 */
Ext.define('AM.store.sys.permission.TreePermissions', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.permission.Permission',
			model : 'AM.model.sys.permission.Permission',			
			proxy : {
				type : 'ajax',
				api : {
					read : 'permission/children.do'

				},
				reader : {
					type : 'json',
					root:  'data'
				}
			},
			root : {
				text :'Root',
				id : -1,
				expanded : true
			},

			folderSort : true,
			sorters : [{
						property : 'sort',
						direction : 'ASC'
					}]
		});