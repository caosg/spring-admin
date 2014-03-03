Ext.define('AM.view.sys.user.UserSelectorWin', {
    extend: 'Ext.window.Window',

    height:500,
    width: 600,
    layout: {
        type: 'fit'
    },
    title : "用户选择",
    modal:true,
    callback:[],
    initComponent: function() {
        var me = this;
        var userselector=Ext.create('AM.view.sys.user.UserSelector',{callback:me.callback});
        Ext.applyIf(me, {
            items: [ userselector ]
        });

        me.callParent(arguments);
    }

});