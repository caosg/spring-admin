/**
 * 部门远程数据加载
 */
Ext.define('AM.store.sys.dept.CommonDepts', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.sys.dept.Dept',
			alias : 'widget.deptstore',
			model : 'AM.model.sys.dept.Dept',
			proxy : {
				type : 'ajax',
				api : {
					read : 'dept/commonChildren.do',
					readAllChildren:'dept/commonAllChildren.do'
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