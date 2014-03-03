Ext.define('AM.view.sys.user.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.userEditWin',
    requires: ['Ext.form.Panel'],
    title : 'Edit User',
    id:'usereditwin',
    layout: {
        align: 'stretch',
        type: 'vbox'
    },
    autoShow: true,
    width: 400,
    height:360,
    modal:true,
    initComponent: function() {
    	var me=this;
	    var statustore=Ext.create('AM.store.sys.Status');
	    var positionStore=Ext.create('AM.store.sys.dict.SelectorS', {storeId:'positionStore',dictType : 'POSITION'}).load();
	    me.deptStore = Ext.create('Ext.data.Store', {
                           model:'AM.model.sys.dept.Dept',
                           data: [],           
                           sorters : [{
						     property : 'code',
						     direction : 'ASC'
					        }]
                         });
	    me.deptGrid=Ext.widget('gridpanel',{
	                flex: 1,
                    title: '兼职部门',
                    store:me.deptStore,
                    columns: [
                        {
                            xtype: 'rownumberer',                       
                            text: '序号',
                            width:40,align:'center'
                        },
                        {
                            
                            dataIndex: 'name',
                            text: '部门名称',
                            flex:1 
                        },
                        {
                            dataIndex: 'code',
                            text: '部门代码',
                            flex:1 
                        }
                    ],
             
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'right',
                            items: [
                                {  
                                    xtype: 'button',
                                    iconCls:'add',
                                    tooltip : '添加部门',
                                    handler:me.selectDept,
                                    scope:me
                                    
                                },'-',
                                {
                                    xtype: 'button',
                                    iconCls:'delete',                                    
                                    tooltip : '移除部门', 
                                    handler:me.removeDept,
                                    disabled:true
                                }
                            ]
                        }
                    ],
                    listeners:{
                    	selectionchange:me.rowSelected
                    }
	    });
        this.items = [
            {
                xtype: 'form',
                
                padding: '5 5 0 5',
                border: false,
                style: 'background-color: #fff;',
                layout : 'anchor',
                defaults : {
								anchor : '100%',
								labelWidth:80
							},
                defaultType:'textfield',
                model:'user.User',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'loginName',
                        fieldLabel: '登录账号',
                        afterLabelTextTpl: required,
                        vtype:'alphanum',
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:3,minLengthText:'长度不能小于3个字符',
                        maxLength:32,maxLengthText:'长度不能大于32个字符'
                    },
                    {
                        xtype: 'textfield',
                        name : 'userName',
                        fieldLabel: '姓名',
                        afterLabelTextTpl: required,
                        allowBlank:false,blankText:'本字段不能为空',
                        minLength:2,minLengthText:'长度不能小于2个字符',
                        maxLength:32,maxLengthText:'长度不能大于32个字符'
                    },{
                        xtype: 'combobox',
                        store:positionStore,
                        queryMode: 'local',
                        name : 'position',
                        fieldLabel: '职位',
                        displayField: 'name',
                        valueField: 'value'
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: 'Email',
                        vtype:'email',vtypeText:'无效的Email账户' 
                    },
                    {   xtype: 'fieldcontainer',
                        layout: 'hbox',                    
                        items:[{
                              xtype: 'textfield',
                              name : 'deptName',
                              fieldLabel: '默认部门',
                              flex:1,labelWidth:80,
                              readOnly:true},
                        	 {   
							   xtype:'button',iconCls:'search',
							   handler:me.oenDeptTree,
							   scope:me
							  }
							]
                    },
                    {
                        xtype: 'combobox',
                        store:statustore,
                        queryMode: 'local',
                        name : 'status',
                        fieldLabel: '状态',
                        displayField: 'value',
                        valueField: 'key'
                    },
                    {
                        xtype: 'hiddenfield',
                        name : 'deptId'
                     
                    },
                    {
                        xtype: 'hiddenfield',
                        name : 'id'
                     
                    },
                    {
                        xtype: 'hiddenfield',
                        name : 'password'
                     
                    }
                ]
            },me.deptGrid
        ];

        this.buttons = [
            {
                text: '保存',
                action: 'save'
            },
            {
                text: '取消',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    },
    rowSelected:function(model, sels){
    	var me=this;
    	me.down('button[iconCls=delete]').setDisabled(sels.length == 0);
    	
    },
    //兼职部门打开部门树选择框
    selectDept:function(){
    	var selectDeptWin=Ext.create('AM.view.sys.dept.Selector',{callback:this.addDept});
    	selectDeptWin.show();
    },
    //兼职部门添加部门
    addDept:function(dept){    	
    	var deptGrid=Ext.getCmp('usereditwin').down('grid'),deptStore=deptGrid.store;
    	deptStore.insert(0, dept);
    	
    	
    },
    //兼职部门删除部门
    removeDept:function(){
    	var deptGrid=Ext.getCmp('usereditwin').down('grid'),store=deptGrid.store;
    	store.remove(deptGrid.getSelectionModel().getSelection());
    	
        
    },
    //编辑部门打开部门选择树
    oenDeptTree:function(){
    	
    	var selectDeptWin=Ext.create('AM.view.sys.dept.Selector',{callback:this.editDept});
    	selectDeptWin.show();
    },
    //编辑用户部门
    editDept:function(dept){
    	
    	var form=Ext.getCmp('usereditwin').down('form').getForm();
    	form.findField('deptName').setValue(dept.name);
    	form.findField('deptId').setValue(dept.id);
    }
    
});
