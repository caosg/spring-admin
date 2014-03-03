/**
 * 菜单功能数据模型
 */
Ext.define('AM.model.sys.role.Function', {
    extend: 'Ext.data.Model',
    fields: ['id',
             'code',
             {name:'text', type:'string',mapping:'name'},
             'name',
             'resource','iconClsName',
             'url',
             {name:'checked',type:'boolean',mapping:'roleChecked'},
             {name:'roleChecked',type:'boolean'},
             'leaf','children',
             'sort','expression','remark']
 });