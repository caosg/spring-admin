Ext.define('AM.store.sys.Status', {
			extend : 'Ext.data.Store',
			fields: ['key', 'value'],
			data : [{
						key : '',
						value : '全部'
					}, {
						key : '0',
						value : '启用'
					}, {
						key : '1',
						value : '禁用'
					},{
						key : '2',
						value : '已删除'
					}
					],
			autoLoad : true
		});