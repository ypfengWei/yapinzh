<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <title>结算</title>
    <link rel="stylesheet" type="text/css" href="css/settlement.css">
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<body>
<header>
    <div class="thead">
        <a href="#" onclick="history.go(-1)"><img src="img/return/return_3.png">
            <!--<span>提交订单</span>--></a>
    </div>
</header>
<article id="menu">
    <!--<div class="bg_content">-->
    <!--<<span>现在预定，预计13:00起送</span>-->
    <!--</div>-->
    <div class="swiper-wrapper">
        <div class="swiper-slide">
            <a href="#" onclick="selectAddress()"
               style="text-decoration: none; color:#ff734c;padding-left: 10px;padding-bottom: 5px;">
                <div>
                    <p id="address" style="padding-bottom: 5px;">xx区xx路xx小区xx栋1225室</p>
                    <p id="name">野猪佩奇（先生）13254598723</p>
                </div>
            </a>
            <div class="swiper-slide">
                <p>
                    <label style="color: #ff734c;">预约<input id="appointment_time" class="ton" size="8"
                                                            type="time"/></label>
                    <a href="#" id="cancel_appointment" class="cancel_appointment">取消预约</a>
                </p>
            </div>
        </div>
    </div>
    <div>
        <form>
            <div class="tbody">
                <div class="frame">
                    <div class="food" id="foodLeft">
                    </div>
                </div>
                <p>
                    <label for="remarks">备注：</label>
                    <textarea id="remarks" name="remarks" rows="5" maxlength="200"></textarea>
                </p>
            </div>
        </form>
    </div>
</article>
<footer>
    <div class="tfoot">
        <a href="#" class="shopping"><img src="./img/icon/icon_10.png"><span id="totalPrice"></span>
            <!--<span>已优惠￥5</span>--><img src="./img/icon/icon_5.png"></a>
        <a href="#" class="settlement"><span>提交订单</span></a>
    </div>
</footer>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/layer.js"></script>
<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/db.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<script type="text/javascript" src="js/settlement.js"></script>
</body>
<script>
    var href = location.href.split('#')[0];
    setTimeout(function () {
        confirmOrder();
    }, 500);
    $(function () {
        $.getJSON("/yapingzh/initJS_SDK.do", {
            "pageUrl": "" + href + ""
        }, function (result) {
            if (result.success) {
                //初始化接口环境
                wx.config({
                    appId: result.appid,
                    timestamp: result.timestamp,
                    nonceStr: result.noncestr,
                    signature: result.signature,
                    jsApiList: ['editAddress', 'getLatestAddress', 'openAddress', 'chooseWXPay']
                });
            }
        });

    });

    function selectAddress() {
        //拉取微信用户地址
        wx.openAddress({
            success: function (res) {
                var deliveryinfo = {};
                deliveryinfo.recevername = res.userName;
                deliveryinfo.recevertel = res.telNumber;
                deliveryinfo.receveraddr = res.detailInfo;
                localStorage.setItem('deliveryinfo', JSON.stringify(deliveryinfo));
                $('#name').text('联系人:' + res.userName + ';联系电话:' + res.telNumber);
                $('#address').text('送货地址:' + res.detailInfo);
            }
        });
    }

    function confirmOrder() {
        var money = 0.0;
        var transaction = db.transaction(["cart"], "readonly");
        var store = transaction.objectStore("cart");
        var cursor = store.openCursor();
        cursor.onsuccess = function (e) {
            var res = e.target.result;
            if (res) {
                var commodity = res.value;
                $('<div>\n' +
                    '<div>\n' +
                    '<img src="/yapingzh/picture/' + commodity.imgPath + '">\n' +
                    '</div>\n' +
                    '<div>\n' +
                    '<span class="color_hs">' + commodity.commodityName + '</span>\n' +
                    '</div>\n' +
                    '<div>\n' +
                    '<span>x' + commodity.itemSize + '</span>\n' +
                    '<span>￥' + commodity.price + '</span>\n' +
                    '</div>\n' +
                    '</div>').appendTo($("#foodLeft"));
                money += commodity.price * commodity.itemSize;
                res.continue();
            }
            $("#totalPrice").text('合计:￥' + money.toFixed(2));
        };
    }

    var deliveryinfo = localStorage.getItem('deliveryinfo');
    if (deliveryinfo) {
        deliveryinfo = $.parseJSON(deliveryinfo);
        $('#name').text('联系人:' + deliveryinfo.recevername + ';联系电话:' + deliveryinfo.recevertel);
        $('#address').text('送货地址:' + deliveryinfo.receveraddr);
    } else {
        $('#name').text('');
        $('#address').text('请选择收货地址>');
    }


    $('.settlement').click(function () {
        var ask = '您选择外卖?';
        //底部对话框
        layer.open({
            content: ask
            , btn: ['是的', '不是']
            , yes: function (index) {
                submitOrder();
                layer.close(index);
            }
        });
    });

    function submitOrder() {
        addOrder(0, function (data) {
            //发起微信支付
            wx.chooseWXPay({
                timestamp: data.timeStamp,
                nonceStr: data.nonceStr,
                package: data.package,
                signType: data.signType,
                paySign: data.paySign,
                success: function (res) {
                    emptyCart();
                    location.href = '${pageContext.request.contextPath}/pay_success.jsp';
                }, fail: function (res) {
                    toast('支付失败');
                }, cancel: function (res) {
                    toast('取消支付');
                }
            });
        })
    }
</script>
</html>
