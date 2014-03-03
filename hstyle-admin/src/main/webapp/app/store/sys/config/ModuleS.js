 Ext.define('AM.store.sys.config.ModuleS', {
	 extend : 'Ext.data.Store',
     model: 'AM.model.sys.config.ModuleM',
     data : [
             /* {name:'订单', code:'order', description:'订单系统',type:'SHOP'},//0:公共级  1:私有
         		{name:'商品', code:'product', description:'商品资料',type:'SYS'},
         		{name:'123', code:'456', description:'789',type:'SHOP'}*/
        	{name:'订单审核设置', code:'ORDER', description:'订单审核设置',type:'SYS'},//0:公共级  1:私有
        	{name:'配货设置', code:'DISPATCH', description:'配货设置',type:'SYS'},//0:公共级  1:私有
        	{name:'退换货单设置', code:'RETURNORCHANGE', description:'退换货单设置',type:'SYS'},
        	{name:'短信设置', code:'MESSAGE', description:'短信设置',type:'SYS'},
        	{name:'店铺设置', code:'SHOP', description:'店铺设置',type:'SHOP'}
     ]
 });
 
 
