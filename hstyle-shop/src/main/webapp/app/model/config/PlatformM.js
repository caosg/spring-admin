Ext.define('AM.model.config.PlatformM', {
    extend: 'Ext.data.Model',
    fields: ['id', 
             {name:'code',mapping:'pltfomcode'},
             {name:'name',mapping:'pltfomname'},
             'status','shops','outercode'],
             idProperty : "id"
});