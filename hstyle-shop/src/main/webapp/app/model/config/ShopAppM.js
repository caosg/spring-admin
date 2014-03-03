Ext.define('AM.model.config.ShopAppM', {
    extend: 'Ext.data.Model',
    fields: ['id', 'shopid','status','appkey','appsecrect','appsession','tokensession','platformShop'],
    idProperty : "id"
});