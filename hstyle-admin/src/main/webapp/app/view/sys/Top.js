function ChooseTheme(item, checked) {
    if (checked) {
        var Css = "extjs/resources/css/";
        cssname = item.id.replace("Skin-", "").toLowerCase();       
        Css += "ext-all" + (cssname=="default"?"":"-"+cssname) + ".css";
        Ext.util.CSS.swapStyleSheet(null, Css);
        var exp = new Date();
        exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);
        Ext.util.Cookies.set("ThemeCss", Css, exp);
        Ext.util.Cookies.set("ThemeName", item.id, exp);
    }
};
function ChooseNavi(item, checked) {
    if (checked) {
        
        var navi= item.id.replace("Navi-", ""); 
        Ext.getCmp('leftNavi').transNaviStyle(navi);      
        var exp = new Date();
        exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);        
        Ext.util.Cookies.set("NaviName", navi, exp);
    }
};
Ext.define('AM.view.sys.CfgMenu', {
    extend: 'Ext.menu.Menu',
    id: 'CfgMenu',
    alias: 'widget.CfgMenu',
    
    items: [
           {
               text: '主题皮肤',
               iconCls: 'Paint',
               menu: {
                   items: [
                        {
                            id:'Skin-Default',
                            text: '天空蓝',
                            checked: false,
                            group: 'theme',
                            checkHandler:ChooseTheme
                        },'-', {
                            id: 'Skin-Gray',
                            text: '淡雅灰',
                            checked: false,
                            group: 'theme',
                            checkHandler: ChooseTheme
                        }, '-', {
                            id: 'Skin-Access',
                            text: '梦幻黑',
                            checked: false,
                            group: 'theme',
                            checkHandler: ChooseTheme
                        }
                   ]
               }
           },'-',
           {
               text: '导航风格',
               iconCls: 'Paint',
               menu: {
                   items: [
                        {
                            id:'Navi-Tree',
                            text: '树形',
                            checked: false,
                            group: 'navi',
                            checkHandler:ChooseNavi
                        },'-', {
                            id: 'Navi-Accordion',
                            text: '手风琴',
                            checked: false,
                            group: 'navi',
                            checkHandler: ChooseNavi
                        }
                   ]
               }
           },'-'
       ]
    
});
Ext.define('AM.view.sys.customMenu', {
    extend: 'Ext.menu.Menu',
    id: 'customMenu',
    alias: 'widget.customMenu',
    items: [
           {
               text: '修改密码',
               iconCls: 'Paint',
               handler: function () {
                 var win=Ext.create('AM.view.sys.user.Password');
                 win.show();
               }
           },'-'
       ]
    
});
Ext.define('AM.view.sys.TopToobar', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.toptoobar',    
    border: 0,
    items: [
        '->',
        {
            id: 'currentUser',
            text: '常用功能',
            iconCls: 'User',
            id: 'show-all-btn',
            menu : {xtype:'customMenu'}
        },'-',
        {
            text: '首选项',
            iconCls: 'Cog',
            menu: { xtype: 'CfgMenu' }
        }, '-',
        {
            text: '安全退出',
            iconCls: 'close',
            id: 'show-complete-btn',
            tooltip: '安全退出重新登录',
            handler: function () {
                Ext.MessageBox.confirm('确认', '您确定要退出本系统吗？ 退出前请保存好您的重要信息！ ', function (result) {
                    if (result == 'yes') {
                        window.location = "logout.do";
                    }
                });
            }
        }
    ]
});

Ext.define('AM.view.sys.Top',{
   extend:'Ext.panel.Panel',
   alias: 'widget.top',
   region: 'north',
   contentEl:'topDiv',
   header:false,
   margins:'0 5 0 0',
   height:90,
   bbar: ['->',{xtype:'toptoobar'}]
});

