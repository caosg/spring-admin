
Ext.Loader.setConfig({ enabled: true});
Ext.Loader.setPath('Ext.ux', 'extjs/ux');
Ext.Loader.setPath('AM','app');
Ext.QuickTips.init();

Ext.application({
			name : 'AM',
			appFolder : 'app',
			launch:function(){
              this.controllers.addListener('add',this.newControllerAdded,this);
             
            },

            newControllerAdded:function(idx, ctrlr, token){
                ctrlr.init();
             },
			// automatically create an instance of AM.view.Viewport
		    autoCreateViewport : true,
			controllers : ['sys.Main']
			
		});
