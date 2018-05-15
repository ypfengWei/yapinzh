window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
window.db;
var versionCode = 1;
var dbName = 'yapin.db';
$(function () {
    loadDB();
});

function loadDB() {
    if (!window.indexedDB) {
        toast("你的浏览器不支持IndexedDB");
    } else {
        var request = window.indexedDB.open(dbName, versionCode);
        request.onerror = function (event) {
            console.log("打开DB失败", event);
        };
        request.onupgradeneeded = function (event) {
            window.db = event.target.result;
            if (!window.db.objectStoreNames.contains('cart')) {
                window.db.createObjectStore("cart", {keyPath: "id"});
            }
        };
        request.onsuccess = function (event) {
            window.db = event.target.result;
            orderAccounting();
        };
    }
}

/*添加到购物车*/
function addCart_Item(commodityId, commodityName, imgPath, price) {
    event.cancelBubble = true;
    var newCartItem = {
        id: commodityId,
        commodityName: commodityName,
        imgPath: imgPath,
        price: price,
        itemSize: 1
    };
    add2Cart(newCartItem, function (result) {
        if (result) {
            queryCartAll();
            toast('已加入购物车');
        }
    });
}

/*添加商品到购物项*/
function add2Cart(newCartItem, callback) {
    var transaction = window.db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.get(newCartItem.id);
    request.onsuccess = function (event) {
        var cartItem = event.target.result;
        if (cartItem == null) {
            var request = store.add(newCartItem);
            request.onsuccess = function () {
                orderAccounting();
                callback(true);
            };
        } else {
            cartItem.itemSize += newCartItem.itemSize;
            var request = store.put(cartItem);
            request.onsuccess = function () {
                orderAccounting();
                callback(true);
            }
        }
    };
}

//增减购物项
function changeOrderCount(id) {
    event.cancelBubble = true;
    var transaction = window.db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.get(id);
    request.onsuccess = function (event) {
        var commodity = event.target.result;
        if (commodity) {
            var oldSize = commodity.itemSize;
            if (oldSize >= 0) {
                oldSize--;
                if (oldSize === 0) {
                    delCart_item(id)
                } else {
                    commodity.itemSize = oldSize;
                    store.put(commodity);
                }
                queryCartAll();
                orderAccounting();
                checkCart(function (result) {
                    if(!result){
                        if($('.order').is(':visible')){
                            $('.order').hide();
                        }
                    }
                });
            }
        }
    }
}

//计算订单总价格
function orderAccounting() {
    var money = 0.00;
    var transaction = db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res != null) {
            var commodity = res.value;
            money += commodity.price * commodity.itemSize;
            res.continue();
        } else
            $("#totalPrice").html("￥" + money.toFixed(2));
    };
}

//删除购物项
function delCart_item(id) {
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.delete(id);
    request.onsuccess = function () {
        orderAccounting();
    };
}

//清空购物车
function emptyCart() {
    var transaction = window.db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res) {
            store.clear();
            queryCartAll();
            orderAccounting();
            $(".order").toggle();
        } else {
            toast('赶快买一份吧');
        }
    };
}

//获取购物车所有商品数据
function queryCartAll() {
    $('#cartlist').html('');
    var transaction = window.db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res) {
            var commodity = res.value;
            $('<li v-for="item in goods">\n' +
                '                    <div>\n' +
                '                        <div class="item">\n' +
                '                            <div class="item-right_on">\n' +
                '                                <div class="title">' + commodity.commodityName + '</div>\n' +
                '                                <div class="number">\n' +
                '                                    <span class="price">￥' + commodity.price + '</span>\n' +
                '                                    <p class="plan">\n' +
                '                                        <a href="#" onclick="changeOrderCount(\'' + commodity.id + '\')"><img src="/yapingzh/img/icon/icon_4.png"></a>\n' +
                '                                        <span>' + commodity.itemSize + '</span>\n' +
                '                                        <a href="#" onclick="addCart_Item(\'' + commodity.id + '\',\'' + commodity.name + '\',\'' + commodity.introducePicture + '\',\'' + commodity.price + '\')"><img\n' +
                '                                                src="/yapingzh/img/icon/icon_8.png"></a>\n' +
                '                                    </p>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </li>').appendTo($("#cartlist"));
            res.continue();
        }
    }
}

