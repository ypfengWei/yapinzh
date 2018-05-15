<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <title>点餐</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css">
</head>
<body>
<article id="menu">
    <div class="swiper-container">
        <div class="shop">
            <img src="${pageContext.request.contextPath }/img/icon/icon_10.png">
            <div>
                <img src="${pageContext.request.contextPath }/img/icon/pho.png">
                <div>
                    <span>本味餐厅</span>
                    <span>公告:</span>
                    <span>为了不影响亲们的用餐时间,请亲们提前半小时下单!...</span>
                </div>
            </div>
        </div>
        <div class="swiper-container-div">
            <ul class="swiper-container-ul">

            </ul>
        </div>
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <div class="content">
                    <div class="right">
                        <ul id="myUL">

                        </ul>
                    </div>
                    <div style="clear:both"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="frame">
        <div>
            <div>
                <img id="dilog_introducePicture"/>
            </div>
            <div>
                <p class="title" id="dilog_name"></p>
                <div>
                    <span class="price" id="dilog_price"></span>
                    <p class="plan">
                        <a href="#" onclick=changeOrderCount(localStorage.commodityId)><img
                                src="${pageContext.request.contextPath }/img/icon/icon_4.png"></a>
                        <span>数量</span>
                        <a href="#"
                           onclick=addCart_Item(localStorage.commodityId,localStorage.commodityName,localStorage.commodityPicture,localStorage.commodityPrice)><img
                                src="${pageContext.request.contextPath }/img/icon/icon_8.png"></a>
                    </p>
                    <div style="clear:both"></div>
                </div>
                <p>商品描述</p>
                <p class="subtitle1" id="dilog_introduceDetails"></p>
            </div>
            <a href="#" onclick="dd()">
                <img src="${pageContext.request.contextPath }/img/icon/icon_11.png">
            </a>
        </div>
    </div>
</article>
<footer>
    <div class="tfoot">
        <a href="#" class="shopping"><img src="${pageContext.request.contextPath }/img/icon/icon_10.png"><span
                id="totalPrice"></span><span><!--另需配送费￥5|支持到店自取--></span><img
                src="${pageContext.request.contextPath }/img/icon/icon_5.png"></a>
        <a href="#" class="settlement" onclick="submitOrderView()"><span>去结算</span></a>
    </div>
    <div class="order">
        <p>
            <span>已选商品</span>
            <span class="empty">清空购物车</span>
            <img src="${pageContext.request.contextPath }/img/garbage/garbage_1.png">
        </p>
        <div class="right">
            <ul id="cartlist">

            </ul>
        </div>
    </div>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/db.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/service.js"></script>
