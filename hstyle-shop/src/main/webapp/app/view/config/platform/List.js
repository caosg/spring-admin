/**
 * 平台列表
 */
Ext.require([
    'Ext.ux.RowExpander'
]);
var obj;

Ext.define('AM.view.config.platform.List', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.platformlist',
			title : "平台列表",
			id : 'platformlist',
			store : 'config.PlatformS',
			region:'center',
			loadMask: true,
			selType:'checkboxmodel',//选择框组件  
            multiSelect:true,//允许选择框多选 
			columnLines: true,
			/**
            plugins: [{
                ptype: 'rowexpander',
                rowBodyTpl : [
                          //'{% sign == -1 %}',
                          '<table width="50%" border="1" cellpadding="0" cellspacing="1" bordercolor="#d4d8e1" bgcolor="#d4d8e1" style=" margin:10px;background-color:#d4d8e1;line-height:22px; text-align:center; font-size:12px; color:#627da1;">',
                          '<tpl for="shops">',//循环子表数组对象
	                          '<tpl if="status &gt; 0">',//如果是非逻辑删除了的数据就显示
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
                          		 	    '{shopcode}',
                          	    	 '</td>',
	                          		 '<td style="{[xindex % 2 === 0 ? "background-color:#f6f7f9;" : "background-color:#edf2f6;"]}">',
	                          		    '{shopname}',
	                          		 '</td>',
	                          	  '</tr>', 
	                          '</tpl>',    
                          '</tpl>',
	                      '</table>'   
                ]
            }],  
            */          
			viewConfig : {
				enableTextSelection: true						
			},
			initComponent : function() {
				var me = this;
				me.tbar = [{
							iconCls : "add",
							text:'增加',
							scope : me,
							tooltip : '增加',
							id : "platformAdd"
						},'-', {
							iconCls : "edit",
							text:'编辑',
							scope : me,
							tooltip : '编辑',
							id : "platformEdit",
							disabled : true
						},'-', {
							iconCls : "delete",
							text:'删除',
							scope : me,
							tooltip : '删除',
							id : "plat-delete",
							disabled : true
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
							header : '外部代码',
							dataIndex : 'outercode',
							flex : 1
						}];
				me.dockedItems = [{
							xtype : 'pagingtoolbar',
							id:'platformpage',
							dock : 'bottom',
							store : me.store,
							displayInfo : true
						}];

				this.callParent(arguments);
			},
			queryUserName:function(){				
				this.store.reload({params: {start:0,page:1}});
				Ext.getCmp('platformpage').moveFirst();
			},
			listeners: {
                'selectionchange': function(view, records) {
                	Ext.getCmp('platformEdit').setDisabled(!records.length);
                	Ext.getCmp('plat-delete').setDisabled(!records.length);
                },
                'render':function( grid, eOpts ){
                	grid.store.load({
    		    		action:'read',
    					params : {
    						page : 1,
    						start:0,
    						limit:25
    					}                		
                	});
                }	                
			}
		});
