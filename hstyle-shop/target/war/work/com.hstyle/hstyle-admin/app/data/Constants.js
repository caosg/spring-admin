Ext.define('AM.data.Constants', {

    singleton: true,

    sysMarkup: '<div class="portlet-content"><p><b>服务器启动时间: </b>'+AM.server.startTime+"</p><br>"+
    '<p><b>系统JVM: </b>'+AM.server.sysJvm+'</p><br>'+
    '<p><b>JVM版本: </b>'+AM.server.sysJvmVersion+'</p><br>'+
    '<p><b>服务器信息:</b> '+AM.server.serverInfo+'</p><br>'+
    '<p><b>服务器操作系统:</b> '+AM.server.os+'</p><br>'+
    '<p><b>本地语言: </b>'+AM.server.local+'</p><br>'+
    '<p><b>内存信息:</b></p>'+memoryHtml+'</div>',


    userMarkup: '<div class="portlet-content" align="center"><div class="X3">此时此刻网站上有</div> '+
    '<div class="C5">'+AM.visitor.loginUsers+'</div>'+
    '<div>位登录用户</div></div>'+
    '<p><b>&nbsp;历史累计用户数: </b> '+AM.visitor.historyUsers+'</p><br>'+
    '<p><b>&nbsp;最大同时在线用户数: </b>'+AM.visitor.maxUsers+'</p><br>'+
    '<p><b>&nbsp;最大用户数时间: </b>'+AM.visitor.maxDate+'</p>'
}); 
