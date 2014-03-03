Ext.define('AM.view.portlet.VisitsPortlet', {

			extend : 'Ext.panel.Panel',
			alias : 'widget.visitsportlet',

			requires : ['Ext.data.Store', 'Ext.chart.theme.Base',
					'Ext.chart.series.Series', 'Ext.chart.series.Line',
					'Ext.chart.axis.Numeric'],

			initComponent : function() {
				var me=this;
				var timeBox = Ext.create('Ext.data.Store', {
							fields : ['type', 'name'],
							data : [{
										"type" : "hour",
										"name" : "小时"
									}, {
										"type" : "day",
										"name" : "天"
									}, {
										"type" : "week",
										"name" : "周"
									}]
						});
				var chartStore = Ext.create('AM.store.portlet.Visits');
				this.tbar = ['->', {
							xtype : 'combobox',
							store : timeBox,
							width:100,
							queryMode : 'local',
							displayField : 'name',
							valueField : 'type',
							value:'hour',
							fieldLabel: '周期',
							labelWidth: 40,
							listeners:{
                              scope: me,
                              'select': me.flushChart
                           }
						}];
				Ext.apply(this, {
							layout : 'fit',
							height : 300,
							items : {
								xtype : 'chart',
								animate : false,
								shadow : false,
								store : chartStore,
								legend : {
									position : 'bottom'
								},
								axes : [{
											type : 'Numeric',
											position : 'left',
											fields : ['users'],
											title : '独立用户数',
											label : {
												font : '11px Arial'
											}
										}, {
											type : 'Numeric',
											position : 'right',
											grid : false,
											fields : ['sessions'],
											title : '访问次数',
											label : {
												font : '11px Arial'
											}
										}, {
											type : 'Category',
											position : 'bottom',
											fields : ['stat_time'],
											title : '时间周期',
											grid : true

										}],

								series : [{
											type : 'line',
											lineWidth : 1,
											showMarkers : false,
											// fill : true,
											axis : 'left',
											xField : 'stat_time',
											yField : 'users',
											style : {
												'stroke-width' : 1,
												stroke : 'rgb(148, 174, 10)'

											}
										}, {
											type : 'line',
											lineWidth : 1,
											showMarkers : false,
											axis : 'right',
											xField : 'stat_time',
											yField : 'sessions',
											style : {
												'stroke-width' : 1,
												stroke : 'rgb(17, 95, 166)'

											}
										}]
							}
						});

				this.callParent(arguments);
			},
			flushChart:function(combo, records, eOpts){
				var selectedValue=records[0].get('type');
				console.log(selectedValue);
				this.down('chart').getStore().reload({params:{type:selectedValue}});
			}
		});
