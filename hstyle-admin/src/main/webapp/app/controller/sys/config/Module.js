/**
 * 模块控制器
 */
Ext.define('AM.controller.sys.config.Module', {
    extend: 'Ext.app.Controller',
    stores: ['sys.config.ModuleS','sys.config.ModulePropertiesS'],
    models: ['sys.config.ModuleM','sys.config.ModulePropertiesM'],
    views: ['sys.config.ModuleListV','sys.config.MainV'],
    refs: [
        {ref : 'moduleListV', selector : 'moduleListV' },
        {ref : 'modulePropertiesV', selector : 'modulePropertiesV'}
    ],

    init: function() {
    	var me=this;
    	me.control({
			'moduleListV' : {
				itemclick : me.getList
			},
			'modulePropertiesV' : {
				itemclick : me.getOptions
			},
			'#perms-module-add' : {
				click : me.onAddModuleProp
			},
			'#perms-module-edit' : {
				click : me.onEditModuleProp
			},
			'#perms-module-delete' : {
				click : me.onDeleteModuleProp
			},
			'#moduleProp-save' : {
				click : me.onSaveMouduleProp
			}
			
		});
    },
    // 返回主视图
    getMainView : function() {
		return this.getSysConfigMainVView();
	},
	//返回模块配置列表
	getList : function() {
		var me = this;
		var grid = me.getModuleListV();
		var opst = Ext.getCmp('sys.config.ModuleOptionsV').store;
		opst.removeAll();
		var rs = grid.getSelectionModel().getSelection()[0];
		var st = Ext.getCmp('sys.config.ModulePropertiesV').store;
		if (!rs) {
			return ;
		}
		/*
		 * st.on('beforeload', function (store, options) {
		 * this.proxy.extraParams = { code:rs.get('code') }; });
		 */
		st.load({
			params: {
				code: rs.get('code')
			}
		});
	},
	//返回选项列表
	getOptions : function() {
		var me = this;
		var grid = me.getModulePropertiesV();
		var rs = grid.getSelectionModel().getSelection()[0];
		if (!rs)
		 return ;
		var st = Ext.getCmp('sys.config.ModuleOptionsV').store;
		st.removeAll();
		var str = rs.get('options');
		if (!str) {
			return;
		}
		var strs = str.split(',');
		for (var i=0; i<strs.length; i++) {
			var outRecord = Ext.create('AM.model.sys.config.ModuleOptionsM');
			var infos = strs[i].split(':');
			outRecord.set('code',infos[0]);
			outRecord.set('name',infos[1]);
			outRecord.commit();
			st.insert(st.getCount(),outRecord);
		}
	},
	//添加模块配置
	onAddModuleProp : function() {
		var grid = this.getModuleListV();
		var rs = grid.getSelectionModel().getSelection()[0];
		if (!rs) {
			Ext.Msg.alert('温馨提示','请选择一个模块！');
			return ;
		}
		var pgrid = this.getModulePropertiesV();
		var modAddWin = Ext.create('AM.view.sys.config.ModulePropertiesEditV',{url:'moduleProperty/addModuleProperty.do'});
		modAddWin.setTitle('增加');
		var form = modAddWin.down('form').getForm();
		form.findField('module_').setValue(rs.get('code'));
		form.findField('moduleType_').setValue(rs.get('type'));
		form.findField('orderNo_').setValue((pgrid.store.getCount()+1));
		modAddWin.show();
	},
	//修改模块配置
	onEditModuleProp : function() {
		var grid = this.getModuleListV();
		var rs = grid.getSelectionModel().getSelection()[0];
		if (!rs) {
			Ext.Msg.alert('温馨提示','请选择一个模块！');
			return ;
		}
		var pgrid = this.getModulePropertiesV();
		var prs = pgrid.getSelectionModel().getSelection()[0];
		if (!prs) {
			Ext.Msg.alert('温馨提示','请选择一条数据！');
			return ;
		}
		var modAddWin = Ext.create('AM.view.sys.config.ModulePropertiesEditV',{url:'moduleProperty/editModuleProperty.do'});
		modAddWin.setTitle('修改');
		var modEditForm = modAddWin.down('form').getForm();
		modEditForm.findField('code_').setReadOnly(true);
		modEditForm.loadRecord(prs);
		modAddWin.show();
	},
	//删除模块配置
	onDeleteModuleProp : function() {
		var grid = this.getModuleListV();
		var rs = grid.getSelectionModel().getSelection()[0];
		if (!rs) {
			Ext.Msg.alert('温馨提示','请选择一个模块！');
			return ;
		}
		var pgrid = this.getModulePropertiesV();
		var prs = pgrid.getSelectionModel().getSelection()[0];
		if (!prs) {
			Ext.Msg.alert('温馨提示','请选择一条数据！');
			return ;
		}
		Ext.Msg.confirm('温馨提示','确定要删除吗?',function(btn){
			if(btn == 'yes'){
				var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍后,正在处理数据..."});
				myMask.show();
				Ext.Ajax.request({
				    url: 'moduleProperty/deleteModuleProperty.do',
				    params: {
						id : prs.get('id')
				    },
				    success: function(response){
				    	var text = Ext.decode(response.responseText);
				    	console.log(text);
				        Ext.Msg.alert('信息提示',text.data);
				    	pgrid.store.load({
				    		params : {
				    			code : rs.get('code')
				    		}
				    	});
				    	var st = Ext.getCmp('sys.config.ModuleOptionsV').store;
						st.removeAll();
				    },
				    failure: function(response){
		            	Ext.Msg.alert('提示', '删除失败!');
		            }
				});
				myMask.hide();
				
			}
		});
	},
	//保存
	onSaveMouduleProp : function() {
		var me = this;
		var modAddWin = Ext.getCmp('modulePropertiesEditV');
		var modAddForm = modAddWin.down('form');
		var opst = modAddWin.optionStore;
		var options = '';
		var s= Ext.getCmp('type_').getValue(); 
		var flag=false,nameFlag=false;
		//判断添加的选项有无重复
		if(s=='selectfield'||s=='checkboxfield'||s=='radiofield'){//当选中的类型为  下拉框，复选框 或者单选按钮时，动态添加
				var keyGroup = new Array(opst.getCount());
				var nameGroup = new Array(opst.getCount());
				for(var i=0;i<opst.getCount();i++){
					var record=opst.getAt(i);
					options+=record.get('code')+":"+record.get('name')+","
					key=record.get('code');
					name=record.get('name');
					for (var j = 0; j < i; j++) {
						if(keyGroup[j]== key){
							flag=true;break;
						}
						if(nameGroup[j]==name){
							nameFlag=true;break;
						}
					}
					if(flag||nameFlag){
						break;
					}
					keyGroup[i]=key;
					nameGroup[i]=name;
				}
				if(options!=''){
					options=options.substring(0,options.length-1);
				}
				if(options==''){
					Ext.Msg.alert('信息提示','请至少添加一条属性信息!');return ;
				}
				if(flag){
					Ext.Msg.alert("温馨提示",'键值重复！');return ;
				}
				if(nameFlag){
					Ext.Msg.alert("温馨提示",'名称重复！');return ;
				}
			}
		
		
		modAddForm.getForm().findField('options_').setValue(options);
		if (!modAddForm.getForm().isValid()) {
			Ext.Msg.alert("提示", "请填写必填项");
			return ;
		}
			
		modAddForm.submit({
			url : modAddWin.url,
            timeout:1000,
            waitMsg:'正在保存，请稍等...',
            success:function (form, action) {
                Ext.Msg.alert("提示", action.result.data);
                Ext.getCmp('sys.config.ModulePropertiesV').store.load({//加载模块配置
					params: {
						code: modAddForm.getForm().findField('module_').getValue()
					}
                });
                var st = Ext.getCmp('sys.config.ModuleOptionsV').store;//清理选项
				st.removeAll();
                modAddWin.destroy();
            },
            failure:function (form, action) {
                Ext.Msg.alert("提示", action.result.data);
//                modAddWin.destroy();
            }
		});
	}
  
});
