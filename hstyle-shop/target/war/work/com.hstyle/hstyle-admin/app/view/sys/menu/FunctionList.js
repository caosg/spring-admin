Ext.define('AM.view.sys.menu.FunctionList', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.funtionGrid',
	title : "功能列表",
	id : 'menuFunctionsView',
	store : 'sys.menu.Functions',
	flex: 1,
    selType : "checkboxmodel",
    selModel : {
	  checkOnly : false,
	  mode : "MULTI"
    },
	loadMask : true,
    viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'正在努力的为您加载......'})
    },
	initComponent : function() {
		var me = this;
		me.tbar = [{
			iconCls : "folder-add",
			text:'增加',
			scope : me,
			tooltip : '增加功能',
			id : "function-add"
		},'-', {
			iconCls : "folder-edit",
			text:'编辑',
			scope : me,
			tooltip : '编辑功能',
			id : "function-edit"
		},'-', {
			iconCls : "folder-delete",
			text:'删除',
			scope : me,
			tooltip : '删除功能',
			id : "function-delete"
		}
        ], 
		me.columns=[
	      
          { header: '功能名称',  dataIndex: 'name',flex:1 },
          { header: '代码',  dataIndex: 'code',flex:1 },
          { header: 'URL', dataIndex: 'url', flex: 1 },
          { header: '资源类型', dataIndex: 'resource', flex: .5 ,renderer:me.stateRender},
          { header: '图标CSS', dataIndex: 'iconClsName', flex: 1 },
          { header: '序号', dataIndex: 'sort', flex: .5 },
          { header: '表达式', dataIndex: 'expression',flex:1 }
        ];
		
      
		this.callParent(arguments);
	},
	stateRender:function(val){
		if(val=='1')
		  return '<span style="color:green;">菜单</span>';
		if(val=='2')
		  return '<span style="color:green;">功能</span>';
	},
	queryFunction:function(){
	}
	
});