(function () {
    'use strict';

    /** Date format **/
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }


    var cctemplate = {};
    Handlebars.registerHelper("date", function (d) {
        return new Handlebars.SafeString(d ? new Date(d).Format("yyyy-MM-dd") : "");
    });
    Handlebars.registerHelper("time", function (d) {
        return new Handlebars.SafeString(d ? new Date(d).Format("hh:mm:ss") : "");
    });
    Handlebars.registerHelper("datetime", function (d) {
        if(!d || ""===d){
            return "";
        }
        if("string"===(typeof d)){
            d = Number(d);
        }
//        return new Handlebars.SafeString(d ? new Date( d ).Format("yyyy-MM-dd hh:mm:ss") : "");
        return new Handlebars.SafeString( new Date( d ).Format("yyyy-MM-dd hh:mm:ss") );
    });
    Handlebars.registerHelper("equal", function (d1, d2, options) {
        if (d1 == d2){
           return options.fn(this);
        }
        return options.inverse(this);
    });
    // 判断字符串数组中是否含有自定项
    Handlebars.registerHelper("_list_include", function (d1, d2, options) {
        if( !d1 || null===d2 || undefined===d2 /*|| ""===d2*/){
            return options.inverse(this);
        }
        let _d1 = ( "string"===typeof(d1) )?d1.split(","):d1;
        if ( _d1.includes(d2) ){
           return options.fn(this);
        }
        return options.inverse(this);
    });

//    Handlebars.registerHelper("helperMissing", function (name, d) {
//        if (cctemplate[name])
//            return cctemplate[name](d);
//        else
//            //return new Handlebars.SafeString("Unknown template " + name);
//            return ""
//    });
//    Handlebars.registerHelper("string", function (name, d) {
//        return name;
//    });

//    Handlebars.registerHelper('select', function( elem,value, options ){
//        debugger;
//        let $el = $('<select />').html( options.fn(this) );
//        $el.find('[value="' + value + '"]').attr({'selected':'selected'});
//        return $el.html();
//    });

//    $.template = function (name, data, proc) {
//        if (cctemplate[name]) {
//            proc(cctemplate[name](data));
//        } else {
//            var t = $(name);
//            if (t.length) {
//                cctemplate[name] = Handlebars.compile(t.html());
//                proc(cctemplate[name](data));
//            } else {
//                $.get("/template/" + name + ".html", function (txt) {
//                    $("<div>" + txt + "</div>").find(">div").each(function (i, e) {
//                        var t = $(e);
//                        cctemplate[t.attr("id")] = Handlebars.compile(t.html());
//                    });
//                    proc(cctemplate[name](data));
//                }).fail(function () {
//                    proc("template failed!")
//                });
//            }
//        }
//    };
//    $.fn.template = function (name, data, proc) {
//        return this.each(function () {
//            var me = $(this);
//            $.template(name, data, function (txt) {
//                proc ? proc(name, data, txt) : me.html(txt);
//            });
//        });
//    };

})();
