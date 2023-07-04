import { fetchPutForm,fetchGet }  from "./fetch_utils.js"

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
    let _title3_str= (""===item.title3)?"": (`title="${item.title3}"`);
    //return "<span style='padding-left:"+width+"px'>"+item.title2 +"</span>"+"<span class='mee-menu-item-dir-tag'> ▼</span>";
    return `<span style="padding-left:${width}px" ${_title3_str} >${item.title2}</span><span class="mee-menu-item-dir-tag"> ▼</span>`;
 }

function buildHref(item,level){
   let width = level*8;
   let _title3_str= (""===item.title3)?"": (`title="${item.title3}"`);
   // 拼接context-path
   let href = (item.target==="_content" && !item.path.startsWith("http"))?(ctxPath+item.path) : item.path;
//   return "<a class='mee-menu-item-text' href='"+href+"' "+
//          "style='display: block;padding-left:"+width+"px' "+
//          "target='"+item.target+"' >"+
//          item.title2+
//          "</a>";
   return `<a class="mee-menu-item-text" href="${href}" style="display:block;padding-left:${width}px" target="${item.target}" ${_title3_str} >
          ${item.title2}
          </a>`;
}

function filterMenu(data){
    let result = [];
    let level = 0;
    for(let i=0;i<data.length;i++){
        if( !(data[i].hidden===true) ){
          let item = data[i];
          item.is_top=true;
          item.level=level;
          if( item.title && item.title.length>18 ){
            // 用于展示
            item.title2=item.title.substr(0,16)+"..";
            // 用于title提示
            item.title3=item.title;
          }else{
            // 用于展示
            item.title2=item.title;
            // 用于title提示
            item.title3="";
          }
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
      if( item.title && item.title.length>=16 ){
        // 用于展示
        item.title2=item.title.substr(0,14)+"..";
        // 用于title提示
        item.title3=item.title;
      }else{
        // 用于展示
        item.title2=item.title;
        // 用于title提示
        item.title3="";
      }
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

function init(){
    let menu_dom = document.querySelector("#mee-menu");
//    // 宽度调整
//    document.querySelector("#mee-content").style.width= ( document.body.clientWidth - menu_dom.clientWidth - 12 )+"px";
    // 组装数据
    fetchGet(ctxPath+"/menus",{},function(res){
        let menu_data = filterMenu(res.data);
        // 生成菜单
        menu_dom = menu_dom.querySelector("#mee-menu-content");
        menu_dom.innerHTML=buildMenu(menu_data);
        // 注册事件
        addEvent(menu_dom);
    });
 }
 export { init }