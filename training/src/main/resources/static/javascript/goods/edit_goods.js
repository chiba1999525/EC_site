
	
	let key = 0;
	function loadImage(obj) {
	    for (i = 0; i < obj.files.length; i++) {
	        var fileReader = new FileReader();
	        fileReader.onload = (function (e) {
	            var field = document.getElementById("imgPreviewField");
	            var figure = document.createElement("figure");
	            var rmBtn = document.createElement("input");
	            var img = new Image();
	            img.src = e.target.result;
	            rmBtn.type = "button";
	            rmBtn.name = key;
	            rmBtn.value = "削除";
	            rmBtn.onclick = (function () {
	                var element = document.getElementById("img-" + String(rmBtn.name)).remove();
	            });
	            figure.setAttribute("id", "img-" + key);
	            figure.appendChild(img);
	            figure.appendChild(rmBtn)
	            field.appendChild(figure);
	            key++;
	        });
	        fileReader.readAsDataURL(obj.files[i]);
	    }
	}

$(function () {
	
	//価格デフォルト
	let price = $("#price").val()
	var tax_price = price * 11/10;  // 税込み価格を計算
    $('#tax_price').html(tax_price);
	$('#tax_price_input').val(tax_price); 
	
	// inputフィールドに変更があった場合
    $('#price').on('input', function() {
        var price = parseFloat($(this).val());  // 入力された価格を取得し、数値に変換
        if (!isNaN(price)) {  // 値が数値であることを確認
            var tax_price = price * 11/10;  // 税込み価格を計算
            $('#tax_price').html(tax_price);
			$('#tax_price_input').val(tax_price);  

        } else {
            $('#tax_price').val().html("0");  // 入力が無効な場合は0を表示
        }
    });

});
	


