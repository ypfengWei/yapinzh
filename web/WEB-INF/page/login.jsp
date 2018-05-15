<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css">
    <script src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
    <script src="${pageContext.request.contextPath }/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
</head>
<body>
<form action="login.do" method="post" onsubmit="return loginchk() ">
    <div class="login">
        <img src="${pageContext.request.contextPath }/img/icon/icon_16.png">
        <p>
            <img src="${pageContext.request.contextPath }/img/icon/icon_15.png">
            <label for="name">账号：</label>
            <span>
					<input id="name" name="sellers.sellersName" placeholder="请输入登录名"/>
				</span>
        </p>
        <p>
            <img src="${pageContext.request.contextPath }/img/icon/icon_12.png">
            <label for="pwd">密码：</label>
            <span>
					<input type="password" id="pwd" name="sellers.loginPassword" placeholder="请输入登录密码"/>
				</span>
        </p>
        <img src="${pageContext.request.contextPath }/img/icon/icon_13.png">
        <img id="loginImg" src="${pageContext.request.contextPath }/img/icon/icon_14.png"/>
    </div>
</form>
</body>
<script>
    $(function () {
        var sellersId = localStorage.getItem('sellersId');
        if (sellersId) {
            location.href = 'backManager.do';
        }
    });
</script>
<script>
    $(function () {
        $('#loginImg').click(function () {
            var name = $('#name').val();
            var pwd = $('#pwd').val();
            if ($.trim(name) === '' || $.trim(pwd) === '') {
                toast('登录名或密码不能为空!');
                return;
            }
            $.ajax({
                type: "POST",
                url: "login.do",
                dataType: "json",
                data: {"sellers.sellersName": name, "sellers.loginPassword": encode64(pwd)},
                success: function (result) {
                    if (result.success) {
                        toast('登录成功!');
                        localStorage.setItem('sellersId', result.sellersId);
                        setTimeout(function () {
                            location.href = 'backManager.do';
                        }, 1000)
                    } else {
                        toast('登录失败');
                    }
                }
            });
        });
    });
</script>
</html>