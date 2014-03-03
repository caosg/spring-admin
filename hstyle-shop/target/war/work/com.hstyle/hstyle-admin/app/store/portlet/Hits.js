Ext.define('AM.store.portlet.Hits', {
			extend : 'Ext.data.Store',
			requires : 'AM.model.portlet.Hits',
			model : 'AM.model.portlet.Hits',
            autoLoad:true,
			loadMask:true,
			proxy : {
				type : "ajax",
				api : {				
					read : 'operl/stathits.do'	
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