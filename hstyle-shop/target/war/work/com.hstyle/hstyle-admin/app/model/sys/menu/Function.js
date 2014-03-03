/**
 * 菜单功能数据模型
 */
Ext.define('AM.model.sys.menu.Function', {
    extend: 'Ext.data.Model',
    fields: ['id',
             'code',
             {name:'text', type:'string',mapping:'name'},
             'name',
             'resource',
             {name:'iconCls',type:'string',mapping:'iconClsName'},
             'iconClsName',
             'url',
             'parentId',
            'leaf',
             'sort','expression','remark','parentId','parentName']
 });