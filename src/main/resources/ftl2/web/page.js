import * as Common  from "../common.js"

// 表单参数 这个参数如有表单则必须定义，同时init后会自动将global_opts中的参数合并到当前module下
var module={
    // 顶部查询表单的ID,以下是默认值单查询模块时可不用定义
    search_form_id:"data-search",
    // table数据区的ID,以下是默认值单查询模块时可不用定义
    data_list_id:"data-list",
    // 模板定义区的ID,以下是默认值单查询模块时可不用定义
    template_id:"template-data-list",
    /* 调用Common.init后是否调用一次查询 */
    default_query:true,
   /* 分页表单数据,一般做分页查询时用，默认无需定义，主要字段有如下:
    *  page_no: 页号
    *  page_size: 分页容量，一般是10，可以自定义调整
    *  _selected: 选中的行，一般做批量操作时会用到，里面一般存放的是 module::id_field
    *  _total: 总页数,此参数主要用于分页在 common.js 有定义及使用
    *  _page_count: 总记录数,此参数主要用于分页在 common.js 有定义及使用
    */
    search_form: {"page_no":1,"page_size":10,"_selected":[]},
    /* 主键字段，删除或者更新的where条件,这个不定义则默认值为id;一般为单string单字段，也可为Array类型["pk1","pk2"]  */
    id_field:"id",
    /* 当前业务模块(页面)扩展函数 id=search-form的扩展事件,每个事件的key在html页面中用 _func 定义
         调用一般会传入参数(dom:当前事件发生地dom对象,search_form_dom:事件发生地所在的form的dom对象,search_form:当前表单的search_form配置项)
    */
    events:{  },
    /* id=data-list的行事件,此类事件会自动传入(data:当前行数据,idx:当前行索引,elem:当前dom对象),每个事件的key在html(->table->td)页面中用 func 定义
    *  这里面有针对默认api的操作,这些操作都是对特定操作的增强：
    *     do_add0: add(添加)操作的plugin,传入值(dialog_dom:当前form对应dom对象,action:对应actions每一项配置,data:渲染用到的当前行数据)
    *     do_update0: update(更新)操作的plugin,传入值(dialog_dom:当前form对应dom对象,action:对应actions每一项配置,data:渲染用到的当前行数据)
    */
    data_events:{   },
    /* 通用api接口，一般包含增删改查四大接口,给Common内的增删改查之使用，使用时一般加上 module.ctx_path 前缀  */
    /* api必须定义完整的增删改查四大api或者定义一个base api,这里面的操作都是直接操作，如果需要通过表单操作，请参见下方的:form_struct */
    api:{
        /* 通用地址,这是个特殊项; 如果 page、delete、delete_batch、export未定义测会通过此配置生成一个默认的增删改查(eg: base+"/list",base+"/delete"...) */
        "base": ctxPath+"/${module_name}/${gs_table}",
        /* 列表分页查询：GET请求 */
        "page": ctxPath+"/${module_name}/${gs_table}/list",
        /* 删除: DELETE请求 */
        "delete": ctxPath+"/${module_name}/${gs_table}/delete",
        /* 批量删除: DELETE+BODY请求 */
        "delete_batch":  ctxPath+"/${module_name}/${gs_table}/delete_batch",
        /* 导入：GET请求 */
        "export": ctxPath+"/${module_name}/${gs_table}/export",
    },
    /* 列表查询请求，如果执行的是Common的相关请求则数据会回写到此字段内*/
    data:[],
    // 表单模板，这里定义的都是通过表单(一般是dialog表单)操作的属性项
    form_struct:{
        // 指定开启,如果值为false则不会预生成dom对象
        on:true,
        // form id，这只是一个prefix后面会加上actions.type
        id:"${table_name}_form",
        actions:{
            /**
            * 这里重点说明:
            * 1.这里的每个key都应下方fields中的key配置，一对一不可少！
            * 2.每个value对象中的每个字段分别如下:
            *   - api: 定义表单最后提交地址
            *   - enc: 默认有两种形式 json.以json方式提交,form.以form方式提交
            *   - method: 提交函数 POST/GET/PUT/POST/DELETE,每一种提交函数都需要在 fetch_utils.js 中定义
            *   - title: 表单标题名称
            *   - width: 表单界面宽度，如果是dialog则就是dialog的宽度
            *   - top: 表单界面相对顶部位置，如果是dialog则就是dialog的相对于顶部的距离
            *   - show_before_func: 函数,定义表单dom对象在被显示之前的动作,此时还未生成dom,插件的方式,参数从左至右一般为(type:当前action的key,action:当前action的value配置,data:表单待渲染用的数据)
            *   - show_after_func: 函数,定义表单dom对象在生成之后的函数,插件的方式,参数从左至右一般为(type:表单类型,就是当前action项,action:表单基本属性配置,data:表单待渲染用的数据,dialog_dom:表单dom对象)
            * 3.
            **/
            // 添加
            "add":{"api":ctxPath+"/${module_name}/${gs_table}/add","enc":"json","method":"POST","title":"新增${table_comment}","width":"600px"},
            // 修改
            "update":{"api":ctxPath+"/${module_name}/${gs_table}/update","enc":"json","method":"PUT","title":"修改${table_comment}","width":"600px"},
            // 导入
            "import":{"api":app+"/${module_name}/${gs_table}/import","enc":"form","method":"POST","title":"导入${table_comment}数据"}
        },
        // 生成模板(这里的数据实在init时生成,进入页面后只生成一次,点击相应操作时会将对应此处的字符串构造为实际的dom并显示)
        templates:{"add":"","update":""},
        fields:{
            /**
            * form字段，这里对应的key与actions下面的key是一一对应的
            **/
            "add":[
               /**
                * 表单字段配置项:
                * name: 表单项字段名称(提交到后端),必定义项！
                * label: 表单项字段label，必定义项！
                * type: 表单项类型 select/input/radio/date/datetime-local/checkbox/textarea/file/hidden 为 hidden 时此表单项不显示，但是提交时会带入到提交参数！
                * dict_type: 字典配置项目,此配置的值一定要在 init_dict 指定 或 在 dicts 中详细指定!
                * col: 表单项的宽度，最大为12，如果是4则宽度是相对于整个表单的33%
                * attrs:{required:"required"}
                *      - required: 必填项，表单提交时候会校验,默认label会以红*标注
                *      - readonly: 只读项，表示不可修改
                *      - placeholder: 输入项内的提示，仅仅只是提示
                *      - pattern: 校验填写内容的正则表达式，一般跟title一起用
                *      - title: 校验项提示语，一般同required以及pattern一起用
                *      - minlength: 输入项内容的长度校验，校验时一般会搭配 title 一起用
                * title: 表单项提示，这个提示体现在label中并以深色体现
                *
                **/
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
                  /** 通用字段 **/
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
    // 定义初始化字典数据,字典需要先定义,接口获取后会写入到 dicts 中,注意，这里仅可指定字典项的name
    init_dict:[<#list columns as c><#if c?? && c.dict_type??>"${c.dict_type}",</#if></#list>],
    // init_dict内的字典项在调用接口后会回写到此处，同时也会合并显式定义(手动扩展的)的字典项：eg：{ "qrtz_app_state":{"Y":{"v":"Y","l":"开启"},"N":{"v":"N","l":"关闭"} } }
    dicts:{ },
};

// 初始化
function init(){
    Common.init(module);
}

export { init }
