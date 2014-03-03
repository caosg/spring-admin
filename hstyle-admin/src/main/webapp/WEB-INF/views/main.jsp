<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hstyle.admin.web.utils.SysContext"%>
<%@ page import="com.hstyle.framework.utils.PropertiesLoader"%>
<%@ page import="java.text.DecimalFormat"%>
<%
	// The java runtime
	Runtime runtime = Runtime.getRuntime();

	double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
	double maxMemory = (double) runtime.maxMemory() / (1024 * 1024);
	double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
	double usedMemory = totalMemory - freeMemory;
	double percentFree = ((maxMemory - usedMemory) / maxMemory) * 100.0;
	double percentUsed = 100 - percentFree;
	int percent = 100 - (int) Math.round(percentFree);

	DecimalFormat mbFormat = new DecimalFormat("#0.00");
	DecimalFormat percentFormat = new DecimalFormat("#0.0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>应用管理系统</title>
<!--extjsCSS-->
<link rel="stylesheet" type="text/css"	href="extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/css/CheckHeader.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/css/ItemSelector.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/css/GroupTabPanel.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/css/LiveSearchGridPanel.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/css/TabScrollerMenu.css" />
<link rel="stylesheet" type="text/css"	href="extjs/ux/DataView/DragSelector.css" />
<!-- custom CSS -->
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="resources/css/portal.css" />

<!--extjs-->
<script type="text/javascript" src="extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="app/global.js"></script>
<script type="text/javascript" src="app.js"></script>
<!-- swfupload -->
<script type="text/javascript" src="resources/swfupload/swfupload.js"></script>
<script type="text/javascript" src="resources/swfupload/swfupload.queue.js"></script>
<!-- lodop打印控件 -->
<script language="javascript" src="resources/lodop/LodopFuncs.js"></script>

<style type="text/css">
<!--
.td_norepeat {
	background-image: url(resources/images/top.png);
	background-repeat: no-repeat;
}

#clock {
	color: white;
	font-size: 14px;
	float: right;
}

#info {
	float: right
}

-->
.X3 {
	font-size: 200%;
}

.C5 {
	font-size: 600%;
	color: blue;
	display: inline-block;
	border-radius: 8px;
}
</style>
<script type="text/javascript">
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
var contextPath='${pageContext.request.contextPath}';
var footerText ='<%=PropertiesLoader.getProperty("footer.copyright.text", "Copyright © 2013 HSTYLE Inc. All Rights Reserved. 韩都衣舍 版权所有")%>';
var memoryHtml=''+
	'<table cellpadding="0" cellspacing="0" border="0" width="90%">'+
'<tr valign="middle">'+
    '<td width="89%" valign="middle">'+
        '<div class="bar">'+
        '<table cellpadding="0" cellspacing="0" border="0" width="100%" style="border:1px #666 solid;">'+
       '<tr>'+
            <%if (percent == 0) {%>

               ' <td width="100%"><img src="resources/images/percent-bar-left.gif" width="100%" height="8" border="0" alt=""></td>'+

            <%} else {%>

                <%if (percent >= 90) {%>

                   '<td width="<%=percent%>%" background="resources/images/percent-bar-used-high.gif"'+
                       ' ><img src="resources/images/blank.gif" width="1" height="8" border="0" alt=""></td>'+

                <%} else {%>

                    '<td width="<%=percent%>%" background="resources/images/percent-bar-used-low.gif"'+
                        '><img src="resources/images/blank.gif" width="1" height="8" border="0" alt=""></td>'+

                <%}%>
                '<td width="<%=(100 - percent)%>%" background="resources/images/percent-bar-left.gif"'+
                    '><img src="resources/images/blank.gif" width="1" height="8" border="0" alt=""></td>'+
            <%}%>
        '</tr>'+
       '</table>'+
        '</div>'+
    '</td>'+
    '<td width="11%" nowrap>'+
        '<div style="padding-left:6px;" class="c2">'+
        '<%=mbFormat.format(usedMemory)%>MB of <%=mbFormat.format(maxMemory)%>MB (<%=percentFormat.format(percentUsed)%>%)'+
        '</div>'+
    '</td>'+
