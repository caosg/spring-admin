Ext.define('AM.model.config.WareHouseAreaM', {
    extend: 'Ext.data.Model',
    fields: ['id', 
             {name:'code',mapping:'whareacode'}, 
             {name:'name',mapping:'whareaname'}, 
             'status','warehouses','outercode'],
    idProperty : "id"
});