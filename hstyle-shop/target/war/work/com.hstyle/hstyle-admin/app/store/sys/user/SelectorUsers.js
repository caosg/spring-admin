Ext.define('AM.store.sys.user.SelectorUsers', {
			extend : 'Ext.data.Store',
			model : 'AM.model.sys.user.User',
			alias : 'widget.selectoruserstore',
			autoLoad : false,
			batchActions : false,
			remoteFilter : true,
			remoteSort : true,
			loadMask:true,
			pageSize : 25,
			proxy : {
				type : "ajax",
				api : {
					
					read : 'dept/getuser.do'
					

				},
				
				reader : {
					type : 'json',
					root : 'result',
					totalProperty : 'totalItems'
				},
				listeners : {
				       exception : AM.proxyException
			       }

			},
			listeners : {
				// 分页添加查询参数
				beforeload : function(store, options) {
					this.proxy.extraParams = {
						deptCode:Ext.getCmp('selectorDeptCode').getValue(),
						filter_LIKE_S_userName:Ext.getCmp('selectorUserName').getValue()
					};
				}
			}
		});