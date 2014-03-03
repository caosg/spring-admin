/**
* 左侧导航菜单
*/
Ext.define('AM.view.sys.Left', {
    extend: 'Ext.panel.Panel',  
    alias : 'widget.systemnavi',   
    layout: 'fit',
    id:'leftNavi',
    region : 'west',
    split : true,
    title: '导航菜单',
    margins:'0 0 0 5',
    collapsible: true,
	width : 200,
	minWidth : 130,
	maxWidth : 300,
	curNavi:'Tree',
    initComponent: function() {
        var me=this;   
        var naviCookie = Ext.util.Cookies.get("NaviName"); 
        var naviPanel;       
        if(naviCookie&&naviCookie=='Accordion'){
           
           naviPanel=Ext.create('AM.view.sys.AcoordionNavi'); 
           me.curNavi='Accordion';
        } 
        else
           naviPanel=Ext.create('AM.view.sys.TreeNavi');        
        Ext.applyIf(this, {items:naviPanel});
        this.callParent();
    },
    //切换导航样式
    transNaviStyle:function(navi){
       if(navi==this.curNavi)
           return;
       
       var naviItem;
       if(navi=='Accordion')
          naviItem=Ext.create('AM.view.sys.AcoordionNavi');
       else
          naviItem=Ext.create('AM.view.sys.TreeNavi');
       this.removeAll(true);
       this.add(naviItem);
       this.curNavi=navi;
    }
   
});