'</tr>'+
'</table>'+
'</td>'+
'</tr>'+
'</tbody>'+
'</table>';
 //应用全局配置
 Ext.ns('AM');
 //当前用户
 AM.user={
	name:'${usercontext.user.userName}',
	isAdmin:'${usercontext.user.admin}',
	//所有的菜单功能权限code集合
	opts:Ext.JSON.decode('${userOpts}'),
	//一级菜单
	menus:Ext.JSON.decode('${userMenus}')
	
 };
 //服务器信息
 AM.server={
		startTime:'<%=SysContext.START_DATE%>',
		sysJvm:'<%=SysContext.VM_NAME%>',
		sysJvmVersion:'<%=SysContext.VM_VERSION + "--" + SysContext.VM_VENDOR%>',
		serverInfo:'<%=SysContext.APP_SERVER%>',
		os:'<%=SysContext.APP_OS%>',
		local:'<%=SysContext.SYS_LOCAL%>'
 };
 //当前网站用户数、登录数
 AM.visitor={
	loginUsers:'<%=SysContext.CURRENT_LOGIN_COUNT%>',
	maxUsers:'<%=SysContext.MAX_ONLINE_COUNT%>',
	maxDate:'<%=SysContext.MAX_ONLINE_COUNT_DATE%>',
	historyUsers:'<%=SysContext.TOTAL_HISTORY_COUNT%>'
	};
	//服务器端异常
	AM.proxyException = function(proxy, response, opts) {
		var result = jsondecode(response.responseText);
		if (result)
			MsgBoxErr(result.data);
		else
			MsgBoxErr("系统异常！");
	};
	//判断当前用户的权限，启用或禁用按钮；true：禁用 
	AM.disabledOpt = function(button) {
		var opts = AM.user.opts;

		//超级管理员全部启用
		if (AM.user.isAdmin == 'true') {
			button.setDisabled(false);
			Ext.Array.forEach(opts,
					function(item, index, allItems) {
						if (button.id == item.code) {
							if (item.iconClsName != null
									&& item.iconClsName.length > 0)
								button.setIconCls(item.iconClsName);
						}
					});
			return;
		}

		if (opts == null) {
			button.setDisabled(true);
			return;
		}
		var exist = false;
		var icon;
		Ext.Array.forEach(opts, function(item, index, allItems) {
			if (button.id == item.code) {
				exist = true;
				icon = item.iconClsName;

			} else {
				button.setDisabled(true);
			}

		});
		if (exist == true) {
			button.setDisabled(false);
			if (icon != null && icon.length > 0)
				button.setIconCls(icon);

		} else {
			button.setDisabled(true);
			button.setIconCls('disabled');
		}
		/* var i=Ext.Array.indexOf(opts,button.id);
		if(i==-1){
		 button.setDisabled(true);
		// return true;
		}
		else {
		 
		 button.setDisabled(false);
		// return false; 
		} */

	};
	Ext.onReady(function() {
		// Add the additional 'advanced' VTypes
	    Ext.apply(Ext.form.field.VTypes, {
	        daterange: function(val, field) {
	            var date = field.parseDate(val);

	            if (!date) {
	                return false;
	            }
	            if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
	                var start = field.up('form').down('#' + field.startDateField);
	                start.setMaxValue(date);
	                start.validate();
	                this.dateRangeMax = date;
	            }
	            else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
	                var end = field.up('form').down('#' + field.endDateField);
	                end.setMinValue(date);
	                end.validate();
	                this.dateRangeMin = date;
	            }
	            /*
	             * Always return true since we're only using this vtype to set the
	             * min/max allowed values (these are tested for after the vtype test)
	             */
	            return true;
	        },

	        daterangeText: '开始日期必须小于结束日期',

	       
	    });
		Ext.QuickTips.init();
		css = Ext.util.Cookies.get("ThemeCss");
		if (css)
			Ext.util.CSS.swapStyleSheet(null, css);
		//Start a simple clock task that updates a div once per second
		function updateClock() {
			var dt = new Date();
			Ext.fly('clock').update(Ext.Date.format(dt, 'Y-m-d l g:i:s A'));
		}
		var task = Ext.TaskManager.start({
			run : updateClock,
			interval : 1000
		});
	});
</script>

</head>
<body>
	<div id="topDiv">
		<table width="100%" border="0" height=100 cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="td_norepeat" bgcolor="#023D79" align="right">
					<div>
						<div id='clock'></div>
						<div id='info'>
							<font color='white' size="2" face="微软雅黑">${usercontext.user.userName}，你好</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</div>
				</td>

			</tr>
		</table>
	</div>
<object  id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
</body>
</html>