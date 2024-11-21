import * as CC from "../cc.js"
import * as Common  from "../common.js"
import * as MeeUtils  from "../mee_utils.js"
import { fetchPutJson }  from "../fetch_utils.js"

// 表单参数
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 分页表单数据(可选) */
    search_form: {"page_no":0,"page_size":10},
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{ },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{ },
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    api:{
        /* 列表分页查询：GET请求 */
        page: ctxPath+"/code_gen2/find_by_table_id",
        "base": ctxPath+"/code_gen2",

    }
};

// 初始化通用模块
function init(){
    Common.init(module);
}

function saveData(){
    let line_dom = document.querySelectorAll("#data-list tbody tr");
    if( !line_dom || line_dom.length==0){
        alert("数据行为空!");
        return;
    }
    let data_list = [];
    for(let i=0;i<line_dom.length;i++ ){
        let line = line_dom[i].querySelectorAll("input,select,textarea,hidden,radio,number,email,tel")
        let item = {};
        for( let j = 0;j<line.length;j++ ){
            if( "checkbox"===line[j].type ){
              item[ line[j].name ]=(true===line[j].checked)?line[j].getAttribute("_selected"):line[j].getAttribute("_not-selected");
              continue;
            }
            item[ line[j].name ] = line[j].value;
        }
        data_list.push(item);
    }
    fetchPutJson(ctxPath+"/code_gen2/gen2_field/update",data_list,function(res){
        if( res && res.status===1 ){
            alert(res.msg);
            // 刷新页面
            window.location.reload();
            return;
        }
        alert( (res && res.msg)? res.msg : "保存失败");
    });

}

export { init ,saveData }
