Ext.define('AM.view.sys.config.ModuleForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.moduleForm',
    defaults : {
		anchor : '40%'
	},
	region:'center',
	 flex:1,
	bodyPadding: '10 10 0',
    defaultType: 'textfield',
    moduleId:'',
    shopcode:'',
    requires: [
               'AM.model.sys.config.ModuleMappingM',
               'AM.store.sys.config.ModuleMappingS'
           ],
    autoHeight:true,
    frame:true,
    buttonAlign:'left',
    initComponent: function() {
        var self = this;      
        var formFields = [];
      
        var formStore=Ext.create('AM.store.sys.config.ModuleMappingS');
        formStore.on('beforeload', function (store, options) {
          	 this.proxy.extraParams = {
          			module:self.moduleId,
          			busicode:'SYS'
          			
               };
          });
        
        formStore.on('load', function (store, options) {
              Ext.each(formStore.data.items, function(record){
                  if((record.data.fieldType == 'textfield') || (record.data.fieldType == 'passwordfield') || (record.data.fieldType == 'emailfield')){
                      var fieldObj = self.getTextField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'textareafield'){
                     var fieldObj = self.getTextAreaField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'selectfield'){
                      var fieldObj = self.getSelectField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'radiofield'){
                     var fieldObj = self.getRadioField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'checkboxfield'){
                     var fieldObj = self.getCheckBoxField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'numberfield'){
                     var fieldObj = self.getNumberField(record);
                      formFields.push(fieldObj);
                  }
                  else if(record.data.fieldType == 'datefield'){
                     var fieldObj = self.getDateField(record);
                      formFields.push(fieldObj);
                  }
                 
              });
              self.add(formFields);
		});
        formStore.load();
      
        	self.buttons= [
	           {
	               text: '保存',
	               action:'perms-ModuleFormAdd'
	           },
	           {
	               text: '取消',
	               handler: function() {
	                   this.up('form').getForm().reset();
	               }
	           }
           ];
        
        this.callParent(arguments);
    },
    //获取form下field的值组成json数据
    getJsonFieldValues:function(){
       var properties=[];
       var form = this.getForm();
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
    },
    //简单文本输入框
    getTextField: function(record) {
        
        var config={
           itemId:record.data.fieldId,
           name:record.data.fieldName,
           fieldLabel:record.data.fieldLabel,
           value: record.data.value,
           maxLength:100,
           labelWidth:record.data.fieldName.length>8?150:100,
           flex:1,
          emptyText:record.data.fieldEmptyText
        };
        if(record.data.fieldType == 'emailfield')
          config.vtype='email';
        if(record.data.fieldType == 'passwordfield'){
          config.vtype='alphanum';
          config.inputType="password";
        }
        if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
        return Ext.widget("textfield",config);

    },
     //富文本输入框
    getTextAreaField: function(record) {
        
        var config={
           itemId:record.data.fieldId,
           name:record.data.fieldName,
           fieldLabel:record.data.fieldLabel,
           grow: true,
           maxLength:300,
           flex:1,
           labelWidth:record.data.fieldName.length>8?150:100,
           value: record.data.value,
           emptyText:record.data.fieldEmptyText
        };
        if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
       
        return Ext.widget("textareafield",config);

    },
    //数值输入框
    getNumberField:function(record) {
        
        var config={
           itemId:record.data.fieldId,
           name:record.data.fieldName,
           fieldLabel:record.data.fieldLabel,
           value: record.data.value,
           maxValue:10000000,
           flex:1,
           labelWidth:record.data.fieldName.length>8?150:100,
          
           emptyText:record.data.fieldEmptyText
        };
        if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
       
        return Ext.widget("numberfield",config);

    },
    //下拉框
    getSelectField: function(record) {
        var selectStore=Ext.create('AM.store.sys.ext.Options');
        if(record.data.fieldOptions !== ''){
            var data = record.data.fieldOptions;
            var splitArray = data.split(',');            
            for(var i = 0; i < splitArray.length; i++)
            {
                var innerData = splitArray[i].split(':');               
                var option=Ext.create('AM.model.sys.ext.Option',{key:innerData[0],value:innerData[1]});
                selectStore.add(option);
            }
            
        }
        // Simple ComboBox using the data store
        var config={
                    itemId:record.data.fieldId,
                    name:record.data.fieldName,
                    fieldLabel: record.data.fieldLabel,
                    displayField: 'value',
                    valueField:'key',
                    editable:false,
                    store: selectStore,
                    value: record.data.value,
                    queryMode: 'local',
                    flex:1,
                    labelWidth:100,
                    emptyText:record.data.fieldEmptyText,
                    typeAhead: true
         };
        if(config.name.length>8){
        	config.labelWidth=150;
        }
         if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
         return Ext.widget('combobox', config);
    },
    //生成单选按钮组
    getRadioField: function(record) {
       var itmes=[];
        if(record.data.fieldOptions !== ''){
            var data = record.data.fieldOptions;
            var splitArray = data.split(',');            
            for(var i = 0; i < splitArray.length; i++)
            {
            	var flag=false;
                var innerData = splitArray[i].split(':');
              
                if(record.data.value!=""){
                	  var valueArr=record.data.value.split(',');
                	  for(var j=0;j<valueArr.length;j++){
                		  var v = valueArr[j];
                		  if(innerData[0]==v){
                			  flag=true;
                		  }
						 
                	  }
                }
                itmes.push({boxLabel:innerData[1],name:record.data.fieldName,inputValue:innerData[0],itemCode:innerData[0],checked :flag});
            }
            
        }
        
        var config={
                    itemId:record.data.fieldId,
                    name:record.data.fieldName,
                    fieldLabel: record.data.fieldLabel,                    
                    columns: 3,
                    vertical: true,
                    flex:1,
                    labelWidth:100,
                    items:itmes
         };
        if(config.name.length>8){
        	config.labelWidth=150;
        }
         if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
         return Ext.widget('radiogroup', config);
    },
    //生成复选钮组
    getCheckBoxField: function(record) {
       var itmes=[];
        if(record.data.fieldOptions !== ''){
            var data = record.data.fieldOptions;
            var splitArray = data.split(',');            
            for(var i = 0; i < splitArray.length; i++)
            {
                var innerData = splitArray[i].split(':'); 
                var flag=false;
                if(record.data.value!=""){
              	  var valueArr=record.data.value.split(',');
              	  for(var j=0;j<valueArr.length;j++){
              		  var v = valueArr[j];
              		  if(innerData[0]==v){
              			  flag=true;
              		  }
						 
              	  }
              }
                itmes.push({boxLabel:innerData[1],name:record.data.fieldName,inputValue:innerData[0],itemCode:innerData[0],checked:flag});
            }
            
        }
        
        var config={
                    itemId:record.data.fieldId,
                    name:record.data.fieldName,
                    fieldLabel: record.data.fieldLabel,                    
                    columns: 3,
                    vertical: true,
                    flex:1,
                    labelWidth:100,
                    items:itmes
         };
        if(config.name.length>8){
        	config.labelWidth=150;
        }
         if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
         return Ext.widget('checkboxgroup', config);
    },
    //日期选择框
    getDateField: function(record) {
    	var a=record.data.value.replace('T',' ');
    	var date=new Date();
    	if(a!=""){
    		date=new Date(a.replace(/-/g,"/"))
    	}
        var config={
           itemId:record.data.fieldId,          
           name:record.data.fieldName,
           value:date,
           flex:1,
           labelWidth:100,
           fieldLabel:record.data.fieldLabel,
           emptyText:record.data.fieldEmptyText,
           format: 'Y-m-d'
        };
        if(config.name.length>8){
        	config.labelWidth=150;
        }
        if(record.data.required=='Y'){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
        return Ext.widget("datefield",config);
    }

});