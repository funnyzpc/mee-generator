import * as CC from "../cc.js"
import * as Common  from "../common.js"
import { fetchPutForm,fetchGet }  from "../fetch_utils.js"
import * as MeeUtils  from "../mee_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen3/find_by_table_id",
        "base": ctxPath+"/code_gen3",
    }
};

function init(){
    Common.init(module);
}

function saveData(){
    let form = document.querySelector("#data_form");
    let json = MeeUtils.formToJson(form);
    fetchPutForm(ctxPath+"/code_gen3/gen3_table/update",form,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            window.open(module.ctx_path+'/code_gen3/gen3.html','_self')
            return;
        }
        alert( (res && res.msg)? res.msg : "保存失败");
    });
}
export { init ,saveData }