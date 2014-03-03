/**
 * 部门远程数据加载
 */
Ext.define('AM.store.sys.dept.Depts', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.dept.Dept',
			alias : 'widget.deptstore',
			model : 'AM.model.sys.dept.Dept',
			proxy : {
				type : 'ajax',
				api : {
					create:'dept/edit.do',
					read : 'dept/children.do',
					destroy:'dept/delete.do',
					readAllChildren:'dept/allChildren.do'
				},
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