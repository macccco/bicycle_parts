<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

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
			form.submit();
		}
    }
    
	//フォームの入力値をチェックするメソッド
	function checkValue(form){
		var str = "";
		var ret = false;
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
		
		if(isNaN(price)){
			str += "値段は半角数値で入力してください。\n";
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
        <h1>Bicycle Parts Insert</h1>
    </header>

    <body>
        <div class="wrap_register">
            <form action="BicyclePartsInsert" name="register_form" enctype="multipart/form-data" method="post">
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
                            	<input type="hidden" name="parts_id" value="${ partsId }">
                            </td>
                        </tr>
                        <tr>
                            <th>商品名（必須）</th>
                            <td><input type="text" name="parts_name"></td>
                        </tr>
                        <tr>
                            <th>メーカー</th>
                            <td>
                                <select name="parts_maker" id="parts_maker">
                                    <option value="">メーカーを選択</option>
                                    <option value="cannondale">cannondale</option>
                                    <option value="Specialized">Specialized</option>
                                    <option value="Giant">Giant</option>
                                    <option value="MKS">MKS</option>
									<option value="CRANK BROTHERS">CRANK BROTHERS	</option>
									<option value="VELO ORANGE">VELO ORANGE</option>
									<option value="SURLY">SURLY</option>
									<option value="Mavic">Mavic</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>カテゴリ（必須）</th>
                            <td>
                                <select name="category" id="category">
                                    <option value="">カテゴリを選択</option>
                                    <option value="フレーム">フレーム</option>
                                    <option value="ホイール">ホイール</option>
                                    <option value="ハンドル">ハンドル</option>
                                    <option value="サドル">サドル</option>
                                    <option value="ペダル">ペダル</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>種類（必須）</th>
                            <td>
                                <select name="type" id="type">
                                    <option value="">種類を選択</option>
                                    <option value="ロード">ロード</option>
                                    <option value="グラベル">グラベル</option>
                                    <option value="クロス">クロス</option>
                                    <option value="マウンテン">マウンテン</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>値段</th>
                            <td><input type="text" name="price" id="price">円</td>
                        </tr>
                        <tr>
                            <th>画像</th>
                            <td><input type="file" name="parts_image" id="parts_image"></td>
                        </tr>
                    </tbody>
                </table>
                <div class="wrap_btn">
                    <input type="button" name="register" id="button" value="登録" onclick="registerSubmit(this.form)">
                    <input type="button" name="clear" id="button" value="クリア" onclick="document.register_form.reset();">
                </div>
            </form>
            <div class="back">
                <a class="a_back" href="#" onclick="history.back()">戻る</a>
            </div>
        </div>
    </body>
</div>

</html>