Ext.define('AM.view.sys.PortletPanel', {
    extend: 'AM.view.portal.PortalPanel',    
    
    alias: 'widget.portletpanel',
    title:'首页',
    uses: ['AM.data.Constants'],

    getTools: function(){
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, panelHeader, tool){
                var portlet = panelHeader.ownerCt;
                portlet.setLoading('Loading...');
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    },
    
    initComponent: function() {
        
        Ext.apply(this, {

            items: [{
                id: 'col-1',
                items: [{
                    id: 'portlet-1',
                    title: '系统信息',
                    height:260,
                    tools: this.getTools(),
                    html: AM.data.Constants.sysMarkup
                    },
                   {
                    id: 'portlet-3',
                    title: '访问统计',
                    tools: this.getTools(),
                    items: Ext.create('AM.view.portlet.VisitsPortlet')
                }]
              },{
                id: 'col-2',
                items: [
                    {
                    id: 'portlet-2',
                    title: '实时概况',
                    height:260,
                    tools: this.getTools(),
                    html: AM.data.Constants.userMarkup
                    },
                    {
                    id: 'portlet-5',
                    title: '点击数统计',
                    tools: this.getTools(),
                    items: Ext.create('AM.view.portlet.HitsPortlet')
                   }
                	]
            },{
                id: 'col-3',
                items: [{
                    id: 'portlet-4',
                    title: '点击数TOP-25',
                    tools: this.getTools(),
                    items: Ext.create('AM.view.portlet.OperationRank')
                }]
            }]
            
        });
                
        this.callParent(arguments);
    }
});
