/**
 * 数据权限管理主控制器
 */
Ext.define('AM.controller.sys.permission.Permissions', {
			extend : 'Ext.app.Controller',
			stores : ['sys.permission.TreePermissions','sys.permission.Targets'],
			models : ['sys.permission.Permission'],
			views : ['sys.permission.Main','sys.permission.Right'],
			refs : [
			        {ref : 'main',selector : 'permissionmain'},
			        {ref : 'detail',selector:'#basicPermissionForm'},
			        {ref : 'rules',selector:'rulegrid'}
			],
            selectNode:{},
            currentNode:{},
			init : function() {
				var me = this;
				me.control({
							'permissionmain' : {
								render : me.onPanelRendered								
							},
							'rulegrid' :{
						       selectionchange:me.onGgidSelected
							},
							'#permission-add' : {
								click : me.onAddMenu
							},
							'#permission-add-child' : {
								click : me.onAddChildMenu
							},
							'#permission-edit' : {
								click : me.onEditMenu
							},
							'#permission-del' : {
								click : me.onDeleteMenu
							},
							'#permission-refresh' :{
								click: me.onRefresh
							},
							
							 'permissionedit button[action=save]' :{
							 	click: me.save
							 },
							 
							 'permissionedit button[action=cancel]' :{
							 	click: me.cancel
							 },
							 'rulegrid #rule-add' : {
							 	click : me.onAddRule
							 },
							 'rulegrid #rule-edit' : {
							 	click : me.onEditRule
							 },
							 'rulegrid #rule-delete' : {
							 	click : me.onDeleteRule
							 },
							 'ruleedit button[action=save]' :{
							 	click :me.saveRule
							 }
							
						});
			},
			getMainView : function() {
				return this.getSysPermissionMainView();
			},
			onPanelRendered : function(panel) {
				console.log('The permission mainpanel was rendered');
			},
			/***************************权限树操作******************************************************************/
			onAddMenu : function() {
				var me=this;
				var tree = this.getMain().down('treepanel');
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode&&me.selectNode.parentNode) {
					me.currentNode = me.selectNode.parentNode.insertBefore({
                      text: '新分类',
                      name: '新分类',                     
                      sort:me.selectNode.parentNode.childNodes.length+1,
                      parentId:me.selectNode.parentNode.data.id,
                      parentName:me.selectNode.parentNode.data.text, 
                      type:me.selectNode.data.type,
                      resource:me.selectNode.data.resource,
                      leaf:'0'
                    });
					var edit = Ext.create('AM.view.sys.permission.PermissionEdit').show();
					edit.title="添加平级分类";
					edit.down('form').loadRecord(me.currentNode);
					if(me.selectNode.data.type=='action')
						edit.down('form').down('combobox[name=resource]').setReadOnly(true);
				} else
					MsgBoxInfo("请先选择一个节点");
			},
			onAddChildMenu : function() {
				var tree = this.getMain().down('treepanel');
				var me=this;
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {					
					if(me.selectNode.data.type!='domain'&&me.selectNode.parentNode){						
						MsgBoxInfo("操作类型不允许添加子节点!");
						return;
					}
					if(!me.selectNode.isExpanded())
					   me.selectNode.expand();
					var edit = Ext.create('AM.view.sys.permission.PermissionEdit').show();
					edit.setTitle("添加数据权限");
					
                    me.currentNode = me.selectNode.appendChild({
                      text: '新分类',
                      name: '新分类',
                      leaf:'0',                      
                      sort:me.selectNode.childNodes.length+1,
                      parentId:me.selectNode.data.id,
                      parentName:me.selectNode.data.text,
                      resource:me.selectNode.data.resource
                      
                   });
                    me.currentNode.updateInfo();
					edit.down('form').loadRecord(me.currentNode);
					if(me.selectNode.parentNode)
						edit.down('form').down('combobox[name=resource]').setReadOnly(true);
				} else
					
					MsgBoxInfo("请先选择一个节点");
			},
			//编辑菜单
			onEditMenu : function() {
				var me=this;
				var tree = me.getMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				me.currentNode=null;
				if (me.selectNode) {				
					var edit = Ext.create('AM.view.sys.permission.PermissionEdit');
					edit.setTitle("编辑数据权限");
					me.selectNode.data.parentId=me.selectNode.parentNode.data.id;
					me.selectNode.data.parentName=me.selectNode.parentNode.data.text;
					console.log(me.selectNode.data.resource);
					edit.down('form').loadRecord(me.selectNode);
					if(me.selectNode.parentNode.data.resource)
						edit.down('form').down('combobox[name=resource]').setReadOnly(true);
					edit.show();
				} else
					
					MsgBoxInfo("请先选择一个节点");
			},
			//删除权限分类
			onDeleteMenu : function() {
				var me=this;
				var tree = this.getMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					if(me.selectNode.data.root){   
                      Ext.MessageBox.show({title:'操作错误',msg:'根节点不允许删除!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;  
                    }  
					if(me.selectNode.hasChildNodes()){
                      Ext.MessageBox.show({title:'操作错误',msg:'当前选中分类含有子分类项，请先删除子分类!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;
                    }
                    Ext.MessageBox.confirm("提示","确定要删除当前选中的分类吗",function (e){
                      if( e == "yes"){
                        me.currentNode = me.selectNode;
                        me.selectNode = me.currentNode.parentNode; 
                        var params = new Object();
                        params.id=me.currentNode.get('id');
                        doAjax('permission/delete.do',params,me.delSuccess,me);
                        
                      }
                    },me);
				} else
					
				    MsgBoxInfo("请先选择一个节点");
			},
			//刷新菜单树
			onRefresh : function(){
			  var tree = this.getMain().down('treepanel');
			  tree.store.load();
			},
			//提交保存菜单按钮
			save : function(button) {
				var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				 var tree=me.getMain().down('treepanel');
				 var record;
				if (form.isValid()) {
                  form.submit({
                     success: function(form, action) {
                       Ext.Msg.alert('提示', '保存成功');
                       //树节点名字以text展现
                       action.result.data.text=action.result.data.name;
                       if(me.currentNode !=null){                    	 
                       	 me.currentNode.set(action.result.data);                    	
                         me.currentNode.updateInfo();  
                         record=me.currentNode;
                         tree.view.select(me.currentNode);                        
                       }else{
                       	 me.selectNode.set(action.result.data);
                       	 me.selectNode.updateInfo();
                       	 tree.view.select(me.selectNode);
                       	 record=me.selectNode;
                       }
                       Ext.getCmp('basicPermissionForm').loadRecord(record);
                       win.close();
                     },
                     failure: function(form, action) {
                     	if(action.result)
                     	  MsgBoxErr(action.result.data);
                     	else
                          MsgBoxErr('系统异常，保存失败');
                     }
                   });
                }
				
				
			},
			//取消编辑
			cancel : function(button) {
				var win = button.up('window');
				var form = win.down('form').getForm();				
				win.close();
				var me=this;
				if(me.currentNode !=null){
                        me.currentNode.remove();
                        if(!me.selectNode.hasChildNodes()){
                            me.selectNode.set({
                                leaf:true
                            });
                           me.selectNode.updateInfo();
                        }
                 }
			},
			delSuccess:function(result,scope){
				Ext.Msg.alert('提示', '删除规则成功');

				var tree=scope.getMain().down('treepanel');
				
				var currentNode=tree.getSelectionModel().getSelection()[0];
				var selectNode=currentNode.parentNode;
				currentNode.remove();
                if(!selectNode.hasChildNodes()){
                      selectNode.set({
                                           leaf:true
                                         });
                      selectNode.updateInfo();
                 }
                 tree.view.select(selectNode);
                 
			},
			delFail:function(result){
				//todo 这里最好抛出自定义异常信息
				MsgBoxInfo('删除失败！');
               
			},
            /************************实例列表操作********************************************************************/
			//grid选中事件
           onGgidSelected:function(model, sels){
    	       /*  var me=this;
    	         Ext.getCmp('functionEdit').setDisabled(sels.length == 0);
    	         Ext.getCmp('functionDelete').setDisabled(sels.length == 0);*/
            },
            //增加管辖对象
            onAddRule : function(){
            	var notAdd=['*','#'];
            	var me = this;
            	var type=me.getDetail().down('#typeGroup').getValue();

            	if(type.type!='action'){
            	   MsgBoxInfo('只有操作类型权限才可以添加对象！');
            	   return;
            	}
            	var dataId=me.getDetail().getForm().findField('id').getValue();           	
				var operator=me.getDetail().getForm().findField('operator').getValue();
				if(operator==null||Ext.Array.contains(notAdd,operator)){
					MsgBoxWar('只有范围性质的运算符才允许添加目标');return;
				}
				
				//创建数据模型，并设置默认值
				var model=Ext.create('AM.model.sys.permission.Target',
					{dataId:dataId});
			    var win=Ext.create('AM.view.sys.permission.RuleEdit');
            	win.setTitle("添加Target");				
				win.down('form').loadRecord(model);
				win.show();
            	
            },
            //编辑规则
			onEditRule:function(){
				var me=this;
				var win=Ext.create('AM.view.sys.permission.RuleEdit'),grid = me.getRules(),
		        rs = grid.getSelectionModel().getSelection();;
				var parentId=me.getDetail().getForm().findField('id').getValue();
				//var parentName=me.getDetail().getForm().findField('name').getValue();
				if (rs.length > 0) {
				  rs = rs[0];		
				  win.setTitle("编辑实例");				
				  win.down('form').loadRecord(rs);
				  win.show();
				}
		    },
		    //删除功能
		    onDeleteRule : function(){
		    	var me = this, grid = me.getRules(),
		        rs = grid.getSelectionModel().getSelection();
		        if (rs.length > 0) {
				  var content = "确定删除选中目标吗？<br/><p style='color:red'>注意：删除的目标将不能恢复!</p>";
				  Ext.Msg.confirm("删除目标", content, function(btn) {
				    if (btn == "yes") {
						
						if (rs.length > 0) {
							var ids = [];
                            for(var i = 0; i < rs.length; i++){
                                  ids.push(rs[i].data.id);
                             }                                                    
                          var param={ids:ids};
						  doAjax('permission/remove-instance.do',param,me.delRuleSuccess,me);	
						}
					 }
				 }, grid);
		       }
		    },
		    //保存规则
		    saveRule : function(button){
		    	var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				var grid = me.getRules();
				var record;
				if (form.isValid()) {
                  form.submit({
                     success: function(form, action) {
                       Ext.Msg.alert('提示', '保存成功');
                       grid.store.reload();
                       win.close();
                     },
                     failure: function(form, action) {
                        Ext.Msg.alert('提示', '保存失败');
                     }
                   });
                }
		    },
		    //删除成功提示
		    delRuleSuccess:function(data,scope){
		    	MsgBoxInfo('删除功能成功！');
		    	scope.getRules().store.reload();
		    }
		   
			
		});