//提交购物车所有商品数据
function submitCartAll(callback) {
    var orderDetails = [];
    var index = 0;
    var transaction = db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res != null) {
            var cartItem = res.value;
            var orderDetail = {};
            orderDetail.commodityId = cartItem.id;
            orderDetail.price = cartItem.price;
            orderDetail.commodityName = cartItem.commodityName;
            orderDetail.commodityNumber = cartItem.itemSize;
            orderDetails[index] = orderDetail;
            index++;
            res.continue();
        } else {
            callback(JSON.stringify(orderDetails));
        }
    };
}

function addOrder(sign, back) {
    var deliveryinfo;
    var seat_number;
    var openid = localStorage.getItem('openid');
    var appointmentTime = $('#appointment_time').val();
    var remarks = $('#remarks').val();
    if (!checkAppointmentTime(appointmentTime)) {
        return;
    }
    if (sign === 0) {
        deliveryinfo = localStorage.getItem('deliveryinfo');
        if (!deliveryinfo) {
            toast('请选择收货地址');
            return;
        }
        deliveryinfo = $.parseJSON(deliveryinfo);
    } else {
        seat_number = $('#seat_number').val();
        if ($.trim(seat_number) === '' || !/^\d+$/.test(seat_number)) {
            toast('请输入座号');
            return;
        }
    }
    submitCartAll(function (cartItem) {
        var array = $.parseJSON(cartItem);
        if (array.length > 0) {
            var commodityOrder = {};
            commodityOrder.sign = sign;
            commodityOrder.openid = openid;
            commodityOrder.remarks = remarks;
            commodityOrder.appointmentTime = appointmentTime;
            commodityOrder.userAddress = deliveryinfo ? (deliveryinfo.recevername + ';' + deliveryinfo.recevertel + ';' + deliveryinfo.receveraddr) : seat_number;
            $.ajax({
                type: "POST",
                url: "/yapingzh/addOrder.do",
                data: {
                    "commodityOrder": JSON.stringify(commodityOrder),
                    "orderDetailsList": JSON.stringify(array)
                },
                dataType: "JSON",
                success: function (res) {
                    if (res.success) {
                        back(res.data);
                    } else {
                        toast('订单错误');
                    }
                },
                error: function () {
                    toast('请求错误');
                }
            });
        }
    });
}

function checkAppointmentTime(appointmentTime) {
    if ($.trim(appointmentTime) !== '') {
        var timestamp = new Date().getTime();
        var atTime = new Date();
        atTime.setHours(parseInt(appointmentTime.split(':')[0]));
        atTime.setMinutes(parseInt(appointmentTime.split(':')[1]));
        if (timestamp > atTime.getTime()) {
            toast('预约时间过去了');
            return false;
        }
        var t_time = (atTime.getTime() - timestamp) / 1000;
        if (t_time > 3 * 3600) {
            toast('只能提前3小时预约');
            return false;
        }
    }
    return true;
}

function showView(sign) {
    checkCart(function (result) {
        if (result) {
            if (!sign || sign == 0) {
                location.href = 'yapingzh/settlement.jsp';
            } else {
                $.getJSON('/yapingzh/getSeatNumber.do', {"openid": localStorage.openid}, function (result) {
                    location.href = 'settlement1.jsp?seatNumber=' + result.seatNumber;
                });
            }
        } else {
            toast('赶快买一份吧');
        }
    });
}

function checkCart(back) {
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res) {
            back(true);
        } else {
            back(false);
        }
    };
}