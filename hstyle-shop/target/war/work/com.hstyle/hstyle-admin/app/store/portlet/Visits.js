Ext.define('AM.store.portlet.Visits', {
			extend : 'Ext.data.Store',
			requires : 'AM.model.portlet.Visits',
			model : 'AM.model.portlet.Visits',
            autoLoad:true,
			loadMask:true,
			proxy : {
				type : "ajax",
				api : {				
					read : 'operl/statvisit.do'	
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