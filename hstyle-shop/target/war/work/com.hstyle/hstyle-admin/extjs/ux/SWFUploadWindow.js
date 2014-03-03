Ext.define('Ext.ux.SWFUploadWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.ux.SWFUploadPanel'],
    height: 400,
    width: 700,
    layout: {
        type: 'fit'
    },
    modal:true,
    post_params:{},
    upload_complete_handler:{},
    upload_url:'',
    title: '多文件上传',
    initComponent: function() {
        var me = this;
        
        Ext.applyIf(me, {
            items: [{
               xtype:'uploadpanel',
               addFileBtnText : '选择文件',
               uploadBtnText : '上传',
               removeBtnText : '移除所有',
               cancelBtnText : '取消上传',
               file_size_limit : 10,//MB,
               post_params : me.post_params,
               upload_complete_handler:me.upload_complete_handler,
               upload_url : me.upload_url==''?'upload.do':me.upload_url
            }]
        });

        me.callParent(arguments);
    }
    
});