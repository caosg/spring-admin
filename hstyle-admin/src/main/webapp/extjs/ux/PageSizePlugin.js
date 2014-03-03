/**
 * @author: Zh_Pan
 * @last modify: 2014-01-10
 * @memo: a plugin for setting the 'pageSize' of a paging toolbar
 */
Ext.define('Ext.ux.PageSizePlugin', {
	alias: 'plugin.pagesizeplugin',
	maximumSize: 200,
	beforeText: '每页显示',
	afterText: '条记录',
	limitWarning: '不能超出设置的最大分页数：',
	constructor: function(config) {
		var me = this;
		Ext.apply(me, config);
	},
	init: function(paging) {
		var me = this;
		me.combo = me.getPageSizeCombo(paging);
		paging.add(' ', me.beforeText, me.combo, me.afterText, ' ');
		me.combo.on('select', me.onChangePageSize, me);
		me.combo.on('keypress', me.onKeyPress, me);
	},
	getPageSizeCombo: function(paging) {
		var me = this,
			defaultValue = paging.pageSize || paging.store.pageSize || 25;
		return Ext.create('Ext.form.field.ComboBox', {
			store: new Ext.data.SimpleStore({
				fields: ['text', 'value'],
				data: me.sizeList || [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200]]
			}),
			mode: 'local',
			displayField: 'text',
			valueField: 'value',
			allowBlank: true,
			triggerAction: 'all',
			width: 50,
			maskRe: /[0-9]/,
			enableKeyEvents: true,
			value: defaultValue
		});
	},
	onChangePageSize: function(combo) {
		var paging = combo.up('standardpaging') || combo.up('pagingtoolbar'),
			store = paging.store,
			comboSize = combo.getValue();
		store.pageSize = comboSize;
		store.loadPage(1);
	},
	onKeyPress: function(field, e) {
		if(Ext.isEmpty(field.getValue())) {
			return;
		}
		var me = this,
			fieldValue = field.getValue(),
			paging = me.combo.up('standardpaging') || me.combo.up('pagingtoolbar'),
			store = paging.store;
		if(e.getKey() == e.ENTER) {
			if(fieldValue <= me.limitWarning) {
				store.pageSize = fieldValue;
				store.loadPage(1);
			} else {
				Ext.MessageBox.alert('警告', "每页不允许超过"+me.limitWarning);
				field.setValue('');
			}
		}
	},
	destory: function() {
		var me = this;
		me.combo.clearListeners();
		Ext.destroy(me.combo);
		delete me.combo;
	}
});