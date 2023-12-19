<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, dto.BicyclePartsDto" %>
	

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/register_style.css">

<%
	BicyclePartsDto dto = (BicyclePartsDto)request.getAttribute("dto");
%>
<script type="text/javascript">

	//更新ボタンを押したときの実行メソッド
	function updateSubmit(form){
		if(!checkValue(form)){
			form.submit();
		}
	}

	//フォームの入力値をチェックするメソッド
	function checkValue(form){
		var str = "";
		var ret = false;
		var partsId = form.elements["parts_id"].value;
		var partsName = form.elements["parts_name"].value;
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
	
</script>

<title>自転車パーツ登録</title>
</head>
<div class="wrap">
	<header>
		<h1>Bicycle Parts Update</h1>
	</header>

	<body>
		<div class="wrap_register">
			<form action="BicyclePartsUpdate" name="update_form" enctype="multipart/form-data" method="post">
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
								<input type="hidden" name="parts_id" value="${ dto.getPartsId() }">
							</td>
						</tr>
						<tr>
							<th>商品名（必須）</th>
							<td><input type="text" name="parts_name" value="${ dto.getPartsName() }"></td>
						</tr>
						<tr>
							<th>メーカー</th>
							<td><select name="parts_maker" id="parts_maker">
									<option value="">メーカーを選択</option>
									<option value="cannondale" <% if(dto.getPartsMaker().equals("cannondale")){ %>selected<% ;} %>>cannondale</option>
									<option value="Specialized" <% if(dto.getPartsMaker().equals("Specialized")){ %>selected<% ;} %>>Specialized</option>
									<option value="Giant" <% if(dto.getPartsMaker().equals("Giant")){ %>selected<% ;} %>>Giant</option>
									<option value="MKS" <% if(dto.getPartsMaker().equals("MKS")){ %>selected<% ;} %>>MKS</option>
									<option value="CRANK BROTHERS" <% if(dto.getPartsMaker().equals("CRANK BROTHERS")){ %>selected<% ;} %>>CRANK BROTHERS	</option>
									<option value="VELO ORANGE" <% if(dto.getPartsMaker().equals("VELO ORANGE")){ %>selected<% ;} %>>VELO ORANGE</option>
									<option value="SURLY" <% if(dto.getPartsMaker().equals("SURLY")){ %>selected<% ;} %>>SURLY</option>
									<option value="Mavic" <% if(dto.getPartsMaker().equals("Mavic")){ %>selected<% ;} %>>Mavic</option>
							</select></td>
						</tr>
						<tr>
							<th>カテゴリ（必須）</th>
							<td><select name="category" id="category">
									<option value="">カテゴリを選択</option>
									<option value="フレーム" <% if(dto.getCategory().equals("フレーム")){ %>selected<% ;} %>>フレーム</option>
									<option value="ホイール" <% if(dto.getCategory().equals("ホイール")){ %>selected<% ;} %>>ホイール</option>
									<option value="ハンドル" <% if(dto.getCategory().equals("ハンドル")){ %>selected<% ;} %>>ハンドル</option>
									<option value="サドル" <% if(dto.getCategory().equals("サドル")){ %>selected<% ;} %>>サドル</option>
									<option value="ペダル" <% if(dto.getCategory().equals("ペダル")){ %>selected<% ;} %>>ペダル</option>
							</select></td>
						</tr>
						<tr>
							<th>種類（必須）</th>
							<td><select name="type" id="type">
									<option value="">種類を選択</option>
									<option value="ロード" <% if(dto.getType().equals("ロード")){ %>selected<% ;} %>>ロード</option>
									<option value="グラベル" <% if(dto.getType().equals("グラベル")){ %>selected<% ;} %>>グラベル</option>
									<option value="クロス" <% if(dto.getType().equals("クロス")){ %>selected<% ;} %>>クロス</option>
									<option value="マウンテン" <% if(dto.getType().equals("マウンテン")){ %>selected<% ;} %>>マウンテン</option>
							</select></td>
						</tr>
						<tr>
							<th>値段</th>
							<td><input type="text" name="price" id="price" value="${ dto.getPrice() }">円</td>
						</tr>
						<tr>
							<th>画像</th>
							<td><input type="file" name="parts_image" id="parts_image"></td>
						</tr>
						<tr>
							<th>元画像</th>
							<td>
								<img src="${pageContext.request.contextPath}/upload/bicycle_image/${ dto.getPartsImage() }" alt="${ dto.getPartsName() }の画像">
								<input type="hidden"  name="file_name" value="${ dto.getPartsImage() }">
							</td>
						</tr>
					</tbody>
				</table>
				<div class="wrap_btn">
					<input type="button" name="update" id="button" value="更新" onclick="updateSubmit(this.form)">
					<input type="button" name="clear" id="button" value="クリア" onclick="document.update_form.reset();">
				</div>
			</form>
			<div class="back">
				<a class="a_back" href="#" onclick="history.back()">戻る</a>
			</div>
		</div>
	</body>
</div>

</html>