Ext.define('AM.store.portlet.OperationRank', {
			extend : 'Ext.data.Store',
			requires : 'AM.model.portlet.OperationRank',
			model : 'AM.model.portlet.OperationRank',
            autoLoad:true,
			loadMask:true,
			proxy : {
				type : "ajax",
				api : {				
					read : 'operl/statoperation.do'	
				},				
				reader : {
					type : 'json',
					root : 'data'
				},
				listeners : {
			       exception : AM.proxyException
		       }

			}
			
		});