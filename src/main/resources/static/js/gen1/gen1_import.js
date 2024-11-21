import * as CC from "../cc.js" // handlebar 及通用helper
import * as Common  from "../common.js" // 通用api
import * as MeeUtils  from "../mee_utils.js" // 公用工具类
import * as FetchUtils  from "../fetch_utils.js" // fetch封装工具类

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen1_import/list",
        "base": ctxPath+"/code_gen1_import",
    }
};

// 初始化通用模块
function init(){
    Common.init(module);
}

// 提交表
function saveTable(){
    let table_list = Common.global_module.search_form._selected;
    if(!table_list || table_list.length<=0){
        alert("请至少选择一个!");
        return;
    }
    FetchUtils.fetchPost(ctxPath+"/code_gen1_import/save_table",table_list,function(res){
        if( !res || 1!==res.status){
            alert( !res?"未知错误...":res.msg);
            return;
        }
        window.close();
    });
}

export { init,module,saveTable }
