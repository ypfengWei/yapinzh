<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>新增类别</title>
    <script src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
    <script src="${pageContext.request.contextPath }/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script src="${pageContext.request.contextPath }/js/service.js"></script>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>新增类别</h1>
        </header>
    </div>
    <div id="content">
        <ul>
            <li>
                <input style="height: 2rem;" id="classifyName" placeholder="类别名称"/>
            </li>
        </ul>
        <input class="footBtn" type="button" value="保存类别" onClick="addClassify()">
    </div>
</div>
</body>
</html>