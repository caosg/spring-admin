Ext.define('AM.view.sys.permission.RuleList', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.rulegrid',
	title : "目标定义",
	id : 'rulesView',
	store : 'sys.permission.Targets',
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
		me.typetStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'TARGET_TYPE'}).load();
		me.tbar = [{
			text:'增加',
			scope : me,
			tooltip : '增加实例',
			id : "rule-add"
		},'-', {
			iconCls : "folder-edit",
			text:'编辑',
			scope : me,
			tooltip : '编辑实例',
			id : "rule-edit"
		},'-', {
			iconCls : "folder-delete",
			text:'删除',
			scope : me,
			tooltip : '删除',
			id : "rule-delete"
		}
        ], 
		me.columns=[
	      
          { header: '名称',  dataIndex: 'name',flex:1 },
          { header: '值',  dataIndex: 'value',flex:1 },          
          { header: '类型', dataIndex: 'type', flex:1,renderer:me.renderType },
          { header: '备注', dataIndex: 'remark', flex: 1 }
        ];
		
      
		this.callParent(arguments);
	},
	renderType:function(val){
		 var record = Ext.getStore('dicSelectStore').findRecord('value',val);          
	       if(record==null)  //当store中找不到对应id得时候,index为-1 
	        return '未知';  
	       else{  
	        var name = record.get('name');   
	        return name;   
	      }  	
	}
	,
	openDynaForm:function(){
	   var proForm=Ext.create('AM.view.sys.ext.PropertyForm');
	  Ext.create('Ext.window.Window', {
           title: '属性信息',
           width: 400,
           layout: 'fit',
           items: [
               proForm
           ]
       }).show();
	}
	
	
});