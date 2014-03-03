Ext.define('AM.model.config.WareHouseM', {
    extend: 'Ext.data.Model',
    fields: ['id', 'areaid',
             {name:'code',mapping:'whcode'}, 
             {name:'name',mapping:'whname'},
             'status','wharea','outercode'],
    idProperty : "id"
});