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
                <img id="introducePicture" onclick="onLoadImg()" src="${pageContext.request.contextPath }/img/icon/icon_8.png"/>
            </div>
            <div class="details">
                <p>
                    <label for="name">菜名：</label>
                    <input type="text" id="name" name="name"/>
                </p>
                <p>
                    <label for="classifyId">类别：</label>
                    <select id="classifyId">
                    </select>
                    <a href="javascript:void(0)" onclick="location.href='addClassifyView.do'"
                       title="添加分类">添加分类</a>
                </p>

                <p>
                    <label for="discount">折扣：</label>
                    <input type="number" placeholder="一折就填数字1或1.0" id="discount" name="discount"/>
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
<script src="${pageContext.request.contextPath }/js/layer.js"></script>
<script src="${pageContext.request.contextPath }/js/utils.js"></script>
<script src="${pageContext.request.contextPath }/js/service.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</body>
<script>
    //拍照或从手机相册中选图接口
    var href = location.href.split('#')[0];
    var media_id;
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
                    jsApiList: ['chooseImage', 'uploadImage']
                });
            }
        });
        getCommodityClassify();
    });

    function getCommodityClassify() {
        getClassifications(function (result) {
            $.each(result.classify, function (i, item) {
                $('<option value="' + item.id + '">' + item.commodityType + '</option>').appendTo($('#classifyId'));
            });
        });
    }

    function saveDishes() {
        layer.open({type: 2});
        if (!media_id) {
            toast('请选择图片');
            return;
        }
        var name = $('#name').val();
        var introduceDetails = $('#introduceDetails').text();
        var price = $('#price').val();
        var discount = $('#discount').val();
        var classifyId = $('#classifyId').val();
        $.ajax({
            url: "insertCommodity.do",
            dataType: "JSON",
            data: {
                "commodity.name": name,
                "commodity.introduceDetails": introduceDetails,
                "commodity.price": price,
                "commodity.discount": discount,
                "media_id": media_id,
                "commodityClassification.id": classifyId
            },
            success: function (result) {
                if (result.success) {
                    toast('保存成功');
                } else {
                    toast('保存失败');
                }
            }, complete: function () {
                setTimeout(function () {
                    layer.closeAll();
                    location.reload();
                }, 500);
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
</script>
</html>