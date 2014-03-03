Ext.define('AM.store.sys.ResourceType', {
			extend : 'Ext.data.Store',
			fields: ['key', 'value'],
			data : [{
						key : '',
						value : '全部'
					}, {
						key : '1',
						value : '菜单'
					}, {
						key : '2',
						value : '功能'
					}],
			autoLoad : true
		});