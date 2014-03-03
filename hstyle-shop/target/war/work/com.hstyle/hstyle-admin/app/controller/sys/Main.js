/**
 * 加载框架
 */
Ext.define('AM.controller.sys.Main', {// 定义类
	extend : 'Ext.app.Controller',// 一定要继承Controller
	requires:['AM.data.*'],
	// 添加views，让控制器找到
	views : ['sys.Top','sys.Left', 'sys.Content', 'sys.Footer'],
	models : ['sys.Menu'],
	stores : [],
	refs : [{
				ref : 'sysNavigation',
				selector : 'systemnavi'
			},
			 {
				ref : 'contentTab',
				selector : 'content'
			}],
	init : function() {

		this.control({
					'systemmenu' : {
						itemmousedown : this.loadMenu
					},
					'systemnavi treepanel':{
					  itemclick:this.loadMenu
					}
				});
	},
	// 单击菜单，在右侧Tab页打开具体内容
	loadMenu : function(selModel, record) {

		if (record.get('leaf')) {
			var me = this, key = record.get('code'), cmp = Ext
					.getCmp("contentPanel").getComponent(key + 'View');
			if (cmp) {
				// console.log('The dept tab was exist');
				me.getContentTab().setActiveTab(cmp);

			} else {
				try{
				
				 var controller = this.getController(key);
				 var view = controller.getMainView();
				 me.openTab(view, key + 'View');
				}catch(e){
					
					MsgBoxErr('系统错误',function(){
						console.log(e);
						  //window.location = "logout.do";
						}
					);
					
				}
				
			}

		}

	},
	//打开tab页
	openTab : function(panel, id) {
		var o = (typeof panel == "string" ? panel : id || panel.id);
		var main = Ext.getCmp("contentPanel");
		var tab = main.getComponent(o);
		if (tab) {
			main.setActiveTab(tab);
		} else if (typeof panel != "string") {

			var p = main.add(panel);
			main.setActiveTab(p);
			//根据权限控制按钮的启用或禁用
			var buttons=p.query('button');
			Ext.Array.each(buttons,function(button){
			      var buttonId=button.id;
			      if(!buttonId) return;
			      //button的id以perms-开头需要权限控制，否则不需要权限控制
			      var isPermsButton=buttonId.indexOf("perms-");
			      
			      /*if(button.xtype=='tab')
			        return;
				  //分页按钮不控制
				  if(button.up('pagingtoolbar')){				  	
				  	return;
				  }*/
				  if(isPermsButton==0)
				     AM.disabledOpt(button);
				});
		}
	}

});