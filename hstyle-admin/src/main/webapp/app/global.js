//ajax提交封装
function doAjax(url,params,successfn,scope,timeout)
{
   return (Ext.Ajax.request({
	    url:url,
	    params:params,
	    method:'POST',
	    success: function(response){
	        if(successfn!=null&&successfn){
	    	  var result=jsondecode(response.responseText);
	    	  if(result)
	    	  {
		        if(result.success)
		        {
		        	if(successfn)
		        	successfn(result.data,scope);
		        }
		        else
		        { 
		        	MsgBoxErr(result.data);
		        	failurefn();
		        }
	    	  }
	    	}
	    	
	    },
	    failure:function(response){
	      if(response==null||response.responseText.length==0) return;	      
	      var result =jsondecode(response.responseText);
	      if(result)
	      {
		      MsgBoxErr(result.data);
		      
	      }
	    },
	    scope:scope,
	    timeout:timeout?timeout:30000
   }));
}
function jsondecode(jsontext,errmsg)
{
	var result;
	try
	{   
		result=Ext.JSON.decode(jsontext);
	}
	catch(e)
	{
		result=false;
		console.log(e);
	}
	return result;
}

//获取record被修改的属性字段名[]
function getModifiedFieldNames(record)
{
    var modified = [];
    for(var i=0;i<record.fields.keys.length;i++)
    {
    	if(record.isModified(record.fields.keys[i]))
    	{
    		modified.push(record.fields.keys[i]);
    	}
    }
    return modified; 
}
//返回参数json对象集合
function getFilterList(keylist,record)
{
   var parameters=[];
   for(i=0;i<keylist.length;i++)
   {
   	  parameters.push(getFilter(keylist[i],'=',record.get(keylist[i])));
   }
   return parameters;
}
function getFilter(sname,soperate,svalue)
{
	return {name:sname,operate:soperate,value:svalue};
}
//返回字段json对象集合
function getFieldList(fiedlist,record)
{
   var fields=[];
   for(i=0;i<fiedlist.length;i++)
   {
   	  fields.push(getField(fiedlist[i],record.get(fiedlist[i])));
   }
   return fields;  
}
//返回字段json对象
function getField(sname,svalue)
{
	return {name:sname,value:svalue};
}
//警告提示框
function MsgBoxWar(MsgText,callback)
{   
	Ext.Msg.show({
		title : '警告',
		msg : MsgText,
		model:true,
		fn:callback,
		buttons : Ext.Msg.OK,
		icon : Ext.Msg.WARNING
	});
}
//错误提示框
function MsgBoxErr(ErrText,callback)
{   
	Ext.Msg.show({
		title : '错误',
		msg : ErrText,
		model:true,
		fn:callback,
		buttons : Ext.Msg.OK,
		icon : Ext.Msg.ERROR
	});
}
//提示提示框
function MsgBoxInfo(InfoText,callback)
{   
	Ext.Msg.show({
		title : '提示',
		msg : InfoText,
		model:true,
		fn:callback,
		buttons : Ext.Msg.OK,
		icon : Ext.Msg.INFO
	});
}
//询问提示框
function MsgBoxQst(MsgText,callback)
{   
	Ext.Msg.show({
		title : '询问',
		msg : MsgText,
		model:true,
		buttons : Ext.Msg.YESNO,
		fn:callback,
		icon : Ext.Msg.QUESTION
	});
}
//输入提示框 callback(id,msg)
function MsgBoxInput(MsgText,callback)
{   
	Ext.Msg.show({
		title : '输入',
		msg : InfoText,
		model:true,
		prompt:true,
		buttons : Ext.Msg.YESNO,
		fn:callback,
		icon : Ext.Msg.INFO
	});
}
//等待进度条
function ProgressBarWait()
{
	Ext.Msg.show({
	  title:'请等待',  
	  msg:'正在工作中.....',  
	  width:240,  
	  progress:true,  
	  closable:false,
	  animate:true
	});
}
//状态渲染
function stateRender(val){
		if(val=='0')
		  return '<span style="color:green;">启用</span>';
		else if(val=='1')
		  return '<span style="color:red;">禁用</span>';
		else if
		  (val=='2')
		  return '<span style="color:black;">删除</span>';
	}