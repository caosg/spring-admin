Ext.define('AM.model.portlet.ChartStock', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'name'},
        {name: 'sp500',     type: 'float'},
        {name: 'djia',  type: 'float'}
    ]
});
