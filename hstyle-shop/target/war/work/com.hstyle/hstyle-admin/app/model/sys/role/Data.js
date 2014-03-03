Ext.define('AM.model.sys.role.Data', {
    extend: 'Ext.data.Model',
    fields: ['id',
             'code',
             {name:'text', type:'string',mapping:'name'},
             'name',
             'resource',
             'url',
             {name:'checked',type:'boolean',mapping:'roleChecked'},
             {name:'roleChecked',type:'boolean'},
             'leaf','children',
             'sort','expression','remark']
 });