Ext.define('AM.view.config.category.Cate', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.categoryGrid',
	title : "类目列表",
	id : 'basicCategoryGrid',
	store : 'config.Categorys',
	flex: 1,
	loadMask : true,
	columnLines: true,
	viewConfig : {
		enableTextSelection: true		
	}, 
	initComponent : function() {
		var me = this;	
		me.columns=[
			Ext.create('Ext.grid.RowNumberer'),
			{header : '类目代码',dataIndex : 'code',flex : 1}, 
			{header : '类目名称',dataIndex : 'name',flex : 1}, 
			{header : '层级',dataIndex : 'level',flex : 1},
			{header : '序号',dataIndex : 'orderNum',flex : 1}
        ];
		me.dockedItems= [
                        {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            store:me.store,                      
                            displayInfo: true
                        }
                    ];
      
		this.callParent(arguments);
	}
	
});