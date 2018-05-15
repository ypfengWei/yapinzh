//获取指定参数的值
var openid = localStorage.getItem('openid');
/**
 * @return {string}
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return (r[2]);
    return null;
}

function toast(msg) {
    layer.open({
        content: msg
        , skin: 'msg'
        , time: 2 //2秒后自动关闭
    });
}

function getOpenid(code) {
    $.ajax({
        url: "/yapingzh/getOpenid.do",
        data: {
            "code": code
        },
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                localStorage.setItem('openid', result.openid);
            }
        }
    });
}

// base64编码开始
function encode64(input) {
    var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
    var output = '';
    var chr1, chr2, chr3 = '';
    var enc1, enc2, enc3, enc4 = '';
    var i = 0;
    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
            + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = '';
        enc1 = enc2 = enc3 = enc4 = '';
    } while (i < input.length);

    return output;
}

// base64编码结束