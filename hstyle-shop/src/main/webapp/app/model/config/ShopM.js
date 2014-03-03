Ext.define('AM.model.config.ShopM', {
    extend: 'Ext.data.Model',
    fields: ['id', 'parentid','platformid',
             //{name:'platform_id',mapping:'platform.id'},
             {name:'code',mapping:'shopcode'},
             {name:'name',mapping:'shopname'},
             'status','platform','outercode'],
    idProperty : "id"
});