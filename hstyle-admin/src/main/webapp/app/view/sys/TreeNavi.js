/*
*  树状导航
*/
Ext.define('AM.view.sys.TreeNavi', {
			extend : 'Ext.tree.Panel',
			alias : 'widget.treenavi',
			requires : ['AM.store.sys.Menu','AM.model.sys.Menu'],
			initComponent : function() {
			    var naviStore=Ext.create('AM.store.sys.Menu');
				Ext.apply(this, {
							
							useArrows : true,							
							enableDD : false,
							border: false,
							rootVisible : true,							
							autoScroll : true,							
							store : naviStore,
							dockedItems: [{
                               xtype: 'toolbar',
                               items: [{
                                            text: '展开',
                                            handler: function(){
                                            	var tree=this.up('toolbar').up("treenavi");                                            	
                                                tree.getEl().mask('Expanding tree...');
                                                var toolbar = this.up('toolbar');
                                                toolbar.disable();                   
                                                tree.expandAll(function() {
                                                  tree.getEl().unmask();
                                                  toolbar.enable();
                                                });
                                             }
                                      }, {
                                           text: '折叠',
                                           handler: function(){
                                           	 var tree=this.up('toolbar').up("treenavi");
                                             var toolbar = this.up('toolbar');
                                             toolbar.disable();                    
                                             tree.collapseAll(function() {
                                                        toolbar.enable();
                                                   });
                                            }
                                    },{
                                    	text:'刷新',
                                    	handler:function(){
                                    		var tree=this.up('toolbar').up("treenavi");
                                    		tree.store.load();
                                    	}
                                    
                                    }
                                    ]
                              }]

						});
				this.callParent(arguments);
			}
		});