/**
* 左侧手风琴式导航菜单
*/
Ext.define('AM.view.sys.AcoordionNavi', {
    extend: 'Ext.panel.Panel',  
    alias : 'widget.accordionnavi',  
    requires: [
        'Ext.layout.container.Accordion','AM.store.sys.Menu','AM.model.sys.Menu'    
    ],    
    layout: 'accordion',
    border: false,
    autoScroll:true,
    defaults: {
        bodyPadding: 5
    },	
    initComponent: function() {
        var me=this;    
        var panels=[];   
        Ext.Array.each(AM.user.menus,function(menu){
          var treePanel=me.buildTree(menu);
          var panel = Ext.create('Ext.Panel', {       
                                                    title :menu.name,                                                     
                                                    layout : 'fit' ,
                                                    items:[treePanel]
                                                });  
          
          panels.push(panel);
          
        });
        
        Ext.applyIf(this, {items:panels});
        this.callParent();
    },
    
    //构造二级菜单树
    buildTree:function(parentNode){
       return Ext.create('Ext.tree.Panel', {  
                rootVisible : false,  
                border : false,  
                useArrows : true,
                lines:false,
                store : Ext.create('AM.store.sys.Menu', {  
                           root : {
				             text :parentNode.name,
				             id :parentNode.id,				 
				             expanded : true
			                }  
                        })
                        /*  
               listeners : {  
                                'itemclick' : function(view, record, item,  
                                        index, e) {  
                                    var id = record.get('id');  
                                    var text = record.get('text');  
                                    var leaf = record.get('leaf');  
                                    if (leaf) {  
                                        alert('id-' + id + ',text-' + text  
                                                + ',leaf-' + leaf);  
                                    }  
                                },  
                                scope : this  
                            }  */
             });  
    }
});