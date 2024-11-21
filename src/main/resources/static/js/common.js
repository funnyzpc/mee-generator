import * as FetchUtils  from "./fetch_utils.js"
import * as MeeUtils  from "./mee_utils.js"

/*通用api及参数定义，init后会自动合并到各自的模块:module */
var global_module = {
    /* 默认是否执行分页查询 */
    default_query:false,
    /* 著录(修改/新增)表单结构定义 */
    form_struct:{ on:false },
    /* 分页表单的默认参数 _selected:已选择的数据体id，一般定义多选框需要此参数*/
    search_form:{"page_no":0,"page_size":10,"_current_page_size":0,"_total":0,"_page_count":0,"_selected":[]},
    /* 主键字段,如果module定义了则会覆盖此字段 */
    id_field:"id",
    /* 查询表单参数 */
    events:{"query": doQuery, "export":doExport,"new":doNew,"previous_page":previousPage,"first_page":firstPage,"next_page":nextPage},
    /* 分页数据行事件 */
    data_events:{"select_one":doSelectOne,"select_all":doSelectAll},
    // 搜索框ID，可由外层module定义
    search_form_id:"data-search",
    // 分页列表ID，可由外层module定义
    data_list_id:"data-list",
    // 模板ID，可由外层module定义
    template_id:"template-data-list",
    // 这里的data可能是Object也可能是Array,所以这里不申明
    // data:[],
    api:{
        /* 列表分页查询：GET请求 */
        "page":"/list",
        /* 删除: DELETE请求 */
        "delete": "/delete",
        /* 导入：GET请求 */
        "export":"/export",
    }
}

// 初始化
function init(module){
    // 合并事件
    mergeProperty(module);
    /* 初始化查询表单事件 */
    initSearchForm();
    if( global_module.default_query===true ){
        doQuery();
    }
}

// 合并module 用户态的module合并到global_module
function mergeProperty(module){
    // api fill base
    if( module.api["base"] ){
      for( let key in global_module.api ){
        global_module.api[key]=module.api["base"]+global_module.api[key];
      }
    }
    // 合并module数据至global_module
    MeeUtils.assign(module,global_module);
    //console.log(module);
    //console.log(global_module);
}

// 注册查询表单事件
function initSearchForm(){
    let query_id = global_module.search_form_id;
    /* 初始化查询表单事件 */
    let search_form_dom = document.querySelector(`#${query_id} form`);
    // 如果没有查询表单则无需注册事件
    if( !search_form_dom ){
        return;
    }
    let search_form = search_form_dom.querySelectorAll("*[_func]");
    search_form.forEach(function (item){
        let func = item.getAttribute("_func");
        let func_event = item.getAttribute("_func_event");
        //let fn = module.events[name]?module.events[name]:global_module.events[name];
        let fn = global_module.events[func];
        if( fn ){
            // 注册事件,默认就是点击也可以自定义
            item.addEventListener( func_event?func_event:"click",
            function (){
                if ( event && event.preventDefault ) {
                    event.preventDefault();
                }
                //fn(global_module.data[idx],idx,this)
                fn( item,search_form_dom,global_module.search_form )
            }, false);
        }else{
            // reset是不需要注册为单独的方法
            if( func && "reset"!==func){
                alert("ERROR:*[_func="+func+"]方法不存在!");
                throw new Error("ERROR:*[_func="+func+"]方法不存在!");
            }
        }
    });
}

// 数据导出
function doExport(/*event*/){
    //let search_form_region_id = global_module.search_form_region?global_module.search_form_region:"data-search";
    let query_id = global_module.search_form_id;
    /* 初始化查询表单事件 */
    let search_form_dom = document.querySelector(`#${query_id} form`);
    if( false===search_form_dom.reportValidity() ){
        console.log("表单校验不通过！");
        return ;
    }
    // 合并表单数据
    Object.assign(global_module.search_form,MeeUtils.formToJson( search_form_dom ));
    // 构建URL参数
    let param_str = "page_size=10000&page_no=1&"+FetchUtils.buildParam( global_module.search_form  )
    window.open(global_module.api.export+"?"+param_str);
}

