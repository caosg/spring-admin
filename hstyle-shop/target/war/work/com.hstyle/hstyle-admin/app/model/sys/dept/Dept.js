/**
 * 部门数据模型
 */
Ext.define('AM.model.sys.dept.Dept', {
    extend: 'Ext.data.Model',
    fields: ['id',
    	{name:'text',mapping:'name'},
        {name:'level',type:'int'},
        {name:'orderNum',type:'int'},
        'code','name','status','remark',
        'parentId','parentName' ]
});