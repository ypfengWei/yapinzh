<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>套餐管理</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/edit.css">
</head>
<body>
	<header>
		<div class="thead">
			<a href="#" onclick="history.go(-1)">
				<img src="${pageContext.request.contextPath }/img/return/return_1.png">
			</a>
		</div>
	</header>
	<article id="menu">
		<form>
			<div class="tbody">
				<div class="box">
			    	<span>选择菜品</span>
			    	<span>数量</span>
			    </div>
				<div class="frame">
					<div class="food">
						<div>
					        <input type="checkbox"  id="radio1"  name="radio"/>
					        <label for="radio1">
								<div>
									<img src="">
								</div>
								<div>
									<span>菜名</span>
								</div>
								<div>
									<span>￥价格</span>
									<span>x数量</span>
								</div>
					        </label>
						</div>
					</div>
				</div>
				<div class="details">
					<p>
						<label for="name">名称：</label>
						<input type="text"  id="name"  name="name"/>
					</p>
					<p>
						<label for="price">套餐价：</label>
						<input type="text"  id="price"  name="price"/>
					</p>
					<p>
						<label for="message">介绍：</label>
						<textarea  id="message" name="message" rows="5" cols="40"></textarea>
					</p>
				</div>
			</div>
		</form>
	</article>
	<footer>
		<div class="tfoot">
			<a href="#">完成</a>
		</div>
	</footer>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.3.1.js"></script>
</body>
</html>