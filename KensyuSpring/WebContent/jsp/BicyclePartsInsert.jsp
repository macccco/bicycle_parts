<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register_style.css">
    
    <script type="text/javascript">

    //登録ボタンを押したときの実行メソッド
    function registerSubmit(form){
		if(!checkValue(form)){
			if(window.confirm("本当に登録しますか?")) {
				form.submit();
			}
		}
    }
    
	//フォームの入力値をチェックするメソッド
	function checkValue(form){
		var str = "";
		var ret = false;
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
		
		if(isNaN(price)){
			str += "値段は半角数値で入力してください。\n";
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
        <h1>Bicycle Parts Insert</h1>
    </header>

    <body>
        <div class="wrap_register">
            <f:form modelAttribute="bicycleInsertForm" action="bicycleInsertRegisterAction" name="bicycleInsertForm" enctype="multipart/form-data" method="post">
                <table>
                    <thead>
                        <tr>
                            <th colspan="2">登録</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th>ID</th>
                            <td>
                            	${ partsId }
                            	<input type="hidden" name="partsId" value="${ partsId }">
                            </td>
                        </tr>
                        <tr>
                            <th>商品名（必須）</th>
                            <td><input type="text" name="partsName"></td>
                        </tr>
                        <tr>
                            <th>メーカー</th>
                            <td>
                                <select name="partsMaker" id="parts_maker">
                                    <option value="">メーカーを選択</option>
                                    <c:forEach var="maker" items="${ MAKERS }">
										<option value="${ maker }">${ maker }</option>
									</c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>カテゴリ（必須）</th>
                            <td>
                                <select name="category" id="category">
                                    <option value="">カテゴリを選択</option>
                                    <c:forEach var="category" items="${ CATEGORIES }">
										<option value="${ category }">${ category }</option>
									</c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>種類（必須）</th>
                            <td>
                                <select name="type" id="type">
                                    <option value="">種類を選択</option>
                                    <c:forEach var="type" items="${ TYPES }">
										<option value="${ type }">${ type }</option>
									</c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>値段</th>
                            <td><input type="text" name="price" id="price">円</td>
                        </tr>
                        <tr>
                            <th>画像</th>
                            <td><input type="file" name="partsImage" id="parts_image" accept="image/*"></td>
                        </tr>
                    </tbody>
                </table>
                <div class="wrap_btn">
                    <input type="button" name="register" id="button" value="登録" onclick="registerSubmit(this.form)">
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