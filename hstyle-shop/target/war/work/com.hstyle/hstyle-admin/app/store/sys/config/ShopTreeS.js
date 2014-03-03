/**
 *店铺平台信息
 */
Ext.define('AM.store.sys.config.ShopTreeS', {
			extend : 'Ext.data.TreeStore',
			alias : 'widget.ShopTreeS',
			model : 'AM.model.sys.config.ShopTreeM',
			autoLoad:true,
			root : {
	        	//text: '店铺平台信息',
				expanded : true,
				children : [ {
					text : "淘宝平台",
					code:'taobao',
					id:'taobao',
					leaf : false,
					children : [ {
						text : "韩都衣舍品牌直营店",
						code:'tb_hstylezy',
						leaf : true
					}, {
						text : "AMH官方旗舰店",
						code:'tb_hstyleamh',
						leaf : true
					}, {
						text : "blackqueen旗舰店",
						code:'tb_blackqueenqj',
						leaf : true
					}, {
						text : "jeanspoint旗舰店",
						code:'tb_jeanspointqj',
						leaf : true
					}, {
						text : "minizaru童装旗舰店",
						code:'tb_minizaruqj',
						leaf : true
					}, {
						text : "minizaru童装直营店",
						code:'tb_minizaruzy',
						leaf : true
					}, {
						text : "xiaoyan_1127",
						code:'tb_hstylexy',
						leaf : true
					}, {
						text : "韩都衣舍旗舰店",
						code:'tb_hstyleqj',
						leaf : true
					}, {
						text : "soneed旗舰店",
						code:'tb_soneedqj',
						leaf : true
					}, {
						text : "素缕旗舰店",
						code:'tb_soulineqj',
						leaf : true
					}, {
						text : "bo薄荷",
						code:'tb_soulinebh',
						leaf : true
					},
					{
						text:'韩都衣舍outlet旗舰店',
						code:'tb_outletqjd',
						leaf:true
					},
					{
						text:'nibbuns官方旗舰店',
						code:'tb_nibbunsqj',
						leaf:true
					},
					{
						text:'韩韵衣香旗舰店',
						code:'tb_hyxyqjd',
						leaf:true
					},
					{
						text:'分销平台（外）',
						code:'tb_hstylefxw',
						leaf:true
					}]
				} ,{
					text : "京东平台",
					code:'jingdong',
					id:'jingdong',
					leaf : false,
					children : [ {
						text : "韩都衣舍京东旗舰店",
						code:'jd_hstyleqj',
						leaf : true
					}, {
						text : "AMH京东旗舰店",
						code:'jd_amhqj',
						leaf : true
					}, {
						text : "京东韩都衣舍饰品旗舰店",
						code:'jd_hstylespqj',
						leaf : true
					}, {
						text : "素缕京东",
						code:'jd_slqjd',
						leaf : true
					}, {
						text : "京东minurazu童装旗舰店",
						code:'jd_minurazuqj',
						leaf : true
					}]
				
				},{
					text : "拍拍",
					code:'paipai',
					id:'paipai',
					leaf : false,
					children : [ {
						text : "拍拍网韩都衣舍",
						code:'pp_hstyleqj',
						leaf : true
					}, {
						text : "拍拍网AMH男装店铺",
						code:'pp_amhqj',
						leaf : true
					}, {
						text : "素缕拍拍女装官方旗舰店",
						code:'pp_slqjd',
						leaf : true
					}, {
						text : "拍拍网韩都衣舍专营店",
						code:'pp_hstylezy',
						leaf : true
					}, {
						text : "拍拍网minizaru官方旗舰店",
						code:'pp_minizaruqj',
						leaf : true
					}, {
						text : "拍拍网jeanspoint官方旗舰店",
						code:'pp_jeanspointqj',
						leaf : true
					}]
				
				}]
			}
			
		});