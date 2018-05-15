<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>菜品管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/variety.css">
</head>
<body>
<header>
    <div class="thead">
        <a href="#" onclick="history.go(-1)"><img src="${pageContext.request.contextPath }/img/return/return_1.png">菜品管理</a>
    </div>
</header>
<article id="menu">
    <form>
        <div class="tbody">
            <div class="pic">
                <img id="introducePicture" onclick="onLoadImg()"/>
            </div>
            <div class="details">
                <p>
                    <label for="name">菜名：</label>
                    <input type="text" id="name" name="name"/>
                </p>
                <p>
                    <label>类别：</label><br/>
                </p>
                <p id="classificationSet">

                </p>
                <p>
                    <label for="discount">折扣：</label>
                    <input type="number" id="discount" name="discount"/>
                </p>
                <p>
                    <label for="price">单价：</label>
                    <input type="number" id="price" name="price"/>
                </p>
                <p>
                    <label for="introduceDetails">介绍：</label>
                    <textarea id="introduceDetails" name="introduceDetails" rows="9" cols="40"></textarea>
                </p>
            </div>
        </div>
    </form>
</article>
<footer>
    <div class="tfoot">
        <a href="#" onclick="saveDishes()">完成</a>
    </div>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/service.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</body>
<script>
    //拍照或从手机相册中选图接口
    var href = location.href.split('#')[0];
    var media_id;
    $(function () {
        $.getJSON("initJS_SDK.do", {
            "pageUrl": "" + href + ""
        }, function (result) {
            if (result.success) {
                //初始化接口环境
                wx.config({
                    appId: result.appid,
                    timestamp: result.timestamp,
                    nonceStr: result.noncestr,
                    signature: result.signature,
                    jsApiList: ['chooseImage', 'uploadImage']
                });
            }
        });
    });
    var commodityId = GetQueryString("commodityId");
    if (commodityId) {
        $.getJSON('loadCommodity.do', {"commodityId": commodityId}, function (result) {
            if (result.success) {
                var commodity = result.commodity;
                var existClassificationSet = commodity.commodityClassificationSet;
                $('#introducePicture').attr('src', '/yapingzh/picture/' + commodity.introducePicture);
                $('#name').attr('value', commodity.name);
                $('#price').attr('value', commodity.price);
                $('#discount').attr('value', commodity.discount);
                $('#introduceDetails').text(commodity.introduceDetails);
                getClassifications(function (result) {
                    $.each(result.classify, function (i, item) {
                        var id = item.id;
                        var tag = $('<label><input type="checkbox" name="commodityClassificationId" value="' + item.id + '"/>' + item.commodityType + '</label>');
                        $.each(existClassificationSet, function (j, item2) {
                            if (item2.id === id) {
                                $(tag).find('input').attr('checked', 'checked');
                            }
                        });
                        $('#classificationSet').append(tag);
                    });
                });
            } else {
                toast('加载商品信息失败');
            }
        });
    }

    function onLoadImg() {
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                //上传图片接口
                wx.uploadImage({
                    localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        media_id = res.serverId; // 返回图片的服务器端ID
                        $('#introducePicture').attr('src', localIds[0]);
                    }
                });
            }
        });
    }

    function saveDishes() {
        var classifications = [];
        layer.open({type: 2});
        var name = $('#name').val();
        var introduceDetails = $('#introduceDetails').text();
        var price = $('#price').val();
        var discount = $('#discount').val();
        var commodityClassificationIds = $("input[name='commodityClassificationId']");
        var j = 0;
        $.each(commodityClassificationIds, function (i, item) {
            if ($(item).is(':checked')) {
                var classification = {};
                classification.commodityId = commodityId;
                classification.classificationId = $(item).val();
                classifications[j] = classification;
                j++;
            }
        });
        if (classifications.length === 0) {
            toast('请选择类别');
            return;
        }
        try {
            $.ajax({
                type: "POST",
                url: "updateCommodity.do",
                data: {
                    "commodity.id": commodityId,
                    "commodity.name": name,
                    "commodity.introduceDetails": introduceDetails,
                    "commodity.price": price,
                    "commodity.discount": discount,
                    "media_id": media_id ? media_id : "",
                    "classifications": JSON.stringify(classifications)
                },
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        toast('保存成功');
                    } else {
                        toast('保存失败');
                    }
                },
                complete: function () {
                    setTimeout(function () {
                        layer.closeAll();
                        location.href = 'backManager.do';
                    }, 500);
                }
            });
        } catch (e) {
            console.log(e);
        }
    }
</script>
</html>