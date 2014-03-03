Ext.define('AM.controller.sys.config.ModuleConfig', {
    extend: 'Ext.app.Controller',
    stores: ['sys.config.ModuleS','sys.config.ShopTreeS'],//
    models: ['sys.config.ModuleM','sys.config.ShopTreeM'],//
    views: ['sys.config.ModuleConfigV'],
    refs: [
           {ref : 'moduleConfigV', selector : 'moduleConfigV' }
       ],
    init: function() {
    	var me=this;
    	me.control({
			'moduleForm button[action=perms-ModuleFormAdd]':{
				click:me.updateModuleConfig
			}
			
		});
    },
    //返回主视图
    getMainView : function() {
		return this.getSysConfigModuleConfigVView();
	},
	updateModuleConfig:function(){
		var me=this;
		var tabpanel=Ext.getCmp('sys.config.ModuleConfigView');
		var activepanel=tabpanel.getActiveTab( );//获取当前打开的页签
		var form=activepanel.down('form').getForm();
		var shopcode=activepanel.down('form').shopcode;
		
		
		var propertyValueArr=me.getJsonFieldValues(form);
		if(form.isValid()){
			Ext.Ajax.request({
				url:'moduleProperty/updateConfig.do',
				method:'post',
				params:{
					properties:Ext.JSON.encode(propertyValueArr),
					busicode:'SYS',
					moduleType:'SYS'
				},
				success:function(response){
					var results=Ext.decode(response.responseText);
					Ext.Msg.alert("温馨提示",results.data,function(){
						
					});
				},
				failure:function(response){
					var results=Ext.decode(response.responseText);
					Ext.Msg.alert("温馨提示",results.data);
				}
			});
		}
	},
	getJsonFieldValues:function(form){
	       var properties=[];
	       form.getFields().each(function(field){    
	          if(field.xtype=='radiofield'||field.xtype=='checkboxfield')
	               return;
	          if(field.xtype=='radiogroup'||field.xtype=='checkboxgroup'){ 
	               var values='';
	               Ext.each(field.getChecked(),function(check){
	                         values+=check.inputValue+',';
	                      });
	               if(values.length>0)
	                      values=values.substring(0,values.length-1);
	               properties.push({id:field.itemId,value:values});
	           }
	           else      
	               properties.push({id:field.itemId,value:field.getValue()});
	        });
	        return properties;
	    }
  
});