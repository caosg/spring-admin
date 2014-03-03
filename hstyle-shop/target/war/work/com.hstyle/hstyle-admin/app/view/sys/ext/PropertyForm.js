Ext.define('AM.view.sys.ext.PropertyForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.propertyform',

    id: 'propertyform',    
    requires: [
        'AM.model.sys.ext.PropertyMeta',
        'AM.store.sys.ext.PropertyMetaS'
    ],
    defaults : {
		anchor : '100%'
	},
	bodyPadding: '5 5 0',
    defaultType: 'textfield',
    url: 'dynaform.do',
    initComponent: function() {
        var self = this;          
        var proMetaStore = Ext.create('AM.store.sys.ext.PropertyMetaS');

        var formFields = [];        
        Ext.each(proMetaStore.data.items, function(record){
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
       self.buttons= [{
            text: 'Save',
            handler: function() {
                var form = this.up('form').getForm();
                if (form.isValid()) {                  
                  var properties=self.getJsonFieldValues();               
                  form.submit({
                    params:{properties:Ext.JSON.encode(properties)},
                    success: function(form, action) {
                       Ext.Msg.alert('Success', action.result.data);
                    },
                    failure: function(form, action) {
                        Ext.Msg.alert('Failed', action.result.data);
                    }
                  });
               }
            }
        },{
            text: 'Cancel',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }];
        Ext.applyIf(self, {
					items : formFields
				});
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
           emptyText:record.data.fieldEmptyText
        };
        if(record.data.fieldType == 'emailfield')
          config.vtype='email';
        if(record.data.fieldType == 'passwordfield'){
          config.vtype='alphanum';
          config.inputType="password";
        }
        if(record.data.required){
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
           emptyText:record.data.fieldEmptyText
        };
        if(record.data.required){
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
           emptyText:record.data.fieldEmptyText
        };
        if(record.data.required){
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
                    store: selectStore,
                    queryMode: 'local',
                    emptyText:record.data.fieldEmptyText,
                    typeAhead: true
         };
         if(record.data.required){
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
                var innerData = splitArray[i].split(':');               
                itmes.push({boxLabel:innerData[1],name:record.data.fieldName,inputValue:innerData[0]});
            }
            
        }
        
        var config={
                    itemId:record.data.fieldId,
                    name:record.data.fieldName,
                    fieldLabel: record.data.fieldLabel,                    
                    columns: 3,
                    vertical: true,
                    items:itmes
         };
         if(record.data.required){
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
                itmes.push({boxLabel:innerData[1],name:record.data.fieldName,inputValue:innerData[0]});
            }
            
        }
        
        var config={
                    itemId:record.data.fieldId,
                    name:record.data.fieldName,
                    fieldLabel: record.data.fieldLabel,                    
                    columns: 3,
                    vertical: true,
                    items:itmes
         };
         if(record.data.required){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
         return Ext.widget('checkboxgroup', config);
    },
    //日期选择框
    getDateField: function(record) {
        
        var config={
           itemId:record.data.fieldId,          
           name:record.data.fieldName,
           fieldLabel:record.data.fieldLabel,
           emptyText:record.data.fieldEmptyText,
           format: 'Y-m-d',
        };
        if(record.data.required){
          config.afterLabelTextTpl=required;
          config.allowBlank=false;
        }
       
        return Ext.widget("datefield",config);

    }

});