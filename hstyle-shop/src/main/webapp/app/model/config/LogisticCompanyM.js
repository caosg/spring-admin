Ext.define('AM.model.config.LogisticCompanyM', {
    extend: 'Ext.data.Model',
    fields: ['id', 
             {name:'code',mapping:'companycode'},
             {name:'name',mapping:'companyname'},
             'status','outercode'],
             idProperty : "id"
});