import * as CC from "../cc.js"
import * as Common  from "../common.js"
import * as MeeUtils  from "../mee_utils.js" // 公用工具类
import * as FetchUtils  from "../fetch_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen1/find_by_table_id",
        "base": ctxPath+"/code_gen1",
    }
};

// init
function init(){
    Common.init(module);
}
function saveData(){
    let form = document.querySelector("#data_form");
    let json = MeeUtils.formToJson(form);
    FetchUtils.fetchPutForm(ctxPath+"/code_gen1/gen1_generate/update",form,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            return;
        }
        alert( (res && res.msg)? res.msg : "保存失败");
    });
}
export { init ,saveData }
