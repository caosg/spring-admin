Ext.define('AM.model.portlet.Visits', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'stat_time',type:'string'},
        {name: 'sessions',type: 'int'},
        {name: 'users',  type: 'int'}
    ]
});
