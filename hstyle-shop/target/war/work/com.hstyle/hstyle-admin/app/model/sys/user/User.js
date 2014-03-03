Ext.define('AM.model.sys.user.User', {
    extend: 'Ext.data.Model',
    fields: ['id', 'userName', 'email','loginName','password','status','position',
    	{name:'deptId',type:'long',mapping:'dept.id'},
    	{name:'deptName',mapping:'dept.name'},
    	'depts'],
    idProperty : "id"
    
});