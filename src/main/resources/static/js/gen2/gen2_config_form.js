import * as CC from "../cc.js"
import * as Common  from "../common.js"
import { fetchPutForm,fetchPost,fetchGet,fetchPutJson }  from "../fetch_utils.js"
import * as MeeUtils  from "../mee_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/gen2_config/find_by_id",
        "base": ctxPath+"/gen2_config",
    }
};

function init(){
    Common.init(module);
}

function add(){
    let _form = document.querySelector("#data_form");
    if( false===_form.reportValidity() ){
        console.log("表单校验不通过！");
        return ;
    }
    let json = MeeUtils.formToJson(_form);
    fetchPost(ctxPath+"/gen2_config/add",json,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            window.close();
            return;
        }
        alert( (res && res.msg)? res.msg : "添加失败");
    });
}

function update(){
    let _form = document.querySelector("#data_form");
    if( false===_form.reportValidity() ){
        console.log("表单校验不通过！");
        return ;
    }
    let json = MeeUtils.formToJson(_form);
    fetchPutJson(ctxPath+"/gen2_config/update",json,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            window.close();
            return;
        }
        alert( (res && res.msg)? res.msg : "修改失败");
    });
}

export { init,add,update }