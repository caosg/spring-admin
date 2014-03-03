/**
 * 商品类目主页面
 */
Ext.define("AM.view.config.category.Main", {
	extend : 'Ext.panel.Panel',
	id : 'config.CategorysView',
	alias : 'widget.categorymain',
	title : "商品类目管理",
	layout : "border",
	split : true,
	closable : true,
	border : 0,
	initComponent : function() {
		var me = this;
		//左侧部门树
		me.tree = Ext.widget("treepanel", {
			region : "west",
			title : '商品类目',
			collapsible : true,
			rootVisible : true,
			store : 'config.CategoryTrees',
			width : 240,
			minWidth : 100,
			maxWidth : 300,
			split : true,
			tbar : [{
						iconCls : "add",
						scope : me,
						//text : '',
						menu : {
							xtype : 'menu',

							width : 120,
							items : [{

										text : '增加子级类目',
										id : 'category-add-child'
									}, {

										id : 'category-add',
										text : '增加平级类目'
									}]
						}
					}, {
						iconCls : "edit",
						scope : me,
						//text : '',
						tooltip : '编辑类目',
						id : "category-edit"

					}, {
						iconCls : "delete",
						scope : me,
						//text : '',
						tooltip : '删除类目',
						id : "category-delete"

					}, {
						iconCls : "refresh",
						scope : me,
						//text : '刷新',
						qtip : '刷新',
						id : "category-refresh"
					}],
			listeners : {
				//商品类目树点击
				'itemclick' : function(view, record, item, index, evt, options) {
					if(record.data.id!=0){
						record.data.parentName = record.parentNode.data.text;
					    Ext.getCmp('basicCategoryForm').loadRecord(record);
					}
					var gridStore = Ext.getCmp('basicCategoryGrid').store;					
							gridStore.load({
								params : {
									page : 1,
									start:0,
									filter_RLIKE_S_code:record.data.code
								}
							});
				}
			}
		});
        //右侧面板和列表
		me.right = Ext.create('AM.view.config.category.Right');
		me.items = [me.tree , me.right];

		me.callParent(arguments);
	}
});