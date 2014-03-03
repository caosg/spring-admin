Ext.define('AM.view.sys.role.RoleSelector', {
    extend: 'Ext.window.Window',
    alias : 'widget.roleSelector',
    height:500,
    width: 600,
    layout: {
        type: 'fit'
    },
    title : "角色选择",
    modal:true,
    callback:[],
    initComponent: function() {
        var me = this;
        var store=Ext.create('AM.store.sys.role.RoleSelectors');
        var grid=Ext.create('Ext.grid.Panel', {
        	store : store,
			selType : "checkboxmodel",
			selModel : {
				checkOnly : false,
				mode : "MULTI"
			},
			loadMask: true,
			columnLines: true,
			tbar :[{
							iconCls : "accept",
							text:'确定',
							scope : this,
							tooltip : '确定角色',
							id : "roleChecked",
							handler:me.selected
						},
						'->',{
							xtype:'textfield',emptyText:'输入角色名称',
							width:120,id:'selectorRoleName'
						}, {xtype: 'tbspacer',width:5},
						{
							
							xtype:'button',text:'查询',handler:me.queryRoleName,
							iconCls : "search",
							scope:me
						}],
			columns : [
				        {
							header : '代码',
							dataIndex : 'code',
							flex : 1
						}, {
							header : '名称',
							dataIndex : 'name',
							flex : 1
						},
						{
							header : '超级管理员',
							dataIndex : 'admin',
							flex : 1,
							renderer:me.stateRender
						},
						{
							header : '备注',
							dataIndex : 'remark',
							flex : 2
						}],
			dockedItems :[{
							xtype : 'pagingtoolbar',							
							dock : 'bottom',
							store : store,
							displayInfo : true
			}]
            
        });
        Ext.applyIf(me, {
            items: [grid]
        });

        me.callParent(arguments);
    },
    queryRoleName:function(){				
				
				this.down('grid').store.loadPage(1);
	},
	stateRender:function(val){
		      if(val=='1')
		         return '是';
		     
		     return '否';
	},
	//选择确定用户
	selected:function(){
		var me = this, grid = me.down('grid'),
		rs = grid.getSelectionModel().getSelection();
		var ids=[];
		if (rs.length > 0) {
				Ext.Array.each(rs, function(rec){
                        ids.push(rec.get('id'));
                    });
		}
		
        me.callback(ids);
        me.close();
	}

});