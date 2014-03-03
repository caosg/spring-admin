/**
 * 部门管理主控制器
 */
Ext.define('AM.controller.sys.dept.Depts', {
			extend : 'Ext.app.Controller',
			stores : ['sys.dept.Depts','sys.dept.Users'],
			models : ['sys.dept.Dept'],
			views : ['sys.dept.Main'],
            selectNode:{},
            currentNode:{},
			refs : [ {
						ref : 'deptMain',
						selector : 'deptmain'
					}, {
						ref : 'contentPanel',
						selector : '#contentPanel'
					}],

			init : function() {
				var me = this;
				me.control({
							'deptmain' : {
								render : me.onPanelRendered
							},
							'#perms-dept-add' : {
								click : me.onAddDept
							},
							'#perms-dept-add-child' : {
								click : me.onAddChildDept
							},
							'#perms-dept-edit' : {
								click : me.onEditDept
							},
							'#perms-dept-del' : {
								click : me.onDeleteDept
							},
							'#perms-dept-refresh' :{
								click: me.onRefresh
							},
							'deptedit button[action=save]':{
								click : me.saveDept
							},
							'deptedit button[action=cancel]':{
								click : me.cancel
							}
						});
			},
			getMainView : function() {
				return this.getSysDeptMainView();
			},
			onPanelRendered : function(panel) {
				console.log('The dept mainpanel was rendered');
			},
			onAddDept : function() {
				var me=this;
				var tree = this.getDeptMain().down('treepanel');
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					if(!me.selectNode.parentNode)
					  return;
					me.currentNode = me.selectNode.parentNode.insertBefore({
                      text: '新部门',
                      name: '新部门',
                      level:me.selectNode.parentNode.data.level+1,
                      orderNum:me.selectNode.parentNode.childNodes.length+1,
                      parentId:me.selectNode.parentNode.data.id,
                      parentName:me.selectNode.parentNode.data.text,
                      status:'0',
                      leaf: true
                    });
					var edit = Ext.create('AM.view.sys.dept.Edit').show();
					edit.title="添加平级部门";
					edit.down('form').getForm().url="dept/add.do";
					edit.down('form').loadRecord(me.currentNode);
					
				} else
					MsgBoxInfo("请先选择一个部门");
			},
			onAddChildDept : function() {
				var tree = this.getDeptMain().down('treepanel');
				var me=this;
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					me.selectNode.data.leaf = false;
					if(!me.selectNode.isExpanded())
					   me.selectNode.expand();
					var edit = Ext.create('AM.view.sys.dept.Edit').show();
					edit.setTitle("添加子部门");
					
                    me.currentNode = me.selectNode.appendChild({
                      text: '新部门',
                      name: '新部门',
                      level:me.selectNode.data.level+1,
                      orderNum:me.selectNode.childNodes.length+1,
                      parentId:me.selectNode.data.id,
                      parentName:me.selectNode.data.text,
                      status:'0',
                      leaf: true
                   });
                    me.currentNode.updateInfo();
                    edit.down('form').getForm().url="dept/add.do";
                    
					edit.down('form').loadRecord(me.currentNode);
					
				} else
					MsgBoxInfo("请先选择一个部门");
			},
			//编辑部门
			onEditDept : function() {
				var me=this;
				var tree = me.getDeptMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				me.currentNode=null;
				if (me.selectNode) {				
					var edit = Ext.create('AM.view.sys.dept.Edit').show();
					edit.setTitle("编辑部门");
					me.selectNode.data.parentId=me.selectNode.parentNode.data.id;
					me.selectNode.data.parentName=me.selectNode.parentNode.data.text;
					edit.down('form').getForm().url="dept/edit.do";
					edit.down('form').loadRecord(me.selectNode);
				} else
					MsgBoxInfo("请先选择一个部门");
			},
			//删除部门
			onDeleteDept : function() {
				var me=this;
				var tree = this.getDeptMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					if(me.selectNode.data.root){   
                      Ext.MessageBox.show({title:'操作错误',msg:'根节点不允许删除!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});
                      return;  
                    }  
					if(me.selectNode.hasChildNodes()){
                      Ext.MessageBox.show({title:'操作错误',msg:'当前选中部门含有子部门项，请先删除子部门!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});
                      return;
                    }
                    Ext.MessageBox.confirm("提示","确定要删除当前选中的部门吗",function (e){
                      if( e == "yes"){
                        me.currentNode = me.selectNode;
                        me.selectNode = me.currentNode.parentNode; 
                        var params = new Object();
                        params.id=me.currentNode.get('id');
                        doAjax('dept/delete.do',params,me.delSuccess,me);
                        
                      }
                    },me);
				} else
					MsgBoxInfo("请先选择一个部门");
			},
			//刷新部门树
			onRefresh : function(){
			  var tree = this.getDeptMain().down('treepanel');
			  tree.store.load();
			},
			//提交保存部门按钮
			saveDept : function(button) {
				var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				 var tree=me.getDeptMain().down('treepanel');
				 var record;
				if (form.isValid()) {
                  form.submit({
                     success: function(form, action) {
                       Ext.Msg.alert('提示', '保存成功');
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
                       Ext.getCmp('basicDeptForm').loadRecord(record);
                       win.close();
                     },
                     failure: function(form, action) {
                        Ext.Msg.alert('提示', '保存失败');
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
				Ext.Msg.alert('提示', '删除部门成功');

				var tree=Ext.getCmp('sys.dept.DeptsView').down('treepanel');
				
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
			}

		});
