<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dto.BicyclePartsDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	List<BicyclePartsDto> list = (List<BicyclePartsDto>)request.getAttribute("dbdata");
	session.setAttribute("dbdata", list);
%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_style.css">

<script type="text/javascript">

	//action属性の設定メソッド
	function actionSwitch(form) {
		var ret = true;
		//押されたボタンオブジェクトを格納
		var button = event.target;
		if(button.value == "更新" || button.value == "削除") {
			ret = checkRadio(form);
		}
		//action属性にボタンが保持しているact属性の内容をコピー
		if(ret) {
			form.action = button.getAttribute("act");
			form.submit();
		}
	}

	//検索フォームの入力値確認メソッド
	function checkValue(form) {
		var str = "";
		var ret = false;
		if(isNaN(form.elements["parts_id"].value)){
			str += "IDは数値を入力してください。\n";
			ret = true;
		}
		if(isNaN(form.elements["lower_price"].value) || isNaN(form.elements["upper_price"].value)){
			str += "値段は数値を入力してください。\n";
			ret = true;
		}
		if(ret){
			alert(str);
		}else{
			form.submit();
		}
	}

	//ラジオボタン確認メソッド
	function checkRadio(form){
		//ラジオボタンの数だけ判定を繰り返す
		for(var i = 0; i < form.select_id.length; i++){
		 	// i番目のラジオボタンがチェックされているかを判定
			if(form.select_id[i].checked){
				return true;
			}
		}
		// 何も選択されていない場合の処理
		alert("行が選択されていません。");
		return false;
	}

	
</script>

<title>自転車パーツ検索</title>
</head>


<div class="wrap">
	<header>
		<h1>Bicycle Parts Search</h1>
	</header>

	<body>
		<div class="flex">
			<div class="search">
				<form action="bicyclePartsAction" name="search_form" class="search_form" method="post">
					<table>
						<thead>
							<tr>
								<th colspan="3">検索</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>ID</th>
								<td><input type="text"  class="box" name="parts_id" id="parts_id"></td>
							</tr>
							<tr>
								<th>商品名</th>
								<td>
									<input type="radio" name="like" value="全一致" checked>全一致
									<input type="radio" name="like" value="あいまい">あいまい
									<input type="text" class="box" name="parts_name" id="parts_name">
								</td>
							</tr>
							<tr>
								<th>メーカー</th>
								<td><select  class="box" name="parts_maker" id="parts_maker">
									<option value="">メーカーを選択</option>
									<option value="cannondale">cannondale</option>
									<option value="Specialized">Specialized</option>
									<option value="Giant">Giant</option>
									<option value="MKS">MKS</option>
									<option value="CRANK BROTHERS">CRANK BROTHERS	</option>
									<option value="VELO ORANGE">VELO ORANGE</option>
									<option value="SURLY">SURLY</option>
									<option value="Mavic">Mavic</option>
								</select></td>
							</tr>
							<tr>
								<th>カテゴリ</th>
								<td><select  class="box" name="category" id="category">
										<option value="">カテゴリを選択</option>
										<option value="フレーム">フレーム</option>
										<option value="ホイール">ホイール</option>
										<option value="ハンドル">ハンドル</option>
										<option value="サドル">サドル</option>
										<option value="ペダル">ペダル</option>
								</select></td>
							</tr>
							<tr>
								<th>種類</th>
								<td><select  class="box" name="type" id="type">
										<option value="">種類を選択</option>
										<option value="ロード">ロード</option>
										<option value="グラベル">グラベル</option>
										<option value="クロス">クロス</option>
										<option value="マウンテン">マウンテン</option>
								</select></td>
							</tr>
							<tr>
								<th>値段</th>
								<td>
									<div class="flex_price">
										<input type="text" name="lower_price" id="lower_price">～<input type="text" name="upper_price"  id="upper_price">
									</div>
								</td>
							</tr>
							<tr>
								<th>登録日</th>
								<td>
									<input type="date" name="lower_create" id="lower_create" min="2000-01-01">～<input type="date" name="upper_create"  id="upper_create" min="2000-01-01">
								</td>
							</tr>
							<tr>
								<th>更新日</th>
								<td>
									<input type="date" name="lower_update" id="lower_update" min="2000-01-01">～<input type="date" name="upper_update"  id="upper_update" min="2000-01-01">
								</td>
							</tr>
						</tbody>
					</table>
					<div class="btn AorO">
						<input type="radio" name="AorO" value="AND" checked>AND
						<input type="radio" name="AorO" value="OR">OR
					</div>
					<div class="btn search">
						<input type="button" name="search" id="button" value="検索" onclick="checkValue(this.form)">
						<input type="button" name="clear" id="button" value="クリア" onclick="document.search_form.reset()">
					</div>
				</form>
			</div>

			<div class="logout">
				<form action="Login" name="logout" method="post">
					<p>こんにちは ${userId} さん</p>
					<input type="button" value="ログアウト" id="button" onclick="document.logout.submit()">
				</form>
			</div>
		</div>


		<div class="list">
			<form action="#" name="list_form" method="post">
				<table>
					<thead>
						<tr>
							<th>選択</th>
							<th>ID</th>
							<th>画像</th>
							<th>商品名</th>
							<th>メーカー</th>
							<th>カテゴリ</th>
							<th>種類</th>
							<th>値段</th>
							<th>登録日</th>
							<th>更新日</th>
						</tr>
					</thead>
					
					<tbody>
					<%-- 受け取ったリストをすべて表示 --%>
						<c:forEach items="${dbdata}" var="dbdataLine" varStatus="status">
					    <tr>
					      <td>
					      	<input type="radio" name="select_id" id="select_id" value="${ dbdataLine.partsId }">
					      	<input type="hidden" name="${ dbdataLine.partsId }" value="${ status.index }">
					      </td>
					      <td>${ dbdataLine.partsId }</td>
					      <td>
					      	<img src="${ pageContext.request.contextPath }/upload/bicycle_image/${ dbdataLine.partsImage }" alt="${ dbdataLine.partsName }の画像">
					      	<input type="hidden" name="file_${ dbdataLine.partsId }" value="${ dbdataLine.partsImage }">
					      </td>
					      <td>${ dbdataLine.partsName }</td>
					      <td>${ dbdataLine.partsMaker }</td>
					      <td>${ dbdataLine.category }</td>
					      <td>${ dbdataLine.type }</td>
					      <td><fmt:formatNumber value="${ dbdataLine.price }" currencyCode="JPY" type="CURRENCY" groupingUsed="true" maxFractionDigits="0" /></td>
					      <td>
						      	<c:forEach var="str" items="${ fn:split(dbdataLine.dateCreate,' ') }" >
									${str}<br>
								</c:forEach>
							</td>
					      <td>
						      	<c:forEach var="str" items="${ fn:split(dbdataLine.dateUpdate,' ') }" >
									${str}<br>
								</c:forEach>
							</td>
					    </tr>
					    </c:forEach>
					</tbody>

				</table>
				<div class="btn list">
					<input type="button" name="register" id="button" value="新規登録" onclick="actionSwitch(this.form)" act="DspBicyclePartsInsert">
					<input type="button" name="update" id="button" value="更新" onclick="actionSwitch(this.form)" act="DspBicyclePartsUpdate">
					<input type="button" name="delete" id="button" value="削除" onclick="actionSwitch(this.form)" act="BicyclePartsDelete">
				</div>
			</form>
		</div>

	</body>
</div>

</html>