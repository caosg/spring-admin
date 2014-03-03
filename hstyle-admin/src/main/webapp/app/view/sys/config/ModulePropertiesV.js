Ext.define('AM.view.sys.config.ModulePropertiesV',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.modulePropertiesV',
	id : 'sys.config.ModulePropertiesV',
	title : '模块属性',
	region:'center',
	height:400,
	autoScroll:true,
	defaults : {
		flex : 1
	},
	selType : "checkboxmodel",
	selModel : {
		checkOnly : false,
		mode : "SINGLE"
	},
	initComponent : function() {
		var me = this;
		me.typeStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'SCM_PROPERTY_TYPE'}).load();//属性项，类型
		me.tbar = [{
						iconCls : "add",
						text:'增加',
						scope : me,
						tooltip : '增加属性',
						id : "perms-module-add"
					},'-',{
						iconCls : "edit",
						text:'修改',
						scope : me,
						tooltip : '修改属性',
						id : "perms-module-edit"
					},'-',{
						iconCls : "delete",
						text:'删除',
						scope : me,
						tooltip : '删除属性',
						id : "perms-module-delete"
					}
		           ];
		me.columns = [
		  			{
		  				header : 'ID',
		  				dataIndex : 'id',
		  				flex : 1,
		  				hidden : true
		  			},
		  			{
		  				header : '代码',
		  				dataIndex : 'code',
		  				width : 120
		  			},
		  			{
		  				header : '名称',
		  				dataIndex : 'name',
		  				width : 100
		  			},
		  			{
		  				header : '模块code',
		  				dataIndex : 'module',
		  				width : 60,
		  				hidden:true
		  				
		  			},{
		  				header : '类型',
		  				dataIndex : 'type',
		  				width : 80,
		  				renderer : me.typeRender
		  				
		  			},
		  			{
		  				header : '默认值',
		  				dataIndex : 'defaultValue',
		  				width : 90
		  			},{
		  				header : '顺序',
		  				dataIndex : 'orderNo',
		  				width : 80
		  			},
		  			{
		  				header : '可选项',
		  				dataIndex : 'options',
		  				width : 60,
		  				hidden:true
		  			},
		  			{
		  				header : '描述',
		  				dataIndex : 'remark',
		  				flex : 1
		  			},{
		  				header: 'value',
		  				dataIndex:'value',
		  				hidden:true
		  			}
		  		];
		me.store = Ext.create('AM.store.sys.config.ModulePropertiesS');
		me.callParent(arguments);
	},
	typeRender : function(v) {
		var me = this;
		var recored = me.typeStore.findRecord('value',v);
		if(recored==null)  //当store中找不到对应id得时候,index为-1 
	        return '未知';  
	    else{  
	        var name = recored.get('name');   
	        return name;  
	    }
	}
});