import * as CC from "../cc.js"
import * as Common  from "../common.js"
import { fetchPutForm,fetchPost,fetchGet }  from "../fetch_utils.js"
import * as MeeUtils  from "../mee_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen3_config/find_by_id",
        "base": ctxPath+"/code_gen3_config",
    }
};

function init(){
    Common.init(module);
}

function add(){
    let form = document.querySelector("#data_form");
    let json = MeeUtils.formToJson(form);
    console.log(JSON.stringify(json));
    fetchPost(ctxPath+"/code_gen3_config/add",json,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            window.close();
            return;
        }
        alert( (res && res.msg)? res.msg : "添加失败");
    });
}

function update(){
    let form = document.querySelector("#data_form");
    let json = MeeUtils.formToJson(form);
    fetchPutForm(ctxPath+"/code_gen3_config/update",form,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            window.close();
            return;
        }
        alert( (res && res.msg)? res.msg : "修改失败");
    });
}

export { init,add,update }