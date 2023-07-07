import * as Common  from "../common.js"
import * as FetchUtils  from "../fetch_utils.js"
import * as MeeUtils  from "../mee_utils.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:false,
    /* 分页表单数据(可选) */
    search_form: {"page_no":0,"page_size":21},
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{ "exec":doExec },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{"preview":doPreview,"delete":doDelete},
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page":     ctxPath+"/db_handler/query",
        "export":   ctxPath+"/db_handler/export",
        "base":     ctxPath+"/db_handler",
    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
    data:[],
};

// 初始化通用模块
function init(){
    Common.init(module);
}

function doExec(){
    let form_id = Common.global_module.search_form_id;
    let list_id = Common.global_module.data_list_id;
    let search_form_dom = document.querySelector(`#${form_id} form`);
    //let param = MeeUtils.formToJson( search_form_dom );
    //Common.global_module.search_form._selected=[]; // 重置为空，否则分页后数据仍保留
    document.querySelector(`#${list_id}`).innerHTML="<div class=\"loading\"></div>";
    FetchUtils.fetchFormPost(ctxPath+"/db_handler/exec",search_form_dom,function (d){
        // 登录超时
        if(!d || 0===d.status){
           alert(!d?"登入超时":d.msg);
           return;
        }
        // 保存至业务模块
        Common.global_module.data=d.data;
        try{
            //toPageData(d);
            Common.showList(d);
            //initBodyEvent();
            //initPageEvent();
        }catch(error){
            console.error(error);
        }
    });
}

/*
 对于表格体的数据的操作，所有的事件均会有这三个参数传入:
    data: 当前行数据(object)
    idx:  当前行所在所有list数据的索引位置，0表示第一行
    elem: 这个是表单参数
*/
function doDelete(data,idx, elem){
    FetchUtils.fetchDelete(ctxPath+"/db_handler/"+data.id,"",function(res){
        //alert(res);
         // 回调后执行一次查询
         Common.doQuery();
    });
}

// 预览
function doPreview(item,idx,elem){
     if (event) {  event.preventDefault();   }
    window.open(ctxPath+'/db_handler/gen2_preview.html?table_id='+item.id,'_blank','channelmode=yes,fullscreen=yes')
};


// 编辑
function edit(table_id){
    window.open(ctxPath+'/db_handler/gen2_edit.html?table_id='+item.table_id,'_blank','channelmode=yes,fullscreen=yes')
};

export { init,module }