</body>
</html>
<script>
    var sign = GetQueryString('sign');
    var code = GetQueryString('code');
    var index = 1;
    var totalPage = 1;
    var totalSize = 0;
    var pageCount = 20;
    var currentClassifyid;
    var itemIndex = 0;
    $(function () {
        if (!openid) {
            getOpenid(code);
        }
        $(".shopping").click(function () {
            checkCart(function (result) {
                if (result) {
                    queryCartAll();
                    $(".order").toggle();
                }
            });
        });

    });
    if ($(window).scrollTop() >= 0) {
        $('.thead').css('position', 'fixed');
        $('.swiper-container-ul').css('position', 'fixed');
        $('.swiper-wrapper').css('margin-top', '100px');
        $('.swiper-wrapper').css('margin-bottom', '57px');
    } else {
        $('.thead').css('position', '');
        $('.swiper-container-ul').css('position', '');
        $('.swiper-container-ul').css('position', 'fixed');
        $('.swiper-wrapper').css('margin-top', '100px');
        $('.swiper-wrapper').css('margin-bottom', '57px');
    }
    $(function () {
        getClassify(function (result) {
            $.each(result.classify, function (i, item) {
                if (i === 0) {
                    $('<li onclick="changer(this,' + item.id + ')" class="swiper-container-ul-li actives">\n' +
                        '\t\t      \t<span>' + item.commodityType + '</span>\n' +
                        '\t\t  \t  </li>').appendTo($('.swiper-container-ul'));
                    adapter(item.id);
                } else {
                    $('<li onclick="changer(this,' + item.id + ')" class="swiper-container-ul-li">\n' +
                        '\t\t      \t<span>' + item.commodityType + '</span>\n' +
                        '\t\t  \t  </li>').appendTo($('.swiper-container-ul'));
                }
            });
        });
        $('.empty').click(function () {
            emptyCart();
        });
    });

    function dd(name, introducePicture, price, introduceDetails) {
        $(".frame").toggle();
        $('#dilog_introducePicture').attr('src', '/yapingzh/picture/' + introducePicture);
        $('#dilog_name').text(name);
        $('#dilog_introduceDetails').text(introduceDetails);
        $('#dilog_price').text(price);
    }

    function adapter(id) {
        $.ajax({
            sync: false,
            url: "/yapingzh/getCommodityArray.do",
            dataType: "JSON",
            data: {"commodityClassification.id": id, "pageIndex": index, "pageCount": pageCount},
            success: function (result) {
                var array = $.parseJSON(result.commodityArray);
                totalPage = result.totalPage;
                totalSize = result.totalSize;
                $.each(array, function (i, item) {
                    $('<li onclick=localStorage.commodityId=\'' + item.id + '\';localStorage.commodityName=\'' + item.name + '\';localStorage.commodityPrice=\'' + item.price + '\';localStorage.commodityPicture=\'' + item.introducePicture + '\';dd(\'' + item.name + '\',\'' + item.introducePicture + '\',\'' + item.price + '\',\'' + item.introduceDetails + '\')>\n' +
                        '\t                <div>\n' +
                        '\t                  <div class="item">\n' +
                        '\t                    <div class="item-left">\n' +
                        '\t                      <div class="item-img"><a href="javascript:" class="cp_on"><img src="/yapingzh/picture/' + item.introducePicture + '"/></a></div>\n' +
                        '\t                    </div>\n' +
                        '\t                    <div class="item-right">\n' +
                        '\t                      <div class="title">' + item.name + '</div>\n' +
                        '\t                      <div class="subtitle">' + item.introduceDetails + '</div>\n' +
                        '\t                      <div class="number">\n' +
                        '\t                      \t<span class="price">￥' + item.price + '</span>\n' +
                        '\t                      \t<p class="plan">\n' +
                        '\t                      \t\t<a href="#" onclick="changeOrderCount(\'' + item.id + '\')"><img src="${pageContext.request.contextPath }/img/icon/icon_4.png"></a>\n' +
                        '\t                      \t\t<span>数量</span>\n' +
                        '\t                      \t\t<a href="javascript:void(0)" onclick="addCart_Item(\'' + item.id + '\',\'' + item.name + '\',\'' + item.introducePicture + '\',\'' + item.price + '\')"><img src="${pageContext.request.contextPath }/img/icon/icon_8.png"></a>\n' +
                        '\t                      \t</p>\n' +
                        '\t                      </div>\n' +
                        '\t                    </div>\n' +
                        '\t                  </div>\n' +
                        '\t                </div>\n' +
                        '\t              </li>').appendTo($('#myUL'));
                });

            },
            error: function () {
                $('#myUL').html('没有条目');
            }
        });
    }

    function changer(tag, id) {
        var selectId = $(tag).index();
        if (itemIndex === selectId) {
            return;
        }
        itemIndex = selectId;
        index = 1;
        totalPage = 1;
        currentClassifyid = id;
        totalSize = 0;
        $('#myUL').html('');
        $(tag).addClass('actives').siblings('li').removeClass('actives');
        adapter(id);
    }

    window.addEventListener("scroll", function (event) {
        var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        if (document.documentElement.scrollHeight === document.documentElement.clientHeight + scrollTop) {
            if (totalPage > index) {
                index++;
                adapter(currentClassifyid);
            } else {
                toast('没有了');
            }
        }
        if ($(window).scrollTop() == 0) {
            $('.swiper-container-ul').css('top', '130px');
        } else if ($(window).scrollTop() < 130 && $(window).scrollTop() > 0) {
            $('.swiper-container-ul').css('top', 130 - $(window).scrollTop() + 'px');
        } else if ($(window).scrollTop() >= 130) {
            $('.swiper-container-ul').css('top', '45px');
        }
    });

    function submitOrderView() {
        showView(sign);
    }

</script>