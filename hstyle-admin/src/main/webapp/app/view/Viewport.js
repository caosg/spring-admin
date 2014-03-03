Ext.define('AM.view.Viewport', {
    extend: 'Ext.container.Viewport',
    layout: 'border',
    items: [{ xtype: 'top'},
    	    { xtype: 'systemnavi'},
    	    { xtype: 'content'},
    	    { xtype: 'footer'}
        ]
});