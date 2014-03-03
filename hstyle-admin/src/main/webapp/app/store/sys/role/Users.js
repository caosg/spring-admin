Ext.define('AM.store.sys.role.Users', {
			extend : 'Ext.data.Store',
			model : 'AM.model.sys.user.User',
			autoLoad : true,
			batchActions : false,
			remoteFilter : true,
			remoteSort : true,
			loadMask:true,
            // allow the grid to interact with the paging scroller by buffering
            buffered: true,
           // Configure the store with a single page of records which will be cached
            pageSize: 5000,
            roleId:[],
			proxy : {
				type : "ajax",
				api : {					
					read : 'role/users.do'

				},				
				reader : {
					type : 'json',
					root : 'data'				
				}

			},
			listeners : {
				// 添加查询参数
				beforeload : function(store, options) {
					var me=this;
					var roleId=me.roleId;
					if(roleId==null||roleId.length==0)
					return false;
					this.proxy.extraParams = {
						roleId:roleId
					};
				}
			}
		});