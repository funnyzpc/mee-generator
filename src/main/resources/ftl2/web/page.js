import * as Common  from "../common.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
    /* 分页表单数据(可选) */
    search_form: {"page_no":1,"page_size":10,"_selected":[]},
    /* 主键字段，删除或者更新的where条件,这个不定义则默认为 id  */
    id_field:"id",
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件 */
    events:{  },
    /* id=data-list的行事件,此类事件会自动传入(当前行数据,当前行索引,当前dom对象),也可默认为空 */
    data_events:{   },
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    /* api必须定义完整的增删改查四大api或者定义一个base api */
    api:{
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/${module_name}/${table_name}/list",
        /* 删除: DELETE请求 */
        "delete": ctxPath+"/${module_name}/${table_name}/delete",
        /* 通用地址,如果 page、add、del、mod未定义测会通过此配置生成一个默认的增删改查(base+"/page",base+"/delete"...) */
        "base": ctxPath+"/${module_name}/${table_name}",
    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
    data:[],
    // 表单模板
    form_struct:{
        // 指定开启
        on:true,
        // form id，这只是一个prefix后面会加上actions.type
        id:"${table_name}_form",
        actions:{
            "add":{"api":ctxPath+"/${module_name}/${table_name}/add","enc":"json","method":"POST","title":"新增${table_comment}","width":"600px"},
            // 修改
            "update":{"api":ctxPath+"/${module_name}/${table_name}/update","enc":"json","method":"PUT","title":"修改${table_comment}","width":"600px"},
            // 导入
            "import":{"api":app+"/${module_name}/${table_name}/import","enc":"form","method":"POST","title":"导入${table_comment}数据"}
        },
        // 生成模板(这里的数据实在init时生成,进入页面后只生成一次)
        templates:{"add":"","update":""},
        fields:{
            "add":[
            <#list columns as c>
            <#if c?? && "1"==c.is_insert && "1"!=c.is_pk>
                  {name:"${c.java_field}",label:"${c.column_comment}",type:"${c.html_type}",col:6<#if "1"==c.is_required>,attrs:{required:"required"}</#if><#if c.dict_type??>,"dict_type":"${c.dict_type}"</#if> },
            </#if>
            </#list>
                ],
            "update":[
                   {name:"${mapper_key_info.java_field}",type: "hidden",label:"${mapper_key_info.column_comment}"},
            <#list columns as c>
            <#if c?? && "1"==c.is_insert && "1"!=c.is_pk>
                   {name:"${c.java_field}",label:"${c.column_comment}",type:"${c.html_type}",col:6<#if "1"==c.is_required>,attrs:{required:"required"}</#if><#if c.dict_type??>,"dict_type":"${c.dict_type}"</#if> },
            </#if>
            </#list>
                   {name:"create_by",label:"创建人",col:6,attrs:{readonly:"readonly" } },
                   {name:"create_time",label:"创建时间",col:6,attrs:{ readonly:"readonly"} },
                   {name:"update_by",label:"更新人",col:6,attrs:{readonly:"readonly" } },
                   {name:"update_time",label:"更新时间",col:6,attrs:{readonly:"readonly" } }
                ],
             "import":[
                  {name:"file",label:"文件",col:12,type:"file",attrs:{required:"required",title:"非空,若导入失败请删除excel数据行下方空白行～"}},
                ],
            }
    },
    // 定义初始化角色数据,这个角色是表单还有查询列表都要用到的
    init_dict:[<#list columns as c><#if c?? && c.dict_type??>"${c.dict_type}",</#if></#list>],
    // 上面需要初始化角色在调用接口后会回写到此处
    dicts:{ },
};

// 初始化
function init(){
    Common.init(module);
}

export { init }
