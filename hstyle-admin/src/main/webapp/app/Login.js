Ext.define("AM.Login", {
			extend : "Ext.window.Window",
			hideMode : 'offsets',
			closeAction : 'hide',
			closable : false,
			resizable : false,
			title : '登录窗口',
			singleton : true,
			width : 300,
			height : 250,
			modal : true,
			currentTabIndex : 0,
			dockedItems : [],
            formIcon:'resources/images/icon/login_t.jpg',
			initComponent : function() {
				var me = this;
				me.fields = [];
				var banner=Ext.widget('box',{
				   autoEl:{
				   	html:'<div><img src="'+me.formIcon+'" /></div>'
				   },
				   style:' font-size:1.5em;width:100%;text-align:center;padding:15px;'
				});
				me.fields.push(Ext.widget("textfield", {
									fieldLabel : "用户名",
									name : "username",
									allowBlank : false,
									tabIndex : 1,									
									listeners : {
										scope : me,
										focus : me.setTabIndex
									}
								}));

				me.fields.push(Ext.widget("textfield", {
									fieldLabel : "密码",
									name : "password",
									inputType : "password",
									allowBlank : false,	
									style:'margin-top:10px',
									tabIndex : 2,
									minLength:6,
									vtype:'alphanum',
									listeners : {
										scope : me,
										focus : me.setTabIndex,
										specialkey: function(field, e){
                                       // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
                                         // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
                                          if (e.getKey() == e.ENTER) {
                                            me.onLogin();
                                          }
                                       }
									}
								}));
				/*var rememberMe=Ext.widget('checkboxfield',{
					      fieldLabel : "记住我",
				          //boxLabel  : '记住我',
                          name      : 'rememberMe',
                          inputValue: '1'
				         }
				       );*/
                
				/*me.fields.push(Ext.widget("textfield", {
									fieldLabel : "验证码",
									name : "jcaptcha",
									minLength : 7,
									maxLength : 7,
									allowBlank : false,
									tabIndex : 3,
									listeners : {
										scope : me,
										focus : me.setTabIndex
									}
								}));

				me.image = Ext.create(Ext.Img);*/

				me.form = Ext.create(Ext.form.Panel, {
							border : false,
							bodyPadding : 5,
							url : "login.do",
							bodyStyle : "background:#DFE9F6",
							height:200,
							fieldDefaults : {
								labelWidth : 80,
								labelAlign:'right',
								labelSeparator : "：",
								
								anchor : "90%"
							},
							items : [banner,me.fields[0],me.fields[1]/*,me.fields[2],
									{
										xtype : "container",
										height : 80,
										anchor : "-5",
										layout : "fit",
										items : [me.image]
									}*/],
							dockedItems : [{
										xtype : 'toolbar',
										dock : 'bottom',
										ui : 'footer',
										layout : {
											pack : "center"
										},
										items : [{
													text : "登录",
													width:60,
													disabled : true,
													formBind : true,
													handler : me.onLogin,
													scope : me
												}, {
													text : "重置",
													width:60,
													handler : me.onReset,
													scope : me
												}/*, {
													text : "刷新验证码",
													handler : me.onRefrehImage,
													scope : me
												}*/]
									}]
						});

				me.items = [me.form];

				me.on("show", function() {
							me.onReset();
							me.fields[0].focus(false,100);
						}, me);

				me.callParent();
			},

			/*initEvents : function() {
				var me = this;
				me.KeyNav = Ext.create("Ext.util.KeyNav", me.form.getEl(), {
							enter : me.onFocus,
							scope : me
						});
			},*/

			onLogin : function() {
				var me = this, f = me.form.getForm();
				if (f.isValid()) {
					f.submit({
								success : function(form, action) {
									window.location = "main.do";
									
								},
								failure : function(form, action) {
									if (action.failureType === "connect") {
										Ext.Msg.alert(
														'错误',
														'状态:'
																+ action.response.status
																+ ': '
																+ action.response.statusText);
										return;
									}
									if (action.result) {
                                       Ext.Msg.show({	
                                       	  title:'提示',
		                                  msg : action.result.data,
		                                  model:true,		
		                                  buttons : Ext.Msg.OK,
		                                  icon : Ext.Msg.WARNING
	                                   });
										
									}
									//this.image.setSrc("jcaptcha.jpg?_dc=" + (new Date()).getTime());
								},
								scope : me
							});
				}
			},

			onReset : function() {
				var me = this;
				me.form.getForm().reset();
				me.fields[0].focus();
				//me.onRefrehImage();
			},

		/*	onRefrehImage : function() {
				this.image.setSrc("jcaptcha.jpg?_dc=" + (new Date()).getTime());
			},*/

			setTabIndex : function(el) {
				this.currentTabIndex = el.tabIndex;
			},

			onFocus : function() {
				var me = this, index = me.currentTabIndex;
				index++;
				if (index > 2) {
					index = 0;
				}
				me.fields[index].focus();
				me.currentTabIndex = index;
			}

		});