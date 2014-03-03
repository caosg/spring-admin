/**
 * 数据字典列表
 */
Ext.require([
    'Ext.ux.RowExpander'
]);
var obj;

Ext.define('AM.view.sys.dict.List', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.dictlist',
			title : "字典列表",
			id : 'dictlist',
			store : 'sys.dict.Dicts',
			region:'center',
			loadMask: true,
			columnLines: true,
            plugins: [{
                ptype: 'rowexpander',
                rowBodyTpl : [
                          '<table width="50%" border="1" cellpadding="0" cellspacing="1" bordercolor="#d4d8e1" bgcolor="#d4d8e1" style=" margin:10px;background-color:#d4d8e1;line-height:22px; text-align:center; font-size:12px; color:#627da1;">',
                          '<tpl for="dictionaries">',//循环子表数组对象
	                          '<tpl if="isdel &gt; 0">',//如果是非逻辑删除了的数据就显示
	                              //如果有数据而且是第一行,就放入表头
                                //'{% if (xindex  === 1) %}',
		                          //'<tr>',//表头
			                      //    '<td width="10%" style="background:url(resources/images/dict/thbg.jpg) repeat-x; font-weight:bold;">&nbsp;</td>',
			                      //    '<td width="45%" style="background:url(resources/images/dict/thbg.jpg) repeat-x; font-weight:bold;">标题名称</td>',
			                      //    '<td width="45%" style="background:url(resources/images/dict/thbg.jpg) repeat-x; font-weight:bold;">标题名称</td>',
	                              //'</tr>',
	                              //'{%  %}',
	                              '<tr style="font-color:#fff000;">',
	                                 '<td style="{[xindex % 2 === 0 ? "background-color:#f6f7f9;" : "background-color:#edf2f6;"]}">',
	                          		 	'<img src="resources/images/dict/arrow.png" width="4" height="6" />',
	                          		 '</td>',
	                          		 '<td style="{[xindex % 2 === 0 ? "background-color:#f6f7f9;" : "background-color:#edf2f6;"]}">',	
                          		 	    '{value}',
                          	    	 '</td>',
	                          		 '<td style="{[xindex % 2 === 0 ? "background-color:#f6f7f9;" : "background-color:#edf2f6;"]}">',
	                          		    '{name}',
	                          		 '</td>',
	                          	  '</tr>', 
	                          '</tpl>',    
                          '</tpl>',
	                      '</table>'    
                ]
            }],            
			viewConfig : {
				loadMask : new Ext.LoadMask(this, {
							msg : '正在努力的为您加载......'
						})
			},
			initComponent : function() {
				var me = this;
				me.tbar = [{
							iconCls : "add",
							text:'增加',
							scope : me,
							tooltip : '增加字典',
							id : "dict-add"
						},'-', {
							iconCls : "edit",
							text:'编辑',
							scope : me,
							tooltip : '编辑字典',
							id : "dict-edit"
						},'-', {
							iconCls : "delete",
							text:'删除',
							scope : me,
							tooltip : '删除字典',
							id : "dict-delete"
						}];
				me.columns = [
				        {
							header : '代码',
							dataIndex : 'code',
							flex : 1
						}, {
							header : '名称',
							dataIndex : 'name',
							flex : 1
						}, {
							header : '备注说明',
							dataIndex : 'remark',
							flex : 1
						}];
				me.dockedItems = [{
							xtype : 'pagingtoolbar',
							id:'dictpage',
							dock : 'bottom',
							store : me.store,
							displayInfo : true
						}];

				this.callParent(arguments);
			},
			queryUserName:function(){				
				this.store.reload({params: {start:0,page:1}});
				Ext.getCmp('dictpage').moveFirst();
			},
			listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('dict-edit').setDisabled(!records.length);
                	Ext.getCmp('dict-delete').setDisabled(!records.length);
                }				
			}
		});
