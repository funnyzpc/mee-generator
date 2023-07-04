//import * as CC from "../../cc.js"
import * as Common  from "../common.js"
import * as FetchUtils  from "../fetch_utils.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 分页表单数据(可选) */
    search_form: {"page_no":0,"page_size":10},
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{ "import_table":doImportTable },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{"preview":doPreview,"delete":doDelete},
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen2/list",
        /* 添加：POST请求 */
        add: ctxPath+"/code_gen2/add",
        /* 删除: DELETE请求 */
        del: ctxPath+"/code_gen2/delete",
        /* 修改: PUT请求 */
        mod: ctxPath+"/code_gen2/update",
        "base": ctxPath+"/code_gen3_config",

    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
    data:[],
};

// 初始化通用模块
function init(){
    Common.init(module);
}

/*
 对于表格体的数据的操作，所有的事件均会有这三个参数传入:
    data: 当前行数据(object)
    idx:  当前行所在所有list数据的索引位置，0表示第一行
    elem: 这个是表单参数
*/
function doDelete(data,idx, elem){
    FetchUtils.fetchDelete(ctxPath+"/code_gen2/"+data.id,"",function(res){
        //alert(res);
         // 回调后执行一次查询
         Common.doQuery();
    });
}

// 预览
function doPreview(item,idx,elem){
     if (event) {  event.preventDefault();   }
    window.open(ctxPath+'/code_gen2/gen2_preview.html?table_id='+item.id,'_blank','channelmode=yes,fullscreen=yes')
};

// 导入表
function doImportTable(){
    let win = window.open(ctxPath+'/code_gen2/gen2_import.html','_blank','channelmode=yes,width=800px,left=320px,top=80px');
    // 关闭窗口后事件回调
    win.onbeforeunload = function(){
        console.log("关闭窗口...");
        //alert("关闭窗口...");
        // 回调后执行一次查询
        Common.doQuery();
    }
}
// 编辑
function edit(table_id){
    window.open(ctxPath+'/code_gen2/gen2_edit.html?table_id='+item.table_id,'_blank','channelmode=yes,fullscreen=yes')
};

export { init,module }
