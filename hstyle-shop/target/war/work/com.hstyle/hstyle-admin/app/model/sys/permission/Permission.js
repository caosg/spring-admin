/**
 * 数据权限数据模型
 */
Ext.define('AM.model.sys.permission.Permission', {
    extend: 'Ext.data.Model',
    fields: ['id',
             'code',
             {name:'text', type:'string',mapping:'name'},
             'name','type',
             'resource' ,'action','operator','targets',
             {name:'url',defaultValue:'#'},          
             'parentId',
            'leaf',
             'sort','expression','remark','parentId','parentName']
 });