
// 合并Object参数(from=>to)
function assign(from,to){
  if( !from || !to ){
    throw new Error(`必要参数不可为空 from:${from},to:${to}，请检查!`);
  }
  let ks = Object.keys(from);
  for( let idx in ks ){
    let k = ks[idx];
    let item_from = from[k];
    let item_to   =   to[k];
    // 函数,函数用instanceof判断也是Object！
    if( "function"===(typeof item_from) ){
        to[k]=from[k];
        continue;
    }
    // 数组,数组判断一定要放在对象(Object)判断前面，因为判断为数组时他也一定是对象
    if( item_from instanceof Array ){
      if( !(item_to instanceof Array) ){
          to[k]=item_to=item_from;
          continue;
      }
      for( let idx in item_from ){
          to[k].push(item_from[idx]);
      }
      continue;
    }
    // 对象
    if( item_from instanceof Object ){
      if( !(item_to instanceof Object) ){
        // 给一个默认值
        to[k]=item_to={};
      }
      assign(from[k],to[k]);
      continue;
    }
    // 原生对象 string\number\bool ... etc
    if( null===item_from || undefined===item_from ){
        continue;
    }
    to[k]=from[k];
  }
}

//// Object属性追加
//function addProperty(from,to){
//	if(!from){
//		console.log("待合并的对象from存在空");
//		return !to?{}:to;
//	}
//	if(!to){
//		return to = from;
//	}
//	for (let p in from){
//		//console.log(p);
//		//if(from.hasOwnProperty(p) && (!to.hasOwnProperty(p) )){
//		//	to[p]=from[p];
//		//}
//		if( !to[p] || null===to[p] || undefined===to[p]  ){
//			// 这里只做追加，有相同属性的不再覆盖
//			to[p]=from[p];
//		}
//	}
//	return to;
//}
//
//// 强制覆盖
//function addPropertyOverwritten(from,to){
//	if(!from){
//		console.log("待合并的对象from存在空");
//		return !to?{}:to;
//	}
//	if(!to){
//		return to = from;
//	}
//	for (let p in from){
//		// 这里强制覆盖
//		if( from[p]){
//			to[p]=from[p];
//		}
//	}
//	return to;
//}

// 表单转json
function formToJson(form){
   let elem = form.elements;
   let data = {};
   for( let idx=0;idx<elem.length;idx++){
        let type = elem[idx].type;
        switch(type) {
            case 'hidden':
                data[elem[idx].name]=elem[idx].value;
                continue;
            case 'text':
                data[elem[idx].name]=elem[idx].value;
                continue;
            case 'textarea':
                data[elem[idx].name]=elem[idx].value;
                continue;
            case 'input':
                data[elem[idx].name]=elem[idx].value;
                continue;
            case 'radio':
                // data[elem[idx].name]=elem[idx].value;
                // continue;
                // 只能取checked=true的value
                if( true===elem[idx].checked ){
                    data[elem[idx].name]=elem[idx].value;
                }
                continue;
            case 'select':
            case 'select-one':
                data[elem[idx].name]=elem[idx].value;
                continue;
            case 'checkbox':
                //data[elem[idx].name]=(true===elem[idx].checked)?elem[idx].getAttribute("selected-value"):elem[idx].getAttribute("not-selected-value");
                // 分两种，一种是将checkbox空间当单选框用，另一种是作为普通的checkbox用，故取值方式存在区别
                let select_one = elem[idx].hasAttribute("_selected");
                if( true===select_one ){
                    data[elem[idx].name]=(true===elem[idx].checked)?elem[idx].getAttribute("_selected"):elem[idx].getAttribute("_not-selected");
                }else{
                   if( false === elem[idx].checked ){
                        continue;
                    }
                    let _name = elem[idx].name;
                    if( typeof(data[_name])==="string" ){
                        data[_name]=data[_name]+","+elem[idx].value;
                    }else{
                        data[_name]=elem[idx].value;
                    }
                }
                continue;
            case 'submit':
            case 'reset':
                // 忽略
                continue;
            default :
                // debugger;
                console.log("未知的节点："+elem[idx]);
                continue;
        }
   }
   return data;
}

export { formToJson,assign }
