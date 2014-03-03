Ext.define('AM.model.portlet.Hits', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'stat_time',type:'string'},
        {name: 'hits',type: 'int'}
    ]
});