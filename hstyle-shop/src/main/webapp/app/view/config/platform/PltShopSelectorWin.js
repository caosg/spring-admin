/**
 * 平台店铺公共查询
 */
Ext.define('AM.view.config.platform.PltShopSelectorWin', {
    extend: 'Ext.window.Window',
    height:550,
    width: 700,
    layout: {
        type: 'fit'
    },
    title : "平台店铺查询",
    modal:true,
    callback:[],//[平台信息,店铺信息]
    initComponent: function() {
        var me = this;
        var shopor=Ext.create('AM.view.config.platform.PltShopSelector',{callback:me.callback});
        Ext.applyIf(me, {
            items: [ shopor ]
        });
        me.callParent(arguments);
    }
});