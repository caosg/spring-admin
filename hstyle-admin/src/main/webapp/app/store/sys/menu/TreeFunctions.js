/**
 * 菜单远程数据加载
 */
Ext.define('AM.store.sys.menu.TreeFunctions', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.menu.Function',
			model : 'AM.model.sys.menu.Function',			
			proxy : {
				type : 'ajax',
				api : {
					read : 'menu/getMenu.do'

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