/**
 * 商品类目数据模型
 */
Ext.define('AM.model.config.SelectorM', {
    extend: 'Ext.data.Model',
    fields: ['id',
    	{name:'text',mapping:'name'},
        {name:'level',type:'int'},
        {name:'orderNum',type:'int'},
        'code','name','status',
        'checked','leaf',
        'parentId','parentName']
});