/**
 * 菜单数据模型
 */
Ext.define('AM.model.sys.Menu', {
    extend: 'Ext.data.Model',
    fields: ['id',
             'code',
             {name:'text', type:'string',mapping:'name'},
             'url',
             'parentId',
             'leaf',
             'sort']
 });