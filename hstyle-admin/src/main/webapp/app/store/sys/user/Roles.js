Ext.define('AM.store.sys.user.Roles', {
			extend : 'Ext.data.Store',
			model : 'AM.model.sys.role.Role',
			autoLoad : true,
			batchActions : false,
			remoteFilter : true,
			remoteSort : true,
			loadMask:true,
            // allow the grid to interact with the paging scroller by buffering
            buffered: true,
           // Configure the store with a single page of records which will be cached
            pageSize: 5000,
            userId:[],
			proxy : {
				type : "ajax",
				api : {					
					read : 'user/roles.do'

				},				
				reader : {
					type : 'json',
					root : 'data'				
				},
				listeners : {
			       exception : AM.proxyException
		       }

			},
			listeners : {
				// 添加查询参数
				beforeload : function(store, options) {
					var me=this;
					var userId=me.userId;
					if(userId==null||userId.length==0)
					return false;
					this.proxy.extraParams = {
						userId:userId
					};
				}
			}
		});