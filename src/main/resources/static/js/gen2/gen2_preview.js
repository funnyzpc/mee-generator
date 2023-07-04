import * as CC from "../cc.js"
import * as Common  from "../common.js"
import * as FetchUtils  from "../fetch_utils.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 分页表单数据(可选) */
    search_form: {"page_no":0,"page_size":10},
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{ },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{/*"preview":doPreview,*/"doSwitchBar":doSwitchBar},
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/code_gen2/preview/",
        "base": ctxPath+"/code_gen2",
    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
    data:[],
};

// 初始化通用模块
function init(table_id){
    module.api.page=module.api.page+table_id;
    Common.init(module);
}

//// 预览
//function doPreview(item,idx,elem){
//    // if (event) {  event.preventDefault();   }
//    window.open(module.ctx_path+'/code_gen2/gen2_preview.html?table_id='+item.table_id,'_blank','channelmode=yes,fullscreen=yes')
//};

// 切换bar
function doSwitchBar(code,elem){
    //  导航颜色
    elem.style.backgroundColor="green";
    elem.style.color="white";
    // pre展示
    document.querySelector("#"+code).style.display="block";
    let bars = elem.parentElement.querySelectorAll("li");
    bars.forEach(function (elem_item){
        let data_id = elem_item.getAttribute("data-id");
        if(data_id!==code){
            // 隐藏
            elem_item.style.removeProperty("background-color");
            elem_item.style.color="blue";
        }
    });
    // 代码块切换
    let codes = document.querySelector("#"+code).parentElement.querySelectorAll("pre")
    codes.forEach(function (elem_item){
            let data_id = elem_item.getAttribute("id");
            if(data_id!==code){
                // 隐藏
                elem_item.style.display="none";
            }
        });

}

export { init,module,doSwitchBar }
