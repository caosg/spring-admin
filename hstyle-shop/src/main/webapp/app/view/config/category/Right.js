Ext.define('AM.view.config.category.Right', {
			extend : 'Ext.panel.Panel',
			id : 'category.CategorysRight',
			alias : 'widget.categoryright',
			layout : {
				align : 'stretch',
				type : 'vbox'
			},
			border : 0,
			region : "center",
			initComponent : function() {
				var me = this;
				
				me.form = Ext.widget("form", {
							title : '基本信息',
							id : 'basicCategoryForm',
							layout : {
								columns : 3,
								type : 'table'
							},
							bodyPadding : 10,
							defaults : {
								anchor : '100%',
								labelAlign : 'right',
								readOnly : true,
								size : 60
							},
							// The fields
							defaultType : 'textfield',
							items : [{

										name : 'name', 
										fieldLabel : '类目名称',
										size : 60
									}, {

										name : 'level',
										fieldLabel : '类目级别',
										size : 60
									}, {

										name : 'code',
										fieldLabel : '类目代码',
										size : 60
									}, {

										name : "parentName",
										fieldLabel : '上级类目'
									}, {
										name : 'orderNum',
										fieldLabel : '序号'
									},{
                                        xtype: 'hiddenfield',
                                        //id: 'catei_id',
                                        name : 'id'
                                   }]
				});		
				/**
				me.grid = Ext.create('Ext.grid.Panel', {//Ext.widget("gridpanel", {
		            id: 'basicCategoryGrid',
		            title: '子项列表',
		            store : 'category.Categorys',
		            flex: 1,
		            loadMask : true,
		            viewConfig:{
		                    loadMask: new Ext.LoadMask(this,{msg:'正在努力的为您加载......'})
		            },		            
		            columnLines: true,
		            columns: [Ext.create('Ext.grid.RowNumberer'),
				            {
								header : '类目代码',
								dataIndex : 'code',
								flex : 1
							}, {
								header : '类目名称',
								dataIndex : 'name',
								flex : 1
							}, {
								header : '层级',
								dataIndex : 'level',
								flex : 1
							},{
								header : '序号',
								dataIndex : 'orderNum',
								flex : 1
							}
					],
				    dockedItems: [{
				        xtype: 'pagingtoolbar',
				        store: 'category.Categorys',  
				        dock: 'bottom',
				        displayInfo: true
				    }]
				});
				*/
				me.cates = Ext.create('AM.view.config.category.Cate');	
				me.items = [me.form,me.cates];
				me.callParent(arguments);
			}

		});