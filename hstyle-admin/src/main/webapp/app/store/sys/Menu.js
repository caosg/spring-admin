Ext.define('AM.store.sys.Menu', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.Menu',
			model : 'AM.model.sys.Menu',	
			autoLoad:true,
			proxy : {
				type : 'ajax',
				api : {
					read : 'getUserMenu.do'

				},
				reader : {
					type : 'json',
					root:  'data'
				}
			},
			root : {
				text :'导航菜单',
				id : -1,
				expanded : true
			},

			folderSort : true,
			sorters : [{
						property : 'sort',
						direction : 'ASC'
					}]
		});