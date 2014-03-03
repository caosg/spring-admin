/**
 * 商品类目远程数据加载 TreeStore
 */
Ext.define('AM.store.config.CategoryTrees', {
			extend : 'Ext.data.TreeStore',
			requires : 'AM.model.config.Category',
			alias : 'widget.categorytreestore',
			model : 'AM.model.config.Category',
			proxy : {
				type : 'ajax',
				api : {
					read : 'productcategory/commonChildren.do',
					readAllChildren:'productcategory/commonAllChildren.do'
				},
				reader : {
					type : 'json',
					root : 'data'
					//idProperty:'id'
				}
			},
			root : {
				text : '商品类目',
				id : '0',
				expanded : true
			},
			folderSort : true,
			sorters : [{
						property : 'sort',
						direction : 'ASC'
					}]
		});