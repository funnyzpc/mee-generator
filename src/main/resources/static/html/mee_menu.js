
 function addEvent(dom){
    // 目录切换 mee-menu-item-dir
    let dir_dom_list = dom.querySelectorAll("#mee-menu-content .mee-menu-item-dir");
    dir_dom_list.forEach(dom_item=>{
        dom_item.addEventListener("click",function(){
            // 获取节点当前状态(dom_is_show)
            let dom_is_show = this.getAttribute("dom_is_show");
            if(dom_is_show==="1"){
                // 对于显示的 置为 隐藏(姊妹节点)
                this.nextElementSibling.style.display="none";
                // 子节点全部隐藏
                // this.nextElementSibling.querySelectorAll(".mee-menu-item-dir");
                // 重置节点参数
                this.setAttribute("dom_is_show","0");
                // 改变箭头方向 ˆˇ ▲▼
                // this.querySelector(".mee-menu-item-dir-tag").innerHTML=MENU_SPACE[1]+"▼";
                this.querySelector(".mee-menu-item-dir-tag").innerHTML="▼";
            }else{
                // 对于隐藏的 置为 显示(姊妹节点)
                this.nextElementSibling.style.display="";
                // 重置节点参数
                this.setAttribute("dom_is_show","1");
                // 改变箭头方向
                // this.querySelector(".mee-menu-item-dir-tag").innerHTML=MENU_SPACE[1]+"▲";
                this.querySelector(".mee-menu-item-dir-tag").innerHTML="▲";
            }
        });
    });

    // 菜单点击 mee-menu-item
    let menu_dom_list = dom.querySelectorAll("#mee-menu-content .mee-menu-item");
    menu_dom_list.forEach(dom_item=>{
        dom_item.addEventListener("click",function(){
             // 获取节点当前状态(dom_is_show)
            let menu_id = this.getAttribute("menu_id");
            // 点击的添加背景色
            this.style.backgroundColor="blue";
            this.style.textShadow="black 2px 2px 4px";
            // 其他menu节点取消背景色
            dom.querySelectorAll("#mee-menu-content .mee-menu-item").forEach(other_item=>{
                // 获取节点当前状态(dom_is_show)
                let other_menu_id = other_item.getAttribute("menu_id");
                if( menu_id !== other_menu_id ){
                    other_item.style.backgroundColor="";
                    other_item.style.textShadow="";
                }
            });

        });
    });
 }

 function buildMenu(menu_data){
    let color = 40+8*(menu_data[0].level);
    let menu_str = menu_data[0].is_top === true ? "<ul>" : "<ul style='display:none;background-color:rgb("+color+","+color+","+color+");'>";
    for(let idx in  menu_data){
            menu_str+=buildItem(menu_data[idx]);
    }
    return ( menu_str + "</ul>" );
 }

 function buildItem(data_item){
    let level = data_item.level;
    if( data_item.has_children === true ){
        return  "<li>"+
                    "<div data-has-child='true' dom_is_show='0' dom_level='"+level+"' class='mee-menu-item-dir'>"+buildItemDir(data_item,level)+"</div>"+
                    buildMenu(data_item.children) +
                "</li>";
    }else{
        //  ▲▼
        return "<li class='mee-menu-item' menu_id='"+data_item.id+"'>"+buildHref(data_item,level) +"</li>";
    }
 }

 function buildItemDir(item,level){
    let width  = level*8;
    return "<span style='padding-left:"+width+"px'>"+item.title +"</span>"+"<span class='mee-menu-item-dir-tag'> ▼</span>";
 }

 function buildHref(item,level){
    // return MENU_SPACE[level]+
    let width  = level*8;
    return "<a class='mee-menu-item-text' href='"+item.path+"' "+
           "style='display: block;padding-left:"+width+"px' "+
           "target='"+item.target+"' >"+
           item.title+
           "</a>";
 }

  function filterMenu(data){
     let result = [];
     let level = 0;
     for(let i=0;i<data.length;i++){
       if( !data[i].hidden===true ){
          let item = data[i];
          item.is_top=true;
          item.level=level;
          if( !item.children || item.children.length==0 ){
            item.has_children=false;
          }else{
            item.children=filterItem(item.children,level+1);
            item.has_children=( item.children && item.children.length>0)?true:false;
          }
          result.push(item);
       }
     }
     return result;
  }

  function filterItem(sub_data,level){
     let result = [];
     for( let i=0;i<sub_data.length;i++){
       if( !sub_data[i].hidden===true ){
          let item = sub_data[i];
          item.level=level;
          if( !item.children || item.children.length==0 ){
            item.has_children=false;
          }else{
            item.children=filterItem(item.children,level+1);
            item.has_children=( item.children && item.children.length>0)?true:false;
          }
          result.push(item);
       } // if
     } // for
     return result;
  }

 const MENU_DATA = {"msg":"操作成功","code":200,"data":[{"id":1,"title":"系统管理","icon":"system","path":"/system","hidden":false,"children":[{"id":11,"title":"用户管理","icon":"user","path":"user.html","hidden":false,"target":"_content"},{"id":12,"title":"角色管理","icon":"peoples","path":"role","hidden":true,"target":"_content"},{"id":13,"title":"菜单管理","icon":"tree-table","path":"_menu.html","hidden":false,"target":"_content"},{"id":14,"title":"部门管理","icon":"tree","path":"https://www.csdn.net/","hidden":false,"target":"_content"},{"id":15,"title":"岗位管理","icon":"post","path":"post","hidden":true,"target":"_content"},{"id":16,"title":"字典管理","icon":"dict","path":"dict","hidden":true,"target":"_content"},{"id":11,"title":"参数设置","icon":"edit","path":"config","hidden":true,"target":"_content"},{"id":17,"title":"通知公告","icon":"message","path":"notice","hidden":true,"target":"_content"},{"id":18,"title":"日志管理","icon":"log","path":"log","hidden":false,"children":[{"id":181,"title":"操作日志","icon":"form","path":"operlog.html","hidden":false,"target":"_content"},{"id":182,"title":"登录日志","icon":"logininfor","path":"logininfor","hidden":false,"target":"_content"}]}]},{"id":2,"title":"系统监控","icon":"monitor","path":"/monitor","hidden":false,"children":[{"id":21,"title":"在线用户","icon":"online","path":"online","hidden":false,"target":"_content"}]},{"id":3,"title":"系统工具","icon":"tool","path":"/tool","hidden":true,"children":[{"id":31,"title":"表单构建","icon":"build","path":"build","hidden":false,"target":"_content"},{"id":32,"title":"代码生成","icon":"code","path":"gen","hidden":true,"target":"_content"},{"id":33,"title":"系统接口","icon":"swagger","path":"swagger","hidden":false,"target":"_content"}]},{"id":4,"title":"shadow website","icon":"guide","path":"http://baidu.com","hidden":false,"target":"_blank"}]};

 window.onload=function(){
    let menu_dom = document.querySelector("#mee-menu");
    // 宽度调整
    document.querySelector("#mee-content").style.width= ( document.body.clientWidth - menu_dom.clientWidth - 8 )+"px";
    // 组装数据
    let menu_data = this.filterMenu(MENU_DATA.data);
    // 生成菜单
    menu_dom = menu_dom.querySelector("#mee-menu-content");
    menu_dom.innerHTML=this.buildMenu(menu_data);
    // 注册事件
    this.addEvent(menu_dom);
 }