function load(param){
    let query_id = global_module.data_list_id;
    global_module.search_form._selected=[]; // 重置为空，否则分页后数据仍保留
    document.querySelector(`#${query_id}`).innerHTML="<div class=\"loading\"></div>";
    FetchUtils.fetchGet(global_module.api.page,param,function (d){
        // 登录超时
        if(!d || 0===d.status){
           alert(!d?"登入超时":d.msg);
           //return window.location.href=app+"/login";
           return;
        }
        // 保存至业务模块
        global_module.data=d.data;
//        // 分页信息
//        d.idx=global_module.search_form.page_no+1;
//        d.page_size=global_module.search_form.page_size;
        try{
            toPageData(d);
            showList(d);
            initBodyEvent();
            initPageEvent();
        }catch(error){
            console.error(error);
        }
    });
}

// search form补充分页信息
function toPageData( d ){
    if( !d ||  !d.data ){
        console.log("不含任何记录信息...");
        return;
    }
    // 即时数据的分页信息
    d.index=global_module.search_form.page_no+1;
    d.page_size=global_module.search_form.page_size;

    // 时间返回的记录数
    global_module.search_form._current_page_size=d.data.length;
}

function initBodyEvent(){
    /* 初始化数据体事件 */
    let body_event = document.querySelector("#data-list").querySelectorAll("table>tbody>tr");
    let idx = 0;
    body_event.forEach(function (item,idx){
        // 自定义事件
        item.querySelectorAll("a[func],button").forEach(function (item){
            let name = item.getAttribute("func");
            if(name && name!=="" /*&& !name.startsWith("window.") */ ){
                // support : `delete` or `delete()` or `delete(param1,param2)`
                name = (-1===name.indexOf("(")) ? name : name.substr(0,name.indexOf("("));
                if( name==="" ){
                    name=item.getAttribute("onClick");// 兼容这样的name: <a href="#" onclick="delete">删除</a>
//                    name=item.getAttribute("func");// 兼容这样的name: <a href="#" onclick="delete">删除</a>
                }
                if( name ){
                    let fn = global_module.data_events[name];
                    if( fn ){
                        // 注册事件
                        item.addEventListener("click",function (){
                            if (event) {  event.preventDefault(); }
                            fn(global_module.data[idx],idx,this)
                        },false)
                    }else{
                        console.log("initBodyEvent::函数未能注册上:"+name);
                        // alert("ERROR:button[name="+name+"]不存在!")
                    }
                }
            }
        });

        // 单选（首列）
        let checkbox_event = item.querySelector("td:first-child input[type=checkbox]");
        if(checkbox_event){
            checkbox_event.addEventListener("click",function (){
                                                        doSelectOne(this)
                                                    },false)
        }

    });
    // 全选
    let checkbox_event = document
        .querySelector("#data-list")
        .querySelector("table>thead>tr>th:first-child input[type=checkbox]");
    if(checkbox_event){
        checkbox_event.addEventListener("click",function (){
                                doSelectAll(body_event,this)
                            },false)
    }
}

// 全选/非全选
function doSelectAll(body_elem,elem){
    let is_checked = elem.checked;
    // 选中每一行
    body_elem.forEach(function (item,idx){
        let checkbox_item = item.querySelector("td:first-child input[type=checkbox]");
        if(checkbox_item && is_checked){
            // 选中所有
            checkbox_item.checked=true;
            // 添加所有
            global_module.search_form._selected.push(checkbox_item.value);
        }else{
            // 取消所有选中
            checkbox_item.checked=false;
            // 清空已选择的
            global_module.search_form._selected=[];
        }
    });
    // alert(JSON.stringify(global_module.search_form._selected));
}

