/**
 * 商品类目数据模型
 */
Ext.define('AM.model.config.Category', {
    extend: 'Ext.data.Model',
    fields: ['id',
    	{name:'text',mapping:'name'},
        {name:'level',type:'int'},
        {name:'orderNum',type:'int'},
        {name:'tmp',mapping:'checked'},
        'code','name','status',
        'parentId','parentName']
});