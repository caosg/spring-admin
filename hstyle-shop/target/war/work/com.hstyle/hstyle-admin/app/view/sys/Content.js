Ext.require([
    'Ext.tab.*',
    'Ext.ux.TabCloseMenu'
]);

Ext.define('AM.view.sys.Content',{
	extend: 'Ext.tab.Panel',
	alias: 'widget.content',
	margins:'0 5 0 0',
	uses: [
        'AM.view.portal.PortalPanel', 
        'AM.view.portal.PortalColumn',
        'AM.view.sys.PortletPanel',       
        'AM.data.Constants'
    ],
	initComponent : function(){
		var currentItem;
		Ext.apply(this,{
			id: 'contentPanel',
		    region: 'center',
		    enableTabScroll: true,
		    defaults: {
			   autoScroll:true
			   //bodyPadding: 10
		    },
		    activeTab: 0,
		    border: false,
		    layout:{
		    	type:'fit'
		    },
		   //plain: true,
		    items: [{
                    id: 'app-portal',
                    xtype: 'portletpanel'
                }],
			plugins: Ext.create('Ext.ux.TabCloseMenu', {
            extraItemsTail: [
                '-',
                {
                    text: 'Closable',
                    checked: true,
                    hideOnClick: true,
                    handler: function (item) {
                        currentItem.tab.setClosable(item.checked);
                    }
                },
                '-',
                {
                    text: 'Enabled',
                    checked: true,
                    hideOnClick: true,
                    handler: function(item) {
                        currentItem.tab.setDisabled(!item.checked);
                    }
                }
            ],
            listeners: {
                aftermenu: function () {
                    currentItem = null;
                },
                beforemenu: function (menu, item) {
                    menu.child('[text="Closable"]').setChecked(item.closable);
                    menu.child('[text="Enabled"]').setChecked(!item.tab.isDisabled());

                    currentItem = item;
                }
            }
        })
		});
		this.callParent(arguments);
	}
});