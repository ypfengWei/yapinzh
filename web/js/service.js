/*查询有商品的类别列表*/
function getClassify(back) {
    $.getJSON("/yapingzh/getClassify.do", function (result) {
        if (result.success) {
            back(result);
        }
    });
}

/*查询所有类别*/
function getClassifications(back) {
    $.getJSON("/yapingzh/getClassifyAll.do", function (result) {
        if (result.success) {
            back(result);
        }
    });
}

function addClassify() {
    var classifyName = $('#classifyName').val();
    if ($.trim(classifyName) === '') {
        toast('填写类别名');
        return;
    }
    $.ajax({
        url: "/yapingzh/addClassify.do",
        data: {"commodityClassification.commodityType": "" + classifyName + ""},
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                toast('添加成功');
                history.go(-1);
            } else {
                toast('类别重复');
            }
        }, error: function () {
            toast('未知错误');
        }
    });
}