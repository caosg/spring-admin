/**
 * 菜单功能管理主控制器
 */
Ext.define('AM.controller.sys.menu.Functions', {
			extend : 'Ext.app.Controller',
			stores : ['sys.menu.TreeFunctions','sys.menu.Functions'],
			models : ['sys.menu.Function'],
			views : ['sys.menu.Main','sys.menu.Right'],
			refs : [
			        {ref : 'menuMain',selector : 'menumain'},
			        {ref : 'menuForm',selector:'#basicMenuForm'},
			        {ref : 'funtionGrid',selector:'funtionGrid'}
			],
            selectNode:{},
            currentNode:{},
			init : function() {
				var me = this;
				me.control({
							'menumain' : {
								render : me.onPanelRendered								
							},
							'funtionGrid' :{
						       selectionchange:me.onGgidSelected
							},
							'#menu-add' : {
								click : me.onAddMenu
							},
							'#menu-add-child' : {
								click : me.onAddChildMenu
							},
							'#menu-edit' : {
								click : me.onEditMenu
							},
							'#menu-del' : {
								click : me.onDeleteMenu
							},
							'#menu-refresh' :{
								click: me.onRefresh
							},
							
							 'menuedit button[action=save]' :{
							 	click: me.save
							 },
							 
							 'menuedit button[action=cancel]' :{
							 	click: me.cancel
							 },
							 'funtionGrid #function-add' : {
							 	click : me.onAddFunction
							 },
							 'funtionGrid #function-edit' : {
							 	click : me.onEditFunction
							 },
							 'funtionGrid #function-delete' : {
							 	click : me.onDeleteFunction
							 },
							 'funcedit button[action=save]' :{
							 	click :me.saveFunc
							 }
							
						});
			},
			getMainView : function() {
				return this.getSysMenuMainView();
			},
			onPanelRendered : function(panel) {
				console.log('The menu mainpanel was rendered');
			},
			/***************************菜单树操作******************************************************************/
			onAddMenu : function() {
				var me=this;
				var tree = this.getMenuMain().down('treepanel');
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					me.currentNode = me.selectNode.parentNode.insertBefore({
                      text: '新菜单',
                      name: '新菜单',                     
                      sort:me.selectNode.parentNode.childNodes.length+1,
                      parentId:me.selectNode.parentNode.data.id,
                      parentName:me.selectNode.parentNode.data.text, 
                      resource:'1',
                      leaf: '1'
                    });
					var edit = Ext.create('AM.view.sys.menu.MenuEdit').show();
					edit.title="添加平级菜单";
					edit.down('form').loadRecord(me.currentNode);
					
				} else
					MsgBoxInfo("请先选择一个菜单");
			},
			onAddChildMenu : function() {
				var tree = this.getMenuMain().down('treepanel');
				var me=this;
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					//console.log(me.selectNode.data);
					if(me.selectNode.data.leaf=='1'){
						//Ext.Msg.alert("提示", "叶子菜单不允许添加子节点！");
						MsgBoxInfo("叶子菜单不允许添加子节点!");
						return;
					}
					if(!me.selectNode.isExpanded())
					   me.selectNode.expand();
					var edit = Ext.create('AM.view.sys.menu.MenuEdit').show();
					edit.setTitle("添加子菜单");
					
                    me.currentNode = me.selectNode.appendChild({
                      text: '新菜单',
                      name: '新菜单',
                      resource:'1',
                      sort:me.selectNode.childNodes.length+1,
                      parentId:me.selectNode.data.id,
                      parentName:me.selectNode.data.text,                    
                      leaf: '1'
                   });
                    me.currentNode.updateInfo();
					edit.down('form').loadRecord(me.currentNode);
					
				} else
					//Ext.Msg.alert("添加子菜单", "请先选择一个菜单");
					MsgBoxInfo("请先选择一个菜单");
			},
			//编辑菜单
			onEditMenu : function() {
				var me=this;
				var tree = me.getMenuMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				me.currentNode=null;
				if (me.selectNode) {				
					var edit = Ext.create('AM.view.sys.menu.MenuEdit').show();
					edit.setTitle("编辑菜单");
					me.selectNode.data.parentId=me.selectNode.parentNode.data.id;
					me.selectNode.data.parentName=me.selectNode.parentNode.data.text;
					edit.down('form').loadRecord(me.selectNode);
				} else
					//Ext.Msg.alert("编辑部门", "请先选择一个菜单");
					MsgBoxInfo("请先选择一个菜单");
			},
			//删除菜单
			onDeleteMenu : function() {
				var me=this;
				var tree = this.getMenuMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					if(me.selectNode.data.root){   
                      Ext.MessageBox.show({title:'操作错误',msg:'根节点不允许删除!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;  
                    }  
					if(me.selectNode.hasChildNodes()){
                      Ext.MessageBox.show({title:'操作错误',msg:'当前选中部门含有子菜单项，请先删除子菜单!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;
                    }
                    Ext.MessageBox.confirm("提示","确定要删除当前选中的菜单吗",function (e){
                      if( e == "yes"){
                        me.currentNode = me.selectNode;
                        me.selectNode = me.currentNode.parentNode; 
                        var params = new Object();
                        params.id=me.currentNode.get('id');
                        doAjax('menu/deleteMenu.do',params,me.delSuccess,me);
                        
                      }
                    },me);
				} else
					//Ext.Msg.alert("删除", "请先选择一个菜单");
				    MsgBoxInfo("请先选择一个菜单");
			},
			//刷新菜单树
			onRefresh : function(){
			  var tree = this.getMenuMain().down('treepanel');
			  tree.store.load();
			},
			//提交保存菜单按钮
			save : function(button) {
				var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				 var tree=me.getMenuMain().down('treepanel');
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
                       Ext.getCmp('basicMenuForm').loadRecord(record);
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
			delSuccess:function(result){
				Ext.Msg.alert('提示', '删除菜单成功');

				var tree=Ext.getCmp('sys.menu.FunctionsView').down('treepanel');
				
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
               Ext.MessageBox.show({title:'提示',msg:'删除失败!',buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
			},
            /************************功能列表操作********************************************************************/
			//grid选中事件
           onGgidSelected:function(model, sels){
    	       /*  var me=this;
    	         Ext.getCmp('functionEdit').setDisabled(sels.length == 0);
    	         Ext.getCmp('functionDelete').setDisabled(sels.length == 0);*/
            },
            //增加菜单功能
            onAddFunction : function(){
            	var me = this;
            	var leaf=me.getMenuForm().getForm().findField('leaf').getValue();
            	if(leaf!='1'){
            	   MsgBoxInfo('只有叶子类型菜单才可以添加功能！');
            	   return;
            	}
            	var menuId=me.getMenuForm().getForm().findField('id').getValue();
				var menuName=me.getMenuForm().getForm().findField('name').getValue();
				//创建数据模型，并设置默认值
				var model=Ext.create('AM.model.sys.menu.Function',
					{parentId:menuId,parentName:menuName,resource:'2',leaf:'1'});
			    var win=Ext.create('AM.view.sys.menu.FuncEdit');
            	win.setTitle("添加功能");				
				win.down('form').loadRecord(model);
				win.show();
            	
            },
            //编辑功能
			onEditFunction:function(){
				var me=this;
				var win=Ext.create('AM.view.sys.menu.FuncEdit'),grid = me.getFuntionGrid(),
		        rs = grid.getSelectionModel().getSelection();;
				var menuId=me.getMenuForm().getForm().findField('id').getValue();
				var menuName=me.getMenuForm().getForm().findField('name').getValue();
				if (rs.length > 0) {
				  rs = rs[0];		
				  rs.data.parentId=menuId;
				  rs.data.parentName=menuName;
				  win.setTitle("编辑功能");				
				  win.down('form').loadRecord(rs);
				  win.show();
				}
		    },
		    //删除功能
		    onDeleteFunction : function(){
		    	var me = this, grid = me.getFuntionGrid(),
		        rs = grid.getSelectionModel().getSelection();
		        if (rs.length > 0) {
				  var content = "确定删除选中功能？<br/><p style='color:red'>注意：删除的功能将不能恢复!</p>";
				  Ext.Msg.confirm("删除功能", content, function(btn) {
				    if (btn == "yes") {
						
						if (rs.length > 0) {
							var ids = [];
                            for(var i = 0; i < rs.length; i++){
                                  ids.push(rs[i].data.id);
                             }                                                    
                          var param={ids:ids};
						  doAjax('menu/deleteFunctions.do',param,me.delFuncSuccess,me);	
						}
					 }
				 }, grid);
		       }
		    },
		    //保存菜单功能
		    saveFunc : function(button){
		    	var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				var grid = me.getFuntionGrid();
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
		    delFuncSuccess:function(data,scope){
		    	MsgBoxInfo('删除功能成功！');
		    	scope.getFuntionGrid().store.reload();
		    }
			
		});