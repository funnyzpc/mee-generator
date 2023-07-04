import * as CC from "../cc.js"
import * as Common  from "../common.js"
import * as MeeUtils  from "../mee_utils.js" // 公用工具类
import * as FetchUtils  from "../fetch_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen3/find_by_table_id",
        "base": ctxPath+"/code_gen3",
    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
//    data:[],
};
function init(){
    Common.init(module);
}
function saveData(){
    let form = document.querySelector("#data_form");
    let json = MeeUtils.formToJson(form);
    FetchUtils.fetchPutForm(ctxPath+"/code_gen3/gen3_generate/update",form,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            return;
        }
        alert( (res && res.msg)? res.msg : "保存失败");
    });
}
export { init ,saveData }
