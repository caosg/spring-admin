/**
 * 部门选择树store
 */
Ext.define('AM.store.sys.dept.Selectors', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.dept.Dept',
			model : 'AM.model.sys.dept.Dept',
			
			proxy : {
				type : 'ajax',
				url:'dept/selectDept.do',
				reader : {
					type : 'json',
					root : 'data'
				}
			},
			root : {
				text : '组织机构',
				id : '-1',
				expanded : true
			},

			folderSort : true,
			sorters : [{
						property : 'sort',
						direction : 'ASC'
					}]
		});