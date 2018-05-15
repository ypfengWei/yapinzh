<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>订单管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/order.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/dropload.css">
</head>
<body>
<header>
    <div class="thead">
        <a href="#" onclick="history.go(-1)"><img src="${pageContext.request.contextPath }/img/return/return_1.png">订单管理</a>
    </div>
</header>
<article id="menu">
    <div class="swiper-container">
        <div class="swiper-container-div">
            <ul class="swiper-container-ul">
                <li class="swiper-container-ul-li actives">待确认</li>
                <li class="swiper-container-ul-li">待配送</li>
                <li class="swiper-container-ul-li">已完成</li>
            </ul>
        </div>
    </div>
    <div class="content">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <div class="frame">
                    <ul>

                    </ul>
                </div>
            </div>
        </div>
        <div class="swiper-wrapper">
            <div class="swiper-slide" style="display:none;">
                <div class="frame">
                    <ul>

                    </ul>
                </div>
            </div>
        </div>
        <div class="swiper-wrapper">
            <div class="swiper-slide" style="display:none;">
                <div class="frame">
                    <ul>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</article>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/dropload.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
</body>
<script>
    var sellersId = GetQueryString('sellersId');
    var item0 = {
        index: 1,
        count: 20,
        state: 1,
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var item1 = {
        index: 1,
        count: 20,
        state: 2,
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var item2 = {
        index: 1,
        count: 20,
        state: 4,
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var itemIndex = 0;
    $(function () {
        // tab
        $('.swiper-container-ul li').on('click', function () {
            var alink = $(this);
            var selectId = alink.index();
            if (itemIndex === selectId) {
                return;
            }
            itemIndex = selectId;
            $(alink).addClass('actives').siblings('li').removeClass('actives');
            $('.swiper-wrapper').eq(itemIndex).show().siblings('.swiper-wrapper').hide();
            // 如果选中菜单一
            switch (itemIndex) {
                case 0:
                    selectClass(item0);
                    break;
                case 1:
                    selectClass(item1);
                    break;
                case 2:
                    selectClass(item2);
                    break;
            }
            // 重置
            dropload.resetload();
        });

        function selectClass(object) {
            if (object.tabLoadEnd) {
                // 锁定
                dropload.lock('down');
                dropload.noData();
            } else {
                // 解锁
                dropload.unlock();
                dropload.noData(false);
            }
        }

        // dropload
        var dropload = $('.content').dropload({
            scrollArea: window,
            loadUpFn: function (me) {
                switch (itemIndex) {
                    case 0:
                        item0.index = 1;
                        item0.tabLoadEnd = false;
                        loadDate(itemIndex, item0, me, true);
                        break;
                    case 1:
                        item1.index = 1;
                        item1.tabLoadEnd = false;
                        loadDate(itemIndex, item1, me, true);
                        break;
                    case 2:
                        item2.index = 1;
                        item2.tabLoadEnd = false;
                        loadDate(itemIndex, item2, me, true);
                        break;
                }
            },
            loadDownFn: function (me) {
                switch (itemIndex) {
                    case 0:
                        loadDate(itemIndex, item0, me, false);
                        break;
                    case 1:
                        loadDate(itemIndex, item1, me, false);
                        break;
                    case 2:
                        loadDate(itemIndex, item2, me, false);
                        break;
                }
            },
            threshold: 100
        });
    });

    function loadDate(itemIndex, object, me, refresh) {
        $.ajax({
            type: 'GET',
            url: 'loadOrders.do',
            data: {
                "state": object.state,
                "pageIndex": object.index,
                "pageCount": object.count
            },
            dataType: 'json',
            success: function (result) {
                object.totalPage = result.totalPage;
                object.totalSize = result.totalSize;
                var data = $.parseJSON(result.commodityOrders);
                var arrLen = data.length;
                if (refresh) {
                    $('.swiper-wrapper').eq(itemIndex).html('');
                    object.currentSize = 0;
                }
                if (object.currentSize < object.totalSize) {
                    for (var i = 0; i < arrLen; i++) {
                        $('.swiper-wrapper').eq(itemIndex).append(adapter(itemIndex, data[i]));
                    }
                    object.currentSize += arrLen;
                }
                if (object.totalPage > object.index) {
                    object.index++;
                } else {
                    // 数据加载完
                    object.tabLoadEnd = true;
                    // 锁定
                    me.lock();
                    // 无数据
                    me.noData();
                }
                // 每次数据加载完，必须重置
                me.resetload();
            },
            error: function (xhr, type) {
                // 即使加载出错，也得重置
                me.noData();
                me.resetload();
            }
        });
    }

    function adapter(itemIndex, order) {
        var itemNode;
        switch (itemIndex) {
            case 0:
                itemNode = $('<li>\n' +
                    '                        <div>\n' +
                    '                            <div class="item">\n' +
                    '                                <div class="item-right">\n' +
                    '                                    <div class="title">' + order.orderDetailsList[0].commodityName + '</div>\n' +
                    '                                    <div class="subtitle">备注:' + order.remarks + '</div>\n' +
                    '                                    <div class="number">\n' +
                    '                                        <span class="price">￥' + order.totalPrice + '</span>\n' +
                    '                                        <a href="javascript:" onclick="exctueMethod(\'confirmOrder.do\',' + order.id + ')">接单</a>\n' +
                    '                                        <a href="javascript:" onclick="exctueMethod(\'cancelOrder.do\',' + order.id + ')">拒绝</a>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </li>');
                break;
            case 1:
                itemNode = $('<li>\n' +
                    '                        <div>\n' +
                    '                            <div class="item">\n' +
                    '                                <div class="item-right">\n' +
                    '                                    <div class="title">' + order.orderDetailsList[0].commodityName + '</div>\n' +
                    '                                    <div class="subtitle">备注:' + order.remarks + '</div>\n' +
                    '                                    <div class="number">\n' +
                    '                                        <span class="price">￥' + order.totalPrice + '</span>\n' +
                    '                                        <a href="javascript:" onclick="exctueMethod(\'completeOrder.do\',' + order.id + ')">完成</a>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </li>');
                break;
            case 2:
                itemNode = $('<li>\n' +
                    '                        <div>\n' +
                    '                            <div class="item">\n' +
                    '                                <div class="item-right">\n' +
                    '                                    <div class="title">' + order.orderDetailsList[0].commodityName + '</div>\n' +
                    '                                    <div class="subtitle">备注:' + order.remarks + '</div>\n' +
                    '                                    <div class="number">\n' +
                    '                                        <span class="price">￥' + order.totalPrice + '</span>\n' +
                    '                                        <a href="javascript:">删除</a>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </li>');
                break;
        }
        return itemNode;
    }

    function exctueMethod(url, orderId) {
        layer.open({
            content: "执行这个操作？"
            , btn: ['是的', '取消']
            , yes: function (index) {
                $.getJSON(url, {"orderId": orderId}, function (result) {
                    if (result.success) {
                        toast(result.msg);
                    } else {
                        toast('操作失败');
                    }
                });
                layer.close(index);
            }
        });
    }
</script>
</html>