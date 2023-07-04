define(function (require, exports, module) {
	var dicts = require("dicts");
	function init(){
		module.tablex = require("common");
		module.tablex.init({
			url:app + "/sys/user",
			idField:"id",
			form:{
				title:"用户管理",
				fields:[
		                <#list columns as c>
		                <#if c??><#if "1"==c.is_pk>
		                  {name:"${c.column_name}",type: "hidden",label:"${(c.column_comment)!c.java_field}"}<#if c_has_next>,</#if>
		                  <#else>
		                  {name:"${c.column_name}",label:"${(c.column_comment)!c.java_field}",width:4,validate:{required:${("1"==c.is_required)?string("required","false")}} }<#if c_has_next>,</#if>
		                  </#if>
		                </#if></#list>
				        ]},
			events:{form0:form0},
			toolbar:{"unlock":unlock}
		});
		module.tablex.doQuery();
	}

	function form0(form){
		form.find("input[name=password]").attr({type:"password"});
	}
    function unlock(event){
        if (event){
            event.preventDefault();
        }
        let data = module.tablex.getSelected();
        if(0==data.length){
           alert("请选择一条记录");
           return;
        }
       let formData = new FormData();
       // 韩束抖音投放日报
       formData.append("user_name",data[0].user_name);

        fetch(app + "/sys/user/unlock",{
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data =>
            function () {
                alert(data.msg);
            }()
        )
        .catch(error => console.log("请求超时,请刷新后重试~")
        );
    }
	exports.init = init;
});