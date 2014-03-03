Ext.define('AM.view.sys.Footer',{ 
    extend:'Ext.Component', 
    padding: 10, 
    alias:'widget.footer', 
    html:'<center>'+(typeof(footerText)=='undefined'?'':footerText)+'</center>',
    region:'south' 
});