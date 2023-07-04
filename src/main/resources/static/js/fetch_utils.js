
/* POST+json */
//function fetchPost( url,callback ){
//    fetchPost( url,"",callback )
//}
function fetchPost( url,data,callback ){
    fetch(url,{
       credentials:'include',
       method: 'POST',
       body: JSON.stringify(data),
       headers: new Headers({
           'Content-Type': 'application/json'
         })
    })
    .then(response => response.json())
    .then(data => callback(data))
    .catch(error => console.log("请求超时,请刷新后重试~")
    );
}
/* POST+form */
function fetchFormPost( url,data ){
    fetch(url,{
       credentials:'include',
       method: 'POST',
       body: data,
    })
    .then(response => response.json())
    .then(data => resolve(data))
    .catch(error => console.log("请求超时,请刷新后重试~")
    );
}

/* GET+json */
function fetchGet(url,param,callback) {
    let param_str = buildParam(param);
    fetch(url+"?"+param_str,{ credentials:'include',method: 'GET' })
        .then((response) => response.json())
        .then((data)=>{
            callback(data)
        }).catch(error =>  {
            console.log("请求超时,请刷新后重试[3]"+error);
            callback(error)
        });
}

/* PUT+form */
function fetchPutForm(url,form,callback) {
//    fetch(url,{ method: 'PUT',body: new FormData(form) ,headers:{"content-type":"application/x-www-form-urlencoded"}})
    fetch(url,{ credentials:'include',method: 'PUT',body: new FormData(form) })
        .then((response) => response.json())
        .then((data)=>{
            callback(data)
        }).catch(error =>  {
            console.log("请求超时,请刷新后重试[3]"+error);
            callback(error)
        });
}

/* PUT+JSON */
function fetchPutJson(url,data,callback) {
    fetch(url,{ credentials:'include',
                method: 'PUT',
                body: JSON.stringify(data),
                headers: new Headers({ 'Content-Type': 'application/json'}) })
    .then((response) => response.json())
    .then((data)=>{
        callback(data)
    }).catch(error =>  {
        console.log("请求超时,请刷新后重试[3]"+error);
        callback(error)
    });
}

/* DELETE+json */
function fetchDelete( url,data,callback ){
    fetch(url,{
       credentials:'include',
       method: 'DELETE',
       body: data?JSON.stringify(data):"{}", /* 兼容各种参数*/
       headers: new Headers({
           'Content-Type': 'application/json'
         })
    })
    .then(response => response.json())
    .then(data => callback(data))
    .catch(error => console.log("请求超时,请刷新后重试~")
    );
}

/* 构建url参数 for GET request */
function buildParam(data){
    if( !data || ""===data ){
        return "";
    }
    let param = "";
    for(let key in data){
        if( ""===param ){
            param =  key+"="+data[key];
        }else{
            param = param + "&"+( key+"="+data[key]);
        }
    }
    return param;
}
export { fetchPost,fetchPutForm,fetchPutJson,fetchFormPost,fetchGet,fetchDelete }