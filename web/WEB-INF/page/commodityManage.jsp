<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>商品</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/commodity.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/dropload.css">
</head>
<body>
<header>
    <div class="thead">
        <a href="#" onclick=""><img src="${pageContext.request.contextPath }/img/return/return_1.png">商品管理</a>
    </div>
</header>
<article id="menu">
    <div class="frame">
        <ul id="commoditys">

        </ul>
    </div>
</article>
<footer>
    <div class="tfoot">
        <a href="${pageContext.request.contextPath }/addCommodityView.do" class="shopping"><span>添加商品</span></a>
        <a href="${pageContext.request.contextPath }/orderManger.do" class="settlement"><span>订单管理</span></a>
    </div>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/dropload.min.js"></script>
</body>
<script>
    // 页数
    var index = 1;
    var totalPage = 1;
    var currentSize = 0;
    var totalSize = 0;
    var pageCount = 20;

    $(function () {
        // dropload
        $('.frame').dropload({
            scrollArea: window,
            loadUpFn: function (me) {
                // 重置页数，重新获取loadDownFn的数据
                index = 1;
                loadData(me, true);
            },
            loadDownFn: function (me) {
                loadData(me, false);
            },
            threshold: 100
        });
    });

    function loadData(me, refresh) {
        $.ajax({
            type: 'GET',
            url: '/yapingzh/getCommodityArray.do',
            data: {
                "pageIndex": index,
                "pageCount": pageCount
            },
            dataType: 'json',
            success: function (result) {
                // 拼接HTML
                totalPage = result.totalPage;
                var data = $.parseJSON(result.commodityArray);
                totalSize = result.totalSize;
                var arrLen = data.length;
                if (refresh) {
                    $('#commoditys').html('');
                    currentSize = 0;
                }
                if (currentSize < totalSize) {
                    $.each(data, function (j, item) {
                        var visible = item.visible;
                        var text;
                        if (visible) {
                            text = '下架';
                        } else {
                            text = '上架';
                        }
                        $('<li>\n' +
                            '                <div class="item">\n' +
                            '                    <div class="item-left">\n' +
                            '                        <div class="item-img"><img src="/yapingzh/picture/' + item.introducePicture + '"></div>\n' +
                            '                    </div>\n' +
                            '                    <div class="item-right">\n' +
                            '                        <div class="title">' + item.name + '</div>\n' +
                            '                        <div class="category">\n' +
                            '                            <a href="editClassifyView.do?commodityId=' + item.id + '">修改</a>\n' +
                            '                            <a href="#" onclick="deleteCommodity(\'' + item.id + '\')">删除</a>\n' +
                            '                        </div>\n' +
                            '                        <div class="number">\n' +
                            '                            <span class="price">￥' + item.price + '</span>\n' +
                            '                            <a href="javascript:" onclick="managerCommodity(\'' + item.id + '\',' + !visible + ')">' + text + '</a>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>\n' +
                            '            </li>').appendTo($('#commoditys'));
                    });
                    currentSize += arrLen;
                }
                if (totalPage > index) {
                    index++;
                } else {
                    // 锁定
                    me.lock();
                    // 无数据
                    me.noData();
                }
                me.resetload();
            },
            error: function (xhr, type) {
                me.noData();
                // 即使加载出错，也得重置
                me.resetload();
            }
        });
    }

    function managerCommodity(commodityId, visible) {
        layer.open({
            content: "执行这个操作？"
            , btn: ['是的', '取消']
            , yes: function (index) {
                $.getJSON('/yapingzh/commodityVisible.do', {
                    "commodity.id": commodityId,
                    "commodity.visible": visible
                }, function (result) {
                    if (result.success) {
                        toast('操作成功');
                    } else {
                        toast('操作失败');
                    }
                });
                layer.close(index);
            }
        });
    }

    function deleteCommodity(commodityId) {
        layer.open({
            content: "要删除吗？"
            , btn: ['是的', '取消']
            , yes: function (index) {
                $.getJSON('/yapingzh/deleteCommodity.do', {
                    "commodity.id": commodityId
                }, function (result) {
                    if (result.success) {
                        toast('操作成功');
                        setTimeout(function () {
                            layer.closeAll();
                            location.reload();
                        }, 500);
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