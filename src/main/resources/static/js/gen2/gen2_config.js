import * as Common  from "../common.js"
import * as FetchUtils  from "../fetch_utils.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{ "new_config":doNewConfig },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{"preview":doPreview,"delete":doDelete,"edit_config":doEditConfig,"enable":doEnable},
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/gen2_config/list",
        /* 添加：POST请求 */
        "add": ctxPath+"/gen2_config/add",
        /* 删除: DELETE请求 */
        "delete": ctxPath+"/gen2_config/delete",
        /* 修改: PUT请求 */
        "mod": ctxPath+"/gen2_config/update",
        "base": ctxPath+"/gen2_config",
    }
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
    let table_id = data.table_id;
    FetchUtils.fetchDelete(ctxPath+"/gen2_config/delete?id="+data.id,"{}",function(res){
       if(!res || res.status!==1){
            alert(!res?"操作失败":res.msg);
        }
         // 回调后执行一次查询
        Common.doQuery();
    });
}

// 预览
function doPreview(item,idx,elem){
     if (event) {  event.preventDefault();   }
    window.open(ctxPath+'/gen2_config/gen2_preview.html?table_id='+item.table_id,'_blank','channelmode=yes,fullscreen=yes')
};

// 修改添加生成配置
function doNewConfig(){
    let win = window.open(ctxPath+'/gen2_config/gen2_config_form.html?type=1','_blank','channelmode=yes,width=800px,left=320px,top=80px');
    // 关闭窗口后事件回调
    win.onbeforeunload = function(){
        //debugger;
        console.log("关闭窗口...");
        //alert("关闭窗口...");
        // 回调后执行一次查询
        Common.doQuery();
    }
}

// 修改生成配置
function doEditConfig(data,idx,elem){
    let win = window.open(ctxPath+'/gen2_config/gen2_config_form.html?type=2&id='+data.id,'_blank','channelmode=yes,width=800px,left=320px,top=80px');
    // 关闭窗口后事件回调
    win.onbeforeunload = function(){
        // 回调后执行一次查询
        Common.doQuery();
    }
}

// 启用配置
function doEnable(data,idx,elem){
    let table_id = data.table_id;
    FetchUtils.fetchPost(ctxPath+"/gen2_config/enable",data,function(res){
        if(!res || res.status!==1){
            alert(!res?"操作失败":res.msg);
        }
         // 回调后执行一次查询
         Common.doQuery();
    });
}

export { init,module }
