Ext.define('AM.view.config.category.TreeSelector', {
	extend : 'Ext.window.Window',
    id:'cateAssignWin',
	height : 450,
	width : 670,
	layout: {
        type: 'fit'
    },
    modal:true,
    callback:null,
	initComponent : function() {
		var me = this;
		var cStore=Ext.create('AM.store.config.SelectorS');
		me.cateTreeGrid = Ext.create('Ext.tree.Panel', {
			 id:'CateTreeSelector',
	         store: cStore,        
	         rootVisible: false,
	         autoScroll:true,
	         multiSelect: false, 
		     
	         columns: [{
		        xtype: 'treecolumn', 
		        header: '名称',
		        flex: 1,          
		        dataIndex: 'text'
		     },{
		        header: '代码',
		        flex: 1,
		        dataIndex: 'code'
		     }
		     ],
	         listeners:{       	 
	              checkchange:function(node, checked){
	               if(checked){
	                   if(node.parentNode!=null){
	                      me.setParentNode(node.parentNode,checked);
	                   }
//	                node.expand();
	               }else{
//	                 node.collapse();
	               }
	               if(node.childNodes.length>0){
	                 me.setChildNode(node.childNodes,checked);
	               }
	             }
	         }   
        });
		me.dockedItems = [
			{
				xtype: 'toolbar',
                items: [
                    {  
                        xtype: 'button',
                        iconCls:'add',
                        text : '确定',
                        tooltip : '添加类目',
                        handler:function(){
                        	var records = me.cateTreeGrid.getView().getChecked();
                            if(records.length == 0){
                            	MsgBoxWar('还没有选中类目');return;  
                            }
                        	me.callback(records);
                        	me.close();
                        },
                        scope:me
                    },'-',
                    {
                        xtype: 'button',
                        iconCls:'close',
                        text : '关闭',
                        tooltip : '关闭窗口',
                        handler:function(){
                        	me.close();
                        }
                    }
                ]
			}
		];
		me.items = [me.cateTreeGrid];
		me.callParent(arguments);
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