// 选中一个/非选中一个
function doSelectOne(elem){
    let is_checked = elem.checked;
    if( is_checked ){
        // 选中一个
        // push一个
        global_module.search_form._selected.push(elem.value);
    }else{
        // 取消一个
        let value = elem.value;
        let idx = -1;
        if(value && (idx=global_module.search_form._selected.indexOf(value))>=0){
            debugger;
            let list = [];
            for(let idx in global_module.search_form._selected ){
               if(  global_module.search_form._selected[idx]===value ){
                 continue;
               }
               list.push(global_module.search_form._selected[idx]);
            }
            // 数组内删除一个
            global_module.search_form._selected=list;
        }
    }
    //alert(JSON.stringify(global_module.search_form._selected));
}

function initPageEvent(){
    /* 初始化数据体事件 */
    let query_id = global_module.data_list_id;
    let page_dom = document.querySelector(`#${query_id} #_page-form`);
    if( !page_dom ){
        console.log("NOT initPageEvent...");
        return;
    }

    // 禁用所有分页按钮
    let _page_no = global_module.search_form.page_no;
    let page_item_dom = page_dom.querySelectorAll("button,select");
    // 如果只有一页则禁用所有分页按钮
    if( global_module.search_form.page_size>global_module.search_form._current_page_size && _page_no===0 ){
        page_item_dom.forEach(function (item,idx){
            item.setAttribute("disabled","disabled");
        });
        return;
    }

    // 第一页时 禁用首页以及上一页
    if( 0===_page_no ){
        // 第一页禁用上一页以及首页 两个按钮
        page_dom.querySelectorAll("button[name=first_page],button[name=previous_page]")
            .forEach(function(item,idx){
                item.setAttribute("disabled","disabled");
            });
    }

    // 最后一页时 禁用下一页
    if( global_module.search_form.page_size>global_module.search_form._current_page_size && _page_no>0 ){
        page_dom.querySelector("button[name=next_page]").setAttribute("disabled","disabled");
    }

    page_item_dom.forEach(function (item,idx){
        let name = item.name;
        if(name && name!==""){
            let fn = global_module.events[name];
            if( fn ){
                // 注册事件
                item.addEventListener("click",function (){
                    if (event) {  event.preventDefault(); };
                    fn(this)
                },false)
            }else{
                console.log("initPageEvent::函数未能注册上:"+name)
            }
        }
    });
}

function showList(data,temp_name){
    let str = document.querySelector("#template-data-list").innerHTML;
    let template = Handlebars.compile(str);
    let html = template( data );
    document.querySelector("#data-list").innerHTML=html;
}

function doQuery(event){
    if (event && event.preventDefault){
        event.preventDefault();
    }
    let query_id = global_module.search_form_id;
    /* 初始化查询表单事件 */
    let search_form_dom = document.querySelector(`#${query_id} form`);
    // 合并表单数据
    Object.assign(global_module.search_form,MeeUtils.formToJson( search_form_dom ));
    global_module.search_form.page_no=0;
    load(global_module.search_form);
}

function doNew(){
    module.exports.showFormType = 0		//显示不同的著录界面类型,0-一般著录界面,1、2..其他
    showForm();
}

// 首页
function firstPage(elem){
    global_module.search_form.page_no=0;
    load(global_module.search_form);
};

// 上一页
function previousPage(elem){
    // alert("上一页");
    if( global_module.search_form.page_no<=0 ){
        alert("已经是第一页了（@,@）")
        return;
    }
    // -1
    global_module.search_form.page_no--;
    load(global_module.search_form);
};

// 下一页
function nextPage(elem){
    if( global_module.data.length<global_module.search_form.page_size ){
        alert("已经是最后一页了（*m*）")
        return;
    }
    // +1
    global_module.search_form.page_no++;
    load(global_module.search_form);
};

export {init,showList,global_module,load,doQuery}
