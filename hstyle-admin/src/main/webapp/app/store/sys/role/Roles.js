Ext.define('AM.store.sys.role.Roles', {
			extend : 'Ext.data.Store',
			model : 'AM.model.sys.role.Role',
			autoLoad : true,
			batchActions : false,
			remoteFilter : true,
			remoteSort : true,
			loadMask:true,
			pageSize : 25,
			proxy : {
				type : "ajax",
				api : {					
					read : 'role/list.do'

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
						filter_LIKE_S_name:Ext.getCmp('queryRoleName').getValue()
					};
				}
			}
		});