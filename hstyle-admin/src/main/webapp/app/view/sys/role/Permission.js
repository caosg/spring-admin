Ext.define('AM.view.sys.role.Permission', {
	extend : 'Ext.window.Window',
    id:'roleAssignWin',
	height : 450,
	width : 670,
	layout: {
        type: 'fit'
    },
    modal:true,
    roleId:[],
	initComponent : function() {
		var me = this;
		var root;
		
		var functionStore=Ext.create('AM.store.sys.role.FunctionTrees',{roleId:me.roleId});
		var funcTreegrid = Ext.create('Ext.tree.Panel', {
		 id:'funcTreegrid',
         rootVisible: false,
         store: functionStore,        
         autoScroll:true,
         multiSelect: true,       
        //the 'columns' property is now 'headers'
         columns: [{
            xtype: 'treecolumn', //this is so we know which column will show the tree
            header: '名称',
            flex: 1,          
            dataIndex: 'text'
         },{
            header: '代码',
            flex: 1,
            dataIndex: 'code'
         }, {
            header: '类型',
            flex: 1,
            dataIndex: 'resource',
            renderer : me.resourceRender
         }],
         listeners:{       	 
              checkchange:function(node, checked){
               if(checked){
                   if(node.parentNode!=null){
                      me.setParentNode(node.parentNode,checked);
                   }
                node.expand();
               }else{
                // node.collapse();
               }
               if(node.childNodes.length>0){
                 me.setChildNode(node.childNodes,checked);
               }
             }
           }
        });
        //数据权限分配
        me.dictStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'RESOURCE_TYPE'});
        var dataStore=Ext.create('AM.store.sys.role.DataTrees',{roleId:me.roleId});
        var dataTreegrid = Ext.create('Ext.tree.Panel', {
		 id:'dataTreegrid',
         rootVisible: false,
         store: dataStore,
         autoScroll:true,
         multiSelect: true,       
        //the 'columns' property is now 'headers'
         columns: [{
            xtype: 'treecolumn', //this is so we know which column will show the tree
            header: '名称',
            flex: 1,          
            dataIndex: 'text'
         },{
            header: '代码',
            flex: 1,
            dataIndex: 'code'
         }, {
            header: '资源类型',
            flex: 1,
            dataIndex: 'resource',
            renderer:me.resourceDataRender
         }],
         listeners:{
         	 
              checkchange:function(node, checked){
               if(checked){
                   if(node.parentNode!=null){
                      me.setParentNode(node.parentNode,checked);
                   }
                node.expand();
               }else{
                // node.collapse();
               }
               if(node.childNodes.length>0){
                 me.setChildNode(node.childNodes,checked);
               }
             }
           }
        });
		Ext.applyIf(me, {
			items : [{
						xtype : 'tabpanel',
						activeTab : 0,
						border:false,
						items : [{
									xtype : 'panel',
									layout : {
										type : 'fit'
									},
									frame:true,
									title : '菜单功能',
									border: 0,
									items : [funcTreegrid],
									buttonAlign : 'center',
									buttons : [{
												text : '保存',
												action : 'saveFunc'
											}, {
												text : '关闭',
												scope : this,
												handler : this.close
											}]
								}, {
									xtype : 'panel',
									title : '数据权限',
									layout : {
										type : 'fit'
									},
									frame:true,
									border: 0,
									items : [dataTreegrid],
									buttonAlign : 'center',
									buttons : [{
												text : '保存',
												action : 'saveData'
											}, {
												text : '关闭',
												scope : this,
												handler : this.close
											}]
								}]
					}]
			

		});

		me.callParent(arguments);
	},
	resourceRender:function(val) {
				if (val == '1')
					return '<span style="color:green;">菜单</span>';
				if (val == '2')
					return '<span style="color:red;">功能</span>';
	},
	resourceDataRender:function(val){	
       var recored = Ext.getStore('dicSelectStore').findRecord('value',val);          
       if(recored==null) 
        return '未知';  
       else{  
        var name = recored.get('name');   
        return name;   
      }  
	},
	setChildNode:function(childNodes,checked){
        var node;
        for(var i=0;i<childNodes.length;i++){
          node= childNodes[i];
          node.set('checked', checked);  
          if(node.childNodes.length>0){
           this.setChildNode(node.childNodes, checked); 
          }
        }
   },
   setParentNode:function(node,checked){
     node.set('checked', checked);  
     if(node.parentNode!=null){
      this.setParentNode(node.parentNode, checked); 
     }
	
   }
});