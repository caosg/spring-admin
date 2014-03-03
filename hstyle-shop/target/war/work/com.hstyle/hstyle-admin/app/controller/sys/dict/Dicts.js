/**
 * 数据字典前台控制层
 */
Ext.define('AM.controller.sys.dict.Dicts', {
    extend: 'Ext.app.Controller',
    stores: ['sys.dict.Dicts','sys.dict.DictItems','sys.dict.DictMappings'],
    models: ['sys.dict.Dict'],
    views: ['sys.dict.Main'],
    refs: [
        {ref : 'dictMain', selector : 'dictmain' },
        {ref : 'dictList', selector : 'dictlist' },
        {ref : 'editWin', selector : 'dicteditwin' }
    ],
    init: function() {

        this.control({
            '#dict-add': {
                click: this.onCreateAddDictTypeView//打开新增页面
            },
            '#dict-update':{
				click: this.onUpdateDictType//点击保存/更新按钮->保存更新数据
			},
            '#dict-edit': {
                click: this.onCreateEditDictTypeView//打开编辑页面
            },
            '#dict-delete': {
            	click: this.onDelDictType//点击删除按钮->删除数据
            }
        });
    },
    /**
     * 加载模块主视图
     */
    getMainView: function() {
    	return this.getSysDictMainView();
    },
    /**
     * 创建新增页面
     */
    onCreateAddDictTypeView: function(){
    	var me = this;
    	var win = Ext.create('AM.view.sys.dict.Edit');   	
        win.down('form').getForm().url='dict/updateDictType.do';
		win.setTitle("新增字典");
		win.show();
    },
    /**
     * 创建编辑页面
     */
    onCreateEditDictTypeView: function() {
    	var me = this ;
    	var grid = me.getDictList();
    	rs = grid.getSelectionModel().getSelection();
    	//从父页面通过选取事件获取选中的值当作弹出窗口的基础数据
    	var code = rs[0].data.code; var name = rs[0].data.name;var id = rs[0].data.id;var remark = rs[0].data.remark;
    	var win = Ext.create('AM.view.sys.dict.Edit');
    	//赋值给文本框
    	Ext.getCmp('code_id').setValue(code);Ext.getCmp('remark_id').setValue(remark);
    	Ext.getCmp('name_id').setValue(name);Ext.getCmp('i_id').setValue(id);
    	Ext.getCmp('DictEditGrid').store.load();//
    	Ext.getCmp('DictEditForm').url='dict/updateDictType.do';
    	win.setTitle("修改字典");
    	win.show;
    },
    /**
     * 根据id逻辑删除数据字典主表信息
     * 1:ids是以','分割的id串
     */
    onDelDictType: function() {
        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
            if( e == "yes"){    	
		    	var grid = Ext.getCmp('dictlist');
		    	rs = grid.getSelectionModel().getSelection();
		    	var ids = '';
		    	for(var i=0;i<rs.length;i++){
		    		if(ids==''){
		    			ids+=rs[i].data.id;
		    		}
		    		ids+=','+rs[i].data.id;
		    	}
				Ext.Ajax.request({
				    url: 'dict/delDictType.do',
				    params: {
				        ids: ids
				    },
				    success: function(response){
				        var text = response.responseText;
				        Ext.Msg.alert('信息提示','操作成功');
				    	grid.store.load();
				    }
				});
            }
        });    
    },
    /**
     * 创建保存按钮事件
     * 1:保存/更新数据字典主信息
     * 2:获取后台数据库主键id回写到页面隐藏域
     * 3:刷新父页面的grid显示新增内容
     * 
     */
    onUpdateDictType : function(){
    	var mainList = this.getDictList();//获取主查询列表
    	var editWin = this.getEditWin();//获取弹出window对象
        var form = editWin.form.getForm();//me.up('form').getForm();
        if (form.isValid()) {
            form.submit({
                success: function(form, action) {
                    //返回的json获取方式为action.result,这是EXT自己的属性,与后台的key没关系
              	  var jsonData = action.result.data;
              	  //为form中隐藏域控件(数据库主键值)赋值
              	  Ext.getCmp('i_id').setValue(jsonData.id);
              	  Ext.Msg.alert('信息提示', '操作成功');
              	  mainList.store.load();
                },
                failure: function(form, action) {
                    Ext.Msg.alert('Failed', action.result.msg);
                }
            });
        }    	
    }
});
