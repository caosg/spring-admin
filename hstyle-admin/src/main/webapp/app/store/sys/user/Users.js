Ext.define('AM.store.sys.user.Users', {
			extend : 'Ext.data.Store',
			model : 'AM.model.sys.user.User',
			alias : 'widget.userstore',
			autoLoad : false,
			batchActions : false,
			remoteFilter : true,
			remoteSort : true,
			loadMask:true,
			pageSize : 25,
			proxy : {
				type : "ajax",
				api : {
					
					read : 'dept/queryuser.do'
					

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
						deptCode:Ext.getCmp('deptCode').getValue(),
						filter_LIKE_S_userName:Ext.getCmp('queryUserName').getValue()
					};
				}
			}
		});