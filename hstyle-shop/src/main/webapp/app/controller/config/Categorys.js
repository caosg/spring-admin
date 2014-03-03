/**
 * 商品类目管理主控制器
 */
Ext.define('AM.controller.config.Categorys', {
			extend : 'Ext.app.Controller',
			id : 'category.CategoryController',
			alias : 'widget.categorycontroller',			
			stores : ['config.CategoryTrees','config.Categorys'],
			models : ['config.Category'],
			views : ['config.category.Main'],
            selectNode:{},
            currentNode:{},
			refs : [ {
						ref : 'categoryMain',
						selector : 'categorymain'
					}],

			init : function() {
				var me = this;
				me.control({
							'#category-add' : {
								click : me.onCategoryAdd
							},
							'#category-add-child' : {
								click : me.onCategoryChildAdd
							},
							'#category-edit' : {
								click : me.onCategoryEdit
							},
							'#category-delete' : {
								click : me.onCategoryDelete
							},
							'#category-refresh' :{
								click: me.onRefresh
							},
							'categoryedit button[action=save]':{
								click : me.saveCategory
							},
							'categoryedit button[action=cancel]':{
								click : me.cancel
							}
						});
			},
			getMainView : function() {
				return this.getConfigCategoryMainView();
			},
			//新增同级别商品类目
			onCategoryAdd: function() {
				var me=this;
				var tree = this.getCategoryMain().down('treepanel');
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if(me.selectNode.data.id==0){
					Ext.Msg.alert("信息提示", "不允许存在多个ROOT节点");
					return ;
				}
				if (me.selectNode) {
					me.currentNode = me.selectNode.parentNode.insertBefore({
                      text: '新类目',
                      name: '新类目',
                      level:me.selectNode.parentNode.data.level+1,
                      orderNum:me.selectNode.parentNode.childNodes.length+1,
                      parentId:me.selectNode.parentNode.data.id,
                      parentName:me.selectNode.parentNode.data.text,
                      status:'1',//正常状态  0 为删除状态
                      leaf: '0'
                    });
					var edit = Ext.create('AM.view.config.category.Edit').show();
					edit.title="添加平级类目";
					Ext.getCmp('categoryEditForm').loadRecord(me.currentNode);
				} else
					Ext.Msg.alert("添加子部门", "currentNode");
			},
			//新增子商品类目
			onCategoryChildAdd: function() {
				var me=this;
				var tree = me.getCategoryMain().down('treepanel');
				me.selectNode = tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					me.selectNode.data.leaf = false;
					if(!me.selectNode.isExpanded())
					   me.selectNode.expand();
					var edit = Ext.create('AM.view.config.category.Edit').show();
					edit.setTitle("添加子类目");
					
                    me.currentNode = me.selectNode.appendChild({
                      text: '新类目',
                      name: '新类目',
                      level:me.selectNode.data.level+1,
                      orderNum:me.selectNode.childNodes.length+1,
                      parentId:me.selectNode.data.id,
                      parentName:me.selectNode.data.text,
                      status:'1',//正常状态  0 为删除状态
                      leaf: '0'
                   });
                    me.currentNode.updateInfo();
					edit.down('form').loadRecord(me.currentNode);
				} else
					Ext.Msg.alert("添加子类目", "请先选择一个类目");
			},
			//编辑商品类目
			onCategoryEdit: function() {
				var me=this;
				var tree = me.getCategoryMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				me.currentNode=null;
				if (me.selectNode) {				
					var edit = Ext.create('AM.view.config.category.Edit').show();
					edit.setTitle("编辑类目");
					me.selectNode.data.parentId=me.selectNode.parentNode.data.id;
					me.selectNode.data.parentName=me.selectNode.parentNode.data.text;
					edit.down('form').loadRecord(me.selectNode);
				} else
					Ext.Msg.alert("编辑类目", "请先选择一个类目");
			},
			//删除类目
			onCategoryDelete: function() {
				var me=this;
				var tree = this.getCategoryMain().down('treepanel');
				me.selectNode=tree.getSelectionModel().getSelection()[0];
				if (me.selectNode) {
					if(me.selectNode.data.root){   
                      Ext.MessageBox.show({title:'操作错误',msg:'根节点不允许删除!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;  
                    }  
					if(me.selectNode.hasChildNodes()){
                      Ext.MessageBox.show({title:'操作错误',msg:'当前选中类目含有子类目项，请先删除子类目!',
                           buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                      return;
                    }
                    Ext.MessageBox.confirm("提示","确定要删除当前选中的类目吗",function (e){
                      if( e == "yes"){
                        me.currentNode = me.selectNode;
                        me.selectNode = me.currentNode.parentNode; 
                        var params = new Object();
                        params.id=me.currentNode.get('id');
                        doAjax('productcategory/delete.do',params,me.delSuccess,me);
                      }
                    },me);
				} else
					Ext.Msg.alert("删除", "请先选择一个类目");
			},
			//刷新部门树
			onRefresh: function(){
			  var tree = this.getCategoryMain().down('treepanel');
			  tree.store.load();
			},
			//提交保存
			saveCategory: function(button) {
				var me=this;
				var win = button.up('window'), form = win.down('form').getForm();
				var tree=me.getCategoryMain().down('treepanel');
				var record;
				if (form.isValid()) {
					form.submit({
						success: function(form, action) {
							Ext.Msg.alert('信息提示', '保存成功');
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
	                        Ext.getCmp('basicCategoryForm').loadRecord(record);
	                        win.close();
                      },
                      failure: function(form, action) {
                    	  Ext.Msg.alert('提示', '保存失败');
                      }
				  });
               }
			},
			//取消编辑
			cancel: function(button) {
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
				Ext.Msg.alert('提示', '删除类目成功');
				var tree=Ext.getCmp('category.CategorysView').down('treepanel');
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
