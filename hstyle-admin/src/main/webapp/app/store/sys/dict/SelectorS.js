/**
 * 数据字典下拉控件远程获取数据store
 */
Ext.define("AM.store.sys.dict.SelectorS",{
    extend: 'Ext.data.Store',
    storeId:'dicSelectStore',
    fields:['value','name'],
    autoLoad:false,
    dictType:'',
    sorters: [{
        property: 'id',
        direction: 'ASC'
    }],
   
    proxy: {
        type: "ajax",
        url: "dict/findDictionaryByCode.do",
        reader: {
            type: 'json',
            root: 'data'

        }
    },
    listeners : {
		beforeload : function(store, options) {
			var me = this;
			var p_code = me.dictType;
			if(p_code==null||p_code==undefined||p_code=='')
				return ;
			this.proxy.extraParams = {
					code: p_code
			};
		}
    }    
});