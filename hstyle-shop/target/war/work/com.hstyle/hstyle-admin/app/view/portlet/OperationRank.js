Ext.define('AM.view.portlet.OperationRank', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.operationportlet',
    
    height: 570,

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    hits: function(val) {

        return '<span style="color:red;">' + val + '</span>';
    },

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    users: function(val) {

        return '<span style="color:green;">' + val + '</span>';
    },

    initComponent: function(){

        var store = Ext.create('AM.store.portlet.OperationRank');

        Ext.apply(this, {
            height: this.height,
            store: store,
            stripeRows: true,
            columnLines: true,
            columns: [{
                xtype: 'rownumberer',
                width: 30,
                sortable: false
             },
            {
                
                text   : '操作',
                flex: 1,
                sortable : true,
                dataIndex: 'operation_name'
            },{
                text   : '菜单',
                width    : 95,
                sortable : true,               
                dataIndex: 'parent_name'
            },{
                text   : '点击数',
                width    : 70,
                sortable : true,
                renderer : this.hits,
                align:'right',
                dataIndex: 'hits'
            },{
                text   : '用户数',
                width    : 60,
                sortable : true,
                renderer : this.users,
                align:'right',
                dataIndex: 'users'
           }]
        });

        this.callParent(arguments);
    }
});
