/**
 * 平台/店铺前台控制层
 */
Ext.define('AM.controller.config.WareHouseAreas', {
    extend: 'Ext.app.Controller',
    stores: ['config.WareHouseAreaS','config.WareHouseS'],
    models: ['config.WareHouseAreaM','config.WareHouseM'],
    views: ['config.warehouse.Main'],
    refs: [
        {ref : 'wareHouseAreaMain', selector : 'warehouseareamain' },
        {ref : 'wareHouseAreaList', selector : 'warehousearealist' },
        {ref : 'editWin', selector : 'areaeditwin' }
    ],
    init: function() {

        this.control({
            '#areaAdd': {
                click: this.onCreateAddView//打开新增页面
            },
            '#wh-areaedit':{
				click: this.onUpdate//点击保存/更新按钮->保存更新数据
			},
            '#areaEdit': {
                click: this.onCreateEditView//打开编辑页面
            },
            '#wh-areadelete': {
            	click: this.onDelete//点击删除按钮->删除数据
            }
        });
    },
    /**
     * 加载模块主视图
     */
    getMainView: function() {
    	return this.getConfigWarehouseMainView();
    },
    /**
     * 创建新增页面
     */
    onCreateAddView: function(){
    	var me = this;
    	var win = Ext.create('AM.view.config.warehouse.Edit');   	
        win.down('form').getForm().url='warehouse/updateWareHouseArea.do';
		win.setTitle("新增区域");
		win.show();
    },
    /**
     * 创建编辑页面
     */
    onCreateEditView: function() {
    	var me = this ;
    	var grid = me.getWareHouseAreaList();
    	rs = grid.getSelectionModel().getSelection();
    	//从父页面通过选取事件获取选中的值当作弹出窗口的基础数据
    	var code = rs[0].data.code; var name = rs[0].data.name ;var id = rs[0].data.id;var outercode = rs[0].data.outercode;
    	var win = Ext.create('AM.view.config.warehouse.Edit');
    	//赋值给文本框
    	Ext.getCmp('whareacode_id').setValue(code);Ext.getCmp('outercode_id').setValue(outercode);
    	Ext.getCmp('whareaname_id').setValue(name);Ext.getCmp('i_id').setValue(id);
    	Ext.getCmp('WareHouseEditGrid').store.load({
			params : {
				action:'read',
				page : 1,
				start:0,
				parentid:id
			}
    	});
    	win.down('form').getForm().url='warehouse/updateWareHouseArea.do';
    	win.setTitle("编辑平台");
    	win.show;
    },
    /**
     * 根据id逻辑删除平台信息
     *
     */
    onDelete: function() {
        Ext.MessageBox.confirm("提示","确定要删除当前选中的信息吗",function (e){
            if( e == "yes"){    	
		    	var grid = Ext.getCmp('warehousearealist');
		    	rs = grid.getSelectionModel().getSelection();
		    	if(rs.length>0){
					var ids = [];
	                for(var i = 0; i < rs.length; i++){
	                      ids.push(rs[i].data.id);
	                }  
					Ext.Ajax.request({
					    url: 'warehouse/deleteWareHouseArea.do',
					    params: {
					        ids: ids
					    },
					    success: function(response){
					        Ext.Msg.alert('信息提示','操作成功');
					    	grid.store.load({
					    		action:'read'
					    	});
					    }
					});
		    	}
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
    onUpdate : function(){
    	var mainList = this.getWareHouseAreaList();//获取主查询列表
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
              	  mainList.store.load({
              		  action:'read'
              	  });
                },
                failure: function(form, action) {
                    Ext.Msg.alert('Failed', action.result.msg);
                }
            });
        }    	
    }
});
