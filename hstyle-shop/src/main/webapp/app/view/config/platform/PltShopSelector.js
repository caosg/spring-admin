/**
 * 商品公共查询界面
 */
Ext.define("AM.view.config.platform.PltShopSelector", {
	extend : 'Ext.panel.Panel',
	id : 'pltShopSelector',
	alias : 'widget.pltShopSelector',
	layout : 'border',
	border : 0,
	callback:[],
	initComponent : function() {
		var me = this;
		me.pltStore = Ext.create('AM.store.config.PlatformS');
		me.shopStore = Ext.create('AM.store.config.ShopS');
//    	me.brandStore = Ext.create('AM.store.sys.dict.SelectorS',{dictType:'SCM_PRODUCT_BRAND'}).load();
		me.pltGrid = Ext.widget('grid',{//左侧平台信息
			title:'平台信息',
			id:'compltGrid',
			region:'center',
			store:me.pltStore,
			loadMask: true,
		    viewConfig : {
			    loadMask : new Ext.LoadMask(this, {
						msg : '正在努力的为您加载......'
					})
		    },
		    tbar:[
					{
						xtype:'textfield',
						id:'comqueryByPlt',
						emptyText:'输入平台名称',
						width:96,
						scope:me,
						enableKeyEvents:true,
						listeners : {
							keydown:function(t, e, eOpts ){
								if(13 == e.getKey()){
									me.pltStore.load({
										action:'read',
										params : {
											page : 1,
											start:0,
											limit:25
										} 
									});
								}
							}
						}
					},{xtype: 'tbspacer',width:2},
					{   
						id:'perms-complt-query',iconCls : "search",
						xtype:'button',text:'查询',scope:me,
						handler:function(){
							me.pltStore.load({
								action:'read',
								params : {
									page : 1,
									start:0,
									limit:25
								} 
							});
						}
					},'->',
					{
						iconCls : 'accept',
						text:'确定',
						scope : me,
						tooltip : '选择店铺的信息',
						id : "comSelectshopinfo",
						disabled : true,
						handler:me.selectShops
					}
		          ],
		    columns:[
						{
							header : 'ID',
							dataIndex : 'id',
							flex : 1,
							hidden : true
						},
						{
							header : '代码',
							dataIndex : 'code',
							width : 100
						},
						{
							header : '名称',
							dataIndex : 'name',
							width : 200
						},
						{
							header : '外部代码',
							dataIndex : 'outercode',
							width : 80,
							scope:me
						}
		    ],
		    dockedItems : [{
				xtype : 'pagingtoolbar',
				id : 'comPltPageBar',
				dock : 'bottom',
				store : me.pltStore,
				displayInfo : true
			}],       
		    listeners:{
		    	itemclick:function(sku,record){//单击时加载该货号的规格信息
		    		me.shopStore.on('beforeload',function(store,options){
		    			this.proxy.extraParams = {
		    					parentid:record.get('id')	
		    			};
		    		});
		    		me.shopStore.load();
		    	}
		    }
		});
		
        
		me.shopGrid = Ext.widget("grid",{//右侧店铺信息，点击平台信息时加载
			title : "店铺信息",
		    id : 'comShopGrid',
		    store : me.shopStore,
		    region:'east',
		    anchor:'60%',
		    loadMask: true,
		    viewConfig : {
			    loadMask : new Ext.LoadMask(this, {
						msg : '正在努力的为您加载......'
					})
		    },
		    columns : [
						{
							header : '店铺代码',
							dataIndex : 'code',
							width:100
						},
						{
							header : '店铺名称',
							dataIndex : 'name',
							width : 105
						},
						{
							header : '外部代码',
							dataIndex : 'outercode',
							width : 95
						}
		      ],
		      listeners:{
		    	  'selectionchange': function(view, records) {
	                	Ext.getCmp('comSelectshopinfo').setDisabled(!records.length);
			    	}
		      }
		});
		
		me.items = [me.pltGrid, me.shopGrid];
		me.pltStore.on('beforeload',function(store,options){
			this.proxy.extraParams={
					filter_RLIKE_S_pltfomname:Ext.getCmp('comqueryByPlt').getValue()
			};
		});
		me.pltStore.load();
		me.callParent(arguments);
	},
	
	selectShops:function(){//选择店铺信息 
		var me = this;
		var pltGrid = Ext.getCmp('compltGrid');
		var shopGrid = Ext.getCmp('comShopGrid');
		pltsl = pltGrid.getSelectionModel().getSelection();
		shopsl = shopGrid.getSelectionModel().getSelection();
		if(pltsl.length != 1){
			Ext.Msg.alert("温馨提示","请选择一条平台信息!");
			return;
		}
		var shops=[];
		shops.push(pltsl[0]);
		shops.push(shopsl[0]);
		me.callback(shops);
        me.up('window').close();
	}
	
//	brandRender:function(v){
//		var me = this;
//		var recored = me.brandStore.findRecord('value',v);
//		if(recored==null)  //当store中找不到对应id得时候,index为-1 
//	        return '未知';  
//	    else{  
//	        var name = recored.get('name');   
//	        return name;   
//	    }
//	}
});