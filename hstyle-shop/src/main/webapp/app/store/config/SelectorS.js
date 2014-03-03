/**
 * 商品类目远程数据加载 Store
 */
Ext.define("AM.store.config.SelectorS",{
	extend : 'Ext.data.TreeStore',
	requires : 'AM.model.config.SelectorM',
	model : 'AM.model.config.SelectorM',
    autoLoad:true,
	proxy : {
		type : 'ajax',
		api : {
			read : 'productcategory/children.do'
		},
		reader : {
			type : 'json',
			root:  'data',
			idProperty:'id'
		}
	},
    root : {
		text : 'Root',
		id : '0',
		expanded : true
	},
	folderSort : true
});