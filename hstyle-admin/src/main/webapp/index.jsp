<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hstyle.admin.service.support.SystemUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>管理系统</title>
<!--引入extjs-CSS-->
<link rel="stylesheet" type="text/css"
	href="extjs/resources/css/ext-all.css" />
<!--引入extjs-->
<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="app/global.js"></script>
<script>
<% if(SystemUtils.isAuthenticated())	
	response.sendRedirect("main.do");
   
%>
	Ext.Loader.setConfig({
		enabled : true,
		paths : {
			'AM' : 'app'
		}
	});
	Ext.Loader.setPath('Ext.ux', '/extjs/ux');
	Ext.require('AM.Login');
	Ext.onReady(function() {
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
			expires : new Date(new Date().getTime() + (1000 * 60 * 60))
		}));

		AM.Login.show();

	});
</script>
</head>
<body>

</body>

</html>
