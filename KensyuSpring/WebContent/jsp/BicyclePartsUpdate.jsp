<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, dto.BicyclePartsDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<%
	BicyclePartsDto dto = (BicyclePartsDto)request.getAttribute("dto");
	request.setAttribute("dtoMaker", dto.getPartsMaker());
	request.setAttribute("dtoCategory", dto.getCategory());
	request.setAttribute("dtoType", dto.getType());
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/register_style.css">

<script type="text/javascript">

	//更新ボタンを押したときの実行メソッド
	function updateSubmit(form){
		if(!checkValue(form)){
			if(window.confirm("本当に更新しますか?")) {
				form.submit();
			}
		}
	}

	//フォームの入力値をチェックするメソッド
	function checkValue(form){
		var str = "";
		var ret = false;
		var partsId = form.elements["partsId"].value;
		var partsName = form.elements["partsName"].value;
		var category = form.elements["category"].value;
		var type = form.elements["type"].value;
		var price = form.elements["price"].value;
		
		
		if(partsName == ""){
			str += "商品名を記入してください。\n";
			ret = true;
		}
		
		if(category == ""){
			str += "カテゴリを選択してください。\n";
			ret = true;
		}
		
		if(type == ""){
			str += "種類を選択してください。\n";
			ret = true;
		}

		if (isNaN(price)) {
			str += "値段は半角数字で入力してください。\n";
			ret = true;
		}
		
		if(ret){
			alert(str);
		}
		
		return ret;
	}


	function clearForm(form) {
		form.elements["partsName"].value = "";
		form.elements["partsMaker"].options[0].selected = true;
		form.elements["category"].options[0].selected = true;
		form.elements["type"].options[0].selected = true;
		form.elements["price"].value = "";
		form.elements["partsImage"].value = "";
	}
	
</script>

<title>自転車パーツ登録</title>
</head>
<div class="wrap">
	<header>
		<h1>Bicycle Parts Update</h1>
	</header>

	<body>
		<div class="wrap_register">
			<f:form modelAttribute="bicycleUpdateForm" action="bicycleUpdateRegisterAction" name="bicycleUpdateForm" enctype="multipart/form-data" method="post">
				<table>
					<thead>
						<tr>
							<th colspan="2">更新</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>ID</th>
							<td>
								${ dto.getPartsId() }
								<input type="hidden" name="partsId" value="${ dto.getPartsId() }">
							</td>
						</tr>
						<tr>
							<th>商品名（必須）</th>
							<td><input type="text" name="partsName" value="${ dto.getPartsName() }"></td>
						</tr>
						<tr>
							<th>メーカー</th>
							<td><select name="partsMaker" id="parts_maker">
									<option value="">メーカーを選択</option>
									<c:forEach var="maker" items="${ MAKERS }">
										<option value="${ maker }" <c:if test="${ dtoMaker == maker }">selected</c:if>>${ maker }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>カテゴリ（必須）</th>
							<td><select name="category" id="category">
									<option value="">カテゴリを選択</option>
									<c:forEach var="category" items="${ CATEGORIES }">
										<option value="${ category }" <c:if test="${ dtoCategory == category }">selected</c:if>>${ category }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>種類（必須）</th>
							<td><select name="type" id="type">
									<option value="">種類を選択</option>
									<c:forEach var="type" items="${ TYPES }">
										<option value="${ type }" <c:if test="${ dtoType == type }">selected</c:if>>${ type }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<th>値段</th>
							<td><input type="text" name="price" id="price" value="${ dto.getPrice() }">円</td>
						</tr>
						<tr>
							<th>画像</th>
							<td><input type="file" name="partsImage" id="parts_image" accept="image/*"></td>
						</tr>
						<tr>
							<th>元画像</th>
							<td>
								  <img src="${pageContext.request.contextPath}/upload/bicycle_image/${ dto.getPartsImage() }" alt="${ dto.getPartsName() }の画像">
								<input type="hidden"  name="lastFileName" value="${ dto.getPartsImage() }">
							</td>
						</tr>
					</tbody>
				</table>
				<div class="wrap_btn">
					<input type="button" name="update" id="button" value="更新" onclick="updateSubmit(this.form)">
					<input type="button" name="clear" id="button" value="クリア" onclick="clearForm(this.form)">
				</div>
			</f:form>
			<div class="back">
				<a class="a_back" href="#" onclick="history.back()">戻る</a>
			</div>
		</div>
	</body>
</div>

</html>